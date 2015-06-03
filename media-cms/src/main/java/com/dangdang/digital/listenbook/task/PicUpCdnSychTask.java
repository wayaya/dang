package com.dangdang.digital.listenbook.task;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.digital.listenbook.model.PicUpCdnObj;
import com.dangdang.digital.listenbook.service.PicUpCdnThreadPoolExecutorService;
import com.dangdang.digital.utils.ConfigPropertieUtils;
import com.dangdang.digital.utils.Image64BitUtil;
import com.dangdang.digital.utils.ScaleCoverImgListen;
import com.dangdang.digital.utils.UploadPicToCDNListen;

public class PicUpCdnSychTask implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(PicUpCdnSychTask.class);
	/*private Media media;
	private MediaSale mediaSale;
	private String imgContent;
	private IMediaSaleService mediaSaleService;
	private IMediaService mediaService;*/
	private PicUpCdnObj picUpCdnObj;
	private PicUpCdnThreadPoolExecutorService picUpCdnThreadPoolExecutorService;
	
    public PicUpCdnSychTask(/*Media media,MediaSale mediaSale,String imgContent,
    		IMediaService mediaService,IMediaSaleService mediaSaleService*/
    		PicUpCdnObj picUpCdnObj,PicUpCdnThreadPoolExecutorService picUpCdnThreadPoolExecutorService) {
        /*this.media = media;
        this.mediaSale = mediaSale;
        this.imgContent = imgContent;
        this.mediaService = mediaService;
        this.mediaSaleService = mediaSaleService;*/
    	this.picUpCdnObj = picUpCdnObj;
        this.picUpCdnThreadPoolExecutorService = picUpCdnThreadPoolExecutorService;
    }
	@Override
	public void run() {
//		try {
			String picCdnPath = upPicToCdn();
			System.out.println("|"+picUpCdnObj.getMediaId()+"|"+"picCdnPath="+picCdnPath+"|=");
			/*if(picCdnPath!=null&&!picCdnPath.equals("")){
				if(picCdnPath.startsWith(":")){
					picCdnPath = picCdnPath.substring(1);	
				}
				
				//update media's coverPic
				Media mediaTemp = new Media();
				mediaTemp.setMediaId(mediaId);
				if(this.media.getCoverPic()==null){
					this.media.setCoverPic("");
				}
				mediaTemp.setCoverPic(this.media.getCoverPic().concat(",").concat(picCdnPath));
				mediaService.update(mediaTemp);
				
				//update media_sale's coverPic
				MediaSale mediaSaleTemp = new MediaSale();
				mediaSaleTemp.setSaleId(saleId);
				if(this.mediaSale.getCoverPic()==null){
					this.mediaSale.setCoverPic("");
				}
				mediaSaleTemp.setCoverPic(this.mediaSale.getCoverPic().concat(",").concat(picCdnPath));
				mediaSaleService.update(mediaSaleTemp);
				
			}
        } catch (Exception e) {
        	logger.error(" : is not completed!");
        }*/
	}
	
	private String upPicToCdn(){
		long mediaId = picUpCdnObj.getMediaId();
		String content = picUpCdnObj.getImgContent();
		long startTime = System.currentTimeMillis();
		String rootDir = ConfigPropertieUtils.getString("listenbook.media.resource.root.path");
		String imgFilePath = rootDir + File.separator+ mediaId + File.separator+mediaId+".jpg";
		String cdnPath = "";
		if (content!=null&&!content.equals("")) {
			File f = new File(rootDir + File.separator+ mediaId + File.separator);
			if (!f.exists()) {
				f.mkdirs();
			}
			boolean isGenerImg = Image64BitUtil.GenerateImage(content,
					imgFilePath);
			long generateImgTime = System.currentTimeMillis();
			if (isGenerImg) {
				f = new File(f, mediaId + ".jpg");
				try {
					if (!f.exists()) {
						f.createNewFile();
					}
					ScaleCoverImgListen sci = new ScaleCoverImgListen(f,mediaId);
					cdnPath = UploadPicToCDNListen.upload(mediaId,sci.scaleCoverPic());

					long upImgTime = System.currentTimeMillis();
//					System.out.println("*****upImg****"+(upImgTime-generateImgTime));
				} catch (IOException e) {
//					logger.error("up media="+media.getMediaId()+"|mediaSale="+mediaSale.getSaleId()+" pic to cdn IO error : ", e);
//					upPicToCdn(mediaId,content);

					picUpCdnObj.setRetryNum(picUpCdnObj.getRetryNum()+1);
			        if(picUpCdnObj.getRetryNum()<10){
//						logger.error("up media="+mediaId+" pic to cdn IO retry …… ");
						// A new thread pool is created...
				        ThreadPoolExecutor executor = picUpCdnThreadPoolExecutorService.getPicUpCdnThreadPoolExecutor();
				        executor.allowCoreThreadTimeOut(true);
				        executor.execute(this);
			        }else{
			        	logger.error("pic to cdn > 10 , will be give up. mediaId="+picUpCdnObj.getMediaId());
			        }
				} catch (Exception e) {
					logger.error("upPic to cdn error : ", e);
				}/*finally{
					if (cdnPath!=null&&!cdnPath.equals("")) {
						File dir = new File(rootDir + File.separator+ mediaId + File.separator);
						boolean result = deleteDir(dir);
						// 文件夹下图片及文件夹删除成功
						if (!result) {
							logger.warn("删除单个文件" + imgFilePath + "失败！");
						}
					}
				}*/
			}
		}
		return cdnPath;
	}
	
	/*private String upPicToCdn(long mediaId,String content){
		long startTime = System.currentTimeMillis();
		String rootDir = ConfigPropertieUtils.getString("listenbook.media.resource.root.path");
		String imgFilePath = rootDir + File.separator+mediaId+".jpg";
		String cdnPath = "";
		if (content!=null&&!content.equals("")) {
			boolean isGenerImg = Image64BitUtil.GenerateImage(content,
					imgFilePath);
			long generateImgTime = System.currentTimeMillis();
			System.out.println("*****generateImg****"+(generateImgTime-startTime));
			if (isGenerImg) {
				File f = new File(rootDir + File.separator);
				if (!f.exists()) {
					f.mkdirs();
				}
				f = new File(f, mediaId + ".jpg");
				try {
					if (!f.exists()) {
						f.createNewFile();
					}
					ScaleCoverImg sci = new ScaleCoverImg(f,mediaId);
					cdnPath = UploadPicToCDN.upload(mediaId,sci.scaleCoverPic());

					long upImgTime = System.currentTimeMillis();
					System.out.println("*****upImg****"+(upImgTime-generateImgTime));
				} catch (IOException e) {
//					logger.error("up media="+media.getMediaId()+"|mediaSale="+mediaSale.getSaleId()+" pic to cdn IO error : ", e);
					logger.error("up media="+media.getMediaId()+"|mediaSale="+mediaSale.getSaleId()+" pic to cdn IO retry …… ");
//					upPicToCdn(mediaId,content);
					// A new thread pool is created...
			        ThreadPoolExecutor executor = picUpCdnThreadPoolExecutorService.getPicUpCdnThreadPoolExecutor();
			        executor.allowCoreThreadTimeOut(true);
			      //异步upPicToCDN
			        executor.execute(new PicUpCdnSychTask(media,mediaSale,
			        		content,mediaService,mediaSaleService,picUpCdnThreadPoolExecutorService));
				} catch (Exception e) {
					logger.error("upPic to cdn error : ", e);
				}finally{
					if (cdnPath!=null&&!cdnPath.equals("")) {
						File fileImg = new File(imgFilePath);
						// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
						if (fileImg.exists() && fileImg.isFile()) {
							if (!fileImg.delete()) {
								logger.warn("删除单个文件" + imgFilePath + "失败！");
							}
						}
					}
				}
			}
		}
		return cdnPath;
	}*/
	
}
