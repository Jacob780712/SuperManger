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
		<title>提现列表</title>
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
		
		function reject(id){
			var t = window.confirm("确定人工驳回该笔提现申请？");
			if(t){
				document.getElementById("rejectId").value=id;
				frmPage.action="cashOutReject.jhtml";
				frmPage.submit();
			}
		}
		
		function exportExl(){
			window.location="cashOutExport.jhtml";
		}
		
		function importExl(){
			window.location="importExl.jhtml";
		}
		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="cashOutList.jhtml" method="post">
			<input type="hidden" id="rejectId" name="rejectId"/>
			<div>
				<div align="left">
					您现在的位置：业务管理 &gt;&gt;提现
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
							<input type="text" id="mobilePhone" name="mobilePhone" value="${param.mobilePhone}"/>
						</td>
						<td align="left">
							卡号:
							<input type="text" id="card_number" name="card_number" value="${param.card_number}"/>
						</td>
						<td align="left">
							状态:${param.status}
							<select id="status" name="status">
								<c:if test="${param.status==0||param.status==null}">
									<option value="0" selected="selected">进行中</option>
									<option value="1">成功</option>
									<option value="2">失败</option>
								</c:if>
								<c:if test="${param.status==1}">
									<option value="0">进行中</option>
									<option value="1" selected="selected">成功</option>
									<option value="2">失败</option>
								</c:if>
								<c:if test="${param.status==2}">
									<option value="0">进行中</option>
									<option value="1">成功</option>
									<option value="2" selected="selected">失败</option>
								</c:if>
							</select>
						</td>
						<td align="left">
							<input type="submit" value="查询"/>
						</td>
					</tr>
					<tr>
						<td align="left">
							批次号:
							<input type="text" id="batch_number" name="batch_number" value="${param.batch_number}"/>
						</td>
						<td align="center">
							<input type="button" value="导出提现文件" onclick="exportExl()"/>
						</td>
						<td align="center">
							<input type="button" value="导入提现列表" onclick="importExl()"/>
						</td>
						<td align="left">
						</td>
						<td align="left">
						</td>
						<td align="left">
						</td>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>查询结果列表</strong>
						</th>
					</tr>
					<tr class="align_Center">
						<td align="center">
							序号
						</td>
						<td align="center">
							批次号
						</td>
						<td align="center">
							姓名
						</td>
						<td align="center">
							手机号
						</td>
						<td align="center">
							卡号
						</td>
						<td align="center">
							提现金额
						</td>
						<td align="center">
							申请时间
						</td>
						<td align="center">
							状态
						</td>
						<td align="center">
							备注
						</td>
						<td align="center">
							提现id
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
								${item.batch_number}
							</td>
							<td align="center">
								${item.full_name}
							</td>
							<td align="center">
								${item.mobile_phone}
							</td>
							<td align="center">
								${item.card_number}
							</td>
							<td align="center">
								<fmt:formatNumber value="${item.amount/100}" pattern="##0.00"/>
							</td>
							<td align="center">
								 <fmt:formatDate value="${item.create_date}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="center">
								<c:if test="${item.status==0}">处理中</c:if>
								<c:if test="${item.status==1}">成功</c:if>
								<c:if test="${item.status==2}">失败</c:if>
							</td>
							<td align="center">
								${item.remark}
							</td>
							<td align="center">
								${item.id}
							</td>
							<td align="center">
								<c:if test="${item.status==0}">
									<input type="button" value="人工驳回" onclick="reject('${item.id}')"/>
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