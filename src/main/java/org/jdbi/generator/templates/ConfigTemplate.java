package org.jdbi.generator.templates;

import org.jdbi.generator.Workspace;
import org.jdbi.generator.explorer.DBTable;
import org.jdbi.generator.utils.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConfigTemplate extends AbstractTemplate
{
    private static final String[] SOURCE =
    {
        "templates/config/${ProjectType}DataSourceConfig.fm",
        "templates/config/${ProjectType}JdbiConfig.fm",
        "templates/config/${type}.properties.fm",
    };

    private static final String[] TARGET =
    {
        "${ClassName}DataSourceConfig.java",
        "${ClassName}JdbiConfig.java",
        "${type}.${DataSourcePropertiesName}.properties",
    };


    public ConfigTemplate(Workspace workspace, List<DBTable> tables)
    {
        super(workspace, tables);
    }


    private String getType()
    {
        return bool(getWorkspace().getJta()) ? "jta" : "datasource";
    }


    @Override
    public String getSource(int index, DBTable dbTable)
    {
        String source;

        if (getWorkspace().isSpring())
            source = SOURCE[index].replace("${ProjectType}", "Spring");
        else
        if (getWorkspace().isQuarkus())
            source = SOURCE[index].replace("${ProjectType}", "Quarkus");
        else
            source = SOURCE[index];

        return source.replace("${type}", getType());
    }


    @Override
    public String getTarget(int index, DBTable dbTable)
    {
        String target;

        if (TARGET[index].endsWith(".properties"))
        {
            target = getWorkspace().getDataSourcesResourcesDir() +
                     TARGET[index].replace("${DataSourcePropertiesName}", super.getDataSourcePropertiesName());
        }
        else
        {
            target = getWorkspace().getConfigDir() +
                     TARGET[index].replace("${ClassName}", Strings.toClassName(getWorkspace().getDataSourceName()));
        }

        return target.replace("${type}", getType());
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        Map<String, Object> map = new HashMap<>();

        String dataSourceName = getWorkspace().getDataSourceName();
        String className      = Strings.toClassName( dataSourceName );
        String objectName     = Strings.toPropertyName( dataSourceName );

        map.put("ConfigPackage", getWorkspace().getConfigPackage());
        map.put("AbstractDaoPackage", getWorkspace().getAbstractDaoPackage());
        map.put("datasources", Workspace.DATA_SOURCES);
        map.put("type", getType());
        map.put("DataSourceName", dataSourceName);
        map.put("DataSourceLowerCase", dataSourceName.toLowerCase());
        map.put("DataSourcePropertiesName", super.getDataSourcePropertiesName());
        map.put("DatabaseProductName", getWorkspace().getDbConnection().getDatabaseProductName());
        map.put("ClassName", className);
        map.put("objectName", objectName);
        map.put("driverClassName", getWorkspace().getDbConnection().getDriverClassName());
        map.put("jdbcUrl", getWorkspace().getDbConnection().getJdbcUrl());
        map.put("username", getWorkspace().getDbConnection().getUsername());
        map.put("password", getWorkspace().getDbConnection().getPassword());
        map.put("properties", getWorkspace().getDataSourceProperties());
        map.put("jta", bool(getWorkspace().getJta()));
        map.put("datasourceClassName", getWorkspace().getDatasourceClassName());
        map.put("atomikosProperties", getWorkspace().getAtomikosProperties());

        return map;
    }


    @Override
    public void generate() throws Exception
    {
        for (int index=0; index<SOURCE.length; index++)
            super.generate( index );
    }

}

