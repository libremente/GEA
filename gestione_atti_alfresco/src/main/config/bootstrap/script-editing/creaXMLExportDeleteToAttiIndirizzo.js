// proprietà del nodo atto da esportare in formato xml
	
crlUtility.sudo(deleteXMLExport, "admin");


function deleteXMLExport(){
	
	var id_atto = document.name;
	var id_legislatura = document.properties["crlatti:legislatura"]
	var tipo_Atto = document.typeShort.substring(12).toUpperCase();
	var numero_atto = document.name;
	var oggetto_atto = document.properties["crlatti:oggetto"];

	// creazione dell'oggetto XML
	var attoXML = new XML();

	// valorizzazione dell'oggetto XML con i dati dell'atto
	attoXML = <atto id_atto="" id_legislatura="" tipo_atto="" numero_atto="" oggetto_atto="" operazione=""></atto>;
	attoXML.@operazione = "DELETE";
	attoXML.@id_atto = id_atto;
	attoXML.@id_legislatura = id_legislatura;
	attoXML.@tipo_Atto = tipo_Atto;
	attoXML.@oggetto_atto = oggetto_atto;


	// creo il contenuto nella cartella di export

	var exportAttiIndirizzoPath = "/app:company_home"+
	"/cm:"+search.ISO9075Encode("Export")+
	"/cm:"+search.ISO9075Encode("Gestione Atti")+
	"/cm:"+search.ISO9075Encode("AttiIndirizzo");

	var exportAttiLuceneQuery = "PATH:\""+exportAttiIndirizzoPath+"\"";
	var exportAttiIndirizzoFolderNode = search.luceneSearch(exportAttiLuceneQuery)[0];

	var dataExport = new Date();
	var giorno = dataExport.getDate();
	var mese = dataExport.getMonth() + 1;
	var anno = dataExport.getFullYear();
	var ora = dataExport.getHours();
	var minuto = dataExport.getMinutes();
	var secondo = dataExport.getSeconds();

	var nomeFileExport = "export_atto_"+numero_atto+"_"+anno+"-"+mese+"-"+giorno+"_"+ora+"-"+minuto+"-"+secondo+".xml";

	var exportNode = exportAttiIndirizzoFolderNode.createNode(nomeFileExport,"cm:content");

	exportNode.content = attoXML;

	
}











