//json.get("atto").get(0).get("legislatura");
//Legislatura1

var atto = json.get("atto").get(0);
var legislatura = atto.get("legislatura");
var tipologia = atto.get("tipologia");
var anno = atto.get("anno");
var numeroAtto = atto.get("numeroAtto");
var tipoAtto = atto.get("tipoAtto");
var dataImportazione = new Date();
var mese = dataImportazione.getMonth() + 1;

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

//creazione del nodo
var attoFolderNode = meseFolderNode.createNode(numeroAtto,nodeType);
attoFolderNode.properties["crlatti:legislatura"] = legislatura;
attoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
attoFolderNode.properties["crlatti:tipologia"] = tipologia;
attoFolderNode.properties["crlatti:anno"] = anno;
attoFolderNode.save();

model.attoFolderNode = attoFolderNode;