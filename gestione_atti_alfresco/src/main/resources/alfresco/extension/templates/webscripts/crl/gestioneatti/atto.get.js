<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var id = args.id;
var attoFolderNode = null;
if(checkIsNotNull(id)){
	attoFolderNode = utils.getNodeFromString(id);
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}
model.atto = attoFolderNode;