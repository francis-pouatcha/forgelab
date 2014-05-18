package org.adorsys.adpharma.client.jpa.customervoucher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.login.Login;
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
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;

public class CustomerVoucherPrintTemplatePdf {
	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private String pdfFileName;
	private FileOutputStream fos;
	private PdfPTable reportTable;

	private final Login reportPrinter;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private final  CustomerVoucherAgency agency;
	private final String salesNumber ;
	private final CustomerVoucher customerVoucher ;	
	static Font boldFont = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 10);

	public CustomerVoucherPrintTemplatePdf(CustomerVoucher customerVoucher, ResourceBundle resourceBundle,
			Locale locale,Login login, String SalesNumber) throws DocumentException {
		this.reportPrinter = login;
		this.customerVoucher = customerVoucher ;
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		this.agency = customerVoucher.getAgency();
		this.salesNumber = SalesNumber ;


		pdfFileName = "CustomerVoucher" + ".pdf";
		document = new Document(PageSize.A6);

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

	public void addItems() {
		newTableRow("Num :", customerVoucher.getVoucherNumber());
		newTableRow(resourceBundle.getString("CustomerVoucher_customer_description.title"), customerVoucher.getCustomer().getFullName());
		newTableRow("Sales  :", salesNumber);
		newTableRow(resourceBundle.getString("CustomerVoucher_amount_description.title"), customerVoucher.getAmount()+"");
		newTableRow(resourceBundle.getString("CustomerVoucher_amountUsed_description.title"), customerVoucher.getAmountUsed()+"");
		newTableRow(resourceBundle.getString("CustomerVoucher_restAmount_description.title"), customerVoucher.getRestAmount()+"");
		newTableRow(resourceBundle.getString("CustomerVoucher_canceled_description.title"), customerVoucher.getCanceled()+"");
	}
	
	private void newTableRow(String title, 
			String value
			) {

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(title));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(value));
		reportTable.addCell(pdfPCell);



	}

	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{.5f,.5f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);
	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText(resourceBundle.getString("CustomerVoucher_description.title")));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);
		document.add(new LineSeparator());

		paragraph = new Paragraph(new BoldText(agency.getName()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText("Tel: " + agency.getPhone()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText("Agent :" 
				+ " " + reportPrinter.getFullName()));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText(calendarFormat.format(Calendar.getInstance(), "dd-MM-yyyy HH:mm", locale) ));
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
