package io.github.appaga.base.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import apgutil.Empty;
import io.github.appaga.config.SysProperty;

@Service
public class UserDetailService implements UserDetailsService {

	@Autowired
	private UserDetailMapperH2 userDetailMapperH2;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetail ud = userDetailMapperH2.loadUserByUsername(username);
		if (Empty.isEmpty(ud))
			throw new UsernameNotFoundException(SysProperty.AUTH_ERROR_USERNOTFOUND);
		return ud;
	}

}