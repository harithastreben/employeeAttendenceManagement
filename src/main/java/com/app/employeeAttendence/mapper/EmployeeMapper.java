package com.app.employeeAttendence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.app.employeeAttendence.dto.EmployeeRequest;
import com.app.employeeAttendence.dto.EmployeeResponse;
import com.app.employeeAttendence.entity.Employee;

@Mapper(componentModel="Spring")
public interface EmployeeMapper {

	Employee toEntity(EmployeeRequest empRequest);

	EmployeeResponse toResponse(Employee employee);

	List<EmployeeResponse> toResponseDto(List<Employee> all);




}
