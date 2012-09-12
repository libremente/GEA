<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var statoAtto = atto.get("stato");
var commissioni = atto.get("commissioni");

var commissioneUtente = json.get("target").get("commissione");

var relatori;
var statoCommissione;

// prendo i valori delle proprietˆ dalla commissione target
for(var i=0; i<commissioni.length(); i++) {
	var commissioneTemp = commissioni.get(i).get("commissione");
	
	if(""+commissioneTemp.get("descrizione")+"" == ""+commissioneUtente+"") {
		relatori = commissioneTemp.get("relatori");
		statoCommissione = commissioneTemp.get("stato");
	}
	
}



if(checkIsNotNull(id)){
	var attoNode = utils.getNodeFromString(id);
	var commissioneFolderNode = null;
	
	//cerco la commissione di riferimento dell'utente corrente
	var commissioniXPathQuery = "*[@cm:name='Commissioni']";
	var commissioniFolderNode = attoNode.childrenByXPath(commissioniXPathQuery)[0];
	
	if(checkIsNotNull(commissioneUtente)){
		var commissioneUtenteXPathQuery = "*[@cm:name='"+commissioneUtente+"']";
		var commissioneUtenteResults = commissioniFolderNode.childrenByXPath(commissioneUtenteXPathQuery);
		if(commissioneUtenteResults!=null && commissioneUtenteResults.length>0){
			commissioneFolderNode = commissioneUtenteResults[0];
			
			// stato della commissione
			commissioneFolderNode.properties["crlatti:statoCommissione"] = statoCommissione;	
			commissioneFolderNode.save();
		} else {
			status.code = 400;
			status.message = "commissione utente non trovata";
			status.redirect = true;
		}
	} 
	
	
	
	var relatoriXPathQuery = "*[@cm:name='Relatori']";
	var relatoriFolderNode = commissioneFolderNode.childrenByXPath(relatoriXPathQuery)[0];
	
	var numeroRelatori = relatori.length();
	//relatori
	
	for(var k=0; k<numeroRelatori; k++) {
		
		var relatore = relatori.get(k).get("relatore");
	
		var descrizione = filterParam(relatore.get("descrizione"));
		var dataNomina = filterParam(relatore.get("dataNomina"));
		var dataUscita = filterParam(relatore.get("dataUscita"));
		
		//verifica l'esistenza di un relatore nel repository
		var existRelatoreXPathQuery = "*[@cm:name='"+descrizione+"']";
		var relatoreEsistenteResults = relatoriFolderNode.childrenByXPath(existRelatoreXPathQuery);
		var relatoreNode = null;
		if(relatoreEsistenteResults!=null && relatoreEsistenteResults.length>0){
			relatoreNode = relatoreEsistenteResults[0];
		} else {
			relatoreNode = relatoriFolderNode.createNode(descrizione,"crlatti:relatore");
			relatoreNode.content = descrizione;
		}
		
		var dataNominaParsed = null;
		if(checkIsNotNull(dataNomina)){
			var dataNominaSplitted = dataNomina.split("-");
			dataNominaParsed = new Date(dataNominaSplitted[0],dataNominaSplitted[1]-1,dataNominaSplitted[2]);
		}
		
		var dataUscitaParsed = null;
		if(checkIsNotNull(dataUscita)){
			var dataUscitaSplitted = dataUscita.split("-");
			dataUscitaParsed = new Date(dataUscitaSplitted[0],dataUscitaSplitted[1]-1,dataUscitaSplitted[2]);
		}
		
		relatoreNode.properties["crlatti:dataNominaRelatore"] = dataNominaParsed;
		relatoreNode.properties["crlatti:dataUscitaRelatore"] = dataUscitaParsed;
		relatoreNode.save();
		
	}	
	
	//verifica relatori da cancellare
	var relatoriNelRepository = relatoriFolderNode.getChildAssocsByType("crlatti:relatore");
	
		
	//query nel repository per capire se bisogna cancellare alcuni relatori
	for(var z=0; z<relatoriNelRepository.length; z++){
		var trovato = false;
		var relatoreNelRepository = relatoriNelRepository[z];
		
		//cerco il nome del relatore nel repo all'interno del json
		for (var q=0; q<relatori.length(); q++){
			var relatore = relatori.get(q).get("relatore");
			var descrizione = filterParam(relatore.get("descrizione"));
			if(""+descrizione+""==""+relatoreNelRepository.name+""){
				trovato = true;
				break
			}
		}
		if(!trovato){
			var relatoriSpace = relatoreNelRepository.parent;
			relatoreNelRepository.remove();
			
			
		}
	}
	
	
	/*
	 * in eliminazione devono essere gestite manualmente 
	 * a causa di un bug di Alfresco risolto nella 4.1.1 riguardo le regole in outbound:
	 * 
	 * https://issues.alfresco.com/jira/browse/ALF-12711
	 * 
	*/
	
	// modifico la proprietÃ  relatori dell'atto riciclando tutte le commissioni e i relativi relatori
	
	var commissioniNodes = commissioniFolderNode.getChildAssocsByType("crlatti:commissione");
	var numeroCommissioni = commissioniNodes.length;
	
	var commRelatoriCommissioni = new Array();
	
	for(var i=0; i<numeroCommissioni; i++) {
		
		var commissioneTempNode = commissioniNodes[i];
		var relatoriCommissioneXPathQuery = "*[@cm:name='Relatori']";
		var relatoriCommissioneFolderNode = commissioneTempNode.childrenByXPath(relatoriCommissioneXPathQuery)[0];
		
		var relatoriNode = relatoriCommissioneFolderNode.getChildAssocsByType("crlatti:relatore");
		
		// considero i relatori di ogni commissione
		var numeroRelatori = relatoriNode.length;
		for(var j=0; j<numeroRelatori; j++) {
			commRelatoriCommissioni.push(relatoriNode[j].properties["cm:name"]);	
		}
	}

	attoNode.properties["crlatti:relatori"] = commRelatoriCommissioni;
	
	
	
	// stato dell'atto
	attoNode.properties["crlatti:statoAtto"] = statoAtto;
	attoNode.save();
	
	
	model.atto = attoNode;
	model.commissione = commissioneFolderNode;
	model.relatori = relatoriFolderNode.getChildAssocsByType("crlatti:relatore");
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}