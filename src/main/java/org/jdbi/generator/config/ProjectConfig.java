package org.jdbi.generator.config;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jdbi.generator.main.AbstractComponent;
import org.jdbi.generator.validators.CheckPath;
import org.jdbi.generator.validators.StringValues;

import java.util.Set;


public class ProjectConfig extends AbstractComponent
{
    @JsonProperty("type")
    @NotNull
    //@StringValues("spring, quarkus")
    @StringValues("spring")
    protected String type;

    @JsonProperty("path")
    @NotBlank
    @CheckPath
    protected String path;

    @JsonProperty("main-package")
    @JsonAlias("mainPackage")
    @NotBlank
    protected String mainPackage;

    @JsonProperty("config-package")
    @JsonAlias("configPackage")
    @NotBlank
    protected String configPackage;

    @JsonProperty("model-package")
    @JsonAlias("modelPackage")
    @NotBlank
    protected String modelPackage;

    @JsonProperty("dao-package")
    @JsonAlias("daoPackage")
    @NotBlank
    protected String daoPackage;

    @JsonProperty("test-package")
    @JsonAlias("testPackage")
    @NotBlank
    protected String testPackage;

    @JsonProperty("use-lombok")
    @JsonAlias("useLombok")
    @NotNull
    protected Boolean useLombok;

    @JsonProperty("dto-builders")
    @JsonAlias("dtoBuilders")
    @NotNull
    protected Boolean dtoBuilders;

    @JsonProperty("timestamps-like-dates")
    @JsonAlias("timestampsLikeDates")
    @NotNull
    protected Boolean timestampsLikeDates;

    /* TODO: ??
    @JsonProperty("log-aspect")
    @JsonAlias("logAspect")
    @NotNull
    protected Boolean logAspect;
    */

    @JsonProperty("log-annotation")
    @JsonAlias("logAnnotation")
    protected String logAnnotation;

    @JsonProperty("tables")
    @NotNull
    protected TablesConfig tablesConfig;

    @JsonProperty("columns")
    @NotNull
    protected ColumnsConfig columnsConfig;


    public ProjectConfig()
    {
        super();
    }


    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getMainPackage()
    {
        return mainPackage;
    }

    public void setMainPackage(String mainPackage)
    {
        this.mainPackage = mainPackage;
    }

    public String getConfigPackage()
    {
        return configPackage;
    }

    public void setConfigPackage(String configPackage)
    {
        this.configPackage = configPackage;
    }

    public String getModelPackage()
    {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage)
    {
        this.modelPackage = modelPackage;
    }

    public String getDaoPackage()
    {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage)
    {
        this.daoPackage = daoPackage;
    }

    public String getTestPackage()
    {
        return testPackage;
    }

    public void setTestPackage(String testPackage)
    {
        this.testPackage = testPackage;
    }

    public Boolean getUseLombok()
    {
        return useLombok;
    }

    public void setUseLombok(Boolean useLombok)
    {
        this.useLombok = useLombok;
    }

    public Boolean getDtoBuilders()
    {
        return dtoBuilders;
    }

    public void setDtoBuilders(Boolean dtoBuilders)
    {
        this.dtoBuilders = dtoBuilders;
    }

    public Boolean getTimestampsLikeDates()
    {
        return timestampsLikeDates;
    }

    public void setTimestampsLikeDates(Boolean timestampsLikeDates)
    {
        this.timestampsLikeDates = timestampsLikeDates;
    }

    public String getLogAnnotation()
    {
        return logAnnotation;
    }

    public void setLogAnnotation(String logAnnotation)
    {
        this.logAnnotation = logAnnotation;
    }

    public TablesConfig getTablesConfig()
    {
        return tablesConfig;
    }

    public void setTablesConfig(TablesConfig tablesConfig)
    {
        this.tablesConfig = tablesConfig;
    }

    public ColumnsConfig getColumnsConfig()
    {
        return columnsConfig;
    }

    public void setColumnsConfig(ColumnsConfig columnsConfig)
    {
        this.columnsConfig = columnsConfig;
    }

    public Set<ConstraintViolation<?>> validate()
    {
        Set<ConstraintViolation<?>> constraintViolations = super.validate( this );

        if (isNotEmpty(tablesConfig))
            constraintViolations.addAll( tablesConfig.validate() );

        if (isNotEmpty(columnsConfig))
            constraintViolations.addAll( columnsConfig.validate() );

        return constraintViolations;
    }

}

