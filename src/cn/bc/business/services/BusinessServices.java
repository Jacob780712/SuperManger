package cn.bc.business.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.bc.business.po.IncomeAnalysisConfig;
import cn.bc.business.po.IncomeAnalysisList;
import cn.bc.business.po.OnReChange;
import cn.bc.business.vo.AnalysFilter;
import cn.bc.business.vo.OnReChangeFilter;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.vo.Filter;

public interface BusinessServices {
	public Page list();
	public void update();
	public void save();
	public Page getAnalyis(AnalysFilter filter, int range);
	public Map<String,Object> getAnalyisDetail(AnalysFilter filter);
	public List<IncomeAnalysisList> taskGetAnalyis(Date date);
	public IncomeAnalysisConfig getIncomeAnalysisConfig();
	public void saveIncomeAnalysisConfig(IncomeAnalysisConfig config);
	public Page reChangeList(OnReChangeFilter filter);
	public void reChange(OnReChange onReChange) throws Exception;
}
