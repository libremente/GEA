var nodeRefAtto = "";
var filename = "";
var content = null;

for each (field in formdata.fields)
{
  if (field.name == "id")
  {
    nodeRefAtto = field.value;
  }
  else if (field.name == "file" && field.isFile)
  {
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
	var attoRecordNode = null;
	var testiXPathQuery = "*[@cm:name='Testi']";
	var recordXPathQuery = "*[@cm:name='"+filename+"']";
	
	var testiFolderNode = attoFolderNode.childrenByXPath(testiXPathQuery)[0];
	var attoResults = testiFolderNode.childrenByXPath(recordXPathQuery);
	
	if(attoResults!=null && attoResults.length>0){
	
		var attoEsistente = attoResults[0];
		attoEsistente.properties.content.write(content);
		attoEsistente.properties.content.setEncoding("UTF-8");
		attoEsistente.properties.content.guessMimetype(filename);
		attoEsistente.save();
		attoRecordNode = attoEsistente;
	
	} 
	
	if (attoRecordNode == null){
	
		// creazione binario
		attoRecordNode = testiFolderNode.createFile(filename);
		attoRecordNode.specializeType("crlatti:testo");
		attoRecordNode.properties.content.write(content);
		attoRecordNode.properties.content.setEncoding("UTF-8");
		attoRecordNode.properties.content.guessMimetype(filename);
		attoRecordNode.save();
	
	}
	model.attoRecord = attoRecordNode;	
}