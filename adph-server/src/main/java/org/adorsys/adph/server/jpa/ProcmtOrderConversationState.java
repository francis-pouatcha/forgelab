package org.adorsys.adph.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("org.adorsys.adph.server.jpa.ProcmtOrderConversationState.description")
public enum ProcmtOrderConversationState
{
   @Description("org.adorsys.adph.server.jpa.ProcmtOrderConversationState.SUBMIT.description")
   SUBMIT, @Description("org.adorsys.adph.server.jpa.ProcmtOrderConversationState.VALIDATE.description")
   VALIDATE, @Description("org.adorsys.adph.server.jpa.ProcmtOrderConversationState.CANCELLED.description")
   CANCELLED, @Description("org.adorsys.adph.server.jpa.ProcmtOrderConversationState.PROVIDER.description")
   PROVIDER, @Description("org.adorsys.adph.server.jpa.ProcmtOrderConversationState.PROCESSING.description")
   PROCESSING, @Description("org.adorsys.adph.server.jpa.ProcmtOrderConversationState.DRUGSTORE_PROCESSING.description")
   DRUGSTORE_PROCESSING
}