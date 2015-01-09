package org.adorsys.adpharma.client.jpa.articlelot;

public class SlsAticleLotEvt {
	private ArticleLot articleLot;

	public SlsAticleLotEvt(ArticleLot articleLot) {
		super();
		this.articleLot = articleLot;
	}

	public SlsAticleLotEvt() {
		super();
	}

	public ArticleLot getArticleLot() {
		return articleLot;
	}

	public void setArticleLot(ArticleLot articleLot) {
		this.articleLot = articleLot;
	}
	
}
