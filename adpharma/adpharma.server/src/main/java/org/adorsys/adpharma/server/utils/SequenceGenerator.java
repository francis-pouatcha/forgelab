package org.adorsys.adpharma.server.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;


public class SequenceGenerator {
	public static String PORCHASE_SEQUENCE_PREFIXE = "CF";
	public static String DELIVERY_SEQUENCE_PREFIXE = "DE";
	public static String LOT_SEQUENCE_PREFIXE = "LO";
	public static String INVENTORY_SEQUENCE_PREFIXE = "INV";
	public static String SALE_SEQUENCE_PREFIXE = "SO";
	public static String AGENCY_SEQUENCE_PREFIXE = "AG";
	public static String CASHDRAWER_SEQUENCE_PREFIXE = "CD";
	public static String PRESCRIPTIONBOOK_SEQUENCE_PREFIXE = "PB";
	public static String CUSTOMER_VOUCHER_SEQUENCE_PREFIXE = "CV";
	public static String CUSTOMER_INVOICE_SEQUENCE_PREFIXE = "CI";


	static long time = 0l;// the time corresponding to the 01.01.2010 00:00:00:00 ...
	static {
		Date date = new Date();
		date = DateUtils.setYears(date, 2014);// never change this date.
		date = DateUtils.truncate(date, Calendar.YEAR);
		time = date.getTime();
	}
	public static String getSequence(String prefixe){
		synchronized (prefixe) {
			try {
				Thread.currentThread().sleep(20);
			} catch (InterruptedException e) {
			}
			return prefixe+Long.toString(System.currentTimeMillis()-time, 36).toUpperCase();
		}
	}


}
