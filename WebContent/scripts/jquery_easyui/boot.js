__CreateJSPath = function (js) {

    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

var bootPATH = __CreateJSPath("boot.js");

//debugger
mini_debugger = true;   

//miniui
document.write('<script src="' + bootPATH + 'tabs_util.js" type="text/javascript"></sc' + 'ript>');
document.write('<script src="' + bootPATH + 'jquery.easyui.min.js" type="text/javascript"></sc' + 'ript>');
document.write('<link href="' + bootPATH + 'themes/default/easyui.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'themes/icon.css" rel="stylesheet" type="text/css" />');
