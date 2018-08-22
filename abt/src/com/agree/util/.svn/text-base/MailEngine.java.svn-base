package com.agree.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;





public class MailEngine {
    protected static final Log log = LogFactory.getLog(MailEngine.class);

//  private FreeMarkerConfigurer freeMarkerConfigurer;
    private VelocityEngine velocityEngine;
    private MailSender mailSender;

    private String res_path;
    
    
//    public void setFreeMarkerConfigurer(
//            FreeMarkerConfigurer freeMarkerConfigurer) {
//        this.freeMarkerConfigurer = freeMarkerConfigurer;
//    }

    public String getRes_path() {
		return res_path;
	}

	public void setRes_path(String res_path) {
		this.res_path = res_path;
	}

	public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
	
    public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    /**
     * 通过模板产生邮件正文
     * @param templateName    邮件模板名称
     * @param map            模板中要填充的对象
     * @return 邮件正文（HTML）
     */
    public String generateEmailContent(String templateName, @SuppressWarnings("rawtypes") Map map) {
        //使用FreeMaker模板
//        try {
//            Configuration configuration = freeMarkerConfigurer.getConfiguration();
//            Template t = configuration.getTemplate(templateName);
//            return FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
//        } catch (TemplateException e) {
//            log.error("Error while processing FreeMarker template ", e);
//        } catch (FileNotFoundException e) {
//            //log.error("Error while open template file ", e);
//        } catch (IOException e) {
//            log.error("Error while generate Email Content ", e);
//        }
        
//        使用Vilocity模板
        try {
           return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, map);
        } catch (VelocityException e) {
            log.error("Error while processing Vilocity template ", e);
        }
        
        return null;
    }

  
    /**
     * 发送简单邮件
     * @param msg    
     */
    public void send(SimpleMailMessage msg) {
        try {
            ((JavaMailSenderImpl) mailSender).send(msg);
        } catch (MailException ex) {
            //log it and go on
            log.error(ex.getMessage());
        }
    }
    
    /**
     * 使用模版发送HTML格式的邮件
     *
     * @param msg          装有to,from,subject信息的SimpleMailMessage
     * @param templateName 模版名,模版根路径已在配置文件定义于freemakarengine中
     * @param model        渲染模版所需的数据
     * @param attachmentName 附件名称
     * @param resource     附件路径
     */
    @SuppressWarnings("rawtypes")
	public void send(SimpleMailMessage msg, String templateName, Map model) {
        //生成html邮件内容
        String content = generateEmailContent(templateName, model);
        MimeMessage mimeMsg = null;
        try {
            mimeMsg = ((JavaMailSenderImpl) mailSender).createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
            helper.setTo(msg.getTo());
            
            if(msg.getSubject()!=null)
                helper.setSubject(msg.getSubject());
            
            if(msg.getFrom()!=null)
                helper.setFrom(msg.getFrom());
            
            helper.setText(content, true);
            
           
            
            ((JavaMailSenderImpl) mailSender).send(mimeMsg);
        } catch (MessagingException ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    /**
     * 发送邮件使用模板粘贴附件
     * @param emailAddress        收件人Email地址的数组
     * @param fromEmail            寄件人Email地址, null为默认寄件人web@vnvtrip.com
     * @param bodyText            邮件正文
     * @param subject            邮件主题
     * @param attachmentName    附件名
     * @param resource            附件
     * @throws MessagingException
     */
    public void sendMsgMdlAtt(SimpleMailMessage msg, String templateName, Map<String,String> model,String attachmentName,
            Hashtable<String,File> resource) {
        //生成html邮件内容
        String content = generateEmailContent(templateName, model);
        MimeMessage mimeMsg = null;
        try {
            mimeMsg = ((JavaMailSenderImpl) mailSender).createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
            helper.setTo(msg.getTo());
            
            if(msg.getSubject()!=null)
                helper.setSubject(msg.getSubject());
            
            if(msg.getFrom()!=null)
                helper.setFrom(msg.getFrom());
            
            helper.setText(content, true);
            
           
            if(attachmentName!=null && resource!=null)
            {
            	Enumeration<String> e=resource.keys();
            	     while(e.hasMoreElements()){
            	    	    String key = (String)e.nextElement();
                            File value = (File)resource.get(key);
            	    	 helper.addAttachment(MimeUtility.encodeWord(key), value);
            	     }
            }
            ((JavaMailSenderImpl) mailSender).send(mimeMsg);
            log.info("成功发送邮件 :" +msg.getFrom()+"->"+msg.getTo()[0]);
        } catch (MessagingException ex) {
            log.error(ex.getMessage(), ex);
        }
        catch (UnsupportedEncodingException eu){
           log.error(eu.getMessage(),eu);
        }
    }
  
}
