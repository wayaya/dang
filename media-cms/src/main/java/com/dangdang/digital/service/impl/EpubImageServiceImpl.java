package com.dangdang.digital.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.annotation.Resource;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dangdang.digital.dao.IBaseDao;
import com.dangdang.digital.dao.IEpubImageDao;
import com.dangdang.digital.model.EpubImage;
import com.dangdang.digital.model.Media;
import com.dangdang.digital.service.IEpubImageService;
import com.dangdang.digital.service.IMediaService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.InputStreamDataSource;
import com.dangdang.digital.utils.ProductRelativeStub;
import com.dangdang.digital.utils.ProductRelativeStub.UploadProductRelativeImage;
import com.dangdang.digital.utils.ProductRelativeStub.UploadProductRelativeImageResponse;

@Service
public class EpubImageServiceImpl extends BaseServiceImpl<EpubImage,Long>  implements IEpubImageService{
	
	Logger log = LoggerFactory.getLogger(EpubImageServiceImpl.class);
	@Resource 
	IEpubImageDao epubImageDao;
	@Resource 
	IMediaService mediaService;
	
	@Override
	public IBaseDao<EpubImage> getBaseDao() {
		return epubImageDao;
	}
	
	@Override
	public boolean uploadImage(Long mediaId, MultipartFile file) throws Exception{
		Media media = mediaService.get(mediaId);
		if(media == null){
			throw new RuntimeException("未查询到有效作品");
		}
		Long productId = media.getMediaId();
		UploadProductRelativeImageResponse rsp;
		String result;
		String fileName = "userUpload_" + String.valueOf(System.currentTimeMillis());
		InetAddress addr = InetAddress.getLocalHost();
		String ip=addr.getHostAddress();
		ProductRelativeStub stb=new ProductRelativeStub();
		UploadProductRelativeImage image=new UploadProductRelativeImage();
		image.setFilestream(new DataHandler(new InputStreamDataSource(file.getInputStream())));
		image.setImage_desc("epub电子书【"+productId+"】内容图.");
		image.setImage_sign(fileName);
		int imageType = 0;
		if(file.getContentType().indexOf("jp")>=0){
			imageType = 0;
		} else if (file.getContentType().indexOf("gif")>=0){
			imageType = 1;
		} else if (file.getContentType().indexOf("png")>=0){
			imageType = 2;
		}
		image.setImage_type(imageType);
		image.setOperator_name("root");
		image.setOperator_type(0);
		image.setOwner_id(productId);
		image.setOwner_type(10);
		image.setUpload_ip(ip);
		rsp = stb.uploadProductRelativeImage(image);
		result = rsp.getUploadProductRelativeImageResult();
		if(result == null || "".equals(result.trim()) || !"true".equals(result.substring(0,result.indexOf(":")))){
			throw new Exception("封面图压缩后，上传CDN时出错！上传返回结果："+result);
		}
		log.info(result);
		EpubImage ei = new EpubImage();
		ei.setMediaId(productId);
		ei.setStatus(EpubImage.STATUS_FINISHED);
		ei.setImportTime(new Date());
		String imageurlpattern = ConfigPropertieUtils.getString("epub.image.url.pattern");
		imageurlpattern = imageurlpattern.replaceFirst("<1>", "3");
		imageurlpattern = imageurlpattern.replaceFirst("<2>", String.valueOf(productId%10));
		imageurlpattern = imageurlpattern.replaceFirst("<3>", "10");
		imageurlpattern = imageurlpattern.replaceFirst("<4>", String.valueOf(productId%99));
		imageurlpattern = imageurlpattern.replaceFirst("<5>", String.valueOf(productId%37));
		imageurlpattern = imageurlpattern.replaceFirst("<6>_", result.substring(result.lastIndexOf("/")+1,result.length()));
		ei.setUrl(imageurlpattern);
		this.save(ei);
		return true;
		
	}
	
	
}
