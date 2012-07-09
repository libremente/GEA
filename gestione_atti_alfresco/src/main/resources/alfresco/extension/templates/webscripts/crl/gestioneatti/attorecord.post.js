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
	var legislatura = attoFolderNode.properties["crlatti:legislatura"];
	var dataPubblicazione = attoFolderNode.properties["crlatti:dataPubblicazione"];
	var anno = dataPubblicazione.getFullYear();
	var mese = dataPubblicazione.getMonth()+1;
	
	if(legislatura!=null && legislatura != "") {
		
		var luceneQuery = "PATH:\"/app:company_home" +
		"/cm:"+search.ISO9075Encode("CRL") +
		"/cm:"+search.ISO9075Encode("Gestione Atti") +
		"/cm:"+search.ISO9075Encode(legislatura) +
		"/cm:"+search.ISO9075Encode(anno) + 
		"/cm:"+search.ISO9075Encode(mese) + 
		"/cm:"+search.ISO9075Encode(attoFolderNode.name) +
		"/cm:"+search.ISO9075Encode(filename) + "\"";
		
		var attoResults = search.luceneSearch(luceneQuery);
		
		if(attoResults!=null && attoResults.length>0){
			var attoEsistente = attoResults[0];
			attoEsistente.properties.content.write(content);
			attoEsistente.properties.content.setEncoding("UTF-8");
			attoEsistente.properties.content.guessMimetype(filename);
			attoEsistente.save();
			attoRecordNode = attoEsistente;
		}
	
		if(attoRecordNode == null){
			//creazione binario
			attoRecordNode = attoFolderNode.createFile(filename);
			attoRecordNode.properties.content.write(content);
			attoRecordNode.properties.content.setEncoding("UTF-8");
			attoRecordNode.properties.content.guessMimetype(filename);
			attoRecordNode.save();
		}
		
	} else {
		status.code = 400;
		status.message = "legislatura dell' atto non valorizzata";
		status.redirect = true;
	}
	
	model.attoRecord = attoRecordNode;	
}