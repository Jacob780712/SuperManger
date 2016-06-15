function addItem(selectElement,text,value)
{
	selectElement.options[selectElement.options.length]=new Option(text,value);
}

function getValues(selectElement)
{
	retStr = "";
	for(i=0;i<selectElement.options.length;i++)
	{
			if(retStr.length>0)
				retStr = retStr+",";
			retStr = retStr+selectElement.options[i].value;
	}		
	return retStr;
}

function getValueTexts(selectElement)
{
	retStr = "";
	for(i=0;i<selectElement.options.length;i++)
	{
			if(retStr.length>0)
				retStr = retStr+",";
			retStr = retStr+selectElement.options[i].value+":"+selectElement.options[i].text;
	}		
	return retStr;
}

function deleteSelectedItem(selectElement)
{
	for(i=selectElement.options.length-1;i>-1;i--)
	{	
		if(selectElement.options[i].selected)	
			selectElement.options[i]=null;					
	}	
}

function upItem(selectElement)
{
	for(i=0;i<selectElement.options.length;i++)
	{
		if(selectElement.options[i].selected&&i>0)
		{
			valueTemp = selectElement.options[i].value;
			textTemp = selectElement.options[i].text;
			
			selectElement.options[i].value=selectElement.options[i-1].value;
			selectElement.options[i].text=selectElement.options[i-1].text;				
			
			selectElement.options[i-1].value=valueTemp;
			selectElement.options[i-1].text=textTemp;	
			
			selectElement.options[i-1].selected=true;
			selectElement.options[i].selected=false;									
		}
	}
}

function downItem(selectElement)
{
	for(i=(selectElement.options.length-1);i>-1;i--)
	{
		if(selectElement.options[i].selected&&i<(selectElement.options.length-1))
		{
			valueTemp = selectElement.options[i].value;
			textTemp = selectElement.options[i].text;
			
			selectElement.options[i].value=selectElement.options[i+1].value;
			selectElement.options[i].text=selectElement.options[i+1].text;				
			
			selectElement.options[i+1].value=valueTemp;
			selectElement.options[i+1].text=textTemp;
			
			selectElement.options[i+1].selected=true;		
			selectElement.options[i].selected=false;			
		}
	}
}

function moveAllItem(sourceSelectElement,destSelectElement)
{
	for(i=0;i<sourceSelectElement.options.length;i++)
	{
			destSelectElement.options[destSelectElement.options.length]=new Option(sourceSelectElement.options[i].text,sourceSelectElement.options[i].value);
	}
	
	while(sourceSelectElement.options.length>0)
	{
			sourceSelectElement.options[sourceSelectElement.options.length-1]=null;					
	}
}

function moveSelectedItem(sourceSelectElement,destSelectElement)
{
	for(i=0;i<sourceSelectElement.options.length;i++)
	{
		if(sourceSelectElement.options[i].selected)	
			destSelectElement.options[destSelectElement.options.length]=new Option(sourceSelectElement.options[i].text,sourceSelectElement.options[i].value);
	}
	
	for(i=sourceSelectElement.options.length-1;i>-1;i--)
	{	
		if(sourceSelectElement.options[i].selected)	
			sourceSelectElement.options[i]=null;					
	}	
}
