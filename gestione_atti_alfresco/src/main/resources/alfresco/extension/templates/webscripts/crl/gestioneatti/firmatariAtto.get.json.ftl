{  
   "List":[
   <#list firmatari as firmatario>
   { "firmatario" : 
	   {
	    "id" : "${firmatario.nodeRef}",
		"nome" : "${firmatario.name}",
		"isPrimoFirmatario" : "<#if firmatario.properties["crlatti:isPrimoFirmatario"]?exists>${firmatario.properties["crlatti:isPrimoFirmatario"]?string("true","false")}<#else></#if>",
		"gruppoConsiliare" : "<#if firmatario.properties["crlatti:gruppoConsiliare"]?exists>${firmatario.properties["crlatti:gruppoConsiliare"]}<#else></#if>",
		"dataFirma" : "<#if firmatario.properties["crlatti:dataFirma"]?exists>${firmatario.properties["crlatti:dataFirma"]?string("dd/MM/yyyy")}<#else></#if>",
		"dataRitiro" : "<#if firmatario.properties["crlatti:dataRitiro"]?exists>${firmatario.properties["crlatti:dataRitiro"]?string("dd/MM/yyyy")}<#else></#if>"
	   }
   }<#if firmatario_has_next>,</#if>
   </#list>
   ]
}