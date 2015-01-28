package org.adorsys.adpharma.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 
 * @author clovisgakam
 *
 */
public class DateHelper {

	public 	final static String  DATE_SLASH_FORMAT = "dd/MM/yy";
	public 	final static String  DATE_FORMAT = "dd-MM-yyyy";
	public 	final static String  MONTH_FORMAT = "MM-yyyy";
	public 	final static String  YEAR_FORMAT = "yyyy";



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


	public List<String> getDayInPeriodeAsString(Date from,Date to){
		List<String> dayInPeriode = new ArrayList<String>();
		if(from.compareTo(to)>0)
			return dayInPeriode ;
		String current = format(from, DATE_FORMAT);
		String end = format(to, DATE_FORMAT);
		do {
			dayInPeriode.add(current);
			current =  format(DateUtils.addDays(from, 1), DATE_FORMAT);
		} while (StringUtils.equals(end, current));
		dayInPeriode.add(end);
		return dayInPeriode ;
	}

	public List<String> getMonthInPeriodeAsString(Date from,Date to){
		List<String> dayInPeriode = new ArrayList<String>();
		if(from.compareTo(to)>0)
			return dayInPeriode ;
		String current = format(from, MONTH_FORMAT);
		String end = format(to, MONTH_FORMAT);
		do {
			dayInPeriode.add(current);
			current =  format(DateUtils.addDays(from, 1), MONTH_FORMAT);
		} while (StringUtils.equals(end, current));
		dayInPeriode.add(end);
		return dayInPeriode ;
	}

	public List<String> getYearsInPeriodeAsString(Date from,Date to){
		List<String> dayInPeriode = new ArrayList<String>();
		if(from.compareTo(to)>0)
			return dayInPeriode ;
		String current = format(from, YEAR_FORMAT);
		String end = format(to, YEAR_FORMAT);
		do {
			dayInPeriode.add(current);
			current =  format(DateUtils.addDays(from, 1), YEAR_FORMAT);
		} while (StringUtils.equals(end, current));
		dayInPeriode.add(end);
		return dayInPeriode ;
	}





}
