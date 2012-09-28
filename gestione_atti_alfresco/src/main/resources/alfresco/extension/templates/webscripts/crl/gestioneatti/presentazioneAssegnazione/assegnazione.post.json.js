<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
// suppongo che ci sia solo un passaggio essendo la fase di assegnazione antecedente all'esame commissioni e all'esame aula
var commissioni = atto.get("passaggi").get(0).get("passaggio").get("commissioni");
var pareri = atto.get("pareri");

if(checkIsNotNull(id)){
	var attoNode = utils.getNodeFromString(id);
	
	// gestione passaggi
	var passaggiXPathQuery = "*[@cm:name='Passaggi']";
	var passaggiFolderNode = attoNode.childrenByXPath(passaggiXPathQuery)[0];
	
	var passaggioXPathQuery = "*[@cm:name='Passaggio1']";
	var passaggioFolderNode = passaggiFolderNode.childrenByXPath(passaggioXPathQuery)[0];
	
	//gestione commissioni e ridondanza (in eliminazione) di commissioni referenti e consultive
	var commissioniXPathQuery = "*[@cm:name='Commissioni']";
	var commissioniFolderNode = passaggioFolderNode.childrenByXPath(commissioniXPathQuery)[0];
	
	var numeroCommissioni = commissioni.length();
	for (var j=0; j<numeroCommissioni; j++){
		var commissione = commissioni.get(j).get("commissione");
		var descrizione = filterParam(commissione.get("descrizione"));
		var dataProposta = filterParam(commissione.get("dataProposta"));
		var dataAssegnazione = filterParam(commissione.get("dataAssegnazione"));
		var ruolo = filterParam(commissione.get("ruolo"));
		var stato = filterParam(commissione.get("stato"));
		var dataAnnullo = filterParam(commissione.get("dataAnnullo"));
		
		//verifica l'esistenza della commissione all'interno del folder Commissioni
		var existCommissioneXPathQuery = "*[@cm:name='"+descrizione+"']";
		var commissioneEsistenteResults = commissioniFolderNode.childrenByXPath(existCommissioneXPathQuery);
		var commissioneNode = null;
		if(commissioneEsistenteResults!=null && commissioneEsistenteResults.length>0){
			commissioneNode = commissioneEsistenteResults[0];
		} else {
			

			//commissioneNode = commissioniFolderNode.createNode(descrizione,"crlatti:commissione");
			
			// copia la cartella commissione dagli space template e la rinomina
			
			var commissioneSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Commissione\"";
			var commissioneSpaceTemplateNode = search.luceneSearch(commissioneSpaceTemplateQuery)[0];
			
			
			// deep copy con secondo argomento = true
			commissioneSpaceTemplateNode.copy(commissioniFolderNode, true);
			
			var commissioneXPathQuery = "*[@cm:name='Commissione']";
			commissioneNode = commissioniFolderNode.childrenByXPath(commissioneXPathQuery)[0];
			commissioneNode.properties["cm:name"] = descrizione;
		}
		
		var dataPropostaParsed = null;
		if(checkIsNotNull(dataProposta)){
			var dataPropostaSplitted = dataProposta.split("-");
			dataPropostaParsed = new Date(dataPropostaSplitted[0],dataPropostaSplitted[1]-1,dataPropostaSplitted[2]);
		}
		
		var dataAssegnazioneParsed = null;
		if(checkIsNotNull(dataAssegnazione)){
			var dataAssegnazioneSplitted = dataAssegnazione.split("-");
			dataAssegnazioneParsed = new Date(dataAssegnazioneSplitted[0],dataAssegnazioneSplitted[1]-1,dataAssegnazioneSplitted[2]);
		}
		
		var dataAnnulloParsed = null;
		if(checkIsNotNull(dataAnnullo)){
			var dataAnnulloSplitted = dataAnnullo.split("-");
			dataAnnulloParsed = new Date(dataAnnulloSplitted[0],dataAnnulloSplitted[1]-1,dataAnnulloSplitted[2]);
		}
		
		commissioneNode.properties["crlatti:dataAssegnazioneCommissione"] = dataAssegnazioneParsed;
		commissioneNode.properties["crlatti:dataAnnulloCommissione"] = dataAnnulloParsed;
		commissioneNode.properties["crlatti:dataPropostaCommissione"] = dataPropostaParsed;
		commissioneNode.properties["crlatti:ruoloCommissione"] = ruolo;
		commissioneNode.properties["crlatti:statoCommissione"] = stato;
		commissioneNode.save();
		
		
		attoNode.setPermission("Coordinator", "GROUP_"+descrizione);
		
	}
	
	//verifica commissioni da cancellare
	var commissioniNelRepository = commissioniFolderNode.getChildAssocsByType("crlatti:commissione");
	
	//query nel repository per capire se bisogna cancellare delle commissioni
	for(var z=0; z<commissioniNelRepository.length; z++){
		var trovato = false;
		var commissioneNelRepository = commissioniNelRepository[z];
		
		//cerco il nome della commissione nel repo all'interno del json
		for (var q=0; q<numeroCommissioni; q++){
			var commissione = commissioni.get(q).get("commissione");
			var descrizione = filterParam(commissione.get("descrizione"));
			if(""+descrizione+""==""+commissioneNelRepository.name+""){
				trovato = true;
				break
			}
		}
		if(!trovato){
			var commissioniSpace = commissioneNelRepository.parent;
			commissioneNelRepository.remove();
			/*
			 * in eliminazione le commissioni devono essere gestite manualmente 
			 * a causa di un bug di Alfresco risolto nella 4.1.1 riguardo le regole in outbound:
			 * 
			 * https://issues.alfresco.com/jira/browse/ALF-12711
			 * 
			*/
			
			//Commissioni referenti
			var commissioniReferentiLuceneQuery = 
				"PARENT:\""+commissioniSpace.nodeRef+"\" AND @crlatti\\:ruoloCommissione:\"Referente\"";
			
		    var commissioniReferentiResults = search.luceneSearch(commissioniReferentiLuceneQuery);		
			var commissioniReferentiAtto = new Array(commissioniReferentiResults.length);
			for (var r=0; r<commissioniReferentiResults.length; r++) {
				commissioniReferentiAtto[r] = commissioniReferentiResults[r].name;
			}
			
			//Commissioni consultive
			var commissioniConsultiveLuceneQuery = 
				"PARENT:\""+commissioniSpace.nodeRef+"\" AND @crlatti\\:ruoloCommissione:\"Consultiva\"";
			
		    var commissioniConsultiveResults = search.luceneSearch(commissioniConsultiveLuceneQuery);		
			var commissioniConsultiveAtto = new Array(commissioniConsultiveResults.length);
			for (var r=0; r<commissioniConsultiveResults.length; r++) {
				commissioniConsultiveAtto[r] = commissioniConsultiveResults[r].name;
			}
			
			attoNode.properties["crlatti:commReferente"] = commissioniReferentiAtto;
			attoNode.properties["crlatti:commConsultiva"] = commissioniConsultiveAtto;
			attoNode.save();
		}
	}
	
	//gestione pareri e ridondanza (in eliminazione) di organismiStatutari
	var pareriXPathQuery = "*[@cm:name='Pareri']";
	var pareriFolderNode = attoNode.childrenByXPath(pareriXPathQuery)[0];
	
	var numeroPareri = pareri.length();
	for (var j=0; j<numeroPareri; j++){
		var parere = pareri.get(j).get("parere");
		var descrizione = filterParam(parere.get("descrizione"));
		var dataAssegnazione = filterParam(parere.get("dataAssegnazione"));
		var dataAnnullo = filterParam(parere.get("dataAnnullo"));
		var obbligatorio = filterParam(parere.get("obbligatorio"));
		
		//verifica l'esistenza del parere all'interno del folder Pareri
		var existParereXPathQuery = "*[@cm:name='"+descrizione+"']";
		var parereEsistenteResults = pareriFolderNode.childrenByXPath(existParereXPathQuery);
		var parereNode = null;
		if(parereEsistenteResults!=null && parereEsistenteResults.length>0){
			parereNode = parereEsistenteResults[0];
		} else {
			parereNode = pareriFolderNode.createNode(descrizione,"crlatti:parere");
		}
		
		var dataAssegnazioneParsed = null;
		if(checkIsNotNull(dataAssegnazione)){
			var dataAssegnazioneSplitted = dataAssegnazione.split("-");
			dataAssegnazioneParsed = new Date(dataAssegnazioneSplitted[0],dataAssegnazioneSplitted[1]-1,dataAssegnazioneSplitted[2]);
		}
		
		var dataAnnulloParsed = null;
		if(checkIsNotNull(dataAnnullo)){
			var dataAnnulloSplitted = dataAnnullo.split("-");
			dataAnnulloParsed = new Date(dataAnnulloSplitted[0],dataAnnulloSplitted[1]-1,dataAnnulloSplitted[2]);
		}
		
		parereNode.properties["crlatti:organismoStatutarioParere"] = descrizione;
		parereNode.properties["crlatti:dataAssegnazioneParere"] = dataAssegnazioneParsed;
		parereNode.properties["crlatti:dataAnnulloParere"] = dataAnnulloParsed;
		parereNode.properties["crlatti:obbligatorio"] = obbligatorio;
		parereNode.save();
	}
	
	//verifica pareri da cancellare
	var pareriNelRepository = pareriFolderNode.getChildAssocsByType("crlatti:parere");
	
	//query nel repository per capire se bisogna cancellare dei pareri
	for(var z=0; z<pareriNelRepository.length; z++){
		var trovato = false;
		var parereNelRepository = pareriNelRepository[z];
		
		//cerco il nome del parere nel repo all'interno del json
		for (var q=0; q<numeroPareri; q++){
			var parere = pareri.get(q).get("parere");
			var descrizione = filterParam(parere.get("descrizione"));
			if(""+descrizione+""==""+parereNelRepository.name+""){
				trovato = true;
				break
			}
		}
		if(!trovato){
			var pareriSpace = parereNelRepository.parent;
			parereNelRepository.remove();
			/*
			 * in eliminazione le commissioni devono essere gestite manualmente 
			 * a causa di un bug di Alfresco risolto nella 4.1.1 riguardo le regole in outbound:
			 * 
			 * https://issues.alfresco.com/jira/browse/ALF-12711
			 * 
			*/
			
			var pareriLuceneQuery = 
				"PARENT:\""+pareriSpace.nodeRef+"\" AND TYPE:\"crlatti:parere\"";
			
		    var pareriResults = search.luceneSearch(pareriLuceneQuery);		
			var pareriAtto = new Array(pareriResults.length);
			for (var r=0; r<pareriResults.length; r++) {
				pareriAtto[r] = pareriResults[r].name;
			}
			
			attoNode.properties["crlatti:organismiStatutari"] = pareriAtto;
			attoNode.save();
		}
	}
	
	
	model.atto = attoNode;
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}