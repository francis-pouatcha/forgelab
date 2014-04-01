package org.adorsys.adpharma.client;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.adorsys.javafx.crud.extensions.login.ClientCookieFilter;

public class PermsService
{
   private WebTarget target;
   private String media = MediaType.APPLICATION_JSON;

   @Inject
   private ClientCookieFilter clientCookieFilter;

   public PermsService()
   {
      Client client = ClientBuilder.newClient();
      String serverAddress = System.getProperty("server.address");
      if (serverAddress == null)
         throw new IllegalStateException("Set system property server address before calling this service. Like: http://localhost:8080/<ContextRoot>");
      this.target = client.target(serverAddress + "/rest/perms");
   }

   @PostConstruct
   protected void postConstruct()
   {
      this.target.register(clientCookieFilter);
   }

   public String loadDCs(String loginName)
   {
      Entity<String> eCopy = Entity.entity(loginName, media);
      return target.request(media).post(eCopy, String.class);
   }
}
