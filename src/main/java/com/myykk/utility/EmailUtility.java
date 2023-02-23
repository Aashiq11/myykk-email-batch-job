package com.myykk.utility;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.myykk.model.BatchEmailBean;
import com.myykk.model.EmailBean;

@Component
public class EmailUtility {
	
	private static final Logger log = LoggerFactory.getLogger(EmailUtility.class);
	
	@Autowired
	private JavaMailSender mailSender;	
	
	private VelocityEngine velocityEngine;
	
	@Value("${dynamic.invoice.pdf.gen.shared.path}")
	String dynInvoicePdfGenSharedPath;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public boolean sendEmail(final EmailBean emailBean,final String pdfFile) {

		boolean isMailSent = true;
		MimeMessagePreparator preparator = new MimeMessagePreparator() {            

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(emailBean.getToAddress());
				if(StringUtils.isNotBlank(emailBean.getFromAddress())) {
					message.setFrom(new InternetAddress(emailBean.getFromAddress()));
				} 
				message.setSubject(emailBean.getEmailSubjet());  
				message.setText(emailBean.getBodyText(),emailBean.isHtmlFormat());
				FileSystemResource file = new FileSystemResource(new File(pdfFile));
				message.addAttachment("Invoice.pdf",file);

			}
		};
		try {
			this.mailSender.send(preparator);
		}
		catch (MailException ex) {
			isMailSent = false;
			log.error("Error While Sending Email " + ex.getMessage());            
		}
		return isMailSent;
	}
	public boolean sendInvoiceEmail(final EmailBean emailBean,final String pdfFile) {

		boolean isMailSent = true;
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				message.setTo(emailBean.getToAddress());
				if(StringUtils.isNotBlank(emailBean.getFromAddress())) {
					message.setFrom(new InternetAddress(emailBean.getFromAddress()));
				} 
				message.setSubject(emailBean.getEmailSubjet());  
				message.setText(emailBean.getBodyText(),emailBean.isHtmlFormat());
				FileSystemResource file = new FileSystemResource(new File(dynInvoicePdfGenSharedPath+"/"+pdfFile));               
				message.addAttachment("Invoice.pdf",file);


			}
		};
		try {
			this.mailSender.send(preparator);
		}
		catch (MailException ex) {
			isMailSent = false;
			log.error("Error While Sending Email for Invoice " + pdfFile + ", with error>>> "+ ex.getMessage());

		} 

		return isMailSent;
	}

	public boolean sendEmail(final EmailBean emailBean) {
		boolean isMailSent = false;
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				// MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailBean.getToAddress()));
				if (StringUtils.isNotBlank(emailBean.getFromAddress())) {
					// mimeMessage.addFrom(InternetAddress.parse(emailBean.getFromAddress()));
					mimeMessage.setFrom(new InternetAddress(emailBean.getFromAddress()));
				}
				mimeMessage.setSubject(emailBean.getEmailSubjet());
				String content = getContent(emailBean);
				mimeMessage.setContent(content, "text/html");
			}
		};
		try {
			this.mailSender.send(preparator);
			isMailSent = true;
		} catch (MailException ex) {
			log.error("Error While Sending Email " + ex.getMessage());
		}
		log.debug("Email sent: " + isMailSent);
		return isMailSent;
	}
	
	public boolean sendEmail(final EmailBean emailBean, String templateName,Map model) {
		
		String text = getContent(templateName, model);
		emailBean.setBodyText(text);
		emailBean.setHtmlFormat(true);		
		return sendEmail(emailBean);
	}
	
	public boolean sendHtmlEmail(EmailBean emailBean) throws AddressException, MessagingException {
		
		boolean isMailSent = false;
		
		MimeMessage message = mailSender.createMimeMessage();

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailBean.getToAddress()));

		if (emailBean.getFromAddress() != null && !emailBean.getFromAddress().equals("")) {
			message.setFrom(new InternetAddress(emailBean.getFromAddress()));
		}
		message.setSubject(emailBean.getEmailSubjet());

		String content = getContent(emailBean);

		message.setContent(content, "text/html; charset=utf-8");

		try {
			mailSender.send(message);
			isMailSent = true;
		} catch (MailException e) {
			log.error("Error While Sending Email " + e.getMessage());
		}
		
		return isMailSent;
	}
	
	private String getContent(EmailBean emailBean) {
		
		return getContent(emailBean.getHtmlTemplate(), emailBean.getData());
		
	}
	
	private String getContent(String templateName, Map model) {
		VelocityContext context = new VelocityContext();
		for (Object obj : model.keySet()) {
              String key = (String) obj;
              Object value =model.get(obj);
              context.put(key, value);
        }
	    
	    StringWriter stringWriter = new StringWriter();
	    velocityEngine.mergeTemplate(templateName, "UTF-8", context, stringWriter);
	    
	    return stringWriter.toString();
	}
	
	public boolean sendBatchEmail(BatchEmailBean batchEmailBean) {


		boolean isMailSent = false;
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.addRecipients(Message.RecipientType.TO,batchEmailBean.getToAddress());
				mimeMessage.addRecipients(Message.RecipientType.CC,batchEmailBean.getCcAddress());
				mimeMessage.addRecipients(Message.RecipientType.BCC,batchEmailBean.getBccAddress());
				
				mimeMessage.addFrom(InternetAddress.parse(batchEmailBean.getFromAddress()));				
				mimeMessage.setSubject(batchEmailBean.getEmailSubjet());
				if(batchEmailBean.isHtmlFormat() ) {
					mimeMessage.setDataHandler(new DataHandler(new ByteArrayDataSource(batchEmailBean.getBodyText().toString(), "text/html")));
				} else {
					mimeMessage.setContent(batchEmailBean.getBodyText(),"text/plain");
				}
				
			}
		};
		try {
			this.mailSender.send(preparator);
			isMailSent = true;
			log.error(" While Sending Email isMailSent=" + isMailSent);
		} catch (MailException ex) {
			isMailSent = false;
			log.error("Error While Sending Email " + ex.getMessage());
		}
		return isMailSent;
	}

}
