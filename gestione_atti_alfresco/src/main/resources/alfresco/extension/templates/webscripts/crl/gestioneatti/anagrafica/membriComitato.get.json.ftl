{
	"List": [
	   <#list membri as membro>
	   { 
		   	"membro" : 
		   	{
				"descrizione":"${membro.name}"
		    }
	   }<#if membro_has_next>,</#if>
	   </#list>
	]
}