package com.dangdang.digital.utils;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailManager {
    
    private static Logger log = LoggerFactory.getLogger(EmailManager.class);
    
    private static final int THREAD_POOL_KEEP_ALIVE_TIME = 300;

	private static final int THREAD_POOL_SIZE = 10;
	
	private static ThreadPoolExecutor emailSendThreadPool = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_SIZE,
			THREAD_POOL_KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
			new CallerRunsPolicy());
	private static EmailManager instance = new EmailManager();
	private EmailManager(){}
	public static EmailManager getInstance(){
		return instance;
	}
    
	public void sendMail(File reportFile, String fileName, String subject, String message,
			String[] toList, String[] ccList, String[] bccList) throws Exception {
	    try {
	    	
    	    String FROM = ConfigPropertieUtils.getString("from", "");
    	    String HOST_NAME = ConfigPropertieUtils.getString("hostname", "");
    	    int SMTP_PORT = Integer.parseInt(ConfigPropertieUtils.getString("smtpport", ""));
    	    String USER_NAME = ConfigPropertieUtils.getString("username", "");
    	    String PASSWORD = ConfigPropertieUtils.getString("password", "");
	        
	        MultiPartEmail email = new MultiPartEmail();
	        email.setCharset("UTF-8");
	        email.setHostName(HOST_NAME);
	        email.setSmtpPort(SMTP_PORT);
	        email.setAuthentication(USER_NAME, PASSWORD);
	        email.setFrom(FROM);
	        if (toList != null) {
	            for (String to : toList) {
	                email.addTo(to);
	            }
	        }
	        if (ccList != null) {
	            for (String cc : ccList) {
	                email.addCc(cc);
	            }
	        }
	        if (bccList != null) {
	            for (String bcc : bccList) {
	                email.addBcc(bcc);
	            }
	        }            
	        email.setSubject(subject);
	        if(reportFile != null){
		        EmailAttachment attachment = new EmailAttachment();
		        attachment.setPath(reportFile.getAbsolutePath());
		        attachment.setName(fileName);
		        email.attach(attachment);
	        }
	        email.setMsg(message);
	        
	        EmailSendExecuter executer = new EmailSendExecuter(email);
	        emailSendThreadPool.execute(executer);
	        
	    } catch (EmailException e) {
	        log.error("邮件发送失败", e);
	        throw e;
	    } catch (Exception e) {
	        log.error("邮件异常", e);
	        throw e;
	    }
	}
	
	public class EmailSendExecuter implements Runnable{
		
		MultiPartEmail email;
		
		public EmailSendExecuter(MultiPartEmail email){
			this.email= email;
		}
		
		@Override
		public void run() {
			if(this.email == null){
				return;
			}
			try{
				email.send();
			} catch (EmailException e) {
		        log.error("邮件发送失败", e);
		    } catch (Exception e) {
		        log.error("邮件异常", e);
		    }
		}
		
	}
	
}