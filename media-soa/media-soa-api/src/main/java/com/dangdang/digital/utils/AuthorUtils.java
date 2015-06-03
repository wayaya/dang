package com.dangdang.digital.utils;

public class AuthorUtils {
	
	/**
	 * 根据格式化串取出作者信息（多个作者用英文;分隔）.
	 * 例如：珍妮弗·爱克曼;温雅;周财林$$（美）{0}　著；{1}，{2}　等译
	 * 返回：珍妮弗·爱克曼;温雅;周财林
	 * @param authorFormat 作者格式化串
	 * @return 作者列表
	 */
	public static String getAuthors(String authorFormat) {
		String result = "";
		if(authorFormat == null || authorFormat.length() == 0){
			return result;
		}
		String[] data = authorFormat.split("\\$\\$");
		if (data.length > 1) {
			String[] authors = data[0].split(";");
			result = handle(0, data[0], data[1], authors);
		} else {
			result = data[0];
		}
		return result;
	}
	
	/**
	 * 处理外国作者中间“.”号问题.
	 * 例如：爱德蒙;沙夫茨伯里;戚成炎$$（美）{0}&#8226;{1} 著，{2}　译
	 * 返回：爱德蒙·沙夫茨伯里;戚成炎
	 * @param startIndex 查询开始位置索引号
	 * @param result 处理后作者字符串
	 * @param formatStr 作者格式化串
	 * @param authors 作者列表
	 * @return 处理后作者字符串
	 */
	private static String handle(int startIndex, String result,
			String formatStr, String[] authors) {
		// 查询作者格式化串中是否有“.”号
		int findIndex = formatStr.indexOf("&#8226;", startIndex);
		if (findIndex >= 0) {
			//如果查询到有“.”号，进行作者拼接处理
			String numValue = formatStr.substring(findIndex - 2, findIndex - 1);
			int num = Integer.valueOf(numValue);
			if (num >= 0 && num < authors.length) {
				startIndex = findIndex + 7;
				result = result.replace(authors[num] + ";" + authors[num + 1],
						authors[num] + "·" + authors[num + 1]);
				return handle(startIndex, result, formatStr, authors);
			} else {
				return result;
			}
		} else {
			return result;
		}
	}
	

}
