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
		<title>用户统计</title>
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
		                    tabObj.rows[i].cells[6].innerHTML = parseInt(tabObj.rows[i].cells[6].innerHTML) +
                    			parseInt(tabObj.rows[j].cells[6].innerHTML);
		                    tabObj.rows[i].cells[6].rowSpan = intSpan;
		                    tabObj.rows[j].cells[6].style.display = "none";
		                    tabObj.rows[i].cells[7].innerHTML = (tabObj.rows[i].cells[7].innerHTML*1 +
	                    		tabObj.rows[j].cells[7].innerHTML*1).toFixed(2);
		                    tabObj.rows[i].cells[7].rowSpan = intSpan;
		                    tabObj.rows[j].cells[7].style.display = "none";
		                    tabObj.rows[i].cells[8].innerHTML =  (tabObj.rows[i].cells[8].innerHTML*1 +
	                    		tabObj.rows[j].cells[8].innerHTML*1).toFixed(2);
		                    tabObj.rows[i].cells[8].rowSpan = intSpan;
		                    tabObj.rows[j].cells[8].style.display = "none";
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
		<form name="frmPage" id="queryFrom" action="userList.jhtml" method="post">
			<div>
				<div align="left">
					您现在的位置：查询统计 &gt;&gt;用户统计
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
							<input type="submit" value="查询"/>
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<td align="center">微信关注总数:
							${TransDataBo.weixin}
						</td>
						<td align="center">取消关注总数:
							${TransDataBo.qg}
						</td>
						<td align="center">注册总数:
							${TransDataBo.bishu}
						</td>
						<td align="center">买单总人数:
							${TransDataBo.bussiness_amount}
						</td>
						<td align="center">两次买单及以上总人数:
							${TransDataBo.twomd}
						</td>
						<td align="center">买卡总人数:
							${TransDataBo.pay_amount}
						</td>
						<td align="center">两张储值卡及以上人数
							${TransDataBo.twomk}
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" id="tab" width="90%">
					<tr>
						<th align="left" colspan="12">
							<strong>用户统计</strong>
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
							微信关注数
						</td>
						<td align="center">
							取消关注数
						</td>
						<td align="center">
							注册人数
						</td>
						<td align="center">
							买单人数
						</td>
						<td align="center">
							其中首次买单人数
						</td>
						<td align="center">
							购卡人数
						</td>
						<td align="center">
							其中首次购卡人数
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
								${item.weixin}
							</td>
							<td align="center">
								${item.qg}
							</td>
							<td align="center">
								${item.bishu}
							</td>
							<td align="center">
								${item.bussiness_amount}
							</td>
							<td align="center">
								${item.twomd}
							</td>
							<td align="center">
								${item.pay_amount}
							</td>
							<td align="center">
								${item.twomk}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>