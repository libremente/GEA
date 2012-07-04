var nomeLegislatura = args.nomeLegislatura;
var anniLegislatura = null;

if(nomeLegislatura!=undefined
		&& nomeLegislatura!=null
		&& nomeLegislatura!="") {
	
	var legislaturaPath = "/app:company_home" +
	"/cm:"+search.ISO9075Encode("CRL") +
	"/cm:"+search.ISO9075Encode("Gestione Atti") +
	"/cm:"+search.ISO9075Encode("Anagrafica") +
	"/cm:"+search.ISO9075Encode("Legislature") + 
	"/cm:"+search.ISO9075Encode(nomeLegislatura);

	var luceneQuery = "PATH:\""+legislaturaPath+"\"";
	var legislaturaResults = search.luceneSearch(luceneQuery);
	
	if(legislaturaResults!=null
			&& legislaturaResults.length>0) {
		var legislatura = legislaturaResults[0];
		anniLegislatura = legislatura.properties["crlatti:anni"];
	}
	
} else {
	status.code = 400;
	status.message = "nomeLegislatura non valorizzato";
	status.redirect = true;
}

model.nomeLegislatura = nomeLegislatura;
model.anniLegislatura = anniLegislatura;