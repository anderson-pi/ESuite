package com.albert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.albert.config.JwtTokenUtil;
import com.albert.model.DTOUserLogin;
import com.albert.model.Employee;
import com.albert.model.JwtRequest;
import com.albert.model.JwtResponse;
import com.albert.service.JwtUserDetailsService;
import com.albert.service.EmployeeMailSender;

@RestController
@CrossOrigin
public class JwtController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private EmployeeMailSender send;
	
	@PostMapping("/auth")
	public ResponseEntity<?> authEmpJwt(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUserName(), authenticationRequest.getPassWord());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		if (userDetails != null) {
			final String token = jwtTokenUtil.generateToken(userDetails);
			return ResponseEntity.ok(new JwtResponse(token));
		}
		return (ResponseEntity<?>) ResponseEntity.badRequest();
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> regEmpJwt(@RequestBody DTOUserLogin authenticationRequest) throws Exception {
		Employee emp = userDetailsService.save(authenticationRequest);
		return ResponseEntity.ok(send.sendingMail(emp.getUserLogin().getUserName(),"Welcome on-Board " + emp.getFirstName().toUpperCase() + " " +
				emp.getLastName().toUpperCase(),"Welcome to on-Board into E-office Corp.\n" + 
						"Welcome " + emp.getFirstName().toUpperCase() +", your details as below:\n\n" +
						"EmpId: " + emp.getEmpId() +"\n"+
						"LoginId: "+ emp.getUserLogin().getUserName()+ "\n" +
						"Password: "+ authenticationRequest.getPassWord() + 
						"\n\nRegards\n" + "Admin Team\n" + "E-office Corp.\n"));
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
