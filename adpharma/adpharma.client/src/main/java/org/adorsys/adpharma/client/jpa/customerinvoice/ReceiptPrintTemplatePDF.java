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
import org.jboss.weld.exceptions.IllegalStateException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;

public class ReceiptPrintTemplatePDF extends ReceiptPrintTemplate {

	private CalendarFormat calendarFormat = new CalendarFormat();

	private static ReceiptPrintProperties receiptPrintProperties = new ReceiptPrintProperties();

	static final String separatorText = "------------------------";

	private Document document;
	private ByteArrayOutputStream bos;

	private final ReceiptPrinterData receiptPrinterData;
	private final ResourceBundle resourceBundle;
	private final Locale locale;

	public ReceiptPrintTemplatePDF(ReceiptPrinterData receiptPrinterData,
			ResourceBundle resourceBundle, Locale locale) {
		this.receiptPrinterData = receiptPrinterData;
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		if(receiptPrintProperties.isDebug())
			receiptPrintProperties = ReceiptPrintProperties.loadPrintProperties();

	}

	public void startPage() {


		document = new Document(receiptPrintProperties.getPageSize());
		document.setMargins(1, 1, 1, 1);
		bos = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, bos);
			document.open();
			printReceiptHeader();
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		}
	}

	double designationRowWidth = 0;
	boolean printOrder = true;

	private void printReceiptHeader() throws DocumentException {

		PdfPTable rt = new PdfPTable(1);
		rt.setWidthPercentage(100);

		Paragraph documentName = new CenterParagraph(new BoldText(
				resourceBundle
				.getString("ReceiptPrintTemplate_cashReceipt.title")
				+ " "
				+ receiptPrinterData.getPayment().getPaymentNumber()));

		borderlessCell(rt, documentName);

		borderlessCell(rt,new CenterParagraph(separatorText));

		Paragraph paragraph = new Paragraph(new StandardText(receiptPrinterData
				.getCompany().getDisplayName()));
		borderlessCell(rt, paragraph);

		//		paragraph = new Paragraph(new BoldText(receiptPrinterData.getCompany()
		//				.getSiteManager()));
		//		borderlessCell(rt, paragraph);

		paragraph = new Paragraph(new StandardText(receiptPrinterData
				.getAgency().getStreet()));
		borderlessCell(rt, paragraph);

		paragraph = new Paragraph(new StandardText("TEL: "
				+ receiptPrinterData.getAgency().getPhone() + " Email : "
				+ receiptPrinterData.getCompany().getEmail()));
		borderlessCell(rt, paragraph);

		//		paragraph = new Paragraph(new StandardText(
		//				resourceBundle.getString("ReceiptPrintTemplate_email.title")
		//				+ " " + receiptPrinterData.getCompany().getEmail()));
		//		borderlessCell(rt, paragraph);

		//		paragraph = new Paragraph(new StandardText(
		//				resourceBundle
		//				.getString("ReceiptPrintTemplate_registerNumber.title")
		//				+ " "
		//				+ receiptPrinterData.getCompany().getRegisterNumber()));
		//		borderlessCell(rt, paragraph);

		borderlessCell(rt, new CenterParagraph(separatorText));

		//		paragraph = new Paragraph(
		//				new StandardText(
		//						resourceBundle
		//						.getString("ReceiptPrintTemplate_receipt.title")
		//						+ " "
		//						+ receiptPrinterData.getPayment()
		//						.getPaymentNumber()
		//						+ resourceBundle
		//						.getString("ReceiptPrintTemplate_receipt_of.title")
		//						+ " "
		//						+ calendarFormat.format(receiptPrinterData
		//								.getPayment().getPaymentDate(),
		//								"dd-MM-yyyy HH:mm", locale)));
		//		borderlessCell(rt, paragraph);

		paragraph = new Paragraph(new StandardText(
				resourceBundle.getString("ReceiptPrintTemplate_cashier.title")
				+ " " + receiptPrinterData.getCashier().getFullName()));
		borderlessCell(rt, paragraph);

		if (receiptPrinterData.getInvoiceData().size() > 1) {
			printOrder = true;
		}

		document.add(rt);
	}

	private PdfPCell borderlessCell(PdfPTable table, Element... elements){
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setBorder(Rectangle.NO_BORDER);
		for (Element element : elements) {
			pdfPCell.addElement(element);
		}
		table.addCell(pdfPCell);
		return pdfPCell;
	}

	private PdfPCell borderlessCell(PdfPTable table, Element element, int colspan, int rowspan){
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setBorder(Rectangle.NO_BORDER);
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(rowspan);
		pdfPCell.addElement(element);
		table.addCell(pdfPCell);
		return pdfPCell;
	}

	PdfPTable invoiceTable = null;

	private BigDecimal coverRate ;
	private BigDecimal customerRest ;
	private BigDecimal insurranceRest ;
	private Boolean hasInsurrance = Boolean.FALSE ;


	@Override
	public void printInvoiceHeader(CustomerInvoicePrinterData invoiceData) {
		CustomerInvoiceInsurance insurance = invoiceData.getCustomerInvoice().getInsurance();
		if(insurance!=null&& insurance.getId()!=null){
			BigDecimal insurrancerate = insurance.getCoverageRate().divide(BigDecimal.valueOf(100));
			hasInsurrance = Boolean.TRUE ;
			coverRate = insurance.getCoverageRate();
			insurranceRest = invoiceData.getCustomerInvoice().getNetToPay().multiply(insurrancerate);
//			insurranceRest = invoiceData.getCustomerInvoice().getInsurranceRestTopay();
			customerRest = invoiceData.getCustomerInvoice().getNetToPay().subtract(insurrancerate);

		}
		int invoiceTableColCount = 3;
		try {
			if (invoiceTable != null)
				document.add(invoiceTable);

			if(receiptPrintProperties.isMergeCipAndDesignation()){
				invoiceTable = new PdfPTable(new float[] { .6f, .13f, .27f });
				designationRowWidth = receiptPrintProperties.getWidth() * .6;
				invoiceTableColCount = 3;
			} else {
				invoiceTable = new PdfPTable(new float[] {.17f, .47f, .06f, .2f });
				designationRowWidth = receiptPrintProperties.getWidth() * .47;
				invoiceTableColCount = 4;
			}
			invoiceTable.setWidthPercentage(100);

			if (printOrder) {

				StandardText standardText = new StandardText(
						resourceBundle
						.getString("ReceiptPrintTemplate_salesOrder.title")
						+ " "
						+ invoiceData.getCustomerInvoice()
						.getSalesOrder().getSoNumber()+" Du : "+calendarFormat.format(receiptPrinterData
								.getPayment().getPaymentDate(),
								"dd-MM-yyyy HH:mm", locale));
				borderlessCell(invoiceTable, new Paragraph(standardText), invoiceTableColCount,1);
			}

			StandardText standardText = new StandardText(
					resourceBundle
					.getString("ReceiptPrintTemplate_salesAgent.title")
					+ " " + invoiceData.getLogin().getFullName());
			borderlessCell(invoiceTable, new Paragraph(standardText), invoiceTableColCount,1);
			String customerName = invoiceData.getCustomerInvoice().getCustomer()
					.getFullName();
			if(StringUtils.isNotBlank(receiptPrinterData.getCustomerName()))
				customerName = receiptPrinterData.getCustomerName();
			Paragraph paragraph = new Paragraph(new StandardText(
					resourceBundle
					.getString("ReceiptPrintTemplate_customer.title")
					+ " "
					+ customerName));
			borderlessCell(invoiceTable, paragraph, invoiceTableColCount,1);

			borderlessCell(invoiceTable, new CenterParagraph(separatorText), invoiceTableColCount,1);


			totalAmountInvoices = totalAmountInvoices.add(invoiceData
					.getCustomerInvoice().getAmountAfterTax());
			totalAmountDiscount = totalAmountDiscount.add(invoiceData
					.getCustomerInvoice().getAmountDiscount());
			totalAmountRestToPay = totalAmountRestToPay.add(invoiceData
					.getCustomerInvoice().getTotalRestToPay());

			if(receiptPrintProperties.isMergeCipAndDesignation()){
				borderlessCell(invoiceTable, new Paragraph(
						new BoldText(resourceBundle.getString("ReceiptPrintTemplate_cip.title") + "/"+resourceBundle.getString("ReceiptPrintTemplate_designation.title"))));
			} else {
				borderlessCell(invoiceTable, new Paragraph(new BoldText(resourceBundle.getString("ReceiptPrintTemplate_cip.title"))));
				borderlessCell(invoiceTable, new Paragraph(new BoldText(resourceBundle.getString("ReceiptPrintTemplate_designation.title"))));
			}
			borderlessCell(invoiceTable, new RightParagraph(new BoldText(resourceBundle.getString("ReceiptPrintTemplate_quantity.title"))));

			borderlessCell(invoiceTable, new RightParagraph(new BoldText(resourceBundle.getString("ReceiptPrintTemplate_totalPrice.title"))));
		} catch (DocumentException d) {
			throw new IllegalStateException(d);
		}
	}

	public void addItems(List<CustomerInvoiceItem> invoiceItems) {
		for (CustomerInvoiceItem customerInvoiceItem : invoiceItems) {

			String articleName = customerInvoiceItem.getArticle()
					.getArticleName();
			if (StringUtils.isNotBlank(articleName)
					&& articleName.length() > receiptPrintProperties.getArticleNameMaxLength()) {
				articleName = StringUtils.substring(articleName, 0, receiptPrintProperties.getArticleNameMaxLength());
			}

			if(receiptPrintProperties.isMergeCipAndDesignation()){
				Paragraph cip = new Paragraph(new StandardText(customerInvoiceItem.getInternalPic()));
				Paragraph artName = new Paragraph(new StandardText(articleName));	
				borderlessCell(invoiceTable, cip,artName);
			} else {
				borderlessCell(invoiceTable, new StandardText(customerInvoiceItem
						.getInternalPic()));
				borderlessCell(invoiceTable,new StandardText(articleName));
			}

			borderlessCell(invoiceTable,new RightParagraph(new StandardText(
					DefaultBigDecimalFormatCM.getinstance().format(
							customerInvoiceItem.getPurchasedQty()))));

			borderlessCell(invoiceTable,new RightParagraph(new StandardText(
					DefaultBigDecimalFormatCM.getinstance().format(
							customerInvoiceItem.getTotalSalesPrice()))));
		}
	}

	BigDecimal totalAmountInvoices = BigDecimal.ZERO;
	BigDecimal totalAmountDiscount = BigDecimal.ZERO;
	BigDecimal totalAmountRestToPay = BigDecimal.ZERO;

	public void closePayment() {
		if (invoiceTable != null)
			try {
				document.add(invoiceTable);
			} catch (DocumentException e) {
				throw new IllegalStateException(e);
			}

		PdfPTable paymentPane = new PdfPTable(new float[] { .8f, .2f });
		paymentPane.setWidthPercentage(100);

		borderlessCell(paymentPane,new CenterParagraph(separatorText), 2, 1);

		borderlessCell(paymentPane,new StandardText(resourceBundle
				.getString("ReceiptPrintTemplate_paymentMode.title")
				+ " "
				+ resourceBundle.getString("PaymentMode_"
						+ receiptPrinterData.getPayment().getPaymentMode()
						.name() + "_description.title")), 2,1);

		borderlessCell(paymentPane,new StandardText(resourceBundle
				.getString("ReceiptPrintTemplate_totalAmount.title")));

		borderlessCell(paymentPane,new RightParagraph(new StandardText(
				DefaultBigDecimalFormatCM.getinstance().format(
						totalAmountInvoices))));

		borderlessCell(paymentPane,new StandardText(resourceBundle
				.getString("ReceiptPrintTemplate_discount.title")));

		borderlessCell(paymentPane,new RightParagraph(new StandardText(
				DefaultBigDecimalFormatCM.getinstance().format(
						totalAmountDiscount))));
		borderlessCell(paymentPane,new StandardText(resourceBundle
				.getString("ReceiptPrintTemplate_netToPay.title")));

		borderlessCell(paymentPane,new RightParagraph(new StandardText(
				DefaultBigDecimalFormatCM.getinstance().format(
						totalAmountInvoices.subtract(totalAmountDiscount)))));


		if(hasInsurrance){
			borderlessCell(paymentPane,new StandardText(resourceBundle
					.getString("ReceiptPrintTemplate_coverRate.title")));

			borderlessCell(paymentPane,new RightParagraph(new StandardText(
					DefaultBigDecimalFormatCM.getinstance().format(
							coverRate)+"%")));


			borderlessCell(paymentPane,new StandardText(resourceBundle
					.getString("ReceiptPrintTemplate_insuranceRest.title")));

			borderlessCell(paymentPane,new RightParagraph(new StandardText(
					DefaultBigDecimalFormatCM.getinstance().format(
							insurranceRest))));

			borderlessCell(paymentPane,new StandardText(resourceBundle
					.getString("ReceiptPrintTemplate_clientRest.title")));


			//			borderlessCell(paymentPane,new RightParagraph(new StandardText(
			//					DefaultBigDecimalFormatCM.getinstance().format(customerRest))));
			borderlessCell(paymentPane,new RightParagraph(new StandardText(
					DefaultBigDecimalFormatCM.getinstance().format(receiptPrinterData.getPayment().getReceivedAmount().subtract(receiptPrinterData.getPayment().getDifference())))));
		}

		borderlessCell(paymentPane,new StandardText(resourceBundle
				.getString("ReceiptPrintTemplate_amountReceived.title")));

		borderlessCell(paymentPane,new RightParagraph(new StandardText(
				DefaultBigDecimalFormatCM.getinstance().format(
						receiptPrinterData.getPayment().getReceivedAmount()))));

		borderlessCell(paymentPane,new StandardText(resourceBundle
				.getString("ReceiptPrintTemplate_amountReimbursed.title")));

		borderlessCell(paymentPane,new RightParagraph(new StandardText(
				DefaultBigDecimalFormatCM.getinstance().format(
						receiptPrinterData.getPayment().getDifference()))));
		try {
			document.add(paymentPane);

			document.add(Chunk.NEWLINE);

			PdfPTable salutationPane = new PdfPTable(1);
			salutationPane.setWidthPercentage(100);

			String invoiceMessage = receiptPrinterData.getAgency()
					.getInvoiceMessage();
			Paragraph paragraph = new Paragraph(new StandardText(invoiceMessage));
			borderlessCell(salutationPane, paragraph);

			// add voucher details

			List<CustomerVoucher> usedVouchers = receiptPrinterData.getUsedVouchers();
			if(!usedVouchers.isEmpty()){
				PdfPTable rt = new PdfPTable(1);
				borderlessCell(rt,new CenterParagraph(separatorText));

				PdfPTable voucherHeader = new PdfPTable(2);
				voucherHeader.setWidthPercentage(100);
				borderlessCell(voucherHeader, new BoldText(resourceBundle
						.getString("ReceiptPrintTemplate_voucherNumer.title")));
				borderlessCell(voucherHeader, new BoldText(resourceBundle
						.getString("ReceiptPrintTemplate_voucherRest.title")));

				PdfPTable voucherPane = new PdfPTable(2);
				voucherPane.setWidthPercentage(100);

				for (CustomerVoucher customerVoucher : usedVouchers) {

					Paragraph voucherParagraphe = new Paragraph(new StandardText(customerVoucher.getVoucherNumber()));
					Paragraph amountParagraphe = new Paragraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(
							customerVoucher.getRestAmount())));
					borderlessCell(voucherPane, voucherParagraphe);
					borderlessCell(voucherPane, amountParagraphe);
				}
				document.add(rt);
				document.add(voucherHeader);
				document.add(voucherPane);
			}

			document.add(salutationPane);

			document.setPageCount(1);
			document.close();
			bos.close();
			byte[] byteArray = bos.toByteArray();
			bos = new ByteArrayOutputStream();
			PdfReader pdfReader = new PdfReader(byteArray);
			PdfStamper stamper = new PdfStamper(pdfReader, bos);

			PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
			TextMarginFinder finder = parser.processContent(1, new TextMarginFinder());

			PdfDictionary page = pdfReader.getPageN(1);
			Rectangle pageSize = receiptPrintProperties.getPageSize();
			page.put(PdfName.CROPBOX, new PdfArray(new float[]{pageSize.getLeft(), finder.getLly(), pageSize.getRight(), pageSize.getTop()}));
			stamper.markUsed(page);
			stamper.close();
			pdfReader.close();

		} catch (DocumentException | IOException d) {
			throw new IllegalStateException(d);
		}
	}

	int invoiceIndex = 0;

	public CustomerInvoicePrinterData nextInvoice() {
		if (receiptPrinterData.getInvoiceData().size() <= invoiceIndex)
			return null;
		return receiptPrinterData.getInvoiceData().get(invoiceIndex++);
	}

	public byte[] getPage() {
		return bos.toByteArray();
	}

	static class StandardText extends Phrase {
		private static final long serialVersionUID = -5796192414147292471L;

		StandardText() {
			super();
			setFont(receiptPrintProperties.getMyFont());
		}

		StandardText(String text) {
			super(text);
			setFont(receiptPrintProperties.getMyFont());
		}
	}

	static class BoldText extends Phrase {
		private static final long serialVersionUID = -6569891897489003768L;

		BoldText() {
			super();
			setFont(receiptPrintProperties.getMyBoldFont());
		}

		BoldText(String text) {
			super(text);
			setFont(receiptPrintProperties.getMyBoldFont());
		}
	}

	static class RightParagraph extends Paragraph {
		private static final long serialVersionUID = 986392503142787342L;

		public RightParagraph(Phrase phrase) {
			super(phrase);
			setAlignment(Element.ALIGN_RIGHT);
		}

		public RightParagraph(String string) {
			this(new StandardText(string));
		}
	}

	static class CenterParagraph extends Paragraph {

		private static final long serialVersionUID = -5432125323541770319L;

		public CenterParagraph(Phrase phrase) {
			super(phrase);
			setAlignment(Element.ALIGN_CENTER);
		}

		public CenterParagraph(String string) {
			this(new StandardText(string));
		}
	}

	@Override
	public ReceiptPrinterData getReceiptPrinterData() {
		return receiptPrinterData;
	}

	@Override
	public String getReceiptPrinterName(){
		return receiptPrintProperties.getReceiptPrinterName();
	}

	@Override
	public ReceiptPrintMode getReceiptPrintMode() {
		return receiptPrintProperties.getReceiptPrintMode();
	}
}