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
    <script type="text/javascript">
        function EndEdit() {
            $('#grid').datagrid('reload');
        }

        function Add() {
            var paramString = $.param({
                "cmd": "add",
            });
            var url = "open_operate?" + paramString;
            easyuiDialog("页面 - 添加", url, 800, 600, paramString);
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
                easyuiDialog("页面 - 修改", url, 800, 600, paramString);
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
                easyuiDialog("页面 - 删除", url, 400, 150);
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
    <table id="grid" border="false" fit="true" class="easyui-datagrid" url="loadList"
           data-options="toolbar: '#opButton',singleSelect: true, nowrap: false ,rownumbers:true, pagination:true, pageSize:10">
        <thead>
        <tr>
            <th data-options="field:'id',hidden:'true',width:50,align:'center',sortable: false">
                ID
            </th>
            <th data-options="field:'label',width:100,align:'center',sortable: false">
                页面名称
            </th>
            <th data-options="field:'stype',width:100,align:'center',sortable: false,formatter: function(v){
            	                     return v == 0 ?'竖屏':'横屏';
            	              }">
                屏幕类型
            </th>
            <th data-options="field:'mbLabel',width:100,align:'center',sortable: false">
                模板名称
            </th>
            <th data-options="field:'imgUrls',width:200,align:'left',sortable: false,formatter: function(v){
                                     if(v){
                                        var urls = v.split('\r\n');

                                        var _result = '';
                                        if(urls){

                                            for(var _url of urls){
                                                    _result = _result + '<a target=\'_blank\' href=\''+ '${resourceUrlPath}' + _url+'\'> '+ _url +' </a> <br> ' ;
                                            }
                                            return _result;
                                         }else{
                                            return v;
                                            }
                                     }else{
                                            return v;
                                     }

            	              }">
                图片资源
            </th>
            <th data-options="field:'videoUrls',width:200,align:'left',sortable: false">
                视频资源
            </th>
            <th data-options="field:'textMsg',width:100,align:'left',sortable: false">
                文字内容
            </th>
            <th data-options="field:'mbDes',width:300,align:'left',sortable: false">
                模板描述
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
