function verifyAND(luceneQuery){
	if(luceneQuery!=""){
		return luceneQuery += " AND ";
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
		var passaggioTemp = passaggi.get(i).get("passaggio");
		if(""+passaggioTemp.get("nome") == ""+passaggio+"") {
		
			passaggioTarget = passaggioTemp;
		}
	}
	
	return passaggioTarget;
}