<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

//Regole da impostare con uno scope attivo solo su crlatti:atto

var legislatura = document.properties["crlatti:legislatura"];
var numeroAtto = document.properties["crlatti:numeroAtto"];
var idProtocollo = document.properties["crlatti:idProtocollo"];

var tipologia = document.properties["crlatti:tipologia"];
var numeroProtocollo = document.properties["crlatti:numeroProtocollo"];
var numeroRepertorio = document.properties["crlatti:numeroRepertorio"];
var dataRepertorio = document.properties["crlatti:dataRepertorio"];
var classificazione = document.properties["crlatti:classificazione"];
var oggetto = document.properties["crlatti:oggetto"];

var tipoIniziativa = document.properties["crlatti:tipoIniziativa"];
var dataIniziativa = document.properties["crlatti:dataIniziativa"];
var descrizioneIniziativa = document.properties["crlatti:descrizioneIniziativa"];

var numeroDgr = document.properties["crlatti:numeroDgr"];
var dataDgr = document.properties["crlatti:dataDgr"];

var assegnazione = document.properties["crlatti:assegnazione"];
var firmatari = document.properties["crlatti:firmatari"];

var dataImportazione = new Date();
var mese = dataImportazione.getMonth() + 1;
var anno = dataImportazione.getFullYear();

if(checkIsNotNull(legislatura)
		&& checkIsNotNull(numeroAtto)
		&& checkIsNotNull(idProtocollo)){
	
	var gestioneAttiPath = "/app:company_home"+
	"/cm:"+search.ISO9075Encode("CRL")+
	"/cm:"+search.ISO9075Encode("Gestione Atti");
	
	//creazione dello spazio legislatura
	var legislaturaPath = gestioneAttiPath + "/cm:"+search.ISO9075Encode(legislatura);
	var legislaturaLuceneQuery = "PATH:\""+legislaturaPath+"\"";
	var legislaturaResults = search.luceneSearch(legislaturaLuceneQuery);
	
	var legislaturaFolderNode = null;
	if(legislaturaResults!=null && legislaturaResults.length>0){
		legislaturaFolderNode = legislaturaResults[0];
	} else {
		var gestioneAttiLuceneQuery = "PATH:\""+gestioneAttiPath+"\"";
		var gestioneAttiFolderNode = search.luceneSearch(gestioneAttiLuceneQuery)[0];
		legislaturaFolderNode = gestioneAttiFolderNode.createFolder(legislatura);
	}
	
	//creazione spazio anno
	var annoPath = legislaturaPath + "/cm:" + search.ISO9075Encode(anno);
	var annoLuceneQuery = "PATH:\""+annoPath+"\"";
	var annoResults = search.luceneSearch(annoLuceneQuery);
	var annoFolderNode = null;
	if(annoResults!=null && annoResults.length>0){
		annoFolderNode = annoResults[0];
	} else {
		annoFolderNode = legislaturaFolderNode.createFolder(anno);
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
	
	//verifica esistenza del folder dell'atto
	var attoPath = mesePath + "/cm:" + search.ISO9075Encode(numeroAtto);
	var attoLuceneQuery = "PATH:\""+attoPath+"\"";
	var attoResults = search.luceneSearch(attoLuceneQuery);
	
	var esisteAttoLuceneQuery = "TYPE:\"crlatti:atto\" AND @crlatti\\:idProtocollo:\""+idProtocollo+"\" AND @cm\\:name:\""+numeroAtto+"\"";
	
	var attoFolderNode = null;
	if(attoResults!=null && attoResults.length>0){
		//atto presente
		attoFolderNode = attoResults[0];
		attoFolderNode.name = numeroAtto;
		attoFolderNode.properties["crlatti:legislatura"] = legislatura;
		attoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
		attoFolderNode.properties["crlatti:tipologia"] = tipologia;
		attoFolderNode.properties["crlatti:anno"] = anno;
		attoFolderNode.properties["crlatti:idProtocollo"] = idProtocollo;
		attoFolderNode.properties["crlatti:numeroProtocollo"] = numeroProtocollo;
		attoFolderNode.properties["crlatti:numeroRepertorio"] = numeroRepertorio;
		attoFolderNode.properties["crlatti:classificazione"] = classificazione;
		attoFolderNode.properties["crlatti:oggetto"] = oggetto;
		attoFolderNode.properties["crlatti:tipoIniziativa"] = tipoIniziativa;
		attoFolderNode.properties["crlatti:descrizioneIniziativa"] = descrizioneIniziativa;
		attoFolderNode.properties["crlatti:assegnazione"] = assegnazione;
		attoFolderNode.save();
		document.remove();
		
	} else {
		//creazione del nodo del nuovo atto
		document.move(meseFolderNode);
	}
	
}