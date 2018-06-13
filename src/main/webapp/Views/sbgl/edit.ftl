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
            $('#form_update').form('submit', {
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
</head>
<body class="easyui-layout">
<form id="form_update" action="update_item.do" method="post">
    <table style="width: 560px" class="kv-table">
        <tbody>
        <input name="id" style="display: none;" value="${item.id}"/>

        <tr>
            <td class="kv-label">
                名称<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
            ${item.label}
            </td>
        </tr>

        <tr>
            <td class="kv-label">
                设备页面ID<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <#--<input class="easyui-validatebox" type="text" style="width: 300px;" name="ggymId"-->
                       <#--value="${item.ggymId}"/>-->

                <select name="ggymId" class="easyui-combobox" labelPosition="top" editable="false" style="width:100px;">

                    <#list selectOpList as selectOp>
                             <#if selectOp.value==item.ggymId>
                                 <option value=${selectOp.value} selected>${selectOp.display}</option>
                             <#else>
                                 <option value=${selectOp.value}>${selectOp.display}</option>
                             </#if>
                    </#list>

                </select>

            </td>
        </tr>
        <tr>
            <td class="kv-label">
                描述
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 300px;" name="des" value="${item.des!}"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                设备位置
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 300px;" name="location"
                       value="${item.location!}"/>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
<div id="ifrDialog">
</div>
