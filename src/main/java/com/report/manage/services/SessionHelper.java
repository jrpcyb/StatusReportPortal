package com.report.manage.services;

import javax.servlet.http.HttpSession;

public interface SessionHelper {
	public String loginService(String username, String password,
			HttpSession session);

	public boolean checkAuth(HttpSession session);

	public boolean isAdmin(HttpSession session);

	public boolean isUser(HttpSession session);

	public boolean isAdminAsUser(HttpSession session);

	public void getSessionDetails(HttpSession session);
	
}
