<import resource="classpath:alfresco/extension/templates/webscripts/crl/gestioneatti/common.js">
var target = args.target;
var lettere = new Array();

if(checkIsNotNull(target)){
	
	var templatesPath = "/app:company_home"+
	"/cm:"+search.ISO9075Encode("CRL")+
	"/cm:"+search.ISO9075Encode("Gestione Atti")+
	"/cm:"+search.ISO9075Encode("Templates");

	var tipologiaTemplatesPath = templatesPath + "/cm:"+search.ISO9075Encode(target);
	var tipologiaTemplatesLuceneQuery = "PATH:\""+tipologiaTemplatesPath+"\"";
	var tipologiaTemplatesFolderNode = search.luceneSearch(tipologiaTemplatesLuceneQuery)[0];
	
	lettereList = tipologiaTemplatesFolderNode.children;
	
	for(var i=0; i<lettereList.length; i++) {
		
		var lettera = lettereList[i];
		
		var templateLettera = new Object();
		templateLettera.id = lettera.nodeRef.toString();
		templateLettera.descrizione = template.getTypeDescription(lettera.type);
		templateLettera.tipoTemplate = lettera.type;
		
		lettere.push(templateLettera);
	}
	
	
	
} else {
	status.code = 400;
	status.message = "parametro target non valorizzato";
	status.redirect = true;
}
model.lettere = lettere;