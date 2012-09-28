<#escape x as jsonUtils.encodeJSONString(x)>
{
   "atto": 
   {
	"id" : "${atto.nodeRef}",
	"nome" : "${atto.name}",
	"tipoAtto" : "${tipoAtto}",
	"numeroAtto" : "<#if atto.properties["crlatti:numeroAtto"]?exists>${atto.properties["crlatti:numeroAtto"]}<#else></#if>",
	"tipologia" : "<#if atto.properties["crlatti:tipologia"]?exists>${atto.properties["crlatti:tipologia"]}<#else></#if>",
	"legislatura" : "<#if atto.properties["crlatti:legislatura"]?exists>${atto.properties["crlatti:legislatura"]}<#else></#if>",
	"stato" : "<#if atto.properties["crlatti:statoAtto"]?exists>${atto.properties["crlatti:statoAtto"]}<#else></#if>",
	"dataIniziativa" : "<#if atto.properties["crlatti:dataIniziativa"]?exists>${atto.properties["crlatti:dataIniziativa"]?string("yyyy-MM-dd")}<#else></#if>",
	"numeroProtocollo" : "<#if atto.properties["crlatti:numeroProtocollo"]?exists>${atto.properties["crlatti:numeroProtocollo"]}<#else></#if>",
	"tipoIniziativa" : "<#if atto.properties["crlatti:tipoIniziativa"]?exists>${atto.properties["crlatti:tipoIniziativa"]}<#else></#if>",
	"oggetto" : "<#if atto.properties["crlatti:oggetto"]?exists>${atto.properties["crlatti:oggetto"]}<#else></#if>",
	"dataPubblicazione" : "<#if atto.properties["crlatti:dataPubblicazione"]?exists>${atto.properties["crlatti:dataPubblicazione"]?string("yyyy-MM-dd")}<#else></#if>",
	"dataSedutaSc" : "<#if atto.properties["crlatti:dataSedutaSc"]?exists>${atto.properties["crlatti:dataSedutaSc"]?string("yyyy-MM-dd")}<#else></#if>",
	"redigente" : "<#if atto.properties["crlatti:redigente"]?exists>${atto.properties["crlatti:redigente"]?string("true","false")}<#else></#if>",
	"deliberante" : "<#if atto.properties["crlatti:deliberante"]?exists>${atto.properties["crlatti:deliberante"]?string("true","false")}<#else></#if>",
	"soggettoConsultato" : "<#if atto.properties["crlatti:soggettoConsultato"]?exists>${atto.properties["crlatti:soggettoConsultato"]}<#else></#if>",
	"anno" : "<#if atto.properties["crlatti:anno"]?exists>${atto.properties["crlatti:anno"]?c}<#else></#if>",
	"rinviato" : "<#if atto.properties["crlatti:rinviato"]?exists>${atto.properties["crlatti:rinviato"]?string("true","false")}<#else></#if>",
	"sospeso" : "<#if atto.properties["crlatti:sospeso"]?exists>${atto.properties["crlatti:sospeso"]?string("true","false")}<#else></#if>",
	"abbinamento" : "<#if atto.properties["crlatti:abbinamento"]?exists>${atto.properties["crlatti:abbinamento"]?string("true","false")}<#else></#if>",
	"stralcio" : "<#if atto.properties["crlatti:stralcio"]?exists>${atto.properties["crlatti:stralcio"]?string("true","false")}<#else></#if>",
	"dataPresaInCarico" : "<#if atto.properties["crlatti:dataPresaInCarico"]?exists>${atto.properties["crlatti:dataPresaInCarico"]?string("yyyy-MM-dd")}<#else></#if>",
	"primoFirmatario" : "<#if atto.properties["crlatti:primoFirmatario"]?exists>${atto.properties["crlatti:primoFirmatario"]}<#else></#if>",
	"classificazione":"<#if atto.properties["crlatti:classificazione"]?exists>${atto.properties["crlatti:classificazione"]}<#else></#if>",
	"numeroRepertorio":"<#if atto.properties["crlatti:numeroRepertorio"]?exists>${atto.properties["crlatti:numeroRepertorio"]}<#else></#if>",
	"dataRepertorio":"<#if atto.properties["crlatti:dataRepertorio"]?exists>${atto.properties["crlatti:dataRepertorio"]?string("yyyy-MM-dd")}<#else></#if>",
	"descrizioneIniziativa":"<#if atto.properties["crlatti:descrizioneIniziativa"]?exists>${atto.properties["crlatti:descrizioneIniziativa"]}<#else></#if>",
	"assegnazione":"<#if atto.properties["crlatti:assegnazione"]?exists>${atto.properties["crlatti:assegnazione"]}<#else></#if>",
	"numeroDgr":"<#if atto.properties["crlatti:numeroDgr"]?exists>${atto.properties["crlatti:numeroDgr"]}<#else></#if>",
	"dataDgr":"<#if atto.properties["crlatti:dataDgr"]?exists>${atto.properties["crlatti:dataDgr"]?string("yyyy-MM-dd")}<#else></#if>",
	"tipoChiusura" : "<#if atto.properties["crlatti:tipoChiusura"]?exists>${atto.properties["crlatti:tipoChiusura"]}<#else></#if>",
	"dataChiusura":"<#if atto.properties["crlatti:dataChiusura"]?exists>${atto.properties["crlatti:dataChiusura"]?string("yyyy-MM-dd")}<#else></#if>",
	"noteChiusuraIter" : "<#if atto.properties["crlatti:noteChiusuraIter"]?exists>${atto.properties["crlatti:noteChiusuraIter"]}<#else></#if>",
	"numeroLr" : "<#if atto.properties["crlatti:numeroLr"]?exists>${atto.properties["crlatti:numeroLr"]}<#else></#if>",
	"dataLR":"<#if atto.properties["crlatti:dataLr"]?exists>${atto.properties["crlatti:dataLr"]?string("yyyy-MM-dd")}<#else></#if>",	
	"numeroPubblicazioneBURL" : "<#if atto.properties["crlatti:numeroPubblicazioneBURL"]?exists>${atto.properties["crlatti:numeroPubblicazioneBURL"]}<#else></#if>",
	"dataPubblicazioneBURL":"<#if atto.properties["crlatti:dataPubblicazioneBURL"]?exists>${atto.properties["crlatti:dataPubblicazioneBURL"]?string("yyyy-MM-dd")}<#else></#if>",	
	"valutazioneAmmissibilita":"<#if atto.properties["crlatti:ammissibilitaValutazione"]?exists>${atto.properties["crlatti:ammissibilitaValutazione"]}<#else></#if>",
	"dataRichiestaInformazioni":"<#if atto.properties["crlatti:ammissibilitaDataRichiestaInformazioni"]?exists>${atto.properties["crlatti:ammissibilitaDataRichiestaInformazioni"]?string("yyyy-MM-dd")}<#else></#if>",
	"dataRicevimentoInformazioni":"<#if atto.properties["crlatti:ammissibilitaDataRicevimentoInformazioni"]?exists>${atto.properties["crlatti:ammissibilitaDataRicevimentoInformazioni"]?string("yyyy-MM-dd")}<#else></#if>",
	"aiutiStato" : "<#if atto.properties["crlatti:ammissibilitaAiutiStato"]?exists>${atto.properties["crlatti:ammissibilitaAiutiStato"]?string("true","false")}<#else></#if>",
	"normaFinanziaria" : "<#if atto.properties["crlatti:ammissibilitaNormaFinanziaria"]?exists>${atto.properties["crlatti:ammissibilitaNormaFinanziaria"]?string("true","false")}<#else></#if>",
	"richiestaUrgenza" : "<#if atto.properties["crlatti:ammissibilitaRichiestaUrgenza"]?exists>${atto.properties["crlatti:ammissibilitaRichiestaUrgenza"]?string("true","false")}<#else></#if>",
	"votazioneUrgenza" : "<#if atto.properties["crlatti:ammissibilitaVotazioneUrgenza"]?exists>${atto.properties["crlatti:ammissibilitaVotazioneUrgenza"]?string("true","false")}<#else></#if>",
	"dataVotazioneUrgenza":"<#if atto.properties["crlatti:ammissibilitaDataVotazioneUrgenza"]?exists>${atto.properties["crlatti:ammissibilitaDataVotazioneUrgenza"]?string("yyyy-MM-dd")}<#else></#if>",
	"noteAmmissibilita":"<#if atto.properties["crlatti:ammissibilitaNote"]?exists>${atto.properties["crlatti:ammissibilitaNote"]}<#else></#if>",
    "notePresentazioneAssegnazione":"<#if notePresentazioneAssegnazione?exists>${notePresentazioneAssegnazione.content}<#else></#if>",
	"linksPresentazioneAssegnazione":[
	<#list links as link>
		{
			"link" : 
			{
				"descrizione":"${link.name}",
				"indirizzo":"<#if link.properties["crlatti:indirizzoCollegamento"]?exists>${link.properties["crlatti:indirizzoCollegamento"]}<#else></#if>",
				"pubblico":"<#if link.properties["crlatti:pubblico"]?exists>${link.properties["crlatti:pubblico"]?string("true","false")}<#else></#if>"
			}
		}<#if link_has_next>,</#if>
	</#list>
	],
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
	</#if>],
	"pareri" : [<#if pareri?exists>
		<#list pareri as parere>
			{
				"parere" :
				{
			    	"descrizione" : "<#if parere.properties["crlatti:organismoStatutarioParere"]?exists>${parere.properties["crlatti:organismoStatutarioParere"]}<#else></#if>",
			    	"dataAssegnazione" : "<#if parere.properties["crlatti:dataAssegnazioneParere"]?exists>${parere.properties["crlatti:dataAssegnazioneParere"]?string("yyyy-MM-dd")}<#else></#if>",
			    	"dataAnnullo" : "<#if parere.properties["crlatti:dataAnnulloParere"]?exists>${parere.properties["crlatti:dataAnnulloParere"]?string("yyyy-MM-dd")}<#else></#if>",
			    	"obbligatorio" : "<#if parere.properties["crlatti:obbligatorio"]?exists>${parere.properties["crlatti:obbligatorio"]?string("true","false")}<#else></#if>"
			    }
			}
			<#if parere_has_next>,</#if>
    	</#list>
    <#else></#if>],
    
    "passaggi" : [<#if passaggi?exists>
		<#list passaggi as passaggio>
			{
				"passaggio" :
				{
			    	"nome" : "${passaggio.name}",
			    	"commissioni" :  [
			    		<#assign commissioni = passaggio.childrenByXPath["*[@cm:name='Commissioni']"][0]>
			    		<#assign commissioniList = commissioni.getChildAssocsByType("crlatti:commissione")>
			    		<#list commissioniList as commissione>
			    		{ 
			    			"commissione" :
						   {
						   	"descrizione" : "${commissione.name}",
					    	"dataProposta" : "<#if commissione.properties["crlatti:dataPropostaCommissione"]?exists>${commissione.properties["crlatti:dataPropostaCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					    	"dataAnnullo" : "<#if commissione.properties["crlatti:dataAnnulloCommissione"]?exists>${commissione.properties["crlatti:dataAnnulloCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					    	"dataAssegnazione" : "<#if commissione.properties["crlatti:dataAssegnazioneCommissione"]?exists>${commissione.properties["crlatti:dataAssegnazioneCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					    	"dataPresaInCarico" : "<#if commissione.properties["crlatti:dataPresaInCaricoCommissione"]?exists>${commissione.properties["crlatti:dataPresaInCaricoCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					    	"ruolo" : "<#if commissione.properties["crlatti:ruoloCommissione"]?exists>${commissione.properties["crlatti:ruoloCommissione"]}<#else></#if>",
					    	"stato" : "<#if commissione.properties["crlatti:statoCommissione"]?exists>${commissione.properties["crlatti:statoCommissione"]}<#else></#if>"	   	
						   }
						}
						<#if commissione_has_next>,</#if>
						</#list>
			    	]
			   }
			   <#if passaggio_has_next>,</#if>
			}
			
    	</#list>
    <#else></#if>]
    
   
	
   }
}
</#escape>