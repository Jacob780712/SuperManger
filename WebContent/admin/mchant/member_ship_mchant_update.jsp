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
		<title>门店到店礼遇修改</title>
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
				setGoodsList();
				frmPage.submit();		
			}
		}
		
		function checkForm() {
	    	document.getElementById("queryFrom").submit();
		}
		
		function showFmtControls(fmt) {
			WdatePicker({dateFmt:fmt});
		}
		
		function updateShip(mchNumber){
			queryFrom.action="membershipUpdate.jhtml?mchNumber="+mchNumber;
			queryFrom.submit();
		}
		
		function checkGoods(goodsNumber,goodsName){
			var ele = document.getElementById("v"+goodsNumber);
			if(ele.checked==true){
				queryFrom.mchShips.options[queryFrom.mchShips.options.length]=new Option(goodsName,goodsNumber);
			}else{
				for(i=queryFrom.mchShips.options.length-1;i>-1;i--)
				{
					if(queryFrom.mchShips.options[i].value==goodsNumber)
					{
						queryFrom.mchShips.options[i]=null;
					}		
				}
			}
			
		}
		
		function removeShips(){
			for(i=queryFrom.mchShips.options.length-1;i>-1;i--)
			{
				if(queryFrom.mchShips.options[i].selected)
				{
					var goodsNumber = queryFrom.mchShips.options[i].value;
					queryFrom.mchShips.options[i]=null;
					document.getElementById("v"+goodsNumber).checked="";
				}		
			}
		}
		
		function setGoodsList(){
			var content = "";
			for(i=queryFrom.mchShips.options.length-1;i>-1;i--){
				content = content + queryFrom.mchShips.options[i].value+"--"+queryFrom.mchShips.options[i].text+"##";
			}
			if(content==""){
				document.getElementById("goodsList").value="empty";
			}else{
				document.getElementById("goodsList").value=content;
			}
		}
		
		function update(){
			setGoodsList();
			frmPage.action="membershipUpdateCommit.jhtml";
			frmPage.submit();
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="membershipUpdate.jhtml" method="post" onsubmit="setGoodsList()">
			<input type="hidden" id="goodsList" name="goodsList"/>
			<input type="hidden" id="mchNumber" name="mchNumber" value="${mchNumber}"/>
			<input type="hidden" id="branchId" name="branchId" value="${branch.id}"/>
			<div>
				<div align="left">
					您现在的位置：商户管理 &gt;&gt;门店到店礼遇修改
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="4">
							<strong>礼遇配置&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								商户名称:${mchName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								商户编号:${mchNumber}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								门店名称:${branch.branch_name}
							</strong>
						</th>
						<th align="right" colspan="2">
							<input type="button" onclick="javascript:history.back()" value="返回" />
						</th>
					</tr>
					<tr>
						<td colspan="2" align="center">会员专享简介</td>
						<td colspan="4" align="center">
							<input type="text" id="vip_brief" style="width:40%;" name="vip_brief" value="${mchant.vip_brief}"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">会员专享介绍</td>
						<td colspan="4" align="center">
							<input type="text" id="vip_introd" style="width:80%;" name="vip_introd" value="${mchant.vip_introd}"/>
						</td>
					</tr>
					<tr>
						<td colspan="3" align="right">
						到店礼遇&nbsp;&nbsp;&nbsp;&nbsp;
							<select name="mchShips" size="2"
								id="mchShips" multiple="true"
								style="WIDTH: 150pt; height: 100px; border: 1px solid;">
								<c:if test="${not empty listMchCourtesyRef}">
									<c:forEach var="item" items="${listMchCourtesyRef}"
										varStatus="stat">
										<option value='${item.goods_number}'>
											${item.goods_name}
										</option>
									</c:forEach>
								</c:if>
							</select>
						</td>
						<td colspan="3">
							<table>
								<tr>
									<td align="left">
										<a href="javascript:removeShips()">删除 </a>
										&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th align="left" colspan="6">
							<strong>礼遇列表</strong>
						</th>
					</tr>
					<tr>
						<td colspan="3" align="right">
							礼遇名称:<input type="text" style="width:200px;" id="goodName" name="goodName" value="${param.goodName}"/>
						</td>
						<td colspan="3">
							<input type="submit" value="查询"/>
						</td>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							礼遇编号
						</td>
						<td align="center">
							礼遇名称
						</td>
						<td align="center">
							礼遇价格
						</td>
						<td align="center">
							创建时间
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
								<fmt:formatNumber value="${item.sale_price/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<c:if test="${item.status=='11'}">
									<input type="checkbox" checked="checked" id="v${item.goods_number}"  onclick="checkGoods('${item.goods_number}','${item.goods_name}')"/>
								</c:if>
								<c:if test="${item.status!='11'}">
									<input type="checkbox" id="v${item.goods_number}"  onclick="checkGoods('${item.goods_number}','${item.goods_name}')"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="6" align="center">
							<input type="button" value="提交" onclick="update()"/>
						</td>
					</tr>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>