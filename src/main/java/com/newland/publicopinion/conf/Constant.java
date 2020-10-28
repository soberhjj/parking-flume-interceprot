package com.newland.publicopinion.conf;

/**
 * 常量定义
 * @author sj
 *
 */
public interface Constant {
	
	String AES_CONFIG_FILE_NAME = "aes.properties";
	
	String AES_CONFIG_KEY_ALGO_MODE = "AES.ALGO_MODE";
	
	String AES_CONFIG_KEY_KEY = "AES.KEY";
	
	String AES_CONFIG_KEY_IV = "AES.IV";
	
	String SEP_COMMA = ",";
	
	String SEP_ONE = "\001";

	//替换成的新字符/
	//(字符串被常用于parseObject函数构建json对象,而fastjson对\后面的转义有限制,因此不能用随便的一个字符代替
	//比如下面的<br/>,fastjson不支持\后面接<字符,因此在构建json对象时候,当字符串有\<br/>,则抛出异常)
	//String HTML_BR = "<br/>";
	String HTML_BR = "//";

	String CHARSET_UTF8 = "UTF-8";

	//被替换的字符(其实下面的\\\\t在字符串里面就是\\t的意思,因为被转义了)
	String REGEX_SPECIAL_CHAR = "\t|\r|\n|\\\\t|\\\\r|\\\\n|\001|\002|\003";
}
