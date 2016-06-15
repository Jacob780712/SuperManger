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
		<title>通知管理</title>
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
	
		function sendmsg(){
			window.location.href="gotomessageSend.jhtml";
		}
		
		function checkForm() {
	    	document.getElementById("queryFrom").submit();
		}
		
		function showFmtControls(fmt) {
			WdatePicker({dateFmt:fmt});
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom"
			                  action="<%=basePath %>admin/messageLogs.jhtml" method="post">
		
			<div>
				<div align="left">
					您现在的位置：业务管理 &gt;&gt;短信发送列表
				</div>
				<table class="table_a marginb10" width="90%">
				<th align="left" colspan="12">
					<strong>查询配置</strong>

				</th>
					<tr height="10">
					<td align="left">
						<strong>手机号码:</strong>
						
						<input type="text" id="mobilePhone" name="mobilePhone" value="${param.mobilePhone }"/>
					</td>
					<td align="left">
						<strong>发送类型:</strong>
						
						<select name="sentType" id="sentType" >
						          <option value="">全部</option>
						          <option value="sms/mt">短信下行</option>
						          <option value="wx/mo">微信上行</option>
						          <option value="wx/mt">微信下行</option>
						</select>
						
					</td>
					
					<td align="left">
						<strong>查询时间:</strong>
					
					 <input  onclick="showFmtControls('yyyy-MM-dd')"  value="${param.beginTime }" readonly="true" id="beginTime"  name="beginTime"   type="text" class="input_line" style="width:100px;" />&nbsp;到&nbsp;<input id="endTime" readonly="true" name="endTime"  value="${param.endTime }" type="text"   class="input_line" style="width:100px;" onclick="showFmtControls('yyyy-MM-dd')" />
				    
						<input type="button" onclick="checkForm()" value="查 询" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" onclick="sendmsg()" value="发送短信" />
					</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
				<th align="left" colspan="7">
					<strong>查询结果列表</strong>

				</th>
					<tr class="align_Center">
						<td width="4%">
							<strong>序号</strong>
						</td>
						<td width="7%">
							<strong>号码</strong>
						</td>
						<td width="4%">
							<strong>类型</strong>
						</td>
						<td width="10%">
							<strong>发送时间</strong>
						</td>
						<td width="4%">
							<strong>发送结果</strong>
						</td>
						<td width="30%">
							<strong>内容</strong>
						</td>
						<td width="10%">
							<strong>备注</strong>
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
								${item.toAddress}
							</td>
							<td align="center">
							<c:if test="${item.noticeType=='sms/mt'}">
   								     短信下行
							</c:if>
							<c:if test="${item.noticeType=='wx/mo'}">
   									微信上行
							</c:if>
							<c:if test="${item.noticeType=='wx/mt'}">
   									微信下行
							</c:if>
								
							</td>
							<td align="center">
							   <fmt:formatDate value="${item.noticeDate}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<c:choose>
									<c:when test="${item.noticeResult==0}">
										成功
									</c:when>
									<c:otherwise>
										失败
									</c:otherwise>
								</c:choose>
							</td>
							<td align="center">
							    ${item.noticeContent}
							</td>
							<td>
								${item.deliveryDesc}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div align="right"><%@ include file="../../../common/page.jsp"%></div>
			</div>
		</form>
	</body>
</html>