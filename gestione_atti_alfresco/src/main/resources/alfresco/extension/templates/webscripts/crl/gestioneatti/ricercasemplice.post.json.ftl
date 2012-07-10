{  
   "List" : [
	   <#list atti as atto>
	   { 
   	  		"atto" : 
		   {
		    "id" : "${atto.nodeRef}",
			"tipo" : "${atto.typeShort?substring(12,15)?capitalize}",
			"numero" : "${atto.properties["crlatti:numeroAtto"]}",
			"oggetto" : "${atto.properties["crlatti:oggetto"]}",
			"primoFirmatario" : "${atto.properties["crlatti:primoFirmatario"]}",
			"dataPresentazione" : "${atto.properties["crlatti:dataPubblicazione"]?string("dd/MM/yyyy")}",
			"stato" : "${atto.properties["crlatti:statoAtto"]}"
		   }
	   }<#if atto_has_next>,</#if>
	   </#list>
   ]
}