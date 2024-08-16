package com.app.employeeAttendence.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.employeeAttendence.dto.EmailRequest;

@FeignClient(name = "emailService", url = "http://localhost:8080")
public interface EmailClient {
	
	@PostMapping("/sendEmail/text")
	ResponseEntity<String> sendEmailText(@RequestBody String message);

	@PostMapping("/sendEmail/template")
	ResponseEntity<String> sendEmailTemplate(@RequestBody String htmlContent);

	@PostMapping("/sendEmail/attachment")
	ResponseEntity<String> sendEmailAttachment(@RequestBody EmailRequest emailRequest);
	

}