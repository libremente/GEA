var atto = json.get("atto");
var legislatura = atto.get("legislatura");
var tipologia = atto.get("tipologia");
var numeroAtto = atto.get("numeroAtto");
var tipoAtto = atto.get("tipoAtto");
var dataImportazione = new Date();
var mese = dataImportazione.getMonth() + 1;
var anno = dataImportazione.getFullYear();

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

//gestione dei tipi atto
var nodeType = "crlatti:atto";
if(tipoAtto=="PDL"){
	nodeType = "crlatti:attoPdl";
}

//verifica esistenza del folder dell'atto
var attoPath = mesePath + "/cm:" + search.ISO9075Encode(numeroAtto);
var attoLuceneQuery = "PATH:\""+attoPath+"\"";
var attoResults = search.luceneSearch(attoLuceneQuery);
if(attoResults!=null && attoResults.length>0){
	//atto presente
	status.code = 500;
	status.message = "atto numero "+numeroAtto+" già presente nel repository";
	status.redirect = true;
} else {
	//creazione del nodo
	var attoFolderNode = meseFolderNode.createNode(numeroAtto,nodeType);
	attoFolderNode.properties["crlatti:legislatura"] = legislatura;
	attoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
	attoFolderNode.properties["crlatti:tipologia"] = tipologia;
	attoFolderNode.properties["crlatti:anno"] = anno;
	attoFolderNode.save();
	model.atto = attoFolderNode;
}