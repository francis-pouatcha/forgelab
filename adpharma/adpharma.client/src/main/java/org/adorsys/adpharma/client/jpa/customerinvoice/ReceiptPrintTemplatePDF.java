package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jboss.weld.exceptions.IllegalStateException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;

public class ReceiptPrintTemplatePDF extends ReceiptPrintTemplate {

private CalendarFormat calendarFormat = new CalendarFormat();


private static int articleNameMaxLength = 36;
private static float fontSize = 6;
private static boolean mergeCipAndDesignation = true;
private static Font myBoldFont = FontFactory.getFont("Times-Roman", fontSize, Font.BOLD);
private static Font myFont = FontFactory.getFont("Times-Roman", fontSize);

static final String separatorText = "------------------------";
private static float docWidth = 138f;
private static float docHeight = 7700f;
private static double width = docWidth;

private Document document;
private ByteArrayOutputStream bos;
private Rectangle pageSize = new Rectangle(docWidth, docHeight);

private final ReceiptPrinterData receiptPrinterData;
private final ResourceBundle resourceBundle;
private final Locale locale;

private static String receiptPrinterName = "receipt";
static {
File file = new File("appconfig.properties");
if(file.exists()){
Properties properties = new Properties();
try {
properties.load(new FileInputStream(file));
String paperFormat = properties.getProperty("receipt-printer-format");
int format = 58;// Default
if(StringUtils.isNotBlank(paperFormat)){
format = NumberUtils.toInt(paperFormat);
if(format == 80){
fontSize = 7;
docWidth = 190f;
width = docWidth;
articleNameMaxLength = 50;
}
}
String mergeCipAndDesignationProp = properties.getProperty("receipt-printer-mergeCipAndDesignation");
if(StringUtils.isNotBlank(mergeCipAndDesignationProp)){
mergeCipAndDesignation = BooleanUtils.toBoolean(mergeCipAndDesignationProp);
}	

String receiptPrinterNameProp = properties.getProperty("receipt-printer-name");
if(StringUtils.isNotBlank(receiptPrinterNameProp)){
receiptPrinterName = receiptPrinterNameProp;
}	
} catch (FileNotFoundException e) {
throw new IllegalStateException(e);
} catch (IOException e) {
throw new IllegalStateException(e);
}
}
}

public ReceiptPrintTemplatePDF(ReceiptPrinterData receiptPrinterData,
ResourceBundle resourceBundle, Locale locale) {
this.receiptPrinterData = receiptPrinterData;
this.resourceBundle = resourceBundle;
this.locale = locale;
}

public void startPage() {
document = new Document(pageSize);
document.setMargins(1, 1, 1, 1);
bos = new ByteArrayOutputStream();
try {
PdfWriter.getInstance(document, bos);
document.open();
printReceiptHeader();
} catch (DocumentException e) {
throw new IllegalStateException(e);
}
}

double designationRowWidth = 0;
boolean printOrder = false;

private void printReceiptHeader() throws DocumentException {

PdfPTable rt = new PdfPTable(1);
rt.setWidthPercentage(100);

Paragraph documentName = new CenterParagraph(new BoldText(
resourceBundle
.getString("ReceiptPrintTemplate_cashReceipt.title")
+ " "
+ receiptPrinterData.getPayment().getPaymentNumber()));

borderlessCell(rt, documentName);

borderlessCell(rt,new CenterParagraph(separatorText));

Paragraph paragraph = new Paragraph(new StandardText(receiptPrinterData
.getCompany().getDisplayName()));
borderlessCell(rt, paragraph);

paragraph = new Paragraph(new BoldText(receiptPrinterData.getCompany()
.getSiteManager()));
borderlessCell(rt, paragraph);

paragraph = new Paragraph(new StandardText(receiptPrinterData
.getAgency().getStreet()));
borderlessCell(rt, paragraph);

paragraph = new Paragraph(new StandardText("TEL: "
+ receiptPrinterData.getAgency().getPhone() + " FAX: "
+ receiptPrinterData.getAgency().getFax()));
borderlessCell(rt, paragraph);

paragraph = new Paragraph(new StandardText(
resourceBundle.getString("ReceiptPrintTemplate_email.title")
+ " " + receiptPrinterData.getCompany().getEmail()));
borderlessCell(rt, paragraph);

paragraph = new Paragraph(new StandardText(
resourceBundle
.getString("ReceiptPrintTemplate_registerNumber.title")
+ " "
+ receiptPrinterData.getCompany().getRegisterNumber()));
borderlessCell(rt, paragraph);

borderlessCell(rt, new CenterParagraph(separatorText));

paragraph = new Paragraph(
new StandardText(
resourceBundle
.getString("ReceiptPrintTemplate_receipt.title")
+ " "
+ receiptPrinterData.getPayment()
.getPaymentNumber()
+ resourceBundle
.getString("ReceiptPrintTemplate_receipt_of.title")
+ " "
+ calendarFormat.format(receiptPrinterData
.getPayment().getPaymentDate(),
"dd-MM-yyyy HH:mm", locale)));
borderlessCell(rt, paragraph);

paragraph = new Paragraph(new StandardText(
resourceBundle.getString("ReceiptPrintTemplate_cashier.title")
+ " " + receiptPrinterData.getCashier().getFullName()));
borderlessCell(rt, paragraph);

if (receiptPrinterData.getInvoiceData().size() > 1) {
printOrder = true;
}

document.add(rt);
}

private PdfPCell borderlessCell(PdfPTable table, Element... elements){
PdfPCell pdfPCell = new PdfPCell();
pdfPCell.setBorder(Rectangle.NO_BORDER);
for (Element element : elements) {
pdfPCell.addElement(element);
}
table.addCell(pdfPCell);
return pdfPCell;
}

private PdfPCell borderlessCell(PdfPTable table, Element element, int colspan, int rowspan){
PdfPCell pdfPCell = new PdfPCell();
pdfPCell.setBorder(Rectangle.NO_BORDER);
pdfPCell.setColspan(colspan);
pdfPCell.setRowspan(rowspan);
pdfPCell.addElement(element);
table.addCell(pdfPCell);
return pdfPCell;
}

PdfPTable invoiceTable = null;

@Override
public void printInvoiceHeader(CustomerInvoicePrinterData invoiceData) {
int invoiceTableColCount = 3;
try {
if (invoiceTable != null)
document.add(invoiceTable);

if(mergeCipAndDesignation){
invoiceTable = new PdfPTable(new float[] { .6f, .13f, .27f });
designationRowWidth = width * .6;
invoiceTableColCount = 3;
} else {
invoiceTable = new PdfPTable(new float[] {.17f, .47f, .06f, .2f });
designationRowWidth = width * .47;
invoiceTableColCount = 4;
}
invoiceTable.setWidthPercentage(100);

if (printOrder) {
borderlessCell(invoiceTable, new CenterParagraph(separatorText), invoiceTableColCount,1);

StandardText standardText = new StandardText(
resourceBundle
.getString("ReceiptPrintTemplate_salesOrder.title")
+ " "
+ invoiceData.getCustomerInvoice()
.getSalesOrder().getSoNumber());
borderlessCell(invoiceTable, new Paragraph(standardText), invoiceTableColCount,1);
}

StandardText standardText = new StandardText(
resourceBundle
.getString("ReceiptPrintTemplate_salesAgent.title")
+ " " + invoiceData.getLogin().getFullName());
borderlessCell(invoiceTable, new Paragraph(standardText), invoiceTableColCount,1);

Paragraph paragraph = new Paragraph(new StandardText(
resourceBundle
.getString("ReceiptPrintTemplate_customer.title")
+ " "
+ invoiceData.getCustomerInvoice().getCustomer()
.getFullName()));
borderlessCell(invoiceTable, paragraph, invoiceTableColCount,1);

borderlessCell(invoiceTable, new CenterParagraph(separatorText), invoiceTableColCount,1);


totalAmountInvoices = totalAmountInvoices.add(invoiceData
.getCustomerInvoice().getAmountAfterTax());
totalAmountDiscount = totalAmountDiscount.add(invoiceData
.getCustomerInvoice().getAmountDiscount());
totalAmountRestToPay = totalAmountRestToPay.add(invoiceData
.getCustomerInvoice().getTotalRestToPay());

if(mergeCipAndDesignation){
borderlessCell(invoiceTable, new Paragraph(
new BoldText(resourceBundle.getString("ReceiptPrintTemplate_cip.title") + "/"+resourceBundle.getString("ReceiptPrintTemplate_designation.title"))));
} else {
borderlessCell(invoiceTable, new Paragraph(new BoldText(resourceBundle.getString("ReceiptPrintTemplate_cip.title"))));
borderlessCell(invoiceTable, new Paragraph(new BoldText(resourceBundle.getString("ReceiptPrintTemplate_designation.title"))));
}
borderlessCell(invoiceTable, new RightParagraph(new BoldText(resourceBundle.getString("ReceiptPrintTemplate_quantity.title"))));

borderlessCell(invoiceTable, new RightParagraph(new BoldText(resourceBundle.getString("ReceiptPrintTemplate_totalPrice.title"))));
} catch (DocumentException d) {
throw new IllegalStateException(d);
}
}

public void addItems(List<CustomerInvoiceItem> invoiceItems) {
for (CustomerInvoiceItem customerInvoiceItem : invoiceItems) {

String articleName = customerInvoiceItem.getArticle()
.getArticleName();
if (StringUtils.isNotBlank(articleName)
&& articleName.length() > articleNameMaxLength) {
articleName = StringUtils.substring(articleName, 0, articleNameMaxLength);
}

if(mergeCipAndDesignation){
Paragraph cip = new Paragraph(new StandardText(customerInvoiceItem.getInternalPic()));
Paragraph artName = new Paragraph(new StandardText(articleName));	
borderlessCell(invoiceTable, cip,artName);
} else {
borderlessCell(invoiceTable, new StandardText(customerInvoiceItem
.getInternalPic()));
borderlessCell(invoiceTable,new StandardText(articleName));
}

borderlessCell(invoiceTable,new RightParagraph(new StandardText(
DefaultBigDecimalFormatCM.getinstance().format(
customerInvoiceItem.getPurchasedQty()))));

borderlessCell(invoiceTable,new RightParagraph(new StandardText(
DefaultBigDecimalFormatCM.getinstance().format(
customerInvoiceItem.getTotalSalesPrice()))));
}
}

BigDecimal totalAmountInvoices = BigDecimal.ZERO;
BigDecimal totalAmountDiscount = BigDecimal.ZERO;
BigDecimal totalAmountRestToPay = BigDecimal.ZERO;

public void closePayment() {
if (invoiceTable != null)
try {
document.add(invoiceTable);
} catch (DocumentException e) {
throw new IllegalStateException(e);
}

PdfPTable paymentPane = new PdfPTable(new float[] { .8f, .2f });
paymentPane.setWidthPercentage(100);

borderlessCell(paymentPane,new CenterParagraph(separatorText), 2, 1);

borderlessCell(paymentPane,new StandardText(resourceBundle
.getString("ReceiptPrintTemplate_paymentMode.title")
+ " "
+ resourceBundle.getString("PaymentMode_"
+ receiptPrinterData.getPayment().getPaymentMode()
.name() + "_description.title")), 2,1);

borderlessCell(paymentPane,new StandardText(resourceBundle
.getString("ReceiptPrintTemplate_totalAmount.title")));

borderlessCell(paymentPane,new RightParagraph(new StandardText(
DefaultBigDecimalFormatCM.getinstance().format(
totalAmountInvoices))));

borderlessCell(paymentPane,new StandardText(resourceBundle
.getString("ReceiptPrintTemplate_discount.title")));

borderlessCell(paymentPane,new RightParagraph(new StandardText(
DefaultBigDecimalFormatCM.getinstance().format(
totalAmountDiscount))));

borderlessCell(paymentPane,new StandardText(resourceBundle
.getString("ReceiptPrintTemplate_netToPay.title")));

borderlessCell(paymentPane,new RightParagraph(new StandardText(
DefaultBigDecimalFormatCM.getinstance().format(
totalAmountRestToPay))));

borderlessCell(paymentPane,new StandardText(resourceBundle
.getString("ReceiptPrintTemplate_amountReceived.title")));

borderlessCell(paymentPane,new RightParagraph(new StandardText(
DefaultBigDecimalFormatCM.getinstance().format(
receiptPrinterData.getPayment().getReceivedAmount()))));

borderlessCell(paymentPane,new StandardText(resourceBundle
.getString("ReceiptPrintTemplate_amountReimbursed.title")));

borderlessCell(paymentPane,new RightParagraph(new StandardText(
DefaultBigDecimalFormatCM.getinstance().format(
receiptPrinterData.getPayment().getDifference()))));
try {
document.add(paymentPane);

document.add(Chunk.NEWLINE);

PdfPTable salutationPane = new PdfPTable(1);
salutationPane.setWidthPercentage(100);

String invoiceMessage = receiptPrinterData.getAgency()
.getInvoiceMessage();
Paragraph paragraph = new Paragraph(new StandardText(invoiceMessage));
borderlessCell(salutationPane, paragraph);

document.add(salutationPane);
document.setPageCount(1);
document.close();
bos.close();
byte[] byteArray = bos.toByteArray();
bos = new ByteArrayOutputStream();
PdfReader pdfReader = new PdfReader(byteArray);
PdfStamper stamper = new PdfStamper(pdfReader, bos);

PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
TextMarginFinder finder = parser.processContent(1, new TextMarginFinder());

PdfDictionary page = pdfReader.getPageN(1);
page.put(PdfName.CROPBOX, new PdfArray(new float[]{pageSize.getLeft(), finder.getLly(), pageSize.getRight(), pageSize.getTop()}));
stamper.markUsed(page);
stamper.close();
pdfReader.close();

} catch (DocumentException | IOException d) {
throw new IllegalStateException(d);
}
}

int invoiceIndex = 0;

public CustomerInvoicePrinterData nextInvoice() {
if (receiptPrinterData.getInvoiceData().size() <= invoiceIndex)
return null;
return receiptPrinterData.getInvoiceData().get(invoiceIndex++);
}

public byte[] getPage() {
return bos.toByteArray();
}

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

@Override
public ReceiptPrinterData getReceiptPrinterData() {
return receiptPrinterData;
}

@Override
public String getReceiptPrinterName(){
return receiptPrinterName;
}
}