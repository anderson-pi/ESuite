package com.albert.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.albert.dao.EmployeeRepo;
import com.albert.dao.UserLoginRepo;
import com.albert.model.DTOUserLogin;
import com.albert.model.Employee;
import com.albert.model.UserLogin;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserLoginRepo userRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	EmployeeRepo empRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserLogin emp = userRepo.findByuserName(username);
		if (emp == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(emp.getUserName(), emp.getPassWord(),
				new ArrayList<>());
	}

	public Employee save(DTOUserLogin user, Long id) {
		Employee tempEmp = empRepo.findById(id).orElse(null);
		if (tempEmp != null && tempEmp.getUserLogin() == null) {
			UserLogin newUser = new UserLogin();
			newUser.setUserName(user.getUserName());
			newUser.setPassWord(bcryptEncoder.encode(user.getPassWord()));
			newUser.setRole(user.getRole());
			tempEmp.setUserLogin(newUser);
			userRepo.save(newUser);
			return empRepo.save(tempEmp);
		}
		return null;
	}
}