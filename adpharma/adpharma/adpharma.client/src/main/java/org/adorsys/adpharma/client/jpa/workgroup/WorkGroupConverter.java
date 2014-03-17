package org.adorsys.adpharma.client.jpa.workgroup;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class WorkGroupConverter extends StringConverter<WorkGroup>
{

   @Inject
   @Bundle({ WorkGroup.class })
   private ResourceBundle bundle;

   @Override
   public WorkGroup fromString(String name)
   {
      return WorkGroup.valueOf(name);
   }

   @Override
   public String toString(WorkGroup object)
   {
      return bundle.getString("WorkGroup_" + object.name()
            + "_description.title");
   }
}
