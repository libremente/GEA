    <import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

    function importaAtto(attoProtocolloNode){

    //Regole da impostare con uno scope attivo solo su crlatti:atto

    var legislatura = attoProtocolloNode.properties["crlatti:legislatura"];
    var numeroAtto = attoProtocolloNode.properties["crlatti:numeroAtto"];
    var estensioneAtto = attoProtocolloNode.properties["crlatti:estensioneAtto"];
    var idProtocollo = attoProtocolloNode.properties["crlatti:idProtocollo"];
	
    var tipologia = attoProtocolloNode.properties["crlatti:tipologia"];
    var numeroProtocollo = attoProtocolloNode.properties["crlatti:numeroProtocollo"];
    var numeroRepertorio = attoProtocolloNode.properties["crlatti:numeroRepertorio"];
    var dataRepertorio = attoProtocolloNode.properties["crlatti:dataRepertorio"];
    var classificazione = attoProtocolloNode.properties["crlatti:classificazione"];
    var oggetto = attoProtocolloNode.properties["crlatti:oggetto"];
	
    var tipoIniziativa = attoProtocolloNode.properties["crlatti:tipoIniziativa"];
    var dataIniziativa = attoProtocolloNode.properties["crlatti:dataIniziativa"];
    var descrizioneIniziativa = attoProtocolloNode.properties["crlatti:descrizioneIniziativa"];
	
    var numeroDgr = attoProtocolloNode.properties["crlatti:numeroDgr"];
    var dataDgr = attoProtocolloNode.properties["crlatti:dataDgr"];
	
    var assegnazione = attoProtocolloNode.properties["crlatti:assegnazione"];
    var firmatari = attoProtocolloNode.properties["crlatti:firmatari"];
	
    var dataImportazione = new Date();
    var mese = dataImportazione.getMonth() + 1;
    var anno = dataImportazione.getFullYear();
	
    var type = attoProtocolloNode.typeShort;
	
    var tipoAtto = type.substring(12).toUpperCase();
	
    var gestioneAttiPath = "/app:company_home"+
	"/cm:"+search.ISO9075Encode("CRL")+
	"/cm:"+search.ISO9075Encode("Gestione Atti");
	
    if(type=="crlatti:attoEac"){
		
	
    } else if (type=="crlatti:attoMis"){
		
		
		
    } else if(checkIsNotNull(legislatura)
        && checkIsNotNull(numeroAtto)
        && checkIsNotNull(idProtocollo)){
		
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
		
	
        // creazione spazio "tipo" con copia dello space template ContenitoreAtto. 
        // Nello space sono presenti le regole necessarie alla creazione degli xml di export
        // verso il sistema di gestione Atti Indirizzo
        var tipoPath = mesePath + "/cm:" + search.ISO9075Encode(tipoAtto);
        var tipoLuceneQuery = "PATH:\""+tipoPath+"\"";
        var tipoResults = search.luceneSearch(tipoLuceneQuery);
        var tipoFolderNode = null;
        if(tipoResults!=null && tipoResults.length>0){
            tipoFolderNode = tipoResults[0];
        } else {
            var contenitoreAttoSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:ContenitoreAtto\"";
            var contenitoreAttoSpaceTemplateNode = search.luceneSearch(contenitoreAttoSpaceTemplateQuery)[0];
            tipoFolderNode = contenitoreAttoSpaceTemplateNode.copy(meseFolderNode,true);
            tipoFolderNode.name = tipoAtto;
            tipoFolderNode.save();
        }
		
        //verifica esistenza del folder dell'atto
        var attoPath = tipoPath + "/cm:" + search.ISO9075Encode(numeroAtto+""+estensioneAtto);
        var attoLuceneQuery = "PATH:\""+attoPath+"\"";
        var attoResults = search.luceneSearch(attoLuceneQuery);
		
        //var esisteAttoLuceneQuery = "TYPE:\"crlatti:atto\" AND @crlatti\\:idProtocollo:\""+idProtocollo+"\" AND @cm\\:name:\""+numeroAtto+"\"";
		
        var attoFolderNode = null;
        if(attoResults!=null && attoResults.length>0){
            //atto presente
            attoFolderNode = attoResults[0];
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
            attoFolderNode.properties["crlatti:dataIniziativa"] = attoProtocolloNode.properties["crlatti:dataIniziativa"];
            attoFolderNode.properties["crlatti:dataRepertorio"] = attoProtocolloNode.properties["crlatti:dataRepertorio"];
			
            if(attoFolderNode.hasAspect("crlatti:dgr")){
                attoFolderNode.properties["crlatti:numeroDgr"] = attoProtocolloNode.properties["crlatti:numeroDgr"];
                attoFolderNode.properties["crlatti:dataDgr"] = attoProtocolloNode.properties["crlatti:dataDgr"];
            }
			
            if(attoFolderNode.hasAspect("crlatti:firmatariAspect")){
                // i firmatari verranno infasati con la rule sulla cartella firmatari
                // attoFolderNode.properties["crlatti:firmatari"] = attoProtocolloNode.properties["crlatti:firmatari"];
	
                var childrenXPathQuery = "*[@cm:name='Firmatari']";
                var firmatariFolderNode = attoFolderNode.childrenByXPath(childrenXPathQuery)[0];
				
                // elimino i firmatari esistenti
                for each (firmatarioEsistente in firmatariFolderNode.children){
                    if(firmatarioEsistente.isDocument) {
                        firmatarioEsistente.remove();
                    }
                }
	
                var listaFirmatari = attoProtocolloNode.properties["crlatti:firmatari"];
				
                //var childrenXPathQuery = "*[@cm:name=\""+nomeFirmatario+"\"]";
                //var firmatarioNode = firmatariFolderNode.childrenByXPath(childrenXPathQuery);
				
                // inserisco i nuovi firmatari
                if(listaFirmatari!=null) {
                    for(var k=0; k < listaFirmatari.length; k++) {
						
                        var nomeFirmatario = listaFirmatari[k];
						
                        var firmatarioNode = firmatariFolderNode.createNode(nomeFirmatario,"crlatti:firmatario");	
		
                        // inserimento proprietà per l'ordinamento 01,02,03 ecc...
                        if(k<10) {
                            firmatarioNode.properties["crlatti:numeroOrdinamento"] = "0"+k+"";
                        }else{
                            firmatarioNode.properties["crlatti:numeroOrdinamento"] = ""+k+"";
                        }
						
                        // il primo firmatario della lista è contrassegnato come primo firmatario
                        if(k==0){
                            firmatarioNode.properties["crlatti:isPrimoFirmatario"] = true;
                        }
						
                        // se non setto la proprietà content Share da un errore nella visualizzazione
                        firmatarioNode.content = "";
							
                        firmatarioNode.save();
						
                    }
                }
	
            }
			
            attoFolderNode.save();
			
            if(attoFolderNode.hasAspect("crlatti:importatoDaProtocollo")){
                attoFolderNode.removeAspect("crlatti:importatoDaProtocollo");
            }
			
				
            attoProtocolloNode.remove();
			
            protocolloLogger.info("Atto importato correttamente. Operazione: Modifica atto esistente - Atto numero:"+numeroAtto+" idProtocollo:"+idProtocollo);	
			
        } else {
            //creazione del nodo del nuovo atto
            attoProtocolloNode.move(tipoFolderNode);
                        
            // FIX PROTOCOLLO
            var attoFolderName = attoProtocolloNode.name;
            attoProtocolloNode.name = attoFolderName.substring(0, attoFolderName.lastIndexOf("_"));
            attoProtocolloNode.save();
                        
            if(attoProtocolloNode.hasAspect("crlatti:importatoDaProtocollo")){
                attoProtocolloNode.removeAspect("crlatti:importatoDaProtocollo");
            }
			

            attoProtocolloNode.addAspect("crlatti:attiIndirizzoAspect");
			
			
            protocolloLogger.info("Atto importato correttamente. Operazione: Creazione nuovo atto - Atto numero:"+numeroAtto+" idProtocollo:"+idProtocollo);	
        }
		
		
	
	
    }
}


function importaAllegato(allegatoProtocolloNode){

	var idProtocolloAtto = allegatoProtocolloNode.properties["crlatti:idProtocolloAtto"];
	var idProtocolloAllegato = allegatoProtocolloNode.properties["crlatti:idProtocollo"];
	var filename = allegatoProtocolloNode.name;
	
	if(idProtocolloAtto == ""){
		
	} else if(idProtocolloAllegato == ""){
		
	} else if(filename == ""){
		
	} else {
            
                filename = filename.substring(filename.indexOf("_") + 1);
		
		var importProtocolloPath = 
			"/app:company_home" +
			"/cm:"+search.ISO9075Encode("CRL")+
			"/cm:"+search.ISO9075Encode("Gestione Atti")+
			"/cm:"+search.ISO9075Encode("Import protocollo")+
			"/cm:"+search.ISO9075Encode("Allegati");
		
		var importLuceneQuery = "PATH:\""+importProtocolloPath+"\"";
		var importFolderNode = search.luceneSearch(importLuceneQuery)[0];
		
		var attoLuceneQuery = "TYPE:\"crlatti:atto\" AND @crlatti\\:idProtocollo:\""+idProtocolloAtto+"\"";
		var attoResults = search.luceneSearch(attoLuceneQuery);
		
		if(attoResults!=null && attoResults.length>0){
			
			var attoFolderNode = attoResults[0];
			
			
			// controllo se l'atto è di tipo ORG o PDA UdP
			
			if(attoFolderNode.typeShort=="crlatti:attoOrg" || 
					(attoFolderNode.typeShort=="crlatti:attoPda" && attoFolderNode.properties["crlatti:tipoIniziativa"]=="05_ATTO DI INIZIATIVA UFFICIO PRESIDENZA") ){
				
				var testiFolderNode = attoFolderNode.childrenByXPath("*[@cm:name='Testi']")[0];
				
				var recordXPathQuery = "*[@cm:name='"+filename+"']";
				var testiResults = testiFolderNode.childrenByXPath(recordXPathQuery);
				
				var attoRecordNode = null;
				
				
				//verifica dell'esistenza di un testo con lo stesso nome dell'allegato
				if(testiResults!=null && testiResults.length>0){
					//esiste un testo con lo stesso nome dell'allegato che si sta per spostare: aggiornamento del solo contenuto
					attoRecordNode = testiResults[0];
				} else {
					//non esiste alcun testo con lo stesso nome dell'allegato
					// creazione binario
					attoRecordNode = testiFolderNode.createFile(filename);
					attoRecordNode.specializeType("crlatti:testo");
				}

				
				//prelevamento properties dall'allegato
				var tipologia = allegatoProtocolloNode.properties["crlatti:tipologia"];
				var pubblico = allegatoProtocolloNode.properties["crlatti:pubblico"];
				var provenienza = allegatoProtocolloNode.properties["crlatti:provenienza"];
				var content = allegatoProtocolloNode.properties.content;
				
				//scrittura properties all'interno del nodo testo
				attoRecordNode.properties["crlatti:tipologia"] = tipologia;
				attoRecordNode.properties["crlatti:pubblico"] = pubblico;
				attoRecordNode.properties["crlatti:provenienza"] = provenienza;
				attoRecordNode.properties.content.write(content);
				attoRecordNode.properties.content.setEncoding("UTF-8");
				attoRecordNode.properties.content.guessMimetype(filename);
				attoRecordNode.save();
				
				//rimozione del vecchio allegato che adesso è presente come testo dell'atto
				allegatoProtocolloNode.remove();
				
			}else{
				
				var allegatiFolderNode = attoFolderNode.childrenByXPath("*[@cm:name='Allegati']")[0];
				
				
				var allegatoResults = allegatiFolderNode.childrenByXPath("*[@cm:name='"+filename+"']");
				var allegatoNode = null;
				if(allegatoResults!=null && allegatoResults.length>0){
					allegatoNode = allegatoResults[0];
					allegatoNode.properties["crlatti:idProtocollo"] = idProtocolloAllegato;
					allegatoNode.properties.content.write(allegatoProtocolloNode.properties.content);
					allegatoNode.properties.content.setEncoding("UTF-8");
					allegatoNode.properties.content.guessMimetype(filename);
					allegatoNode.save();
					
					allegatoNode.addAspect("crlatti:importatoDaProtocollo");
					allegatoNode.properties["crlatti:idProtocolloAtto"] = idProtocolloAtto;
					allegatoNode.save();
					
					if(allegatoNode.hasAspect("crlatti:importatoDaProtocollo")){
						allegatoNode.removeAspect("crlatti:importatoDaProtocollo");
					}
					
					allegatoProtocolloNode.remove();
					
				} else {
					
					if(allegatoProtocolloNode.hasAspect("crlatti:importatoDaProtocollo")){
						allegatoProtocolloNode.removeAspect("crlatti:importatoDaProtocollo");
					}
					
					allegatoProtocolloNode.move(allegatiFolderNode);
                                        
                                        allegatoProtocolloNode.name = filename;
                                        allegatoProtocolloNode.save();
				}
				
				
				
			}
			
			
			
			
		}
	}
}


//Script per l'importazione degli atti verso il sistema di Protocollo
protocolloLogger.info("Importazione Atti da Sistema di Protocollo avviato...");

//var importFolderXPathQuery = "*[@cm:name='Import']";
//var importFolderNode = companyhome.childrenByXPath(importFolderXPathQuery)[0];
//
//var gestioneAttiFolderXPathQuery = "*[@cm:name='Gestione Atti']";
//var gestioneAttiFolderNode = importFolderNode.childrenByXPath(gestioneAttiFolderXPathQuery)[0];


var gestioneAttiProtocolloPath = "/app:company_home"+
    "/cm:"+search.ISO9075Encode("Import")+
    "/cm:"+search.ISO9075Encode("Gestione Atti")+
    "/cm:"+search.ISO9075Encode("Protocollo") +
    "/cm:"+search.ISO9075Encode("Atti");

var gestioneAttiProtocolloQuery = "PATH:\""+gestioneAttiProtocolloPath+"/*\" AND TYPE:\"crlatti:atto\"";
var attiProtocolloList = search.luceneSearch(gestioneAttiProtocolloQuery, "cm:name", true);

protocolloLogger.info("Inizio importazione atti. Elementi da importare: "+attiProtocolloList.length);

var count = 0;

for(var i=0; i<attiProtocolloList.length; i++){
	
    var attoProtocollo =  attiProtocolloList[i];
    var attoName = attoProtocollo.name;
	
    //xmlFile.move(attiParsedFolderNode);
    importaAtto(attoProtocollo);
    
    protocolloLogger.info("Importato il file "+attoName);
    count++;
	
}

protocolloLogger.info("Importazione Atti terminata. Elementi importati: "+count);



//Script per l'importazione degli allegati verso il sistema di Protocollo
protocolloLogger.info("Importazione Allegati da Sistema di Protocollo avviato...");

//var importFolderXPathQuery = "*[@cm:name='Import']";
//var importFolderNode = companyhome.childrenByXPath(importFolderXPathQuery)[0];
//
//var gestioneAttiFolderXPathQuery = "*[@cm:name='Gestione Atti']";
//var gestioneAttiFolderNode = importFolderNode.childrenByXPath(gestioneAttiFolderXPathQuery)[0];


var gestioneAllegatiProtocolloPath = "/app:company_home"+
    "/cm:"+search.ISO9075Encode("Import")+
    "/cm:"+search.ISO9075Encode("Gestione Atti")+
    "/cm:"+search.ISO9075Encode("Protocollo") +
    "/cm:"+search.ISO9075Encode("Allegati");

var gestioneAllegatiProtocolloQuery = "PATH:\""+gestioneAllegatiProtocolloPath+"/*\" AND TYPE:\"crlatti:allegato\" AND ASPECT:\"crlatti:importatoDaProtocollo\""; //ASPECT: importatoDaprotocollo ASPECT:"crlatti:importatoDaProtocollo"
var allegatiProtocolloList = search.luceneSearch(gestioneAllegatiProtocolloQuery, "cm:name", true);

protocolloLogger.info("Inizio importazione allegati. Elementi da importare: "+allegatiProtocolloList.length);

var count = 0;

for(var i=0; i<allegatiProtocolloList.length; i++){
	
    var allegatoProtocollo =  allegatiProtocolloList[i];
    var allegatoName = allegatoProtocollo.name;
	
    //xmlFile.move(attiParsedFolderNode);
    importaAllegato(allegatoProtocollo);
    
    protocolloLogger.info("Importato il file "+allegatoName);
    count++;
	
}

protocolloLogger.info("Importazione Allegati terminata. Elementi importati: "+count);






