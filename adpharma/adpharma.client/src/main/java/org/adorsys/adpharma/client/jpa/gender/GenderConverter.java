package org.adorsys.adpharma.client.jpa.gender;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class GenderConverter extends StringConverter<Gender>
{

   @Inject
   @Bundle({ Gender.class })
   private ResourceBundle bundle;

   @Override
   public Gender fromString(String name)
   {
      return Gender.valueOf(name);
   }

   @Override
   public String toString(Gender object)
   {
      return bundle.getString("Gender_" + object.name()
            + "_description.title");
   }
}
