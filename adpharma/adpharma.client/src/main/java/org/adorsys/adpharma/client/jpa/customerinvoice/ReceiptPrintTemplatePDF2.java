package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public class ReceiptPrintTemplatePDF2 extends ReceiptPrintTemplate {

	private CalendarFormat calendarFormat = new CalendarFormat();

	static final String separatorText = "------------------------";
	
	private ByteArrayOutputStream bos;

	private final ReceiptPrinterData receiptPrinterData;
	private final ResourceBundle resourceBundle;
	private final Locale locale;

	private static ReceiptPrintProperties2 rpp = new ReceiptPrintProperties2();
	private static ReceiptDataItemMetaData itemMataData;
	private static ReceiptDataTableMetaData tableMetaData;
	private static ReceiptDataTwoColsMetaData twoColsMetaData;

	private boolean printOrder=Boolean.TRUE;
	
	static {
		initProperties();
	}
	
	public ReceiptPrintTemplatePDF2(ReceiptPrinterData receiptPrinterData,
			ResourceBundle resourceBundle, Locale locale) {
		this.receiptPrinterData = receiptPrinterData;
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		if(rpp.isDebug()){
			initProperties();
		}
		
	}
	
	private static final void initProperties(){
		rpp = ReceiptPrintProperties2.loadPrintProperties();
		itemMataData = new ReceiptDataItemMetaData(rpp.getRowHeight(), rpp.getWidth(),rpp.getMargin(),
				rpp.getCellMargin(), rpp.getCodeWidth(), rpp.getQuantityWidth(), rpp.getTotalWidth());
		tableMetaData = new ReceiptDataTableMetaData(rpp.getMargin(), rpp.getWidth(), rpp.getFontSize(), rpp.getRowHeight());
		twoColsMetaData = new ReceiptDataTwoColsMetaData(rpp.getRowHeight(), rpp.getWidth(),rpp.getMargin(), 
				rpp.getCellMargin(), rpp.getTotalWidth());
		
	}

	private ReceiptDataTable rdt;
	public void startPage() {
		try {
			printReceiptHeader();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void printReceiptHeader() throws IOException {
		rdt = new ReceiptDataTable(tableMetaData);
		
		new ReceiptDataOneColBuilder(tableMetaData)
			.withBoldFont()
			.withCenterAlign()
			.withDesignation(resourceBundle
				.getString("ReceiptPrintTemplate_cashReceipt.title")
				+ " "
				+ receiptPrinterData.getPayment().getPaymentNumber()).build(rdt);

		// underline
		if(rpp.isDisplayTitleUnderLine())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);
		
		// pharmacyName  
		if(rpp.isDisplayPharmacyName())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withDesignation(receiptPrinterData.getCompany().getDisplayName()).build(rdt);
			
		// ownerName
		if(rpp.isDisplayOwnerName())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withBoldFont()
			.withDesignation(receiptPrinterData.getCompany().getSiteManager()).build(rdt);

		// address 
		if(rpp.isDisplayAddress())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withDesignation(receiptPrinterData.getAgency().getStreet()).build(rdt);
		
		if(rpp.isDisplayTelFax()){
			String phoneAndFax = "TEL: "
					+ receiptPrinterData.getAgency().getPhone() + " FAX: "
					+ receiptPrinterData.getAgency().getFax();
			new ReceiptDataOneColBuilder(tableMetaData)
				.withDesignation(phoneAndFax).build(rdt);
		}
		
		// email
		if(rpp.isDisplayEmail())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_email.title")
				+ " " + receiptPrinterData.getCompany().getEmail()).build(rdt);
		
		// compnyRregisterNumber
		if(rpp.isDisplayRegisterNumber())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withDesignation(resourceBundle
					.getString("ReceiptPrintTemplate_registerNumber.title")
					+ " "
					+ receiptPrinterData.getCompany().getRegisterNumber()).build(rdt);

		// underline
		if(rpp.isDisplayRegisterUnderline())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);
		
		if(rpp.isDisplayTicketNumberAndDate()){
			String receiptNumberAndDate=
					resourceBundle
					.getString("ReceiptPrintTemplate_receipt.title")
					+ " "
					+ receiptPrinterData.getPayment()
					.getPaymentNumber()
					+ " "
					+ resourceBundle
					.getString("ReceiptPrintTemplate_receipt_of.title")
					+ " "
					+ calendarFormat.format(receiptPrinterData
							.getPayment().getPaymentDate(),
							"dd-MM-yyyy HH:mm", locale);
			new ReceiptDataOneColBuilder(tableMetaData)
				.withDesignation(receiptNumberAndDate).build(rdt);
		}

		// cashierName
		if(rpp.isDisplayCashier())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_cashier.title")
					+ " " + receiptPrinterData.getCashier().getFullName()).build(rdt);

		if (receiptPrinterData.getInvoiceData().size() > 1) {
			printOrder = true;
		}
	}

	private BigDecimal coverRate ;
	private BigDecimal customerRest ;
	private BigDecimal insurranceRest ;
	private Boolean hasInsurrance = Boolean.FALSE ;

	@Override
	public void printInvoiceHeader(CustomerInvoicePrinterData invoiceData) {
		try {
			printInvoiceHeaderInternal(invoiceData);
		} catch (IOException e) {
			throw new java.lang.IllegalStateException(e);
		}
	}
	
	public void printInvoiceHeaderInternal(CustomerInvoicePrinterData invoiceData) throws IOException {
		CustomerInvoiceInsurance insurance = invoiceData.getCustomerInvoice().getInsurance();
		if(insurance!=null&& insurance.getId()!=null){
			hasInsurrance = Boolean.TRUE ;
			coverRate = insurance.getCoverageRate();
			insurranceRest = invoiceData.getCustomerInvoice().getInsurranceRestTopay();
			// If the invoice is reprinted, this will show zero.
			customerRest = invoiceData.getCustomerInvoice().getCustomerRestTopay();
//			if(invoiceData.getCustomerInvoice().getPrinted()!=null)
			if(BigDecimal.ZERO.compareTo(customerRest)>=0)// reprint.
				customerRest = invoiceData.getCustomerInvoice().getNetToPay().subtract(insurranceRest);

		}
			
		if (printOrder) {
			new ReceiptDataOneColBuilder(tableMetaData)
				.withDesignation(resourceBundle
					.getString("ReceiptPrintTemplate_salesOrder.title")
					+ " "
					+ invoiceData.getCustomerInvoice()
					.getSalesOrder().getSoNumber()).build(rdt);
		}

		if(rpp.isDisplaySalesAgent())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withDesignation(resourceBundle
				.getString("ReceiptPrintTemplate_salesAgent.title")
				+ " " + invoiceData.getLogin().getFullName()).build(rdt);

		if(rpp.isDisplayClient()){
//			if(rpp.isDisplayClientDiver())// todo check client divers and do not display.
			String customerName = invoiceData.getCustomerInvoice().getCustomer()
					.getFullName();
			if(StringUtils.isNotBlank(receiptPrinterData.getCustomerName()))
				customerName = receiptPrinterData.getCustomerName();
			new ReceiptDataOneColBuilder(tableMetaData)
				.withDesignation(resourceBundle
					.getString("ReceiptPrintTemplate_customer.title")
					+ " "
					+ customerName).build(rdt);
		}

		totalAmountInvoices = totalAmountInvoices.add(invoiceData
				.getCustomerInvoice().getAmountAfterTax());
		totalAmountDiscount = totalAmountDiscount.add(invoiceData
				.getCustomerInvoice().getAmountDiscount());
		totalAmountRestToPay = totalAmountRestToPay.add(invoiceData
				.getCustomerInvoice().getTotalRestToPay());

		// underline
		new ReceiptDataOneColBuilder(tableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);

		// Invoice header
		ReceiptDataItemBuilder invoiceTableHeader = new ReceiptDataItemBuilder(tableMetaData, itemMataData)
			.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_designation.title"))
			.withQuantity(resourceBundle.getString("ReceiptPrintTemplate_quantity.title"))
			.withTotal(resourceBundle.getString("ReceiptPrintTemplate_totalPrice.title"))
			.withBoldFont();
		if(itemMataData.isMergeCodeAndDesignation()){
			invoiceTableHeader = invoiceTableHeader.withCode("");
		} else {
			invoiceTableHeader = invoiceTableHeader.withCode(resourceBundle.getString("ReceiptPrintTemplate_cip.title"));
		}
		invoiceTableHeader.build(rdt);
	}

	public void addItems(List<CustomerInvoiceItem> invoiceItems) {
		for (CustomerInvoiceItem customerInvoiceItem : invoiceItems) {
			try {
				new ReceiptDataItemBuilder(tableMetaData, itemMataData)
					.withCode(customerInvoiceItem.getInternalPic())
					.withDesignation(customerInvoiceItem.getArticle().getArticleName())
					.withQuantity(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getPurchasedQty()))
					.withTotal(DefaultBigDecimalFormatCM.getinstance().format(customerInvoiceItem.getTotalSalesPrice()))
					.build(rdt);
			} catch (IOException e) {
				throw new java.lang.IllegalStateException(e);
			}
		}
	}

	BigDecimal totalAmountInvoices = BigDecimal.ZERO;
	BigDecimal totalAmountDiscount = BigDecimal.ZERO;
	BigDecimal totalAmountRestToPay = BigDecimal.ZERO;
	public void closePayment() {
		try {
			closePaymentInternal();
		} catch (IOException e) {
			throw new java.lang.IllegalStateException(e);
		}
	}
	private void closePaymentInternal() throws IOException {
		
		// underline
		if(rpp.isDisplayInvoiceUnderline())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withCenterAlign().withDesignation(separatorText).build(rdt);

		if(rpp.isDisplayPaymentMode())
		new ReceiptDataOneColBuilder(tableMetaData)
			.withDesignation(resourceBundle
				.getString("ReceiptPrintTemplate_paymentMode.title")
				+ " "
				+ resourceBundle.getString("PaymentMode_"
						+ receiptPrinterData.getPayment().getPaymentMode()
						.name() + "_description.title"))
			.build(rdt);
		
		new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
			.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_totalAmount.title"))
			.withValue(DefaultBigDecimalFormatCM.getinstance().format(totalAmountInvoices))
			.build(rdt);

		if(BigDecimal.ZERO.compareTo(totalAmountDiscount)>0 || rpp.isAlwaysShowDiscount()){
			new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
				.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_discount.title"))
				.withValue(DefaultBigDecimalFormatCM.getinstance().format(totalAmountDiscount))
				.build(rdt);
	
			new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
				.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_netToPay.title"))
				.withValue(DefaultBigDecimalFormatCM.getinstance().format(totalAmountInvoices.subtract(totalAmountDiscount)))
				.build(rdt);
		}

		if(hasInsurrance){
			new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
				.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_coverRate.title"))
				.withValue(DefaultBigDecimalFormatCM.getinstance().format(coverRate)+"%")
				.build(rdt);

			new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
				.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_insuranceRest.title"))
				.withValue(DefaultBigDecimalFormatCM.getinstance().format(insurranceRest))
				.build(rdt);
			
			new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
				.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_clientRest.title"))
				.withValue(DefaultBigDecimalFormatCM.getinstance().format(customerRest))
				.build(rdt);
		}

		new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
			.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_amountReceived.title"))
			.withValue(DefaultBigDecimalFormatCM.getinstance().format(receiptPrinterData.getPayment().getReceivedAmount()))
			.build(rdt);

		new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
			.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_amountReimbursed.title"))
			.withValue(DefaultBigDecimalFormatCM.getinstance().format(receiptPrinterData.getPayment().getDifference()))
			.build(rdt);


		List<CustomerVoucher> usedVouchers = receiptPrinterData.getUsedVouchers();
		if(!usedVouchers.isEmpty()){
			// underline
			new ReceiptDataOneColBuilder(tableMetaData)
				.withCenterAlign().withDesignation(separatorText).build(rdt);

			new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
				.withDesignation(resourceBundle.getString("ReceiptPrintTemplate_voucherNumer.title"))
				.withValue(resourceBundle.getString("ReceiptPrintTemplate_voucherRest.title"))
				.withBoldFont()
				.build(rdt);
			
			for (CustomerVoucher customerVoucher : usedVouchers) {
				new ReceiptDataTwoColsBuilder(tableMetaData, twoColsMetaData)
					.withDesignation(customerVoucher.getVoucherNumber())
					.withValue(DefaultBigDecimalFormatCM.getinstance().format(customerVoucher.getRestAmount()))
					.withBoldFont()
					.build(rdt);
			}
		}
		
		new ReceiptDataOneColBuilder(tableMetaData)
			.withDesignation(receiptPrinterData.getAgency().getInvoiceMessage())
			.build(rdt);
		
		PDDocument pdDocument = new PDDocument();
		float docHeight = rdt.computeHeight();
		float docWidth = tableMetaData.getWidth();
		PDPage sourcePage = new PDPage(new PDRectangle(tableMetaData.getWidth(), rdt.computeHeight()));
		pdDocument.addPage(sourcePage);
		PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, sourcePage );
		pageContentStream.setFont(tableMetaData.getFont(), tableMetaData.getFontSize() );
		rdt.write(pageContentStream, docWidth, docHeight);
		pageContentStream.close();
		bos = new ByteArrayOutputStream();
		try {
			pdDocument.save(bos);
		} catch (COSVisitorException e) {
			throw new IOException(e);
		}
		pdDocument.close();
	}

	int invoiceIndex = 0;

	public CustomerInvoicePrinterData nextInvoice() {
		if (receiptPrinterData.getInvoiceData().size() <= invoiceIndex)
			return null;
		return receiptPrinterData.getInvoiceData().get(invoiceIndex++);
	}

	public byte[] getPage() {
		if(bos==null) return null;
		return bos.toByteArray();
	}

	@Override
	public ReceiptPrinterData getReceiptPrinterData() {
		return receiptPrinterData;
	}

	@Override
	public String getReceiptPrinterName(){
		return rpp.getReceiptPrinterName();
	}

	@Override
	public ReceiptPrintMode getReceiptPrintMode() {
		return rpp.getReceiptPrintMode();
	}
}