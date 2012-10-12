<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

//Regole da impostare con uno scope attivo solo su crlatti:atto

var legislatura = document.properties["crlatti:legislatura"];
var numeroAtto = document.properties["crlatti:numeroAtto"];
var idProtocollo = document.properties["crlatti:idProtocollo"];

var tipologia = document.properties["crlatti:tipologia"];
var numeroProtocollo = document.properties["crlatti:numeroProtocollo"];
var numeroRepertorio = document.properties["crlatti:numeroRepertorio"];
var dataRepertorio = document.properties["crlatti:dataRepertorio"];
var classificazione = document.properties["crlatti:classificazione"];
var oggetto = document.properties["crlatti:oggetto"];

var tipoIniziativa = document.properties["crlatti:tipoIniziativa"];
var dataIniziativa = document.properties["crlatti:dataIniziativa"];
var descrizioneIniziativa = document.properties["crlatti:descrizioneIniziativa"];

var numeroDgr = document.properties["crlatti:numeroDgr"];
var dataDgr = document.properties["crlatti:dataDgr"];

var assegnazione = document.properties["crlatti:assegnazione"];
var firmatari = document.properties["crlatti:firmatari"];

var dataImportazione = new Date();
var mese = dataImportazione.getMonth() + 1;
var anno = dataImportazione.getFullYear();

var type = document.typeShort;

var gestioneAttiPath = "/app:company_home"+
"/cm:"+search.ISO9075Encode("CRL")+
"/cm:"+search.ISO9075Encode("Gestione Atti");

if(type=="crlatti:attoEac"){
	//gestione importazione atti di tipo EAC
	var eacRootPath = gestioneAttiPath + "/cm:EAC";
	var eacRootFolderNode = search.luceneSearch("PATH:\""+eacRootPath+"\"")[0];
	
	//creazione spazio anno
	var eacAnnoPath = eacRootPath + "/cm:" + search.ISO9075Encode(anno);
	var eacAnnoLuceneQuery = "PATH:\""+eacAnnoPath+"\"";
	var eacAnnoResults = search.luceneSearch(eacAnnoLuceneQuery);
	var eacAnnoFolderNode = null;
	if(eacAnnoResults!=null && eacAnnoResults.length>0){
		eacAnnoFolderNode = eacAnnoResults[0];
	} else {
		eacAnnoFolderNode = eacRootFolderNode.createFolder(anno);
	}
	
	//creazione spazio mese
	var eacMesePath = eacAnnoPath + "/cm:" + search.ISO9075Encode(mese);
	var eacMeseLuceneQuery = "PATH:\""+eacMesePath+"\"";
	var eacMeseResults = search.luceneSearch(eacMeseLuceneQuery);
	var eacMeseFolderNode = null;
	if(eacMeseResults!=null && eacMeseResults.length>0){
		eacMeseFolderNode = eacMeseResults[0];
	} else {
		eacMeseFolderNode = eacAnnoFolderNode.createFolder(mese);
	}
	
	//verifica esistenza del folder dell'atto EAC
	var eacAttoPath = eacMesePath + "/cm:" + search.ISO9075Encode(numeroAtto);
	var eacAttoLuceneQuery = "PATH:\""+eacAttoPath+"\"";
	var eacAttoResults = search.luceneSearch(eacAttoLuceneQuery);
	var eacAttoFolderNode = null;
	
	if(eacAttoResults!=null && eacAttoResults.length>0){
		eacAttoFolderNode = eacAttoResults[0];
		eacAttoFolderNode.properties["crlatti:numeroAtto"] = document.properties["crlatti:numeroAtto"];
		eacAttoFolderNode.properties["crlatti:dataAtto"] = document.properties["crlatti:dataAtto"];
		eacAttoFolderNode.properties["crlatti:noteEac"] = document.properties["crlatti:noteEac"];
		eacAttoFolderNode.save();
		document.remove();
	} else {
		//importa il nodo
		document.move(eacMeseFolderNode);
		if(document.hasAspect("crlatti:importatoDaProtocollo")){
			document.removeAspect("crlatti:importatoDaProtocollo");
		}
	}

} else if (type=="crlatti:attoMis"){
	//gestione importazione atti di tipo MIS
	var misRootPath = gestioneAttiPath + "/cm:MIS";
	var misRootFolderNode = search.luceneSearch("PATH:\""+misRootPath+"\"")[0];
	
	//creazione spazio anno
	var misAnnoPath = misRootPath + "/cm:" + search.ISO9075Encode(anno);
	var misAnnoLuceneQuery = "PATH:\""+misAnnoPath+"\"";
	var misAnnoResults = search.luceneSearch(misAnnoLuceneQuery);
	var misAnnoFolderNode = null;
	if(misAnnoResults!=null && misAnnoResults.length>0){
		misAnnoFolderNode = misAnnoResults[0];
	} else {
		misAnnoFolderNode = misRootFolderNode.createFolder(anno);
	}
	
	//creazione spazio mese
	var misMesePath = misAnnoPath + "/cm:" + search.ISO9075Encode(mese);
	var misMeseLuceneQuery = "PATH:\""+misMesePath+"\"";
	var misMeseResults = search.luceneSearch(misMeseLuceneQuery);
	var misMeseFolderNode = null;
	if(misMeseResults!=null && misMeseResults.length>0){
		misMeseFolderNode = misMeseResults[0];
	} else {
		misMeseFolderNode = misAnnoFolderNode.createFolder(mese);
	}
	
	//verifica esistenza del folder dell'atto MIS
	var misAttoPath = misMesePath + "/cm:" + search.ISO9075Encode(numeroAtto);
	var misAttoLuceneQuery = "PATH:\""+misAttoPath+"\"";
	var misAttoResults = search.luceneSearch(misAttoLuceneQuery);
	var misAttoFolderNode = null;
	
	if(misAttoResults!=null && misAttoResults.length>0){
		misAttoFolderNode = misAttoResults[0];
		
		misAttoFolderNode.name = numeroAtto;
		misAttoFolderNode.properties["crlatti:numeroAtto"] = document.properties["crlatti:numeroAtto"];
		misAttoFolderNode.properties["crlatti:noteMis"] = document.properties["crlatti:noteMis"];
		misAttoFolderNode.properties["crlatti:commissioneCompetenteMis"] = document.properties["crlatti:commissioneCompetenteMis"];
		misAttoFolderNode.properties["crlatti:esitoVotoIntesaMis"] = document.properties["crlatti:esitoVotoIntesaMis"];
		misAttoFolderNode.properties["crlatti:numeroAttoUdpMis"] = document.properties["crlatti:numeroAttoUdpMis"];
		misAttoFolderNode.properties["crlatti:istitutoIncaricatoMis"] = document.properties["crlatti:istitutoIncaricatoMis"];
		misAttoFolderNode.properties["crlatti:dataIniziativaComitatoMis"] = document.properties["crlatti:dataIniziativaComitatoMis"];
		misAttoFolderNode.properties["crlatti:dataPropostaCommissioneMis"] = document.properties["crlatti:dataPropostaCommissioneMis"];
		misAttoFolderNode.properties["crlatti:dataIntesaMis"] = document.properties["crlatti:dataIntesaMis"];
		misAttoFolderNode.properties["crlatti:dataRispostaComitatoMis"] = document.properties["crlatti:dataRispostaComitatoMis"];
		misAttoFolderNode.properties["crlatti:dataApprovazioneProgettoMis"] = document.properties["crlatti:dataApprovazioneProgettoMis"];
		misAttoFolderNode.properties["crlatti:dataApprovazioneUdpMis"] = document.properties["crlatti:dataApprovazioneUdpMis"];
		misAttoFolderNode.properties["crlatti:scadenzaMvMis"] = document.properties["crlatti:scadenzaMvMis"];
		misAttoFolderNode.properties["crlatti:dataEsameRapportoFinaleMis"] = document.properties["crlatti:dataEsameRapportoFinaleMis"];
		misAttoFolderNode.properties["crlatti:dataTrasmissioneACommissioniMis"] = document.properties["crlatti:dataTrasmissioneACommissioniMis"];
		misAttoFolderNode.save();
		document.remove();
		
	} else {
		//creazione dell'atto di tipo MIS
		document.move(misMeseFolderNode);
		if(document.hasAspect("crlatti:importatoDaProtocollo")){
			document.removeAspect("crlatti:importatoDaProtocollo");
		}
	}
	
	
} else if(checkIsNotNull(legislatura)
		&& checkIsNotNull(numeroAtto)
		&& checkIsNotNull(idProtocollo)){
	
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
	
	//verifica esistenza del folder dell'atto
	var attoPath = mesePath + "/cm:" + search.ISO9075Encode(numeroAtto);
	var attoLuceneQuery = "PATH:\""+attoPath+"\"";
	var attoResults = search.luceneSearch(attoLuceneQuery);
	
	var esisteAttoLuceneQuery = "TYPE:\"crlatti:atto\" AND @crlatti\\:idProtocollo:\""+idProtocollo+"\" AND @cm\\:name:\""+numeroAtto+"\"";
	
	var attoFolderNode = null;
	if(attoResults!=null && attoResults.length>0){
		//atto presente
		attoFolderNode = attoResults[0];
		attoFolderNode.name = numeroAtto;
		attoFolderNode.properties["crlatti:legislatura"] = legislatura;
		attoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
		attoFolderNode.properties["crlatti:tipologia"] = tipologia;
		attoFolderNode.properties["crlatti:anno"] = anno;
		attoFolderNode.properties["crlatti:idProtocollo"] = idProtocollo;
		attoFolderNode.properties["crlatti:numeroProtocollo"] = numeroProtocollo;
		attoFolderNode.properties["crlatti:numeroRepertorio"] = numeroRepertorio;
		attoFolderNode.properties["crlatti:classificazione"] = classificazione;
		attoFolderNode.properties["crlatti:oggetto"] = oggetto;
		attoFolderNode.properties["crlatti:tipoIniziativa"] = tipoIniziativa;
		attoFolderNode.properties["crlatti:descrizioneIniziativa"] = descrizioneIniziativa;
		attoFolderNode.properties["crlatti:assegnazione"] = assegnazione;
		attoFolderNode.properties["crlatti:dataIniziativa"] = document.properties["crlatti:dataIniziativa"];
		attoFolderNode.properties["crlatti:dataRepertorio"] = document.properties["crlatti:dataRepertorio"];
		
		if(attoFolderNode.hasAspect("crlatti:dgr")){
			attoFolderNode.properties["crlatti:numeroDgr"] = document.properties["crlatti:numeroDgr"];
			attoFolderNode.properties["crlatti:dataDgr"] = document.properties["crlatti:dataDgr"];
		}
		
		if(attoFolderNode.hasAspect("crlatti:firmatariAspect")){
			attoFolderNode.properties["crlatti:firmatari"] = document.properties["crlatti:firmatari"];
		}
		
		attoFolderNode.save();
		
		if(attoFolderNode.hasAspect("crlatti:importatoDaProtocollo")){
			attoFolderNode.removeAspect("crlatti:importatoDaProtocollo");
		}
		
		document.remove();
		
	} else {
		//creazione del nodo del nuovo atto
		document.move(meseFolderNode);
		if(document.hasAspect("crlatti:importatoDaProtocollo")){
			document.removeAspect("crlatti:importatoDaProtocollo");
		}
	}

}