{
	"List": [
	   <#list firmatari as firmatario>
	   { 
		   	"firmatario" : 
		   	{
				"descrizione":"${firmatario.name}"
		    }
	   }<#if firmatario_has_next>,</#if>
	   </#list>
	]
}