package com.albert.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.albert.dao.DeptRepo;
import com.albert.dao.EmployeeRepo;
import com.albert.model.Department;
import com.albert.model.Employee;


@RestController
public class AdminRestController {
	@Autowired
	EmployeeRepo empRepo;
	@Autowired
	DeptRepo deptRepo;

	@GetMapping("/emp")
	public Iterable<Employee> getAllEmp() {
		return empRepo.findAll();
	}

	@GetMapping("/dept")
	public Iterable<Department> getAllDpt() {
		return deptRepo.findAll();
	}
	
	@GetMapping("/dept/emps/{dept}")
	public List<Employee> getFromDpt(@PathVariable("dept")String dept) {
		return deptRepo.findBydeptName(dept).getEmpList();
	}
	
	@GetMapping("emps/{last}")
	public List<Employee> getByLastName(@PathVariable("last")String last) {
		return empRepo.findAllBylastName(last);
	}

	
	@PostMapping("/emp/create/{deptName}")
	public Employee createEmp(@RequestBody Employee emp, @PathVariable String deptName) {
		Department tempDpt = deptRepo.findBydeptName(deptName);
		if (tempDpt != null) {
			emp.setDept(tempDpt);
			return empRepo.save(emp);
		}
		return new Employee();
	}

	@PostMapping("/dept/create")
	public Department createDpt(@RequestBody Department dept){
		return deptRepo.save(dept);
	}

	@PutMapping("/emp/update/{id}")
	public Employee updateEmp(@RequestBody Employee emp, @PathVariable Long id) {
		Employee e = empRepo.findById(id).orElse(null);
		emp.setEmpId(id);
		emp.setDept(e.getDept());
		return empRepo.save(emp);
	}

	@PutMapping("/dept/update/{id}")
	public Department updateDpt(@RequestBody Department dept, @PathVariable Long id) {
		dept.setDeptId(id);
		return deptRepo.save(dept);
	}
	
	@DeleteMapping("/dept/{id}")
	public String deleteByDeptId(@PathVariable Long id) {
		deptRepo.deleteById(id);
		return ("Deleted Department ID: " + id);
	}
	@DeleteMapping("/emp/{id}")
	public String deleteByEmpId(@PathVariable Long id) {
		empRepo.deleteById(id);
		return ("Deleted Employee ID: " + id);
	}

}
