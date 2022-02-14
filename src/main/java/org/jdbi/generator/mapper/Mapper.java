package org.jdbi.generator.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
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


    private void configureObjectMapper(ObjectMapper objectMapper)
    {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false); // writerWithDefaultPrettyPrinter
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // register modules
        objectMapper.registerModule( new StringTrimModule() );
    }


    private void configureJsonObjectMapper()
    {
        /*
        jsonObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jsonObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        //jsonObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true); // writerWithDefaultPrettyPrinter
        jsonObjectMapper.registerModule( new StringTrimModule() );
        */
        configureObjectMapper( jsonObjectMapper );
    }


    private void configureYamlObjectMapper()
    {
        /*
        yamlObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        yamlObjectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        //yamlObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true); // writerWithDefaultPrettyPrinter
        yamlObjectMapper.registerModule( new StringTrimModule() );
        */
        configureObjectMapper( yamlObjectMapper );
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


    private static class StringTrimModule extends SimpleModule
    {

        public StringTrimModule()
        {
            addSerializer(String.class, new StdScalarSerializer<String>(String.class)
            {
                @Override
                public void serialize(String str, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                                      throws IOException
                {
                    if (str != null)
                        jsonGenerator.writeString( str.trim() );
                    else
                        jsonGenerator.writeNull();
                }
            });

            addDeserializer(String.class, new StdScalarDeserializer<String>(String.class)
            {
                @Override
                public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                                          throws IOException
                {
                    return jsonParser.getValueAsString().trim();
                }
            });
        }

    }

}

