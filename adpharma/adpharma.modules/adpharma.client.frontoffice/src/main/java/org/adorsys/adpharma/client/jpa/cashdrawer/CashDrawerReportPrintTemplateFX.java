package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.print.Paper;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.control.CalendarFormat;
import org.adorsys.javafx.crud.extensions.control.DefaultBigDecimalFormatCM;

public class CashDrawerReportPrintTemplateFX  implements CashDrawerReportPrintTemplate {
	
	private CalendarFormat calendarFormat = new CalendarFormat();

	private double printableWidth = Paper.A4.getWidth() -(40*2);
	private double printableHeight = Paper.A4.getHeight() -(40*2);
	private double width = printableWidth;
	private RowConstraints mainRowHeight = new RowConstraints();
	private RowConstraints doubleRowHeight = new RowConstraints();
	private RowConstraints separatorRowConstraint = new RowConstraints();

	private VBox currentPage = null;
	
	private List<VBox> pages = new ArrayList<VBox>();

	private GridPane reportTable = null;

	private final Login reportPrinter;
	private final ResourceBundle resourceBundle;
	private final Locale locale;
	private final Agency agency;
	private final Map<Long, Login> logins;
	private final Calendar startDate;
	private final Calendar endDate;
	
	public List<VBox> getPages() {
		return pages;
	}
	int rowHeiht = 15;
	int separatorRowHeight = 2;

	public CashDrawerReportPrintTemplateFX(CashDrawerReportPrinterData cashDrawerPrinterData, ResourceBundle resourceBundle,
			Locale locale) {
		this.reportPrinter = cashDrawerPrinterData.getLogin();
		this.resourceBundle = resourceBundle;
		this.locale = locale;
		this.agency = cashDrawerPrinterData.getAgency();
		this.logins = cashDrawerPrinterData.getLogins();
		this.startDate = cashDrawerPrinterData.getStartDate();
		this.endDate = cashDrawerPrinterData.getEndDate();
		mainRowHeight.setPrefHeight(rowHeiht);
		doubleRowHeight.setPrefHeight(rowHeiht * 2);
		separatorRowConstraint.setPrefHeight(separatorRowHeight);
		
		// FIrst page
		currentPage = new VBox();
		pages.add(currentPage);
		printReportHeader(currentPage);
		fillTableHaeder();
	}

	private void newPage() {
		currentPage = new VBox();
		currentPage.setPrefHeight(printableHeight);
		pages.add(currentPage);
		currentPageHeight=rowHeiht+separatorRowHeight;
		fillTableHaeder();
	}
	
	double currentPageHeight = rowHeiht+separatorRowHeight;
	public void addItems(List<CashDrawer> cashDrawers) {
		for (CashDrawer cashDrawer : cashDrawers) {
			if(currentPageHeight>=printableHeight){
				newPage();
			}
			newTableRow(cashDrawer.getCashDrawerNumber(), 
					cashDrawer.getCashier().getLoginName(), 
					logins.get(cashDrawer.getCashier().getId()).getFullName(), 
					cashDrawer.getTotalClientVoucher(),
					cashDrawer.getTotalCompanyVoucher(),
					cashDrawer.getTotalCash(),
					cashDrawer.getTotalCreditCard());
			newSeparator();
		}
	}

	private void newTableRow(String cashDrawerNumber, 
			String cashierLoginName,
			String cashierFullName,
			BigDecimal totalClientVoucher,
			BigDecimal totalCompanyVoucher, 
			BigDecimal totalCash,
			BigDecimal totalCreditCard) {
		reportTable.getRowConstraints().add(mainRowHeight);
		rowIndex++;
		reportTable.add(new StandardText(cashDrawerNumber), 0, rowIndex, 1,  1);
		reportTable.add(new StandardText(cashierLoginName), 1, rowIndex, 1,  1);
		reportTable.add(new StandardText(cashierLoginName), 2, rowIndex, 1,  1);
		reportTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalClientVoucher)), 3, rowIndex, 1,  1);
		reportTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalCompanyVoucher)), 4, rowIndex, 1,  1);
		reportTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalCash)), 5, rowIndex, 1,  1);
		reportTable.add(new StandardText(DefaultBigDecimalFormatCM.getinstance().format(totalCreditCard)), 6, rowIndex, 1,  1);
		currentPageHeight+=rowHeiht;
	}
	
	private void newSeparator(){
		rowIndex++;
		reportTable.getRowConstraints().add(separatorRowConstraint);
		currentPageHeight+=separatorRowHeight;
		Separator separator = new Separator();
		reportTable.add(separator, 0, rowIndex, 7, 1);
	}

	int rowIndex = -1;
	int pageNumber = 0;	
	private void fillTableHaeder() {
		reportTable = new GridPane();
		rowIndex = -1;
		currentPage.getChildren().add(reportTable);
		newSeparator();
		rowIndex++;
		reportTable.getRowConstraints().add(mainRowHeight);
		currentPageHeight+=rowHeiht;
		reportTable.getColumnConstraints().add(new ColumnConstraints(width * .11));
		reportTable.getColumnConstraints().add(new ColumnConstraints(width * .11));
		reportTable.getColumnConstraints().add(new ColumnConstraints(width * .40));
		reportTable.getColumnConstraints().add(new ColumnConstraints(width * .1));
		reportTable.getColumnConstraints().add(new ColumnConstraints(width * .1));
		reportTable.getColumnConstraints().add(new ColumnConstraints(width * .1));
		reportTable.getColumnConstraints().add(new ColumnConstraints(width * .1));

		reportTable.add(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_cashDrawerNumber.title")), 0, rowIndex, 1,  1);
		reportTable.add(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_cashierLogin.title")), 1, rowIndex, 1,  1);
		reportTable.add(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_cashier.title")), 2, rowIndex, 1,  1);
		reportTable.add(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_totalClientVoucher.title")), 3, rowIndex, 1,  1);
		reportTable.add(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_totalCompanyVoucher.title")), 4, rowIndex, 1,  1);
		reportTable.add(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_totalCash.title")), 5, rowIndex, 1,  1);
		reportTable.add(new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_totalCreditCard.title")), 6, rowIndex, 1,  1);
		
		newSeparator();
	}

	private void printReportHeader(VBox page) {

		int row = -1;

		GridPane headerPane = new GridPane();
		page.getChildren().add(headerPane);
		
		headerPane.getColumnConstraints().add(new ColumnConstraints(width * .7));
		headerPane.getColumnConstraints().add(new ColumnConstraints(width * .3));
		
		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		currentPageHeight+=rowHeiht;
		Text documentName = new BoldText(resourceBundle.getString("CashDrawerReportPrintTemplate_header.title"));
		headerPane.add(documentName, 0, row, 2, 1);
		GridPane.setHalignment(documentName, HPos.CENTER);

		row++;
		headerPane.getRowConstraints().add(doubleRowHeight);
		currentPageHeight+=rowHeiht;
		currentPageHeight+=rowHeiht;
		Text companyName = new BoldText(agency.getName());
		headerPane.add(companyName, 0, row, 1, 1);
		GridPane.setHalignment(companyName, HPos.LEFT);
		GridPane.setValignment(companyName, VPos.BOTTOM);

		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		currentPageHeight+=rowHeiht;
		Text Tel = new StandardText("Tel: " + agency.getPhone());
		headerPane.add(Tel, 0, row, 1, 1);
		GridPane.setHalignment(Tel, HPos.LEFT);
		GridPane.setValignment(Tel, VPos.BOTTOM);

		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		currentPageHeight+=rowHeiht;
		
		Text supplier = new StandardText(agency.getStreet());
		headerPane.add(supplier, 0, row, 1, 1);
		GridPane.setHalignment(supplier, HPos.LEFT);
		GridPane.setValignment(supplier, VPos.BOTTOM);

		Text agent = new StandardText(resourceBundle.getString("CashDrawerReportPrintTemplate_agent.title") 
				+ " " + reportPrinter.getFullName());
		headerPane.add(agent, 1, row, 1, 1);
		GridPane.setHalignment(agent, HPos.RIGHT);
		GridPane.setValignment(agent, VPos.BOTTOM);

		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		currentPageHeight+=rowHeiht;
		Separator separator = new Separator(Orientation.HORIZONTAL);
		headerPane.add(separator, 0, row, 2, 1);
		GridPane.setHgrow(separator, Priority.ALWAYS);

		row++;
		headerPane.getRowConstraints().add(mainRowHeight);
		currentPageHeight+=rowHeiht;
		Text invoiceDateLabel = new StandardText(calendarFormat.format(startDate, "dd-MM-yyyy HH:mm", locale) +
				" - " + calendarFormat.format(endDate, "dd-MM-yyyy HH:mm", locale));
		headerPane.add(invoiceDateLabel, 1, row, 1, 2);
		GridPane.setHalignment(invoiceDateLabel, HPos.CENTER);
		GridPane.setValignment(invoiceDateLabel, VPos.BOTTOM);

		row++;
		currentPageHeight+=rowHeiht;
		headerPane.getRowConstraints().add(mainRowHeight);
		headerPane.add(new Label(), 0, row, 2, 1);

	}

	private static Font boldFont() {
		Text text = new Text();
		Font font = text.getFont();
		double size = font.getSize()-5;
		return Font.font(null, FontWeight.EXTRA_BOLD, size);
	}
	
	private static Font font() {
		Text text = new Text();
		Font font = text.getFont();
		double size = font.getSize()-5;
		return Font.font(null, FontWeight.NORMAL, size);
	}
	static Font boldFont = boldFont();
	static Font font = font();
	static class StandardText extends Text{
		StandardText() {
			super();
			setFont(font);
		}
		StandardText(String text) {
			super(text);
			setFont(font);
		}
	}
	
	static class BoldText extends Text{
		BoldText() {
			super();
			setFont(boldFont);
		}
		BoldText(String text) {
			super(text);
			setFont(boldFont);
		}
	}
}
