//搜索回车键
document.onkeydown = function (e) {
    if (!e) e = window.event; //火狐中是 window.event
    if ((e.keyCode || e.which) == 13) {
        var btnSearch = document.getElementById("btnSearch");
        if (btnSearch != null) {
            btnSearch.focus(); //让另一个控件获得焦点就等于让文本输入框失去焦点
            btnSearch.click();
        }
    }
}
$(function () {
    $.ajaxSetup({ cache: false });
    if (top.$.messager != undefined) {
        top.$.messager.progress('close');
    }
    if ($.messager != undefined) {
        $.messager.progress('close');
    }
    //    resizeWindow();
});
function resizeWindow() {
    try {
        if (window.screen) {
            var myw = screen.availWidth;
            var myh = screen.availHeight;
            window.moveTo(0, 0);
            window.resizeTo(myw, myh);
        }
    } catch (e) { }
}
function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}
function jqGuid() {
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}
function CloseTab() {
    var currTab = parent.$('#tabs').tabs('getSelected');
    var index = parent.$('#tabs').tabs('getTabIndex', currTab);
    parent.$('#tabs').tabs('close', index);
}
function BackHisTabNoPage() {
    try {
        var tab = parent.$('#tabs').tabs('getSelected');
        var title = $("[name=title]").val();
        var index = parent.$('#tabs').tabs('getTabIndex', tab);
        var currTab = parent.$('#tabs').tabs('select', title);
        parent.$('#tabs').tabs("close", index);
    } catch (e) { }
}
function BackHisTabPage() {
    try {
        var tab = parent.$('#tabs').tabs('getSelected');
        var title = $("[name=title]").val();
        var index = parent.$('#tabs').tabs('getTabIndex', tab);
        var currTab = parent.$('#tabs').tabs('select', title);
        parent.$('#mm-tabupdate').click();
        parent.$('#tabs').tabs("close", index);
    } catch (e) { }
}

function RefreshCurTabPage() {
    try {
        var tab = $('#tabs').tabs('getSelected');
        var tabFrame = tab.find('iframe')[0]
        tabFrame.contentWindow.Refresh();
    } catch (e) { }
}
/**
* 
* 获得URL参数
* 
* @returns 对应名称的值
*/
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
function getDialogParam(name) {
    var obj = __dialog.dialog('options');
    var queryParams = obj["queryParams"];
    return queryParams[name];
}
function getIfrDialogParam(name) {
    var obj = __ifrDialog.dialog('options');
    var queryParams = obj["queryParams"];
    return queryParams[name];
}
/**
* 
* 
* 将form表单元素的值序列化成对象
* 
* @returns object
*/
function serializeObject(form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

function easyuiViewDialog(t, url, w, h, param) {
    __dialog = $('#iframeDialog').dialog({ closed: false, modal: true, shadow: false,
        cache: false, width: w, height: h, title: t, iconCls: 'icon-list',
        queryParams: param,
        content: "<iframe scrolling='auto' frameborder='0' src='" + url + "' style='width:100%; height:100%; display:block;'></iframe>",
        buttons: [{
            text: '关闭',
            iconCls: 'icon-cancel',
            handler: function () {
                __dialog.dialog('close');
            }
        }]
    });
    __dialog.window('center');
}
function easyuiDialog(t, url, w, h, param) {

    __dialog = $('#iframeDialog').dialog({ closed: false, modal: true, shadow: false,
        cache: false, width: w, height: h, title: t, iconCls: 'icon-list',
        // queryParams: param,
        content: "<iframe scrolling='auto' frameborder='0' src='" + url + "' style='width:100%; height:100%; display:block;'></iframe>",
        buttons: [{
            text: '提交',
            iconCls: 'icon-ok',
            handler: function () {
                DialogSave(__dialog);
            }
        }, {
            text: '关闭',
            iconCls: 'icon-cancel',
            handler: function () {
                __dialog.dialog('close');
            }
        }]
    });
    __dialog.window('center');
}

function easyuiOtherDialog(t, url, w, h, param) {
    __OtherDialog = parent.$('#iframeOtherDialog').dialog({ closed: false, modal: true, shadow: false,
        cache: false, width: w, height: h, title: t, iconCls: 'icon-list', left: 10, top: 10,
        queryParams: param,
        content: "<iframe scrolling='auto' frameborder='0' src='" + url + "' style='width:100%; height:100%; display:block;'></iframe>",
        buttons: [{
            text: '关闭',
            iconCls: 'icon-cancel',
            handler: function () {
                __OtherDialog.dialog('close');
            }
        }]
    });
}

function GetAjax(url, callBack) {
    $.ajax({
        url: url, type: "POST",
        data: serializeObject($("form")),
        beforeSend: function () {
            top.$.messager.progress({ title: '请稍等', msg: '数据正在处理中...' });
        },
        success: function (data) {
            eval('data=' + data);
            if (data.success) {
                $.messager.show({ title: '提示', msg: '操作成功', timeout: 3000, showType: 'slide' });
                if (callBack != undefined) {
                    callBack();
                }
                try {
                    __dialog.dialog('close');
                }
                catch (e) { }
            } else {
                $.messager.alert('错误', "保存失败，请稍后重试!", 'error');
            }
            top.$.messager.progress('close');
        },
        error: function () {
            $.messager.alert('错误', "保存失败，请稍后重试!", 'error');
            top.$.messager.progress('close');
        }
    });
}

function GetFormAjax(url, callBack) {
    top.$.messager.progress();
    __form.form('submit', {
        url: url,
        onSubmit: function () {
            var isValid = $(this).form('validate');
            if (!isValid) {
                top.$.messager.progress('close');
            }
            return isValid;
        },
        success: function (data) {
            eval('data=' + data);
            if (data.success) {
                top.$.messager.show({ title: '提示', msg: '操作成功', timeout: 5000, showType: 'slide' });
                if (callBack != undefined) {
                    callBack();
                }
                try {
                    __dialog.dialog('close');
                }
                catch (e) { }
            } else {
                $.messager.alert('错误', "操作失败，请稍后重试!", 'error');
            }
            top.$.messager.progress('close');
        },
        error: function () {
            $.messager.alert('错误', "操作失败，请稍后重试!", 'error');
            top.$.messager.progress('close');
        }
    });
}

function IsEditdata(id) {
    var isOK = true;
    if (id == undefined || id == "") {
        isOK = false;
        $.messager.alert('提示', "很抱歉，请选择要操作的数据行!", 'info');
    } else if (id.split(",").length > 1) {
        isOK = false;
        $.messager.alert('提示', "很抱歉，很抱歉，一次只能选择一条记录!", 'info');
    }
    return isOK;
}
function IsDelData(id) {
    var isOK = true;

    if (id == undefined || id == "") {
        isOK = false;
        $.messager.alert('提示', "很抱歉，请选择要操作的数据行!", 'info');
    }
    return isOK;
}
function CheckboxValue(nm) {
    var reVal = '';
    $('input:checkbox[name=' + nm + ']:checked').each(function (i) {
        reVal += $(this).val() + ",";
    });
    reVal = reVal.substr(0, reVal.length - 1);
    return reVal;
}

function CheckboxValueAndText(nm) {
    var reVal = '';
    var reText = '';
    $('input:checkbox[name=' + nm + ']:checked').each(function (i) {
        reVal += $(this).val() + ",";
        reText += $("#" + this.id + "_0000").text() + ",";
    });
    reVal = reVal.substr(0, reVal.length - 1);
    reText = reText.substr(0, reText.length - 1);
    return reVal + "|" + reText;
}

var CheckAllLinestatus = 0;
function CheckAllLine() {
    if (CheckAllLinestatus == 0) {
        CheckAllLinestatus = 1;
        $("#checkAllOff").attr('title', '反选');
        $("#checkAllOff").attr('id', 'checkAllOn');
        isChecked = true;
        $(" :checkbox").prop("checked", isChecked);
    } else {
        CheckAllLinestatus = 0;
        isChecked = false;
        $("#checkAllOn").attr('title', '全选');
        $("#checkAllOn").attr('id', 'checkAllOff');
        $(":checkbox").prop("checked", isChecked);
    }
}
//树表格复选框，点击子，把父也打勾
function ckbValueObj(e) {
    var item_id = '';
    var arry = new Array();
    arry = e.split('-');
    for (var i = 0; i < arry.length - 1; i++) {
        item_id += arry[i] + '-';
    }
    item_id = item_id.substr(0, item_id.length - 1);
    if (item_id != "") {
        $("#" + item_id).attr("checked", true);
        ckbValueObj(item_id);
    }
}
function addTab(title, url) {
    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title); //选中并刷新
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if (url != undefined && currTab.panel('options').title != '首页') {
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url, title)
                }
            })
        }
    } else {
        //        $.messager.progress({ title: '请稍等', msg: '页面正在加载中...' });
        var content = createFrame(url, title)
        $('#tabs').tabs('add', {
            title: title,
            content: content,
            cache: false,
            closable: true
        });
    }
    tabClose();
}
function createFrame(url, title) {
    url = (url.indexOf("?") != -1) ? (url + "&random=" + Math.random() + "&t=" + escape(title)) : (url + "?random=" + Math.random() + "&t=" + escape(title));
    var s = '<iframe scrolling="hidden" frameborder="0"  src="' + url + '" style="width:100%;height:100%; "></iframe>';
    return s;
}

function tabClose() {
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
    })
    $(".tabs-inner").bind('contextmenu', function (e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();

        $('#mm').data("currtab", subtitle);
        $('#tabs').tabs('select', subtitle);
        return false;
    });
}
function tabCloseEven() {
    $('#mm-tabupdate').click(function () {
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        if (url != undefined && currTab.panel('options').title != '首页') {
            $('#tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: createFrame(url, currTab.panel('options').title),
                    closable: true
                }
            })
        }
    })
    $('#mm-tabclose').click(function () {
        var currtab_title = $('#mm').data("currtab");
        $('#tabs').tabs('close', currtab_title);
    })
    $('#mm-tabcloseall').click(function () {
        $('.tabs-inner span').each(function (i, n) {
            var t = $(n).text();
            if (t != '首页') {
                $('#tabs').tabs('close', t);
            }
        });
    });
    $('#mm-tabcloseother').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        var nextall = $('.tabs-selected').nextAll();
        if (prevall.length > 0) {
            prevall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                if (t != '首页') {
                    $('#tabs').tabs('close', t);
                }
            });
        }
        if (nextall.length > 0) {
            nextall.each(function (i, n) {
                var t = $('a:eq(0) span', $(n)).text();
                if (t != '首页') {
                    $('#tabs').tabs('close', t);
                }
            });
        }
        return false;
    });
    $('#mm-tabcloseright').click(function () {
        var nextall = $('.tabs-selected').nextAll();
        if (nextall.length == 0) {
            alert('后边没有啦~~');
            return false;
        }
        nextall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    $('#mm-tabcloseleft').click(function () {
        var prevall = $('.tabs-selected').prevAll();
        if (prevall.length == 0) {
            alert('到头了，前边没有啦~~');
            return false;
        }
        prevall.each(function (i, n) {
            var t = $('a:eq(0) span', $(n)).text();
            $('#tabs').tabs('close', t);
        });
        return false;
    });
    $("#mm-exit").click(function () {
        $('#mm').menu('hide');
    })
}
function setFirstPage(ids) {
    var opts = $("#" + ids).datagrid("options");
    var pager = $("#" + ids).datagrid("getPager");
    opts.pageNumber = 1;
    opts.pageSize = opts.pageSize;
    pager.pagination('refresh', {
        pageNumber: 1,
        pageSize: opts.pageSize
    });
}


window.onload = function () {
    document.getElementsByTagName("body")[0].onkeydown = function () {

        //获取事件对象
        var elem = event.relatedTarget || event.srcElement || event.target || event.currentTarget;

        if (event.keyCode == 8) {//判断按键为backSpace键

            //获取按键按下时光标做指向的element
            var elem = event.srcElement || event.currentTarget;

            //判断是否需要阻止按下键盘的事件默认传递
            var name = elem.nodeName;

            if (name != 'INPUT' && name != 'TEXTAREA' && name != 'DIV') {
                return _stopIt(event);
            }
            if (name != "DIV") {
                try {
                    var type_e = elem.type.toUpperCase();
                    if (name == 'INPUT' && (type_e != 'TEXT' && type_e != 'TEXTAREA' && type_e != 'PASSWORD' && type_e != 'FILE')) {
                        return _stopIt(event);
                    }
                    if (name == 'INPUT' && (elem.readOnly == true || elem.disabled == true)) {
                        return _stopIt(event);
                    }
                } catch (e) { }
            }
        }
    }
}
function _stopIt(e) {
    if (e.returnValue) {
        e.returnValue = false;
    }
    if (e.preventDefault) {
        e.preventDefault();
    }

    return false;
}