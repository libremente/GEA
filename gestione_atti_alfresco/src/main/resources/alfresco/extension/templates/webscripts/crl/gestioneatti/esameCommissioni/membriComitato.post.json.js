<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var commissioneUtente = atto.get("commissione");

//vettori
var membri = atto.get("membriComitatoRistretto");

//reperimento informazioni commissione dell'utente che sta effettuando l'operazione?
//devo prelevare a quale commissione appartiente l'utente corrente?
//Oppure esiste un utente speciale che potr� selezionare la commissione?
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
		var commissioneUtenteXPathQuery = "*[@cm:name='"+commissioneUtente+"']";
		var commissioneUtenteResults = commissioniFolderNode.childrenByXPath(commissioneUtenteXPathQuery);
		if(commissioneUtenteResults!=null && commissioneUtenteResults.length>0){
			commissioneFolderNode = commissioneUtenteResults[0];
		} else {
			status.code = 400;
			status.message = "commissione utente non trovata";
			status.redirect = true;
		}
	} 
	
	

	var comitatoXPathQuery = "*[@cm:name='ComitatoRistretto']";
	var comitatoFolderNode = commissioneFolderNode.childrenByXPath(comitatoXPathQuery)[0];
	
	var numeroMembri = membri.length();
	
	
	for(var k=0; k<numeroMembri; k++) {
		
		var membro = membri.get(k).get("membro");
	
		var descrizione = filterParam(membro.get("descrizione"));
		var dataNomina = filterParam(membro.get("dataNomina"));
		var dataUscita = filterParam(membro.get("dataUscita"));
		var coordinatore = filterParam(membro.get("coordinatore"));
		
		//verifica l'esistenza di un membro nel repository
		var existMemebroXPathQuery = "*[@cm:name='"+descrizione+"']";
		var memebroEsistenteResults = comitatoFolderNode.childrenByXPath(existMemebroXPathQuery);
		var membroNode = null;
		if(memebroEsistenteResults!=null && memebroEsistenteResults.length>0){
			membroNode = memebroEsistenteResults[0];
		} else {
			membroNode = comitatoFolderNode.createNode(descrizione,"crlatti:membroComitatoRistretto");
			membroNode.content = descrizione;
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
		
		membroNode.properties["crlatti:dataNominaMCR"] = dataNominaParsed;
		membroNode.properties["crlatti:dataUscitaMCR"] = dataUscitaParsed;
		membroNode.properties["crlatti:coordinatoreMCR"] = coordinatore;
		membroNode.save();
		
	}	
	
	//verifica membri da cancellare
	var membriNelRepository = comitatoFolderNode.getChildAssocsByType("crlatti:membroComitatoRistretto");
	
		
	//query nel repository per capire se bisogna cancellare alcuni membri
	for(var z=0; z<membriNelRepository.length; z++){
		var trovato = false;
		var membroNelRepository = membriNelRepository[z];
		
		//cerco il nome del memebro nel repo all'interno del json
		for (var q=0; q<membri.length(); q++){
			var membro = membri.get(q).get("membro");
			var descrizione = filterParam(membro.get("descrizione"));
			if(""+descrizione+""==""+membroNelRepository.name+""){
				trovato = true;
				break
			}
		}
		if(!trovato){
			var comitatoSpace = membroNelRepository.parent;
			membroNelRepository.remove();
			
		}
	}
	
	model.atto = attoNode;
	model.commissione = commissioneFolderNode;
	model.membri = comitatoFolderNode.getChildAssocsByType("crlatti:membroComitatoRistretto");
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}