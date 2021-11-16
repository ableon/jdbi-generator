package org.jdbi.generator.templates;

import org.jdbi.generator.utils.Strings;


public class FieldData
{
    private String name;
    private String property;
    private String column;
    private String type;
    private boolean encrypted;
    private boolean invisible;
    private String jsonType;
    private String getter;
    private String setter;
    private String comment;


    public FieldData() {}


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
        this.getter   = "get"+Strings.capitalizeFirstChar(property);
        this.setter   = "set"+Strings.capitalizeFirstChar(property);
    }

    public String getColumn()
    {
        return column;
    }

    public void setColumn(String column)
    {
        this.column = column;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
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

    public String getJsonType()
    {
        return jsonType;
    }

    public void setJsonType(String jsonType)
    {
        this.jsonType = jsonType;
    }

    public String getGetter()
    {
        return getter;
    }

    public String getSetter()
    {
        return setter;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

}

