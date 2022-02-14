package org.jdbi.generator.config;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;
import org.jdbi.generator.main.AbstractComponent;

import java.util.Set;


public class GeneratorConfig extends AbstractComponent
{
    @JsonProperty("generate-config")
    @JsonAlias("generateConfig")
    @NotNull
    protected Boolean generateConfig;

    @JsonProperty("generate-resources")
    @JsonAlias("generateResources")
    @NotNull
    protected Boolean generateResources;

    @JsonProperty("generate-entities")
    @JsonAlias("generateEntities")
    @NotNull
    protected Boolean generateEntities;

    @JsonProperty("generate-lookups")
    @JsonAlias("generateLookups")
    @NotNull
    protected Boolean generateLookups;

    @JsonProperty("generate-catalogs")
    @JsonAlias("generateCatalogs")
    @NotNull
    protected Boolean generateCatalogs;

    @JsonProperty("generate-crud-daos")
    @JsonAlias("generateCrudDaos")
    @NotNull
    protected Boolean generateCrudDaos;

    @JsonProperty("generate-crud-tests")
    @JsonAlias("generateCrudTests")
    @NotNull
    protected Boolean generateCrudTests;

    @JsonProperty("jta")
    @NotNull
    protected JtaConfig jtaConfig;

    @JsonProperty("datasource")
    @NotNull
    protected DataSourceConfig dataSourceConfig;

    @JsonProperty("project")
    @NotNull
    protected ProjectConfig projectConfig;


    public GeneratorConfig()
    {
        super();
    }


    public Boolean getGenerateConfig()
    {
        return generateConfig;
    }

    public void setGenerateConfig(Boolean generateConfig)
    {
        this.generateConfig = generateConfig;
    }

    public Boolean getGenerateResources()
    {
        return generateResources;
    }

    public void setGenerateResources(Boolean generateResources)
    {
        this.generateResources = generateResources;
    }

    public Boolean getGenerateEntities()
    {
        return generateEntities;
    }

    public void setGenerateEntities(Boolean generateEntities)
    {
        this.generateEntities = generateEntities;
    }

    public Boolean getGenerateLookups()
    {
        return generateLookups;
    }

    public void setGenerateLookups(Boolean generateLookups)
    {
        this.generateLookups = generateLookups;
    }

    public Boolean getGenerateCatalogs()
    {
        return generateCatalogs;
    }

    public void setGenerateCatalogs(Boolean generateCatalogs)
    {
        this.generateCatalogs = generateCatalogs;
    }

    public Boolean getGenerateCrudDaos()
    {
        return generateCrudDaos;
    }

    public void setGenerateCrudDaos(Boolean generateCrudDaos)
    {
        this.generateCrudDaos = generateCrudDaos;
    }

    public Boolean getGenerateCrudTests()
    {
        return generateCrudTests;
    }

    public void setGenerateCrudTests(Boolean generateCrudTests)
    {
        this.generateCrudTests = generateCrudTests;
    }

    public JtaConfig getJtaConfig()
    {
        return jtaConfig;
    }

    public void setJtaConfig(JtaConfig jtaConfig)
    {
        this.jtaConfig = jtaConfig;
    }

    public DataSourceConfig getDataSourceConfig()
    {
        return dataSourceConfig;
    }

    public void setDataSourceConfig(DataSourceConfig dataSourceConfig)
    {
        this.dataSourceConfig = dataSourceConfig;
    }

    public ProjectConfig getProjectConfig()
    {
        return projectConfig;
    }

    public void setProjectConfig(ProjectConfig projectConfig)
    {
        this.projectConfig = projectConfig;
    }

    public Set<ConstraintViolation<?>> validate()
    {
        Set<ConstraintViolation<?>> constraintViolations = super.validate( this );

        if (isNotEmpty(jtaConfig))
            constraintViolations.addAll( jtaConfig.validate() );

        if (isNotEmpty(dataSourceConfig))
            constraintViolations.addAll( dataSourceConfig.validate() );

        if (isNotEmpty(projectConfig))
            constraintViolations.addAll( projectConfig.validate() );

        return constraintViolations;
    }

}

