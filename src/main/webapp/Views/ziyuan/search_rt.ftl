<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查询实时</title>
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

    </script>
</head>
<body class="easyui-layout" fit="true">

<div data-options="region:'center',border:false,iconCls:'icon-list'">
    <table id="grid" border="false" fit="true" class="easyui-datagrid" url="loadVideoList"
           data-options="toolbar: '#opButton',singleSelect: true, nowrap: false">
        <thead>
        <tr>
            <th data-options="field:'url',width:300,align:'center',sortable: false">
                引用路径
            </th>
            <th data-options="field:'fileName',width:300, align:'center'">路径
            </th>
        </tr>
        </thead>

    </table>
</div>
<div id="iframeDialog">
</div>
<div id="iframeOtherDialog">
</div>
<div style="display: none">
    <iframe id="ifrExp" src=""></iframe>
</div>

</body>
</html>
