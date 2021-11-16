package org.jdbi.generator.templates;

import org.jdbi.generator.main.Workspace;
import org.jdbi.generator.explorer.DBTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DaoTemplate extends AbstractTemplate
{
    private static final String[] SOURCE =
    {
        "templates/dao/_abstract/encoder/Encoder.fm",
        "templates/dao/_abstract/encoder/XOREncoder.fm",
        "templates/dao/_abstract/encoder/Base64Encoder.fm",
        "templates/dao/_abstract/freemarker/SqlTemplateLoader.fm",
        "templates/dao/_abstract/freemarker/UseSqlLocator.fm",
        "templates/dao/_abstract/freemarker/UseSqlLocatorImpl.fm",
        "templates/dao/_abstract/multitenant/MultiTenantContext.fm",
        "templates/dao/_abstract/multitenant/MultiTenantSqlObjectPlugin.fm",
        "templates/dao/_abstract/pagination/Page.fm",
        "templates/dao/_abstract/AbstractCrudDao.fm",
        "templates/dao/_abstract/AbstractLogging.fm",
        "templates/dao/_abstract/CrudDao.fm",
        "templates/dao/_abstract/JdbiCrudDao.fm",
        "templates/dao/_abstract/SqlOptions.fm",
    };

    private static final String[] TARGET =
    {
        "encoder/Encoder.java",
        "encoder/XOREncoder.java",
        "encoder/Base64Encoder.java",
        "freemarker/SqlTemplateLoader.java",
        "freemarker/UseSqlLocator.java",
        "freemarker/UseSqlLocatorImpl.java",
        "multitenant/MultiTenantContext.java",
        "multitenant/MultiTenantSqlObjectPlugin.java",
        "pagination/Page.java",
        "AbstractCrudDao.java",
        "AbstractLogging.java",
        "CrudDao.java",
        "JdbiCrudDao.java",
        "SqlOptions.java",

        /*
        "AbstractCrudDao.java",
        "AbstractDao.java",
        "AbstractLogging.java",
        "AbstractLookUpDao.java",
        //"AbstractResultSet.java",
        "Base64Encoder.java",
        "XOREncoder.java",
        "DefaultSqlLoader.java",
        "Encoder.java",
        "ICrudDao.java",
        "ILookUpDao.java",
        "LookUpDto.java",
        "OptionsCrudDto.java",
        "ParamsCrudDto.java",
        "SqlLoader.java",
        */
    };


    public DaoTemplate(Workspace workspace, List<DBTable> tables)
    {
        super(workspace, tables);
    }


    @Override
    public String getSource(int index, DBTable dbTable)
    {
        return SOURCE[index];
    }


    @Override
    public String getTarget(int index, DBTable dbTable)
    {
        return getWorkspace().getAbstractDaoDir() + TARGET[index];
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("AbstractDaoPackage", getWorkspace().getAbstractDaoPackage());
        map.put("EncoderDaoPackage", getWorkspace().getEncoderDaoPackage());
        map.put("FreemarkerDaoPackage", getWorkspace().getFreemarkerDaoPackage());
        map.put("MultiTenantDaoPackage", getWorkspace().getMultiTenantDaoPackage());
        map.put("PaginationDaoPackage", getWorkspace().getPaginationDaoPackage());

        return map;
    }


    @Override
    public void generate() throws Exception
    {
        for (int index=0; index<SOURCE.length; index++)
            super.generate( index );
    }

}
