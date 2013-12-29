

@/* Entity ProcurementOrderType */;
entity --named ProcurementOrderType --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order Type" --text "Procurement order type";
description add-class-description  --locale fr --title "Type Commande Fournisseur" --text "Type commande fournisseur";
@/* Enumération{NORMAL,PACKAGED,SPECIAL} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this procurement order type.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce type commande fournisseur." --locale fr;




@/* Entity ProcmtOrderConversationState */;
entity --named ProcmtOrderConversationState --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order Coversation State" --text "Procurement order conversation state.";
description add-class-description  --locale fr --title "Etat d Echange de Commande Fournisseur" --text "Etat de conversation pour cette commande fournisseur.";
@/* Enumération{SUBMIT, VALIDATE,CANCELLED,PROVIDER_PROCESSING,DRUGSTORE_PROCESSING}, exchange_bean_state */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this procurement order conversation state.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de cet etat de conversation sur commande fournisseur." --locale fr;




@/* Entity ProcmtOrderProcessingState */;
entity --named ProcmtOrderProcessingState --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order Processing State" --text "Procurement order processing state.";
description add-class-description  --locale fr --title "Etat de Traitement de Commande Fournisseur" --text "Type commande fournisseur.";
@/* Enumération{EN_COURS, CLOS, RECEIVED, SEND_TO_PROVIDER} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this procurement order processing state.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de cet etat de traitement de commande fournisseur." --locale fr;




@/* Entity ProcmtOrderTrigger Mode */;
entity --named ProcmtOrderTriggerMode --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order Trigger Mode" --text "Procurement order trigger mode.";
description add-class-description  --locale fr --title "Criteres de Preparation de Commande Fournisseur" --text "Criteres de preparation de commande fournisseur.";
@/* Enumération{MANUELLE, RUPTURE_STOCK , ALERTE_STOCK,PLUS_VENDU} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this rocurement order trigger mode.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce critere de preparation de commande fournisseur." --locale fr;



@/* Entity ProcurementOrder */;
entity --named ProcurementOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order" --text "Procurement order";
description add-class-description  --locale fr --title "Commande Fournisseur" --text "Commande fournisseur";

field manyToOne --named purchaseOrder --fieldType ~.jpa.PurchaseOrder;
description add-field-description --onProperty purchaseOrder --title "Purchase Order" --text "The purchase order managed by this procurement order.";
description add-field-description --onProperty purchaseOrder --locale fr --title "Approvisionnement" --text "Approvisionement generé par cette commande.";
@/* approvisionnement_id, Long why not direct reference */;

field string --named procurementNumber;
description add-field-description --onProperty orderNumber --title "Order Number" --text "The number of this procurement order";
description add-field-description --onProperty orderNumber --title "Numéro de Commande" --text "Le numéro de cette commande" --locale fr;

field temporal --type TIMESTAMP --named submissionDate; 
@/* pattern=dd-MM-yyyy HH:mm */;
description add-field-description --onProperty submissionDate --title "Submission DAte" --text "Date of submission of the order to Ubipharm created from the module Ubipharm";
description add-field-description --onProperty submissionDate --title "Date de Soumission" --text "Date de soumission de la commande à Ubipharm crée à partir du module de Ubipharm" --locale fr;

field temporal --type TIMESTAMP --named processingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty processingDate --title "Processing Date" --text "The processing date";
description add-field-description --onProperty processingDate --title "Date de Traitement" --text "The processing date" --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this order.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette commande." --locale fr;

field temporal --type TIMESTAMP --named deliveryDeadline; 
@/* pattern=dd-MM-yyyy HH:mm */;
description add-field-description --onProperty deliveryDeadline --title "Delivery Deadline" --text "The delivery deadline of this order.";
description add-field-description --onProperty deliveryDeadline --title "Date Limite Livraison" --text "La date limite livraison de cette commande." --locale fr;

field manyToOne --named orderType --fieldType ~.jpa.ProcurementOrderType;
description add-field-description --onProperty orderType --title "Order Type" --text "The procurement order type";
description add-field-description --onProperty orderType --title "Type Commande" --text "Le type de commande fourmnisseur." --locale fr;
@/* Enumération{NORMAL,PACKAGED,SPECIAL} */;

field manyToOne --named conversationState --fieldType ~.jpa.ProcmtOrderConversationState;
description add-field-description --onProperty conversationState --title "Coversation State" --text "The procurement order conversation state (state of the conversation with the module Ubipharm) .";
description add-field-description --onProperty conversationState --title "Etat Echange" --text "État des echanges pour cette commande fourmnisseur (Etat de la commande echangee avec le module Ubipharm)." --locale fr;
@/* Enumération{SUBMIT, VALIDATE,CANCELLED,PROVIDER_PROCESSING,DRUGSTORE_PROCESSING}, exchange_bean_state */;

field manyToOne --named supplier --fieldType ~.jpa.Supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier of this order.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur de cette commande." --locale fr;

field boolean --named delivered; 
description add-field-description --onProperty delivered --title "Delivered" --text "Sates if the order was delivered or not.";
description add-field-description --onProperty delivered --title "Livré" --text "Precise si la commande a été livrée ou non." --locale fr;
@/* default=false */;

field manyToOne --named orderTriggerMode --fieldType ~.jpa.ProcmtOrderTriggerMode;
description add-field-description --onProperty orderTriggerMode --title "Procurement Order Trigger Mode" --text "Procurement order trigger mode.";
description add-field-description --onProperty orderTriggerMode --title "Criteres de Preparation de Commande Fournisseur" --text "Criteres de preparation de commande fournisseur." --locale fr;
@/* Enumération{MANUELLE, RUPTURE_STOCK , ALERTE_STOCK,PLUS_VENDU} */;

field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "The site in which the order was made.";
description add-field-description --onProperty site --title "Site" --text "Le site dans lequel la commande a été éffectuée." --locale fr;

field manyToOne --named creatingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this procurement order.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur ayant crée cette commande." --locale fr;
constraint NotNull --onProperty creatingUser;

field manyToOne --named orderProcessingState --fieldType ~.jpa.ProcmtOrderConversationState;
description add-field-description --onProperty orderProcessingState --title "Order Processing State" --text "The procurement order processing state.";
description add-field-description --onProperty orderProcessingState --title "Type Commande" --text "État de traitement de cette commande fourmnisseur." --locale fr;
@/* Enumération{EN_COURS, CLOS, RECEIVED, SEND_TO_PROVIDER} */;
@/* default=EN_COURS */;

field boolean --named canceled; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the order was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Precise si la commande a été annulée ou non." --locale fr;
@/* default=false */;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this procurement order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette commande fournisseur." --locale fr;
@/* Default=0, montant_ht */;

field number --named amountVAT --type java.math.BigDecimal;
description add-field-description --onProperty amountVAT --title "Amount VAT" --text "Total amount VAT for this purchase order.";
description add-field-description --onProperty amountVAT --title "Montant TVA" --text "Montant total TVA pour cette commande fournisseur." --locale fr;
@/* Default=0, montant_tva */;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this procurement order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette commande fournisseur." --locale fr;
@/* Default=0, montant_ttc */;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur la valeur ajoute" --locale fr;


@/* Entity ProcurementOrderItem */;
entity --named ProcurementOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order Item" --text "Procurement order item";
description add-class-description  --locale fr --title "Ligne Commande Fournisseur" --text "Ligne de commande fournisseur";

field string --named indexLine; 
description add-field-description --onProperty indexLine --title "Line Index" --text "Index for searching through purchase order items";
description add-field-description --onProperty indexLine --title "Index de Ligne" --text "Index permettant de rechercher la ligne de commande fournisseur" --locale fr;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The code identifying the product in this order item.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le code identifiant du produit de la ligne de commande." --locale fr;

field manyToOne --named article --fieldType ~.jpa.Article;
constraint NotNull --onProperty article;
description add-field-description --onProperty article --title "Article" --text "The article fo this lot";
description add-field-description --onProperty article --title "Produit" --text "Le produit du lot." --locale fr;

field temporal --type TIMESTAMP --named recCreated; 
@/* Pattern=dd-MM-yyy  */;
description add-field-description --onProperty recCreated --title "Record Created" --text "Order item record creation date";
description add-field-description --onProperty recCreated --title "Date de Saisie" --text "Date à laquelle la ligne de commande a été saisi(crée)" --locale fr;

field number --named quantity --type java.math.BigDecimal;
description add-field-description --onProperty quantity --title "Quantity Ordered" --text "The quantity ordered in this lot.";
description add-field-description --onProperty quantity --title "Quantité Commandée" --text "La quantité de produits commandés dans le lot." --locale fr;
@/* Default=0 */;

field number --named availableQty --type java.math.BigDecimal;
description add-field-description --onProperty availableQty --title "Available Quantity" --text "The quantity available at the supplier.";
description add-field-description --onProperty availableQty --title "Quantité Disponible" --text "La quantité de produits disponible chez le fournisseur." --locale fr;
@/* Default=0 quantité_fournie*/;

field manyToOne --named creatingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this procurement order item.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur ayant crée cet ligne de commande." --locale fr;

field string --named designation; 
description add-field-description --onProperty designation --title "Designation" --text "The name the product in this order item.";
description add-field-description --onProperty designation --title "Designation" --text "Le nom du produit de la ligne de commande." --locale fr;

field boolean --named valid;
description add-field-description --onProperty valid --title "Valid" --text "Determines if the order item is valid or not according to the expectations of the supplier.";
description add-field-description --onProperty valid --title "Valide" --text "Détermine si la ligne de commande est valide ou pas selon les attentes du fournisseur." --locale fr;

field number --named minPurchasePriceSug --type java.math.BigDecimal;
description add-field-description --onProperty minPurchasePriceSug --title "Minimum Purchase Price Suggested" --text "Minimum suggested purchase price suggested for this purchase order item.";
description add-field-description --onProperty minPurchasePriceSug --title "Prix d Achat Minimum Proposé" --text "Prix d achat minimum proposé d un produit de la ligne de commande Fournisseur." --locale fr;
@/* Default=0 */;

field number --named minPurchasePriceSupl --type java.math.BigDecimal;
description add-field-description --onProperty minPurchasePriceSupl --title "Minimum Purchase Price Supplier" --text "Minimum supplier purchase price suggested for this purchase order item.";
description add-field-description --onProperty minPurchasePriceSupl --title "Prix d Achat Minimum Fournisseur" --text "Prix fournisseur minimum pour achat pour un produit de la ligne de commande Fournisseur." --locale fr;
@/* Default=0 */;

field number --named minSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty minSalesPrice --title "Minimum Sales Price" --text "Minimum sales price for the product of this procurement order item.";
description add-field-description --onProperty minSalesPrice --title "Prix de Vente Minimum" --text "Prix de vente minimum pour les produits de cette ligne de commande fournisseur." --locale fr;
@/* Default=0 */;

field number --named totalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasePrice --title "Total Purchase Price" --text "Total purchase price for this procurement order item.";
description add-field-description --onProperty totalPurchasePrice --title "Prix d Achat Totale" --text "Prix achat totale pour cette ligne de commande fournisseur." --locale fr;
@/* Default=0, Formule=prix_achat_min*quantité_commandée */;

field manyToOne --named procurementOrder --fieldType ~.jpa.ProcurementOrder;
description add-field-description --onProperty procurementOrder --title "Procurement Order" --text "The containing procurement order";
description add-field-description --onProperty procurementOrder --title "Commande Fournisseur" --text "La commande fourmnisseur contenant cette ligne." --locale fr;

