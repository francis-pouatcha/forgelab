package org.adorsys.adpharma.client.jpa.customerinvoice;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;
import org.apache.commons.lang3.StringUtils;

public class CustomerInvoicePrintTemplate {
	
	private CalendarFormat calendarFormat = new CalendarFormat();

	Font boldFont = boldFont();
//	Printer printer = Printer.getDefaultPrinter();
//	private PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER,
//			PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

	private double printableWidth = Paper.A4.getWidth() ;
	private double printableHeight = Paper.A4.getHeight();
	private double width = printableWidth;
	private RowConstraints mainRowHeight = new RowConstraints();
	private RowConstraints doubleRowHeight = new RowConstraints();

	private double rowHeiht = 15;
	private List<VBox> pages = new ArrayList<VBox>();
	private Insets insets = new Insets(5);

	private GridPane invoiceTable = null;


	double currentPageHeight = 0l;

	private final CustomerInvoice customerInvoice;
	private final Company company;
	private final Agency agency;
	private final Login salesAgent;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	
	public List<VBox> getPages() {
		return pages;
	}

	public CustomerInvoicePrintTemplate(CustomerInvoicePrinterData invoicePrinterData, ResourceBundle resourceBundle,
			Locale locale) {
		this.customerInvoice = invoicePrinterData.getCustomerInvoice();
		this.company = invoicePrinterData.getCompany();
		this.agency = invoicePrinterData.getAgency();
		this.salesAgent = invoicePrinterData.getLogin();
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		mainRowHeight.setPrefHeight(rowHeiht);
		doubleRowHeight.setPrefHeight(rowHeiht * 2);
	}

	int rowsAvailableForPage = 0;
	int pageNumber = 0;
	private GridPane newPage() {
		VBox page = new VBox();
		pages.add(page);
		GridPane headerPane = printInvoiceHeader(width, mainRowHeight,
				doubleRowHeight);
		page.getChildren().add(headerPane);
		GridPane invoiceTable = fillTableHaeder(width, mainRowHeight);
		page.getChildren().add(invoiceTable);

		if (page.getBoundsInParent().getHeight() + (7 * rowHeiht) >= printableHeight)
			throw new IllegalStateException("Invoice haeder: "
					+ page.getBoundsInParent().getHeight() + (7 * rowHeiht)
					+ "bigger than default page size: " + printableHeight);

		currentPageHeight = page.getBoundsInParent().getHeight();
		double spaceLeft = printableHeight - currentPageHeight;
		rowsAvailableForPage = (int)(spaceLeft / (rowHeiht / .75));
		pageNumber = pages.size();
		currentRowSpan = 0;
		tableRow = new TableRow();
		return invoiceTable;
	}
	
	int currentRowSpan = 0;
	TableRow tableRow = null;
	public void addItems(List<CustomerInvoiceItem> invoiceItems) {
		if (invoiceTable == null)
			invoiceTable = newPage();
		for (CustomerInvoiceItem customerInvoiceItem : invoiceItems) {
			String articleName = customerInvoiceItem.getArticle().getArticleName();
			Text articleNameText = new Text(articleName);
			Text articleNameText2 = null;
			
			double articleTextWidth = articleNameText.getLayoutBounds().getWidth();
			if(articleTextWidth>width){
				double ratio = (articleName.length()+1)/articleTextWidth;
				int blockSize = (int) (articleName.length()*ratio);
				String[] split = StringUtils.split(articleName);
				String firstBlockString = "";
				String secondBlockString = "";
				int i = 0;
				while(firstBlockString.length()<blockSize){
					if(StringUtils.isBlank(firstBlockString)) {
						firstBlockString = split[i];
					} else {
						firstBlockString+= " " + split[i];
					}
					i+=1;
					if(split.length==i) break;
				}
				if(split.length>i){
					while(secondBlockString.length()<blockSize){
						if(StringUtils.isBlank(secondBlockString)) {
							secondBlockString = split[i];
						} else {
							secondBlockString+= " " + split[i];
						}
						i+=1;
						if(split.length==i) break;
					}
				}
				if(firstBlockString.length()>blockSize){
					firstBlockString = articleName.substring(0, blockSize-1);
					secondBlockString= articleName.substring(blockSize-1, blockSize);
				}
				if(StringUtils.isNotBlank(firstBlockString)){
					articleNameText = new Text(firstBlockString);
				}
				if(StringUtils.isNotBlank(secondBlockString)){
					articleNameText2 = new Text(secondBlockString);
				}
			}
			int targetSpan =articleNameText2==null?currentRowSpan+1:currentRowSpan+2;
			// if the target span > rowsAvailableForPage then new page.
			if(targetSpan>rowsAvailableForPage){
				closePage();
				invoiceTable = newPage();
			}
			
			currentRowSpan=articleNameText2==null?currentRowSpan+1:currentRowSpan+2;
			tableRow.getCipBox().getChildren().add(new Text(customerInvoiceItem.getInternalPic()));
			tableRow.getDesignationBox().getChildren().add(articleNameText);
			tableRow.getQtyBox().getChildren().add(new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getPurchasedQty())));
			tableRow.getSppuBox().getChildren().add(new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getSalesPricePU())));
			tableRow.getTotalPriceBox().getChildren().add(new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getTotalSalesPrice())));
			if(articleNameText2!=null){
				tableRow.getCipBox().getChildren().add(new Text(""));
				tableRow.getDesignationBox().getChildren().add(articleNameText2);
				tableRow.getQtyBox().getChildren().add(new Text(""));
				tableRow.getSppuBox().getChildren().add(new Text(""));
				tableRow.getTotalPriceBox().getChildren().add(new Text(""));
			}
			
		}
	}
	
	private final int sumarryRows = 6;	
	public void closeInvoice() {
		int targetSpan =currentRowSpan+sumarryRows;
		// if the target span > rowsAvailableForPage then new page.
		if(targetSpan>rowsAvailableForPage){
			closePage();
			invoiceTable = newPage();
		}
		
		tableRow.getDesignationBox().getChildren()
			.add(new Text(resourceBundle.getString("CustomerInvoice_amountAfterTax_description.title")));
		tableRow.getTotalPriceBox().getChildren()
				.add(new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoice.getAmountAfterTax())));

		tableRow.getDesignationBox().getChildren()
			.add(new Text(resourceBundle.getString("CustomerInvoice_amountDiscount_description.title")));
		tableRow.getTotalPriceBox().getChildren().add(new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoice.getAmountDiscount())));

		tableRow.getDesignationBox().getChildren()
			.add(new Text(resourceBundle.getString("CustomerInvoice_advancePayment_description.title")));
		tableRow.getTotalPriceBox().getChildren()
				.add(new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoice.getAdvancePayment())));

		tableRow.getDesignationBox().getChildren()
			.add(new Text(resourceBundle.getString("CustomerInvoice_totalRestToPay_description.title")));
		tableRow.getTotalPriceBox().getChildren().add(new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoice.getTotalRestToPay())));
		currentRowSpan += sumarryRows;
		closePage();
	}
	
	public void closePage(){
		// ROW (1)
		// ROW (1-0) cip
		invoiceTable.add(tableRow.getCipBox(), 0, 1, 1, currentRowSpan);
		// ROW (1-1) articleName
		invoiceTable.add(tableRow.getDesignationBox(), 1, 1, 1, currentRowSpan);
		// ROW (1-3) qtyOrdered
		invoiceTable.add(tableRow.getQtyBox(), 2, 1, 1, currentRowSpan);
		// ROW (1-4) sppu
		invoiceTable.add(tableRow.getSppuBox(), 3, 1, 1, currentRowSpan);
		// ROW (1-5) total
		invoiceTable.add(tableRow.getTotalPriceBox(), 4, 1, 1, currentRowSpan);
	}

	double designationRowWidth = 0;
	private GridPane fillTableHaeder(double width, RowConstraints mainRowHeight) {
		GridPane invoiceTable = new GridPane();
		invoiceTable.getRowConstraints().add(mainRowHeight);
		invoiceTable.setGridLinesVisible(true);

		// ROW -1: comlumn dimentions
		invoiceTable.getColumnConstraints().add(
				new ColumnConstraints(width * .23));
		ColumnConstraints designationRowConstraint = new ColumnConstraints(width * .43);
		invoiceTable.getColumnConstraints().add(designationRowConstraint);
		designationRowWidth = designationRowConstraint.getPrefWidth();
		invoiceTable.getColumnConstraints().add(
				new ColumnConstraints(width * .06));
		invoiceTable.getColumnConstraints().add(
				new ColumnConstraints(width * .11));
		invoiceTable.getColumnConstraints().add(
				new ColumnConstraints(width * .15));

		VBox cipHeaderBox = new VBox();
		cipHeaderBox.setAlignment(Pos.TOP_LEFT);
		cipHeaderBox.setPadding(insets);
		invoiceTable.add(cipHeaderBox, 0, 0, 1, 1);

		VBox designationHeaderBox = new VBox();
		designationHeaderBox.setAlignment(Pos.TOP_LEFT);
		designationHeaderBox.setPadding(insets);
		invoiceTable.add(designationHeaderBox, 1, 0, 1, 1);

		VBox qtyHeaderBox = new VBox();
		qtyHeaderBox.setAlignment(Pos.TOP_RIGHT);
		qtyHeaderBox.setPadding(insets);
		invoiceTable.add(qtyHeaderBox, 2, 0, 1, 1);

		VBox sppuHeaderBox = new VBox();
		sppuHeaderBox.setAlignment(Pos.TOP_RIGHT);
		sppuHeaderBox.setPadding(insets);
		invoiceTable.add(sppuHeaderBox, 3, 0, 1, 1);

		VBox totalPriceHeaderBox = new VBox();
		totalPriceHeaderBox.setAlignment(Pos.TOP_RIGHT);
		totalPriceHeaderBox.setPadding(insets);
		invoiceTable.add(totalPriceHeaderBox, 4, 0, 1, 1);

		// ROW (0-0) cip
		Text cipHeader = new Text("Code Produit");
		cipHeader.setFont(boldFont);
		cipHeaderBox.getChildren().add(cipHeader);

		// ROW (0-1,2) articleName
		Text designationHeader = new Text("Designation");
		designationHeader.setFont(boldFont);
		designationHeaderBox.getChildren().add(designationHeader);

		// ROW (0-3) qtyOrdered
		Text qtyOrdered = new Text("Qté");
		qtyOrdered.setFont(boldFont);
		qtyHeaderBox.getChildren().add(qtyOrdered);

		// ROW (0-4) sppu
		Text sppu = new Text("Prix U.");
		sppu.setFont(boldFont);
		sppuHeaderBox.getChildren().add(sppu);

		// ROW (0-5) total
		Text totalPrice = new Text("Prix Total");
		totalPrice.setFont(boldFont);
		totalPriceHeaderBox.getChildren().add(totalPrice);

		return invoiceTable;
	}

	private GridPane printInvoiceHeader(double width,
			RowConstraints mainRowHeight, RowConstraints doubleRowHeight) {

		Text text = new Text();
		Font font = text.getFont();
		double size = font.getSize();
		Font boldFont = Font.font(null, FontWeight.EXTRA_BOLD, size);

		int colCount = 6;

		int rowIndex = -1;

		GridPane headerPane = new GridPane();

		// ROW -1: comlumn dimentions
		headerPane.getColumnConstraints().add(
				new ColumnConstraints(width * .15));
		headerPane.getColumnConstraints()
				.add(new ColumnConstraints(width * .2));
		headerPane.getColumnConstraints()
				.add(new ColumnConstraints(width * .2));
		headerPane.getColumnConstraints().add(
				new ColumnConstraints(width * .15));
		headerPane.getColumnConstraints().add(
				new ColumnConstraints(width * .15));
		headerPane.getColumnConstraints().add(
				new ColumnConstraints(width * .15));

		// ROW 0
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text documentName = new Text(
				resourceBundle
						.getString("CustomerInvoicePrintTemplate_invoice.title")
						+ " " + customerInvoice.getSalesOrder().getSoNumber());
		documentName.setFont(Font.font(null, FontWeight.BOLD, 15));
		headerPane.add(documentName, 0, rowIndex, colCount, 1);
		GridPane.setHalignment(documentName, HPos.CENTER);

		// ROW 1
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Separator separator = new Separator(Orientation.HORIZONTAL);
		BackgroundFill bgf = new BackgroundFill(Paint.valueOf("black"),
				CornerRadii.EMPTY, Insets.EMPTY);
		separator.setBackground(new Background(bgf));
		headerPane.add(separator, 0, rowIndex, colCount, 1);
		GridPane.setHgrow(separator, Priority.ALWAYS);

		// ROW 2
		rowIndex++;
		headerPane.getRowConstraints().add(doubleRowHeight);
		Text companyName = new Text(customerInvoice.getAgency().getName());
		companyName.setFont(boldFont);
		headerPane.add(companyName, 0, rowIndex, colCount, 1);
		GridPane.setHalignment(companyName, HPos.LEFT);
		GridPane.setValignment(companyName, VPos.BOTTOM);

		// ROW 3
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text ownerName = new Text(company.getSiteManager());
		ownerName.setFont(boldFont);
		headerPane.add(ownerName, 0, rowIndex, colCount, 1);
		GridPane.setHalignment(ownerName, HPos.LEFT);
		GridPane.setValignment(ownerName, VPos.BOTTOM);

		// ROW 4
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text address = new Text(agency.getStreet());
		headerPane.add(address, 0, rowIndex, colCount, 1);
		GridPane.setHalignment(address, HPos.LEFT);
		GridPane.setValignment(address, VPos.BOTTOM);

		// ROW 5
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text telFax = new Text("Tel: " + agency.getPhone() + " Fax: "
				+ agency.getFax());
		headerPane.add(telFax, 0, rowIndex, colCount, 1);
		GridPane.setHalignment(telFax, HPos.LEFT);
		GridPane.setValignment(telFax, VPos.BOTTOM);

		// ROW 6
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text email = new Text(
				resourceBundle.getString("Company_email_description.title")
						+ " " + company.getEmail());
		headerPane.add(email, 0, rowIndex, colCount, 1);
		GridPane.setHalignment(email, HPos.LEFT);
		GridPane.setValignment(email, VPos.BOTTOM);
		
		// ROW 6
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text registerNumber = new Text(
				resourceBundle.getString("Company_registerNumber_description.title")
						+ " " + company.getRegisterNumber());
		headerPane.add(registerNumber, 0, rowIndex, colCount, 1);
		GridPane.setHalignment(registerNumber, HPos.LEFT);
		GridPane.setValignment(registerNumber, VPos.BOTTOM);

		// ROW (7)
		rowIndex++;
		headerPane.getRowConstraints().add(doubleRowHeight);
		// ROW (7-0,1)
		Text customerNameLabel = new Text(
				resourceBundle.getString("CustomerInvoicePrintTemplate_customerName.title"));
		customerNameLabel.setFont(boldFont);
		headerPane.add(customerNameLabel, 0, rowIndex, 2, 1);
		GridPane.setHalignment(customerNameLabel, HPos.LEFT);
		GridPane.setValignment(customerNameLabel, VPos.BOTTOM);
		// ROW (7-3..)
		Text customerNameValue = new Text(customerInvoice.getCustomer()
				.getFullName());
		headerPane.add(customerNameValue, 2, rowIndex, colCount - 2, 1);
		GridPane.setHalignment(customerNameValue, HPos.LEFT);
		GridPane.setValignment(customerNameValue, VPos.BOTTOM);

		// ROW (8)
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		// ROW (8-0,1)
		Text invoiceDateLabel = new Text(
				resourceBundle.getString("CustomerInvoicePrintTemplate_invoiceDate.title"));
		invoiceDateLabel.setFont(boldFont);
		headerPane.add(invoiceDateLabel, 0, rowIndex, 2, 1);
		GridPane.setHalignment(invoiceDateLabel, HPos.LEFT);
		GridPane.setValignment(invoiceDateLabel, VPos.BOTTOM);
		// ROW (8-3..)
		Text invoiceDateValue = new Text(calendarFormat.format(customerInvoice.getCreationDate(), "dd-MM-yyyy HH:mm", locale));
		headerPane.add(invoiceDateValue, 2, rowIndex, colCount - 2, 1);
		GridPane.setHalignment(invoiceDateValue, HPos.LEFT);
		GridPane.setValignment(invoiceDateValue, VPos.BOTTOM);

		// ROW (9)
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		// ROW (9-0,1)
		Text salesAgentNameLabel = new Text(resourceBundle.getString("CustomerInvoicePrintTemplate_salesAgent.title"));
		salesAgentNameLabel.setFont(boldFont);
		headerPane.add(salesAgentNameLabel, 0, rowIndex, 2, 1);
		GridPane.setHalignment(salesAgentNameLabel, HPos.LEFT);
		GridPane.setValignment(salesAgentNameLabel, VPos.BOTTOM);
		// ROW (9-3..)
		Text salesAgentNameValue = new Text(salesAgent.getFullName());
		headerPane.add(salesAgentNameValue, 2, rowIndex, colCount - 2, 1);
		GridPane.setHalignment(salesAgentNameValue, HPos.LEFT);
		GridPane.setValignment(salesAgentNameValue, VPos.BOTTOM);

		// ROW (10)
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		// ROW (10-0,1)
		Text invoiceNumberlabel = new Text(resourceBundle.getString("CustomerInvoicePrintTemplate_invoiceNr.title"));
		invoiceNumberlabel.setFont(boldFont);
		headerPane.add(invoiceNumberlabel, 0, rowIndex, 2, 1);
		GridPane.setHalignment(invoiceNumberlabel, HPos.LEFT);
		GridPane.setValignment(invoiceNumberlabel, VPos.BOTTOM);
		// ROW (10-3..)
		Text invoiceNumberValue = new Text(customerInvoice.getInvoiceNumber());
		headerPane.add(invoiceNumberValue, 2, rowIndex, colCount - 2, 1);
		GridPane.setHalignment(invoiceNumberValue, HPos.LEFT);
		GridPane.setValignment(invoiceNumberValue, VPos.BOTTOM);

		// ROW (11)
		rowIndex++;
		headerPane.getRowConstraints().add(doubleRowHeight);
		headerPane.add(new Label(), 0, rowIndex, colCount, 1);

		return headerPane;
	}

	private static Font boldFont() {
		Text text = new Text();
		Font font = text.getFont();
		double size = font.getSize();
		return Font.font(null, FontWeight.EXTRA_BOLD, size);
	}

	static class TableRow {
		final VBox cipBox = new VBox();
		final VBox designationBox = new VBox();
		final VBox qtyBox = new VBox();
		final VBox sppuBox = new VBox();
		final VBox totalPriceBox = new VBox();
		final Insets insets = new Insets(5);

		TableRow() {
			cipBox.setPadding(insets);
			designationBox.setPadding(insets);
			qtyBox.setAlignment(Pos.TOP_RIGHT);
			qtyBox.setPadding(insets);
			sppuBox.setAlignment(Pos.TOP_RIGHT);
			sppuBox.setPadding(insets);
			totalPriceBox.setAlignment(Pos.TOP_RIGHT);
			totalPriceBox.setPadding(insets);
		}

		public VBox getCipBox() {
			return cipBox;
		}

		public VBox getDesignationBox() {
			return designationBox;
		}

		public VBox getQtyBox() {
			return qtyBox;
		}

		public VBox getSppuBox() {
			return sppuBox;
		}

		public VBox getTotalPriceBox() {
			return totalPriceBox;
		}

		public Insets getInsets() {
			return insets;
		}

	}

}
