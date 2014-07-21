package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import org.adorsys.adpharma.client.jpa.agency.Agency;
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

/**
 * We will try to use IText to produce the report template directly.
 * 
 * @author francis
 *
 */
public class CashDrawerReportPrintTemplatePDF  implements CashDrawerReportPrintTemplate {

	private CalendarFormat calendarFormat = new CalendarFormat();

	private Document document;
	private String pdfFileName;
	private FileOutputStream fos;
	private PdfPTable reportTable;

	private final Login reportPrinter;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private final Agency agency;
	private final Map<Long, Login> logins;
	private final Calendar startDate;
	private final Calendar endDate;
	private final Login login;

	static Font boldFont = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 12);

	public CashDrawerReportPrintTemplatePDF(CashDrawerReportPrinterData cashDrawerPrinterData, ResourceBundle resourceBundle,
			Locale locale,Login login) throws DocumentException {
		this.reportPrinter = cashDrawerPrinterData.getLogin();
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		this.agency = cashDrawerPrinterData.getAgency();
		this.logins = cashDrawerPrinterData.getLogins();
		this.startDate = cashDrawerPrinterData.getStartDate();
		this.endDate = cashDrawerPrinterData.getEndDate();
		this.login =  login ;

		pdfFileName = "caisse du "+DateHelper.format(new Date(), "dd-MM-yyyy") + ".pdf";
		document = new Document(PageSize.A4.rotate(),5,5,5,5);
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
		document.open();
		printReportHeader();
		fillTableHaeder();
	}

	public void addItems(List<CashDrawer> cashDrawers) {
		BigDecimal simulationRate =BigDecimal.ONE ;
		BigDecimal drugVoucherT = BigDecimal.ZERO ;
		BigDecimal cashOutT = BigDecimal.ZERO ;
		BigDecimal cashInT = BigDecimal.ZERO ;
		if(login.getSimulationRate()!=null && login.getSimulationRate().compareTo(BigDecimal.ZERO)!= 0)
			simulationRate = login.getSimulationRate().divide(BigDecimal.valueOf(100));

		for (CashDrawer cashDrawer : cashDrawers) {

			BigDecimal initialAmount = cashDrawer.getInitialAmount() !=null ? cashDrawer.getInitialAmount() : BigDecimal.ZERO;
			BigDecimal drugVoucher = cashDrawer.getTotalDrugVoucher() !=null ? cashDrawer.getTotalDrugVoucher() : BigDecimal.ZERO;
			String closindDate = cashDrawer.getClosingDate() ==null ?"":DateHelper.format(cashDrawer.getClosingDate().getTime(),"dd-MM-yyyy HH:mm");

			drugVoucherT=drugVoucherT.add(drugVoucher.multiply(simulationRate));
			cashOutT =cashOutT.add(cashDrawer.getTotalCashOut().multiply(simulationRate));
			cashInT =cashInT.add(cashDrawer.getTotalCash().multiply(simulationRate));
			newTableRow(cashDrawer.getCashDrawerNumber(),
					DateHelper.format(cashDrawer.getOpeningDate().getTime(),"dd-MM-yyyy HH:mm"),
					closindDate,
					cashDrawer.getCashier().getLoginName(), 
					drugVoucher.multiply(simulationRate),
					initialAmount.multiply(simulationRate),
					cashDrawer.getTotalClientVoucher().multiply(simulationRate),
					cashDrawer.getTotalCashOut().multiply(simulationRate),
					cashDrawer.getTotalCash().multiply(simulationRate));

		}
		newTableRow("",
				"",
				"",
				"TOTAUX : ", 
				drugVoucherT,
				null,
				null,
				cashOutT,
				cashInT);
	}

	private void newTableRow(String cashDrawerNumber, 
			String opendDate,
			String closedDate,
			String cashierLoginName,
			BigDecimal drugVoucher,
			BigDecimal fond,
			BigDecimal totalClientVoucher,
			BigDecimal cashOut,
			BigDecimal totalCash) {
		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(cashDrawerNumber));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(opendDate));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(closedDate));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(cashierLoginName));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(drugVoucher))));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(fond))));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalClientVoucher))));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(cashOut))));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalCash))));
		reportTable.addCell(pdfPCell);

		//		pdfPCell = new PdfPCell();
		//		pdfPCell.addElement(new RightParagraph(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(solde))));
		//		reportTable.addCell(pdfPCell);
	}

	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{.11f,.11f,.11f,.11f,.11f,.11f,.11f,.11f,.12f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_cashDrawerNumber.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Date Ouverture"));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Date Fermeture"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_cashier.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Bon Med "));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Fond "));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_totalClientVoucher.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText("Decaissement"));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_totalCash.title")));
		reportTable.addCell(pdfPCell);

		//		pdfPCell = new PdfPCell();
		//		pdfPCell.addElement(new StandardText("Encaissement "));
		//		reportTable.addCell(pdfPCell);

	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText(resourceBundle.getString("CashDrawerReportPrintTemplate_header.title")));
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		paragraph = new Paragraph(new BoldText(agency.getName()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText("Tel: " + agency.getPhone()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText(agency.getStreet()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_agent.title") 
				+ " " + reportPrinter.getFullName()));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText(calendarFormat.format(startDate, "dd-MM-yyyy HH:mm", locale) +
				" - " + calendarFormat.format(endDate, "dd-MM-yyyy HH:mm", locale)));
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
}
