<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var commissioni = atto.get("commissioni");
var pareri = atto.get("pareri");

var firmatari = atto.get("firmatari");

if(checkIsNotNull(id)){
	var attoNode = utils.getNodeFromString(id);
	
	//gestione commissioni e ridondanza (in eliminazione) di commissioni referenti e consultive
	var commissioniXPathQuery = "*[@cm:name='Commissioni']";
	var commissioniFolderNode = attoNode.childrenByXPath(commissioniXPathQuery)[0];
	
	var numeroCommissioni = commissioni.length();
	for (var j=0; j<numeroCommissioni; j++){
		var commissione = commissioni.get(j).get("commissione");
		var descrizione = filterParam(commissione.get("descrizione"));
		var dataProposta = filterParam(commissione.get("dataProposta"));
		var dataAssegnazione = filterParam(commissione.get("dataFirma"));
		var ruolo = filterParam(commissione.get("dataRitiro"));
		var stato = filterParam(commissione.get("primoFirmatario"));
		var dataAnnullo = filterParam(commissione.get("dataAnnullo"));
		
		//verifica l'esistenza della commissione all'interno del folder Commissioni
		var existCommissioneXPathQuery = "*[@cm:name='"+descrizione+"']";
		var commissioneEsistenteResults = commissioniFolderNode.childrenByXPath(existCommissioneXPathQuery);
		var commissioneNode = null;
		if(commissioneEsistenteResults!=null && commissioneEsistenteResults.length>0){
			commissioneNode = commissioneEsistenteResults[0];
		} else {
			commissioneNode = commissioniFolderNode.createNode(descrizione,"crlatti:commissione");
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
		commissioneNode.properties["crlatti:dataPresaInCaricoCommissione"] = dataPropostaParsed;
		commissioneNode.properties["crlatti:ruoloCommissione"] = ruolo;
		commissioneNode.properties["crlatti:statoCommissione"] = stato;
		commissioneNode.save();
	}
	
	//verifica firmatari da cancellare
	var commissioniNelRepository = commissioniFolderNode.getChildAssocsByType("crlatti:commissione");
	
	//query nel repository per capire se bisogna cancellare delle commissioni
	for(var z=0; z<commissioniNelRepository.length; z++){
		var trovato = false;
		var commissioneNelRepository = commissioniNelRepository[z];
		
		//cerco il nome del firmatario nel repo all'interno del json
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
			//var commissioniNelloSpazio = commissioniSpace.getChildAssocsByType("crlatti:commissione");
			
			//Commissioni referenti
			var commissioniReferentiLuceneQuery = 
				"PARENT:\""+commissioneSpace.nodeRef+"\" AND @crlatti\\:ruoloCommissione:\"Referente\"":
			
		    var commissioniReferentiResults = search.luceneSearch(commissioniReferentiLuceneQuery);		
			var commissioniReferentiAtto = new Array(commissioniReferentiResults.length);
			for (var r=0; r<commissioniReferentiResults.length; r++) {
				commissioniReferentiAtto[r] = commissioniReferentiResults[r].name;
			}
			
			//Commissioni consultive
			var commissioniConsultiveLuceneQuery = 
				"PARENT:\""+commissioneSpace.nodeRef+"\" AND @crlatti\\:ruoloCommissione:\"Consultiva\"":
			
		    var commissioniConsultiveResults = search.luceneSearch(commissioniConsultiveLuceneQuery);		
			var commissioniConsultiveAtto = new Array(commissioniConsultiveResults.length);
			for (var r=0; r<commissioniConsultiveResults.length; r++) {
				commissioniConsultiveAtto[r] = commissioniConsultiveResults[r].name;
			}
			
			var attoNode = commissioniSpace.parent;
			attoNode.properties["crlatti:commReferente"] = commissioniReferentiAtto;
			attoNode.properties["crlatti:commConsultiva"] = commissioniConsultiveAtto;
			attoNode.save();
		}
	}
	
	//gestione pareri e ridondanza (in eliminazione) di organismiStatutari
	
	
	
	model.atto = attoNode;
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}