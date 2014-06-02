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

import com.report.manage.beans.User;

@Component
public class UserDao {
	private static final Logger log = Logger.getLogger("UserDao");
	private DataSource dataSource;
	private static JdbcTemplate jdbcTemplate;
	private String password;

	private String getPassword() {
		return password;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void save(User user, HttpSession session) {
		if (session.getAttribute("userDB") == null) {
			fetchUserType((String) session.getAttribute("username"),
					user.getPassword(), session);
		}
		
		long  man_emp_id = (Long) session.getAttribute("currEmpid");
		
		String sql = "insert into tbl_users(emp_id, username, password, firstname, lastname, user_type,"
				+ " manager_emp_id, created_by, created_on)"
				+ " values (?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(
				sql,
				new Object[] {
						user.getEmpid(),
						user.getUsername(),
						user.getPassword(),
						user.getFirstname(),
						user.getLastname(),
						user.getUserType(),
						man_emp_id,
						session.getAttribute("username"),
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date()) });

	}

	public int fetchUserType(String username, String password,
			HttpSession session) {
		if ("inpw".equals(password)) {
			password = getPassword();
		}
		setPassword(password);
		List<User> curr_user = jdbcTemplate
				.query("select emp_id,username, firstname, lastname, user_type, project_code, manager_emp_id from tbl_users"
						+ " where username = ? and password = ?", new Object[] {
						username, password }, new UserMapper());
		if (!curr_user.isEmpty()) {
			session.setAttribute("userDB", curr_user.get(0));
			session.setAttribute("currEmpid", curr_user.get(0).getEmpid());
			log.info(username + " is valid, session value is populated");
			return curr_user.get(0).getUserType();
		} else {
			log.info(username + " is an invalid user, but can be Super Admin");
			return -1;
		}

	}

	public List<User> getUsers(HttpSession session) {
		if (session.getAttribute("userDB") == null) {
			fetchUserType((String) session.getAttribute("username"),
					getPassword(), session);
		}
		long manager_emp_id = (Long) session.getAttribute("currEmpid");

		List<User> users = jdbcTemplate
				.query("select emp_id, username, firstname, lastname, user_type, project_code, manager_emp_id from"
						+ " tbl_users where manager_emp_id = ?",
						new Object[] { manager_emp_id }, new UserMapper());
		log.info("Fetched all resources under current user id :"
				+ manager_emp_id);
		return users;
	}

	public User getUserDetails(long empid) {
		return jdbcTemplate.queryForObject(
				"select * from tbl_users where emp_id =?",
				new Object[] { empid }, new UserMapper());

	}

	public void update(User user, long empid, HttpSession session) {
		jdbcTemplate
				.update("Update tbl_users set emp_id=?, username=?, firstname=?, lastname=?, user_type=?,"
						+ " updated_by=?, updated_on=? where emp_id = ?",
						new Object[] {
								user.getEmpid(),
								user.getUsername(),
								user.getFirstname(),
								user.getFirstname(),
								user.getUserType(),
								session.getAttribute("username"),
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date()), empid });
		if (user.getUserType() > user.getHideOldUserType()) {

			if (session.getAttribute("userDB") == null) {
				fetchUserType((String) session.getAttribute("username"),
						user.getPassword(), session);
			}

			long manager_emp_id = ((User) session.getAttribute("userDB"))
					.getManager_id();
			jdbcTemplate
					.update("update tbl_users set manager_emp_id = ?,updated_by=?, updated_on=? where manager_emp_id = ?",
							new Object[] {
									manager_emp_id,
									session.getAttribute("username"),
									new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
											.format(new Date()), empid });
		}
	}

	// TODO - while deleting admin, the user's manager_emp_id will be changed
	public void deleteUser(int empid) {
		jdbcTemplate.update("delete from tbl_users where emp_id = ?",
				new Object[] { empid });

	}

	// Mapper inner class

	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet res, int rowNum) throws SQLException {
			User user = new User();
			user.setEmpid(res.getInt("emp_id"));
			user.setFirstname(res.getString("firstname"));
			user.setLastname(res.getString("lastname"));
			user.setUsername(res.getString("username"));
			user.setUserType(res.getInt("user_type"));
			user.setProject_code(res.getString("project_code"));
			user.setManager_id(res.getInt("manager_emp_id"));
			return user;
		}

	}

}
