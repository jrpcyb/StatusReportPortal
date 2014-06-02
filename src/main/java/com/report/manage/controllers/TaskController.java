package com.report.manage.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.report.manage.beans.Project;
import com.report.manage.beans.Role;
import com.report.manage.beans.Status;
import com.report.manage.beans.Task;
import com.report.manage.beans.TaskConfiguration;
import com.report.manage.beans.User;
import com.report.manage.dao.ProjectDao;
import com.report.manage.dao.TaskDao;
import com.report.manage.services.Report;
import com.report.manage.services.ReportDoc;
import com.report.manage.services.SessionHelper;

@Controller
public class TaskController {
	private static final Logger log = Logger.getLogger("TaskController");
	private TaskDao taskDao;
	private File downloadFile = null;

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	private SessionHelper sessionHelp;

	@Autowired
	public void setSessionHelp(SessionHelper sessionHelp) {
		this.sessionHelp = sessionHelp;
	}

	private ProjectDao projectDao;

	@Autowired
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	private Report reportService;

	@Autowired
	public void setReportService(Report reportService) {
		this.reportService = reportService;
	}

	private ReportDoc reportDocService;

	@Autowired
	public void setReportDocService(ReportDoc reportDocService) {
		this.reportDocService = reportDocService;
	}

	private static final int BUFFER_SIZE = 4096;

	@RequestMapping(value = "/admin/viewTask", method = RequestMethod.GET)
	public String getTaskDetails(
			@ModelAttribute("task") Task task,
			BindingResult result,
			@RequestParam(value = "for_date", required = false) Date date,
			@RequestParam(value = "notifymsg", required = false) String notifymsg,
			HttpSession session, Model model) {
		log.info("viewTask - getTaskDetails : START - for date(if any) - "
				+ date);
		List<Task> taskList = new ArrayList<Task>();
		if (date == null) {
			taskList = taskDao.getTodayTask(session);
			if (taskList.isEmpty()) {
				model.addAttribute("noTaskMsg", "No Task created yet");
			} else {
				model.addAttribute("taskList", taskList);
			}
			model.addAttribute("day", "today");
		} else {
			taskList = taskDao.getTaskDetails(session, date);
			if (taskList.isEmpty()) {
				model.addAttribute("noTaskMsg", "No Task created");
			} else {
				model.addAttribute("taskList", taskList);
			}
			model.addAttribute("day", "anyday");
		}

		if (notifymsg != null) {
			model.addAttribute("notifymsg", notifymsg);
		}

		// model.addAttribute(new Task());

		return "viewTask";
	}

	@RequestMapping(value = "/tasks/edit/{project_id}/{id}", method = RequestMethod.GET)
	public String editTask(
			@PathVariable("project_id") long project_id,
			@PathVariable("id") long id,
			@ModelAttribute("taskConfiguration") TaskConfiguration taskConfiguration,
			HttpSession session, Model model) {
		log.info("editTask : START - for {project id =>" + project_id
				+ ", task id =>" + id + "}");
		Task task = taskDao.getTaskInfo(session, id);

		Date today = new Date();
		SimpleDateFormat sdfToday = new SimpleDateFormat("MM/dd/yyyy");
		String todayStr = sdfToday.format(today);
		model.addAttribute("todayStr", todayStr);

		List<Role> roles = taskDao.getRoles();
		model.addAttribute("roles", roles);

		List<Status> statusList = taskDao.getStatus();
		model.addAttribute("statusList", statusList);
		model.addAttribute("task", task);
		model.addAttribute("id", id);

		TaskConfiguration taskConfig = projectDao.getComponents(project_id,
				(Long) session.getAttribute("currEmpid"),
				(String) session.getAttribute("username"),
				(String) session.getAttribute("role"));

		if (taskConfig.getProject_id() == 0) {
			User user = projectDao.getOwnManager((Long) session
					.getAttribute("currEmpid"));
			String managerName = null;
			if (user.getManager_id() == 0) {
				managerName = "Admin";
			} else {
				managerName = user.getFirstname() + " " + user.getLastname();
			}
			log.info("No Configuration for Task page, Contact " + managerName);
			model.addAttribute("noConfig", "Please contact your manager ("
					+ managerName + ") to configure the task details for you.");
		} else {
			log.info("Configuration Present");
			model.addAttribute("allConfig", taskConfig);
		}

		return "taskForm";
	}

	@RequestMapping(value = "/tasks/edit/{project_id}/taskForm", method = RequestMethod.POST)
	public String updateTask(@PathVariable("project_id") long project_id,
			@ModelAttribute("task") Task task, HttpSession session, Model model) {
		log.info("updateTask : START");
		taskDao.update(task, session);
		model.addAttribute("notifymsg", "Task updated successfully");
		return "redirect:/admin/viewTask";
	}

	@RequestMapping(value = "/tasks/delete/{id}", method = RequestMethod.GET)
	public String deleteTask(@PathVariable("id") int id, Model model) {
		taskDao.delete(id);
		log.info("deleteTask : START - for task id " + id);
		model.addAttribute("notifymsg", "Task deleted successfully");
		return "redirect:/admin/viewTask";
	}

	@RequestMapping(value = "/admin/report", method = RequestMethod.GET)
	public String getProjectNames(HttpSession session, Model model) {
		log.info("report - getProjectNames : START");
		if (sessionHelp.isAdmin(session)) {
			List<Project> projList = projectDao
					.getOwnProjectNames((String) session
							.getAttribute("username"));
			model.addAttribute(new Project());
			if (projList.size() == 0) {
				log.info("No project found");
				model.addAttribute("noProjMsg",
						"No projects created yet. Please create a project first.");
			} else {
				model.addAttribute("projectList", projList);
			}
			return "report";
		} else {
			return "/";
		}

	}

	@RequestMapping(value = "/admin/report", method = RequestMethod.POST)
	public void downloadReport(@ModelAttribute("project") Project project,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session, Model model) throws IOException {
		if (project.getProject_id() == -1)
			project.setProject_name("Internal");
		else
			project.setProject_name(projectDao.getProjectNameById(project
				.getProject_id()));
		if (project.getFormat().equals("excel")) {
			log.info("downloadReport :  START - Excel");
			downloadFile = reportService.createReport(project, taskDao,
					(Long) session.getAttribute("currEmpid"));
		} else if (project.getFormat().equals("word")) {
			log.info("downloadReport :  START - Word");
			downloadFile = reportDocService.createDocReport(project, taskDao,
					(Long) session.getAttribute("currEmpid"));
		}

		String fullPath = downloadFile.getAbsolutePath();
		log.info("The downloading file is present @ " + fullPath);
		ServletContext context = session.getServletContext();
		try {
			FileInputStream inputStream = new FileInputStream(downloadFile);
			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}

			// set content attributes for the response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			// get output stream of the response
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
			log.info("Report Download : END");
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@RequestMapping(value = "projectTask", method = RequestMethod.POST)
	public String viewTaskForm(
			@ModelAttribute("taskConfiguration") TaskConfiguration taskConfiguration,
			HttpSession session, Model model) {
		log.info("projectTask - viewTaskForm : START");
		Date today = new Date();
		SimpleDateFormat sdfToday = new SimpleDateFormat("MM/dd/yyyy");
		String todayStr = sdfToday.format(today);
		model.addAttribute("todayStr", todayStr);

		List<Role> roles = taskDao.getRoles();
		model.addAttribute("roles", roles);

		List<Status> statusList = taskDao.getStatus();
		model.addAttribute("statusList", statusList);

		model.addAttribute(new Task());
		TaskConfiguration taskConfig = projectDao.getComponents(
				taskConfiguration.getProject_id(),
				(Long) session.getAttribute("currEmpid"),
				(String) session.getAttribute("username"),
				(String) session.getAttribute("role"));

		if (taskConfig.getProject_id() == 0) {
			User user = projectDao.getOwnManager((Long) session
					.getAttribute("currEmpid"));
			String managerName = null;
			if (user.getManager_id() == 0) {
				managerName = "Admin";
			} else {
				managerName = user.getFirstname() + " " + user.getLastname();
			}
			log.info("No Configuration present, contact " + managerName);
			model.addAttribute("noConfig", "Please contact your manager ("
					+ managerName + ") to configure the task details for you.");
		} else {
			model.addAttribute("allConfig", taskConfig);
		}
		return "taskForm";
	}

	@RequestMapping(value = "taskForm", method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("task") Task task,
			HttpSession session, Model model) {
		taskDao.save(task, session);
		log.info("submitForm : START - task saved sucessfully");
		model.addAttribute("notifymsg", "Task Submitted Successfully");
		return "redirect:/home";
	}

}
