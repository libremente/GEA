//Script da attivare solo su una regola con scope con aspect crlatti:importatoDaProtocollo

crlUtility.sudo(importaAllegato, "admin");


function importaAllegato(){

	var idProtocolloAtto = document.properties["crlatti:idProtocolloAtto"];
	var idProtocolloAllegato = document.properties["crlatti:idProtocollo"];
	var filename = document.name;
	
	if(idProtocolloAtto == ""){
		
	} else if(idProtocolloAllegato == ""){
		
	} else if(filename == ""){
		
	} else {
		
		var importProtocolloPath = 
			"/app:company_home" +
			"/cm:"+search.ISO9075Encode("CRL")+
			"/cm:"+search.ISO9075Encode("Gestione Atti")+
			"/cm:"+search.ISO9075Encode("Import protocollo")+
			"/cm:"+search.ISO9075Encode("Allegati");
		
		var importLuceneQuery = "PATH:\""+importProtocolloPath+"\"";
		var importFolderNode = search.luceneSearch(importLuceneQuery)[0];
		
		var attoLuceneQuery = "TYPE:\"crlatti:atto\" AND @crlatti\\:idProtocollo:\""+idProtocolloAtto+"\"";
		var attoResults = search.luceneSearch(attoLuceneQuery);
		
		if(attoResults!=null && attoResults.length>0){
			
			var attoFolderNode = attoResults[0];
			
			
			// controllo se l'atto è di tipo ORG o PDA UdP
			
			if(attoFolderNode.typeShort=="crlatti:attoOrg" || 
					(attoFolderNode.typeShort=="crlatti:attoPda" && attoFolderNode.properties["crlatti:tipoIniziativa"]=="05_ATTO DI INIZIATIVA UFFICIO PRESIDENZA") ){
				
				var testiFolderNode = attoFolderNode.childrenByXPath("*[@cm:name='Testi']")[0];
				
				var recordXPathQuery = "*[@cm:name='"+filename+"']";
				var testiResults = testiFolderNode.childrenByXPath(recordXPathQuery);
				
				var attoRecordNode = null;
				
				
				//verifica dell'esistenza di un testo con lo stesso nome dell'allegato
				if(testiResults!=null && testiResults.length>0){
					//esiste un testo con lo stesso nome dell'allegato che si sta per spostare: aggiornamento del solo contenuto
					attoRecordNode = testiResults[0];
				} else {
					//non esiste alcun testo con lo stesso nome dell'allegato
					// creazione binario
					attoRecordNode = testiFolderNode.createFile(filename);
					attoRecordNode.specializeType("crlatti:testo");
				}

				
				//prelevamento properties dall'allegato
				var tipologia = document.properties["crlatti:tipologia"];
				var pubblico = document.properties["crlatti:pubblico"];
				var provenienza = document.properties["crlatti:provenienza"];
				var content = document.properties.content;
				
				//scrittura properties all'interno del nodo testo
				attoRecordNode.properties["crlatti:tipologia"] = tipologia;
				attoRecordNode.properties["crlatti:pubblico"] = pubblico;
				attoRecordNode.properties["crlatti:provenienza"] = provenienza;
				attoRecordNode.properties.content.write(content);
				attoRecordNode.properties.content.setEncoding("UTF-8");
				attoRecordNode.properties.content.guessMimetype(filename);
				attoRecordNode.save();
				
				//rimozione del vecchio allegato che adesso è presente come testo dell'atto
				document.remove();
				
			}else{
				
				var allegatiFolderNode = attoFolderNode.childrenByXPath("*[@cm:name='Allegati']")[0];
				
				
				var allegatoResults = allegatiFolderNode.childrenByXPath("*[@cm:name='"+filename+"']");
				var allegatoNode = null;
				if(allegatoResults!=null && allegatoResults.length>0){
					allegatoNode = allegatoResults[0];
					allegatoNode.properties["crlatti:idProtocollo"] = idProtocolloAllegato;
					allegatoNode.properties.content.write(document.properties.content);
					allegatoNode.properties.content.setEncoding("UTF-8");
					allegatoNode.properties.content.guessMimetype(filename);
					allegatoNode.save();
					
					allegatoNode.addAspect("crlatti:importatoDaProtocollo");
					allegatoNode.properties["crlatti:idProtocolloAtto"] = idProtocolloAtto;
					allegatoNode.save();
					
					if(allegatoNode.hasAspect("crlatti:importatoDaProtocollo")){
						allegatoNode.removeAspect("crlatti:importatoDaProtocollo");
					}
					
					document.remove();
					
				} else {
					
					if(document.hasAspect("crlatti:importatoDaProtocollo")){
						document.removeAspect("crlatti:importatoDaProtocollo");
					}
					
					document.move(allegatiFolderNode);
				}
				
				
				
			}
			
			
			
			
		}
	}
}