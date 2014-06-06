package org.adorsys.adpharma.client.jpa.section;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class SectionIntialScreenController extends InitialScreenController<Section>
{
   @Override
   public Section newEntity()
   {
      return new Section();
   }
}
