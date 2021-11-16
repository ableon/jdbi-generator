package org.example.connectors.jdbi.loader;

import org.slf4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedHashMap;
import java.util.Map;


public class XmlHandler extends DefaultHandler
{
    private final Map<String, String> includesMap   = new LinkedHashMap<>();
    private final Map<String, String> statementsMap = new LinkedHashMap<>();

    private boolean isQuery;
    private String namespace;
    private String statementId;
    private final StringBuilder statementText = new StringBuilder();


    public XmlHandler()
    {
        super();
    }


    public Map<String, String> getIncludes()
    {
        return includesMap;
    }

    public Map<String, String> getStatements()
    {
        return statementsMap;
    }


    private boolean isNotEmpty(String str)
    {
        return (str != null && !str.trim().isEmpty());
    }


    private String getStatementId(String id)
    {
        return (isNotEmpty(namespace)) ? (namespace+"."+id) : id;

    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        if (qName.equalsIgnoreCase("includes"))
        {

        }
        else
        if (qName.equalsIgnoreCase("include"))
        {
            String id = attributes.getValue("id");
            String include = "<#include \"" + id + "\">";
            includesMap.put(id, include);
        }
        if (qName.equalsIgnoreCase("statements"))
        {
            namespace = attributes.getValue("namespace");
        }
        else
        if (qName.equalsIgnoreCase("statement"))
        {
            isQuery = true;
            statementId = getStatementId(attributes.getValue("id"));
            statementText.delete(0, statementText.length()); // reset to blank
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        if (isQuery && statementId != null && !statementId.isEmpty() && statementText.length() > 0)
        {
            statementsMap.put(statementId, statementText.toString().trim());
            isQuery     = false;
            statementId = null;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        if (isQuery)
        {
            String line = new String(ch, start, length);
            this.statementText.append(line);
        }
    }


    public static class ErrHandler implements ErrorHandler
    {
        public final Logger logger;

        public ErrHandler(Logger logger)
        {
            this.logger = logger;
        }

        private String getParseExceptionInfo(SAXParseException spe)
        {
            String systemId = spe.getSystemId();

            if (systemId == null)
                systemId = "null";

            return "URI=" + systemId + " Line=" + spe.getLineNumber() + ": " + spe.getMessage();
        }

        @Override
        public void warning(SAXParseException spe) throws SAXException
        {
            logger.warn(getParseExceptionInfo(spe));
        }

        @Override
        public void error(SAXParseException spe) throws SAXException
        {
            String message = "Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }

        @Override
        public void fatalError(SAXParseException spe) throws SAXException
        {
            String message = "Fatal Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
    }

}

