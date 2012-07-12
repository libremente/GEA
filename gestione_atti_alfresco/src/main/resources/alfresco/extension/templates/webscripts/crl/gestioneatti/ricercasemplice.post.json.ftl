{  
   "List" : [
	   <#list atti as atto>
	   <#assign tipoSubstring = atto.typeShort?substring(12,15)>
	   <#assign tipoTroncatoMaiuscolo = tipoSubstring?capitalize>
	   { 
   	  		"atto" : 
		   {
		    "id" : "${atto.nodeRef}",
			"tipo" : "${tipoTroncatoMaiuscolo}",
			"numero" : "${atto.properties["crlatti:numeroAtto"]}",
			"oggetto" : "<#if atto.properties["crlatti:oggetto"]?exists>${atto.properties["crlatti:oggetto"]}<#else></#if>",
			"primoFirmatario" : "<#if atto.properties["crlatti:primoFirmatario"]?exists>${atto.properties["crlatti:primoFirmatario"]}<#else></#if>",
			"dataPresentazione" : "<#if atto.properties["crlatti:dataPubblicazione"]?exists>${atto.properties["crlatti:dataPubblicazione"]}?string("dd/MM/yyyy")<#else></#if>",
			"stato" : "<#if atto.properties["crlatti:statoAtto"]?exists>${atto.properties["crlatti:statoAtto"]}<#else></#if>"
		   }
	   }
	   <#if atto_has_next>,</#if>
	   </#list>
   ]
}