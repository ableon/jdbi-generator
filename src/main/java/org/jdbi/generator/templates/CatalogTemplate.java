package org.jdbi.generator.templates;

import org.jdbi.generator.main.Workspace;
import org.jdbi.generator.explorer.DBColumn;
import org.jdbi.generator.explorer.DBTable;
import org.jdbi.generator.utils.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CatalogTemplate extends AbstractTemplate
{
    private static final String[] SOURCE =
    {
        "templates/catalogs/Catalog.fm",
    };

    private static final String[] TARGET =
    {
        "${ClassName}.java",
    };


    public CatalogTemplate(Workspace workspace, List<DBTable> tables)
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
        String className = Strings.toClassName( dbTable.getName() );
        return getWorkspace().getCatalogsDir() + TARGET[index].replace("${ClassName}", className);
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        Map<String, Object> map = new HashMap<>();

        String className = Strings.toClassName( dbTable.getName() );

        List<String> importList = new ArrayList<>();

        DBColumn pkColumn = dbTable.getPrimaryKeyColumnList().get(0);

        String pkType = (super.isIntegerType(pkColumn))
                            ? "int"
                            : super.getNormalizedType(pkColumn.getClassName(), importList);

        map.put("CatalogsPackage", getWorkspace().getCatalogsPackage());
        map.put("ClassName", className);
        map.put("importList", importList);
        map.put("pkType", pkType);
        map.put("catalogData", dbTable.getCatalogData());

        return map;
    }


    @Override
    public void generate() throws Exception
    {
        for (int index=0; index<SOURCE.length; index++)
            for (DBTable table : getTables())
                if (!table.getCatalogData().isEmpty())
                    super.generate(index, table);
    }

}

