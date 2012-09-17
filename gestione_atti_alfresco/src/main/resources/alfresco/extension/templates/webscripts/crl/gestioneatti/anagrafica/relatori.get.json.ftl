<#escape x as jsonUtils.encodeJSONString(x)>
{
	"List": [
	   <#list relatori as relatore>
	   { 
		   	"relatore" : 
		   	{
				"descrizione":"${relatore.name}"
		    }
	   }<#if relatore_has_next>,</#if>
	   </#list>
	]
}
</#escape>