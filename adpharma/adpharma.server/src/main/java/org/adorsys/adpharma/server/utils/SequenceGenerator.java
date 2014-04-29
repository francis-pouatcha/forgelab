package org.adorsys.adpharma.server.utils;


public class SequenceGenerator {
	public static String PORCHASE_SEQUENCE_PREFIXE = "CF";
	public static String DELIVERY_SEQUENCE_PREFIXE = "DE";
	public static String LOT_SEQUENCE_PREFIXE = "LO";
	public static String INVENTORY_SEQUENCE_PREFIXE = "IN";
	public static String SALE_SEQUENCE_PREFIXE = "SO";
	public static String AGENCY_SEQUENCE_PREFIXE = "AG";
	public static String CASHDRAWER_SEQUENCE_PREFIXE = "CD";
	public static String PRESCRIPTIONBOOK_SEQUENCE_PREFIXE = "PB";
	public static String CUSTOMER_VOUCHER_SEQUENCE_PREFIXE = "CV";


	public static String getSequence(String prefixe){
		String sequence  = prefixe;

		try {
			sequence = sequence+"-"+Long.toString(System.currentTimeMillis(), 36);
			Thread.currentThread().sleep(2);
			
		} catch (InterruptedException e) {
			
		}
		return sequence;
	}


}
