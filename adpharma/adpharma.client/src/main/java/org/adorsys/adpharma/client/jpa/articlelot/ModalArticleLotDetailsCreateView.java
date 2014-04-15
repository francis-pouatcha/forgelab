package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.javaext.description.Description;
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

public class ModalArticleLotDetailsCreateView extends ApplicationModal{
	private AnchorPane rootPane;
	private ComboBox<ArticleLot> lotToDetails;
	private ComboBox<ProductDetailConfig> detailConfig;
	private BigDecimalField detailsQty;
	private BigDecimalField lotQty;
	@Description("ArtilceLotDetailsManager_targetQty_description")
	private BigDecimalField targetQty;

	@Description("ArtilceLotDetailsManager_targetPrice_description")
	private BigDecimalField targetPrice;
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
		lotToDetails = lvb.addComboBox("ArtilceLotDetailsManager_lotToDetails_description.title", "lotToDetails", resourceBundle);
		lotQty =lvb.addBigDecimalField("ArtilceLotDetailsManager_lotQty_description.title", "lotQty", resourceBundle, NumberType.INTEGER, locale,ViewModel.READ_ONLY);
		detailsQty =lvb.addBigDecimalField("ArtilceLotDetailsManager_detailsQty_description.title", "detailsQty", resourceBundle, NumberType.INTEGER, locale);

		detailConfig = lvb.addComboBox("ArtilceLotDetailsManager_detailConfig_description.title", "detailConfig", resourceBundle);
		targetQty =lvb.addBigDecimalField("ArtilceLotDetailsManager_targetQty_description.title", "targetQty", resourceBundle, NumberType.INTEGER, locale,ViewModel.READ_ONLY);
		targetPrice =lvb.addBigDecimalField("ArtilceLotDetailsManager_targetPrice_description.title", "targetPrice", resourceBundle, NumberType.INTEGER, locale,ViewModel.READ_ONLY);

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lvb.toRows(),ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();

	}


	public void bind(ArticleLotDetailsManager model)
	{
		lotToDetails.valueProperty().bindBidirectional(model.lotToDetailsProperty());
		detailConfig.valueProperty().bindBidirectional(model.detailConfigProperty());
		detailsQty.numberProperty().bindBidirectional(model.detailsQtyProperty());
		lotQty.numberProperty().bindBidirectional(model.lotQtyProperty());
		//		targetQty.numberProperty().bindBidirectional(model.getDetailConfig(). targetQuantityProperty());
		//		targetPrice.numberProperty().bindBidirectional(model.getDetailConfig().salesPriceProperty());
	}

	public void addValidators()
	{
		detailsQty.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLotDetailsManager>(textInputControlValidator, detailsQty, ArticleLotDetailsManager.class, "detailsQty", resourceBundle));
		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<ArticleLotDetailsManager>> validate(ArticleLotDetailsManager model)
	{
		Set<ConstraintViolation<ArticleLotDetailsManager>> violations = new HashSet<ConstraintViolation<ArticleLotDetailsManager>>();
		violations.addAll(textInputControlValidator.validate(detailsQty, ArticleLotDetailsManager.class, "detailsQty", resourceBundle));
		violations.addAll(toOneAggreggationFieldValidator.validate(detailConfig, model.getDetailConfig(), ArticleLotDetailsManager.class, "detailConfig", resourceBundle));
		violations.addAll(toOneAggreggationFieldValidator.validate(lotToDetails, model.getLotToDetails(), ArticleLotDetailsManager.class, "lotToDetails", resourceBundle));
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

	public BigDecimalField getTargetPrice()
	{
		return targetPrice;

	}
	public BigDecimalField getTargetQty()
	{
		return targetQty;

	}

	public ComboBox<ProductDetailConfig> getDetailsConfig()
	{
		return detailConfig;
	}


}
