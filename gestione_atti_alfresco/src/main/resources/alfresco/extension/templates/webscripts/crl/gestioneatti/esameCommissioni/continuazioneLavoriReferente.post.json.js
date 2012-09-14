<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var statoAtto = atto.get("stato");
var commissioni = atto.get("commissioni");

var commissioneUtente = json.get("target").get("commissione");

var dataSedutaContinuazioneInReferente = "";
var motivazioniContinuazioneInReferente = "";
var statoCommissione = "";


// prendo i valori delle proprietˆ dalla commissione target

for(var i=0; i<commissioni.length(); i++) {
	var commissioneTemp = commissioni.get(i);
	
	if(""+commissioneTemp.get("descrizione")+"" == ""+commissioneUtente+"") {
		dataSedutaContinuazioneInReferente = commissioneTemp.get("dataSedutaContinuazioneInReferente");
		motivazioniContinuazioneInReferente = commissioneTemp.get("motivazioniContinuazioneInReferente");
		statoCommissione = commissioneTemp.get("stato");
	}
	
}

if(checkIsNotNull(id)){
	var attoNode = utils.getNodeFromString(id);
	var commissioneFolderNode = null;
	
	//cerco la commissione di riferimento dell'utente corrente
	var commissioniXPathQuery = "*[@cm:name='Commissioni']";
	var commissioniFolderNode = attoNode.childrenByXPath(commissioniXPathQuery)[0];
	
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
			
			// passaggio di stato per l'atto: votato in Commissione
			attoNode.properties["crlatti:statoAtto"] = statoAtto;
			attoNode.save();
			
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