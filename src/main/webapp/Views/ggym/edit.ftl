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
                页面名称<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                ${item.label}
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                模板类型<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <select class="easyui-combobox" editable="false" name="ggmbId" style="width: 100px; height: 28px;
                        line-height: 28px;" panelheight="auto">
                    <#list mbs as mb>

                        <#if mb.id == item.ggmbId>
                            <option selected="selected" value=${mb.id}>${mb.label}</option>
                        <#else>
                            <option value=${mb.id}>${mb.label}</option>
                         </#if>

                    </#list>
                </select>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                屏幕类型<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <select name="stype" class="easyui-combobox" editable="false" style="width: 100px; height: 28px;
                        line-height: 28px;" panelheight="auto">

                    <#if item.stype == 0>
                        <option value=0 selected="selected">竖屏</option>
                        <option value=1>横屏</option>
                    <#else>
                        <option value=0>竖屏</option>
                        <option value=1 selected="selected">横屏</option>
                    </#if>

                </select>
            </td>
        </tr>
        <tr>
            <td class="kv-label">
                图片URL<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <span class="textbox easyui-fluid" style="width: 100%; height: 120px;">
                    <textarea name="imgUrls" autocomplete="off"
                              style="margin: 0px; height: 120px; width: 90%;">${item.imgUrls!}</textarea>
                </span>
            </td>
        </tr>

        <tr>
            <td class="kv-label">
                视频URL<span class="ui-input-must">&nbsp;</span>
            </td>
            <td class="kv-content">
                <span class="textbox easyui-fluid" style="width: 100%; height: 60px;">
                        <textarea name="videoUrls" autocomplete="off"
                                  style="margin: 0px; height: 60px; width: 90%;">${item.videoUrls!}</textarea>
                </span>
            </td>
        </tr>

        <tr>
            <td class="kv-label">
                文字
            </td>
            <td class="kv-content">
                <span class="textbox easyui-fluid" style="width: 100%; height: 60px;">
                    <textarea name="textMsg" autocomplete="off"
                              style="margin: 0px; height: 60px; width: 95%;">${item.textMsg!}</textarea>
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
