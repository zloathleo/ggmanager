<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>修改</title>
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
        function HideRow(rowId) {
            var row = document.getElementById(rowId);
            row.style.display = "none";
        }

        $(document).ready(function(){
            if ("${type}" != "wz") {
                HideRow("tr2");
            } else {
                HideRow("tr3");
            }
        });
    </script>
</head>
<body class="easyui-layout">
<form id="form_add" action="update_item.do" method="post" enctype="multipart/form-data">
    <table style="width: 565px" class="kv-table">
        <tbody>
        <input name="id" style="display: none;" value="${item.id}"/>
        <tr id="tr1">
            <td class="kv-label">
                资源名称
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" disabled="disabled" type="text" style="width: 96%;" name="label" value="${item.label}"/>
            </td>
        </tr>
        <tr id="tr2">
            <td class="kv-label">
                资源内容
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="content" value="${item.content}"/>
            </td>
        </tr>
        <tr id="tr3">
            <td class="kv-label">
                资源描述
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="des" value="${item.des}"/>
            </td>
        </tr>
        <tr id="tr4">
            <td class="kv-label">
                关联链接
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="link" value="${item.link}"/>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
<div id="ifrDialog">
</div>
