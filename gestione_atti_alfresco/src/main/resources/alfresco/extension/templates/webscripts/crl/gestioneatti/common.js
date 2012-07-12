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
			&& parameterValue!=""){
		return true;
	} else {
		return false;
	}
}