package com.myykk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
public class SupportEmailService {

	private static final Logger log = LoggerFactory.getLogger(SupportEmailService.class);
	
		
	@Autowired
	NotificationRepository notificationRepository;
	
	
	@Autowired
	EmailUtility emailUtility;
	
	@Value("${email.banner.path}")
	String imagepath;
	
	@Value("${vmtemplate.path}")
	String vmTemplatePath;
	
	@Value("${email.fromaddress}")
	String fromAddress;
	
	public void sendSupportEmailJob() {
		try{
			log.debug("Enter into mailinvoice");			

			List<EmailDTO> emailList = notificationRepository.getEmailListForFuncId("S"); 
			log.debug(" No of Support Emails to be sent : " + emailList.size());
			for (EmailDTO emailDTO : emailList) {
				EmailBean emailBean = new EmailBean();
				
				emailBean.setToAddress(emailDTO.getEmailid().trim());
				emailBean.setFromAddress(fromAddress);
				
				if ("1".equals( emailDTO.getLangflg())) {
		            emailBean.setEmailSubjet("Your Incident No is : " + emailDTO.getDocnum().trim());
		            emailBean.setHtmlTemplate(vmTemplatePath + "/support.vm");
		        } else {
		            emailBean
		                    .setEmailSubjet("Su numero de incidente es: " + emailDTO.getDocnum().trim());
		            emailBean.setHtmlTemplate(vmTemplatePath + "/support_es.vm");
		        }
				
				Map<String, String> data = new HashMap<String, String>();
				
				Map<String, String> isu = notificationRepository.getIssueDetails(emailDTO.getDocnum().trim());

				data.put("incidentNo", emailDTO.getDocnum().trim());
				data.put("IssueCategory", isu.get("iscatgory"));
				data.put("IssueDetail", isu.get("isdetail"));
				data.put("phone", isu.get("prvstatus"));
				data.put("imagepath", imagepath);
				
				for (EmailDTO email : emailList) {
					data.put("createtime", email.getCreatetime());
					data.put("custnum", email.getCustnum());
					data.put("docnum", email.getDocnum());
					data.put("emailid", email.getEmailid());
					data.put("langflg", email.getLangflg());
				}

				emailBean.setData(data);
				 
				emailUtility.sendHtmlEmail(emailBean);
			
				emailBean.setToAddress(fromAddress);

				boolean isMailSent = emailUtility.sendHtmlEmail(emailBean);
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
