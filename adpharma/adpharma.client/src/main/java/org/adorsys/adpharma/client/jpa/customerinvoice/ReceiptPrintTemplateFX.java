package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;
import org.apache.commons.lang3.StringUtils;

public class ReceiptPrintTemplateFX extends ReceiptPrintTemplate {
	
	private CalendarFormat calendarFormat = new CalendarFormat();

	static Font boldFont = boldFont();
	static Font font = font();

	static final String separatorText = "------------------------";
	private double printableWidth = 219-10;
	private double width = printableWidth;
	private RowConstraints mainRowHeight = new RowConstraints();
	private RowConstraints doubleRowHeight = new RowConstraints();

	private double rowHeiht = 15;
	private VBox page = new VBox();
//	private Insets insets = new Insets(5);
	private Insets lesftInsets = new Insets(0, 0, 0, 2);
//	private Insets rightInsets = new Insets(0, 5, 0, 0);
	private GridPane invoicePane = null;

	private final ReceiptPrinterData receiptPrinterData;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
//	BorderStroke borderStroke = new BorderStroke(Paint.valueOf("black"), BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY);
//	Border dashedBlackBorder = new Border(borderStroke);

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

    public ReceiptPrintTemplateFX(ReceiptPrinterData receiptPrinterData, ResourceBundle resourceBundle,
			Locale locale) {
		this.receiptPrinterData = receiptPrinterData;
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		mainRowHeight.setPrefHeight(rowHeiht);
		doubleRowHeight.setPrefHeight(rowHeiht * 2);
	}

	public void startPage() {
		GridPane headerPane = printReceiptHeader(width, mainRowHeight,doubleRowHeight);
		page.getChildren().add(headerPane);
	}

	double designationRowWidth = 0;

	boolean printOrder = false;
	private GridPane printReceiptHeader(double width,
			RowConstraints mainRowHeight, RowConstraints doubleRowHeight) {

		int rowIndex = -1;

		GridPane headerPane = new GridPane();
		headerPane.getColumnConstraints().add(new ColumnConstraints(width));

		// ROW 0
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text documentName = new BoldText(
				resourceBundle
						.getString("ReceiptPrintTemplate_cashReceipt.title")
						+ " " + receiptPrinterData.getPayment().getPaymentNumber());
		headerPane.add(documentName, 0, rowIndex, 1, 1);
		GridPane.setHalignment(documentName, HPos.CENTER);

		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text firstSeparator = new StandardText(separatorText);
		headerPane.add(firstSeparator, 0, rowIndex, 1, 1);
		GridPane.setHalignment(firstSeparator, HPos.CENTER);

		rowIndex++;
		headerPane.getRowConstraints().add(doubleRowHeight);
		Text companyName = new StandardText(receiptPrinterData.getCompany().getDisplayName());
		companyName.setFont(boldFont);
		headerPane.add(companyName, 0, rowIndex, 1, 1);
		GridPane.setHalignment(companyName, HPos.LEFT);
		GridPane.setValignment(companyName, VPos.BOTTOM);

		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text ownerName = new BoldText(receiptPrinterData.getCompany().getSiteManager());
		headerPane.add(ownerName, 0, rowIndex, 1, 1);
		GridPane.setHalignment(ownerName, HPos.LEFT);
		GridPane.setValignment(ownerName, VPos.BOTTOM);

		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text address = new StandardText(receiptPrinterData.getAgency().getStreet());
		headerPane.add(address, 0, rowIndex, 1, 1);
		GridPane.setHalignment(address, HPos.LEFT);
		GridPane.setValignment(address, VPos.BOTTOM);

		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text telFax = new StandardText("TEL: " + receiptPrinterData.getAgency().getPhone() + " FAX: "
				+ receiptPrinterData.getAgency().getFax());
		headerPane.add(telFax, 0, rowIndex, 1, 1);
		GridPane.setHalignment(telFax, HPos.LEFT);
		GridPane.setValignment(telFax, VPos.BOTTOM);

		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text email = new StandardText(
				resourceBundle.getString("ReceiptPrintTemplate_email.title")
						+ ": " + receiptPrinterData.getCompany().getEmail());
		headerPane.add(email, 0, rowIndex, 1, 1);
		GridPane.setHalignment(email, HPos.LEFT);
		GridPane.setValignment(email, VPos.BOTTOM);
		
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text registerNumber = new StandardText(
				resourceBundle.getString("ReceiptPrintTemplate_registerNumber.title")
						+ ": " + receiptPrinterData.getCompany().getRegisterNumber());
		headerPane.add(registerNumber, 0, rowIndex, 1, 1);
		GridPane.setHalignment(registerNumber, HPos.LEFT);
		GridPane.setValignment(registerNumber, VPos.BOTTOM);

		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text secondSeparator = new StandardText(separatorText);
		headerPane.add(secondSeparator, 0, rowIndex, 1, 1);
		GridPane.setHalignment(secondSeparator, HPos.CENTER);

		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text receiptNumber = new StandardText(
				resourceBundle.getString("ReceiptPrintTemplate_receipt.title")
						+ " " + receiptPrinterData.getPayment().getPaymentNumber()
				
				+ resourceBundle.getString("ReceiptPrintTemplate_receipt_of.title")
				+ " " + calendarFormat.format(receiptPrinterData.getPayment().getPaymentDate(), "dd-MM-yyyy HH:mm", locale)
				);
		headerPane.add(receiptNumber, 0, rowIndex, 1, 1);
		GridPane.setHalignment(receiptNumber, HPos.LEFT);
		GridPane.setValignment(receiptNumber, VPos.BOTTOM);
		
		rowIndex++;
		headerPane.getRowConstraints().add(mainRowHeight);
		Text cashier = new StandardText(
				resourceBundle.getString("ReceiptPrintTemplate_cashier.title")
						+ " " + receiptPrinterData.getCashier().getFullName());
		headerPane.add(cashier, 0, rowIndex, 1, 1);
		GridPane.setHalignment(cashier, HPos.LEFT);
		GridPane.setValignment(cashier, VPos.BOTTOM);

		if(receiptPrinterData.getInvoiceData().size()>1){
			printOrder = true;
		}
		return headerPane;
	}

	int invoiceRowIndex = -1;
	public void printInvoiceHeader(CustomerInvoicePrinterData invoiceData){

		invoiceRowIndex = -1;
		invoicePane = new GridPane();
		page.getChildren().add(invoicePane);
		
		invoicePane.getRowConstraints().add(mainRowHeight);
		invoicePane.getColumnConstraints().add(new ColumnConstraints(width * .3));
		ColumnConstraints designationRowConstraint = new ColumnConstraints(width * .47);
		invoicePane.getColumnConstraints().add(designationRowConstraint);
		designationRowWidth = designationRowConstraint.getPrefWidth();
		invoicePane.getColumnConstraints().add(
				new ColumnConstraints(width * .06));
		invoicePane.getColumnConstraints().add(
				new ColumnConstraints(width * .17));
		
		int colSpan = 4;
		
		totalAmountInvoices = totalAmountInvoices.add(invoiceData.getCustomerInvoice().getAmountAfterTax());
		totalAmountDiscount = totalAmountDiscount.add(invoiceData.getCustomerInvoice().getAmountDiscount());
		totalAmountRestToPay = totalAmountRestToPay.add(invoiceData.getCustomerInvoice().getTotalRestToPay());
		
		invoiceData.getCustomerInvoice().getNetToPay();
		
		if(printOrder){
			
			invoiceRowIndex++;
			invoicePane.getRowConstraints().add(mainRowHeight);
			Text separator = new StandardText(separatorText);
			invoicePane.add(separator, 0, invoiceRowIndex, colSpan, 1);
			GridPane.setHalignment(separator, HPos.CENTER);
			
			invoiceRowIndex++;
			invoicePane.getRowConstraints().add(mainRowHeight);
			Text invoiceNumberlabel = new StandardText(
					resourceBundle.getString("ReceiptPrintTemplate_salesOrder.title")
					+ " " + invoiceData.getCustomerInvoice().getSalesOrder().getSoNumber());
			invoicePane.add(invoiceNumberlabel, 0, invoiceRowIndex, colSpan, 1);
			GridPane.setHalignment(invoiceNumberlabel, HPos.LEFT);
			GridPane.setValignment(invoiceNumberlabel, VPos.BOTTOM);
		}

		invoiceRowIndex++;
		invoicePane.getRowConstraints().add(mainRowHeight);
		Text salesAgent = new StandardText(
				resourceBundle.getString("ReceiptPrintTemplate_salesAgent.title")
				+ " " + invoiceData.getLogin().getFullName());
		invoicePane.add(salesAgent, 0, invoiceRowIndex, colSpan, 1);
		GridPane.setHalignment(salesAgent, HPos.LEFT);
		GridPane.setValignment(salesAgent, VPos.BOTTOM);

		invoiceRowIndex++;
		invoicePane.getRowConstraints().add(mainRowHeight);
		Text customer = new StandardText(
				resourceBundle.getString("ReceiptPrintTemplate_customer.title")
				+ " " + invoiceData.getCustomerInvoice().getCustomer().getFullName());
		invoicePane.add(customer, 0, invoiceRowIndex, colSpan, 1);
		GridPane.setHalignment(customer, HPos.LEFT);
		GridPane.setValignment(customer, VPos.BOTTOM);

		invoiceRowIndex++;
		invoicePane.getRowConstraints().add(mainRowHeight);
		Text separator = new StandardText(separatorText);
		invoicePane.add(separator, 0, invoiceRowIndex, colSpan, 1);
		GridPane.setHalignment(separator, HPos.CENTER);

		invoiceRowIndex++;
		invoicePane.getRowConstraints().add(mainRowHeight);
		VBox cipHeaderBox = new VBox();
		cipHeaderBox.setAlignment(Pos.TOP_LEFT);
//		cipHeaderBox.setPadding(insets);
		Text cipHeader = new BoldText(resourceBundle.getString("ReceiptPrintTemplate_cip.title"));
		cipHeaderBox.getChildren().add(cipHeader);
		invoicePane.add(cipHeaderBox, 0, invoiceRowIndex, 1, 1);

		VBox designationHeaderBox = new VBox();
		designationHeaderBox.setAlignment(Pos.TOP_LEFT);
		designationHeaderBox.setPadding(lesftInsets);
		Text designationHeader = new BoldText(resourceBundle.getString("ReceiptPrintTemplate_designation.title"));
		designationHeaderBox.getChildren().add(designationHeader);
		invoicePane.add(designationHeaderBox, 1, invoiceRowIndex, 1, 1);

		VBox qtyHeaderBox = new VBox();
		qtyHeaderBox.setAlignment(Pos.TOP_CENTER);
		qtyHeaderBox.setPadding(lesftInsets);
		Text qtyOrdered = new BoldText(resourceBundle.getString("ReceiptPrintTemplate_quantity.title"));
		qtyHeaderBox.getChildren().add(qtyOrdered);
		invoicePane.add(qtyHeaderBox, 2, invoiceRowIndex, 1, 1);

		VBox totalPriceHeaderBox = new VBox();
		totalPriceHeaderBox.setAlignment(Pos.TOP_CENTER);
		totalPriceHeaderBox.setPadding(lesftInsets);
		Text totalPrice = new BoldText(resourceBundle.getString("ReceiptPrintTemplate_totalPrice.title"));
		totalPriceHeaderBox.getChildren().add(totalPrice);
		invoicePane.add(totalPriceHeaderBox, 3, invoiceRowIndex, 1, 1);

	}
	
	public void addItems(List<CustomerInvoiceItem> invoiceItems) {
		for (CustomerInvoiceItem customerInvoiceItem : invoiceItems) {
			invoiceRowIndex++;
			invoicePane.getRowConstraints().add(mainRowHeight);
			VBox cipHeaderBox = new VBox();
			cipHeaderBox.setAlignment(Pos.TOP_LEFT);
//			cipHeaderBox.setPadding(insets);
			String articleName = customerInvoiceItem.getArticle().getArticleName();
			if(StringUtils.isNotBlank(articleName) && articleName.length()>18){
				articleName = StringUtils.substring(articleName, 0, 18);
			}
			Text articleNameText = new StandardText(articleName);			
			cipHeaderBox.getChildren().add(new StandardText(customerInvoiceItem.getInternalPic()));
			invoicePane.add(cipHeaderBox, 0, invoiceRowIndex, 1, 1);

			VBox designationHeaderBox = new VBox();
			designationHeaderBox.setAlignment(Pos.TOP_LEFT);
			designationHeaderBox.setPadding(lesftInsets);
			designationHeaderBox.getChildren().add(articleNameText);
			invoicePane.add(designationHeaderBox, 1, invoiceRowIndex, 1, 1);

			VBox qtyHeaderBox = new VBox();
			qtyHeaderBox.setAlignment(Pos.TOP_RIGHT);
			qtyHeaderBox.setPadding(lesftInsets);
			qtyHeaderBox.getChildren().add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getPurchasedQty())));
			invoicePane.add(qtyHeaderBox, 2, invoiceRowIndex, 1, 1);

			VBox totalPriceHeaderBox = new VBox();
			totalPriceHeaderBox.setAlignment(Pos.TOP_RIGHT);
			totalPriceHeaderBox.setPadding(lesftInsets);
			invoicePane.add(totalPriceHeaderBox, 3, invoiceRowIndex, 1, 1);
			totalPriceHeaderBox.getChildren().add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getTotalSalesPrice())));
		}
	}
	
	BigDecimal totalAmountInvoices = BigDecimal.ZERO;
	BigDecimal totalAmountDiscount = BigDecimal.ZERO;
	BigDecimal totalAmountRestToPay = BigDecimal.ZERO;
	
	public void closePayment(){
		GridPane paymentPane = new GridPane();
		page.getChildren().add(paymentPane);
		paymentPane.getColumnConstraints().add(new ColumnConstraints(width * .80));
		paymentPane.getColumnConstraints().add(new ColumnConstraints(width * .2));

		int rowIndex =-1;
		
		rowIndex++;
		paymentPane.getRowConstraints().add(mainRowHeight);
		VBox cipHeaderBox = new VBox();
		cipHeaderBox.setAlignment(Pos.TOP_LEFT);
//		cipHeaderBox.setPadding(insets);
		cipHeaderBox.getChildren().add(new StandardText(
				resourceBundle.getString("ReceiptPrintTemplate_paymentMode.title")
				+" "+ resourceBundle.getString("PaymentMode_"+receiptPrinterData.getPayment().getPaymentMode().name()+"_description.title")
				));
		paymentPane.add(cipHeaderBox, 0, rowIndex, 2, 1);

		rowIndex++;
		paymentPane.getRowConstraints().add(mainRowHeight);
		VBox totalAmountLabelBox = new VBox();
		totalAmountLabelBox.setAlignment(Pos.TOP_LEFT);
//		totalAmountLabelBox.setPadding(insets);
		totalAmountLabelBox.getChildren().add(new StandardText(resourceBundle.getString("ReceiptPrintTemplate_totalAmount.title")));
		paymentPane.add(totalAmountLabelBox, 0, rowIndex, 1, 1);

		VBox totalAmountValueBox = new VBox();
		totalAmountValueBox.setAlignment(Pos.TOP_RIGHT);
//		totalAmountValueBox.setPadding(insets);
		paymentPane.add(totalAmountValueBox, 1, rowIndex, 1, 1);
		totalAmountValueBox.getChildren().add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountInvoices)));

		rowIndex++;
		paymentPane.getRowConstraints().add(mainRowHeight);
		VBox discountLabelBox = new VBox();
		discountLabelBox.setAlignment(Pos.TOP_LEFT);
//		discountLabelBox.setPadding(insets);
		discountLabelBox.getChildren().add(new StandardText(resourceBundle.getString("ReceiptPrintTemplate_discount.title")));
		paymentPane.add(discountLabelBox, 0, rowIndex, 1, 1);

		VBox discountValueBox = new VBox();
		discountValueBox.setAlignment(Pos.TOP_RIGHT);
//		discountValueBox.setPadding(insets);
		paymentPane.add(discountValueBox, 1, rowIndex, 1, 1);
		discountValueBox.getChildren().add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountDiscount)));

		rowIndex++;
		paymentPane.getRowConstraints().add(mainRowHeight);
		VBox netToPayLabelBox = new VBox();
		netToPayLabelBox.setAlignment(Pos.TOP_LEFT);
//		netToPayLabelBox.setPadding(insets);
		netToPayLabelBox.getChildren().add(new StandardText(resourceBundle.getString("ReceiptPrintTemplate_netToPay.title")));
		paymentPane.add(netToPayLabelBox, 0, rowIndex, 1, 1);

		VBox netToPayValueBox = new VBox();
		netToPayValueBox.setAlignment(Pos.TOP_RIGHT);
//		netToPayValueBox.setPadding(insets);
		paymentPane.add(netToPayValueBox, 1, rowIndex, 1, 1);
		netToPayValueBox.getChildren().add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountRestToPay)));

		rowIndex++;
		paymentPane.getRowConstraints().add(mainRowHeight);
		VBox amountReceivedLabelBox = new VBox();
		amountReceivedLabelBox.setAlignment(Pos.TOP_LEFT);
//		amountReceivedLabelBox.setPadding(insets);
		amountReceivedLabelBox.getChildren().add(new StandardText(resourceBundle.getString("ReceiptPrintTemplate_amountReceived.title")));
		paymentPane.add(amountReceivedLabelBox, 0, rowIndex, 1, 1);

		VBox amountReceivedValueBox = new VBox();
		amountReceivedValueBox.setAlignment(Pos.TOP_RIGHT);
//		amountReceivedValueBox.setPadding(insets);
		paymentPane.add(amountReceivedValueBox, 1, rowIndex, 1, 1);
		amountReceivedValueBox.getChildren().add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(receiptPrinterData.getPayment().getReceivedAmount())));

		rowIndex++;
		paymentPane.getRowConstraints().add(mainRowHeight);
		VBox amountReimbursedLabelBox = new VBox();
		amountReimbursedLabelBox.setAlignment(Pos.TOP_LEFT);
//		amountReimbursedLabelBox.setPadding(insets);
		amountReimbursedLabelBox.getChildren().add(new StandardText(resourceBundle.getString("ReceiptPrintTemplate_amountReimbursed.title")));
		paymentPane.add(amountReimbursedLabelBox, 0, rowIndex, 1, 1);

		VBox amountReimbursedValueBox = new VBox();
		amountReimbursedValueBox.setAlignment(Pos.TOP_RIGHT);
//		amountReimbursedValueBox.setPadding(insets);
		paymentPane.add(amountReimbursedValueBox, 1, rowIndex, 1, 1);
		amountReimbursedValueBox.getChildren().add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(receiptPrinterData.getPayment().getDifference())));
		
		
		GridPane greetingPane = new GridPane();
		page.getChildren().add(greetingPane);
		greetingPane.getColumnConstraints().add(new ColumnConstraints(width));


		int index = 0;
		greetingPane.getRowConstraints().add(mainRowHeight);
		Text firstSeparator = new StandardText(separatorText);
		greetingPane.add(firstSeparator, 0, index++, 1, 1);
		GridPane.setHalignment(firstSeparator, HPos.CENTER);

		int chuncksize = 55;
		String invoiceMessage = receiptPrinterData.getAgency().getInvoiceMessage();
		invoiceMessage = StringUtils.replace(invoiceMessage, "\n", " ");
		String[] split = StringUtils.split(invoiceMessage, ",");
		StringBuilder stringBuilder = new StringBuilder();
		for (String string : split) {
			if(stringBuilder.length()>0)
				stringBuilder.append(", ");
			stringBuilder.append(string);
		}
		invoiceMessage = stringBuilder.toString().toUpperCase();
		String str1 = StringUtils.substring(invoiceMessage, 0, chuncksize);
		str1 = StringUtils.substringBeforeLast(str1, " ");
		greetingPane.getRowConstraints().add(mainRowHeight);
		greetingPane.add(new StandardText(str1), 0,index++, 1, 1);
		invoiceMessage = StringUtils.substringAfter(invoiceMessage,str1).trim();
		if(StringUtils.isNotBlank(invoiceMessage)){
			String str2 = StringUtils.substring(invoiceMessage, 0, chuncksize);
			str2 = StringUtils.substringBeforeLast(str2, " ");
			greetingPane.getRowConstraints().add(mainRowHeight);
			greetingPane.add(new StandardText(str2), 0,index++, 1, 1);
			invoiceMessage = StringUtils.substringAfter(invoiceMessage,str2).trim();
			if(StringUtils.isNotBlank(invoiceMessage)){
				String str3 = StringUtils.substring(invoiceMessage, 0, chuncksize);
				str3 = StringUtils.substringBeforeLast(str3, " ");
				greetingPane.getRowConstraints().add(mainRowHeight);
				greetingPane.add(new StandardText(str3), 0,index++, 1, 1);
				invoiceMessage = StringUtils.substringAfter(invoiceMessage,str3).trim();
				if(StringUtils.isNotBlank(invoiceMessage)){
					String str4 = StringUtils.substring(invoiceMessage, 0, chuncksize);
					str4 = StringUtils.substringBeforeLast(str4, " ");
					greetingPane.getRowConstraints().add(mainRowHeight);
					greetingPane.add(new StandardText(str4), 0,index++, 1, 1);
				}
			}
		}
		
	}
	
	int invoiceIndex = 0;
	public CustomerInvoicePrinterData nextInvoice(){
		if(receiptPrinterData.getInvoiceData().size()<=invoiceIndex) return null;
		return receiptPrinterData.getInvoiceData().get(invoiceIndex++);
	}

	public VBox getPage() {
		return page;
	}
	
	
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

	@Override
	public ReceiptPrinterData getReceiptPrinterData() {
		return receiptPrinterData;
	}

	@Override
	public String getReceiptPrinterName() {
		return "receipt";
	}

	@Override
	public ReceiptPrintMode getReceiptPrintMode() {
		return ReceiptPrintMode.open;
	}
	
}
