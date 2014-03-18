package org.adorsys.adpharma.client.jpa.article;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;

public class ModalArticleSearchView extends ApplicationModal{

	@FXML
	private VBox rootPane ;

	@FXML
	private TextField articleName;

	@FXML Button searchButton;

	@FXML Button cancelutton;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	@Bundle({ CrudKeys.class, Article.class })
	private ResourceBundle resourceBundle;


	@Override
	public VBox getRootPane() {
		return rootPane;
	}

	@PostConstruct
	public void onPostConstruct(){
		FXMLLoaderUtils.load(fxmlLoader, this,resourceBundle);

	}

	public TextField getArticleName() {
		return articleName;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public Button getCancelutton() {
		return cancelutton;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}


}
