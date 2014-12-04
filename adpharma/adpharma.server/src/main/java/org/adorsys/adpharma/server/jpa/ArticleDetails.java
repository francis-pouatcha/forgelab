package org.adorsys.adpharma.server.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

public class ArticleDetails implements Serializable{
	
	private String internalPic;
	
	private String mainPic;
	
	private String articleName;
	
	private String supplier;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private Date deliveryDate;
	
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal purchasePricePU= BigDecimal.ZERO;

	public String getInternalPic() {
		return internalPic;
	}

	public void setInternalPic(String internalPic) {
		this.internalPic = internalPic;
	}

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}


	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public BigDecimal getPurchasePricePU() {
		return purchasePricePU;
	}

	public void setPurchasePricePU(BigDecimal purchasePricePU) {
		this.purchasePricePU = purchasePricePU;
	}
	
	
	

}
