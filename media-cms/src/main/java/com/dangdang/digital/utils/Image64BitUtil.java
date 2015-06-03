package com.dangdang.digital.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

public class Image64BitUtil {
	private static Logger logger = LoggerFactory.getLogger(Image64BitUtil.class);
	
	public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
		byte[] data = null;  
		// 读取图片字节数组  
		try {  
			InputStream in = new FileInputStream(imgFilePath);  
			data = new byte[in.available()];  
			in.read(data);  
			in.close();  
		} catch (IOException e) {  
			logger.error("read path:" + imgFilePath + " error: ", e);
		}  
		  
		// 对字节数组Base64编码  
		BASE64Encoder encoder = new BASE64Encoder();  
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串  
	}  
		  
	public static boolean GenerateImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片  
		if (imgStr == null) // 图像数据为空  
			return false;
		
//		BASE64Decoder decoder = new BASE64Decoder();  
		try {  
			/*// Base64解码  
			byte[] bytes = decoder.decodeBuffer(imgStr);  
			for (int i = 0; i < bytes.length; ++i) {  
				if (bytes[i] < 0) {// 调整异常数据  
					bytes[i] += 256;  
				}  
			}  */

			byte[] bytes = org.apache.ws.commons.util.Base64.decode(imgStr);
			// 生成jpeg图片  
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);  
			out.flush();  
			out.close();  
			return true;  
		} catch (Exception e) {
			logger.error("write imgpath:" + imgFilePath + " error: ", e);
			return false;  
		}
	}
	
	public static void main(String[] args) {
		String str = GetImageStr("D:\\temp\\122.jpg");
		System.out.println(str);
		GenerateImage(str,"D:\\temp\\221.jpg");
	}
}
