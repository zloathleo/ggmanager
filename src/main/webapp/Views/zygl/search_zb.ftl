﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查询转播信号资源</title>
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
        var obj = "转播信号";
        var res = "zb";

        $(document).ready(function () {
            Init("load_zb", 20)
        });
    </script>
</head>
<body class="easyui-layout" fit="true">
<div data-options="region:'center',border:false,iconCls:'icon-list'">
    <div id="opButton">
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-10'"
           onclick="Add(obj, res)" title="新增">新增</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'"
           onclick="Edit(obj, res)" title="修改">修改</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-no'"
           onclick="Delete(obj, res)" title="删除">删除</a>
    </div>
    <table id="grid" border="false" fit="true" data-options="toolbar: '#opButton'">
        <thead>
        <tr>
            <th data-options="field:'id', hidden:'true', width:50, align:'center', sortable:false">
                资源ID
            </th>
            <th data-options="field:'label', width:200, align:'center', sortable:false">
                信号名称
            </th>
            <th data-options="field:'des', width:200, align:'center', sortable:false">
                信号描述
            </th>
            <th data-options="field:'link', width:250, align:'center', sortable:false" formatter="ToLink">
                点击链接
            </th>
            <th data-options="field:'content', width:150, align:'center', sortable:false">
                信号预览
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
