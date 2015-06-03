package com.dangdang.digital.processor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.utils.RequestParamCheckerUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.utils.RequestParamCheckerUtils.ListResultTuple;
import com.dangdang.digital.vo.ReturnDDHotFreeVo;
import com.dangdang.digital.vo.ReturnHotFreeSaleListVo;
@Component("hapigetDDHotFreeprocessor")
public class GetDDHotFreeListProcessor extends BaseApiProcessor{

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		//start,end请求参数判断
		ListResultTuple<Boolean,Integer,Integer,Integer,String> checkResult = RequestParamCheckerUtils.checkStartEndParam(request);
		if(!checkResult.success){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),checkResult.errorMsg, response);
			return;
		}
				
		List<ReturnDDHotFreeVo> mediaList=new ArrayList<ReturnDDHotFreeVo>();
		
		List<ReturnHotFreeSaleListVo> saleList=new ArrayList<ReturnHotFreeSaleListVo>();
		
		ReturnHotFreeSaleListVo saleListVo=new ReturnHotFreeSaleListVo();
		
		//存假数据
		ReturnDDHotFreeVo rv1=new ReturnDDHotFreeVo();
		rv1.setSaleId((long) 1980123514);
		rv1.setMediaId((long) 1960249496);
		rv1.setAuthorName("六月莫言1");
		rv1.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv1.setMediaName("古董局中局");
		rv1.setDescription("这是一部不错的小说");
		
		
		ReturnDDHotFreeVo rv2=new ReturnDDHotFreeVo();
		rv2.setSaleId((long) 1980123514);
		rv2.setMediaId((long) 1960249496);
		rv2.setAuthorName("六月莫言2");
		rv2.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv2.setMediaName("古董局中局");
		rv2.setDescription("这是一部不错的小说");
		
		
		ReturnDDHotFreeVo rv3=new ReturnDDHotFreeVo();
		rv3.setSaleId((long) 1980123514);
		rv3.setMediaId((long) 1960249496);
		rv3.setAuthorName("六月莫言3");
		rv3.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv3.setMediaName("古董局中局");
		rv3.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv4=new ReturnDDHotFreeVo();
		rv4.setSaleId((long) 1980123514);
		rv4.setMediaId((long) 1960249496);
		rv4.setAuthorName("六月莫言3");
		rv4.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv4.setMediaName("古董局中局");
		rv4.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv5=new ReturnDDHotFreeVo();
		rv5.setSaleId((long) 1980123514);
		rv5.setMediaId((long) 1960249496);
		rv5.setAuthorName("六月莫言3");
		rv5.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv5.setMediaName("古董局中局");
		rv5.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv6=new ReturnDDHotFreeVo();
		rv6.setSaleId((long) 1980123514);
		rv6.setMediaId((long) 1960249496);
		rv6.setAuthorName("六月莫言3");
		rv6.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv6.setMediaName("古董局中局");
		rv6.setDescription("这是一部不错的小说");
		
		
		ReturnDDHotFreeVo rv7=new ReturnDDHotFreeVo();
		rv7.setSaleId((long) 1980123514);
		rv7.setMediaId((long) 1960249496);
		rv7.setAuthorName("六月莫言3");
		rv7.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv7.setMediaName("古董局中局");
		rv7.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv8=new ReturnDDHotFreeVo();
		rv8.setSaleId((long) 1980123514);
		rv8.setMediaId((long) 1960249496);
		rv8.setAuthorName("六月莫言3");
		rv8.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv8.setMediaName("古董局中局");
		rv8.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv9=new ReturnDDHotFreeVo();
		rv9.setSaleId((long) 1980123514);
		rv9.setMediaId((long) 1960249496);
		rv9.setAuthorName("六月莫言3");
		rv9.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv9.setMediaName("古董局中局");
		rv9.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv10=new ReturnDDHotFreeVo();
		rv10.setSaleId((long) 1980123514);
		rv10.setMediaId((long) 1960249496);
		rv10.setAuthorName("六月莫言3");
		rv10.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv10.setMediaName("古董局中局");
		rv10.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv11=new ReturnDDHotFreeVo();
		rv11.setSaleId((long) 1980123514);
		rv11.setMediaId((long) 1960249496);
		rv11.setAuthorName("六月莫言3");
		rv11.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv11.setMediaName("古董局中局");
		rv11.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv12=new ReturnDDHotFreeVo();
		rv12.setSaleId((long) 1980123514);
		rv12.setMediaId((long) 1960249496);
		rv12.setAuthorName("六月莫言3");
		rv12.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv12.setMediaName("古董局中局");
		rv12.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv13=new ReturnDDHotFreeVo();
		rv13.setSaleId((long) 1980123514);
		rv13.setMediaId((long) 1960249496);
		rv13.setAuthorName("六月莫言3");
		rv13.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv13.setMediaName("古董局中局");
		rv13.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv14=new ReturnDDHotFreeVo();
		rv14.setSaleId((long) 1980123514);
		rv14.setMediaId((long) 1960249496);
		rv14.setAuthorName("六月莫言3");
		rv14.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv14.setMediaName("古董局中局");
		rv14.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv15=new ReturnDDHotFreeVo();
		rv15.setSaleId((long) 1980123514);
		rv15.setMediaId((long) 1960249496);
		rv15.setAuthorName("六月莫言3");
		rv15.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv15.setMediaName("古董局中局");
		rv15.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv16=new ReturnDDHotFreeVo();
		rv16.setSaleId((long) 1980123514);
		rv16.setMediaId((long) 1960249496);
		rv16.setAuthorName("六月莫言3");
		rv16.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv16.setMediaName("古董局中局");
		rv16.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv17=new ReturnDDHotFreeVo();
		rv17.setSaleId((long) 1980123514);
		rv17.setMediaId((long) 1960249496);
		rv17.setAuthorName("六月莫言3");
		rv17.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv17.setMediaName("古董局中局");
		rv17.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv18=new ReturnDDHotFreeVo();
		rv18.setSaleId((long) 1980123514);
		rv18.setMediaId((long) 1960249496);
		rv18.setAuthorName("六月莫言3");
		rv18.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv18.setMediaName("古董局中局");
		rv18.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv19=new ReturnDDHotFreeVo();
		rv19.setSaleId((long) 1980123514);
		rv19.setMediaId((long) 1960249496);
		rv19.setAuthorName("六月莫言3");
		rv19.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv19.setMediaName("古董局中局");
		rv19.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv20=new ReturnDDHotFreeVo();
		rv20.setSaleId((long) 1980123514);
		rv20.setMediaId((long) 1960249496);
		rv20.setAuthorName("六月莫言3");
		rv20.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv20.setMediaName("古董局中局");
		rv20.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv21=new ReturnDDHotFreeVo();
		rv21.setSaleId((long) 1980123514);
		rv21.setMediaId((long) 1960249496);
		rv21.setAuthorName("六月莫言3");
		rv21.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv21.setMediaName("古董局中局");
		rv21.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv22=new ReturnDDHotFreeVo();
		rv22.setSaleId((long) 1980123514);
		rv22.setMediaId((long) 1960249496);
		rv22.setAuthorName("六月莫言3");
		rv22.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv22.setMediaName("古董局中局");
		rv22.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv23=new ReturnDDHotFreeVo();
		rv23.setSaleId((long) 1980123514);
		rv23.setMediaId((long) 1960249496);
		rv23.setAuthorName("六月莫言3");
		rv23.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv23.setMediaName("古董局中局");
		rv23.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv24=new ReturnDDHotFreeVo();
		rv24.setSaleId((long) 1980123514);
		rv24.setMediaId((long) 1960249496);
		rv24.setAuthorName("六月莫言3");
		rv24.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv24.setMediaName("古董局中局");
		rv24.setDescription("这是一部不错的小说");
		
		ReturnDDHotFreeVo rv25=new ReturnDDHotFreeVo();
		rv25.setSaleId((long) 1980123514);
		rv25.setMediaId((long) 1960249496);
		rv25.setAuthorName("六月莫言3");
		rv25.setImagUrl("http://d.hiphotos.baidu.com/zhidao/pic/item/962bd40735fae6cd0009f9410eb30f2442a70f54.jpg");
		rv25.setMediaName("古董局中局");
		rv25.setDescription("这是一部不错的小说");
		
		mediaList.add(rv1);
		mediaList.add(rv2);
		mediaList.add(rv3);
		mediaList.add(rv4);
		mediaList.add(rv5);
		mediaList.add(rv6);
		mediaList.add(rv7);
		mediaList.add(rv8);
		mediaList.add(rv9);
		mediaList.add(rv10);
		mediaList.add(rv11);
		mediaList.add(rv12);
		mediaList.add(rv13);
		mediaList.add(rv14);
		mediaList.add(rv15);
		mediaList.add(rv16);
		mediaList.add(rv17);
		mediaList.add(rv18);
		mediaList.add(rv19);
		mediaList.add(rv20);
		mediaList.add(rv21);
		mediaList.add(rv22);
		mediaList.add(rv23);
		mediaList.add(rv24);
		mediaList.add(rv25);
		
		saleListVo.setActivityEndTime(System.currentTimeMillis());
//		if(checkResult.end < mediaList.size()){
//			sender.put("mediaList", mediaList.subList(checkResult.start, checkResult.end+1));
//		}else{
//			sender.put("mediaList", mediaList.subList(checkResult.start,mediaList.size()));
//		}
		if(checkResult.end < mediaList.size()){
			saleListVo.setMediaList( mediaList.subList(checkResult.start, checkResult.end+1));
		}else{
			saleListVo.setMediaList(mediaList.subList(checkResult.start,mediaList.size()));
		}
//		saleListVo.setMediaList(mediaList);
		
		saleList.add(saleListVo);
		
    	sender.put("saleList", saleList);
		sender.put("saleListNum", saleList.size());
		sender.put("mediaListNum", mediaList.size());
		sender.success(response);
		
	}

}
