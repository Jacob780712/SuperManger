<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title>用户组管理</title>
<script language="javascript" src="../scripts/common.js"></script>
		<link href="../themes/default/css.css" rel="stylesheet" type="text/css" />
		<LINK href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script type="text/javascript">
			function batch_do(entityName, action)
			{ 
				   if (!atleaseOneCheck())
				        {
				            alert('请至少选择一个' + entityName + '！');
				            return;
				        }
				
				    if (confirm("确定要" + entityName + "?"))
				    {
				        var form = document.forms.frmPage;
				        form.action = action;
				        form.submit();
				    }
			}
			//checkbox中至少有一项被选中
			function atleaseOneCheck()
			{
			    var items = document.getElementsByName('chkIds');
			    if (items.length > 0) {
			        for (var i = 0; i < items.length; i++)
			        {
			            if (items[i].checked == true)
			            {
			                return true;
			            }
			        }
			    } else {
			        if (items.checked == true) {
			            return true;
			        }
			    }
			    return false;
			}
			
		function gotoPage(pageNo,callSubmit)
		{
		    document.getElementById("pageNo").value= pageNo;
			if(callSubmit==1)
			{
				frmPage.submit();		
			}
		}
		</script>
		
</head>
<body>

<form action="" method="post" name="frmPage" id="frmPage">
	<div class="right2">
			<div align="left">您现在的位置：用户管理  &gt;&gt; 用户组管理</div>
			<table border='0' >
				<tr height="10">
				<td>
				&nbsp;
				</td></tr>
			</table>
				<table class="table_a marginb10">
						<tr>
							<th align="left" colspan="6">
							<span class="float_Right">
								<a href="roleMgtAction!preSave.jhtml?param=create" class="dh"> <img src="../images/admin/add.jpg" border="0" align="absmiddle"/>&nbsp;新增</a>
								<a href="javascript:batch_do('删除','roleMgtAction!remove.jhtml')" class="dh"> <img src="../images/admin/del.jpg" border="0" align="absmiddle"/>&nbsp;删除</a>
							</span>
							<strong>用户组管理</strong>
							</th>
						</tr>
						<tr class="align_Center">
						    <td width="5%"><strong>编号</strong></td>
							<td width="20%">
								<strong>组名</strong>
							</td>
							<td width="30%">
								<strong>描述</strong>
							</td>
							<td width="10%">
								<strong>添加方式</strong>
							</td>
							<td width="15%">
								<strong>操作</strong>
							</td>
							<td width="5%">
								<strong>选择</strong>
							</td>
						</tr>
						<c:forEach var="item" items="${page.result}" varStatus="status">
				      	<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)' onMouseout='onTrMoveOut(this)'  class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose> >
					      	<td align="center" style="word-break : break-all"><fmt:parseNumber value="${(page.currentPageNo-1)*page.pageSize+status.index+1}"  /></td>
					      	<td align="center" style="word-break : break-all">${item.name}</td>
					      	<td style="word-break : break-all">${item.remark}</td>
					      	
					      	<td align="center" style="word-break : break-all">${item.definedName}</td>
					      	<c:set value="roleMgtAction!preSaveRoleAuth.jhtml?id=${item.roleId}" var="url"></c:set>
					      	<td align="center" style="word-break : break-all"><a href="${url}" ><img border=0 src="../images/admin/edit.jpg" alt="权限设置"/>权限设置</a>
					      	<a href="roleMgtAction!preSave.jhtml?param=update&id=${item.roleId}" ><img border=0 src="../images/admin/edit.jpg" alt="修改"/>修改</a></td>
					      	<td align="center" style="word-break : break-all"><INPUT id='chk_${item.roleId}' type=checkbox name='chkIds' value='${item.roleId}' /></td>
				        </tr>
				        </c:forEach> 	 
				</table>
				<div align="right"><%@ include file="../../common/page.jsp"%></div>
			</div>
			</form>
</body>
 
</html>
