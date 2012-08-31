var luceneQueryAtti = "TYPE:\"crlatti:attoPdl\"";


var atti = search.luceneSearch(luceneQueryAtti);
var atto;
var commissioni;

// seleziono il primo atto disponibile e le relative commissioni
if(atti.length > 0) {
	atto = atti[0];
	
	// space commissioni
	var commissioniXPathQuery = "*[@cm:name='Commissioni']";
	var commissioniFolderNode = atto.childrenByXPath(commissioniXPathQuery)[0];
	
	commissioni = commissioniFolderNode.getChildAssocsByType("crlatti:commissione");
}
	
model.atto = atto;
model.commissioni = commissioni;


var relatoriPath = "/app:company_home" +
"/cm:"+search.ISO9075Encode("CRL") +
"/cm:"+search.ISO9075Encode("Gestione Atti") +
"/cm:"+search.ISO9075Encode("Anagrafica") +
"/cm:"+search.ISO9075Encode("Relatori") + "/*";
var luceneQuery = "PATH:\""+relatoriPath+"\"";
var relatoriResults = search.luceneSearch(luceneQuery);
model.relatori = relatoriResults;