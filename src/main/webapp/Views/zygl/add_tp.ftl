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
<form id="form_add" action="add_item.do" method="post" enctype="multipart/form-data">
    <table style="width: 565px" class="kv-table">
        <tbody>
        <tr>
            <td class="kv-label">
                图片名称
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="label"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                图片描述
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="des"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                支持格式<.jpg .png .bmp><span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <input id="input1" type="file" name="file" value="选择文件"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                外部图片链接<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="content"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                关联链接
            </td>
            <td class="kv-content">
                <input class="easyui-validatebox" type="text" style="width: 96%;" name="link"/>
            </td>
        </tr>
        <tr>
            <input name="type" style="display: none;" value="${type}"/>
        </tr>
        </tbody>
    </table>
    <table border="0">
        <tr>
            <th><p style="font-size:14px">&nbsp;&nbsp;*可选择上传图片或指定外部图片链接</p></th>
        </tr>
    </table>
</form>
</body>
</html>
<div id="ifrDialog">
</div>
