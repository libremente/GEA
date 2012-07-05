{
   "tipoAtto":"${tipoAtto}",
   
   "tipologieAtto":[
   <#list tipologieAtto as tipologiaAttoNode>
   {
	"tipologiaAtto":"${tipologiaAttoNode.name}"
   }<#if tipologiaAttoNode_has_next>,</#if>
   ]
   </#list>
}