package org.adorsys.adpharma.client.jpa.salesorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.exceptions.IllegalStateException;

public class SalesModeProperties {
	
	
private String salesModeStrategy = "CODE";


public static SalesModeProperties loadSalesModeProperties() {
	SalesModeProperties s = new SalesModeProperties();
	File file = new File("appconfig.properties");
	if(file.exists()){
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
			String mode = properties.getProperty("sales_mode_strategy");
			if(StringUtils.isNotBlank(mode)) {
				s.setSalesModeStrategy(mode);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	return s;
}


public String getSalesModeStrategy() {
	return salesModeStrategy;
}


public void setSalesModeStrategy(String salesModeStrategy) {
	this.salesModeStrategy = salesModeStrategy;
}








}
