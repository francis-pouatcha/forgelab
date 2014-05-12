package org.adorsys.adpharma.client.jpa.print;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.Paper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

import jfxtras.scene.layout.HBox;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.print.SMTPSender;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.jboss.weld.exceptions.IllegalStateException;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class PrintDialogPDF {
	public static double printableWidth = Paper.A4.getWidth() - (40 * 2);
	public static double printableHeight = Paper.A4.getHeight() - (40 * 2);
	public static int printableWidthInt = new Double(printableWidth).intValue();
	public static int printableHeightInt = new Double(printableHeight)
			.intValue();

	private Button printButton;

	private Button closeButton;

	private Button nextButton;

	private Button previousButton;

	private Button sendMailButton;

	private Stage dialog = new Stage();

//	private final List<String> pages = new ArrayList<String>();
	private String fileName;
	private Map<Integer, Image> images = new HashMap<Integer, Image>();
	private PDDocument pdDocument;

	private int pageNumber = 0;

	@Inject
	@Bundle({ CrudKeys.class })
	private ResourceBundle resourceBundle;

	@Inject
	private SMTPSender smtpSender;

	private VBox rootPane;
	private ImageView imageView=new ImageView();
	
	private String receiver;
	private String sender;
	private String username;
	private String password;
	private String subject;
	private String messageText;

	@PostConstruct
	public void postConstruct() {
		ViewBuilder viewBuilder = new ViewBuilder();
		printButton = viewBuilder.addButton(null, "Entity_print.title",
				"printButton", resourceBundle, AwesomeIcon.PRINT);
		closeButton = viewBuilder.addButton(null, "Window_close.title",
				"closeButton", resourceBundle, AwesomeIcon.STOP);
		nextButton = viewBuilder.addButton(null, "Entity_next.title",
				"nextButton", resourceBundle, AwesomeIcon.FORWARD);
		previousButton = viewBuilder.addButton(null, "Entity_previous.title",
				"previousButton", resourceBundle, AwesomeIcon.BACKWARD);
		sendMailButton = viewBuilder.addButton(null, "Entity_email.title",
				"sendMailButton", resourceBundle, AwesomeIcon.MAIL_FORWARD);

		dialog.initModality(Modality.APPLICATION_MODAL);

		printButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PrintService printService = PrintServiceLookup
						.lookupDefaultPrintService();
				if (printService == null) {
					throw new IllegalStateException("No print service available.");
				}
				DocPrintJob printJob = printService.createPrintJob();

				try {
					FileInputStream fis = new FileInputStream(fileName);
					Doc doc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.PDF,
							null);
					printJob.print(doc, null);
				} catch (IOException ioe) {
					throw new IllegalStateException(ioe);
				} catch (PrintException e) {
					throw new IllegalStateException(e);
				}
			}
		});

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});

		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pageNumber += 1;
				show();
			}
		});
		previousButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				pageNumber -= 1;
				show();

			}
		});

		sendMailButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				smtpSender.sendMail(receiver, sender, username, password,
						subject, fileName, messageText);
			}
		});

		rootPane = new VBox();
		HBox hBox = new HBox();
		Scene scene = new Scene(rootPane);
		hBox = new HBox();
		hBox.add(printButton);
		hBox.add(previousButton);
		hBox.add(nextButton);
		hBox.add(closeButton);
		javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane();
		scrollPane.setContent(imageView);
		scrollPane.setPrefHeight(Paper.A4.getHeight());
		rootPane.getChildren().add(scrollPane);
		VBox.setVgrow(scrollPane, Priority.ALWAYS);
		hBox.setPrefHeight(50);
		hBox.setPrefWidth(Paper.A4.getWidth()+20);
		rootPane.getChildren().add(hBox);
		dialog.setScene(scene);
	}

	public void show() {
		createImage();
		
		Image image = images.get(pageNumber);
//		String s = pages.get(pageNumber);
//		FileInputStream fis;
//		try {
//			fis = new FileInputStream(s);
//		} catch (FileNotFoundException e) {
//			throw new IllegalStateException(e);
//		}
//		Image image = new Image(fis);
//		IOUtils.closeQuietly(fis);
		imageView.setImage(image);
		dialog.setTitle("Page: " + pageNumber+1);
		if(!dialog.isShowing())
			dialog.show();
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

//	public List<String> getPages() {
//		return pages;
//	}
//
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private void createImage() {
				
		if(pdDocument==null){
			try {
				pdDocument = PDDocument.load(fileName);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}

		if(pageNumber<0) pageNumber=pdDocument.getNumberOfPages()-1;
		if(pageNumber>=pdDocument.getNumberOfPages())pageNumber=0;
		
		if(images.containsKey(pageNumber)) return;

		List<PDPage> allPages = pdDocument.getDocumentCatalog().getAllPages();
		PDPage page = allPages.get(pageNumber);
		BufferedImage img;
		try {
			img = page.convertToImage(BufferedImage.TYPE_BYTE_BINARY, 72);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		WritableImage fxImage = SwingFXUtils.toFXImage(img, null);
		images.put(pageNumber, fxImage);
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
