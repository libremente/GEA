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

//campi ricerca avanzata

//pannello sinistra
var tipoChiusura = atto.get("tipoChiusura");
var esitoVotoComRef = atto.get("esitoVotoComRef");
var esitoVotoAula = atto.get("esitoVotoAula");
var commReferente = atto.get("commReferente");
var commConsultiva = atto.get("commConsultiva");
var redigente = atto.get("redigente");
var deliberante = atto.get("deliberante");
var numeroLcr = atto.get("numeroLcr");
var numeroLr = atto.get("numeroLr");
var anno = atto.get("anno");
var abbinamento = atto.get("abbinamento");
var stralcio = atto.get("stralcio");

//pannello destra
var dataPubblicazioneDa = atto.get("dataPubblicazioneDa");
var dataPubblicazioneA = atto.get("dataPubblicazioneA");

var dataSedutaScDa = atto.get("dataSedutaScDa");
var dataSedutaScA = atto.get("dataSedutaScA");

var dataSedutaCommDa = atto.get("dataSedutaCommDa");
var dataSedutaCommA = atto.get("dataSedutaCommA");

var dataSedutaAulaDa = atto.get("dataSedutaAulaDa");
var dataSedutaAulaA = atto.get("dataSedutaAulaA");

var relatore = atto.get("relatore");
var organismoStatutario = atto.get("organismoStatutario");
var soggettoConsultato = atto.get("soggettoConsultato");
var emendato = atto.get("emendato");
var sospeso = atto.get("sospeso");


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

//dataIniziativa
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

//ricerca avanzata

//pannello sinistro
if(checkIsNotNull(tipoChiusura)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:tipoChiusura:\""+tipoChiusura+"\"";
}

if(checkIsNotNull(esitoVotoComRef)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:esitoVotoComRef:\""+esitoVotoComRef+"\"";
}

if(checkIsNotNull(esitoVotoAula)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:esitoVotoAula:\""+esitoVotoAula+"\"";
}

if(checkIsNotNull(commReferente)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:commReferente:\""+commReferente+"\"";
}

if(checkIsNotNull(commConsultiva)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:commConsultiva:\""+commConsultiva+"\"";
}

if(checkIsNotNull(redigente)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:redigente:\""+redigente+"\"";
}

if(checkIsNotNull(deliberante)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:deliberante:\""+deliberante+"\"";
}

if(checkIsNotNull(numeroLcr)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroLcr:\""+numeroLcr+"\"";
}

if(checkIsNotNull(numeroLr)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroLr:\""+numeroLr+"\"";
}

if(checkIsNotNull(anno)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:anno:\""+anno+"\"";
}

if(checkIsNotNull(abbinamento)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:abbinamento:\""+abbinamento+"\"";
}

if(checkIsNotNull(stralcio)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:stralcio:\""+stralcio+"\"";
}

//pannello destro

//dataPubblicazione
if(checkIsNotNull(dataPubblicazioneDa)
		&& checkIsNotNull(dataPubblicazioneA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataPubblicazione:["+dataPubblicazioneDa+"T00:00:00 TO "+dataPubblicazioneA+"T00:00:00]";
} else if(checkIsNotNull(dataPubblicazioneDa)
		&& (dataPubblicazioneA==null || dataPubblicazioneA==undefined || dataPubblicazioneA=="")) {
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataPubblicazione:["+dataPubblicazioneDa+"T00:00:00 TO MAX]";
} else if(checkIsNotNull(dataPubblicazioneA)
		&& (dataPubblicazioneDa==null || dataPubblicazioneDa==undefined || dataPubblicazioneDa=="")){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataPubblicazione:[MIN TO "+dataPubblicazioneA+"T00:00:00]";
}

//dataSedutaSc
if(checkIsNotNull(dataSedutaScDa)
		&& checkIsNotNull(dataSedutaScA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaSc:["+dataSedutaScDa+"T00:00:00 TO "+dataSedutaScA+"T00:00:00]";
} else if(checkIsNotNull(dataSedutaScDa)
		&& (dataSedutaScA==null || dataSedutaScA==undefined || dataSedutaScA=="")) {
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaSc:["+dataSedutaScDa+"T00:00:00 TO MAX]";
} else if(checkIsNotNull(dataSedutaScA)
		&& (dataSedutaScDa==null || dataSedutaScDa==undefined || dataSedutaScDa=="")){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaSc:[MIN TO "+dataSedutaScA+"T00:00:00]";
}

//dataSedutaComm
if(checkIsNotNull(dataSedutaCommDa)
		&& checkIsNotNull(dataSedutaCommA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaComm:["+dataSedutaCommDa+"T00:00:00 TO "+dataSedutaCommA+"T00:00:00]";
} else if(checkIsNotNull(dataSedutaCommDa)
		&& (dataSedutaCommA==null || dataSedutaCommA==undefined || dataSedutaCommA=="")) {
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaComm:["+dataSedutaCommDa+"T00:00:00 TO MAX]";
} else if(checkIsNotNull(dataSedutaCommA)
		&& (dataSedutaCommDa==null || dataSedutaCommDa==undefined || dataSedutaCommDa=="")){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaComm:[MIN TO "+dataSedutaCommA+"T00:00:00]";
}

//dataSedutaAula
if(checkIsNotNull(dataSedutaAulaDa)
		&& checkIsNotNull(dataSedutaAulaA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaAula:["+dataSedutaAulaDa+"T00:00:00 TO "+dataSedutaAulaA+"T00:00:00]";
} else if(checkIsNotNull(dataSedutaAulaDa)
		&& (dataSedutaAulaA==null || dataSedutaAulaA==undefined || dataSedutaAulaA=="")) {
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaAula:["+dataSedutaAulaDa+"T00:00:00 TO MAX]";
} else if(checkIsNotNull(dataSedutaAulaA)
		&& (dataSedutaAulaDa==null || dataSedutaAulaDa==undefined || dataSedutaAulaDa=="")){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataSedutaAula:[MIN TO "+dataSedutaAulaA+"T00:00:00]";
}

if(checkIsNotNull(relatore)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:relatori:\""+relatore+"\"";
}

if(checkIsNotNull(organismoStatutario)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:organismiStatutari:\""+organismoStatutario+"\"";
}

if(checkIsNotNull(soggettoConsultato)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:soggettoConsultato:\""+soggettoConsultato+"\"";
}

if(checkIsNotNull(emendato)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:emendato:\""+emendato+"\"";
}

if(checkIsNotNull(sospeso)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:sospeso:\""+sospeso+"\"";
}

var attiResults = search.luceneSearch(luceneQuery);
model.atti = attiResults;