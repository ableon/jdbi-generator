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
    public static final String ABSTRACT     = "_abstract";
    public static final String CONSTRAINTS  = "constraints";
    public static final String MODEL        = "model";
    public static final String DAO          = "dao";
    public static final String ENTITIES     = "entities";
    public static final String CATALOGS     = "catalogs";
    public static final String CRUD         = "crud";
    public static final String LOOKUP       = "lookup";

    private Boolean jta;
    private String datasourceClassName;
    private Map<String, String> atomikosProperties;

    private String dataSourceName;
    private Map<String, String> dataSourceProperties;
    private String projectType;
    private String projectPath;
    private String mainPackage;
    private String configPackage;
    private String testPackage;
    private Boolean lombok;
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
        setAtomikosProperties( jtaConfig.getAtomikosProperties() );
        setDataSourceName( dataSourceConfig.getName() );
        setDataSourceProperties( dataSourceConfig.getProperties() );
        setProjectType( projectConfig.getType() );
        setProjectPath( projectConfig.getPath() );
        setMainPackage( projectConfig.getMainPackage() );
        setConfigPackage( projectConfig.getConfigPackage() );
        setTestPackage( projectConfig.getTestPackage() );
        setLombok( projectConfig.getLombok() );
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

    public Map<String, String> getAtomikosProperties()
    {
        return atomikosProperties;
    }

    public void setAtomikosProperties(Map<String, String> atomikosProperties)
    {
        this.atomikosProperties = atomikosProperties;
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

    public Boolean getLombok()
    {
        return lombok;
    }

    public void setLombok(Boolean lombok)
    {
        this.lombok = lombok;
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
        return getJavaDir() + getMainPackagePath() + toJavaPath( MODEL ) + toJavaPath( dataSourceName.toLowerCase() );
    }

    public String getEntitiesDir()
    {
        return getModelDir() + toJavaPath( ENTITIES );
    }

    public String getLookUpDir()
    {
        return getModelDir() + toJavaPath( LOOKUP );
    }

    public String getCatalogsDir()
    {
        return getModelDir() + toJavaPath( CATALOGS );
    }

    public String getDaoDir()
    {
        return getJavaDir() + getMainPackagePath() + toJavaPath( DAO );
    }

    public String getDataSourceDaoDir()
    {
        return getDaoDir() + toJavaPath( dataSourceName.toLowerCase() );
    }

    public String getAbstractDaoDir()
    {
        return getDaoDir() + toJavaPath( ABSTRACT );
    }

    public String getEncoderDaoDir()
    {
        return getAbstractDaoDir() + toJavaPath( "encoder" );
    }

    public String getFreemarkerDaoDir()
    {
        return getAbstractDaoDir() + toJavaPath( "freemarker" );
    }

    public String getMultiTenantDaoDir()
    {
        return getAbstractDaoDir() + toJavaPath( "multitenant" );
    }

    public String getPaginationDaoDir()
    {
        return getAbstractDaoDir() + toJavaPath( "pagination" );
    }

    public String getCrudDaoDir()
    {
        return getDataSourceDaoDir() + toJavaPath( CRUD );
    }

    public String getLookupDaoDir()
    {
        return getDataSourceDaoDir() + toJavaPath( LOOKUP );
    }

    public String getTestDaoDir()
    {
        return getTestJavaDir() + getTestPackagePath() + toJavaPath( DAO );
    }

    public String getTestAbstractDaoDir()
    {
        return getTestDaoDir() + toJavaPath( ABSTRACT );
    }

    public String getTestConstraintsDaoDir()
    {
        return getTestAbstractDaoDir() + toJavaPath( CONSTRAINTS );
    }

    public String getDataSourceTestDaoDir()
    {
        return getTestDaoDir() + toJavaPath( dataSourceName.toLowerCase() );
    }

    public String getTestCrudDaoDir()
    {
        return getDataSourceTestDaoDir() + toJavaPath( CRUD );
    }

    public String getTestLookupDaoDir()
    {
        return getDataSourceTestDaoDir() + toJavaPath( LOOKUP );
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

    public String getCatalogsPackage()
    {
        return getPackage( getCatalogsDir() );
    }

    public String getAbstractDaoPackage()
    {
        return getPackage( getAbstractDaoDir() );
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

    public String getTestAbstractDaoPackage()
    {
        return getPackage( getTestAbstractDaoDir() );
    }

    public String getTestConstraintsDaoPackage()
    {
        return getPackage( getTestConstraintsDaoDir() );
    }

    public String getTestCrudDaoPackage()
    {
        return getPackage( getTestCrudDaoDir() );
    }



    public void create()
    {
        // root
        createDir( getJavaDir() );
        createDir( getResourcesDir() );
        createDir( getTestJavaDir() );
        createDir( getTestResourcesDir() );

        // config
        createDir( getConfigDir() );

        // model
        createDir( getEntitiesDir() );
        createDir( getLookUpDir() );
        createDir( getCatalogsDir() );

        // dao
        createDir( getAbstractDaoDir() );
        createDir( getEncoderDaoDir() );
        createDir( getFreemarkerDaoDir() );
        createDir( getMultiTenantDaoDir() );
        createDir( getPaginationDaoDir() );
        createDir( getCrudDaoDir() );
        createDir( getLookupDaoDir() );

        // test
        createDir( getTestAbstractDaoDir() );
        createDir( getTestConstraintsDaoDir() );
        createDir( getTestCrudDaoDir() );
        createDir( getTestLookupDaoDir() );

        // resources
        createDir( getDataSourcesResourcesDir() );
        createDir( getDataSourceSqlResourcesDir() );
    }

}

