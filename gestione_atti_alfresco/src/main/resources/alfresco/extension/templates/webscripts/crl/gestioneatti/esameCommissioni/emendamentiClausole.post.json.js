<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = filterParam(json.get("atto"));
var id = filterParam(atto.get("id"));
var statoAtto = filterParam(atto.get("stato"));
var commissioneUtente = json.get("target").get("commissione");
var passaggio = json.get("target").get("passaggio");

//selezione della commissione e del passaggio corrente
var commissioneTarget = getCommissioneTarget(json, passaggio, commissioneUtente);


var numEmendPresentatiMaggiorEsameCommissioni = filterNumericParam(commissioneTarget.get("numEmendPresentatiMaggiorEsameCommissioni"));
var numEmendPresentatiMinorEsameCommissioni = filterNumericParam(commissioneTarget.get("numEmendPresentatiMinorEsameCommissioni"));
var numEmendPresentatiGiuntaEsameCommissioni = filterNumericParam(commissioneTarget.get("numEmendPresentatiGiuntaEsameCommissioni"));
var numEmendPresentatiMistoEsameCommissioni = filterNumericParam(commissioneTarget.get("numEmendPresentatiMistoEsameCommissioni"));

var numEmendApprovatiMaggiorEsameCommissioni = filterNumericParam(commissioneTarget.get("numEmendApprovatiMaggiorEsameCommissioni"));
var numEmendApprovatiMinorEsameCommissioni = filterNumericParam(commissioneTarget.get("numEmendApprovatiMinorEsameCommissioni"));
var numEmendApprovatiGiuntaEsameCommissioni = filterNumericParam(commissioneTarget.get("numEmendApprovatiGiuntaEsameCommissioni"));
var numEmendApprovatiMistoEsameCommissioni = filterNumericParam(commissioneTarget.get("numEmendApprovatiMistoEsameCommissioni"));

var nonAmmissibiliEsameCommissioni = filterNumericParam(commissioneTarget.get("nonAmmissibiliEsameCommissioni"));
var decadutiEsameCommissioni = filterNumericParam(commissioneTarget.get("decadutiEsameCommissioni"));
var ritiratiEsameCommissioni = filterNumericParam(commissioneTarget.get("ritiratiEsameCommissioni"));
var respintiEsameCommissioni = filterNumericParam(commissioneTarget.get("respintiEsameCommissioni"));
var noteEmendamentiEsameCommissioni = filterParam(commissioneTarget.get("noteEmendamentiEsameCommissioni"));

var dataPresaInCaricoProposta = filterParam(commissioneTarget.get("dataPresaInCaricoProposta"));
var dataIntesa = filterParam(commissioneTarget.get("dataIntesa"));
var esitoVotazioneIntesa = filterParam(commissioneTarget.get("esitoVotazioneIntesa"));
var noteClausolaValutativa = filterParam(commissioneTarget.get("noteClausolaValutativa"));


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
	
	
			commissioneFolderNode.properties["crlatti:numEmendPresentatiMaggiorEsame"] = numEmendPresentatiMaggiorEsameCommissioni;
		
			commissioneFolderNode.properties["crlatti:numEmendPresentatiMaggiorEsame"] = numEmendPresentatiMaggiorEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendPresentatiMinorEsame"] = numEmendPresentatiMinorEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendPresentatiGiuntaEsame"] = numEmendPresentatiGiuntaEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendPresentatiMistoEsame"] = numEmendPresentatiMistoEsameCommissioni;
			
			commissioneFolderNode.properties["crlatti:numEmendApprovatiMaggiorEsame"] = numEmendApprovatiMaggiorEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendApprovatiMinorEsame"] = numEmendApprovatiMinorEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendApprovatiGiuntaEsame"] = numEmendApprovatiGiuntaEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendApprovatiMistoEsame"] = numEmendApprovatiMistoEsameCommissioni;
			
			commissioneFolderNode.properties["crlatti:numEmendNonAmmissibiliEsame"] = nonAmmissibiliEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendDecadutiEsame"] = decadutiEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendRitiratiEsame"] = ritiratiEsameCommissioni;
			commissioneFolderNode.properties["crlatti:numEmendRespintiEsame"] = respintiEsameCommissioni;
			commissioneFolderNode.properties["crlatti:noteEmendamentiEsame"] = noteEmendamentiEsameCommissioni;
	
			var dataPresaInCaricoPropostaParsed= null;
			if(checkIsNotNull(dataPresaInCaricoProposta)){
				var dataPresaInCaricoPropostaSplitted = dataPresaInCaricoProposta.split("-");
				dataPresaInCaricoPropostaParsed = new Date(dataPresaInCaricoPropostaSplitted[0],dataPresaInCaricoPropostaSplitted[1]-1,dataPresaInCaricoPropostaSplitted[2]);
			}
			
			var dataIntesaParsed= null;
			if(checkIsNotNull(dataIntesa)){
				var dataIntesaSplitted = dataIntesa.split("-");
				dataIntesaParsed = new Date(dataIntesaSplitted[0],dataIntesaSplitted[1]-1,dataIntesaSplitted[2]);
			}
	
			commissioneFolderNode.properties["crlatti:dataPresaInCaricoPropClausolaValutEsame"] = dataPresaInCaricoPropostaParsed;
			commissioneFolderNode.properties["crlatti:dataIntesaClausolaValutEsame"] = dataIntesaParsed;
			commissioneFolderNode.properties["crlatti:esitoVotazioneIntesaClausolaValutEsame"] = esitoVotazioneIntesa;
			commissioneFolderNode.properties["crlatti:noteClausolaValutativaEsame"] = noteClausolaValutativa;
	
			commissioneFolderNode.save();
	
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
	status.message = "id atto, passaggio e commissione non valorizzato";
	status.redirect = true;
}