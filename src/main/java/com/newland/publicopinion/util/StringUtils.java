package com.newland.publicopinion.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static String replace(Object text,String regexStringm,String replacement) {
		if(text == null || "".equals(text)){
			return "";
		}
		Pattern p = Pattern.compile(regexStringm);
		Matcher m = p.matcher("" + text);
		return  m.replaceAll(replacement);
	}
	
	public static boolean isEmpty(String str) {
		return (str == null || "".equals(str)) ? true : false;
	}

	/**
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static String toStr(Object obj) {
		return toStr(obj, null);
	}
	
	public static String toStr(Object obj,String defaultValue) {
		return obj == null ? defaultValue : obj.toString();
	}
	
	/**
	 * 转整型
	 * @param obj
	 * @param defaultValue
	 * @return
	 */
	public static int strToInt(Object obj) {
		return strToInt(obj, 0);
	}
	
	public static int strToInt(Object obj, int defaultValue) {
		if(obj == null || "".equals(obj)) {
			return defaultValue;
		}
		return obj instanceof Integer ? (int)obj : Integer.parseInt(obj.toString());
	}
	
	/**
	 * 打印参数
	 *
	 * @param args
	 */
	public static void printArguments(String[] args) {
		System.out.println("args:");
		for (int i = 0, len = args.length; i < len; i++) {
			System.out.println(args[i]);
		}
	}
}
