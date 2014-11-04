package org.adorsys.adpharma.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.jboss.weld.exceptions.IllegalStateException;

public class AppConfigProperties {

	private static Properties properties;

	public AppConfigProperties() {
		if(properties==null)loadProps();
	}
	
	private static void loadProps() {
		File file = new File("appconfig.properties");
		if(file.exists()){
			properties = new Properties();
			try {
				properties.load(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				throw new IllegalStateException(e);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		
	}

	public Properties getProperties() {
		return properties;
	}
	
}
