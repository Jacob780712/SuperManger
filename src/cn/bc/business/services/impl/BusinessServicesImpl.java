package cn.bc.business.services.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bc.business.dao.BusinessDao;
import cn.bc.business.po.IncomeAnalysisConfig;
import cn.bc.business.po.IncomeAnalysisList;
import cn.bc.business.po.MchValueList;
import cn.bc.business.po.MchValueSummary;
import cn.bc.business.po.OnReChange;
import cn.bc.business.services.BusinessServices;
import cn.bc.business.vo.AnalysBo;
import cn.bc.business.vo.AnalysDetailListBo;
import cn.bc.business.vo.AnalysFilter;
import cn.bc.business.vo.OnReChangeFilter;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.vo.Filter;

public class BusinessServicesImpl implements BusinessServices{
	private BusinessDao bdao;
	
	public void setBdao(BusinessDao bdao) {
		this.bdao = bdao;
	}

	@Override
	public Page list() {
		return null;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void save() {
		
	}

	@Override
	public Page getAnalyis(AnalysFilter filter,int range) {
		// TODO Auto-generated method stub
		return bdao.getAnalyis(filter,range);
	}

	@Override
	public Map<String, Object> getAnalyisDetail(AnalysFilter filter) {
		// TODO Auto-generated method stub
		return bdao.getAnalyisDetail(filter);
	}

	@Override
	public List<IncomeAnalysisList> taskGetAnalyis(Date date) {
		// TODO Auto-generated method stub
		List<IncomeAnalysisList> list = bdao.taskGetAnalyis(date); 
		HashSet set = new HashSet();
		for(int i=0;i<list.size();i++){
			bdao.setData(list.get(i));
			set.add(list.get(i).getMch_number());
		}
		Iterator it=set.iterator();
        while(it.hasNext())
        {
            String mchNumber=it.next().toString();
            MchValueSummary mchSummary = bdao.getMchValueSummary(mchNumber, date);
            mchSummary.setMch_number(mchNumber);
            List<MchValueList> mchValueList = bdao.getMchValueList(mchNumber, date);
    		
    		int card_number = 0;//储值卡总张数
    		int ck_quota = 0;//储值总面额
    		int sales_amount = 0;//购买储值的总金额
    		int card_balance = 0;//储值余额
    		int back_amount = 0;//返现总金额
            for(int i=0;i<mchValueList.size();i++){
            	MchValueList mchValue = mchValueList.get(i);
            	bdao.setMchValueList(mchValue);
            	card_number = card_number + Integer.valueOf(mchValue.getCard_number());
            	ck_quota = ck_quota + + Integer.valueOf(mchValue.getAll_ck_quate());
            	sales_amount = sales_amount + Integer.valueOf(mchValue.getAll_sale_amont());
            	card_balance = card_balance + Integer.valueOf(mchValue.getCard_balance());
            	back_amount = back_amount + Integer.valueOf(mchValue.getTotal_actrul_amount());
            	
            }
            if(mchSummary!=null){
	            mchSummary.setCard_number(card_number+"");
	            mchSummary.setCk_quota(ck_quota+"");
	            mchSummary.setSales_amount(sales_amount+"");
	            mchSummary.setCard_balance(card_balance+"");
	            mchSummary.setBack_amount(back_amount+"");
	            bdao.setMchValueSummary(mchSummary);
            }
        }
		return null;
	}

	@Override
	public IncomeAnalysisConfig getIncomeAnalysisConfig() {
		// TODO Auto-generated method stub
		return bdao.getIncomeAnalysisConfig();
	}

	@Override
	public void saveIncomeAnalysisConfig(IncomeAnalysisConfig config) {
		// TODO Auto-generated method stub
		bdao.saveIncomeAnalysisConfig(config);
	}

	@Override
	public void reChange(OnReChange onReChange)throws Exception {
		// TODO Auto-generated method stub
		bdao.reChange(onReChange);
	}

	@Override
	public Page reChangeList(OnReChangeFilter filter) {
		// TODO Auto-generated method stub
		return bdao.reChangeList(filter);
	}

}
