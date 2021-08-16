package com.harshit.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harshit.demo.model.Employee;
import com.harshit.demo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	@GetMapping("/access-denied")
	public String showAccessDeniedPage() {
		return "access-denied";
	}
	
	@GetMapping("/list")
	public String listEmployees(Model model) {
		List<Employee> employees=employeeService.findAll();
		model.addAttribute("employees", employees);
		return "list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		// Create model attribute to bind the form data
		Employee theEmployee=new Employee();
		
		theModel.addAttribute("employee",theEmployee);
		
		return "employee-form";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int id,
									Model theModel) {
		// get the employee from the service
		Employee theEmployee = employeeService.findById(id);
		
		// set employee as a model attribute to prepopulate the form
		theModel.addAttribute("employee",theEmployee);
		
		//send over to our form
		return "employee-form";
		
	}
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
		// save the employee
		employeeService.save(theEmployee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int id) {
		// delete the employee
		employeeService.deleteById(id);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
	
	
	
}
