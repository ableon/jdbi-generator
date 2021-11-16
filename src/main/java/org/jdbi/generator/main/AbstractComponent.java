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


    protected int getProcessorsNumber()
    {
        return Runtime.getRuntime().availableProcessors();
    }


    protected void sleep(long time)
    {
        try { Thread.sleep(time); } catch (Throwable th) { /**/ }
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


    protected boolean isNotEmpty(Object value)
    {
        return isAllNotEmpty( value );
    }


    protected boolean isNullOrEmpty(Object value)
    {
        return isAnyNullOrEmpty( value );
    }


    protected boolean isAllNotEmpty(Object... objects)
    {
        return !isAnyNullOrEmpty( objects );
    }


    protected boolean isAnyNullOrEmpty(Object... objects)
    {
        if (objects == null)
            return true;

        for (Object obj : objects)
        {
            if (obj == null)
                return true;

            if (obj.getClass().isArray() && (Array.getLength(obj) == 0))
                return true;

            if (obj instanceof Collection && ((Collection<?>)obj).isEmpty())
                return true;

            if (obj instanceof Map && ((Map<?,?>)obj).isEmpty())
                return true;

            if (obj.toString().trim().isEmpty())
                return true;
        }

        return false;
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

