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
    <script type="text/javascript">
        function EndEdit() {
            $('#grid').datagrid('reload');
        }

        function Add() {
            var paramString = $.param({
                "cmd": "add",
            });
            var url = "open_operate?" + paramString;
            easyuiDialog("设备 - 添加", url, 600, 400, paramString);
        }

        function Edit() {
            var _row = $('#grid').datagrid('getSelected');
            if (_row) {
                // var _itemId = _row.id.replace(/\s/g, "");
                var _itemId = _row.id;

                var paramString = $.param({
                    "cmd": "edit",
                    "itemId": _itemId
                });
                var url = "open_operate?" + paramString;
                easyuiDialog("设备 - 修改", url, 600, 400, paramString);
            }
        }

        function Delete() {
            var _row = $('#grid').datagrid('getSelected');
            if (_row) {
                var _itemId = _row.id;

                var paramString = $.param({
                    "cmd": "delete",
                    "itemId": _itemId
                });
                var url = "open_operate?" + paramString;
                easyuiDialog("设备 - 删除", url, 400, 150);
            }
        }
    </script>
</head>
<body class="easyui-layout" fit="true">

<div data-options="region:'center',border:false,iconCls:'icon-list'">
    <div id="opButton">
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-10'"
           onclick="Add()" title="新增">新增</a>
        <a href="javascript:;" class="easyui-linkbutton"
           data-options="plain:true,iconCls:'icon-edit'" onclick="Edit()" title="编辑">编辑</a>
        <a href="javascript:;" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-no'"
           onclick="Delete()" title="删除">删除</a>
    </div>
    <table id="grid" border="false" fit="true" class="easyui-datagrid" url="load_fz"
           data-options="toolbar: '#opButton',singleSelect: true, nowrap: false ,rownumbers:true, pagination:true, pageSize:20">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:'true',width:50,align:'center',sortable: false">
                分组ID
            </th>
            <th data-options="field:'label',width:150,align:'center',sortable: false">
                分组名称
            </th>
            <th data-options="field:'desc',width:200,align:'center',sortable: false">
                分组描述
            </th>
            <th data-options="field:'ymLabel',width:200,align:'center',sortable: false">
                展示页面
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
