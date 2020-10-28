package com.newland.interceprot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.newland.framework.common.date.DateUtils;
import com.newland.framework.common.parser.JSON;
import com.newland.publicopinion.conf.Constant;
import com.newland.publicopinion.util.StringUtils;

public class JsonTest {

	private static final String ALGO = "AES";
    private static final String ALGO_MODE = "AES/CBC/PKCS7Padding";
    private static final String KEY = "Hybi2xa4Z3azeA6e";
    private static final String IV = "Hybi2xa4Z3azeA6e";
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final String FIELD_CRAWL_TIME = "crawl_time";

	public static void main(String[] args) throws Exception {
		String charsetName = "UTF-8";

		String inputFile = "D:\\tmp\\data\\scrapy_udn.log";
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFile))));
		String str = null;
		String decryptStr = null;
		String[] dataArry = null;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
			decryptStr = decrypt(str, charsetName);
			System.out.println(decryptStr);
			dataArry = parse(decryptStr);
			System.out.println(dataArry[0]);
			System.out.println(dataArry[1]);
		}
		br.close();

	}

	private static String decrypt(String content, String charsetName) throws Exception {
        try {
        	Cipher cipher = getCipher();
            byte[] encrypted1 = decoder.decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,charsetName);
            return originalString;
        } catch (Exception ex) {
        	ex.printStackTrace();
            return null;
        }
	}
	
	private static Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance(ALGO_MODE);
    	byte[] keyByteArray = KEY.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(keyByteArray, ALGO);
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        return cipher;
	}
	
	private static String[] parse(String eventData) throws Exception {
		String[] resultArr = new String[2];
		Map<String,Object> jsonObj = JSON.toBean(StringUtils.replace(eventData, Constant.REGEX_SPECIAL_CHAR, Constant.HTML_BR), Map.class);
		String id = StringUtils.toStr(jsonObj.get("_id")); // 唯一标识
		String title = StringUtils.toStr(jsonObj.get("title")); // 标题
		String content = StringUtils.toStr(jsonObj.get("content")); // 发布的内容
		String userId = StringUtils.toStr(jsonObj.get("user_id")); // 发布用户ID
		String userName = StringUtils.toStr(jsonObj.get("user_name")); // 发布用户名称
		String createdAt = StringUtils.toStr(jsonObj.get("created_at")); // 发布时间(yyyy-MM-dd HH:mm:ss)
		String website = StringUtils.toStr(jsonObj.get("website")); // 网址地址
		String url = StringUtils.toStr(jsonObj.get("url")); // 原文链接地址
		int likeNum = StringUtils.strToInt(jsonObj.get("like_num")); // 点赞数
		int repostNum = StringUtils.strToInt(jsonObj.get("repost_num")); // 转发数
		int commentNum = StringUtils.strToInt(jsonObj.get("comment_num")); // 评论数
		int crawlTime = StringUtils.strToInt(jsonObj.get(FIELD_CRAWL_TIME)); // 抓取时间戳 
		
		StringBuilder sb = new StringBuilder();
		sb.append(id).append(Constant.SEP_ONE)
		  .append(title).append(Constant.SEP_ONE)
		  .append(content).append(Constant.SEP_ONE)
		  .append(userId).append(Constant.SEP_ONE)
		  .append(userName).append(Constant.SEP_ONE)
		  .append(createdAt).append(Constant.SEP_ONE)
		  .append(website).append(Constant.SEP_ONE)
		  .append(url).append(Constant.SEP_ONE)
		  .append(likeNum).append(Constant.SEP_ONE)
		  .append(repostNum).append(Constant.SEP_ONE)
		  .append(commentNum).append(Constant.SEP_ONE)
		  .append(crawlTime);
		resultArr[0] = DateUtils.dateToString(DateUtils.getDate(crawlTime),"yyyyMMdd");
		resultArr[1] = sb.toString(); 
		return resultArr;
	}
}
