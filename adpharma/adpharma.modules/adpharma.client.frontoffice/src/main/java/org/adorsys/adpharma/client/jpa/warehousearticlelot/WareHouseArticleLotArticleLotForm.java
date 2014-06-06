package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class WareHouseArticleLotArticleLotForm extends AbstractToOneAssociation<WareHouseArticleLot, ArticleLot>
{

	   private TextField articleName;
	   @Inject
	   @Bundle({ CrudKeys.class, ArticleLot.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();
	      articleName = viewBuilder.addTextField("ArticleLot_articleName_description.title", "articleName", resourceBundle);
	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(WareHouseArticleLot model)
	   {
		   articleName.textProperty().bindBidirectional(model.getArticleLot().articleNameProperty());
	   }

	   public void update(WareHouseArticleLotArticleLot data)
	   {
		   articleName.textProperty().set(data.articleNameProperty().get());
	   }

	   public TextField getName()
	   {
	      return articleName;
	   }
}
