

@/* ======================== */;

@/* Entité Caisse */;
entity --named CashDrawer --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Cash Drawer" --text "A cash drawer.";
description add-class-description  --locale fr --title "Caisse" --text "Une caisse.";

field string --named cashDrawerNumber;
description add-field-description --onProperty cashDrawerNumber --title "Cash Drawer Number" --text "The number of this cash drawer.";
description add-field-description --onProperty cashDrawerNumber --title "Numéro de Caisse" --text "Le numéro de cette caisse." --locale fr;

field manyToOne --named cashier --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment on this drawer.";
description add-field-description --onProperty cashier --title "Caissier" --text "Utilisateur percevant le paiement surcette caisse." --locale fr;

field manyToOne --named closedBy --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty closedBy --title "Closed By" --text "The user who closed this cash drawer.";
description add-field-description --onProperty closedBy --title "Fermé Par" --text "Utilisateur ayant fermé la caisse." --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site ;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "Site in which this drawer resides.";
description add-field-description --onProperty site --title "Site" --text "Site dans lequel la caisse est gerée." --locale fr;

field temporal --type TIMESTAMP --named openingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty openingDate --title "Opening Date" --text "The opening date of this drawer.";
description add-field-description --onProperty openingDate --title "Date d'Ouverture" --text "La date d'ouverture de cette caisse." --locale fr;

field temporal --type TIMESTAMP --named closingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty closingDate --title "Closing Date" --text "The closing date of this drawer.";
description add-field-description --onProperty closingDate --title "Date de Fermeture" --text "La date de fermeture de cette caisse." --locale fr;

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "The initial amount.";
description add-field-description --onProperty initialAmount --title "Fond de Caisse" --text "Le fond initial de la caisse." --locale fr;
@/* Default=0 */;

field boolean --named opened;
description add-field-description --onProperty opened --title "Open" --text "Indicates whether the cash drawer is open.";
description add-field-description --onProperty opened --title "Ouverte" --text "Indique si la caisse est ouverte." --locale fr;
@/* default=true */;

field number --named totalCashIn --type java.math.BigDecimal;
description add-field-description --onProperty totalCashIn --title "Total Cash In" --text "The total cash in.";
description add-field-description --onProperty totalCashIn --title "Total Encaissement" --text "L'encaissement totale." --locale fr;
@/* Default=0 */;

field number --named totalCashOut --type java.math.BigDecimal;
description add-field-description --onProperty totalCashOut --title "Total Cash Out" --text "Total withdrawal from this drawer.";
description add-field-description --onProperty totalCashOut --title "Total Retrait" --text "Total des decaissements éffectués en caisse." --locale fr;
@/* Default=0 */;

field number --named totalCash --type java.math.BigDecimal;
description add-field-description --onProperty totalCash --title "Total Cash" --text "Total cash in this drawer.";
description add-field-description --onProperty totalCash --title "Total Cash" --text "Total cash dans cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCreditSales --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditSales --title "Total Credit Sales" --text "Total credit sales in this drawer.";
description add-field-description --onProperty totalCreditSales --title "Total Credit" --text "Total credit vendu par cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCheck --type java.math.BigDecimal;
description add-field-description --onProperty totalCheck --title "Total Checks" --text "Total checks in this drawer.";
description add-field-description --onProperty totalCheck --title "Total Chèque" --text "Total chèque dans cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCreditCard --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditCard --title "Total Credit Card" --text "Total credit cards by this drawer.";
description add-field-description --onProperty totalCreditCard --title "Total Carte Credit" --text "Total carte de credit par cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCompanyVoucher --type java.math.BigDecimal;
description add-field-description --onProperty totalCompanyVoucher --title "Total Company Vouchera" --text "Total voucher (from company or hospital) in this drawer.";
description add-field-description --onProperty totalCompanyVoucher --title "Total Avoir Companie" --text "Totale des bons (de sociéte ou d'hopital) qu'il ya en caisse." --locale fr;
@/* Default=0 */;

field number --named totalClientVoucher --type java.math.BigDecimal;
description add-field-description --onProperty totalClientVoucher --title "Total Client Voucher" --text "Total voucher (from client) in this drawer.";
description add-field-description --onProperty totalClientVoucher --title "Total Avoir Client" --text "Totale des bons (client) qu'il ya en caisse." --locale fr;
@/* Default=0 */;

field number --named totalCashDebt --type java.math.BigDecimal;
description add-field-description --onProperty totalCashDebt --title "Total Cash Debt" --text "Total cash from client debts in the drawer.";
description add-field-description --onProperty totalCashDebt --title "Total Cash Dette" --text "Totale cash des dettes client dans cette caisse." --locale fr;
@/* Default=0 */;

@/* Invoice */;

@/* Type Facture*/;
java new-enum-type --named InvoiceType --package ~.jpa ;
enum add-enum-class-description --title "Invoice Type" --text "Type of invoice.";
enum add-enum-class-description  --locale fr --title "Type Facture" --text "Type de facture.";
@/* Enumeration{CAISSE, PROFORMAT} */;
java new-enum-const CAISSE ;
java new-enum-const PROFORMAT ;
enum add-enum-constant-description --onConstant CAISSE --title "Caisse" --text "facture de caisse";
enum add-enum-constant-description --onConstant PROFORMAT --title "Proformat" --text "facture proformat";

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
@/* pattern= dd-MM-yyyy HH:mm*/;
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


field oneToMany --named invoiceItems --fieldType ~.jpa.InvoiceItem --inverseFieldName invoice --fetchType EAGER  --cascade;
description add-field-description --onProperty invoiceItems --title "Invoice Items" --text "The invoice items";
description add-field-description --onProperty invoiceItems --title "Lignes Facture" --text "Les ligne facture" --locale fr;
@/* OneToMany(cascadeType=All) */;

field custom --named invoiceType --type ~.jpa.InvoiceType;
description add-field-description --onProperty invoiceType --title "Type" --text "The type of this invoice.";
description add-field-description --onProperty invoiceType --title "Type" --text "Le type de cette facture." --locale fr;
enum enumerated-field --onProperty invoiceType ;
@/* Enumeration{CAISSE, PROFORMAT} */;

cd ~~ ;
 
