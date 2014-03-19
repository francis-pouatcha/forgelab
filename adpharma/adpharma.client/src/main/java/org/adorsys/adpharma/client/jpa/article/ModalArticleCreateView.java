package org.adorsys.adpharma.client.jpa.article;

import javax.inject.Inject;

import javafx.scene.layout.Pane;

import org.adorsys.javafx.crud.extensions.view.ApplicationModal;

public class ModalArticleCreateView extends ApplicationModal{

	@Inject
	ArticleCreateView createView;
	@Override
	public Pane getRootPane() {
		return createView.getRootPane();
	}

}
