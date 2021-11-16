

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
            DATE(${dateColumn}) >= DATE(${dateValue})
</#macro>


<#macro dateLE dateColumn dateValue>
            DATE(${dateColumn}) <= DATE(${dateValue})
</#macro>

