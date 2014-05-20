package org.adorsys.adpharma.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.enterprise.inject.Instance;

import org.adorsys.javafx.crud.extensions.MainController;
import org.adorsys.javafx.crud.extensions.address.ServerAddress;
import org.adorsys.javafx.crud.extensions.locale.LocaleFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.exceptions.IllegalStateException;

public final class AdpharmaClient extends Application {

	private Weld weld;

	private static Locale locale;
//	private static ServerAddress serverAddress;

	public static void main(String[] args) {
		// parse locale
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (StringUtils.isBlank(arg))
				continue;

			if (StringUtils.equalsIgnoreCase(arg, "-L")
					|| StringUtils.equalsIgnoreCase(arg, "--locale")) {
				if (args.length > i + 1) {
					try {
						locale = new Locale(args[i + 1]);
						Locale.setDefault(locale);
					} catch (Exception ex) {
						throw new IllegalStateException(ex);
					}
				}
			}

			if (StringUtils.equalsIgnoreCase(arg, "-S")
					|| StringUtils.equalsIgnoreCase(arg, "-server.address")) {
				if (args.length > i + 1) {
					try {
						URL url = new URL(args[i + 1]);
						ServerAddress.serverUrl = url;
					} catch (MalformedURLException e) {
						throw new IllegalStateException(e);
					}
				}
			}
		}
		
		if(locale==null || ServerAddress.serverUrl==null){
			File file = new File("appconfig.properties");
			if(file.exists()){
				Properties properties = new Properties();
				try {
					properties.load(new FileInputStream(file));
					String localeProperty = properties.getProperty("locale");
					String serverAddressProperty = properties.getProperty("server.address");
					if(locale==null){
						locale = new Locale(localeProperty);
						Locale.setDefault(locale);
					}
					if(ServerAddress.serverUrl==null)ServerAddress.serverUrl=new URL(serverAddressProperty);
				} catch (FileNotFoundException e) {
					throw new IllegalStateException(e);
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
		}

		if (locale == null) {
			locale = Locale.getDefault();
		}
		if (locale == null) {
			locale = new Locale("fr");
			Locale.setDefault(locale);
		}
		String propertySa = System.getProperty("server.address");
		if (ServerAddress.serverUrl == null
				&& StringUtils.isNotBlank(propertySa)) {
			try {
				URL url = new URL(propertySa);
				ServerAddress.serverUrl = url;
			} catch (MalformedURLException e) {
				throw new IllegalStateException(e);
			}
		}

		if (ServerAddress.serverUrl == null)
			try {
				URL url = new URL("http://localhost:8080/adpharma.server");
				ServerAddress.serverUrl = url;
			} catch (MalformedURLException e) {
				throw new IllegalStateException(e);
			}

		launch(args);
	}

	@Override
	public void init() {
		weld = new Weld();
	}

	@Override
	public void start(Stage stage) {
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Collection<File> listFiles = FileUtils.listFiles(new File("."), new String[]{"pdf"}, false);
				for (File file : listFiles) {
					if(file.getName().endsWith("pdf"))FileUtils.deleteQuietly(file);
				}
			}
		});
		Instance<Object> instance = weld.initialize().instance();
		instance.select(LocaleFactory.class).get().setLocale(locale);
		instance.select(MainController.class).get()
				.start(stage, locale, "styles/application.css");
	}

	@Override
	public void stop() {
		weld.shutdown();
	}
}
