package org.adorsys.adpharma.client.jpa.articlelot;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
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

	private Button saveButton ;
	private Button cancelButton ;

	@Inject
	@Bundle({ CrudKeys.class, ArticleLot.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;


	@PostConstruct
	public void postConstruct(){
		LazyViewBuilder lvb = new LazyViewBuilder();
		lotToDetails = lvb.addComboBox("ArtilceLotDetailsManager_lotToDetails_description.title", "lotToDetails", resourceBundle);
		lotQty =lvb.addBigDecimalField("ArtilceLotDetailsManager_lotQty_description.title", "lotQty", resourceBundle, NumberType.INTEGER, locale,ViewModel.READ_ONLY);
		detailConfig = lvb.addComboBox("ArtilceLotDetailsManager_detailConfig_description.title", "detailConfig", resourceBundle);
		detailsQty =lvb.addBigDecimalField("ArtilceLotDetailsManager_detailsQty_description.title", "detailsQty", resourceBundle, NumberType.INTEGER, locale);

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
	   
	   public ComboBox<ProductDetailConfig> getDetailsConfig()
	   {
	      return detailConfig;
	   }


}
