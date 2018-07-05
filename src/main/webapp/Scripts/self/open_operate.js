function EndEdit() {
    $('#grid').datagrid('reload');
}

function Init(loadUrl, pageSize) {
    $('#grid').datagrid({
        url: loadUrl,
        pageSize: pageSize,
        singleSelect: true,
        nowrap: false,
        rownumbers: true,
        pagination: true
    });
}

function Add(obj, type) {
    var paramString = $.param({
        "cmd": "add",
        "type": type
    });
    var title = obj ? obj + " - 新增" : "新增";
    var url = "open_operate?" + paramString;
    easyuiDialog(title, url, 600, 505, paramString);
}

function Edit(obj, type) {
    var row = $('#grid').datagrid('getSelected');
    if (row) {
        var paramString = $.param({
            "cmd": "edit",
            "itemId": row.id,
            "type": type
        });
        var title = obj ? obj + " - 修改" : "修改";
        var url = "open_operate?" + paramString;
        easyuiDialog(title, url, 600, 505, paramString);
    } else {
        $.messager.alert("提示", "请先选择一行记录！", "info");
    }
}

function Delete(obj, type) {
    var row = $('#grid').datagrid('getSelected');
    if (row) {
        var paramString = $.param({
            "cmd": "delete",
            "itemId": row.id,
            "type": type
        });
        var title = obj ? obj + " - 删除" : "删除";
        var url = "open_operate?" + paramString;
        easyuiDialog(title, url, 350, 280);
    } else {
        $.messager.alert("提示", "请先选择一行记录！", "info");
    }
}

function OnSuccess(__dialog, data) {
    var jsonObject = JSON.parse(data);

    if (jsonObject.hasOwnProperty('err')) {
        $.messager.alert('失败', '提交操作异常[' + jsonObject.message + "]", "error", function (r) {
            __dialog.dialog('close');
            parent.EndEdit();
        });
    } else {
        $.messager.alert('成功', '提交操作成功', "info", function (r) {
            __dialog.dialog('close');
            parent.EndEdit();
        });
    }
}