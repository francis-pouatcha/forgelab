package org.adorsys.adpharma.client.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class NetWorkChecker {

	public static Boolean hasNetwork(){
		Boolean hasNetwork = Boolean.FALSE ;
		try {
			URL url = new URL("http://www.google.com");
			URLConnection connection = url.openConnection();
			connection.connect();
			hasNetwork = Boolean.TRUE;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return hasNetwork;
	}

}
