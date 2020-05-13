package io.github.appaga.base.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import io.github.appaga.config.SysProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	@Qualifier("passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String pwd = authentication.getCredentials().toString();
		UserDetails ud = null;
		try {
			ud = userDetailService.loadUserByUsername(username);
			if (!passwordEncoder.matches(pwd, ud.getPassword())) {
				// 암호 불일치
				throw new BadCredentialsException(SysProperty.AUTH_ERROR_PWDNOTMATCH);
			}
			// 암호 확인이 끝나면 널처리
			((UserDetail)ud).getUser().setPwd(null);
			if(!ud.isEnabled()) {
				throw new BadCredentialsException(SysProperty.AUTH_ERROR_ACCOUNTDISABLED);
			}
			if(!ud.isAccountNonExpired()) {
				throw new BadCredentialsException(SysProperty.AUTH_ERROR_ACCOUNTEXPIRED);
			}
			if(!ud.isAccountNonLocked()) {
				throw new BadCredentialsException(SysProperty.AUTH_ERROR_ACCOUNTLOCKED);
			}
			if(!ud.isCredentialsNonExpired()) {
				throw new BadCredentialsException(SysProperty.AUTH_ERROR_CREDENTIALSEXPIRED);
			}
		} catch (UsernameNotFoundException e) {
			log.debug(e.getMessage(), e);
			throw new BadCredentialsException(e.getMessage());
		} catch (BadCredentialsException e) {
			log.debug(e.getMessage(), e);
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
		return new UsernamePasswordAuthenticationToken(ud, ud.getPassword(), ud.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
