package org.jdbi.generator.main;

import jakarta.validation.ConstraintViolation;
import org.jdbi.generator.validators.Validator;

import java.lang.reflect.Array;
import java.util.*;


@SuppressWarnings("unchecked")
public abstract class AbstractComponent
{

    protected AbstractComponent() {}


    protected Set<ConstraintViolation<Object>> validate(Object obj)
    {
        return Validator.getInstance().validate( obj );
    }


    protected String generateUUID()
    {
        //return UUID.randomUUID().toString().replace("-","");
        return UUID.randomUUID().toString();
    }


    protected int getProcessorsNumber()
    {
        return Runtime.getRuntime().availableProcessors();
    }


    public static void sleep(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException th)
        {
            Thread.currentThread().interrupt();
        }
    }


    protected <T> T trim(Object value)
    {
        if (value != null)
            if (value instanceof String)
                return (T) ((String)value).trim();

        return (T) value;
    }


    protected boolean bool(Boolean b)
    {
        return (b != null && b);
    }


    protected boolean isNullOrEmpty(Object value)
    {
        if (value == null)
            return true;

        if (value.getClass().isArray() && (Array.getLength(value) == 0))
            return true;

        if (value instanceof Collection && ((Collection<?>)value).isEmpty())
            return true;

        if (value instanceof Map && ((Map<?,?>)value).isEmpty())
            return true;

        if (value instanceof String)
            return ((String)value).trim().isEmpty();

        return false;
    }


    protected boolean isAnyNullOrEmpty(Object... values)
    {
        if (values == null)
            return true;

        for (Object value : values)
            if (isNullOrEmpty(value))
                return true;

        return false;
    }


    protected boolean isNotEmpty(Object value)
    {
        return !isNullOrEmpty( value );
    }


    protected boolean isAllNotEmpty(Object... values)
    {
        return !isAnyNullOrEmpty( values );
    }


    protected int getSize(Collection <?> collection)
    {
        try{ return collection.size(); } catch (Exception e) { return 0; }
    }


    protected <T> T getFirst(List<T> list)
    {
        try{ return list.get(0); } catch (Exception e) { return null; }
    }


    protected <T> T getLast(List<T> list)
    {
        try{ return list.get( list.size()-1 ); } catch (Exception e) { return null; }
    }


    protected void removeNulls(Map<?,?> map)
    {
        if (map != null)
            while (map.values().remove(null));
    }


    protected void removeNulls(Collection<?> collection)
    {
        if (collection != null)
            while (collection.remove(null));
    }


    protected List<String> removeNullsAndBlanks(Collection<String> collection)
    {
        List<String> result = new ArrayList<>();

        if (collection != null) {
            collection.forEach(str -> {
                if (str != null && !str.trim().isEmpty()) {
                    result.add( str.trim() );
                }
            });
        }

        return result;
    }

}

