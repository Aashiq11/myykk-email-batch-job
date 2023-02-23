/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.myykk.schedulers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myykk.service.AdminEmailService;
import com.myykk.service.InvoiceEmailService;
import com.myykk.service.LoginEmailService;
import com.myykk.service.SupportEmailService;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	AdminEmailService adminEmailService;
	
	@Autowired
	InvoiceEmailService invoiceEmailService;
	
	@Autowired
	LoginEmailService loginEmailService;
	
	@Autowired
	SupportEmailService supportEmailService;

	@Scheduled(cron = "${admin.email.job.time}")
	public void sendAdminEmailJob() throws Exception {
		log.info("Start: Admin email job started time - {}", dateFormat.format(new Date()));
		adminEmailService.sendAdminEmailJob();
		log.info("End: Admin email job end time - {}", dateFormat.format(new Date()));
		
	}
	
	@Scheduled(cron = "${notification.polling.job.time}")
	public void sendNotificationPollingEmailJob() throws Exception {
		log.info("Start: Invoice email job started time - {}", dateFormat.format(new Date()));
		invoiceEmailService.sendInvoiceEmailJob();
		log.info("End: Invoice email job end time - {}", dateFormat.format(new Date()));
		
	}
	
	@Scheduled(cron = "${login.email.job.time}")
	public void sendLoginEmailJob() throws Exception {
		log.info("Start: Login email job started time - {}", dateFormat.format(new Date()));
		loginEmailService.sendLoginEmailJob();
		log.info("End: Login email job end time - {}", dateFormat.format(new Date()));
		
	}
	
	@Scheduled(cron = "${support.email.job.time}")
	public void sendSupportEmailJob() throws Exception {
		log.info("Start: Support email job started time - {}", dateFormat.format(new Date()));
		supportEmailService.sendSupportEmailJob();
		log.info("End: Support email job end time - {}", dateFormat.format(new Date()));
		
	}
	
	@Scheduled(cron = "${batch.email.job.time}")
	public void sendBatchEmailJob() throws Exception {
		log.info("Start: Support email job started time - {}", dateFormat.format(new Date()));
		supportEmailService.sendSupportEmailJob();
		log.info("End: Support email job end time - {}", dateFormat.format(new Date()));
		
	}
}
