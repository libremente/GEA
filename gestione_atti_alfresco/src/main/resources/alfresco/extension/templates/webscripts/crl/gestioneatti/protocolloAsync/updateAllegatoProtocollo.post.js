var username = person.properties.userName;
if(username=="protocollo" || username=="admin"){

	var idProtocolloAtto = "";
	var idProtocolloAllegato = "";
	var filename = "";
	var content = null;
	
	for each (field in formdata.fields) {
	  if (field.name == "idProtocolloAllegato") {
		    idProtocolloAllegato = field.value;
	  } else if (field.name == "file" && field.isFile) {
	    filename = field.filename;
	    content = field.content;
	  }
	}
	
	if(idProtocolloAllegato == ""){
		status.code = 400;
		status.message = "ID protocollo allegato non valorizzato";
		status.redirect = true;
	} else if(filename == ""){
		status.code = 400;
		status.message = "filename allegato non valorizzato";
		status.redirect = true;
	} else if(content==null){ 
		status.code = 400;
		status.message = "content allegato non valorizzato";
		status.redirect = true;
	} else {
		
		var importProtocolloPath = 
			"/app:company_home" +
			"/cm:"+search.ISO9075Encode("Import")+
			"/cm:"+search.ISO9075Encode("Gestione Atti")+
			"/cm:"+search.ISO9075Encode("Protocollo")+
			"/cm:"+search.ISO9075Encode("Allegati");
		
		var importLuceneQuery = "PATH:\""+importProtocolloPath+"\"";
		var importFolderNode = search.luceneSearch(importLuceneQuery)[0];
		
		var allegatoLuceneQuery = "TYPE:\"crlatti:allegato\" " +
				"AND @crlatti\\:idProtocollo:\""+idProtocolloAllegato+"\"";
		
		var allegatoResults = search.luceneSearch(allegatoLuceneQuery);
		if(allegatoResults!=null && allegatoResults.length>0){
			var allegatoEsistenteFolderNode = allegatoResults[0];
			var allegatiFolderNode = allegatoEsistenteFolderNode.parent;
			var attoFolderNode = allegatiFolderNode.parent;
			idProtocolloAtto = attoFolderNode.properties["crlatti:idProtocollo"];
		}
		
		var allegatoNode = null;
		if(allegatoResults!=null && allegatoResults.length>0){
			allegatoNode = allegatoResults[0];	
		} else {
		    allegatoNode = allegatiFolderNode.createNode(filename,"crlatti:allegato");
		}
		
		allegatoNode.name = filename;
		allegatoNode.properties["crlatti:idProtocollo"] = idProtocolloAllegato;
		allegatoNode.properties.content.write(content);
		allegatoNode.properties.content.setEncoding("UTF-8");
		allegatoNode.properties.content.guessMimetype(filename);
		allegatoNode.save();
		
		allegatoNode.addAspect("crlatti:importatoDaProtocollo");
		allegatoNode.properties["crlatti:idProtocolloAtto"] = idProtocolloAtto;
		allegatoNode.save();			
		
	}

} else {
	status.code = 401;
	status.message = "utenza non abilitata ad accedere a questo servizio";
	status.redirect = true;
}