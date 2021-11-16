

<#macro schema><#if schemaName??>${schemaName}.</#if></#macro>


<#macro begin_paginate>
    <#if options?? && options.limit??>
        select * from ( select t.*, ROWNUM rnum from (
    </#if>
</#macro>


<#macro end_paginate>
    <#if options?? && options.limit??>
    <#if options.offset??>
        ) t where ROWNUM <= (${options.offset}+${options.limit}) )
        where rnum > ${options.offset}
    <#else>
        ) t where ROWNUM <= (0+${.options.limit}) )
        where rnum > 0
    </#if>
    </#if>
</#macro>


<#macro like value>LIKE ('%' || ${value} || '%')</#macro>


<#macro dateGE dateColumn dateValue>
            TRUNC(${dateColumn}, 'DDD') >= TRUNC(${dateValue}, 'DDD')
</#macro>


<#macro dateLE dateColumn dateValue>
            TRUNC(${dateColumn}, 'DDD') <= TRUNC(${dateValue}, 'DDD')
</#macro>

