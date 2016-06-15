package cn.bc.mchant.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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
import cn.bc.mchant.po.MchCourtesyRef;
import cn.bc.mchant.po.MchMerchant;
import cn.bc.mchant.po.MchMerchantBranch;
import cn.bc.mchant.services.MchMemberServices;
import cn.bc.mchant.services.MchantServices;
import cn.bc.mchant.vo.GoodsFilter;
import cn.bc.mchant.vo.MchantFilter;
import cn.bc.util.UpLoadPic;
import cn.common.security.vo.User;

public class MchMemberUpdateAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MchMemberServices mchMemberService;
	MchantServices mchService;
	private String goods_name;//商品名称
	private String goods_introd;//商品介绍
	private File goods_pic;//商品图片
	private String sale_price;//销售价格
	private String orig_price;//原价
	private String goods_number_upd;//礼遇编号
	private String status;//状态0上架 1下架 9删除
	
	public void setMchMemberService(MchMemberServices mchMemberService) {
		this.mchMemberService = mchMemberService;
	}
	public void setMchService(MchantServices mchService) {
		this.mchService = mchService;
	}

	//修改礼遇信息
	public String updateMembership(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String goodsId = request.getParameter("goodsId");
		int id = Integer.valueOf(goodsId);
		Goods goods = mchMemberService.detail(id);
		request.setAttribute("goods", goods);
		return SUCCESS;
	}
	//礼遇修改
	public String updateMembershipSubmit() throws IOException{
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		Goods goods = new Goods();
		Date date = new Date();
		String goods_pic_name = "";
        if(goods_pic!=null){
        	goods_pic_name = goods_number_upd+"goods.png";//商户图片名字
	        UpLoadPic.putObject(goods_pic_name, goods_pic);
        }
		
        goods.setGoods_number(goods_number_upd);
		goods.setGoods_name(goods_name);
		goods.setGoods_introd(goods_introd);
		goods.setSale_price((int)(Double.valueOf(sale_price)*100.00));
		goods.setOrig_price((int)(Double.valueOf(orig_price)*100.00));
		goods.setStatus(status);
		goods.setUpdate_date(date);
		goods.setUpdate_person(user.getName());
		mchMemberService.updateGoods(goods);
		response.sendRedirect("membershipList.jhtml?goods_number="+goods_number_upd);
		return null;
	}

	//进入商户礼遇修改页
	public String membershipUpdate(){
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		String mchNumber = request.getParameter("mchNumber");
		String branchId = request.getParameter("branchId");
		String goodName = request.getParameter("goodName");
		MchMerchantBranch branch = mchService.MchMerchantBranchDetail(mchNumber, branchId);
//		Page page = mchMemberService.mchantShipList(mchNumber);
		GoodsFilter filter = new GoodsFilter();
		filter.setGoods_name(goodName);
		filter.setStatus("0");
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
		String goodsList = request.getParameter("goodsList");
		List<MchCourtesyRef> listMchCourtesyRef = new ArrayList<MchCourtesyRef>();
		if(goodsList!=null&&!"".equals(goodsList)){
			if(!goodsList.equals("empty")){
				String[] goodAry = goodsList.split("##");
				for(int i=0;i<goodAry.length;i++){
					String[] mchShip = goodAry[i].split("--");
					MchCourtesyRef ref = new MchCourtesyRef();
					ref.setGoods_number(mchShip[0]);
					ref.setGoods_name(mchShip[1]);
					listMchCourtesyRef.add(ref);
				}
			}
		}else{
			listMchCourtesyRef = mchMemberService.mchantShipList(mchNumber,branchId);
		}
		Collections.sort(listMchCourtesyRef);  
		List<Goods> listGoods = (List<Goods>) page.getResult();
		for(int i=0;i<listMchCourtesyRef.size();i++){
			for(int j=0;j<listGoods.size();j++){
				if(listMchCourtesyRef.get(i).getGoods_number().equals(listGoods.get(j).getGoods_number())){
					listGoods.get(j).setStatus("11");
				}
			}
		}
		request.setAttribute("listMchCourtesyRef", listMchCourtesyRef);
		request.setAttribute("page", page);
		MchMerchant mchant = mchService.isMchantExist(mchNumber);
		request.setAttribute("mchName", mchant.getMch_name());
		request.setAttribute("mchant", mchant);
		request.setAttribute("mchNumber", mchNumber);
		request.setAttribute("branch", branch);
		return SUCCESS;
	}
	 
	//商户礼遇修改提交
	public String membershipUpdateCommit() throws IOException{
		ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
		User user = (User) session.get("loginUser");
		HttpServletRequest request = (HttpServletRequest) getContextParam(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) getContextParam(ServletActionContext.HTTP_RESPONSE);
		String vip_brief = request.getParameter("vip_brief");
		String vip_introd = request.getParameter("vip_introd");
		String mchNumber = request.getParameter("mchNumber");
		String branchId = request.getParameter("branchId");
		String goodsList = request.getParameter("goodsList");
		List<MchCourtesyRef> listMchCourtesyRef = new ArrayList<MchCourtesyRef>();
		if(goodsList!=null&&!"".equals(goodsList)){
			if(!goodsList.equals("empty")){
				String[] goodAry = goodsList.split("##");
				for(int i=0;i<goodAry.length;i++){
					String[] mchShip = goodAry[i].split("--");
					MchCourtesyRef ref = new MchCourtesyRef();
					Date date = new Date();
					ref.setMch_number(mchNumber);
					ref.setBranch_id(Integer.valueOf(branchId));
					ref.setGoods_number(mchShip[0]);
					ref.setGoods_name(mchShip[1]);
					ref.setCreate_date(date);
					ref.setCreate_person(user.getName());
					listMchCourtesyRef.add(ref);
				}
			}
			if(goodsList.equals("empty")){
				mchMemberService.delMchShips(mchNumber, branchId);
			}
		}
		MchMerchant mchant = mchService.isMchantExist(mchNumber);
		if(vip_brief!=null&&!"".equals(vip_brief.trim())&&vip_introd!=null&&!"".equals(vip_introd.trim())){
			mchant.setVip_brief(vip_brief);
			mchant.setVip_introd(vip_introd);
			mchMemberService.updateMchVipInfo(mchant);
		}
		
		mchMemberService.updateMchShips(listMchCourtesyRef, mchNumber);
		response.sendRedirect("mchantShipList.jhtml?mchNumber="+mchNumber.trim()+"&branchId="+branchId.trim());
		return null;
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


	public void setGoods_number_upd(String goods_number_upd) {
		this.goods_number_upd = goods_number_upd;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
