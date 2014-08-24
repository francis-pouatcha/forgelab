package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
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


public class CustomerInvoiceTobePayedRepportPdf {
	
	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;
	private final Login agent;
	private final LoginAgency agency ;
	private String pdfFileName;
	private PdfPTable reportTable;
	private String clientName ;
	
	static Font boldFont = FontFactory.getFont("Times-Roman", 12, Font.BOLD,Color.RED);
	
	static Font font = FontFactory.getFont("Times-Roman", 8);

	
	public CustomerInvoiceTobePayedRepportPdf(
			Login agent, LoginAgency agency,String clientName
			) throws DocumentException {
		this.agency =agency ;
		this.agent = agent;
		this.clientName = clientName ;
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
		BigDecimal totalrest = BigDecimal.ZERO;
		BigDecimal totalamount = BigDecimal.ZERO;
		for (CustomerInvoice item : items) {
			totalrest = item.getInsurranceRestTopay()!=null?totalrest.add(item.getInsurranceRestTopay()):totalrest;
			BigDecimal insuranceRate = item.getInsurance().getCoverageRate().divide(BigDecimal.valueOf(100));
			BigDecimal insr = item.getNetToPay().multiply(insuranceRate);
			totalamount = totalamount.add(insr);
			newTableRow(item.getInvoiceNumber(),
					item.getSalesOrder().getSoNumber(),
					DateHelper.format(item.getCreationDate().getTime(), "dd-MM-yyyy HH:mm"),
					insr,
					insr.subtract(item.getInsurranceRestTopay()),
					item.getInsurranceRestTopay()
					);
		}
		newTableRow("", "Total", null,totalamount,totalamount.subtract(totalrest),totalrest);
	}

	private void newTableRow(String invNumber, 
			String soNumber,
			String date,
			BigDecimal monatnt,
			BigDecimal avance,
			BigDecimal rest) {


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(invNumber));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(soNumber));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(date)));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(monatnt!=null?monatnt.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(avance!=null?avance.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new Phrase(rest!=null?rest.toBigInteger()+"":"",boldFont)));
		reportTable.addCell(pdfPCell);
		
	}

	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{.15f,.15f,.25f,.15f,.15f,.15f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Num Fac"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Num Cmd"));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Date de Facturation"));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Montant"));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Avance"));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Reste"));
		reportTable.addCell(pdfPCell);
		

	}
	

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText("ETAT DES IMPAYES"));
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
		

		paragraph = new Paragraph(new StandardText("CLIENT : "+ (clientName+"").toUpperCase()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText("Etat a la date du   : "+org.adorsys.adpharma.client.utils.DateHelper.format(new Date(), "EEEE dd MMMMM yyyy")));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText("Imprimer par : "+agent.getFullName()));
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
