package org.adorsys.adpharma.client.jpa.salesorder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
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
import com.lowagie.text.Chunk;

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
	private final boolean isProformat;

	public SalesOrderPrintTemplate(SalesOrderPrinterData invoicePrinterData, ResourceBundle resourceBundle,
			Locale locale) {
		this.salesOrder = invoicePrinterData.getSalesOrder();
		this.company = invoicePrinterData.getCompany();
		this.agency = invoicePrinterData.getAgency();
		this.salesAgent = invoicePrinterData.getLogin();
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		this.isProformat = invoicePrinterData.isProformat();

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
		String invoiceHeader = resourceBundle.getString("SalesOrderPrintTemplate_invoice.title") ;
		if(isProformat)
			invoiceHeader = "FACTURE PRO FORMA";
		Paragraph documentName = new CenterParagraph(new BoldText(invoiceHeader));
		documentName.setSpacingAfter(10);
		borderlessCell(rt, documentName, 2, 1);

		borderlessCell(rt, new LineSeparator(), 2, 2);

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
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_customerName.title") + " " +
				salesOrder.getCustomer().getFullName()));
		borderlessCell(rt, paragraph, 2, 1);
		
		String patient=salesOrder.getPatientMatricle()!=null?salesOrder.getPatientMatricle():" ";
		String insurance=salesOrder.getInsurance()!=null?salesOrder.getInsurance().getInsurer().getFullName():" ";
		
		@SuppressWarnings("unused")
		String insuranceName = salesOrder.getInsurance().getInsurer().getFullName();
		
		if(insurance!=null) {
			
			paragraph = new Paragraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_patientMatricle.title") + " " +patient));
			borderlessCell(rt, paragraph, 2, 1);
		
			paragraph = new Paragraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_insurerName.title") + " " +insurance));
			borderlessCell(rt, paragraph, 2, 1);
		
		}
		
//		paragraph = new Paragraph(new StandardText( "Societe Client : " +"  "+salesOrder.getCustomer().getSociete()+" "));
//		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_salesAgent.title") 
				+ " " + salesAgent.getFullName()));
		paragraph.setSpacingAfter(15);
		borderlessCell(rt, paragraph, 1, 1);
		

		paragraph = new Paragraph(new StandardText( "Facture No  " +salesOrder.getSoNumber() +" Du "+ calendarFormat.format(salesOrder.getCreationDate(), "dd-MM-yyyy HH:mm", locale)));
		paragraph.setSpacingAfter(15);
		borderlessCell(rt, paragraph, 1, 1);
		

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
		borderCell(invoiceTable, par);

		par = new CenterParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_designation.title")));
		borderCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_qty.title")));
		borderCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_spputtc.title")));
		borderCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_sppuht.title")));
		borderCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("SalesOrderPrintTemplate_totalPriceHT.title")));
		borderCell(invoiceTable, par);
	}

	private BigDecimal totalAmountHTBeforeDiscount = BigDecimal.ZERO;
	private BigDecimal totalAmountHTAfterDiscount = BigDecimal.ZERO;
	private BigDecimal totalAmountDiscount = BigDecimal.ZERO;
	private BigDecimal totalAmountTax = BigDecimal.ZERO;

	public void addItems(List<SalesOrderItem> invoiceItems) {
		BigDecimal discountRate = salesOrder.getDiscountRate()==null?BigDecimal.ZERO:VAT.getRawRate(salesOrder.getDiscountRate());
		for (SalesOrderItem soItem : invoiceItems) {
			int colspan = 1;
			int rowspan = 1;
			if(BigDecimal.ZERO.compareTo(discountRate)!=0){
				rowspan+=1;
			}			
			BigDecimal vatRate = soItem.getVat()==null?BigDecimal.ZERO:VAT.getRawRate(soItem.getVat().getRate());
			if(BigDecimal.ZERO.compareTo(vatRate)!=0){
				rowspan+=1;
			}

			Paragraph par = new Paragraph(new StandardText(soItem.getInternalPic()));
			borderCell(invoiceTable,colspan,rowspan, par);

			List<Paragraph> pars = new ArrayList<Paragraph>();
			pars.add(new Paragraph(new StandardText(soItem.getArticle().getArticleName())));
			if(BigDecimal.ZERO.compareTo(discountRate)!=0)
				pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_discount.title"))));
			if(BigDecimal.ZERO.compareTo(vatRate)!=0)
				pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_vat.title"))));
			borderCell(invoiceTable, colspan,rowspan, pars.toArray(new Paragraph[pars.size()]));

			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(soItem.getOrderedQty())));
			borderCell(invoiceTable,colspan,rowspan, par);

			BigDecimal sppuTTC = soItem.getSalesPricePU()==null?BigDecimal.ZERO:soItem.getSalesPricePU();
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(sppuTTC)));
			borderCell(invoiceTable,colspan,rowspan, par);

			BigDecimal sppuHT = sppuTTC.divide(BigDecimal.ONE.add(vatRate), 8, RoundingMode.HALF_EVEN);
			par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(sppuHT)));
			borderCell(invoiceTable,colspan,rowspan, par);

			pars = new ArrayList<Paragraph>();
			BigDecimal totalPriceHT = sppuHT.multiply(soItem.getOrderedQty());
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalPriceHT))));
			BigDecimal discount = BigDecimal.ZERO;
			if(BigDecimal.ZERO.compareTo(discountRate)!=0){
				discount = totalPriceHT.multiply(discountRate);
				pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(discount.negate()))));
			}
			BigDecimal totalPriceHTAfterDiscount = totalPriceHT.subtract(discount);
			BigDecimal vatAmount = BigDecimal.ZERO;
			if(BigDecimal.ZERO.compareTo(vatRate)!=0){
				vatAmount = totalPriceHTAfterDiscount.multiply(vatRate);
				pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(vatAmount))));
			}
			borderCell(invoiceTable, colspan,rowspan, pars.toArray(new Paragraph[pars.size()]));

			totalAmountHTBeforeDiscount = totalAmountHTBeforeDiscount.add(totalPriceHT);
			totalAmountDiscount = totalAmountDiscount.add(discount);
			totalAmountHTAfterDiscount = totalAmountHTAfterDiscount.add(totalPriceHTAfterDiscount);
			totalAmountTax = totalAmountTax.add(vatAmount);
		}
	}



	public void closeReport(){
		Paragraph par = new Paragraph(new StandardText(""));
		borderCell(invoiceTable,6,1, par);

		if(BigDecimal.ZERO.compareTo(totalAmountDiscount)!=0) {
			int colspan = 1;
			int rowspan = 5;
			par = new Paragraph(new StandardText(""));
			borderCell(invoiceTable, colspan,rowspan,par);

			List<Paragraph> pars = new ArrayList<Paragraph>();
			pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalTTCBeforeDiscount.title"))));
			pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalDiscount.title"))));
			pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalHTAfterDiscount.title"))));
			pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalAmountTax.title"))));
			pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalTTC.title"))));
			borderCell(invoiceTable, colspan,rowspan, pars.toArray(new Paragraph[pars.size()]));

			par = new Paragraph(new StandardText(""));
			borderCell(invoiceTable, colspan,rowspan,par);
			par = new Paragraph(new StandardText(""));
			borderCell(invoiceTable, colspan,rowspan,par);
			par = new Paragraph(new StandardText(""));
			borderCell(invoiceTable, colspan,rowspan,par);

			pars = new ArrayList<Paragraph>();
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTBeforeDiscount))));
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountDiscount))));
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTAfterDiscount))));
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountTax))));
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTAfterDiscount.add(totalAmountTax)))));
			borderCell(invoiceTable, colspan,rowspan, pars.toArray(new Paragraph[pars.size()]));

		} else {
			int colspan = 1;
			int rowspan = 3;

			par = new Paragraph(new StandardText(""));
			borderCell(invoiceTable, colspan,rowspan,par);

			List<Paragraph> pars = new ArrayList<Paragraph>();
			pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalHT.title"))));
			pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalAmountTax.title"))));
			pars.add(new Paragraph(new StandardText(resourceBundle.getString("SalesOrderPrintTemplate_totalTTC.title"))));
			SalesOrderInsurance insurance = salesOrder.getInsurance();
			BigDecimal inssurancePart = null;
			if(insurance!=null&&insurance.getId()!=null){
				inssurancePart = salesOrder.getNetToPay().multiply(insurance.getCoverageRate().divide(BigDecimal.valueOf(100)));
				pars.add(new Paragraph(new StandardText("Taux de couverture ")));
				pars.add(new Paragraph(new StandardText("Part Client ")));
				pars.add(new Paragraph(new StandardText("Part Assurance ")));
			}
			borderCell(invoiceTable, colspan,rowspan, pars.toArray(new Paragraph[pars.size()]));

			par = new Paragraph(new StandardText(""));
			borderCell(invoiceTable, colspan,rowspan,par);
			par = new Paragraph(new StandardText(""));
			borderCell(invoiceTable, colspan,rowspan,par);
			par = new Paragraph(new StandardText(""));
			borderCell(invoiceTable, colspan,rowspan,par);

			pars = new ArrayList<Paragraph>();
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTAfterDiscount))));
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountTax))));
			pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmountHTAfterDiscount.add(totalAmountTax)))));
			if(inssurancePart!=null){
				pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(salesOrder.getInsurance().getCoverageRate())+"%")));
				pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(salesOrder.getNetToPay().subtract(inssurancePart)))));
				pars.add(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(inssurancePart))));
			}
			borderCell(invoiceTable, colspan,rowspan, pars.toArray(new Paragraph[pars.size()]));
		}

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

	private PdfPCell borderCell(PdfPTable table, Element... elements) {
		PdfPCell pdfPCell = new PdfPCell();
		//		pdfPCell.setBorder(Rectangle.NO_BORDER);
		for (Element element : elements) {
			pdfPCell.addElement(element);
		}
		table.addCell(pdfPCell);
		return pdfPCell;
	}

	private PdfPCell borderCell(PdfPTable table,
			int colspan, int rowspan, Element... elements) {
		PdfPCell pdfPCell = new PdfPCell();
		//		pdfPCell.setBorder(Rectangle.NO_BORDER);
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(rowspan);
		for (Element element : elements) {
			pdfPCell.addElement(element);
		}
		table.addCell(pdfPCell);
		return pdfPCell;
	}

}
