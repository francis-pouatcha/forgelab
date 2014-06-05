package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.vat.VATSearchInput;
import org.adorsys.adpharma.client.jpa.vat.VATSearchResult;
import org.adorsys.adpharma.client.jpa.vat.VATService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class VATLoader extends Service<List<VAT>> {

	@Inject
	private VATService remoteService;
	
	private HSSFWorkbook workbook;

	public VATLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public VATLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public VATLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<VAT> load() {
		List<VAT> result = new ArrayList<VAT>();
		result.addAll(remoteService.listAll().getResultList());
		HSSFSheet sheet = workbook.getSheet("VAT");
		if(sheet==null)return result;
		
		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			VAT entity = new VAT();
			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setName(cell.getStringCellValue().trim());
			
			if (StringUtils.isBlank(entity.getName()))
				continue;
			
			VATSearchInput searchInput = new VATSearchInput();
			searchInput.setEntity(entity);
			searchInput.setFieldNames(Arrays.asList("name"));
			VATSearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
//				result.add(found.getResultList().iterator().next());
				continue;
			}
			cell = row.getCell(1);
			if (cell != null)
			{
				double numericCellValue = cell.getNumericCellValue();
				BigDecimal decimal = new BigDecimal(numericCellValue);
				entity.setRate(decimal);
			}

			cell = row.getCell(2);
			if (cell != null)
				entity.setActive(1==cell.getNumericCellValue());
			
			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<VAT>> createTask() {
		return new Task<List<VAT>>() {
			@Override
			protected List<VAT> call() throws Exception {
				return load();
			}
		};
	}
}
