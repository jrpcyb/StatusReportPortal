package com.report.manage.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Project {

	private String project_name;
	private long manager_id;
	private String project_code;
	private long[] empid;
	private String hiddenAction;
	private Date selected_date;
	private List<String> col = new ArrayList<String>();
	private String format;
	private long project_id;
	private String created_by;


	public Project() {

	}

	public Project(String project_name, long manager_id, String project_code,
			long[] empid, String hiddenAction, Date selected_date,
			List<String> col, String format, long project_id) {
		super();
		this.project_name = project_name;
		this.manager_id = manager_id;
		this.project_code = project_code;
		this.empid = empid;
		this.hiddenAction = hiddenAction;
		this.selected_date = selected_date;
		this.col = col;
		this.format = format;
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public long getManager_id() {
		return manager_id;
	}

	public void setManager_id(long manager_id) {
		this.manager_id = manager_id;
	}

	public String getProject_code() {
		return project_code;
	}

	public void setProject_code(String project_code) {
		this.project_code = project_code;
	}

	public long[] getEmpid() {
		return empid;
	}

	public void setEmpid(long[] objects) {
		this.empid = objects;
	}

	public String getHiddenAction() {
		return hiddenAction;
	}

	public void setHiddenAction(String hiddenAction) {
		this.hiddenAction = hiddenAction;
	}

	public Date getSelected_date() {
		return selected_date;
	}

	public void setSelected_date(Date selected_date) {
		this.selected_date = selected_date;
	}

	public List<String> getCol() {
		return col;
	}

	public void setCol(List<String> col) {
		this.col = col;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}
	
	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

}
