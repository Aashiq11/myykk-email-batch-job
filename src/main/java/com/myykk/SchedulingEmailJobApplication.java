package com.myykk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.myykk"})
@PropertySource({"classpath:Queries.xml"})
//@PropertySource({"classpath:TQueries.xml"})
@EnableScheduling
public class SchedulingEmailJobApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingEmailJobApplication.class);
	}
}
