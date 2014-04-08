package org.adorsys.adpharma.client.jpa.documenttype;

import org.adorsys.javaext.description.Description;

@Description("DocumentType_description")
public enum DocumentType
{
   @Description("DocumentType_CASH_STMT_description")
   CASH_STMT, @Description("DocumentType_CASH_RECEIPT_description")
   CASH_RECEIPT, @Description("DocumentType_CUSTUMER_INVOICE_description")
   CUSTUMER_INVOICE, @Description("DocumentType_CUSTUMER_VOUCHER_description")
   CUSTUMER_VOUCHER, @Description("DocumentType_DEBT_STMT_description")
   DEBT_STMT, @Description("DocumentType_DELIVERY_SLIP_description")
   DELIVERY_SLIP, @Description("DocumentType_INVENTORY_REPORT_description")
   INVENTORY_REPORT, @Description("DocumentType_PROCUREMENT_ORDER_description")
   PROCUREMENT_ORDER, @Description("DocumentType_SALES_ORDER_description")
   SALES_ORDER, @Description("DocumentType_SALES_REPORT_description")
   SALES_REPORT, @Description("DocumentType_SUPPLIER_INVOICE_description")
   SUPPLIER_INVOICE
}