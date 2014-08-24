package org.adorsys.adpharma.client.jpa.debtstatement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryReportPrinterData;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
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


public class DebtstatementCashReportPrintTemplatePDF  {

	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;
	private final DebtStatement debtStatement;
	private Agency agency = new Agency() ;
	private Login login ;
	private PdfPTable reportTable;
	private String pdfFileName;


	public DebtstatementCashReportPrintTemplatePDF(
			DebtStatement debtStatement, 
			Login login
			) throws DocumentException {
		this.debtStatement = debtStatement;
		this.login = login;
		PropertyReader.copy(login.getAgency(), agency);
		pdfFileName = "debtStatement_"+debtStatement.getStatementNumber() + ".pdf";

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

	static Font boldFont = FontFactory.getFont("latin", 10, Font.BOLD);
	static Font font = FontFactory.getFont("latin", 10);

	public void addItems(List<Payment> payments) {
	String fullName = debtStatement.getInsurrance().getFullName();
		BigDecimal totaAmount = BigDecimal.ZERO;
		for (Payment payment : payments) {
			if(BigDecimal.ZERO.compareTo(payment.getAmount())==0)
				continue ;
			newTableRow(payment.getPaymentNumber(), 
					DateHelper.format(payment.getPaymentDate().getTime(), "dd-MM-yyyy HH:mm"), 
					fullName, 
					payment.getAmount());
			totaAmount = totaAmount.add(payment.getAmount()) ;
			
		}
		newTableRow("", "", "TOTAL ", totaAmount);
	}

	private void newTableRow(String num, 
			String date,
			String payBy,
			BigDecimal amount
			) {


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(18f);
		pdfPCell.addElement(new Phrase(num,font));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(18f);
		pdfPCell.addElement(new Phrase(date,font));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(18f);
		pdfPCell.addElement(new Phrase(payBy,font));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(18f);
		pdfPCell.addElement(new RightParagraph(new Phrase(amount!=null?amount.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

	}



	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{ .15f,.2f, .48f, .17f });
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(18f);
		pdfPCell.addElement(new Phrase("Num paie",boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase("Date Paie",boldFont));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase("Payer Par",boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase("Montant",boldFont));
		reportTable.addCell(pdfPCell);

	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText("ETAT DES PAIEMENTS "+" "+debtStatement.getStatementNumber()));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		paragraph = new Paragraph(new BoldText(agency.getName()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText(agency.getFax() ));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText(agency.getPhone()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);
		
		paragraph = new Paragraph(new StandardText("Client : "+debtStatement.getInsurrance().getFullName()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText("Etat A la date du  :"+org.adorsys.adpharma.client.utils.DateHelper.format(new Date(), "EEEE dd MMMMM yyyy")));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText("Edite Par : "+login.getFullName()));
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
