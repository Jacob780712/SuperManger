package cn.common.security.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.bc.common.exception.BusinessException;
import cn.common.security.service.UserAuthService;
import cn.common.security.vo.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * @ClassName: PrivilegesIterceptor
 * @Description: 权限拦截器
 * @author zhouyanan
 * @date Jul 25, 2011
 * @version 1.0
 * @since JDK1.5
 */
public class PrivilegesIterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = 1L;
	private List<String> whitePrivileges;
	private UserAuthService userAuthService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		// 获取URL
		String path = request.getServletPath();
		// 权限白名单
		if (isItEquals(whitePrivileges, path)) {
			return invocation.invoke();
		}
		// 验证User信息
		Map session = ctx.getSession();
		User user = (User) session.get("loginUser"); 
		if(user == null) {
			Exception exception = new Exception("User信息为null，请重新登录！");
			request.setAttribute("exception", exception);
			return "error";
			//throw new BusinessException("User信息为null，请重新登录！");
		}
		//验证权限是否有效
		boolean validateInfo = userAuthService.validateUserAuth(user, path);
		if (validateInfo) {
			return invocation.invoke();
		} else{
			Exception exception = new Exception("访问权限无效，请联系管理员！");
			request.setAttribute("exception", exception);
			return "error";
//			throw new BusinessException("访问权限无效，请联系管理员！");
		}
	}
	/**
	 * 
	 * @Title: isItEquals
	 * @Description: 验证List中是否包含特定字符串
	 * @param list
	 * @param str
	 * @throws Exception
	 * @return boolean
	 */
	public boolean isItEquals(List list, String str) throws Exception {
		if (list == null || list.size() == 0 ) {
			return false;
		}
		return list.contains(str);
	}
	/**
	 * 
	 * @Title: setWhitePrivileges
	 * @param whitePrivileges
	 * @return void
	 */
	public void setWhitePrivileges(List<String> whitePrivileges) {
		this.whitePrivileges = whitePrivileges;
	}
	/**
	 * 
	 * @Title: setUserAuthService
	 * @param userAuthService
	 * @return void
	 */
	public void setUserAuthService(UserAuthService userAuthService) {
		this.userAuthService = userAuthService;
	}
	
}
