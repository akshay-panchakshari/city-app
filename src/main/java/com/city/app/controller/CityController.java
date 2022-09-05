package com.city.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.city.app.model.City;
import com.city.app.model.User;
import com.city.app.service.CityService;

@RestController
public class CityController {

	@Autowired
	CityService cityService;

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/api/cities")
	public ResponseEntity<Page<City>> getCities(@RequestParam Optional<String> name,
			@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {
		
		Page<City> cities = cityService.getCities(name.orElse(""), page.orElse(0), size.orElse(10));
		return new ResponseEntity<>(cities, HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/api/cities/{id}")
	public ResponseEntity<City> updateCity(@RequestBody  City city,@PathVariable Long id){
		City updatedCity = cityService.updateCity(city,id);
		return ResponseEntity.ok(updatedCity);
	}
	
	@PostMapping("/api/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public User login(@RequestBody  User user) {
		if(user.getUsername().equals("user") && user.getPassword().equals("user")) {
			return new User(user.getUsername(),user.getPassword(),"ROLE_ALLOW_EDIT");
		}else {
			return new User(user.getUsername(),user.getPassword(),"ROLE_READ_ONLY");
		}
	} 
}
