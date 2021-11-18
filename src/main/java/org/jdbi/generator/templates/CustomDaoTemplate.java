package org.jdbi.generator.templates;

import org.jdbi.generator.explorer.DBTable;
import org.jdbi.generator.main.Workspace;
import org.jdbi.generator.utils.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomDaoTemplate extends AbstractTemplate
{
    private static final String[] SOURCE =
    {
        "templates/dao/custom/ExampleDao.fm",
        "templates/dao/custom/ExampleDaoImpl.fm",
        "templates/dao/custom/ExampleJdbiDao.fm",
    };

    private static final String[] TARGET =
    {
        "ExampleDao.java",
        "ExampleDaoImpl.java",
        "ExampleJdbiDao.java",
    };


    public CustomDaoTemplate(Workspace workspace, List<DBTable> tables)
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
        return getWorkspace().getCustomDaoDir() + TARGET[index];
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        Map<String, Object> map = new HashMap<>();

        List<String> importList = new ArrayList<>();

        String logAnnotation = "";
        if (getWorkspace().isLog())
        {
            importList.add( getWorkspace().getLogAnnotation() );
            logAnnotation = "@"+Strings.splitLast(getWorkspace().getLogAnnotation(), ".")[1];
        }

        map.put("CustomDaoPackage", getWorkspace().getCustomDaoPackage());
        map.put("BaseDaoPackage", getWorkspace().getBaseDaoPackage());
        map.put("ConfigPackage", getWorkspace().getConfigPackage());
        map.put("DataSourceClassName", Strings.toClassName( getWorkspace().getDataSourceName() ));
        map.put("importList", importList);
        map.put("spring", getWorkspace().isSpring());
        map.put("log", getWorkspace().isLog());
        map.put("LogAnnotation", logAnnotation);

        return map;
    }


    @Override
    public void generate() throws Exception
    {
        for (int index=0; index<SOURCE.length; index++)
            super.generate( index );
    }

}
