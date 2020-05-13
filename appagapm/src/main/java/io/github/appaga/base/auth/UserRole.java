package io.github.appaga.base.auth;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class UserRole implements GrantedAuthority {
	private static final long serialVersionUID = 1L;
	
	private String role_id;
	private String role_nm;
	private String role_dp_nm;
	private int priority;
	
	@Override
	public String getAuthority() {
		return role_nm;
	}
	
}
