package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adpharma.client.events.CashDrawerPrintRequest;
import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.jpa.print.PrintDialog;
import org.adorsys.adpharma.client.utils.AdTimeFrame;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class CashDrawerReportPrinter {

	@Inject
	private CashDrawerReportPrinterDataService dataService;
	@Inject
	private ServiceCallFailedEventHandler dataServiceCallFailedEventHandler;
	@Inject
	private ErrorMessageDialog dataErrorMessageDialog;
	
	@Inject
	@Bundle({ CrudKeys.class, CashDrawerReportPrintTemplate.class })
	private ResourceBundle resourceBundle;
	
	@Inject
	private Locale locale;
	
	@Inject
	private PrintDialog printDialog;
	
	@Inject
	private CashDrawerReportTimeFrameDialog timeFrameDialog;
	
	public void handlePrintRequestedEvent(
			@Observes @PrintRequestedEvent CashDrawerPrintRequest request) {
		AdTimeFrame timeFrame = request.getTimeFrame();
		if(timeFrame==null) timeFrame = new AdTimeFrame();
		if(timeFrame.getStartTime()==null || timeFrame.getEndTime()==null) {
			Date todayDate = new Date();
			Date tomorrowDate = DateUtils.addDays(todayDate, 1);
			tomorrowDate = DateUtils.addMilliseconds(tomorrowDate, -1);
			Calendar startTime = DateUtils.truncate(DateUtils.toCalendar(todayDate), Calendar.DATE);
			Calendar endTime = DateUtils.truncate(DateUtils.toCalendar(tomorrowDate), Calendar.DATE);
			timeFrameDialog.getStartTime().setCalendar(startTime);
			timeFrameDialog.getEndTime().setCalendar(endTime);
			timeFrameDialog.display();
		} else {
			dataService.setStartDate(timeFrame.getStartTime()).setEndDate(timeFrame.getEndTime()).start();
		}
	}

	@PostConstruct
	public void postConstruct() {
		dataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerReportPrinterDataService s = (CashDrawerReportPrinterDataService) event.getSource();
	            CashDrawerReportPrinterData reportData = s.getValue();
	            event.consume();
	            s.reset();
	    		if(reportData==null) return;
	    		CashDrawerReportPrintTemplate worker = s.getWorker();
	    		if(worker==null)
	    			worker = new CashDrawerReportPrintTemplate(reportData, resourceBundle, locale);
	    		if(reportData.getCashDrawerSearchResult().getResultList().isEmpty()) {
	    			List<VBox> pages = worker.getPages();
	    			printDialog.getPages().clear();
	    			printDialog.getPages().addAll(pages);
	    			printDialog.show();
	    		} else {
		    		worker.addItems(reportData.getCashDrawerSearchResult().getResultList());
		    		int start = reportData.getCashDrawerSearchResult().getSearchInput().getStart();
		    		int currentSize = reportData.getCashDrawerSearchResult().getResultList().size();
		    		reportData.getCashDrawerSearchResult().getSearchInput().setStart(start+currentSize);
		    		dataService.setWorker(worker).seReportData(reportData).start();
	    		}
			}
		});

		dataServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {
			@Override
			protected void showError(Throwable exception) {
				String message = exception.getMessage();
				dataErrorMessageDialog.getTitleText().setText(
						resourceBundle.getString("Entity_search_error.title"));
				if (!StringUtils.isBlank(message))
					dataErrorMessageDialog.getDetailText().setText(message);
				dataErrorMessageDialog.display();
			}
		});
		dataService.setOnFailed(dataServiceCallFailedEventHandler);

		dataErrorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						dataErrorMessageDialog.closeDialog();
					}
				});
		
		timeFrameDialog.getOkButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Calendar startTime = timeFrameDialog.getStartTime().getCalendar();
				Calendar endTime = timeFrameDialog.getEndTime().getCalendar();
				timeFrameDialog.closeDialog();
				dataService.setStartDate(startTime).setEndDate(endTime).start();
			}
		});
		
		timeFrameDialog.getOkButton().disableProperty().bind(timeFrameDialog.getStartTime().calendarProperty().isNull().or(timeFrameDialog.getStartTime().calendarProperty().isNull()));
		
		timeFrameDialog.getCancelButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				timeFrameDialog.closeDialog();
			}
		});
	}
}
