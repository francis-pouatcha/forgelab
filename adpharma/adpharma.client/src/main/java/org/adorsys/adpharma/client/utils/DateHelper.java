package org.adorsys.adpharma.client.utils;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;

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


	public static LocalDate dateToLacalDate(Date date){
		Date input =date==null?new Date():date;
		LocalDate localdate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localdate;
	}

	public static Date localDateToDate(LocalDate localDate){
		if(localDate==null) return null;
		Instant instant = Instant.ofEpochSecond(localDate.toEpochDay()*86400);
//		Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		return date;
	}
	
	public static List<Integer> getYears(){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<Integer> years = new ArrayList<Integer>();
		for (int i = year; i > year-10; i--) {
			years.add(Integer.valueOf(i));
		}
		years.add(0, null);
		return years;
		
	}
	
	
	public static boolean isWEEK(Date date){
		LocalDate localDate = DateHelper.dateToLacalDate(date);
		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		return DayOfWeek.SATURDAY.equals(dayOfWeek)||DayOfWeek.SUNDAY.equals(dayOfWeek);
	}

}
