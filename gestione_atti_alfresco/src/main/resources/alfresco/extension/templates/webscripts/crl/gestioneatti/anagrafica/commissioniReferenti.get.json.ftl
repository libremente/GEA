{
	"List": [
	   <#list commissioniReferenti as commissioneReferente>
	   { 
		   	"commissioneReferente" : 
		   	{
				"descrizione":"${commissioneReferente.name}"
		    }
	   }<#if commissioneReferente_has_next>,</#if>
	   </#list>
	]
}