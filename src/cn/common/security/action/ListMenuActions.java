package cn.common.security.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import cn.bc.common.support.page.Page;
import cn.bc.common.webapp.base.BaseAction;
import cn.common.security.service.MenuService;
import cn.common.security.vo.Menu;
import cn.common.security.vo.MenuFilter;
import cn.common.security.vo.ModuGroup;

import com.opensymphony.xwork2.ActionContext;

public class ListMenuActions extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3450117684353540963L;
	private MenuService menuService;
	protected void onList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String parentIdStr = null;
		String name = null;
		parentIdStr = request.getParameter("parentId");
		name = request.getParameter("name");
		
		String pageNoStr = request.getParameter("pageNo");
		String pageSizeStr = request.getParameter("pageSize");
		
		int pageNo=1;
		int pageSize = 30;
		
		if(StringUtils.isNotBlank(pageNoStr))
			pageNo = new Integer(pageNoStr);
		if(StringUtils.isNotBlank(pageSizeStr))
			pageSize = new Integer(pageSizeStr);
		
		MenuFilter filter = new MenuFilter();
		if(StringUtils.isNotBlank(parentIdStr)){
			filter.setParentId(new Long(parentIdStr));
		}
		if(StringUtils.isNotBlank(name)){
			filter.setName(name);
		}
		
		filter.setPageNo(pageNo);
		filter.setPageSize(pageSize);
		
		Page page = menuService.search(filter);
		
		request.setAttribute("page", page);
	}
    public String preSSelectList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
    	return null;
		
    }
    
    public String queryMenu()throws Exception{
    	HttpServletRequest request = (HttpServletRequest) super
		.getContextParam(ServletActionContext.HTTP_REQUEST);
    	ActionContext actionContext = ActionContext.getContext();    
		Map session = actionContext.getSession();
		 
		String menuId = null;
		menuId = request.getParameter("menuId");
		
		
		List<ModuGroup> moduGroupList = (List<ModuGroup>)session.get("moduGroupList");
		List<Menu> menuList = new ArrayList<Menu>();
		if(moduGroupList!=null&&moduGroupList.size()>0){
			if(StringUtils.isNotBlank(menuId)){
				for(int i=0;i<moduGroupList.size();i++){
					ModuGroup moduGroup = moduGroupList.get(i);
					List<Menu> tempMenuList = moduGroup.getMenuList();
					for(int j=0;j<tempMenuList.size();j++){
						if(tempMenuList.get(j).getMenuId().toString().trim().equals(menuId.trim())){
							menuList.add(tempMenuList.get(j));
							break;
						}
					}
				}
			}
		}
		request.setAttribute("flag", "no");
		request.setAttribute("menuList", menuList);
		return SUCCESS;
    }
    
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
}
