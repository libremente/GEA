<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var id = args.id;

if(checkIsNotNull(id)){
	var firmatarioNode = utils.getNodeFromString(id);
	firmatarioNode.remove();
} else {
	status.code = 400;
	status.message = "id non valorizzato";
	status.redirect = true;
}