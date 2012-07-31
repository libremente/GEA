{
   "Atto": 
   {
	"id" : "${atto.nodeRef}",
	"nome" : "${atto.name}",
	"numeroAtto" : "<#if atto.properties["crlatti:numeroAtto"]?exists>${atto.properties["crlatti:numeroAtto"]}<#else></#if>",
	"tipologia" : "<#if atto.properties["crlatti:tipologia"]?exists>${atto.properties["crlatti:tipologia"]}<#else></#if>",
	"legislatura" : "<#if atto.properties["crlatti:legislatura"]?exists>${atto.properties["crlatti:legislatura"]}<#else></#if>",
	"stato" : "<#if atto.properties["crlatti:statoAtto"]?exists>${atto.properties["crlatti:statoAtto"]}<#else></#if>",
	"dataIniziativa" : "<#if atto.properties["crlatti:dataIniziativa"]?exists>${atto.properties["crlatti:dataIniziativa"]?string("yyyy-MM-dd")}<#else></#if>",
	"numeroProtocollo" : "<#if atto.properties["crlatti:numeroProtocollo"]?exists>${atto.properties["crlatti:numeroProtocollo"]}<#else></#if>",
	"tipoIniziativa" : "<#if atto.properties["crlatti:tipoIniziativa"]?exists>${atto.properties["crlatti:tipoIniziativa"]}<#else></#if>",
	"numeroDcr" : "<#if atto.properties["crlatti:numeroDcr"]?exists>${atto.properties["crlatti:numeroDcr"]}<#else></#if>",
	"oggetto" : "<#if atto.properties["crlatti:oggetto"]?exists>${atto.properties["crlatti:oggetto"]}<#else></#if>",
	"tipoChiusura" : "<#if atto.properties["crlatti:tipoChiusura"]?exists>${atto.properties["crlatti:tipoChiusura"]}<#else></#if>",
	"dataPubblicazione" : "<#if atto.properties["crlatti:dataPubblicazione"]?exists>${atto.properties["crlatti:dataPubblicazione"]?string("yyyy-MM-dd")}<#else></#if>",
	"esitoVotoCommissioneReferente" : "<#if atto.properties["crlatti:esitoVotoComRef"]?exists>${atto.properties["crlatti:esitoVotoComRef"]}<#else></#if>",
	"dataSedutaSc" : "<#if atto.properties["crlatti:dataSedutaSc"]?exists>${atto.properties["crlatti:dataSedutaSc"]?string("yyyy-MM-dd")}<#else></#if>",
	"esitoVotoAula" : "<#if atto.properties["crlatti:esitoVotoAula"]?exists>${atto.properties["crlatti:esitoVotoAula"]}<#else></#if>",
	"dataSedutaCommissione" : "<#if atto.properties["crlatti:dataSedutaComm"]?exists>${atto.properties["crlatti:dataSedutaComm"]?string("yyyy-MM-dd")}<#else></#if>",
	"commissioneReferente" : "<#if atto.properties["crlatti:commReferente"]?exists>${atto.properties["crlatti:commReferente"]}<#else></#if>",
	"dataSedutaAula" : "<#if atto.properties["crlatti:dataSedutaAula"]?exists>${atto.properties["crlatti:dataSedutaAula"]?string("yyyy-MM-dd")}<#else></#if>",
	"commissioneConsultiva" : "<#if atto.properties["crlatti:commConsultiva"]?exists>${atto.properties["crlatti:commConsultiva"]}<#else></#if>",
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
	"primoFirmatario" : "<#if atto.properties["crlatti:primoFirmatario"]?exists>${atto.properties["crlatti:primoFirmatario"]}<#else></#if>",
	"classificazione":"<#if atto.properties["crlatti:classificazione"]?exists>${atto.properties["crlatti:classificazione"]}<#else></#if>",
	"numeroRepertorio":"<#if atto.properties["crlatti:numeroRepertorio"]?exists>${atto.properties["crlatti:numeroRepertorio"]}<#else></#if>",
	"dataRepertorio":"<#if atto.properties["crlatti:dataRepertorio"]?exists>${atto.properties["crlatti:dataRepertorio"]?string("yyyy-MM-dd")}<#else></#if>",
	"descrizioneIniziativa":"<#if atto.properties["crlatti:descrizioneIniziativa"]?exists>${atto.properties["crlatti:descrizioneIniziativa"]}<#else></#if>",
	"assegnazione":"<#if atto.properties["crlatti:assegnazione"]?exists>${atto.properties["crlatti:assegnazione"]}<#else></#if>",
	"numeroDgr":"<#if atto.properties["crlatti:numeroDgr"]?exists>${atto.properties["crlatti:numeroDgr"]}<#else></#if>",
	"dataDgr":"<#if atto.properties["crlatti:dataDgr"]?exists>${atto.properties["crlatti:dataDgr"]?string("yyyy-MM-dd")}<#else></#if>",
	"relatori" : [<#if atto.properties["crlatti:relatori"]?exists>
	
	<#list atto.properties["crlatti:relatori"] as relatore>
		{ 
			"relatore" : 
			{ 
				"nome" : "${relatore}"
			}
		}
		<#if relatore_has_next>,</#if>
	</#list>
	
		<#else>
	</#if>]
	
   }
}