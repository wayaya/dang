package com.dangdang.digital.processor.bookbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.base.bookbar.client.commons.enums.BarModuleTagTypeEnum;
import com.dangdang.base.bookbar.client.commons.enums.BarModuleTypeEnum;
import com.dangdang.base.bookbar.client.commons.enums.ModuleLocationEnum;
import com.dangdang.base.bookbar.client.vo.BarModuleTagVo;
import com.dangdang.base.bookbar.client.vo.BarModuleVo;
import com.dangdang.base.bookbar.client.vo.BarVo;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

@Component("hapisquareprocessor")
public class SquareProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		// 	moduleLocation  模块位置（1.吧广场 2.热帖）（必填）
		String moduleLocation = request.getParameter("moduleLocation");
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if ( ModuleLocationEnum.SQUARE.getModuleLocation().equals( moduleLocation)){
			Map<String, Object> tagMap = new HashMap<String, Object>();
			BarModuleVo barModuleVo = new BarModuleVo();
			barModuleVo.setBarModuleId( 1L);
			barModuleVo.setModuleName("热门标签");
			barModuleVo.setType(BarModuleTypeEnum.TAG.getType());
			tagMap.put("module", barModuleVo);
			
			List<BarModuleTagVo> tagList = new ArrayList<BarModuleTagVo>();
			BarModuleTagVo vo1 = new BarModuleTagVo();
			vo1.setBarModuleTagId( 1L);
			vo1.setTagName( "五十度灰");
			vo1.setTagType( BarModuleTagTypeEnum.BAR_TAG.getType());
			tagList.add( vo1);
			BarModuleTagVo vo2 = new BarModuleTagVo();
			vo2.setBarModuleTagId( 2L);
			vo2.setTagName( "雾霾");
			vo2.setTagType( BarModuleTagTypeEnum.BAR_TAG.getType());
			tagList.add( vo2);
			tagMap.put("moduleContent", tagList);
			result.add( tagMap);
			
			Map<String, Object> barMap1 = new HashMap<String, Object>();
			BarModuleVo barModuleVo1 = new BarModuleVo();
			barModuleVo1.setBarModuleId( 2L);
			barModuleVo1.setModuleName( "值得加入的书吧");
			barModuleVo1.setType( BarModuleTypeEnum.BAR.getType());
			barModuleVo1.setTempletNo( 1);
			barMap1.put("module", barModuleVo1);
			
			List<BarVo> barList = new ArrayList<BarVo>();
			BarVo barVo = new BarVo();
			barVo.setBarId( 90L);
			barVo.setBarName( "星际穿越");
			barVo.setRecommentReason( "【这里是推荐语】强力推荐这个书吧，里面都是你喜欢的好书，是科幻迷的天堂！");
			barVo.setBarImgUrl( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
			barVo.setMemberNum( "9999");
			barList.add( barVo);
			BarVo barVo1 = new BarVo();
			barVo1.setBarId( 91L);
			barVo1.setBarName( "狼图腾");
			barVo1.setRecommentReason( "【这里是推荐语】不看狼图腾，就不可以，只要是男人，就一定要看这部小说，请大家加入吧！");
			barVo1.setBarImgUrl( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
			barVo1.setMemberNum( "8888");
			barList.add( barVo1);
			barMap1.put( "moduleContent", barList);
			result.add( barMap1);
			
			Map<String, Object> barMap2 = new HashMap<String, Object>();
			BarModuleVo barModuleVo2 = new BarModuleVo();
			barModuleVo2.setBarModuleId( 3L);
			barModuleVo2.setModuleName( "人气书吧");
			barModuleVo2.setType( BarModuleTypeEnum.BAR.getType());
			barModuleVo2.setTempletNo( 2);
			barMap2.put("module", barModuleVo2);
			
			List<BarVo> barList1 = new ArrayList<BarVo>();
			BarVo barVo2 = new BarVo();
			barVo2.setBarId( 101L);
			barVo2.setBarName( "王小波问下走狗");
			barVo2.setBarDesc( "【这里是吧简介】强力推荐这个书吧，里面都是你喜欢的好书，是科幻迷的天堂！");
			barVo2.setBarImgUrl( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
			barVo2.setMemberNum( "7777");
			barList1.add( barVo2);
			BarVo barVo3 = new BarVo();
			barVo3.setBarId( 102L);
			barVo3.setBarName( "大龄文艺女青年");
			barVo3.setBarDesc( "【这里是吧简介】不看狼图腾，就不可以，只要是男人，就一定要看这部小说，请大家加入吧！");
			barVo3.setBarImgUrl( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
			barVo3.setMemberNum( "10000");
			barList1.add( barVo3);
			barMap2.put( "moduleContent", barList1);
			result.add( barMap2);
		} else if ( ModuleLocationEnum.HOT_ARTICLE.getModuleLocation().equals( moduleLocation)){
//			Map<String, Object> articleMap = new HashMap<String, Object>();
//			BarModuleVo barModuleVo = new BarModuleVo();
//			barModuleVo.setBarModuleId( 8L);
//			barModuleVo.setModuleName("默认帖子模块");
//			barModuleVo.setType(BarModuleTypeEnum.ARTICLE.getType());
//			articleMap.put("module", barModuleVo);
//			
//			List<ArticleVo> articleList = new ArrayList<ArticleVo>();
//			ArticleVo vo1 = new ArticleVo();
//			vo1.setArticleId( 1L);
//			vo1.setNickName( "我不是刘超");
//			
//			List<BarModuleTagVo> tagList = new ArrayList<BarModuleTagVo>();
		}
		sender.put("data", result);
		sender.success(response);
	}

}
