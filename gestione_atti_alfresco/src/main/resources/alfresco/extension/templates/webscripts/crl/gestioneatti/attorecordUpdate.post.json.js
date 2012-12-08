<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var attoRecord = json.get("attoRecord");

var id = attoRecord.get("id");

var nome = filterParam(attoRecord.get("nome"));
var mimetype = filterParam(attoRecord.get("mimetype"));
var tipologia = filterParam(attoRecord.get("tipologia"));
var pubblico = filterParam(attoRecord.get("pubblico"));
var provenienza = filterParam(attoRecord.get("provenienza"));

if(checkIsNotNull(id)){
	
	var attoRecordNode = utils.getNodeFromString(id);
	
	attoRecordNode.name = nome;
	attoRecordNode.properties["crlatti:tipologia"] = tipologia;
	attoRecordNode.properties["crlatti:pubblico"] = pubblico;
	attoRecordNode.properties["crlatti:provenienza"] = provenienza;
	
	attoRecordNode.save();
	model.attoRecord = attoRecordNode;	
	
}else{
	status.code = 400;
	status.message = "id atto record non valorizzato";
	status.redirect = true;
}