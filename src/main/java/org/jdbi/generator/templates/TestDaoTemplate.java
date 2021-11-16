package org.jdbi.generator.templates;

import org.jdbi.generator.Workspace;
import org.jdbi.generator.explorer.DBTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestDaoTemplate extends AbstractTemplate
{
    private static final String[] SOURCE =
    {
        "templates/testing/dao/_abstract/AbstractDaoTest.fm",
        "templates/testing/dao/_abstract/DataTestFactory.fm",
        "templates/testing/dao/_abstract/constraints/AbstractConstraints.fm",
        "templates/testing/dao/_abstract/constraints/DB2Constraints.fm",
        "templates/testing/dao/_abstract/constraints/H2Constraints.fm",
        "templates/testing/dao/_abstract/constraints/HSQLDBConstraints.fm",
        "templates/testing/dao/_abstract/constraints/MariaDBConstraints.fm",
        "templates/testing/dao/_abstract/constraints/MySQLConstraints.fm",
        "templates/testing/dao/_abstract/constraints/OracleConstraints.fm",
        "templates/testing/dao/_abstract/constraints/PostgreSQLConstraints.fm",
        "templates/testing/dao/_abstract/constraints/SQLiteConstraints.fm",
        "templates/testing/dao/_abstract/constraints/SQLServerConstraints.fm",
    };

    private static final String[] TARGET =
    {
        "AbstractDaoTest.java",
        "DataTestFactory.java",
        "AbstractConstraints.java",
        "DB2Constraints.java",
        "H2Constraints.java",
        "HSQLDBConstraints.java",
        "MariaDBConstraints.java",
        "MySQLConstraints.java",
        "OracleConstraints.java",
        "PostgreSQLConstraints.java",
        "SQLiteConstraints.java",
        "SQLServerConstraints.java",
    };


    public TestDaoTemplate(Workspace workspace, List<DBTable> tables)
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
        if (TARGET[index].contains("Constraints"))
        {
            return getWorkspace().getTestConstraintsDaoDir() + TARGET[index];
        }
        else
        {
            return getWorkspace().getTestAbstractDaoDir() + TARGET[index];
        }
    }


    @Override
    protected void preMapping(StringBuilder template)
    {
    }


    @Override
    public Map<String, Object> getMapping(int index, DBTable dbTable)
    {
        Map<String, Object> map = new HashMap<>();

        map.put("TestAbstractDaoPackage", getWorkspace().getTestAbstractDaoPackage());
        map.put("TestConstraintsDaoPackage", getWorkspace().getTestConstraintsDaoPackage());
        map.put("MainPackage", getWorkspace().getMainPackage());
        map.put("spring", getWorkspace().isSpring());

        return map;
    }


    @Override
    public void generate() throws Exception
    {
        for (int index=0; index<SOURCE.length; index++)
            super.generate( index );
    }

}

