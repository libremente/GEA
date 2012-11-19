<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var statoAtto = atto.get("stato");
var commissioneUtente = json.get("target").get("commissione");
var passaggio = json.get("target").get("passaggio");

// selezione della commissione e del passaggio corrente
var commissioneTarget = getCommissioneTarget(json, passaggio, commissioneUtente);

var tipoVotazione = filterParam(commissioneTarget.get("quorumEsameCommissioni"));
var esitoVotazione = filterParam(commissioneTarget.get("esitoVotazione"));
var dataVotazione = filterParam(commissioneTarget.get("dataSedutaCommissione"));
var statoCommissione = filterParam(commissioneTarget.get("stato"));
var ruoloCommissione = filterParam(commissioneTarget.get("ruolo"));


if(checkIsNotNull(id)){
	var attoNode = utils.getNodeFromString(id);

	// gestione passaggi
	var passaggiXPathQuery = "*[@cm:name='Passaggi']";
	var passaggiFolderNode = attoNode.childrenByXPath(passaggiXPathQuery)[0];
	
	var passaggioXPathQuery = "*[@cm:name='"+passaggio+"']";
	var passaggioFolderNode = passaggiFolderNode.childrenByXPath(passaggioXPathQuery)[0];
	
	var commissioneFolderNode = null;
	
	//cerco la commissione di riferimento dell'utente corrente
	var commissioniXPathQuery = "*[@cm:name='Commissioni']";
	var commissioniFolderNode = passaggioFolderNode.childrenByXPath(commissioniXPathQuery)[0];
	
	if(checkIsNotNull(commissioneUtente)){
		var commissioneUtenteXPathQuery = "*[@cm:name='"+commissioneUtente+"']";
		var commissioneUtenteResults = commissioniFolderNode.childrenByXPath(commissioneUtenteXPathQuery);
		if(commissioneUtenteResults!=null && commissioneUtenteResults.length>0){
			commissioneFolderNode = commissioneUtenteResults[0];
			
			commissioneFolderNode.properties["crlatti:tipoVotazioneCommissione"] = tipoVotazione;
			commissioneFolderNode.properties["crlatti:esitoVotazioneCommissione"] = esitoVotazione;
			
			// data votazione
			if(checkIsNotNull(dataVotazione)) {
				var dataVotazioneSplitted = dataVotazione.split("-");
				var dataVotazioneParsed = new Date(dataVotazioneSplitted[0],dataVotazioneSplitted[1]-1,dataVotazioneSplitted[2]);
				commissioneFolderNode.properties["crlatti:dataVotazioneCommissione"] = dataVotazioneParsed;
			}else{
				commissioneFolderNode.properties["crlatti:dataVotazioneCommissione"] = null;
			}
			
			

			// passaggio di stato per la commissione: votato in Commissione
			commissioneFolderNode.properties["crlatti:statoCommissione"] = statoCommissione;
			commissioneFolderNode.save();
			
			// passaggio di stato per l'atto in caso di commissione Referente
			if(canChangeStatoAtto(ruoloCommissione)) {
				attoNode.properties["crlatti:statoAtto"] = statoAtto;
				attoNode.save();
			}
			
		} else {
			status.code = 400;
			status.message = "commissione utente non trovata";
			status.redirect = true;
		}
	} 
	
	
	model.atto = attoNode;
	model.commissione = commissioneFolderNode;
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}