package com.dangdang.digital.processor.bookbar;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.base.bookbar.client.commons.enums.BarMemberStatusEnum;
import com.dangdang.base.bookbar.client.vo.BarMemberVo;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 吧成员列表接口
 * All Rights Reserved.
 * @version 1.0  2015-5-21 下午4:54:32  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapiqueryBarMemberprocessor")
public class QueryBarMemberProcessor extends BaseApiProcessor{

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		
		List<BarMemberVo> list = new ArrayList<BarMemberVo>();
		BarMemberVo vo = new BarMemberVo(); 
		vo.setCustId(111111L);
		vo.setNickName("强哥");
		vo.setMemberStatus(BarMemberStatusEnum.HOST.getMemberStatus());
		vo.setHeadPhoto("http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		list.add( vo);
		BarMemberVo vo1 = new BarMemberVo(); 
		vo1.setCustId(222222L);
		vo1.setNickName("楠哥");
		vo1.setMemberStatus(BarMemberStatusEnum.MEMBER.getMemberStatus());
		vo1.setHeadPhoto("http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		list.add( vo1);
		BarMemberVo vo2 = new BarMemberVo(); 
		vo2.setCustId(333333L);
		vo2.setNickName("汇哥");
		vo2.setMemberStatus(BarMemberStatusEnum.MEMBER.getMemberStatus());
		vo2.setHeadPhoto("http://img39.ddimg.cn/21/34/1900364619-1_e_1.jpg");
		list.add( vo2);
		sender.put( "barMembers", list);
		sender.success(response);
	}

}
