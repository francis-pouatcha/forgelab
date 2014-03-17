package org.adorsys.adpharma.client;

import java.util.Locale;

import javafx.application.Application;
import javafx.stage.Stage;

import org.adorsys.javafx.crud.extensions.MainController;
import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.environment.se.Weld;

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
      }
      if (locale == null)
         locale = Locale.getDefault();
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
