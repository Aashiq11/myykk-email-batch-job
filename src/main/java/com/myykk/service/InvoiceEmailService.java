package com.myykk.service;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.myykk.dao.NotificationRepository;
import com.myykk.model.EmailBean;
import com.myykk.model.EmailDTO;
import com.myykk.utility.EmailUtility;

@Service
public class InvoiceEmailService {

	private static final Logger log = LoggerFactory.getLogger(InvoiceEmailService.class);
	
		
	@Autowired
	NotificationRepository notificationRepository;
	
	
	@Autowired
	EmailUtility emailUtility;
	
	@Value("${email.banner.path}")
	String imagepath;
	
	@Value("${email.fromaddress}")
	String fromAddress;
	
	@Value("${resource.property}")
	String resourceProperty;	
	
	public void setResourceProperty(String resourceProperty) {
		this.resourceProperty = resourceProperty;
	}



	public void sendInvoiceEmailJob() {
		try{
			log.debug("Enter into mailinvoice");			

			List<EmailDTO> emailList = notificationRepository.getEmailListForFuncId("I"); 
			log.debug(" No of Invoice Emails to be sent : " + emailList.size());
			for (EmailDTO emailDTO : emailList) {
				EmailBean emailBean = new EmailBean();
				Locale locale = new Locale("1".equals(emailDTO.getLangflg())?"en":"es");
				
				ResourceBundle bundle = ResourceBundle.getBundle(resourceProperty, locale);
				
				String emailSubject = bundle.getString("invoice.email.subject");
				String invoiceDetails = bundle.getString("invoice.email.details");
				String noteAuthDetails = bundle.getString("login.email.noteAuthDetails");
				String clickhere = bundle.getString("login.email.clickhere");
				String emailDisclaimer = bundle.getString("login.passwordemail.disclaimer");

				String invid[] = emailDTO.getDocnum().trim().split("_");
				String noteInvoiceDetails = bundle.getString("invoice.email.invoiceId1")+ " " + invid[0];

				String globalWeb = bundle.getString("global.web");

				StringBuilder sb = new StringBuilder();
				sb.append("<html><head>");
				sb.append("<META http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>");
				sb.append("</HEAD>");
				sb.append("<BODY marginheight='0' marginwidth='0' leftmargin='0' topmargin='0'>");
				sb.append("<TABLE width='483' border='0' cellspacing='0' cellpadding='0'>");

				sb.append("<TR><TD colspan='3'><img src='" + imagepath
						+ "' width='483' height='83'></TD>");
				sb.append("</TR><TR><TD bgcolor='#FFFFFF'>&nbsp;&nbsp;&nbsp;</TD>");
				sb.append("<TD bgcolor='#FFFFFF'><FONT face='Verdana, Arial, Helvetica, sans-serif' size='-1'>");
				sb.append(noteInvoiceDetails + "<BR/><BR/>");
				sb.append("<BR/><STRONG>" + invoiceDetails + "</STRONG><BR/><BR/>");
				sb.append("<TABLE width='100%' border='0' cellspacing='0' cellpadding='0'><TR><TD valign='top'>");
				sb.append("<FONT face='Verdana, Arial, Helvetica, sans-serif' size='-1'>");

				sb.append(clickhere + "&nbsp;<A href='" + globalWeb + "'>"+ globalWeb + "</A>");

				sb.append("</FONT></td></tr></table>");
				sb.append("<br/><br/><FONT face='Verdana, Arial, Helvetica, sans-serif' size='-2'>");
				sb.append(emailDisclaimer);
				sb.append("<BR/><BR/></FONT><BR/><BR/><BR/><BR/>");
				sb.append("</TD><TD bgcolor='#FFFFFF'>&nbsp;&nbsp;&nbsp;</TD></TR></table></body></html>");

				emailBean.setBodyText(sb.toString());
				emailBean.setToAddress(emailDTO.getEmailid());
				emailBean.setEmailSubjet(emailSubject);
				emailBean.setFromAddress(fromAddress);

				boolean isMailSent = emailUtility.sendInvoiceEmail(emailBean, emailDTO.getDocnum().trim());
				if (isMailSent)
					notificationRepository.updateInvoiceDetailStatus(
							emailDTO.getEmailid(),
							emailDTO.getCustnum(),
							emailDTO.getDocnum(), "1",
							emailDTO.getCreatetime());
					
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

	
}
