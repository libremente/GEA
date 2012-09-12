<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var statoAtto = atto.get("stato");
var commissioni = atto.get("commissioni");

var commissioneUtente = json.get("target").get("commissione");

var dataPresaInCarico = "";
var dataScadenza = "";
var materia = "";
var statoCommissione = "";

// prendo i valori delle propriet� dalla commissione target

for(var i=0; i<commissioni.length(); i++) {
	var commissioneTemp = commissioni.get(i).get("commissione");
	
	if(""+commissioneTemp.get("descrizione")+"" == ""+commissioneUtente+"") {
		dataPresaInCarico = commissioneTemp.get("dataPresaInCarico");
		materia = commissioneTemp.get("materia");
		dataScadenza = commissioneTemp.get("dataScadenza");
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
			
			// presa in carico
			if(checkIsNotNull(dataPresaInCarico)) {
				var dataPresaInCaricoSplitted = dataPresaInCarico.split("-");
				var dataPresaInCaricoParsed = new Date(dataPresaInCaricoSplitted[0],dataPresaInCaricoSplitted[1]-1,dataPresaInCaricoSplitted[2]);
				commissioneFolderNode.properties["crlatti:dataPresaInCaricoCommissione"] = dataPresaInCaricoParsed;
			}
			
		
			if(checkIsNotNull(dataScadenza)) {
				var dataScadenzaSplitted = dataScadenza.split("-");
				var dataScadenzaParsed = new Date(dataScadenzaSplitted[0],dataScadenzaSplitted[1]-1,dataScadenzaSplitted[2]);
				commissioneFolderNode.properties["crlatti:dataScadenzaCommissione"] = dataScadenzaParsed;
			}
			
			commissioneFolderNode.properties["crlatti:materiaCommissione"] = materia;
			
			// passaggio di stato per la commissione: presa in carico
			commissioneFolderNode.properties["crlatti:statoCommissione"] = statoCommissione;
			commissioneFolderNode.save();
			
			// passaggio di stato per l'atto: presa in carico
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