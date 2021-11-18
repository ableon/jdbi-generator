package org.jdbi.generator.templates;

import org.jdbi.generator.main.Workspace;
import org.jdbi.generator.explorer.DBTable;
import org.jdbi.generator.utils.Strings;

import java.util.List;
import java.util.Map;


public class SqlTemplate extends AbstractTemplate
{
    private static final String[] PROVIDERS =
    {
        "db2",
        "h2",
        "hsqldb",
        "mariadb",
        "mysql",
        "oracle",
        "postgresql",
        "sqlite",
        "sqlserver"
        //"sybase"
    };


    private static final String[] SOURCE =
    {
        "templates/sql/crud.ftl.xml",
        "templates/sql/crud_macros.ftl",
        "templates/sql/crud_macros_dialect_${DataBaseType}.ftl",
        "templates/sql/example.ftl.xml",
        "templates/sql/lookup.ftl.xml",
    };

    private static final String[] TARGET =
    {
        "crud.ftl.xml",
        "crud_macros.ftl",
        "crud_macros_dialect.ftl",
        "example.ftl.xml",
        "lookup.ftl.xml",
    };


    public SqlTemplate(Workspace workspace, List<DBTable> tables)
    {
        super(workspace, tables);
    }


    public static String checkDataBaseType(String databaseProductName)
    {
        for (String provider : PROVIDERS)
            if (databaseProductName.toLowerCase().contains(provider))
                return provider;

        throw new RuntimeException("unsupported '" + databaseProductName + "' database");
    }


    @Override
    public String getSource(int index, DBTable dbTable)
    {
        String dataBaseType = getWorkspace().getDbConnection().getDatabaseType();
        return SOURCE[index].replace("${DataBaseType}", dataBaseType);
    }


    @Override
    public String getTarget(int index, DBTable dbTable)
    {
        return getWorkspace().getDataSourceSqlResourcesDir() + TARGET[index];
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
        Strings.replace(template, "@@DataSourceLowerCase@@", getWorkspace().getDataSourceName().toLowerCase());
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        return null;
    }


    @Override
    public void generate() throws Exception
    {
        for (int index=0; index<SOURCE.length; index++)
            super.generate( index );
    }

}
