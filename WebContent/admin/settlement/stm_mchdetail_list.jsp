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
		<title>商户结算</title>
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
		
		function stm_import(id){
			var fkdate=prompt("请输入付款日期,如2016-02-10","");
			var	reg = /^(\d{4})-(\d{2})-(\d{2})$/;
			if(fkdate==null){
				return;
			}
			if(!reg.test(fkdate)){  
				alert("请输入正确的日期格式");
				return false;
			}
			if(fkdate!=""&&fkdate!=null){
				frmPage.action="updateStmMchDetail.jhtml?clmid="+id+"&fkdate="+fkdate;
				frmPage.submit();
			}else{
				return;
			}
		}

		function exportExcel(){
			frmPage.action = "exportStmExcel.jhtml";
			frmPage.submit();
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="listStmMchDetail.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：结算管理 &gt;&gt;商户结算列表
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="12">
							<strong>查询配置</strong>
						</th>
						<th align="right" colspan="12">
							<button onclick="exportExcel()" >导出到Excel</button>
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
							<input type="text" name="mch_name" id="mch_name" value="${param.mch_name}"/>
						</td>
						<td align="left">
							结算状态:
							<select id="status" name="status">
								<c:if test="${param.status==null||param.status==2}">
									<option selected="selected" value="2">全部</option>
									<option value="0">未付款</option>
									<option value="1">已付款</option>
								</c:if>
								<c:if test="${param.status=='0'}">
									<option value="2">全部</option>
									<option value="0" selected="selected">未付款</option>
									<option value="1">已付款</option>
								</c:if>
								<c:if test="${param.status=='1'||statusted=='0'}">
									<option value="2">全部</option>
									<option value="0">未付款</option>
									<option value="1" selected="selected">已付款</option>
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
							商户名称
						</td>
						<td align="center">
							结算类型
						</td>
						<td align="center">
							状态
						</td>
						<td align="center">
							开始日期
						</td>
						<td align="center">
							结束日期
						</td>
						<td align="center">
							上期未结算购卡余额
						</td>
						<td align="center">
							本期新增购卡金额
						</td>
						<td align="center">
							上期结算日
						</td>
						<td align="center">
							上期预付
						</td>
						<td align="center">
							本期消费
						</td>
						<td align="center">
							本期商户垫款
						</td>
						<td align="center">
							距上个结算日（天）
						</td>
						<td align="center">
							距下个结算日（天）
						</td>
						<td align="center">
							本期应预付
						</td>
						<td align="center">
							本期结算手续费
						</td>
						<td align="center">
							本期预付净额
						</td>
						<td align="center">
							本期未结算购卡余额
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
								${item.mch_name}
							</td>
							<td align="center">
								<c:if test="${item.type==0}">首次开通购卡</c:if>
								<c:if test="${item.type==1}">周期结算
									<c:if test="${item.set_type==1}">(全额结算)
									</c:if>
									<c:if test="${item.set_type!=1}">(非全额结算)
									</c:if>
								</c:if>
								<c:if test="${item.type==2}">提前结算</c:if>
							</td>
							<td align="center">
								<c:if test="${item.status==0}">未付款</c:if>
								<c:if test="${item.status==1}">已付款</c:if>
							</td>
							<td align="center">
								 <fmt:formatDate value="${item.start_date}"
									pattern="yyyy-MM-dd" />
							</td>
							<td align="center">
								 <fmt:formatDate value="${item.end_date}"
									pattern="yyyy-MM-dd" />
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.upper_dps_balance/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.cur_add_dps_amout/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatDate value="${item.upper_dps_date}"
									pattern="yyyy-MM-dd" />
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.upper_prepay/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.cur_consume/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.cur_arrears/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								${item.last_to_this_day}
							</td>
							<td align="center">
								${item.this_to_next_day}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.cur_prepay/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.cur_fee/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.cur_prepay_net/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.all_dps_balance/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<c:if test="${item.status==0}">
									<input type="button" value="付款" onclick="stm_import('${item.id}')"/>
								</c:if>
								<c:if test="${item.status==1}">
									${item.fkdate}
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
