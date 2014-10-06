package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;

public class ReceiptPrinterDataTest {
	private final PDFont font = PDType1Font.HELVETICA;
	private final PDFont boldFont = PDType1Font.HELVETICA_BOLD;

	@Test
	public void test() throws IOException {
		PDDocument pdDocument = new PDDocument();
		float docHeight = 600;
		float docWidth = 302;
		PDPage sourcePage = new PDPage(new PDRectangle(docWidth, docHeight));
		pdDocument.addPage(sourcePage);
		PDPageContentStream pageContentStream = new PDPageContentStream(pdDocument, sourcePage );
		pageContentStream.setFont(font, 12 );
		pageContentStream.beginText();
		pageContentStream.moveTextPositionByAmount(100, 500);
		pageContentStream.drawString("Position 100 500");
		pageContentStream.endText();
		pageContentStream.beginText();
		pageContentStream.moveTextPositionByAmount(25, 400);
		pageContentStream.drawString("Position 25 400");
		pageContentStream.endText();
		pageContentStream.beginText();
		pageContentStream.moveTextPositionByAmount(150, 400);
		pageContentStream.drawString("Position 150 400");
		pageContentStream.endText();
		pageContentStream.close();
		File f = new File("sample.pdf");
		try {
			pdDocument.save(f);
		} catch (COSVisitorException e) {
			throw new IOException(e);
		}
		Desktop.getDesktop().open(f);
		pdDocument.close();
	}

}
