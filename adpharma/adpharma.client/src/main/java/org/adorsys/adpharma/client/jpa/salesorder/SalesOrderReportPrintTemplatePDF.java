package org.adorsys.adpharma.client.jpa.salesorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
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


public class SalesOrderReportPrintTemplatePDF {

	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;
	private LoginAgency agency  ;
	private Login login ;
	private PdfPTable reportTable;
	private String pdfFileName;
	private PeriodicalDataSearchInput model;
	private String headerName = "";

	public SalesOrderReportPrintTemplatePDF(
			Login login,LoginAgency agency,PeriodicalDataSearchInput model,String headerName
			) throws DocumentException {
		this.agency = agency;
		this.login = login;
		this.model = model;
		this.headerName = headerName;
		PropertyReader.copy(login.getAgency(), agency);
		pdfFileName = "sales.pdf";

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

	public void addItems(List<SalesOrderItem> items) {
		int artNamelenght = 68 ;
		BigDecimal totalQty = BigDecimal.ZERO ;
		BigDecimal totalPrice = BigDecimal.ZERO ;
		BigDecimal totalvat = BigDecimal.ZERO ;

		for (SalesOrderItem item : items) {
			String articleName = item.getArticle().getArticleName();
			totalQty = totalQty.add(item.getDeliveredQty());
			totalPrice = totalPrice.add(item.getTotalSalePrice());
			BigDecimal vatValue = item.getVatValue();
			totalvat = totalvat.add(vatValue);
			if(articleName.length()>artNamelenght) articleName = StringUtils.substring(articleName, 0, artNamelenght);

			newTableRow(item.getInternalPic(), 
					articleName, 
					item.getArticle().getQtyInStock(),
					item.getDeliveredQty(),
					item.getTotalSalePrice(),
					vatValue
					);
		}
		newTableRow("", 
				"TOTAL :", 
				totalQty,
				null,
				totalPrice,
				totalvat
				);
		
		
		
		
		
	}

	private void newTableRow(String internalPic, 
			String articleName,
			BigDecimal stockQty,
			BigDecimal salesQty,
			BigDecimal salesPricePU,
			BigDecimal vat) {


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(internalPic));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(articleName));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(stockQty!=null?stockQty.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(salesQty!=null?salesQty.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(salesPricePU!=null?salesPricePU.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(vat!=null?vat.toBigInteger()+"":"0")));
		reportTable.addCell(pdfPCell);
	}



	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{ .10f, .41f, .12f, .12f,.11f,.11f });
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("cip"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Libelle"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("En Stock "));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Qte"));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("PVT"));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("T.V.A"));
		reportTable.addCell(pdfPCell);
	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText("RAPPORTS PERIODIQUE DES VENTES "+headerName));
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

		paragraph = new Paragraph(new StandardText("Periode Du  :"+org.adorsys.adpharma.client.utils.DateHelper.format(model.getBeginDate().getTime(), "EEE dd MMMMM yyyy")+" AU : "+
				org.adorsys.adpharma.client.utils.DateHelper.format(model.getEndDate().getTime(), "EEE dd MMMMM yyyy")));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText("Imprimer Par : "+login.getFullName()));
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
