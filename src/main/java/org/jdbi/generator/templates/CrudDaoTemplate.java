package org.jdbi.generator.templates;

import org.jdbi.generator.explorer.DBColumn;
import org.jdbi.generator.explorer.DBTable;
import org.jdbi.generator.main.Workspace;
import org.jdbi.generator.utils.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CrudDaoTemplate extends AbstractTemplate
{
    private static final String[] SOURCE =
    {
        "templates/dao/crud/CrudDao.fm",
        "templates/dao/crud/CrudDaoImpl.fm",
        "templates/dao/crud/JdbiCrudDao.fm",
    };

    private static final String[] TARGET =
    {
        "${ClassName}CrudDao.java",
        "${ClassName}CrudDaoImpl.java",
        "${ClassName}JdbiCrudDao.java",
    };


    public CrudDaoTemplate(Workspace workspace, List<DBTable> tables)
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
        return getWorkspace().getCrudDaoDir() + TARGET[index].replace("${ClassName}", className);
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

        List<FieldData> fieldDataList = new ArrayList<>();
        List<FieldData> pkFieldDataList = new ArrayList<>();
        List<FieldData> generatedKeyDataList = new ArrayList<>();
        List<String> importList = new ArrayList<>();

        for (DBColumn dbColumn : dbTable.getColumnList())
        {
            FieldData fieldData = new FieldData();

            fieldData.setName( dbColumn.getName() );
            fieldData.setColumn( Strings.toColumnName(dbColumn.getName()) );
            fieldData.setProperty( Strings.toPropertyName(dbColumn.getName()) );
            fieldData.setType( super.getNormalizedType(dbColumn.getClassName(), null) );
            fieldData.setEncrypted( dbColumn.isEncrypted() );

            if (dbColumn.isJson())
                fieldData.setJsonType("::json");
            else
            if (dbColumn.isJsonB())
                fieldData.setJsonType("::jsonb");

            fieldDataList.add( fieldData );

            if (dbColumn.isPrimaryKey())
                pkFieldDataList.add( fieldData );

            if (super.isAutoGenerated(dbColumn))
                generatedKeyDataList.add( fieldData );
        }

        String logAnnotation = "";
        if (getWorkspace().isLog())
        {
            importList.add( getWorkspace().getLogAnnotation() );
            logAnnotation = "@"+Strings.splitLast(getWorkspace().getLogAnnotation(), ".")[1];
        }

        String key = (pkFieldDataList.size() > 1)
                        ? (className+"Entity.Key")
                        : pkFieldDataList.get(0).getType();

        String generatedKey = "Void";
        boolean isGeneratedKey = super.isNotEmpty(generatedKeyDataList);

        if (isGeneratedKey)
        {
            generatedKey = (generatedKeyDataList.size() > 1)
                            ? (className+"Entity.GeneratedKey")
                            : generatedKeyDataList.get(0).getType();
        }

        map.put("CrudDaoPackage", getWorkspace().getCrudDaoPackage());
        map.put("BaseDaoPackage", getWorkspace().getBaseDaoPackage());
        map.put("EntitiesPackage", getWorkspace().getEntitiesPackage());
        map.put("ConfigPackage", getWorkspace().getConfigPackage());
        map.put("ClassName", className);
        map.put("DataSourceClassName", Strings.toClassName( getWorkspace().getDataSourceName() ));
        map.put("quoteId", workspace.getDbConnection().getQuoteId());
        map.put("Entity", className+"Entity");
        map.put("Key", key);
        map.put("GeneratedKey", generatedKey);
        map.put("isGeneratedKey", isGeneratedKey);
        map.put("importList", importList);
        map.put("fieldDataList", fieldDataList);
        map.put("pkFieldDataList", pkFieldDataList);
        map.put("isMultipleKey", super.getSize(pkFieldDataList) > 1);
        map.put("generatedKeyDataList", generatedKeyDataList);
        map.put("isMultipleGeneratedKey", super.getSize(generatedKeyDataList) > 1);
        map.put("spring", getWorkspace().isSpring());
        map.put("log", getWorkspace().isLog());
        map.put("LogAnnotation", logAnnotation);

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

