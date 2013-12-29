

@/* ==================================== */;
@/* Commande Client */;

@/* SalesOrderStatus */;
entity --named SalesOrderStatus --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order Status" --text "The status of this sales order.";
description add-class-description  --locale fr --title "Etat Commande Client" --text "Etat de la commande client.";
@/* Enumeration{EN_COURS, CLOS} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this sales order status.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de cet etat de commande client." --locale fr;




@/* SalesOrderType */;
entity --named SalesOrderType --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order Type" --text "The type of this sales order.";
description add-class-description  --locale fr --title "Type de Commande Client" --text "Le type d'une commande client.";
@/* Enumeration{VENTE_AU_PUBLIC, VENTE_A_CREDIT, VENTE_PROFORMAT} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this sales order type.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce type de commande client." --locale fr;





@/* Sales Order */;
entity --named SalesOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order" --text "A sales order.";
description add-class-description  --locale fr --title "Commande Client" --text "Une commande client.";

field string --named soNumber;
description add-field-description --onProperty soNumber --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNumber --title "Numéro de Commande Client" --text "Le numéro de la commande client." --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this order.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette commande." --locale fr;

field temporal --type TIMESTAMP --named cancelationDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty cancelationDate --title "Cancelation Date" --text "The cancelation date of this order.";
description add-field-description --onProperty cancelationDate --title "Date d'Annulation" --text "La date d'annulation de cette commande." --locale fr;

field temporal --type TIMESTAMP --named restorationDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty restorationDate --title "Restoration Date" --text "The restoration date of this order.";
description add-field-description --onProperty restorationDate --title "Date de Restauration" --text "La date de restauration de cette commande." --locale fr;

field manyToOne --named client --fieldType ~.jpa.Client;
description add-field-description --onProperty client --title "Client" --text "The client ordering.";
description add-field-description --onProperty client --title "Client" --text "Le client  qui passe la commande." --locale fr;

field manyToOne --named salesStaff --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty salesStaff --title "Sales Staff" --text "The user making this sale.";
description add-field-description --onProperty salesStaff --title "Vendeur" --text "L'utilisateur qui effectue la vente." --locale fr;

field manyToOne --named salesOrderStatus --fieldType ~.jpa.SalesOrderStatus;
description add-field-description --onProperty salesOrderStatus --title "Status" --text "The status of this sales order.";
description add-field-description --onProperty salesOrderStatus --title "Status" --text "L'etat de cette commande client." --locale fr;

field boolean --named cashed;
description add-field-description --onProperty cashed --title "Cashed" --text "Indicates whether the order has been cashed or not.";
description add-field-description --onProperty cashed --title "Encaissé" --text "Indique si la commande a été encaissée ou non." --locale fr;
@/* default=false */;

field boolean --named canceled; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the order was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Indique si la commande a été annulée ou pas." --locale fr;
@/* default=false */;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this sales order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette commande client." --locale fr;
@/* Default=0, montant_ht */;

field number --named amountVAT --type java.math.BigDecimal;
description add-field-description --onProperty amountVAT --title "Amount VAT" --text "Total amount VAT for this sales order.";
description add-field-description --onProperty amountVAT --title "Montant TVA" --text "Montant total TVA pour cette commande client." --locale fr;
@/* Default=0, montant_tva */;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this sales order. The sum of all discounts.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Remise totale de la commande, c'est-à-dire la somme des remise totales des  lignes de commande." --locale fr;
@/* Default=0, remise */;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this sales order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette commande client." --locale fr;
@/* Default=0, Formule=(montant_HT - remise) */;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this sales order. The sum of all discounts.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Remise totale de la commande, c'est-à-dire la somme des remise totales des  lignes de commande." --locale fr;
@/* Default=0, remise */;

field number --named otherDiscount --type java.math.BigDecimal;
description add-field-description --onProperty otherDiscount --title "Other Discount" --text "Other form of discount.";
description add-field-description --onProperty otherDiscount --title "Autre Remise" --text "Autre forme de remise sur la commande." --locale fr;
@/* Default=0, other_remise */;

field long --named invoiceId;
description add-field-description --onProperty invoiceId --title "Invoice Identifier" --text "The identifier of the invoice associated with this sales order.";
description add-field-description --onProperty invoiceId --title "Identifiant Facture" --text "Idenfiant de la facture liée à cette commande." --locale fr;

field manyToOne --named salesOrderType --fieldType ~.jpa.SalesOrderType;
description add-field-description --onProperty salesOrderType --title "Type" --text "The type of this sales order.";
description add-field-description --onProperty salesOrderType --title "Type" --text "Le type de cette commande client." --locale fr;
@/* Enumeration{VENTE_AU_PUBLIC, VENTE_A_CREDIT, VENTE_PROFORMAT} */;

field manyToOne --named payingClient --fieldType ~.jpa.Client;
description add-field-description --onProperty payingClient --title "Paying Client" --text "The customer in charge for full or partial payment of the invoice of this order.";
description add-field-description --onProperty payingClient --title "Client Payeur" --text "Le client qui est en charge du payment(total ou partiel) de la facture de la commande." --locale fr;
constraint NotNull --onProperty payingClient; 

field number --named coverageRate --type java.math.BigDecimal;
@/* default=100% */;;
description add-field-description --onProperty coverageRate --title "Coverage Rate" --text "The coverage rate (including tax) for this order.";
description add-field-description --onProperty coverageRate --title "Taux Couverture" --text "Taux de couverture TTC pour cette commande." --locale fr;
@/* taux_couverture, Default=100% */;

field boolean --named partialSales; 
description add-field-description --onProperty canceled --title "Partial Sales" --text "Sates if the current order is a partial sales.";
description add-field-description --onProperty canceled --title "VVente Partielle" --text "Indique si la presente commande represente un vente partielle." --locale fr;
@/* default=false */;

field string --named voucherNumber;
description add-field-description --onProperty voucherNumber --title "Voucher Number" --text "The number of the client voucher associated with this order.";
description add-field-description --onProperty voucherNumber --title "Numéro Avoir" --text "Le numéro  de l'avoir client lié à la commande." --locale fr;

field string --named couponNumber;
description add-field-description --onProperty couponNumber --title "Coupon Number" --text "The number of the client coupon associated with this order.";
description add-field-description --onProperty couponNumber --title "Numéro Bon" --text "Le numéro bu bon client lié à la commande." --locale fr;





@/* Sales Order Item */;
entity --named SalesOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order Item" --text "The sales order item.";
description add-class-description  --locale fr --title "Ligne Commande Client" --text "La ligne de command client.";

field string --named soItemNumber;
description add-field-description --onProperty soItemNumber --title "Item Number" --text "The sales order item number.";
description add-field-description --onProperty soItemNumber --title "Numéro de Ligne" --text "Le numéro de categorie client." --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this client category.";
description add-field-description --onProperty name --title "Libelle" --text "Le nom de cette categorie client." --locale fr;

field number --named orderedQty --type java.math.BigDecimal;
description add-field-description --onProperty orderedQty --title "Quantity Ordered" --text "The quantity ordered in this line.";
description add-field-description --onProperty orderedQty --title "Quantité Commandés" --text "La quantité commandée dans cette ligne." --locale fr;
@/* Default=0 */;

field number --named returnedQty --type java.math.BigDecimal;
description add-field-description --onProperty returnedQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty returnedQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;
@/* Default=0 */;

field temporal --type TIMESTAMP --named recordDate; 
@/* Pattern='' dd-MM-yyy HH:MM''Date; //  */;
description add-field-description --onProperty recordDate --title "Record Date" --text "Creation date for this line.";
description add-field-description --onProperty recordDate --title "Date de Saisie" --text "Date de saisie de cette ligne." --locale fr;

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix de vente unitaire." --locale fr;
@/*  Default=0 */; 

field number --named unitDiscount --type java.math.BigDecimal;
description add-field-description --onProperty unitDiscount --title "Unit Discount" --text "Discount for one unit of the product in this line.";
description add-field-description --onProperty unitDiscount --title "Remise Unitaire" --text "Remise unitaire pour un produit de cette ligne." --locale fr;

field number --named totalDiscount --type java.math.BigDecimal;
description add-field-description --onProperty totalDiscount --title "Total Discount" --text "Total discount of this line.";
description add-field-description --onProperty totalDiscount --title "Remise Totale" --text "Remise totale pour cette ligne." --locale fr;
@/* Default=0., Formule=(remise * qté_commande) */;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code for a product in this line.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard pour un produit dans cette ligne." --locale fr;

field string --named picm; 
description add-field-description --onProperty picm --title "Product Identification Code PO" --text "The standard product identification code for a product in this line.";
description add-field-description --onProperty picm --title "Code Identifiant Prouit PO" --text "Le Code identifiant standard pour un de produit dans cette ligne." --locale fr;

field string --named designation; 
description add-field-description --onProperty designation --title "Designation" --text "The name the product in this order item.";
description add-field-description --onProperty designation --title "Designation" --text "Le nom du produit de la ligne de commande." --locale fr;

field string --named modifyingUser; 
description add-field-description --onProperty modifyingUser --title "Modifying User" --text "The user editing this order item.";
description add-field-description --onProperty modifyingUser --title "Agent de Saisie" --text "Agent de saisie de cette ligne." --locale fr;

@/* Question: Why do we reference the purchase order item instead of referencing the product. */;
field manyToOne --named purchaseOrderItem --fieldType ~.jpa.PurchaseOrderItem;
description add-field-description --onProperty purchaseOrderItem --title "Purchase Order Item" --text "The purchase order item of the product to be sold.";
description add-field-description --onProperty purchaseOrderItem --title "Ligne d'Approvisionement" --text "La ligne d'approvisionnement contenant le produit que l'on veut vendre." --locale fr;

field manyToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order conatining this line.";
description add-field-description --onProperty salesOrder --title "Commande Client" --text "La commande client contenant cet ligne." --locale fr;





