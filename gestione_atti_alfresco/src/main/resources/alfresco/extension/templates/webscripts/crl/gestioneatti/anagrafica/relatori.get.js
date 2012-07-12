var relatoriPath = "/app:company_home" +
	"/cm:"+search.ISO9075Encode("CRL") +
	"/cm:"+search.ISO9075Encode("Gestione Atti") +
	"/cm:"+search.ISO9075Encode("Anagrafica") +
	"/cm:"+search.ISO9075Encode("Relatori") + "/*";
var luceneQuery = "PATH:\""+relatoriPath+"\"";
var relatoriResults = search.luceneSearch(luceneQuery);
model.relatori = relatoriResults;