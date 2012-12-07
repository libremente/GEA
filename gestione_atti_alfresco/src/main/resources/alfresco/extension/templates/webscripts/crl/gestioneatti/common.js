function verifyAND(luceneQuery){
	if(luceneQuery!=""){
		return luceneQuery += " AND ";
	} else {
		return "";
	}
}

function verifyOR(luceneQuery){
	if(luceneQuery!=""){
		return luceneQuery += " OR ";
	} else {
		return "";
	}
}

function checkIsNotNull(parameterValue){
	if(parameterValue!=null
			&& parameterValue!=undefined
			&& parameterValue!=""
			&& parameterValue!="null"){
		return true;
	} else {
		return false;
	}
}

function checkIsNull(parameterValue){
	if(parameterValue==undefined 
			|| parameterValue==null 
			|| parameterValue==""
			|| parameterValue=="null"){
		return true;
	} else {
		return false;
	}
}

function filterParam(parameterValue){
	if(checkIsNotNull(parameterValue)){
		return parameterValue;
	} else {
		return "";
	}
}

function filterNumericParam(parameterValue){
	if(checkIsNotNull(parameterValue)){
		return parameterValue;
	} else {
		return 0;
	}
}



function getCommissioneTarget(json, passaggio, commissione){
	
	var passaggi = json.get("atto").get("passaggi");
	var commissioneTarget = null;
	
	// seleziono il passaggio corrente
	for(var i=0; i<passaggi.length(); i++) {
		var passaggioTemp = passaggi.get(i);
		if(""+passaggioTemp.get("nome") == ""+passaggio+"") {
		
			var commissioni = passaggioTemp.get("commissioni");
			
			// seleziono la commissione target
			for(var j=0; j<commissioni.length(); j++) {
				var commissioneTemp = commissioni.get(j);
				
				if(""+commissioneTemp.get("descrizione")+"" == ""+commissione+"") {
					
					commissioneTarget = commissioneTemp;
					
				}	
			}
		}
	}
	
	return commissioneTarget;
}


function getPassaggioTarget(json, passaggio){
	
	var passaggi = json.get("atto").get("passaggi");
	var passaggioTarget = null;
	
	// seleziono il passaggio corrente
	for(var i=0; i<passaggi.length(); i++) {
		var passaggioTemp = passaggi.get(i);
		if(""+passaggioTemp.get("nome") == ""+passaggio+"") {
		
			passaggioTarget = passaggioTemp;
		}
	}
	
	return passaggioTarget;
}

function getLastPassaggio(attoNodeRef){
	
	var passaggio;
	
	var passaggiXPathQuery = "*[@cm:name='Passaggi']";
	var passaggiFolderNode = attoNodeRef.childrenByXPath(passaggiXPathQuery)[0];
	
	var listaPassaggiXPathQuery = "*[starts-with(@cm:name,'Passaggio')]"
	var listaPassaggiFolderNode = passaggiFolderNode.childrenByXPath(listaPassaggiXPathQuery);
	
	var passaggioMax = 0;
	
	for(var i=0; i<listaPassaggiFolderNode.length; i++){
		
		var nomePassaggio = listaPassaggiFolderNode[i].name;
		numeroPassaggio = parseInt(nomePassaggio.substring(9));
		
		if(numeroPassaggio > passaggioMax) {
			passaggioMax = numeroPassaggio;
			passaggio = listaPassaggiFolderNode[i] ;
		}
	}
		
	return passaggio;
}

function canChangeStatoAtto(ruoloCommissione) {

	if (""+ruoloCommissione+"" == "Referente" || ""+ruoloCommissione+"" == "Deliberante" 
		|| ""+ruoloCommissione+"" == "Redigente" || ""+ruoloCommissione+"" == "Co-Referente"){
		
		return true;
	}else{
		return false;
	}

}

function getLegislaturaCorrente(){
	
	var legislaturaCorrente = null;
	
	var legislaturePath = "/app:company_home" +
	"/cm:"+search.ISO9075Encode("CRL") +
	"/cm:"+search.ISO9075Encode("Gestione Atti") +
	"/cm:"+search.ISO9075Encode("Anagrafica") +
	"/cm:"+search.ISO9075Encode("Legislature") + "/*";

	var luceneQuery = "PATH:\""+legislaturePath+"\" AND TYPE:\"crlatti:legislaturaAnagrafica\"";
	var legislatureResults = search.luceneSearch(luceneQuery);

	var legislatureArrayTemp = new Array();

	for(var i=0; i<legislatureResults.length; i++){
		if(legislatureResults[i].properties["crlatti:correnteLegislatura"] == true){
			legislatureArrayTemp.push(legislatureResults[i].properties["cm:name"]);
		}
	}
	
	if(legislatureArrayTemp.length>1){
		logger.error("Piu' di una legislatura attiva presente. Non e' stato possibile determinare la legislatura attiva.")
	}else if(legislatureArrayTemp.length==0){
		logger.error("Nessuna legislatura attiva presente.. Non e' stato possibile determinare la legislatura attiva.") 
	}else{
		legislaturaCorrente = legislatureArrayTemp[0];
	}
	
	return legislaturaCorrente;
	
}