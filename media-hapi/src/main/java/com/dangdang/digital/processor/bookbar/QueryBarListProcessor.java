package com.dangdang.digital.processor.bookbar;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.base.bookbar.client.vo.BarVo;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;

/**
 * Description: 根据吧名称模糊查询吧列表 
 * All Rights Reserved.
 * @version 1.0  2015-5-28 下午5:13:42  by 傅作魁（fuzuokui@dangdang.com）创建
 */
@Component("hapiqueryBarListprocessor")
public class QueryBarListProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		List<BarVo> barList = new ArrayList<BarVo>();
		BarVo vo1 = new BarVo();
		vo1.setBarId( 1111L);
		vo1.setBarName( "张三吧");
		vo1.setMemberNum( "23145");
		barList.add( vo1);
		BarVo vo2 = new BarVo();
		vo2.setBarId( 2222L);
		vo2.setBarName( "张三的吧");
		vo2.setMemberNum( "432");
		barList.add( vo2);
		BarVo vo3 = new BarVo();
		vo3.setBarId( 3333L);
		vo3.setBarName( "我是张三吧");
		vo3.setMemberNum( "4022");
		barList.add( vo3);
		
		sender.put("data", barList);
		sender.success(response);
	}

}
