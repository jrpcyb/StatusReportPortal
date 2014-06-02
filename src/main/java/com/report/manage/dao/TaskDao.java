package com.report.manage.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.report.manage.beans.Role;
import com.report.manage.beans.Status;
import com.report.manage.beans.Task;
import com.report.manage.services.Utility;

@Component
public class TaskDao {
	private static final Logger log = Logger.getLogger("TaskDao");
	private DataSource dataSource;
	private static JdbcTemplate jdbcTemplate;
	private Utility utility;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Autowired
	public void setUtility(Utility utility) {
		this.utility = utility;
	}

	public List<Role> getRoles() {
		return jdbcTemplate.query(
				"select id, roles from tbl_roles where id <> 1",
				new RoleMapper());
	}

	public List<Status> getStatus() {
		return jdbcTemplate.query(
				"select status, status_description from tbl_status",
				new StatusMapper());
	}

	public void save(Task task, HttpSession session) {
		log.info("saving task");
		jdbcTemplate
				.update("insert into tbl_tasks(ticket_id, role, plan_unplanned,work_done_yesterday, work_done_today,"
						+ " impediments, comments, status, plan_for_next_day,other_work, created_by, created_on, project_id)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { task.getTicketid(), task.getRoleid(),
								task.getPlanUnplan(),
								utility.initCap(task.getWorkDoneYesterday()),
								utility.initCap(task.getWorkDoneToday()),
								utility.initCap(task.getImpediments()),
								utility.initCap(task.getComment()),
								task.getStatusCode(),
								utility.initCap(task.getPlanForNextDay()),
								utility.initCap(task.getOtherWork()),
								session.getAttribute("username"), new Date(),
								task.getProject_id() });

	}

	public List<Task> getTodayTask(HttpSession session) {
		log.info("getTodayTask : Getting Today's task");
		return jdbcTemplate
				.query("select id, ticket_id, role, plan_unplanned, work_done_yesterday, work_done_today, impediments, comments,"
						+ " status, plan_for_next_day, other_work, created_on, created_by, project_id from tbl_tasks where created_by = ? and "
						+ " date(created_on)  = ?",
						new Object[] {
								(String) session.getAttribute("username"),
								new SimpleDateFormat("yyyy-MM-dd")
										.format(new Date()) }, new TaskMapper());

	}

	public List<Task> getTaskDetails(HttpSession session, Date date) {
		log.info("getTaskDetails : Getting task details for "
				+ new SimpleDateFormat("yyyy-MM-dd").format(date));
		return jdbcTemplate
				.query("select id, ticket_id, role, plan_unplanned, work_done_yesterday, work_done_today, impediments, comments,"
						+ " status, plan_for_next_day, other_work, created_on, created_by, project_id from tbl_tasks where created_by = ?"
						+ " and date(created_on) = ?", new Object[] {
						(String) session.getAttribute("username"),
						new SimpleDateFormat("yyyy-MM-dd").format(date) },
						new TaskMapper());
	}

	public Task getTaskInfo(HttpSession session, long id) {
		log.info("getTaskInfo : Getting task info for task id " + id);
		List<Task> taskList = jdbcTemplate
				.query("select id, ticket_id, role, plan_unplanned,work_done_yesterday, work_done_today, impediments, comments,"
						+ " status, plan_for_next_day, other_work, created_on, created_by, project_id from tbl_tasks where id = ?",
						new Object[] { id }, new TaskMapper());
		return taskList.get(0);

	}

	public void update(Task task, HttpSession session) {
		log.info("update : Updating task");
		jdbcTemplate
				.update("update tbl_tasks set ticket_id = ?, role = ?, plan_unplanned = ?, work_done_yesterday=?, work_done_today = ?,"
						+ " impediments = ?, comments = ?, status = ?, plan_for_next_day = ?, other_work=?, updated_on = ? where id = ? and project_id=?",
						new Object[] { task.getTicketid(), task.getRoleid(),
								task.getPlanUnplan(),
								utility.initCap(task.getWorkDoneYesterday()),
								utility.initCap(task.getWorkDoneToday()),
								utility.initCap(task.getImpediments()),
								utility.initCap(task.getComment()),
								task.getStatusCode(),
								utility.initCap(task.getPlanForNextDay()),
								utility.initCap(task.getOtherWork()),
								new Date(), task.getId(), task.getProject_id() });

	}

	public void delete(int id) {
		jdbcTemplate.update("delete from tbl_tasks where id = ?",
				new Object[] { id });

	}

	public List<Task> getAllTask(long project_id, Date selected_date,
			Long currEmpid) {
		log.info("getAllTask : {project_id=>" + project_id
				+ ", selected_date=>"
				+ new SimpleDateFormat("yyyy-MM-dd").format(selected_date)
				+ ", currEmpid=>" + currEmpid + "}");
		String sql = null;
		if (project_id != -1) {
			sql = "select t.id, t.ticket_id, t.role, t.plan_unplanned,"
					+ "t.work_done_yesterday, t.work_done_today, t.impediments,"
					+ " t.comments, t.status, t.plan_for_next_day,t.other_work,"
					+ " t.created_on, t.created_by, t.project_id from tbl_tasks t inner join"
					+ " tbl_users u on u.username = t.created_by  where date(t.created_on)"
					+ " = ? and u.emp_id in (select empid from tbl_proj_resources where project_id"
					+ " = ? union select manager_id from tbl_projects where id = ? union select emp_id from tbl_users where emp_id = ?)"
					+ " and t.project_id = ? order by id";
			log.info("Getting all task of (user + resources under user) for non internal project" + sql);
			return jdbcTemplate.query(sql, new Object[] {
					new SimpleDateFormat("yyyy-MM-dd").format(selected_date),
					project_id, project_id, currEmpid, project_id }, new TaskMapper());
		} else  {
			sql = "select t.id, t.ticket_id, t.role, t.plan_unplanned,t.work_done_yesterday,"
					+ " t.work_done_today, t.impediments, t.comments, t.status, t.plan_for_next_day,"
					+ " t.other_work, t.created_on, t.created_by, t.project_id from tbl_tasks t "
					+ " inner join tbl_users u on u.username = t.created_by  "
					+ " where date(t.created_on) = ? and "
					+ " u.emp_id in (select emp_id from tbl_users where manager_emp_id =?) and"
					+ " t.project_id=-1 order by id";
			log.info("Getting all task for internal project of user"+sql);
			return jdbcTemplate.query(sql, new Object[] { new SimpleDateFormat(
					"yyyy-MM-dd").format(selected_date), currEmpid }, new TaskMapper());
		}
	}

	// All Mapper inner classes

	private static final class TaskMapper implements RowMapper<Task> {

		public Task mapRow(ResultSet res, int rowNum) throws SQLException {
			Task task = new Task();
			task.setId(res.getLong("id"));
			task.setDateSelected(res.getDate("created_on"));
			task.setTicketid(res.getString("ticket_id"));
			task.setRoleid(res.getInt("role"));
			task.setPlanUnplan(res.getString("plan_unplanned"));
			task.setWorkDoneYesterday(res.getString("work_done_yesterday"));
			task.setWorkDoneToday(res.getString("work_done_today"));
			task.setImpediments(res.getString("impediments"));
			task.setComment(res.getString("comments"));
			task.setStatusCode(res.getString("status"));
			task.setPlanForNextDay(res.getString("plan_for_next_day"));
			task.setTaskOwner(res.getString("created_by"));
			task.setOtherWork(res.getString("other_work"));
			task.setProject_id(res.getLong("project_id"));
			return task;
		}
	}

	private static final class RoleMapper implements RowMapper<Role> {

		public Role mapRow(ResultSet res, int rowNum) throws SQLException {
			Role role = new Role();
			role.setRoleid(res.getInt("ID"));
			role.setRole(res.getString("ROLES"));
			return role;
		}
	}

	private static final class StatusMapper implements RowMapper<Status> {

		public Status mapRow(ResultSet res, int rowNum) throws SQLException {
			Status status = new Status();
			status.setStatus(res.getString("STATUS"));
			status.setStatus_description(res.getString("STATUS_DESCRIPTION"));
			return status;
		}
	}

}
