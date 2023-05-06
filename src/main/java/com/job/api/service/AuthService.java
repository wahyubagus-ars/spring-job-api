package com.job.api.service;

import com.job.api.config.JwtTokenUtil;
import com.job.api.domain.dao.User;
import com.job.api.domain.dto.ApiResponse;
import com.job.api.domain.dto.AuthRequest;
import com.job.api.domain.dto.AuthResponse;
import com.job.api.exception.AppException;
import com.job.api.repository.UserRepository;
import com.job.api.util.ResponseUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.job.api.constant.AppConstant.ResponseKey.SUCCESS;
import static com.job.api.constant.AppConstant.ResponseKey.UNKNOWN_ERROR;
import static com.job.api.constant.AppConstant.ResponseMessage.SUCCESS_MSG;
import static com.job.api.constant.AppConstant.ResponseMessage.UNKNOWN_ERROR_MSG;

@Service
@Slf4j
public class AuthService implements UserDetailsService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		try {
			Optional<User> user = userRepository.findByUsername(username);

			if (user.isEmpty()) {
				throw new UsernameNotFoundException("USER_NOT_FOUND");
			}

			return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
					user.get().getPassword(),
					new ArrayList<>());
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw new AppException(UNKNOWN_ERROR, UNKNOWN_ERROR_MSG);
		}
	}

	@SneakyThrows
	public ResponseEntity<Object> login(AuthRequest request) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			final UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);

			return ResponseUtils.buildResponse(SUCCESS, SUCCESS_MSG, new AuthResponse(token), HttpStatus.OK);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}