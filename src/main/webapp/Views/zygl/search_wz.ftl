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
        function EndEdit() {
            $('#grid').datagrid('reload');
        }

        function Add() {
            var paramString = $.param({
                "cmd": "add",
                "type": "wz"
            });
            var url = "open_operate?" + paramString;
            easyuiDialog("文字 - 新增", url, 600, 280, paramString);
        }

        function Edit() {
            var _row = $('#grid').datagrid('getSelected');
            if (_row) {
                var paramString = $.param({
                    "cmd": "edit",
                    "type": "wz",
                    "itemId": _row.id
                });
                var url = "open_operate?" + paramString;
                easyuiDialog("文字 - 修改", url, 600, 280, paramString);
            }
        }

        function Delete() {
            var _row = $('#grid').datagrid('getSelected');
            if (_row) {
                var paramString = $.param({
                    "cmd": "delete",
                    "type": "wz",
                    "itemId": _row.id
                });
                var url = "open_operate?" + paramString;
                easyuiDialog("文字 - 删除", url, 350, 280);
            }
        }
    </script>
</head>
<body class="easyui-layout" fit="true">

<div data-options="region:'center',border:false,iconCls:'icon-list'">
    <div id="opButton">
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-10'"
           onclick="Add()" title="新增">新增</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'"
           onclick="Edit()" title="修改">修改</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-no'"
           onclick="Delete()" title="删除">删除</a>
    </div>
    <table id="grid" border="false" fit="true" class="easyui-datagrid" url="load_wz"
           data-options="toolbar: '#opButton', singleSelect: true, nowrap: false, rownumbers: true, pagination: true, pageSize: 20">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:'true',width:50,align:'center',sortable: false">
                资源ID
            </th>
            <th data-options="field:'label',width:200,align:'center',sortable: false">
                资源名称
            </th>
            <th data-options="field:'content',width:200,align:'center',sortable: false">
                文字内容
            </th>
            <th data-options="field:'link',width:250,align:'center',sortable: false,
                formatter: function(v){ if (v == null) return ''; else return '<a href=' + v + ' target=_blank>' + v + '</a>'; }">
                点击链接
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
