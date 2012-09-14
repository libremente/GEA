<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = filterParam(json.get("atto"));
var id = filterParam(atto.get("id"));
var statoAtto = filterParam(atto.get("stato"));

var numEmendPresentatiMaggiorEsameCommissioni = filterParam(atto.get("numEmendPresentatiMaggiorEsameCommissioni"));
var numEmendPresentatiMinorEsameCommissioni = filterParam(atto.get("numEmendPresentatiMinorEsameCommissioni"));
var numEmendPresentatiGiuntaEsameCommissioni = filterParam(atto.get("numEmendPresentatiGiuntaEsameCommissioni"));
var numEmendPresentatiMistoEsameCommissioni = filterParam(atto.get("numEmendPresentatiMistoEsameCommissioni"));

var numEmendApprovatiMaggiorEsameCommissioni = filterParam(atto.get("numEmendApprovatiMaggiorEsameCommissioni"));
var numEmendApprovatiMinorEsameCommissioni = filterParam(atto.get("numEmendApprovatiMinorEsameCommissioni"));
var numEmendApprovatiGiuntaEsameCommissioni = filterParam(atto.get("numEmendApprovatiGiuntaEsameCommissioni"));
var numEmendApprovatiMistoEsameCommissioni = filterParam(atto.get("numEmendApprovatiMistoEsameCommissioni"));

var nonAmmissibiliEsameCommissioni = filterParam(atto.get("nonAmmissibiliEsameCommissioni"));
var decadutiEsameCommissioni = filterParam(atto.get("decadutiEsameCommissioni"));
var ritiratiEsameCommissioni = filterParam(atto.get("ritiratiEsameCommissioni"));
var respintiEsameCommissioni = filterParam(atto.get("respintiEsameCommissioni"));
var noteEmendamentiEsameCommissioni = filterParam(atto.get("noteEmendamentiEsameCommissioni"));

var dataPresaInCaricoProposta = filterParam(atto.get("dataPresaInCaricoProposta"));
var dataIntesa = filterParam(atto.get("dataIntesa"));
var esitoVotazioneIntesa = filterParam(atto.get("esitoVotazioneIntesa"));
var noteClausolaValutativa = filterParam(atto.get("noteClausolaValutativa"));


if(checkIsNotNull(id)){
	
	var attoNode = utils.getNodeFromString(id);
	
	attoNode.properties["crlatti:numEmendPresentatiMaggiorEsameCommissioni"] = numEmendPresentatiMaggiorEsameCommissioni;

	attoNode.properties["crlatti:numEmendPresentatiMaggiorEsameCommissioni"] = numEmendPresentatiMaggiorEsameCommissioni;
	attoNode.properties["crlatti:numEmendPresentatiMinorEsameCommissioni"] = numEmendPresentatiMinorEsameCommissioni;
	attoNode.properties["crlatti:numEmendPresentatiGiuntaEsameCommissioni"] = numEmendPresentatiGiuntaEsameCommissioni;
	attoNode.properties["crlatti:numEmendPresentatiMistoEsameCommissioni"] = numEmendPresentatiMistoEsameCommissioni;
	
	attoNode.properties["crlatti:numEmendApprovatiMaggiorEsameCommissioni"] = numEmendApprovatiMaggiorEsameCommissioni;
	attoNode.properties["crlatti:numEmendApprovatiMinorEsameCommissioni"] = numEmendApprovatiMinorEsameCommissioni;
	attoNode.properties["crlatti:numEmendApprovatiGiuntaEsameCommissioni"] = numEmendApprovatiGiuntaEsameCommissioni;
	attoNode.properties["crlatti:numEmendApprovatiMistoEsameCommissioni"] = numEmendApprovatiMistoEsameCommissioni;
	
	attoNode.properties["crlatti:numEmendNonAmmissibiliEsameCommissioni"] = nonAmmissibiliEsameCommissioni;
	attoNode.properties["crlatti:numEmendDecadutiEsameCommissioni"] = decadutiEsameCommissioni;
	attoNode.properties["crlatti:numEmendRitiratiEsameCommissioni"] = ritiratiEsameCommissioni;
	attoNode.properties["crlatti:numEmendRespintiEsameCommissioni"] = respintiEsameCommissioni;
	attoNode.properties["crlatti:noteEmendamentiEsameCommissioni"] = noteEmendamentiEsameCommissioni;
	
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
	
	attoNode.properties["crlatti:dataPresaInCaricoPropClausolaValutEsameCommissioni"] = dataPresaInCaricoPropostaParsed;
	attoNode.properties["crlatti:dataIntesaClausolaValutEsameCommissioni"] = dataIntesaParsed;
	attoNode.properties["crlatti:esitoVotazioneIntesaClausolaValutEsameCommissioni"] = esitoVotazioneIntesa;
	attoNode.properties["crlatti:noteClausolaValutativaEsameCommissioni"] = noteClausolaValutativa;
	

	
	
	attoNode.save();
	
	model.atto = attoNode;
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}