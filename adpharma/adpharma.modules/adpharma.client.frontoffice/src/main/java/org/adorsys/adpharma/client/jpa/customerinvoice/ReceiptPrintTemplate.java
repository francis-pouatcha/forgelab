package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.List;

import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;


public abstract class ReceiptPrintTemplate {

	public abstract void startPage();

	public abstract CustomerInvoicePrinterData nextInvoice();

	public abstract void closePayment();

	public abstract void printInvoiceHeader (
			CustomerInvoicePrinterData customerInvoicePrinterData);

	public abstract void addItems(List<CustomerInvoiceItem> resultList);

	public abstract Object getPage();

	public abstract ReceiptPrinterData getReceiptPrinterData();

	public abstract String getReceiptPrinterName();
}
