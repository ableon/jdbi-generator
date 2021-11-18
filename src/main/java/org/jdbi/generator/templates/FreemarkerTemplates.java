package org.jdbi.generator.templates;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.jdbi.generator.utils.Strings;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;


public class FreemarkerTemplates
{
    private static final FreemarkerTemplates instance = new FreemarkerTemplates();


    public FreemarkerTemplates() {}


    public static FreemarkerTemplates getInstance()
    {
        return instance;
    }


    private static class ConvertMethod implements TemplateMethodModelEx
    {
        private String convertType = "";

        public ConvertMethod(String convertType)
        {
            this.convertType = convertType;
        }

        public Object exec(List arguments) throws TemplateModelException
        {
            if (arguments == null || arguments.isEmpty() || arguments.get(0) == null)
                throw new TemplateModelException("Wrong arguments!");

            String argument = arguments.get(0).toString();

            switch (convertType)
            {
                case "ClassName"    : return Strings.toClassName( argument );
                case "PropertyName" : return Strings.toPropertyName( argument );
                case "ColumnName"   : return Strings.toColumnName( argument );
                //case "Camel"        : return Strings.camel( argument );
            }

            return argument;
        }
    }


    public void process(String templateName, StringBuilder template, Map<String, Object> mapping) throws Exception
    {
        String templateContent = template.toString();
        template.delete(0, template.length());

        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate(templateName, templateContent);

        Configuration config = new Configuration( Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS );
        config.setTemplateLoader( stringLoader );

        Template templateConf = config.getTemplate(templateName, "UTF-8");
        StringWriter writer = new StringWriter();

        mapping.put("asClass",    new ConvertMethod("ClassName"));
        mapping.put("asProperty", new ConvertMethod("PropertyName"));
        mapping.put("asColumn",   new ConvertMethod("ColumnName"));
        //mapping.put("asCamel",    new ConvertMethod("Camel"));

        templateConf.process(mapping, writer);

        template.append( writer );
    }

}

