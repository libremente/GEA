<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var classificazione = atto.get("classificazione");
var oggetto = atto.get("oggetto");
var numeroRepertorio = atto.get("numeroRepertorio");
var dataRepertorio = atto.get("dataRepertorio");
var tipoIniziativa = atto.get("tipoIniziativa");
var dataIniziativa = atto.get("dataIniziativa");
var descrizioneIniziativa = atto.get("descrizioneIniziativa");
var assegnazione = atto.get("assegnazione");
var numeroDgr = atto.get("numeroDgr");
var dataDgr = atto.get("dataDgr");

if(checkIsNotNull(id)){
	var attoNode = utils.getNodeFromString(id);
	attoNode.properties["crlatti:classificazione"] = classificazione;
	attoNode.properties["crlatti:oggetto"] = oggetto;
	attoNode.properties["crlatti:numeroRepertorio"] = numeroRepertorio;
	
	if(checkIsNotNull(dataRepertorio)){
		var dataRepertorioSplitted = dataRepertorio.split("-");
		var dataRepertorioParsed = new Date(dataRepertorioSplitted[0],dataRepertorioSplitted[1]-1,dataRepertorioSplitted[2]);
		attoNode.properties["crlatti:dataRepertorio"] = dataRepertorioParsed;
	}
	
	attoNode.properties["crlatti:tipoIniziativa"] = tipoIniziativa;
	
	if(checkIsNotNull(dataIniziativa)){
		var dataIniziativaSplitted = dataIniziativa.split("-");
		var dataIniziativaParsed = new Date(dataIniziativaSplitted[0],dataIniziativaSplitted[1]-1,dataIniziativaSplitted[2]);
		attoNode.properties["crlatti:dataIniziativa"] = dataIniziativaParsed;
	}
	
	attoNode.properties["crlatti:descrizioneIniziativa"] = descrizioneIniziativa;
	attoNode.properties["crlatti:assegnazione"] = assegnazione;
	
	if(attoNode.typeShort!="crlatti:attoInp"){
		//campi DGR non attivi solo per INP
		attoNode.properties["crlatti:numeroDgr"] = numeroDgr;
		if(checkIsNotNull(dataDgr)){
			var dataDgrSplitted = dataDgr.split("-");
			var dataDgrParsed = new Date(dataDgrSplitted[0],dataDgrSplitted[1]-1,dataDgrSplitted[2]);
			attoNode.properties["crlatti:dataDgr"] = dataDgrParsed;
		}
	}
	attoNode.save();
	model.atto = attoNode;
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}