package com.cspro.authenticationapp.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cspro.authenticationapp.model.AuthenticationRequest;
import com.cspro.authenticationapp.model.AuthenticationResponse;
import com.cspro.authenticationapp.utils.JwtUtils;

@RestController
public class AuthenticationController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	
	
	@GetMapping(value = "/hello")
	public ResponseEntity<Map<String, String>> helloworld(HttpServletRequest request) {
		Map<String, String> maps = new HashMap<>();
		maps.put("name", jwtUtils.extractUsername(request.getHeader("Authorization").substring(7)));
		return new ResponseEntity<Map<String,String>>(maps, HttpStatus.OK);
	}


	@PostMapping(value = "/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {
		String jwt=null;
		String username = authenticationRequest.getUsername();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			logger.info("Auth successfull for user "+ authenticationRequest.getUsername());
			jwt=jwtUtils.generateJwt(username);
		} catch (BadCredentialsException e) {
			logger.error("Auth failed with error "+ e.getMessage());
			throw e;
		}
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(jwt), HttpStatus.OK);
	}
	
	
}
