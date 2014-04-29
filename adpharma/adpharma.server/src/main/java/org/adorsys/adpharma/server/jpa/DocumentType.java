package org.adorsys.adpharma.server.jpa;

import org.adorsys.javaext.description.Description;

@Description("DocumentType_description")
public enum DocumentType
{
   @Description("DocumentType_CUSTUMER_INVOICE_description")
   CUSTUMER_INVOICE, @Description("DocumentType_SUPPLIER_INVOICE_description")
   SUPPLIER_INVOICE, @Description("DocumentType_DELIVERY_SLIP_description")
   DELIVERY_SLIP, @Description("DocumentType_CASH_RECEIPT_description")
   CASH_RECEIPT, @Description("DocumentType_CUSTUMER_VOUCHER_description")
   CUSTUMER_VOUCHER, @Description("DocumentType_BARCODE_REPORT_description")
   BARCODE_REPORT, @Description("DocumentType_ARTICLE_PCMT_REPORT_description")
   ARTICLE_PCMT_REPORT, @Description("DocumentType_ARTICLE_SALES_REPORT_description")
   ARTICLE_SALES_REPORT, @Description("DocumentType_SECTION_SALES_REPORT_description")
   SECTION_SALES_REPORT, @Description("DocumentType_ARTICLE_PCMT_SALES_REPORT_description")
   ARTICLE_PCMT_SALES_REPORT, @Description("DocumentType_CUSTOMER_SALES_REPORT_description")
   CUSTOMER_SALES_REPORT, @Description("DocumentType_SUPPLIER_PCMT_REPORT_description")
   SUPPLIER_PCMT_REPORT, @Description("DocumentType_STOCK_MVMT_REPORT_description")
   STOCK_MVMT_REPORT, @Description("DocumentType_STOCK_MVMT_BY_ARTICLE_description")
   STOCK_MVMT_BY_ARTICLE, @Description("DocumentType_ARTICLE_REPORT_description")
   ARTICLE_REPORT, @Description("DocumentType_ARTICLE_REPORT_BY_SECTION_description")
   ARTICLE_REPORT_BY_SECTION, @Description("DocumentType_ARTICLE_WITH_VAT_REPORT_description")
   ARTICLE_WITH_VAT_REPORT, @Description("DocumentType_REVENUE_REPORT_description")
   REVENUE_REPORT, @Description("DocumentType_REVENUE_REPORT_BY_SALES_AGENT_description")
   REVENUE_REPORT_BY_SALES_AGENT, @Description("DocumentType_CASH_STMT_description")
   CASH_STMT, @Description("DocumentType_PRODUCT_DETAIL_REPORT_description")
   PRODUCT_DETAIL_REPORT, @Description("DocumentType_OUT_OF_ORDER_REPORT_description")
   OUT_OF_ORDER_REPORT, @Description("DocumentType_DEBT_STMT_description")
   DEBT_STMT, @Description("DocumentType_DEBT_STMT_BY_CUSTOMER_description")
   DEBT_STMT_BY_CUSTOMER, @Description("DocumentType_STOCK_APPRECIATION_description")
   STOCK_APPRECIATION, @Description("DocumentType_SECTION_REPORT_description")
   SECTION_REPORT, @Description("DocumentType_CUSTOMER_REPORT_description")
   CUSTOMER_REPORT, @Description("DocumentType_INVENTORY_LIST_description")
   INVENTORY_LIST, @Description("DocumentType_ALPHA_INVENTORY_STMT_description")
   ALPHA_INVENTORY_STMT, @Description("DocumentType_INVENTORY_REPORT_description")
   INVENTORY_REPORT, @Description("DocumentType_PROCUREMENT_ORDER_description")
   PROCUREMENT_ORDER
}