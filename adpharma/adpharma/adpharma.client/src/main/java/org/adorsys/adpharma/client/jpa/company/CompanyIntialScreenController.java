package org.adorsys.adpharma.client.jpa.company;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class CompanyIntialScreenController extends InitialScreenController<Company>
{
   @Override
   public Company newEntity()
   {
      return new Company();
   }
}
