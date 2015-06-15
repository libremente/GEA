startTs = Date.now();

function backup(folder){
    logger.log("backup: "+folder.displayPath);
    data = [];

    if (Date.now() - startTs > 10000){
        return data;
    }

    if (folder.isSubType("crlatti:atto")){
        var firmatari = folder.properties["crlatti:firmatari"];
        if (firmatari){
            data.push('"'+folder.displayPath+'" : "' + firmatari + '"');
        }
    } else {
        var thisChildren = folder.children;
        for (i = 0; i< thisChildren.length; i++){
            var child = thisChildren[i];
            var thisData = backup(child);
            data.concat(thisData);
        }
    }
    return data;
}

backupData = backup(space);
file = companyhome.createFile("backupFirmatari.json");
file.content = '{\n';
var body = data.join(",\n");
file.content += body;
file.content += '}\n';
file.save();
