var firmatari = space.getChildAssocsByType("crlatti:firmatario");
var firmatariAtto = new Array();
var firmatariOriginari = new Array();
var primoFirmatarioAtto = "";

firmatari.sort(function(a, b){
	
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


for (var i=0; i<firmatari.length; i++) {
	
	if(firmatari[i].properties["crlatti:isPrimoFirmatario"]==true){
		primoFirmatarioAtto = firmatari[i].name;
	}else{
		firmatariOriginari.push(firmatari[i].name);
	}
	
	firmatariAtto.push(firmatari[i].name);
	
	
}

var attoNode = space.parent;
attoNode.properties["crlatti:primoFirmatario"] = primoFirmatarioAtto;
attoNode.properties["crlatti:firmatariOriginari"] = firmatariOriginari;
attoNode.properties["crlatti:firmatari"] = firmatariAtto;
attoNode.save();