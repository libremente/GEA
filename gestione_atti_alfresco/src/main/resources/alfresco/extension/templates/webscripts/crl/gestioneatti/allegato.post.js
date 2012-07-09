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
	var allegatoNode = null;
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
		"/cm:"+search.ISO9075Encode("Allegati") +
		"/cm:"+search.ISO9075Encode(filename) + "\"";
		
		var allegatoResults = search.luceneSearch(luceneQuery);
		
		if(allegatoResults!=null && allegatoResults.length>0){
			var allegatoEsistente = allegatoResults[0];
			allegatoEsistente.properties.content.write(content);
			allegatoEsistente.properties.content.setEncoding("UTF-8");
			allegatoEsistente.properties.content.guessMimetype(filename);
			allegatoEsistente.save();
			allegatoNode = allegatoEsistente;
		}
	
		if(allegatoNode == null) {
			var allegatiFolderNode = null;
			var luceneQueryAllegatiFolder = "PATH:\"/app:company_home" +
			"/cm:"+search.ISO9075Encode("CRL") +
			"/cm:"+search.ISO9075Encode("Gestione Atti") +
			"/cm:"+search.ISO9075Encode(legislatura) +
			"/cm:"+search.ISO9075Encode(anno) + 
			"/cm:"+search.ISO9075Encode(mese) + 
			"/cm:"+search.ISO9075Encode(attoFolderNode.name) +
			"/cm:"+search.ISO9075Encode("Allegati") + "\"";
			
			var allegatiFolderResults = search.luceneSearch(luceneQueryAllegatiFolder);
			if(allegatiFolderResults!=null && allegatiFolderResults.length>0){
				allegatiFolderNode = allegatiFolderResults[0];
			} else {
				allegatiFolderNode = attoFolderNode.createFolder("Allegati");
			}
			
			//creazione binario
			allegatoNode = allegatiFolderNode.createFile(filename);
			allegatoNode.properties.content.write(content);
			allegatoNode.properties.content.setEncoding("UTF-8");
			allegatoNode.properties.content.guessMimetype(filename);
			allegatoNode.save();
		}
		
	} else {
		status.code = 400;
		status.message = "legislatura dell' atto non valorizzata";
		status.redirect = true;
	}
	
	model.allegato = allegatoNode;	
}