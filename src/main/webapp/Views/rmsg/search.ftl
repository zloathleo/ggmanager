<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>查询设备</title>
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
        var obj = "消息";
        $(document).ready(function(){
            Init("load_list", 20)
        });
    </script>
</head>
<body class="easyui-layout" fit="true">
<div data-options="region:'center', border:false, iconCls:'icon-list'">
    <div id="opButton">
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-10'"
           onclick="Add(obj)" title="新增">新增</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'"
           onclick="Edit(obj)" title="编辑">编辑</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-no'"
           onclick="Delete(obj)" title="删除">删除</a>
    </div>
    <table id="grid" border="false" fit="true" data-options="toolbar: '#opButton'">
        <thead>
        <tr>
            <th data-options="field:'id', hidden:'true', width:50, align:'center', sortable:false">
                消息ID
            </th>
            <th data-options="field:'label', width:150, align:'center', sortable:false">
                消息名称
            </th>
            <th data-options="field:'startTime', width:200, align:'center', sortable:false">
                播放时间
            </th>
            <th data-options="field:'endTime', width:200, align:'center', sortable:false">
                停止时间
            </th>
            <th data-options="field:'zyLabel', width:150, align:'center', sortable:false">
                关联资源
            </th>
            <th data-options="field:'fzLabel', width:150, align:'center', sortable:false">
                关联分组
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
