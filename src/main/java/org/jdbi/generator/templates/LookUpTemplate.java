package org.jdbi.generator.templates;

import org.jdbi.generator.Workspace;
import org.jdbi.generator.explorer.DBTable;
import org.jdbi.generator.utils.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LookUpTemplate extends AbstractTemplate
{
    private static final String[] SOURCE =
    {
        "templates/lookups/LombokLookUp.fm",
    };

    private static final String[] TARGET =
    {
        "LookUp.java",
    };


    public LookUpTemplate(Workspace workspace, List<DBTable> tables)
    {
        super(workspace, tables);
    }


    @Override
    public String getSource(int index, DBTable dbTable)
    {
        if (bool(getWorkspace().getLombok()))
            return SOURCE[index];
        else
            return SOURCE[index].replace("Lombok", "");
    }


    @Override
    public String getTarget(int index, DBTable dbTable)
    {
        String className = Strings.toClassName( dbTable.getName() );
        return getWorkspace().getLookUpDir() + TARGET[index];
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("LookUpPackage", getWorkspace().getLookUpPackage());
        map.put("builder", bool(getWorkspace().getDtoBuilders()));

        return map;
    }


    @Override
    public void generate() throws Exception
    {
        for (int index=0; index<SOURCE.length; index++)
            for (DBTable table : getTables())
                super.generate(index, table);
    }

}

