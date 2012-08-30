var commissioni = space.getChildAssocsByType("crlatti:commissione");

var commConsultivaAtto = new Array();
var commReferenteAtto = new Array();


for (var i=0; i<commissioni.length; i++) {
	
	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Consultiva") {
		commConsultivaAtto.push(commissioni[i].name);
	}
	
	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Referente") {
		commReferenteAtto.push(commissioni[i].name);
	}
		
}

var attoNode = space.parent;
attoNode.properties["crlatti:commConsultiva"] = commConsultivaAtto;
attoNode.properties["crlatti:commReferente"] = commReferenteAtto;

attoNode.save();