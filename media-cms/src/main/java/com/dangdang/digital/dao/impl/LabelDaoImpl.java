package com.dangdang.digital.dao.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.dangdang.digital.dao.ILabelDao;
import com.dangdang.digital.model.Label;
import com.dangdang.digital.utils.PageFinder;
import com.dangdang.digital.utils.Query;
/**
 * @Repository 将 DAO 类声明为 Bean 
 * @author Simple
 *
 * @date 2014年11月7日 下午5:18:34
 *
 */
@Repository
public class LabelDaoImpl  extends BaseDaoImpl<Label>  implements ILabelDao {

	public Label getLabelById(Integer id) {
		// TODO Auto-generated method stub
		return  this.selectOne("LabelMapper.selectByPrimaryKey", id);

	}

	public List<Label> getLabelList() {
		return  this.selectList("LabelMapper.selectAll");
	}

	public List<Label> getLabelsByPageRequest(PageRequest pagerReqest) {
		// TODO Auto-generated method stub
		
		return  this.selectList("LabelMapper.selectAll");
	}

	@Override
	public PageFinder<Label> getLabelList(Label label, Query query) {
		return this.getPageFinderObjs(label, query, "LabelMapper.selectCount", "LabelMapper.selectAll");
	}

	


}
