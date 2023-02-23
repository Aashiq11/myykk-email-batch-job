package com.myykk.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.myykk.dao.BatchEmailRepository;
import com.myykk.dao.NotificationRepository;
import com.myykk.model.BatchEmailBean;
import com.myykk.model.BatchEmailDTO;
import com.myykk.utility.EmailUtility;
import com.myykk.utility.Util;
import com.myykk.utility.YKKConstants;

@Service
public class BatchEmailService {

	private static final Logger log = LoggerFactory.getLogger(BatchEmailService.class);
	
		
	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	BatchEmailRepository batchEmailRepository;
	
	
	@Autowired
	EmailUtility emailUtility;
	
		
	@Value("${email.fromaddress}")
	String fromAddress;
	
	public void sendBatchEmailJob() {
		
		StringBuffer body = new StringBuffer();
		try{
			log.debug("Enter into mailinvoice");			

			List<BatchEmailDTO> batchEmailList = batchEmailRepository.getBatchEmailList();
			log.debug(" No of Header Records Found : " + batchEmailList.size());
			for (BatchEmailDTO batchEmail : batchEmailList) {
				List<String> toAddrList = new ArrayList<String>();
				BatchEmailBean batchEmailBean = new BatchEmailBean();				
				boolean isValidToAddr = Util.isValidEmailAddress(batchEmail.getTo());				
				if(!isValidToAddr) {
					batchEmail.setStatus(-2);
	            	batchEmail.setSentDateTime(new java.util.Date());
	            	log.error("No valid address found for email : " + batchEmail.getExternalEmailSource()+ " , " 
	            			+ batchEmail.getExternalEmailID());
	            	batchEmailRepository.updateHeaderStatus(batchEmail);
	            	continue;
				}
				toAddrList.add(batchEmail.getTo());							
				//Get Body Details
				body.setLength(0);
	            boolean first = true;   
			
				List<BatchEmailDTO> bodyList = 
						batchEmailRepository.getBodyListForHdr(batchEmail.getExternalEmailSource(), batchEmail.getExternalEmailID());
		            for(BatchEmailDTO bodyRecord : bodyList) {
		            	if(!first) {
		            		if(bodyRecord.getParagraphFlag() == 0) {
		            			body.append("\n");
		            		} else {
		            			body.append(" ");
		            		}
		            	}
		            	first = false;
		            	if(bodyRecord.getParagraphFlag() == 0) {
		            		body.append(Util.rtrim(bodyRecord.getEmailText()));
		            	} else {
		            		body.append(StringUtils.trimToEmpty(bodyRecord.getEmailText()));
		            	}
		            }	            
		            
		            try {
		            	int toCount = toAddrList.size();
		            	InternetAddress[] toAddrs = new InternetAddress[toCount];
		            	for (int i = 0; i < toCount; ++i) {
		            		toAddrs[i] = new InternetAddress((String) toAddrList.get(i));
		            	}
	
		            	batchEmailBean.setToAddress(toAddrs);		                
		            	batchEmailBean.setFromAddress(batchEmail.getFrom());
		            	batchEmailBean.setEmailSubjet(batchEmail.getSubject());
		            	if(batchEmail.getBodyType() == 1) {
		            		batchEmailBean.setHtmlFormat(true);
		            	}
		            	batchEmailBean.setBodyText(body.toString());
		            	boolean mailStatus = emailUtility.sendBatchEmail(batchEmailBean);
		            	if(mailStatus) {
		            		batchEmail.setStatus(1); //EMAIL_STATUS_SENT
		            	} else {
		            		batchEmail.setStatus(-5); //EMAIL_STATUS_ERROR
		            	}
		            } catch(AddressException aee) {
		            	log.error("Error for Email Header : " + batchEmail.getExternalEmailID());
		            	batchEmail.setStatus(-5); //EMAIL_STATUS_ERROR
		            	batchEmailRepository.updateHeaderStatus(batchEmail);
		            }
		            batchEmailRepository.updateHeaderStatus(batchEmail);
			}
	            
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}

	
}
