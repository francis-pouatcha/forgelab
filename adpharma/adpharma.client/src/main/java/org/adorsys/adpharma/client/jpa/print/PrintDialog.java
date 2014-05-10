package org.adorsys.adpharma.client.jpa.print;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.Paper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
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
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDMemoryStream;
import org.apache.pdfbox.util.ImageIOUtil;
import org.jboss.weld.exceptions.IllegalStateException;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class PrintDialog {
	public static double printableWidth = Paper.A4.getWidth() - (40 * 2);
	public static double printableHeight = Paper.A4.getHeight() - (40 * 2);
	public static int printableWidthInt = new Double(printableWidth).intValue();
	public static int printableHeightInt = new Double(printableHeight).intValue();

	private Button printButton;

	private Button closeButton;

	private Button nextButton;

	private Button previousButton;

	private Button sendMailButton;

	private Stage dialog = new Stage();

	private final List<Parent> pages = new ArrayList<Parent>();
	private VBox rootPane;
	private int pageNumber = 1;

	@Inject
	@Bundle({ CrudKeys.class })
	private ResourceBundle resourceBundle;
	
	@Inject
	private SMTPSender smtpSender;

	private String receiver;
	private String sender;
	private String username;
	private String password;
	private String subject;
	private String messageText;
	private String fileName = UUID.randomUUID().toString();
	private File file;
	
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
				PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
				if(printService==null){
					// no printer installed.
					return;
				}
				DocPrintJob printJob = printService.createPrintJob();
				
				if(file!=null || !file.exists()){
					createDocument();
				}
				try {
					FileInputStream fis = new FileInputStream(file);
					Doc doc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.PDF, null);
					printJob.print(doc, null);
				} catch(IOException ioe){
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
				if(file!=null || !file.exists()){
					createDocument();
				}
				smtpSender.sendMail(receiver, sender, username, password, subject, fileName, messageText);
			}
		});
	}

	HBox hBox = new HBox();
	BorderStroke borderStroke = new BorderStroke(Paint.valueOf("black"),
			BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT,
			Insets.EMPTY);
	Border dashedBlackBorder = new Border(borderStroke);

	public void show() {
		if (pageNumber > pages.size())
			pageNumber = 1;
		if (pageNumber <= 0)
			pageNumber = pages.size();
		Parent node = pages.get(pageNumber - 1);
		if (rootPane == null) {
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

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
	private void createDocument(){
		try {
			PDDocument doc = new PDDocument();
			for (Parent page : pages) {
				PDPage p = new PDPage();
				WritableImage wi = new WritableImage(printableWidthInt, printableHeightInt);
				WritableImage snapshot = page.snapshot(null, wi);
				BufferedImage bi = SwingFXUtils.fromFXImage(snapshot, null);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(baos);
				ImageIOUtil.writeImage(bi, ".png", bos);
				p.setContents(new PDMemoryStream(baos.toByteArray()));
				doc.addPage(p);
			}
			file = File.createTempFile(fileName, ".pdf");
			doc.save(file);
			doc.close();
		} catch(IOException ioe){
			throw new IllegalStateException(ioe);
		} catch (COSVisitorException e) {
			throw new IllegalStateException(e);
		}
	}
}
