function popUpWindowsExt(loadpos,title,width,height,top,left)
{
	loadpos=loadpos+"&popUpWindow=y";
	newWindow = window.open(loadpos,title,"width="+width+",height="+height+",top="+top+",left="+left+",toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
    newWindow.focus();
}

function popUpWindows(loadpos,title)
{
	popUpWindowsExt(loadpos,title,"600","480","110","160");
}


function goUrl(url)
{
	window.location=url;
}

function openPopup(URL,windowName,width, height,left,top) {
	
	windowFeatures = 'width=' + width + ',height=' + height+',top='+top+',left='+left
			+ ',resizable=1,scrollbars=1';

	win = window.open(URL, windowName, windowFeatures);
	if (window.focus) {
		win.focus();
	}
	return win;
}