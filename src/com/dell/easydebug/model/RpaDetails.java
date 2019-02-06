package com.dell.easydebug.model;


public class RpaDetails {
	private String rpaIp = "127.0.0.1";
	private String rpaUser = "root";
	private String rpaPass = "";

	public String getRpaIp() {
		return rpaIp;
	}

	public void setRpaIp(String rpaIp) {
		this.rpaIp = rpaIp;
	}

	public String getRpaUser() {
		return rpaUser;
	}

	public void setRpaUser(String rpaUser) {
		this.rpaUser = rpaUser;
	}

	public String getRpaPass() {
		return rpaPass;
	}

	public void setRpaPass(char[] password) {
		setRpaPass(String.valueOf(password));
	}

	public void setRpaPass(String rpaPass) {
		this.rpaPass = rpaPass;
	}

}
