package org.adorsys.adpharma.client.jpa.articlelot;

public class SlsArticleLotSearchInputEvt {
	private ArticleLotSearchInput model;

	public SlsArticleLotSearchInputEvt(ArticleLotSearchInput model) {
		this.model = model;
	}

	public ArticleLotSearchInput getModel() {
		return model;
	}

	public void setModel(ArticleLotSearchInput model) {
		this.model = model;
	}
}
