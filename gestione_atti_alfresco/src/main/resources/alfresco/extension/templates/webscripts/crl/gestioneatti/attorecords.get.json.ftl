{  
   "List":[
   <#list records as record>
   { "record" : 
	   {
	    "id" : "${record.nodeRef}",
		"nome" : "${record.name}",
		"mimetype" : "${record.mimetype}"
	  }
   }<#if record_has_next>,</#if>
   </#list>
   ]
}