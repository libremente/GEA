<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">
var idAtto = args.id;
var abbinamenti = null;
if(checkIsNotNull(idAtto)){
	var attoFolderNode = utils.getNodeFromString(idAtto);
	var abbinamentiFolderXpathQuery = "*[@cm:name='Abbinamenti']";
	var abbinamentiFolderNode = attoFolderNode.childrenByXPath(abbinamentiFolderXpathQuery)[0];
	abbinamenti = abbinamentiFolderNode.getChildAssocsByType("crlatti:abbinamento");
} else {
	status.code = 400;
	status.message = "id atto o tipologiaAllegati non valorizzato";
	status.redirect = true;
}
model.abbinamenti = abbinamenti;