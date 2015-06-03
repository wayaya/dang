package com.dangdang.digital.utils;

/**
 * 电子书封面URL.
 * 
 * @author maqiang
 * 
 */
public final class MediaCoverPicUrlUtil {
	/**
	 * 图片尺寸代号.
	 */
	private enum ImageSizeCode {
		d, h, k
	}

	/**
	 * 图片尺寸：186X248
	 */
	public static final MediaCoverPicUrlUtil D = getInstance(ImageSizeCode.d);

	/**
	 * 168X224
	 */
	public static final MediaCoverPicUrlUtil H = getInstance(ImageSizeCode.h);

	/**
	 * 图片尺寸：132X176
	 */
	public static final MediaCoverPicUrlUtil K = getInstance(ImageSizeCode.k);

	/**
	 * img{idc}{product_id%10}.ddimg.cn/imgother10/{product_id%99}/{product_id%
	 * 37}/{product_id}_cover_{size}_epub.jpg.
	 */
	private static final String EBOOK_IMAGE_URL_TEMPLATE = "http://%s/imgother10/%s/%s/%s_cover_%s_media.jpg";// ConfigReader.get("ebook.image.url",
																												// "");
	/**
	 * epub template
	 */
	private static final String EBOOK_IMAGE_URL_FOR_EPUB_TEMPLATE = "http://%s/imgother10/%s/%s/%s_cover_%s_epub.jpg";
	
	private static final String EBOOK_IMAGE_SERVERNO = ConfigPropertieUtils.getString("media.cdn.serverno");
	
	private static final String EBOOK_IMAGE_VERSION = ConfigPropertieUtils.getString("media.cdn.version");
	private static final String EBOOK_IMAGE_URL_TEST = "10.255.242.6:8300";// ConfigReader.get("ebook.image.url.test");

	private ImageSizeCode imgSizeCode;

	// http://10.255.242.6:8300/imgother10/28/17/1900046143_cover_a_epub.jpg
	public String getUrl(long productId, String serverNo) {
		/*
		 * 组装url模板为最终访问url http://%s/imgother10/%s/%s/%s_cover_%s_epub.jpg
		 */
		// 测试环境用
		if (serverNo == null || serverNo.length() == 0) {
			return String.format(EBOOK_IMAGE_URL_TEMPLATE, EBOOK_IMAGE_URL_TEST, productId % 99, productId % 37,
					productId, imgSizeCode);
		}
		// 正式环境用
		return String.format(EBOOK_IMAGE_URL_TEMPLATE, "img" + serverNo + (productId % 10) + ".ddimg.cn",
				productId % 99, productId % 37, productId, imgSizeCode);
	}

	public String getUrl(long productId) {
		return getUrl(productId, EBOOK_IMAGE_SERVERNO);
	}
	
	public String getEpubUrl(long productId, String serverNo) {
		/*
		 * 组装url模板为最终访问url http://%s/imgother10/%s/%s/%s_cover_%s_epub.jpg
		 */
		// 测试环境用
		if (serverNo == null || serverNo.length() == 0) {
			return String.format(EBOOK_IMAGE_URL_FOR_EPUB_TEMPLATE, EBOOK_IMAGE_URL_TEST, productId % 99, productId % 37,
					productId, imgSizeCode);
		}
		// 正式环境用
		return String.format(EBOOK_IMAGE_URL_FOR_EPUB_TEMPLATE, "img" + serverNo + (productId % 10) + ".ddimg.cn",
				productId % 99, productId % 37, productId, imgSizeCode);
	}
	
	public String getEpubUrl(long productId) {
		return getEpubUrl(productId, EBOOK_IMAGE_SERVERNO);
	}

	private MediaCoverPicUrlUtil(ImageSizeCode imgSizeCode) {
		this.imgSizeCode = imgSizeCode;
	}

	private static final MediaCoverPicUrlUtil mcpuH = new MediaCoverPicUrlUtil(ImageSizeCode.h);

	public static String getMediaCoverPic(long productId) {
		String url = mcpuH.getUrl(productId);
		return url+"?version="+EBOOK_IMAGE_VERSION;
	}

	private static MediaCoverPicUrlUtil getInstance(ImageSizeCode imgSizeCode) {

		return new MediaCoverPicUrlUtil(imgSizeCode);
	}

	public static void main(String[] args) {
		System.out.println(new MediaCoverPicUrlUtil(ImageSizeCode.h).getUrl(1960008714L));
	}
}
