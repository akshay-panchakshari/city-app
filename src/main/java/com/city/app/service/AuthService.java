package com.city.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.city.app.model.User;

@Service
public class AuthService {
	
	@RequestMapping("/login")
	public boolean login(User user) {
		return user.getUsername().equals("user") && user.getPassword().equals("password");
	} 
}
