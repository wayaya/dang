package com.dangdang.digital.processor.discovery.nearby;


import java.util.Date;
import java.util.List;

import javacommon.util.ConfigReader;
import javacommon.util.JsonUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.model.BookshelfCategory;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.IBookshelfBookService;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserLocation;
import com.dangdang.digital.service.nearby.impl.nearby.NearbyUpdateServiceImpl;
import com.dangdang.digital.service.nearby.impl.nearby.UserNearbyServiceImpl;
import com.dangdang.digital.service.nearby.impl.nearby.util.GeoUtil;
import com.dangdang.digital.service.nearby.impl.nearby.util.Pair;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;

/**
 * 
 * Description: 上传书架信息
 * All Rights Reserved.
 * @version 1.0  2014年7月16日 下午2:52:00  by 林永奇（linyongqi@dangdang.com）创建
 */
@Component("mobileuploadBookListprocessor")
public class UploadBookListProcessor extends BaseApiProcessor {
	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(UploadBookListProcessor.class);
	@Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
	@Resource(name="updateService")
    private NearbyUpdateServiceImpl updateService;
	@Resource(name="nearbyService")
    private UserNearbyServiceImpl searchService;
	@Autowired
	private IBookshelfBookService service;
    
    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {

        String data=request.getParameter("data");
        checkParams(data);
        //data="{\"token\":\"k5r9iz4g9gd9dx1k3s2ndt8zbBbjun\",\"data\":[{\"categoryName\":\"计算 机\",\"lastChangedDate\":14567000000,\"books\":[{\"productId\":1900089259,\"lastChangedDate\":14567000000}]}]}";
        JsonData input=new JsonData();
        String flag=ConfigReader.get("bookshelf.upload.enable","1");
        if("0".equals(flag)){
        	sender.put("msg","done");
        	sender.send(response);
        	return;
        }
        try{
        	 input=(JsonData)JsonUtils.toObject(data, JsonData.class);
        }catch(Exception e){
        	throw ApiException.invalidParams();
        }
        List<BookshelfCategory> categorys=input.getData();
        String uToken=input.getToken();
        
        //未登录用户直接返回
  		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(uToken);
  		if (custVo == null || custVo.getCustId() == null) {
  			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
  			return;
  		}
  		Long custId = custVo.getCustId();
//        ThirdpartyCustId thirdpartyCustId=thirdpartyCustIdManager.getByCustId(custId);
//        custId=thirdpartyCustId.getId();
  		service.insertBookshelf(categorys, custId);
    	boolean isExits=searchService.isExits(Long.toString(custId));
        if(!isExits){
        	Pair<Double, Double> randomAddress=GeoUtil.randomInChina();
//            updateService.updateSolrUserLocation(String.valueOf(custId), String.valueOf(custVo.get()), new UserLocation(randomAddress.left,randomAddress.right,0,new Date()));
        }
        LOG.info("UploadBookListProcessor:data->{0}",data);
        //manager.insertBookshelf(books, category);
        sender.put("msg","done");
        sender.send(response);
    }
    
    private static class JsonData{
		private String token;
		private List<BookshelfCategory> data;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public List<BookshelfCategory> getData() {
			return data;
		}
		public void setData(List<BookshelfCategory> data) {
			this.data = data;
		}
		
		
	}
}
