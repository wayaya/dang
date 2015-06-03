package com.dangdang.digital.processor.discovery.nearby;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javacommon.util.HeadportraitUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.Constants;
import com.dangdang.common.api.vo.DangdangUser;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.ApiException;
import com.dangdang.digital.facade.IAuthorityApiFacade;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.service.nearby.api.nearby.bean.LocationEntity;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserLocation;
import com.dangdang.digital.service.nearby.api.nearby.bean.UserQueryParams;
import com.dangdang.digital.service.nearby.impl.nearby.NearbyUpdateServiceImpl;
import com.dangdang.digital.service.nearby.impl.nearby.UserNearbyServiceImpl;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.UserTradeBaseVo;
/**
 * 
 * Description: 附近列表查询
 * All Rights Reserved.
 * @version 1.0  2014年7月16日 下午2:50:46  by 林永奇（linyongqi@dangdang.com）创建
 */
@Component("mobilenearbySearchprocessor")
public class NearbySearchProcessor  extends BaseApiProcessor{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(UploadBookListProcessor.class);
    @Resource(name="updateService")
    private NearbyUpdateServiceImpl updateService;
	@Resource(name="nearbyService")
    private UserNearbyServiceImpl searchService;
    @Resource(name = "authorityApiFacade")
	private IAuthorityApiFacade authorityApiFacade;
    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response, ResultSender sender) throws Exception {
        String uToken = request.getParameter("uToken");
        String token = request.getParameter("token");
        String start = request.getParameter("start");
        String limit = request.getParameter("limit");
        String lat = request.getParameter("lat");
        String lng = request.getParameter("lng");
        if(!StringUtils.isBlank(token)){
        	uToken=token;
        }
        
        checkParams(uToken, lat,lng);   
        checkParams(start, limit);  
        
        //未登录用户直接返回
  		UserTradeBaseVo custVo = authorityApiFacade.getUserInfoByToken(uToken);
  		if (custVo == null || custVo.getCustId() == null) {
  			sender.fail(ErrorCodeEnum.ERROR_CODE_10003.getErrorCode(),ErrorCodeEnum.ERROR_CODE_10003.getErrorMessage(), response);
  			return;
  		}
  		Long custId = custVo.getCustId();
  		
        BigDecimal latBd = new BigDecimal(lat);
        BigDecimal lngtBd = new BigDecimal(lng);
        lat = latBd.toPlainString();
        lng = lngtBd.toPlainString();
        
//        ThirdpartyCustId thirdpartyCustId = thirdpartyCustIdManager.getByCustId(custId);
//        custId = thirdpartyCustId.getId();
//		updateService.updateSolrUserLocation(Long.toString(custId),Long.toString(user.getDisplayId()), new UserLocation(Double.parseDouble(lat),Double.parseDouble(lng),0,new Date()));
        UserQueryParams param = new UserQueryParams();
        param.setLat(Double.parseDouble(lat));
        param.setLng(Double.parseDouble(lng));
        param.setDistance(150);
        param.setCustId(Long.toString(custId));
        List<LocationEntity> locationEntitys=searchService.searchNearbyCustId(param, Integer.parseInt(start), Integer.parseInt(limit));
        List<NearbyUser> results=new ArrayList<NearbyUser>();
        for(Iterator<LocationEntity> it = locationEntitys.iterator();it.hasNext();){
        	LocationEntity entity = it.next();
        	String custIdOther = entity.getCustId();
        	NearbyUser nearbyUser=new NearbyUser();
        	String displayId = entity.getDisplayId();
        	if(displayId == null || "null".equals(displayId)){
        		displayId = "8254187711217";
        	}
        	String thumb = HeadportraitUtil.getHeadportrait(Long.parseLong(displayId));
        	nearbyUser.setThumb(thumb);
        	nearbyUser.setDistance(entity.getDistance());
        	nearbyUser.setUserId(custIdOther);
    		results.add(nearbyUser);
        }
        LOG.info("UploadBookListProcessor:uToken->"+uToken+",lat->"+lat+",lng->"+lng+",start->"+start+",limit"+limit);
        sender.put("nearbyList", results);
        sender.success(response);
    }
    private static class NearbyUser{
    	private String thumb;
    	private String userId;
    	private double distance;
		public String getThumb() {
			return thumb;
		}
		public void setThumb(String thumb) {
			this.thumb = thumb;
		}
		
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public double getDistance() {
			return distance;
		}
		public void setDistance(double distance) {
			this.distance = distance;
		}
    	
    }
    
}
