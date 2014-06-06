package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencyService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginSearchResult;
import org.adorsys.adpharma.client.jpa.login.LoginService;
import org.adorsys.adpharma.client.utils.AdTimeFrame;
import org.adorsys.adpharma.client.utils.AdTimeFrameBasedSearchInput;

public class CashDrawerReportPrinterDataService extends
		Service<CashDrawerReportPrinterData> {

	@Inject
	private CashDrawerService cashDrawerService;
	@Inject
	private LoginService loginService;
	@Inject
	private AgencyService agencyService;
	@Inject
	private SecurityUtil securityUtil;
	
	private Calendar startDate;
	private Calendar endDate;
	
	private CashDrawerReportPrintTemplate worker;
	private CashDrawerReportPrinterData reportData;
	public CashDrawerReportPrinterDataService seReportData(
			CashDrawerReportPrinterData reportData) {
		this.reportData = reportData;
		return this;
	}

	public CashDrawerReportPrintTemplate getWorker() {
		return worker;
	}

	public CashDrawerReportPrinterDataService setWorker(CashDrawerReportPrintTemplate worker) {
		this.worker = worker;
		return this;
	}

	public CashDrawerReportPrinterDataService setStartDate(Calendar startDate) {
		this.startDate = startDate;
		return this;
	}

	public CashDrawerReportPrinterDataService setEndDate(Calendar endDate) {
		this.endDate = endDate;
		return this;
	}

	@Override
	protected Task<CashDrawerReportPrinterData> createTask() {
		return new Task<CashDrawerReportPrinterData>() {
			@Override
			protected CashDrawerReportPrinterData call() throws Exception {
				if (startDate == null || endDate==null)
					return null;
				if(reportData==null){
					Login login = securityUtil.getConnectedUser();
					Agency agency = agencyService.findById(login.getAgency().getId());
					LoginSearchResult loginList = loginService.listAll(0, 500);
					List<Login> resultList = loginList.getResultList();
					HashMap<Long, Login> logins = new HashMap<Long, Login>(resultList.size());
					for (Login l : resultList) {
						logins.put(l.getId(), l);
					}
					reportData = new CashDrawerReportPrinterData(agency, login, logins, endDate, startDate);
				}
				AdTimeFrame adTimeFrame = new AdTimeFrame();
				adTimeFrame.setStartTime(startDate);
				adTimeFrame.setEndTime(endDate);
				AdTimeFrameBasedSearchInput adTimeFrameBasedSearchInput = new AdTimeFrameBasedSearchInput();
				adTimeFrameBasedSearchInput.setTimeFrame(adTimeFrame);
				adTimeFrameBasedSearchInput.setStart(reportData.getStart());
				adTimeFrameBasedSearchInput.setMax(reportData.getMax());
				CashDrawerSearchResult cashDrawerSearchResult = cashDrawerService.findByClosingDateBetween(adTimeFrameBasedSearchInput);
				reportData.setCashDrawerSearchResult(cashDrawerSearchResult);	
				return reportData;
			}
		};
	}

}
