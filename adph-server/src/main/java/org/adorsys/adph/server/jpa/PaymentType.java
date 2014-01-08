package org.adorsys.adph.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("org.adorsys.adph.server.jpa.PaymentType.description")
public enum PaymentType
{
   @Description("org.adorsys.adph.server.jpa.PaymentType.CASH.description")
   CASH, @Description("org.adorsys.adph.server.jpa.PaymentType.CHEQUE.description")
   CHEQUE, @Description("org.adorsys.adph.server.jpa.PaymentType.CARTE_CREDIT.description")
   CARTE_CREDIT, @Description("org.adorsys.adph.server.jpa.PaymentType.BON_AVOIR_CLIENT.description")
   BON_AVOIR_CLIENT
}