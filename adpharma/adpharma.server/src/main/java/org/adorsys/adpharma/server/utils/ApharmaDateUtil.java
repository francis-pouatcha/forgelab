package org.adorsys.adpharma.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @author clovisgakam
 *
 */
public class ApharmaDateUtil {

	public static final String DATE_PATTERN_LONG = "dd-MM-yyyy";
	public static final String DATE_PATTERN_TRIM = "ddMMyy";

	public static final String DATE_PATTERN_LONG_LIT = "EEE, dd MMM yyyy";
	public static final String DAY_MONTH_PATTERN = "dd-yy";
	public static final String MONTH_YEAR_PATTERN = "MMyy";
	public static final String DAY_MONTH_PATTERN_LIT = "EEE, dd MMM";

	public static final String DATE_TIME_PATTERN_LONG = "dd-MM-yyyy HH:mm";
	public static final String DATE_TIME_PATTERN_LONG_LIT = "EEE, dd MMM yyyy HH:mm";

	public static final String YEAR_PATTERN = "yyyy";

	public static final String dateToString(Date date, String pattern){
		if (date == null) {
			return new SimpleDateFormat(pattern).format(new Date());
		}else {
			return new SimpleDateFormat(pattern).format(date);

		}
	}

	public static final Date stringToDate(String date, String pattern){
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {

			return new DateUtils().addYears(new Date(), 0);
		}
	}


	public static Date getBeginDayDate(){
		Date date = new Date();
		String stringDate = dateToString(date, ApharmaDateUtil.DATE_PATTERN_LONG);
		stringDate = stringDate + " 00:00" ;
		return stringToDate(stringDate, DATE_TIME_PATTERN_LONG);
	}

	public static Date getEndDayDate(){
		Date date = new Date();
		String stringDate = dateToString(date, ApharmaDateUtil.DATE_PATTERN_LONG);
		stringDate = stringDate + " 23:59" ;
		return stringToDate(stringDate, DATE_TIME_PATTERN_LONG);
	}
	
	public static String getYear(){
		return dateToString(new Date(), YEAR_PATTERN);
	}
	public static String getMonthYear(){
		return dateToString(new Date(), MONTH_YEAR_PATTERN);
	}


}
