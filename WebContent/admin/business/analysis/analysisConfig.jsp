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
		<title>收益计算配置</title>
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
		
		function updateConfig(){
			var t = window.confirm("由于技术问题，修改储值收益配置时，请先提醒开发人员修改客户端查询视图");
			if(t){
				document.getElementById("type").value= "update";
				frmPage.action="investmentAnalysisConfig.jhtml";
				frmPage.submit();
			}
		}
		function saveConfig(){
			document.getElementById("type").value= "update";
			if(check()){
				frmPage.action="investmentAnalysisConfigSave.jhtml";
				frmPage.submit();
			}
		}
		
		function check(){
			var ranges = document.getElementById("ranges").value;
			var re = /^[1-9]+[0-9]*]*$/;   //判断字符串是否为数字     //判断正整数 
			if (!re.test(ranges)){
				alert("请输入整数");
				return false;
			}else{
				return true;
			}
		}

		</script>
	</head>
	<body>
		<form name="frmPage" id="queryFrom" action="investmentAnalysisConfig.jhtml" method="post">
			<input type="hidden" id="type" name="type"/>
			<div>
				<div align="left">
					您现在的位置：业务管理 &gt;&gt;收益计算配置
				</div>
				<table class="table_a marginb10" width="90%">
					<tr>
						<th align="left" colspan="11">
							<strong>计算配置</strong>
						</th>
					</tr>
				</table>
				<table class="table_a marginb10" width="90%">
					<tr>
						<td align="center">计算基数范围（以N天内交易数据为准）</td>
						<td align="center">
							<c:if test="${type=='select'}">
								计算${config.ranges}天以内交易数据
							</c:if>
							<c:if test="${type=='update'}">
								计算<input type="text" id="ranges" name="ranges" value="${config.ranges}"/>天以内交易数据
							</c:if>
							
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<c:if test="${type=='select'}">
								<input type="button" value="修改" onclick="updateConfig()"/>
							</c:if>
							<c:if test="${type=='update'}">
								<input type="button" value="保存" onclick="saveConfig()"/>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>