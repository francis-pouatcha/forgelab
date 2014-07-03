package org.adorsys.adpharma.client.jpa.article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.TextFields;
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

public class ArticleRepportTemplatePdf {
	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;
	private LoginAgency agency  ;
	private Login login ;
	private PdfPTable reportTable;
	private String pdfFileName;
	private String section;

	public ArticleRepportTemplatePdf(
			Login login,LoginAgency agency,String section
			) throws DocumentException {
		this.agency = agency;
		this.login = login;
		this.section = section;
		PropertyReader.copy(login.getAgency(), agency);
		pdfFileName = "article.pdf";

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

	static Font boldFont = FontFactory.getFont("Times-Roman", 8, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 8);

	public void addItems(List<Article> items) {
		int artNamelenght = 68 ;
		BigDecimal totalQty = BigDecimal.ZERO ;
		BigDecimal totalPrice = BigDecimal.ZERO ;

		for (Article item : items) {
			String articleName = item.getArticleName();
			totalQty = totalQty.add(item.getQtyInStock());
			if(articleName.length()>artNamelenght) articleName = StringUtils.substring(articleName, 0, artNamelenght);
            BigDecimal vat = item.getVat()!=null?item.getVat().getRate():BigDecimal.ZERO;
			newTableRow(item.getPic(), 
					articleName, 
					item.getQtyInStock(),
					item.getSppu(),
					item.getPppu(),
					vat
					
					);
		}
//		newTableRow("", 
//				"TOTAL :", 
//				totalQty,
//				totalPrice
//				);
	}

	private void newTableRow(String pic, 
			String articleName,
			BigDecimal stockQuantity,
			BigDecimal sppu,
			BigDecimal pppu,
			BigDecimal vat
			
			) {


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(pic));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(articleName));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(stockQuantity!=null?stockQuantity.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(pppu!=null?pppu.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(sppu!=null?sppu.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(vat+"")));
		reportTable.addCell(pdfPCell);
		

	}



	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{ .1f, .5f, .1f,.1f,.1f,.1f });
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("cip"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Libelle"));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Stock "));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("P.A"));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("P.V"));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("T.V.A"));
		reportTable.addCell(pdfPCell);
	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText("CATALOGUE DES PRODUITS"));
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

		paragraph = new Paragraph(new StandardText("RAYON : "+section));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText("Print By : "+login.getFullName()));
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

	public void closeReport() {
		// TODO Auto-generated method stub

	}
}
