package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jboss.weld.exceptions.IllegalStateException;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Rectangle;

public class ReceiptPrintProperties {

	private ReceiptPrintMode receiptPrintMode = ReceiptPrintMode.open;
	private int format = 72;// Default
	private int articleNameMaxLength = 80;
	private float fontSize = 14;
	private boolean mergeCipAndDesignation = true;
	private float docWidth = 430f;
	private float docHeight = 4700f;
	private double width = docWidth;
	private double height = docHeight;

	private Font myBoldFont = FontFactory.getFont("Arial", fontSize, Font.BOLD);
	private Font myFont = FontFactory.getFont("Arial", fontSize);


	private Rectangle pageSize = new Rectangle(docWidth, docHeight);

	private String receiptPrinterName = "receipt";

	private boolean debug = true;

	public ReceiptPrintMode getReceiptPrintMode() {
		return receiptPrintMode;
	}
	public void setReceiptPrintMode(ReceiptPrintMode receiptPrintMode) {
		this.receiptPrintMode = receiptPrintMode;
	}
	public int getFormat() {
		return format;
	}
	public void setFormat(int format) {
		this.format = format;
	}

	public int getArticleNameMaxLength() {
		return articleNameMaxLength;
	}
	public void setArticleNameMaxLength(int articleNameMaxLength) {
		this.articleNameMaxLength = articleNameMaxLength;
	}
	public float getFontSize() {
		return fontSize;
	}
	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
		myBoldFont = FontFactory.getFont("Arial", fontSize, Font.BOLD);
		myFont = FontFactory.getFont("Arial", fontSize);
	}
	public boolean isMergeCipAndDesignation() {
		return mergeCipAndDesignation;
	}
	public void setMergeCipAndDesignation(boolean mergeCipAndDesignation) {
		this.mergeCipAndDesignation = mergeCipAndDesignation;
	}
	public float getDocWidth() {
		return docWidth;
	}
	public void setDocWidth(float docWidth) {
		this.docWidth = docWidth;
		width = docWidth;
		pageSize = new Rectangle(docWidth, docHeight);
	}

	public void setDocHeight(float docHeight) {
		this.docHeight = docHeight;
		height = docHeight;
		pageSize = new Rectangle(docWidth, docHeight);
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public Font getMyBoldFont() {
		return myBoldFont;
	}
	public Font getMyFont() {
		return myFont;
	}
	public Rectangle getPageSize() {
		return pageSize;
	}
	public String getReceiptPrinterName() {
		return receiptPrinterName;
	}
	public void setReceiptPrinterName(String receiptPrinterName) {
		this.receiptPrinterName = receiptPrinterName;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public static ReceiptPrintProperties loadPrintProperties(){
		ReceiptPrintProperties r = new ReceiptPrintProperties();
		File file = new File("appconfig.properties");
		if(file.exists()){
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(file));
				String paperFormat = properties.getProperty("receipt-printer-format");
				if(StringUtils.isNotBlank(paperFormat)){
					r.setFormat(NumberUtils.toInt(paperFormat));
				}
				String docHeigthStr = properties.getProperty("receipt-doc-height");
				if(StringUtils.isNotBlank(docHeigthStr)){
					r.setDocHeight(NumberUtils.toFloat(docHeigthStr));
				}

				String fontSizeStr = properties.getProperty("receipt-font-size");
				if(StringUtils.isNotBlank(fontSizeStr)){
					r.setFontSize(NumberUtils.toInt(fontSizeStr));
				}
				String docWidthStr = properties.getProperty("receipt-doc-with");
				if(StringUtils.isNotBlank(docWidthStr)){
					r.setDocWidth(NumberUtils.toFloat(docWidthStr));
				}
				String articleNameMaxLengthStr = properties.getProperty("receipt-articlename-max-length");
				if(StringUtils.isNotBlank(articleNameMaxLengthStr)){
					r.setArticleNameMaxLength(NumberUtils.toInt(articleNameMaxLengthStr));
				}
				String mergeCipAndDesignationProp = properties.getProperty("receipt-printer-mergeCipAndDesignation");
				if(StringUtils.isNotBlank(mergeCipAndDesignationProp)){
					r.setMergeCipAndDesignation(BooleanUtils.toBoolean(mergeCipAndDesignationProp));
				}	

				String receiptPrinterNameProp = properties.getProperty("receipt-printer-name");
				if(StringUtils.isNotBlank(receiptPrinterNameProp)){
					r.setReceiptPrinterName(receiptPrinterNameProp);
				}	
				String receiptPrintModeProp = properties.getProperty("receipt-print-mode");
				if(StringUtils.isNotBlank(receiptPrintModeProp)){
					try {
						r.setReceiptPrintMode(ReceiptPrintMode.valueOf(receiptPrintModeProp));
					} catch(Exception ex){
						// noop. Keep default mode
					}
				}

				String debugProp = properties.getProperty("receipt-print-debug");
				if(StringUtils.isNotBlank(debugProp)){
					r.setDebug(BooleanUtils.toBoolean(debugProp));
				}	
			} catch (FileNotFoundException e) {
				throw new IllegalStateException(e);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		return r;
	}
}
