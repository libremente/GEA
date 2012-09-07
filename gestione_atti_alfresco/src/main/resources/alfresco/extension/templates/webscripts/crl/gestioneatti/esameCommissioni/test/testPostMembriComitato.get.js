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


var membriComitatoPath = "/app:company_home" +
"/cm:"+search.ISO9075Encode("CRL") +
"/cm:"+search.ISO9075Encode("Gestione Atti") +
"/cm:"+search.ISO9075Encode("Anagrafica") +
"/cm:"+search.ISO9075Encode("MembriComitato") + "/*";
var luceneQuery = "PATH:\""+membriComitatoPath+"\"";
var membriResults = search.luceneSearch(luceneQuery);
model.membri = membriResults;