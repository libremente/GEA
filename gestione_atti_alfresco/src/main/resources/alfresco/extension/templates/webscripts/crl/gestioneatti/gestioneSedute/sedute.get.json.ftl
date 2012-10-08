<#escape x as jsonUtils.encodeJSONString(x)>
{  
   "List":[
   <#if sedute?exists>
   <#list sedute as seduta>
   { "seduta" : 
	   {
	    "idSeduta" : "${seduta.nodeRef}",
		"dataSeduta" : "<#if seduta.properties["crlatti:dataSedutaSedutaODG"]?exists>${seduta.properties["crlatti:dataSedutaSedutaODG"]?string("yyyy-MM-dd")}<#else></#if>",
		"numVerbale" : "<#if seduta.properties["crlatti:numVerbaleSedutaODG"]?exists>${seduta.properties["crlatti:numVerbaleSedutaODG"]}<#else></#if>",
		"note" : "<#if seduta.properties["crlatti:noteSedutaODG"]?exists>${seduta.properties["crlatti:noteSedutaODG"]}<#else></#if>",
		"links":[
			<#assign links = seduta.childrenByXPath["*[@cm:name='Links']"][0]>
			<#assign linksList = links.getChildAssocsByType("crlatti:link")>
			<#list linksList as link>
				{
					"link" : 
					{
						"descrizione":"${link.name}",
						"indirizzo":"<#if link.properties["crlatti:indirizzoCollegamento"]?exists>${link.properties["crlatti:indirizzoCollegamento"]}<#else></#if>"
					}
				}<#if link_has_next>,</#if>
			</#list>
		],
		"attiTrattati":[
			<#assign atti = seduta.childrenByXPath["*[@cm:name='AttiTrattati']"][0]>
			<#assign attiList = atti.getChildAssocsByType("crlatti:attoTrattatoODG")>
			<#list attiList as atto>
				{
					"attoTrattato" : 
					{
						"id":"${atto.name}"
					}
				}<#if atto_has_next>,</#if>
			</#list>
		]
	 }
   }<#if seduta_has_next>,</#if>
   </#list>
   </#if>
   ]
}
</#escape>


