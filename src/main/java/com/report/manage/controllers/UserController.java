package com.report.manage.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.report.manage.beans.User;
import com.report.manage.dao.UserDao;
import com.report.manage.services.SessionHelper;

@Controller
@SessionAttributes("user")
public class UserController {
	private static final Logger log = Logger.getLogger("UserController");
	private UserDao userDao;

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	private SessionHelper sessionHelp;

	@Autowired
	public void setSessionHelp(SessionHelper sessionHelp) {
		this.sessionHelp = sessionHelp;
	}

	@RequestMapping(value = "/admin/createUser", method = RequestMethod.GET)
	public String createUser(
			@RequestParam(value = "notifymsg", required = false) String notifymsg,
			HttpSession session, Model model) {
		log.info("createUser: START");
		if (sessionHelp.isAdmin(session)) {
			if (session.getAttribute("userDB") == null) {
				userDao.fetchUserType(
						(String) session.getAttribute("username"), "inpw",
						session);
			}
			model.addAttribute(new User());
			if (notifymsg != null) {
				model.addAttribute("notifymsg", notifymsg);
			}
			return "createUser";
		} else {
			return "/";
		}
	}

	@RequestMapping(value = "/admin/showUsers", method = RequestMethod.GET)
	public String showUsers(
			@RequestParam(value = "notifymsg", required = false) String notifymsg,
			HttpSession session, Model model) {
		log.info("showUsers : START");
		List<User> users = userDao.getUsers(session);
		if (users.size() > 0) {
			log.info(users.size() + " resources under the current user found");
			model.addAttribute("users", users);
			if (notifymsg != null) {
				model.addAttribute("notifymsg", notifymsg);
			}
		} else {
			log.info("No resources under the current user");
			model.addAttribute("noUsersMsg",
					"No Users created yet. Please create some users.");
		}
		return "showUsers";
	}

	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public String editUser(@RequestParam("empid") long empid, Model model) {
		log.info("editUser : START - for user id "+empid);
		User user = userDao.getUserDetails(empid);
		model.addAttribute("user", user);
		model.addAttribute("action", "edit");
		return "createUser";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitUserForm(@ModelAttribute("user") User user,
			HttpSession session, SessionStatus status, Model model) throws Exception {
		long empid = user.getHidempid();
		log.info("submitUserForm : START");
		if (empid > 0) {
			log.info("Updating info for empid "+empid);
			userDao.update(user, empid, session);
			model.addAttribute("notifymsg",
					"The details of resource is updated successfully");
			return "redirect: showUsers";
		} else {
			userDao.save(user, session);
			log.info("New user is registered successfully");
			model.addAttribute("notifymsg",
					"The resource is created successfully");
			return "redirect:createUser";
		}

	}

	@RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("empid") int empid, Model model) {
		log.info("deleteUser : START");
		userDao.deleteUser(empid);
		log.info("User having empid "+empid+" is deleted successfully");
		model.addAttribute("notifymsg", "Resource deleted successfully");
		return "redirect:showUsers";
	}

}
