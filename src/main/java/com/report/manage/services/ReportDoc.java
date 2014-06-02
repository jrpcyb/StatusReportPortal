package com.report.manage.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.report.manage.beans.Project;
import com.report.manage.beans.Task;
import com.report.manage.dao.TaskDao;

public class ReportDoc {
	private static final Logger log = Logger.getLogger("ReportDoc");
	private Utility utility;
	private String downloadFilePath;

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public File createDocReport(Project project, TaskDao taskDao, Long currEmpid) {
		log.info("createDocReport : START");
		File output = null;
		utility = new Utility();
		
		//Get all Task to populate in report
		List<Task> taskListofAllusers = taskDao.getAllTask(
				project.getProject_id(), project.getSelected_date(), currEmpid);
		
		//Group task by users , use of Multi-Map
		Map<String, List<Task>> taskCache = new HashMap<String, List<Task>>();

		for (Task eachTask : taskListofAllusers) {
			List<Task> tskList = taskCache.get(eachTask.getTaskOwner());
			if (tskList == null) {
				taskCache.put(eachTask.getTaskOwner(),
						tskList = new ArrayList<Task>());
				tskList.add(eachTask);
			} else {
				tskList.add(eachTask);
			}
		}

		XWPFDocument document = new XWPFDocument();

		XWPFParagraph paragraphHeader = document.createParagraph();
		paragraphHeader.setAlignment(ParagraphAlignment.LEFT);
		paragraphHeader.setBorderBottom(Borders.SINGLE);

		XWPFRun paragraphOneRunHeader = paragraphHeader.createRun();
		paragraphOneRunHeader.setFontSize(26);
		paragraphOneRunHeader.setFontFamily("Cambria (Headings)");
		paragraphOneRunHeader.setColor("17365D");
		paragraphOneRunHeader.setText((String) utility.initCap(project
				.getProject_name())
				+ " – "
				+ utility.convertDateToString(project.getSelected_date(),
						"d-MMM-yyyy"));

		XWPFParagraph paragraphBody = document.createParagraph();

		for (Entry<String, List<Task>> eachEntry : taskCache.entrySet()) {
			paragraphBody.setWordWrap(true);
			paragraphBody.setSpacingLineRule(LineSpacingRule.AT_LEAST);
			paragraphBody.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun paragraphOneRunBody = paragraphBody.createRun();
			paragraphOneRunBody.addBreak();
			paragraphOneRunBody.setFontFamily("Calibri (Body)");
			paragraphOneRunBody.setFontSize(13);
			paragraphOneRunBody.setColor("E36C0A");
			paragraphOneRunBody.setBold(true);
			paragraphOneRunBody.setText(utility.initCap(eachEntry.getKey()) + " :"); // variable
			paragraphOneRunBody.addBreak();

			XWPFParagraph paragraphBodyInnerFirst = document.createParagraph();
			paragraphBodyInnerFirst.setAlignment(ParagraphAlignment.LEFT);

			XWPFRun paragraphOneRunBodyInnerFirst = paragraphBody.createRun();
			paragraphOneRunBodyInnerFirst.setFontFamily("Calibri (Body)");
			paragraphOneRunBodyInnerFirst.setFontSize(11);
			paragraphOneRunBodyInnerFirst.setColor("4A442A");
			paragraphOneRunBodyInnerFirst.setBold(true);
			paragraphOneRunBodyInnerFirst.setItalic(true);
			paragraphOneRunBodyInnerFirst.addBreak();
			paragraphOneRunBodyInnerFirst.setText("                    ");
			paragraphOneRunBodyInnerFirst.setText("What did you work today –?");

			// innermost
			XWPFParagraph paragraphBodyInnerMostFirst = document
					.createParagraph();
			paragraphBodyInnerMostFirst.setAlignment(ParagraphAlignment.LEFT);
			paragraphBodyInnerMostFirst.setWordWrap(true);

			XWPFRun paragraphOneRunBodyInnerMostFirst = paragraphBody
					.createRun();
			paragraphOneRunBodyInnerMostFirst.setFontFamily("Calibri (Body)");
			paragraphOneRunBodyInnerMostFirst.setFontSize(11);
			paragraphOneRunBodyInnerMostFirst.setColor("17365D");
			paragraphOneRunBodyInnerMostFirst.setItalic(true);

			for (Task eachTaskOfUser : eachEntry.getValue()) {
				paragraphOneRunBodyInnerMostFirst.addBreak();
				paragraphOneRunBodyInnerMostFirst
						.setText("                    		");
				paragraphOneRunBodyInnerMostFirst.setText("-" + "	   "
						+ eachTaskOfUser.getWorkDoneToday());
			}

			XWPFParagraph paragraphBodyInnerSecond = document.createParagraph();
			paragraphBodyInnerSecond.setAlignment(ParagraphAlignment.LEFT);

			XWPFRun paragraphOneRunBodyInnerSecond = paragraphBody.createRun();
			paragraphOneRunBodyInnerSecond.setFontFamily("Calibri (Body)");
			paragraphOneRunBodyInnerSecond.setFontSize(11);
			paragraphOneRunBodyInnerSecond.setColor("4A442A");
			paragraphOneRunBodyInnerSecond.setBold(true);
			paragraphOneRunBodyInnerSecond.setItalic(true);
			paragraphOneRunBodyInnerSecond.addBreak();
			paragraphOneRunBodyInnerSecond.setText("                    ");
			paragraphOneRunBodyInnerSecond
					.setText("What are you going to do tomorrow –?");

			// innermost
			XWPFParagraph paragraphBodyInnerMostSecond = document
					.createParagraph();
			paragraphBodyInnerMostSecond.setWordWrap(true);
			paragraphBodyInnerMostSecond.setAlignment(ParagraphAlignment.LEFT);

			XWPFRun paragraphOneRunBodyInnerMostSecond = paragraphBody
					.createRun();
			paragraphOneRunBodyInnerMostSecond.setFontFamily("Calibri (Body)");
			paragraphOneRunBodyInnerMostSecond.setFontSize(11);
			paragraphOneRunBodyInnerMostSecond.setColor("17365D");
			paragraphOneRunBodyInnerMostSecond.setItalic(true);

			for (Task eachTaskOfUser : eachEntry.getValue()) {
				paragraphOneRunBodyInnerMostSecond.addBreak();
				paragraphOneRunBodyInnerMostSecond
						.setText("                    		");
				paragraphOneRunBodyInnerMostSecond.setText("-" + "		"
						+ eachTaskOfUser.getPlanForNextDay());
			}

			XWPFParagraph paragraphBodyInnerThird = document.createParagraph();
			paragraphBodyInnerThird.setAlignment(ParagraphAlignment.LEFT);

			XWPFRun paragraphOneRunBodyInnerThird = paragraphBody.createRun();
			paragraphOneRunBodyInnerThird.setFontFamily("Calibri (Body)");
			paragraphOneRunBodyInnerThird.setFontSize(11);
			paragraphOneRunBodyInnerThird.setColor("4A442A");
			paragraphOneRunBodyInnerThird.setBold(true);
			paragraphOneRunBodyInnerThird.setItalic(true);
			paragraphOneRunBodyInnerThird.addBreak();
			paragraphOneRunBodyInnerThird.setText("                    ");
			paragraphOneRunBodyInnerThird
					.setText("Are there any impediments –?");

			// innermost
			XWPFParagraph paragraphBodyInnerMostThird = document
					.createParagraph();
			paragraphBodyInnerMostThird.setWordWrap(true);
			paragraphBodyInnerMostThird.setAlignment(ParagraphAlignment.LEFT);

			XWPFRun paragraphOneRunBodyInnerMostThird = paragraphBody
					.createRun();
			paragraphOneRunBodyInnerMostThird.setFontFamily("Calibri (Body)");
			paragraphOneRunBodyInnerMostThird.setFontSize(11);
			paragraphOneRunBodyInnerMostThird.setColor("17365D");
			paragraphOneRunBodyInnerMostThird.setItalic(true);

			for (Task eachTaskOfUser : eachEntry.getValue()) {
				paragraphOneRunBodyInnerMostThird.addBreak();
				paragraphOneRunBodyInnerMostThird
						.setText("                    		");
				paragraphOneRunBodyInnerMostThird.setText("-" + "    "
						+ eachTaskOfUser.getImpediments());

			}

		}
		output = new File(getDownloadFilePath()
				+ "\\report-"
				+ project.getProject_name()
				+ "-"
				+ utility.convertDateToString(project.getSelected_date(),
						"MM-dd-yyyy") + ".doc");
		
		log.info("Doc file is created @ "+output.getAbsolutePath());
		
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		}

		try {
			document.write(outStream);
			outStream.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return output;
	}
}
