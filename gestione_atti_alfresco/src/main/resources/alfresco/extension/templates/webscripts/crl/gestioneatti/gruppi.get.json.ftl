{
   "gruppi":
   <#list gruppi as gruppo>
   {
	"gruppo":"${gruppo.name}"
   }<#if gruppo_has_next>,</#if>
   </#list>
}