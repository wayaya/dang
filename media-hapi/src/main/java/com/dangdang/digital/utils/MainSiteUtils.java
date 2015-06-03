package com.dangdang.digital.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javacommon.util.ConfigReader;
import javacommon.util.DateTimeUtils;
import javacommon.util.RegexpUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.digital.constant.ApiConstant;

/**
 * 调用主站接口工具.
 * @author yangming
 *
 */

public final class MainSiteUtils {
	
	private static Logger logger = LoggerFactory.getLogger(MainSiteUtils.class);
	
	private static final String PRODUCT_IMAGE_UPLOAD_ACTION = ConfigPropertieUtils.getString("action.product.image.upload");
	
	/** 分类用“.”分开(split)后的段数. */
	private static final int CATEGORY_SPLIT_NUM = 6;
	
	private static final String CATEGORY_PATTERN = "^([0-9]{2})|^([0-9]{2}.)+[0-9]{2}$";
	
	/** 区分电子书、纸书与其他商品字段. */
	private static final String PRODUCT_DISTINGUIST = "category_path";
	
	private MainSiteUtils() {		
	}		
		
	/**
	 * 产生permanent_id或trace_id.
	 * @return
	 */
	public static String generateGuid() {
		StringBuffer guid = new StringBuffer();
		String time = String.valueOf(new java.util.Date().getTime());		
		time = time.substring(time.length() - 3, time.length());
		String now = DateTimeUtils.format(new java.util.Date(), "yyyyMMddHHmmss");
		guid.append(now).append(time).append(RandomStringUtils.randomNumeric(6))
			.append(RandomStringUtils.randomNumeric(6))
			.append(RandomStringUtils.randomNumeric(6));
		return guid.toString();
	}
	
	/**
	 * 产生cart_id.
	 * @return
	 */
	public static String generateCartId() {
		String time = DateTimeUtils.format(new java.util.Date(), "yyMMddHHmmss");		 
		return time + RandomStringUtils.randomNumeric(4);
	}
	
	public static String generateCartDbIndex(double cartId) {
		return String.valueOf((int) cartId % 2);
	}
	
	public static String generateCheckoutDbIndex(double cartId) {
		return "0";
	}
	
	/**
	 * 组装XML格式的请求参数.
	 * @param params
	 * @return
	 */
	public static String assembleXmlRequestParams(Map<String, Object> params) {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\'1.0\' encoding=\'utf-8\' standalone=\'yes\' ?>");
		xml.append("<inputObject>");
		for (String key : params.keySet()) {
			xml.append("<").append(key).append(">")
				.append("<![CDATA[").append(params.get(key)).append("]]>")
				.append("</").append(key).append(">");
		}		
		xml.append("</inputObject>");
		return xml.toString();
	}
	
	/**
	 * 凑齐分类信息到6位.
	 *  
	 */
	public static String fillCatPath(String category) {
		String catPath = "";
		String[] catPaths = StringUtils.split(category, ",");
		for (String cat : catPaths) {
			Pattern pattern = Pattern.compile(CATEGORY_PATTERN);
			Matcher matcher = pattern.matcher(cat);
			if (!matcher.matches()) {
				throw new RuntimeException();
			} 
			int fillNum = CATEGORY_SPLIT_NUM - cat.split("\\.").length;
			for (int i = 0; i < fillNum; i++) {
				cat += ".00";
			}
			catPath += cat + ",";
		}
		return catPath.substring(0, catPath.length() - 1);
	}
	
	/**
	 * 五星排行参数处理.
	 * 
	 */
	public static Map<String, Object> handle5StarParam(String year, String month, String type,
			String catpath, String time) {
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.equals(type, "month")) {
			year = String.valueOf(calendar.get(Calendar.YEAR));
			month = String.valueOf(calendar.get(Calendar.MONTH));
			params.put("type", type);
		} else if (StringUtils.equals(type, "all")) {
			params.put("type", type);
		} else {
			type = "recent" + time;
			params.put("type", type);
		}
		params.put("catpath", catpath);
		params.put("year", year);
		params.put("month", month);
		return params;
	}
	
	/**
	 * 畅销排行参数处理.
	 * 
	 */
	public static Map<String, Object> handleBestSellParam(String type,
			String limit, String catPath, String row, String range, String year) {
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.equals(type, "week") || StringUtils.equals(type, "month")) {
			year = String.valueOf(calendar.get(Calendar.YEAR));
			params.put("year", year);
			params.put("range", range);
		} else {
			params.put("range", "0");
			params.put("year", year);
		}  
		params.put("type", type);
		params.put("limit", limit);
		params.put("catpath", catPath);
		params.put("row", row);
		return params;
	}
	
	/**
	 * 获得产品类型(电子书、纸书、电子书纸书混合).
	 * 
	 */
	public static String getProductType(String catpath) {
		String productType = "";
		if (StringUtils.startsWith(catpath, "98")) {
			productType = ApiConstant.PRODUCT_TYPE_EBOOK;
		} else if (StringUtils.startsWith(catpath, "01")) {
			productType = ApiConstant.PRODUCT_TYPE_PAPER;
		} 
		return productType;
	}
	    
    /** 
     * 
     * @param contentId
     * @return
     * @throws Exception
     */
    public static String getProductsByContentId(String contentId) throws Exception {     	
		String result = getProductById(contentId);
		if (StringUtils.isNotBlank(result)) {		
			result = result.replaceAll("[^\\d\\,]", "");
			result = result.replaceAll(",{2,}", ",");
			if (result.startsWith(",")) {
				result = result.substring(1, result.length());
			}
		}
		return result;
    }
    
    
    private static String getProductById(String contentId) throws SQLException {    	
    	String result = "";
    	String headerStr = "";
    	String headerIds = "";
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	try {
    		Class.forName(ConfigReader.get("jdbc.driver"));
			conn = DriverManager.getConnection(
					ConfigReader.get("product.jdbc.url"),
					ConfigReader.get("product.jdbc.username"),
					ConfigReader.get("product.jdbc.password"));
			stmt = conn.createStatement();
			rs = stmt
					.executeQuery("SELECT content_head_html FROM `InfoContent` WHERE content_id = "
							+ contentId);
			while (rs.next()) {
				headerStr += rs.getString(1) + ",";
			}
			System.out.println(headerStr);
			Matcher m = RegexpUtils.matcher("AddToShoppingCart\\((\\d+)\\)", headerStr);
			while (m.find()) {
				headerIds += m.group(1) + ",";
			}
			result += headerIds;
			
			rs = stmt
					.executeQuery("select relation_product_ids from InfoContentRelation where content_id = "
							+ contentId);
			
			while (rs.next()) {
				result += rs.getString(1) + ",";
			}
			if (result.endsWith(",")) {
				result = result.substring(0, result.length() - 1);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return result;
    }
    
	public static void main(String[] args) throws Exception {		
		String str = "22585212,  ____,22585241,22552794,22590171,22490352, 21092827," 
			+ "21053523,22520251,22469344,20512860,22520244____,  22775964,22781710,22784018,22543216____";

		str = str.replaceAll("[^\\d\\,]", "");
		System.out.println(str);
		str=str.replaceAll(",{2,}", ",");
		//if (str.startsWith(",")) {
		//	str = str.substring(1, str.length());
		//}
		System.out.println(str);
		
/*		Matcher m = RegexpUtils.matcher("AddToShoppingCart\\((\\d+)\\)", "javascript:AddToShoppingCart(22785291)asdsad javascript:AddToShoppingCart(22813330)");
		while (m.find()) {
			System.out.println(m.group(1));
		}*/
	
		//System.out.println(getProductById("223388"));
		
	}
}
