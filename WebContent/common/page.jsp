<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		${page.totalCount }条记录
		<c:if test="${page.totalCount>0}">
		(${page.currentPageNo}/${page.totalPageCount}页)
		<c:if test="${(page.currentPageNo!=1) && (page.totalPageCount>0)}">
	    	<input type="button" name="button0" class="buttonface" value="首页" onclick="gotoPage(1,1)">
    	    <input type="button" name="button1" class="buttonface" value="上一页" onclick="gotoPage(${page.currentPageNo-1 },1)"> 

		</c:if>
		<c:if test="${page.currentPageNo!=page.totalPageCount && page.totalPageCount>0}">
    	    <input type="button" name="button2" class="buttonface" value="下一页" onclick="gotoPage(${page.currentPageNo+1 },1)"> 		  
			<input type="button" name="button3" class="buttonface" value="末页" onclick="gotoPage(${page.totalPageCount},1)"> 		  
       </c:if>
		  
		每页<input type="text" id="pageSize" name="pageSize" value="${page.pageSize }" size="2" maxlength="3">行
		到<input type="text" name="pageNo" id="pageNo" value="${page.currentPageNo}" id="pageNo" size="3"  onkeyup="javascript:onEnterSubmit()">页
		<input type="button" name="Go" class="buttonface"  value="确定" onclick="javascript:goExt()">
		</else>
			<input type="hidden" name="pageNo" id="pageNo" value="1">					
		</c:if>
		&nbsp;&nbsp;		
<script>
function isNum(num)   
{
    var   i,j,strTemp;   
    strTemp="0123456789";   
    if(num.length==0)   
      return 0;
    for(i=0;i<num.length;i++)   
    {   
      j=strTemp.indexOf(num.charAt(i));     
      if(j==-1)   
      {   
        return 0;   
      }   
    }   
    return 1;   
}   

function goExt()
{
		var pageNo = document.getElementById("pageNo").value;	
		gotoPage(pageNo,1);
}

function gotoPageEx(pageNo)
{
	gotoPage(pageNo,0);
}

function onEnterSubmit(){

		var code = event.keyCode;
   		if(code == 13)
   		{
				var pageNo = document.getElementById("pageNo").value;		
				gotoPage(pageNo,1);
		}
}

	
</script>