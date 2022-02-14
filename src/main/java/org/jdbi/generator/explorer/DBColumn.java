package org.jdbi.generator.explorer;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class DBColumn
{
    private String name;
    private String table;
    private String schema;
    private String catalog;
    private int sqlType;
    private String sqlTypeName;
    private int size;
    private int precision;
    private boolean nullable;
    private boolean autoIncrement;
    private boolean generated;
    private boolean encrypted;
    private boolean invisible;
    private boolean primaryKey;
    private boolean uniqueKey;
    private String foreignKey;
    private String foreignTable;
    private String externalForeignKey;
    private String externalForeignTable;
    private String className;
    private boolean quoted;


    public DBColumn() {}

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTable()
    {
        return table;
    }

    public void setTable(String table)
    {
        this.table = table;
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

    public int getSqlType()
    {
        return sqlType;
    }

    public void setSqlType(int sqlType)
    {
        this.sqlType = sqlType;
    }

    public String getSqlTypeName()
    {
        return sqlTypeName;
    }

    public void setSqlTypeName(String sqlTypeName)
    {
        this.sqlTypeName = sqlTypeName;
    }

    public int getPrecision()
    {
        return precision;
    }

    public void setPrecision(int precision)
    {
        this.precision = precision;
    }

    public boolean isNullable()
    {
        return nullable;
    }

    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }

    public boolean isAutoIncrement()
    {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement)
    {
        this.autoIncrement = autoIncrement;
    }

    public boolean isGenerated()
    {
        return generated;
    }

    public void setGenerated(boolean generated)
    {
        this.generated = generated;
    }

    public boolean isEncrypted()
    {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted)
    {
        this.encrypted = encrypted;
    }

    public boolean isInvisible()
    {
        return invisible;
    }

    public void setInvisible(boolean invisible)
    {
        this.invisible = invisible;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public boolean isPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public boolean isUniqueKey()
    {
        return uniqueKey;
    }

    public void setUniqueKey(boolean uniqueKey)
    {
        this.uniqueKey = uniqueKey;
    }

    public String getForeignKey()
    {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey)
    {
        this.foreignKey = foreignKey;
    }

    public String getForeignTable()
    {
        return foreignTable;
    }

    public void setForeignTable(String foreignTable)
    {
        this.foreignTable = foreignTable;
    }

    public String getExternalForeignKey()
    {
        return externalForeignKey;
    }

    public void setExternalForeignKey(String externalForeignKey)
    {
        this.externalForeignKey = externalForeignKey;
    }

    public String getExternalForeignTable()
    {
        return externalForeignTable;
    }

    public void setExternalForeignTable(String externalForeignTable)
    {
        this.externalForeignTable = externalForeignTable;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public boolean isQuoted()
    {
        return quoted;
    }

    public void setQuoted(boolean quoted)
    {
        this.quoted = quoted;
    }

    @JsonIgnore
    public boolean isJson()
    {
        return "json".equalsIgnoreCase(sqlTypeName);
    }

    @JsonIgnore
    public boolean isJsonB()
    {
        return "jsonb".equalsIgnoreCase(sqlTypeName);
    }

}

