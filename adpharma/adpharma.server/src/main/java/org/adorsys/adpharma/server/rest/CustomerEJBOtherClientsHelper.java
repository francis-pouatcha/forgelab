package org.adorsys.adpharma.server.rest;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Properties;

import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.Customer;
import org.adorsys.adpharma.server.jpa.CustomerSearchInput;
import org.adorsys.adpharma.server.jpa.CustomerType;
import org.adorsys.adpharma.server.jpa.Customer_;
import org.adorsys.adpharma.server.jpa.Gender;
import org.apache.commons.lang3.time.DateUtils;

public class CustomerEJBOtherClientsHelper {
	
	public static Customer searchInput;
	public static String customerSerial;
	public static SingularAttribute[] attributes;
	
	static{
		Properties properties = new Properties();
		InputStream propStream = CustomerEJBOtherClientsHelper.class.getResourceAsStream(CustomerEJBOtherClientsHelper.class.getSimpleName()+".properties");
		System.out.println(CustomerEJBOtherClientsHelper.class.getSimpleName()+".properties");
		if(propStream==null) throw new IllegalStateException("Missing other customers property file");
		try {
			properties.load(propStream);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
		attributes = new SingularAttribute[]{Customer_.serialNumber};
		customerSerial = properties.getProperty("serialNumber");
		searchInput = new Customer();
		searchInput.setSerialNumber(customerSerial);
	}
}
