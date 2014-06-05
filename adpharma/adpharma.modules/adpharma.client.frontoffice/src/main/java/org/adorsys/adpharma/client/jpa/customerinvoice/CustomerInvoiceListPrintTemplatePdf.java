package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.jboss.weld.exceptions.IllegalStateException;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;


public class CustomerInvoiceListPrintTemplatePdf {
	
	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;
	private final Login agent;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private final Agency agency ;
	private String pdfFileName;
	private PdfPTable reportTable;
	
	static Font boldFont = FontFactory.getFont("Times-Roman", 8, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 8);

	
	public CustomerInvoiceListPrintTemplatePdf(
			Login agent, Agency agency,
			ResourceBundle resourceBundle,
			Locale locale) throws DocumentException {
		this.agency =agency ;
		this.agent = agent;
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		pdfFileName = "invoices" + ".pdf";

		document = new Document(PageSize.A4,5,5,5,5);
		File file = new File(pdfFileName);
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
		try {
			PdfWriter.getInstance(document, fos);
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		}
		resetDocument();

	}

	
	public void addItems(List<CustomerInvoice> items) {
		BigDecimal totalnet = BigDecimal.ZERO;
		BigDecimal totalrest = BigDecimal.ZERO;
		BigDecimal totaldiscount = BigDecimal.ZERO;
		BigDecimal totalAdvence = BigDecimal.ZERO;
		for (CustomerInvoice item : items) {
			totalnet = item.getNetToPay()!=null?totalnet.add(item.getNetToPay()):totalnet;
			totalrest = item.getTotalRestToPay()!=null?totalrest.add(item.getTotalRestToPay()):totalrest;
			totaldiscount = item.getAmountDiscount()!=null?totaldiscount.add(item.getAmountDiscount()):totaldiscount;
			totalAdvence = item.getAdvancePayment()!=null?totalAdvence.add(item.getAdvancePayment()):totalAdvence;
			newTableRow(item.getInvoiceNumber(),
					DateHelper.format(item.getCreationDate().getTime(), "dd-MM-yyyy HH:mm"),
					item.getCreatingUser().getLoginName(),
					item.getNetToPay(),
					item.getAmountDiscount(),
					item.getAdvancePayment(),
					item.getTotalRestToPay()
					);
		}
		newTableRow("", "Total", null, totalnet, totaldiscount, totalAdvence,totalrest);
	}

	private void newTableRow(String invNumber, 
			String date,
			String saller,
			BigDecimal net, 
			BigDecimal discount, 
			BigDecimal avance, 
			BigDecimal rest) {


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(invNumber));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(date));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(saller)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(net!=null?net.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(discount!=null?discount.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(avance!=null?avance.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(rest!=null?rest.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);
	}

	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{.15f,.17f,.18f,.12f,.12f,.12f,.12f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CustomerInvoice_invoiceNumber_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CustomerInvoice_creationDate_description.title")));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CustomerInvoice_creatingUser_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CustomerInvoice_netToPay_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CustomerInvoice_amountDiscount_description.title")));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CustomerInvoice_advancePayment_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CustomerInvoice_totalRestToPay_description.title")));
		reportTable.addCell(pdfPCell);

	}
	

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText(resourceBundle.getString("CustomerInvoicePrintTemplate_menuitem.title")+agency));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		paragraph = new Paragraph(new BoldText(agency!=null?agency.getName():"Toute les Agences"));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText(agency!=null?agency.getFax():"FAX :" ));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText(agency!=null?agency.getPhone():"Phone :"));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText("Date  :"+org.adorsys.adpharma.client.utils.DateHelper.format(new Date(), "EEEE dd MMMMM yyyy")));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText("Print By : "+agent.getFullName()));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);
	}



	static class StandardText extends Phrase{
		private static final long serialVersionUID = -5796192414147292471L;
		StandardText() {
			super();
			setFont(font);
		}
		StandardText(String text) {
			super(text);
			setFont(font);
		}
	}

	static class BoldText extends Phrase {
		private static final long serialVersionUID = -6569891897489003768L;
		BoldText() {
			super();
			setFont(boldFont);
		}
		BoldText(String text) {
			super(text);
			setFont(boldFont);
		}
	}

	static class RightParagraph extends Paragraph {
		private static final long serialVersionUID = 986392503142787342L;

		public RightParagraph(Phrase phrase) {
			super(phrase);
			setAlignment(Element.ALIGN_RIGHT);
		}

		public RightParagraph(String string) {
			this(new Phrase(string));
		}


	}

	public void closeDocument() {
		try {
			document.add(reportTable);
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		}
		document.close();
	}

	public String getFileName() {
		return pdfFileName;
	}

	public void resetDocument() throws DocumentException{
		document.open();
		if(reportTable!=null)
			reportTable.getRows().clear();
		printReportHeader();
		fillTableHaeder();
	}

}
