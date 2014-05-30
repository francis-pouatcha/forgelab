package org.adorsys.adpharma.server.utils;

import java.math.BigDecimal;

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

}
