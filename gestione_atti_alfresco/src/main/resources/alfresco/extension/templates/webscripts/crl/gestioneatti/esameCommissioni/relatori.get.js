<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var idAtto = args.idAtto;
var commissione = args.commissione


if(checkIsNotNull(idAtto) && checkIsNotNull(commissione)){
	var attoNode = utils.getNodeFromString(idAtto);
	var commissioneFolderNode = null;
	
	// cerco la cartella commissioni dell'atto
	var commissioniXPathQuery = "*[@cm:name='Commissioni']";
	var commissioniFolderNode = attoNode.childrenByXPath(commissioniXPathQuery)[0];
	
	// cerco la commissione
	var commissioneXPathQuery = "*[@cm:name='"+commissione+"']";
	var commissioneResults = commissioniFolderNode.childrenByXPath(commissioneXPathQuery);
	
	if(commissioneResults!=null && commissioneResults.length>0){
		commissioneFolderNode = commissioneResults[0];
		
		var relatoriXPathQuery = "*[@cm:name='Relatori']";
		var relatoriCommissioneFolderNode = commissioneFolderNode.childrenByXPath(relatoriXPathQuery)[0];
		
		var relatoriNode = relatoriCommissioneFolderNode.getChildAssocsByType("crlatti:relatore");
			
		model.atto = attoNode;
		model.commissione = commissioneFolderNode;
		model.relatori = relatoriNode;

			
	} else {
		status.code = 400;
		status.message = "commissione utente non trovata";
		status.redirect = true;
	}
	
	
} else {
		
	status.code = 400;
	status.message = "commissione utente non trovata";
	status.redirect = true;	
}