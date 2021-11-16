package org.jdbi.generator.explorer;

import jakarta.validation.ValidationException;
import org.jdbi.generator.AbstractComponent;
import org.jdbi.generator.cli.Console;
import org.jdbi.generator.config.Config;
import org.jdbi.generator.config.DataSourceConfig;
import org.jdbi.generator.config.GeneratorConfig;
import org.jdbi.generator.mapper.Mapper;
import org.jdbi.generator.templates.SqlTemplate;
import org.jdbi.generator.utils.Resources;
import org.jdbi.generator.utils.Strings;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Explorer extends AbstractComponent implements AbstractResultSet
{
    private final DBConnection dbConnection = new DBConnection();
    private final List<DBTable> tables = new ArrayList<>();


    public Explorer()
    {
        super();
    }


    public DBConnection getDbConnection()
    {
        return dbConnection;
    }


    public List<DBTable> getTables()
    {
        return tables;
    }


    private Connection getConnection(DataSourceConfig dataSourceConfig) throws Exception
    {
        return DriverManager.getConnection( dataSourceConfig.getJdbcUrl(),
                                            dataSourceConfig.getUsername(),
                                            dataSourceConfig.getPassword() );
    }


    private void close(Connection conn, Statement stmt, ResultSet rst)
    {
        try { if (rst  != null) rst.close();  } catch (Exception e) { /**/ }
        try { if (stmt != null) stmt.close(); } catch (Exception e) { /**/ }
        try { if (conn != null) conn.close(); } catch (Exception e) { /**/ }
    }


    public void explore(Config config) throws Exception
    {
        // load driver jar
        for (String fileName : config.getGeneratorConfig().getDataSourceConfig().getDriverJarAndDependencies())
            Resources.addToClassPath( fileName );

        // init driver
        Class.forName( config.getGeneratorConfig().getDataSourceConfig().getDriverClassName() );

        // explore database
        exploreDatabase( config.getGeneratorConfig() );
    }


    private boolean isIncluded(String name, List<String> includes)
    {
        // not included
        if (name == null || name.trim().isEmpty()/* || name.contains("$")*/)
            return false;

        // validate if is included
        if (includes != null && !includes.isEmpty())
            return Strings.checkWildcard(name, includes, true);

        // included by default
        return true;
    }


    private boolean isExcluded(String name, List<String> excludes)
    {
        // excluded
        if (name == null || name.trim().isEmpty()/* || name.contains("$")*/)
            return true;

        // validate if is excluded
        if (excludes != null && !excludes.isEmpty())
            return Strings.checkWildcard(name, excludes, true);

        // not excluded by default
        return false;
    }


    private void exploreDatabase(GeneratorConfig generatorConfig) throws Exception
    {
        Connection conn = null;
        ResultSet rst = null;

        try
        {
            conn = getConnection( generatorConfig.getDataSourceConfig() );
            DatabaseMetaData dbMetaData = conn.getMetaData();

            dbConnection.setDatabaseType( SqlTemplate.checkDataBaseType( dbMetaData.getDatabaseProductName()) );
            dbConnection.setDataSource( generatorConfig.getDataSourceConfig().getName() );
            dbConnection.setDriverJarAndDependencies( generatorConfig.getDataSourceConfig().getDriverJarAndDependencies() );
            dbConnection.setDriverClassName( generatorConfig.getDataSourceConfig().getDriverClassName() );
            dbConnection.setQuoteId( dbMetaData.getIdentifierQuoteString() );
            dbConnection.setJdbcUrl( generatorConfig.getDataSourceConfig().getJdbcUrl() );
            dbConnection.setUsername( generatorConfig.getDataSourceConfig().getUsername() );
            dbConnection.setPassword( generatorConfig.getDataSourceConfig().getPassword() );
            dbConnection.setDatabaseProductName( dbMetaData.getDatabaseProductName() );
            dbConnection.setDatabaseProductVersion( dbMetaData.getDatabaseProductVersion() );


            // filter by includes and excludes

            int totalTables    = 0;
            int includedTables = 0;
            int excludedTables = 0;
            int catalogsTables = 0;

            List<String> includes = generatorConfig.getProjectConfig().getTablesConfig().getIncludes();
            List<String> excludes = generatorConfig.getProjectConfig().getTablesConfig().getExcludes();
            List<String> catalogs = generatorConfig.getProjectConfig().getTablesConfig().getCatalogs();

            includes = removeNullsAndBlanks( includes );
            excludes = removeNullsAndBlanks( excludes );
            catalogs = removeNullsAndBlanks( catalogs );

            /*
                table types: "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"

                The getTables() method return a ResultSet with 5 Columns

                1. TABLE_CAT String     - table catalog (may be null)
                2. TABLE_SCHEM String   - table schema (may be null)
                3. TABLE_NAME String    - table name
                4. TABLE_TYPE String    - table type
                5. REMARKS String       - explanatory comment on the table
            */

            String[] types = { "TABLE", "VIEW" };
            rst = dbMetaData.getTables(conn.getCatalog(), conn.getSchema(), "%", types);

            while (rst.next())
            {
                totalTables++;

                String tableCatalog = getString(rst, "TABLE_CAT");
                String tableSchema  = getString(rst, "TABLE_SCHEM");
                String tableName    = getString(rst, "TABLE_NAME");
                String tableType    = getString(rst, "TABLE_TYPE");
                String remarks      = getString(rst, "REMARKS");

                boolean included = isIncluded(tableName, includes);
                boolean excluded = isExcluded(tableName, excludes);

                if (excluded)
                    excludedTables++;

                if (!included || excluded)
                    continue;

                if (included)
                    includedTables++;

                // table
                DBTable dbTable = new DBTable(tableName, tableType, tableSchema, tableCatalog);

                // quoted
                dbTable.setQuoted( !Strings.isValidIdentifier(tableName) );

                // columns
                exploreColumns(dbTable, dbMetaData, generatorConfig);

                // primary keys
                explorePrimaryKeys(dbTable, dbMetaData);

                // unique keys
                exploreUniqueKeys(dbTable, dbMetaData);

                // foreign keys
                exploreForeignKeys(dbTable, dbMetaData);

                // lookupsData and catalogsData
                if (!catalogs.isEmpty())
                {
                    if (isIncluded(tableName, catalogs))
                    {
                        catalogsTables++;
                        readCatalogsData(conn, dbTable, dbConnection.getQuoteId(), generatorConfig);
                    }
                }

                tables.add( dbTable );
            }

            // verbose
            for (DBTable dbTable : tables)
            {
                Console.verbose("---" +
                                Console.lineSeparator +
                                "Table " + dbTable.getName() + ":" +
                                Console.lineSeparator +
                                Mapper.getInstance().toYaml(dbTable), true);
            }

            Console.info("Total Tables    ", totalTables);
            Console.info("Included Tables ", includedTables);
            Console.info("Excluded Tables ", excludedTables);
            Console.info("Catalogs Tables ", catalogsTables);
        }
        finally
        {
            close(conn, null, rst);
        }
    }


    private void exploreColumns(DBTable dbTable,
                                DatabaseMetaData dbMetaData,
                                GeneratorConfig generatorConfig) throws Exception
    {
        ResultSet rst = null;

        try
        {
            List<String> includes  = generatorConfig.getProjectConfig().getColumnsConfig().getIncludes();
            List<String> excludes  = generatorConfig.getProjectConfig().getColumnsConfig().getExcludes();
            List<String> encrypted = generatorConfig.getProjectConfig().getColumnsConfig().getEncrypted();
            List<String> invisible = generatorConfig.getProjectConfig().getColumnsConfig().getInvisible();

            includes  = removeNullsAndBlanks( includes );
            excludes  = removeNullsAndBlanks( excludes );
            encrypted = removeNullsAndBlanks( encrypted );
            invisible = removeNullsAndBlanks( invisible );

            rst = dbMetaData.getColumns(dbTable.getCatalog(), dbTable.getSchema(), dbTable.getName(), "%");

            while (rst.next())
            {
                String tableCatalog = getString(rst, "TABLE_CAT");
                String tableSchema  = getString(rst, "TABLE_SCHEM");
                String tableName    = getString(rst, "TABLE_NAME");
                String columnName   = getString(rst, "COLUMN_NAME");

                String name = (tableName + "." + columnName);

                boolean included = isIncluded(name, includes);
                boolean excluded = isExcluded(name, excludes);

                if (!included || excluded)
                    continue;

                Integer sqlType         = getInt(rst,   "DATA_TYPE"); // java.sql.Types
                String sqlTypeName      = getString(rst, "TYPE_NAME");
                Integer size            = getInt(rst, "COLUMN_SIZE");
                Integer precision       = getInt(rst, "DECIMAL_DIGITS");
                Integer numPrecRadix    = getInt(rst, "NUM_PREC_RADIX");
                Integer nullable        = getInt(rst, "NULLABLE");  // ResultSetMetaData.columnNullable
                String remarks          = getString(rst, "REMARKS");
                String columnDef        = getString(rst, "COLUMN_DEF");
                Integer charOctetLength = getInt(rst, "CHAR_OCTET_LENGTH");
                Integer ordinalPosition = getInt(rst, "ORDINAL_POSITION");      // starting at 1
                String isNullable       = getString(rst, "IS_NULLABLE");        // YES, NO, <empty>
                String isAutoIncrement  = getString(rst, "IS_AUTOINCREMENT");   // YES, NO, <empty>
                String isGenerated      = getString(rst, "IS_GENERATEDCOLUMN"); // YES, NO, <empty>
                String scopeCatalog     = getString(rst, "SCOPE_CATALOG");
                String scopeSchema      = getString(rst, "SCOPE_SCHEMA");
                String scopeTable       = getString(rst, "SCOPE_TABLE");
                Integer sourceDataType  = getInt(rst, "SOURCE_DATA_TYPE"); // java.sql.Types


                // column
                DBColumn dbColumn = new DBColumn();

                dbColumn.setQuoted( !Strings.isValidIdentifier(columnName) );
                dbColumn.setName( columnName );
                dbColumn.setTable( tableName );
                dbColumn.setSchema( tableSchema );
                dbColumn.setCatalog( tableCatalog );
                dbColumn.setSqlType( sqlType );
                dbColumn.setSqlTypeName( sqlTypeName );
                dbColumn.setSize( size );
                dbColumn.setPrecision( precision );
                dbColumn.setNullable( "YES".equalsIgnoreCase(isNullable) );
                dbColumn.setAutoIncrement( "YES".equalsIgnoreCase(isAutoIncrement) );
                dbColumn.setGenerated( "YES".equalsIgnoreCase(isGenerated) );
                dbColumn.setClassName( dbConnection.getColumnClassName(sqlType, sqlTypeName) );

                if (dbColumn.getClassName().equals(Object.class.getName()))
                {
                    if (dbColumn.isJson())
                        dbColumn.setClassName(String.class.getName());
                    else
                    if (dbColumn.isJsonB())
                        dbColumn.setClassName(String.class.getName());
                }

                // encrypted: only strings
                if (!encrypted.isEmpty())
                {
                    if (dbColumn.getClassName().equals(String.class.getName()))
                        dbColumn.setEncrypted( isIncluded(name, encrypted) );
                }

                // invisible
                if (!invisible.isEmpty())
                {
                    dbColumn.setInvisible( isIncluded(name, invisible) );
                }

                dbTable.addColumn( dbColumn );
            }
        }
        finally
        {
            close(null, null, rst);
        }
    }


    private void explorePrimaryKeys(DBTable dbTable, DatabaseMetaData dbMetaData) throws Exception
    {
        ResultSet rst = null;

        try
        {
            rst = dbMetaData.getPrimaryKeys(dbTable.getCatalog(), dbTable.getSchema(), dbTable.getName());

            while (rst.next())
            {
                String tableCatalog = getString(rst, "TABLE_CAT");
                String tableSchema  = getString(rst, "TABLE_SCHEM");
                String tableName    = getString(rst, "TABLE_NAME");
                String columnName   = getString(rst, "COLUMN_NAME");
                Integer keySeq      = getInt(rst, "KEY_SEQ");
                String pkName       = getString(rst, "PK_NAME");

                dbTable.setPrimaryKeyColumn( columnName );
            }

            if (dbTable.getPrimaryKeyColumnList().isEmpty())
                throw new ValidationException("table '" + dbTable.getName() + "' has not primary key");
        }
        finally
        {
            close(null, null, rst);
        }
    }


    private void exploreUniqueKeys(DBTable dbTable, DatabaseMetaData dbMetaData) throws Exception
    {
        ResultSet rst = null;

        try
        {
            final boolean unique = true;
            final boolean approximate = false;
            rst = dbMetaData.getIndexInfo(dbTable.getCatalog(), dbTable.getSchema(), dbTable.getName(), unique, approximate);

            while (rst.next())
            {
                String tableCatalog = getString(rst, "TABLE_CAT");
                String tableSchema  = getString(rst, "TABLE_SCHEM");
                String tableName    = getString(rst, "TABLE_NAME");
                String columnName   = getString(rst, "COLUMN_NAME");
                Boolean isNonUnique = getBoolean(rst, "NON_UNIQUE");
                String indexCatalog = getString(rst, "INDEX_QUALIFIER");
                String indexName    = getString(rst, "INDEX_NAME");

                dbTable.setUniqueKeyColumn( columnName );
            }
        }
        finally
        {
            close(null, null, rst);
        }
    }


    private void exploreForeignKeys(DBTable dbTable, DatabaseMetaData dbMetaData) throws Exception
    {
        ResultSet rst = null;

        try
        {
            rst = dbMetaData.getImportedKeys(dbTable.getCatalog(), dbTable.getSchema(), dbTable.getName());

            while (rst.next())
            {
                String pkTableName  = getString(rst, "PKTABLE_NAME");
                String pkColumnName = getString(rst, "PKCOLUMN_NAME");
                String fkTableName  = getString(rst, "FKTABLE_NAME");
                String fkColumnName = getString(rst, "FKCOLUMN_NAME");
                Integer keySeq      = getInt(rst, "KEY_SEQ");

                dbTable.setForeignKey(fkColumnName, pkTableName, pkColumnName);
            }
        }
        finally
        {
            close(null, null, rst);
        }
    }


    private void readCatalogsData(Connection conn, DBTable dbTable, String quoteId, GeneratorConfig generatorConfig)
    {
        final String prefix = "read catalogs data: ";

        if (super.getSize(dbTable.getPrimaryKeyColumnList()) > 1)
            throw new ValidationException(prefix + "'" + dbTable.getName() + "' has multiple primary key");

        DBColumn dbColumnPK = dbTable.getPrimaryKeyColumnList().get(0);

        List<String> catalogs = generatorConfig.getProjectConfig().getColumnsConfig().getCatalogs();
        catalogs = removeNullsAndBlanks( catalogs );

        Statement stmt = null;
        ResultSet rst  = null;

        for (String catalog : catalogs)
        {
            try
            {
                String query = "select "
                                + quoteId + catalog + quoteId
                                + ", "
                                + quoteId + dbColumnPK.getName() + quoteId
                                + " from "
                                + quoteId + dbTable.getName() + quoteId
                                + " order by "
                                + quoteId + dbColumnPK.getName() + quoteId
                                + " asc";

                stmt = conn.createStatement();
                rst  = stmt.executeQuery( query );

                while (rst.next())
                {
                    Object code  = rst.getObject( catalog );
                    Object value = rst.getObject( dbColumnPK.getName() );

                    String fieldName = dbTable.getName() + "." + catalog;

                    if (code == null)
                    {
                        throw new ValidationException(prefix + "'" + fieldName + "' is null");
                    }
                    else
                    if (code instanceof String)
                    {
                        dbTable.addCatalogData((String)code, value);
                    }
                    else
                    {
                        throw new ValidationException(prefix + "'" + fieldName + "' is not a String class");
                    }
                }

                if (!dbTable.getCatalogData().isEmpty())
                    break;
            }
            catch (Exception e)
            {
                // ignore
            }
            finally
            {
                close(null, stmt, rst);
            }
        }
    }

}

