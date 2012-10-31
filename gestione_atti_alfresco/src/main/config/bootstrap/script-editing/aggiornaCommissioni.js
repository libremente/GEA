var commissioni = space.getChildAssocsByType("crlatti:commissione");

var commConsultivaAtto = new Array();
var commReferenteAtto = new Array();
var commCoReferenteAtto = new Array();
var commRedigenteAtto = new Array();
var commDeliberanteAtto = new Array();

for (var i=0; i<commissioni.length; i++) {
	
	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Consultiva") {
		commConsultivaAtto.push(commissioni[i].name);
	}
	
	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Referente") {
		commReferenteAtto.push(commissioni[i].name);
	}

	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Co-Referente") {
		commCoReferenteAtto.push(commissioni[i].name);
	}
	
	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Redigente") {
		commRedigenteAtto.push(commissioni[i].name);
	}
	
	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Deliberante") {
		commDeliberanteAtto.push(commissioni[i].name);
	}
		
}

var passaggioNode = space.parent;
var passaggiNode = passaggioNode.parent;
var attoNode = passaggiNode.parent;
attoNode.properties["crlatti:commConsultiva"] = commConsultivaAtto;
attoNode.properties["crlatti:commReferente"] = commReferenteAtto;

if(commCoReferenteAtto.length>0) {
	attoNode.properties["crlatti:commCoreferente"] = commCoReferenteAtto[0];
}
if(commRedigenteAtto.length>0) {
	attoNode.properties["crlatti:commRedigente"] = commRedigenteAtto[0];
	attoNode.properties["crlatti:redigente"] = true;
}else{
	attoNode.properties["crlatti:redigente"] = false;
}

if(commDeliberanteAtto.length>0) {
	attoNode.properties["crlatti:commDeliberante"] = commDeliberanteAtto[0];
	attoNode.properties["crlatti:deliberante"] = true;
}else{
	attoNode.properties["crlatti:deliberante"] = false;
}

attoNode.save();