package org.adorsys.adpharma.client.jpa.salesorder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;
import org.apache.commons.io.IOUtils;
import org.jboss.weld.exceptions.IllegalStateException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class SalesOrderPrintTemplate {
	
	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;

	private PdfPTable invoiceTable = null;

	private final SalesOrder salesOrder;
	private final Company company;
	private final Agency agency;
	private final Login salesAgent;
	private final ResourceBundle resourceBundle;
	private final Locale locale;

	public SalesOrderPrintTemplate(SalesOrderPrinterData invoicePrinterData, ResourceBundle resourceBundle,
			Locale locale) {
		this.salesOrder = invoicePrinterData.getSalesOrder();
		this.company = invoicePrinterData.getCompany();
		this.agency = invoicePrinterData.getAgency();
		this.salesAgent = invoicePrinterData.getLogin();
		this.resourceBundle = resourceBundle;
		this.locale = locale;

		printReportHeader();
		
		fillTableHaeder();		
	}
	private void printReportHeader() {
		document = new Document(PageSize.A4, 50,50,50,50);
		String soNumber = salesOrder.getSoNumber();
		try {
			fos = new FileOutputStream(soNumber+".pdf");
			PdfWriter.getInstance(document, fos);
			document.open();
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
		
		PdfPTable rt = new PdfPTable(2);
		rt.setWidthPercentage(100);

		Paragraph documentName = new CenterParagraph(new BoldText(
				resourceBundle.getString("SalesOrderPrintTemplate_invoice.title")+soNumber));
		borderlessCell(rt, documentName, 2, 1);

		borderlessCell(rt, new LineSeparator(), 2, 1);

		Paragraph paragraph = new Paragraph(new BoldText(agency.getName()));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new BoldText(company.getSiteManager()));
		borderlessCell(rt, paragraph, 2, 1);
		
		paragraph = new Paragraph(new StandardText(agency.getStreet()));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText("Tel: " + agency.getPhone() + " Fax: " + agency.getFax()));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("Company_email_description.title") 
				+ ": " + company.getEmail()));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("Company_registerNumber_description.title") 
				+ ": " + company.getRegisterNumber()));
		borderlessCell(rt, paragraph, 1, 1);

		borderlessCell(rt, new CenterParagraph(""));

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_customerName.title") + " " + salesOrder.getCustomer().getFullName()));
		borderlessCell(rt, paragraph, 1, 1);
		
		paragraph = new Paragraph(new StandardText(
				resourceBundle.getString("SalesOrderPrintTemplate_invoiceDate.title")
				+ " " + calendarFormat.format(salesOrder.getCreationDate(), "dd-MM-yyyy HH:mm", locale)));
		borderlessCell(rt, paragraph, 1, 1);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_salesAgent.title") 
				+ " " + salesAgent.getFullName()));
		borderlessCell(rt, paragraph, 1, 1);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_invoiceNr.title") 
				+ " " + salesOrder.getSoNumber()));
		borderlessCell(rt, paragraph, 1, 1);
		
		borderlessCell(rt, new CenterParagraph(""));

		try {
			document.add(rt);
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private void fillTableHaeder() {
		invoiceTable = new PdfPTable(new float[] { .18f, .37f, .06f,.11f,.11f,.15f});
		invoiceTable.setWidthPercentage(100);

		Paragraph par = new Paragraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_cip.title")));
		borderlessCell(invoiceTable, par);

		par = new CenterParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_designation.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_qty.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_spputtc.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_sppuht.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_totalPriceHT.title")));
		borderlessCell(invoiceTable, par);
	}

	private BigDecimal totalAmountHTBeforeDiscount = BigDecimal.ZERO;
	private BigDecimal totalAmountHTAfterDiscount = BigDecimal.ZERO;
	private BigDecimal totalAmountDiscount = BigDecimal.ZERO;
	private BigDecimal totalAmountTax = BigDecimal.ZERO;
	
	public void addItems(List<SalesOrderItem> invoiceItems) {
		for (SalesOrderItem soItem : invoiceItems) {
			Paragraph par = new Paragraph(new StandardText(soItem.getInternalPic()));
			borderlessCell(invoiceTable, par);

			par = new Paragraph(new StandardText(soItem.getArticle().getArticleName()));
			borderlessCell(invoiceTable, par);

			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(soItem.getOrderedQty())));
			borderlessCell(invoiceTable, par);
			
			BigDecimal sppuTTC = soItem.getSalesPricePU()==null?BigDecimal.ZERO:soItem.getSalesPricePU();
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(sppuTTC)));
			borderlessCell(invoiceTable, par);

			BigDecimal vatRate = soItem.getVat()==null?BigDecimal.ZERO:VAT.getRawRate(soItem.getVat().getRate());
			BigDecimal sppuHT = sppuTTC.divide(BigDecimal.ONE.add(vatRate), 8, RoundingMode.HALF_EVEN);
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(sppuHT)));
			borderlessCell(invoiceTable, par);

			BigDecimal totalPriceHT = sppuHT.multiply(soItem.getOrderedQty());
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalPriceHT)));
			borderlessCell(invoiceTable, par);
			
			BigDecimal discountRate = salesOrder.getDiscountRate()==null?BigDecimal.ZERO:VAT.getRawRate(salesOrder.getDiscountRate());
			BigDecimal discount = totalPriceHT.multiply(discountRate);
			if(BigDecimal.ZERO.compareTo(discountRate)!=0){
				par = new Paragraph(new StandardText(""));
				borderlessCell(invoiceTable, par);

				par = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_discount.title")));
				borderlessCell(invoiceTable, par);

				par = new RightParagraph(new StandardText(""));
				borderlessCell(invoiceTable, par);
				
				par = new RightParagraph(new StandardText(""));
				borderlessCell(invoiceTable, par);

				par = new RightParagraph(new StandardText(""));
				borderlessCell(invoiceTable, par);

				par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(discount.negate())));
				borderlessCell(invoiceTable, par);
			}
			BigDecimal totalPriceHTAfterDiscount = totalPriceHT.subtract(discount);
			BigDecimal vatAmount = totalPriceHTAfterDiscount.multiply(vatRate);
			if(BigDecimal.ZERO.compareTo(vatRate)!=0){
				par = new Paragraph(new StandardText(""));
				borderlessCell(invoiceTable, par);

				par = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_vat.title")));
				borderlessCell(invoiceTable, par);

				par = new RightParagraph(new StandardText(""));
				borderlessCell(invoiceTable, par);
				
				par = new RightParagraph(new StandardText(""));
				borderlessCell(invoiceTable, par);

				par = new RightParagraph(new StandardText(""));
				borderlessCell(invoiceTable, par);

				par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(vatAmount)));
				borderlessCell(invoiceTable, par);
			}

			totalAmountHTBeforeDiscount = totalAmountHTBeforeDiscount.add(totalPriceHT);
			totalAmountDiscount = totalAmountDiscount.add(discount);
			totalAmountHTAfterDiscount = totalAmountHTAfterDiscount.add(totalPriceHTAfterDiscount);
			totalAmountTax = totalAmountTax.add(vatAmount);
		}
	}


	
	public void closeReport(){
		Paragraph par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);

		if(BigDecimal.ZERO.compareTo(totalAmountDiscount)!=0) {
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalTTCBeforeDiscount.title")));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTBeforeDiscount)));
			borderlessCell(invoiceTable, par);

			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalDiscount.title")));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountDiscount)));
			borderlessCell(invoiceTable, par);

			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalHTAfterDiscount.title")));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTAfterDiscount)));
			borderlessCell(invoiceTable, par);
		} else {
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalHT.title")));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new Paragraph(new StandardText(""));
			borderlessCell(invoiceTable, par);
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTAfterDiscount)));
			borderlessCell(invoiceTable, par);
		}
		
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalAmountTax.title")));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountTax)));
		borderlessCell(invoiceTable, par);

		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalTTC.title")));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new Paragraph(new StandardText(""));
		borderlessCell(invoiceTable, par);
		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTAfterDiscount.add(totalAmountTax))));
		borderlessCell(invoiceTable, par);

		try {
			document.add(invoiceTable);
			document.close();
			IOUtils.closeQuietly(fos);
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		}
		
	}

	private static float fontSize = 8;
	private static Font myBoldFont = FontFactory.getFont("Arial",
			fontSize, Font.BOLD);
	private static Font myFont = FontFactory.getFont("Arial", fontSize);

	static class StandardText extends Phrase {
		private static final long serialVersionUID = -5796192414147292471L;

		StandardText() {
			super();
			setFont(myFont);
		}

		StandardText(String text) {
			super(text);
			setFont(myFont);
		}
	}

	static class BoldText extends Phrase {
		private static final long serialVersionUID = -6569891897489003768L;

		BoldText() {
			super();
			setFont(myBoldFont);
		}

		BoldText(String text) {
			super(text);
			setFont(myBoldFont);
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
	private PdfPCell borderlessCell(PdfPTable table, Element... elements) {
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setBorder(Rectangle.NO_BORDER);
		for (Element element : elements) {
			pdfPCell.addElement(element);
		}
		table.addCell(pdfPCell);
		return pdfPCell;
	}

	private PdfPCell borderlessCell(PdfPTable table, Element element,
			int colspan, int rowspan) {
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setBorder(Rectangle.NO_BORDER);
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(rowspan);
		pdfPCell.addElement(element);
		table.addCell(pdfPCell);
		return pdfPCell;
	}
}
