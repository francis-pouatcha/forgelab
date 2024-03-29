package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class ProductDetailConfigTargetForm extends AbstractToOneAssociation<ProductDetailConfig, Article>
{

	private TextField articleName;

	private TextField pic;

	private TextField manufacturer;

	private CheckBox active;

	private CheckBox authorizedSale;

	private BigDecimalField qtyInStock;

	private BigDecimalField sppu;

	@Inject
	@Bundle({ CrudKeys.class, Article.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		articleName = viewBuilder.addTextField("Article_articleName_description.title", "articleName", resourceBundle);
		//      pic = viewBuilder.addTextField("Article_pic_description.title", "pic", resourceBundle);
		//      manufacturer = viewBuilder.addTextField("Article_manufacturer_description.title", "manufacturer", resourceBundle);
		//      active = viewBuilder.addCheckBox("Article_active_description.title", "active", resourceBundle);
		//      authorizedSale = viewBuilder.addCheckBox("Article_authorizedSale_description.title", "authorizedSale", resourceBundle);
		qtyInStock = viewBuilder.addBigDecimalField("Article_qtyInStock_description.title", "qtyInStock", resourceBundle, NumberType.INTEGER, locale);
		sppu = viewBuilder.addBigDecimalField("Article_sppu_description.title", "sppu", resourceBundle, NumberType.INTEGER, locale);

		gridRows = viewBuilder.toRows();
	}

	public void bind(ProductDetailConfig model)
	{
		articleName.textProperty().bindBidirectional(model.getTarget().articleNameProperty());
		//      pic.textProperty().bindBidirectional(model.getTarget().picProperty());
		//      manufacturer.textProperty().bindBidirectional(model.getTarget().manufacturerProperty());
		//      active.textProperty().bindBidirectional(model.getTarget().activeProperty(), new BooleanStringConverter());
		//      authorizedSale.textProperty().bindBidirectional(model.getTarget().authorizedSaleProperty(), new BooleanStringConverter());
		qtyInStock.numberProperty().bindBidirectional(model.getTarget().qtyInStockProperty());
		sppu.numberProperty().bindBidirectional(model.getTarget().sppuProperty());
	}

	public void update(ProductDetailConfigTarget data)
	{
		articleName.textProperty().set(data.articleNameProperty().get());
		//      pic.textProperty().set(data.picProperty().get());
		//      manufacturer.textProperty().set(data.manufacturerProperty().get());
		//      active.textProperty().set(new BooleanStringConverter().toString(data.activeProperty().get()));
		//      authorizedSale.textProperty().set(new BooleanStringConverter().toString(data.authorizedSaleProperty().get()));
		qtyInStock.numberProperty().set(data.qtyInStockProperty().get());
		sppu.numberProperty().set(data.sppuProperty().get());
	}

	public TextField getArticleName()
	{
		return articleName;
	}

//	public TextField getPic()
//	{
//		return pic;
//	}
//
//	public TextField getManufacturer()
//	{
//		return manufacturer;
//	}
//
//	public CheckBox getActive()
//	{
//		return active;
//	}
//
//	public CheckBox getAuthorizedSale()
//	{
//		return authorizedSale;
//	}

	public BigDecimalField getQtyInStock()
	{
		return qtyInStock;
	}

	public BigDecimalField getSppu()
	{
		return sppu;
	}
}
