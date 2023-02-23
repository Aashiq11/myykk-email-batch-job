package com.myykk.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myykk.dao.NotificationRepository;
import com.myykk.model.EmailDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceEmailServiceTest {
	
	@Mock
	NotificationRepository notificationRepository;
	
	@InjectMocks
	InvoiceEmailService invoiceEmailService;
	
	@Test
	public void sendInvoiceEmailJob() {
		
		List<EmailDTO> emailList = new ArrayList<EmailDTO>();
		EmailDTO email = new EmailDTO();
		email.setEmailid("test@gmail.com");
		email.setCustnum("1");
		email.setDocnum("1");
		email.setLangflg("1");
		emailList.add(email);
		Mockito.when(notificationRepository.getEmailListForFuncId("I")).thenReturn(emailList);
		
		//invoiceEmailService.sendInvoiceEmailJob();
	}

}
