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
		<title>储值明细</title>
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
		
		function userDetail(fullName,mobile,mchName,ck_name,cardNumber,sales_amount){
			document.getElementById("fullName").value= fullName;
			document.getElementById("mobile").value= mobile;
			document.getElementById("mchName").value= mchName;
			document.getElementById("ck_name").value= ck_name;
			document.getElementById("cardNumber").value= cardNumber;
			document.getElementById("sales_amount").value= sales_amount;
			frmPage.action="cardUserDetail.jhtml";
			frmPage.submit();
		}
		
		function dataExport(){
			frmPage.action="prepaidCardDetailsExport.jhtml";
			frmPage.submit();
			frmPage.action="prepaidCardDetails.jhtml";
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="prepaidCardDetails.jhtml" method="post">
			<input type="hidden" id="fullName" name="fullName"/>
			<input type="hidden" id="mobile" name="mobile"/>
			<input type="hidden" id="mchName" name="mchName"/>
			<input type="hidden" id="ck_name" name="ck_name"/>
			<input type="hidden" id="cardNumber" name="cardNumber"/>
			<input type="hidden" id="sales_amount" name="sales_amount"/>
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;储值明细
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<span style="float: left" >
								<strong>
									<strong>储值明细
									&nbsp;&nbsp;&nbsp;&nbsp;
									储值总余额:<fmt:formatNumber value="${balance/100}" pattern="##0.00"/>
									</strong>
									
								</strong>
							</span>
							<span style="float: right">
								<input type="button" value="返回" onclick="javascript:history.back()"/>
							</span>
						</th>
					</tr>
					<tr height="10">
						<td align="left">
							购买时间范围:
							<input size="8" value="${param.beginTime}" onclick="showFmtControls('yyyy-MM-dd')" readonly="readonly" id="beginTime"  name="beginTime" value="${param.beginTime }"  type="text" class="input_line" style="width:100px;" />&nbsp;
							到&nbsp;<input id="endTime" value="${param.endTime}"  readonly="readonly" name="endTime" type="text" value="${param.endTime }"   class="input_line" style="width:100px;" onclick="showFmtControls('yyyy-MM-dd')" size="8"/>
						</td>
						<td align="left">
							姓名:
							<input type="text" id="full_name" name="full_name" value="${param.full_name}"/>
						</td>
						<td align="left">
							手机号:
							<input type="text" id="mobilePhone" name="mobilePhone" value="${param.mobilePhone}"/>
						</td>
						<td align="left">
							卡号:
							<input type="text" id="svc_number" name="svc_number" value="${param.svc_number}"/>
						</td>
					</tr>
					<tr>
						<td align="left">
							商户名称:
							<input type="text" id="mch_name" name="mch_name" value="${param.mch_name}"/>
						</td>
						<td align="left">
							是否首次购卡:
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
							<input type="submit" value="查询"/>
						</td>
						<td align="left">
							<input type="button" value="导出数据" onclick="dataExport()"/>
						</td>
						
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							商户名称
						</td> 
						<td align="center">
							门店名称
						</td>
						<td align="center">
							卡种
						</td>
						<td align="center">
							类型
						</td>
						<td align="center">
							折扣
						</td>
						<td align="center">
							卡号
						</td>
						<td align="center">
							状态
						</td>
						<td align="center">
							储值面额
						</td>
						<td align="center">
							购买金额
						</td>
						<td align="center">
							当前余额
						</td>
						<td align="center">
							购买人姓名
						</td>
						<td align="center">
							手机号
						</td>
						<td align="center">
							是否首次购卡
						</td>
						<td align="center">
							累计购卡张数
						</td>
						<td align="center">
							购买日期时间
						</td>
						<td align="center">
							详情
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
								${item.mch_name}
							</td>
							<td align="center">
								${item.branch_name}
							</td>
							<td align="center">
								${item.ck_name}
							</td>
							<td align="center">
								<c:if test="${item.share=='0'}">分享</c:if>
								<c:if test="${item.share=='1'}">不分享</c:if>
								&nbsp;
								<c:if test="${item.ck_type=='0'}">买送</c:if>
								<c:if test="${item.ck_type=='1'}">买折</c:if>
							</td>
							<td align="center">
								${item.ck_discount}折卡
							</td>
							<td align="center">
								${item.svc_number}
							</td>
							<td align="center">
								<c:if test="${item.status==0}">正常</c:if>
								<c:if test="${item.status==1}">用尽</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.ck_quota/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.sales_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.svc_balance/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								${item.name}
							</td>
							<td align="center">
								${item.mobile}
							</td>
							<td align="center">
								<c:if test="${item.firstFlag=='0'}">否</c:if>
								<c:if test="${item.firstFlag=='1'}">是</c:if>
							</td>
							<td align="center">
								${item.zhangshu}
							</td>
							<td align="center">
								 <fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								<input type="button" value="使用情况" onclick="userDetail('${item.name}',
								'${item.mobile}','${item.mch_name}','${item.ck_name}',
								'${item.svc_number}','${item.sales_amount}')"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>