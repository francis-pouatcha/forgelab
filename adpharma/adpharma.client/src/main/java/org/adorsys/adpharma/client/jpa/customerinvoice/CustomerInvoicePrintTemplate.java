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
import javafx.scene.Node;
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

	private double printableWidth = Paper.A4.getWidth() - (2*72);
	private double printableHeight = Paper.A4.getHeight()- (2*72);
	private double width = printableWidth;
	private RowConstraints mainRowHeight = new RowConstraints();
	private RowConstraints doubleRowHeight = new RowConstraints();

	private double rowHeiht = 15;
	private List<VBox> pages = new ArrayList<VBox>();
	private Insets insets = new Insets(5);
	private double spaceUsed = -1;

	private GridPane invoiceTable = null;

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
		newPage();
	}
	
	private void newPage() {
		VBox page = new VBox();
		pages.add(page);
		GridPane headerPane = printInvoiceHeader();
		page.getChildren().add(headerPane);
		fillTableHaeder(page);
	}
	
	TableRow tableRow = new TableRow();
	final int blockSize = 26;
	public void addItems(List<CustomerInvoiceItem> invoiceItems) {
		for (CustomerInvoiceItem customerInvoiceItem : invoiceItems) {
			String articleName = customerInvoiceItem.getArticle().getArticleName();
			
			String[] splitArticleName = splitArticleName(articleName);
			Text articleNameText = new Text(splitArticleName[0]);
			Text articleNameText2 = null;
			if(StringUtils.isNotBlank(splitArticleName[1]))articleNameText2 = new Text(splitArticleName[1]);

			int targetSpan =articleNameText2==null?1:2;
			double requiredSpace = spaceUsed + mainRowHeight.getPercentHeight()*targetSpan;
			// if the target span > rowsAvailableForPage then new page.
			if(requiredSpace>=printableHeight){
				closePage();
			}
			spaceUsed =requiredSpace;
			
			tableRow.add(new Text(customerInvoiceItem.getInternalPic()), 
					articleNameText, 
					new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getPurchasedQty())), 
					new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getSalesPricePU())), 
					new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getTotalSalesPrice())), 
					mainRowHeight);

			if(articleNameText2!=null){
				tableRow.add(new Text(""), articleNameText2, new Text(""), new Text(""), new Text(""), mainRowHeight);
			}
			
		}
	}
	
	private final int sumarryRows = 5;	
	public void closeInvoice() {
		double requiredSpace = spaceUsed + mainRowHeight.getPercentHeight()*sumarryRows;
		// if the target span > rowsAvailableForPage then new page.
		if(requiredSpace>=printableHeight){
			closePage();
		}
		spaceUsed =requiredSpace;
		
		tableRow.add(new Text(""), new Text(""), new Text(""), new Text(""), new Separator(), mainRowHeight);
		tableRow.add(new Text(""), new Text(resourceBundle.getString("CustomerInvoice_amountAfterTax_description.title")), new Text(""), new Text(""), new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoice.getAmountAfterTax())), mainRowHeight);
		tableRow.add(new Text(""), new Text(resourceBundle.getString("CustomerInvoice_amountDiscount_description.title")), new Text(""), new Text(""), new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoice.getAmountDiscount())), mainRowHeight);
		tableRow.add(new Text(""), new Text(resourceBundle.getString("CustomerInvoice_advancePayment_description.title")), new Text(""), new Text(""), new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoice.getAdvancePayment())), mainRowHeight);
		tableRow.add(new Text(""), new Text(resourceBundle.getString("CustomerInvoice_totalRestToPay_description.title")), new Text(""), new Text(""), new Text(DefaultBigDecimalFormatCM.getinstance().format(customerInvoice.getTotalRestToPay())), mainRowHeight);

		closePage();
	}
	
	public void closePage(){
		tableRow.into(invoiceTable, 1);
		newPage();		
	}

	double designationRowWidth = 0;
	private void fillTableHaeder(VBox page) {
		invoiceTable = new GridPane();
		page.getChildren().add(invoiceTable);
		invoiceTable.getRowConstraints().add(mainRowHeight);
		spaceUsed+=mainRowHeight.getPrefHeight();
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
	}

	private GridPane printInvoiceHeader() {

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
				.getFullName()+" [ "+customerInvoice.getCustomer().getSociete()+" ]");
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
		final GridPane cipBox = new GridPane();
		final GridPane designationBox = new GridPane();
		final GridPane qtyBox = new GridPane();
		final GridPane sppuBox = new GridPane();
		final GridPane totalPriceBox = new GridPane();
		final Insets insets = new Insets(2);
		
		private int rowIndex=-1;
		private double spaceUsed = 0;

		TableRow() {
			cipBox.setPadding(insets);
			designationBox.setPadding(insets);
//			qtyBox.setAlignment(Pos.TOP_RIGHT);
			qtyBox.setPadding(insets);
//			sppuBox.setAlignment(Pos.TOP_RIGHT);
			sppuBox.setPadding(insets);
//			totalPriceBox.setAlignment(Pos.TOP_RIGHT);
			totalPriceBox.setPadding(insets);
		}
		
		public void add(Node cip,Node designation, Node qty, Node sppu, Node total, RowConstraints rh){
			rowIndex+=1;
			addRowConstraints(rh);
			cipBox.add(cip, 0, rowIndex, 1, 1);
			cipBox.setAlignment(Pos.CENTER_LEFT);
			designationBox.add(designation, 0, rowIndex, 1, 1);
			designationBox.setAlignment(Pos.CENTER_LEFT);
			qtyBox.add(qty, 0, rowIndex, 1, 1);
			qtyBox.setAlignment(Pos.CENTER_RIGHT);
			GridPane.setHalignment(qty, HPos.RIGHT);
			sppuBox.add(sppu, 0, rowIndex, 1, 1);
			sppuBox.setAlignment(Pos.CENTER_RIGHT);
			GridPane.setHalignment(sppu, HPos.RIGHT);
			totalPriceBox.add(total, 0, rowIndex, 1, 1);
			totalPriceBox.setAlignment(Pos.CENTER_RIGHT);
			GridPane.setHalignment(total, HPos.RIGHT);
			
		}
		
		private void addRowConstraints(RowConstraints rc){
			spaceUsed+=rc.getPrefHeight();
			cipBox.getRowConstraints().add(rc);
			designationBox.getRowConstraints().add(rc);
			qtyBox.getRowConstraints().add(rc);
			sppuBox.getRowConstraints().add(rc);
			totalPriceBox.getRowConstraints().add(rc);
		}
		
		public double getSpaceUsed(){
			return spaceUsed;
		}
		
		public void into(GridPane parent, int row){
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPrefHeight(spaceUsed);
			parent.getRowConstraints().add(rowConstraints);
			// ROW (1)
			// ROW (1-0) cip
			parent.add(cipBox, 0, row, 1, 1);
			// ROW (1-1) articleName
			parent.add(designationBox, row, 1, 1, 1);
			// ROW (1-3) qtyOrdered
			parent.add(qtyBox, 2, row, 1, 1);
			// ROW (1-4) sppu
			parent.add(sppuBox, 3, row, 1, 1);
			// ROW (1-5) total
			parent.add(totalPriceBox, 4, row, 1, 1);
		}
	}

	private String[] splitArticleName(String articleName){
		String firstBlockString = "";
		String secondBlockString = "";
		if(articleName.length()>blockSize){
			String[] split = StringUtils.split(articleName);
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
				secondBlockString=  StringUtils.substring(articleName, blockSize, blockSize + blockSize-1);
			}
		} else {
			firstBlockString = articleName;
		}
		return new String[]{firstBlockString, secondBlockString};
		
	}
}
