package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.Calendar;
import java.util.Map;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;

public class CashDrawerReportPrinterData {

	private final Agency agency;
	private final Login login;
	private CashDrawerSearchResult cashDrawerSearchResult;
	private final Map<Long, Login> logins;
	private final Calendar endDate;
	private final Calendar startDate;
	private int start = 0;
	private final int max = 100;

	public CashDrawerReportPrinterData(Agency agency,
			Login login, Map<Long, Login> logins, Calendar endDate, Calendar startDate) {
		super();
		this.agency = agency;
		this.login = login;
		this.logins = logins;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Login getLogin() {
		return login;
	}

	public Agency getAgency() {
		return agency;
	}

	public Map<Long, Login> getLogins() {
		return logins;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public CashDrawerSearchResult getCashDrawerSearchResult() {
		return cashDrawerSearchResult;
	}

	public void setCashDrawerSearchResult(
			CashDrawerSearchResult cashDrawerSearchResult) {
		this.cashDrawerSearchResult = cashDrawerSearchResult;
	}


	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getMax() {
		return max;
	}
}
