package org.adorsys.adpharma.client.jpa.delivery;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.exceptions.IllegalStateException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class DeliveryReportPrintTemplatePDF implements DeliveryReportPrintTemplate {

	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;

	private PdfPTable deliveryTable = null;

	private final Login stockAgent;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private final Delivery delivery;

	public DeliveryReportPrintTemplatePDF(
			DeliveryReportPrinterData invoicePrinterData, 
			ResourceBundle resourceBundle,
			Locale locale) {
		this.delivery = invoicePrinterData.getDelivery();
		this.stockAgent = invoicePrinterData.getLogin();
		this.resourceBundle = resourceBundle;
		this.locale = locale;

		printReportHeader();
		fillTableHaeder();
	}

	int artNamelenght = 68;
	public void addItems(List<DeliveryItem> deliveryItems) {

		for (DeliveryItem deliveryItem : deliveryItems) {
			String articleName = deliveryItem.getArticleName();
			if(articleName.length()>artNamelenght) articleName = StringUtils.substring(articleName, 0, artNamelenght);

			newTableRow(deliveryItem.getInternalPic(), 
					articleName, 
					deliveryItem.getStockQuantity(), 
					deliveryItem.getPurchasePricePU(),
					deliveryItem.getQtyOrdered(),
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

		Paragraph par = new Paragraph(new StandardText(internalPic));
		borderlessCell(deliveryTable, par);

		par = new Paragraph(new StandardText(articleName));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(stockQuantity)));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(purchasePricePU)));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(qtyOrdered)));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(salesPricePU)));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalPurchasePrice)));
		borderlessCell(deliveryTable, par);

	}

	private void fillTableHaeder() {
		deliveryTable = new PdfPTable(new float[] { .2f, .38f, .06f,.1f,.06f,.1f,.1f });
		deliveryTable.setWidthPercentage(100);

		Paragraph par = new Paragraph(new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_internalPic.title")));
		borderlessCell(deliveryTable, par);

		par = new CenterParagraph(new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_articleName.title")));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_stockQuantity.title")));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_purchasePricePU.title")));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_qtyOrdered.title")));
		borderlessCell(deliveryTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_salesPricePU.title")));
		borderlessCell(deliveryTable, par);
		
		par = new RightParagraph(new BoldText(resourceBundle.getString("DeliveryReportPrintTemplate_totalPurchasePrice.title")));
		borderlessCell(deliveryTable, par);
	}

	private void printReportHeader() {
		document = new Document(PageSize.A4, 50,50,50,50);
		String deliveryNumber = delivery.getDeliveryNumber();
		try {
			fos = new FileOutputStream(deliveryNumber+".pdf");
			PdfWriter.getInstance(document, fos);
			document.open();
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
		
		PdfPTable rt = new PdfPTable(2);
		rt.setWidthPercentage(100);

		Paragraph documentName = new CenterParagraph(new BoldText(
				resourceBundle.getString("DeliveryReportPrintTemplate_header.title")+deliveryNumber));
		borderlessCell(rt, documentName, 2, 1);

		borderlessCell(rt, new LineSeparator(), 2, 1);

		Paragraph paragraph = new Paragraph(new BoldText(delivery.getReceivingAgency().getName()));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText("Tel: " + delivery.getReceivingAgency().getPhone()));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_supplier.title") 
				+ " " + delivery.getSupplier().getName()));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_agent.title") 
				+ " " + stockAgent.getFullName()));
		borderlessCell(rt, paragraph, 1, 1);

		paragraph = new Paragraph(new StandardText(
				resourceBundle.getString("DeliveryReportPrintTemplate_deliveryDate.title")
				+ " " + calendarFormat.format(delivery.getDeliveryDate(), "dd-MM-yyyy HH:mm", locale)));
		borderlessCell(rt, paragraph, 1, 1);

		borderlessCell(rt, new CenterParagraph(""));

		try {
			document.add(rt);
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		}

	}
	
	public void closeReport(){
		try {
			document.add(deliveryTable);
			document.close();
			IOUtils.closeQuietly(fos);
		} catch (DocumentException e) {
			throw new IllegalStateException(e);
		}
		
	}

	private static float fontSize = 8;
	private static Font myBoldFont = FontFactory.getFont("Arial",
			fontSize, Font.BOLD);
	private static Font myFont = FontFactory.getFont("Arial", fontSize);

	static class StandardText extends Phrase {
		private static final long serialVersionUID = -5796192414147292471L;

		StandardText() {
			super();
			setFont(myFont);
		}

		StandardText(String text) {
			super(text);
			setFont(myFont);
		}
	}

	static class BoldText extends Phrase {
		private static final long serialVersionUID = -6569891897489003768L;

		BoldText() {
			super();
			setFont(myBoldFont);
		}

		BoldText(String text) {
			super(text);
			setFont(myBoldFont);
		}
	}

	static class RightParagraph extends Paragraph {
		private static final long serialVersionUID = 986392503142787342L;

		public RightParagraph(Phrase phrase) {
			super(phrase);
			setAlignment(Element.ALIGN_RIGHT);
		}

		public RightParagraph(String string) {
			this(new StandardText(string));
		}
	}

	static class CenterParagraph extends Paragraph {

		private static final long serialVersionUID = -5432125323541770319L;

		public CenterParagraph(Phrase phrase) {
			super(phrase);
			setAlignment(Element.ALIGN_CENTER);
		}

		public CenterParagraph(String string) {
			this(new StandardText(string));
		}
	}
	private PdfPCell borderlessCell(PdfPTable table, Element... elements) {
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setBorder(Rectangle.NO_BORDER);
		for (Element element : elements) {
			pdfPCell.addElement(element);
		}
		table.addCell(pdfPCell);
		return pdfPCell;
	}

	private PdfPCell borderlessCell(PdfPTable table, Element element,
			int colspan, int rowspan) {
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setBorder(Rectangle.NO_BORDER);
		pdfPCell.setColspan(colspan);
		pdfPCell.setRowspan(rowspan);
		pdfPCell.addElement(element);
		table.addCell(pdfPCell);
		return pdfPCell;
	}

}
