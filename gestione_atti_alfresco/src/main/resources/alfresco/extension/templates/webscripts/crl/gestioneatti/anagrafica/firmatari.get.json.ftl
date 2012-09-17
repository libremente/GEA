<#escape x as jsonUtils.encodeJSONString(x)>
{
	"List": [
	   <#list firmatari as firmatario>
	   { 
		   	"firmatario" : 
		   	{
				"descrizione" : "${firmatario.name}",
				"gruppoConsiliare" : "${firmatario.properties["crlatti:gruppoConsiliare"]}"
		    }
	   }<#if firmatario_has_next>,</#if>
	   </#list>
	]
}
</#escape>