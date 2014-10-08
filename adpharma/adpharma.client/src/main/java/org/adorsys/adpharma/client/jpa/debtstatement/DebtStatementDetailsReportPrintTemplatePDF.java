package org.adorsys.adpharma.client.jpa.debtstatement;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementReportPrintTemplatePDF.BoldText;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementReportPrintTemplatePDF.RightParagraph;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatementReportPrintTemplatePDF.StandardText;
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
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.sun.accessibility.internal.resources.accessibility;

public class DebtStatementDetailsReportPrintTemplatePDF {
	
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
	static Font boldFont = FontFactory.getFont("Times-Roman", 8, Font.BOLD,Color.BLUE);
	static Font font = FontFactory.getFont("Times-Roman", 8);

	public DebtStatementDetailsReportPrintTemplatePDF(DebtStatement debtStatement, ResourceBundle resourceBundle,
			Locale locale,Login login) throws DocumentException {
		this.reportPrinter = login;
		this.debtStatement = debtStatement ;
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		this.agency = debtStatement.getAgency();


		pdfFileName = "DebtStatement" + ".pdf";
		document = new Document(PageSize.A4.rotate());
		document.setMargins(5f, 5f, 5f, 0);

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
	
	
	
	// Get articles from an invoice
	public List<String> getArticlesFromInvoice(CustomerInvoice invoice) {
		List<CustomerInvoiceItem> invoiceItems = invoice.getInvoiceItems();
		List<String> resultList= new ArrayList<String>();
		for(CustomerInvoiceItem item: invoiceItems) {
			System.out.println("Invoice Item: "+item.toString());
			String internalPic = item.getInternalPic(); 
			String articleName = item.getArticle().getArticleName();
			  BigInteger qty = item.getPurchasedQty().toBigInteger();
			String product = new StringBuilder()
							        .append(internalPic)
							        .append(" - ")
							        .append(articleName)
							        .append(" - ")
							        .append(qty+"")
							        .toString();
			resultList.add(product);
		}
		return resultList;
	}
	
	public String formatArticles(List<String> liste) {
		StringBuilder builder = new StringBuilder();
		int i=0;
		for(String s: liste) {
			i++;
			builder.append(i+". ").append(s).append(System.lineSeparator());
		}
		return builder.toString();
	}

	public void addItems(List<CustomerInvoice> invoices) {
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal instotal = BigDecimal.ZERO;
		BigDecimal ticketMt = BigDecimal.ZERO;
		for (CustomerInvoice inoice : invoices) {
			String societe = null ;
			BigDecimal inssurancePart = inoice.getInsurance().getCoverageRate().multiply(inoice.getNetToPay()).divide(BigDecimal.valueOf(100));
			List<String> articlesFromInvoice = getArticlesFromInvoice(inoice);
			String articles = formatArticles(articlesFromInvoice);
			if(inoice.getCustomer()!=null)
				newTableRow(inoice.getInvoiceNumber(),
						articles,
//						inoice.getCustomer().getSociete()+"",
//						inoice.getInsurance().getCustomer().getSerialNumber(),
//						inoice.getPatientMatricle(),
						inoice.getInsurance().getCustomer().getFullName(),
						DateHelper.format(inoice.getCreationDate().getTime(),"dd-MM-yyyy HH:mm"),
						inoice.getNetToPay(),
						inoice.getNetToPay().subtract(inssurancePart),
						inssurancePart,
						inoice.getInsurance().getCoverageRate() );

			total = total.add(inoice.getNetToPay());
			instotal =	instotal.add(inoice.getInsurranceRestTopay());
			ticketMt = ticketMt.add(inoice.getNetToPay().subtract(inoice.getInsurranceRestTopay()));
		}
		newTableRow(null,null, null, "TOTAUX :",total,ticketMt, instotal, null);
	}

	private void newTableRow(String invoiceNumber, 
			String product,
			String client,
			String date,
			BigDecimal totalAmount,
			BigDecimal ticketm,
			BigDecimal amount, 
			BigDecimal rate
			) {

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(invoiceNumber));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(product));
		reportTable.addCell(product);


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
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(ticketm))));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(amount))));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(rate)+"%")));
		reportTable.addCell(pdfPCell);

	}

	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{.1f,.40f,.25f,.14f,.07f,.07f,.07f,.07f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_invoice_description.title")));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Produits(CIP - DESIGNATION - QTE CMDEE)"));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_customer_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_date_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("M.Brut"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Ticket M."));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_insurrance_amount_description.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("DebtStatement_Print_covert_rate_description.title")));
		reportTable.addCell(pdfPCell);


	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText(resourceBundle.getString("DebtStatement_Print_details_header_description.title")+" No :"+debtStatement.getStatementNumber()));
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
