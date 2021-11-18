package org.jdbi.generator.main;

import org.jdbi.generator.config.DataSourceConfig;
import org.jdbi.generator.config.JtaConfig;
import org.jdbi.generator.config.ProjectConfig;
import org.jdbi.generator.explorer.DBConnection;
import org.jdbi.generator.utils.Strings;

import java.io.File;
import java.util.Map;


public class Workspace
{
    public static final String SPRING       = "spring";
    public static final String QUARKUS      = "quarkus";
    public static final String JDBI         = "jdbi";
    public static final String SRC          = "src";
    public static final String MAIN         = "main";
    public static final String TEST         = "test";
    public static final String JAVA         = "java";
    public static final String RESOURCES    = "resources";
    public static final String DATA_SOURCES = "datasources";
    public static final String BASE         = "_base";
    public static final String CONSTRAINTS  = "constraints";
    public static final String ENTITIES     = "entities";
    public static final String CATALOGS     = "catalogs";
    public static final String DAO          = "dao";
    public static final String CRUD         = "crud";
    public static final String LOOKUP       = "lookup";
    public static final String CUSTOM       = "custom";

    private Boolean jta;
    private String datasourceClassName;
    private Map<String, String> jtaProperties;

    private String dataSourceName;
    private Map<String, String> dataSourceProperties;
    private String projectType;
    private String projectPath;
    private String mainPackage;
    private String configPackage;
    private String modelPackage;
    private String daoPackage;
    private String testPackage;
    private Boolean useLombok;
    private Boolean dtoBuilders;
    private Boolean timestampsLikeDates;
    private String logAnnotation;
    private DBConnection dbConnection;


    public Workspace()
    {
        super();
    }


    public Workspace(JtaConfig jtaConfig, DataSourceConfig dataSourceConfig, ProjectConfig projectConfig)
    {
        super();

        setJta( jtaConfig.getEnabled() );
        setDatasourceClassName( jtaConfig.getDatasourceClassName() );
        setJtaProperties( jtaConfig.getProperties() );
        setDataSourceName( dataSourceConfig.getName() );
        setDataSourceProperties( dataSourceConfig.getProperties() );
        setProjectType( projectConfig.getType() );
        setProjectPath( projectConfig.getPath() );
        setMainPackage( projectConfig.getMainPackage() );
        setConfigPackage( projectConfig.getConfigPackage() );
        setModelPackage( projectConfig.getModelPackage() );
        setDaoPackage( projectConfig.getDaoPackage() );
        setTestPackage( projectConfig.getTestPackage() );
        setUseLombok( projectConfig.getUseLombok() );
        setDtoBuilders( projectConfig.getDtoBuilders() );
        setTimestampsLikeDates( projectConfig.getTimestampsLikeDates() );
        setLogAnnotation( projectConfig.getLogAnnotation() );
    }

    public Boolean getJta()
    {
        return jta;
    }

    public void setJta(Boolean jta)
    {
        this.jta = jta;
    }

    public String getDatasourceClassName()
    {
        return datasourceClassName;
    }

    public void setDatasourceClassName(String datasourceClassName)
    {
        this.datasourceClassName = datasourceClassName;
    }

    public Map<String, String> getJtaProperties()
    {
        return jtaProperties;
    }

    public void setJtaProperties(Map<String, String> jtaProperties)
    {
        this.jtaProperties = jtaProperties;
    }

    public String getDataSourceName()
    {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName)
    {
        this.dataSourceName = dataSourceName;
    }

    public Map<String, String> getDataSourceProperties()
    {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(Map<String, String> dataSourceProperties)
    {
        this.dataSourceProperties = dataSourceProperties;
    }

    public String getProjectType()
    {
        return projectType;
    }

    public void setProjectType(String projectType)
    {
        this.projectType = projectType;
    }

    public String getProjectPath()
    {
        return projectPath;
    }

    public void setProjectPath(String projectPath)
    {
        this.projectPath = projectPath;
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

    public Boolean getDtoBuilders()
    {
        return dtoBuilders;
    }

    public void setDtoBuilders(Boolean dtoBuilders)
    {
        this.dtoBuilders = dtoBuilders;
    }

    public Boolean getUseLombok()
    {
        return useLombok;
    }

    public void setUseLombok(Boolean useLombok)
    {
        this.useLombok = useLombok;
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

    public boolean isLog()
    {
        return (logAnnotation != null && !logAnnotation.trim().isEmpty());
    }

    public boolean isSpring()
    {
        return SPRING.equalsIgnoreCase(projectType);
    }

    public boolean isQuarkus()
    {
        return QUARKUS.equalsIgnoreCase(projectType);
    }

    public DBConnection getDbConnection()
    {
        return dbConnection;
    }

    public void setDbConnection(DBConnection dbConnection)
    {
        this.dbConnection = dbConnection;
    }


    private void createDir(String dirName)
    {
        if (Strings.isNullOrEmpty(dirName))
            return;

        File dir = new File(dirName);

        if (!dir.exists())
            dir.mkdirs();
    }

    private String toJavaPath(String path)
    {
        path = path.replace('\\', '/');

        if (!path.endsWith("/"))
            return path + "/";
        else
            return path;
    }


    //
    // Paths
    //

    public String getMainPackagePath()
    {
        return toJavaPath( mainPackage.replace('.', '/') );
    }

    public String getConfigPackagePath()
    {
        return toJavaPath( configPackage.replace('.', '/') );
    }

    public String getModelPackagePath()
    {
        return toJavaPath( modelPackage.replace('.', '/') );
    }

    public String getDaoPackagePath()
    {
        return toJavaPath( daoPackage.replace('.', '/') );
    }

    public String getTestPackagePath()
    {
        return toJavaPath( testPackage.replace('.', '/') );
    }

    public String getProjectDir()
    {
        return toJavaPath( getProjectPath() );
    }

    public String getSrcDir()
    {
        return getProjectDir() + toJavaPath( SRC );
    }

    public String getMainDir()
    {
        return getSrcDir() + toJavaPath( MAIN );
    }

    public String getJavaDir()
    {
        return getMainDir() + toJavaPath( JAVA );
    }

    public String getResourcesDir()
    {
        return getMainDir() + toJavaPath( RESOURCES );
    }

    public String getTestDir()
    {
        return getSrcDir() + toJavaPath( TEST );
    }

    public String getTestJavaDir()
    {
        return getTestDir() + toJavaPath( JAVA );
    }

    public String getTestResourcesDir()
    {
        return getTestDir() + toJavaPath( RESOURCES );
    }

    public String getConfigDir()
    {
        return getJavaDir() + getConfigPackagePath();
    }

    public String getModelDir()
    {
        return getJavaDir() + getModelPackagePath() + toJavaPath( dataSourceName.toLowerCase() );
    }

    public String getEntitiesDir()
    {
        return getModelDir() + toJavaPath( ENTITIES );
    }

    public String getLookUpDir()
    {
        return getModelDir() + toJavaPath( LOOKUP );
    }

    public String getCustomDir()
    {
        return getModelDir() + toJavaPath( CUSTOM );
    }

    public String getCatalogsDir()
    {
        return getModelDir() + toJavaPath( CATALOGS );
    }

    public String getDaoDir()
    {
        return getJavaDir() + getDaoPackagePath();
    }

    public String getDataSourceDaoDir()
    {
        return getDaoDir() + toJavaPath( dataSourceName.toLowerCase() );
    }

    public String getBaseDaoDir()
    {
        return getDaoDir() + toJavaPath(BASE);
    }

    public String getEncoderDaoDir()
    {
        return getBaseDaoDir() + toJavaPath( "encoder" );
    }

    public String getFreemarkerDaoDir()
    {
        return getBaseDaoDir() + toJavaPath( "freemarker" );
    }

    public String getMultiTenantDaoDir()
    {
        return getBaseDaoDir() + toJavaPath( "multitenant" );
    }

    public String getPaginationDaoDir()
    {
        return getBaseDaoDir() + toJavaPath( "pagination" );
    }

    public String getCrudDaoDir()
    {
        return getDataSourceDaoDir() + toJavaPath( CRUD );
    }

    public String getLookUpDaoDir()
    {
        return getDataSourceDaoDir() + toJavaPath( LOOKUP );
    }

    public String getCustomDaoDir()
    {
        return getDataSourceDaoDir() + toJavaPath( CUSTOM );
    }

    public String getTestDaoDir()
    {
        return getTestJavaDir() + getTestPackagePath() + toJavaPath( DAO );
    }

    public String getTestBaseDaoDir()
    {
        return getTestDaoDir() + toJavaPath(BASE);
    }

    public String getTestConstraintsDaoDir()
    {
        return getTestBaseDaoDir() + toJavaPath( CONSTRAINTS );
    }

    public String getDataSourceTestDaoDir()
    {
        return getTestDaoDir() + toJavaPath( dataSourceName.toLowerCase() );
    }

    public String getTestCrudDaoDir()
    {
        return getDataSourceTestDaoDir() + toJavaPath( CRUD );
    }

    public String getTestLookUpDaoDir()
    {
        return getDataSourceTestDaoDir() + toJavaPath( LOOKUP );
    }

    public String getTestCustomDaoDir()
    {
        return getDataSourceTestDaoDir() + toJavaPath( CUSTOM );
    }

    public String getDataSourcesResourcesDir()
    {
        return getResourcesDir() + toJavaPath( DATA_SOURCES );
    }

    public String getSqlResourcesDir()
    {
        return getResourcesDir() + toJavaPath( JDBI );
    }

    public String getDataSourceSqlResourcesDir()
    {
        return getSqlResourcesDir() + toJavaPath( dataSourceName.toLowerCase() );
    }


    //
    // Packages
    //

    private String getPackage(String path)
    {
        path = path.replace('\\', '/');

        if (path.endsWith("/"))
            path = path.substring(0, path.length()-1);

        int index = path.indexOf( JAVA );

        if (index >= 0)
            index += (JAVA.length() + 1);
        else
            index = 0;

        return path.substring(index).replace('/', '.');
    }

    public String getEntitiesPackage()
    {
        return getPackage( getEntitiesDir() );
    }

    public String getLookUpPackage()
    {
        return getPackage( getLookUpDir() );
    }

    public String getCustomPackage()
    {
        return getPackage( getCustomDir() );
    }

    public String getCatalogsPackage()
    {
        return getPackage( getCatalogsDir() );
    }

    public String getBaseDaoPackage()
    {
        return getPackage( getBaseDaoDir() );
    }

    public String getEncoderDaoPackage()
    {
        return getPackage( getEncoderDaoDir() );
    }

    public String getFreemarkerDaoPackage()
    {
        return getPackage( getFreemarkerDaoDir() );
    }

    public String getMultiTenantDaoPackage()
    {
        return getPackage( getMultiTenantDaoDir() );
    }

    public String getPaginationDaoPackage()
    {
        return getPackage( getPaginationDaoDir() );
    }

    public String getCrudDaoPackage()
    {
        return getPackage( getCrudDaoDir() );
    }

    public String getLookUpDaoPackage()
    {
        return getPackage( getLookUpDaoDir() );
    }

    public String getCustomDaoPackage()
    {
        return getPackage( getCustomDaoDir() );
    }

    public String getTestBaseDaoPackage()
    {
        return getPackage( getTestBaseDaoDir() );
    }

    public String getTestConstraintsDaoPackage()
    {
        return getPackage( getTestConstraintsDaoDir() );
    }

    public String getTestCrudDaoPackage()
    {
        return getPackage( getTestCrudDaoDir() );
    }

    public String getTestLookUpDaoPackage()
    {
        return getPackage( getTestLookUpDaoDir() );
    }

    public String getTestCustomDaoPackage()
    {
        return getPackage( getTestCustomDaoDir() );
    }



    public void create()
    {
        // root
        createDir( getMainDir() );
        createDir( getJavaDir() );
        createDir( getResourcesDir() );

        // test
        createDir( getTestDir() );
        createDir( getTestJavaDir() );
        createDir( getTestResourcesDir() );

        // config
        createDir( getConfigDir() );

        // model
        createDir( getModelDir() );
        createDir( getEntitiesDir() );
        createDir( getLookUpDir() );
        createDir( getCatalogsDir() );

        // dao
        createDir( getDaoDir() );
        createDir( getBaseDaoDir() );
        createDir( getEncoderDaoDir() );
        createDir( getFreemarkerDaoDir() );
        createDir( getMultiTenantDaoDir() );
        createDir( getPaginationDaoDir() );
        createDir( getCrudDaoDir() );
        createDir( getLookUpDaoDir() );
        createDir( getCustomDaoDir() );

        // test
        createDir( getTestDaoDir() );
        createDir( getTestBaseDaoDir() );
        createDir( getTestConstraintsDaoDir() );
        createDir( getTestCrudDaoDir() );
        createDir( getTestLookUpDaoDir() );
        createDir( getTestCustomDaoDir() );

        // resources
        createDir( getResourcesDir() );
        createDir( getDataSourcesResourcesDir() );
        createDir( getDataSourceSqlResourcesDir() );
    }

}

