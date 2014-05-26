package org.adorsys.adpharma.client.jpa.loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchInput;
import org.adorsys.adpharma.client.jpa.supplier.SupplierSearchResult;
import org.adorsys.adpharma.client.jpa.supplier.SupplierService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SupplierLoader extends Service<List<Supplier>> {

	@Inject
	private SupplierService remoteService;
	
	private HSSFWorkbook workbook;

	public SupplierLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public SupplierLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public SupplierLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<Supplier> load() {
		List<Supplier> result = new ArrayList<Supplier>();
		result.addAll(remoteService.listAll().getResultList());
		HSSFSheet sheet = workbook.getSheet("Supplier");
		if(sheet==null) return result;
		
		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Supplier entity = new Supplier();
			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setName(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(entity.getName()))
				continue;

			SupplierSearchInput searchInput = new SupplierSearchInput();
			searchInput.setEntity(entity);
			searchInput.setFieldNames(Arrays.asList("name"));
			SupplierSearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
//				result.add(found.getResultList().iterator().next());
				continue;
			}
			
			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setMobile(cell.getStringCellValue().trim());

			cell = row.getCell(2);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setFax(cell.getStringCellValue().trim());

			cell = row.getCell(3);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setEmail(cell.getStringCellValue().trim());

			cell = row.getCell(4);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setZipCode(cell.getStringCellValue().trim());

			cell = row.getCell(5);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setCountry(cell.getStringCellValue().trim());

			cell = row.getCell(6);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setInternetSite(cell.getStringCellValue().trim());

			cell = row.getCell(7);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setResponsable(cell.getStringCellValue().trim());

			cell = row.getCell(8);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setRevenue(cell.getStringCellValue().trim());

			cell = row.getCell(9);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setTaxIdNumber(cell.getStringCellValue().trim());

			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<Supplier>> createTask() {
		return new Task<List<Supplier>>() {
			@Override
			protected List<Supplier> call() throws Exception {
				return load();
			}
		};
	}
}
