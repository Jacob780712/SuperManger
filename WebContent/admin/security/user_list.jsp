<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>用户管理</title>
		<script language="javascript" src="../scripts/common.js"></script>
		<link href="../themes/default/css.css" rel="stylesheet" type="text/css" />
		<LINK href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script src="${pageContext.request.contextPath}/scripts/jQuery/jquery-1.4.4.min.js"
			type="text/javascript"></script>
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
		function searchUser(){
			frmPage.action='listUserAction!list.jhtml';
			frmPage.submit();	
		}
		function initPassWord(userId){
			$.post('initPassWord.jhtml',{userId:userId,type:'init'},function(data){
				alert(data);
			});
		}
		</script>
	</head>
	<body  >
		<form name="frmPage" action="" method="post">
			<div>
			<div align="left">您现在的位置：用户管理  &gt;&gt; 系统用户管理</div>
			<table border='0' >
				<tr height="10">
				<td>
				&nbsp;
				</td></tr>
			</table>
				<table class="table_a marginb10" >
					<tr>
						<th align="left" colspan='4'>
							<strong>用户查询</strong>
						</th>
					</tr>
					<tr>
						<td align="left">
							<strong>用户姓名</strong>
							<input id='name' name='name' value='${name }'/>
						</td>
						<td align="left">
							<strong>登录帐号</strong>
							<input id='loginid' name='loginid' value='${loginid }'/>
						</td>
						<td align="left">
							<strong>状态</strong>
							<select id='status' name='status'>
								<option value=''>全部</option>
								<option value='1' <c:if test="${status=='1' }">selected='selected'</c:if>>开通</option>
								<option value='2' <c:if test="${status=='2' }">selected='selected'</c:if>>锁定</option>
							</select>
						</td>
						<td align="left">
							<input type="button" onclick="searchUser()" value="查询">
						</td>
						
					</tr>
				</table>
				<table class="table_a marginb10">
						<tr>
							<th align="left" colspan="9">
							<span class="float_Right">
								<a href="userMgtAction!preSave.jhtml?param=create" class="thickbox dh" title="添加系统用户"> <img src="../images/admin/add.jpg" border="0" align="absmiddle"/>&nbsp;新增用户</a>
								<a href="javascript:batch_do('删除','userMgtAction!remove.jhtml')" class="dh"> <img src="../images/admin/del.jpg" border="0" align="absmiddle"/>&nbsp;删除用户</a>
								<a href="javascript:batch_do('开通','userMgtAction!forbid.jhtml')" class="dh"><img src="../images/admin/open.jpg" border="0" align="absmiddle"/>&nbsp;开通用户</a>
								<a href="javascript:batch_do('禁用','userMgtAction!forbid.jhtml?action=true')" class="dh"><img src="../images/admin/close.jpg" border="0" align="absmiddle"/>&nbsp;禁用用户</a>
								</span>
							</th>
						</tr>
						<tr class="align_Center">
							<td width="10%">
								<strong>登录ID</strong>
							</td>
							<td width="10%">
								<strong>真实姓名</strong>
							</td>
							<td width="12%">
								<strong>邮箱</strong>
							</td>
							<td width="10%">
								<strong>电话</strong>
							</td>
							
							<td width="10%">
								<strong>状态</strong>
							</td>
							<td width="15%">
								<strong>操作</strong>
							</td>
							<td width="10%"><strong>选择</strong></td>
						</tr>
						<c:forEach var="item" items="${page.result}" varStatus="status">
							<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)' onMouseout='onTrMoveOut(this)'  class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose> >
								<td align="center" style="word-break : break-all">${item.loginid}</td>
								<td align="center" style="word-break : break-all">${item.name}</td>
					      		<td align="center" style="word-break : break-all">${item.email}</td>
					      		<td align="center" style="word-break : break-all">${item.mobile}</td>	
					      		<td align="left" style="word-break : break-all"><c:if test="${item.status == 2}">锁定</c:if><c:if test="${item.status == 1}">开通</c:if></td>
					      		<td align="left" style="word-break : break-all">
						      	    <a class="a_image" href="userMgtAction!preSaveUserRole.jhtml?userId=${item.userId}" ><img src="../images/admin/edit.jpg" border="0" align="absmiddle" />&nbsp;角色</a>
						      	    <a class="a_image" href="userMgtAction!preSave.jhtml?param=update&userId=${item.userId}" ><img src="../images/admin/edit.jpg" border="0" align="absmiddle" />&nbsp;编辑</a>
						      	    <a class="a_image" href="objectPrivilegeMgmtAction!preSave.jhtml?userId=${item.userId}" ><img src="../images/admin/edit.jpg" border="0" align="absmiddle" />&nbsp;数据权限</a>
						      	    <c:if test="${sessionScope.loginUser.name=='admin'}">
						      	    <a class="a_image" href="#" onclick="initPassWord(${item.userId})" ><img src="../images/admin/edit.jpg" border="0" align="absmiddle" />&nbsp;密码重置</a>
						      	    </c:if>
					      		</td>
					      		<td align="center" style="word-break : break-all"><input type="checkbox" name='chkIds' value='${item.userId}' id="chkIds" />
					      		</td>
					      		
					        </tr>
						</c:forEach>
						
				</table>
				<div align="right"><%@ include file="../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>