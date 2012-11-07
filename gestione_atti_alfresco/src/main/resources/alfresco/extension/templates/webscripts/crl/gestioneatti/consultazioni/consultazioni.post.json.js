<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");

var consultazioni = atto.get("consultazioni");



if(checkIsNotNull(id)){
	
	var attoNode = utils.getNodeFromString(id);
	
	var consultazioniXPathQuery = "*[@cm:name='Consultazioni']";
	var consultazioniFolderNode = attoNode.childrenByXPath(consultazioniXPathQuery)[0];
	
	var numeroConsultazioni = consultazioni.length();
	
	for (var i=0; i<numeroConsultazioni; i++){
	
		var consultazione = consultazioni.get(i).get("consultazione");
	
		var descrizione = filterParam(consultazione.get("descrizione"));
		var dataSeduta = filterParam(consultazione.get("dataSeduta"));
		var dataConsultazione =  filterParam(consultazione.get("dataConsultazione"));
		var prevista =  filterParam(consultazione.get("prevista"));
		var discussa =  filterParam(consultazione.get("discussa"));
		var note =  filterParam(consultazione.get("note"));
		
		var commissione =  filterParam(consultazione.get("commissione"));
		
			
		
			
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
			
			// creo la seduta con la data della consultazione per la commissione corrente
			

			var gestioneSedutePath = "/app:company_home"+
			"/cm:"+search.ISO9075Encode("CRL")+
			"/cm:"+search.ISO9075Encode("Gestione Atti")+
			"/cm:"+search.ISO9075Encode("Sedute");


			if(checkIsNotNull(commissione) 
					&& checkIsNotNull(dataSeduta)){
				
				var dataCreazioneSedutaSplitted = dataSeduta.split("-");
				var dataCreazioneSedutaParsed = new Date(dataCreazioneSedutaSplitted[0],dataCreazioneSedutaSplitted[1]-1,dataCreazioneSedutaSplitted[2]);	
				
				var anno = dataCreazioneSedutaSplitted[0]
				var mese = dataCreazioneSedutaSplitted[1];

				//creazione spazio per la provenienza (commissione o aula)
				var provenienzaPath = gestioneSedutePath + "/cm:"+search.ISO9075Encode(commissione);
				var provenienzaLuceneQuery = "PATH:\""+provenienzaPath+"\"";
				var provenienzaResults = search.luceneSearch(provenienzaLuceneQuery);

				var provenienzaFolderNode = null;
				if(provenienzaResults!=null && provenienzaResults.length>0){
					provenienzaFolderNode = provenienzaResults[0];
				} else {
					var gestioneSeduteLuceneQuery = "PATH:\""+gestioneSedutePath+"\"";
					var gestioneSeduteFolderNode = search.luceneSearch(gestioneSeduteLuceneQuery)[0];
					provenienzaFolderNode = gestioneSeduteFolderNode.createFolder(provenienza);
					
					// setting dei permessi sul nodo in base alla provenienza
					provenienzaFolderNode.setPermission("Coordinator", "GROUP_"+commissione);
				}

				//creazione spazio anno
				var annoPath = provenienzaPath + "/cm:" + search.ISO9075Encode(anno);
				var annoLuceneQuery = "PATH:\""+annoPath+"\"";
				var annoResults = search.luceneSearch(annoLuceneQuery);
				var annoFolderNode = null;
				if(annoResults!=null && annoResults.length>0){
					annoFolderNode = annoResults[0];
				} else {
					annoFolderNode = provenienzaFolderNode.createFolder(anno);
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


				//verifica esistenza del folder della seduta
				var sedutaPath = mesePath + "/cm:" + search.ISO9075Encode(dataSeduta);
				var sedutaLuceneQuery = "PATH:\""+sedutaPath+"\"";
				var sedutaResults = search.luceneSearch(sedutaLuceneQuery);
				if(sedutaResults!=null && sedutaResults.length>0){
					sedutaFolderNode = sedutaResults[0];
					
				} else {
					//creazione del nodo
					var sedutaSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Seduta\"";
					var sedutaSpaceTemplateNode = search.luceneSearch(sedutaSpaceTemplateQuery)[0];
					var sedutaFolderNode = sedutaSpaceTemplateNode.copy(meseFolderNode,true);
					sedutaFolderNode.name = dataSeduta;
				}
					
			
				sedutaFolderNode.properties["crlatti:dataSedutaSedutaODG"] = dataCreazioneSedutaParsed;				
				sedutaFolderNode.save();
				
				
				var attiXPathQuery = "*[@cm:name='AttiTrattati']";
				var attiFolderNode = sedutaFolderNode.childrenByXPath(attiXPathQuery)[0];	
				
				// Inserimento atto trattato
					
				var attoTrattatoFolderNode = utils.getNodeFromString(id);
				
				//verifica l'esistenza dell'atto all'interno del folder AttiTrattati
				var existAttoTrattatoXPathQuery = "*[@cm:name='"+attoTrattatoFolderNode.name+"']";
				var attoTrattatoEsistenteResults = attiFolderNode.childrenByXPath(existAttoTrattatoXPathQuery);
				
				var attoTrattatoNode = null;
				
				var creaAssociazione = true;
				
				if(attoTrattatoEsistenteResults!=null && attoTrattatoEsistenteResults.length>0){
					attoTrattatoNode = attoTrattatoEsistenteResults[0];
					creaAssociazione = false;
				} else {
					attoTrattatoNode = attiFolderNode.createNode(attoTrattatoFolderNode.name,"crlatti:attoTrattatoODG");
				}
			
				

				if(creaAssociazione){
					attoTrattatoNode.createAssociation(attoTrattatoFolderNode,"crlatti:attoTrattatoSedutaODG");
				}
				
				attoTrattatoNode.save();
				
				
			}
		}
	
		
		
		if(checkIsNotNull(dataSeduta)) {
			var dataSedutaSplitted = dataSeduta.split("-");
			var dataSedutaParsed = new Date(dataSedutaSplitted[0],dataSedutaSplitted[1]-1,dataSedutaSplitted[2]);	
			consultazioneNode.properties["crlatti:dataSedutaConsultazione"] = dataSedutaParsed;
		}else {
			consultazioneNode.properties["crlatti:dataSedutaConsultazione"] = null;
		}
		
		if(checkIsNotNull(dataConsultazione)) {
			var dataConsultazioneSplitted = dataConsultazione.split("-");
			var dataConsultazioneParsed = new Date(dataConsultazioneSplitted[0],dataConsultazioneSplitted[1]-1,dataConsultazioneSplitted[2]);
			consultazioneNode.properties["crlatti:dataConsultazione"] = dataConsultazioneParsed;
		}else {
			consultazioneNode.properties["crlatti:dataConsultazione"] = null;
		}
		
		consultazioneNode.properties["crlatti:previstaConsultazione"] = prevista;
		consultazioneNode.properties["crlatti:discussaConsultazione"] = discussa;
		consultazioneNode.properties["crlatti:noteConsultazione"] = note;
		consultazioneNode.properties["crlatti:commissioneConsultazione"] = commissione;
		
		
		consultazioneNode.save();
		
		var soggettiInvitati = consultazione.get("soggettiInvitati");
		
		var soggettiInvitatiPathQuery = "*[@cm:name='SoggettiInvitati']";
		var soggettiInvitatiFolderNode = consultazioneNode.childrenByXPath(soggettiInvitatiPathQuery)[0];
		
		var numeroSoggettiInvitati = soggettiInvitati.length();
	
		for (var j=0; j<numeroSoggettiInvitati; j++){
		
			var soggettoInvitato = soggettiInvitati.get(j).get("soggettoInvitato");
		
			var descrizione = filterParam(soggettoInvitato.get("descrizione"));
			var intervenuto = filterParam(soggettoInvitato.get("intervenuto"));
				
			//verifica l'esistenza della consultazione all'interno del folder Consultazioni
			var existSogettoXPathQuery = "*[@cm:name='"+descrizione+"']";
			var soggettoNodeEsistenteResults = soggettiInvitatiFolderNode.childrenByXPath(existSogettoXPathQuery);
			var soggettoNode = null;
			if(soggettoNodeEsistenteResults!=null && soggettoNodeEsistenteResults.length>0){
				soggettoNode = soggettoNodeEsistenteResults[0];
			} else {
				soggettoNode = soggettiInvitatiFolderNode.createNode(descrizione,"crlatti:soggettoInvitato");
			}
		
			soggettoNode.properties["crlatti:descrizioneSoggettoInvitato"] = descrizione;
			soggettoNode.properties["crlatti:intervenutoSoggettoInvitato"] = intervenuto;
			soggettoNode.save();
			
		}
		
		
		//verifica soggetti da cancellare
		var soggettiNelRepository = soggettiInvitatiFolderNode.getChildAssocsByType("crlatti:soggettoInvitato");
		
			
		//query nel repository per capire se bisogna cancellare alcuni soggetti
		for(var z=0; z<soggettiNelRepository.length; z++){
			var trovato = false;
			var soggettoNelRepository = soggettiNelRepository[z];
			
			//cerco il nome del soggetto nel repo all'interno del json
			for (var q=0; q<soggettiInvitati.length(); q++){
				var soggetto = soggettiInvitati.get(q).get("soggettoInvitato");
				var descrizione = filterParam(soggetto.get("descrizione"));
				if(""+descrizione+""==""+soggettoNelRepository.name+""){
					trovato = true;
					break
				}
			}
			if(!trovato){
				soggettoNelRepository.remove();
			}
		}
		
		
		
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