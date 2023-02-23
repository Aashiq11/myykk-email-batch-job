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
import com.myykk.dao.UserAuthenticationRepository;
import com.myykk.model.EmailDTO;
import com.myykk.model.UserDetailsDTO;
import com.myykk.utility.EmailUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminEmailServiceTest {
	
	@Mock
	NotificationRepository notificationRepository;
	
	@Mock
	UserAuthenticationRepository userAuthenticationRepository;
	
	@Mock
	EmailUtility emailUtility;
	
	@InjectMocks
	AdminEmailService adminEmailService;
	
	@Test
	public void sendAdminEmailJob() {
		List<EmailDTO> emailList = new ArrayList<EmailDTO>();
		EmailDTO email = new EmailDTO();
		email.setEmailid("test@gmail.com");
		email.setCustnum("1");
		email.setDocnum("1");
		email.setLangflg("1");
		emailList.add(email);
		Mockito.when(notificationRepository.getEmailListForFuncId("A")).thenReturn(emailList);
		
		UserDetailsDTO userDetails = new UserDetailsDTO();
		userDetails.setFirstName("Test1");
		
		
		Mockito.when(userAuthenticationRepository.getUserDetailsForEmailId(email.getEmailid())).thenReturn(userDetails);
		 
		//adminEmailService.sendAdminEmailJob();
	}

}
