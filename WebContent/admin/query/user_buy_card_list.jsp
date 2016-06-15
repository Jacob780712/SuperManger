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
		<title>购卡统计</title>
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
		
		function updat(id){
			window.location.href="updateStatus.jhtml?id="+id;
			
		}
		function seeStrack(info){
			alert(info)
		}
		
		
		function init() {
			var tabObj = document.getElementById("tab");
			
		    var colIndex = 1;
		    var rowBeginIndex = 2;
		    if (tabObj != null) {
		        var i, j, m;
		        var intSpan;
		        var strTemp;
		        m = 0;
		        for (i = rowBeginIndex; i < tabObj.rows.length; i++) {
		            intSpan = 1;
		            m++;
		            strTemp = tabObj.rows[i].cells[colIndex].innerHTML;
		            for (j = i + 1; j < tabObj.rows.length; j++) {
		                if (strTemp == tabObj.rows[j].cells[colIndex].innerHTML) {
		                    intSpan++;
		                    tabObj.rows[i].cells[1].rowSpan = intSpan;
		                    tabObj.rows[j].cells[1].style.display = "none";
		                 
		                    tabObj.rows[i].cells[7].innerHTML = tabObj.rows[i].cells[7].innerHTML*1 +
	                    		tabObj.rows[j].cells[7].innerHTML*1
		                    tabObj.rows[i].cells[7].rowSpan = intSpan;
		                    tabObj.rows[j].cells[7].style.display = "none";
		                    tabObj.rows[i].cells[8].innerHTML =  tabObj.rows[i].cells[8].innerHTML*1 +
	                    		tabObj.rows[j].cells[8].innerHTML*1;
		                    tabObj.rows[i].cells[8].rowSpan = intSpan;
		                    tabObj.rows[j].cells[8].style.display = "none";
		                    tabObj.rows[i].cells[9].innerHTML =  (tabObj.rows[i].cells[9].innerHTML*1 +
	                    		tabObj.rows[j].cells[9].innerHTML*1).toFixed(2);
		                    tabObj.rows[i].cells[9].rowSpan = intSpan;
		                    tabObj.rows[j].cells[9].style.display = "none";
		                    tabObj.rows[i].cells[10].innerHTML =  (tabObj.rows[i].cells[10].innerHTML*1 +
	                    		tabObj.rows[j].cells[10].innerHTML*1).toFixed(2);
		                    tabObj.rows[i].cells[10].rowSpan = intSpan;
		                    tabObj.rows[j].cells[10].style.display = "none";
		                    
		                }
		                else {
		                    break;
		                }
		            }

		        }
		        i = j - 1;
		    }
		}
		
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="userBuyCard.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;购卡统计
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="12">
							<strong>查询配置
							</strong>
						</th>
					</tr>
					<tr height="10">
						<td align="left">
							时间范围:
							<input size="8" value="${param.beginTime}" onclick="showFmtControls('yyyy-MM-dd')" readonly="readonly" id="beginTime"  name="beginTime" value="${param.beginTime }"  type="text" class="input_line" style="width:100px;" />&nbsp;
							到&nbsp;<input id="endTime" value="${param.endTime}"  readonly="readonly" name="endTime" type="text" value="${param.endTime }"   class="input_line" style="width:100px;" onclick="showFmtControls('yyyy-MM-dd')" size="8"/>
						</td>
						<td align="left">
							商户名称
							<input type="text" id="mchName" name="mchName" value="${param.mchName}"/>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<td align="center">购卡总人数
						</td>
						<td align="center">两张储值卡及以上总人数
						</td>
						<td align="center">购卡总张数
						</td>
						<td align="center">购卡总面额
						</td>
						<td align="center">购卡总金额
						</td>
						<td align="center">其中账户支付总额
						</td>
						<td align="center">其中移动支付总额
						</td>
					</tr>
					<tr>
						<td align="center">
							${TransDataBo.renshu}
						</td>
						<td align="center">
							${TransDataBo.twomk}
						</td>
						<td align="center">
							${TransDataBo.purchase_number}
						</td>
						<td align="center">
							<fmt:formatNumber value="${TransDataBo.svc_quota/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							<fmt:formatNumber value="${TransDataBo.purchase_amount/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							<fmt:formatNumber value="${TransDataBo.account_pay_amount/100}" pattern="##0.00"/>
						</td>
						<td align="center">
							<fmt:formatNumber value="${TransDataBo.pay_amount/100}" pattern="##0.00"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" id="tab" width="90%">
					<tr>
						<th align="left" colspan="12">
							<strong>购卡统计</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							日期
						</td>
						<td align="center">
							购卡人数
						</td>
						<td align="center">
							首次购卡人数
						</td>
						<td align="center">
							购卡数量
						</td>
						<td align="center">
							购卡面额
						</td>
						<td align="center">
							购卡金额
						</td>
						<td align="center">
							账户支付金额
						</td>
						<td align="center">
							移动支付金额
						</td>
					</tr>
					<c:forEach var="item" items="${page.result}" varStatus="status">
						<tr>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								${item.date}
							</td>
							<td align="center">
								${item.renshu}
							</td>
							<td align="center">
								${item.twomk}
							</td>
							<td align="center">
								${item.purchase_number}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.svc_quota/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.purchase_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.account_pay_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.pay_amount/100}" pattern="##0.00"/>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>