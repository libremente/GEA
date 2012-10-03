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
					      	"ruolo" : "<#if commissione.properties["crlatti:ruoloCommissione"]?exists>${commissione.properties["crlatti:ruoloCommissione"]}<#else></#if>",
					    	"stato" : "<#if commissione.properties["crlatti:statoCommissione"]?exists>${commissione.properties["crlatti:statoCommissione"]}<#else></#if>",
					    	"tipoVotazione" : "<#if commissione.properties["crlatti:tipoVotazioneCommissione"]?exists>${commissione.properties["crlatti:tipoVotazioneCommissione"]}<#else></#if>",
					    	"esitoVotazione" : "<#if commissione.properties["crlatti:esitoVotazioneCommissione"]?exists>${commissione.properties["crlatti:esitoVotazioneCommissione"]}<#else></#if>",
					    	"materia" : "<#if commissione.properties["crlatti:materiaCommissione"]?exists>${commissione.properties["crlatti:materiaCommissione"]}<#else></#if>",	
					    	"motivazioniContinuazioneInReferente": "<#if commissione.properties["crlatti:motivazioniContinuazioneInReferente"]?exists>${commissione.properties["crlatti:motivazioniContinuazioneInReferente"]}<#else></#if>",
					    	
					    	"esitoVotazioneIntesa": "<#if commissione.properties["crlatti:esitoVotazioneIntesaClausolaValutEsame"]?exists>${commissione.properties["crlatti:esitoVotazioneIntesaClausolaValutEsame"]}<#else></#if>",
					    	"noteClausolaValutativa": "<#if commissione.properties["crlatti:noteClausolaValutativaEsame"]?exists>${commissione.properties["crlatti:noteClausolaValutativaEsame"]}<#else></#if>",

					    	"dataProposta" : "<#if commissione.properties["crlatti:dataPropostaCommissione"]?exists>${commissione.properties["crlatti:dataPropostaCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					    	"dataAnnullo" : "<#if commissione.properties["crlatti:dataAnnulloCommissione"]?exists>${commissione.properties["crlatti:dataAnnulloCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					    	"dataAssegnazione" : "<#if commissione.properties["crlatti:dataAssegnazioneCommissione"]?exists>${commissione.properties["crlatti:dataAssegnazioneCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					    	"dataPresaInCarico" : "<#if commissione.properties["crlatti:dataPresaInCaricoCommissione"]?exists>${commissione.properties["crlatti:dataPresaInCaricoCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					  		"dataIntesa" : "<#if commissione.properties["crlatti:dataIntesaClausolaValutEsame"]?exists>${commissione.properties["crlatti:dataIntesaClausolaValutEsame"]?string("yyyy-MM-dd")}<#else></#if>",
					  		"dataPresaInCaricoProposta" : "<#if commissione.properties["crlatti:dataPresaInCaricoPropClausolaValutEsame"]?exists>${commissione.properties["crlatti:dataPresaInCaricoPropClausolaValutEsame"]?string("yyyy-MM-dd")}<#else></#if>",
					    	"dataScadenzaEsameCommissioni" : "<#if commissione.properties["crlatti:dataScadenzaCommissione"]?exists>${commissione.properties["crlatti:dataScadenzaCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					  		"dataNomina" : "<#if commissione.properties["crlatti:dataNominaCommissione"]?exists>${commissione.properties["crlatti:dataNominaCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					  		"dataVotazione" : "<#if commissione.properties["crlatti:dataVotazioneCommissione"]?exists>${commissione.properties["crlatti:dataVotazioneCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					  		"dataTrasmissione" : "<#if commissione.properties["crlatti:dataTrasmissioneCommissione"]?exists>${commissione.properties["crlatti:dataTrasmissioneCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
					  		"dataSedutaContinuazioneInReferente" : "<#if commissione.properties["crlatti:dataSedutaContinuazioneInReferente"]?exists>${commissione.properties["crlatti:dataSedutaContinuazioneInReferente"]?string("yyyy-MM-dd")}<#else></#if>",
							"dataRichiestaIscrizioneAula" : "<#if commissione.properties["crlatti:dataRichiestaIscrizioneAulaCommissione"]?exists>${commissione.properties["crlatti:dataRichiestaIscrizioneAulaCommissione"]?string("yyyy-MM-dd")}<#else></#if>",
		
							"passaggioDirettoInAula": "<#if commissione.properties["crlatti:passaggioDirettoInAulaCommissione"]?exists>${commissione.properties["crlatti:passaggioDirettoInAulaCommissione"]?string("true","false")}<#else></#if>",
		
							<#assign comitato = commissione.childrenByXPath["*[@cm:name='ComitatoRistretto']"][0]>
							"dataIstituzioneComitato" : "<#if comitato.properties["crlatti:dataIstituzioneCR"]?exists>${comitato.properties["crlatti:dataIstituzioneCR"]?string("yyyy-MM-dd")}<#else></#if>",
							"dataFineLavoriComitato" : "<#if comitato.properties["crlatti:dataFineLavoriCR"]?exists>${comitato.properties["crlatti:dataFineLavoriCR"]?string("yyyy-MM-dd")}<#else></#if>",
							"presenzaComitatoRistretto": "<#if comitato.properties["crlatti:presenzaCR"]?exists>${comitato.properties["crlatti:presenzaCR"]?string("true","false")}<#else></#if>",
							
					    	"relatori" : [<#if atto.properties["crlatti:relatori"]?exists>
	
								<#assign relatori = commissione.childrenByXPath["*[@cm:name='Relatori']"][0]>
								<#assign relatoriList = relatori.getChildAssocsByType("crlatti:relatore")>
								<#list  relatoriList as relatore>
								{	 
										"relatore" : 
										{ 
											"nome" : "${relatore.name}",
											"dataNomina": "<#if relatore.properties["crlatti:dataNominaRelatore"]?exists>${relatore.properties["crlatti:dataNominaRelatore"]?string("yyyy-MM-dd")}<#else></#if>",
											"dataUscita": "<#if relatore.properties["crlatti:dataUscitaRelatore"]?exists>${relatore.properties["crlatti:dataUscitaRelatore"]?string("yyyy-MM-dd")}<#else></#if>"
										}
								}	
									<#if relatore_has_next>,</#if>
								</#list>
									<#else>
								</#if>],
						   	"comitatoRistretto" : 
						   	{
						   		"comitatoRistretto" : {
							   		"componenti":
							   			[
							   			<#assign componenti = commissione.childrenByXPath["*[@cm:name='ComitatoRistretto']"][0]>
							   			<#assign componentiList = componenti.getChildAssocsByType("crlatti:membroComitatoRistretto")>
							   			<#list  componentiList as componente>
										 {
											"componente" : 
											{ 
												"nome" : "${componente.name}",
												"dataNomina": "<#if componente.properties["crlatti:dataNominaMCR"]?exists>${componente.properties["crlatti:dataNominaMCR"]?string("yyyy-MM-dd")}<#else></#if>",
												"dataUscita": "<#if componente.properties["crlatti:dataUscitaMCR"]?exists>${componente.properties["crlatti:dataUscitaMCR"]?string("yyyy-MM-dd")}<#else></#if>",
												"coordinatore": "<#if componente.properties["crlatti:coordinatoreMCR"]?exists>${componente.properties["crlatti:coordinatoreMCR"]?string("true","false")}<#else></#if>"
											}
										}
										<#if componente_has_next>,</#if>
									</#list>
							   			
							   			]
						   		}
						   	},
						   	"numEmendPresentatiMaggiorEsameCommissioni": "<#if commissione.properties["crlatti:numEmendPresentatiMaggiorEsame"]?exists>${commissione.properties["crlatti:numEmendPresentatiMaggiorEsame"]}<#else></#if>",
   							"numEmendPresentatiMinorEsameCommissioni": "<#if commissione.properties["crlatti:numEmendPresentatiMinorEsame"]?exists>${commissione.properties["crlatti:numEmendPresentatiMinorEsame"]}<#else></#if>",
  							"numEmendPresentatiGiuntaEsameCommissioni": "<#if commissione.properties["crlatti:numEmendPresentatiGiuntaEsame"]?exists>${commissione.properties["crlatti:numEmendPresentatiGiuntaEsame"]}<#else></#if>",
						    "numEmendPresentatiMistoEsameCommissioni": "<#if commissione.properties["crlatti:numEmendPresentatiMistoEsame"]?exists>${commissione.properties["crlatti:numEmendPresentatiMistoEsame"]}<#else></#if>",
						    "numEmendApprovatiMaggiorEsameCommissioni": "<#if commissione.properties["crlatti:numEmendApprovatiMaggiorEsame"]?exists>${commissione.properties["crlatti:numEmendApprovatiMaggiorEsame"]}<#else></#if>",
						    "numEmendApprovatiMinorEsameCommissioni": "<#if commissione.properties["crlatti:numEmendApprovatiMinorEsame"]?exists>${commissione.properties["crlatti:numEmendApprovatiMinorEsame"]}<#else></#if>",
						    "numEmendApprovatiGiuntaEsameCommissioni": "<#if commissione.properties["crlatti:numEmendApprovatiGiuntaEsame"]?exists>${commissione.properties["crlatti:numEmendApprovatiGiuntaEsame"]}<#else></#if>",
						    "numEmendApprovatiMistoEsameCommissioni": "<#if commissione.properties["crlatti:numEmendApprovatiMistoEsame"]?exists>${commissione.properties["crlatti:numEmendApprovatiMistoEsame"]}<#else></#if>",
  							"nonAmmissibiliEsameCommissioni": "<#if commissione.properties["crlatti:numEmendNonAmmissibiliEsame"]?exists>${commissione.properties["crlatti:numEmendNonAmmissibiliEsame"]}<#else></#if>",
   							"decadutiEsameCommissioni": "<#if commissione.properties["crlatti:numEmendDecadutiEsame"]?exists>${commissione.properties["crlatti:numEmendDecadutiEsame"]}<#else></#if>",
   							"ritiratiEsameCommissioni": "<#if commissione.properties["crlatti:numEmendRitiratiEsame"]?exists>${commissione.properties["crlatti:numEmendRitiratiEsame"]}<#else></#if>",
   							"respintiEsameCommissioni": "<#if commissione.properties["crlatti:numEmendRespintiEsame"]?exists>${commissione.properties["crlatti:numEmendRespintiEsame"]}<#else></#if>",
							"noteEmendamentiEsameCommissioni": "<#if commissione.properties["crlatti:noteEmendamentiEsame"]?exists>${commissione.properties["crlatti:noteEmendamentiEsame"]}<#else></#if>"
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