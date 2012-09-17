<#escape x as jsonUtils.encodeJSONString(x)>
{
   "List": [
   <#list legislatureResults as legislaturaNode>
   { 
   	"legislatura" : 
	   {
		"nome":"${legislaturaNode.name}"
	   }
   }<#if legislaturaNode_has_next>,</#if>
   </#list>
   ]
}
</#escape>