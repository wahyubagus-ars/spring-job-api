package com.job.api.controller;

import com.job.api.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/job")
public class JobController {

	@Autowired
	private JobService jobService;

	@GetMapping(value = "/")
	public ResponseEntity<? extends Object> getJobList() {
		return jobService.getJobList();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<? extends Object> getJobList(@PathVariable(name = "id") String id) {
		return jobService.getJobById(id);
	}

}
