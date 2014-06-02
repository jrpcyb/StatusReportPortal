package com.report.manage.beans;

public class User {
	private String username;
	private String password;
	private long empid;
	private String firstname;
	private String lastname;
	private int userType;
	private String project_code;
	private long manager_id;
	private long hidempid;
	private int hideOldUserType;

	public User(String username, String password, int empid, String firstname,
			String lastname, int userType, String project_code, long manager_id,
			int hidempid, int hideOldUserType) {
		super();
		this.username = username;
		this.password = password;
		this.empid = empid;
		this.firstname = firstname;
		this.lastname = lastname;
		this.userType = userType;
		this.project_code = project_code;
		this.manager_id = manager_id;
		this.hidempid = hidempid;
		this.hideOldUserType = hideOldUserType;
	}

	public User() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getEmpid() {
		return empid;
	}

	public void setEmpid(long empid) {
		this.empid = empid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public long getManager_id() {
		return manager_id;
	}

	public void setManager_id(long manager_id) {
		this.manager_id = manager_id;
	}

	public int getHideOldUserType() {
		return hideOldUserType;
	}

	public void setHideOldUserType(int hideOldUserType) {
		this.hideOldUserType = hideOldUserType;
	}

	public long getHidempid() {
		return hidempid;
	}

	public void setHidempid(long hidempid) {
		this.hidempid = hidempid;
	}

}
