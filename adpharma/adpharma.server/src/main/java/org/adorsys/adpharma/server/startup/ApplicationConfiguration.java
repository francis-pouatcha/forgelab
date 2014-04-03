package org.adorsys.adpharma.server.startup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Startup
public class ApplicationConfiguration {

	private	Properties configuration ;

	@PostConstruct
	public void  postConstruct(){

		Properties configuration = new Properties();
		InputStream configurationStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
		if (configurationStream != null)
		{
			try
			{
				configuration.load(configurationStream);

			}
			catch (IOException e)
			{
				throw new IllegalStateException(e);
			}
		}

	}

	public Properties getConfiguration() {
		return configuration;
	}


}
