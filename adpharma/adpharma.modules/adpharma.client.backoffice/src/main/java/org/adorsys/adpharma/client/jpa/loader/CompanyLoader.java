package org.adorsys.adpharma.client.jpa.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.company.CompanySearchInput;
import org.adorsys.adpharma.client.jpa.company.CompanySearchResult;
import org.adorsys.adpharma.client.jpa.company.CompanyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CompanyLoader extends Service<List<Company>> {

	@Inject
	private CompanyService remoteService;
	
	private HSSFWorkbook workbook;

	public CompanyLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public CompanyLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public CompanyLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<Company> loadCompanies() {
		List<Company> result = new ArrayList<Company>();
		result.addAll(remoteService.listAll().getResultList());
		
		HSSFSheet sheet = workbook.getSheet("Company");
		if(sheet==null){
			return result;
		}

		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Company company = new Company();
			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setDisplayName(cell.getStringCellValue().trim());

			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setPhone(cell.getStringCellValue().trim());

			cell = row.getCell(2);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setFax(cell.getStringCellValue().trim());

			cell = row.getCell(3);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setSiteManager(cell.getStringCellValue().trim());

			cell = row.getCell(4);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setEmail(cell.getStringCellValue().trim());

			cell = row.getCell(5);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setStreet(cell.getStringCellValue().trim());

			cell = row.getCell(6);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setZipCode(cell.getStringCellValue().trim());

			cell = row.getCell(7);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setCity(cell.getStringCellValue().trim());

			cell = row.getCell(8);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setCountry(cell.getStringCellValue().trim());

			cell = row.getCell(9);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setSiteInternet(cell.getStringCellValue().trim());

			cell = row.getCell(10);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setMobile(cell.getStringCellValue().trim());

			cell = row.getCell(11);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				company.setRegisterNumber(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(company.getRegisterNumber()))
				continue;

			company.setRecordingDate(new GregorianCalendar());

			CompanySearchInput searchInput = new CompanySearchInput();
			searchInput.setEntity(company);
			searchInput.setFieldNames(Arrays.asList("registerNumber"));
			CompanySearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
//				result.add(found.getResultList().iterator().next());
				continue;
			} else {
				result.add(remoteService.create(company));
			}
		}
		return result;
	}

	@Override
	protected Task<List<Company>> createTask() {
		return new Task<List<Company>>() {
			@Override
			protected List<Company> call() throws Exception {
				try {
					return loadCompanies();
				} catch (Exception ex){
					throw ex;
				}
			}
		};
	}
}
