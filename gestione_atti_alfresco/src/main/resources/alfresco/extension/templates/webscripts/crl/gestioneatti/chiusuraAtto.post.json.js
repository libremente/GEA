<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var atto = json.get("atto");
var id = atto.get("id");
var stato = atto.get("stato");

var dataChiusura = filterParam(atto.get("dataChiusura"));
var tipoChiusura = filterParam(atto.get("tipoChiusura"));
var noteChiusuraIter = filterParam(atto.get("noteChiusuraIter"));
var numeroLr = filterParam(atto.get("numeroLr"));
var dataLr = filterParam(atto.get("dataLR"));
var numeroPubblicazioneBURL = filterParam(atto.get("numeroPubblicazioneBURL"));
var dataPubblicazioneBURL = filterParam(atto.get("dataPubblicazioneBURL"));


if(atto!=null
		&& id!=null
		&& stato!=null) {
	
	var attoFolderNode = utils.getNodeFromString(id);
	
	var dataChiusuraSplitted = dataChiusura.split("-");
	var dataChiusuraParsed = new Date(dataChiusuraSplitted[0],dataChiusuraSplitted[1]-1,dataChiusuraSplitted[2]);
	attoFolderNode.properties["crlatti:dataChiusura"] = dataChiusuraParsed;
	
	attoFolderNode.properties["crlatti:tipoChiusura"] = tipoChiusura;
	attoFolderNode.properties["crlatti:noteChiusura"] = noteChiusuraIter;
	
	attoFolderNode.properties["crlatti:numeroLr"] = numeroLr;
	
	var dataLrSplitted = dataLr.split("-");
	var dataLrParsed = new Date(dataLrSplitted[0],dataLrSplitted[1]-1,dataLrSplitted[2]);
	attoFolderNode.properties["crlatti:dataLr"] = dataLrParsed;
	
	attoFolderNode.properties["crlatti:numeroPubblicazioneBURL"] = numeroPubblicazioneBURL;
	
	var dataPubblicazioneBURLSplitted = dataPubblicazioneBURL.split("-");
	var dataPubblicazioneBURLParsed = new Date(dataPubblicazioneBURLSplitted[0],dataPubblicazioneBURLSplitted[1]-1,dataPubblicazioneBURLSplitted[2]);
	attoFolderNode.properties["crlatti:dataPubblicazioneBURL"] = dataPubblicazioneBURLParsed;
	
	attoFolderNode.properties["crlatti:statoAtto"] = stato;
	attoFolderNode.save();
	
} else {
	status.code = 500;
	status.message = "atto, id, stato sono obbligatori";
	status.redirect = true;
}