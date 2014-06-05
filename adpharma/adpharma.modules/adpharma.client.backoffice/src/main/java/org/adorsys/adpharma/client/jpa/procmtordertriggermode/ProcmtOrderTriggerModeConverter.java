package org.adorsys.adpharma.client.jpa.procmtordertriggermode;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class ProcmtOrderTriggerModeConverter extends StringConverter<ProcmtOrderTriggerMode>
{

   @Inject
   @Bundle({ ProcmtOrderTriggerMode.class })
   private ResourceBundle bundle;

   @Override
   public ProcmtOrderTriggerMode fromString(String name)
   {
      return ProcmtOrderTriggerMode.valueOf(name);
   }

   @Override
   public String toString(ProcmtOrderTriggerMode object)
   {
      if (object == null)
         return null;
      return bundle.getString("ProcmtOrderTriggerMode_" + object.name()
            + "_description.title");
   }
}
