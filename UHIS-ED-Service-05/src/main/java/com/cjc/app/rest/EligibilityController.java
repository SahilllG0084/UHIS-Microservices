package com.cjc.app.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cjc.app.dto.EligibilityResponseDTO;
import com.cjc.app.entity.EligibilityEntity;
import com.cjc.app.report.ExcelGenerator;
import com.cjc.app.report.PdfGenerator;
import com.cjc.app.service.EligibilityService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/ed/module")
public class EligibilityController {

	private EligibilityService elgserv;

	public EligibilityController(EligibilityService elgserv) {
		super();
		this.elgserv = elgserv;
	}

    @GetMapping("/check/{caseNumber}")
    public ResponseEntity<EligibilityResponseDTO> checkEligibility(@PathVariable String caseNumber) {
    	
        log.info("Received request to check eligibility for caseNumber: {}", caseNumber);

        EligibilityResponseDTO response = elgserv.checkEligibility(caseNumber);

        log.info("Eligibility check completed for caseNumber: {}", caseNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/excel", produces = "application/vnd.ms-excel")
    public void generateExcelReports(HttpServletResponse response) throws Exception {

        log.info("Excel report generation started");

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=Eligibility_Report.xls");

        List<EligibilityEntity> records = elgserv.getAllEligibleApplicants();
        
        log.debug("Total records fetched for Excel: {}", records.size());

        ExcelGenerator.generateExcel(records, response);

        log.info("Excel report generated successfully");
    }

    @GetMapping(value = "/pdf", produces = "application/pdf")
    public void generateEligibilityPdf(HttpServletResponse response) throws Exception {

        log.info("PDF report generation started");

        response.setContentType("application/pdf");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormat.format(new Date());

        String fileName = "UHIS_Eligibility_Report_" + currentDateTime + ".pdf";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        List<EligibilityEntity> records = elgserv.getAllEligibleApplicants();
        
        log.debug("Total records fetched for PDF: {}", records.size());

        PdfGenerator.generatePdf(records, response);

        log.info("PDF report generated successfully");
    }

    @GetMapping("/data")
    public ResponseEntity<Map<String, Long>> getEntireEdData() {

        log.info("Fetching overall eligibility data (Approved / Denied)");

        ResponseEntity<Map<String, Long>> response = elgserv.getEligibilityData();

        log.info("Eligibility data fetched successfully");
        return response;
    }
}
