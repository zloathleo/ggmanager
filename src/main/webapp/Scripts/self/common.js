
function ToLink(val) {
    if (!val || val == '') {
        return '';
    } else {
        var urls = val.split('\r\n');
        var result = '';
        urls.forEach(function (url) {
            result = result + '<a target=\'_blank\' href=\'' + '${resourceUrlPath}' + url + '\'>' + url + '</a><br>';
        });
        return result;
    }
}

function ToLine(val) {
    if (!val || val == '') {
        return '';
    } else {
        var urls = val.split('\r\n');
        var result = '';
        urls.forEach(function (url) {
            result = result + url + '<br>';
        });
        return result;
    }
}

function ToScreenType(val) {
    return val == 0 ? '竖屏' : '横屏';
}
/*
function FillSelect(obj, aa, curId) {
    if (!list) {
        return '';
    } else {
        var json = [];
        <#list aa as item>
            var json2 = {};
            json2["text"] = "${item.label}";
            json2["id"] = ${item.id};
            json.push(json2);
        </#list>
        var str = JSON.stringify(json);
        $.messager.alert("tishi", str, "info");
        $('#' + obj).combobox("loadData", json);
    }
}*/
