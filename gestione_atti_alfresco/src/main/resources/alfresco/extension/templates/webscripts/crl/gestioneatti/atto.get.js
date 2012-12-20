<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var id = args.id;
var attoFolderNode = null;
var notePresentazioneAssegnazioneNode = null;
var pareri = null;
var consultazioni = null;
var links = null;
var relatoriAtto = null;

if(checkIsNotNull(id)){
	attoFolderNode = utils.getNodeFromString(id);
	
	//lettura delle note generali
	var noteXPathQuery = "*[@cm:name='Note Generali.txt']";
	var noteResults = attoFolderNode.childrenByXPath(noteXPathQuery);
	if(noteResults!=null && noteResults.length>0){
		notePresentazioneAssegnazioneNode = noteResults[0];
	}
	
	//lettura links
	var linksFolderXPathQuery = "*[@cm:name='Links']";
	var linksFolderNode = attoFolderNode.childrenByXPath(linksFolderXPathQuery)[0];
	if(linksFolderNode!=null && linksFolderNode!=undefined){
		links = linksFolderNode.getChildAssocsByType("crlatti:link");
	}
	
	//lettura pareri
	var pareriFolderXPathQuery = "*[@cm:name='Pareri']";
	var pareriFolderNode = attoFolderNode.childrenByXPath(pareriFolderXPathQuery)[0];
	if(pareriFolderNode!=null && pareriFolderNode!=undefined){
		pareri = pareriFolderNode.getChildAssocsByType("crlatti:parere");
	}
	
	
	//lettura pareri
	var consultazioniFolderXPathQuery = "*[@cm:name='Consultazioni']";
	var consultazioniFolderNode = attoFolderNode.childrenByXPath(consultazioniFolderXPathQuery)[0];
	if(consultazioniFolderNode!=null && consultazioniFolderNode!=undefined){
		consultazioni = consultazioniFolderNode.getChildAssocsByType("crlatti:consultazione");
	}
	

	// gestione passaggi
	var passaggiXPathQuery = "*[@cm:name='Passaggi']";
	var passaggiFolderNode = attoFolderNode.childrenByXPath(passaggiXPathQuery)[0];
	var passaggi = null;
	if(passaggiFolderNode!=null && passaggiFolderNode!=undefined){
		passaggi = passaggiFolderNode.getChildAssocsByType("cm:folder");
	}
	
	// gestione relatori atto ORG
	var relatoriAttoXPathQuery = "*[@cm:name='RelatoriAtto']";
	var relatoriAttoFolderNode = attoFolderNode.childrenByXPath(relatoriAttoXPathQuery)[0];
	
	if(relatoriAttoFolderNode!=null && relatoriAttoFolderNode!=undefined){
		relatoriAtto = relatoriAttoFolderNode.getChildAssocsByType("crlatti:relatore");
	}
	
} else {
	status.code = 400;
	status.message = "id atto non valorizzato";
	status.redirect = true;
}

//reperimento tipoAtto
var typeQName = attoFolderNode.typeShort;
var tipoAtto = typeQName.substring(12).toUpperCase();

model.notePresentazioneAssegnazione = notePresentazioneAssegnazioneNode;
model.links = links;
model.organismiStatutari = pareri;
model.consultazioni = consultazioni;
model.passaggi = passaggi;
model.atto = attoFolderNode;
model.tipoAtto = tipoAtto;
model.relatoriAtto = relatoriAtto;