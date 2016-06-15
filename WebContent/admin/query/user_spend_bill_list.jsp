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
		<title>储值卡交易</title>
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
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="onSpendBillList.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;储值卡消费明细
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>查询配置&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								交易总金额:<fmt:formatNumber value="${amount/100}" pattern="##0.00"/>
							</strong>
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
					</tr>
					<tr>	
						<td align="left">
							卡号:
							<input type="text" id="svc_number" name="svc_number" value="${param.svc_number}"/>
						</td>
						<td align="left">
							商户名称:
							<input type="text" id="mch_name" name="mch_name" value="${param.mch_name}"/>
						</td>
						<td align="left">
							类型:
							<select name="type" id="type">
								<c:if test="${param.type==''||param.type==null}">
									<option value='' selected="selected">全部</option>
									<option value='0'>储值用户买单</option>
									<option value='1'>普通用户买单</option>
									<option value='2'>购卡</option>
									<option value='3'>充值</option>
								</c:if>
								<c:if test="${param.type==0}">
									<option value=''>全部</option>
									<option value='0' selected="selected">储值用户买单</option>
									<option value='1'>普通用户买单</option>
									<option value='2'>购卡</option>
									<option value='3'>充值</option>
								</c:if>
								<c:if test="${param.type==1}">
									<option value=''>全部</option>
									<option value='0'>储值用户买单</option>
									<option value='1' selected="selected">普通用户买单</option>
									<option value='2'>购卡</option>
									<option value='3'>充值</option>
								</c:if>
								<c:if test="${param.type==2}">
									<option value=''>全部</option>
									<option value='0'>储值用户买单</option>
									<option value='1'>普通用户买单</option>
									<option value='2' selected="selected">购卡</option>
									<option value='3'>充值</option>
								</c:if>
								<c:if test="${param.type==3}">
									<option value=''>全部</option>
									<option value='0'>储值用户买单</option>
									<option value='1'>普通用户买单</option>
									<option value='2'>购卡</option>
									<option value='3' selected="selected">充值</option>
								</c:if>
							</select>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
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
							类型
						</td>
						<td align="center">
							订单号
						</td>
						<td align="center">
							姓名
						</td>
						<td align="center">
							手机号
						</td>
						<td align="center">
							商户名称
						</td>
						<td align="center">
							卡种
						</td>
						<td align="center">
							卡号
						</td>
						<td align="center">
							储值卡交易金额
						</td>
						<td align="center">
							储值卡余额
						</td>
						<td align="center">
							储值用户返现金额
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
								<c:if test="${item.type==0}">
									储值用户买单
								</c:if>
								<c:if test="${item.type==1}">
									普通用户买单
								</c:if>
								<c:if test="${item.type==2}">
									购卡
								</c:if>
								<c:if test="${item.type==3}">
									充值
								</c:if>
							</td>
							<td align="center">
								${item.order_number}
							</td>
							<td align="center">
								<c:if test="${item.co_tel!=''&&item.co_tel!=null}">
									${item.co_name}
								</c:if>
								<c:if test="${item.mobile_phone!=''&&item.mobile_phone!=null}">
									${item.full_name}
								</c:if>
							</td>
							<td align="center">
								<c:if test="${item.co_tel!=''&&item.co_tel!=null}">
									${item.co_tel}
								</c:if>
								<c:if test="${item.mobile_phone!=''&&item.mobile_phone!=null}">
									${item.mobile_phone}
								</c:if>
							</td>
							<td align="center">
								<c:if test="${item.mch_name_co!=''&&item.mch_name_co!=null}">
									${item.mch_name_co}
								</c:if>
								<c:if test="${item.mch_name!=''&&item.mch_name!=null}">
									${item.mch_name}
								</c:if>
							</td>
							<td align="center">
								<c:if test="${item.ck_type!=''&&item.ck_type!=null}">
									<c:if test="${item.ck_type==0}">买送</c:if>
									<c:if test="${item.ck_type==1}">折扣</c:if>
								</c:if>
								<c:if test="${item.ck_type_co!=''&&item.ck_type_co!=null}">
									<c:if test="${item.ck_type_co==0}">买送</c:if>
									<c:if test="${item.ck_type_co==1}">折扣</c:if>
								</c:if>
								,
								<c:if test="${item.ck_name!=''&&item.ck_name!=null}">${item.ck_name}</c:if>
								<c:if test="${item.ck_name_co!=''&&item.ck_name_co!=null}">${item.ck_name_co}</c:if>
							</td>
							<td align="center">
								<c:if test="${item.svc_number!=''&&item.svc_number!=null}">${item.svc_number}</c:if>
								<c:if test="${item.co_svc_number!=''&&item.co_svc_number!=null}">${item.co_svc_number}</c:if>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.spend_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.svc_balance/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<c:if test="${item.type==1}">
									<fmt:formatNumber value="${item.actual_amount/100}" pattern="##0.00"/>
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