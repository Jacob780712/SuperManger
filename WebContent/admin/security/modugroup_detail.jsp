<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>模块组管理</title>
		<LINK href="../themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css
			rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css
			rel=stylesheet>
		<script type="text/javascript">
			function saveItem(){
			    var name = document.getElementById("moduGroupName");
				if (name.value == null || name.value == ""){
					alert("模块组名称为必填项！");
					return false;
				}
				document.forms.moduGroup.action = "moduGroupMgtAction!save.jhtml";
				document.forms.moduGroup.submit();
			}
			
		</script>
	</head>

	<body>
		<div class="right2">
			<div align="center">
				<div align="left" class="kjj">
					<c:if test="${moduGroup.moduGroupId == null}">
					添加模块组
				</c:if>
					<c:if test="${moduGroup.moduGroupId!=null }">
					修改模块组信息
				</c:if>
				</div>
				<!-- 添加模块 -->

				<!-- 修改模块信息 -->
				<form name="moduGroup" id="moduGroup" method="post" action="">
				   <input type="hidden" name="moduGroupId" value="${moduGroup.moduGroupId }">
					<table class="table_a marginb10">
                            <tr>
							  <td width="5%" >
								模块组名称
							  </td>
							  <td width="45%" >
								<input type="text" name="moduGroupName" id="moduGroupName"
								    value="${moduGroup.moduGroupName }" />
								<font color="red">( *模块组名称必填)</font>
							  </td>
							</tr>
							<tr>
							  <td width="5%" >
								模块组编码
							  </td>
							  <td width="45%" >
								<input type="text" name="moduGroupCode" id="moduGroupCode"
								    value="${moduGroup.moduGroupCode }" />
							  </td>
						   </tr>
						   <tr>
							  <td width="5%" >
								模块组显示级别
							  </td>
							  <td width="45%" >
								<input type="text" name="viewSeq" id="viewSeq"
								    <c:if test="${moduGroup.viewSeq !=0}">value="${moduGroup.viewSeq }"</c:if> />
							  </td>
						   </tr>
							<tr>
							  <td width="5%" >
								模块组说明
							  </td>
							  <td width="45%" >
								<input type="text" name="moduGroupDesc"
								    value="${moduGroup.moduGroupDesc }"
									style="font-size: 9pt; width: 40%" />
							  </td>
						   </tr>
						<tr align_Center>
							<td style="margin-left: 150px;" width="10%" colspan="4">
								<input type="button" name="Submit" onClick="saveItem()" value="确  定" />
								<input name="back" type="button"
									onClick="window.location ='listModuGroupAction!list.jhtml?s=1'"
									value="返回">
							</td>
						</tr>
					</table>
				</form>
	</body>
</html>
