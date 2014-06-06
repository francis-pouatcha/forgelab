package org.adorsys.adpharma.client.jpa.debtstatement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;
import org.jboss.weld.exceptions.IllegalStateException;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;

/**
 * We will try to use IText to produce the report template directly.
 * 
 * @author francis
 *
 */
public class DebtStatementReportPrintTemplatePDF {

	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private String pdfFileName;
	private FileOutputStream fos;
	private PdfPTable reportTable;

	private final Login reportPrinter;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private final  DebtStatementAgency agency;
	private final DebtStatement debtStatement ;	
	static Font boldFont = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 12);

	public DebtStatementReportPrintTemplatePDF(DebtStatement debtStatement, ResourceBundle resourceBundle,
			Locale locale,Login login) throws DocumentException {
		this.reportPrinter = login;
		this.debtStatement = debtStatement ;
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		this.agency = debtStatement.getAgency();


		pdfFileName = "DebtStatement" + ".pdf";
		document = new Document();
		document.setMargins(10f, 10f, 5f, 0);
		
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

	public void addItems(List<CustomerInvoice> invoices) {
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal instotal = BigDecimal.ZERO;
		for (CustomerInvoice inoice : invoices) {
			newTableRow(inoice.getInvoiceNumber(), 
					inoice.getInsurance().getCustomer().getFullName(),
					DateHelper.format(inoice.getCreationDate().getTime(),DateHelper.DATE_FORMAT),
					inoice.getNetToPay(),
					inoice.getInsurranceRestTopay(),
					inoice.getInsurance().getCoverageRate() );
		
			total = total.add(inoice.getNetToPay());
		instotal =	instotal.add(inoice.getInsurranceRestTopay());
		}
		newTableRow(null, null, "TOTAUX :",total, instotal, null);
	}

	private void newTableRow(String invoiceNumber, 
			String client,
			String date,
			BigDecimal totalAmount,
			BigDecimal amount, 
			BigDecimal rate
			) {

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(invoiceNumber));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(client));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(date));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalAmount))));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(amount))));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(rate)+"%")));
		reportTable.addCell(pdfPCell);

	}
	
	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{.11f,.45f,.15f,.14f,.14f,.08f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_invoice_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_customer_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_date_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_amount_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_insurrance_amount_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_covert_rate_description.title")));
		reportTable.addCell(pdfPCell);


	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText(resourceBundle.getString("DebtStatement_Print_header_description.title")));
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

		paragraph = new Paragraph(new StandardText("Asurrance : "+debtStatement.getInsurrance().getFullName()));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_agent.title") 
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
