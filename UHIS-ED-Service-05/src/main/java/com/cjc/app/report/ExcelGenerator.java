package com.cjc.app.report;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;
import com.cjc.app.entity.EligibilityEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

@Component
public class ExcelGenerator {

	public static void generateExcel(List<EligibilityEntity> elist, HttpServletResponse response) throws IOException {

		HSSFWorkbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Eligibility_Report");

		Font titleFont = workbook.createFont();
		titleFont.setBold(true);
		titleFont.setFontHeightInPoints((short) 14);

		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(titleFont);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setWrapText(true);

		CellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setAlignment(HorizontalAlignment.CENTER);
		dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		dataStyle.setBorderTop(BorderStyle.THIN);
		dataStyle.setBorderBottom(BorderStyle.THIN);
		dataStyle.setBorderLeft(BorderStyle.THIN);
		dataStyle.setBorderRight(BorderStyle.THIN);
		dataStyle.setWrapText(true);

		CellStyle amountStyle = workbook.createCellStyle();
		amountStyle.cloneStyleFrom(dataStyle);
		DataFormat format = workbook.createDataFormat();
		amountStyle.setDataFormat(format.getFormat("0.00"));

		Row titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(22);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue("Unified Health Insurance System - Eligibility Report");
		titleCell.setCellStyle(titleStyle);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

		Row headerRow = sheet.createRow(2);
		headerRow.setHeightInPoints(25);

		String[] headers = { "Eligibility Id", "Case Number", "Plan Name", "Plan Status", "Full Name", "Benefit Amount",
				"Denial Reason", "Start Date", "End Date", "SSN No" };

		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}

		int rowNum = 3;

		for (EligibilityEntity e : elist) {
			Row row = sheet.createRow(rowNum++);
			row.setHeightInPoints(20);

			Cell cell0 = row.createCell(0);
			cell0.setCellValue(e.getEdId() != null ? e.getEdId() : 0);
			cell0.setCellStyle(dataStyle);

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(e.getCaseNumber() != null ? e.getCaseNumber() : "N/A");
			cell1.setCellStyle(dataStyle);

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(e.getPlanName() != null ? e.getPlanName() : "N/A");
			cell2.setCellStyle(dataStyle);

			Cell cell3 = row.createCell(3);
			cell3.setCellValue(e.getPlanStatus() != null ? e.getPlanStatus() : "N/A");
			cell3.setCellStyle(dataStyle);

			Cell cell4 = row.createCell(4);
			cell4.setCellValue(e.getFullName() != null ? e.getFullName() : "N/A");
			cell4.setCellStyle(dataStyle);

			Cell cell5 = row.createCell(5);
			if (e.getBenefitAmt() != null) {
				cell5.setCellValue(e.getBenefitAmt().doubleValue());
			} else {
				cell5.setCellValue(0.0);
			}
			cell5.setCellStyle(amountStyle);

			Cell cell6 = row.createCell(6);
			cell6.setCellValue(e.getDenialReason() != null ? e.getDenialReason() : "N/A");
			cell6.setCellStyle(dataStyle);

			Cell cell7 = row.createCell(7);
			cell7.setCellValue(e.getStartDate() != null ? e.getStartDate().toString() : "N/A");
			cell7.setCellStyle(dataStyle);

			Cell cell8 = row.createCell(8);
			cell8.setCellValue(e.getEndDate() != null ? e.getEndDate().toString() : "N/A");
			cell8.setCellStyle(dataStyle);

			Cell cell9 = row.createCell(9);
			cell9.setCellValue(e.getSsnNo() != null ? String.valueOf(e.getSsnNo()) : "N/A");
			cell9.setCellStyle(dataStyle);
		}

		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
		}

		workbook.write(response.getOutputStream());
		workbook.close();
	}
}
