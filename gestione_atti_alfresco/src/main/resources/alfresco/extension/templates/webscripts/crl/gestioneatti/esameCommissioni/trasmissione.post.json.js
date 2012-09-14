<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var statoAtto = atto.get("stato");
var commissioni = atto.get("commissioni");

var commissioneUtente = json.get("target").get("commissione");

var passaggioDirettoInAula = false;
var dataRichiestaIscrizioneAula = "";
var dataTrasmissione = "";
var statoCommissione = "";


// prendo i valori delle proprietˆ dalla commissione target

for(var i=0; i<commissioni.length(); i++) {
	var commissioneTemp = commissioni.get(i);
	
	if(""+commissioneTemp.get("descrizione")+"" == ""+commissioneUtente+"") {
		passaggioDirettoInAula = commissioneTemp.get("passaggioDirettoInAula");
		dataRichiestaIscrizioneAula = commissioneTemp.get("dataRichiestaIscrizioneAula");
		dataTrasmissione = commissioneTemp.get("dataTrasmissione");
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
			
			commissioneFolderNode.properties["crlatti:passaggioDirettoInAulaCommissione"] = passaggioDirettoInAula;
			
			// data trasmissione
			if(checkIsNotNull(dataTrasmissione)) {
				var dataTrasmissioneSplitted = dataTrasmissione.split("-");
				var dataTrasmissioneParsed = new Date(dataTrasmissioneSplitted[0],dataTrasmissioneSplitted[1]-1,dataTrasmissioneSplitted[2]);
				commissioneFolderNode.properties["crlatti:dataTrasmissioneCommissione"] = dataTrasmissioneParsed;
			}
			
			// data trasmissione
			if(checkIsNotNull(dataRichiestaIscrizioneAula)) {
				var dataRichiestaIscrizioneAulaSplitted = dataRichiestaIscrizioneAula.split("-");
				var dataRichiestaIscrizioneAulaParsed = new Date(dataRichiestaIscrizioneAulaSplitted[0],dataRichiestaIscrizioneAulaSplitted[1]-1,dataRichiestaIscrizioneAulaSplitted[2]);
				commissioneFolderNode.properties["crlatti:dataRichiestaIscrizioneAulaCommissione"] = dataRichiestaIscrizioneAulaParsed;
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