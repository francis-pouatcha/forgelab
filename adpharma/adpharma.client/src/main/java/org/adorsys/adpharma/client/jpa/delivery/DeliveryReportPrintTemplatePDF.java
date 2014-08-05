package org.adorsys.adpharma.client.jpa.delivery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.login.Login;
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


public class DeliveryReportPrintTemplatePDF implements DeliveryReportPrintTemplate {

	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private final Delivery delivery;
	private Agency agency = new Agency() ;
	private Login login ;
	private PdfPTable reportTable;
	private String pdfFileName;


	public DeliveryReportPrintTemplatePDF(
			DeliveryReportPrinterData invoicePrinterData, 
			ResourceBundle resourceBundle,
			Locale locale) throws DocumentException {
		this.delivery = invoicePrinterData.getDelivery();
		this.login = invoicePrinterData.getLogin();
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		PropertyReader.copy(login.getAgency(), agency);
		pdfFileName = "delivery_"+delivery.getDeliveryNumber() + ".pdf";

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
	static Font font = FontFactory.getFont("latin", 7);
	
	public void addItems(List<DeliveryItem> deliveryItems) {
	int artNamelenght = 68 ;
			for (DeliveryItem deliveryItem : deliveryItems) {
				String articleName = deliveryItem.getArticleName();
				if(articleName.length()>artNamelenght) articleName = StringUtils.substring(articleName, 0, artNamelenght);
	
				newTableRow(deliveryItem.getInternalPic(), 
						articleName, 
						deliveryItem.getArticle().getQtyInStock(), 
						deliveryItem.getPurchasePricePU(),
						deliveryItem.getStockQuantity(),
						deliveryItem.getSalesPricePU(),
						deliveryItem.getTotalPurchasePrice());
			}
		}
	
	private void newTableRow(String internalPic, 
			String articleName,
			BigDecimal stockQuantity,
			BigDecimal purchasePricePU,
			BigDecimal qtyOrdered, 
			BigDecimal salesPricePU,
			BigDecimal totalPurchasePrice) {


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(14f);
		pdfPCell.addElement(new Phrase(internalPic,font));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(14f);
		pdfPCell.addElement(new Phrase(articleName,font));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(14f);
		pdfPCell.addElement(new RightParagraph(new Phrase(stockQuantity!=null?stockQuantity.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(14f);
		pdfPCell.addElement(new RightParagraph(new Phrase(purchasePricePU!=null?purchasePricePU.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(14f);
		pdfPCell.addElement(new RightParagraph(new Phrase(qtyOrdered!=null?qtyOrdered.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(14f);
		pdfPCell.addElement(new RightParagraph(new Phrase(salesPricePU!=null?salesPricePU.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(14f);
		pdfPCell.addElement(new RightParagraph(new Phrase(totalPurchasePrice!=null?totalPurchasePrice.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);
	}
	


	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{ .2f, .38f, .06f,.1f,.06f,.1f,.1f });
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(15f);
		pdfPCell.addElement(new Phrase(resourceBundle.getString("DeliveryReportPrintTemplate_internalPic.title"),boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase(resourceBundle.getString("DeliveryReportPrintTemplate_articleName.title"),boldFont));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase(resourceBundle.getString("DeliveryReportPrintTemplate_stockQuantity.title"),boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase(resourceBundle.getString("DeliveryReportPrintTemplate_purchasePricePU.title"),boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase(resourceBundle.getString("DeliveryReportPrintTemplate_qtyOrdered.title"),boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase(resourceBundle.getString("DeliveryReportPrintTemplate_salesPricePU.title"),boldFont));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new Phrase(resourceBundle.getString("DeliveryReportPrintTemplate_totalPurchasePrice.title"),boldFont));
		reportTable.addCell(pdfPCell);

	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_header.title")+" "+delivery.getDeliveryNumber()));
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
		fillTableHaeder();
	}

	@Override
	public void closeReport() {
		// TODO Auto-generated method stub
		
	}


}
