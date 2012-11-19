{
	"List": [
	   <#list gruppi as gruppo>
	   { 
		   	"gruppoConsiliare" : 
		   	{
				"descrizione":"${gruppo.name}"
		    }
	   }<#if gruppo_has_next>,</#if>
	   </#list>
	]
}