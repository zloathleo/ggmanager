function ToLink(val, urlPath) {
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
    return val == 0 ? '竖屏' : '横屏';
}
