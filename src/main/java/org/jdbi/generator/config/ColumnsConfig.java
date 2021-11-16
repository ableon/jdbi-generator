package org.jdbi.generator.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import org.jdbi.generator.main.AbstractComponent;

import java.util.List;
import java.util.Set;


public class ColumnsConfig extends AbstractComponent
{
    @JsonProperty("includes")
    protected List<String> includes;

    @JsonProperty("excludes")
    protected List<String> excludes;

    @JsonProperty("lookups")
    protected List<String> lookups;

    @JsonProperty("catalogs")
    protected List<String> catalogs;

    @JsonProperty("encrypted")
    protected List<String> encrypted;

    @JsonProperty("invisible")
    protected List<String> invisible;


    public ColumnsConfig()
    {
        super();
    }

    public List<String> getIncludes()
    {
        return includes;
    }

    public void setIncludes(List<String> includes)
    {
        this.includes = includes;
    }

    public List<String> getExcludes()
    {
        return excludes;
    }

    public void setExcludes(List<String> excludes)
    {
        this.excludes = excludes;
    }

    public List<String> getLookups()
    {
        return lookups;
    }

    public void setLookups(List<String> lookups)
    {
        this.lookups = lookups;
    }

    public List<String> getCatalogs()
    {
        return catalogs;
    }

    public void setCatalogs(List<String> catalogs)
    {
        this.catalogs = catalogs;
    }

    public List<String> getEncrypted()
    {
        return encrypted;
    }

    public void setEncrypted(List<String> encrypted)
    {
        this.encrypted = encrypted;
    }

    public List<String> getInvisible()
    {
        return invisible;
    }

    public void setInvisible(List<String> invisible)
    {
        this.invisible = invisible;
    }

    public Set<ConstraintViolation<Object>> validate()
    {
        Set<ConstraintViolation<Object>> constraintViolations = super.validate( this );

        return constraintViolations;
    }

}

