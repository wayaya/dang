package com.dangdang.digital.facade;

import java.util.List;

import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
import com.dangdang.digital.vo.MediaCacheWholeVo;

public interface IRankListApiFacade {

	public ResultTwoTuple<Long, List<Long>> getAllSaleIdListByType(String type);

	public List<MediaCacheWholeVo> getAllMediaListByType(String type) throws Exception;

	public List<MediaCacheWholeVo> getRandomMediaListByTypeAndNumber(String type, int number) throws Exception;

}
