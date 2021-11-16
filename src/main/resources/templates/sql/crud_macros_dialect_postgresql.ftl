

<#macro schema><#if schemaName??>${schemaName}.</#if></#macro>


<#macro begin_paginate>

</#macro>


<#macro end_paginate>
    <#if options?? && options.limit??>
        LIMIT ${options.limit}<#if options.offset??> OFFSET ${options.offset}</#if>
    </#if>
</#macro>


<#macro like value>LIKE ('%' || ${value} || '%')</#macro>


<#macro dateGE dateColumn dateValue>
            date_trunc('day', ${dateColumn}) >= date_trunc('day', ${dateValue})
</#macro>


<#macro dateLE dateColumn dateValue>
            date_trunc('day', ${dateColumn}) <= date_trunc('day', ${dateValue})
</#macro>

