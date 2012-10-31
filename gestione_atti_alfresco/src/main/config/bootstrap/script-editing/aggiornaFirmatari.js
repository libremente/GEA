var firmatari = space.getChildAssocsByType("crlatti:firmatario");
var firmatariAtto = new Array();
var primoFirmatarioAtto = "";

for (var i=0; i<firmatari.length; i++) {
	
	if(firmatari[i].properties["primoFirmatario"]==true){
		primoFirmatarioAtto = firmatari[i].name;
	}else{
		firmatariAtto.push(fimratart[i].name);
	}
	
}

var attoNode = space.parent;
attoNode.properties["crlatti:primoFirmatario"] = primoFirmatarioAtto;
attoNode.properties["crlatti:firmatari"] = firmatariAtto;
attoNode.save();