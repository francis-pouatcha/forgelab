package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.delivery.DeliveryReportPrinterData;
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

public class CustomerInvoiceListPrintTemplatePdf {
	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private FileOutputStream fos;

	private PdfPTable invoiceTable = null;

	private final Login agent;
	private final ResourceBundle resourceBundle;
	private final Locale locale;

	private String fileName ;
	public CustomerInvoiceListPrintTemplatePdf(
			Login agent,
			ResourceBundle resourceBundle,
			Locale locale) {
		this.agent = agent;
		this.resourceBundle = resourceBundle;
		this.locale = locale;

		printReportHeader();
		fillTableHaeder();
	}

	int artNamelenght = 68;
	public void addItems(List<CustomerInvoice> customerInvoices) {

		for (CustomerInvoice invoice : customerInvoices) {

			newTableRow(invoice.getInvoiceNumber(), 
					invoice.getCustomer().getFullName(),
					invoice.getNetToPay(), 
					invoice.getCreatingUser().getLoginName(), 
					invoice.getAdvancePayment(),
					invoice.getAmountDiscount(),
					invoice.getCashed());
		}
	}

	private void newTableRow(String invoivenumber, 
			String customer,
			BigDecimal netTopay,
			String createdBy,
			BigDecimal advencePay, 
			BigDecimal discount,
			Boolean cashed) {

		Paragraph par = new Paragraph(new StandardText(invoivenumber));
		borderlessCell(invoiceTable, par);

		par = new Paragraph(new StandardText(createdBy));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(netTopay)));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new StandardText(customer));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(advencePay)));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(discount)));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new StandardText(cashed+""));
		borderlessCell(invoiceTable, par);

	}

	private void fillTableHaeder() {
		invoiceTable = new PdfPTable(new float[] { .2f, .38f, .06f,.1f,.06f,.1f,.1f });
		invoiceTable.setWidthPercentage(100);

		Paragraph par = new Paragraph(new BoldText(resourceBundle.getString("CustomerInvoice_invoiceNumber_description.title")));
		borderlessCell(invoiceTable, par);

		par = new CenterParagraph(new BoldText(resourceBundle.getString("CustomerInvoice_creatingUser_description.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("CustomerInvoice_netToPay_description.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("CustomerInvoice_customer_description.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("CustomerInvoice_advancePayment_description.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("CustomerInvoice_amountDiscount_description.title")));
		borderlessCell(invoiceTable, par);

		par = new RightParagraph(new BoldText(resourceBundle.getString("CustomerInvoice_cashed_description.title")));
		borderlessCell(invoiceTable, par);
	}

	private void printReportHeader() {
		document = new Document(PageSize.A4, 50,50,50,50);
		fileName = "invoiceList.pdf";
		try {
			fos = new FileOutputStream(fileName);
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
				resourceBundle.getString("DeliveryReportPrintTemplate_header.title")));
		borderlessCell(rt, documentName, 2, 1);

		borderlessCell(rt, new LineSeparator(), 2, 1);

		Paragraph paragraph = new Paragraph(new BoldText("AgencyName"));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText("Tel: " ));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_supplier.title") 
				+ " " ));
		borderlessCell(rt, paragraph, 2, 1);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("DeliveryReportPrintTemplate_agent.title") 
				+ " "));
		borderlessCell(rt, paragraph, 1, 1);

		paragraph = new Paragraph(new StandardText(
				resourceBundle.getString("DeliveryReportPrintTemplate_deliveryDate.title")
				+ " " ));
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
			document.add(invoiceTable);
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


	public String getFileName() {
		return fileName;
	}

}
