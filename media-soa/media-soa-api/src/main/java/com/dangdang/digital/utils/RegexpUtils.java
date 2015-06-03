package com.dangdang.digital.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类.
 * 
 * @author dangdang
 *
 */
public abstract class RegexpUtils {
	
	private RegexpUtils() {
		
	}

	public static Matcher matcher(final String regex, final String value) {
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(value);
	}

	public static boolean isExactlyMatches(final String regex, final String value) {
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		return pattern.matcher(value).matches();
	}

	public static boolean isMatches(final String regex, final String value) {
		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		final Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}

	public static boolean isMatches(final String[] regexs, final String value) {
		for (final String regex : regexs) {
			final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			final Matcher matcher = pattern.matcher(value);
			if (matcher.find()) {
				return true;
			}
		}
		return false;
	}

	public static void main(final String[] args) {
		System.out.println(isExactlyMatches("\\d{1,3}", "726"));
	}
}
