package com.dangdang.digital.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import magick.MagickException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ScaleCoverImgListen {
	private static Logger logger = LoggerFactory.getLogger(ScaleCoverImgListen.class);
	
	private boolean isSuccess = false;
	private String msg;
	private File coverPic;
	private File scaleDir;
	private float ratio = 0;  //宽高比
	private String uid;
	private long productId;
	private DecimalFormat df = new DecimalFormat("0.00");
	private String suffix[] = {"d","h","k"};
	
	public ScaleCoverImgListen(File coverPic,
			long productId){
		this.coverPic = coverPic;
		this.productId = productId;
	}
	public boolean isSuccess() {
		return isSuccess;
	}

	public float getRatio(){
		return this.ratio;
	}
	public String getMsg() {
		return msg;
	}

	public void setCoverPic(File coverPic) {
		this.coverPic = coverPic;
	}

	public void setScaleDir(File scaleDir) {
		this.scaleDir = scaleDir;
	}
	
	public List<File> scaleCoverPic(){
		String frontcoverScale = "186#248;168#224;132#176;";//ConfigPropertieUtils.getString("frontcover.scale");
		String scaleArr[] = frontcoverScale.split(";");
		 System.setProperty("jmagick.systemclassloader","no"); 
		//用于存放需要压缩的比例
		int scale[][] = new int[scaleArr.length][2];
		for(int i = 0; i<scaleArr.length; i++){
			String ratioArr[] = scaleArr[i].split("#");
			scale[i][0] = Integer.valueOf(ratioArr[0]);
			scale[i][1] = Integer.valueOf(ratioArr[1]);
		}
		List<File> scaleFiles = new ArrayList<File>();
		try {
//			ImageInfo info = new ImageInfo(coverPic.getAbsolutePath());
//			MagickImage img = new MagickImage(info);
			String name = coverPic.getName();
			for(int i=0; i<scale.length; i++){
				scaleFiles.add(cropImageCenter(coverPic.getAbsolutePath(), "cover_"+suffix[i]+name.substring(name.lastIndexOf(".")), scale[i][0], scale[i][1]));
			}
		} catch (Exception e) {
			logger.error("cropImageCenter error : ", e);
		}
		isSuccess = true;
		return scaleFiles;
		
	}
	
	public File cropImageCenter(String srcPath, String desPath, int rectw, int recth) throws Exception {  
		Image srcImage = ImageIO.read(new File(srcPath));  
		BufferedImage distImage = new BufferedImage(rectw, recth, BufferedImage.TYPE_INT_RGB);  
		// 缩放图片  
		distImage.getGraphics().drawImage(srcImage.getScaledInstance(rectw, recth, Image.SCALE_SMOOTH), 0, 0, null);  
	         
		// 输出  
		OutputStream os = null;
		File f = new File(coverPic.getParent(),desPath);
		try {
			os = new FileOutputStream(f);  
			ImageIO.write(distImage, "JPG", os);  
			os.flush();  
		} finally {  
			os.close();  
		}  
        return f;
	}

	
	/*public List<File> scaleCoverPic(){
		String frontcoverScale = "186#248;168#224;132#176;";//ConfigPropertieUtils.getString("frontcover.scale");
		String scaleArr[] = frontcoverScale.split(";");
		 System.setProperty("jmagick.systemclassloader","no"); 
		//用于存放需要压缩的比例
		int scale[][] = new int[scaleArr.length][2];
		for(int i = 0; i<scaleArr.length; i++){
			String ratioArr[] = scaleArr[i].split("#");
			scale[i][0] = Integer.valueOf(ratioArr[0]);
			scale[i][1] = Integer.valueOf(ratioArr[1]);
		}
		List<File> scaleFiles = new ArrayList<File>();
		try {
			ImageInfo info = new ImageInfo(coverPic.getAbsolutePath());
			MagickImage img = new MagickImage(info);
			String name = coverPic.getName();
			for(int i=0; i<scale.length; i++){
				MagickImage scaleImg = null;
				scaleImg = img.scaleImage(scale[i][0], scale[i][1]);
//				scaleImg.profileImage("*", null); //清除无用信息  
//				scaleImg.setImageAttribute("JPEG-Sampling-factors", null); //清除无用信息  
//				scaleImg.setImageAttribute("comment", null); //清除无用信息  
				File file = new File(coverPic.getParent(),"cover_"+suffix[i]+name.substring(name.lastIndexOf(".")));
				String path = file.getAbsolutePath();
				scaleImg.setFileName(path);
				scaleImg.writeImage(new ImageInfo(path));
				scaleImg.destroyImages();
				scaleFiles.add(file);
			}
			img.destroyImages();
		} catch (MagickException e) {
			e.printStackTrace();
			msg = "压缩图片出错!"+e.getMessage();
			System.out.println(msg);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		isSuccess = true;
		return scaleFiles;
	}*/
	
	public static void main(String[] args) throws MagickException {
		long mediaId = 569L;
		File f = new File("D:\\temp" + File.separator+ mediaId + File.separator);
		f = new File(f, mediaId + ".jpg");
		ScaleCoverImgListen s = new ScaleCoverImgListen(f,mediaId);
		System.out.println(s.scaleCoverPic());
		/*ImageInfo info = new ImageInfo("D:\\temp\\epub\\source\\ebookfiles\\39\\83\\140626074026983983\\br.140626074026983983.jpg");
        
		File afterCut = new File("D:\\cms\\1900704210","1900704210_cut.jpg");
		int rect[] = {0,0,200,300};
        MagickImage img = new MagickImage(info);
        MagickImage cut = null;
        cut = img.cropImage(new Rectangle(new Point(rect[0],rect[1]), new Dimension(rect[2], rect[3])));
        
        cut.setFileName(afterCut.getAbsolutePath());
        boolean flag = cut.writeImage(new ImageInfo(afterCut.getAbsolutePath()));
        
        img.destroyImages();
        cut.destroyImages();*/
	}
}
