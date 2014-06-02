package com.report.manage.controllers;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.support.SessionStatus;

import com.report.manage.beans.Project;
import com.report.manage.beans.ProjectResource;
import com.report.manage.beans.TaskConfiguration;
import com.report.manage.beans.User;
import com.report.manage.dao.ProjectDao;
import com.report.manage.services.SessionHelper;

@Controller
@SessionAttributes("project")
public class ProjectController {
	private static final Logger log = Logger.getLogger("ProjectController");
	private ProjectDao projectDao;

	@Autowired
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	private SessionHelper sessionHelp;

	@Autowired
	public void setSessionHelp(SessionHelper sessionHelp) {
		this.sessionHelp = sessionHelp;
	}

	@RequestMapping(value = "/admin/createProject", method = RequestMethod.GET)
	public String createProject(
			@RequestParam(value = "project_code", required = false) String projectCode,
			@RequestParam(value = "project_id", required = false) Long projectId,
			@RequestParam(value = "notifymsg", required = false) String notifymsg,
			HttpSession session, Model model) {
		if (sessionHelp.isAdmin(session)) {
			log.info("createProject : START - for (if present) {project_code =>"
					+ projectCode + ", project_id =>" + projectId + "}");
			model.addAttribute(new Project());
			List<User> managers = projectDao.getManagers((Long) session
					.getAttribute("currEmpid"));
			if (managers.size() > 0) {
				model.addAttribute("managers", managers);
			} else {
				log.info("No resource under user as Admin, can't create any project");
				model.addAttribute(
						"noManagersMsg",
						"No manager created yet. Please create a user as Admin and then create the project to assign");
			}
			if (projectId != null) {
				log.info("Edit Project : Changing manager for project id"
						+ projectId);
				Project projectDetails = projectDao
						.getProjectDetails(projectId);
				model.addAttribute("project", projectDetails);
				model.addAttribute("action", "edit");
			}

			if (notifymsg != null) {
				model.addAttribute("notifymsg", notifymsg);
			}
			return "createProject";
		} else {
			return "/";
		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitProject(@ModelAttribute("project") Project project,
			HttpSession session, SessionStatus status, Model model) {
		log.info("submitProject : START");
		if ("update".equals(project.getHiddenAction())) {
			projectDao.update(project, session);
			log.info("Project updated sucessfully");
			model.addAttribute("notifymsg", "Project updated Successfully");
			return "redirect:assignProject";
		} else {

			projectDao.save(project, session);
			log.info("Project created successfully");
			model.addAttribute("notifymsg", "Project created Successfully");
			return "redirect:createProject";
		}

	}

	@RequestMapping(value = "/admin/assignProject", method = RequestMethod.GET)
	public String getAssignProject(
			HttpSession session,
			@RequestParam(value = "notifymsg", required = false) String notifymsg,
			Model model) {
		log.info("getAssignProject : START");
		if (sessionHelp.isAdmin(session)) {
			List<Project> projList = projectDao.getProjectNames(
					(String) session.getAttribute("username"),
					(Long) session.getAttribute("currEmpid"),
					(String) session.getAttribute("role"));
			model.addAttribute(new Project());
			if (projList.size() == 0) {
				log.info("no project found to assign");
				model.addAttribute("noProjMsg",
						"No projects created yet. Please create a project first.");
			} else {
				log.info("projects found to be assigned");
				model.addAttribute("projectList", projList);
				if (notifymsg != null) {
					model.addAttribute("notifymsg", notifymsg);
				}
			}
			return "assignProject";
		} else {
			return "/";
		}
	}

	// TODO : make it get
	@RequestMapping(value = "/admin/assignProject", method = RequestMethod.POST)
	public String assignUsersToProject(
			@ModelAttribute("project") Project project, Model model) {
		log.info("assignUsersToProject : START");
		Project proDetails = projectDao.getProjectDetails(project
				.getProject_id());
		List<User> usersUnderManager = projectDao.getUsersForManager(proDetails
				.getManager_id());

		String managerName = projectDao.getManagerName(proDetails
				.getManager_id());

		if (usersUnderManager.size() > 0) {
			log.info("resources found under user to be assigned");
			model.addAttribute("usersList", usersUnderManager);
			List<ProjectResource> proResList = projectDao
					.getSelectedIdsFmDB(project.getProject_id());
			List<Long> resids = new ArrayList<Long>();
			for (ProjectResource proRes : proResList) {
				resids.add(proRes.getEmpid());
			}
			// Making long[] from the List<Long>
			long[] arr = new long[resids.size()];
			int i = 0;
			for (Long l : resids) {
				arr[i++] = l;
			}
			project.setEmpid(arr);

		} else {
			log.info("No resources found under user");
			model.addAttribute("noUsersUnderManager",
					"No resources created under manager " + managerName + ".");
		}
		model.addAttribute("projectCode", proDetails.getProject_code());
		model.addAttribute("projectId", project.getProject_id());
		model.addAttribute("managerName", managerName);
		model.addAttribute("projectName", proDetails.getProject_name());
		model.addAttribute("managerId", proDetails.getManager_id());
		return "assignUsers";
	}

	@RequestMapping(value = "/admin/assignUsers", method = RequestMethod.POST)
	public String submitProjUsers(@ModelAttribute("project") Project project,
			Model model) {
		log.info("submitProjUsers : START - Assigning resources to project");
		projectDao.assignUsersToProject(project.getEmpid(),
				project.getProject_id());
		model.addAttribute("notifymsg", "Assigning resources to project '"
				+ project.getProject_name() + "' done successfully");
		return "redirect:assignProject";

	}

	@RequestMapping(value = "/admin/configureTaskPage", method = RequestMethod.GET)
	public String getProjects(
			HttpSession session,
			@RequestParam(value = "notifymsg", required = false) String notifymsg,
			Model model) {
		if (sessionHelp.isAdmin(session)) {
			log.info("getProjects : START");
			List<Project> projList = projectDao
					.getOwnProjectNames((String) session
							.getAttribute("username"));
			model.addAttribute(new TaskConfiguration());
			if (projList.size() == 0) {
				log.info("No project created yet, still can configure for default internal project");
				model.addAttribute(
						"noProjMsg",
						"You didn't create any project. You can customize the default setting for all your resources");
			} else {
				log.info("projects found");
				model.addAttribute("projectList", projList);

			}
			return "configureTaskPage";
		} else {
			return "/";
		}
	}

	@RequestMapping(value = "/admin/configureTaskPage", method = RequestMethod.POST)
	public String configureColumns(
			@ModelAttribute("taskConfiguration") TaskConfiguration taskConfiguration,
			BindingResult result, Model model) {
		log.info("configureColumns : START - Configuring task page");
		List<TaskConfiguration> colList = new ArrayList<TaskConfiguration>();
		colList = projectDao.getColumnNames(taskConfiguration.getManager_id(),
				taskConfiguration.getProject_id());
		if (!colList.isEmpty()) {
			model.addAttribute("allCols", colList.get(0));
		} else {
			log.info("no task configuration found");
			model.addAttribute("allCols", "");
		}
		model.addAttribute("taskConfiguration", taskConfiguration);
		return "configTaskComponents";
	}

	@RequestMapping(value = "/admin/configTaskComponents", method = RequestMethod.POST)
	public String persistTaskComponents(
			@ModelAttribute("taskConfiguration") TaskConfiguration taskConfiguration,
			BindingResult result, HttpSession session, Model model) {
		log.info("persistTaskComponents : START - saving task components");
		projectDao.saveTaskComponents(taskConfiguration);
		log.info("Task Components configure for the project successfully");
		model.addAttribute("notifymsg",
				"Task Components configure for the project successfully");
		List<Project> projList = projectDao.getOwnProjectNames((String) session
				.getAttribute("username"));
		model.addAttribute("projectList", projList);
		return "configureTaskPage";
	}

}
