package org.adorsys.adpharma.server.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adorsys.adpharma.server.jpa.Currency;

public class CurencyUtil {
	
	public static BigDecimal convertToCfa(Currency currency , BigDecimal amount){
		if(currency==null)
			return amount;
		BigDecimal cfaEquivalent = currency.getCfaEquivalent() ;
		if(cfaEquivalent!=null&& amount!=null)
			return amount.multiply(cfaEquivalent);
		return amount ;
	}
	
	/*
	 * set decimal part to zero an round to up
	 * 
	 */
	public static BigDecimal round(BigDecimal value){
		return value.setScale(0,RoundingMode.HALF_UP).setScale(2) ;
	}

}
