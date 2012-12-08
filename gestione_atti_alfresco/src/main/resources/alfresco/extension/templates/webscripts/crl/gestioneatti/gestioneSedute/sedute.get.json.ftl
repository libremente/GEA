<#escape x as jsonUtils.encodeJSONString(x)>
{  
   "List":[
   <#if sedute?exists>
   <#list sedute as seduta>
   { "seduta" : 
	   {
	    "idSeduta" : "${seduta.nodeRef}",
		"dataSeduta" : "<#if seduta.properties["crlatti:dataSedutaSedutaODG"]?exists>${seduta.properties["crlatti:dataSedutaSedutaODG"]?string("yyyy-MM-dd")}<#else></#if>",
		"dalleOre" : "<#if seduta.properties["crlatti:dalleOreSedutaODG"]?exists>${seduta.properties["crlatti:dalleOreSedutaODG"]}<#else></#if>",
		"alleOre" : "<#if seduta.properties["crlatti:alleOreSedutaODG"]?exists>${seduta.properties["crlatti:alleOreSedutaODG"]}<#else></#if>",		
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
			<#list attiList as attoTrattato>
				<#assign atto = attoTrattato.assocs["crlatti:attoTrattatoSedutaODG"][0]>
				<#assign tipoSubstring = atto.typeShort?substring(12,15)>
	   			<#assign tipoTroncatoMaiuscolo = tipoSubstring?capitalize>
				{
					"attoTrattato" : 
					{
						"previsto" : "<#if attoTrattato.properties["crlatti:previstoAttoTrattatoODG"]?exists>${attoTrattato.properties["crlatti:previstoAttoTrattatoODG"]?string("true","false")}<#else></#if>",
						"discusso" : "<#if attoTrattato.properties["crlatti:discussoAttoTrattatoODG"]?exists>${attoTrattato.properties["crlatti:discussoAttoTrattatoODG"]?string("true","false")}<#else></#if>",
						"numeroOrdinamento" : "<#if attoTrattato.properties["crlatti:numeroOrdinamento"]?exists>${attoTrattato.properties["crlatti:numeroOrdinamento"]}<#else></#if>",
						"atto": {
							"atto": {
								"id": "${atto.nodeRef}",
								"nome": "${atto.name}",
								"tipoAtto": "${tipoTroncatoMaiuscolo}",
								"numeroAtto" : "${atto.name}",
								"consultazioni": [
									<#assign consultazioni = atto.childrenByXPath["*[@cm:name='Consultazioni']"][0]>
			    					<#assign consultazioniList = consultazioni.getChildAssocsByType("crlatti:consultazione")>
			    					<#list consultazioniList as consultazione>
			    					{
			    						"consultazione" : 
			    						{
											"descrizione" : "${consultazione.name}",
											"commissione" : "<#if consultazione.properties["crlatti:commissioneConsultazione"]?exists>${consultazione.properties["crlatti:commissioneConsultazione"]}<#else></#if>",
											"dataConsultazione" : "<#if consultazione.properties["crlatti:dataConsultazione"]?exists>${consultazione.properties["crlatti:dataConsultazione"]?string("yyyy-MM-dd")}<#else></#if>",
											"prevista" : "<#if consultazione.properties["crlatti:previstaConsultazione"]?exists>${consultazione.properties["crlatti:previstaConsultazione"]?string("true","false")}<#else></#if>",
											"discussa" : "<#if consultazione.properties["crlatti:discussaConsultazione"]?exists>${consultazione.properties["crlatti:discussaConsultazione"]?string("true","false")}<#else></#if>",
											"dataSeduta" :  "<#if consultazione.properties["crlatti:dataSedutaConsultazione"]?exists>${consultazione.properties["crlatti:dataSedutaConsultazione"]?string("yyyy-MM-dd")}<#else></#if>",
											"note" : "<#if consultazione.properties["crlatti:noteConsultazione"]?exists>${consultazione.properties["crlatti:noteConsultazione"]}<#else></#if>",
											"soggettiInvitati" : [
												<#assign soggettiConsultazioneFolder = consultazione.childrenByXPath["*[@cm:name='SoggettiInvitati']"][0]>
												<#assign soggettiConsultazione = soggettiConsultazioneFolder.getChildAssocsByType("crlatti:soggettoInvitato")>
												<#list soggettiConsultazione as soggetto>
												{
													"soggettoInvitato" : 
													{
														"descrizione" : "<#if soggetto.properties["crlatti:descrizioneSoggettoInvitato"]?exists>${soggetto.properties["crlatti:descrizioneSoggettoInvitato"]}<#else></#if>",
														"intervenuto" : "<#if soggetto.properties["crlatti:intervenutoSoggettoInvitato"]?exists>${soggetto.properties["crlatti:intervenutoSoggettoInvitato"]?string("true","false")}<#else></#if>"
													}
												}<#if soggetto_has_next>,</#if>
												</#list>
											]
						
    									}
    								}
									<#if consultazione_has_next>,</#if>
									</#list>
								]
			    					
							}
						}
					}
				}<#if attoTrattato_has_next>,</#if>
			</#list>
		],
		"audizioni" : [
			<#assign audizioni = seduta.childrenByXPath["*[@cm:name='Audizioni']"][0]>
			<#assign audizioniList = audizioni.getChildAssocsByType("crlatti:audizione")>
			<#list audizioniList as audizione>
			{
				"audizione" : {
					"soggettoPartecipante" : "${audizione.name}",
					"previsto" : "<#if audizione.properties["crlatti:previstoAudizione"]?exists>${audizione.properties["crlatti:previstoAudizione"]?string("true","false")}<#else></#if>",
					"discusso" : "<#if audizione.properties["crlatti:discussoAudizione"]?exists>${audizione.properties["crlatti:discussoAudizione"]?string("true","false")}<#else></#if>"
				}
			}
			<#if audizione_has_next>,</#if>
			</#list>
		
		]
	 }
   }<#if seduta_has_next>,</#if>
   </#list>
   </#if>
   ]
}
</#escape>


