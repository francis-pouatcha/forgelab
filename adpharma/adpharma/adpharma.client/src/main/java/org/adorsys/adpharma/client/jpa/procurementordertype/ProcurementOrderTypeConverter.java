package org.adorsys.adpharma.client.jpa.procurementordertype;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class ProcurementOrderTypeConverter extends StringConverter<ProcurementOrderType>
{

   @Inject
   @Bundle({ ProcurementOrderType.class })
   private ResourceBundle bundle;

   @Override
   public ProcurementOrderType fromString(String name)
   {
      return ProcurementOrderType.valueOf(name);
   }

   @Override
   public String toString(ProcurementOrderType object)
   {
      return bundle.getString("ProcurementOrderType_" + object.name()
            + "_description.title");
   }
}
