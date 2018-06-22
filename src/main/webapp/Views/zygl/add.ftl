<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加</title>
    <link type="text/css" rel="Stylesheet" href="../../Contents/Css/base.css"/>
    <link type="text/css" rel="Stylesheet" href="../../Contents/Css/platform.css"/>
    <link type="text/css" rel="Stylesheet" href="../../Scripts/jQEasyUI/themes/easyui.css"/>
    <link type="text/css" rel="Stylesheet" href="../../Scripts/jQEasyUI/themes/icon.css"/>
    <script type="text/javascript" src="../../Scripts/jquery.min.js"></script>
    <script type="text/javascript" src="../../Scripts/IllegalityCheck.js"></script>
    <script type="text/javascript" src="../../Scripts/jQEasyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../Scripts/jQEasyUI/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../../Scripts/FunctionJS.js"></script>
    <script type="text/javascript">
        function DialogSave(__dialog) {
            $('#form_add').form('submit', {
                success: function (data) {

                    var jsonObject = JSON.parse(data);
                    if (jsonObject.hasOwnProperty('err')) {
                        $.messager.confirm('异常', '提交操作异常[' + jsonObject.message + "]", function (r) {
                            __dialog.dialog('close');
                            parent.EndEdit();
                        });
                    } else {
                        $.messager.confirm('成功', '提交操作成功', function (r) {
                            __dialog.dialog('close');
                            parent.EndEdit();
                        });
                    }
                }
            });
        }

        parent.DialogSave = DialogSave;
    </script>
    <script type="text/javascript">
        function HideRow(id) {
            var row = document.getElementById(id);
            row.style.display = "none";
        }

        function SetValue(id, value) {
            var col = document.getElementById(id);
            col.innerText = value;
        }

        function InertValue(id, value) {
            var col = document.getElementById(id);
            col.insertBefore(document.createTextNode(value), col.childNodes[0]);
        }

        function SetAccept(id, value) {
            var col = document.getElementById(id);
            col.accept = value;
        }

        $(document).ready(function(){
            if ("${type}" == "tp") {
                HideRow('tr2');
                SetValue("td1", "资源名称");
                InertValue("td2", "支持格式<.jpg .png .bmp>");
                SetAccept("input1", ".jpg,.png,.bmp");
            } else if ("${type}" == "sp") {
                HideRow('tr2');
                SetValue("td1", "资源名称");
                InertValue("td2", "支持格式<.mp4 .flv>");
                SetAccept("input1", ".mp4,.flv");
            } else if ("${type}" == "zb") {
                HideRow('tr2');
                HideRow('tr5');
            } else {
                HideRow('tr3');
                HideRow('tr5');
            }
        });
    </script>
</head>
<body class="easyui-layout">
<form id="form_add" action="add_item.do" method="post" enctype="multipart/form-data">
    <table style="width: 565px" class="kv-table">
        <tbody>
        <tr id="tr1">
            <td id="td1" class="kv-label">
                资源名称<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="label"/>
            </td>
        </tr>
        <tr id="tr2">
            <td class="kv-label">
                资源内容
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="content"/>
            </td>
        </tr>
        <tr id="tr3">
            <td class="kv-label">
                资源描述
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="des"/>
            </td>
        </tr>
        <tr id="tr4">
            <td class="kv-label">
                关联链接
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="link"/>
            </td>
        </tr>
        <tr id="tr5">
            <td id="td2" class="kv-label">
                <span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <input id="input1" type="file" name="file" value="选择文件"/>
            </td>
        </tr>
        <tr>
            <input name="type" style="display: none;" value="${type}"/>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
<div id="ifrDialog">
</div>
