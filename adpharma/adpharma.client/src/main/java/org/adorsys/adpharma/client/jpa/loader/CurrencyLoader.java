package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.currency.CurrencySearchInput;
import org.adorsys.adpharma.client.jpa.currency.CurrencySearchResult;
import org.adorsys.adpharma.client.jpa.currency.CurrencyService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CurrencyLoader extends Service<List<Currency>> {

	@Inject
	private CurrencyService remoteService;
	
	private HSSFWorkbook workbook;

	public CurrencyLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}

	private List<Currency> load() {
		HSSFSheet sheet = workbook.getSheet("Currency");
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		List<Currency> result = new ArrayList<Currency>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Currency entity = new Currency();
			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setName(cell.getStringCellValue().trim());
			
			if (StringUtils.isBlank(entity.getName()))
				continue;
			
			CurrencySearchInput searchInput = new CurrencySearchInput();
			searchInput.setEntity(entity);
			searchInput.setFieldNames(Arrays.asList("name"));
			CurrencySearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
				result.add(found.getResultList().iterator().next());
				continue;
			}

			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
			{
				String rate = cell.getStringCellValue().trim();
				BigDecimal decimal = new BigDecimal(rate);
				entity.setCfaEquivalent(decimal);
			}

			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<Currency>> createTask() {
		return new Task<List<Currency>>() {
			@Override
			protected List<Currency> call() throws Exception {
				return load();
			}
		};
	}
}
