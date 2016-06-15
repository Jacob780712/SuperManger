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

var bootPATH = __CreateJSPath("tabs_util.js");

//debugger
mini_debugger = true;   

//miniui
document.write('<script src="' + bootPATH + 'jquery_easyui/jquery.easyui.min.js" type="text/javascript"></sc' + 'ript>');
document.write('<link href="' + bootPATH + 'jquery_easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />');
document.write('<link href="' + bootPATH + 'jquery_easyui/themes/icon.css" rel="stylesheet" type="text/css" />');

function returnPreTab(title, url){
	var tab = $('#mainTabs').tabs('getSelected');
    if(tab){
        var index = $('#mainTabs').tabs('getTabIndex',tab);
        $('#mainTabs').tabs('close',index);
    }
	openTab(title, url);
}

function closeCurrentTab(title){
	var tab = $('#mainTabs').tabs('getSelected');
    if(tab){
        var index = $('#mainTabs').tabs('getTabIndex',tab);
        $('#mainTabs').tabs('close',index);
    }
	if ($('#mainTabs').tabs('exists', title)){
		$("#mainTabs").tabs('select', title);
	}
}

function openTab(title, url){
	if ($('#mainTabs').tabs('exists', title)){
		$("#mainTabs").tabs('select', title);
		var tab = $('#mainTabs').tabs('getSelected');
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		$("#mainTabs").tabs('update',{
	        tab: tab,
	        options:{
	        	content:content
	        }
	    });
	    tab.panel('refresh');	
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		$('#mainTabs').tabs('add',{
			title:title,
			content:content,
			closable:true,
			selected:true
		});
	}
}