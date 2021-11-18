package org.jdbi.generator.templates;

import org.jdbi.generator.explorer.DBTable;
import org.jdbi.generator.main.Workspace;
import org.jdbi.generator.utils.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestCustomDaoTemplate extends AbstractTemplate
{
    private static final String[] SOURCE =
    {
        "templates/testing/dao/custom/ExampleDaoTest.fm",
    };

    private static final String[] TARGET =
    {
        "ExampleDaoTest.java",
    };


    public TestCustomDaoTemplate(Workspace workspace, List<DBTable> tables)
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
        return getWorkspace().getTestCustomDaoDir() + TARGET[index];
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("TestCustomDaoPackage", getWorkspace().getTestCustomDaoPackage());
        map.put("TestBaseDaoPackage", getWorkspace().getTestBaseDaoPackage());
        map.put("BaseDaoPackage", getWorkspace().getBaseDaoPackage());
        map.put("CustomDaoPackage", getWorkspace().getCustomDaoPackage());
        map.put("EntitiesPackage", getWorkspace().getEntitiesPackage());
        map.put("ConfigPackage", getWorkspace().getConfigPackage());
        map.put("DataSourceClassName", Strings.toClassName( getWorkspace().getDataSourceName() ));
        map.put("Entity", Strings.toClassName(getTables().get(0).getName())+"Entity"); // first table
        map.put("spring", getWorkspace().isSpring());
        map.put("jta", bool(getWorkspace().getJta()));

        return map;
    }


    @Override
    public void generate() throws Exception
    {
        for (int index=0; index<SOURCE.length; index++)
            super.generate( index );
    }

}
