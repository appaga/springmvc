package io.github.appaga.base.auth;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String user_id;
	private byte active_yn;
	private byte expired_yn;
	private byte locked_yn;
	private byte pwd_expired_yn;
	private String login_id;
	private String nickname;
	private String pwd;
	private String reg_ymd;
	private String reg_hms;
	private Date upd_ts;
}
