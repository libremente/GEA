<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">


var seduta = json.get("seduta");
var dataSeduta = seduta.get("dataSeduta");
var numVerbale = seduta.get("numVerbale");
var note = seduta.get("note");
var links = seduta.get("links");

var provenienza = json.get("target").get("provenienza");


var gestioneSedutePath = "/app:company_home"+
"/cm:"+search.ISO9075Encode("CRL")+
"/cm:"+search.ISO9075Encode("Gestione Atti")+
"/cm:"+search.ISO9075Encode("Sedute");


if(checkIsNotNull(provenienza)){
	
	var dataSedutaSplitted = dataSeduta.split("-");

	var anno = dataSedutaSplitted[0]
	var mese = dataSedutaSplitted[1];


	//creazione spazio per la provenienza (commissione o aula)
	var provenienzaPath = gestioneSedutePath + "/cm:"+search.ISO9075Encode(provenienza);
	var provenienzaLuceneQuery = "PATH:\""+provenienzaPath+"\"";
	var provenienzaResults = search.luceneSearch(provenienzaLuceneQuery);

	var provenienzaFolderNode = null;
	if(provenienzaResults!=null && provenienzaResults.length>0){
		provenienzaFolderNode = provenienzaResults[0];
	} else {
		var gestioneSeduteLuceneQuery = "PATH:\""+gestioneSedutePath+"\"";
		var gestioneSeduteFolderNode = search.luceneSearch(gestioneSeduteLuceneQuery)[0];
		provenienzaFolderNode = gestioneSeduteFolderNode.createFolder(provenienza);
		
		// setting dei permessi sul nodo in base alla provenienza
		provenienzaFolderNode.setPermission("Coordinator", "GROUP_"+provenienza);
	}

	//creazione spazio anno
	var annoPath = provenienzaPath + "/cm:" + search.ISO9075Encode(anno);
	var annoLuceneQuery = "PATH:\""+annoPath+"\"";
	var annoResults = search.luceneSearch(annoLuceneQuery);
	var annoFolderNode = null;
	if(annoResults!=null && annoResults.length>0){
		annoFolderNode = annoResults[0];
	} else {
		annoFolderNode = provenienzaFolderNode.createFolder(anno);
	}

	//creazione spazio mese
	var mesePath = annoPath + "/cm:" + search.ISO9075Encode(mese);
	var meseLuceneQuery = "PATH:\""+mesePath+"\"";
	var meseResults = search.luceneSearch(meseLuceneQuery);
	var meseFolderNode = null;
	if(meseResults!=null && meseResults.length>0){
		meseFolderNode = meseResults[0];
	} else {
		meseFolderNode = annoFolderNode.createFolder(mese);
	}


	//verifica esistenza del folder della seduta
	var sedutaPath = mesePath + "/cm:" + search.ISO9075Encode(dataSeduta);
	var sedutaLuceneQuery = "PATH:\""+sedutaPath+"\"";
	var sedutaResults = search.luceneSearch(sedutaLuceneQuery);
	if(sedutaResults!=null && sedutaResults.length>0){
		//seduta presente
		status.code = 500;
		status.message = "seduta in data "+dataSeduta+" per l'organo "+provenienza+" gia' presente nel repository";
		status.redirect = true;
	} else {
		//creazione del nodo
		var sedutaSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Seduta\"";
		var sedutaSpaceTemplateNode = search.luceneSearch(sedutaSpaceTemplateQuery)[0];
		var sedutaFolderNode = sedutaSpaceTemplateNode.copy(meseFolderNode,true);
		sedutaFolderNode.name = dataSeduta;
		
		var dataSedutaSplitted = dataSeduta.split("-");
		var dataSedutaParsed = new Date(dataSedutaSplitted[0],dataSedutaSplitted[1]-1,dataSedutaSplitted[2]);
		
		sedutaFolderNode.properties["crlatti:dataSedutaSedutaODG"] = dataSedutaParsed;
		sedutaFolderNode.properties["crlatti:numVerbaleSedutaODG"] = numVerbale;
		sedutaFolderNode.properties["crlatti:noteSedutaODG"] = note;
		
		sedutaFolderNode.save();
		
		
		
		//links
		var linksFolderXPathQuery = "*[@cm:name='Links']";
		var linksFolderNode = sedutaFolderNode.childrenByXPath(linksFolderXPathQuery)[0];
		
		for (var i=0; i<links.length(); i++){
			var link = links.get(i).get("link");
			var descrizione = link.get("descrizione");
			var indirizzo = link.get("indirizzo");
			
			var existLinkXPathQuery = "*[@cm:name='"+descrizione+"']";
			var existLinkResults = linksFolderNode.childrenByXPath(existLinkXPathQuery);
			
			var linkNode = null;
			if(existLinkResults!=null && existLinkResults.length>0){
				linkNode = existLinkResults[0];
			} else {
				linkNode = linksFolderNode.createNode(descrizione,"crlatti:link");
			}
			
			linkNode.properties["crlatti:indirizzoCollegamento"] = indirizzo;
			linkNode.save();
		}
		
		model.seduta = sedutaFolderNode;
			
	}
	
}else{
	
	status.code = 400;
	status.message = "id seduta e provenienza non valorizzati";
	status.redirect = true;
}
		