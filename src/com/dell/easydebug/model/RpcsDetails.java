package com.dell.easydebug.model;

public class RpcsDetails {
	private int rpcsNum = 1;
	private String rpcsUser = "";
	private String rpcsPass = "";

	public int getRpcsNum() {
		return rpcsNum;
	}

	public void setRpcsNum(int rpcsNum) {
		this.rpcsNum = rpcsNum;
	}

	public String getRpcsUser() {
		return rpcsUser;
	}

	public void setRpcsUser(String rpcsUser) {
		this.rpcsUser = rpcsUser;
	}

	public String getRpcsPass() {
		return rpcsPass;
	}

	public void setRpcsPass(char[] password) {
		setRpcsPass(String.valueOf(password));
	}

	public void setRpcsPass(String rpcsPass) {
		this.rpcsPass = rpcsPass;
	}

}
