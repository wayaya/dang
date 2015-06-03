package com.dangdang.digital.system;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dangdang.digital.constant.Constans;


/**
 * imge验证码图片
 *
 * @author kaiser
 * @date 2014-11-14
 */

@Controller
@RequestMapping("/captcha")
public class CaptchaImg {
	
    /**
     * 通过文件创建图像
     * 格式为jpg类型
     * */
 	 @RequestMapping(value = "/cimge", method = RequestMethod.GET)
 	 public void service(HttpServletRequest request, HttpServletResponse response){
 		 try {
			creatImage("me.jpg",getContent(),request, response);
		} catch (InterruptedException e) {
			creatImage("me.jpg", "DJ2B",request, response);
			e.printStackTrace();
		}
 	 }
     
     public void creatImage(String fileName,String content, HttpServletRequest request, HttpServletResponse response){
         //在内存中创建图象
       int width=110, height=35;
       BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
       // 获取图形上下文
       Graphics2D g = image.createGraphics();
       //Graphics g=image.getGraphics();
       // 设定背景色
       g.setColor(getRandColor(200,250));
       g.fillRect(0, 0, width, height);
       //设定字体
       g.setFont(new Font("Times New Roman",Font.PLAIN,30));
       g.setColor(Color.black);//黑色文字
       g.drawString(content,24,25);
       g.dispose();
       HttpSession session = request.getSession(true);
       String sessionId = session.getId();
	   session.setAttribute(Constans.XS_SESSIONID, content+ "_" + sessionId);
       try {
    	   ImageIO.write(image, "JPEG", response.getOutputStream());
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
       
     }
     
     /**
      * 返回一个4位的验证码
      * */
     public String getContent() throws InterruptedException{
         String content="";
         for(int i=0;i<4;i++){
             content+=getChar();
             Thread.sleep(new Random().nextInt(10)+10);//休眠以控制字符的重复问题
         }
         return content;
     }
     /**
      * 获取随机字符
      * */
     public char getChar(){
         Random random=new Random();
         char ch='0';
         LinkedList<String> ls=new LinkedList<String>();
         for(int i=0;i<10;i++){//0-9
             ls.add(String.valueOf(48+i));
         }
         for(int i=0;i<26;i++){//A-Z
             ls.add(String.valueOf(65+i));
         }
         for(int i=0;i<26;i++){//a-z
             ls.add(String.valueOf(97+i));
         }
         int index=random.nextInt(ls.size());
         if(index>(ls.size()-1)){
             index=ls.size()-1;
         }
         ch=(char)Integer.parseInt(String.valueOf(ls.get(index)));
         return ch;
     }
     
     public Color getRandColor(int fc,int bc){//给定范围获得随机颜色
       Random random = new Random();
       if(fc>255) fc=255;
       if(bc>255) bc=255;
       int r=fc+random.nextInt(bc-fc);
       int g=fc+random.nextInt(bc-fc);
       int b=fc+random.nextInt(bc-fc);
       return new Color(r,g,b);
     }
     
      /**
       测试
     */
     public static void main(String []args) throws Exception{
//    	 CaptchaImg c=new CaptchaImg();
//         c.creatImage("me.jpg",c.getContent());
     }

	
}
