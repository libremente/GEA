<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var provenienza = args.provenienza;
var sedute = null;

var gestioneSedutePath = "/app:company_home"+
"/cm:"+search.ISO9075Encode("CRL")+
"/cm:"+search.ISO9075Encode("Gestione Atti")+
"/cm:"+search.ISO9075Encode("Sedute")+
"/cm:"+search.ISO9075Encode(provenienza);

var luceneQuery = "PATH:\""+gestioneSedutePath+"//*\"" + " AND TYPE:\"crlatti:sedutaODG\"";


var seduteResults = search.luceneSearch(luceneQuery);

model.sedute = seduteResults;