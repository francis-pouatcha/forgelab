package org.adorsys.adpharma.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import javafx.application.Application;
import javafx.stage.Stage;

import org.adorsys.javafx.crud.extensions.MainController;
import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.exceptions.IllegalStateException;

public final class AdpharmaClient extends Application
{

   private Weld weld;

   private static Locale locale;

   public static void main(String[] args)
   {
      // parse locale
      for (int i = 0; i < args.length; i++)
      {
         String arg = args[i];
         if (StringUtils.isBlank(arg))
            continue;

         if (StringUtils.equalsIgnoreCase(arg, "-L") || StringUtils.equalsIgnoreCase(arg, "--locale"))
         {
            if (args.length > i + 1)
            {
               try
               {
                  locale = new Locale(args[i + 1]);
                  Locale.setDefault(locale);
               }
               catch (Exception ex)
               {
                  locale = Locale.getDefault();
               }
            }
         }
         
         if(StringUtils.equalsIgnoreCase(arg, "server.address") || StringUtils.equals(arg, "s")){
             if (args.length > i + 1){
            	 System.setProperty("server.address", args[i + 1]);
             }
         }
      }
      if (locale == null)
         locale = Locale.getDefault();
      
      
      if(System.getProperty("server.address")==null){
    	  Properties properties = new Properties();
    	  InputStream resourceAsStream = AdpharmaClient.class.getResourceAsStream("/server-address.properties");
    	  if(resourceAsStream!=null){
			try {
				properties.load(resourceAsStream);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
			String sa = properties.getProperty("server.address");
			if(StringUtils.isNotBlank(sa))
           	 System.setProperty("server.address", sa);
		}
    	  
      }
      
      launch(args);
   }

   @Override
   public void init()
   {
      weld = new Weld();
   }

   @Override
   public void start(Stage stage)
   {
      weld.initialize().instance().select(MainController.class).get()
            .start(stage, locale, "styles/application.css");
   }

   @Override
   public void stop()
   {
      weld.shutdown();
   }
}
