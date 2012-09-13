<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var statoAtto = atto.get("stato");
var commissioni = atto.get("commissioni");

var commissioneUtente = json.get("target").get("commissione");

var dataFineLavoriComitato = "";

//prendo i valori delle propriet� dalla commissione target

var statoCommissione;

for(var i=0; i<commissioni.length(); i++) {
	var commissioneTemp = commissioni.get(i);
	
	if(""+commissioneTemp.get("descrizione")+"" == ""+commissioneUtente+"") {
		dataFineLavoriComitato = commissioneTemp.get("dataFineLavoriComitato");
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
			
			// stato della commissione
			commissioneFolderNode.properties["crlatti:statoCommissione"] = statoCommissione;	
			commissioneFolderNode.save();
		} else {
			status.code = 400;
			status.message = "commissione utente non trovata";
			status.redirect = true;
		}
	} 
	
	var comitatoXPathQuery = "*[@cm:name='ComitatoRistretto']";
	var comitatoFolderNode = commissioneFolderNode.childrenByXPath(comitatoXPathQuery)[0];
	
	// setting delle propriet� del comitato ristretto
	
	var dataFineLavoriComitatoParsed = null;
	if(checkIsNotNull(dataFineLavoriComitato)){
		var dataFineLavoriComitatoSplitted = dataFineLavoriComitato.split("-");
		dataFineLavoriComitatoParsed = new Date(dataFineLavoriComitatoSplitted[0],dataFineLavoriComitatoSplitted[1]-1,dataFineLavoriComitatoSplitted[2]);
	}
		
	comitatoFolderNode.properties["crlatti:dataFineLavoriCR"] = dataFineLavoriComitatoParsed;
	comitatoFolderNode.save();
	
	
	attoNode.properties["crlatti:statoAtto"] = statoAtto;
	attoNode.save();
	
	model.atto = attoNode;
	model.commissione = commissioneFolderNode;
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}