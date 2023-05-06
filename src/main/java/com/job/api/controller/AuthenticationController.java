package com.job.api.controller;

import com.job.api.domain.dto.AuthRequest;
import com.job.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/auth")
public class AuthenticationController {

	@Autowired
	private AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<Object> login(@RequestBody AuthRequest request) {
		return authService.login(request);
	}
}
