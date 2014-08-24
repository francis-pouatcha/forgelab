package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalArticleLotMovedToTrashView extends ApplicationModal {

	private AnchorPane rootPane;

	private TextField mainPic;

	private TextField internalPic;

	private TextField articleName;
	
	private TextField raison;

	private BigDecimalField stockQuantity;

	private BigDecimalField qtyToMoved;

	private Button saveButton;

	private Button resetButton;

	@Inject
	private Locale locale ;

	@Inject
	private TextInputControlValidator textInputControlValidator;

	@Inject
	@Bundle({ CrudKeys.class, ArticleLot.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder lazyviewBuilder = new LazyViewBuilder();
		internalPic = lazyviewBuilder.addTextField("ArticleLot_internalPic_description.title", "internalPic", resourceBundle,ViewModel.READ_ONLY);
		mainPic = lazyviewBuilder.addTextField("ArticleLot_mainPic_description.title", "mainPic", resourceBundle,ViewModel.READ_ONLY);
		articleName = lazyviewBuilder.addTextField("ArticleLot_articleName_description.title", "articleName", resourceBundle,ViewModel.READ_ONLY);
		stockQuantity = lazyviewBuilder.addBigDecimalField("ArticleLot_stockQuantity_description.title", "stockQuantity", resourceBundle, NumberType.INTEGER, locale,ViewModel.READ_ONLY);
		qtyToMoved = lazyviewBuilder.addBigDecimalField("ArticleLot_qtyToMoved_description.title", "salesPricePU", resourceBundle, NumberType.INTEGER, locale);
		raison = lazyviewBuilder.addTextField("ArticleLot_raison_description.title", "raison", resourceBundle);


		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lazyviewBuilder.toRows(), ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();
	}



	public void bind(ArticleLotMovedToTrashData model)
	{
		internalPic.textProperty().bindBidirectional(model.internalPicProperty());
		mainPic.textProperty().bindBidirectional(model.mainPicProperty());
		articleName.textProperty().bindBidirectional(model.articleNameProperty());
		stockQuantity.numberProperty().bindBidirectional(model.stockQuantityProperty());
		qtyToMoved.numberProperty().bindBidirectional(model.qtyToMovedProperty());
		raison.textProperty().bindBidirectional(model.raisonProperty());
	}

	public void addValidators()
	{
		qtyToMoved.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLotMovedToTrashData>(textInputControlValidator, qtyToMoved, ArticleLotMovedToTrashData.class, "qtyToMoved", resourceBundle));

		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<ArticleLotMovedToTrashData>> validate(ArticleLotMovedToTrashData model)
	{
		Set<ConstraintViolation<ArticleLotMovedToTrashData>> violations = new HashSet<ConstraintViolation<ArticleLotMovedToTrashData>>();
		violations.addAll(textInputControlValidator.validate(qtyToMoved, ArticleLotMovedToTrashData.class, "qtyToMoved", resourceBundle));
		return violations;
	}


	@Override
	public Pane getRootPane() {
		return rootPane;
	}



	public TextField getMainPic() {
		return mainPic;
	}



	public TextField getInternalPic() {
		return internalPic;
	}



	public TextField getArticleName() {
		return articleName;
	}



	public BigDecimalField getStockQuantity() {
		return stockQuantity;
	}



	public BigDecimalField getQtyToMoved() {
		return qtyToMoved;
	}



	public Button getSaveButton() {
		return saveButton;
	}



	public Button getResetButton() {
		return resetButton;
	}



	public Locale getLocale() {
		return locale;
	}



	public TextInputControlValidator getTextInputControlValidator() {
		return textInputControlValidator;
	}



	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}




}
