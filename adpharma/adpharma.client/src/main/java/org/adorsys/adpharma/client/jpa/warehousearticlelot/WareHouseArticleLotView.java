package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class WareHouseArticleLotView extends AbstractForm<WareHouseArticleLot>
{

	 private TextField internalCip;

	   private TextField mainCip;

	   private TextField secondaryCip;

	   private TextField articleName;

	   private BigDecimalField stockQuantity;


	   @Inject
	   private WareHouseArticleLotWareHouseForm wareHouseArticleLotWareHouseForm;
	   @Inject
	   private WareHouseArticleLotWareHouseSelection wareHouseArticleLotWareHouseSelection;
	   
	   @Inject
	   private WareHouseArticleLotArticleLotForm wareHouseArticleLotArticleLotForm;
	   @Inject
	   private WareHouseArticleLotArticleLotSelection wareHouseArticleLotArticleLotSelection;

	   @Inject
	   @Bundle({ CrudKeys.class, WareHouseArticleLot.class })
	   private ResourceBundle resourceBundle;

	   @Inject
	   private Locale locale;

	   @Inject
	   private TextInputControlValidator textInputControlValidator;
	   @Inject
	   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();
	      internalCip = viewBuilder.addTextField("WareHouseArticleLot_internalCip_description.title", "internalCip", resourceBundle);
	      mainCip = viewBuilder.addTextField("WareHouseArticleLot_mainCip_description.title", "mainCip", resourceBundle);
	      secondaryCip = viewBuilder.addTextField("WareHouseArticleLot_secondaryCip_description.title", "secondaryCip", resourceBundle);
	      articleName = viewBuilder.addTextField("WareHouseArticleLot_articleName_description.title", "articleName", resourceBundle);
	      stockQuantity = viewBuilder.addBigDecimalField("WareHouseArticleLot_stockQuantity_description.title", "stockQuantity", resourceBundle, NumberType.INTEGER, locale);
	      viewBuilder.addTitlePane("WareHouseArticleLot_wareHouse_description.title", resourceBundle);
	      viewBuilder.addSubForm("WareHouseArticleLot_wareHouse_description.title", "wareHouse", resourceBundle, wareHouseArticleLotWareHouseForm, ViewModel.READ_ONLY);
	      viewBuilder.addSubForm("WareHouseArticleLot_wareHouse_description.title", "wareHouse", resourceBundle, wareHouseArticleLotWareHouseSelection, ViewModel.READ_WRITE);
	     
	      viewBuilder.addTitlePane("WareHouseArticleLot_articleLot_description.title", resourceBundle);
	      viewBuilder.addSubForm("WareHouseArticleLot_articleLot_description.title", "articleLot", resourceBundle, wareHouseArticleLotArticleLotForm, ViewModel.READ_ONLY);
	      viewBuilder.addSubForm("WareHouseArticleLot_articleLot_description.title", "articleLot", resourceBundle, wareHouseArticleLotArticleLotSelection, ViewModel.READ_WRITE);
	     
	      
	      
	      
	      gridRows = viewBuilder.toRows();
	   }

	   public void addValidators()
	   {
	      internalCip.focusedProperty().addListener(new TextInputControlFoccusChangedListener<WareHouseArticleLot>(textInputControlValidator, internalCip, WareHouseArticleLot.class, "internalCip", resourceBundle));
	      mainCip.focusedProperty().addListener(new TextInputControlFoccusChangedListener<WareHouseArticleLot>(textInputControlValidator, mainCip, WareHouseArticleLot.class, "mainCip", resourceBundle));
	      secondaryCip.focusedProperty().addListener(new TextInputControlFoccusChangedListener<WareHouseArticleLot>(textInputControlValidator, secondaryCip, WareHouseArticleLot.class, "secondaryCip", resourceBundle));
	      articleName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<WareHouseArticleLot>(textInputControlValidator, articleName, WareHouseArticleLot.class, "articleName", resourceBundle));
	      // no active validator
	      // no active validator
	   }

	   public Set<ConstraintViolation<WareHouseArticleLot>> validate(WareHouseArticleLot model)
	   {
	      Set<ConstraintViolation<WareHouseArticleLot>> violations = new HashSet<ConstraintViolation<WareHouseArticleLot>>();
	      violations.addAll(textInputControlValidator.validate(internalCip, WareHouseArticleLot.class, "internalCip", resourceBundle));
	      violations.addAll(textInputControlValidator.validate(mainCip, WareHouseArticleLot.class, "mainCip", resourceBundle));
	      violations.addAll(textInputControlValidator.validate(secondaryCip, WareHouseArticleLot.class, "secondaryCip", resourceBundle));
	      violations.addAll(textInputControlValidator.validate(articleName, WareHouseArticleLot.class, "articleName", resourceBundle));
	      violations.addAll(toOneAggreggationFieldValidator.validate(wareHouseArticleLotWareHouseSelection.getWareHouse(), model.getWareHouse(), WareHouseArticleLot.class, "wareHouse", resourceBundle));
	      violations.addAll(toOneAggreggationFieldValidator.validate(wareHouseArticleLotArticleLotSelection.getArticleLot(), model.getArticleLot(), WareHouseArticleLot.class, "articleLot", resourceBundle));
 
	      return violations;
	   }

	   public void bind(WareHouseArticleLot model)
	   {
	      internalCip.textProperty().bindBidirectional(model.internalCipProperty());
	      mainCip.textProperty().bindBidirectional(model.mainCipProperty());
	      secondaryCip.textProperty().bindBidirectional(model.secondaryCipProperty());
	      articleName.textProperty().bindBidirectional(model.articleNameProperty());
	      stockQuantity.numberProperty().bindBidirectional(model.stockQuantityProperty());
	      wareHouseArticleLotWareHouseForm.bind(model);
	      wareHouseArticleLotWareHouseSelection.bind(model);
	      wareHouseArticleLotArticleLotForm.bind(model);
	      wareHouseArticleLotArticleLotSelection.bind(model);
	   }

	   public TextField getInternalCip()
	   {
	      return internalCip;
	   }

	   public TextField getMainCip()
	   {
	      return mainCip;
	   }

	   public TextField getSecondaryCip()
	   {
	      return secondaryCip;
	   }

	   public TextField getArticleName()
	   {
	      return articleName;
	   }

	   public BigDecimalField getStockQuantity()
	   {
	      return stockQuantity;
	   }

	

	   public WareHouseArticleLotWareHouseForm getWareHouseArticleLotWareHouseForm()
	   {
	      return wareHouseArticleLotWareHouseForm;
	   }

	   public WareHouseArticleLotWareHouseSelection getWareHouseArticleLotWareHouseSelection()
	   {
	      return wareHouseArticleLotWareHouseSelection;
	   }
	   
	   public WareHouseArticleLotArticleLotForm getWareHouseArticleLotArticleLotForm()
	   {
	      return wareHouseArticleLotArticleLotForm;
	   }

	   public WareHouseArticleLotArticleLotSelection getWareHouseArticleLotArticleLotSelection()
	   {
	      return wareHouseArticleLotArticleLotSelection;
	   }
}
