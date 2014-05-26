package org.adorsys.adpharma.client.jpa.loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.employer.EmployerSearchInput;
import org.adorsys.adpharma.client.jpa.employer.EmployerSearchResult;
import org.adorsys.adpharma.client.jpa.employer.EmployerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class EmployerLoader extends Service<List<Employer>> {

	@Inject
	private EmployerService remoteService;
	
	private HSSFWorkbook workbook;

	public EmployerLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public EmployerLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public EmployerLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<Employer> load() {
		List<Employer> result = new ArrayList<Employer>();
		result.addAll(remoteService.listAll().getResultList());
		
		HSSFSheet sheet = workbook.getSheet("Employer");
		if(sheet==null){
			return result;
		}
		
		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Employer entity = new Employer();

			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setName(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(entity.getName()))
				continue;
			EmployerSearchInput sectionSearchInput = new EmployerSearchInput();
			sectionSearchInput.setEntity(entity);
			sectionSearchInput.getFieldNames().add("name");
			
			EmployerSearchResult found = remoteService.findBy(sectionSearchInput);
			if (!found.getResultList().isEmpty()){
//				result.add(found.getResultList().iterator().next());
				continue;
			}
			
			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setPhone(cell.getStringCellValue().trim());

			cell = row.getCell(2);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setZipCode(cell.getStringCellValue().trim());

			cell = row.getCell(3);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setCity(cell.getStringCellValue().trim());

			cell = row.getCell(4);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setCountry(cell.getStringCellValue().trim());

			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<Employer>> createTask() {
		return new Task<List<Employer>>() {
			@Override
			protected List<Employer> call() throws Exception {
				return load();
			}
		};
	}
}
