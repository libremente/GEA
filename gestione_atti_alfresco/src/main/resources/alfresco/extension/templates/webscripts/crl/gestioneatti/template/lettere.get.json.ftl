<#escape x as jsonUtils.encodeJSONString(x)>
{  
   "List":[
   <#list lettere as lettera>
   { "lettera" : 
	   {
	   	"id" : "${lettera.id}",
		"descrizione" : "${lettera.descrizione}",
		"tipoTemplate" : "${lettera.tipoTemplate}"
		}
   }
   <#if lettera_has_next>,</#if>
   </#list>
   ]
}
</#escape>