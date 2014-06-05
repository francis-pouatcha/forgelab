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

import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilyParentFamily;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchInput;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchResult;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ProductFamilyLoader extends Service<List<ProductFamily>> {

	@Inject
	private ProductFamilyService remoteService;
	
	private HSSFWorkbook workbook;

	public ProductFamilyLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public ProductFamilyLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public ProductFamilyLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<ProductFamily> load() {
		List<ProductFamily> result = new ArrayList<ProductFamily>();
		result.addAll(remoteService.listAll().getResultList());
		
		HSSFSheet sheet = workbook.getSheet("ProductFamily");
		if(sheet==null){
			return result;
		}

		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			ProductFamily entity = new ProductFamily();
			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setName(cell.getStringCellValue().trim());
			
			if (StringUtils.isBlank(entity.getName()))
				continue;
			
			ProductFamilySearchInput searchInput = new ProductFamilySearchInput();
			searchInput.setEntity(entity);
			searchInput.setFieldNames(Arrays.asList("name"));
			ProductFamilySearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
				result.add(found.getResultList().iterator().next());
				continue;
			}

			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				String parentFamilyName = cell.getStringCellValue().trim();
				for (ProductFamily parentFamily : result) {
					if(parentFamilyName.equalsIgnoreCase(parentFamily.getName())){
						entity.setParentFamily(new ProductFamilyParentFamily(parentFamily));
					}
				}
			}

			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<ProductFamily>> createTask() {
		return new Task<List<ProductFamily>>() {
			@Override
			protected List<ProductFamily> call() throws Exception {
				return load();
			}
		};
	}
}
