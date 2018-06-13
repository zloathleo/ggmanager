var myview = $.extend({}, $.fn.datagrid.defaults.view, {
    onAfterRender: function (target) {
        $.fn.datagrid.defaults.view.onAfterRender.call(this, target);
        var opts = $(target).datagrid('options');
        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
        vc.children('div.datagrid-empty').remove();
        if (!$(target).datagrid('getRows').length) {
            var d = $('<div class="datagrid-empty"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
            d.css({
                position: 'absolute',
                left: 0,
                top: '50%',
                width: '100%',
                textAlign: 'center'
            });
        }
    }
});
/*
* EasyUI DataGrid根据字段动态合并单元格
* @param tableID 要合并table的id
* @param colList 要合并的列,用逗号分隔(例如："name,department,office");
*/
function mergeCellsByField(tableID, colList) {
    var ColArray = colList.split(",");
    var tTable = $('#' + tableID);
    var TableRowCnts = tTable.datagrid("getRows").length;
    var tmpA;
    var tmpB;
    var PerTxt = "";
    var CurTxt = "";
    var alertStr = "";
    for (j = ColArray.length - 1; j >= 0; j--) {
        PerTxt = "";
        tmpA = 1;
        tmpB = 0;

        for (i = 0; i <= TableRowCnts; i++) {

            if (i == TableRowCnts) {
                CurTxt = "";
            }
            else {
                CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
            }

            if (PerTxt == CurTxt) {
                tmpA += 1;
            }
            else {
                tmpB += tmpA;

                tTable.datagrid('mergeCells', {
                    index: i - tmpA,
                    field: ColArray[j],
                    rowspan: tmpA,
                    colspan: null
                });
                tmpA = 1;
            }
            PerTxt = CurTxt;
        }
    }
}
var easyuiErrorFunction = function (XMLHttpRequest) {
    $.messager.progress('close');
   
};

var createGridHeaderContextMenu = function (e, field) {
    e.preventDefault();
    var grid = $(this); /* grid本身 */
    var headerContextMenu = this.headerContextMenu; /* grid上的列头菜单对象 */
    if (!headerContextMenu) {
        var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
        var asc = $('<div iconCls="icon-asc" field="asc">升序</div>').appendTo(tmenu);
        var desc = $('<div iconCls="icon-desc" field="desc">降序</div>').appendTo(tmenu);
        var filedHTML = $('<div iconCls="icon-columns"></div>');
        var span = $('<span>显示列/隐藏列</span>');
        var spdiv = $('<div></div>');
        var fields = grid.datagrid('getColumnFields');
        for (var i = 0; i < fields.length; i++) {
            var fildOption = grid.datagrid('getColumnOption', fields[i]);
            if (!fildOption.hidden) {
                $('<div iconCls="icon-checked" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(spdiv);
            } else {
                $('<div iconCls="icon-unchecked" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(spdiv);
            }
        }
        span.appendTo(filedHTML);
        spdiv.appendTo(filedHTML);
        filedHTML.appendTo(tmenu);
        headerContextMenu = this.headerContextMenu = tmenu.menu({
            onClick: function (item) {
                var f = $(this).attr('field')
                var fieldProperty = $(item.target).attr('field');
                if (item.iconCls == 'icon-checked') {
                    grid.datagrid('hideColumn', fieldProperty);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-unchecked'
                    });
                }
                if (item.iconCls == 'icon-unchecked') {
                    grid.datagrid('showColumn', fieldProperty);
                    $(this).menu('setIcon', {
                        target: item.target,
                        iconCls: 'icon-checked'
                    });
                }
                if (item.iconCls == 'icon-asc') {
                    var options = grid.datagrid('options');
                    options.sortName = f;
                    options.sortOrder = fieldProperty;
                    grid.datagrid('reload');
                }
                if (item.iconCls == 'icon-desc') {
                    var options = grid.datagrid('options');
                    options.sortName = f;
                    options.sortOrder = fieldProperty;
                    grid.datagrid('reload');
                }
            }
        });
    }
    headerContextMenu.attr('field', field);
    headerContextMenu.menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};

/** 
* 
* panel关闭时回收内存
*/

if ($.fn.pagination){
	$.fn.pagination.defaults.beforePageText = '第';
	$.fn.pagination.defaults.afterPageText = '共{pages}页';
	$.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
}
if ($.fn.datagrid){
	$.fn.datagrid.defaults.loadMsg = '正在处理，请稍待。。。';
	$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
	$.fn.datagrid.defaults.method = 'post';
//	$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
}
if ($.fn.treegrid) {
    $.fn.treegrid.defaults.loadMsg = '正在处理，请稍待。。。';
    $.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
    /**
    * 
    * 扩展treegrid，使其支持平滑数据格式
    */
    $.fn.treegrid.defaults.loadFilter = function (data, parentId) {
        var opt = $(this).data().treegrid.options;
        var idFiled, textFiled, parentField;
        if (opt.parentField) {
            idFiled = opt.idFiled || 'id';
            textFiled = opt.textFiled || 'text';
            parentField = opt.parentField;
            var i, l, treeData = [], tmpMap = [];
            for (i = 0, l = data.length; i < l; i++) {
                tmpMap[data[i][idFiled]] = data[i];
            }
            for (i = 0, l = data.length; i < l; i++) {
                if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                    if (!tmpMap[data[i][parentField]]['children'])
                        tmpMap[data[i][parentField]]['children'] = [];
                    data[i]['text'] = data[i][textFiled];
                    tmpMap[data[i][parentField]]['children'].push(data[i]);
                } else {
                    data[i]['text'] = data[i][textFiled];
                    treeData.push(data[i]);
                }
            }
            return treeData;
        }
        return data;
    };
}
if ($.messager){
	$.messager.defaults.ok = '确定';
	$.messager.defaults.cancel = '取消';
}
if ($.fn.validatebox){
	$.fn.validatebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
	$.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
	$.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
	$.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
}
if ($.fn.numberspinner) {
    $.fn.numberspinner.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.numberbox){
	$.fn.numberbox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.combobox){
	$.fn.combobox.defaults.missingMessage = '该输入项为必输项';
}
if ($.fn.tree) {
    $.fn.tree.defaults.onLoadError = easyuiErrorFunction;
    /**
    * 
    * 扩展tree，使其支持平滑数据格式
    */
    $.fn.tree.defaults.loadFilter = function (data, parent) {
        var opt = $(this).data().tree.options;
        var idFiled, textFiled, parentField;
        if (opt.parentField) {
            idFiled = opt.idFiled || 'id';
            textFiled = opt.textFiled || 'text';
            parentField = opt.parentField;
            var i, l, treeData = [], tmpMap = [];
            for (i = 0, l = data.length; i < l; i++) {
                tmpMap[data[i][idFiled]] = data[i];
            }
            for (i = 0, l = data.length; i < l; i++) {
                if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
                    if (!tmpMap[data[i][parentField]]['children'])
                        tmpMap[data[i][parentField]]['children'] = [];
                    data[i]['text'] = data[i][textFiled];
                    tmpMap[data[i][parentField]]['children'].push(data[i]);
                } else {
                    data[i]['text'] = data[i][textFiled];
                    treeData.push(data[i]);
                }
            }
            return treeData;
        }
        return data;
    };
}
if ($.fn.combobox) {
    $.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
}
if ($.fn.form) {
    $.fn.form.defaults.onLoadError = easyuiErrorFunction;
}
if ($.fn.combotree){
    $.fn.combotree.defaults.missingMessage = '该输入项为必输项';
    /**
    * 
    * 扩展combotree，使其支持平滑数据格式
    */
    $.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;
}
if ($.fn.combogrid){
    $.fn.combogrid.defaults.missingMessage = '该输入项为必输项';
    $.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
}
if ($.fn.calendar){
	$.fn.calendar.defaults.weeks = ['日','一','二','三','四','五','六'];
	$.fn.calendar.defaults.months = ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'];
}
if ($.fn.datebox){
	$.fn.datebox.defaults.currentText = '今天';
	$.fn.datebox.defaults.closeText = '关闭';
	$.fn.datebox.defaults.okText = '确定';
	$.fn.datebox.defaults.missingMessage = '该输入项为必输项';
	$.fn.datebox.defaults.formatter = function(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	};
	$.fn.datebox.defaults.parser = function(s){
		if (!s) return new Date();
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	};
}
if ($.fn.datetimebox && $.fn.datebox){
	$.extend($.fn.datetimebox.defaults,{
		currentText: $.fn.datebox.defaults.currentText,
		closeText: $.fn.datebox.defaults.closeText,
		okText: $.fn.datebox.defaults.okText,
		missingMessage: $.fn.datebox.defaults.missingMessage
	});
}

$.extend($.fn.validatebox.defaults.rules, {
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的密码不一致'
    },
    equalToPw: {
        validator: function (value, param) {
            return value.toUpperCase() == $(param[0]).val();
        },
        message: '原密码有错！'
    },
    idcard: {
        validator: function (value, param) {
            return idCard(value);
        },
        message: '请输入正确的身份证号码'
    }
});

var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];    // 加权因子     
var ValideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2];            // 身份证验证位值.10代表X     
var idCard = function (value) {
    value = trim(value.replace(/ /g, ""));               //去掉字符串头尾空格                       
    if (value.length == 15) {
        return isValidityBrithBy15IdCard(value);       //进行15位身份证的验证      
    } else if (value.length == 18) {
        var a_idCard = value.split("");                // 得到身份证数组     
        if (isValidityBrithBy18IdCard(value) && isTrueValidateCodeBy18IdCard(a_idCard)) {   //进行18位身份证的基本验证和第18位的验证  
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}
/**   
* 判断身份证号码为18位时最后的验证位是否正确   
* @param a_idCard 身份证号码数组   
* @return   
*/
function isTrueValidateCodeBy18IdCard(a_idCard) {
    var sum = 0;                             // 声明加权求和变量     
    if (a_idCard[17].toLowerCase() == 'x') {
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作     
    }
    for (var i = 0; i < 17; i++) {
        sum += Wi[i] * a_idCard[i];            // 加权求和     
    }
    valCodePosition = sum % 11;                // 得到验证码所位置     
    if (a_idCard[17] == ValideCode[valCodePosition]) {
        return true;
    } else {
        return false;
    }
}
/**   
* 验证18位数身份证号码中的生日是否是有效生日   
* @param idCard 18位书身份证字符串   
* @return   
*/
function isValidityBrithBy18IdCard(idCard18) {
    var year = idCard18.substring(6, 10);
    var month = idCard18.substring(10, 12);
    var day = idCard18.substring(12, 14);
    var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
    // 这里用getFullYear()获取年份，避免千年虫问题     
    if (temp_date.getFullYear() != parseFloat(year)
              || temp_date.getMonth() != parseFloat(month) - 1
              || temp_date.getDate() != parseFloat(day)) {
        return false;
    } else {
        return true;
    }
}
/**   
* 验证15位数身份证号码中的生日是否是有效生日   
* @param idCard15 15位书身份证字符串   
* @return   
*/
function isValidityBrithBy15IdCard(idCard15) {
    var year = idCard15.substring(6, 8);
    var month = idCard15.substring(8, 10);
    var day = idCard15.substring(10, 12);
    var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法     
    if (temp_date.getYear() != parseFloat(year)
                  || temp_date.getMonth() != parseFloat(month) - 1
                  || temp_date.getDate() != parseFloat(day)) {
        return false;
    } else {
        return true;
    }
}
//去掉字符串头尾空格     
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}