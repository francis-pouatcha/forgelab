package org.adorsys.adpharma.server.jpa;

import java.math.BigDecimal;

public class ArticleLotDetailsManager {
	
	private ArticleLot lotToDetails ;
	
	private ProductDetailConfig detailConfig;
	
	private BigDecimal detailsQty;
	
	private BigDecimal lotQty;

	public void setLotToDetails(ArticleLot lotToDetails) {
		this.lotToDetails = lotToDetails;
	}

	public void setDetailConfig(ProductDetailConfig detailConfig) {
		this.detailConfig = detailConfig;
	}

	public void setDetailsQty(BigDecimal detailsQty) {
		this.detailsQty = detailsQty;
	}

	public void setLotQty(BigDecimal lotQty) {
		this.lotQty = lotQty;
	}

	public ArticleLot getLotToDetails() {
		return lotToDetails;
	}

	public ProductDetailConfig getDetailConfig() {
		return detailConfig;
	}

	public BigDecimal getDetailsQty() {
		return detailsQty;
	}

	public BigDecimal getLotQty() {
		return lotQty;
	}
	
	
	

}
