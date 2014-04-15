package org.adorsys.adpharma.server.jpa;

import java.math.BigDecimal;

public class ArticleLotDetailsManager {

	private ArticleLot lotToDetails ;

	private ProductDetailConfig detailConfig;

	private BigDecimal detailsQty;

	private BigDecimal lotQty;

	private BigDecimal targetQty;

	private BigDecimal targetPrice;

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

	public void setTargetQty(BigDecimal targetQty) {
		this.targetQty = targetQty;
	}

	public void setTargetPrice(BigDecimal targetPrice) {
		this.targetPrice = targetPrice;
	}

	public BigDecimal getTargetQty() {
		return targetQty;
	}

	public BigDecimal getTargetPrice() {
		return targetPrice;
	}




}
