package org.adorsys.adpharma.client.jpa.paymentmode;

import org.adorsys.javaext.description.Description;

@Description("PaymentMode_description")
public enum PaymentMode
{
   @Description("PaymentMode_CASH_description")
   CASH, 
   @Description("PaymentMode_VOUCHER_description")
   VOUCHER, 
   @Description("PaymentMode_COMP_VOUCHER_description")
   COMP_VOUCHER,
   @Description("PaymentMode_CHECK_description")
   CHECK, 
   @Description("PaymentMode_CREDIT_CARD_description")
   CREDIT_CARD, 
}