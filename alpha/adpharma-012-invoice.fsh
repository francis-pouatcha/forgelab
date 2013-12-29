

@/* ======================== */;
@/* Invoice */;

@/* Type Facture*/;
entity --named InvoiceType --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice Type" --text "Type of invoice.";
description add-class-description  --locale fr --title "Type Facture" --text "Type de facture.";
@/* Enumeration{CAISSE, PROFORMAT} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this invoice type.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce type de facture." --locale fr;

@/* Entite Ligne Facture */;
entity --named InvoiceItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice Item" --text "An invoice item.";
description add-class-description  --locale fr --title "Ligne Facture" --text "Une ligne facture.";

field int --named indexLine; 
description add-field-description --onProperty indexLine --title "Line Index" --text "Index for searching through invoice items";
description add-field-description --onProperty indexLine --title "Index de Ligne" --text "Index permettant de rechercher la ligne de facture" --locale fr;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code for this invoice line.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard pour cette ligne de facture." --locale fr;

field string --named picm; 
description add-field-description --onProperty picm --title "Product Identification Code PO" --text "The standard product identification code for this invoice item.";
description add-field-description --onProperty picm --title "Code Identifiant Prouit PO" --text "Le code identifiant standard pour cette ligne de facture." --locale fr;

field string --named designation; 
description add-field-description --onProperty designation --title "Designation" --text "The name the product in this invoice item.";
description add-field-description --onProperty designation --title "Designation" --text "Le nom du produit de la ligne de facture." --locale fr;

field number --named purchasedQty --type java.math.BigDecimal;
description add-field-description --onProperty purchasedQty --title "Quantity Purchased" --text "The quantity purchased in this line.";
description add-field-description --onProperty purchasedQty --title "Quantité Achetée" --text "La quantité achetée dans cette ligne." --locale fr;
@/* Default=0 */;

field number --named returnedQty --type java.math.BigDecimal;
description add-field-description --onProperty returnedQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty returnedQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;
@/* Default=0 */;

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit for product of this line.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix unitaire du produit de la ligne de facture" --locale fr;
@/*  Default=0 */; 

field number --named discountAmount --type java.math.BigDecimal;
description add-field-description --onProperty discountAmount --title "Discount Amount" --text "Discount amount for this invoice item.";
description add-field-description --onProperty discountAmount --title "Montant Remise" --text "Remise totale de la ligne de facture." --locale fr;
@/* Default=0, montant_remise */;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "The total sales price for product of this line.";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Total" --text "Prix total du produit de la ligne de facture" --locale fr;
@/*  Default=0 */; 



@/* Entité Facture */;
entity --named Invoice --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice" --text "An invoice.";
description add-class-description  --locale fr --title "Facture" --text "Une facture.";

field string --named invoiceNumber;
description add-field-description --onProperty invoiceNumber --title "Invoice Number" --text "The number of the invoice.";
description add-field-description --onProperty invoiceNumber --title "Numéro Facture" --text "Le numéro de cette facture." --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this invoicd.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette facture." --locale fr;

field manyToOne --named client --fieldType ~.jpa.Client;
description add-field-description --onProperty client --title "Client" --text "The client referenced by this invoice.";
description add-field-description --onProperty client --title "Client" --text "Le client mentionné sur la facture." --locale fr;

field manyToOne --named cashDrawer --fieldType ~.jpa.CashDrawer;
description add-field-description --onProperty cashDrawer --title "Cash Drawer" --text "Cash drawer in which the invoice is stored while waiting for payment.";
description add-field-description --onProperty cashDrawer --title "Caisse" --text "Caisse dans laquelle la facture est deposée en attente de paiement." --locale fr;

field manyToOne --named salesStaff --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty salesStaff --title "Sales Staff" --text "The user creating this invoice.";
description add-field-description --onProperty salesStaff --title "Vendeur" --text "Utilisateur vendeur ayant generé la facture" --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site
description add-field-description --onProperty site --title "Site" --text "Site in which this invoice is generated.";
description add-field-description --onProperty site --title "Site" --text "Site dans lequel la caisse est gerée." --locale fr;

field manyToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
description add-field-description --onProperty client --title "Sales Order" --text "The sales order generating of this invoice.";
description add-field-description --onProperty client --title "Commande Client" --text "Commande client à l'origine de la facture" --locale fr;

field manyToOne --named salesOrderType --fieldType ~.jpa.SalesOrderType;
description add-field-description --onProperty salesOrderType --title "Type" --text "The type of the assoicated sales order.";
description add-field-description --onProperty salesOrderType --title "Type" --text "Le type de la commande client." --locale fr;
@/* Enumeration{VENTE_AU_PUBLIC, VENTE_A_CREDIT, VENTE_PROFORMAT} */;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "The total sales price for this invoice.";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Total" --text "Prix total de la facture." --locale fr;
@/*  Default=0 */; 

field number --named discountAmount --type java.math.BigDecimal;
description add-field-description --onProperty discountAmount --title "Discount Amount" --text "Discount amount for this invoice.";
description add-field-description --onProperty discountAmount --title "Montant Remise" --text "Remise totale de la facture." --locale fr;
@/* Default=0, montant_remise */;

field number --named netToPay --type java.math.BigDecimal;
description add-field-description --onProperty netToPay --title "Net a Payer" --text "The net amount to pay.";
description add-field-description --onProperty netToPay --title "Net a Payer" --text "Le montant net à payer." --locale fr;
@/*  Default=0 */; 

field boolean --named settled; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the invoice is settled.";
description add-field-description --onProperty settled --title "Soldée" --text "Indique si la facture est soldée ou pas." --locale fr;
@/* default=false */;

field boolean --named cashed; 
description add-field-description --onProperty cashed --title "Cashed" --text "Sates if the invoice is cashed.";
description add-field-description --onProperty cashed --title "encaisseé" --text "Indique si la facture est encaissée ou pas." --locale fr;
@/* default=false */;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "The advance payment.";
description add-field-description --onProperty advancePayment --title "Net a Payer" --text "L'avance sur paiement." --locale fr;
@/*  Default=0 */; 

field number --named restToPay --type java.math.BigDecimal;
description add-field-description --onProperty restToPay --title "Ret to Pay" --text "The rest to pay.";
description add-field-description --onProperty restToPay --title "Reste a Payer" --text "Le reste a payer." --locale fr;
@/*  Default=0 */; 

cd ../InvoiceItem.java;

field manyToOne --named invoice --fieldType ~.jpa.Invoice;
description add-field-description --onProperty invoice --title "Invoice" --text "The invoice containing this line";
description add-field-description --onProperty invoice --title "Facture" --text "la facture contenant cette ligne" --locale fr;

cd ../Invoice.java;

field oneToMany --named invoiceItems --fieldType ~.jpa.InvoiceItem --inverseFieldName invoice --fetchType EAGER  --cascade;
description add-field-description --onProperty invoiceItems --title "Invoice Items" --text "The invoice items";
description add-field-description --onProperty invoiceItems --title "Lignes Facture" --text "Les ligne facture" --locale fr;
@/* OneToMany(cascadeType=All) */;

field manyToOne --named invoiceType --fieldType ~.jpa.InvoiceType;
description add-field-description --onProperty invoiceType --title "Type" --text "The type of this invoice.";
description add-field-description --onProperty invoiceType --title "Type" --text "Le type de cette facture." --locale fr;
@/* Enumeration{CAISSE, PROFORMAT} */;


 