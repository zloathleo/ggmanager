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
    <script type="text/javascript" src="../../Scripts/self/open_operate.js"></script>
    <script type="text/javascript" src="../../Scripts/self/common.js"></script>
    <script type="text/javascript">
        function DialogSave(__dialog) {
            $('#form_add').form('submit', {
                success: function (data) {
                    OnSuccess(__dialog, data)
                }
            });
        }
        parent.DialogSave = DialogSave;
    </script>
</head>
<body class="easyui-layout">
<form id="form_add" action="add_item.do" method="post">
    <table style="width: 660px" class="kv-table">
        <tbody>
        <tr>
            <td class="kv-label">
                设备名称<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 300px;" name="label" value="Device_${count}"
                       data-options="required:true"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                设备位置
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 300px;" name="location"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                设备描述
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 300px;" name="des"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                展示页面
            </td>
            <td class="kv-content">
                <select name="ymId" class="easyui-combobox" editable="false" style="width: 150px; height: 28px;
                        line-height: 28px;" panelheight="auto">
                    <option value="0">请选择</option>
                    <#list yms as ym>
                        <option value=${ym.id}>${ym.label}</option>
                    </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                所在分组
            </td>
            <td class="kv-content">
                <select name="fzId" class="easyui-combobox" editable="false" style="width: 150px; height: 28px;
                        line-height: 28px;" panelheight="auto">
                    <option value="0">请选择</option>
                    <#list fzs as fz>
                        <option value=${fz.id}>${fz.label}</option>
                    </#list>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
<div id="ifrDialog">
</div>
