package org.adorsys.adpharma.client.jpa.cashout;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
@Singleton
public class CashOutRegistration extends DomainComponentRegistration
{

	   @Inject
	   @Bundle(CashOut.class)
	   private ResourceBundle resourceBundle;

	   @Override
	   protected Class<?> getComponentClass()
	   {
	      return CashOut.class;
	   }

	   @Override
	   protected Class<? extends DomainComponentController> getControllerClass()
	   {
	      return CashOutController.class;
	   }

	   @Override
	   protected String getComponentI18nName()
	   {
	      Description annotation = CashOut.class.getAnnotation(Description.class);
	      if (annotation != null)
	         return resourceBundle.getString(annotation.value() + ".title");
	      return CashOut.class.getName();
	   }

	   @Override
	   public String getComponentPermission()
	   {
	      return "org.adorsys.adpharma.server.jpa.CashOut";
	   }


}
