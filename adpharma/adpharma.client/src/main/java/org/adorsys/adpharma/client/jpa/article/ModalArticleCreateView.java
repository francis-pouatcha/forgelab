package org.adorsys.adpharma.client.jpa.article;

import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchInput;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchService;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfigSearchInput;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfigSearchService;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchInput;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingModeSearchService;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchInput;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamilySearchService;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMarginSearchInput;
import org.adorsys.adpharma.client.jpa.salesmargin.SalesMarginSearchService;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.adpharma.client.jpa.section.SectionSearchService;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.BeforeShowing;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.controlsfx.dialog.Dialogs;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalArticleCreateView extends ApplicationModal{

	@Inject
	private ArticleView articleView;


	private AnchorPane rootPane;

	private Button saveButton;

	private Button resetButton;

	private Button cancelButton;

	@Inject   
	ServiceCallFailedEventHandler callFailedEventHandler ;

	@Inject
	private SectionSearchService sectionSearchService;

	@Inject
	private ProductFamilySearchService familySearchService ;

	@Inject
	private SalesMarginSearchService marginSearchService;

	@Inject
	private PackagingModeSearchService packagingModeSearchService;

	@Inject
	private AgencySearchService agencySearchService;

	@Inject
	private ClearanceConfigSearchService clearanceConfigSearchService ;

	@Inject
	private ArticleView view;

	@Inject
	private Article article;

	@Inject
	@Bundle({ CrudKeys.class, Article.class })
	private ResourceBundle resourceBundle;

	@Override
	public Pane getRootPane() {
		return rootPane ;
	}
	public ArticleView getView() {
		return view;
	}

	@PostConstruct
	public void postConstruct()
	{

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addMainForm(view, ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_reset.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.TRASH_ALT);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(600d);
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);				
			}
		});

		sectionSearchService.setOnFailed(callFailedEventHandler);
		familySearchService.setOnFailed(callFailedEventHandler); 
		marginSearchService.setOnFailed(callFailedEventHandler);
		packagingModeSearchService.setOnFailed(callFailedEventHandler);
		agencySearchService.setOnFailed(callFailedEventHandler);
		clearanceConfigSearchService.setOnFailed(callFailedEventHandler);

	}


	public void bind(Article model)
	{
		this.article = model;
		view.bind(this.article);
		
	}
	public ArticleView getArticleView() {
		return articleView;
	}
	public Button getSaveButton() {
		return saveButton;
	}
	public Button getResetButton() {
		return resetButton;
	}
	public Button getCancelButton() {
		return cancelButton;
	}

	@BeforeShowing
	public void beforShowing(){
		sectionSearchService.setSearchInputs(new SectionSearchInput()).start();
		agencySearchService.setSearchInputs(new AgencySearchInput()).start();
		familySearchService.setSearchInputs(new ProductFamilySearchInput()).start();;
		marginSearchService.setSearchInputs(new SalesMarginSearchInput()).start();
		clearanceConfigSearchService.setSearchInputs(new ClearanceConfigSearchInput()).start();
		packagingModeSearchService.setSearchInputs(new PackagingModeSearchInput()).start();
	}

	public SectionSearchService getSectionSearchService() {
		return sectionSearchService;
	}
	public ServiceCallFailedEventHandler getCallFailedEventHandler() {
		return callFailedEventHandler;
	}
	public ProductFamilySearchService getFamilySearchService() {
		return familySearchService;
	}
	public SalesMarginSearchService getMarginSearchService() {
		return marginSearchService;
	}
	public PackagingModeSearchService getPackagingModeSearchService() {
		return packagingModeSearchService;
	}
	public AgencySearchService getAgencySearchService() {
		return agencySearchService;
	}
	public ClearanceConfigSearchService getClearanceConfigSearchService() {
		return clearanceConfigSearchService;
	}




}
