package com.report.manage.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.report.manage.beans.Project;
import com.report.manage.beans.TaskConfiguration;
import com.report.manage.beans.User;
import com.report.manage.dao.ProjectDao;
import com.report.manage.dao.TaskDao;
import com.report.manage.dao.UserDao;
import com.report.manage.services.SessionHelper;

@Controller
@RequestMapping({ "/", "/welcome", "/home", "/index" })
@SessionAttributes("task")
public class WelcomeController {
	private static final Logger log = Logger.getLogger("WelcomeController");

	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	private SessionHelper sessionHelp;

	@Autowired
	public WelcomeController(SessionHelper sesHelp) {
		this.sessionHelp = sesHelp;
	}

	private TaskDao taskDao;

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	private ProjectDao projectDao;

	@Autowired
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String welcomeContent(
			@RequestParam(value = "notifymsg", required = false) String notifymsg,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		/*
		 * If a session is existing, it will return that session object,
		 * OTHERWISE WILL CREATE A SESSION OBJECT EXPLICITLY AND RETURN TO THE
		 * CLIENT
		 */
		HttpSession session = request.getSession(true);

		if (session.getAttribute("username") != null) {
			log.info("welcomeContent : START - Returning to dashboard");
			sessionHelp.getSessionDetails(session);

			List<Project> projList = projectDao.getProjectNames(
					(String) session.getAttribute("username"),
					(Long) session.getAttribute("currEmpid"),
					(String) session.getAttribute("role"));

			if (projList.isEmpty()) {
				log.info("No project , but can log for internal projects");
				model.addAttribute("noProjMsg",
						"You can log your task for Internal project");
			} else {
				log.info("Projects found");
				model.addAttribute("projectList", projList);
			}
			model.addAttribute(new TaskConfiguration());
			model.addAttribute("notifymsg", notifymsg);

			if (session.getAttribute("userDB") == null) {
				userDao.fetchUserType(
						(String) session.getAttribute("username"), "inpw",
						session);
			}

			if (sessionHelp.isAdmin(session)) {
				log.info("returning as ADMIN");
				return "adminDashboard";
			} else {
				log.info("returning as USER");
				return "userDashboard";
			}
		} else {
			model.addAttribute(new User());
			// Forces caches to obtain a new copy of the page from the origin
			// server
			response.setHeader("Cache-Control", "no-cache");
			// Directs caches not to store the page under any circumstance
			response.setHeader("Cache-Control", "no-store");
			// Causes the proxy cache to see the page as "stale"
			response.setDateHeader("Expires", 0);
			// HTTP 1.0 backward compatibility
			response.setHeader("Pragma", "no-cache");
			return "welcome";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String loginForm(
			@ModelAttribute("user") User user,
			BindingResult result,
			HttpSession session,
			@RequestParam(value = "notifymsg", required = false) String notifymsg,
			Model model) {
		String username = user.getUsername();
		String password = user.getPassword();
		if (!username.isEmpty() && !password.isEmpty()) {
			log.info("loginForm : START - for username "+username);
			model.addAttribute("user", user);
			String userType = sessionHelp.loginService(username, password,
					session);

			if ("admin".equals(userType)) {

				session.setAttribute("role", "admin");
				session.setAttribute("username", username);
				if (sessionHelp.isAdminAsUser(session)) {
					user.setEmpid(0);
					user.setUserType(1);
					user.setManager_id(0);
					session.setAttribute("userDB", user);
					session.setAttribute("currEmpid", (long) 0);
					log.info("Login of Super Admin");
				}

				List<Project> projList = projectDao.getProjectNames(username,
						(Long) session.getAttribute("currEmpid"), userType);
				if (projList.isEmpty()) {
					log.info("No projects");
					model.addAttribute("noProjMsg",
							"You can log your task for Internal project");
				} else {
					log.info("projects found");
					model.addAttribute("projectList", projList);
				}
				model.addAttribute(new TaskConfiguration());
				model.addAttribute("notifymsg", notifymsg);
				log.info("Logging in " + username + " as ADMIN");
				return "adminDashboard";

			} else if ("user".equals(userType)) {

				session.setAttribute("role", "user");
				session.setAttribute("username", username);
				List<Project> projList = projectDao.getProjectNames(username,
						(Long) session.getAttribute("currEmpid"), userType);
				if (projList.isEmpty()) {
					model.addAttribute("noProjMsg",
							"You can log your task for Internal project");
				} else {
					model.addAttribute("projectList", projList);
				}
				model.addAttribute(new TaskConfiguration());
				model.addAttribute("notifymsg", notifymsg);
				log.info("Logging in " + username + " as USER");
				return "userDashboard";

			} else {
				model.addAttribute("message", "Invalid Username and Password");
				session.setAttribute("role", "NoAuth");
				log.info(username + " and " + password
						+ " are incorrect, redirecting to home page");
				return "redirect: welcome";
			}

		} else {
			return "redirect: welcome";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, HttpServletResponse response) {
		// Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control", "no-cache");
		// Directs caches not to store the page under any circumstance
		response.setHeader("Cache-Control", "no-store");
		// Causes the proxy cache to see the page as "stale"
		response.setDateHeader("Expires", 0);
		// HTTP 1.0 backward compatibility
		response.setHeader("Pragma", "no-cache");
		log.info("Logging out :" + (String) session.getAttribute("username"));
		session.invalidate();
		return "redirect: welcome";
	}

}
