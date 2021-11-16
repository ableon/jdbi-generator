package org.jdbi.generator.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;

import java.io.IOException;


public class StringTrimModule extends SimpleModule
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

