<#escape x as jsonUtils.encodeJSONString(x)>
{
	"List": [
	   <#list firmatari as firmatario>
	   { 
		   	"firmatario" : 
		   	{
				"descrizione":"<#if firmatario.properties["crlatti:nomeConsigliereAnagrafica"]?exists>${firmatario.properties["crlatti:nomeConsigliereAnagrafica"]}<#else></#if> <#if firmatario.properties["crlatti:cognomeConsigliereAnagrafica"]?exists>${firmatario.properties["crlatti:cognomeConsigliereAnagrafica"]}<#else></#if>",
				"cognomeNome":"<#if firmatario.properties["crlatti:cognomeConsigliereAnagrafica"]?exists>${firmatario.properties["crlatti:cognomeConsigliereAnagrafica"]}<#else></#if> <#if firmatario.properties["crlatti:nomeConsigliereAnagrafica"]?exists>${firmatario.properties["crlatti:nomeConsigliereAnagrafica"]}<#else></#if>",
				"gruppoConsiliare" : "<#if firmatario.properties["crlatti:codiceGruppoConsigliereAnagrafica"]?exists>${firmatario.properties["crlatti:codiceGruppoConsigliereAnagrafica"]}<#else></#if>"
				<#-- "id_persona" : ${firmatario.properties["crlatti:idAnagrafica"]} -->
		    }
	   }<#if firmatario_has_next>,</#if>
	   </#list>
	]
}
</#escape>