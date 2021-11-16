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

    @JsonProperty("main-package")
    @JsonAlias("mainPackage")
    @NotBlank
    protected String mainPackage;

    @JsonProperty("config-package")
    @JsonAlias("configPackage")
    @NotBlank
    protected String configPackage;

    @JsonProperty("test-package")
    @JsonAlias("testPackage")
    @NotBlank
    protected String testPackage;

    @JsonProperty("path")
    @NotBlank
    @CheckPath
    protected String path;

    @JsonAlias("lombok")
    @NotNull
    protected Boolean lombok;

    @JsonProperty("dto-builders")
    @JsonAlias("dtoBuilders")
    @NotNull
    protected Boolean dtoBuilders;

    @JsonProperty("timestamps-like-dates")
    @JsonAlias("timestampsLikeDates")
    @NotNull
    protected Boolean timestampsLikeDates;

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

    public String getTestPackage()
    {
        return testPackage;
    }

    public void setTestPackage(String testPackage)
    {
        this.testPackage = testPackage;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Boolean getLombok()
    {
        return lombok;
    }

    public void setLombok(Boolean lombok)
    {
        this.lombok = lombok;
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

    public Set<ConstraintViolation<Object>> validate()
    {
        Set<ConstraintViolation<Object>> constraintViolations = super.validate( this );

        if (isNotEmpty(tablesConfig))
            constraintViolations.addAll( tablesConfig.validate() );

        if (isNotEmpty(columnsConfig))
            constraintViolations.addAll( columnsConfig.validate() );

        return constraintViolations;
    }

}

