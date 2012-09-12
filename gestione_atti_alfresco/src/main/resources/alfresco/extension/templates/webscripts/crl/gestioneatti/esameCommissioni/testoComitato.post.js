<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var nodeRefAtto = "";
var tipologia = "";
var pubblico = false;
var filename = "";
var dataSeduta = "";
var commissione = "";
var content = null;

for each (field in formdata.fields) {
  if (field.name == "id") {
    nodeRefAtto = field.value;
  } else if(field.name == "tipologia"){
	tipologia = field.value;
  } else if(field.name == "pubblico"){
	pubblico = field.value;
  } else if(field.name == "commissione"){
	commissione = field.value;
  } else if(field.name == "dataSeduta"){
	dataSeduta = field.value;
  } else if (field.name == "file" && field.isFile) {
    filename = field.filename;
    content = field.content;
  }
}

if(nodeRefAtto == ""){
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
} else {
	var attoFolderNode = utils.getNodeFromString(nodeRefAtto);
	var allegatoNode = null;
	var allegatiXpathQuery = "*[@cm:name='Allegati']";
	var allegatiSpace = attoFolderNode.childrenByXPath(allegatiXpathQuery)[0];
		
	var allegatoXpathQuery = "*[@cm:name='"+filename+"']";
	var allegatoResults = allegatiSpace.childrenByXPath(allegatoXpathQuery);
	
	if(allegatoResults!=null && allegatoResults.length>0){
		var allegatoEsistente = allegatoResults[0];
		allegatoEsistente.properties.content.write(content);
		allegatoEsistente.properties.content.setEncoding("UTF-8");
		allegatoEsistente.properties.content.guessMimetype(filename);
		allegatoEsistente.save();
		allegatoNode = allegatoEsistente;
	}

	if(allegatoNode == null) {
		// creazione binario
		allegatoNode = allegatiSpace.createFile(filename);
		allegatoNode.specializeType("crlatti:testoComitatoRistretto");
		allegatoNode.properties["crlatti:tipologia"] = tipologia;
		allegatoNode.properties["crlatti:pubblico"] = pubblico;
		
		var dataSedutaParsed = null;
		if(checkIsNotNull(dataSeduta)){
			var dataSedutaSplitted = dataSeduta.split("-");
			dataSedutaParsed = new Date(dataSedutaSplitted[0],dataSedutaSplitted[1]-1,dataSedutaSplitted[2]);
			allegatoNode.properties["crlatti:dataSedutaTestoCR"] = dataSedutaParsed;
		}

		allegatoNode.properties["crlatti:commissioneCR"] = commissione;
		
		allegatoNode.properties.content.write(content);
		allegatoNode.properties.content.setEncoding("UTF-8");
		allegatoNode.properties.content.guessMimetype(filename);
		allegatoNode.save();
	}
	
	model.allegato = allegatoNode;	
}