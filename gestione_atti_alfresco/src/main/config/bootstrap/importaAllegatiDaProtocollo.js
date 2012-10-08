//Script da attivare solo su una regola con scope con aspect crlatti:importatoDaProtocollo

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
			
			document.remove();
			
		} else {
			document.move(allegatiFolderNode);
			document.removeAspect("crlatti:importatoDaProtocollo");
		}
	}
}