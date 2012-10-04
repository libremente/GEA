<#escape x as jsonUtils.encodeJSONString(x)>
{  
   "List":[
   <#list abbinamenti as abbinamento>
   { "abbinamento" : 
	   {
	    "id" : "${abbinamento.nodeRef}",
		"nome" : "${abbinamento.name}",
		"tipoAtto" : "${abbinamento.assocs["crlatti:attoAssociatoAbbinamento"][0].typeShort?substring(12)?upper_case}",
		"tipoTesto" : "<#if abbinamento.properties["crlatti:tipoTestoAbbinamento"]?exists>${abbinamento.properties["crlatti:tipoTestoAbbinamento"]}<#else></#if>",
		"numeroProtocollo" : "<#if abbinamento.properties["crlatti:numeroProtocollo"]?exists>${abbinamento.properties["crlatti:numeroProtocollo"]}<#else></#if>",
		"dataAbbinamento" : "<#if abbinamento.properties["crlatti:dataAbbinamento"]?exists>${abbinamento.properties["crlatti:dataAbbinamento"]?string("yyyy-MM-dd")}<#else></#if>",
		"dataDisabbinamento" : "<#if abbinamento.properties["crlatti:dataDisabbinamento"]?exists>${abbinamento.properties["crlatti:dataDisabbinamento"]?string("yyyy-MM-dd")}<#else></#if>"
	  }
   }<#if abbinamento_has_next>,</#if>
   </#list>
   ]
}
</#escape>