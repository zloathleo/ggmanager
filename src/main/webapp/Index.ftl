<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Admin</title>
    <link type="text/css" rel="Stylesheet" href="Contents/Css/base.css"/>
    <link type="text/css" rel="Stylesheet" href="Contents/Css/platform.css"/>
    <link type="text/css" rel="Stylesheet" href="Scripts/jQEasyUI/themes/easyui.css"/>
    <link type="text/css" rel="Stylesheet" href="Scripts/jQEasyUI/themes/icon.css"/>
    <link type="text/css" rel="Stylesheet" href="Contents/Css/base.css"/>
    <script type="text/javascript" src="Scripts/jquery.min.js"></script>
    <script type="text/javascript" src="Scripts/jQEasyUI/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="Scripts/jQEasyUI/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="Scripts/main.js"></script>
    <script type="text/javascript" src="Scripts/FunctionJS.js"></script>
    <style type="text/css">
        html, body, div, span, applet, object, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
            margin: 0;
            padding: 0;
            border: 0;
            font-family: Arial, "宋体", Verdana, arial, serif, "iconfont";
            font-size: 12px;
            list-style: none;
        }
    </style>
</head>
<div id="ajax-loader" style="cursor: progress; position: fixed; top: -50%; left: -50%;
    width: 200%; height: 200%; background: #fff; z-index: 10000; overflow: hidden;">
    <img src="Contents/Images/ajax-loader.gif" style="position: absolute; top: 0; left: 0;
        right: 0; bottom: 0; margin: auto;"/>
</div>
<body class="easyui-layout">
<div data-options="region:'north',border:false" style="height: 120px" split="true">
    <div style="background: url('Contents/Images/bg1.jpg') repeat  left top transparent;">
        <div id="north">
            <div id="north_left">
                <table>
                    <tr>
                        <td style="padding-left: 30px; font-size: 30px; font-weight: bold">
                            ADAdmin
                        </td>
                    </tr>
                </table>
            </div>
            <div id="north_right">
                <table>
                    <tr>
                        <td>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="ui-header-nav">
                <dl>
                    <dt><a href="javascript:;" onclick="EmpPassword('')" style="color: #034B75"><span
                            class="ui-icon ui-icon-loginout"></span>
                        <img src="Scripts/jQEasyUI/themes/icons/40.png" class="img"/>修改密码</a></dt>
                    <dt><a href="javascript:;" onclick="QuitSystem()" style="color: #034B75"><span
                            class="ui-icon ui-icon-loginout">
                        </span>
                        <img src="Scripts/jQEasyUI/themes/icons/19.png" class="img"/>安全退出</a></dt>
                </dl>
            </div>
        </div>
        <div id="taskbar">
            <div id="taskbar_center" style="color: White; padding-left: 10px; font-size: 13px;">
                <img src="Contents/Images/user_green.png" align="absMiddle" style="vertical-align: top"/>
                欢迎：【admin】系统管理员
                <img src="Contents/Images/clock.png" align="absMiddle" style="vertical-align: top;
                        padding-left: 50px;"/>
                当前时间：<span id="x">2018年05月05日 09点15分47秒</span>
            </div>
        </div>
    </div>
</div>
<div region="west" border="true" split="true" title="&nbsp;&nbsp;菜单" style="width: 200px;
        padding: 0px;" data-options="iconCls:'icon-05'">
    <div class="easyui-accordion" fit="true" border="false">
        <div title="广告页面管理" data-options="iconCls:'icon-56'">
            <div name="csj-menu" class="csj-menu" rel="广告页面管理" src="Views/ymgl/search_ym">
                <img src="Contents/Images/pagination_next.gif">广告页面管理
            </div>
            <div name="csj-menu" class="csj-menu" rel="模板页面查询" src="Views/ymgl/search_mb">
                <img src="Contents/Images/pagination_next.gif">模板页面查询
            </div>
        </div>
        <div title="多媒体资源管理" data-options="iconCls:'icon-49'">
            <div name="csj-menu" class="csj-menu" rel="图片资源管理" src="Views/zygl/search_tp">
                <img src="Contents/Images/pagination_next.gif">图片资源管理
            </div>
            <div name="csj-menu" class="csj-menu" rel="本地视频资源管理" src="Views/zygl/search_sp">
                <img src="Contents/Images/pagination_next.gif">本地视频资源管理
            </div>
            <div name="csj-menu" class="csj-menu" rel="实时视频资源查询" src="Views/zygl/search_zb">
                <img src="Contents/Images/pagination_next.gif">实时视频资源查询
            </div>
            <div name="csj-menu" class="csj-menu" rel="文字资源查询" src="Views/zygl/search_wz">
                <img src="Contents/Images/pagination_next.gif">文字资源查询
            </div>
        </div>
        <div title="广告机设备管理" data-options="iconCls:'icon-08'">
            <div name="csj-menu" class="csj-menu" rel="设备管理" src="Views/sbgl/search_sb">
                <img src="Contents/Images/pagination_next.gif">设备管理
            </div>
            <div name="csj-menu" class="csj-menu" rel="分组管理" src="Views/sbgl/search_fz">
                <img src="Contents/Images/pagination_next.gif">分组管理
            </div>
        </div>
    </div>
</div>
<div region="center" border="true">
    <div id="tabs" class="easyui-tabs" fit="true" border="false">
        <div title="首页">
        </div>
    </div>
</div>
<div id="mm" class="easyui-menu cs-tab-menu">
    <div id="mm-tabupdate">
        刷新
    </div>
    <div class="menu-sep">
    </div>
    <div id="mm-tabclose">
        关闭
    </div>
    <div id="mm-tabcloseother">
        关闭其他
    </div>
    <div id="mm-tabcloseall">
        关闭全部
    </div>
</div>
<script type="text/javascript">
    $(window).load(function () {
        window.setTimeout(function () {
            $('#ajax-loader').fadeOut();
        }, 200);
    });
    $(function () {
        setInterval(function () {
            var myDate = new Date();
            var x = myDate.getFullYear() + "年" + (myDate.getMonth() + 1) + "月" + myDate.getDate() + "日 " + myDate.getHours() + "点" + myDate.getMinutes() + "分" + myDate.getSeconds() + "秒";
            document.getElementById("x").innerHTML = x;
        }, 1000);
    });

    function EmpPassword(empid) {
        var url = "/Views/System/UserPassword.aspx?key=" + empid;
        easyuiDialog("密码修改", url, 500, 270);
    }
</script>
</body>
</html>
<div id="iframeDialog">
</div>
<div id="iframeCompanyDialog">
</div>
