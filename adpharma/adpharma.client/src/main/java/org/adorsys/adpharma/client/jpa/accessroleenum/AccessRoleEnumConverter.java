package org.adorsys.adpharma.client.jpa.accessroleenum;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class AccessRoleEnumConverter extends StringConverter<AccessRoleEnum>
{

   @Inject
   @Bundle({ AccessRoleEnum.class })
   private ResourceBundle bundle;

   @Override
   public AccessRoleEnum fromString(String name)
   {
      return AccessRoleEnum.valueOf(name);
   }

   @Override
   public String toString(AccessRoleEnum object)
   {
      if (object == null)
         return null;
      return bundle.getString("AccessRoleEnum_" + object.name()
            + "_description.title");
   }
}
