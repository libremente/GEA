{
   "atto": 
   {
	"id" : "${atto.nodeRef}",
	"nome" : "${atto.name}",
	"numeroAtto" : "<#if atto.properties["crlatti:numeroAtto"]?exists>${atto.properties["crlatti:numeroAtto"]}<#else></#if>",
	"tipologia" : "<#if atto.properties["crlatti:tipologia"]?exists>${atto.properties["crlatti:tipologia"]}<#else></#if>",
	"legislatura" : "<#if atto.properties["crlatti:legislatura"]?exists>${atto.properties["crlatti:legislatura"]}<#else></#if>",
	"stato" : "<#if atto.properties["crlatti:statoAtto"]?exists>${atto.properties["crlatti:statoAtto"]}<#else></#if>",
	"dataIniziativa" : "<#if atto.properties["crlatti:dataIniziativa"]?exists>${atto.properties["crlatti:dataIniziativa"]?string("dd/MM/yyyy")}<#else></#if>",
	"numeroProtocollo" : "<#if atto.properties["crlatti:numeroProtocollo"]?exists>${atto.properties["crlatti:numeroProtocollo"]}<#else></#if>",
	"tipoIniziativa" : "<#if atto.properties["crlatti:tipoIniziativa"]?exists>${atto.properties["crlatti:tipoIniziativa"]}<#else></#if>",
	"numeroDcr" : "<#if atto.properties["crlatti:numeroDcr"]?exists>${atto.properties["crlatti:numeroDcr"]}<#else></#if>",
	"oggetto" : "<#if atto.properties["crlatti:oggetto"]?exists>${atto.properties["crlatti:oggetto"]}<#else></#if>",
	"tipoChiusura" : "<#if atto.properties["crlatti:tipoChiusura"]?exists>${atto.properties["crlatti:tipoChiusura"]}<#else></#if>",
	"dataPubblicazione" : "<#if atto.properties["crlatti:dataPubblicazione"]?exists>${atto.properties["crlatti:dataPubblicazione"]?string("dd/MM/yyyy")}<#else></#if>",
	"esitoVotoComRef" : "<#if atto.properties["crlatti:esitoVotoComRef"]?exists>${atto.properties["crlatti:esitoVotoComRef"]}<#else></#if>",
	"dataSedutaSc" : "<#if atto.properties["crlatti:dataSedutaSc"]?exists>${atto.properties["crlatti:dataSedutaSc"]?string("dd/MM/yyyy")}<#else></#if>",
	"esitoVotoAula" : "<#if atto.properties["crlatti:esitoVotoAula"]?exists>${atto.properties["crlatti:esitoVotoAula"]}<#else></#if>",
	"dataSedutaComm" : "<#if atto.properties["crlatti:dataSedutaComm"]?exists>${atto.properties["crlatti:dataSedutaComm"]?string("dd/MM/yyyy")}<#else></#if>",
	"commReferente" : "<#if atto.properties["crlatti:commReferente"]?exists>${atto.properties["crlatti:commReferente"]}<#else></#if>",
	"dataSedutaAula" : "<#if atto.properties["crlatti:dataSedutaAula"]?exists>${atto.properties["crlatti:dataSedutaAula"]?string("dd/MM/yyyy")}<#else></#if>",
	"commConsultiva" : "<#if atto.properties["crlatti:commConsultiva"]?exists>${atto.properties["crlatti:commConsultiva"]}<#else></#if>",
	"redigente" : "<#if atto.properties["crlatti:redigente"]?exists>${atto.properties["crlatti:redigente"]?string("true","false")}<#else></#if>",
	"deliberante" : "<#if atto.properties["crlatti:deliberante"]?exists>${atto.properties["crlatti:deliberante"]?string("true","false")}<#else></#if>",
	"organismoStatutario" : "<#if atto.properties["crlatti:organismoStatutario"]?exists>${atto.properties["crlatti:organismoStatutario"]}<#else></#if>",
	"numeroLcr" : "<#if atto.properties["crlatti:numeroLcr"]?exists>${atto.properties["crlatti:numeroLcr"]}<#else></#if>",
	"soggettoConsultato" : "<#if atto.properties["crlatti:soggettoConsultato"]?exists>${atto.properties["crlatti:soggettoConsultato"]}<#else></#if>",
	"numeroLr" : "<#if atto.properties["crlatti:numeroLr"]?exists>${atto.properties["crlatti:numeroLr"]}<#else></#if>",
	"anno" : "<#if atto.properties["crlatti:anno"]?exists>${atto.properties["crlatti:anno"]?c}<#else></#if>",
	"emendato" : "<#if atto.properties["crlatti:emendato"]?exists>${atto.properties["crlatti:emendato"]?string("true","false")}<#else></#if>",
	"rinviato" : "<#if atto.properties["crlatti:rinviato"]?exists>${atto.properties["crlatti:rinviato"]?string("true","false")}<#else></#if>",
	"sospeso" : "<#if atto.properties["crlatti:sospeso"]?exists>${atto.properties["crlatti:sospeso"]?string("true","false")}<#else></#if>",
	"abbinamento" : "<#if atto.properties["crlatti:abbinamento"]?exists>${atto.properties["crlatti:abbinamento"]?string("true","false")}<#else></#if>",
	"stralcio" : "<#if atto.properties["crlatti:stralcio"]?exists>${atto.properties["crlatti:stralcio"]?string("true","false")}<#else></#if>",
	"sospeso" : "<#if atto.properties["crlatti:sospeso"]?exists>${atto.properties["crlatti:sospeso"]?string("true","false")}<#else></#if>",
	"primoFirmatario" : "<#if atto.properties["crlatti:primoFirmatario"]?exists>${atto.properties["crlatti:primoFirmatario"]}<#else></#if>",
	"relatori" : <#if atto.properties["crlatti:relatori"]?exists>
	[
	<#list atto.properties["crlatti:relatori"] as relatore>
		{ 
			"relatore" : 
			{ 
				"nome" : "${relatore}"
			}
		}
		<#if relatore_has_next>,</#if>
	</#list>
	]
		<#else>
	</#if>
	
   }
}