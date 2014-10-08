package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
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

public class PrescriptionBookReportPrintTemplatePDF {
	
	private Document document;
	private FileOutputStream fos;
	private LoginAgency agency  ;
	private Login login ;
	private PdfPTable reportTable;
	private String pdfFileName;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private PeriodicalPrescriptionBookDataSearchInput model;
	
	
	public PrescriptionBookReportPrintTemplatePDF(LoginAgency agency, Login login, ResourceBundle resourceBundle, Locale locale,
			PeriodicalPrescriptionBookDataSearchInput model) throws DocumentException {
		this.agency = agency;
		this.login = login;
		this.model = model;
		this.resourceBundle=resourceBundle;
		this.locale=locale;
		pdfFileName="Ordonnance.pdf";
		PropertyReader.copy(login.getAgency(), agency);
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
	
	static Font boldFont = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 10);
	
	
	
	public void addItems(List<PrescriptionBook> items) {
		if(!items.isEmpty()) {
			for(PrescriptionBook ord: items) {
				newTableRow(ord.getPrescriptionNumber(), 
						ord.getSalesOrder().getSoNumber(), 
						ord.getPrescriptionDate()!=null?DateHelper.format(ord.getPrescriptionDate().getTime(), DateHelper.DATE_FORMAT):"--", 
						ord.getPrescriber().getName(), 
						ord.getHospital().getName());
			}
		}
	}
	
	private void newTableRow(String ordNumber, 
			String salesNumber,
			String ordDate,
			String prescriptor,
			String hospital
			) {

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(ordNumber));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(salesNumber));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(ordDate));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(prescriptor));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(hospital));
		reportTable.addCell(pdfPCell);
	}
	
	
	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText(resourceBundle.getString("PrescriptionBook_repport_description.title")));
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

		paragraph = new Paragraph(new StandardText("Periode Du  :"+DateHelper.format(model.getBeginDate().getTime(), "EEE dd MMMMM yyyy")+" AU : "+
				DateHelper.format(model.getEndDate().getTime(), "EEE dd MMMMM yyyy")));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText("Imprimer Par : "+login.getFullName()));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);
	}
	
	private void fillTableHeader() throws DocumentException {
		reportTable = new PdfPTable(new float[]{ .12f, .12f, .16f, .18f,.18f });
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("No Ordonnance"));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("No Commande"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Date Prescription"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Prescripteur"));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Hopital"));
		reportTable.addCell(pdfPCell);
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
	
	public String getPdfFileName() {
		return pdfFileName;
	}
	
	
	public void resetDocument() throws DocumentException{
		document.open();
		if(reportTable!=null)
			reportTable.getRows().clear();
		printReportHeader();
		fillTableHeader();
	}
	

}
