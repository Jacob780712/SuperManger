<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<title>到店礼遇列表</title>
		<script language="javascript" src="<%=basePath%>scripts/common.js"></script>
		<script language="javascript" src="<%=basePath%>scripts/Windows.js"></script>
		<link href="<%=basePath%>themes/admin/css.css" rel="stylesheet" type="text/css" />
		<LINK href="<%=basePath%>themes/admin/user_userinfo.css" type=text/css
			rel=stylesheet>
		<LINK href="<%=basePath%>themes/admin/common.css" type=text/css rel=stylesheet>
		<LINK href="<%=basePath%>themes/admin/userhome.css" type=text/css rel=stylesheet>
		<script src="<%=basePath%>scripts/jQuery/jquery-1.2.6.js" type="text/javascript"></script>
		<script src="<%=basePath%>scripts/jQuery/formValidator.js" type="text/javascript"></script>
		<script language="javascript" src="<%=basePath%>scripts/jQuery/jquery.loadmask.js"></script>
		<link href="<%=basePath%>themes/admin/jquery.loadmask.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="../scripts/dateNew/My97DatePicker/date.js"></script>
		<script language="javascript" src="../scripts/dateNew/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
			.ddiv{
				display: none;
			}
		</style>
		<script type="text/javascript">
		
		function gotoPage(pageNo,callSubmit)
		{
		    document.getElementById("pageNo").value= pageNo;
			if(callSubmit==1)
			{
				frmPage.submit();		
			}
		}
		
		function checkForm() {
	    	document.getElementById("queryFrom").submit();
		}
		
		function showFmtControls(fmt) {
			WdatePicker({dateFmt:fmt});
		}
		
		function addGoods(){
			window.location.href="addGoods.jhtml";
		}
		function detail(id){
			document.getElementById("goodsId").value=id;
			document.getElementById("queryFrom").action="detailMembership.jhtml";
			document.getElementById("queryFrom").submit();
		}
		
		function update(id){
			document.getElementById("goodsId").value=id;
			document.getElementById("queryFrom").action="updateMembership.jhtml";
			document.getElementById("queryFrom").submit();
		}
		
		function del(id){
			document.getElementById("goodsId").value=id;
			document.getElementById("queryFrom").action="delMembership.jhtml";
			document.getElementById("queryFrom").submit();
		}
		
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="membershipList.jhtml" method="post">
			<input type="hidden" id="goodsId" name="goodsId"/>
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;到店礼遇列表
				</div>
				<table class="table_a marginb10" width="90%">
				<th align="left" colspan="8">
					<strong>查询配置
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;礼遇总数:${count}
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已开通礼遇:${openCount}
					</strong>
				</th>
					<tr height="10">
					<td align="left">
						<strong>礼遇名称:</strong>
						<input type="text" id="goods_name" name="goods_name" value="${param.goods_name}"/>
					</td>
					<td align="left">
						<strong>状态:</strong>
						<select id="status" name="status">
							<c:if test="${param.status==8||param.status==null}">
								<option value="8" selected="selected">所有</option>
								<option value="0">上架</option>
								<option value="1">下架</option>
								<option value="9">已删除</option>
							</c:if>
							<c:if test="${param.status==0}">
								<option value="8">所有</option>
								<option value="0" selected="selected">上架</option>
								<option value="1">下架</option>
								<option value="9">已删除</option>
							</c:if>
							<c:if test="${param.status==1}">
								<option value="8">所有</option>
								<option value="0">上架</option>
								<option value="1" selected="selected">下架</option>
								<option value="9">已删除</option>
							</c:if>
							<c:if test="${param.status==9}">
								<option value="8">所有</option>
								<option value="0">上架</option>
								<option value="1">下架</option>
								<option value="9" selected="selected">已删除</option>
							</c:if>
						</select>					
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="submit" value="查 询" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="addGoods()" value="新 增" />
					</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<th align="left" colspan="8">
						<strong>查询结果列表</strong>
					</th>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							编号
						</td>
						<td align="center">
							礼遇名称
						</td>
						<td align="center">
							状态
						</td>
						<td align="center">
							原价
						</td>
						<td align="center">
							礼遇价格
						</td>
						<td align="center">
							录入日期
						</td>
						<td align="center">
							操作
						</td>
						
					</tr>
					<c:forEach var="item" items="${page.result}" varStatus="status">
						<tr id='tr_${status.count}' onMouseover='onTrMoveIn(this)'
							onMouseout='onTrMoveOut(this)'
							class=<c:choose><c:when test="${status.count%2==0}">"list_trTwo"</c:when><c:otherwise>"list_trOne"</c:otherwise ></c:choose>>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								${item.goods_number}
							</td>
							<td align="center">
								${item.goods_name}
							</td>
							<td align="center">
								<c:if test="${item.status=='0'}">上架</c:if>
								<c:if test="${item.status=='1'}">下架</c:if>
								<c:if test="${item.status=='9'}">已删除</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.orig_price/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.sale_price/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<input type="button" value="详情" onclick="detail(${item.id})"/>
								<c:if test="${item.status=='0'||item.status=='1'}">
									<input type="button" value="修改" onclick="update(${item.id})"/>
								</c:if>
								<c:if test="${item.status=='1'}">
									<input type="button" value="删除" onclick="del(${item.id})"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>