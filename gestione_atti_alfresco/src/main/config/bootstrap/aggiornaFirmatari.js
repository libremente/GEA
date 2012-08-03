var firmatari = space.getChildAssocsByType("crlatti:firmatario");
var firmatariAtto = new Array(firmatari.length);
for (var i=0; i<firmatari.length; i++) {
	firmatariAtto[i] = firmatari[i].name;
}
var attoNode = space.parent;
attoNode.properties["crlatti:firmatari"] = firmatariAtto;
attoNode.save();