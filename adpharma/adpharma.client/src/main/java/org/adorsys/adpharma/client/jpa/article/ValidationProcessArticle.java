package org.adorsys.adpharma.client.jpa.article;

import java.math.BigDecimal;

public class ValidationProcessArticle {
	
	
	public static boolean validatePrices(Article article) {
	      BigDecimal sppu = article.getSppu();
	      BigDecimal pppu = article.getPppu();
	      int result = sppu.compareTo(pppu); 
	      if(result==1) {
	    	  return true;
	      }else {
			  return false;
	      }
	}
	
	public static boolean validatePrices(BigDecimal pa, BigDecimal pv) {
	      int result = pv.compareTo(pa); 
	      if(result==1) {
	    	  return true;
	      }else {
			  return false;
	      }
	}

}