package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import jxl.CellType;

import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchInput;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategorySearchResult;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CustomerCategoryLoader extends Service<List<CustomerCategory>> {

	@Inject
	private CustomerCategoryService remoteService;
	
	private HSSFWorkbook workbook;

	public CustomerCategoryLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public CustomerCategoryLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public CustomerCategoryLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<CustomerCategory> load() {
		List<CustomerCategory> result = new ArrayList<CustomerCategory>();
		result.addAll(remoteService.listAll().getResultList());
		
		HSSFSheet sheet = workbook.getSheet("CustomerCategory");
		if(sheet==null){
			return result;
		}
		
		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			CustomerCategory entity = new CustomerCategory();

			Cell cell = row.getCell(0);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setName(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(entity.getName()))
				continue;
			CustomerCategorySearchInput sectionSearchInput = new CustomerCategorySearchInput();
			sectionSearchInput.setEntity(entity);
			sectionSearchInput.getFieldNames().add("name");
			
			CustomerCategorySearchResult found = remoteService.findBy(sectionSearchInput);
			if (!found.getResultList().isEmpty()){
//				result.add(found.getResultList().iterator().next());
				continue;
			}
			
			cell = row.getCell(1);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			if (cell != null)
			{
				double numericCellValue = cell.getNumericCellValue();
				BigDecimal decimal = new BigDecimal(numericCellValue);
				entity.setDiscountRate(decimal);
			}

			cell = row.getCell(2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setDescription(cell.getStringCellValue().trim());

			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<CustomerCategory>> createTask() {
		return new Task<List<CustomerCategory>>() {
			@Override
			protected List<CustomerCategory> call() throws Exception {
				return load();
			}
		};
	}
}
