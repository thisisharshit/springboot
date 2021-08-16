package com.harshit.demo.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.harshit.demo.model.Employee;

public interface EmployeeService {
	public List<Employee> findAll();
	
	public Employee findById(int id);
	
	public void save(Employee employee);
	
	public void deleteById(int id);

}
