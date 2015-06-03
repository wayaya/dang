package com.dangdang.digital.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class URLPathMatcher {

	public static final String DEFAULT_PATH_SEPARATOR = "/";
	private String pathSeparator = DEFAULT_PATH_SEPARATOR;

	public boolean match(String pattern, String path) {
		return (getMatchPoint(pattern, path)>0);
	}
	
	public int getMatchPoint(String pattern, String path) {
		return doMatchWithPoints(pattern, path, true, null);
	}

	
	protected int doMatchWithPoints(String pattern, String path, boolean fullMatch,
			Map<String, String> uriTemplateVariables) {
		

		if (path.startsWith(this.pathSeparator) != pattern.startsWith(this.pathSeparator)) {
			return 0;
		}

		String[] pattDirs = tokenizeToStringArray(pattern, this.pathSeparator);
		String[] pathDirs = tokenizeToStringArray(path, this.pathSeparator);

		int pattIdxStart = 0;
		int pattIdxEnd = pattDirs.length - 1;
		int pathIdxStart = 0;
		int pathIdxEnd = pathDirs.length - 1;

		// Match all elements up to the first **
		while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			String patDir = pattDirs[pattIdxStart];
			if ("**".equals(patDir)) {
				break;
			}
			if (!matchStrings(patDir, pathDirs[pathIdxStart], uriTemplateVariables)) {
				return 0;
			}
			pattIdxStart++;
			pathIdxStart++;
		}
		boolean result = false;
		if (pathIdxStart > pathIdxEnd) {
			// Path is exhausted, only match if rest of pattern is * or **'s
			if (pattIdxStart > pattIdxEnd) {
				result = (pattern.endsWith(this.pathSeparator) ? path.endsWith(this.pathSeparator) :
					!path.endsWith(this.pathSeparator));
				if(!result){
					return 0;
				}else{
					return getMatchPoints(pattDirs, pathDirs);
				}
				
			}
			if (!fullMatch) {
				 return getMatchPoints(pattDirs, pathDirs);
			}
			if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(this.pathSeparator)) {
				 return getMatchPoints(pattDirs, pathDirs);
			}
			for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
				if (!pattDirs[i].equals("**")) {
					return 0;
				}
			}
			 return getMatchPoints(pattDirs, pathDirs);
		}
		else if (pattIdxStart > pattIdxEnd) {
			// String not exhausted, but pattern is. Failure.
			return 0;
		}
		else if (!fullMatch && "**".equals(pattDirs[pattIdxStart])) {
			// Path start definitely matches due to "**" part in pattern.
			 return getMatchPoints(pattDirs, pathDirs);
		}

		// up to last '**'
		while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			String patDir = pattDirs[pattIdxEnd];
			if (patDir.equals("**")) {
				break;
			}
			if (!matchStrings(patDir, pathDirs[pathIdxEnd], uriTemplateVariables)) {
				return 0;
			}
			pattIdxEnd--;
			pathIdxEnd--;
		}
		if (pathIdxStart > pathIdxEnd) {
			// String is exhausted
			for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
				if (!pattDirs[i].equals("**")) {
					return 0;
				}
			}
			return getMatchPoints(pattDirs, pathDirs);
		}

		while (pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
			int patIdxTmp = -1;
			for (int i = pattIdxStart + 1; i <= pattIdxEnd; i++) {
				if (pattDirs[i].equals("**")) {
					patIdxTmp = i;
					break;
				}
			}
			if (patIdxTmp == pattIdxStart + 1) {
				// '**/**' situation, so skip one
				pattIdxStart++;
				continue;
			}
			// Find the pattern between padIdxStart & padIdxTmp in str between
			// strIdxStart & strIdxEnd
			int patLength = (patIdxTmp - pattIdxStart - 1);
			int strLength = (pathIdxEnd - pathIdxStart + 1);
			int foundIdx = -1;

			strLoop:
			for (int i = 0; i <= strLength - patLength; i++) {
				for (int j = 0; j < patLength; j++) {
					String subPat = pattDirs[pattIdxStart + j + 1];
					String subStr = pathDirs[pathIdxStart + i + j];
					if (!matchStrings(subPat, subStr, uriTemplateVariables)) {
						continue strLoop;
					}
				}
				foundIdx = pathIdxStart + i;
				break;
			}

			if (foundIdx == -1) {
				return 0;
			}

			pattIdxStart = patIdxTmp;
			pathIdxStart = foundIdx + patLength;
		}

		for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
			if (!pattDirs[i].equals("**")) {
				return 0;
			}
		}

		return getMatchPoints(pattDirs, pathDirs);
	}
	
	private int getMatchPoints(String[] pattDirs, String[] pathDirs) {
		int point = 1;
		point+=pattDirs.length;
		String lastPattDir = pattDirs[pattDirs.length-1];
		String lastPathDir = pathDirs[pathDirs.length-1];
		if(lastPattDir.length()==lastPathDir.length() && lastPattDir.equals(lastPathDir)){
			point+=1;
		}
		if(!"**".equals(lastPattDir)){
			point+=1;
		}
		return point;
	}


	private boolean matchStrings(String pattern, String str, Map<String, String> uriTemplateVariables) {
		AntPathStringMatcher matcher = new AntPathStringMatcher(pattern, str, uriTemplateVariables);
		return matcher.matchStrings();
	}

	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
	
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}


	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}
		return collection.toArray(new String[collection.size()]);
	}

	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	public static String[] tokenizeToStringArray(
			String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return toStringArray(tokens);
	}

}
