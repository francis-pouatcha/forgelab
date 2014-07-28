package org.adorsys.adpharma.client.jpa.disbursement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Locale;

import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
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

public class DisbursementPrintTemplatePdf {
	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private String pdfFileName;
	private FileOutputStream fos;
	private PdfPTable reportTable;

	private final Login reportPrinter;
	private final Locale locale;
	private final Disbursement disbursement;
	private final  LoginAgency agency;
	private final CustomerVoucher customerVoucher ;	
	static Font boldFont = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 10);

	public DisbursementPrintTemplatePdf(CustomerVoucher customerVoucher, Disbursement disbursement,Login login,Locale locale) throws DocumentException {
		this.reportPrinter = login;
		this.customerVoucher = customerVoucher ;
		this.agency = login.getAgency();
		this.disbursement = disbursement ;
		this.locale = locale ;

		pdfFileName = "disbursement" + ".pdf";
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
		newTableRow("NUM :", "DC-"+disbursement.getId());
		newTableRow("CASSIER : ", disbursement.getCashier().getLoginName());
		newTableRow("RAISON  :", disbursement.getRaison());
		newTableRow("MODE  :", disbursement.getPaymentMode().toString());
		newTableRow("MONTANT  :", disbursement.getAmount()+"");
		if(customerVoucher!=null){
			newTableRow("NUM AVOIR  :", customerVoucher.getVoucherNumber());
			newTableRow("AVOIR INITIAL  :", customerVoucher.getAmount()+"");
			newTableRow("AVOIR UTILISE :", customerVoucher.getAmountUsed()+"");
			newTableRow("RESTE AVOIR :", customerVoucher.getRestAmount()+"");
		}
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

		Paragraph paragraph = new Paragraph(new BoldText("RECU DE DECCASISSEMENT"));
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
