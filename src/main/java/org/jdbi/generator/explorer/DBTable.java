package org.jdbi.generator.explorer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class DBTable
{
    private String name;
    private String type;
    private String schema;
    private String catalog;
    private boolean quoted;
    private final List<DBColumn> columnList = new ArrayList<>();
    private final List<DBColumn> primaryKeyColumnList = new ArrayList<>();
    private final List<DBColumn> uniqueKeyColumnList  = new ArrayList<>();
    private final List<DBColumn> foreignKeyColumnList = new ArrayList<>();
    private final Map<String,Object> catalogData = new LinkedHashMap<>();


    public DBTable() {}

    public DBTable(String name, String type, String schema, String catalog)
    {
        this.name    = name;
        this.type    = type;
        this.schema  = schema;
        this.catalog = catalog;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSchema()
    {
        return schema;
    }

    public void setSchema(String schema)
    {
        this.schema = schema;
    }

    public String getCatalog()
    {
        return catalog;
    }

    public void setCatalog(String catalog)
    {
        this.catalog = catalog;
    }

    public boolean isQuoted()
    {
        return quoted;
    }

    public void setQuoted(boolean quoted)
    {
        this.quoted = quoted;
    }

    public List<DBColumn> getColumnList()
    {
        return columnList;
    }

    public void addColumn(DBColumn dbColumn)
    {
        if (dbColumn != null)
            columnList.add( dbColumn );
    }

    public List<DBColumn> getPrimaryKeyColumnList()
    {
        return primaryKeyColumnList;
    }

    public void setPrimaryKeyColumn(String columnName)
    {
        for (DBColumn dbColumn : columnList)
        {
            if (dbColumn.getName().equals(columnName))
            {
                dbColumn.setPrimaryKey( true );

                if (!primaryKeyColumnList.contains(dbColumn))
                    primaryKeyColumnList.add( dbColumn );

                break;
            }
        }
    }

    public List<DBColumn> getUniqueKeyColumnList()
    {
        return uniqueKeyColumnList;
    }

    public void setUniqueKeyColumn(String columnName)
    {
        for (DBColumn dbColumn : columnList)
        {
            if (dbColumn.getName().equals(columnName))
            {
                if (dbColumn.isPrimaryKey())
                    break;

                dbColumn.setUniqueKey( true );

                if (!uniqueKeyColumnList.contains(dbColumn))
                    uniqueKeyColumnList.add( dbColumn );

                break;
            }
        }
    }

    public List<DBColumn> getForeignKeyColumnList()
    {
        return foreignKeyColumnList;
    }

    public void setForeignKey(String columnName, String fkTableName, String fkColumnName)
    {
        for (DBColumn dbColumn : columnList)
        {
            if (dbColumn.getName().equals(columnName))
            {
                dbColumn.setForeignKey(fkTableName + "." + fkColumnName);

                if (!foreignKeyColumnList.contains(dbColumn))
                    foreignKeyColumnList.add( dbColumn );

                break;
            }
        }
    }

    public Map<String, Object> getCatalogData()
    {
        return catalogData;
    }

    public void addCatalogData(String code, Object value)
    {
        this.catalogData.put(code, value);
    }

}

