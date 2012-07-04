{
   "legislature":
   <#list legislatureResults as legislaturaNode>
   {
	"legislatura":"${legislaturaNode.name}"
   }<#if legislaturaNode_has_next>,</#if>
   </#list>
}