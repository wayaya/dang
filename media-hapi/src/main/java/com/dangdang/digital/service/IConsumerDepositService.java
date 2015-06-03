package com.dangdang.digital.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dangdang.digital.model.ConsumerDeposit;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.ConsumerDepositReturnVo;

public interface IConsumerDepositService extends IBaseService<ConsumerDeposit,Long> {

	/**
	 * 
	 * Description: 参数检查和封装
	 * @Version1.0 2014年12月9日 下午4:46:24 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param request
	 * @param response
	 * @param sender
	 * @param consumerDeposit
	 * @return
	 */
	public boolean checkAndEncapsulateParams(HttpServletRequest request,HttpServletResponse response, ResultSender sender,ConsumerDeposit consumerDeposit);
	
	/**
	 * 
	 * Description: 获取用户最后一次充值方式编号
	 * @Version1.0 2014年12月11日 下午6:04:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public ConsumerDeposit findLastTimePayment(Long custId,String fromPaltform);
	
	/**
	 * 
	 * Description: 获取用户充值记录（分页）
	 * @Version1.0 2014年12月17日 下午2:35:20 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @return
	 */
	public List<ConsumerDepositReturnVo> findConsumerDepositPageByCust(Long custId,Integer start,Integer count,String fromPaltform);
	/**
	 * 
	 * Description: 获取用户充值记录总条数
	 * @Version1.0 2015年1月24日 下午2:44:02 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param custId
	 * @param start
	 * @param count
	 * @return
	 */
	public Integer findConsumerDepositCountByCust(Long custId, String fromPaltform);
	
	/**
	 * ios支付状态校验
	 */
	public Map<String, Object> getValidateResult(String receiptData) throws Exception;
	/**
	 * 
	 * Description: ios支付校验获取支付订单号
	 * @Version1.0 2015年3月18日 下午5:03:14 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param receiptData
	 * @return
	 */
	public String getOrderNo(String receiptData);
	
	/**
	 * 
	 * Description: ios支付校验检查是否是沙盒环境
	 * @Version1.0 2015年4月14日 下午8:28:40 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param receiptData
	 * @return
	 */
	public boolean checkSandbox(String receiptData);
	/**
	 * 
	 * Description: ios支付校验获取商品id
	 * @Version1.0 2015年3月18日 下午5:03:14 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param receiptData
	 * @return
	 */
	public String getProductId(String receiptData);
	
	/**
	 * 
	 * Description: 充值校验md5签名
	 * @Version1.0 2015年3月19日 下午6:31:32 by 张宪斌（zhangxianbin@dangdang.com）创建
	 * @param request
	 * @return
	 */
	public boolean checkDepositMD5Sign(HttpServletRequest request);
}
