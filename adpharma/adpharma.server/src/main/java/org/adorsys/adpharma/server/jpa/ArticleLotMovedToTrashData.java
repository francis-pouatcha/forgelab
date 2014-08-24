package org.adorsys.adpharma.server.jpa;

import java.math.BigDecimal;

public class ArticleLotMovedToTrashData {
	private Long id;
	   private int version;

	   private String internalPic;
	   private String mainPic;
	   private String articleName;
	   private String raison;
	   private BigDecimal stockQuantity;
	   private BigDecimal qtyToMoved;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
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
	public BigDecimal getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(BigDecimal stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public BigDecimal getQtyToMoved() {
		return qtyToMoved;
	}
	public void setQtyToMoved(BigDecimal qtyToMoved) {
		this.qtyToMoved = qtyToMoved;
	}
	public String getRaison() {
		return raison;
	}
	public void setRaison(String raison) {
		this.raison = raison;
	}

	 

	
}
