
@/* Entity Supplier */;
entity --named Supplier --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Supplier" --text "The supplier";
description add-class-description  --locale fr --title "Fournisseur" --text "Le fournisseur";

field string --named supplierNumber;
description add-field-description --onProperty supplierNumber --title "Supplier Number" --text "The number identifying this supplier.";
description add-field-description --onProperty supplierNumber --title "Numéro du Fournisseur" --text "Le numéro identification de ce fournisseur." --locale fr;


field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of the supplier.";
description add-field-description --onProperty name --title "Nom" --text "Le nom du fournisseur." --locale fr;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of the supplier";
description add-field-description --onProperty shortName --title "Libelle Court" --text "Abréviation du nom du fournisseur." --locale fr;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The phone number of the supplier";
description add-field-description --onProperty phone --title "Téléphone" --text "Le téléphone du fournisseur" --locale fr;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile" --text "The mobile phone number of the supplier";
description add-field-description --onProperty mobile --title "Mobile" --text "Le téléphone mobile du fournisseur" --locale fr;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the supplier";
description add-field-description --onProperty fax --title "Fax" --text "Le fax du fournisseur" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email of the supplier";
description add-field-description --onProperty email --title "Email" --text "Le email du fournisseur" --locale fr;

field string --named adresse;
description add-field-description --onProperty adresse --title "Address" --text "The addresse of the supplier";
description add-field-description --onProperty adresse --title "Adresse" --text "Adresse du fournisseur" --locale fr;

field string --named internetSite;
description add-field-description --onProperty internetSite --title "Web Site" --text "The web site of the supplier";
description add-field-description --onProperty internetSite --title "Site Internet" --text "Le site internet du fournisseur" --locale fr;

field string --named responsable;
description add-field-description --onProperty responsable --title "Person In Charge" --text "The person in charge at the supplier side.";
description add-field-description --onProperty responsable --title "Responsable" --text "Le responsable chez le fournisseur." --locale fr;

field string --named revenue;
description add-field-description --onProperty revenue --title "Revenue" --text "The revenue of this supplier side.";
description add-field-description --onProperty revenue --title "Chiffre d Affaires" --text "Le chiffre d affaires de ce fournisseur." --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this supplier";
description add-field-description --onProperty city --title "Ville" --text "La ville du fournisseur." --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The country of this supplier";
description add-field-description --onProperty country --title "Pays" --text "Le pays de ce fournisseur" --locale fr;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code of this supplier";
description add-field-description --onProperty zipCode --title "Code Postal" --text "Le code postal de ce fournisseur" --locale fr;

field string --named taxIdNumber;
description add-field-description --onProperty taxIdNumber --title "Tax Id Number" --text "The tax id number of this supplier";
description add-field-description --onProperty taxIdNumber --title "Numéro du Contribuable" --text "Le numéro du contribuable de ce fournisseur" --locale fr;

field string --named commRegNumber;
description add-field-description --onProperty commRegNumber --title "Commercial Register Number" --text "The commercial register number of this supplier";
description add-field-description --onProperty commRegNumber --title "Numéro Registre du Commerce" --text "Le numéro du registre de commerce de ce fournisseur" --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this supplier";
description add-field-description --onProperty note --title "Note" --text "Description de ce fournisseur" --locale fr;
constraint Size --onProperty note --max 256;

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


@/* Entity ProcmtOrderProcessingState */;
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

field string --named procurementNumber;
description add-field-description --onProperty orderNumber --title "Order Number" --text "The number of this procurement order";
description add-field-description --onProperty orderNumber --title "Numéro de Commande" --text "Le numéro de cette commande" --locale fr;

field temporal --type TIMESTAMP --named submissionDate; 
@/* pattern=dd-MM-yyyy HH:mm */;
description add-field-description --onProperty submissionDate --title "Submission DAte" --text "Date of submission of the order to Ubipharm created from the module Ubipharm";
description add-field-description --onProperty submissionDate --title "Date de Soumission" --text "Date de soumission de la commande à Ubipharm crée à partir du module d Ubipharm" --locale fr;

field temporal --type TIMESTAMP --named processingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty processingDate --title "Processing Date" --text "The processing date";
description add-field-description --onProperty processingDate --title "Date de Traitement" --text "The processing date" --locale fr;

