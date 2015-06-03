package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具类.
 * 
 * @author dangdang
 * 
 */
public abstract class PagerUtils {

	private PagerUtils() {
	}

	/**
	 * 获取分页结果.
	 * 
	 * @param pageNum
	 *            页码
	 * @param pageSize
	 *            页大小
	 * @param totalNum
	 *            总数大小
	 * @param list
	 *            待处理列表
	 * @return
	 */
	public static <T> List<T> getResult(int pageNum, int pageSize, int totalNum, List<T> list) {
		if (pageNum * pageSize > totalNum) {
			pageNum = totalNum / pageSize + 1;
		}
		int start = (pageNum - 1) * pageSize;
		int leftRecords = totalNum - start;
		List<T> result = new ArrayList<T>();
		int end = 0;
		if (leftRecords < pageSize) {
			end = totalNum;
			for (int i = start; i < end; i++) {
				result.add(list.get(i));
			}
		} else {
			end = pageNum * pageSize;
			for (int i = start; i < end; i++) {
				result.add(list.get(i));
			}
		}
		return result;
	}

	public static <T> List<T> getResult(int pageNum, int pageSize, List<T> list) {
		int totalNum = list.size();
		int start = (pageNum - 1) * pageSize;
		int leftRecords = totalNum - start;
		List<T> result = new ArrayList<T>();
		int end = 0;
		if (leftRecords <= 0) {

		} else if (leftRecords < pageSize) {
			end = totalNum;
			for (int i = start; i < end; i++) {
				result.add(list.get(i));
			}
		} else {
			end = pageNum * pageSize;
			for (int i = start; i < end; i++) {
				result.add(list.get(i));
			}
		}
		return result;
	}

}
