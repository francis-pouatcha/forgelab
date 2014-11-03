package org.adorsys.adpharma.client.jpa.customerinvoice.print;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;

public interface ReceiptDataRow {

	public float getHeight();
	
	public float writeItemLine(PDPageContentStream contentStream, float posX) throws IOException;

}