package com.dangdang.digital.utils;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * IP工具类.
 */
public abstract class IpUtils {
	
	public static final String IP_REGEXP_1 = "(\\d{1,3}\\.){3}\\d{1,3}";
	public static final String IP_REGEXP_2 = "(\\d{1,3}\\.){1}((\\d{1,3}|\\*)\\.){2}(\\d{1,3}|\\*)";
	public static final String IP_REGEXP_3 = "(\\d{1,3}\\.){3}\\d{1,3}\\s*-\\s*(\\d{1,3}\\.){3}\\d{1,3}";
	public static final String IP_REGEXP_4 = "(\\d{1,3}\\.){3}\\d{1,3}\\s*,\\s*(\\d{1,3}\\.){3}\\d{1,3}";

	public static final String IP_REGEXP_5 = 
		"((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";
	public static final String IP_REGEXP_6 = 
		"((\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]|\\*)\\.){3}(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5]|\\*)";

	private IpUtils() {
		
	}
	
	/**
	 * 将字符串表示的ip地址转换为long表示.
	 *
	 * @param ip ip地址
	 * @return 以32位整数表示的ip地址
	 */
	public static final long ip2Long(final String ip) {
		if (!RegexpUtils.isExactlyMatches(IP_REGEXP_1, ip)) {
			throw new IllegalArgumentException("[" + ip + "]不是有效的ip地址");
		}
		final String[] ipNums = ip.split("\\.");
		return (Long.parseLong(ipNums[0]) << 24)
				+ (Long.parseLong(ipNums[1]) << 16)
				+ (Long.parseLong(ipNums[2]) << 8)
				+ (Long.parseLong(ipNums[3]));
	}

	/**
	 * 将整数表示的ip地址转换为字符串表示.
	 *
	 * @param ip 32位整数表示的ip地址
	 * @return 点分式表示的ip地址
	 */
	public static final String long2Ip(final long ip) {
		final long[] mask = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		final StringBuilder ipAddress = new StringBuilder();
		for (int i = 0; i < mask.length; i++) {
			ipAddress.insert(0, (ip & mask[i]) >> (i * 8));
			if (i < mask.length - 1) {
				ipAddress.insert(0, ".");
			}
		}
		return ipAddress.toString();
	}

	/**
	 * 检测IP地址是否在范围之内(带“-”的格式，只适用于同一网段).
	 *
	 * @param ip 要查看的IP地址
	 * @param ipStrings 点分式表示的ip地址范围
	 * @return true-在范围之内，false不在范围之内
	 */
	public static final boolean matchTwoIpNums(final String ip, final String ipStrings) {
		final String[] ipTwo = ipStrings.split("-");
		for (int i = 0; i < ipTwo.length; i++) {
			ipTwo[i] = ipTwo[i].trim();
		}
		final long beginIp = Math.min(IpUtils.ip2Long(ipTwo[0]), IpUtils
				.ip2Long(ipTwo[1]));
		final long endIp = Math.max(IpUtils.ip2Long(ipTwo[0]), IpUtils
				.ip2Long(ipTwo[1]));
		return (IpUtils.ip2Long(ip) >= beginIp && IpUtils.ip2Long(ip) <= endIp);
	}

	/**
	 * 检测IP地址是否在范围之内(带“-”的格式，适用任一网段).
	 *
	 * @param ip 要查看的IP地址
	 * @param ipStrings 点分式表示的ip地址范围
	 * @return true-在范围之内，false不在范围之内
	 */
	public static final boolean matchTwoIpNumsSegment(final String ip,
			final String ipStrings) throws Exception {
		if (ip == null || ipStrings == null || "".equals(ip)
				|| "".equals(ipStrings)) {
			throw new Exception("matchTwoIpNumsSegment方法的参数为空");
		}
		if (!RegexpUtils.isExactlyMatches(IP_REGEXP_3, ipStrings)) {
			throw new Exception(
					"表示ip地址范围格式不正确，为xxx.xxx.xxx.xxx-xxx.xxx.xxx.xxx");
		}
		final String[] ipStrs = ipStrings.split("-");
		boolean bMatch = compareIP(ipStrs[0].trim(), ip.trim())
				&& compareIP(ip.trim(), ipStrs[1].trim());
		return bMatch;
	}

	/**
	 * 比较IP地址.
	 *
	 * @param startIp
	 * @param endIp
	 * @return startIp小于或等于endIp时返回True，否则返回False
	 * @throws Exception
	 */
	public static final boolean compareIP(final String startIp, final String endIp)
			throws Exception {
		if (!Pattern.matches(IP_REGEXP_5, startIp)
				|| !Pattern.matches(IP_REGEXP_5, endIp)) {
			throw new Exception("ip地址格式不正确");
		}
		final String[] ipSplitArray1 = startIp.trim().split("\\.");
		final String[] ipSplitArray2 = endIp.trim().split("\\.");
		if ((Integer.parseInt(ipSplitArray2[0]) > Integer
				.parseInt(ipSplitArray1[0]))) {
			return true;
		} else if ((Integer.parseInt(ipSplitArray2[0]) == Integer
				.parseInt(ipSplitArray1[0]))
				&& (Integer.parseInt(ipSplitArray2[1]) > Integer
						.parseInt(ipSplitArray1[1]))) {
			return true;
		} else if ((Integer.parseInt(ipSplitArray2[0]) == Integer
				.parseInt(ipSplitArray1[0]))
				&& (Integer.parseInt(ipSplitArray2[1]) == Integer
						.parseInt(ipSplitArray1[1]))
				&& (Integer.parseInt(ipSplitArray2[2]) > Integer
						.parseInt(ipSplitArray1[2]))) {
			return true;
		} else if ((Integer.parseInt(ipSplitArray2[0]) == Integer
				.parseInt(ipSplitArray1[0]))
				&& (Integer.parseInt(ipSplitArray2[1]) == Integer
						.parseInt(ipSplitArray1[1]))
				&& (Integer.parseInt(ipSplitArray2[2]) == Integer
						.parseInt(ipSplitArray1[2]))
				&& (Integer.parseInt(ipSplitArray2[3]) >= Integer
						.parseInt(ipSplitArray1[3]))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取访问用户的客户端IP（适用于公网与局域网）.
	 */
	public static final String getIpAddr(final HttpServletRequest request)
			throws Exception {
		if (request == null) {
			throw (new Exception("getIpAddr method HttpServletRequest Object is null"));
		}
		String ipString = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
			ipString = request.getRemoteAddr();
		}

		// 多个路由时，取第一个非unknown的ip
		final String[] arr = ipString.split(",");
		for (final String str : arr) {
			if (!"unknown".equalsIgnoreCase(str)) {
				ipString = str;
				break;
			}
		}

		return ipString;
	}

	/**
	 * 比较IP地址是否相同.
	 *
	 * @param startIp 要查看的IP地址
	 * @param endIp 表示的ip地址范围
	 * @return 相同返回True，否则返回False
	 * @throws Exception
	 */
	public static final boolean equalsIP(final String startIp, final String endIp)
			throws Exception {
		if (!Pattern.matches(IP_REGEXP_5, startIp)
				|| !Pattern.matches(IP_REGEXP_6, endIp)) {
			throw new Exception("ip地址格式不正确");
		}
		final String[] ipSplitArray1 = startIp.trim().split("\\.");
		final String[] ipSplitArray2 = endIp.trim().split("\\.");
		if ("*".equals(ipSplitArray2[0])) {
			return true;
		} else if (ipSplitArray1[0].equals(ipSplitArray2[0])
				&& "*".equals(ipSplitArray2[1])) {
			return true;
		} else if (ipSplitArray1[0].equals(ipSplitArray2[0])
				&& ipSplitArray1[1].equals(ipSplitArray2[1])
				&& "*".equals(ipSplitArray2[2])) {
			return true;
		} else if (ipSplitArray1[0].equals(ipSplitArray2[0])
				&& ipSplitArray1[1].equals(ipSplitArray2[1])
				&& ipSplitArray1[2].equals(ipSplitArray2[2])
				&& "*".equals(ipSplitArray2[3])) {
			return true;
		} else if (ipSplitArray1[0].equals(ipSplitArray2[0])
				&& ipSplitArray1[1].equals(ipSplitArray2[1])
				&& ipSplitArray1[2].equals(ipSplitArray2[2])
				&& ipSplitArray1[3].equals(ipSplitArray2[3])) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是有效的IP地址.
	 */
	public static final boolean isIP(final String ipStr) {
		if (ipStr == null) {
			return false;
		}
		return Pattern.matches(IP_REGEXP_5, ipStr);
	}

	/**
	 * 判断IP表达式中是否带“*”号.
	 */
	public static final boolean isHaveAsteriskStr(final String ipStr) {
		if ("".equals(ipStr) || ipStr == null) {
			return false;
		}
		return ipStr.indexOf("*") != -1;
	}

	/**
	 * 判断IP表达式中是否带“-”号.
	 */
	public static final boolean isHaveMinusStr(final String ipStr) {
		if ("".equals(ipStr) || ipStr == null) {
			return false;
		}
		return ipStr.indexOf("-") != -1;
	}

	/**
	 * 判断IP表达式中是否带“,”号.
	 */
	public static final boolean isHaveCommaStr(final String ipStr) {
		if ("".equals(ipStr) || ipStr == null) {
			return false;
		}
		return ipStr.indexOf(",") != -1;
	}

	/**
	 * 检测IP地址是否在范围之内.
	 */
	public static final boolean matchIpAddress(String ip, final String ipStrings) {
		final String[] ipNums = ipStrings.split("\r\n");
		if (RegexpUtils.isExactlyMatches(IpUtils.IP_REGEXP_4, ip)) {
			ip = ip.split("\\,")[0];
		}
		for (int i = 0; i < ipNums.length; i++) {
			if (RegexpUtils.isExactlyMatches(IpUtils.IP_REGEXP_1, ipNums[i])) {
				if (ip.equals(ipNums[i])) {
					return true;
				}
			} else if (RegexpUtils.isExactlyMatches(IpUtils.IP_REGEXP_2,
					ipNums[i])) {
				final String beginIp = ipNums[i].replaceAll("\\*", "0");
				final String endIp = ipNums[i].replaceAll("\\*", "255");
				final String ipTwo = beginIp + "-" + endIp;
				if (IpUtils.matchTwoIpNums(ip, ipTwo)) {
					return true;
				}
			} else if (RegexpUtils.isExactlyMatches(IpUtils.IP_REGEXP_3,
					ipNums[i])) {
				if (IpUtils.matchTwoIpNums(ip, ipNums[i])) {
					return true;
				}

			}
		}
		return false;
	}

	public static void main(final String[] args) throws Exception {
		System.out.println(matchTwoIpNumsSegment("192.1.1.1",
				"192.0.1.1 - 192.1.1.3"));
		System.out.println(IpUtils.equalsIP("192.168.2.1", "*.168.1.*"));
	}
}
