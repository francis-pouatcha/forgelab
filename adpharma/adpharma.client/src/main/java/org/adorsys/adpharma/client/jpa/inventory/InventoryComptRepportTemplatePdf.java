package org.adorsys.adpharma.client.jpa.inventory;

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
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
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

public class InventoryComptRepportTemplatePdf {

	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;
	private final Inventory inventory;
	private LoginAgency agency = new LoginAgency() ;
	private Login login ;
	private PdfPTable reportTable;
	private String pdfFileName;
	private boolean isCountRepport ;

	public InventoryComptRepportTemplatePdf(
			Login login, 
			LoginAgency agency, 
			InventoryRepportData data) throws DocumentException {
		this.inventory = data.getInventory();
		this.login = login;
		this.agency = agency;
		this.isCountRepport = data.isCountRepport();
		PropertyReader.copy(login.getAgency(), agency);
		pdfFileName = "inventory_"+inventory.getInventoryNumber() + ".pdf";

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

	static Font boldFont = FontFactory.getFont("latin", 8, Font.BOLD);
	static Font font = FontFactory.getFont("latin", 8);

	public void addItems(List<InventoryItem> items) {
		int artNamelenght = 68 ;
		BigDecimal total = BigDecimal.ZERO;
		for (InventoryItem item : items) {
			String articleName = item.getArticle().getArticleName();
			if(articleName.length()>artNamelenght) articleName = StringUtils.substring(articleName, 0, artNamelenght);
			if(isCountRepport){
				newCountRepportTableRow(item.getInternalPic(), articleName, null, "");
			}else {
				newTableRow(item.getInternalPic(), articleName, item.getExpectedQty(), item.getAsseccedQty(), item.getGap(), item.getGapTotalSalePrice());
				total = total.add(item.getGapTotalSalePrice());
			}
		}
		if(!isCountRepport)
			newTableRow("", "Total :", null, null, null, total);

	}

	private void newTableRow(String internalPic, 
			String articleName,
			BigDecimal expectedQty,
			BigDecimal realQty,
			Long ecart,
			BigDecimal montant
			) {


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(internalPic));
		reportTable.addCell(pdfPCell);
		pdfPCell.setFixedHeight(4);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(articleName));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(expectedQty!=null?expectedQty.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(realQty!=null?realQty.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(ecart+"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(montant!=null?montant.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);


	}

	private void newCountRepportTableRow(String internalPic, 
			String articleName,
			BigDecimal realQty,
			String observation
			) {

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(internalPic));
		reportTable.addCell(pdfPCell);
		pdfPCell.setFixedHeight(4);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(articleName));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(realQty!=null?realQty.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(observation));
		reportTable.addCell(pdfPCell);


	}




	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{ .13f, .47f, .1f,.1f,.1f,.1f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("CIPM"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Designation"));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Stock M"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Stock R"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Ecart"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Montant"));
		reportTable.addCell(pdfPCell);


	}

	private void fillCountRepportTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{ .13f, .57f, .1f,.2f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("CIPM"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Designation"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Stock R"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("OBS"));
		reportTable.addCell(pdfPCell);


	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText("FICHE INVENTAIRE NUM: "+inventory.getInventoryNumber()));
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

		paragraph = new Paragraph(new StandardText("RAYON : "+inventory.getSection()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText("Date  :"+org.adorsys.adpharma.client.utils.DateHelper.format(new Date(), "EEEE dd MMMMM yyyy")));
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
		if(isCountRepport){
			fillCountRepportTableHaeder();
		}else {
			fillTableHaeder();
		}
	}

	public void closeReport() {
		// TODO Auto-generated method stub

	}

}
