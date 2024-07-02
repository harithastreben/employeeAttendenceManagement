package com.app.employeeAttendence.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomErrorDetails {
	private Date timestamp;
	private Integer errorcode;
	private String message;
	private String errordetails;
	


}