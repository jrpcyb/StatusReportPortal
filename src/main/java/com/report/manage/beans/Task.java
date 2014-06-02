package com.report.manage.beans;

import java.util.Date;

public class Task {
	private Date dateSelected;
	private int roleid;
	private String ticketid;
	private String planUnplan;
	private String workDoneToday;
	private String impediments;
	private String comment;
	private String statusCode;
	private String planForNextDay;
	private long id;
	private String taskOwner;
	private String workDoneYesterday;
	private String otherWork;
	private long project_id;


	public Task() {

	}

	public Task(Date date, int roleid, String ticketid, String planUnplan,
			String workDoneToday, String impediments, String comment,
			String statusCode, String planForNextDay, int id, String taskOwner,
			String workDoneYesterday, String otherWork, long project_id) {
		super();
		this.dateSelected = date;
		this.roleid = roleid;
		this.ticketid = ticketid;
		this.planUnplan = planUnplan;
		this.workDoneToday = workDoneToday;
		this.impediments = impediments;
		this.comment = comment;
		this.statusCode = statusCode;
		this.planForNextDay = planForNextDay;
		this.id = id;
		this.taskOwner = taskOwner;
		this.workDoneYesterday = workDoneYesterday;
		this.otherWork = otherWork;
		this.project_id = project_id;
	}

	public Date getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Date dateSelected) {
		this.dateSelected = dateSelected;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getTicketid() {
		return ticketid;
	}

	public void setTicketid(String ticketid) {
		this.ticketid = ticketid;
	}

	public String getPlanUnplan() {
		return planUnplan;
	}

	public void setPlanUnplan(String planUnplan) {
		this.planUnplan = planUnplan;
	}

	public String getWorkDoneToday() {
		return workDoneToday;
	}

	public void setWorkDoneToday(String workDoneToday) {
		this.workDoneToday = workDoneToday;
	}

	public String getImpediments() {
		return impediments;
	}

	public void setImpediments(String impediments) {
		this.impediments = impediments;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getPlanForNextDay() {
		return planForNextDay;
	}

	public void setPlanForNextDay(String planForNextDay) {
		this.planForNextDay = planForNextDay;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getWorkDoneYesterday() {
		return workDoneYesterday;
	}

	public void setWorkDoneYesterday(String workDoneYesterday) {
		this.workDoneYesterday = workDoneYesterday;
	}

	public String getOtherWork() {
		return otherWork;
	}

	public void setOtherWork(String otherWork) {
		this.otherWork = otherWork;
	}
	
	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

}
