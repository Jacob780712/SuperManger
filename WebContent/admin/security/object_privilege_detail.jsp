<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>选择角色</title>
		<script language="javascript" src="../js/Windows.js"></script>
		<script src="../scripts/LookupFunctionLib.js"></script>
		<script src="../scripts/Windows.js"></script>
		<script src="../scripts/jQuery/jquery-1.2.6.js"></script>
		<script src="../scripts/SelectBoxUtils.js"></script>
		<LINK href="../themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
	</head>

	<SCRIPT language=javascript>

function cmdProcess(cmdStr)
{	
	if(cmdStr=="back")
	{		
		window.location="listUserAction!list.jhtml";
	}
	else
	{
		if(!checkForm())
			return;
		
		document.objectPrivilegeMgmtForm.objectPrivileges.value=getObjectPrivileges();
		document.objectPrivilegeMgmtForm.submit();
	}
}
function checkForm()
{
	
	return true;
}

function getObjectPrivileges()
{
	objectPrivileges = "";
	
	for(i=0;i<objectPrivilegeMgmtForm.grantedModuGroups.options.length;i++)
	{
		if(objectPrivileges.length>0)
			objectPrivileges = objectPrivileges+";";			
		objectPrivileges=objectPrivileges+"user_assign_modugroup:"+document.objectPrivilegeMgmtForm.grantedModuGroups.options[i].value;					
	}
	
	//user role
	jQuery("input[@name='grantedRoles']").each(function(){
	   if($(this).attr("checked")==true){
	    if(objectPrivileges.length>0)
			objectPrivileges = objectPrivileges+";";			
		objectPrivileges=objectPrivileges+"user_role:"+jQuery(this).attr("value");					
	   }
	});
	return objectPrivileges;
	
}

function upMessageModuGroups(id)
{
	var obj = document.getElementById(id);
	upItem(obj);
}

function downMessageModuGroups(id)
{
  var obj = document.getElementById(id);
	downItem(obj);
}
function removeGrantedModuGroups()
{
	for(i=objectPrivilegeMgmtForm.grantedModuGroups.options.length-1;i>-1;i--)
	{
		if(objectPrivilegeMgmtForm.grantedModuGroups.options[i].selected)
		{
			objectPrivilegeMgmtForm.grantedModuGroups.options[i]=null;
		}		
	}
}
function addGrantedModuGroups()
{
	url = "listModuGroupActionSelect!list.jhtml?";
	url = url + "setReturnFunctionName=selectedModuGroups";
	openPopup(url,"",600,350,200,100);
}
function selectedModuGroups(retStr)
{
	if(retStr=="")
		return false;

	var rcplistAccounts = retStr.split("|");
	for(i=0;i<rcplistAccounts.length;i++)
	{
		mgBizId = "";
		mgBizName = "";	
		var rcplist = rcplistAccounts[i].split(":");
		for(j=0;j<rcplist.length;j++)
		{
			if(j==0)
				mgBizId=rcplist[j];
			if(j==1)
				mgBizName=rcplist[j];	
		}
		objectPrivilegeMgmtForm.grantedModuGroups.options[objectPrivilegeMgmtForm.grantedModuGroups.options.length]=new Option(mgBizName,mgBizId);		
	}	
		
	window.focus();
}
//****************************




</SCRIPT>

	<body>
		<div class="right2">
			<div align="center">
				<div align="left" class="kjj">
					配置业务
				</div>
				<form action="objectPrivilegeMgmtAction!save.jhtml"
					onsubmit="return checkForm()" name="objectPrivilegeMgmtForm"
					method="post">

					<table width="70%" border="0" align="left" cellpadding="0"
						cellspacing="0" class="table_a word_break">
						<tr align="center">
							<td colspan="3">
								用户数据权限
							</td>
						</tr>

						<tr>
							<td align="right" width="50%">
								用户名
							</td>
							<td class="detailFormRowField" colspan="2">
								${grantee.loginid } &nbsp;
							</td>
						</tr>

						<tr>
							<td align="right">
								姓名
							</td>
							<td class="detailFormRowField" colspan="2">
								${grantee.name } &nbsp;
							</td>
						</tr>
						<tr>
							<td align="right" class="detailFormRowLabel">
								模块组
							</td>
							<td class="detailFormRowField">
								<table>
									<tr>
										<td>
											<select name="grantedModuGroups" size="2"
												id="grantedModuGroups" multiple="true"
												id="grantedModuGroups"
												style="WIDTH: 150pt; height: 100px; border: 1px solid;">
												<c:if test="${not empty moduGroupList}">
													<c:forEach var="item" items="${moduGroupList}"
														varStatus="stat">
														<option value='${item.objectId}'>
															${item.objectName}
														</option>
													</c:forEach>
												</c:if>
											</select>
										</td>
										<td>
											<table>
												<tr>
													<td>
														<a href="javascript:addGrantedModuGroups()">增加 </a>
													</td>
												</tr>
												<tr>
													<td>
														<a href="javascript:removeGrantedModuGroups()">删除 </a>
														&nbsp;
													</td>
												</tr>
												<tr>
													<td>
														<a
															href="javascript:upMessageModuGroups('grantedModuGroups')">上移</a>
													</td>
												</tr>
												<tr>
													<td>
														<a
															href="javascript:downMessageModuGroups('grantedModuGroups')">下移</a>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>

						</tr>
							<tr>
								<td align="right" class="detailFormRowLabel">
									可再授权的角色
								</td>
								<td class="detailFormRowField">
								
											<c:forEach items="${roleList}" var="role">
													<div style="padding-top:10px;"><input type="checkbox" name="grantedRoles" <c:if test="${role.selected=='y'}">checked="checked"</c:if> value="${role.roleId}">${role.name } &nbsp;&nbsp;</div>
											</c:forEach>
									&nbsp;
								</td>
							</tr>
						<tr align="center">
							<td colspan="3">
								<input name="save" type="Button" class="buttonface" value="保存"
									onclick="javascript:cmdProcess('save')">
								&nbsp;
								<input name="cancel" type="button" class="buttonface" value="返回"
									onclick="javascript:cmdProcess('back')">
							</td>
						</tr>
						<input type="hidden" name="userId" id="userId"
							value="${grantee.userId }" />
						<input type="hidden" name="objectPrivileges" id="objectPrivileges" />
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
