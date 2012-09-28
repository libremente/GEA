<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var statoAtto = atto.get("stato");
var passaggio = json.get("target").get("passaggio");

// selezione del passaggio corrente
var passaggioTarget = getPassaggioTarget(json, passaggio);


var numEmendPresentatiMaggiorEsame = filterNumericParam(passaggioTarget.get("aula").get("numEmendPresentatiMaggiorEsameAula"));
var numEmendPresentatiMinorEsame = filterNumericParam(passaggioTarget.get("aula").get("numEmendPresentatiMinorEsameAula"));
var numEmendPresentatiGiuntaEsame = filterNumericParam(passaggioTarget.get("aula").get("numEmendPresentatiGiuntaEsameAula"));
var numEmendPresentatiMistoEsame = filterNumericParam(passaggioTarget.get("aula").get("numEmendPresentatiMistoEsameAula"));

var numEmendApprovatiMaggiorEsame = filterNumericParam(passaggioTarget.get("aula").get("numEmendApprovatiMaggiorEsameAula"));
var numEmendApprovatiMinorEsame = filterNumericParam(passaggioTarget.get("aula").get("numEmendApprovatiMinorEsameAula"));
var numEmendApprovatiGiuntaEsame = filterNumericParam(passaggioTarget.get("aula").get("numEmendApprovatiGiuntaEsameAula"));
var numEmendApprovatiMistoEsame = filterNumericParam(passaggioTarget.get("aula").get("numEmendApprovatiMistoEsameAula"));

var nonAmmissibiliEsame = filterNumericParam(passaggioTarget.get("aula").get("nonAmmissibiliEsameAula"));
var decadutiEsame = filterNumericParam(passaggioTarget.get("aula").get("decadutiEsameAula"));
var ritiratiEsame = filterNumericParam(passaggioTarget.get("aula").get("ritiratiEsameAula"));
var respintiEsame  = filterNumericParam(passaggioTarget.get("aula").get("respintiEsameAula"));
var noteEmendamentiEsame = filterParam(passaggioTarget.get("aula").get("noteEmendamentiEsameAula"));


if(checkIsNotNull(id)
		&& checkIsNotNull(passaggio)){
	
	var attoNode = utils.getNodeFromString(id);
	
	// gestione passaggi
	var passaggiXPathQuery = "*[@cm:name='Passaggi']";
	var passaggiFolderNode = attoNode.childrenByXPath(passaggiXPathQuery)[0];
	
	var passaggioXPathQuery = "*[@cm:name='"+passaggio+"']";
	var passaggioFolderNode = passaggiFolderNode.childrenByXPath(passaggioXPathQuery)[0];
	
	var aulaXPathQuery = "*[@cm:name='Aula']";
	var aulaFolderNode = passaggioFolderNode.childrenByXPath(aulaXPathQuery)[0];
	

	aulaFolderNode.properties["crlatti:numEmendPresentatiMaggiorEsame"] = numEmendPresentatiMaggiorEsame;
	aulaFolderNode.properties["crlatti:numEmendPresentatiMinorEsame"] = numEmendPresentatiMinorEsame;
	aulaFolderNode.properties["crlatti:numEmendPresentatiGiuntaEsame"] = numEmendPresentatiGiuntaEsame;
	aulaFolderNode.properties["crlatti:numEmendPresentatiMistoEsame"] = numEmendPresentatiMistoEsame;
	
	aulaFolderNode.properties["crlatti:numEmendApprovatiMaggiorEsame"] = numEmendApprovatiMaggiorEsame;
	aulaFolderNode.properties["crlatti:numEmendApprovatiMinorEsame"] = numEmendApprovatiMinorEsame;
	aulaFolderNode.properties["crlatti:numEmendApprovatiGiuntaEsame"] = numEmendApprovatiGiuntaEsame;
	aulaFolderNode.properties["crlatti:numEmendApprovatiMistoEsame"] = numEmendApprovatiMistoEsame;
	
	aulaFolderNode.properties["crlatti:numEmendNonAmmissibiliEsame"] = nonAmmissibiliEsame;
	aulaFolderNode.properties["crlatti:numEmendDecadutiEsame"] = decadutiEsame;
	aulaFolderNode.properties["crlatti:numEmendRitiratiEsame"] = ritiratiEsame;
	aulaFolderNode.properties["crlatti:numEmendRespintiEsame"] = respintiEsame;
	aulaFolderNode.properties["crlatti:noteEmendamentiEsame"] = noteEmendamentiEsame;

	aulaFolderNode.save();

	
	model.atto = attoNode;
	model.aula = aulaFolderNode;
	
} else {
	status.code = 400;
	status.message = "id atto e passaggio non valorizzato";
	status.redirect = true;
}