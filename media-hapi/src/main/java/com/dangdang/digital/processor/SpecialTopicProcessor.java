package com.dangdang.digital.processor;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICacheApi;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.model.Column;
import com.dangdang.digital.model.SpecialTopic;
import com.dangdang.digital.service.IBlockService;
import com.dangdang.digital.service.IColumnService;
import com.dangdang.digital.service.ISpecialTopicService;
import com.dangdang.digital.utils.LogUtil;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.ReturnBeanUtils;
import com.dangdang.digital.utils.TupleUtils.ResultTwoTuple;
import com.dangdang.digital.vo.BlockVo;
import com.dangdang.digital.vo.MediaSaleCacheVo;
import com.dangdang.digital.vo.ReturnSaleVo;
import com.dangdang.digital.vo.ReturnSpecialTopicVo;

/**
 * Description: 专题处理器
 * All Rights Reserved.
 * @version 1.0  2015年1月7日 下午10:05:58  by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
@Component("hapispecialtopicprocessor")
public class SpecialTopicProcessor extends BaseApiProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpecialTopicProcessor.class);
	@Resource
	ISpecialTopicService specialTopicService;
	@Resource
	IBlockService blockService;
	@Resource
	ICacheApi cacheApi;
	@Resource IColumnService columnService;
	
	
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception{
		//入参 获取块code
		String deviceType = request.getParameter("deviceType");
		String channelType = request.getParameter("channelType");
		String stId = request.getParameter("stId");
		String needcolumn = request.getParameter("needcolumn");
		String columnNuber = request.getParameter("columnNuber");
		
		//验证是否登陆
		try {
			if(!checkParams(deviceType) && !checkParams(channelType)){
				sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
						ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
				return;
			}
			channelType  = channelType.toLowerCase();
			List<SpecialTopic> listSpecialTopic = new LinkedList<SpecialTopic>();
			listSpecialTopic = cacheApi.getSpecialTopicFromCache(stId, deviceType, channelType);
			if(listSpecialTopic.size()<=0 ){
				LogUtil.error(LOGGER, "获取专题内容失败,没有查询到专题信息：",deviceType, channelType, stId, needcolumn, columnNuber);
				sender.fail(ErrorCodeEnum.ERROR_CODE_11102.getErrorCode(), "没有数据", response);
				return ;
			}
			ReturnSpecialTopicVo specialTopicVo = new ReturnSpecialTopicVo();
			SpecialTopic firstSpecialTopic = listSpecialTopic.get(0);//第一个专题
			specialTopicVo.setName(firstSpecialTopic.getName());
			specialTopicVo.setPicPath(firstSpecialTopic.getPicPath());
			specialTopicVo.setCreationDate(firstSpecialTopic.getCreationDate());
			specialTopicVo.setLastModifiedDate(firstSpecialTopic.getLastModifiedDate());
			
			List<BlockVo> blockVoList = new LinkedList<BlockVo>();
			for(SpecialTopic st:listSpecialTopic){
				String columnCode = st.getColumnCode();
				if(null == columnCode||columnCode.indexOf("_")<0){
					sender.fail(ErrorCodeEnum.ERROR_CODE_11100.getErrorCode(), st.getStId()+ErrorCodeEnum.ERROR_CODE_11100.getErrorMessage(), response);
					continue ;
				}
				Column column = this.cacheApi.getColumnFromCache(columnCode);
				BlockVo blockVo = new BlockVo();
				//不再关联Banner(Block)
				blockVo.setContent(st.getDescription());
				blockVo.setCreationDate(st.getCreationDate());
				blockVo.setBlockName(st.getName());
				blockVo.setPicPath(st.getPicPath());
				//不带频道的栏目信息
				String suffixColumn = column.getColumnCode().substring(column.getColumnCode().indexOf("_")+1);
				blockVo.setRelationColumnCode(suffixColumn);
				blockVo.setRelationColumnName(column.getName());
				if(null != columnNuber ){
					int iColumnNumber = Integer.valueOf(columnNuber);
					if(iColumnNumber > 0 ){
						ResultTwoTuple<Long,List<MediaSaleCacheVo>> resultTuple = cacheApi.getMediaSaleByColumnCode(0,Integer.parseInt(columnNuber) - 1,column.getColumnCode());
						List<MediaSaleCacheVo> listMediaSingelSales = resultTuple.listId;
						if(listMediaSingelSales != null && listMediaSingelSales.size() > 0){
							List<ReturnSaleVo> returnSalelist = ReturnBeanUtils.batchgetReturnSaleListVo(listMediaSingelSales);
							blockVo.setSaleList(returnSalelist);
						}
					}
				}
				blockVoList.add(blockVo);
			}
			sender.put("specialTopic", specialTopicVo);
			sender.put("blockVoList", blockVoList);
			sender.success(response);
		} catch (Exception e) {
			LogUtil.error(LOGGER, e, "获取专题内容失败：",deviceType, channelType, stId, needcolumn, columnNuber);
			sender.fail(ErrorCodeEnum.ERROR_CODE_11102.getErrorCode(), ErrorCodeEnum.ERROR_CODE_11102.getErrorMessage(), response);
		}
	}

}
