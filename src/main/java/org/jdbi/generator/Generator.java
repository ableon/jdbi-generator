package org.jdbi.generator;

import org.jdbi.generator.cli.Console;
import org.jdbi.generator.config.Config;
import org.jdbi.generator.config.GeneratorConfig;
import org.jdbi.generator.explorer.Explorer;
import org.jdbi.generator.mapper.Mapper;
import org.jdbi.generator.templates.*;
import org.jdbi.generator.validators.Validator;

import java.io.File;
import java.io.FileInputStream;


public class Generator extends AbstractComponent
{

    public Generator()
    {
        super();
    }


    private Config read(File sourceFile) throws Exception
    {
        try (FileInputStream inputStream = new FileInputStream(sourceFile))
        {
            return Mapper.getInstance().readYaml(inputStream, Config.class);
        }
    }


    public void generate(File sourceFile) throws Exception
    {
        Console.separator();

        // read config files
        Config config = read( sourceFile );

        // validate config file
        Validator.getInstance().validate( config );

        Console.verbose("---" +
                        Console.lineSeparator +
                        "Config:" +
                        Console.lineSeparator +
                        Mapper.getInstance().toYaml(config), true);

        // explore database
        Explorer explorer = new Explorer();
        explorer.explore( config );

        // generate
        generate(config.getGeneratorConfig(), explorer);

        Console.out("Generated in '" + config.getGeneratorConfig().getProjectConfig().getPath() + "'");
    }


    private void generate(GeneratorConfig generatorConfig, Explorer explorer) throws Exception
    {
        Console.separator();

        Workspace workspace = new Workspace(generatorConfig.getJtaConfig(),
                                            generatorConfig.getDataSourceConfig(),
                                            generatorConfig.getProjectConfig());

        workspace.setDbConnection( explorer.getDbConnection() );

        workspace.create();

        if (super.bool(generatorConfig.getGenerateConfig()))
        {
            Console.verbose("Generating Config...");
            ConfigTemplate configTemplate = new ConfigTemplate(workspace, explorer.getTables());
            configTemplate.generate();
        }

        if (super.bool(generatorConfig.getGenerateResources()))
        {
            Console.verbose("Generating Resources...");
            SqlTemplate sqlTemplate = new SqlTemplate(workspace, explorer.getTables());
            sqlTemplate.generate();
        }

        if (super.bool(generatorConfig.getGenerateEntities()))
        {
            Console.verbose("Generating Entities...");
            EntityTemplate entityTemplate = new EntityTemplate(workspace, explorer.getTables());
            entityTemplate.generate();
        }

        if (super.bool(generatorConfig.getGenerateLookups()))
        {
            Console.verbose("Generating LookUps...");
            LookUpTemplate lookUpTemplate = new LookUpTemplate(workspace, explorer.getTables());
            lookUpTemplate.generate();
        }

        if (super.bool(generatorConfig.getGenerateCatalogs()))
        {
            Console.verbose("Generating Catalogs...");
            CatalogTemplate catalogTemplate = new CatalogTemplate(workspace, explorer.getTables());
            catalogTemplate.generate();
        }

        if (super.bool(generatorConfig.getGenerateCrudDaos()))
        {
            Console.verbose("Generating Abstract Dao...");
            DaoTemplate daoTemplate = new DaoTemplate(workspace, explorer.getTables());
            daoTemplate.generate();

            Console.verbose("Generating Crud Dao...");
            CrudDaoTemplate crudDaoTemplate = new CrudDaoTemplate(workspace, explorer.getTables());
            crudDaoTemplate.generate();
        }

        ////Console.verbose("Generating LookUp Dao...");

        if (super.bool(generatorConfig.getGenerateCrudTests()))
        {
            Console.verbose("Generating Testing...");
            TestDaoTemplate testDaoTemplate = new TestDaoTemplate(workspace, explorer.getTables());
            testDaoTemplate.generate();

            TestCrudDaoTemplate testCrudDaoTemplate = new TestCrudDaoTemplate(workspace, explorer.getTables());
            testCrudDaoTemplate.generate();
        }

        Console.verbose("");
    }

}

