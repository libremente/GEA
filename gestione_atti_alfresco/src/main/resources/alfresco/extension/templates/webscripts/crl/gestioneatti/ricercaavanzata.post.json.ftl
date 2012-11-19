<#escape x as jsonUtils.encodeJSONString(x)>
{  
   "List" : [
	   <#list atti as atto>
	   { 
   	  		"atto" : 
		   {
		    "id" : "<#if atto.id?exists>${atto.id}<#else></#if>",
			"tipo" : "<#if atto.tipo?exists>${atto.tipo}<#else></#if>",
			"numeroAtto" : "<#if atto.numeroAtto?exists>${atto.numeroAtto}<#else></#if>",
			"oggetto" : "<#if atto.oggetto?exists>${atto.oggetto}<#else></#if>",
			"primoFirmatario" : "<#if atto.primoFirmatario?exists>${atto.primoFirmatario}<#else></#if>",
			"dataPresentazione" : "<#if atto.dataPresentazione?exists>${atto.dataPresentazione?string("yyyy-MM-dd")}<#else></#if>",
			"stato" : "<#if atto.stato?exists>${atto.stato}<#else></#if>",
			"elencoFirmatari" : "<#if atto.elencoFirmatari?exists>${atto.elencoFirmatari}<#else></#if>",
			"tipoIniziativa" : "<#if atto.tipoIniziativa?exists>${atto.tipoIniziativa}<#else></#if>",
			"tipoChiusura" : "<#if atto.tipoChiusura?exists>${atto.tipoChiusura}<#else></#if>",
			"commissioniConsultive" :  "<#if atto.commissioniConsultive?exists>${atto.commissioniConsultive}<#else></#if>",
			"commissioniNonConsultive" : "<#if atto.commissioniNonConsultive?exists>${atto.commissioniNonConsultive}<#else></#if>",							
			"elencoAbbinamenti" : "<#if atto.elencoAbbinamenti?exists>${atto.elencoAbbinamenti}<#else></#if>",
			
			"dataAssegnazione" : "<#if atto.dataAssegnazione?exists>${atto.dataAssegnazione?string("yyyy-MM-dd")}<#else></#if>",
			"relatore" : "<#if atto.relatore?exists>${atto.relatore}<#else></#if>",
			"dataNominaRelatore" : "<#if atto.dataNominaRelatore?exists>${atto.dataNominaRelatore?string("yyyy-MM-dd")}<#else></#if>",
			"esitoVotazioneCommissioneReferente" : "<#if atto.esitoVotazioneCommissioneReferente?exists>${atto.esitoVotazioneCommissioneReferente}<#else></#if>",
			"dataVotazioneCommissione" : "<#if atto.dataVotazioneCommissione?exists>${atto.dataVotazioneCommissione?string("yyyy-MM-dd")}<#else></#if>",
			
			"dataScadenza" : "<#if atto.dataScadenza?exists>${atto.dataScadenza?string("yyyy-MM-dd")}<#else></#if>",
			"dataRichiestaIscrizioneAula" : "<#if atto.dataRichiestaIscrizioneAula?exists>${atto.dataRichiestaIscrizioneAula?string("yyyy-MM-dd")}<#else></#if>",
			"esitoVotazioneAula" : "<#if atto.esitoVotazioneAula?exists>${atto.esitoVotazioneAula}<#else></#if>",
			"dataVotazioneAula" : "<#if atto.dataVotazioneAula?exists>${atto.dataVotazioneAula?string("yyyy-MM-dd")}<#else></#if>",
			"numeroDcr" : "<#if atto.numeroDcr?exists>${atto.numeroDcr}<#else></#if>",
			"numeroLcr" : "<#if atto.numeroLcr?exists>${atto.numeroLcr}<#else></#if>",
			"numeroPubblicazioneBURL" : "<#if atto.numeroPubblicazioneBURL?exists>${atto.numeroPubblicazioneBURL}<#else></#if>",
			"dataPubblicazioneBURL" : "<#if atto.dataPubblicazioneBURL?exists>${atto.dataPubblicazioneBURL?string("yyyy-MM-dd")}<#else></#if>",
			"numeroLr" : "<#if atto.numeroLr?exists>${atto.numeroLr}<#else></#if>",
			"dataLR" : "<#if atto.dataLR?exists>${atto.dataLR?string("yyyy-MM-dd")}<#else></#if>",
			"notePresentazioneAssegnazione" : "<#if atto.notePresentazioneAssegnazione?exists>${atto.notePresentazioneAssegnazione}<#else></#if>"

		   }
	   }
	   <#if atto_has_next>,</#if>
	   </#list>
   ]
}
</#escape>

		
		