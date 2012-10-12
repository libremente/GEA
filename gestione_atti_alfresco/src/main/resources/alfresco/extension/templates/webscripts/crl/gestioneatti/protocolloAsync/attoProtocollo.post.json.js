<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var username = person.properties.userName;
if(username=="protocollo" || username=="admin"){

	var importProtocolloPath = 
		"/app:company_home" +
		"/cm:"+search.ISO9075Encode("Import")+
		"/cm:"+search.ISO9075Encode("Gestione Atti")+
		"/cm:"+search.ISO9075Encode("Protocollo")+
		"/cm:"+search.ISO9075Encode("Atti");
	
	var importProtocolloLuceneQuery = "PATH:\""+importProtocolloPath+"\"";
	var importProtocolloFolderNode = search.luceneSearch(importProtocolloLuceneQuery)[0];
	
	var atto = json.get("atto");
	var tipoAtto = atto.get("tipoAtto");
	var idProtocollo = atto.get("idProtocollo");
	var numeroAtto = atto.get("numeroAtto");
	
	if(checkIsNotNull(tipoAtto)) {
	
		if(tipoAtto=="EAC"){
			//gestione importazione atti di tipo EAC
			
			var dataAtto = atto.get("dataAtto");
			var note = atto.get("note");
			
			if(checkIsNotNull(numeroAtto) && checkIsNotNull(idProtocollo)){
				
				//verifica esistenza del folder dell'atto
				var attoPath = importProtocolloPath + "/cm:" + search.ISO9075Encode(numeroAtto);
				var attoLuceneQuery = "PATH:\""+attoPath+"\"";
				var attoResults = search.luceneSearch(attoLuceneQuery);
					
				var eacAttoFolderNode = null;
				if(attoResults!=null && attoResults.length>0){
					//atto presente
					eacAttoFolderNode = attoResults[0];
				} else {
					//creazione del nodo del nuovo atto
					var attoEacSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:AttoEac\"";
					var attoEacSpaceTemplateNode = search.luceneSearch(attoEacSpaceTemplateQuery)[0];
					eacAttoFolderNode = attoEacSpaceTemplateNode.copy(importProtocolloFolderNode,true);
				}
				
				eacAttoFolderNode.name = numeroAtto;
				eacAttoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
				eacAttoFolderNode.properties["crlatti:noteEac"] = note;
				eacAttoFolderNode.properties["crlatti:idProtocollo"] = idProtocollo;
				
				if(checkIsNotNull(dataAtto)){
					var dataAttoSplitted = dataAtto.split("-");
					var dataAttoParsed = new Date(dataAttoSplitted[0],dataAttoSplitted[1]-1,dataAttoSplitted[2]);
					eacAttoFolderNode.properties["crlatti:dataAtto"] = dataAttoParsed;
				}
				
				eacAttoFolderNode.save();
				eacAttoFolderNode.addAspect("crlatti:importatoDaProtocollo");
				model.atto = eacAttoFolderNode;
			}
			
		} else if(tipoAtto=="MIS"){
			//gestione importazione atti di tipo MIS
			if(checkIsNotNull(numeroAtto) 
					&& checkIsNotNull(idProtocollo)){
				
				var dataIniziativaComitato = atto.get("dataIniziativaComitato");
				var dataPropostaCommissione = atto.get("dataPropostaCommissione");
				var commissioneCompetente = atto.get("commissioneCompetente");
				var esitoVotoIntesa = atto.get("esitoVotoIntesa");
				var dataIntesa = atto.get("dataIntesa");
				var dataRispostaComitato = atto.get("dataRispostaComitato");
				var dataApprovazioneProgetto = atto.get("dataApprovazioneProgetto");
				var dataApprovazioneUdp = atto.get("dataApprovazioneUdp");
				var numeroAttoUdp = atto.get("numeroAttoUdp");
				var istitutoIncaricato = atto.get("istitutoIncaricato");
				var scadenzaMv = atto.get("scadenzaMv");
				var dataEsameRapportoFinale = atto.get("dataEsameRapportoFinale");
				var dataTrasmissioneACommissioni = atto.get("dataTrasmissioneACommissioni");
				var note = atto.get("note");
				
				//verifica esistenza del folder dell'atto
				var attoPath = importProtocolloPath + "/cm:" + search.ISO9075Encode(numeroAtto);
				var attoLuceneQuery = "PATH:\""+attoPath+"\"";
				var attoResults = search.luceneSearch(attoLuceneQuery);
					
				var misAttoFolderNode = null;
				if(attoResults!=null && attoResults.length>0){
					//atto presente
					misAttoFolderNode = attoResults[0];
				} else {
					//creazione del nodo del nuovo atto
					var attoMisSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:AttoMis\"";
					var attoMisSpaceTemplateNode = search.luceneSearch(attoMisSpaceTemplateQuery)[0];
					misAttoFolderNode = attoMisSpaceTemplateNode.copy(importProtocolloFolderNode,true);
				}
				
				misAttoFolderNode.name = numeroAtto;
				misAttoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
				
				//date
				if(checkIsNotNull(dataIniziativaComitato)){
					var dataIniziativaComitatoSplitted = dataIniziativaComitato.split("-");
					var dataIniziativaComitatoParsed = new Date(dataIniziativaComitatoSplitted[0],dataIniziativaComitatoSplitted[1]-1,dataIniziativaComitatoSplitted[2]);
					misAttoFolderNode.properties["crlatti:dataIniziativaComitatoMis"] = dataIniziativaComitatoParsed;
				}
				
				if(checkIsNotNull(dataPropostaCommissione)){
					var dataPropostaCommissioneSplitted = dataPropostaCommissione.split("-");
					var dataPropostaCommissioneParsed = new Date(dataPropostaCommissioneSplitted[0],dataPropostaCommissioneSplitted[1]-1,dataPropostaCommissioneSplitted[2]);
					misAttoFolderNode.properties["crlatti:dataPropostaCommissioneMis"] = dataPropostaCommissioneParsed;
				}
				
				if(checkIsNotNull(dataIntesa)){
					var dataIntesaSplitted = dataIntesa.split("-");
					var dataIntesaSplittedParsed = new Date(dataIntesaSplitted[0],dataIntesaSplitted[1]-1,dataIntesaSplitted[2]);
					misAttoFolderNode.properties["crlatti:dataIntesaMis"] = dataIntesaSplittedParsed;
				}
				
				if(checkIsNotNull(dataRispostaComitato)){
					var dataRispostaComitatoSplitted = dataRispostaComitato.split("-");
					var dataRispostaComitatoParsed = new Date(dataRispostaComitatoSplitted[0],dataRispostaComitatoSplitted[1]-1,dataRispostaComitatoSplitted[2]);
					misAttoFolderNode.properties["crlatti:dataRispostaComitatoMis"] = dataRispostaComitatoParsed;
				}
				
				if(checkIsNotNull(dataApprovazioneProgetto)){
					var dataApprovazioneProgettoSplitted = dataApprovazioneProgetto.split("-");
					var dataApprovazioneProgettoParsed = new Date(dataApprovazioneProgettoSplitted[0],dataApprovazioneProgettoSplitted[1]-1,dataApprovazioneProgettoSplitted[2]);
					misAttoFolderNode.properties["crlatti:dataApprovazioneProgettoMis"] = dataApprovazioneProgettoParsed;
				}
				
				if(checkIsNotNull(dataApprovazioneUdp)){
					var dataApprovazioneUdpSplitted = dataApprovazioneUdp.split("-");
					var dataApprovazioneUdpParsed = new Date(dataApprovazioneUdpSplitted[0],dataApprovazioneUdpSplitted[1]-1,dataApprovazioneUdpSplitted[2]);
					misAttoFolderNode.properties["crlatti:dataApprovazioneUdpMis"] = dataApprovazioneUdpParsed;
				}
				
				if(checkIsNotNull(scadenzaMv)){
					var scadenzaMvSplitted = scadenzaMv.split("-");
					var scadenzaMvParsed = new Date(scadenzaMvSplitted[0],scadenzaMvSplitted[1]-1,scadenzaMvSplitted[2]);
					misAttoFolderNode.properties["crlatti:scadenzaMvMis"] = scadenzaMvParsed;
				}
				
				if(checkIsNotNull(dataEsameRapportoFinale)){
					var dataEsameRapportoFinaleSplitted = dataEsameRapportoFinale.split("-");
					var dataEsameRapportoFinaleParsed = new Date(dataEsameRapportoFinaleSplitted[0],dataEsameRapportoFinaleSplitted[1]-1,dataEsameRapportoFinaleSplitted[2]);
					misAttoFolderNode.properties["crlatti:dataEsameRapportoFinaleMis"] = dataEsameRapportoFinaleParsed;
				}
				
				if(checkIsNotNull(dataTrasmissioneACommissioni)){
					var dataTrasmissioneACommissioniSplitted = dataTrasmissioneACommissioni.split("-");
					var dataTrasmissioneACommissioniParsed = new Date(dataTrasmissioneACommissioniSplitted[0],dataTrasmissioneACommissioniSplitted[1]-1,dataTrasmissioneACommissioniSplitted[2]);
					misAttoFolderNode.properties["crlatti:dataTrasmissioneACommissioniMis"] = dataTrasmissioneACommissioniParsed;
				}
				
				misAttoFolderNode.name = numeroAtto;
				misAttoFolderNode.properties["crlatti:numeroAtto"] = numeroAtto;
				misAttoFolderNode.properties["crlatti:noteMis"] = note;
				misAttoFolderNode.properties["crlatti:commissioneCompetenteMis"] = commissioneCompetente;
				misAttoFolderNode.properties["crlatti:esitoVotoIntesaMis"] = esitoVotoIntesa;
				misAttoFolderNode.properties["crlatti:numeroAttoUdpMis"] = numeroAttoUdp;
				misAttoFolderNode.properties["crlatti:istitutoIncaricatoMis"] = istitutoIncaricato;
				misAttoFolderNode.properties["crlatti:idProtocollo"] = idProtocollo;
				misAttoFolderNode.save();
				misAttoFolderNode.addAspect("crlatti:importatoDaProtocollo");
				
				model.atto = misAttoFolderNode;
			}
			
		} else {
		
			var legislatura = atto.get("legislatura");
			var tipologia = atto.get("tipologia");
			
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
				}
				
				//verifica esistenza del folder dell'atto
				var attoPath = importProtocolloPath + "/cm:" + search.ISO9075Encode(numeroAtto);
				var attoLuceneQuery = "PATH:\""+attoPath+"\"";
				var attoResults = search.luceneSearch(attoLuceneQuery);
					
				var attoFolderNode = null;
				if(attoResults!=null && attoResults.length>0){
					//atto presente
					attoFolderNode = attoResults[0];
				} else {
					//creazione del nodo del nuovo atto
					var attoSpaceTemplateQuery = "PATH:\"/app:company_home/app:dictionary/app:space_templates/cm:Atto\"";
					var attoSpaceTemplateNode = search.luceneSearch(attoSpaceTemplateQuery)[0];
					attoFolderNode = attoSpaceTemplateNode.copy(importProtocolloFolderNode,true);
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
										var nomeConsigliere = consigliereAnagraficaNode.properties["crlatti:nomeConsigliereAnagrafica"];
										var cognomeConsigliere = consigliereAnagraficaNode.properties["crlatti:cognomeConsigliereAnagrafica"];
										if(checkIsNotNull(nomeConsigliere) && checkIsNotNull(cognomeConsigliere)){
											var nomeCompletoConsigliere = nomeConsigliere + " " + cognomeConsigliere;
											firmatariArray.push(nomeCompletoConsigliere);
										}
									}
								}
							}
						}
					} else {
						
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
				attoFolderNode.addAspect("crlatti:importatoDaProtocollo");
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
			
		} 
	}
} else {
	status.code = 401;
	status.message = "utenza non abilitata ad accedere a questo servizio";
	status.redirect = true;
}