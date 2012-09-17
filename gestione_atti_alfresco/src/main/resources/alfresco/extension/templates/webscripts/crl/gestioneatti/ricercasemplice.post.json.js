<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");

var tipoAtto = atto.get("tipoAtto");
var legislatura = atto.get("legislatura");
var stato = atto.get("stato");
var numeroAtto = atto.get("numeroAtto");
var numeroProtocollo = atto.get("numeroProtocollo");
var tipoIniziativa = atto.get("tipoIniziativa");
var numeroDcr = atto.get("numeroDcr");
var primoFirmatario = atto.get("primoFirmatario");
var oggetto = atto.get("oggetto");
var firmatario = atto.get("firmatario");
var dataIniziativaDa = atto.get("dataIniziativaDa");
var dataIniziativaA = atto.get("dataIniziativaA");

var luceneQuery = "PATH:\"/app:company_home/cm:CRL//*\" ";

var tipoAttoString = ""+tipoAtto+"";
var type = "crlatti:atto";

if(checkIsNotNull(tipoAttoString)){
	if(tipoAttoString == "PDL") {
		type = "crlatti:attoPdl";	
	} else if(tipoAttoString == "INP") {
		type = "crlatti:attoInp";	
	} else if(tipoAttoString == "PAR") {
		type = "crlatti:attoPar";	
	} else if(tipoAttoString == "PDA") {
		type = "crlatti:attoPda";	
	} else if(tipoAttoString == "PLP") {
		type = "crlatti:attoPlp";	
	} else if(tipoAttoString == "PRE") {
		type = "crlatti:attoPre";	
	} else if(tipoAttoString == "REF") {
		type = "crlatti:attoRef";	
	} else if(tipoAttoString == "REL") {
		type = "crlatti:attoRel";	
	} else if(tipoAttoString == "EAC") {
		type = "crlatti:attoEac";	
	} else if(tipoAttoString == "REF") {
		type = "crlatti:attoRef";	
	} else if(tipoAttoString == "DOC") {
		type = "crlatti:attoDoc";	
	}
}

luceneQuery += "AND TYPE:\""+type+"\"";

if(checkIsNotNull(legislatura)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:legislatura:\""+legislatura+"\"";
}

if(checkIsNotNull(stato)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:statoAtto:\""+stato+"\"";
}

if(checkIsNotNull(numeroAtto)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroAtto:\""+numeroAtto+"\"";
}

if(checkIsNotNull(numeroProtocollo)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroProtocollo:\""+numeroProtocollo+"\"";
}

if(checkIsNotNull(tipoIniziativa)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:tipoIniziativa:\""+tipoIniziativa+"\"";
}

if(checkIsNotNull(numeroDcr)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroDcr:\""+numeroDcr+"\"";
}

if(checkIsNotNull(primoFirmatario)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:primoFirmatario:\""+primoFirmatario+"\"";
}

if(checkIsNotNull(oggetto)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:oggetto:\""+oggetto+"\"";
}

if(checkIsNotNull(firmatario)){
	verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:firmatari:\""+firmatario+"\"";
}


if(checkIsNotNull(dataIniziativaDa)
		&& checkIsNotNull(dataIniziativaA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataIniziativa:["+dataIniziativaDa+"T00:00:00 TO "+dataIniziativaA+"T00:00:00]";
} else if(checkIsNotNull(dataIniziativaDa)
		&& (dataIniziativaA==null || dataIniziativaA==undefined || dataIniziativaA=="")) {
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataIniziativa:["+dataIniziativaDa+"T00:00:00 TO MAX]";
} else if(checkIsNotNull(dataIniziativaA)
		&& (dataIniziativaDa==null || dataIniziativaDa==undefined || dataIniziativaDa=="")){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataIniziativa:[MIN TO "+dataIniziativaA+"T00:00:00]";
}

var attiResults = search.luceneSearch(luceneQuery);
model.atti = attiResults;