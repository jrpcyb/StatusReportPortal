package com.report.manage.beans;

public class Role {
	private int roleid;
	private String role;

	public Role() {

	}

	public Role(int roleid, String role) {
		super();
		this.roleid = roleid;
		this.role = role;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
