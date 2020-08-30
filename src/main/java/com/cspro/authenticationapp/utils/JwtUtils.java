package com.cspro.authenticationapp.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtils {
	final String SECRET_KEY = "secret";

	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	public Date extractExpirationDate(String token) {
		return extractAllClaims(token).getExpiration();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public String generateJwt(String username) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("type", "Bearer");
		String jwt = createJwt(username, claims);
		return jwt;
	}

	private String createJwt(String username, Map<String, Object> claims) {
		String jwt = Jwts.builder().setClaims(claims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() * 10)).signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
		return jwt;
	}

	private Boolean isExpired(String token) {
		return extractExpirationDate(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = userDetails.getUsername();
		return (extractUsername(token).equals(username) && !isExpired(token));
	}
}
