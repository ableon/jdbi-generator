package org.jdbi.generator.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import org.jdbi.generator.AbstractComponent;

import java.util.List;
import java.util.Set;


public class TablesConfig extends AbstractComponent
{
    @JsonProperty("includes")
    protected List<String> includes;

    @JsonProperty("excludes")
    protected List<String> excludes;

    @JsonProperty("catalogs")
    protected List<String> catalogs;


    public TablesConfig()
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

    public List<String> getCatalogs()
    {
        return catalogs;
    }

    public void setCatalogs(List<String> catalogs)
    {
        this.catalogs = catalogs;
    }

    public Set<ConstraintViolation<Object>> validate()
    {
        Set<ConstraintViolation<Object>> constraintViolations = super.validate( this );

        return constraintViolations;
    }

}

