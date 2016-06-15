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
            window.location.href="loadAddQrCodePage.jhtml";
        }

        function checkForm() {
            document.getElementById("queryFrom").submit();
        }

        function showFmtControls(fmt) {
            WdatePicker({dateFmt:fmt});
        }

        function delQrCode(qrId) {
            if(qrId == null || qrId.length < 1)
                return;
            $.ajax({
                type:"POST",
                url:"<%=basePath%>admin/delQrCode.jhtml",
                data:{qrId:qrId},
                success:function(data){
                    if(data == "true")
                        alert("删除成功！");
                    else
                        alert("删除失败！");
                    location.reload(true);
                },
                error:function(erro){
                    alert(erro);
                    console.log(erro);
                }
            });
        }
    </script>
</head>
<body>
<form name="frmPage" id="queryFrom"  action="qrCodeList.jhtml" method="post">
    <div>
        <div align="left">
            您现在的位置：业务管理 &gt;&gt;二维码管理
        </div>
        <table class="table_a marginb10" width="90%">
            <th align="left" colspan="12">
                <strong>查询配置</strong>
            </th>
            <tr height="10">
                <td align="left">
                    <strong>渠道名称:</strong>
                    <input type="text" id="channel" name="channel" value="${param.channel}" />
                </td>
                <td align="left">
                    <strong>查询时间:</strong>
                    <input  onclick="showFmtControls('yyyy-MM-dd')" id="beginTime" value="${param.beginTime }" name="beginTime" type="text" class="input_line" style="width:100px;" />
                    &nbsp;到&nbsp;
                    <input id="endTime" value="${param.endTime }" name="endTime" type="text" class="input_line" style="width:100px;" onclick="showFmtControls('yyyy-MM-dd')" />
                    <input type="button" onclick="checkForm()" value="查 询" />
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" onclick="sendmsg()" value="新 建" />
                </td>
            </tr>
        </table>
        <table class="table_a marginb10" width="90%">
            <th align="left" colspan="7">
                <strong>查询结果列表</strong>
            </th>
            <tr class="align_Center">
                <td width="10%">
                    <strong>序号</strong>
                </td>
                <td width="10%">
                    <strong>渠道名称</strong>
                </td>
                <td width="10%">
                    <strong>创建日期</strong>
                </td>
                <td width="30%">
                    <strong>二维码</strong>
                </td>
                 <td width="20%">
                    <strong>二维码内容</strong>
                </td>
                <td width="20%">
                    <strong>跳转地址</strong>
                </td>
                <td width="20%">
                    <strong>备注</strong>
                </td>
                <td width="10%">
                    <strong>操作</strong>
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
	                        ${item.qrChannelName}
	                </td>
	                <td align="center">
	                    <fmt:formatDate value="${item.qrCreateDate}" pattern="yyyy-MM-dd HH:mm:ss" />
	                </td>
	                <td align="left">
                        <textarea style="width: 100%" readonly="readonly" rows="4">${item.qrPicUrl}</textarea>
	                </td>
	                 <td align="center">
	                    ${item.qrContent}
	                </td>
	                 <td align="center">
	                    ${item.qrHref}
	                </td>
	                <td align="center">
	                    ${item.qrRemark}
	                </td>
                    <td align="center">
                        <button onclick="delQrCode(${item.id})">删除</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div align="right"><%@ include file="../../../common/page.jsp"%></div>
    </div>
</form>
</body>
</html>