<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var jsonAbbinamento = filterParam(json.get("abbinamento"));
var idAtto = filterParam(jsonAbbinamento.get("idAtto"));
var idAttoAbbinato = filterParam(jsonAbbinamento.get("idAttoAbbinato"));
var tipoTesto = filterParam(jsonAbbinamento.get("tipoTesto"));
var note = filterParam(jsonAbbinamento.get("note"));
var dataAbbinamento = filterParam(jsonAbbinamento.get("dataAbbinamento"));
var dataDisabbinamento = filterParam(jsonAbbinamento.get("dataDisabbinamento"));

var passaggio = json.get("target").get("passaggio");

var abbinamentoNode = null;

if(checkIsNotNull(jsonAbbinamento)
		&& checkIsNotNull(idAtto)
		&& checkIsNotNull(idAttoAbbinato)
		&& checkIsNotNull(passaggio)){
	
	
	
	//reperimento dei nodi degli atti coinvolti nell'abbinamento
	var attoFolderNode = utils.getNodeFromString(idAtto);
	var attoAbbinatoFolderNode = utils.getNodeFromString(idAttoAbbinato);
	
	// gestione passaggi
	var passaggiXPathQuery = "*[@cm:name='Passaggi']";
	var passaggiFolderNode = attoFolderNode.childrenByXPath(passaggiXPathQuery)[0];
	
	var passaggioXPathQuery = "*[@cm:name='"+passaggio+"']";
	var passaggioFolderNode = passaggiFolderNode.childrenByXPath(passaggioXPathQuery)[0];
	
	
	var xPathQueryAbbinamenti = "*[@cm:name='Abbinamenti']";
	var abbinamentiFolderNode = passaggioFolderNode.childrenByXPath(xPathQueryAbbinamenti)[0];
	
	//verifica esistenza abbinamento preesistente
	var esisteAbbinamentoXPathQuery = "*[@cm:name='"+attoAbbinatoFolderNode.name+"']";
	var abbinamentoEsistenteResults = abbinamentiFolderNode.childrenByXPath(esisteAbbinamentoXPathQuery);
	
	var creaAssociazione = true;
	if(abbinamentoEsistenteResults!=null && abbinamentoEsistenteResults.length>0){
		abbinamentoNode = abbinamentoEsistenteResults[0];
		creaAssociazione = false;
	} else {
		abbinamentoNode = abbinamentiFolderNode.createNode(attoAbbinatoFolderNode.name,"crlatti:abbinamento");
	}

	//aggiornamento dei metadati
	var dataAbbinamentoParsed = null;
	if(checkIsNotNull(dataAbbinamento)){
		var dataAbbinamentoSplitted = dataAbbinamento.split("-");
		dataAbbinamentoParsed = new Date(dataAbbinamentoSplitted[0],dataAbbinamentoSplitted[1]-1,dataAbbinamentoSplitted[2]);
	}
	
	var dataDisabbinamentoParsed = null;
	if(checkIsNotNull(dataDisabbinamento)){
		var dataDisabbinamentoSplitted = dataDisabbinamento.split("-");
		dataDisabbinamentoParsed = new Date(dataDisabbinamentoSplitted[0],dataDisabbinamentoSplitted[1]-1,dataDisabbinamentoSplitted[2]);
	}
	
	abbinamentoNode.properties["crlatti:dataAbbinamento"] = dataAbbinamentoParsed;
	abbinamentoNode.properties["crlatti:dataDisabbinamento"] = dataDisabbinamentoParsed;
	abbinamentoNode.properties["crlatti:tipoTestoAbbinamento"] = tipoTesto;
	abbinamentoNode.properties["crlatti:noteAbbinamento"] = note;
	abbinamentoNode.save();
	
	if(creaAssociazione){
		abbinamentoNode.createAssociation(attoAbbinatoFolderNode,"crlatti:attoAssociatoAbbinamento");
	}
	
} else {
	status.code = 400;
	status.message = "abbinamento non valorizzato correttamente: idAtto, idAttoAbbinato e passaggio sono obbligatori";
	status.redirect = true;
}

model.abbinamento = abbinamentoNode;