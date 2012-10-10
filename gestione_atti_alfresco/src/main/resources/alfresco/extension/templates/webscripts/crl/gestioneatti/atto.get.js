<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var id = args.id;
var attoFolderNode = null;
var notePresentazioneAssegnazioneNode = null;
var pareri = null;
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
	links = linksFolderNode.getChildAssocsByType("crlatti:link");
	
	//lettura pareri
	var pareriFolderXPathQuery = "*[@cm:name='Pareri']";
	var pareriFolderNode = attoFolderNode.childrenByXPath(pareriFolderXPathQuery)[0];
	pareri = pareriFolderNode.getChildAssocsByType("crlatti:parere");
	
//	//lettura commissioni
//	var commissioniFolderXPathQuery = "*[@cm:name='Commissioni']";
//	var commissioniFolderNode = attoFolderNode.childrenByXPath(commissioniFolderXPathQuery)[0];
//	commissioni = commissioniFolderNode.getChildAssocsByType("crlatti:commissione");
	

	// gestione passaggi
	var passaggiXPathQuery = "*[@cm:name='Passaggi']";
	var passaggiFolderNode = attoFolderNode.childrenByXPath(passaggiXPathQuery)[0];
	var passaggi = passaggiFolderNode.getChildAssocsByType("cm:folder");
	
	
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
model.passaggi = passaggi;
model.atto = attoFolderNode;
model.tipoAtto = tipoAtto;