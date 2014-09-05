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
	private boolean onlyRupture = false ;

	private ProcurementOrderReportPrinterData data ;
	private Agency agency ;
	private Login login ;


	static Font boldFont = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
	static Font font = FontFactory.getFont("Times-Roman", 9);

	public ProcurementOrderReportPrintTemplatePdf(ProcurementOrderReportPrinterData data, Agency agency,Login login,ResourceBundle resourceBundle,boolean onlyRupture) throws DocumentException {
		this.agency = agency ;
		this.data = data ;
		this.login = login ;
		this.resourceBundle = resourceBundle ;
		this.onlyRupture = onlyRupture ;
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
			if(onlyRupture){
				if (item.getQtyOrdered().compareTo(item.getAvailableQty())==0) 
					continue ;
			}
			total = item.getTotalPurchasePrice()!=null?total.add(item.getTotalPurchasePrice()):total;
			newTableRow(item.getMainPic(),
					item.getArticle().getArticleName(),
					item.getStockQuantity(),
					item.getQtyOrdered(),
					item.getAvailableQty(),
					item.getQtyOrdered().subtract(item.getAvailableQty()),
					item.getPurchasePricePU(),
					item.getTotalPurchasePrice()
					);
		}
		newTableRow("", "Total", null, null, null, null,null, total);
	}

	public void addItem(){
		addItems(data.getProcurementOrderItemSearchResult().getResultList());
	}
	private void newTableRow(String cip, 
			String articleName,
			BigDecimal stock,
			BigDecimal saleQty, 
			BigDecimal stockQty, 
			BigDecimal rest,
			BigDecimal pppu, 
			BigDecimal ttppu) {


		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase(cip,font));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase(articleName,font));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new RightParagraph(new Phrase(stock!=null?stock.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new RightParagraph(new Phrase(saleQty!=null?saleQty.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new RightParagraph(new Phrase(stockQty!=null?stockQty.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new RightParagraph(new Phrase(rest!=null?rest.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new RightParagraph(new Phrase(pppu!=null?pppu.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new RightParagraph(new Phrase(ttppu!=null?ttppu.toBigInteger()+"":"",font)));
		reportTable.addCell(pdfPCell);
	}

	private void fillTableHaeder() throws DocumentException {
		reportTable = new PdfPTable(new float[]{.11f,.34f,.09f,.09f,.09f,.09f,.09f,.10f});
		reportTable.setWidthPercentage(100);
		reportTable.setHeaderRows(1);

		PdfPCell pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase("CIP",boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase("DESIGNATION",boldFont));
		reportTable.addCell(pdfPCell);
		
		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase("STOCK",boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase("CMD",boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase("Dispo",boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase("Reste",boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase("P.A",boldFont));
		reportTable.addCell(pdfPCell);

		pdfPCell = new PdfPCell();
		pdfPCell.setFixedHeight(16);
		pdfPCell.addElement(new Phrase("Total",boldFont));
		reportTable.addCell(pdfPCell);

	}

	private void printReportHeader() throws DocumentException {
		String headerText = "RAPPORT COMMANDE FOURNISSEUR Num : " ;
		if(onlyRupture)
			headerText = "RAPPORT PRODUIT NON DISPO/RUPTURE COMMANDE FOURNISSEUR Num : ";
		Paragraph paragraph = new Paragraph(headerText+data.getProcurementOrder().getProcurementOrderNumber());
		paragraph.setAlignment(Element.ALIGN_CENTER);
		document.add(paragraph);

		paragraph = new Paragraph(new BoldText(agency.getName()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new Phrase(agency.getFax() ));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new Phrase(agency.getPhone()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new Phrase( data.getProcurementOrder().getSupplier().getName()));
		paragraph.setAlignment(Element.ALIGN_LEFT);
		document.add(paragraph);

		paragraph = new Paragraph(new Phrase("CMD DU : "+org.adorsys.adpharma.client.utils.DateHelper.format(data.getProcurementOrder().getCreatedDate().getTime(), "EEEE dd MMMMM yyyy HH:mm:ss")));
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		document.add(paragraph);

		//		paragraph= new Paragraph(new StandardText("Periode: "));
		//		paragraph.setAlignment(Element.ALIGN_LEFT);
		//		document.add(paragraph);
		document.add(Chunk.NEWLINE);

		document.add(new LineSeparator());

		paragraph = new Paragraph(new StandardText("Imprime Le : "+org.adorsys.adpharma.client.utils.DateHelper.format(new Date(), "EEEE dd MMMMM yyyy HH:mm:ss")+" par : "+login.getFullName()));
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
