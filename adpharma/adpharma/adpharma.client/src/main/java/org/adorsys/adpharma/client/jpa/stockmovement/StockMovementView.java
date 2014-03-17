package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementType;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminal;
import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.scene.control.ComboBox;
import org.adorsys.javafx.crud.extensions.validation.ComboBoxValidator;
import org.adorsys.javafx.crud.extensions.validation.ComboBoxFoccusChangedListener;
import javafx.scene.control.TextField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.stockmovement.StockMovement;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementTypeConverter;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementTypeListCellFatory;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminalConverter;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminalListCellFatory;

public class StockMovementView extends AbstractForm<StockMovement>
{

   private TextField originatedDocNumber;

   private ComboBox<StockMovementType> movementType;

   private ComboBox<StockMovementTerminal> movementOrigin;

   private ComboBox<StockMovementTerminal> movementDestination;

   private BigDecimalField movedQty;

   private BigDecimalField initialQty;

   private BigDecimalField finalQty;

   private BigDecimalField totalPurchasingPrice;

   private BigDecimalField totalDiscount;

   private BigDecimalField totalSalesPrice;

   private CalendarTextField creationDate;

   @Inject
   private StockMovementCreatingUserForm stockMovementCreatingUserForm;
   @Inject
   private StockMovementCreatingUserSelection stockMovementCreatingUserSelection;

   @Inject
   private StockMovementArticleForm stockMovementArticleForm;
   @Inject
   private StockMovementArticleSelection stockMovementArticleSelection;

   @Inject
   private StockMovementAgencyForm stockMovementAgencyForm;
   @Inject
   private StockMovementAgencySelection stockMovementAgencySelection;

   @Inject
   @Bundle({ CrudKeys.class, StockMovement.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(StockMovementType.class)
   private ResourceBundle movementTypeBundle;

   @Inject
   private StockMovementTypeConverter movementTypeConverter;

   @Inject
   private StockMovementTypeListCellFatory movementTypeListCellFatory;
   @Inject
   @Bundle(StockMovementTerminal.class)
   private ResourceBundle movementOriginBundle;

   @Inject
   private StockMovementTerminalConverter movementOriginConverter;

   @Inject
   private StockMovementTerminalListCellFatory movementOriginListCellFatory;
   @Inject
   @Bundle(StockMovementTerminal.class)
   private ResourceBundle movementDestinationBundle;

   @Inject
   private StockMovementTerminalConverter movementDestinationConverter;

   @Inject
   private StockMovementTerminalListCellFatory movementDestinationListCellFatory;

   @Inject
   private Locale locale;

   @Inject
   private BigDecimalFieldValidator bigDecimalFieldValidator;
   @Inject
   private ComboBoxValidator comboBoxValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      originatedDocNumber = viewBuilder.addTextField("StockMovement_originatedDocNumber_description.title", "originatedDocNumber", resourceBundle);
      movementType = viewBuilder.addComboBox("StockMovement_movementType_description.title", "movementType", resourceBundle, StockMovementType.values());
      movementOrigin = viewBuilder.addComboBox("StockMovement_movementOrigin_description.title", "movementOrigin", resourceBundle, StockMovementTerminal.values());
      movementDestination = viewBuilder.addComboBox("StockMovement_movementDestination_description.title", "movementDestination", resourceBundle, StockMovementTerminal.values());
      movedQty = viewBuilder.addBigDecimalField("StockMovement_movedQty_description.title", "movedQty", resourceBundle, NumberType.INTEGER, locale);
      initialQty = viewBuilder.addBigDecimalField("StockMovement_initialQty_description.title", "initialQty", resourceBundle, NumberType.INTEGER, locale);
      finalQty = viewBuilder.addBigDecimalField("StockMovement_finalQty_description.title", "finalQty", resourceBundle, NumberType.INTEGER, locale);
      totalPurchasingPrice = viewBuilder.addBigDecimalField("StockMovement_totalPurchasingPrice_description.title", "totalPurchasingPrice", resourceBundle, NumberType.CURRENCY, locale);
      totalDiscount = viewBuilder.addBigDecimalField("StockMovement_totalDiscount_description.title", "totalDiscount", resourceBundle, NumberType.CURRENCY, locale);
      totalSalesPrice = viewBuilder.addBigDecimalField("StockMovement_totalSalesPrice_description.title", "totalSalesPrice", resourceBundle, NumberType.CURRENCY, locale);
      creationDate = viewBuilder.addCalendarTextField("StockMovement_creationDate_description.title", "creationDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("StockMovement_creatingUser_description.title", resourceBundle);
      viewBuilder.addSubForm("StockMovement_creatingUser_description.title", "creatingUser", resourceBundle, stockMovementCreatingUserForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("StockMovement_creatingUser_description.title", "creatingUser", resourceBundle, stockMovementCreatingUserSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("StockMovement_article_description.title", resourceBundle);
      viewBuilder.addSubForm("StockMovement_article_description.title", "article", resourceBundle, stockMovementArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("StockMovement_article_description.title", "article", resourceBundle, stockMovementArticleSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("StockMovement_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("StockMovement_agency_description.title", "agency", resourceBundle, stockMovementAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("StockMovement_agency_description.title", "agency", resourceBundle, stockMovementAgencySelection, ViewModel.READ_WRITE);

      ComboBoxInitializer.initialize(movementType, movementTypeConverter, movementTypeListCellFatory, movementTypeBundle);
      ComboBoxInitializer.initialize(movementOrigin, movementOriginConverter, movementOriginListCellFatory, movementOriginBundle);
      ComboBoxInitializer.initialize(movementDestination, movementDestinationConverter, movementDestinationListCellFatory, movementDestinationBundle);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      movementType.valueProperty().addListener(new ComboBoxFoccusChangedListener<StockMovement, StockMovementType>(comboBoxValidator, movementType, StockMovement.class, "movementType", resourceBundle));
      totalPurchasingPrice.numberProperty().addListener(new BigDecimalFieldFoccusChangedListener<StockMovement>(bigDecimalFieldValidator, totalPurchasingPrice, StockMovement.class, "totalPurchasingPrice", resourceBundle));
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<StockMovement>> validate(StockMovement model)
   {
      Set<ConstraintViolation<StockMovement>> violations = new HashSet<ConstraintViolation<StockMovement>>();
      violations.addAll(comboBoxValidator.validate(movementType, StockMovement.class, "movementType", resourceBundle));
      violations.addAll(bigDecimalFieldValidator.validate(totalPurchasingPrice, StockMovement.class, "totalPurchasingPrice", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(stockMovementCreatingUserSelection.getCreatingUser(), model.getCreatingUser(), StockMovement.class, "creatingUser", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(stockMovementArticleSelection.getArticle(), model.getArticle(), StockMovement.class, "article", resourceBundle));
      return violations;
   }

   public void bind(StockMovement model)
   {
      originatedDocNumber.textProperty().bindBidirectional(model.originatedDocNumberProperty());
      movementType.valueProperty().bindBidirectional(model.movementTypeProperty());
      movementOrigin.valueProperty().bindBidirectional(model.movementOriginProperty());
      movementDestination.valueProperty().bindBidirectional(model.movementDestinationProperty());
      movedQty.numberProperty().bindBidirectional(model.movedQtyProperty());
      initialQty.numberProperty().bindBidirectional(model.initialQtyProperty());
      finalQty.numberProperty().bindBidirectional(model.finalQtyProperty());
      totalPurchasingPrice.numberProperty().bindBidirectional(model.totalPurchasingPriceProperty());
      totalDiscount.numberProperty().bindBidirectional(model.totalDiscountProperty());
      totalSalesPrice.numberProperty().bindBidirectional(model.totalSalesPriceProperty());
      creationDate.calendarProperty().bindBidirectional(model.creationDateProperty());
      stockMovementCreatingUserForm.bind(model);
      stockMovementCreatingUserSelection.bind(model);
      stockMovementArticleForm.bind(model);
      stockMovementArticleSelection.bind(model);
      stockMovementAgencyForm.bind(model);
      stockMovementAgencySelection.bind(model);
   }

   public TextField getOriginatedDocNumber()
   {
      return originatedDocNumber;
   }

   public ComboBox<StockMovementType> getMovementType()
   {
      return movementType;
   }

   public ComboBox<StockMovementTerminal> getMovementOrigin()
   {
      return movementOrigin;
   }

   public ComboBox<StockMovementTerminal> getMovementDestination()
   {
      return movementDestination;
   }

   public BigDecimalField getMovedQty()
   {
      return movedQty;
   }

   public BigDecimalField getInitialQty()
   {
      return initialQty;
   }

   public BigDecimalField getFinalQty()
   {
      return finalQty;
   }

   public BigDecimalField getTotalPurchasingPrice()
   {
      return totalPurchasingPrice;
   }

   public BigDecimalField getTotalDiscount()
   {
      return totalDiscount;
   }

   public BigDecimalField getTotalSalesPrice()
   {
      return totalSalesPrice;
   }

   public CalendarTextField getCreationDate()
   {
      return creationDate;
   }

   public StockMovementCreatingUserForm getStockMovementCreatingUserForm()
   {
      return stockMovementCreatingUserForm;
   }

   public StockMovementCreatingUserSelection getStockMovementCreatingUserSelection()
   {
      return stockMovementCreatingUserSelection;
   }

   public StockMovementArticleForm getStockMovementArticleForm()
   {
      return stockMovementArticleForm;
   }

   public StockMovementArticleSelection getStockMovementArticleSelection()
   {
      return stockMovementArticleSelection;
   }

   public StockMovementAgencyForm getStockMovementAgencyForm()
   {
      return stockMovementAgencyForm;
   }

   public StockMovementAgencySelection getStockMovementAgencySelection()
   {
      return stockMovementAgencySelection;
   }
}
