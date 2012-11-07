<#escape x as jsonUtils.encodeJSONString(x)>
{  
   "List":[
   <#if firmatari?exists>
   <#list firmatari as firmatario>
   { "firmatario" : 
	   {
	    "id" : "${firmatario.nodeRef}",
		"descrizione" : "${firmatario.name}",
		"primoFirmatario" : "<#if firmatario.properties["crlatti:isPrimoFirmatario"]?exists>${firmatario.properties["crlatti:isPrimoFirmatario"]?string("true","false")}<#else></#if>",
		"gruppoConsiliare" : "<#if firmatario.properties["crlatti:gruppoConsiliare"]?exists>${firmatario.properties["crlatti:gruppoConsiliare"]}<#else></#if>",
		"dataFirma" : "<#if firmatario.properties["crlatti:dataFirma"]?exists>${firmatario.properties["crlatti:dataFirma"]?string("yyyy-MM-dd")}<#else></#if>",
		"dataRitiro" : "<#if firmatario.properties["crlatti:dataRitiro"]?exists>${firmatario.properties["crlatti:dataRitiro"]?string("yyyy-MM-dd")}<#else></#if>",
		"numeroOrdinamento" : "<#if firmatario.properties["crlatti:numeroOrdinamento"]?exists>${firmatario.properties["crlatti:numeroOrdinamento"]}<#else></#if>"
	   }
   }<#if firmatario_has_next>,</#if>
   </#list>
   </#if>
   ]
}
</#escape>