package org.adorsys.adpharma.client.jpa.loader;

import javafx.scene.control.Label;

public class PGRunner implements Runnable {

	final Label progressLabel;
	public PGRunner(Label progressLabel) {
		this.progressLabel = progressLabel;
	}
	String text = "";
	
	public PGRunner setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	public void run() {
		progressLabel.setText(text);
	}
}
