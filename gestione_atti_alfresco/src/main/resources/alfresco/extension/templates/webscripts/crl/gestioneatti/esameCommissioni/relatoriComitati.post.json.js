<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var commissioneUtente = atto.get("commissione");
var dataPresaInCarico = atto.get("dataPresaInCarico");
var dataScadenza = atto.get("dataScadenza");
var materia = atto.get("materia");

//vettori
var relatori = atto.get("relatori");
var comitatoRistretto = atto.get("comitatoRistretto");

//reperimento informazioni commissione dell'utente che sta effettuando l'operazione?
//devo prelevare a quale commissione appartiente l'utente corrente?
//Oppure esiste un utente speciale che potrˆ selezionare la commissione?
var firstName = person.properties["cm:firstName"];
var lastName = person.properties["cm:lastName"];
var fullName = firstName + " " + lastName;


if(checkIsNotNull(id)){
	var attoNode = utils.getNodeFromString(id);
	var commissioneFolderNode = null;
	
	//cerco la commissione di riferimento dell'utente corrente
	var commissioniXPathQuery = "*[@cm:name='Commissioni']";
	var commissioniFolderNode = attoNode.childrenByXPath(commissioniXPathQuery)[0];
	
	if(checkIsNotNull(commissioneUtente)){
		var commissioneUtenteXPathQuery = "*[cm:name='"+commissioneUtente+"']";
		var commissioneUtenteResults = commissioniFolderNode.childrenByXPath(commissioneUtenteXPathQuery);
		if(commissioneUtenteResults!=null && commissioneUtenteResults.length>0){
			commissioneFolderNode = commissioneUtenteResults[0];
		} else {
			status.code = 400;
			status.message = "commissione utente non trovata";
			status.redirect = true;
		}
	} else {
		
		//stiamo presupponendo che il relatore sia la persona che pu˜ salvare i dati per tutta la commissione
		//stiamo inoltre presupponendo che nessun consigliere possa fare da relatore per pi di una commissione
		var commissioneUtenteLuceneQuery = 
			"PARENT:\""+commissioniFolderNode.nodeRef+"\" AND @crlatti\\:relatoriCommissione:\""+fullName+"\"";
		
		var commissioneNodeResults = search.luceneSearch(commissioneUtenteLuceneQuery);
		if(commissioneNodeResults!=null && commissioneNodeResults.length>0){
			commissioneFolderNode = commissioneNodeResults[0];
		} else {
			status.code = 400;
			status.message = "commissione utente non trovata";
			status.redirect = true;
		}
	}
	
	
	var dataPresaInCaricoParsed = null;
	if(checkIsNotNull(dataPresaInCarico)){
		var dataPresaInCaricoSplitted = dataPresaInCarico.split("-");
		dataPresaInCaricoParsed = new Date(dataPresaInCaricoSplitted[0],dataPresaInCaricoSplitted[1]-1,dataPresaInCaricoSplitted[2]);
	}
	
	var dataScadenzaParsed = null;
	if(checkIsNotNull(dataScadenza)){
		var dataScadenzaSplitted = dataScadenza.split("-");
		dataScadenzaParsed = new Date(dataScadenzaSplitted[0],dataScadenzaSplitted[1]-1,dataScadenzaSplitted[2]);
	}
	
	commissioneFolderNode.properties["crlatti:dataPresaInCaricoCommissione"] = dataPresaInCaricoParsed;
	commissioneFolderNode.properties["crlatti:materiaCommissione"] = materia;
	commissioneFolderNode.save();
	
	if(attoNode.hasAspect("crlatti:dataScadenzaAspect")){
		attoNode.properties["crlatti:dataScadenza"] = dataScadenzaParsed;
		attoNode.save();
	}
	
	var relatoriXPathQuery = "*[@cm:name='Relatori']";
	var relatoriFolderNode = commissioneFolderNode.childrenByXPath(relatoriXPathQuery)[0];
	
	//relatori
	for each (relatore in relatori){
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
	var relatoriNelRepository = relatoriFolderNode.getChildAssocsByType("crlatti:relatori");
	
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
			/*
			 * in eliminazione devono essere gestite manualmente 
			 * a causa di un bug di Alfresco risolto nella 4.1.1 riguardo le regole in outbound:
			 * 
			 * https://issues.alfresco.com/jira/browse/ALF-12711
			 * 
			*/
			
			var relatoriLuceneQuery = "PARENT:\""+relatoriSpace.nodeRef+"\" AND TYPE:\"crlatti:relatore\"";
		    var relatoriResults = search.luceneSearch(relatoriLuceneQuery);		
			var relatoriCommissione = new Array(relatoriResults.length);
			for (var r=0; r<relatoriResults.length; r++) {
				relatoriCommissione[r] = relatoriResults[r].name;
			}
			var commissioneFolderNode = relatoriSpace.parent;
			commissioneFolderNode.properties["crlatti:relatoriCommissione"] = relatoriCommissione;
			commissioneFolderNode.save();
		}
	}
	
	model.atto = attoNode;
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}