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
        "templates/dao/_base/encoder/Encoder.fm",
        "templates/dao/_base/encoder/XOREncoder.fm",
        "templates/dao/_base/encoder/Base64Encoder.fm",
        "templates/dao/_base/freemarker/SqlTemplateLoader.fm",
        "templates/dao/_base/freemarker/UseSqlLocator.fm",
        "templates/dao/_base/freemarker/UseSqlLocatorImpl.fm",
        "templates/dao/_base/multitenant/TenantInfo.fm",
        "templates/dao/_base/multitenant/MultiTenantContext.fm",
        "templates/dao/_base/multitenant/MultiTenantSqlObjectPlugin.fm",
        "templates/dao/_base/pagination/Page.fm",
        "templates/dao/_base/AbstractDao.fm",
        "templates/dao/_base/AbstractCrudDao.fm",
        "templates/dao/_base/CrudDao.fm",
        "templates/dao/_base/JdbiCrudDao.fm",
        "templates/dao/_base/SqlOptions.fm",
    };

    private static final String[] TARGET =
    {
        "encoder/Encoder.java",
        "encoder/XOREncoder.java",
        "encoder/Base64Encoder.java",
        "freemarker/SqlTemplateLoader.java",
        "freemarker/UseSqlLocator.java",
        "freemarker/UseSqlLocatorImpl.java",
        "multitenant/TenantInfo.java",
        "multitenant/MultiTenantContext.java",
        "multitenant/MultiTenantSqlObjectPlugin.java",
        "pagination/Page.java",
        "AbstractDao.java",
        "AbstractCrudDao.java",
        "CrudDao.java",
        "JdbiCrudDao.java",
        "SqlOptions.java",
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
        return getWorkspace().getBaseDaoDir() + TARGET[index];
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("BaseDaoPackage", getWorkspace().getBaseDaoPackage());
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
