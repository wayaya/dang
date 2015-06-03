package com.dangdang.digital.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.dao.IAnthologyDao;
import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IDigestAnthologyDao;
import com.dangdang.digital.model.Anthology;
import com.dangdang.digital.model.DigestAnthology;
import com.dangdang.digital.service.IAnthologyService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.DateUtil;

@Service("anthologyService")
public class AnthologyServiceImpl extends BaseServiceImpl<Anthology, Long> implements IAnthologyService {
	
	@Resource
	private IAnthologyDao anthologyDao;
	
	@Resource
	private IDigestAnthologyDao digestAnthologyDao;
	
	@Override
	public IBaseDao<Anthology> getBaseDao() {
		return anthologyDao;
	}

	@Override
	public int insert(Anthology record) {
		return anthologyDao.insert(record);
	}
	
	@Override
	public int update(Anthology record){
		return anthologyDao.update(record);
	}

	@Override
	public int delete(Long id) {
		return anthologyDao.delete(id);
	}

	@Override
	public Anthology findAnthologyById(Long id) {
		return anthologyDao.findAnthologyById(id);
	}

	@Override
	public Anthology findAnthologyByCustIdAndAnthologyName(Long custId, String anthologyName) {
		Map<String, Object> paramObj = new HashMap<String, Object>();
		paramObj.put("custId", custId);
		paramObj.put("anthologyName", anthologyName);
		return anthologyDao.findAnthologyByCustIdAndAnthologyName(paramObj);
	}

	@Override
	public List<Anthology> queryAnthologyListByCustId(Long custId, String platform, Date lastDate, Integer pageSize) {
		return anthologyDao.queryAnthologyListByCustId(custId, platform, lastDate, pageSize);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Map<String, Integer> addAnthology(Anthology anthology, List<Long> digestIds, boolean newAnthology) {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		if(newAnthology){
			int insertResult = anthologyDao.insert(anthology);
			if(insertResult <= 0){
				resultMap.put("errorMsg", ErrorCodeEnum.ERROR_CODE_22008.getErrorCode());
				return resultMap;
			}
		}
		if(CollectionUtils.isNotEmpty(digestIds)){
			//绑定文集和精品的对应关系
			Long anthologyId = anthology.getAnthologyId();
			List<Long> filterDigestIds = new ArrayList<Long>();
			//过滤重复添加文集
			Map<Long, DigestAnthology> digestAnthologyMap = digestAnthologyDao.queryDigestAnthologyByAnthologyIdAndDigestIds(anthologyId, digestIds);
			if(MapUtils.isNotEmpty(digestAnthologyMap)){
				for(Long digestId:digestIds){
					if(!digestAnthologyMap.containsKey(digestId)){
						filterDigestIds.add(digestId);
					}
				}
			}else{
				filterDigestIds = digestIds;
			}
			
			//重复添加校验
			if(CollectionUtils.isEmpty(filterDigestIds)){
				resultMap.put("errorMsg", ErrorCodeEnum.ERROR_CODE_22030.getErrorCode());
				return resultMap;
			}else{
				List<DigestAnthology> records = new ArrayList<DigestAnthology>();
				int size = filterDigestIds.size();
				for(int i=0; i<size; i++){
					DigestAnthology record = new DigestAnthology();
					record.setAnthologyId(anthologyId);
					record.setDigestId(filterDigestIds.get(i));
					record.setCreateDate(DateUtil.addMillSecond(new Date(), -i * 1000));
					records.add(record);
				}
				
				if(CollectionUtils.isNotEmpty(records)){
					int batchResult = digestAnthologyDao.batchInsert(records);
					if(batchResult <= 0){
						resultMap.put("errorMsg", ErrorCodeEnum.ERROR_CODE_22029.getErrorCode());
						return resultMap;
					}
				}
			}
		}
		return resultMap;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean deleteAnthology(Long anthologyId) {
		boolean flag = false;
		int effectReferRows = digestAnthologyDao.deleteByAnthologyId(anthologyId);
		if(effectReferRows >= 0){
			int effectRows = anthologyDao.delete(anthologyId);
			if(effectRows > 0){
				return true;
			}
		}
		return flag;
	}
	
	@Override
	public boolean deleteDigestsForAnthology(Long anthologyId, Collection<Long> digestIds) {
		int effectRows = digestAnthologyDao.deleteByDigestIdsAndAnthologyId(anthologyId, digestIds);
		return effectRows > 0?true:false;
	}

	@Override
	public Map<String, Integer> checkAnthologyName(Long custId, String anthologyName) {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		//校验文集名称长度
		if(anthologyName.trim().length() > ConfigPropertieUtils.getInteger("anthology.name.length.limit", 16)){
			resultMap.put("errorMsg", ErrorCodeEnum.ERROR_CODE_22005.getErrorCode());
			return resultMap;
		}
		//中文，字母，数字正则表达式
		String regex = "^[a-zA-Z0-9\u4e00-\u9fa5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(anthologyName.trim());
		//校验文集名称只能输入中文，字母，数字字符
		if(!matcher.matches()){
			resultMap.put("errorMsg", ErrorCodeEnum.ERROR_CODE_22006.getErrorCode());
			return resultMap;
		}
		Anthology anthology = this.findAnthologyByCustIdAndAnthologyName(custId, anthologyName.trim());
		if(anthology != null){
			resultMap.put("errorMsg", ErrorCodeEnum.ERROR_CODE_22007.getErrorCode());
			return resultMap;
		}
		return resultMap;
	}
	
}
