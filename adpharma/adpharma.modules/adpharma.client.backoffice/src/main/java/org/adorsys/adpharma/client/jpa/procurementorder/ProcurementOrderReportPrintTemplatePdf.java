package org.adorsys.adpharma.client.jpa.procurementorder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
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

public class ProcurementOrderReportPrintTemplatePdf {
	private Document document;
	private String pdfFileName;
	private FileOutputStream fos;
	private PdfPTable reportTable;
	private ResourceBundle resourceBundle;

	private ProcurementOrderReportPrinterData data ;
	private Agency agency ;
	private Login login ;
	

	static Font boldFont = FontFactory.getFont("Times-Roman", 8, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 8);

	public ProcurementOrderReportPrintTemplatePdf(ProcurementOrderReportPrinterData data, Agency agency,Login login,ResourceBundle resourceBundle) throws DocumentException {
		this.agency = agency ;
		this.data = data ;
		this.login = login ;
		this.resourceBundle = resourceBundle ;
		pdfFileName = "procurementRepport" + ".pdf";

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

	public void addItems(List<ProcurementOrderItem> items) {
		BigDecimal total = BigDecimal.ZERO;
		for (ProcurementOrderItem item : items) {
			total = item.getTotalPurchasePrice()!=null?total.add(item.getTotalPurchasePrice()):total;
			newTableRow(item.getMainPic(),
					item.getArticle().getArticleName(),
					item.getQtyOrdered(),
					item.getStockQuantity(),
					item.getPurchasePricePU(),
					item.getTotalPurchasePrice()
					);
		}
		newTableRow("", "Total", null, null, null, total);
	}

	public void addItem(){
		addItems(data.getProcurementOrderItemSearchResult().getResultList());
	}
	private void newTableRow(String cip, 
			String articleName,
			BigDecimal saleQty, 
			BigDecimal stockQty, 
			BigDecimal pppu, 
			BigDecimal ttppu) {
		

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(cip));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(articleName));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(saleQty!=null?saleQty.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(stockQty!=null?stockQty.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(pppu!=null?pppu.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new RightParagraph(new StandardText(ttppu!=null?ttppu.toBigInteger()+"":"")));
		reportTable.addCell(pdfPCell);
	}

	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{.10f,.48f,.10f,.12f,.10f,.10f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("ProcurementOrderPrintTemplate_pic.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("ProcurementOrderPrintTemplate_articleName.title")));
		reportTable.addCell(pdfPCell);


		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("ProcurementOrderPrintTemplate_qtyOrdered.title")));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("ProcurementOrderPrintTemplate_stockQuantity.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("ProcurementOrderPrintTemplate_purchasePricePU.title")));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.addElement(new StandardText(resourceBundle.getString("ProcurementOrderPrintTemplate_totalPurchasePrice.title")));
		reportTable.addCell(pdfPCell);

	}

	private void printReportHeader() throws DocumentException {

		Paragraph paragraph = new Paragraph(new BoldText(resourceBundle.getString("ProcurementOrderPrintTemplate_header.title")+data.getProcurementOrder().getProcurementOrderNumber()));
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
}
