<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var statoAtto = atto.get("stato");
var commissioneUtente = json.get("target").get("commissione");
var passaggio = json.get("target").get("passaggio");

// selezione della commissione e del passaggio corrente
var commissioneTarget = getCommissioneTarget(json, passaggio, commissioneUtente);

var dataSedutaContinuazioneInReferente = filterParam(commissioneTarget.get("dataSedutaContinuazioneInReferente"));
var motivazioniContinuazioneInReferente = filterParam(commissioneTarget.get("motivazioniContinuazioneInReferente"));
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
			
			commissioneFolderNode.properties["crlatti:motivazioniContinuazioneInReferente"] = motivazioniContinuazioneInReferente;
			
			
			if(checkIsNotNull(dataSedutaContinuazioneInReferente)) {
				var dataSedutaContinuazioneInReferenteSplitted = dataSedutaContinuazioneInReferente.split("-");
				var dataSedutaContinuazioneInReferenteParsed = new Date(dataSedutaContinuazioneInReferenteSplitted[0],dataSedutaContinuazioneInReferenteSplitted[1]-1,dataSedutaContinuazioneInReferenteSplitted[2]);
				commissioneFolderNode.properties["crlatti:dataSedutaContinuazioneInReferente"] = dataSedutaContinuazioneInReferenteParsed;
			}
		
			

			// passaggio di stato per la commissione: votato in Commissione
			commissioneFolderNode.properties["crlatti:statoCommissione"] = statoCommissione;
			commissioneFolderNode.save();
			
			// passaggio di stato per l'atto in caso di commissione Referente
			if(""+ruoloCommissione+""=="Referente") {
				attoNode.properties["crlatti:statoAtto"] = statoAtto;
				attoNode.save();
			}
			
		} else {
			status.code = 400;
			status.message = "commissione utente non valorizzata";
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