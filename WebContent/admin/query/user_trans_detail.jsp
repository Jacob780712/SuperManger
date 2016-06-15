<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
	<head>
		<title>交易明细</title>
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
			.hrCla{
				color: #AACDED;
				height: 1px;
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
			
			function detail(orderNumber,orderType,mobile){
				frmPage.action="detailBpayBill.jhtml?orderNumber="+orderNumber+"&orderType="+orderType+"&mobile="+mobile;
				frmPage.submit();
			}
			
			function exportData(){
				frmPage.action="transactionDetailExport.jhtml";
				frmPage.submit();
				frmPage.action="transactionDetail.jhtml";
			}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="transactionDetail.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;交易明细
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
							商户名称:
							<input type="text" id="mch_name" name="mch_name" value="${param.mch_name}"/>
						</td>
						<td align="left">
							门店名称:
							<input type="text" id="branch_name" name="branch_name" value="${param.branch_name}"/>
						</td>
						<td align="left">
							手机号:
							<input type="text" id="mobile_phone" name="mobile_phone" value="${param.mobile_phone}"/>
						</td>
					</tr>
					<tr>
						<td align="left">
							是否为首次交易：
							<select id="firstFlag" name="firstFlag">
								<c:if test="${param.firstFlag=='all'||param.firstFlag==null}">
									<option value="all" selected="selected">全部</option>
									<option value="1">是</option>
									<option value="0">否</option>
								</c:if>
								<c:if test="${param.firstFlag=='1'}">
									<option value="all">全部</option>
									<option value="1" selected="selected">是</option>
									<option value="0">否</option>
								</c:if>
								<c:if test="${param.firstFlag=='0'}">
									<option value="all">全部</option>
									<option value="1">是</option>
									<option value="0" selected="selected">否</option>
								</c:if>
							</select>
						</td>
						<td align="left">
							状态：
							<select id="status" name="status">
								<c:if test="${param.status=='all'||param.status==null}">
									<option value="all" selected="selected">全部</option>
									<option value="1">成功</option>
									<option value="2">失败</option>
								</c:if>
								<c:if test="${param.status=='1'}">
									<option value="all">全部</option>
									<option value="1" selected="selected">成功</option>
									<option value="2">失败</option>
								</c:if>
								<c:if test="${param.status=='2'}">
									<option value="all">全部</option>
									<option value="1">成功</option>
									<option value="2" selected="selected">失败</option>
								</c:if>
							</select>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
						</td>
						<td align="left">
							<input type="button" value="导出数据" onclick="exportData()"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							日期
						</td> 
						<td align="center">
							时间
						</td> 
						<td align="center">
							订单号
						</td>
						<td align="center">
							订单状态
						</td>
						<td align="center">
							订单类型
						</td>
						<td align="center">
							手机号
						</td>
						<td align="center">
							是否为首次买单
						</td>
						<td align="center">
							商户名称
						</td>
						<td align="center">
							门店名称
						</td>
						<td align="center">
							订单金额
						</td>
						<td align="center">
							用户类型
						</td>
						<td align="center">
							储值卡扣除总额
						</td>
						<td align="center">
							账户余额支付金额
						</td>
						<td align="center">
							微信支付金额
						</td>
						<td align="center">
							储值用户返现金额
						</td>
						<td align="center">
							用户优惠金额
						</td>
						<td align="center">
							平台返总金额
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
								<fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd" />
							</td>
							<td align="center">
								<fmt:formatDate value="${item.create_date}"
									pattern="HH:mm:ss" />
							</td>
							<td align="center">
								${item.order_number}
							</td>
							<td align="center">
								<c:if test="${item.status=='0'}">处理中</c:if>
								<c:if test="${item.status=='1'}">成功</c:if>
								<c:if test="${item.status=='2'}">失败</c:if>
							</td>
							<td align="center">
								<c:if test="${item.order_type=='0'}">储值用户买单</c:if>
								<c:if test="${item.order_type=='1'}">普通用户买单</c:if>
								<c:if test="${item.order_type!='0'&&item.order_type!='1'}">礼遇</c:if>
							</td>
							<td align="center">
								${item.mobile_phone}
							</td>
							<td align="center">
								<c:if test="${item.order_type=='0'||item.order_type=='1'}">
									<c:if test="${item.firstFlag==1}">是</c:if>
									<c:if test="${item.firstFlag==0}">否</c:if>
								</c:if>
							</td>
							<td align="center">
								${item.mch_name}
							</td>
							<td align="center">
								${item.branch_name}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.business_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<c:if test="${item.userType=='0'}">非本店会员</c:if>
								<c:if test="${item.userType=='1'}">本店会员</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status=='1'}">
									<fmt:formatNumber value="${item.spendAmount/100}" pattern="##0.00"/>
								</c:if>
								<c:if test="${item.status!='1'}">
									0.00
								</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status=='1'}">
									<fmt:formatNumber value="${item.account_pay_amount/100}" pattern="##0.00"/>
								</c:if>
								<c:if test="${item.status!='1'}">
									0.00
								</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status=='1'}">
									<fmt:formatNumber value="${item.pay_amount/100}" pattern="##0.00"/>
								</c:if>
								<c:if test="${item.status!='1'}">
									0.00
								</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status=='1'}">
									<fmt:formatNumber value="${(item.pay_amount+item.account_pay_amount-item.o_return_amount)/100}" pattern="##0.00"/>
								</c:if>
								<c:if test="${item.status!='1'}">
									0.00
								</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status=='1'}">
									<fmt:formatNumber value="${(item.business_amount-item.svc_pay_amount-item.pay_amount-item.account_pay_amount)/100}" pattern="##0.00"/>
								</c:if>
								<c:if test="${item.status!='1'}">
									0.00
								</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status=='1'}">
									<fmt:formatNumber value="${item.o_return_amount/100}" pattern="##0.00"/>
								</c:if>
								<c:if test="${item.status!='1'}">
									0.00
								</c:if>
							</td>
							<td align="center">
								<input type="button" value="详情" onclick="detail('${item.order_number}','${item.order_type}','${item.mobile_phone}')"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>