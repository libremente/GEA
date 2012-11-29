<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var legislatura = atto.get("legislatura");
var tipologia = atto.get("tipologia");
var numeroAtto = atto.get("numeroAtto");
var tipoAtto = atto.get("tipoAtto");
var stato = atto.get("stato");
var dataImportazione = new Date();
var mese = dataImportazione.getMonth() + 1;
var anno = dataImportazione.getFullYear();

var gestioneAttiPath = "/app:company_home"+
"/cm:"+search.ISO9075Encode("CRL")+
"/cm:"+search.ISO9075Encode("Gestione Atti");

//gestione dei tipi atto
var nodeType = "crlatti:atto";
if(tipoAtto=="PDL"){
	nodeType = "crlatti:attoPdl";
} else if(tipoAtto=="DOC"){
	nodeType = "crlatti:attoDoc";
} else if(tipoAtto=="INP"){
	nodeType = "crlatti:attoInp";
} else if(tipoAtto=="PAR"){
	nodeType = "crlatti:attoPar";
} else if(tipoAtto=="PDA"){
	nodeType = "crlatti:attoPda";
} else if(tipoAtto=="PLP"){
	nodeType = "crlatti:attoPlp";
} else if(tipoAtto=="PRE"){
	nodeType = "crlatti:attoPre";
} else if(tipoAtto=="REF"){
	nodeType = "crlatti:attoRef";
} else if(tipoAtto=="REL"){
	nodeType = "crlatti:attoRel";
} else if(tipoAtto=="EAC"){
	nodeType = "crlatti:attoEac";
} else if(tipoAtto=="MIS"){
	nodeType = "crlatti:attoMis";
}

if(nodeType=="crlatti:attoEac"){
	//gestione inserimento e modifica dell'atto di tipo EAC
	var id = atto.get("id");
	var dataAtto = atto.get("dataAtto");
	var note = atto.get("note");
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
	
	if(checkIsNotNull(id)){
		eacAttoFolderNode = utils.getNodeFromString(id);
	} else {
		//creazione dell'atto di tipo EAC
		var eacAttoSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:AttoEac\"";
		var eacAttoSpaceTemplateNode = search.luceneSearch(eacAttoSpaceTemplateQuery)[0];
		eacAttoFolderNode = eacAttoSpaceTemplateNode.copy(eacMeseFolderNode,true);
	}
	
	eacAttoFolderNode.name = numeroAtto;
	eacAttoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
	eacAttoFolderNode.properties["crlatti:noteEac"] = note;
	eacAttoFolderNode.properties["crlatti:legislatura"] = "nd";

	
	if(checkIsNotNull(dataAtto)){
		var dataAttoSplitted = dataAtto.split("-");
		var dataAttoParsed = new Date(dataAttoSplitted[0],dataAttoSplitted[1]-1,dataAttoSplitted[2]);
		eacAttoFolderNode.properties["crlatti:dataAtto"] = dataAttoParsed;
	}
	
	eacAttoFolderNode.save();
	model.atto = eacAttoFolderNode;
		
} else if(nodeType=="crlatti:attoMis") {
	
	//gestione inserimento e modifica dell'atto di tipo MIS
	var id = atto.get("id");
	
	var numeroRepertorio = atto.get("numeroRepertorio");
	var dataIniziativaComitato = atto.get("dataIniziativaComitato");
	var dataPropostaCommissione = atto.get("dataPropostaCommissione");
	var commissioneCompetente = atto.get("commissioneCompetente");
	var esitoVotoIntesa = atto.get("esitoVotoIntesa");
	var dataIntesa = atto.get("dataIntesa");
	var dataRispostaComitato = atto.get("dataRispostaComitato");
	var dataApprovazioneProgetto = atto.get("dataApprovazioneProgetto");
	var dataApprovazioneUdp = atto.get("dataApprovazioneUdP");
	var numeroAttoUdp = atto.get("numeroAttoUdp");
	var istitutoIncaricato = atto.get("istitutoIncaricato");
	var scadenzaMv = atto.get("dataScadenzaMV");
	var dataEsameRapportoFinale = atto.get("dataEsameRapportoFinale");
	var dataTrasmissioneACommissioni = atto.get("dataTrasmissioneCommissioni");
	var note = atto.get("note");
	
	
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
	
	
	if(checkIsNotNull(id)){
		misAttoFolderNode = utils.getNodeFromString(id);
	} else {
		//creazione dell'atto di tipo MIS
		var misAttoSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:AttoMis\"";
		var misAttoSpaceTemplateNode = search.luceneSearch(misAttoSpaceTemplateQuery)[0];
		misAttoFolderNode = misAttoSpaceTemplateNode.copy(meseFolderNode,true);
	}
	
	//metadati
	
	//date
	if(checkIsNotNull(dataIniziativaComitato)){
		var dataIniziativaComitatoSplitted = dataIniziativaComitato.split("-");
		var dataIniziativaComitatoParsed = new Date(dataIniziativaComitatoSplitted[0],dataIniziativaComitatoSplitted[1]-1,dataIniziativaComitatoSplitted[2]);
		misAttoFolderNode.properties["crlatti:dataIniziativaComitatoMis"] = dataIniziativaComitatoParsed;
	}
	
	if(checkIsNotNull(dataPropostaCommissione)){
		var dataPropostaCommissioneSplitted = dataPropostaCommissione.split("-");
		var dataPropostaCommissioneParsed = new Date(dataPropostaCommissioneSplitted[0],dataPropostaCommissioneSplitted[1]-1,dataPropostaCommissioneSplitted[2]);
		misAttoFolderNode.properties["crlatti:dataPropostaCommissioneMis"] = dataPropostaCommissioneParsed;
	}
	
	if(checkIsNotNull(dataIntesa)){
		var dataIntesaSplitted = dataIntesa.split("-");
		var dataIntesaSplittedParsed = new Date(dataIntesaSplitted[0],dataIntesaSplitted[1]-1,dataIntesaSplitted[2]);
		misAttoFolderNode.properties["crlatti:dataIntesaMis"] = dataIntesaSplittedParsed;
	}
	
	if(checkIsNotNull(dataRispostaComitato)){
		var dataRispostaComitatoSplitted = dataRispostaComitato.split("-");
		var dataRispostaComitatoParsed = new Date(dataRispostaComitatoSplitted[0],dataRispostaComitatoSplitted[1]-1,dataRispostaComitatoSplitted[2]);
		misAttoFolderNode.properties["crlatti:dataRispostaComitatoMis"] = dataRispostaComitatoParsed;
	}
	
	if(checkIsNotNull(dataApprovazioneProgetto)){
		var dataApprovazioneProgettoSplitted = dataApprovazioneProgetto.split("-");
		var dataApprovazioneProgettoParsed = new Date(dataApprovazioneProgettoSplitted[0],dataApprovazioneProgettoSplitted[1]-1,dataApprovazioneProgettoSplitted[2]);
		misAttoFolderNode.properties["crlatti:dataApprovazioneProgettoMis"] = dataApprovazioneProgettoParsed;
	}
	
	if(checkIsNotNull(dataApprovazioneUdp)){
		var dataApprovazioneUdpSplitted = dataApprovazioneUdp.split("-");
		var dataApprovazioneUdpParsed = new Date(dataApprovazioneUdpSplitted[0],dataApprovazioneUdpSplitted[1]-1,dataApprovazioneUdpSplitted[2]);
		misAttoFolderNode.properties["crlatti:dataApprovazioneUdpMis"] = dataApprovazioneUdpParsed;
	}
	
	if(checkIsNotNull(scadenzaMv)){
		var scadenzaMvSplitted = scadenzaMv.split("-");
		var scadenzaMvParsed = new Date(scadenzaMvSplitted[0],scadenzaMvSplitted[1]-1,scadenzaMvSplitted[2]);
		misAttoFolderNode.properties["crlatti:scadenzaMvMis"] = scadenzaMvParsed;
	}
	
	if(checkIsNotNull(dataEsameRapportoFinale)){
		var dataEsameRapportoFinaleSplitted = dataEsameRapportoFinale.split("-");
		var dataEsameRapportoFinaleParsed = new Date(dataEsameRapportoFinaleSplitted[0],dataEsameRapportoFinaleSplitted[1]-1,dataEsameRapportoFinaleSplitted[2]);
		misAttoFolderNode.properties["crlatti:dataEsameRapportoFinaleMis"] = dataEsameRapportoFinaleParsed;
	}
	
	if(checkIsNotNull(dataTrasmissioneACommissioni)){
		var dataTrasmissioneACommissioniSplitted = dataTrasmissioneACommissioni.split("-");
		var dataTrasmissioneACommissioniParsed = new Date(dataTrasmissioneACommissioniSplitted[0],dataTrasmissioneACommissioniSplitted[1]-1,dataTrasmissioneACommissioniSplitted[2]);
		misAttoFolderNode.properties["crlatti:dataTrasmissioneACommissioniMis"] = dataTrasmissioneACommissioniParsed;
	}
	
	misAttoFolderNode.name = numeroAtto;
	misAttoFolderNode.properties["crlatti:legislatura"] = legislatura;
	misAttoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
	misAttoFolderNode.properties["crlatti:numeroRepertorio"] = numeroRepertorio;
	misAttoFolderNode.properties["crlatti:noteMis"] = note;
	misAttoFolderNode.properties["crlatti:commissioneCompetenteMis"] = commissioneCompetente;
	misAttoFolderNode.properties["crlatti:esitoVotoIntesaMis"] = esitoVotoIntesa;
	misAttoFolderNode.properties["crlatti:numeroAttoUdpMis"] = numeroAttoUdp;
	misAttoFolderNode.properties["crlatti:istitutoIncaricatoMis"] = istitutoIncaricato;

	misAttoFolderNode.save();
	model.atto = misAttoFolderNode;
	
} else {

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
	if(attoResults!=null && attoResults.length>0){
		//atto presente
		status.code = 500;
		status.message = "atto numero "+numeroAtto+" gia' presente nel repository";
		status.redirect = true;
	} else {
		//creazione del nodo
		var attoSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Atto\"";
		var attoSpaceTemplateNode = search.luceneSearch(attoSpaceTemplateQuery)[0];
		var attoFolderNode = attoSpaceTemplateNode.copy(meseFolderNode,true);
		attoFolderNode.name = numeroAtto;
		attoFolderNode.specializeType(nodeType);
		attoFolderNode.properties["crlatti:legislatura"] = legislatura;
		attoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
		attoFolderNode.properties["crlatti:tipologia"] = tipologia;
		attoFolderNode.properties["crlatti:anno"] = anno;
		attoFolderNode.properties["crlatti:statoAtto"] = stato;
		attoFolderNode.save();
		
		if(attoFolderNode.hasAspect("crlatti:firmatariAspect")){
			var firmatariSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Firmatari\"";
			var firmatariSpaceTemplateNode = search.luceneSearch(firmatariSpaceTemplateQuery)[0];
			firmatariSpaceTemplateNode.copy(attoFolderNode);
		}
		
		
		// creazione del primo passaggio per le commissioni e l'aula
		var passaggiXPathQuery = "*[@cm:name='Passaggi']";
		var passaggiFolderNode = attoFolderNode.childrenByXPath(passaggiXPathQuery)[0];
		
	
		var passaggioSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Passaggio\"";
		var passaggioSpaceTemplateNode = search.luceneSearch(passaggioSpaceTemplateQuery)[0];
		var passaggioFolderNode = passaggioSpaceTemplateNode.copy(passaggiFolderNode, true); // deep copy
		passaggioFolderNode.properties["cm:name"] = "Passaggio1";
		passaggioFolderNode.save();
		
		model.atto = attoFolderNode;
	}
}