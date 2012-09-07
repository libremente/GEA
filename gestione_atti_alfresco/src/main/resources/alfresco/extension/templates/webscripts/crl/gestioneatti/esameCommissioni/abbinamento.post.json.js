<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var jsonAbbinamento = filterParam(json.get("abbinamento"));
var idAtto = filterParam(jsonAbbinamento.get("idAtto"));
var idAttoAbbinato = filterParam(jsonAbbinamento.get("idAttoAbbinato"));
var tipoTesto = filterParam(jsonAbbinamento.get("tipoTesto"));
var dataAbbinamento = filterParam(jsonAbbinamento.get("dataAbbinamento"));
var dataDisabbinamento = filterParam(jsonAbbinamento.get("dataDisabbinamento"));

var abbinamentoNode = null;

if(checkIsNotNull(jsonAbbinamento)
		&& checkIsNotNull(idAtto)
		&& checkIsNotNull(idAttoAbbinato)){
	
	//reperimento dei nodi degli atti coinvolti nell'abbinamento
	var attoFolderNode = utils.getNodeFromString(idAtto);
	var attoAbbinatoFolderNode = utils.getNodeFromString(idAttoAbbinato);
	
	var xPathQueryAbbinamenti = "*[@cm:name='Abbinamenti']";
	var abbinamentiFolderNode = attoFolderNode.childrenByXPath(xPathQueryAbbinamenti)[0];
	
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
	abbinamentoNode.save();
	
	if(creaAssociazione){
		abbinamentoNode.createAssociation(attoAbbinatoFolderNode,"crlatti:attoAssociatoAbbinamento");
	}
	
} else {
	status.code = 400;
	status.message = "abbinamento non valorizzato correttamente: idAtto e idAttoAbbinato sono obbligatori";
	status.redirect = true;
}

model.abbinamento = abbinamentoNode;