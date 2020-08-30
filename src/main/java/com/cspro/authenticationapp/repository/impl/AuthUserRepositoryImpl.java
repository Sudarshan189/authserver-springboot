package com.cspro.authenticationapp.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.cspro.authenticationapp.repository.AuthUserRepository;

@Repository
public class AuthUserRepositoryImpl implements AuthUserRepository {

	private List<UserDetails> listUsers;

	private static final Logger logger = LoggerFactory.getLogger(AuthUserRepositoryImpl.class);

	public AuthUserRepositoryImpl() {

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		listUsers = new ArrayList<UserDetails>();
		listUsers.add(new User("foo", encoder.encode("foo"), new ArrayList<>()));
		listUsers.add(new User("fo1", encoder.encode("fo1"), new ArrayList<>()));
	}

	@Override
	public UserDetails getUserDetaisByUsername(String username) {

		for (UserDetails userDetails : listUsers) {
			if (userDetails.getUsername().equals(username)) {
				logger.info("Password:  " + userDetails.getPassword());
				return userDetails;
			}
		}
		return null;
	}

}
