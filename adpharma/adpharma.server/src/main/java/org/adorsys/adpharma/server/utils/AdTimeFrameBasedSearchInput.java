package org.adorsys.adpharma.server.utils;

public class AdTimeFrameBasedSearchInput {

	private AdTimeFrame timeFrame;
	
   /**
    * The start cursor
    */
   private int start = -1;

	/**
	 * The max number of records to return.
	 */
	private int max = -1;

	public AdTimeFrame getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(AdTimeFrame timeFrame) {
		this.timeFrame = timeFrame;
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

	public void setMax(int max) {
		this.max = max;
	}
}
