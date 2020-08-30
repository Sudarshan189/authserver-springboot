package com.cspro.authenticationapp.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cspro.authenticationapp.utils.JwtUtils;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt=null;
		String authHeader = request.getHeader("Authorization");
		if (authHeader!= null && authHeader.startsWith("Bearer ")) {
			jwt= authHeader.substring(7);
		}
		// To check the stateless thing
		if (jwt!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			String username = jwtUtils.extractUsername(jwt);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtUtils.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				response.setHeader("jwt", jwt);
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
