package org.adorsys.adpharma.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author clovisgakam
 *
 */
public class DateHelper {

	public 	final static String  DATE_SLASH_FORMAT = "dd/MM/yy";
	public 	final static String  DATE_FORMAT = "dd-MM-yyyy";
	
	
	public static String  format(Date date , String patern){
		if(date ==null) return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patern);
		String format = simpleDateFormat.format(date);
		return format ;
	}
	
	
	public static Date parse(String stringFormat,String patern){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patern);
		Date date =  null ;
		try {
			date  = simpleDateFormat.parse(stringFormat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}


	

}
