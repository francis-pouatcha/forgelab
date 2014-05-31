package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleService;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigService;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigSource;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfigTarget;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ProductDetailConfigLoader extends Service<List<ProductDetailConfig>> {

	@Inject
	private ProductDetailConfigService remoteService;

	@Inject
	private ArticleService articleService;
	
	private HSSFWorkbook workbook;

	public ProductDetailConfigLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}

	private String progressText;
	private Label progressLabel;
	public ProductDetailConfigLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public ProductDetailConfigLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<ProductDetailConfig> loadArticles() {
		List<ProductDetailConfig> result = new ArrayList<ProductDetailConfig>();
		result.addAll(remoteService.listAll().getResultList());
		
		PGRunner pgRunner = new PGRunner(progressLabel);
		Platform.runLater(pgRunner.setText(progressText));
		

		HSSFSheet sheet = workbook.getSheet("ProductDetailConfig");
		if(sheet==null){
			return result;
		}
		
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		
		String working = progressText;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			final ProductDetailConfig entity = new ProductDetailConfig();
			
			working+= " .";
			if(working.length()>150) working=progressText;
			Platform.runLater(pgRunner.setText(working));
			
			String sourcePic =null;
			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				sourcePic = cell.getStringCellValue().trim();
			} else {
				continue;
			}

			String targetPic = null;
			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				targetPic = cell.getStringCellValue().trim();
			} else {
				continue;
			}

			for (ProductDetailConfig productDetailConfig : result) {
				if(StringUtils.equalsIgnoreCase(sourcePic, productDetailConfig.getSource().getPic())
						&&
					StringUtils.equalsIgnoreCase(targetPic, productDetailConfig.getTarget().getPic())){
					continue;
				}
			}
			ArticleSearchInput searchInput = new ArticleSearchInput();
			Article article = new Article();
			article.setPic(sourcePic);
			searchInput.setEntity(article );
			searchInput.getFieldNames().add("pic");
			ArticleSearchResult found = articleService.findBy(searchInput);
			if (found.getResultList().isEmpty()){
				continue;
			}
			article = found.getResultList().iterator().next();
			ProductDetailConfigSource productDetailConfigSource = new ProductDetailConfigSource(article);
			entity.setSource(productDetailConfigSource );
			searchInput = new ArticleSearchInput();
			article = new Article();
			article.setPic(targetPic);
			searchInput.setEntity(article );
			searchInput.getFieldNames().add("pic");
			found = articleService.findBy(searchInput);
			if (found.getResultList().isEmpty()){
				continue;
			}
			article = found.getResultList().iterator().next();
			ProductDetailConfigTarget productDetailConfigTarget = new ProductDetailConfigTarget(article);
			entity.setTarget(productDetailConfigTarget);

			Platform.runLater(pgRunner.setText(progressText + " : " + entity.getSource().getArticleName() + " -> " + entity.getTarget().getArticleName()));

			cell = row.getCell(2);
			if (cell != null)
				entity.setActive(1==cell.getNumericCellValue());

			cell = row.getCell(3);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setTargetQuantity(decimal);
			}

			cell = row.getCell(4);
			if (cell != null)
			{
				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setSalesPrice(decimal);
			}

			entity.setRecordingDate(new GregorianCalendar());

			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<ProductDetailConfig>> createTask() {
		return new Task<List<ProductDetailConfig>>() {
			@Override
			protected List<ProductDetailConfig> call() throws Exception {
				return loadArticles();
			}
		};
	}
}
