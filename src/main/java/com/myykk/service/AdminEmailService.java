package com.myykk.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.myykk.dao.NotificationRepository;
import com.myykk.dao.UserAuthenticationRepository;
import com.myykk.model.EmailBean;
import com.myykk.model.EmailDTO;
import com.myykk.model.UserDetailsDTO;
import com.myykk.utility.EmailUtility;
import com.myykk.utility.StringEncrypter;

@Service
public class AdminEmailService {

	private static final Logger log = LoggerFactory.getLogger(AdminEmailService.class);
	
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	UserAuthenticationRepository userAuthenticationRepository;
	
	@Autowired
	EmailUtility emailUtility;
	
	@Value("${email.banner.path}")
	String imagepath;
	
	@Value("${email.fromaddress}")
	String fromAddress;

	public void sendAdminEmailJob() {

		try {
			List<EmailDTO> emailList = notificationRepository.getEmailListForFuncId("A");
			
			for (EmailDTO emailDTO : emailList) {
				
				UserDetailsDTO userDetails = userAuthenticationRepository.getUserDetailsForEmailId(emailDTO.getEmailid());
				EmailBean emailBean = new EmailBean();
				String tempPwd = "";
				if (userDetails != null) {
					
			        tempPwd = userDetails.getPassword();
			        // getting padded special character from encrypted PWD and
			        // creating instance to the StringEncrypter with encrypted key
			        // which is mapped to the special character
			        char key = tempPwd.charAt(0);
			        StringEncrypter stringEncrypt = StringEncrypter.getInstance(String.valueOf(key));
			        tempPwd = stringEncrypt.decrypt((tempPwd.substring(1, tempPwd.length() - 1)));

			       // String imagepath = com.myykk.constants.YKKConstants.EMAIL_BANNER_PATH;
			        String salutation = "";
			        
			       
			        if ("1".equals( emailDTO.getLangflg())) {
			            emailBean.setEmailSubjet("Password for myYKK site ");
			            emailBean.setHtmlTemplate("admin.html");
			            salutation = "Dear";
			        } else {
			            emailBean
			                    .setEmailSubjet("Contrasena para la pagina de myYKK ");
			            emailBean.setHtmlTemplate("admin_es.html");
			            salutation = "Estimado";
			        }
			        Map<String, String> data = new HashMap<String, String>();
			        data.put("imagepath", imagepath);
			        data.put("tempPwd", tempPwd);

			        if (userDetails.getFirstName() != null
			                && !"null".equals(userDetails.getFirstName())) {
			            salutation = salutation + " " + userDetails.getFirstName();

			        }
			        if (userDetails.getLastName() != null
			                && !"null".equals(userDetails.getLastName())) {
			            salutation = salutation + " " + userDetails.getLastName();
			        }
			        if ("Dear".equals(salutation)) {
			            salutation = "Dear User";
			        }
			        if ("Estimado".equals(salutation)) {
			            salutation = "Estimado usuario";
			        }

			        data.put("salutation", salutation);

			        data.put("emailId", emailDTO.getEmailid());

			        emailBean.setData(data);
			        emailBean.setToAddress(emailDTO.getEmailid());
			        emailBean.setFromAddress(fromAddress);

			        boolean isMailSent = emailUtility.sendHtmlEmail(emailBean);
			        if (isMailSent) {                    	
			        	notificationRepository.updateMailStatus(emailDTO.getEmailid(), "1", emailDTO.getCreatetime(), new Timestamp(System.currentTimeMillis()));
			        }
			    } else {
			    	 //set status flag to 2 in webemailf for this userid
			    	notificationRepository.updateMailStatus(emailDTO.getEmailid(), "2", emailDTO.getCreatetime(), new Timestamp(System.currentTimeMillis()));
			        log.debug("No record found for '" + emailDTO.getEmailid() + "' in VBS65V4");
			    }
			}
		} catch (Exception e) {
			log.error("Exception while sending emails " + e.getMessage());
		}
		
	}

	
}
