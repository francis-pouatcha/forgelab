package org.adorsys.adpharma.client.jpa.delivery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.print.Paper;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;
import org.apache.commons.lang3.StringUtils;

public class DeliveryReportPrintTemplate {
	
	private CalendarFormat calendarFormat = new CalendarFormat();

//	private double printableWidth = Paper.A4.getWidth() ;
//	private double printableHeight = Paper.A4.getHeight();
	private double printableWidth = Paper.NA_LETTER.getWidth() ;
	private double printableHeight = Paper.NA_LETTER.getHeight();
	private double width = printableWidth;
	private RowConstraints mainRowHeight = new RowConstraints();
	private RowConstraints doubleRowHeight = new RowConstraints();
	private RowConstraints separatorRowConstraint = new RowConstraints();

	private double realRowHeiht;
	private List<VBox> pages = new ArrayList<VBox>();

	private GridPane deliveryTable = null;

	private final Login stockAgent;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private final Delivery delivery;
	
	public List<VBox> getPages() {
		return pages;
	}

	public DeliveryReportPrintTemplate(DeliveryReportPrinterData invoicePrinterData, ResourceBundle resourceBundle,
			Locale locale) {
		this.delivery = invoicePrinterData.getDelivery();
		this.stockAgent = invoicePrinterData.getLogin();
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		int rowHeiht = 15;
		int separatorRowHeight = 2;
		mainRowHeight.setPrefHeight(rowHeiht);
		doubleRowHeight.setPrefHeight(rowHeiht * 2);
		separatorRowConstraint.setPrefHeight(1);
		realRowHeiht = rowHeiht + separatorRowHeight;
		
		// FIrst page
		VBox firstPage = new VBox();
		pages.add(firstPage);
		GridPane headerPane = printReportHeader();
		firstPage.getChildren().add(headerPane);
		fillTableHaeder(firstPage);
		computeRowLeft(firstPage);
	}
	
	int numberOfItemsAvailable = 0;
	int pageNumber = 0;
	private void computeRowLeft(VBox page){
		if (page.getBoundsInParent().getHeight() >= printableHeight)
			throw new IllegalStateException("Invoice haeder: "
					+ page.getBoundsInParent().getHeight()
					+ "bigger than default page size: " + printableHeight);

		double currentPageHeight = page.getBoundsInParent().getHeight();
		double spaceLeft = printableHeight - currentPageHeight;
		numberOfItemsAvailable = ((int)(spaceLeft / (realRowHeiht / .72)))-1; // DPI 
		pageNumber = pages.size();
	}


	private void newPage() {
		VBox page = new VBox();
		pages.add(page);
		fillTableHaeder(page);
		computeRowLeft(page);
	}
	
	public void addItems(List<DeliveryItem> deliveryItems) {

		for (DeliveryItem deliveryItem : deliveryItems) {

			if(numberOfItemsAvailable<1){
//				closePage();
				newPage();
			}
			numberOfItemsAvailable-=1;
			
			String articleName = deliveryItem.getArticleName();
			if(articleName.length()>60) articleName = StringUtils.substring(articleName, 0, 60);
			
			newTableRow(deliveryItem.getInternalPic(), 
					articleName, 
					deliveryItem.getStockQuantity(), 
					deliveryItem.getPurchasePricePU(),
					deliveryItem.getQtyOrdered(),
					deliveryItem.getSalesPricePU(),
					deliveryItem.getTotalPurchasePrice());
			newSeparator();
		}
	}
	private void newTableRow(String internalPic, 
			String articleName,
			BigDecimal stockQuantity,
			BigDecimal purchasePricePU,
			BigDecimal qtyOrdered, 
			BigDecimal salesPricePU,
			BigDecimal totalPurchasePrice) {
		deliveryTable.getRowConstraints().add(mainRowHeight);
		rowIndex++;
		deliveryTable.add(new StandardText(internalPic), 0, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(articleName), 1, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(stockQuantity)), 2, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(purchasePricePU)), 3, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(qtyOrdered)), 4, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(salesPricePU)), 5, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalPurchasePrice)), 6, rowIndex, 1,  1);
	}
	
	private void newSeparator(){
		rowIndex++;
		Separator separator = new Separator();
		deliveryTable.getRowConstraints().add(separatorRowConstraint);
		deliveryTable.add(separator, 0, rowIndex, 7, 1);
	}

//	public void closePage(){
//		// ROW (1)
//		// ROW (1-0) cip
//		deliveryTable.add(tableRow.getCipBox(), 0, 1, 1, currentRowSpan);
//		// ROW (1-1) articleName
//		deliveryTable.add(tableRow.getDesignationBox(), 1, 1, 1, currentRowSpan);
//		// ROW (1-3) qtyOrdered
//		deliveryTable.add(tableRow.getQtyBox(), 2, 1, 1, currentRowSpan);
//		// ROW (1-4) sppu
//		deliveryTable.add(tableRow.getSppuBox(), 3, 1, 1, currentRowSpan);
//		// ROW (1-5) total
//		deliveryTable.add(tableRow.getTotalPriceBox(), 4, 1, 1, currentRowSpan);
//	}

	int rowIndex = -1;
	BorderStroke borderStroke = new BorderStroke(Paint.valueOf("green"), BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY);
	Border dashedGreenBorder = new Border(borderStroke);
	private void fillTableHaeder(VBox page) {
		deliveryTable = new GridPane();
		deliveryTable.setBorder(dashedGreenBorder);
		deliveryTable.setAlignment(Pos.TOP_CENTER);
		page.getChildren().add(deliveryTable);
		newSeparator();

		rowIndex++;
		deliveryTable.getRowConstraints().add(mainRowHeight);
		deliveryTable.getColumnConstraints().add(new ColumnConstraints(width * .15));
		deliveryTable.getColumnConstraints().add(new ColumnConstraints(width * .49));
		deliveryTable.getColumnConstraints().add(new ColumnConstraints(width * .03));
		deliveryTable.getColumnConstraints().add(new ColumnConstraints(width * .1));
		deliveryTable.getColumnConstraints().add(new ColumnConstraints(width * .03));
		deliveryTable.getColumnConstraints().add(new ColumnConstraints(width * .1));
		deliveryTable.getColumnConstraints().add(new ColumnConstraints(width * .1));

		deliveryTable.add(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_internalPic.title")), 0, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_articleName.title")), 1, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_stockQuantity.title")), 2, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_purchasePricePU.title")), 3, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_qtyOrdered.title")), 4, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_salesPricePU.title")), 5, rowIndex, 1,  1);
		deliveryTable.add(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_totalPurchasePrice.title")), 6, rowIndex, 1,  1);
		
		newSeparator();

		
//		return deliveryTable;
	}

	private GridPane printReportHeader() {

		int row = -1;

		GridPane headerPane = new GridPane();


		
		headerPane.getColumnConstraints().add(new ColumnConstraints(width * .7));
		headerPane.getColumnConstraints().add(new ColumnConstraints(width * .3));
		
		// ROW 0
		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text documentName = new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_header.title")
						+ " " + delivery.getDeliveryNumber());
		headerPane.add(documentName, 0, row, 2, 1);
		GridPane.setHalignment(documentName, HPos.CENTER);

		// ROW 1
		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Separator separator = new Separator(Orientation.HORIZONTAL);
		headerPane.add(separator, 0, row, 2, 1);
		GridPane.setHgrow(separator, Priority.ALWAYS);

		// ROW 2
		row++;
		headerPane.getRowConstraints().add(doubleRowHeight);
		Text companyName = new BoldText(delivery.getReceivingAgency().getName());
		headerPane.add(companyName, 0, row, 1, 1);
		GridPane.setHalignment(companyName, HPos.LEFT);
		GridPane.setValignment(companyName, VPos.BOTTOM);

		// ROW 3
		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text Tel = new StandardText("Tel: " + delivery.getReceivingAgency().getPhone());
		headerPane.add(Tel, 0, row, 1, 1);
		GridPane.setHalignment(Tel, HPos.LEFT);
		GridPane.setValignment(Tel, VPos.BOTTOM);

		// ROW 4
		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text supplier = new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_supplier.title") 
				+ " " + delivery.getSupplier().getName());
		headerPane.add(supplier, 0, row, 1, 1);
		GridPane.setHalignment(supplier, HPos.LEFT);
		GridPane.setValignment(supplier, VPos.BOTTOM);

		// ROW 5
		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text agent = new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_agent.title") 
				+ " " + stockAgent.getFullName());
		headerPane.add(agent, 0, row, 1, 1);
		GridPane.setHalignment(agent, HPos.LEFT);
		GridPane.setValignment(agent, VPos.BOTTOM);

		headerPane.getRowConstraints().add(mainRowHeight);
		Text invoiceDateLabel = new StandardText(
				resourceBundle.getString("DeliveryReportPrintTemplate_deliveryDate.title")
				+ " " + calendarFormat.format(delivery.getDeliveryDate(), "dd-MM-yyyy HH:mm", locale));
		headerPane.add(invoiceDateLabel, 1, row, 1, 1);
		GridPane.setHalignment(invoiceDateLabel, HPos.LEFT);
		GridPane.setValignment(invoiceDateLabel, VPos.BOTTOM);

		// ROW (6)
		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		headerPane.add(new Label(), 0, row, 2, 1);

		return headerPane;
	}

	private static Font boldFont() {
		Text text = new Text();
		Font font = text.getFont();
		double size = font.getSize()-5;
		return Font.font(null, FontWeight.EXTRA_BOLD, size);
	}
	
	private static Font font() {
		Text text = new Text();
		Font font = text.getFont();
		double size = font.getSize()-5;
		return Font.font(null, FontWeight.NORMAL, size);
	}
	static Font boldFont = boldFont();
	static Font font = font();
	static class StandardText extends Text{
		StandardText() {
			super();
			setFont(font);
		}
		StandardText(String text) {
			super(text);
			setFont(font);
		}
	}
	
	static class BoldText extends Text{
		BoldText() {
			super();
			setFont(boldFont);
		}
		BoldText(String text) {
			super(text);
			setFont(boldFont);
		}
	}

}
