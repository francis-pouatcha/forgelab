package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalArticleLotTransferCreateView extends ApplicationModal{
	private AnchorPane rootPane;
	private ComboBox<ArticleLot> lotToTransfer;
	private ComboBox<WareHouse> wareHouse;
	private BigDecimalField qtyToTransfer;
	private BigDecimalField lotQty;
	private Button saveButton ;
	private Button cancelButton ;

	@Inject
	@Bundle({ CrudKeys.class, ArticleLot.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Inject
	private TextInputControlValidator textInputControlValidator;

	@Inject
	private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;


	@PostConstruct
	public void postConstruct(){
		LazyViewBuilder lvb = new LazyViewBuilder();
		lotToTransfer = lvb.addComboBox("ArticleLotTransferManager_lotToTransfer_description.title", "lotToTransfer", resourceBundle);
		lotQty =lvb.addBigDecimalField("ArticleLotTransferManager_lotQty_description.title", "lotQty", resourceBundle, NumberType.INTEGER, locale,ViewModel.READ_ONLY);
		wareHouse = lvb.addComboBox("ArticleLotTransferManager_wareHouse_description.title", "wareHouse", resourceBundle);
		qtyToTransfer =lvb.addBigDecimalField("ArticleLotTransferManager_qtyToTransfer_description.title", "qtyToTransfer", resourceBundle, NumberType.INTEGER, locale);

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lvb.toRows(),ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();

	}


	public void bind(ArticleLotTransferManager model)
	{
		lotToTransfer.valueProperty().bindBidirectional(model.lotToTransferProperty());
		wareHouse.valueProperty().bindBidirectional(model.wareHouseProperty());
		qtyToTransfer.numberProperty().bindBidirectional(model.qtyToTransferProperty());
		lotQty.numberProperty().bindBidirectional(model.lotQtyProperty());
	}

	public void addValidators()
	{
		qtyToTransfer.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLotTransferManager>(textInputControlValidator, qtyToTransfer, ArticleLotTransferManager.class, "qtyToTransfer", resourceBundle));
		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<ArticleLotTransferManager>> validate(ArticleLotTransferManager model)
	{
		Set<ConstraintViolation<ArticleLotTransferManager>> violations = new HashSet<ConstraintViolation<ArticleLotTransferManager>>();
		violations.addAll(textInputControlValidator.validate(qtyToTransfer, ArticleLotTransferManager.class, "qtyToTransfer", resourceBundle));
		violations.addAll(toOneAggreggationFieldValidator.validate(wareHouse, model.wareHouseProperty(), ArticleLotTransferManager.class, "wareHouse", resourceBundle));
		return violations;
	}


	public AnchorPane getRootPane()
	{
		return rootPane;
	}

	public Button getSaveButton()
	{
		return saveButton;
	}

	public Button getCancelButton()
	{
		return cancelButton;

	}

	public BigDecimalField getQtyToTransfer()
	{
		return qtyToTransfer;

	}
	public BigDecimalField getLotQty()
	{
		return lotQty;

	}

	public ComboBox<WareHouse> getWareHouse()
	{
		return wareHouse;
	}
	public ComboBox<ArticleLot> getLotToTransfer()
	{
		return lotToTransfer;
	}


}
