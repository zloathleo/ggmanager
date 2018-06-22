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
    <script type="text/javascript" src="../../Scripts/self/open_operate.js"></script>
    <script type="text/javascript" src="../../Scripts/self/common.js"></script>
    <script type="text/javascript">
        function DialogSave(__dialog) {
            $('#form_update').form('submit', {
                success: function (data) {
                    OnSuccess(__dialog, data)
                }
            });
        }
        parent.DialogSave = DialogSave;
    </script>
</head>
<body class="easyui-layout">
<form id="form_update" action="update_item.do" method="post">
    <table style="width: 565px" class="kv-table">
        <tbody>
        <input name="id" style="display: none;" value="${item.id}"/>
        <tr>
            <td class="kv-label">
                页面名称<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" disabled="disabled" style="width: 96%;"
                       name="label" value="${item.label}"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                页面描述
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="des" value="${item.des}"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                引用模板<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <select class="easyui-combobox" editable="false" name="mbId"
                        style="width: 100px; height: 28px; line-height: 28px;" panelheight="auto" disabled="disabled">
                    <option value=${item.mbId}>${item.mbLabel}</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                图片资源
            </td>
            <td class="kv-content">
                <span class="textbox easyui-fluid" style="width: 100%; height: 100px;">
                    <textarea name="imgUrls" autocomplete="off" style="margin: 0px; height: 100px; width: 95%;">${item.imgUrls}</textarea>
                </span>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                视频资源
            </td>
            <td class="kv-content">
                <span class="textbox easyui-fluid" style="width: 100%; height: 60px;">
                    <textarea name="videoUrls" autocomplete="off" style="margin: 0px; height: 60px; width: 95%;">${item.videoUrls}</textarea>
                </span>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                文字资源
            </td>
            <td class="kv-content">
                <span class="textbox easyui-fluid" style="width: 100%; height: 60px;">
                    <textarea name="textUrls" autocomplete="off" style="margin: 0px; height: 60px; width: 95%;">${item.textUrls}</textarea>
                </span>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
<div id="ifrDialog">
</div>
