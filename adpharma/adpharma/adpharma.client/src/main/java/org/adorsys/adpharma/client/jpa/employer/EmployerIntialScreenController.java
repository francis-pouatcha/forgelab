package org.adorsys.adpharma.client.jpa.employer;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class EmployerIntialScreenController extends InitialScreenController<Employer>
{
   @Override
   public Employer newEntity()
   {
      return new Employer();
   }
}
