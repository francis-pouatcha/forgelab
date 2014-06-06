package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.warehouse.WareHouse;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class WareHouseArticleLotWareHouseForm extends AbstractToOneAssociation<WareHouseArticleLot, WareHouse>
{

	   private TextField name;
	   @Inject
	   @Bundle({ CrudKeys.class, WareHouse.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();
	      name = viewBuilder.addTextField("WareHouse_name_description.title", "name", resourceBundle);
	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(WareHouseArticleLot model)
	   {
	      name.textProperty().bindBidirectional(model.getWareHouse().nameProperty());
	   }

	   public void update(WareHouseArticleLotWareHouse data)
	   {
	      name.textProperty().set(data.nameProperty().get());
	   }

	   public TextField getName()
	   {
	      return name;
	   }
}
