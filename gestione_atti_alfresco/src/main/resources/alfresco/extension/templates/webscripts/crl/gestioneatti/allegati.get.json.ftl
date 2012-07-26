{  
   "List":[
   <#list allegati as allegato>
   { "allegato" : 
	   {
	    "id" : "${allegato.nodeRef}",
		"nome" : "${allegato.name}",
		"mimetype" : "${allegato.mimetype}"
	  }
   }<#if allegato_has_next>,</#if>
   </#list>
   ]
}