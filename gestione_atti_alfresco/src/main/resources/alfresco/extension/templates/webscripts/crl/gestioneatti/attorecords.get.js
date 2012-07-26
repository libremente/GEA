<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">
var id = args.id;
var records = null;
if(checkIsNotNull(id)){
	var attoFolderNode = utils.getNodeFromString(id);
	var records = attoFolderNode.getChildAssocsByType("cm:content");
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}
model.records = records;