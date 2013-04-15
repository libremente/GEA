<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">

var gruppi = new Array();

// Controllare che il gruppo dell'utente sia relativo alla legislatura corrente

var legislaturaCorrente = getLegislaturaCorrente();

if(legislaturaCorrente != null){
	
	var gruppiUtente =  people.getContainerGroups(person);
	
	// N.B. Nella gestione dei gruppi, gli utenti possono avere solo un gruppo associato
	// GRUPPI da gestire
	
//	-> Commissioni hanno COMM_ prefisso
//	-> ServizioCommissioni
//	-> Aula
//	-> CPCV
//	-> Administrator
//	-> guest
//	
//	Servizio commissioni
//	Commissioni (vari ruoli) Giunta per il regolamento
//	Aula
//	CPCV
//	Guest (sola lettura)
//	Amministratore
//	
	
	var commissioneGroup = getCommissioneGroup(gruppiUtente);
	
	if(commissioneGroup!=null){
                var gruppoToken = commissioneGroup.properties['cm:authorityName'].split("_");
		gruppi.push("COMM_" + gruppoToken[2]);
	}else if(gruppiUtente[0]!=null){
		gruppi.push(gruppiUtente[0].properties['cm:authorityName'].substring(6));	
	}	
	

}


model.gruppi = gruppi;



function getCommissioneGroup(gruppi){
	
	var gruppoCommissione = null;
	
	for(var i=0; i< gruppi.length; i++){
		
		var gruppo = groups.getGroup(gruppi[i].properties["cm:authorityName"].substring(6));
		if(gruppo.getParentGroups().length>0){
			if(gruppo.getParentGroups()[0].getShortName() == "Commissioni"){
				gruppoCommissione = gruppi[i];
			}
		}
	}
	
	return gruppoCommissione;

}