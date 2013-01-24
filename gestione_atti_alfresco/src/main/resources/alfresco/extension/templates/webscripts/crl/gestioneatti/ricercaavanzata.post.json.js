<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

function creaLuceneQueryCommissioniRuoli(luceneQuery, commissione, ruoloCommissione){
	if(checkIsNotNull(commissione)){
		var commissioneString = ""+commissione+"";
		if(checkIsNotNull(ruoloCommissione)){
			var ruoloCommissioneString = ""+ruoloCommissione+"";
			if(ruoloCommissioneString=="Referente"){
				luceneQuery = verifyAND(luceneQuery);
				luceneQuery += "@crlatti\\:commReferente:\""+commissioneString+"\"";
			} else if(ruoloCommissioneString=="Co-Referente"){
				luceneQuery = verifyAND(luceneQuery);
				luceneQuery += "@crlatti\\:commCoreferente:\""+commissioneString+"\"";
			} else if(ruoloCommissioneString=="Consultiva"){
				luceneQuery = verifyAND(luceneQuery);
				luceneQuery += "@crlatti\\:commConsultiva:\""+commissioneString+"\"";
			} else if(ruoloCommissioneString=="Redigente"){
				luceneQuery = verifyAND(luceneQuery);
				luceneQuery += "@crlatti\\:commRedigente:\""+commissioneString+"\"";
			} else if(ruoloCommissioneString=="Deliberante"){
				luceneQuery = verifyAND(luceneQuery);
				luceneQuery += "@crlatti\\:commDeliberante:\""+commissioneString+"\"";
			}
		} else {
			luceneQuery = verifyAND(luceneQuery);
			luceneQuery += " ( " +
					"@crlatti\\:commReferente:\""+commissioneString+"\" OR "+
					"@crlatti\\:commCoreferente:\""+commissioneString+"\" OR "+
					"@crlatti\\:commConsultiva:\""+commissioneString+"\" OR "+
					"@crlatti\\:commRedigente:\""+commissioneString+"\" OR "+
					"@crlatti\\:commDeliberante:\""+commissioneString+"\" " +
					") ";
		}
	}
	return luceneQuery;
}

var atto = json.get("atto");

var tipoAtto = atto.get("tipoAtto");
var legislatura = atto.get("legislatura");
var stato = atto.get("stato");

var numeroAttoDa = atto.get("numeroAttoDa");
var numeroAttoA = atto.get("numeroAttoA");

var numeroProtocollo = atto.get("numeroProtocollo");
var tipoIniziativa = atto.get("tipoIniziativa");
var numeroDcr = atto.get("numeroDcr");
var primoFirmatario = atto.get("primoFirmatario");
var oggetto = atto.get("oggetto");
var firmatario = atto.get("firmatario");
var dataIniziativaDa = atto.get("dataIniziativaDa");
var dataIniziativaA = atto.get("dataIniziativaA");

var dataAssegnazioneDa = atto.get("dataAssegnazioneDa");
var dataAssegnazioneA = atto.get("dataAssegnazioneA");

var dataChiusuraDa = atto.get("dataChiusuraDa");
var dataChiusuraA = atto.get("dataChiusuraA");

var dataLr = atto.get("dataLR");

var commissione1 = atto.get("commissione1");
var commissione2 = atto.get("commissione2");
var commissione3 = atto.get("commissione3");

var ruoloCommissione1 = atto.get("ruoloCommissione1");
var ruoloCommissione2 = atto.get("ruoloCommissione2");
var ruoloCommissione3 = atto.get("ruoloCommissione3");

//pannello sinistra
var tipoChiusura = atto.get("tipoChiusura");
var esitoVotoComRef = atto.get("esitoVotoCommissioneReferente");
var esitoVotoAula = atto.get("esitoVotoAula");

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

var dataSedutaScDa = atto.get("dataSedutaSCDa");
var dataSedutaScA = atto.get("dataSedutaSCA");

var dataSedutaCommDa = atto.get("dataSedutaCommissioneDa");
var dataSedutaCommA = atto.get("dataSedutaCommissioneA");

var dataSedutaAulaDa = atto.get("dataSedutaAulaDa");
var dataSedutaAulaA = atto.get("dataSedutaAulaA");

var relatore = atto.get("relatore");
var organismoStatutario = atto.get("organismoStatutario");
var soggettoConsultato = atto.get("soggettoConsultato");
var emendato = atto.get("emendato");
var emendatoAula = atto.get("emendatoAula");
var sospeso = atto.get("sospeso");
var numeroDgr = atto.get("numeroDGR");
var dataDgr = atto.get("dataDGR");

var numeroFascicolo = atto.get("numeroRepertorio");
var rinviato = atto.get("rinviato");

var luceneQuery = "PATH:\"/app:company_home/cm:CRL//*\" ";

var tipoAttoString = ""+tipoAtto+"";
var type = "crlatti:atto";

var statiUtente = atto.get("statiUtente");

//atto -> statiUtente -> StatoAtto -> descrizione
//statoAtto = statiUtente
//ruoliCommissione = singolo metadato
//numeroRepertorio = numeroFascicolo
//emendatoAula da ridondare
//emendato in commissione

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
	} else if(tipoAttoString == "ORG") {
		type = "crlatti:attoOrg";	
	}
}

luceneQuery += "AND TYPE:\""+type+"\"";

if(type=="crlatti:atto" || type=="crlatti:attoEAC"){
	
	if(checkIsNotNull(legislatura)){
		luceneQuery = verifyAND(luceneQuery);
		luceneQuery += "( @crlatti\\:legislatura:\""+legislatura+"\" OR @crlatti\\:legislatura:\"nd\" )";
	}
}
else{
	
	if(checkIsNotNull(legislatura)){
		luceneQuery = verifyAND(luceneQuery);
		luceneQuery += "@crlatti\\:legislatura:\""+legislatura+"\"";
	}
}


if(checkIsNotNull(stato)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:statoAtto:\""+stato+"\"";
}

if(checkIsNotNull(numeroFascicolo)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroRepertorio:\""+numeroFascicolo+"\"";
}

//numeroAtto range
if(checkIsNotNull(numeroAttoDa) && checkIsNotNull(numeroAttoA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroAtto:["+numeroAttoDa+" TO "+numeroAttoA+"]";
} else if(checkIsNotNull(numeroAttoDa)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroAtto:["+numeroAttoDa+" TO MAX]";
} else if(checkIsNotNull(numeroAttoA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroAtto:[MIN TO "+numeroAttoA+"]";
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
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:firmatari:\""+firmatario+"\"";
}

//commissioni e ruoli
luceneQuery = creaLuceneQueryCommissioniRuoli(luceneQuery,commissione1,ruoloCommissione1);
luceneQuery = creaLuceneQueryCommissioniRuoli(luceneQuery,commissione2,ruoloCommissione2);
luceneQuery = creaLuceneQueryCommissioniRuoli(luceneQuery,commissione3,ruoloCommissione3);


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

//dataChiusura
if(checkIsNotNull(dataChiusuraDa)
		&& checkIsNotNull(dataChiusuraA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataChiusura:["+dataChiusuraDa+"T00:00:00 TO "+dataChiusuraA+"T00:00:00]";
} else if(checkIsNotNull(dataChiusuraDa)
		&& (dataChiusuraA==null || dataChiusuraA==undefined || dataChiusuraA=="")) {
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataChiusura:["+dataChiusuraDa+"T00:00:00 TO MAX]";
} else if(checkIsNotNull(dataChiusuraA)
		&& (dataChiusuraDa==null || dataChiusuraDa==undefined || dataChiusuraDa=="")){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataChiusura:[MIN TO "+dataChiusuraA+"T00:00:00]";
}

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

//dataAssegnazioneCommissioneReferente
if(checkIsNotNull(dataAssegnazioneDa)
		&& checkIsNotNull(dataAssegnazioneA)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataAssegnazioneCommissioneReferente:["+dataAssegnazioneDa+"T00:00:00 TO "+dataAssegnazioneA+"T00:00:00]";
} else if(checkIsNotNull(dataAssegnazioneDa)
		&& (dataAssegnazioneA==null || dataAssegnazioneA==undefined || dataAssegnazioneA=="")) {
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataAssegnazioneCommissioneReferente:["+dataAssegnazioneDa+"T00:00:00 TO MAX]";
} else if(checkIsNotNull(dataAssegnazioneA)
		&& (dataAssegnazioneDa==null || dataAssegnazioneDa==undefined || dataAssegnazioneDa=="")){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataAssegnazioneCommissioneReferente:[MIN TO "+dataAssegnazioneA+"T00:00:00]";
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

if(checkIsNotNull(rinviato)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:rinviato:\""+rinviato+"\"";
}

if(checkIsNotNull(emendatoAula)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:emendatoAulaAtto:\""+emendatoAula+"\"";
}

if(checkIsNotNull(sospeso)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:sospeso:\""+sospeso+"\"";
}

if(checkIsNotNull(numeroDgr)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:numeroDgr:\""+numeroDgr+"\"";
}

if(checkIsNotNull(dataDgr)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataDgr:["+dataDgr+"T00:00:00 TO "+dataDgr+"T00:00:00]";
}

if(checkIsNotNull(dataLr)){
	luceneQuery = verifyAND(luceneQuery);
	luceneQuery += "@crlatti\\:dataLr:["+dataLr+"T00:00:00 TO "+dataLr+"T00:00:00]";
}

//statiUtente - condizioni in OR per Lucene
if(checkIsNotNull(statiUtente)){
	var numeroStatiUtente = statiUtente.length();
	if(numeroStatiUtente>0){
		luceneQuery = verifyAND(luceneQuery);
		luceneQuery += " ( ";
		for (var j=0; j<numeroStatiUtente; j++){
			var statoAtto = statiUtente.get(j).get("statoAtto");
			if(checkIsNotNull(statoAtto)){
				var descrizioneStatoAtto = statoAtto.get("descrizione");
				if(checkIsNotNull(descrizioneStatoAtto)){
					//luceneQuery = verifyOR(luceneQuery);
					luceneQuery += "@crlatti\\:statoAtto:\""+descrizioneStatoAtto+"\"";
					if(j<numeroStatiUtente-1){
						luceneQuery = verifyOR(luceneQuery);
					}
					
				}
			}
		}
		luceneQuery += " ) ";
	}
}

protocolloLogger.info(luceneQuery);

var attiResults = search.luceneSearch(luceneQuery);


// logica per la creazione degli oggetti da visualizzare nei risultati

var attiResultsObj = new Array();

for(var i=0; i< attiResults.length; i++){
	
	var attoResult = attiResults[i];
	var attoResultObj = new Object();
	
	attoResultObj.id = attoResult.nodeRef.toString();
	

	attoResultObj.tipo =  attoResult.typeShort.substring(12,15);
	
	
	attoResultObj.name = attoResult.name;
	attoResultObj.numeroAtto = attoResult.properties["crlatti:numeroAtto"];
	attoResultObj.oggetto = attoResult.properties["crlatti:oggetto"];
	attoResultObj.primoFirmatario = attoResult.properties["crlatti:primoFirmatario"];
	attoResultObj.stato = attoResult.properties["crlatti:statoAtto"];
	attoResultObj.tipoIniziativa = attoResult.properties["crlatti:tipoIniziativa"];
	attoResultObj.dataPresentazione = attoResult.properties["crlatti:dataIniziativa"];
	attoResultObj.tipoChiusura = attoResult.properties["crlatti:tipoChiusura"];
	
		
	var elencoFirmatariArray = attoResult.properties["crlatti:firmatari"];
	var elencoFirmatariString = "";
	
	if(elencoFirmatariArray!=null) {
		for(var j=0; j<elencoFirmatariArray.length; j++){
			elencoFirmatariString += elencoFirmatariArray[j];
			if(j!=elencoFirmatariArray.length-1){
				elencoFirmatariString += ",";
			}
		}
	}
	
	attoResultObj.elencoFirmatari = elencoFirmatariString
	
	
	var commissioniConsultiveArray = attoResult.properties["crlatti:commConsultiva"];
	var commissioniConsultiveString = "";
	
	if(commissioniConsultiveArray!=null) {
		for(var j=0; j<commissioniConsultiveArray.length; j++){
			commissioniConsultiveString += commissioniConsultiveArray[j];
			if(j!=commissioniConsultiveArray.length-1){
				commissioniConsultiveString += ",";
			}
		}
	}
	
	attoResultObj.commissioniConsultive = commissioniConsultiveString
	
	var commissioniReferentiArray = attoResult.properties["crlatti:commReferente"];
	var commissioniReferentiString = "";
	
	if (commissioniReferentiArray!=null){
		for(var j=0; j<commissioniReferentiArray.length; j++){
			commissioniReferentiString += commissioniReferentiArray[j];
			if(j!=commissioniReferentiArray.length-1){
				commissioniReferentiString += ",";
			}
		}
	}
		
	
	attoResultObj.commissioniNonConsultive = commissioniReferentiString + "," + attoResult.properties["crlatti:commCoreferente"] + "," + 
		attoResult.properties["crlatti:commRedigente"] + "," + attoResult.properties["crlatti:commDeliberante"]

	if(attoResult.typeShort != "crlatti:attoMis" && attoResult.typeShort != "crlatti:attoEac") {
		
		var passaggioFolderNode = getLastPassaggio(attoResult);
		
		var abbinamentiFolderXpathQuery = "*[@cm:name='Abbinamenti']";
		var abbinamentiFolderNode = passaggioFolderNode.childrenByXPath(abbinamentiFolderXpathQuery)[0];
		abbinamenti = abbinamentiFolderNode.getChildAssocsByType("crlatti:abbinamento");
		
		abbinamentiString = "";
		
		for(var j=0 ; j<abbinamenti.lenght; j++){
			abbinamentiString += abbinamenti[j].name;
			if(j!=abbinamenti.length-1){
				abbinamentiString += ",";
			}
		}
		
		attoResultObj.elencoAbbinamenti = abbinamentiString;
		
		
		var commissioniFolderXpathQuery = "*[@cm:name='Commissioni']";
		var commissioniFolderNode = passaggioFolderNode.childrenByXPath(commissioniFolderXpathQuery)[0];
		commissioni = commissioniFolderNode.getChildAssocsByType("crlatti:commissione");

		for(var j=0 ; j<commissioni.lenght; j++){

			var commissioneCorrente = commissioni[j];
			
			if(commissioneCorrente.properties["crlatti:ruoloCommissione"] == "Referente" || commissioneCorrente.properties["crlatti:ruoloCommissione"] == "Delibernate"
				|| commissioneCorrente.properties["crlatti:ruoloCommissione"] == "Redigente") {
				
				attoResultObj.dataAssegnazione = commissioneCorrente.properties["crlatti:dataAssegnazioneCommissione"];
				attoResultObj.esitoVotazioneCommissioneReferente = commissioneCorrente.properties["crlatti:esitoVotazioneCommissione"];
				attoResultObj.dataVotazioneCommissione = commissioneCorrente.properties["crlatti:dataVotazioneCommissione"];
				attoResultObj.dataRichiestaIscrizioneAula = commissioneCorrente.properties["crlatti:dataRichiestaIscrizioneAula"];
			
				var relatoriXPathQuery = "*[@cm:name='Relatori']";
				var relatoriCommissioneFolderNode = commissioneCorrente.childrenByXPath(relatoriXPathQuery)[0];
				
				var relatoriNode = relatoriCommissioneFolderNode.getChildAssocsByType("crlatti:relatore");
				
				attoResultObj.relatore = relatoriNode[0].name;
				attoResultObj.dataNominaRelatore = relatoriNode[0].properties["crlatti:dataNominaRelatore"];		
			}
		}
		
		
	}
	
	

	attoResultObj.dataScadenza = attoResult.properties["crlatti:dataScadenza"];
	
	attoResultObj.esitoVotazioneAula = attoResult.properties["crlatti:esitoVotoAula"];
	attoResultObj.dataVotazioneAula = attoResult.properties["crlatti:dataSedutaAula"];
		
	attoResultObj.numeroPubblicazioneBURL = attoResult.properties["crlatti:numeroPubblicazioneBURL"];
	attoResultObj.dataPubblicazioneBURL = attoResult.properties["crlatti:dataPubblicazioneBURL"];
	
	attoResultObj.numeroDcr = attoResult.properties["crlatti:numeroDcr"];
	attoResultObj.numeroLcr = attoResult.properties["crlatti:numeroLcr"];
	
	attoResultObj.numeroLr = attoResult.properties["crlatti:numeroLr"];
	attoResultObj.dataLR = attoResult.properties["crlatti:dataLr"];
	
	
	var noteGeneraliFilename = "Note Generali.txt";
	var noteGeneraliXPathQuery = "*[@cm:name='"+noteGeneraliFilename+"']";
	var noteGenerali = attoResult.childrenByXPath(noteGeneraliXPathQuery)[0];
	
	if(noteGenerali!=null){
		attoResultObj.notePresentazioneAssegnazione = noteGenerali.content;
	}
	
	attiResultsObj.push(attoResultObj);
	
}





model.atti = attiResultsObj;