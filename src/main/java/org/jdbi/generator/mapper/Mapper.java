package org.jdbi.generator.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.InputStream;
import java.io.OutputStream;


public class Mapper
{
    private static final Mapper instance = new Mapper();

    private final ObjectMapper jsonObjectMapper = new JsonMapper();
    private final ObjectMapper yamlObjectMapper = new YAMLMapper();


    private Mapper()
    {
        configureJsonObjectMapper();
        configureYamlObjectMapper();
    }


    private void configureJsonObjectMapper()
    {
        jsonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        //jsonObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true); // writerWithDefaultPrettyPrinter
        jsonObjectMapper.registerModule( new StringTrimModule() );
    }


    private void configureYamlObjectMapper()
    {
        yamlObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        yamlObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        //yamlObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true); // writerWithDefaultPrettyPrinter
        yamlObjectMapper.registerModule( new StringTrimModule() );
    }


    public static Mapper getInstance()
    {
        return instance;
    }


    public <T> T readJson(InputStream inputStream, Class<T> type) throws Exception
    {
        return jsonObjectMapper.readValue(inputStream, type);
    }


    public <T> T readYaml(InputStream inputStream, Class<T> type) throws Exception
    {
        return yamlObjectMapper.readValue(inputStream, type);
    }


    public void writeJson(OutputStream outputStream, Object obj) throws Exception
    {
        jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, obj);
    }


    public void writeYaml(OutputStream outputStream, Object obj) throws Exception
    {
        yamlObjectMapper.writerWithDefaultPrettyPrinter().writeValue(outputStream, obj);
    }


    public String toJson(Object obj) throws Exception
    {
        return (obj != null)
                ? jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj).trim()
                : null;
    }


    public String toYaml(Object obj) throws Exception
    {
        return (obj != null)
                ? yamlObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj).trim()
                : null;
    }

}

