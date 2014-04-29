package org.adorsys.adpharma.server.utils;

import java.util.Date;

public class AdTimeFrame {
	private Date startTime;
	private Date endTime;
	public Date getStartTime() {
		return startTime;
	}
	public AdTimeFrame setStartTime(Date startTime) {
		this.startTime = startTime;
		return this;
	}
	public Date getEndTime() {
		return endTime;
	}
	public AdTimeFrame setEndTime(Date endTime) {
		this.endTime = endTime;
		return this;
	}
}
