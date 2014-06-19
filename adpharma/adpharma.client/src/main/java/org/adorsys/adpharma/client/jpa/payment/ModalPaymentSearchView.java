package org.adorsys.adpharma.client.jpa.payment;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

public class ModalPaymentSearchView extends ApplicationModal{

	@FXML
	private VBox rootPane ;

	@FXML
	private TextField paymentNumber;

	@FXML Button cancelButton;
	@FXML Button printButton;

	@FXML
	Pagination pagination;

	@FXML
	TableView<Payment> dataList;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	@Bundle({ CrudKeys.class, Payment.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Inject
	private PaymentModeConverter paymentModeConverter;

	@Override
	public VBox getRootPane() {
		return rootPane;
	}

	@PostConstruct
	public void onPostConstruct(){
		FXMLLoaderUtils.load(fxmlLoader, this,resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addStringColumn(dataList, "paymentNumber", "Payment_paymentNumber_description.title", resourceBundle,200d);
		viewBuilder.addEnumColumn(dataList, "paymentMode", "Payment_paymentMode_description.title", resourceBundle, paymentModeConverter);
		viewBuilder.addStringColumn(dataList, "cashier", "Payment_cashier_description.title", resourceBundle,200d);
		viewBuilder.addBigDecimalColumn(dataList, "amount", "Payment_amount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "receivedAmount", "Payment_receivedAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "difference", "Payment_difference_description.title", resourceBundle, NumberType.CURRENCY, locale);

	}

	public TextField getPaymentNumber() {
		return paymentNumber;
	}

	public Button getPrintButton() {
		return printButton;
	}
	
	public Button getCancelButton() {
		return cancelButton;
	}

	public TableView<Payment> getDataList() {
		return dataList;
	}

	public Pagination getPagination() {
		return pagination;
	}

}
