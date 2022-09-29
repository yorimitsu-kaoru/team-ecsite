package jp.co.internous.wings.model.session;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LoginSession implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private int tempUserId;
	private String userName;
	private String password;
	private boolean loginFlag;
	
	public int getUserId () {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getTempUserId () {
		return tempUserId;
	}
	
	public void setTempUserId(int tempUserId) {
		this.tempUserId = tempUserId;
	}
	
	public String getUserName () {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword () {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getLoginFlag () {
		return loginFlag;
	}
	
	public void setLoginFlag(boolean loginFlag) {
		this.loginFlag = loginFlag;
	}
	
	public void setSession(int userId, int tempUserId, String userName, String password, boolean loginFlag) {
		this.userId = userId;
		this.tempUserId = tempUserId;
		this.userName = userName;
		this.password = password;
		this.loginFlag = loginFlag;
	}
}