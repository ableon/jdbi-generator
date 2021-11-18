package org.jdbi.generator.config;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;
import org.jdbi.generator.cli.Console;
import org.jdbi.generator.main.AbstractComponent;

import java.util.Map;
import java.util.Set;


public class JtaConfig extends AbstractComponent
{
    @JsonProperty("enabled")
    @NotNull
    protected Boolean enabled;

    @JsonProperty("datasource-class-name")
    @JsonAlias("datasourceClassName")
    protected String datasourceClassName;

    @JsonProperty("atomikos-properties")
    @JsonAlias("atomikosProperties")
    protected Map<String, String> properties;


    public JtaConfig() {}


    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getDatasourceClassName()
    {
        return datasourceClassName;
    }

    public void setDatasourceClassName(String datasourceClassName)
    {
        this.datasourceClassName = datasourceClassName;
    }

    public Map<String, String> getProperties()
    {
        return properties;
    }

    public void setProperties(Map<String, String> properties)
    {
        this.properties = properties;
    }

    public Set<ConstraintViolation<Object>> validate()
    {
        Set<ConstraintViolation<Object>> constraintViolations = super.validate( this );

        if (bool(enabled))
            if (isAnyNullOrEmpty(datasourceClassName))
                Console.throwValidationException("'JtaConfig.datasourceClassName' is undefined");

        return constraintViolations;
    }

}

