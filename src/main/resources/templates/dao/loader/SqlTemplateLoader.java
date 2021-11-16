package org.example.connectors.jdbi.loader;

import freemarker.cache.TemplateLoader;
import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;


public class SqlTemplateLoader implements TemplateLoader
{
    private static final Logger logger = LoggerFactory.getLogger( SqlTemplateLoader.class );

    private final Map<String, SqlTemplateSource> templatesMap = new LinkedHashMap<>();


    public SqlTemplateLoader() {}


    public Map<String, SqlTemplateSource> getTemplates()
    {
        return templatesMap;
    }


    public String getTemplateContent(String name)
    {
        SqlTemplateSource sqlTemplateSource = templatesMap.get(name);
        return (sqlTemplateSource != null) ? sqlTemplateSource.getContent() : null;
    }


    public void loadTemplates(String... templates) throws Exception
    {
        XmlHandler xmlHandler = new XmlHandler();
        SAXParserFactory saxParserFactory = new SAXParserFactoryImpl();
        SAXParser parser = saxParserFactory.newSAXParser();
        XMLReader xmlReader = parser.getXMLReader();
        xmlReader.setErrorHandler( new XmlHandler.ErrHandler(logger) );
        xmlReader.setContentHandler( xmlHandler );

        for (String template : templates)
        {
            xmlHandler.getStatements().clear();

            try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(template))
            {
                xmlReader.parse( new InputSource(inputStream) );

                xmlHandler.getStatements().forEach((statementName, statementContent) ->
                {
                    StringBuilder st = new StringBuilder();

                    // use includes
                    //if (statementContent.contains("<@"))
                    xmlHandler.getIncludes().values().forEach(include -> st.append(include).append(System.lineSeparator()));
                    st.append( statementContent );

                    SqlTemplateSource sqlTemplateSource = new SqlTemplateSource(template,
                                                                                //statementContent,
                                                                                st.toString(),
                                                                                System.currentTimeMillis());
                    templatesMap.put(statementName, sqlTemplateSource);
                });
            }
        }
    }


    @Override
    public Object findTemplateSource(String name) throws IOException
    {
        return templatesMap.get(name);
    }


    @Override
    public long getLastModified(Object templateSource)
    {
        return ((SqlTemplateSource)templateSource).getLastModified();
    }


    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException
    {
        if (logger.isDebugEnabled())
            logger.debug("Looking for Freemarker template: {}",
                        ((SqlTemplateSource)templateSource).getSource());

        return new StringReader( ((SqlTemplateSource)templateSource).getContent() );
    }


    @Override
    public void closeTemplateSource(Object templateSource) throws IOException
    {
        // Do nothing
    }


    private static class SqlTemplateSource
    {
        private final String source;
        private final String content;
        private final long lastModified;

        public SqlTemplateSource(String source, String content, long lastModified)
        {
            this.source = source;
            this.content = content;
            this.lastModified = lastModified;
        }

        public String getSource() { return source; }

        public String getContent() { return content; }

        public long getLastModified() { return lastModified; }

        @Override
        public String toString()
        {
            StringBuilder str = new StringBuilder(getClass().getSimpleName());
            str.append("{");
            str.append("source=").append(source);
            str.append(",content=").append(content);
            str.append(",lastModified=").append(lastModified);
            str.append("}");
            return str.toString();
        }
    }

}

