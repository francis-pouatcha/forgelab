package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DebtStatementViewSearchFields extends AbstractForm<DebtStatement>
{

   private TextField statementNumber;

   private CheckBox settled;

   private CheckBox canceled;

   private CheckBox useVoucher;

   @Inject
   @Bundle({ CrudKeys.class, DebtStatement.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      statementNumber = viewBuilder.addTextField("DebtStatement_statementNumber_description.title", "statementNumber", resourceBundle);
      settled = viewBuilder.addCheckBox("DebtStatement_settled_description.title", "settled", resourceBundle);
      canceled = viewBuilder.addCheckBox("DebtStatement_canceled_description.title", "canceled", resourceBundle);
      useVoucher = viewBuilder.addCheckBox("DebtStatement_useVoucher_description.title", "useVoucher", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(DebtStatement model)
   {
      statementNumber.textProperty().bindBidirectional(model.statementNumberProperty());
      settled.textProperty().bindBidirectional(model.settledProperty(), new BooleanStringConverter());
      canceled.textProperty().bindBidirectional(model.canceledProperty(), new BooleanStringConverter());
      useVoucher.textProperty().bindBidirectional(model.useVoucherProperty(), new BooleanStringConverter());

   }

   public TextField getStatementNumber()
   {
      return statementNumber;
   }

   public CheckBox getSettled()
   {
      return settled;
   }

   public CheckBox getCanceled()
   {
      return canceled;
   }

   public CheckBox getUseVoucher()
   {
      return useVoucher;
   }
}
