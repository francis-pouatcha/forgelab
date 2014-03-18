package org.adorsys.adpharma.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("DocumentProcessingState_description")
public enum DocumentProcessingState
{
   @Description("DocumentProcessingState_SUSPENDED_description")
   SUSPENDED, @Description("DocumentProcessingState_ONGOING_description")
   ONGOING, @Description("DocumentProcessingState_RESTORED_description")
   RESTORED, @Description("DocumentProcessingState_CLOSED_description")
   CLOSED
}