exit = false;

function backup(folder){
    var path = folder.displayPath.split("/");
    var level = path.length;
    var fullPath = folder.displayPath+"/"+folder.name;
    logger.log("backup: "+fullPath+" level: "+level);
    var data = [];

    if (exit){
        return data;
    }

    if (folder.isSubType("crlatti:atto")){
        var firmatari = folder.properties["crlatti:firmatari"];
        if (firmatari){
            data.push('"'+fullPath+'" : "' + firmatari + '"');
            logger.log(folder.name+" : "+firmatari);
        } else {
            logger.log(folder.name+" empty");
        }

    } else if (level <= 7 ) {   //|| (level == 8 && fullPath.indexOf("AttiIndirizzo")!=-1 )
        var thisChildren = folder.children;
        if (!thisChildren){
            thisChildren = [];
        }
        logger.log(folder.name+" "+thisChildren.length+" children");
        for (var i = 0; i< thisChildren.length; i++){
            var child = thisChildren[i];
            var thisData = backup(child);
            data = data.concat(thisData);
        }
    }
    return data;
}

backupData = backup(space);
var file = companyhome.childByNamePath("backupFirmatari.json");
if (!file){
    file = companyhome.createFile("backupFirmatari.json");
}

file.content = '{\n';
var body = backupData.join(",\n");
file.content += body;
file.content += '}\n';
file.save();
