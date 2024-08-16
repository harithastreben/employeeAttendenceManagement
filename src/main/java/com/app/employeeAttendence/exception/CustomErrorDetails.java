package com.app.employeeAttendence.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorDetails {
	private Date timestamp;
	private Integer errorcode;
	private String message;
	private String errordetails;

}