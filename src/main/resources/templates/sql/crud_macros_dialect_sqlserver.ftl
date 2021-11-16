

<#macro schema><#if schemaName??>${schemaName}.</#if></#macro>


<#macro begin_paginate>

</#macro>


<#macro end_paginate>
    <#if options?? && options.limit??>
    <#if options.offset??>
        OFFSET ${options.offset} ROWS FETCH NEXT ${options.limit} ROWS ONLY
    <#else>
        OFFSET 0 ROWS FETCH NEXT ${options.limit} ROWS ONLY
    </#if>
</#macro>


<#macro like value>LIKE ('%' + ${value} + '%')</#macro>


<#macro dateGE dateColumn dateValue>
            (DATEDIFF(day, ${dateColumn}, ${dateValue}) >= 0)
</#macro>


<#macro dateLE dateColumn dateValue>
            (DATEDIFF(day, ${dateColumn}, ${dateValue}) <= 0)
</#macro>

