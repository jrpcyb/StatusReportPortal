package com.report.manage.services;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import com.report.manage.dao.UserDao;

public class SessionHelperImpl implements SessionHelper {
	private String username;

	private String password;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String loginService(String username, String password,
			HttpSession session) {
		int userType = userDao.fetchUserType(username, password, session);
		if ((username.equals(this.username) && password.equals(this.password))
				|| userType == 1) {
			return "admin";
		} else if (userType == 2) {
			return "user";
		} else {
			return "none";
		}

	}

	public boolean checkAuth(HttpSession session) {
		// return true for user and admin
		return session != null && !"NA".equals(session.getAttribute("role"));
	}

	public boolean isAdmin(HttpSession session) {
		return session != null && "admin".equals(session.getAttribute("role"));
	}

	public boolean isUser(HttpSession session) {
		return session != null && "user".equals(session.getAttribute("role"));
	}

	public boolean isAdminAsUser(HttpSession session) {
		// return true for admin/cybadmin
		return session != null
				&& "admin".equals(session.getAttribute("username"));
	}

	public void getSessionDetails(HttpSession session) {
		Enumeration<?> sessionContent = session.getAttributeNames();
		while (sessionContent.hasMoreElements()) {
			String key = sessionContent.nextElement().toString();
			//System.out.println(key + ":" + session.getAttribute(key));
		}

	}

}
