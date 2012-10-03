<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">


var atto = json.get("atto");
var id = atto.get("id");

var consultazioni = atto.get("consultazioni");

if(checkIsNotNull(id)){
	
	var attoNode = utils.getNodeFromString(id);
	
	var consultazioniXPathQuery = "*[@cm:name='Consultazioni']";
	var consultazioniFolderNode = attoNode.childrenByXPath(consultazioniXPathQuery)[0];
	
	var numeroConsultazioni = consultazioni.length();
	
	for (var j=0; j<numeroConsultazioni; j++){
	
		var consultazione = consultazioni.get(j).get("consultazione");
	
		var descrizione = filterParam(consultazione.get("descrizione"));
		var dataConsultazione = filterParam(consultazione.get("dataConsultazione"));
			
		//verifica l'esistenza della consultazione all'interno del folder Consultazioni
		var existConsultazioneXPathQuery = "*[@cm:name='"+descrizione+"']";
		var consultazioneEsistenteResults = consultazioniFolderNode.childrenByXPath(existConsultazioneXPathQuery);
		var consultazioneNode = null;
		if(consultazioneEsistenteResults!=null && consultazioneEsistenteResults.length>0){
			consultazioneNode = consultazioneEsistenteResults[0];
		} else {
			// copia la cartella consultazione dagli space template e la rinomina
			
			var consSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Consultazione\"";
			var consSpaceTemplateNode = search.luceneSearch(consSpaceTemplateQuery)[0];
			
			
			// deep copy con secondo argomento = true
			consSpaceTemplateNode.copy(consultazioniFolderNode, true);
			
			var consXPathQuery = "*[@cm:name='Consultazione']";
			consultazioneNode = consultazioniFolderNode.childrenByXPath(consXPathQuery)[0];
			consultazioneNode.properties["cm:name"] = descrizione;
			
		}
	
		var dataConsultazioneSplitted = dataConsultazione.split("-");
		var dataConsultazioneParsed = new Date(dataConsultazioneSplitted[0],dataConsultazioneSplitted[1]-1,dataConsultazioneSplitted[2]);
		
		consultazioneNode.properties["crlatti:dataConsultazione"] = dataConsultazioneParsed;
		consultazioneNode.save();
		
	}
	
	
	//verifica consultazioni da cancellare
	var consultazioniNelRepository = consultazioniFolderNode.getChildAssocsByType("crlatti:consultazione");
	
		
	//query nel repository per capire se bisogna cancellare alcune consultazioni
	for(var z=0; z<consultazioniNelRepository.length; z++){
		var trovato = false;
		var consultazioneNelRepository = consultazioniNelRepository[z];
		
		//cerco il nome della consultazione nel repo all'interno del json
		for (var q=0; q<consultazioni.length(); q++){
			var consult = consultazioni.get(q).get("consultazione");
			var descrizione = filterParam(consult.get("descrizione"));
			if(""+descrizione+""==""+consultazioneNelRepository.name+""){
				trovato = true;
				break
			}
		}
		if(!trovato){
			consultazioneNelRepository.remove();
		}
	}
	
	
} else {
	status.code = 400;
	status.message = "id Atto è obbligatorio";
	status.redirect = true;
}