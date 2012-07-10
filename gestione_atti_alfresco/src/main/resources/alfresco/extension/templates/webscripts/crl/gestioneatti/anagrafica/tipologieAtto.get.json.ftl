{
   "tipoAtto":"${tipoAtto}",
   
   "List":[
   { tipologia : 
	   <#list tipologieAtto as tipologiaAttoNode>
	   {
		"descrizione":"${tipologiaAttoNode.name}"
	   }
   }<#if tipologiaAttoNode_has_next>,</#if>
   </#list>
   ]
}