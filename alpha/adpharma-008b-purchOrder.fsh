
@/* Entity PurchaseOrderProcessingState */;
entity --named PurchaseOrderProcessingState --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Purchase Order Processing State" --text "Purchase Order Processing State.";
description add-class-description  --locale fr --title "Etat de Traitement de l Approvisionements" --text "Etat de Traitement de l Approvisionements.";
@/* Enumération{EN_COURS, CLOS, RECEIVED, SEND_TO_PROVIDER} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this purchase order processing state.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de cet etat de traitement d approvisionement." --locale fr;



@/* Entity PurchaseOrder */;
entity --named PurchaseOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Purchase Order" --text "The purchase order";
description add-class-description  --locale fr --title "Approvisionnement" --text "Un approvisionement (une Commande)";

field string --named poNumber;
description add-field-description --onProperty poNumber --title "PO Number" --text "The number of the purchase order.";
description add-field-description --onProperty poNumber --title "Numéro Appr." --text "Le numéro de l'approvisionement" --locale fr;

field string --named deliverySlipNumber;
constraint NotNull --onProperty deliverySlipNumber;
description add-field-description --onProperty deliverySlipNumber --title "Delivery Slip Number" --text "The delivery slip number (generaly available on the delivery slip)";
description add-field-description --onProperty deliverySlipNumber --title "Numéro Bordereau de Livraison" --text "Numéro de bordereau de l'approvisionement(mentionné géneralement sur le bordereau de livraison)" --locale fr;

field temporal --type TIMESTAMP --named dateOnDeliverySlip; 
@/* Pattern='' dd-MM-yyy''Date; //  */;
description add-field-description --onProperty dateOnDeliverySlip --title "Date on Delivery Slip" --text "Date as mentioned on delivery slip";
description add-field-description --onProperty dateOnDeliverySlip --title "Date sur Bordereau" --text "Date de livraison mentionée sur le bordereau de livraison de l'approvisionement" --locale fr;

field manyToOne --named creatingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this purchase order.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "L'utilisateur ayant crée l'approvisionement." --locale fr;
constraint NotNull --onProperty creatingUser;

field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "The site in which the purchase order was made.";
description add-field-description --onProperty site --title "Site" --text "Le site dans lequel l'approvisionement a été éffectuée." --locale fr;

field manyToOne --named currency --fieldType ~.jpa.Currency;
description add-field-description --onProperty currency --title "Currency" --text "The currency used for the conversion of the currency stated on the delivery note in local currency (FCFA).";
description add-field-description --onProperty currency --title "Devise" --text "La devise utilisée pour la conversion de la monnaie mentionnée sur le bordereau de livraison en monnaie locale(FCFA)." --locale fr;

field temporal --type TIMESTAMP --named orderDate; 
@/* Pattern='' dd-MM-yyy''Date; Default= new Date(), date_commande//  */;
description add-field-description --onProperty orderDate --title "Order Date" --text "Order date.";
description add-field-description --onProperty orderDate --title "Date de Commande" --text "Date de commande." --locale fr;

field temporal --type TIMESTAMP --named deliveryDate; 
@/* Pattern='' dd-MM-yyy''Date; Default= new Date()//  */;
description add-field-description --onProperty deliveryDate --title "Delivery Date" --text "Date on which the products where effectively delivered products.";
description add-field-description --onProperty deliveryDate --title "Date de Livraison" --text "Date à laquelle a été livrée les produits qui entrent en stock.." --locale fr;

field manyToOne --named supplier --fieldType ~.jpa.Supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;

field temporal --type TIMESTAMP --named paymentDate; 
@/* Pattern='' dd-MM-yyy''Date; Default= new Date()//  */;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "Date of settlement of this order.";
description add-field-description --onProperty paymentDate --title "Date de Règlement" --text "Date à laquelle l'approvisionement a été doit être reglé auprès du fournisseur. Prévu pour recapituler les dettes fournisseur." --locale fr;.

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this purchase order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette approvisionement." --locale fr;
constraint NotNull --onProperty amountBeforeTax;
@/* Default=0, montant_ht */;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this purchase order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette approvisionement." --locale fr;
@/* Default=0, montant_ttc */;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this purchase order.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Montant de la remise de l'approvisionement." --locale fr;
@/* Default=0, montant_remise */;

field number --named netAmountToPay --type java.math.BigDecimal;
description add-field-description --onProperty netAmountToPay --title "Net Amount to Pay" --text "Teh net amount to pay.";
description add-field-description --onProperty netAmountToPay --title "Montant net a payer" --text "Montant de la remise de l'approvisionement." --locale fr;
@/* Default=0, montant_nap */;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur la valeur ajoute" --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern='' dd-MM-yyyy HH:mm''; Default= new Date(), date_création */;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this order.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette commande." --locale fr;

field boolean --named claims;
description add-field-description --onProperty claims --title "Claims" --text "Specifies whether the purchase contains claims or not.";
description add-field-description --onProperty claims --title "Réclamations" --text "Précise si l'approvisionement contient des réclamations ou non." --locale fr;
@/* default=false */;

field boolean --named emergency;
description add-field-description --onProperty emergency --title "Emergency" --text "Emergency";
description add-field-description --onProperty emergency --title "Urgence" --text "Urgence." --locale fr;
@/* default=false */;

field boolean --named closed;
description add-field-description --onProperty closed --title "Closed" --text "Specifies whether the purchase was closed or not.";
description add-field-description --onProperty closed --title "Cloturé" --text "Précise si l'approvisionement a été cloturé ou  non." --locale fr;
@/* default=false, cloturer */;

field manyToOne --named orderProcessingState --fieldType ~.jpa.PurchaseProcessingState;
description add-field-description --onProperty orderProcessingState --title "Order Processing State" --text "The purchase order processing state.";
description add-field-description --onProperty orderProcessingState --title "Etat de traitement Commande" --text "L'etat de traitement de cette approvisionement." --locale fr;
@/* Enumération{EN_COURS, CLOS, RECEIVED, SEND_TO_PROVIDER} */;
@/* default=EN_COURS */;

field manyToOne --named receivingAgency --fieldType ~.jpa.Agency;
description add-field-description --onProperty receivingAgency --title "Agency" --text "Name of the agency in which the product was delivered.";
description add-field-description --onProperty receivingAgency --title "Filiale" --text "Nom de la filiale dans laquelle l'entree en stock s'effectue" --locale fr;




@/* Entity PurchaseOrderItem */;
entity --named PurchaseOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Purchase Order Item" --text "Purchase order item";
description add-class-description  --locale fr --title "Ligne Approvisionnement" --text "la ligne d approvisionement";

field manyToOne --named purchaseOrder --fieldType ~.jpa.PurchaseOrder;
description add-field-description --onProperty purchaseOrder --title "Purchase Order" --text "The purchase order enclosing this item";
description add-field-description --onProperty purchaseOrder --title "Approvisionnement" --text "L'approvisionement contenant cette ligne d'approvisionement." --locale fr;

field string --named poItemNumber;
description add-field-description --onProperty poItemNumber --title "PO Item Number" --text "The number of this PO Item";
description add-field-description --onProperty poItemNumber --title "Numéro LAP" --text "Le numéro de cette ligne d approvisionement" --locale fr;

field string --named lineIndex;
description add-field-description --onProperty lineIndex --title "Line Index" --text "The index of a line of this item in the PO";
description add-field-description --onProperty lineIndex --title "Index de ligne" --text "L index de la ligne de cette LAP" --locale fr;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code of this product.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard du produit." --locale fr;

field string --named localPic; 
description add-field-description --onProperty localPic --title "Local PIC" --text "The internal product identification code used to identify lots during sales.";
description add-field-description --onProperty localPic --title "CIP Maison" --text "Le code identifiant produit maison, utilisé pour identifier les lots de produits lors de la vente." --locale fr;
constraint Size --onProperty sss --min 7;

field manyToOne --named article --fieldType ~.jpa.Article
constraint NotNull --onProperty article;
description add-field-description --onProperty article --title "Article" --text "The article fo this lot";
description add-field-description --onProperty article --title "Produit" --text "Le produit du lot." --locale fr;

field string --named designation;
description add-field-description --onProperty designation --title "Designation" --text "The designation of the product in thos lot";
description add-field-description --onProperty designation --title "Designation" --text "La designation du produit dans le lot" --locale fr;

field temporal --type TIMESTAMP --named productionDate; 
@/* Pattern= dd-MM-yyy  */;
description add-field-description --onProperty productionDate --title "Production Date" --text "Production date fo the article in this lot";
description add-field-description --onProperty productionDate --title "Date de Fabrication" --text "Date de fabrication du produit dans ce lot" --locale fr;

field temporal --type TIMESTAMP --named expirationDate; 
@/* Pattern=dd-MM-yyy  */;
description add-field-description --onProperty expirationDate --title "Expiration Date" --text "Expiration date for the article in this lot";
description add-field-description --onProperty expirationDate --title "Date de Peremption" --text "Date de peremption du produit dans le lot" --locale fr;

field string --named modifyingUser; 
description add-field-description --onProperty modifyingUser --title "Modifying User" --text "The user editing this PO";
description add-field-description --onProperty modifyingUser --title "Agent de Saisie" --text "Agent de saisie du produit" --locale fr;

field temporal --type TIMESTAMP --named productRecCreated; 
@/* Pattern= dd-MM-yyy  */;
description add-field-description --onProperty productRecCreated --title "Product Record Created" --text "Product record creation date";
description add-field-description --onProperty productRecCreated --title "Date de Saisie du Produit" --text "Date à laquelle le produit a été saisi(crée)" --locale fr;

field number --named quantity --type java.math.BigDecimal;
description add-field-description --onProperty quantity --title "Quantity" --text "The quantity purchase in this lot.";
description add-field-description --onProperty quantity --title "Quantité" --text "La quantité de produits approvisionnée dans le lot." --locale fr;

field number --named freeQuantity --type java.math.BigDecimal;
description add-field-description --onProperty freeQuantity --title "Free Quantity" --text "The auntity of products given by the supplier free of charge during purchasing. These articles are a value aded for the products in stock.";
description add-field-description --onProperty freeQuantity --title "Quantité Gratuite" --text "La quantité de produits fournis gratuitement par le fournisseur lors de l approvisionnement. Ces produits sont une plus value pour les produits dans le stock" --locale fr;

field number --named soldQuantity --type java.math.BigDecimal;
description add-field-description --onProperty soldQuantity --title "Sold Quantity" --text "The quantity of articles sold by the suplier";
description add-field-description --onProperty soldQuantity --title "Quantité Vendue" --text "La quantité de produits vendus par le fournisseur" --locale fr;
@/*  Default=0 */; 

field number --named claimedQuantity --type java.math.BigDecimal;
description add-field-description --onProperty claimedQuantity --title "Claimed Quantity" --text "The quantity of products claimed";
description add-field-description --onProperty claimedQuantity --title "Quantité reclamée" --text "Quantité de produits reclamés" --locale fr;
@/*  Default=0 */; 

field number --named stockQuantity --type java.math.BigDecimal;
description add-field-description --onProperty stockQuantity --title "Stock Quantity" --text "The quantity of products claimed";
description add-field-description --onProperty stockQuantity --title "Quantité en Stock" --text "Quantité de produits du lot." --locale fr;
@/*  Default=0 */; 

field number --named releasedQuantity --type java.math.BigDecimal;
description add-field-description --onProperty releasedQuantity --title "Released Quantity" --text "The quantity of products released";
description add-field-description --onProperty releasedQuantity --title "Quantité Sortie" --text "Quantité de produits sortis du lot." --locale fr;
@/*  Default=0 */; 

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix de vente unitaire." --locale fr;
@/*  Default=0 */; 

field number --named purchasePricePU --type java.math.BigDecimal;
description add-field-description --onProperty purchasePricePU --title "Purchase Price per Unit" --text "The purchase price per unit.";
description add-field-description --onProperty purchasePricePU --title "Prix d Achat Unitaire" --text "Prix d achat unitaire." --locale fr;
@/*  Default=0 */; 

field number --named totalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasePrice --title "Total Purchase Price" --text "The total purchase price.";
description add-field-description --onProperty totalPurchasePrice --title "Prix d Achat Total" --text "Prix d achat totale." --locale fr;
@/*  Default=0 */; 
@/*Formule= (prix_achat_unitaire*[qte_aprovisionée-qte_unité_gratuite]) */;

field number --named grossMargin --type java.math.BigDecimal;
description add-field-description --onProperty grossMargin --title "Gross Margin" --text "The gross margin.";
description add-field-description --onProperty grossMargin --title "Marge Brute" --text "La marge brute." --locale fr;
@/* Formule= prix_vente_unitaire – prix_achat_unitaire */; 
@/* C est le benefice net pour chaque produit du lot */;

field boolean --named approvedForSale; 
description add-field-description --onProperty approvedForSale --title "Approved For Sale" --text "Used to approved the sale of products in this lot.";
description add-field-description --onProperty approvedForSale --title "Vente Autorisée" --text "Permet d autoriser ou non la vente d un lot de produits." --locale fr;
@/* default=true */;

field number --named maxDiscountRate --type java.math.BigDecimal;
@/*  remise_max, Default=0 */;
description add-field-description --onProperty maxDiscountRate --title "Max Discount Rate" --text "Maximal discount rate for product of this lot.";
description add-field-description --onProperty maxDiscountRate --title "Taux Maximal Remise" --text "Taux de remise max pour les produits de ce lot." --locale fr;

field boolean --named discountApproved; 
description add-field-description --onProperty discountApproved --title "Discount Approved" --text "Used to approved the discount on products in this lot.";
description add-field-description --onProperty discountApproved --title "Remise Autorisée" --text "Permet d autoriser ou non la remise sur vent d un produit de ce lot." --locale fr;
@/* default=true */;










