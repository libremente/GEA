{
	"List": [
	   <#list organismiStatutari as organismoStatutario>
	   { 
		   	"organismoStatutario" : 
		   	{
				"descrizione":"${organismoStatutario.name}"
		    }
	   }<#if organismoStatutario_has_next>,</#if>
	   </#list>
	]
}