<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script language="javascript" src="../scripts/common.js"></script>
		<link href="../themes/admin/css.css" rel="stylesheet" type="text/css" />
		<LINK href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script type="text/javascript">
		
		var setReturnFunctionName = "${param.setReturnFunctionName}";
		function gotoPage(pageNo,callSubmit)
		{
		    document.getElementById("pageNo").value= pageNo;
			if(callSubmit==1)
			{
				frmPage.submit();		
			}
		}
function selectAll(selectStr)
{
	for (var i=0;i<frmPage.elements.length;i++)
	{
	    var e=frmPage.elements[i];
	    if (e.type=='checkbox')
	    {
		        e.disabled = "";	    
		    	if(selectStr=="")
    	             	e.checked=frmPage.allCheckBox.checked;
        	     else if(selectStr=="y")
            	     	e.checked=true;                 
               	  else
                 		e.checked=false;                                  	
	    }
	}
}

function getSelectedModuGroupIds()
{
	ids = "";
	for (var i=0;i<frmPage.elements.length;i++)
	{
	    var e=frmPage.elements[i];
	    if ((e.type=='checkbox') && e.checked && e.name!="allCheckBox")
	    {
	    			if(ids!="")
	    				ids=ids+"|";
                 	ids=ids+e.name;
	    }
	}
	return ids;
}
	

function selectModuGroup()
{
	closeMe();
	var selectedModuGroupIds = getSelectedModuGroupIds();
	callStr = "window.opener."+setReturnFunctionName+"('"+selectedModuGroupIds+"')";
	eval(callStr);
}	

function closeMe()
{
	window.close();
}		
</script>
	</head>
		
	<body>
		<!--模块组选择  -->
		
		<table class="table_a marginb10" >
					<tr>
						<th align="left">
							<strong>模块组查询</strong>
						</th>
					</tr>
				</table>
			
		<form action="listModuGroupActionSelect!list.jhtml" method="post" name="frmPage">
			<input type="hidden" name="setReturnFunctionName" value="${param.setReturnFunctionName}"/>
			<table class="table_a marginb10">
				<tr>
					<td>
						模块组名称

						<input type="text" name="moduGroupName" value="${requestScope.moduGroupName}"/>

						<input type="submit" value="查询">
				</tr>
			</table>
		<table class="table_a marginb10">
			<tr>
			      <td  width="5%" class='listTableTitleTd'>
			    	<input type="checkbox" name="allCheckBox" value="checkbox"  onclick="javascript:selectAll('')" class="checkbox">
			    </td>
				<td>
					<p align="center">模块组名称</p>
				</td>
				<td>
					<p align="center">模块组编码</p>
				</td>
				<td>
					<p align="center">模块组描述</p>
				</td>
				<td>
					<p align="center">模块组显示级别</p>
				</td>
			</tr>
			<s:iterator id="messageModuGroup" value="#request.page.result"
				status="listStat">
				<tr id='tr_<s:property value="#listStat.count" />' onMouseover='onTrMoveIn(this)' onMouseout='onTrMoveOut(this)'  class=<s:if test="#listStat.count%2==0">"list_trTwo"</s:if><s:else>"list_trOne"</s:else>>
					<td width="5%" align="center" class="listTableTd">
						<input type="checkbox" name="<s:property value="#messageModuGroup.moduGroupId" />:<s:property value="#messageModuGroup.moduGroupName" />" class="checkbox">
					</td>
					<td width="10%" >
						<p align="center"><s:property value="#messageModuGroup.moduGroupName" /></p>
					
					</td>
					<td width="10%" >
						<p align="center"><s:property value="#messageModuGroup.moduGroupCode" /></p>
						
					</td>
					<td width="10%" >
						<p align="center"><s:property value="#messageModuGroup.moduGroupDesc" /></p>
						
					</td>
					<td width="10%" >
						<p align="center"><s:property value="#messageModuGroup.viewSeq" /></p>
						
					</td>
				</tr>
			</s:iterator>
			  <tr>
			    <td class="listTableTd"> &nbsp;</td>
			    
			    <td class="listTableTd" colspan="4"> 
			    	<input type="button" name="Submit" value="完成选择" onClick="javascript:selectModuGroup()"  class="buttonface">     	
			    </td>
			  </tr>
			 <c:if test="${empty page.result}">
			 			<tr><td colspan="5">
				           <table class="table_a marginb10"> 
				             <tr>
				                <td align="center">
				                   <font color=#FF0000>
				                         没有符合条件的数据！！
				                    </font>
				                 </td>
				             </tr>
				           </table>
				           </td>
				           </tr>
				</c:if>
		</table>
		<div align="right"><%@ include file="../../common/page.jsp"%></div>
		</form>
	</body>
</html>
