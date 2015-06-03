package com.dangdang.digital.processor.cart;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javacommon.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.dangdang.digital.api.ICartApi;
import com.dangdang.digital.constant.ApiConstant;
import com.dangdang.digital.constant.ErrorCodeEnum;
import com.dangdang.digital.exception.MainsiteException;
import com.dangdang.digital.exception.MobileApiException;
import com.dangdang.digital.model.CartResult;
import com.dangdang.digital.processor.BaseApiProcessor;
import com.dangdang.digital.utils.MainSiteUtils;
import com.dangdang.digital.utils.PagerUtils;
import com.dangdang.digital.utils.ResultSender;
import com.dangdang.digital.vo.MobileCartResultVo;
import com.dangdang.ucenter.api.service.IUcenterApi;
import com.dangdang.ucenter.model.DangdangUser;

/**
 * 添加购物车
 * @author maqiang
 *
 */
@Component("hapiappendCartprocessor")
public class AppendCartProcessor extends BaseApiProcessor {
	@Resource
	private ICartApi cartApi;
	@Resource
	private IUcenterApi ucenterApi;
	
	private static final int INNER_PAGE_SIZE = 12;
	
	private static final int PRODUCT_ID_LENGTH = 10;

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		String fromPlatform = request.getParameter("fromPlatform");
		String productIds = request.getParameter("productIds");
		String cartId = request.getParameter("cartId");
		String permanentId = get(request, "permanentId",
				MainSiteUtils.generateGuid());
		String token = request.getParameter("token");
		String isPaperBook = request.getParameter("isPaperBook");
		
		if (!checkParams(fromPlatform, productIds, permanentId)) {
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		
		int login = 0;
		String custId = null;
		if (isPaperBook != null && "true".equals(isPaperBook)) {
			// 纸书，判断是否登录
			if (StringUtils.isNotBlank(token)) {
				DangdangUser user = ucenterApi.getDangdangUserByToken(token);
				if (user == null) {
					throw MobileApiException.userValidateFailed();
				} else {
					custId = String.valueOf(user.getId());
					// 登录状态传用户id
					cartId = custId;
					login = 1;
				}
			}
			if (StringUtils.isBlank(cartId)) {
				// 如果没传，生成临时的
				cartId = MainSiteUtils.generateCartId();
			}
		} else {
			// 电子书使用临时购物车
			cartId = MainSiteUtils.generateCartId();
		}
		
		// 检查是否重复添加
		checkEbookRepeatAppend(fromPlatform, productIds, cartId, custId, login);

		CartResult result = null;
		String[] productIdArray = productIds.split(",");
		
		int totalCount = productIdArray.length;
		// 楼上接口有一次添加12本限制，多次添加处理
		if (productIds.length() > INNER_PAGE_SIZE * PRODUCT_ID_LENGTH + INNER_PAGE_SIZE - 1) {
			int pageCount = totalCount % INNER_PAGE_SIZE == 0 ? totalCount
					/ INNER_PAGE_SIZE : totalCount / INNER_PAGE_SIZE + 1;
			List<String> productIdList = Arrays.asList(productIdArray);
			for (int i = 1; i <= pageCount; i++) {
				List<String> pageProductIds = PagerUtils.getResult(i,
						INNER_PAGE_SIZE, productIdList);
				String productId = StringUtils.join(
						pageProductIds, ",");
				result = appendCart(cartId, productId, permanentId,
						request.getRemoteHost(), fromPlatform, login, isPaperBook);
			}
		} else {
			result = appendCart(cartId, productIds, permanentId,
					request.getRemoteHost(), fromPlatform, login, isPaperBook);
		}
		sender.put("cartId", cartId);
		// 客户端直接读取状态码，然后重新获取购物车列表
//		sender.put("result", result);
		sender.send(response);

		log.debug(javacommon.util.StringUtils.concat("来源:[", fromPlatform,
				"]添加购物车成功, cartId:[", cartId, "]"));
	}
	
	/**
	 * 添加购物车.
	 * @param cartId
	 * @param productIds
	 * @param permanentId
	 * @param remoteHost
	 * @param orderSource
	 * @param login 是否登录（登录用户：1， 未登录用户: 0）
	 * @param isPaperBook 是否纸书
	 * @return
	 * @throws Exception
	 */
	private CartResult appendCart(String cartId, String productIds,
			String permanentId, String remoteHost, String orderSource,
			int login, String isPaperBook) throws Exception {
		// 电子书设置为未登录
		if (isPaperBook == null || !"true".equals(isPaperBook)) {
			login = 0;
		}
		Map<String, Object> params = CollectionUtils.buildMap("cart_id",
				cartId, "product_ids", productIds, "permanent_id", permanentId,
				"trace_id", permanentId, "order_from_ip", remoteHost,
				"cust_type", ApiConstant.CUST_TYPE, "from_platform",
				orderSource, "login", login);
		CartResult result = cartApi.appendCart(params);
		if (!result.isSucc()) {
			throw MainsiteException.appendPaperCartError(result.getErrorCode(), result.getErrorMsg());
		}
		return result;
	}

	private void checkEbookRepeatAppend(String orderSource, String productIds,
			String cartId, String custId, int login) throws Exception {
		String[] pids = productIds.split(",");
		if (pids.length > 0) {
			Map<String, Object> params = CollectionUtils.buildMap("cart_id",
					cartId, "cust_type", ApiConstant.CUST_TYPE,
					"from_platform", orderSource, "login", login);
			MobileCartResultVo result = cartApi.getCart(params, custId);
			if (!result.isSucc() && 2 != result.getErrorCode()) {
				throw MainsiteException.otherError();
			}
			String getCartInfo = result.getCartResult().getData().toString();
			for (String epid : pids) {
				if (getCartInfo.contains(epid)) {
					throw MainsiteException
							.appendPaperCartError(140, "购物车中已经有相同商品。");
				}
			}
		}
	}

}
