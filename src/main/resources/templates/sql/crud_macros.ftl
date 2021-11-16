

<#macro table_name>
            ${tableName}
</#macro>


<#macro columns_name>
    <#if columnsName?? && columnsName?has_content>
        <#list columnsName as columnName>
            ${tableName}.${columnName}<#if columnName_has_next>,</#if>
        </#list>
    </#if>
</#macro>


<#macro insert_columns>
    <#if entityColumns?? && entityColumns?has_content>
        <#list entityColumns as entityColumn, entityProperty>
            ${entityColumn}<#if entityColumn_has_next>,</#if>
        </#list>
    </#if>
</#macro>


<#macro insert_values>
    <#if entityColumns?? && entityColumns?has_content>
        <#list entityColumns as entityColumn, entityProperty>
            :entity.${entityProperty}<#if entityColumn_has_next>,</#if>
        </#list>
    </#if>
</#macro>


<#macro set_values>
    <#if entityColumns?? && entityColumns?has_content>
        <#list entityColumns as entityColumn, entityProperty>
            ${entityColumn} = :entity.${entityProperty}<#if entityColumn_has_next>,</#if>
        </#list>
    </#if>
</#macro>


<#macro equal_filter>
    <#if filterColumns?? && filterColumns?has_content>
        <#list filterColumns as filterColumn, filterProperty>
            ${tableName}.${filterColumn} = :filter.${filterProperty}
        <#if filterColumn_has_next>AND</#if>
        </#list>
    </#if>
    <@dates_filter />
    <@in_columns_filter />
    <@null_columns_filter />
</#macro>


<#macro find_filter>
    <#if filterColumns?? && filterColumns?has_content>
        <#list filterColumns as filterColumn, filterProperty>
            <#if filter[filterProperty]?is_string>
                ${tableName}.${filterColumn} <@like value=":filter.${filterProperty}"/>
            <#else>
                ${tableName}.${filterColumn} = :filter.${filterProperty}
            </#if>
        <#if filterColumn_has_next>AND</#if>
        </#list>
    </#if>
    <@dates_filter />
    <@in_columns_filter />
    <@null_columns_filter />
</#macro>


<#macro dates_filter>
    <#if options?? && options.betweenDates?? && options.betweenDates?has_content>
        <#if filter?? && filter?has_content>
        AND
        </#if>
        <#list options.betweenDates as betweenDates>
            <#if betweenDates.column??>
                <#if betweenDates.beginDate??>
                    <@dateGE dateColumn="${betweenDates.column}" dateValue=":options.betweenDates_${betweenDates?index}.beginDate"/>
        <#if betweenDates_has_next || betweenDates.endDate??>AND</#if>
                </#if>
                <#if betweenDates.endDate??>
                    <@dateLE dateColumn="${betweenDates.column}" dateValue=":options.betweenDates_${betweenDates?index}.endDate"/>
        <#if betweenDates_has_next>AND</#if>
                </#if>
            </#if>
        </#list>
    </#if>
</#macro>


<#macro in_columns_filter>
    <#if options?? && options.ins?? && options.ins?has_content>
        <#if (filter?? && filter?has_content)
              ||
             (options.betweenDates?? && options.betweenDates?has_content)>
        AND
        </#if>
        <#list options.ins as inFilter>
        <#if inFilter.column?? && inFilter.values??>
            ${tableName}.${inFilter.column} IN (<#list inFilter.values as value>:options.ins_${inFilter?index}.values_${value?index}<#if value_has_next>, </#if></#list>)
        <#if inFilter_has_next>AND</#if>
        </#if>
        </#list>
    </#if>
</#macro>


<#macro null_columns_filter>
    <#if options?? && options.nullColumns?? && options.nullColumns?has_content>
        <#if (filter?? && filter?has_content)
              ||
             (options.betweenDates?? && options.betweenDates?has_content)
              ||
             (options.inColumns?? && options.inColumns?has_content)>
        AND
        </#if>
        <#list options.nullColumns as columnName>
            ${tableName}.${columnName} IS NULL
        <#if columnName_has_next>AND</#if>
        </#list>
    </#if>
</#macro>


<#macro columns_order>
    <#if options?? && options.sorting?? && options.sorting?has_content>
        ORDER BY
        <#list options.sorting as sorting>
            ${tableName}.${sorting.column} ${sorting.order}<#if sorting_has_next>,</#if>
        </#list>
    <#elseif order?? && order?has_content>
        ORDER BY
        <#list order as columnName>
            ${tableName}.${columnName} ASC<#if columnName_has_next>,</#if>
        </#list>
    </#if>
</#macro>

