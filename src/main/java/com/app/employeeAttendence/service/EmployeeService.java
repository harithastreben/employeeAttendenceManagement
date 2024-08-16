package com.app.employeeAttendence.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.employeeAttendence.dto.EmailRequest;
import com.app.employeeAttendence.dto.EmployeeRequest;
import com.app.employeeAttendence.dto.EmployeeResponse;
import com.app.employeeAttendence.entity.EmailNotification;
import com.app.employeeAttendence.entity.Employee;
import com.app.employeeAttendence.exception.EmployeeNotFoundException;
import com.app.employeeAttendence.feignClient.EmailClient;
import com.app.employeeAttendence.mapper.EmployeeMapper;
import com.app.employeeAttendence.repository.EmailRepository;
import com.app.employeeAttendence.repository.EmployeeRepository;

import jakarta.validation.constraints.Min;

@Service

public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepo;
	@Autowired
	private EmployeeMapper empMapper;
	@Autowired
	private EmailClient emailClient;
	@Autowired
	private EmailRepository emailRepository;

// send email with text message

	public EmployeeResponse EmployeeAttendence(EmployeeRequest empRequest) {
		Employee employee = empMapper.toEntity(empRequest);
		empRepo.save(employee);
		if (!employee.getIsPresent()) {
			sendAbsentNotificationMessage(employee);
		}
		return empMapper.toResponse(employee);
	}

	private void sendAbsentNotificationMessage(Employee employee) {
		String message = employee.getEmployeeName() + " is absent today";
		emailClient.sendEmailText(message);
	}

// list all employee 

	public List<EmployeeResponse> getAllEmployee() {
		return empMapper.toResponseDto(empRepo.findAll());
	}

// get employee by id

	public EmployeeResponse getEmployeeById(@PathVariable @Min(1) Long id) throws EmployeeNotFoundException {
		Optional<Employee> employeeOptional = empRepo.findById(id);
		if (!employeeOptional.isPresent()) {
			throw new EmployeeNotFoundException("employee of that id isn't available");
		}
		Employee employee = employeeOptional.get();
		return empMapper.toResponse(employee);
	}
//filter by employee name	

	public List<EmployeeResponse> findByEmployeeName(@PathVariable String employeeName) {
		return empMapper.toResponseDto(empRepo.findByEmployeeName(employeeName));
	}

//filter by name and is present
	public List<EmployeeResponse> findByEmployeeNameandIsPresent(String employeeName, Boolean isPresent) {
		return empMapper.toResponseDto(empRepo.findByEmployeeNameAndIsPresent(employeeName, isPresent));
	}

// pagination and sorting	
	public List<EmployeeResponse> getAllEmployee(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Page<Employee> allEmployees = empRepo.findAll(PageRequest.of(pageNo, pageSize, sort));
		return empMapper.toResponseDto(allEmployees.getContent());
	}

// email scheduler

	public EmployeeResponse saveEmployeeAttendence(EmployeeRequest empRequest) {
		Employee employee = empMapper.toEntity(empRequest);
		empRepo.save(employee);
		if (!employee.getIsPresent()) {
			EmailNotification notification = createEmailNotification(employee);
			emailRepository.save(notification);
		}
		return empMapper.toResponse(employee);
	}

	private EmailNotification createEmailNotification(Employee employee) {
		EmailNotification notification = new EmailNotification();
		notification.setRecipient("haritha@strebentechnik.com"); // Assuming email field is in Employee entity
		notification.setSubject("Attendance Notification");
		notification.setBody("Employee " + employee.getEmployeeName() + " is absent today.");
		notification.setIssend(false); // Default value

		return notification;
	}

//	send mail with attachment

	public EmployeeResponse saveEmployee(EmployeeRequest empRequest) {
		Employee employee = empMapper.toEntity(empRequest);
		empRepo.save(employee);
		if (!employee.getIsPresent()) {
			sendAbsentNotificationWithAttachment(employee);
		}
		return empMapper.toResponse(employee);
	}

	private void sendAbsentNotificationWithAttachment(Employee employee) {
		try {
			// Load HTML template
			String template = new String(
					Files.readAllBytes(new ClassPathResource("templates/absent_notification.html").getFile().toPath()));

			String htmlContent = template.replace("{{ employeeName }}", employee.getEmployeeName());

			// Paths for the logo and attachment
			String logoPath = new ClassPathResource("static/logo.png").getFile().getAbsolutePath();
			String attachmentPath = new ClassPathResource("static/attachment.pdf").getFile().getAbsolutePath();

			// Create an email request object
			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setHtmlContent(htmlContent);
			emailRequest.setLogoPath(logoPath);
			emailRequest.setAttachmentPath(attachmentPath);

			// Send email with HTML content, logo, and attachment
			emailClient.sendEmailAttachment(emailRequest);
		} catch (IOException e) {
			// Handle exception
			e.printStackTrace();
		}

	}

//  email with html template	

	public EmployeeResponse saveEmployeeDetails(EmployeeRequest empRequest) {
		Employee employee = empMapper.toEntity(empRequest);
		empRepo.save(employee);
		if (!employee.getIsPresent()) {
			sendAbsentNotificationWithTemplate(employee);
		}
		return empMapper.toResponse(employee);
	}

	private void sendAbsentNotificationWithTemplate(Employee employee) {
		try {
			// Load HTML template
			String template = new String(
					Files.readAllBytes(new ClassPathResource("templates/notification.html").getFile().toPath()));

			String htmlContent = template.replace("{{ employeeName }}", employee.getEmployeeName());
			// Send email with HTML content
			emailClient.sendEmailTemplate(htmlContent);
		} catch (IOException e) {
			// Handle exception
			e.printStackTrace();
		}
	}

}
