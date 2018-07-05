function ToLinkList(val, urlPath) {
    if (!val || val == '') {
        return '';
    } else {
        var list = val.split('\r\n');
        var result = '';
        list.forEach(function (item) {
            result = result + '<a target=\'_blank\' href=\'' + urlPath + item + '\'>' + item + '</a><br>';
        });
        return result;
    }
}

function ToLink(val) {
    if (!val || val == '') {
        return '';
    } else {
        return '<a href=' + val + ' target=_blank>' + val + '</a>';
    }
}

function ToImg(val, urlPath) {
    if (!val || val == '') {
        return '';
    } else if (val.substr(0, 4).toLowerCase() == "http") {
        return '<a href=\'' + val + '\' target=_blank><img style=width:40px;height:40px src=\'' + val + '\'/></a>';
    } else {
        return '<a href=\'' + urlPath + val + '\' target=_blank><img style=width:40px;height:40px src=\'' + urlPath + val + '\'/></a>';
    }
}

function ToLine(val) {
    if (!val || val == '') {
        return '';
    } else {
        var urls = val.split('\r\n');
        var result = '';
        urls.forEach(function (url) {
            result = result + url + '<br>';
        });
        return result;
    }
}

function ToScreenType(val) {
    return val === 0 ? '竖屏' : '横屏';
}
