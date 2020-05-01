package io.github.appaga.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import apgutil.Empty;
import lombok.Data;

@Data
@Component
@ConfigurationProperties("sys.property")
public class SysProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final int RESULT_OK = HttpStatus.OK.value();
	public static final int RESULT_ERROR = 9999;
	public static final String AUTH_ERROR_USERNOTFOUND = "USER_NOT_FOUND";
	public static final String AUTH_ERROR_PWDNOTMATCH = "PWD_NOT_MATCH";
	public static final String AUTH_ERROR_ACCOUNTDISABLED = "ACCOUNT_DISABLED";
	public static final String AUTH_ERROR_ACCOUNTEXPIRED = "ACCOUNT_EXPIRED";
	public static final String AUTH_ERROR_ACCOUNTLOCKED = "ACCOUNT_LOCKED";
	public static final String AUTH_ERROR_CREDENTIALSEXPIRED = "CREDENTIALS_EXPIRED";

	public static final String SERVER_MODE_LOCAL = "LOCAL";
	public static final String SERVER_MODE_DEV = "DEV";
	public static final String SERVER_MODE_STG = "STG";
	public static final String SERVER_MODE_PRD = "PRD";

	/** 서버 실행 모드 */
	private String serverMode = SERVER_MODE_LOCAL;

	final void setServerMode(final String serverMode) {
		if (Empty.isEmpty(serverMode)) {
			this.serverMode = SERVER_MODE_LOCAL;
		} else {
			switch (serverMode) {
			case SERVER_MODE_LOCAL:
			case SERVER_MODE_DEV:
			case SERVER_MODE_STG:
			case SERVER_MODE_PRD:
				this.serverMode = serverMode;
				break;
			}
		}
	}
}
