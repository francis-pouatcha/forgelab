package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleAgency;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSection;
import org.adorsys.adpharma.client.jpa.article.ArticleService;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ArticleLoader extends Service<List<Article>> {

	@Inject
	private ArticleService remoteService;
	
	private HSSFWorkbook workbook;
	private DataMap dataMap;

	public ArticleLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}

	public ArticleLoader setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
		return this;
	}

	private List<Article> loadAgencies() {
		HSSFSheet sheet = workbook.getSheet("Article");
		
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		
		List<Article> result = new ArrayList<Article>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Article entity = new Article();

			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setArticleName(cell.getStringCellValue().trim());

			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setPic(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(entity.getPic()))
				continue;

			ArticleSearchInput searchInput = new ArticleSearchInput();
			searchInput.setEntity(entity);
			searchInput.getFieldNames().add("pic");
			
			ArticleSearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
				result.add(found.getResultList().iterator().next());
				continue;
			}

			cell = row.getCell(2);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setManufacturer(cell.getStringCellValue().trim());

			cell = row.getCell(3);
			if (cell != null)
				entity.setActive(1==cell.getNumericCellValue());

			cell = row.getCell(4);
			if (cell != null)
				entity.setAuthorizedSale(1==cell.getNumericCellValue());

			cell = row.getCell(5);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setMaxStockQty(decimal);
			}

			cell = row.getCell(6);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setQtyInStock(decimal);
			}

			cell = row.getCell(7);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setPppu(decimal);
			}

			cell = row.getCell(8);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setSppu(decimal);
			}

			cell = row.getCell(9);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setMaxDiscountRate(decimal);
			}

			cell = row.getCell(10);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setTotalStockPrice(decimal);
			}

			cell = row.getCell(11);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
			{
				String date = cell.getStringCellValue().trim();
				Date parseDate;
				try {
					parseDate = DateUtils.parseDate(date, "dd-MM-yyyy");
				} catch (ParseException e) {
					throw new IllegalStateException(e);
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(parseDate);
				entity.setLastStockEntry(calendar);
			}

			cell = row.getCell(12);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
			{
				String date = cell.getStringCellValue().trim();
				Date parseDate;
				try {
					parseDate = DateUtils.parseDate(date, "dd-MM-yyyy");
				} catch (ParseException e) {
					throw new IllegalStateException(e);
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(parseDate);
				entity.setLastOutOfStock(calendar);
			}

			entity.setRecordingDate(new GregorianCalendar());

			cell = row.getCell(14);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				String sectionCode = cell.getStringCellValue().trim();
				List<Section> sections = dataMap.getSections();
				Section section = null;
				for (Section s : sections) {
					if(sectionCode.equalsIgnoreCase(s.getSectionCode())){
						section = s;
						break;
					}
				}
				if(section!=null){
					entity.setSection(new ArticleSection(section));
				} else {
					throw new IllegalStateException("No section found with section code: " + sectionCode);
				}
			} else {
				throw new IllegalStateException("No section code provided for article with pic: " + entity.getPic());
			}

			cell = row.getCell(18);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				String agencyNumber = cell.getStringCellValue().trim();
				List<Agency> agencies = dataMap.getAgencies();
				Agency agency = null;
				for (Agency ag : agencies) {
					if(agencyNumber.equalsIgnoreCase(ag.getAgencyNumber())){
						agency = ag;
						break;
					}
				}
				if(agency!=null){
					entity.setAgency(new ArticleAgency(agency));
				} else {
					throw new IllegalStateException("No agency found with agency number: " + agencyNumber);
				}
			} else {
				throw new IllegalStateException("No agency number provided for article with pic: " + entity.getPic());
			}
			
			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<Article>> createTask() {
		return new Task<List<Article>>() {
			@Override
			protected List<Article> call() throws Exception {
				return loadAgencies();
			}
		};
	}
}
