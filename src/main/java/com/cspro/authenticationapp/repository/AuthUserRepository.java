package com.cspro.authenticationapp.repository;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthUserRepository {
	UserDetails getUserDetaisByUsername(String username);
}
