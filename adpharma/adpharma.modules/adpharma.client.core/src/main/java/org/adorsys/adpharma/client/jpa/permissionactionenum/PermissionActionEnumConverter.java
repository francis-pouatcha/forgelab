package org.adorsys.adpharma.client.jpa.permissionactionenum;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class PermissionActionEnumConverter extends StringConverter<PermissionActionEnum>
{

   @Inject
   @Bundle({ PermissionActionEnum.class })
   private ResourceBundle bundle;

   @Override
   public PermissionActionEnum fromString(String name)
   {
      return PermissionActionEnum.valueOf(name);
   }

   @Override
   public String toString(PermissionActionEnum object)
   {
      if (object == null)
         return null;
      return bundle.getString("PermissionActionEnum_" + object.name()
            + "_description.title");
   }
}
