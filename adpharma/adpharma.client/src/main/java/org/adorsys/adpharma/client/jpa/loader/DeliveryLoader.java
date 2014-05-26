package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleVat;
import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryCurrency;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryReceivingAgency;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySearchInput;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySearchResult;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryService;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySupplier;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryVat;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemArticle;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemService;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DeliveryLoader extends Service<List<Delivery>> {

	@Inject
	private DeliveryService remoteService;
	
	@Inject
	private DeliveryItemService deliveryItemService;

	private HSSFWorkbook workbook;
	private DataMap dataMap;

	public DeliveryLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}

	public DeliveryLoader setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public DeliveryLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public DeliveryLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<Delivery> load() {
		List<Delivery> result = new ArrayList<Delivery>();
		HSSFSheet sheet = workbook.getSheet("Delivery");
		if(sheet==null) return result;

		PGRunner pgRunner = new PGRunner(progressLabel);
		Platform.runLater(pgRunner.setText(progressText));
//		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		
		Delivery currentDelivery = null;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell cell = row.getCell(0);
			if (cell == null || StringUtils.isBlank(cell.getStringCellValue())){
				if(currentDelivery==null) continue;
				// check if item row.
				Cell itemCell = row.getCell(17);
				if (itemCell == null || StringUtils.isBlank(itemCell.getStringCellValue())){
					continue;
				} else {
					if(DocumentProcessingState.CLOSED.equals(currentDelivery.getDeliveryProcessingState())) continue;					
					DeliveryItem deliveryItem = new DeliveryItem();
					// add item
//					String deliveryNumber = itemCell.getStringCellValue();
					
//					itemCell = row.getCell(18);
					deliveryItem.setCreationDate(new GregorianCalendar());
					
					itemCell = row.getCell(19);
					if (itemCell != null && StringUtils.isNotBlank(itemCell.getStringCellValue()))
						deliveryItem.setInternalPic(itemCell.getStringCellValue().trim());
						
					itemCell = row.getCell(20);
					if (itemCell != null && StringUtils.isNotBlank(itemCell.getStringCellValue()))
						deliveryItem.setMainPic(itemCell.getStringCellValue().trim());
					
					itemCell = row.getCell(21);
					if (itemCell != null && StringUtils.isNotBlank(itemCell.getStringCellValue()))
						deliveryItem.setSecondaryPic(itemCell.getStringCellValue().trim());

					itemCell = row.getCell(22);
					if (itemCell != null && StringUtils.isNotBlank(itemCell.getStringCellValue())){
						deliveryItem.setArticleName(itemCell.getStringCellValue().trim());
						Platform.runLater(pgRunner.setText(progressText + " : " + currentDelivery.getDeliveryNumber() + " : " + deliveryItem.getArticleName()));
					}

					itemCell = row.getCell(23);
					Article article = null;
					if (itemCell != null && StringUtils.isNotBlank(itemCell.getStringCellValue())){
						String articlePic = itemCell.getStringCellValue().trim();
						List<Article> articles = dataMap.getArticles();
						for (Article a : articles) {
							if(articlePic.equals(a.getPic())){
								article = a;
							}
						}
						if(article!=null){
							deliveryItem.setArticle(new DeliveryItemArticle(article));
						} else {
							throw new IllegalStateException("Missing article for delivery item with pic: " + articlePic);
						}
					} else {
						throw new IllegalStateException("Missing article number for delivery item: " + deliveryItem.getMainPic());
					}

					itemCell = row.getCell(24);
					if (itemCell != null && StringUtils.isNotBlank(itemCell.getStringCellValue()))
					{
						String date = itemCell.getStringCellValue().trim();
						Date parseDate;
						try {
							parseDate = DateUtils.parseDate(date, "dd-MM-yyyy");
						} catch (ParseException e) {
							throw new IllegalStateException(e);
						}
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(parseDate);
						deliveryItem.setExpirationDate(calendar);
					}

					itemCell = row.getCell(25);
					if (itemCell != null)
					{
						BigDecimal decimal = new BigDecimal(itemCell.getNumericCellValue());
						if(BigDecimal.ZERO.equals(decimal))
							continue;
						deliveryItem.setQtyOrdered(decimal);
					}
					
					itemCell = row.getCell(26);
					if (itemCell != null)
					{
						BigDecimal decimal = new BigDecimal(itemCell.getNumericCellValue());
						deliveryItem.setAvailableQty(decimal);
					}

					itemCell = row.getCell(27);
					if (itemCell != null)
					{
						BigDecimal decimal = new BigDecimal(itemCell.getNumericCellValue());
						deliveryItem.setFreeQuantity(decimal);
					}

//					itemCell = row.getCell(28);
					
					itemCell = row.getCell(29);
					if (itemCell != null)
					{
						BigDecimal decimal = new BigDecimal(itemCell.getNumericCellValue());
						if(BigDecimal.ZERO.equals(decimal))
							continue;
						deliveryItem.setStockQuantity(decimal);
					}

					itemCell = row.getCell(30);
					if (itemCell != null)
					{
						BigDecimal decimal = new BigDecimal(itemCell.getNumericCellValue());
						deliveryItem.setSalesPricePU(decimal);
					}

					itemCell = row.getCell(31);
					if (itemCell != null)
					{
						BigDecimal decimal = new BigDecimal(itemCell.getNumericCellValue());
						deliveryItem.setPurchasePricePU(decimal);
					}
				
					BigDecimal totalPurchasePrice = deliveryItem.getStockQuantity()
														.subtract(deliveryItem.getFreeQuantity())
														.multiply(deliveryItem.getPurchasePricePU());
					deliveryItem.setTotalPurchasePrice(totalPurchasePrice);
					currentDelivery.setAmountAfterTax(currentDelivery.getAmountAfterTax().add(totalPurchasePrice));
//					itemCell = row.getCell(32);
//					if (itemCell != null)
//					{
//						BigDecimal decimal = new BigDecimal(itemCell.getNumericCellValue());
//					}
					
					ArticleVat vat = article.getVat();
					BigDecimal purchasePriceBeforTax = totalPurchasePrice.divide(BigDecimal.ONE.add(VAT.getRawRate(vat.getRate())), 4, RoundingMode.HALF_EVEN);
					currentDelivery.setAmountBeforeTax(currentDelivery.getAmountBeforeTax().add(purchasePriceBeforTax));
					currentDelivery.setAmountVat(currentDelivery.getAmountVat().add(totalPurchasePrice.subtract(purchasePriceBeforTax)));
					
					deliveryItem.setDelivery(new DeliveryItemDelivery(currentDelivery));
					
					deliveryItem = deliveryItemService.create(deliveryItem);
					currentDelivery.addToDeliveryItems(deliveryItem);
				}
				
			} else {
				Delivery delivery = loadDelivery(row);
				
				if(delivery!=null){
					result.add(delivery);
				}
				
				if(currentDelivery==null){
					currentDelivery = delivery;
				} else {
					if(!DocumentProcessingState.CLOSED.equals(currentDelivery.getDeliveryProcessingState())){
						remoteService.saveAndClose(currentDelivery);
						currentDelivery = delivery;
					}
				}
			}
		}
		if(currentDelivery!=null)
			remoteService.saveAndClose(currentDelivery);
		
		return result;
	}
	
	private Delivery loadDelivery(Row row){
		Delivery entity = new Delivery();

		Cell cell = row.getCell(0);
		if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
			entity.setDeliveryNumber(cell.getStringCellValue().trim());

		if (StringUtils.isBlank(entity.getDeliveryNumber()))
			return null;

		DeliverySearchInput searchInput = new DeliverySearchInput();
		searchInput.setEntity(entity);
		searchInput.getFieldNames().add("deliveryNumber");
		DeliverySearchResult found = remoteService.findBy(searchInput);
		if (!found.getResultList().isEmpty()){
//			return found.getResultList().iterator().next();
			return null;
		}

		cell = row.getCell(1);
		if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
			entity.setDeliverySlipNumber(cell.getStringCellValue().trim());
		

		cell = row.getCell(2);
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
			entity.setDateOnDeliverySlip(calendar);
		}

//		cell = row.getCell(3);
//		creatingUser // auto

		cell = row.getCell(4);
		if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
		{
			String date = cell.getStringCellValue().trim();
			Date parseDate;
			try {
				parseDate = DateUtils.parseDate(date, "dd-MM-yyyy HH:mm");
			} catch (ParseException e) {
				throw new IllegalStateException(e);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parseDate);
			entity.setOrderDate(calendar);
		}

		cell = row.getCell(5);
		if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
		{
			String date = cell.getStringCellValue().trim();
			Date parseDate;
			try {
				parseDate = DateUtils.parseDate(date, "dd-MM-yyyy HH:mm");
			} catch (ParseException e) {
				throw new IllegalStateException(e);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parseDate);
			entity.setDeliveryDate(calendar);
		}

		cell = row.getCell(6);
		if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
			String supplierName = cell.getStringCellValue().trim();
			List<Supplier> suppliers = dataMap.getSuppliers();
			Supplier supplier = null;
			for (Supplier s : suppliers) {
				if(supplierName.equalsIgnoreCase(s.getName())){
					supplier = s;
					break;
				}
			}
			if(supplier!=null){
				entity.setSupplier(new DeliverySupplier(supplier));
			} else {
				throw new IllegalStateException("No supplier found with name: " + supplierName);
			}
		}

		cell = row.getCell(7);
		if (cell != null)
		{
			BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
			entity.setAmountBeforeTax(decimal);
		}

		cell = row.getCell(8);
		if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
		{
			String vatName = cell.getStringCellValue().trim();
			List<VAT> vats = dataMap.getVats();
			VAT vat = null;
			for (VAT v : vats) {
				if(vatName.equals(v.getName())){
					vat = v;
					break;
				}
			}
			if(vat!=null){
				entity.setVat(new DeliveryVat(vat));
			}
		}

		cell = row.getCell(9);
		if (cell != null)
		{
			BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
			entity.setAmountVat(decimal);
		}

		cell = row.getCell(10);
		if (cell != null)
		{
			BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
			entity.setAmountVat(decimal);
		}

		cell = row.getCell(11);
		if (cell != null)
		{
			BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
			entity.setAmountAfterTax(decimal);
		}

		cell = row.getCell(12);
		if (cell != null)
		{
			BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
			entity.setAmountDiscount(decimal);
		}

		cell = row.getCell(13);
		if (cell != null)
		{
			BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
			entity.setNetAmountToPay(decimal);
		}

		cell = row.getCell(14);
		if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
			String currencyName = cell.getStringCellValue().trim();
			List<Currency> currencies = dataMap.getCurrencies();
			Currency currency = null;
			for (Currency c : currencies) {
				if(currencyName.equals(c.getName())){
					currency = c;
					break;
				}
			}
			if(currency!=null){
				entity.setCurrency(new DeliveryCurrency(currency));
			}
		}

//		cell = row.getCell(15);
		entity.setRecordingDate(new GregorianCalendar());
		
		cell = row.getCell(16);
		if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
			String deliveryProcessingStateName = cell.getStringCellValue().trim();
			DocumentProcessingState[] documentProcessingStates = DocumentProcessingState.values();
			for (DocumentProcessingState documentProcessingState : documentProcessingStates) {
				if(deliveryProcessingStateName.equalsIgnoreCase(documentProcessingState.name())){
					entity.setDeliveryProcessingState(documentProcessingState);
					break;
				}
			}
			if(entity.getDeliveryProcessingState()==null)
				entity.setDeliveryProcessingState(DocumentProcessingState.ONGOING);
		}
		
		cell = row.getCell(17);
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
				entity.setReceivingAgency(new DeliveryReceivingAgency(agency));
			} else {
				throw new IllegalStateException("No agency found with agency number: " + agencyNumber);
			}
		}

		return remoteService.create(entity);
	}

	@Override
	protected Task<List<Delivery>> createTask() {
		return new Task<List<Delivery>>() {
			@Override
			protected List<Delivery> call() throws Exception {
				return load();
			}
		};
	}
}
