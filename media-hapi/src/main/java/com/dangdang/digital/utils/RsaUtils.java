package com.dangdang.digital.utils;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA算法，实现数据的加密解密.
 * 
 */
public abstract class RsaUtils {
	private static final Logger LOG = LoggerFactory.getLogger(RsaUtils.class);
	private static final String ALGORITHM = "RSA";

	/**
	 * 默认秘钥长度
	 */
	private static final int DEFAULT_KEY_BIT = 512;

	/**
	 * 默认最大加密秘钥长度
	 */
	private static final int DEFAULT_MAX_ENCRYPT_BLOCK = 53;

	/**
	 * 默认最大解密秘钥长度
	 */
	private static final int DEFAULT_MAX_DECRYPT_BLOCK = 64;

	private static Cipher cipher;

	static {
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (Exception e) {
			LOG.debug("初始化失败！", e);
			throw new RuntimeException(e);
		}
	}

	private RsaUtils() {
	}

	/**
	 * 
	 * Description: 生成特定长度的秘钥对
	 * 
	 * @Version1.0 2014年12月10日 上午9:17:22 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param keyBit
	 * @return
	 */
	public static Map<RsaKey, String> generateKeyPair(int keyBit) {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
			// 密钥位数
			keyPairGen.initialize(keyBit);
			// 密钥对
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 私钥
			PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			// 得到公钥字符串
			String publicKeyStr = getKeyString(publicKey);
			// 得到私钥字符串
			String privateKeyStr = getKeyString(privateKey);
			// 将生成的密钥对返回
			Map<RsaKey, String> map = new HashMap<RsaKey, String>();
			map.put(RsaKey.publicKey, publicKeyStr);
			map.put(RsaKey.privateKey, privateKeyStr);
			return map;
		} catch (Exception e) {
			LOG.error("生成密钥对失败！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Description: 生成默认长度的秘钥对
	 * 
	 * @Version1.0 2014年12月10日 上午9:17:57 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @return
	 */
	public static Map<RsaKey, String> generateKeyPair() {
		return generateKeyPair(DEFAULT_KEY_BIT);
	}

	/**
	 * 
	 * Description: 获取公钥
	 * 
	 * @Version1.0 2014年12月10日 上午9:19:41 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @return
	 */
	public static Key getPublicKey(String key) {
		try {
			byte[] keyBytes = Base64Utils.decode(key);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			Key publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (Exception e) {
			LOG.error("得到公钥失败！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Description: 获取私钥
	 * 
	 * @Version1.0 2014年12月10日 上午9:19:56 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @return
	 */
	public static Key getPrivateKey(String key) {
		try {
			byte[] keyBytes = Base64Utils.decode(key);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(keySpec);
			return privateKey;
		} catch (Exception e) {
			LOG.error("得到私钥失败！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Description: 通过秘钥对象获取秘钥字符串
	 * 
	 * @Version1.0 2014年12月10日 上午9:20:38 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @return
	 */
	public static String getKeyString(Key key) {
		try {
			byte[] keyBytes = key.getEncoded();
			String keyStr = Base64Utils.encodeBytes(keyBytes);
			return keyStr;
		} catch (Exception e) {
			LOG.error("得到密钥字符串失败！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Description: 加密
	 * 
	 * @Version1.0 2014年12月10日 上午9:22:23 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @param plainContent
	 * @return
	 */
	public static byte[] encrypt(Key key, byte[] plainContent) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(plainContent);
		} catch (Exception e) {
			LOG.error("加密失败！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Description: 分段加密
	 * 
	 * @Version1.0 2014年12月10日 上午9:22:57 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @param plainContent
	 * @return
	 */
	public static byte[] encryptBlock(Key key, byte[] plainContent, int eccryptBlock) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			int inputLen = plainContent.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > eccryptBlock) {
					cache = cipher.doFinal(plainContent, offSet, eccryptBlock);
				} else {
					cache = cipher.doFinal(plainContent, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * eccryptBlock;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;
		} catch (Exception e) {
			LOG.error("加密失败！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Description: 使用默认分段长度进行分段加密
	 * 
	 * @Version1.0 2014年12月10日 上午9:25:43 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @param plainContent
	 * @return
	 */
	public static byte[] encryptBlock(Key key, byte[] plainContent) {
		return encryptBlock(key, plainContent, DEFAULT_MAX_ENCRYPT_BLOCK);
	}

	/**
	 * 
	 * Description: 解密
	 * 
	 * @Version1.0 2014年12月10日 上午9:27:19 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @param encryptContent
	 * @return
	 */
	public static byte[] decrypt(Key key, byte[] encryptContent) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] deBytes = cipher.doFinal(encryptContent);
			return deBytes;
		} catch (Exception e) {
			LOG.error("解密失败！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Description: 分段解密
	 * 
	 * @Version1.0 2014年12月10日 上午9:28:13 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @param encryptBytes
	 * @param decryptBlock
	 * @return
	 */
	public static byte[] decryptSectionTest(Key key, byte[] encryptBytes, int decryptBlock) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			int inputLen = encryptBytes.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > decryptBlock) {
					cache = cipher.doFinal(encryptBytes, offSet, decryptBlock);
				} else {
					cache = cipher.doFinal(encryptBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * decryptBlock;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return decryptedData;
		} catch (Exception e) {
			LOG.error("解密失败！", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * Description: 使用默认长度分段进行分段解密
	 * 
	 * @Version1.0 2014年12月10日 上午9:29:42 by 许文轩（xuwenxuan@dangdang.com）创建
	 * @param key
	 * @param encryptBytes
	 * @return
	 */
	public static byte[] decryptSectionTest(Key key, byte[] encryptBytes) {
		return decryptSectionTest(key, encryptBytes, DEFAULT_MAX_DECRYPT_BLOCK);
	}

	/**
	 * 
	 * Description: 秘钥类型枚举 All Rights Reserved.
	 * 
	 * @version 1.0 2014年12月10日 上午9:28:45 by 许文轩（xuwenxuan@dangdang.com）创建
	 */
	public enum RsaKey {
		publicKey, privateKey;
	}

}
