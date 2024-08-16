package com.app.employeeAttendence.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.employeeAttendence.dto.EmployeeRequest;
import com.app.employeeAttendence.dto.EmployeeResponse;
import com.app.employeeAttendence.exception.EmployeeNotFoundException;
import com.app.employeeAttendence.service.EmployeeService;

import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService empService;

// send email using  scheduler

	@PostMapping("/email/scheduler")
	public ResponseEntity<EmployeeResponse> saveEmployeeAttendence(@RequestBody EmployeeRequest empRequest) {
		return new ResponseEntity<>(empService.saveEmployeeAttendence(empRequest), HttpStatus.CREATED);
	}

//	send mail with attachment	

	@PostMapping("/email/attachment")
	public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody EmployeeRequest empRequest) {
		return new ResponseEntity<>(empService.saveEmployee(empRequest), HttpStatus.CREATED);
	}

// send email with text message	

	@PostMapping("/email/text")
	public ResponseEntity<EmployeeResponse> Employeeattendence(@RequestBody EmployeeRequest empRequest) {
		return new ResponseEntity<>(empService.EmployeeAttendence(empRequest), HttpStatus.CREATED);
	}

//  email with html template

	@PostMapping("/email/template")
	public ResponseEntity<EmployeeResponse> saveEmployeeDetails(@RequestBody EmployeeRequest empRequest) {
		return new ResponseEntity<>(empService.saveEmployeeDetails(empRequest), HttpStatus.CREATED);
	}

// list all employees

	@GetMapping("/all")
	public ResponseEntity<List<EmployeeResponse>> getAllEmployee() {
		return new ResponseEntity<>(empService.getAllEmployee(), HttpStatus.OK);
	}

// get employee by id

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable("id") @Min(1) Long id)
			throws EmployeeNotFoundException {
		return new ResponseEntity<>(empService.getEmployeeById(id), HttpStatus.OK);
	}

//	filter employee by name

	@GetMapping("/name/{name}")
	public ResponseEntity<List<EmployeeResponse>> getEmployeeByName(@PathVariable("name") String employeeName)
			throws EmployeeNotFoundException {
		return new ResponseEntity<>(empService.findByEmployeeName(employeeName), HttpStatus.OK);
	}

//filter employee by name and isPresent	

	@GetMapping("/find")
	public ResponseEntity<List<EmployeeResponse>> findByEmployeeNameandIsPresent(@RequestParam String name,
			@RequestParam Boolean present) throws EmployeeNotFoundException {
		return new ResponseEntity<>(empService.findByEmployeeNameandIsPresent(name, present), HttpStatus.OK);
	}

//pagination and sorting	

	@GetMapping("/pages")
	public ResponseEntity<List<EmployeeResponse>> getAllEmployee(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {

		return new ResponseEntity<>(empService.getAllEmployee(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
	}

}
