package com.dangdang.digital.processor.ddreader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.mock.model.Channel;
import com.dangdang.digital.mock.model.ChannelArticle;
import com.dangdang.digital.mock.model.ChannelArticlePackage;
import com.dangdang.digital.mock.model.ChannelDetail;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.DateUtil;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;

/**
 * 频道文章接口
 * @author lvxiang
 * @date   2015年5月15日 上午11:07:14
 */
@Component("hapichannelArticleprocessor")
public class ChannelArticleProcessor extends BaseApiProcessor {
	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//start,end请求参数判断
		ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
		if(!checkResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
			return;
		}
		DateUtil dateutil=new DateUtil();
		Date date=new Date();
		
		List<ChannelArticlePackage> articlePackageList = new ArrayList<ChannelArticlePackage>();
		
		//一次文章更新包,其实就是多个文章
		ChannelArticlePackage package1 = new ChannelArticlePackage();
		List<ChannelArticle> articleContentList = new ArrayList<ChannelArticle>();
		ChannelArticle cac1 = new ChannelArticle();
		cac1.setArticleId(1L);
		cac1.setTitle("移轴摄影的世界很精彩!");
		cac1.setDescription("移轴摄影的世界很精彩!");
		cac1.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList.add(cac1);
		
		
		ChannelArticle cac2 = new ChannelArticle();
		cac2.setArticleId(1L);
		cac2.setTitle("小米智能自行车");
		cac2.setDescription("小米智能自行车,只买999元");
		cac2.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList.add(cac2);
		
		ChannelArticle cac3 = new ChannelArticle();
		cac3.setArticleId(1L);
		cac3.setTitle("小米智能自行车");
		cac3.setDescription("小米智能自行车,只买999元");
		cac3.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList.add(cac3);
		
		ChannelArticle cac4 = new ChannelArticle();
		cac4.setArticleId(1L);
		cac4.setTitle("小米智能自行车");
		cac4.setDescription("小米智能自行车,只买999元");
		cac4.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList.add(cac4);
		
		ChannelArticle cac5 = new ChannelArticle();
		cac5.setArticleId(1L);
		cac5.setTitle("小米智能自行车");
		cac5.setDescription("小米智能自行车,只买999元");
		cac5.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList.add(cac5);
		
		ChannelArticle cac6 = new ChannelArticle();
		cac6.setArticleId(1L);
		cac6.setTitle("小米智能自行车");
		cac6.setDescription("小米智能自行车,只买999元");
		cac6.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList.add(cac6);
		
		
		package1.setArticleContentPackage(articleContentList);
		
		package1.setDate(dateutil.formatDate(date));
		package1.setTimeMills(System.currentTimeMillis());
		
		
		
		ChannelArticlePackage package2 = new ChannelArticlePackage();
		//一次文章更新包,其实就是多个文章
		List<ChannelArticle> articleContentList2 = new ArrayList<ChannelArticle>();
		ChannelArticle cac21 = new ChannelArticle();
		cac21.setArticleId(1L);
		cac21.setTitle("斯洛文尼亚的梦幻之岛");
		cac21.setDescription("好吊的地方啊");
		cac21.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList2.add(cac21);
		
		
		ChannelArticle cac22 = new ChannelArticle();
		cac22.setArticleId(1L);
		cac22.setTitle("小屁孩画展");
		cac22.setDescription("一个夏天,小屁孩......");
		cac22.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList2.add(cac22);
		package2.setArticleContentPackage(articleContentList2);
		package2.setDate(dateutil.formatDate(date));
		package2.setTimeMills(System.currentTimeMillis());
		
		
		ChannelArticlePackage package3 = new ChannelArticlePackage();
		//一次文章更新包,其实就是多个文章
		List<ChannelArticle> articleContentList3 = new ArrayList<ChannelArticle>();
		ChannelArticle cac31 = new ChannelArticle();
		cac31.setArticleId(1L);
		cac31.setTitle("斯洛文尼亚的梦幻之岛");
		cac31.setDescription("好吊的地方啊");
		cac31.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList3.add(cac31);
		
		
		ChannelArticle cac32 = new ChannelArticle();
		cac32.setArticleId(1L);
		cac32.setTitle("小屁孩画展");
		cac32.setDescription("一个夏天,小屁孩......");
		cac32.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList3.add(cac32);
		package3.setArticleContentPackage(articleContentList3);
		package3.setDate(dateutil.formatDate(date));
		package3.setTimeMills(System.currentTimeMillis());
		
		ChannelArticlePackage package4 = new ChannelArticlePackage();
		//一次文章更新包,其实就是多个文章
		List<ChannelArticle> articleContentList4 = new ArrayList<ChannelArticle>();
		ChannelArticle cac41 = new ChannelArticle();
		cac41.setArticleId(1L);
		cac41.setTitle("斯洛文尼亚的梦幻之岛");
		cac41.setDescription("好吊的地方啊");
		cac41.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList4.add(cac41);
		
		
		ChannelArticle cac42 = new ChannelArticle();
		cac42.setArticleId(1L);
		cac42.setTitle("小屁孩画展");
		cac42.setDescription("一个夏天,小屁孩......");
		cac42.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList4.add(cac42);
		package4.setArticleContentPackage(articleContentList4);
		package4.setDate(dateutil.formatDate(date));
		package4.setTimeMills(System.currentTimeMillis());
		
		ChannelArticlePackage package5 = new ChannelArticlePackage();
		//一次文章更新包,其实就是多个文章
		List<ChannelArticle> articleContentList5 = new ArrayList<ChannelArticle>();
		ChannelArticle cac51 = new ChannelArticle();
		cac51.setArticleId(1L);
		cac51.setTitle("斯洛文尼亚的梦幻之岛");
		cac51.setDescription("好吊的地方啊");
		cac51.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList5.add(cac51);
		
		
		ChannelArticle cac52 = new ChannelArticle();
		cac52.setArticleId(1L);
		cac52.setTitle("小屁孩画展");
		cac52.setDescription("一个夏天,小屁孩......");
		cac52.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList5.add(cac52);
		
		package5.setArticleContentPackage(articleContentList5);
		package5.setDate(dateutil.formatDate(date));
		package5.setTimeMills(System.currentTimeMillis());
		
		
		ChannelArticlePackage package6 = new ChannelArticlePackage();
		//一次文章更新包,其实就是多个文章
		List<ChannelArticle> articleContentList6 = new ArrayList<ChannelArticle>();
		ChannelArticle cac61 = new ChannelArticle();
		cac61.setArticleId(1L);
		cac61.setTitle("斯洛文尼亚的梦幻之岛");
		cac61.setDescription("好吊的地方啊");
		cac61.setIcon("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		articleContentList6.add(cac61);
		
		package6.setArticleContentPackage(articleContentList6);
		package6.setDate(dateutil.formatDate(date));
		package6.setTimeMills(System.currentTimeMillis());
		
		
		articlePackageList.add(package1);
		articlePackageList.add(package2);
		articlePackageList.add(package3);
		articlePackageList.add(package4);
		articlePackageList.add(package5);
		articlePackageList.add(package6);
		
		
		
		if(checkResult.end < articlePackageList.size()){
			sender.put("articlePackageList", articlePackageList.subList(checkResult.start, checkResult.end+1));
		}else{
			sender.put("articlePackageList", articlePackageList.subList(checkResult.start,articlePackageList.size()));
		}
		
		sender.put("total", articlePackageList.size());
		sender.success(response);
	}

}
