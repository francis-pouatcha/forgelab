package org.adorsys.adpharma.client.utils;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

public class ArticleLotDetailResultHolder {

	private ArticleLot source ;
	
	private ArticleLot target ;

	public ArticleLot getSource() {
		return source;
	}

	public void setSource(ArticleLot source) {
		this.source = source;
	}

	public ArticleLot getTarget() {
		return target;
	}

	public void setTarget(ArticleLot target) {
		this.target = target;
	}
	
	
}
