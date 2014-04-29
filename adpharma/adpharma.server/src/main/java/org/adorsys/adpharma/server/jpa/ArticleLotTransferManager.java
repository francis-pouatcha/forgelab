package org.adorsys.adpharma.server.jpa;

import java.math.BigDecimal;

public class ArticleLotTransferManager {
	
	   private ArticleLot lotToTransfer;
	   
	   private WareHouse wareHouse;
	  
	   private BigDecimal qtyToTransfer;
	   
	   private BigDecimal lotQty;

	public ArticleLot getLotToTransfer() {
		return lotToTransfer;
	}

	public WareHouse getWareHouse() {
		return wareHouse;
	}

	public BigDecimal getQtyToTransfer() {
		return qtyToTransfer;
	}

	public BigDecimal getLotQty() {
		return lotQty;
	}

	public void setLotToTransfer(ArticleLot lotToTransfer) {
		this.lotToTransfer = lotToTransfer;
	}

	public void setWareHouse(WareHouse wareHouse) {
		this.wareHouse = wareHouse;
	}

	public void setQtyToTransfer(BigDecimal qtyToTransfer) {
		this.qtyToTransfer = qtyToTransfer;
	}

	public void setLotQty(BigDecimal lotQty) {
		this.lotQty = lotQty;
	}
	   
	  
	   
	   
}
