<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var username = person.properties.userName;
if(username=="protocollo" || username=="admin"){

	var atto = json.get("atto");
	var legislatura = atto.get("legislatura");
	var tipologia = atto.get("tipologia");
	var numeroAtto = atto.get("numeroAtto");
	var tipoAtto = atto.get("tipoAtto");
	var idProtocollo = atto.get("idProtocollo");
	var numeroProtocollo = atto.get("numeroProtocollo");
	var numeroRepertorio = atto.get("numeroRepertorio");
	var dataRepertorio = atto.get("dataRepertorio");
	var classificazione = atto.get("classificazione");
	var oggetto = atto.get("oggetto");
	
	var tipoIniziativa = atto.get("tipoIniziativa");
	var dataIniziativa = atto.get("dataIniziativa");
	var descrizioneIniziativa = atto.get("descrizioneIniziativa");
	
	var numeroDgr = atto.get("numeroDgr");
	var dataDgr = atto.get("dataDgr");
	
	var assegnazione = atto.get("assegnazione");
	var esibenteMittente = atto.get("esibenteMittente");
	
	var dataImportazione = new Date();
	var mese = dataImportazione.getMonth() + 1;
	var anno = dataImportazione.getFullYear();
	
	if(checkIsNotNull(legislatura)
			&& checkIsNotNull(numeroAtto)
			&& checkIsNotNull(tipoAtto)
			&& checkIsNotNull(idProtocollo)){
		
		var gestioneAttiPath = "/app:company_home"+
		"/cm:"+search.ISO9075Encode("CRL")+
		"/cm:"+search.ISO9075Encode("Gestione Atti");
		
		//creazione dello spazio legislatura
		var legislaturaPath = gestioneAttiPath + "/cm:"+search.ISO9075Encode(legislatura);
		var legislaturaLuceneQuery = "PATH:\""+legislaturaPath+"\"";
		var legislaturaResults = search.luceneSearch(legislaturaLuceneQuery);
		
		var legislaturaFolderNode = null;
		if(legislaturaResults!=null && legislaturaResults.length>0){
			legislaturaFolderNode = legislaturaResults[0];
		} else {
			var gestioneAttiLuceneQuery = "PATH:\""+gestioneAttiPath+"\"";
			var gestioneAttiFolderNode = search.luceneSearch(gestioneAttiLuceneQuery)[0];
			legislaturaFolderNode = gestioneAttiFolderNode.createFolder(legislatura);
		}
		
		//creazione spazio anno
		var annoPath = legislaturaPath + "/cm:" + search.ISO9075Encode(anno);
		var annoLuceneQuery = "PATH:\""+annoPath+"\"";
		var annoResults = search.luceneSearch(annoLuceneQuery);
		var annoFolderNode = null;
		if(annoResults!=null && annoResults.length>0){
			annoFolderNode = annoResults[0];
		} else {
			annoFolderNode = legislaturaFolderNode.createFolder(anno);
		}
		
		//creazione spazio mese
		var mesePath = annoPath + "/cm:" + search.ISO9075Encode(mese);
		var meseLuceneQuery = "PATH:\""+mesePath+"\"";
		var meseResults = search.luceneSearch(meseLuceneQuery);
		var meseFolderNode = null;
		if(meseResults!=null && meseResults.length>0){
			meseFolderNode = meseResults[0];
		} else {
			meseFolderNode = annoFolderNode.createFolder(mese);
		}
		
		//gestione dei tipi atto
		var nodeType = "crlatti:atto";
		if(tipoAtto=="PDL"){
			nodeType = "crlatti:attoPdl";
		} else if(tipoAtto=="DOC"){
			nodeType = "crlatti:attoDoc";
		} else if(tipoAtto=="INP"){
			nodeType = "crlatti:attoInp";
		} else if(tipoAtto=="PAR"){
			nodeType = "crlatti:attoPar";
		} else if(tipoAtto=="PDA"){
			nodeType = "crlatti:attoPda";
		} else if(tipoAtto=="PLP"){
			nodeType = "crlatti:attoPlp";
		} else if(tipoAtto=="PRE"){
			nodeType = "crlatti:attoPre";
		} else if(tipoAtto=="REF"){
			nodeType = "crlatti:attoRef";
		} else if(tipoAtto=="REL"){
			nodeType = "crlatti:attoRel";
		} else if(tipoAtto=="EAC"){
			nodeType = "crlatti:attoEac";
		}
		
		//verifica esistenza del folder dell'atto
		var attoPath = mesePath + "/cm:" + search.ISO9075Encode(numeroAtto);
		var attoLuceneQuery = "PATH:\""+attoPath+"\"";
		var attoResults = search.luceneSearch(attoLuceneQuery);
		
		var esisteAttoLuceneQuery = "TYPE:\"crlatti:atto\" AND @crlatti\\:idProtocollo:\""+idProtocollo+"\" AND @cm\\:name:\""+numeroAtto+"\"";
		
		var attoFolderNode = null;
		if(attoResults!=null && attoResults.length>0){
			//atto presente
			attoFolderNode = attoResults[0];
		} else {
			//creazione del nodo del nuovo atto
			var attoSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Atto\"";
			var attoSpaceTemplateNode = search.luceneSearch(attoSpaceTemplateQuery)[0];
			attoFolderNode = attoSpaceTemplateNode.copy(meseFolderNode,true);
		}
			
		attoFolderNode.name = numeroAtto;
		attoFolderNode.specializeType(nodeType);
		attoFolderNode.properties["crlatti:legislatura"] = legislatura;
		attoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
		attoFolderNode.properties["crlatti:tipologia"] = tipologia;
		attoFolderNode.properties["crlatti:anno"] = anno;
		attoFolderNode.properties["crlatti:idProtocollo"] = idProtocollo;
		attoFolderNode.properties["crlatti:numeroProtocollo"] = numeroProtocollo;
		attoFolderNode.properties["crlatti:numeroRepertorio"] = numeroRepertorio;
		attoFolderNode.properties["crlatti:classificazione"] = classificazione;
		attoFolderNode.properties["crlatti:oggetto"] = oggetto;
		attoFolderNode.properties["crlatti:tipoIniziativa"] = tipoIniziativa;
		attoFolderNode.properties["crlatti:descrizioneIniziativa"] = descrizioneIniziativa;
		attoFolderNode.properties["crlatti:assegnazione"] = assegnazione;
		
		//dataRepertorio
		if(checkIsNotNull(dataRepertorio)){
			var dataRepertorioSplitted = dataRepertorio.split("-");
			var dataRepertorioParsed = new Date(dataRepertorioSplitted[0],dataRepertorioSplitted[1]-1,dataRepertorioSplitted[2]);
			attoFolderNode.properties["crlatti:dataRepertorio"] = dataRepertorioParsed;
		}
		
		//dataIniziativa
		if(checkIsNotNull(dataIniziativa)){
			var dataIniziativaSplitted = dataIniziativa.split("-");
			var dataIniziativaParsed = new Date(dataIniziativaSplitted[0],dataIniziativaSplitted[1]-1,dataIniziativaSplitted[2]);
			attoFolderNode.properties["crlatti:dataIniziativa"] = dataIniziativaParsed;
		}
		
		//aspect firmatari
		if(attoFolderNode.hasAspect("crlatti:firmatariAspect")){
			var firmatariSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Firmatari\"";
			var firmatariSpaceTemplateNode = search.luceneSearch(firmatariSpaceTemplateQuery)[0];
			firmatariSpaceTemplateNode.copy(attoFolderNode);
		}
		
		//gestione tipo iniziativa
		if(checkIsNotNull(esibenteMittente)){
			var firmatariArray = new Array();
			if(tipoIniziativa=="01_ATTO DI INIZIATIVA CONSILIARE"){
				
				/**
				 * CONSIGLIERI REGIONALI: <nome1> <cognome1>, <nome2> <cognome2>, �.. (<gruppo appartenenza>) -
				 *	<nome1> <cognome1>, <nome2> <cognome2>, �.. (<gruppo appartenenza>) �..
				 */
				
				var prefix = "CONSIGLIERI REGIONALI:";
				
				if(esibenteMittente.indexOf(prefix)!=-1){
					var esibenteMittenteFirmatari = esibenteMittente.split(prefix)[1];
					var firmatariNomeCognomeSplitted = esibenteMittenteFirmatari.split("-");
					var firmatariSplitted = null;
					if(firmatariNomeCognomeSplitted.length==0){
						firmatariSplitted = esibenteMittenteFirmatari.split(",");
					} else {
						firmatariSplitted = firmatariNomeCognomeSplitted;
					}
					
					for(var i=0; i<firmatariSplitted.length; i++){
						if(firmatariSplitted[i].indexOf("(")==-1){
							var firmatario = firmatariSplitted[i].trim();
							
							//cerca il consigliere exact match - difficile
							var consigliereLuceneQuery = "TYPE:\"crlatti:consigliereAnagrafica\" AND @cm\\:name:\""+firmatario+"\"";
							var consigliereResults = search.luceneSearch(consigliereLuceneQuery);
							var consigliereAnagraficaNode = null;
							if(consigliereResults!=null && consigliereResults.length==1){
								consigliereAnagraficaNode = consigliereResults[0];
							} else {
								//cerca il consigliere per nome o per cognome
								var nomeCognomeConsigliereLuceneQuery = 
									"TYPE:\"crlatti:consigliereAnagrafica\" AND ( @crlatti\\:nomeConsigliereAnagrafica:\""+firmatario+"\" OR @crlatti\\:cognomeConsigliereAnagrafica:\""+firmatario+"\")";
								var nomeCognomeConsigliereResults = search.luceneSearch(nomeCognomeConsigliereLuceneQuery);
								if(nomeCognomeConsigliereResults!=null && nomeCognomeConsigliereResults.length==1){
									consigliereAnagraficaNode = nomeCognomeConsigliereResults[0];
								}
							}
							
							if(consigliereAnagraficaNode!=null){
								firmatariArray.push(consigliereAnagraficaNode.name);
							}
						}
					}
				}
			} else {
				/**
				 * 
					02 ATTO DI INIZIATIVA GIUNTA
					
					(NON PIU UTILIZZATO VEDI 06)
					
					03_ATTO DI INIZIATIVA POPOLARE
					
					Campo libero (cittadini o comuni)
					
					04_ATTO DI INIZIATIVA COMMISSIONI
					
					I � II III � IV � V VI VII VIII COMMISSIONE CONSILIARE ( una sola Commissione per tipo atto)
					
					05 ATTO DI INIZIATIVA UFFICIO DI PRESIDENZA
					
					UFFICIO DI PRESIDENZA
					
					06_ATTO DI INIZIATIVA PRESIDENTE GIUNTA
					
					PRESIDENTE GIUNTA REGIONALE
					
					07_ATTO DI INIZIATIVA AUTONOMIE LOCALI
					
					PRESIDENTE COMITATO AUTONOMIE LOCALI
				 */
				firmatariArray.push(esibenteMittente);
			}
			attoFolderNode.properties["crlatti:firmatari"] = firmatariArray;
		}
		
		
		//aspect Dgr
		if(attoFolderNode.hasAspect("crlatti:dgr")){
			
			//dataDgr
			if(checkIsNotNull(dataDgr)){
				var dataDgrSplitted = dataDgr.split("-");
				var dataDgrParsed = new Date(dataDgrSplitted[0],dataDgrSplitted[1]-1,dataDgrSplitted[2]);
				attoFolderNode.properties["crlatti:dataDgr"] = dataDgrParsed;
			}
			
			attoFolderNode.properties["crlatti:numeroDgr"] = numeroDgr;
		}
		
		attoFolderNode.save();
		model.atto = attoFolderNode;
	
	} else if(checkIsNull(numeroAtto)){
		status.code = 400;
		status.message = "numero atto non valorizzato";
		status.redirect = true;
	} else if(checkIsNull(tipoAtto)){
		status.code = 400;
		status.message = "tipoAtto per atto "+numeroAtto+" non valorizzato";
		status.redirect = true;
	} else if(checkIsNull(legislatura)){
		status.code = 400;
		status.message = "Legislatura per atto "+numeroAtto+" non valorizzata";
		status.redirect = true;
	} else if(checkIsNull(idProtocollo)){
		status.code = 400;
		status.message = "idProtocollo non valorizzato";
		status.redirect = true;
	}

} else {
	status.code = 401;
	status.message = "utenza non abilitata ad accedere a questo servizio";
	status.redirect = true;
}