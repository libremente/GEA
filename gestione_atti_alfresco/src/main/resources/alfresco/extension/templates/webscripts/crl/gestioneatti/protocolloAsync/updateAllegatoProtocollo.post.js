<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

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
		protocolloLogger.error(status.message);
		status.redirect = true;
	} else if(filename == ""){
		status.code = 400;
		status.message = "filename allegato non valorizzato";
		protocolloLogger.error(status.message);
		status.redirect = true;
	} else if(content==null){ 
		status.code = 400;
		status.message = "content allegato non valorizzato";
		protocolloLogger.error(status.message);
		status.redirect = true;
	} else {

/*
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
		
		protocolloLogger.info("Allegato modificato correttamente. Atto idProtocottoAtto:"+idProtocolloAtto);
                
*/               
                
                var allegatoLuceneQuery = "TYPE:\"crlatti:allegato\" " +
				"AND @crlatti\\:idProtocollo:\""+idProtocolloAllegato+"\"";
		
		var allegatoResults = search.luceneSearch(allegatoLuceneQuery);
		if(allegatoResults!=null && allegatoResults.length>0){
			var allegatoEsistenteFolderNode = allegatoResults[0];
                        
                        if(allegatoEsistenteFolderNode.hasAspect("crlatti:importatoDaProtocollo")) {
                            // Allegato in cartella import
                            idProtocolloAtto = allegatoEsistenteFolderNode.properties["crlatti:idProtocolloAtto"];
                        } else {
                            // Allegato in cartella atto
                            var allegatiFolderNode = allegatoEsistenteFolderNode.parent;
                            var attoFolderNode = allegatiFolderNode.parent;
                            idProtocolloAtto = attoFolderNode.properties["crlatti:idProtocollo"];
                        }
                        
		}
                 
                var importProtocolloPath = 
			"/app:company_home" +
			"/cm:"+search.ISO9075Encode("Import")+
			"/cm:"+search.ISO9075Encode("Gestione Atti")+
			"/cm:"+search.ISO9075Encode("Protocollo")+
			"/cm:"+search.ISO9075Encode("Allegati");
		
		var importLuceneQuery = "PATH:\""+importProtocolloPath+"\"";
		var importFolderNode = search.luceneSearch(importLuceneQuery)[0];
		
                filename = "" + makeTimestamp() + "_" + filename;
		//verifica esistenza allegato
//		var allegatoResults = importFolderNode.childrenByXPath("*[@cm:name='"+filename+"']");
		var allegatoNode = null;
//		if(allegatoResults!=null && allegatoResults.length>0){
//			allegatoNode = allegatoResults[0];	
//		} else {
		    allegatoNode = importFolderNode.createNode(filename,"crlatti:allegato");
//		}
		
		allegatoNode.properties["crlatti:idProtocollo"] = idProtocolloAllegato;
		allegatoNode.properties.content.write(content);
		allegatoNode.properties.content.setEncoding("UTF-8");
		allegatoNode.properties.content.guessMimetype(filename);
		allegatoNode.save();
		
		allegatoNode.addAspect("crlatti:importatoDaProtocollo");
		allegatoNode.properties["crlatti:idProtocolloAtto"] = idProtocolloAtto;
		allegatoNode.save();	
		
		protocolloLogger.info("Allegato inserito correttamente. Atto idProtocottoAtto:"+idProtocolloAtto+" nome file:"+filename);

	}

} else {
	status.code = 401;
	status.message = "utenza non abilitata ad accedere a questo servizio";
	protocolloLogger.error(status.message);
	status.redirect = true;
}