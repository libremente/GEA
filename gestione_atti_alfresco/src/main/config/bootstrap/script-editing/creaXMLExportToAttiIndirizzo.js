// Controllo per la creazione di un xml relativo alle operazioni di creazione o modifica
if(document.properties["crlatti:statoExportAttiIndirizzo"]=="CREATE"){
	
	crlUtility.sudoWithArgs(creaXMLExport, new Array("CREATE"), "admin");

}else if(document.properties["crlatti:statoExportAttiIndirizzo"]=="UPDATE"){

	crlUtility.sudoWithArgs(creaXMLExport, new Array("UPDATE"), "admin");

}



function creaXMLExport(operazione){
	
	
	// proprietà del nodo atto da esportare in formato xml

	var id_atto = document.name;
	var id_legislatura = document.properties["crlatti:legislatura"]
	var tipo_Atto = document.typeShort.substring(12).toUpperCase();
	var numero_atto = document.name;
	var oggetto_atto = document.properties["crlatti:oggetto"];

	// creazione dell'oggetto XML
	var attoXML = new XML();

	// valorizzazione dell'oggetto XML con i dati dell'atto
	attoXML = <atto id_atto="" id_legislatura="" tipo_atto="" numero_atto="" oggetto_atto="" operazione=""></atto>;
	attoXML.@operazione = operazione;
	attoXML.@id_atto = id_atto;
	attoXML.@id_legislatura = id_legislatura;
	attoXML.@tipo_atto = tipo_Atto;
	attoXML.@oggetto_atto = oggetto_atto;
	attoXML.@numero_atto = numero_atto;

	// creazione e valorizzazione del tag <firmatari>
	firmatari = <firmatari></firmatari>;

	if(document.hasAspect("crlatti:firmatariAspect")){
		
		var firmatariFolderNode=null

		var firmatariXPathQuery = "*[@cm:name='Firmatari']";
		var firmatariFolderNode = document.childrenByXPath(firmatariXPathQuery)[0];
		
		var firmatariArray = firmatariFolderNode.getChildAssocsByType("crlatti:firmatario");
		
		// ordinamento dell'array di firmatari per numero di ordinamento
		firmatariArray.sort(function(a, b){
			var indiceA = a.properties["crlatti:numeroOrdinamento"]; 
			var indiceB = b.properties["crlatti:numeroOrdinamento"]; 
			 
			if (indiceA < indiceB) {
				return -1 
			}
			if (indiceA > indiceB) {
				return 1
			}
			  
			return 0
		})
		
		// creazione dei tag <firmatario>
		// l'array è ciclato al contrario in modo da inserire il tag firmatario sempre al primo posto
		for (var i=firmatariArray.length-1; i>=0; i--) {
			
			var firmatario = <firmatario id_firmario="" id_gruppo_firmatario="" nome_firmatario="" cognome_firmatario="" gruppo_frimatario=""></firmatario>;
		
			// Non è previsto a sistema un id firmatario. Viene utilizzato il nomeFirmatario
			firmatario.@id_firmario = firmatariArray[i].properties["crlatti:nomeFirmatario"];
			firmatario.@id_gruppo_firmatario = firmatariArray[i].properties["crlatti:gruppoConsiliare"];
			firmatario.@nome_firmatario = firmatariArray[i].properties["crlatti:nomeFirmatario"];
			firmatario.@cognome_firmatario = "";
			firmatario.@gruppo_frimatario = firmatariArray[i].properties["crlatti:gruppoConsiliare"];

			firmatari.insertChildAfter(null, firmatario);	
				
		}

	}

	attoXML.insertChildAfter(null, firmatari);

	// Creazione del tag <collegamenti>
	collegamenti = <collegamenti></collegamenti>;

	var collegamentiFolderNode=null

	var collegamentiXPathQuery = "*[@cm:name='Collegamenti']";
	var collegamentiFolderNode = document.childrenByXPath(collegamentiXPathQuery)[0];

	// Collegamenti atti interni
	var collegamentiInterniXPathQuery = "*[@cm:name='Interni']";
	var collegamentiInterniFolderNode = collegamentiFolderNode.childrenByXPath(collegamentiInterniXPathQuery)[0];

	var collegamentiInterniArray = collegamentiInterniFolderNode.getChildAssocsByType("crlatti:collegamento");


	// creazione dei tag <collegamento> - Atti Interni
	for (var i=0; i<collegamentiInterniArray.length; i++) {
		
		var collegamento = <collegamento id_atto="" tipo_atto="" numero_atto=""></firmatario>;

		var attoAssociato = collegamentiInterniArray[i].assocs["crlatti:attoAssociatoCollegamento"][0];
		
		
		collegamento.@id_atto = attoAssociato.name;
		collegamento.@tipo_atto = attoAssociato.typeShort.substring(12).toUpperCase();
		collegamento.@numero_atto = attoAssociato.name;
		
		collegamenti.insertChildAfter(null, collegamento);	
			
	}


	// Collegamenti atti indirizzo sindacato ispettivo
	var collegamentiAttoIndirizzoXPathQuery = "*[@cm:name='AttiIndirizzoSindacatoIspettivo']";
	var collegamentiAttoIndirizzoFolderNode = collegamentiFolderNode.childrenByXPath(collegamentiAttoIndirizzoXPathQuery)[0];

	var collegamentiAttoIndirizzoArray = collegamentiAttoIndirizzoFolderNode.getChildAssocsByType("crlatti:collegamentoAttiIndirizzo");

	//creazione dei tag <collegamento> - Atti indirizzo
	for (var i=0; i<collegamentiAttoIndirizzoArray.length; i++) {
		
		var collegamento = <collegamento id_atto="" tipo_atto="" numero_atto=""></firmatario>;

		var attoAssociato = collegamentiAttoIndirizzoArray[i].assocs["crlatti:attoAssociatoCollegamentoAttiIndirizzo"][0];
		
		collegamento.@id_atto = attoAssociato.properties["crlatti:idAttoIndirizzo"];
		collegamento.@tipo_atto = attoAssociato.properties["crlatti:tipoAttoIndirizzo"];
		collegamento.@numero_atto = attoAssociato.properties["crlatti:numeroAttoIndirizzo"];
		
		collegamenti.insertChildAfter(null, collegamento);	
			
	}


	attoXML.insertChildAfter(null, collegamenti);


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

	document.properties["crlatti:statoExportAttiIndirizzo"]="UPDATE";
	document.save();
	
}











