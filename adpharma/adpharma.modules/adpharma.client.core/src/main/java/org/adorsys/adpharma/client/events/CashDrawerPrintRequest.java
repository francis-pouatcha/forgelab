package org.adorsys.adpharma.client.events;

import org.adorsys.adpharma.client.utils.AdTimeFrame;

public class CashDrawerPrintRequest {
	private final AdTimeFrame timeFrame;

	public AdTimeFrame getTimeFrame() {
		return timeFrame;
	}

	public CashDrawerPrintRequest(AdTimeFrame timeFrame) {
		super();
		this.timeFrame = timeFrame;
	}
	
}
