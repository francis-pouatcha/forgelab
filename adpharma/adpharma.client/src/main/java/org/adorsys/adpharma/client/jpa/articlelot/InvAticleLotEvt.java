package org.adorsys.adpharma.client.jpa.articlelot;

public class InvAticleLotEvt {
	private ArticleLot articleLot;

	public InvAticleLotEvt(ArticleLot articleLot) {
		super();
		this.articleLot = articleLot;
	}

	public InvAticleLotEvt() {
		super();
	}

	public ArticleLot getArticleLot() {
		return articleLot;
	}

	public void setArticleLot(ArticleLot articleLot) {
		this.articleLot = articleLot;
	}
	
}
