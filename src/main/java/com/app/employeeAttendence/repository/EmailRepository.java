package com.app.employeeAttendence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeeAttendence.entity.EmailNotification;
@Repository
public interface EmailRepository  extends JpaRepository<EmailNotification,Long>{

}
