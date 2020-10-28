package com.newland.publicopinion.util;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
	
	/**
	 * 读取配置文件
	 * @param confFile
	 * @return
	 * @throws IOException
	 */
	public static Properties getProperties(String confFile) throws IOException {
		Properties properties = new Properties();
		properties.load(ConfigUtil.class.getResourceAsStream(confFile));
		return properties;
	}
	
}