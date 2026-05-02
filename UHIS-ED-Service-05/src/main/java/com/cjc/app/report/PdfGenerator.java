package com.cjc.app.report;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import com.cjc.app.entity.EligibilityEntity;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class PdfGenerator {

	public static void generatePdf(List<EligibilityEntity> elist, HttpServletResponse response) throws Exception {

		Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
		Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
		Font reportFont = new Font(Font.HELVETICA, 14, Font.BOLDITALIC, Color.BLACK);
		Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 9);
		Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);


		Paragraph companyName = new Paragraph("Unified Health Insurance System", titleFont);
		companyName.setAlignment(Element.ALIGN_CENTER);
		companyName.setSpacingBefore(10);
		companyName.setSpacingAfter(5);
		document.add(companyName);

		Paragraph companyCode = new Paragraph("Project Code : UHIS-IND-2026", normalFont);
		companyCode.setAlignment(Element.ALIGN_CENTER);
		companyCode.setSpacingAfter(10);
		document.add(companyCode);

		Paragraph address = new Paragraph(
				"Address:\n"
				+ "UHIS Health Department,\n"
				+ "2450 Wellness Parkway, Suite 410,\n"
				+ "Pune, Maharashtra - 411001,\n"
				+ "India",
				normalFont);
		address.setSpacingBefore(5);
		address.setSpacingAfter(10);
		document.add(address);

		Paragraph reportTitle = new Paragraph("Eligibility Report", reportFont);
		reportTitle.setAlignment(Element.ALIGN_CENTER);
		reportTitle.setSpacingAfter(10);
		document.add(reportTitle);


		List<String> columns = Arrays.asList(
				"Eligibility Id",
				"Case Number",
				"Plan Name",
				"Plan Status",
				"Full Name",
				"Benefit Amount",
				"Denial Reason",
				"Start Date",
				"End Date",
				"SSN No");

		PdfPTable table = new PdfPTable(columns.size());
		table.setWidthPercentage(100);
		table.setSpacingBefore(10);
		table.setHeaderRows(1);

		table.setWidths(new float[] { 2f, 3f, 3f, 2.5f, 3f, 2.5f, 5f, 2.5f, 2.5f, 3f });

		for (String column : columns) {
			PdfPCell cell = new PdfPCell(new Phrase(column, headerFont));
			cell.setBackgroundColor(Color.YELLOW);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setPadding(4);
			table.addCell(cell);
		}

		for (EligibilityEntity e : elist) {

			addCell(table, e.getEdId() != null ? String.valueOf(e.getEdId()) : "N/A", dataFont);
			addCell(table, e.getCaseNumber(), dataFont);
			addCell(table, e.getPlanName(), dataFont);
			addCell(table, e.getPlanStatus(), dataFont);
			addCell(table, e.getFullName(), dataFont);

			if (e.getBenefitAmt() != null) {
				addCell(table, String.valueOf(e.getBenefitAmt()), dataFont);
			} else {
				addCell(table, "0.0", dataFont);
			}

			addCell(table, e.getDenialReason(), dataFont);
			addCell(table, e.getStartDate() != null ? String.valueOf(e.getStartDate()) : "N/A", dataFont);
			addCell(table, e.getEndDate() != null ? String.valueOf(e.getEndDate()) : "N/A", dataFont);
			addCell(table, e.getSsnNo() != null ? String.valueOf(e.getSsnNo()) : "N/A", dataFont);
		}

		document.add(table);
		document.close();
	}

	private static void addCell(PdfPTable table, String value, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(value != null ? value : "N/A", font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPadding(3);
		table.addCell(cell);
	}
}
