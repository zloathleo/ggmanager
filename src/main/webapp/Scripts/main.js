$(function () {
    tabCloseEven();
    $('div[name=csj-menu]').click(function () {
        $('div[name=csj-menu]').removeClass().addClass("csj-menu");
        var $this = $(this);
        $this.addClass("csj-menu-selected")
        var href = $this.attr('src');
        var title = $this.attr('rel');
        addTab(title, href);
    });
});
function QuitSystem() {
    $.messager.confirm('提示信息', '您确定要退出吗？', function (data) {
        if (data) {
           
        }
    });
}
function Quit() {
   
}



