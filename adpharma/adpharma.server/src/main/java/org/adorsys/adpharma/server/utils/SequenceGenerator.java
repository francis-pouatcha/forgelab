package org.adorsys.adpharma.server.utils;


public class SequenceGenerator {
	public static String PORCHASE_SEQUENCE_PREFIXE = "CF";
	public static String DELIVERY_SEQUENCE_PREFIXE = "DE";
	public static String LOT_SEQUENCE_PREFIXE = "LO";
	public static String INVENTORY_SEQUENCE_PREFIXE = "IN";
	public static String SALE_SEQUENCE_PREFIXE = "SO";
	public static String AGENCY_SEQUENCE_PREFIXE = "AG";


	public static String getSequence(Long index ,String prefixe){
		String sequence  = prefixe+"-"+ApharmaDateUtil.getMonthYear() +"-"+ formatNumber(index.toString(), 4) ;
		return sequence;
	}
	
	public static String getSequenceWhitoutDate(Long index ,String prefixe){
		String sequence  = prefixe+"/"+ formatNumber(index.toString(), 4) ;
		return sequence;
	}


	private static String formatNumber(String stringFormat , int patern){
		String format  = null ;
		if (stringFormat.length() > patern-1) return stringFormat ;
			format = new StringBuilder().append(getZero(patern-stringFormat.length()-1)).append(stringFormat).toString();
		return format.trim();
	}

	private  static String getZero(int nbOfZero){
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i <= nbOfZero; i++) {
			stringBuilder.append(0);

		}
		return stringBuilder.toString();
	}
}
