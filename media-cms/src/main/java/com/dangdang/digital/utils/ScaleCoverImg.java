package com.dangdang.digital.utils;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import magick.CompositeOperator;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;


public class ScaleCoverImg {
	
	private boolean isSuccess = false;
	private String msg;
	private File coverPic;
	private File scaleDir;
	private float ratio = 0;  //宽高比
	private String uid;
	private long productId;
	private DecimalFormat df = new DecimalFormat("0.00");
	private String suffix[] = {"d","h","k"};
	
	public ScaleCoverImg(File coverPic,
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

	public static void markImageByIcon(String iconPath, String srcImgPath,   
            String targerPath,int x,int y) {   
        OutputStream os = null;   
        try {   
            Image srcImg = ImageIO.read(new File(srcImgPath));   
  
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),   
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
  
            // 得到画笔对象   
            // Graphics g= buffImg.getGraphics();   
            Graphics2D g = buffImg.createGraphics();   
  
            // 设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);    
  
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度   
            ImageIcon imgIcon = new ImageIcon(iconPath);   
  
            // 得到Image对象。   
            Image img = imgIcon.getImage();   
  
            float alpha = 1.0f; // 透明度   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,   
                    alpha));   
  
            // 表示水印图片的位置   
            g.drawImage(img, x, y, null);   
  
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));   
  
            g.dispose();   
  
            os = new FileOutputStream(targerPath);   
  
            // 生成图片   
            ImageIO.write(buffImg, "JPG", os);   
  
            System.out.println("图片完成添加Icon印章。。。。。。");   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
}
	public List<File> scaleCoverPic(){
		String frontcoverScale = "186#248;168#224;";//ConfigPropertieUtils.getString("frontcover.scale");
		String scaleArr[] = frontcoverScale.split(";");
		String syImgPathArr[] = null;
		try {
			syImgPathArr = new String[]{
					new ClassPathResource("sign/sy186X248.png").getFile().getPath()
					,new ClassPathResource("sign/sy168X224.png").getFile().getPath()
					};
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
				File file = new File(coverPic.getParent(),"scale_cover_"+suffix[i]+name.substring(name.lastIndexOf(".")));
				if(file.exists()){
					file.delete();
				}
				
				String path = file.getAbsolutePath();
				scaleImg.setFileName(path);
				scaleImg.writeImage(new ImageInfo(path));
				scaleImg.destroyImages();
				
				MagickImage mask = new MagickImage(new ImageInfo(syImgPathArr[i]));  
				Dimension dim = mask.getDimension();
				
				
				MagickImage maskImage = new MagickImage(new ImageInfo(path));
				
				System.out.println(new File(syImgPathArr[i]).exists()+"######"+syImgPathArr[i]);
				System.out.println(path);
				System.out.println(scale[i][0]-((int)dim.getWidth())-6);
				System.out.println(scale[i][1]-((int)dim.getHeight())-6);
				maskImage.compositeImage(CompositeOperator.AtopCompositeOp, mask, 
						scale[i][0]-((int)dim.getWidth())-6, scale[i][1]-((int)dim.getHeight())-6);  
		
				File maskFile = new File(file.getParent(),"cover_"+suffix[i]+name.substring(name.lastIndexOf(".")));
				if(!maskFile.exists()){
					maskFile.createNewFile();
				}
				
				ImageInfo maskInfo = new ImageInfo(maskFile.getAbsolutePath());
				
				maskImage.setFileName(maskFile.getAbsolutePath());  
				maskImage.writeImage(maskInfo);  
				
				//FileUtils.copyFile(maskFile, file);
				file.delete();
				mask.destroyImages();
				maskImage.destroyImages();
				scaleFiles.add(maskFile);
			}
			img.destroyImages();
		} catch (Exception e) {
			e.printStackTrace();
			msg = "压缩图片出错!"+e.getMessage();
			return null;
		}
		isSuccess = true;
		return scaleFiles;
	}
	
	public static void main(String[] args) throws MagickException {
			ImageInfo info = new ImageInfo("/usr/local/mediajob/pic/11.jpg");
			MagickImage img = new MagickImage(info);
		
			MagickImage scaleImg = null;
			scaleImg = img.scaleImage(186, 248);
			File file = new File("/usr/local/mediajob/pic/sy.jpg");
			String path = file.getAbsolutePath();
			scaleImg.setFileName(path);
			scaleImg.writeImage(new ImageInfo(path));
			
			
			MagickImage mask = new MagickImage(new ImageInfo("/usr/local/mediajob/pic/jj.png"));  
	    
			scaleImg.compositeImage(CompositeOperator.AtopCompositeOp, mask, 90, 222);  
			scaleImg.setFileName("/usr/local/mediajob/pic/sy.jpg");  
			scaleImg.writeImage(info);  
			scaleImg.destroyImages();
			
			mask.destroyImages();
			img.destroyImages();
	}
}
