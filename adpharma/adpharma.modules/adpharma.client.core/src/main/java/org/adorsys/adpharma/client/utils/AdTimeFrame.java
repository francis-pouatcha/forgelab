package org.adorsys.adpharma.client.utils;

import java.util.Calendar;

public class AdTimeFrame {
	private Calendar startTime;
	private Calendar endTime;
	public Calendar getStartTime() {
		return startTime;
	}
	public AdTimeFrame setStartTime(Calendar startTime) {
		this.startTime = startTime;
		return this;
	}
	public Calendar getEndTime() {
		return endTime;
	}
	public AdTimeFrame setEndTime(Calendar endTime) {
		this.endTime = endTime;
		return this;
	}
}
