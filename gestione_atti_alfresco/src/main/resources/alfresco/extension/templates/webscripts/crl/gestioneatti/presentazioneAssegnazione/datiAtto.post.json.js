<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var classificazione = atto.get("classificazione");
var oggetto = atto.get("oggetto");
var numeroRepertorio = atto.get("numeroRepertorio");
var dataRepertorio = atto.get("dataRepertorio");
var tipoIniziativa = atto.get("tipoIniziativa");
var dataIniziativa = atto.get("dataIniziativa");
var descrizioneIniziativa = atto.get("descrizioneIniziativa");
var assegnazione = atto.get("assegnazione");
var numeroDgr = atto.get("numeroDgr");
var dataDgr = atto.get("dataDgr");
var firmatari = atto.get("firmatari");

if(checkIsNotNull(id)){
	var attoNode = utils.getNodeFromString(id);
	attoNode.properties["crlatti:classificazione"] = classificazione;
	attoNode.properties["crlatti:oggetto"] = oggetto;
	attoNode.properties["crlatti:numeroRepertorio"] = numeroRepertorio;
	
	if(checkIsNotNull(dataRepertorio)){
		var dataRepertorioSplitted = dataRepertorio.split("-");
		var dataRepertorioParsed = new Date(dataRepertorioSplitted[0],dataRepertorioSplitted[1]-1,dataRepertorioSplitted[2]);
		attoNode.properties["crlatti:dataRepertorio"] = dataRepertorioParsed;
	}
	
	attoNode.properties["crlatti:tipoIniziativa"] = tipoIniziativa;
	
	if(checkIsNotNull(dataIniziativa)){
		var dataIniziativaSplitted = dataIniziativa.split("-");
		var dataIniziativaParsed = new Date(dataIniziativaSplitted[0],dataIniziativaSplitted[1]-1,dataIniziativaSplitted[2]);
		attoNode.properties["crlatti:dataIniziativa"] = dataIniziativaParsed;
	}
	
	attoNode.properties["crlatti:descrizioneIniziativa"] = descrizioneIniziativa;
	attoNode.properties["crlatti:assegnazione"] = assegnazione;
	
	if(attoNode.typeShort!="crlatti:attoInp"){
		//campi DGR non attivi solo per INP
		attoNode.properties["crlatti:numeroDgr"] = numeroDgr;
		if(checkIsNotNull(dataDgr)){
			var dataDgrSplitted = dataDgr.split("-");
			var dataDgrParsed = new Date(dataDgrSplitted[0],dataDgrSplitted[1]-1,dataDgrSplitted[2]);
			attoNode.properties["crlatti:dataDgr"] = dataDgrParsed;
		}
	}
	attoNode.save();
	
	//gestione firmatari
	var firmatariXPathQuery = "*[@cm:name='Firmatari']";
	var firmatariFolderNode = attoNode.childrenByXPath(firmatariXPathQuery)[0];
	
	var numeroFirmatari = firmatari.length();
	for (var i=0; i<numeroFirmatari; i++){
		var firmatario = firmatari.get(i).get("firmatario");
		var descrizione = filterParam(firmatario.get("descrizione"));
		var gruppoConsiliare = filterParam(firmatario.get("gruppoConsiliare"));
		var dataFirma = filterParam(firmatario.get("dataFirma"));
		var dataRitiro = filterParam(firmatario.get("dataRitiro"));
		var primoFirmatario = filterParam(firmatario.get("primoFirmatario"));
		
		//verifica l'esistenza del firmatario all'interno del folder Firmatari
		var existFirmatarioXPathQuery = "*[@cm:name='"+descrizione+"']";
		var firmatarioEsistenteResults = firmatariFolderNode.childrenByXPath(existFirmatarioXPathQuery);
		var firmatarioNode = null;
		if(firmatarioEsistenteResults!=null && firmatarioEsistenteResults.length>0){
			firmatarioNode = firmatarioEsistenteResults[0];
		} else {
			firmatarioNode = firmatariFolderNode.createNode(descrizione,"crlatti:firmatario");
		}
		
		var dataFirmaParsed = null;
		if(checkIsNotNull(dataFirma)){
			var dataFirmaSplitted = dataFirma.split("-");
			dataFirmaParsed = new Date(dataFirmaSplitted[0],dataFirmaSplitted[1]-1,dataFirmaSplitted[2]);
		}
		
		var dataRitiroParsed = null;
		if(checkIsNotNull(dataRitiro)){
			var dataRitiroSplitted = dataRitiro.split("-");
			dataRitiroParsed = new Date(dataRitiroSplitted[0],dataRitiroSplitted[1]-1,dataRitiroSplitted[2]);
		}
		
		firmatarioNode.properties["crlatti:nomeFirmatario"] = descrizione;
		firmatarioNode.properties["crlatti:dataFirma"] = dataFirmaParsed;
		firmatarioNode.properties["crlatti:dataRitiro"] = dataRitiroParsed;
		firmatarioNode.properties["crlatti:isPrimoFirmatario"] = primoFirmatario;
		firmatarioNode.properties["crlatti:gruppoConsiliare"] = gruppoConsiliare;
		firmatarioNode.save();
	}
	
	//verifica firmatari da cancellare
	var firmatariNelRepository = firmatariFolderNode.getChildAssocsByType("crlatti:firmatario");
	
	//query nel repository per capire se bisogna cancellare dei firmatari
	for(var j=0; j<firmatariNelRepository.length; j++){
		var trovato = false;
		var firmatarioNelRepository = firmatariNelRepository[j];
		
		//cerco il nome del firmatario nel repo all'interno del json
		for (var i=0; i<numeroFirmatari; i++){
			var firmatario = firmatari.get(i).get("firmatario");
			var descrizione = filterParam(firmatario.get("descrizione"));
			if(""+descrizione+""==""+firmatarioNelRepository.name+""){
				trovato = true;
				break
			}
		}
		if(!trovato){
			firmatarioNelRepository.remove();
		}
	}
	
	model.atto = attoNode;
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}