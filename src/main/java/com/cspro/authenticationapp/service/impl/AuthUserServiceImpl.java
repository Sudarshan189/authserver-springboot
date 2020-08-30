package com.cspro.authenticationapp.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cspro.authenticationapp.repository.AuthUserRepository;

@Service
public class AuthUserServiceImpl implements UserDetailsService {

	
	private static final Logger logger = LoggerFactory.getLogger(AuthUserServiceImpl.class);

	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails userDetails = new User("foo", encoder.encode("foo"), new ArrayList<>());
		logger.info(userDetails.getUsername() + "   "+ userDetails.getPassword());
		if (userDetails.getUsername().equals(username)) {
			return userDetails;
		}
		return null;
	}

}
