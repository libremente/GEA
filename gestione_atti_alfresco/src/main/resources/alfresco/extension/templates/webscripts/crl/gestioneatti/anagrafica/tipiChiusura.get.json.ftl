{
	"List": [
	   <#list tipiChiusura as tipoChiusura>
	   { 
		   	"tipoChiusura" : 
		   	{
				"descrizione":"${tipoChiusura.name}"
		    }
	   }<#if tipoChiusura_has_next>,</#if>
	   </#list>
	]
}