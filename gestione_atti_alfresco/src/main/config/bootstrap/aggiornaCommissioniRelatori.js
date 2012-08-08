var commissioni = space.getChildAssocsByType("crlatti:commissione");

var commConsultivaAtto = new Array();
var commReferenteAtto = new Array();
var commRelatoriCommissione = new Array();


for (var i=0; i<commissioni.length; i++) {
	
	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Consultiva") {
		commConsultivaAtto.push(commissioni[i].name);
	}
	
	if(commissioni[i].properties["crlatti:ruoloCommissione"] == "Referente") {
		commReferenteAtto.push(commissioni[i].name);
	}
		
	var relatoriCommissione = commissioni[i].properties["crlatti:relatoriCommissione"];
	
	if(relatoriCommissione!=null) {
		for(var j=0; j<relatoriCommissione.length; j++) {
			commRelatoriCommissione.push(relatoriCommissione[j]);
		}
	}	
}

var attoNode = space.parent;
attoNode.properties["crlatti:commConsultiva"] = commConsultivaAtto;
attoNode.properties["crlatti:commReferente"] = commReferenteAtto;
attoNode.properties["crlatti:relatori"] = commRelatoriCommissione;

attoNode.save();