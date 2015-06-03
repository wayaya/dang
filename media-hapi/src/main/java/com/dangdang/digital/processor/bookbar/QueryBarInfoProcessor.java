package com.dangdang.digital.processor.bookbar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.base.bookbar.client.commons.enums.BarMemberStatusEnum;
import com.dangdang.base.bookbar.client.commons.enums.BarTypeEnum;
import com.dangdang.base.bookbar.client.vo.BarVo;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description:  吧详情页接口
 * All Rights Reserved.
 * @version 1.0  2015-5-21 下午4:04:01  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapiqueryBarInfoprocessor")
public class QueryBarInfoProcessor extends BaseApiProcessor{

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		BarVo barVo = new BarVo();
		barVo.setBarName("白鹿原");
		barVo.setBarDesc( "集家庭史民族史于一体，以厚重的历史感和复杂的人物形象而在同类作品中脱颖而出，成为当代文学中不可多得的杰作之一。 ");
		barVo.setBarImgUrl( "http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		barVo.setBarHostCustId( 9999999L);
		barVo.setBarHost( "风来如山倒");
		barVo.setMemberNum( "466");
		barVo.setMemberStatus( BarMemberStatusEnum.NOT_MEMBER.getMemberStatus());
		barVo.setBarType(BarTypeEnum.USER_CREATE.getType());
		
		sender.put("bar", barVo);
		sender.success(response);
	}

}
