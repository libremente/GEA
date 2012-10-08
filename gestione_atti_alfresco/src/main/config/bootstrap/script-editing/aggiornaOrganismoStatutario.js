var organismiStatutari = space.getChildAssocsByType("crlatti:parere");
var organismiStatutariAtto = new Array(organismiStatutari.length);
for (var i=0; i<organismiStatutari.length; i++) {
	organismiStatutariAtto[i] = organismiStatutari[i].properties["crlatti:organismoStatutarioParere"];
}
var attoNode = space.parent;
attoNode.properties["crlatti:organismiStatutari"] = organismiStatutariAtto;
attoNode.save();