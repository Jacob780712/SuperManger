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
		<title>微信结算</title>
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
		
		function setmed(){
			var idstr = document.getElementById("idstr").value;
			if(idstr==""){
				alert("请选择要结算的记录");
				return;
			}
			var batch_number = prompt("请输入结算批次号", "");
			var aut_fee = prompt("请输入实际结算手续费", "");
			var aut_amount = prompt("请输入实际结算金额", "");
			var t = confirm("确定提交吗？");
			if(batch_number==""){
				alert("请输入批次号")
				return;
			}
			if(aut_fee==""){
				alert("实际手续费单位元")
				return;
			}
			if(aut_amount==""){
				alert("实际结算金额单位元")
				return;
			}
			if(t){
				var idstr = document.getElementById("idstr").value;
				frmPage.action="setWechatSetmed.jhtml?idstr="+idstr+"&batchNumber="+batch_number
						+"&aut_fee="+aut_fee+"&aut_amount="+aut_amount;
				frmPage.submit();
			}else{
				return;
			}
		}		
		
		function stmGoOn(){
			frmPage.action="setWechatList.jhtml?status=1";
			frmPage.submit();
		}
		
		function checkThis(id){
			var checkBox = document.getElementById("check"+id);
			var idstr = document.getElementById("idstr").value;
			if(checkBox.checked){
				document.getElementById("idstr").value = idstr+id+",";
			}else{
				document.getElementById("idstr").value = idstr.replace(id+",","");
			}
		}
		function init() {
			var tabObj = document.getElementById("tab");
			
		    var colIndex = 2;
		    var rowBeginIndex = 1;
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
		                if (strTemp.length>26&strTemp == tabObj.rows[j].cells[colIndex].innerHTML) {
		                    intSpan++;
		                    tabObj.rows[i].cells[2].rowSpan = intSpan;
		                    tabObj.rows[j].cells[2].style.display = "none";
		                    tabObj.rows[i].cells[7].rowSpan = intSpan;
		                    tabObj.rows[j].cells[7].style.display = "none";
		                    tabObj.rows[i].cells[8].rowSpan = intSpan;
		                    tabObj.rows[j].cells[8].style.display = "none";
		                    tabObj.rows[i].cells[9].rowSpan = intSpan;
		                    tabObj.rows[j].cells[9].style.display = "none";
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
	<body onload="init()">
		<form name="frmPage" id="queryFrom" action="setWechatList.jhtml" method="post">
		<input type="hidden" id="idstr" name="idstr" />
			<div>
				<div align="left">
					您现在的位置：结算管理 &gt;&gt;微信结算
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
							批次号:
							<input type="text" id="batch_number"  name="batch_number" value="${param.batch_number }"/>
						</td>
						<td align="left">
							结算状态:
							<select id="status" name="status">
								<c:if test="${param.status==null||param.status==2}">
									<option selected="selected" value="2">全部</option>
									<option value="0">已结算</option>
									<option value="1">未结算</option>
								</c:if>
								<c:if test="${param.status=='0'}">
									<option value="2">全部</option>
									<option value="0" selected="selected">已结算</option>
									<option value="1">未结算</option>
								</c:if>
								<c:if test="${param.status=='1'||statusted=='0'}">
									<option value="2">全部</option>
									<option value="0">已结算</option>
									<option value="1" selected="selected">未结算</option>
								</c:if>
							</select>
						</td>
						<td align="left">
							<c:if test="${flag=='oneStm'}">
								<input type="button" value="继续结算" onclick="stmGoOn()"/>
							</c:if>
							<c:if test="${flag!='oneStm'}">
								<input type="submit" value="查询"/>
							</c:if>
						</td>
						<td align="left">
							<input type="button" value="结算" onclick="setmed()"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" id="tab" width="90%">
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							日期
						</td>
						<td align="center">
							批次号
						</td>
						<td align="center">
							交易笔数
						</td>
						<td align="center">
							交易金额
						</td>
						<td align="center">
							交易手续费
						</td>
						<td align="center">
							结算金额
						</td>
						<td align="center">
							实际手续费
						</td>
						<td align="center">
							实际结算金额
						</td>
						<td align="center">
							状态
						</td>
						<td align="center">
							选择
						</td>
					</tr>
					<c:forEach var="item" items="${page.result}" varStatus="status">
						<tr id='tr_${status.count}'>
							<td align="center">
								${status.index+1}
							</td>
							<td align="center">
								<fmt:formatDate value="${item.date}"
									pattern="yyyy-MM-dd" />
							</td>
							<td align="center">
								${item.batch_number}
							</td>
							<td align="center">
								${item.business_pen}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.business_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.business_fee/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.settlement_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.aut_business_fee/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.aut_settlement_amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								<c:if test="${item.status==0}">
									已结算
								</c:if>
								<c:if test="${item.status==1}">
									未结算
								</c:if>
							</td>
							<td align="center">	
								<c:if test="${item.status==1}">
									<input type="checkbox" id="check${item.id}" onclick="checkThis('${item.id}')"/>
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