package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import jxl.CellType;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleAgency;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSection;
import org.adorsys.adpharma.client.jpa.article.ArticleService;
import org.adorsys.adpharma.client.jpa.article.ArticleVat;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jboss.weld.executor.ExecutorServicesFactory;

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
	private String progressText;
	private Label progressLabel;
	public ArticleLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public ArticleLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<Article> loadArticles() {
		List<Article> result = new ArrayList<Article>();
		result.addAll(remoteService.listAll().getResultList());

		PGRunner pgRunner = new PGRunner(progressLabel);
		Platform.runLater(pgRunner.setText(progressText));


		HSSFSheet sheet = workbook.getSheet("Article");
		if(sheet==null){
			return result;
		}

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();

		String working = progressText;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			final Article entity = new Article();

			working+= " .";
			if(working.length()>150) working=progressText;
			Platform.runLater(pgRunner.setText(working));

			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setArticleName(cell.getStringCellValue().trim());



			cell = row.getCell(1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				entity.setPic(cell.getStringCellValue().trim());
				if(StringUtils.isBlank(entity.getArticleName()))
					entity.setArticleName(entity.getPic());
			}

			if (StringUtils.isBlank(entity.getPic()))
				continue;
			
			cell = row.getCell(14);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				String sc = cell.getStringCellValue().trim();
				List<Section> ss = dataMap.getSections();
				Section sec = null;
				for (Section s : ss) {
					if(sc.equalsIgnoreCase(s.getSectionCode())){
						sec = s;
						break;
					}
				}
				if(sec!=null){
					entity.setSection(new ArticleSection(sec));
				} else {
					continue ;
				}
			}else {
				continue ;
			}

			ArticleSearchInput searchInput = new ArticleSearchInput();
			searchInput.setEntity(entity);
			searchInput.getFieldNames().add("section");
			searchInput.getFieldNames().add("pic");
//check heare
			ArticleSearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
				continue;
			}
			Platform.runLater(pgRunner.setText(progressText + " : " + entity.getArticleName()));

			cell = row.getCell(2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setManufacturer(cell.getStringCellValue().trim());

			cell = row.getCell(3);
			if (cell != null)
				entity.setActive(Boolean.TRUE);

			cell = row.getCell(4);
			if (cell != null)
				entity.setAuthorizedSale(Boolean.TRUE);

			cell = row.getCell(5);
			if (cell != null)
			{
				//				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setMaxStockQty(BigDecimal.valueOf(50));
			}

			cell = row.getCell(6);
			if (cell != null)
			{
				//				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setQtyInStock(BigDecimal.ZERO);
			}

			cell = row.getCell(7);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setPppu(decimal);
			}

			cell = row.getCell(8);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setSppu(decimal);
			}

			cell = row.getCell(9);
			if (cell != null)
			{
				//				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setMaxDiscountRate(BigDecimal.valueOf(5));
			}

			cell = row.getCell(10);
			if (cell != null)
			{
				//				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setTotalStockPrice(BigDecimal.ZERO);
			}else {
				entity.setTotalStockPrice(BigDecimal.ZERO);
			}

			cell = row.getCell(11);
			//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
			//			{
			//				String date = cell.getStringCellValue().trim();
			//				Date parseDate;
			//				try {
			//					parseDate = DateUtils.parseDate(date, "dd-MM-yyyy");
			//				} catch (ParseException e) {
			//					throw new IllegalStateException(e);
			//				}
			//				Calendar calendar = Calendar.getInstance();
			//				calendar.setTime(parseDate);
			//				entity.setLastStockEntry(calendar);
			//			}

			cell = row.getCell(12);
			//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
			//			{
			//				String date = cell.getStringCellValue().trim();
			//				Date parseDate;
			//				try {
			//					parseDate = DateUtils.parseDate(date, "dd-MM-yyyy");
			//				} catch (ParseException e) {
			//					throw new IllegalStateException(e);
			//				}
			//				Calendar calendar = Calendar.getInstance();
			//				calendar.setTime(parseDate);
			//				entity.setLastOutOfStock(calendar);
			//			}

			entity.setRecordingDate(new GregorianCalendar());

			cell = row.getCell(14);
			cell.setCellType(Cell.CELL_TYPE_STRING);
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
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String agencyNumber = "AG-0001" ;
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				agencyNumber = cell.getStringCellValue().trim();
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

			//			cell = row.getCell(19);

			cell = row.getCell(20);
			//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
			//				String vatNamme = cell.getStringCellValue().trim();
			//				List<VAT> vats = dataMap.getVats();
			//				VAT vat = null;
			//				for (VAT v : vats) {
			//					if(vatNamme.equals(v.getName())){
			//						vat=v;
			//						break;
			//					}
			//				}
			//				if(vat!=null){
			//					entity.setVat(new ArticleVat(vat));
			//				} else {
			//					throw new IllegalStateException("No vat found with vat name: " + vatNamme);
			//				}
			//			} else {
			//			}
			VAT vat = dataMap.getVats().iterator().next();
			entity.setVat(new ArticleVat(vat));

			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<Article>> createTask() {
		return new Task<List<Article>>() {
			@Override
			protected List<Article> call() throws Exception {
				return loadArticles();
			}
		};
	}
}
