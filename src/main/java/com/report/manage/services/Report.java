package com.report.manage.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;

import com.report.manage.beans.Project;
import com.report.manage.beans.Task;
import com.report.manage.dao.TaskDao;

public class Report {
	private static final Logger log = Logger.getLogger("Report");
	private List<String> colList = null;
	private List<Integer> columnIndexRemoved = null;
	private String downloadFilePath;
	private Utility utility;

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public File createReport(Project project, TaskDao taskDao, Long currEmpid)
			throws IOException {
		log.info("createReport : START");
		File output = null;
		colList = new ArrayList<String>();
		colList = project.getCol();
		utility = new Utility();
		try {
			Workbook wb = new HSSFWorkbook();
			Sheet dsrSheet = wb.createSheet("DSR");

			CellStyle styleHeader = wb.createCellStyle();

			// Create a row and put some cells in it. Rows are 0 based.
			Row row0 = dsrSheet.createRow((short) 0);

			styleHeader.setFillForegroundColor(IndexedColors.DARK_BLUE
					.getIndex());
			styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Cell cell = row0.createCell((short) 0);
			Font fontHeader = wb.createFont();
			fontHeader.setFontHeightInPoints((short) 20);
			fontHeader.setFontName("Calibri");
			fontHeader.setColor(IndexedColors.WHITE.getIndex());
			styleHeader.setFont(fontHeader);
			cell.setCellValue((String) utility.initCap(project
					.getProject_name()));
			cell.setCellStyle(styleHeader);

			CellUtil.setAlignment(cell, wb, CellStyle.ALIGN_CENTER);
			dsrSheet.addMergedRegion(new CellRangeAddress(0, // first row
																// (0-based)
					1, // last row (0-based)
					0, // first column (0-based)
					11 // last column (0-based)
			));

			CellStyle styleTableHeader = wb.createCellStyle();

			Row row2 = dsrSheet.createRow((short) 2);

			styleTableHeader
					.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE
							.getIndex());
			styleTableHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);

			Font fontTableHeader = wb.createFont();
			fontTableHeader.setFontHeightInPoints((short) 12);
			fontTableHeader.setBoldweight((short) 2);
			fontTableHeader.setFontName("Calibri-Regular");
			styleTableHeader.setFont(fontTableHeader);

			Cell cell0 = row2.createCell(0);
			Cell cell1 = row2.createCell(1);
			Cell cell2 = row2.createCell(2);
			Cell cell3 = row2.createCell(3);
			Cell cell4 = row2.createCell(4);
			Cell cell5 = row2.createCell(5);
			Cell cell6 = row2.createCell(6);
			Cell cell7 = row2.createCell(7);
			Cell cell8 = row2.createCell(8);
			Cell cell9 = row2.createCell(9);
			Cell cell10 = row2.createCell(10);
			Cell cell11 = row2.createCell(11);

			cell0.setCellValue("Date");
			cell1.setCellValue("Resource Name");
			cell2.setCellValue("Dev/QA/UI");
			cell3.setCellValue("Assignment/Story/Ticket ID");
			cell4.setCellValue("Planned/Unplanned");
			cell5.setCellValue("Work Done Today");
			cell6.setCellValue("Impediment");
			cell7.setCellValue("Comment");
			cell8.setCellValue("Status");
			cell9.setCellValue("Work Done Yesterday");
			cell10.setCellValue("Plan For Next Day");
			cell11.setCellValue("Other work");

			// data
			CellStyle styleTableBody = wb.createCellStyle();
			styleTableBody.setBorderBottom(CellStyle.BORDER_THIN);
			styleTableBody.setBorderTop(CellStyle.BORDER_THIN);
			styleTableBody.setBorderRight(CellStyle.BORDER_THIN);
			styleTableBody.setBorderLeft(CellStyle.BORDER_THIN);
			
			//Getting All task of (user + resources under user)
			List<Task> taskListFromAncestors = taskDao.getAllTask(
					project.getProject_id(), project.getSelected_date(),
					currEmpid);

			int rowCount = 4;
			String resource = "-abc";
			if (!taskListFromAncestors.isEmpty()) {
				for (Task task : taskListFromAncestors) {
					Row row = dsrSheet.createRow((short) rowCount++);
					row.createCell(0).setCellValue(
							resource.equals(task.getTaskOwner()) ? ""
									: new SimpleDateFormat("yyyy-MM-dd")
											.format(task.getDateSelected()));
					row.createCell(1).setCellValue(
							resource.equals(task.getTaskOwner()) ? "" : task
									.getTaskOwner());
					row.createCell(2).setCellValue(getRole(task.getRoleid()));
					row.createCell(3).setCellValue(task.getTicketid());
					row.createCell(4).setCellValue(task.getPlanUnplan());
					row.createCell(5).setCellValue(task.getWorkDoneToday());
					row.createCell(6).setCellValue(task.getImpediments());
					row.createCell(7).setCellValue(task.getComment());
					row.createCell(8).setCellValue(
							getStatus(task.getStatusCode()));
					row.createCell(9).setCellValue(task.getWorkDoneYesterday());
					row.createCell(10).setCellValue(task.getPlanForNextDay());
					row.createCell(11).setCellValue(task.getOtherWork());
					for (int c = 0; c < 12; c++) {
						row.getCell(c).setCellStyle(styleTableBody);
					}
					resource = task.getTaskOwner();

				}
			} else {
				Row row = dsrSheet.createRow((short) rowCount);
				row.createCell(0).setCellValue(
						new SimpleDateFormat("yyyy-MM-dd").format(project
								.getSelected_date()));
				row.createCell(1).setCellValue("NA");
				row.createCell(2).setCellValue("NA");
				row.createCell(3).setCellValue("NA");
				row.createCell(4).setCellValue("NA");
				row.createCell(5).setCellValue("NA");
				row.createCell(6).setCellValue("NA");
				row.createCell(7).setCellValue("NA");
				row.createCell(8).setCellValue("NA");
				row.createCell(9).setCellValue("NA");
				row.createCell(10).setCellValue("NA");
				row.createCell(11).setCellValue("NA");
				for (int c = 0; c < 12; c++) {
					row.getCell(c).setCellStyle(styleTableBody);
				}
			}

			columnIndexRemoved = new ArrayList<Integer>();
			Iterator<Cell> iter = row2.cellIterator();
			int counter = 0;
			while (iter.hasNext()) {
				String col_name = iter.next().toString();
				if (!colList.contains(col_name)) {
					columnIndexRemoved.add(counter);
					for (Row row : dsrSheet) {
						HSSFCell cellToRemove = (HSSFCell) row.getCell(counter);
						if (!(counter == dsrSheet.getLastRowNum())
								&& cellToRemove != null) {
							row.removeCell(cellToRemove);

						} else {
							if (!(counter == dsrSheet.getLastRowNum())) {
								continue;
							} else {
								break;
							}
						}
					}

				}
				counter++;
			}
			// data end

			final short border = CellStyle.BORDER_THIN;
			for (int c = 0; c < 12; c++) {
				if (!columnIndexRemoved.contains(c)) {

					row2.getCell(c).setCellStyle(styleTableHeader);
					CellRangeAddress region = new CellRangeAddress(2, // first
																		// row
																		// (0-based)
							3, // last row (0-based)
							c, // first column (0-based)
							c // last column (0-based)
					);
					dsrSheet.addMergedRegion(region);
					RegionUtil.setBorderBottom(border, region, dsrSheet, wb);
					RegionUtil.setBorderTop(border, region, dsrSheet, wb);
					RegionUtil.setBorderLeft(border, region, dsrSheet, wb);
					RegionUtil.setBorderRight(border, region, dsrSheet, wb);
					dsrSheet.autoSizeColumn(c);
					dsrSheet.setColumnWidth(c, dsrSheet.getColumnWidth(c) + 256);
				} else {
					dsrSheet.setColumnHidden(c, true);
				}
			}

			String reportDate = utility.convertDateToString(
					project.getSelected_date(), "MM-dd-yyyy");

			output = new File(getDownloadFilePath() + "\\report-"
					+ project.getProject_name() + "-" + reportDate + ".xls");

			FileOutputStream fileOut;
			fileOut = new FileOutputStream(output);
			wb.write(fileOut);
			log.info("Excel File created @ " + output.getAbsolutePath());
			fileOut.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		}

		return output;
	}

	private String getRole(int roleid) {
		String role = null;
		switch (roleid) {
		case 1:
			role = "None";
			break;
		case 2:
			role = "Dev";
			break;
		case 3:
			role = "QA";
			break;
		case 4:
			role = "UI";
			break;
		}
		return role;
	}

	private String getStatus(String statusCode) {
		String status = null;

		if (statusCode.equals("IP")) {
			status = "In Progress";
		} else if (statusCode.equals("WL")) {
			status = "On Hold";
		} else if (statusCode.equals("CM")) {
			status = "Completed";
		}
		return status;
	}

}
