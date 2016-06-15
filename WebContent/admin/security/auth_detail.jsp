<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>权限管理</title>
		<LINK href="../themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css
			rel=stylesheet>
		<script type="text/javascript">
			function saveItem(){
			    var name = document.getElementById("authName");
				if (name.value == null || name.value == ""){
					alert("权限名称为必填项！");
					return false;
				}
				document.forms.auth.action="authMgtAction!save.jhtml";
				document.forms.auth.submit();
			}
		</script>
	</head>

	<body>
		<div class="right2">
			<div align="center">
				<div align="left" class="kjj">
					<c:if test="${auth.authId == null}">
					添加权限
				</c:if>
					<c:if test="${auth.authId != null}">
					修改权限信息
				</c:if>
				</div>

				<!-- 修改模块信息 -->
					<form method="post" name="auth" id="auth" action="">
					   <input type="hidden" name="authId" value="${auth.authId }">
						<table class="table_a word_break">
                             <tr>
								<td width="5%" >
									权限名称
								</td>
								<td width="43%" >
									<input type="text" name="authName" id="authName"
									    value="${auth.authName }" />
									<font color="red">( *权限名称必填)</font>
								</td>
							</tr>
							<tr>
								<td width="5%" >
									对象名称
								</td>
								<td width="43%" >
									<input type="text" name="objectName"
									    value="${auth.objectName }" />
								</td>
							</tr>
							<tr>
								<td width="5%" >
									URL
								</td>
								<td width="43%" >
									<input type="text" name="url"
									    value="${auth.url }"
										style="font-size: 9pt; width: 40%" />
								</td>
							</tr>
							<tr>
								<td width="5%" >
									方法名
								</td>
								<td width="43%" >
									<input type="text" name="methodName"
									    value="${auth.methodName }" />
								</td>
							</tr>
							<tr>
								<td width="5%" >
									所属模块
								</td>
								<td width="43%" >
								 <select name="moduId" style="WIDTH: 100pt">
								     <c:forEach items="${moduList}" var="modu">
									<option
										value='${modu.moduId }'
										<c:if test="${modu.moduId==auth.moduId}"> selected </c:if>>
										${modu.name }
									</option>
									</c:forEach>
								 </select>
								</td>
							</tr>
							<tr>
								<td width="5%" >
									挂载菜单
								</td>
								<td width="43%" >
								
								<select name="menuId" style="WIDTH: 100pt">
								   <option value="-1">无菜单</option>
								   
								     <c:forEach items="${menuList}" var="menu">
									<option
										value='${menu.menuId }'
										<c:if test="${menu.menuId==auth.menuId}"> selected </c:if>>
										${menu.name }
									</option>
									</c:forEach>
								 </select>
								</td>
							</tr>
							<tr>
								<td width="5%" >
									权限说明
								</td>
								<td width="43%" style="word-break : break-all">
									<input type="text" name="remark"
									    value="${auth.remark }"
										style="font-size: 9pt; width: 40%" />
								</td>
							</tr>
							<tr align_Center>
								<td style="margin-left: 150px;" width="10%" colspan="4">
									<input type="button" name="Submit" onclick="saveItem()" value="确  定" />
									<input name="back" type="button"
										onClick="window.location ='listAuthAction!list.jhtml?s=1'"
										value="返回">
								</td>
							</tr>
						</table>
					</form>
			</div>
	</body>
</html>
