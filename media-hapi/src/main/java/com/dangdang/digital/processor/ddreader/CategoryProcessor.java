package com.dangdang.digital.processor.ddreader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.dangdang.digital.mock.model.Category;
import com.dangdang.digital.mock.model.Channel;
import com.dangdang.digital.mock.model.Column;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.ResultSender;
/**
 * 
 * @author lvxiang
 * @date   2015年5月15日 下午6:54:48
 * 
 */
@Component("hapicategoryprocessor")
public class CategoryProcessor extends BaseApiProcessor {

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		/** 栏目类型 频道栏目还是书栏目  **/
		List<Category> categoryList = new ArrayList<Category>();
		Category  xiaoshuo= new Category();
		xiaoshuo.setOrder(1);
		xiaoshuo.setCategoryName("小说");
		xiaoshuo.setIndexKey("X");
		xiaoshuo.setCagegoryCode("XIAOSHUO");
		List<Category> xiaoshuoCategoryList = new ArrayList<Category>();
		Category  qinggan = new Category();
		qinggan.setCategoryName("情感");
		qinggan.setCagegoryCode("QINGGAN");
		xiaoshuoCategoryList.add(qinggan);
		Category  shehui = new Category();
		shehui.setCategoryName("社会");
		shehui.setCagegoryCode("SHEHUI");
		xiaoshuoCategoryList.add(shehui);
		xiaoshuo.setCategoryList(xiaoshuoCategoryList);
		categoryList.add(xiaoshuo);
		sender.put("categoryList",categoryList);
		sender.success(response);
	}

}
