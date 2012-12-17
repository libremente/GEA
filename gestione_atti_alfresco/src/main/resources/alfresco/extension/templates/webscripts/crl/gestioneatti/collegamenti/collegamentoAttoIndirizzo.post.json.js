<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");

var collegamenti = atto.get("collegamenti");

if(checkIsNotNull(id)){
	
	var attoFolderNode = utils.getNodeFromString(id);
	
	var collegamentiXPathQuery = "*[@cm:name='Collegamenti']";
	var collegamentiFolderNode = attoFolderNode.childrenByXPath(collegamentiXPathQuery)[0];
	
	var attoIndirizzoXPathQuery = "*[@cm:name='AttiIndirizzoSindacatoIspettivo']";
	var attoIndirizzoFolderNode = attoNode.collegamentiFolderNode(attoIndirizzoXPathQuery)[0];
	
	
	var numeroCollegamenti = collegamenti.length();
	
	for (var j=0; j<numeroCollegamenti; j++){
	
		var collegamento = collegamenti.get(j).get("collegamento");
	
		var idAttoCollegato = filterParam(collegamento.get("idAttoCollegato"));
		var note = filterParam(collegamento.get("note"));
			
		var attoCollegatoFolderNode = utils.getNodeFromString(idAttoCollegato);
		
		
		//verifica l'esistenza del collegamento all'interno del folder Collegamenti/Interni
		var existCollegamentoXPathQuery = "*[@cm:name='"+attoCollegatoFolderNode.name+"']";
		var collegamentoEsistenteResults = interniFolderNode.childrenByXPath(existCollegamentoXPathQuery);
		var collegamentoNode = null;
		
		var creaAssociazione = true;
		
		if(collegamentoEsistenteResults!=null && collegamentoEsistenteResults.length>0){
			collegamentoNode = collegamentoEsistenteResults[0];
			creaAssociazione = false;
		} else {
			collegamentoNode = interniFolderNode.createNode(attoCollegatoFolderNode.name,"crlatti:collegamento");
		}
	
		//aggiornamento dei metadati
		
		collegamentoNode.properties["crlatti:note"] = note;


		if(creaAssociazione){
			collegamentoNode.createAssociation(attoCollegatoFolderNode,"crlatti:attoAssociatoCollegamento");
		}
		
		collegamentoNode.save();
		
	}
	
	
} else {
	status.code = 400;
	status.message = "collegamento non valorizzato correttamente: idAtto e idAttoCollegamento per ogni atto da collegare, sono obbligatori";
	status.redirect = true;
}
