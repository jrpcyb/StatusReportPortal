package com.report.manage.beans;

public class Status {
	private String status;
	private String status_description;

	public Status() {

	}

	public Status(String status, String status_description) {
		super();
		this.status = status;
		this.status_description = status_description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_description() {
		return status_description;
	}

	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

}
