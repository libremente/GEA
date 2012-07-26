<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">
var idAtto = args.idAtto;
var tipologiaAllegati = args.tipologiaAllegati;
var allegati = null;
if(checkIsNotNull(idAtto) && checkIsNotNull(tipologiaAllegati)){
	var attoFolderNode = utils.getNodeFromString(idAtto);
	var children = attoFolderNode.getChildAssocsByType("cm:folder");
	var allegatiFolderXpathQuery = "*[@cm:name='Allegati']";
	var allegatiFolderNode = attoFolderNode.childrenByXPath(allegatiFolderXpathQuery)[0];
	var allegatiDiUnaTipologiaXPathQuery = "*[@crlatti:tipologiaAllegato='"+tipologiaAllegati+"']";
	allegati = allegatiFolderNode.childrenByXPath(allegatiDiUnaTipologiaXPathQuery);
} else {
	status.code = 400;
	status.message = "id atto o tipologiaAllegati non valorizzato";
	status.redirect = true;
}
model.allegati = allegati;