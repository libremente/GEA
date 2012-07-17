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