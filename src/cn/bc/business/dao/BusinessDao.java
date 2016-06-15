package cn.bc.business.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.bc.business.po.IncomeAnalysisConfig;
import cn.bc.business.po.IncomeAnalysisList;
import cn.bc.business.po.MchValueList;
import cn.bc.business.po.MchValueSummary;
import cn.bc.business.po.OnReChange;
import cn.bc.business.vo.AnalysBo;
import cn.bc.business.vo.AnalysFilter;
import cn.bc.business.vo.OnReChangeFilter;
import cn.bc.common.support.page.Page;
import cn.bc.query.po.CoAccount;
import cn.bc.query.vo.Filter;

public interface BusinessDao {
	public Page list();
	public void update();
	public void save();
	public Page getAnalyis(AnalysFilter filter, int range);
	public Map<String,Object> getAnalyisDetail(AnalysFilter filter);
	public List<IncomeAnalysisList> taskGetAnalyis(Date date);
	public void setData(IncomeAnalysisList incomeAnalysisList);
	public MchValueSummary getMchValueSummary(String mchNumber, Date date);
	public void setMchValueSummary(MchValueSummary mchValueSummary);
	public List<MchValueList> getMchValueList(String mchNumber, Date date);
	public void setMchValueList(MchValueList mchValueList);
	public IncomeAnalysisConfig getIncomeAnalysisConfig();
	public void saveIncomeAnalysisConfig(IncomeAnalysisConfig config);
	public Page reChangeList(OnReChangeFilter filter);
	public void reChange(OnReChange onReChange)throws Exception;
}
