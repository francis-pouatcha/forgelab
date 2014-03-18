package org.adorsys.adpharma.server.lm;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.adorsys.adpharma.server.jpa.AccessRoleEnum;

@WebListener
public class DeclarativeRolesContextListener implements ServletContextListener
{

   @Override
   public void contextInitialized(ServletContextEvent sce)
   {
      ServletContext servletContext = sce.getServletContext();
      AccessRoleEnum[] roleEnums = AccessRoleEnum.values();
      for (AccessRoleEnum roleEnum : roleEnums)
      {
         servletContext.declareRoles(roleEnum.name());
      }
   }

   @Override
   public void contextDestroyed(ServletContextEvent sce)
   {
      // TODO Auto-generated method stub

   }

}
