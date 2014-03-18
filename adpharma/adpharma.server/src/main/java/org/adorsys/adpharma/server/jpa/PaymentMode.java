package org.adorsys.adpharma.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("PaymentMode_description")
public enum PaymentMode
{
   @Description("PaymentMode_CASH_description")
   CASH, @Description("PaymentMode_CHECK_description")
   CHECK, @Description("PaymentMode_CREDIT_CARD_description")
   CREDIT_CARD, @Description("PaymentMode_VOUCHER_description")
   VOUCHER
}