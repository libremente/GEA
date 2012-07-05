{
   "legislatura":"${nomeLegislatura}",
   "anni": [
   <#list anniLegislatura as anno>
   {
	"anno":"${anno?c}"
   }<#if anno_has_next>,</#if>
   ]
   </#list>
}