var atto = json.get("atto");
var id = atto.get("id");
var stato = atto.get("stato");
var dataPresaInCarico = atto.get("dataPresaInCarico");

if(atto!=null
		&& id!=null
		&& stato!=null
		&& dataPresaInCarico!=null) {
	
	var dataPresaInCaricoSplitted = dataPresaInCarico.split("-");
	var dataPresaInCaricoParsed = new Date(dataPresaInCaricoSplitted[0],dataPresaInCaricoSplitted[1]-1,dataPresaInCaricoSplitted[2]);
	
	var attoFolderNode = utils.getNodeFromString(id);
	attoFolderNode.properties["crlatti:statoAtto"] = stato;
	attoFolderNode.properties["crlatti:dataPresaInCarico"] = dataPresaInCaricoParsed;
	attoFolderNode.save();
	
} else {
	status.code = 500;
	status.message = "atto, id, stato e dataPresaInCarico sono obbligatori";
	status.redirect = true;
}