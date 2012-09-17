{  
   "List":[
   <#list allegati as allegato>
   { "allegato" : 
	   {
	    "id" : "${allegato.nodeRef}",
		"nome" : "${allegato.name}",
		"mimetype" : "${allegato.mimetype}",
		"tipologia" : "<#if allegato.properties["crlatti:tipologia"]?exists>${allegato.properties["crlatti:tipologia"]}<#else></#if>",
		"pubblico" : "<#if allegato.properties["crlatti:pubblico"]?exists>${allegato.properties["crlatti:pubblico"]?string("true","false")}<#else></#if>",
		"provenienza" : "<#if record.properties["crlatti:provenienza"]?exists>${record.properties["crlatti:provenienza"]}<#else></#if>"
	  }
   }<#if allegato_has_next>,</#if>
   </#list>
   ]
}