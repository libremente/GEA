<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var id = args.id;
var firmatari = null;

if(checkIsNotNull(id)){
	var attoFolderNode = utils.getNodeFromString(id);
	var children = attoFolderNode.getChildAssocsByType("cm:folder");
	var firmatariFolderNode = null;
	for each (child in children){
		if(child.name=="Firmatari"){
			firmatariFolderNode = child;
		}
	}
	firmatari = firmatariFolderNode.getChildAssocsByType("crlatti:firmatario");
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}
model.firmatari = firmatari;