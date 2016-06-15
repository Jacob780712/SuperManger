package cn.bc.mchant.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import cn.bc.business.action.CashOutImportAction;
import cn.bc.common.support.page.Page;
import cn.bc.common.webapp.base.BaseAction;
import cn.bc.mchant.po.Goods;
import cn.bc.mchant.po.MchMerchant;
import cn.bc.mchant.po.MchMerchantBranch;
import cn.bc.mchant.services.MchMemberServices;
import cn.bc.mchant.services.MchantServices;
import cn.bc.mchant.vo.GoodsFilter;
import cn.bc.mchant.vo.MchCourtesyRefFilter;
import cn.bc.mchant.vo.MchantFilter;
import cn.bc.util.UpLoadPic;
import cn.common.security.vo.User;

public class MchMemberAction extends BaseAction{
	MchMemberServices mchMemberService;
	MchantServices mchService;
	private String goods_name;//商品名称
	private String goods_introd;//商品介绍
	private File goods_pic;//商品图片
	private String sale_price;//销售价格
	private String orig_price;//原价
	
	public void setMchService(MchantServices mchService) {
		this.mchService = mchService;
	}

	public void setMchMemberService(MchMemberServices mchMemberService) {
		this.mchMemberService = mchMemberService;
	}

	public String membershipList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		GoodsFilter filter = new GoodsFilter();
		String goods_number = request.getParameter("goods_number");
		String goods_name = request.getParameter("goods_name");
		String status = request.getParameter("status");
		filter.setGoods_name(goods_name);
		filter.setGoods_number(goods_number);
		if(status!=null&&!"".equals(status)&&!"8".equals(status)){
			filter.setStatus(status);
		}
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);

		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = mchMemberService.goodsList(filter);
		List<Goods> list = mchMemberService.goodsList();
		int openCount = 0;
		for(int i=0;i<list.size();i++){
			if("0".equals(list.get(i).getStatus())){
				openCount = openCount + 1;
			}
		}
		request.setAttribute("openCount", openCount);
		request.setAttribute("count", list.size());
		request.setAttribute("page", page);
		return SUCCESS;
	}
	
	public String addGoods(){
		return SUCCESS;
	}
	
	public String addGoodsSubmit() throws Exception{
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Goods goods = new Goods();
		Date date = new Date();
		String goodNumber = mchMemberService.goodsNumber();
		String goods_pic_name = "";
        if(goods_pic!=null){
        	goods_pic_name = goodNumber+"goods.png";//商户图片名字
	        UpLoadPic.putObject(goods_pic_name, goods_pic);
        }
		
		goods.setGoods_number(goodNumber);
		if(goods_pic!=null){
			InputStream inStream = CashOutImportAction.class.getResourceAsStream("/mch_pic_url.properties");
			Properties prop = new Properties();  
			try {
				prop.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			}  
			String url = prop.getProperty("url");
			String dir=prop.getProperty("dir").trim();//文件夹名字
//			mchant.setPic_url_logo(url+"/"+dir+"/"+pic_url_logo_name);
			goods.setGoods_pic(url+"/"+dir+"/"+goods_pic_name);
	    }
		goods.setGoods_name(goods_name);
		goods.setGoods_introd(goods_introd);
		goods.setSale_price((int)(Double.valueOf(sale_price)*100.00));
		goods.setOrig_price((int)(Double.valueOf(orig_price)*100.00));
		goods.setStock("1");
		goods.setStatus("0");
		goods.setCreate_date(date);
		goods.setCreate_person(user.getName());
		mchMemberService.addGoods(goods);
		response.sendRedirect("membershipList.jhtml");
		return null;
	}
	
	public String delMembership() throws IOException{
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String goodsId = request.getParameter("goodsId");
		int id = Integer.valueOf(goodsId);
		mchMemberService.delGoods(id);
		response.sendRedirect("membershipList.jhtml");
		return null;
	}

	public String detailMembership(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String goodsId = request.getParameter("goodsId");
		int id = Integer.valueOf(goodsId);
		Goods goods = mchMemberService.detail(id);
		request.setAttribute("goods", goods);
		return SUCCESS;
	}
	
	public String mchantShipList(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		
		String mchNumber = request.getParameter("mchNumber");
		String branchId = request.getParameter("branchId");
		
		//public MchMerchantBranch MchMerchantBranchDetail(String mchantNo,String branchId);
		MchMerchantBranch branch = mchService.MchMerchantBranchDetail(mchNumber, branchId);
		MchMerchant mchant = mchService.isMchantExist(mchNumber);
		
		MchCourtesyRefFilter filter = new MchCourtesyRefFilter();
		filter.setBranchId(branchId);
		filter.setMch_number(mchNumber.trim());
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
		if (pageNo == null && pageSize == null) {
			filter.setPageNo(1);
			filter.setPageSize(30);

		} else {
			filter.setPageNo(Integer.valueOf(pageNo));
			filter.setPageSize(Integer.valueOf(pageSize));
		}
		Page page = mchMemberService.mchantShipList(filter);
		request.setAttribute("page", page);
		request.setAttribute("mchName", mchant.getMch_name());
		request.setAttribute("mchNumber", mchNumber);
		request.setAttribute("branch", branch);
		
		return SUCCESS;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public void setGoods_introd(String goods_introd) {
		this.goods_introd = goods_introd;
	}

	public void setGoods_pic(File goods_pic) {
		this.goods_pic = goods_pic;
	}

	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}

	public void setOrig_price(String orig_price) {
		this.orig_price = orig_price;
	}

	
}
