
function dialogOpener(action,title)
{
	var str;
	str = window.showModalDialog(action,'','dialogWidth=600px;dialogHeight=400px;scroll=yes;status=no');
	return str;
}

function replaceAll(Str,oldStr,newStr)
{	
	var retStr = new String(Str);	
	var oldStrObj = new String(oldStr);
	var newStrObj = new String(newStr);
	
	while(retStr.indexOf(oldStrObj)!=-1)
	{
		retStr = retStr.replace(oldStrObj,newStrObj);	
	}	
	return retStr;
}

function windowOpener(action,title)
{
	action=replaceAll(action,"lookupAction.do","view/default_lookup_view_frame.jsp");	
	return dialogOpener(action,'','dialogWidth=600px;dialogHeight=400px;scroll=yes;status=no');
}

function getValue(str,seperator,index)
{
	if (str == '' || typeof(str) == 'undefined')
		return '';
		
	var rcplist = str.split(seperator);
	if(rcplist.length>0)
	{
		return rcplist[index];
	}			
}

function getLookupReturnId(str)
{
	return getValue(str,',',0);			
}

function getLookupReturnName(str)
{
	return getValue(str,',',1);			
}

function getLookupReturnCode(str)
{
	return getValue(str,',',2);			
}

function getSelectedValueStr(id,name,code,seperator)
{
	var str = '';
	str = id+seperator+name+seperator+code;	
	return str;
}

function getSelectedValueStr(id,name,code)
{
	return getSelectedValueStr(id,name,code,',');
}

function returnOpener(id,name,code)
{
	var str = getSelectedValueStr(id,name,code);
	returnSelectValueStrToOpener=str;
}

function returnSelectValueStrToOpener(selectedValueStr)
{
	window.returnValue=selectedValueStr;	
	window.close();	
}


