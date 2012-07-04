{
   "tipiAtto":
   <#list tipiAttoResults as tipoAttoNode>
   {
	"descrizione":"${tipoAttoNode.name} - ${tipoAttoNode.properties.title}",
	"codice":"${tipoAttoNode.name}"
   }<#if tipoAttoNode_has_next>,</#if>
   </#list>
}