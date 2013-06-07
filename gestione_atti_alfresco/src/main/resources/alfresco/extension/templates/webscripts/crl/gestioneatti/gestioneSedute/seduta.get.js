<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var dataSeduta = args.dataSeduta;
var legislatura = args.legislatura;

var luceneQuery = 
	    "TYPE:\"crlatti:sedutaODG\" " +
		"AND @crlatti\\:dataSedutaSedutaODG:\""+dataSeduta+"T00:00:00\" " +
		"AND @crlatti\\:legislaturaSedutaODG:\""+legislatura+"\"";

var sedutaResults = search.luceneSearch(luceneQuery);
var seduta = null;

if(sedutaResults!=null && sedutaResults.length>0){
	seduta = sedutaResults[0];
	model.seduta = seduta;
} else {
	status.code = 404;
	status.message = "Seduta con data "+dataSeduta+" e legislatura "+legislatura+" non trovata";
	status.redirect = true;
}