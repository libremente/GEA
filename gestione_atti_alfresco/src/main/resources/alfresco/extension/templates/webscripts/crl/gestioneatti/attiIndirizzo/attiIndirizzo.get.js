<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var legislaturaCorrente = getLegislaturaCorrente();

var tipo = args.tipo;

if(tipo==null 
		|| tipo==undefined
		|| tipo==""){
	status.code = 400;
	status.message = "Parametro tipo non presente nella richiesta";
	status.redirect = true;
}

var gestioneAttiPath = "/app:company_home"+
"/cm:"+search.ISO9075Encode("CRL")+
"/cm:"+search.ISO9075Encode("Gestione Atti");

var legislaturaRepositoryPath = gestioneAttiPath + "/cm:"+search.ISO9075Encode(legislaturaCorrente);
var attoIndirizzoLuceneQuery = "PATH:\""+legislaturaRepositoryPath+"//*\"";
attoIndirizzoLuceneQuery += " AND TYPE:\"crlatti:attoIndirizzo\" AND @crlatti\\:tipoAttoIndirizzo:\""+tipo+"\"";

var attoIndirizzoResults = search.luceneSearch(attoIndirizzoLuceneQuery);

model.atti = attoIndirizzoResults;