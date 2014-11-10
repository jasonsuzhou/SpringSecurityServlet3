package com.mh.web.handler;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.mh.model.user.UserAttempts;
import com.mh.model.user.dao.UserDetailsDao;

@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Autowired
	private UserDetailsDao userDetailsDao;

	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		try {
			Authentication auth = super.authenticate(authentication);
			userDetailsDao.resetFailAttempts(authentication.getName());
			return auth;
		} catch (BadCredentialsException e) {
			userDetailsDao.updateFailAttempts(authentication.getName());
			throw e;
		} catch (LockedException e) {
			String error = "";
			UserAttempts userAttempts = userDetailsDao.getUserAttempts(authentication.getName());
			if (userAttempts != null) {
				Date lastAttempts = userAttempts.getLastModified();
				error = "User account is locked!<"+authentication.getName()+">";
			} else {
				error = e.getMessage();
			}
			throw new LockedException(error);
		}
	}
	
	

}
