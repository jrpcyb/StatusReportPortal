package com.report.manage.beans;

public class TaskConfiguration {
	private long manager_id;
	private long project_id;
	private int role;
	private int ticket_id;
	private int plan_unplanned;
	private int work_done_today;
	private int impediments;
	private int comments;
	private int status;
	private int plan_for_next_day;
	private int work_done_yesterday;
	private int other_work;
	
	public long getManager_id() {
		return manager_id;
	}
	public void setManager_id(long manager_id) {
		this.manager_id = manager_id;
	}
	public long getProject_id() {
		return project_id;
	}
	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getPlan_unplanned() {
		return plan_unplanned;
	}
	public void setPlan_unplanned(int plan_unplanned) {
		this.plan_unplanned = plan_unplanned;
	}
	public int getWork_done_today() {
		return work_done_today;
	}
	public void setWork_done_today(int work_done_today) {
		this.work_done_today = work_done_today;
	}
	public int getImpediments() {
		return impediments;
	}
	public void setImpediments(int impediments) {
		this.impediments = impediments;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPlan_for_next_day() {
		return plan_for_next_day;
	}
	public void setPlan_for_next_day(int plan_for_next_day) {
		this.plan_for_next_day = plan_for_next_day;
	}
	public int getWork_done_yesterday() {
		return work_done_yesterday;
	}
	public void setWork_done_yesterday(int work_done_yesterday) {
		this.work_done_yesterday = work_done_yesterday;
	}
	public int getOther_work() {
		return other_work;
	}
	public void setOther_work(int other_work) {
		this.other_work = other_work;
	}
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	
	
	
	
}
