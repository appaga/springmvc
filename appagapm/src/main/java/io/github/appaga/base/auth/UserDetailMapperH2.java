package io.github.appaga.base.auth;

import io.github.appaga.config.database.DbH2ConnMapper;

@DbH2ConnMapper
public interface UserDetailMapperH2 {
	UserDetail loadUserByUsername(String login_id);
}
