<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>菜单信息管理</title>
		<script language="javascript" src="../scripts/common.js"></script>
		<link href="../themes/default/css.css" rel="stylesheet" type="text/css" />
		<LINK href="../themes/admin/user_userinfo.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="../themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script type="text/javascript">
		function gotoPage(pageNo,callSubmit)
		{
		    document.getElementById("pageNo").value= pageNo;
			if(callSubmit==1)
			{
				frmPage.submit();		
			}
		}
		function edit(menuId){
		    var pageNo = document.getElementById("pageNo").value;
		    var pageSize = document.getElementById("pageSize").value;
		    var url = "menuMgtAction!preSave.jhtml?menuId="+menuId+"&pageNo="+pageNo+"&pageSize="+pageSize;
		    window.location.href = url;
		}
		</script>
	</head>
	<body  style="word-break: break-all;">
		<form name="frmPage"
			action="listMenuAction!list.jhtml"
			method="post">
			<div>
			<div align="left">您现在的位置：菜单管理  &gt;&gt; 系统菜单管理</div>
			<table border='0' >
				<tr height="10">
				<td>
				&nbsp;
				</td></tr>
			</table>
				<table class="table_a marginb10" >
					<tr>
						<th align="left">
							<strong>菜单查询</strong>
						</th>
					</tr>
				</table>
				<table class="table_a word_break">
						<tr>
							<th align="left" colspan="10">
							<span class="float_Right">
								<a href="menuMgtAction!preSave.jhtml" class="thickbox dh" title="添加系统菜单"> <img src="../images/admin/add.jpg" border="0" align="absmiddle"/>&nbsp;新增菜单</a>
								<a href="javascript:batch_doEx('删除','frmPage','chkIds','menuMgtAction!remove.jhtml')" class="dh"> <img src="../images/admin/del.jpg" border="0" align="absmiddle"/>&nbsp;删除菜单</a>
								</span>
							</th>
						</tr>
						<tr class="align_Center">
							<td width="5%">
								<strong>编号</strong>
							</td>
							<td width="10%">
								<strong>菜单名称</strong>
							</td>
							<td width="12%">
								<strong>URL</strong>
							</td>
							<td width="10%">
								<strong>菜单位置</strong>
							</td>
							<td width="5%">
								<strong>菜单SEQ</strong>
							</td>
							<td width="5%">
								<strong>父节点</strong>
							</td>
							<td width="10%">
								<strong>图片路径</strong>
							</td>
							<td width="10%">
								<strong>菜单描述</strong>
							</td>
							<td width="5%">
								<strong>操作</strong>
							</td>
							<td width="5%"><strong>选择</strong></td>
						</tr>
						<c:forEach var="item" items="${page.result}" varStatus="status">
							<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)' onMouseout='onTrMoveOut(this)'  class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose> >
								<td align="center" ><fmt:parseNumber value="${(page.currentPageNo-1)*page.pageSize+status.index+1}"  /></td>
								<td align="center" >${item.name}</td>
								<td align="center" ><div style="word-wrap:break-word;white-space:pre-wrap;word-break:break-all;width:200px;">${item.url}</div></td>
					      		<td align="center" >${item.target}</td>
					      		<td align="center" >${item.seq}</td>
					      		<td align="center" >${item.parentName}</td>
					      		<td align="center" >${item.imgPath}</td>
					      		<td align="center" ><div style="word-wrap:break-word;white-space:pre-wrap;word-break:break-all;width:100px;">${item.remark}</div></td>
					      		<td align="left" style="word-break : break-all">
						      	    <a class="a_image" href="#" onClick="edit(${item.menuId })"><img src="../images/admin/edit.jpg" border="0" align="absmiddle" />&nbsp;编辑</a>
					      		</td>
					      		<td align="center" style="word-break : break-all"><input type="checkbox" name='chkIds' value='${item.menuId}' id="chkIds" />
					      		</td>
					      		
					        </tr>
						</c:forEach>
						
				</table>
				<div align="right"><%@ include file="../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>