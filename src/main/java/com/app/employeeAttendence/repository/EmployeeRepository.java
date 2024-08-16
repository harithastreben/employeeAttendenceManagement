package com.app.employeeAttendence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeeAttendence.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByEmployeeName(String employeeName);

	List<Employee> findByEmployeeNameAndIsPresent(String employeeName, Boolean isPresent);
}
