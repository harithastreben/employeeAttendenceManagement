package com.app.employeeAttendence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmployeeAttendenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeAttendenceApplication.class, args);
	}

}
