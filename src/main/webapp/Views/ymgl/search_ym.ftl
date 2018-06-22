<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>广告页面管理</title>
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
        var obj = "页面";
        /*$(document).ready(function(){
            Init("load_ym", 20)
        });*/
    </script>
</head>
<body class="easyui-layout" fit="true">
<div data-options="region:'center', border:false, iconCls:'icon-list'">
    <div id="opButton">
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-10'"
           onclick="Add(obj)" title="新增">新增</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-edit'"
           onclick="Edit(obj)" title="编辑">编辑</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true, iconCls:'icon-no'"
           onclick="Delete(obj)" title="删除">删除</a>
    </div>
    <table id="grid" border="false" fit="true" class="easyui-datagrid" data-options="toolbar:'#opButton'">
        <thead>
        <tr>
            <th data-options="field:'label', width:100, align:'center', sortable: false">
                页面名称
            </th>
            <th data-options="field:'des', width:100, align:'center', sortable: false">
                页面描述
            </th>
            <th data-options="field:'mbLabel', width:100, align:'center', sortable: false">
                引用模板
            </th>
            <th data-options="field:'imgUrls', width:200, align:'center', sortable: false, formatter: ToLink">
                图片资源
            </th>
            <th data-options="field:'videoUrls', width:200, align:'center', sortable: false, formatter: ToLine">
                视频资源
            </th>
            <th data-options="field:'textUrls',width:200, align:'center', sortable: false, formatter: ToLine">
                文字资源
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
