package com.app.employeeAttendence.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
	private Long id;

	private String employeeName;

	private Date date;

	private Boolean isPresent;
}
