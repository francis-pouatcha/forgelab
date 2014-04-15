package org.adorsys.adpharma.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.company.CompanyService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class AdpharmaClientTest {

	@Test
	public void test() throws IOException {
		
//		InputStream resourceAsStream = AdpharmaClientTest.class.getResourceAsStream("/adpharma_data.xls");
//		HSSFWorkbook workbook = new HSSFWorkbook(resourceAsStream);
//		HSSFSheet companySheet = workbook.getSheet("Company");
//		createCompany(companySheet);
//		HSSFSheet agencySheet = workbook.getSheet("Agency");
//		HSSFSheet supplierSheet = workbook.getSheet("Supplier");
	}

	@Inject
	private CompanyService companyService;
	
	private void createAgency(HSSFSheet agencySheet){
		Company company = new Company();
		Iterator<Row> rowIterator = agencySheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();

			Cell cell = row.getCell(0);
			if(cell!=null)
				company.setDisplayName(cell.getStringCellValue());
			
			cell = row.getCell(1);
			if(cell!=null)
				company.setPhone(cell.getStringCellValue());
			
			cell = row.getCell(2);
			if(cell!=null)
				company.setFax(cell.getStringCellValue());
			
			cell = row.getCell(3);
			if(cell!=null)
				company.setSiteManager(cell.getStringCellValue());
			
			cell = row.getCell(4);
			if(cell!=null)
				company.setEmail(cell.getStringCellValue());
			
			cell = row.getCell(5);
			if(cell!=null)
				company.setStreet(cell.getStringCellValue());
			
			cell = row.getCell(6);
			if(cell!=null)
				company.setZipCode(cell.getStringCellValue());
			
			cell = row.getCell(7);
			if(cell!=null)
				company.setCity(cell.getStringCellValue());
			
			cell = row.getCell(8);
			if(cell!=null)
				company.setCountry(cell.getStringCellValue());
			
			cell = row.getCell(9);
			if(cell!=null)
				company.setSiteInternet(cell.getStringCellValue());
			
			cell = row.getCell(10);
			if(cell!=null)
				company.setMobile(cell.getStringCellValue());
			
			cell = row.getCell(11);
			if(cell!=null)
				company.setRegisterNumber(cell.getStringCellValue());

			company.setRecordingDate(new GregorianCalendar());
			
			companyService.create(company);
		}
	}
	
	private void createCompany(HSSFSheet companySheet) {
		Company company = new Company();
		Iterator<Row> rowIterator = companySheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();

			Cell cell = row.getCell(0);
			if(cell!=null)
				company.setDisplayName(cell.getStringCellValue());
			
			cell = row.getCell(1);
			if(cell!=null)
				company.setPhone(cell.getStringCellValue());
			
			cell = row.getCell(2);
			if(cell!=null)
				company.setFax(cell.getStringCellValue());
			
			cell = row.getCell(3);
			if(cell!=null)
				company.setSiteManager(cell.getStringCellValue());
			
			cell = row.getCell(4);
			if(cell!=null)
				company.setEmail(cell.getStringCellValue());
			
			cell = row.getCell(5);
			if(cell!=null)
				company.setStreet(cell.getStringCellValue());
			
			cell = row.getCell(6);
			if(cell!=null)
				company.setZipCode(cell.getStringCellValue());
			
			cell = row.getCell(7);
			if(cell!=null)
				company.setCity(cell.getStringCellValue());
			
			cell = row.getCell(8);
			if(cell!=null)
				company.setCountry(cell.getStringCellValue());
			
			cell = row.getCell(9);
			if(cell!=null)
				company.setSiteInternet(cell.getStringCellValue());
			
			cell = row.getCell(10);
			if(cell!=null)
				company.setMobile(cell.getStringCellValue());
			
			cell = row.getCell(11);
			if(cell!=null)
				company.setRegisterNumber(cell.getStringCellValue());

			company.setRecordingDate(new GregorianCalendar());
			
			companyService.create(company);
		}
	}

}
