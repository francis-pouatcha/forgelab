package org.adorsys.adpharma.client.jpa.articlelot;

public class InvArticleLotSearchInputEvt {
	private ArticleLotSearchInput model;

	public InvArticleLotSearchInputEvt(ArticleLotSearchInput model) {
		this.model = model;
	}

	public ArticleLotSearchInput getModel() {
		return model;
	}

	public void setModel(ArticleLotSearchInput model) {
		this.model = model;
	}
}
