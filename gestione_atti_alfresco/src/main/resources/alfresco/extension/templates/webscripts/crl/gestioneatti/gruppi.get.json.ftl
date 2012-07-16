{
   "List": [
   <#list gruppi as gruppo>
   	{ 
   		"gruppoUtente" : 
    		{
			"nome" : "${gruppo.name}"
   		}
   	}<#if gruppo_has_next>,</#if>
   </#list>
   ]
}