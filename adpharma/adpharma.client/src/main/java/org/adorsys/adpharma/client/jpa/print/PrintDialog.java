package org.adorsys.adpharma.client.jpa.print;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import jfxtras.scene.layout.HBox;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class PrintDialog {

	   private Button printButton;

	   private Button closeButton;
	   
	   private Button nextButton;

	   private Button previousButton;
	   
	   private Stage dialog = new Stage();
	   
	   private final List<Parent> pages = new ArrayList<Parent>();
	   private VBox rootPane;
	   private int pageNumber = 1;

	   @Inject
	   @Bundle({ CrudKeys.class})
	   private ResourceBundle resourceBundle;
	   
	   @PostConstruct
	   public void postConstruct()
	   {
			ViewBuilder viewBuilder = new ViewBuilder();
			printButton = viewBuilder.addButton(null, "Entity_print.title", "printButton", resourceBundle, AwesomeIcon.PRINT);
			closeButton = viewBuilder.addButton(null, "Window_close.title", "closeButton", resourceBundle, AwesomeIcon.STOP);
			nextButton = viewBuilder.addButton(null, "Entity_next.title", "nextButton", resourceBundle, AwesomeIcon.FORWARD);
			previousButton = viewBuilder.addButton(null, "Entity_previous.title", "previousButton", resourceBundle, AwesomeIcon.BACKWARD);

			dialog.initModality(Modality.APPLICATION_MODAL);
			
			printButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
	    			PrinterJob job = PrinterJob.createPrinterJob();
	    			job.showPrintDialog(dialog);
	    			if (job != null) {
	    				boolean success =  false;
	    				for (Parent page : pages) {
	    					success = job.printPage(page);
	    					if(!success) break;
	    				}
	    				if (success) {
	    					job.endJob();
	    				}
	    			}
				}
			});
			
			closeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					dialog.close();
				}
			});
			
			nextButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					pageNumber+=1;
					show();
				}
			});
			previousButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					pageNumber-=1;
					show();
					
				}
			});
			
	   }

	HBox hBox = new HBox();
	BorderStroke borderStroke = new BorderStroke(Paint.valueOf("black"), BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT, Insets.EMPTY);
	Border dashedBlackBorder = new Border(borderStroke);
	
	public void show() {
		if(pageNumber>pages.size())pageNumber=1;
		if(pageNumber<=0)pageNumber=pages.size();
		Parent node = pages.get(pageNumber-1);
		if(rootPane==null){
			rootPane = new VBox();
			rootPane.setBorder(dashedBlackBorder);
			
			Scene scene = new Scene(rootPane);
			hBox = new HBox();
			hBox.add(printButton);
			hBox.add(previousButton);
			hBox.add(nextButton);
			hBox.add(closeButton);
			
			dialog.setScene(scene);
			dialog.setTitle("Page: " + pageNumber);
			rootPane.getChildren().clear();
			rootPane.getChildren().add(node);
			rootPane.getChildren().add(hBox);
			dialog.show();
		} else {
			dialog.setTitle("Page: " + pageNumber);
			rootPane.getChildren().clear();
			rootPane.getChildren().add(node);
			rootPane.getChildren().add(hBox);
		}
	}

	public Button getPrintButton() {
		return printButton;
	}

	public Button getCloseButton() {
		return closeButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public Button getPreviousButton() {
		return previousButton;
	}

	public List<Parent> getPages() {
		return pages;
	}
	
}
