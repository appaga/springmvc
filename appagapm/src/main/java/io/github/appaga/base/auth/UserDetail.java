package io.github.appaga.base.auth;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserDetail implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String user_id;
	private User user;
	private List<UserRole> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getPwd();
	}

	@Override
	public String getUsername() {
		return user.getLogin_id();
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getExpired_yn()==0;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getLocked_yn()==0;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getPwd_expired_yn()==0;
	}

	@Override
	public boolean isEnabled() {
		return user.getActive_yn()==1;
	}

}
