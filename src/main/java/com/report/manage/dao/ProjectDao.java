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

import com.report.manage.beans.Project;
import com.report.manage.beans.ProjectResource;
import com.report.manage.beans.TaskConfiguration;
import com.report.manage.beans.User;

@Component
public class ProjectDao {
	private static final Logger log = Logger.getLogger("ProjectDao");
	private DataSource dataSource;
	private static JdbcTemplate jdbcTemplate;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<User> getManagers(long currEmpid) {
		log.info("getManagers : Getting leads under manager " + currEmpid);
		List<User> managers = jdbcTemplate
				.query("select emp_id, username, firstname, lastname, manager_emp_id from tbl_users where manager_emp_id = ? and user_type =1",
						new Object[] { currEmpid }, new UserMapper());
		return managers;
	}

	public void save(Project project, HttpSession session) {
		String sql = "insert into tbl_projects(project_name, manager_id, project_code,"
				+ " created_on, created_by) values(?,?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] { project.getProject_name(),
				project.getManager_id(), project.getProject_code(),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
				session.getAttribute("username") });
	}

	public void update(Project project, HttpSession session) {
		jdbcTemplate
				.update("update tbl_projects set project_name=?, manager_id =? where id=?",
						new Object[] { project.getProject_name(),
								project.getManager_id(),
								project.getProject_id() });
	}

	public List<Project> getProjectNames(String username, long currEmpid,
			String role) {
		List<Project> projList = null;
		if ("admin".equals(role)) {
			log.info("getProjectNames : Getting the list of projects created by or assigned to user");
			projList = jdbcTemplate
					.query("select id, project_name, project_code, manager_id, created_by from tbl_projects where created_by=? " +
							" or manager_id=? union select id, project_name, project_code, manager_id, created_by " +
							" from tbl_projects where id in (select project_id from tbl_proj_resources where empid=?)",
							new Object[] { username, currEmpid, currEmpid },
							new ProjectMapper());
		} else if ("user".equals(role)) {
			log.info("getProjectNames : Getting the list of projects assigned to resource as user");
			projList = jdbcTemplate
					.query("select p.id, p.project_name, p.manager_id, p.project_code, p.created_by from tbl_projects p inner join"
							+ " tbl_proj_resources r on r.project_id = p.id where r.empid = ?",
							new Object[] { currEmpid }, new ProjectMapper());
		}
		return projList;
	}

	public List<Project> getOwnProjectNames(String username) {
		log.info("getOwnProjectNames : Getting project list created by user");
		List<Project> projList = jdbcTemplate
				.query("select id, project_name, project_code, manager_id, created_by from tbl_projects where created_by=?",
						new Object[] { username }, new ProjectMapper());
		return projList;
	}

	public List<User> getUsersForManager(long manager_id) {
		log.info("getUsersForManager : Getting resources under manager");
		return jdbcTemplate
				.query("select emp_id, username, firstname, lastname, manager_emp_id from tbl_users where manager_emp_id = ?",
						new Object[] { manager_id }, new UserMapper());
	}

	public String getManagerName(long manager_id) {
		log.info("getManagerName : Getting name of manager " + manager_id);
		List<User> user = jdbcTemplate
				.query("select emp_id, username, firstname, lastname, manager_emp_id from tbl_users where emp_id=?",
						new Object[] { manager_id }, new UserMapper());
		return user.get(0).getFirstname() + " " + user.get(0).getLastname();
	}
	

	public Project getProjectDetails(long projectId) {
		return jdbcTemplate
				.query("select id, project_name, project_code, manager_id, created_by from tbl_projects where id=?",
						new Object[] { projectId }, new ProjectMapper()).get(0);
	}

	public void assignUsersToProject(long[] empids, long project_id) {
		String sql = null;
		jdbcTemplate.update(
				"delete from tbl_proj_resources where project_id = ?",
				new Object[] { project_id });
		sql = "insert into tbl_proj_resources(project_id, empid) values (?,?)";
		for (long eachId : empids) {
			jdbcTemplate.update(sql, new Object[] { project_id, eachId });
		}
		log.info("resources are assigned to project (" + project_id
				+ ") successfully");

	}

	public List<ProjectResource> getSelectedIdsFmDB(long project_id) {
		log.info("Getting users assigned to project " + project_id);
		return jdbcTemplate
				.query("select empid, project_id from tbl_proj_resources where project_id =?",
						new Object[] { project_id },
						new ProjectResourceMapper());
	}

	public List<TaskConfiguration> getColumnNames(long manager_id,
			long project_id) {
		log.info("Getting the task configuration for {project_id=>"
				+ project_id + ", manager_id=>" + manager_id + "}");
		return jdbcTemplate
				.query("select manager_id, project_id, role,ticket_id, planned_unplanned,"
						+ " work_done_today, impediments, comments, status, plan_for_next_day,"
						+ " work_done_yesterday, other_work "
						+ "from tbl_manage_task_columns "
						+ "where project_id =? and manager_id=?", new Object[] {
						project_id, manager_id }, new TaskConfigurationMapper());

	}

	public void saveTaskComponents(TaskConfiguration taskConfiguration) {
		jdbcTemplate
				.update("delete from tbl_manage_task_columns where manager_id=? and project_id=?",
						new Object[] { taskConfiguration.getManager_id(),
								taskConfiguration.getProject_id() });

		String sql = "insert into tbl_manage_task_columns (manager_id, project_id,role,"
				+ " ticket_id, planned_unplanned, work_done_today,impediments, comments, status,"
				+ " plan_for_next_day, work_done_yesterday, other_work)"
				+ "     values (?,?,?,?,?,?,?,?,?,?,?,?)";

		log.info("saveTaskComponents : Saving task components - " + sql);
		jdbcTemplate.update(
				sql,
				new Object[] { taskConfiguration.getManager_id(),
						taskConfiguration.getProject_id(),
						taskConfiguration.getRole(),
						taskConfiguration.getTicket_id(),
						taskConfiguration.getPlan_unplanned(),
						taskConfiguration.getWork_done_today(),
						taskConfiguration.getImpediments(),
						taskConfiguration.getComments(),
						taskConfiguration.getStatus(),
						taskConfiguration.getPlan_for_next_day(),
						taskConfiguration.getWork_done_yesterday(),
						taskConfiguration.getOther_work() });

	}

	public String getProjectNameById(long project_id) {
		return jdbcTemplate
				.query("select id, project_code, project_name, manager_id, created_by from tbl_projects where id=?",
						new Object[] { project_id }, new ProjectMapper())
				.get(0).getProject_name();

	}
	
	public long getProjectOwnerIDById(long project_id) {
		List<User> user = jdbcTemplate
				.query("select u.emp_id, u.username, u.firstname, u.lastname, u.manager_emp_id from tbl_users u inner join tbl_projects p on p.created_by = u.username where p.id = ?",
						new Object[] { project_id }, new UserMapper());
		return user.get(0).getEmpid();

	}

	public TaskConfiguration getComponents(long project_id, long empid,
			String username, String role) {
		log.info("getComponents : Getting task components of {project_id=>"
				+ project_id + ", empid=>" + empid + ", username=>" + username
				+ ", role=>" + role + "}");
		long manager_id = 0;
		List<TaskConfiguration> tsList = null;
		if ("admin".equals(role)) {
			List<Project> ownProjects = getOwnProjectNames(username);

			// get the manager id and with the help of project id , get task
			// configurations

			if (!ownProjects.isEmpty()) {
				List<User> managerList = jdbcTemplate
						.query("select emp_id, username, firstname, lastname, manager_emp_id from tbl_users where emp_id =?",
								new Object[] { empid }, new UserMapper());
				if (managerList != null
						&& managerList.get(0).getManager_id() == 0) {
					log.info("For immediate successor of super Admin who has created projects");
					boolean isOwner = false;
					for (Project proj : ownProjects) {
						if (project_id == proj.getProject_id()) {
							isOwner = true;
						}
					}

					if (isOwner) {
						log.info("user's project");
						manager_id = empid;
					} else {
						log.info("user is assigned to the project");
						manager_id = 0;
					}
				} else {
					log.info("for super admin who has some projects created");
					manager_id = empid;
				}
				/*
				 * log.info("For users who has created  some projects");
				 * manager_id = empid;
				 */

			} else {
				if (empid == 0) {
					log.info("for Super Admin who has not created any project");
					manager_id = 0;
				} else {
					log.info("For successor of super admin who has not created any project");
					if (project_id != -1) {
						log.info("for non internal project");
						List<Project> projList = jdbcTemplate.query("select id, project_name, project_code, manager_id, created_by from tbl_projects where id=?" , new Object[]{project_id}, new ProjectMapper());
						if ("admin".equals(projList.get(0).getCreated_by())) {
							log.info("Project is owned by Super Admin");
							manager_id = 0;
						}
							
						else {
							log.info("Getting the owner of the project");
							manager_id = getProjectOwnerIDById(project_id);
						}
					} else {
						log.info("for internal project");
						List<User> managerList = jdbcTemplate
								.query("select emp_id, username, firstname, lastname, manager_emp_id from tbl_users where emp_id =?",
										new Object[] { empid }, new UserMapper());
						manager_id = managerList.get(0).getManager_id();
					}
				}

			}
			log.info("manager_id" + manager_id);
			tsList = jdbcTemplate
					.query("select * from tbl_manage_task_columns where project_id = ? and manager_id = ?",
							new Object[] { project_id, manager_id },
							new TaskConfigurationMapper());
		} else if ("user".equals(role)) {
			// Get the task configuration for a user directly for a project
			tsList = jdbcTemplate
					.query("select * from tbl_manage_task_columns where project_id = ?",
							new Object[] { project_id },
							new TaskConfigurationMapper());
		}
		if (tsList.isEmpty()) {
			return new TaskConfiguration();
		} else {
			return tsList.get(0);
		}

	}

	public User getOwnManager(long empid) {
		log.info("getOwnManager : Getting the manager of empid " + empid);
		List<User> managerList = jdbcTemplate
				.query("select emp_id, username, firstname, lastname, manager_emp_id from tbl_users where emp_id =?",
						new Object[] { empid }, new UserMapper());
		if (managerList.get(0).getManager_id() == 0) {
			// Super Admin
			return managerList.get(0);
		} else {
			List<User> usList = jdbcTemplate
					.query("select emp_id, username, firstname, lastname, manager_emp_id from tbl_users where emp_id = ?",
							new Object[] { managerList.get(0).getManager_id() },
							new UserMapper());
			return usList.get(0);
		}
	}

	// All Mapper inner classes

	private static final class ProjectMapper implements RowMapper<Project> {

		public Project mapRow(ResultSet res, int rowNum) throws SQLException {
			Project proj = new Project();
			proj.setProject_id(res.getLong("id"));
			proj.setProject_name(res.getString("project_name"));
			proj.setProject_code(res.getString("project_code"));
			proj.setManager_id(res.getInt("manager_id"));
			proj.setCreated_by(res.getString("created_by"));
			return proj;
		}

	}

	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet res, int rowNum) throws SQLException {
			User user = new User();
			user.setEmpid(res.getInt("emp_id"));
			user.setUsername(res.getString("username"));
			user.setFirstname(res.getString("firstname"));
			user.setLastname(res.getString("lastname"));
			user.setManager_id(res.getLong("manager_emp_id"));
			return user;
		}

	}

	private static final class ProjectResourceMapper implements
			RowMapper<ProjectResource> {

		public ProjectResource mapRow(ResultSet res, int rowNum)
				throws SQLException {

			ProjectResource resources = new ProjectResource();
			resources.setProject_id(res.getLong("project_id"));
			resources.setEmpid(res.getLong("empid"));
			return resources;
		}

	}

	private static final class TaskConfigurationMapper implements
			RowMapper<TaskConfiguration> {

		public TaskConfiguration mapRow(ResultSet res, int rowNum)
				throws SQLException {
			TaskConfiguration configure = new TaskConfiguration();
			configure.setManager_id(res.getLong("manager_id"));
			configure.setProject_id(res.getLong("project_id"));
			configure.setRole(res.getInt("role"));
			configure.setTicket_id(res.getInt("ticket_id"));
			configure.setPlan_unplanned(res.getInt("planned_unplanned"));
			configure.setWork_done_today(res.getInt("work_done_today"));
			configure.setImpediments(res.getInt("impediments"));
			configure.setComments(res.getInt("comments"));
			configure.setStatus(res.getInt("status"));
			configure.setPlan_for_next_day(res.getInt("plan_for_next_day"));
			configure.setWork_done_yesterday(res.getInt("work_done_yesterday"));
			configure.setOther_work(res.getInt("other_work"));
			return configure;
		}

	}

}
