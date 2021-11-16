package org.jdbi.generator.config;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jdbi.generator.AbstractComponent;
import org.jdbi.generator.validators.CheckFile;
import org.jdbi.generator.validators.CheckVarName;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class DataSourceConfig extends AbstractComponent
{
    @JsonProperty("name")
    @NotNull
    @CheckVarName
    protected String name;

    @JsonProperty("driver-jar-and-dependencies")
    @JsonAlias("driverJarAndDependencies")
    @NotNull
    @CheckFile
    protected List<String> driverJarAndDependencies;

    @JsonProperty("driver-class-name")
    @JsonAlias("driverClassName")
    @NotBlank
    protected String driverClassName;

    @JsonProperty("jdbc-url")
    @JsonAlias("jdbcUrl")
    @NotBlank
    protected String jdbcUrl;

    @JsonProperty("username")
    @NotNull
    protected String username;

    @JsonProperty("password")
    @NotNull
    protected String password;

    @JsonProperty("properties")
    protected Map<String,String> properties;


    public DataSourceConfig()
    {
        super();
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<String> getDriverJarAndDependencies()
    {
        return driverJarAndDependencies;
    }

    public void setDriverJarAndDependencies(List<String> driverJarAndDependencies)
    {
        this.driverJarAndDependencies = driverJarAndDependencies;
    }

    public String getDriverClassName()
    {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName)
    {
        this.driverClassName = driverClassName;
    }

    public String getJdbcUrl()
    {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl)
    {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
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

        return constraintViolations;
    }

}

