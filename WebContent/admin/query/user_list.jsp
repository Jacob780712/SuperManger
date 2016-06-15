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
		<title>用户明细</title>
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
			.butttt{
			border:0px;width:100%;height:100%;background-color:#ffffff;
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
	
		
		function showFmtControls(fmt) {
			WdatePicker({dateFmt:fmt});
		}
		
		function cardList(user_id){
			frmPage.action="bankCardList.jhtml?user_id="+user_id;
			frmPage.submit();
		}		
		
		function svcCardList(mobile){
			frmPage.action="onDepositCardList.jhtml?mobilePhone="+mobile;
			frmPage.submit();
		} 
		
		function payInfo(user_id){
			frmPage.action="userPayInfo.jhtml?user_id="+user_id;
			frmPage.submit();
		}
		
		function sort(flag){
			var oldFlag = document.getElementById("orderFlag").value;
			if(flag==oldFlag&&oldFlag.length==1){
				document.getElementById("orderFlag").value= oldFlag+oldFlag;
			}else{
				document.getElementById("orderFlag").value= flag;
			}
			
			frmPage.submit();
		}
		function init(){
			var s = "${param.orderFlag}";
			var flag = s.trim();
			if(flag=='0'||flag.length<1){
				document.getElementById("zcdate").value= "注册时间 ↓";
			}
			if(flag=='00'){
				document.getElementById("zcdate").value= "注册时间 ↑";
			}
			if(flag=='1'){
				document.getElementById("cardCount").value= "储值卡张数 ↓";
			}
			if(flag=='11'){
				document.getElementById("cardCount").value= "储值卡张数 ↑";
			}
			if(flag=='2'){
				document.getElementById("payCount").value= "买单次数 ↓";
			}
			if(flag=='22'){
				document.getElementById("payCount").value= "买单次数 ↑";
			}
			if(flag=='3'){
				document.getElementById("balance").value= "账户余额 ↓";
			}
			if(flag=='33'){
				document.getElementById("balance").value= "账户余额 ↑";
			}
		}
		</script>
	</head>
	<body onload="init()">
		<form name="frmPage" id="queryFrom" action="UserList.jhtml" method="post">
			<input type="hidden" value="${param.orderFlag}" name="orderFlag" id="orderFlag"/>
 			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;用户明细
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>查询配置</strong>
						</th>
					</tr>
					<tr height="10">
						<td align="left">
							时间范围:
							<input size="8" value="${param.beginTime}" onclick="showFmtControls('yyyy-MM-dd')" readonly="readonly" id="beginTime"  name="beginTime" value="${param.beginTime }"  type="text" class="input_line" style="width:100px;" />&nbsp;
							到&nbsp;<input id="endTime" value="${param.endTime}"  readonly="readonly" name="endTime" type="text" value="${param.endTime }"   class="input_line" style="width:100px;" onclick="showFmtControls('yyyy-MM-dd')" size="8"/>
						</td>
						<td align="left">
							用户姓名:
							<input type="text" id="full_name" name="full_name" value="${param.full_name}"/>
						</td>
						<td align="left">
							手机号:
							<input type="text" id="mobile_phone" name="mobile_phone" value="${param.mobile_phone}"/>
						</td>
						<td align="left">
							排序规则:
								<c:if test="${param.orderFlag=='0'||param.orderFlag==null||param.orderFlag=='00'}">
									注册时间
								</c:if>
								<c:if test="${param.orderFlag=='1'||param.orderFlag=='11'}">
									储值卡张数
								</c:if>
								<c:if test="${param.orderFlag=='2'||param.orderFlag=='22'}">
									买单次数
								</c:if>
								<c:if test="${param.orderFlag=='3'||param.orderFlag=='33'}">
									账户余额
								</c:if>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="4">
							<strong>用户明细</strong>
						</th>
						<th align="right" colspan="7">
							<strong>
								用户账户总余额:<fmt:formatNumber value="${accounts_balance/100}" pattern="##0.00"/>
							</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							姓名
						</td>
						<td align="center">
							手机号
						</td>
						<td align="center" onclick="sort(3)">
							<input type="button" id="balance" name="balance" value="账户余额" class="butttt"/>
						</td>
						<td align="center" onclick="sort(1)">
							<input type="button" id="cardCount" name="cardCount" value="储值卡张数" class="butttt"/>
						</td>
						<td align="center" onclick="sort(2)">
							<input type="button" id="payCount" name="payCount" value="买单次数" class="butttt"/>
						</td>
						<td align="center" onclick="sort(0)">
							<input type="button" id="zcdate" name="zcdate" value="注册时间" class="butttt"/>
						</td>
						<td align="center">
							注册渠道
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
								${item.full_name}
							</td>
							<td align="center">
								${item.mobile_phone}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.accounts_balance/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								${item.cardCount}
							</td>
							<td align="center">
								${item.payCount}
							</td>
							<td align="center">
								 <fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								${item.qudao}
							</td>
							<td align="center">
								<input type="button" value="银行卡信息" onclick="cardList('${item.user_id}')"/>
								<input type="button" value="储值卡信息" onclick="svcCardList('${item.mobile_phone}')"/>
								<input type="button" value="买单信息" onclick="payInfo('${item.user_id}')"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>