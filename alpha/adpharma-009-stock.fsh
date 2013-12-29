


@/* ==================================== */;
@/* Movement Stock*/;

@/* Type Movement Stock*/;
entity --named StockMovementType --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Stock Movement Type" --text "Type of movement made ​​in the stock";
description add-class-description  --locale fr --title "Type Mouvement Stock" --text "Type de mouvement effectué dans le stock";
@/* Enumeration{APPROVISIONEMENT, VENTE, ANNULATION, MODIFICATION, ENTREE_TRANSFORMATION, SORTIE_TRANSFORMATION, SORTIE_PRODUIT, SORTIE_INVENTAIRE, RETOUR_INVENTAIRE, RETOUR_PRODUIT, ANNULATION_SORTIE,ANNULATION_RETOUR, FUSION_CIP} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this stock movement type.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce type de mouvement de stock." --locale fr;

@/* Movement Stock Endpoint*/;
entity --named StockMovementEndpoint --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Stock Movement Endpoint" --text "An origin or the destination of a stock movement.";
description add-class-description  --locale fr --title "Origine ou Destination du Mouvement Stock" --text "Origine ou la destination du movement de stock.";
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this stock movement endpoint.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de la cible de mouvement de stock." --locale fr;




@/* Mouvement de stock */;
entity --named StockMovement --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Purchase Order" --text "It saves the traces of all the movements that take place in the stock (inputs, outputs, inventory, returns processing, etc ...)";
description add-class-description  --locale fr --title "Approvisionnement" --text "Elle permet de sauvegarder les traces de  tous les mouvements qui prennen place dans le stock(entrees, sorties, inventaires, retours, transformation, etc...)";

field string --named mvtNumber;
description add-field-description --onProperty mvtNumber --title "Movement Number" --text "The movement number.";
description add-field-description --onProperty mvtNumber --title "Numéro du Movement" --text "Le numero du mouvement." --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern=dd-MM-yyyy HH:mm */;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this stock movement.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette movement de stock." --locale fr;

field manyToOne --named creatingUser --fieldType ~.jpa.AdpharmaUser;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this stock movement.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur originaire du mouvement." --locale fr;
constraint NotNull --onProperty creatingUser;

field number --named movedQty --type java.math.BigDecimal;
description add-field-description --onProperty movedQty --title "Quantity Moved" --text "The quantity moved during this stockage operation.";
description add-field-description --onProperty movedQty --title "Quantité Deplacés" --text "La quantité de produits deplacés lors de cette operation de stockage." --locale fr;
@/* Default=0 */;

field manyToOne --named movementType --fieldType ~.jpa.StockMovementType;
constraint NotNull --onProperty movementType;
description add-field-description --onProperty movementType --title "Movement Type" --text "The type of this stock movement.";
description add-field-description --onProperty movementType --title "Type de Mouvement" --text "Le type de ce mouvement de stock." --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "The site in which the movement occured";
description add-field-description --onProperty site --title "Magasin" --text "Le site dans lequel le mouvement prend place" --locale fr;

field manyToOne --named movementOrigin --fieldType ~.jpa.StockMovementType;
description add-field-description --onProperty movementOrigin --title "Movement Origin" --text "The starting point of the movement.";
description add-field-description --onProperty movementOrigin --title "Origine du Mouvement" --text "Le point de depart du mouvement." --locale fr;
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

field manyToOne --named movementDestination --fieldType ~.jpa.StockMovementType;
description add-field-description --onProperty movementDestination --title "Movement Destination" --text "Point of arrival of the movement.";
description add-field-description --onProperty movementDestination --title "Destination du Mouvement" --text "Point arrivée du mouvement." --locale fr;
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

field string --named soNumber;
description add-field-description --onProperty soNumber --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNumber --title "Numéro de Commande Client" --text "Le numéro de commande client." --locale fr;
@/* numero_ticket, Numero de la commande client pour un mouvement de type vente */;

field string --named soNumber;
description add-field-description --onProperty soNumber --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNumber --title "Numéro de Commande Client" --text "Le numéro de commande client." --locale fr;
@/* numero_ticket, Numero de la commande client pour un mouvement de type vente */;

field string --named deliverySlipNumber;
description add-field-description --onProperty deliverySlipNumber --title "Delivery Slip Number" --text "The delivery slip number for purchase order (in case of a purchase)";
description add-field-description --onProperty deliverySlipNumber --title "Numéro Bordereau de Livraison" --text "Numéro de bordereau de cet approvisionement (pour un mouvement de type approvisionement)" --locale fr;

field string --named pic; 
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code for a product in stock.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard pour un mouvement de produit dans le stock." --locale fr;

field string --named picm; 
description add-field-description --onProperty picm --title "Product Identification Code PO" --text "The standard product identification code for a product (in case of a purchase order).";
description add-field-description --onProperty picm --title "Code Identifiant Prouit PO" --text "Le Code identifiant standard pour un mouvement de produit (cas approvisionent)." --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Name of the agency in which the movement takes place.";
description add-field-description --onProperty agency --title "Filiale" --text "Nom de la filiale dans laquelle le mouvement a lieu." --locale fr;

field string --named designation; 
description add-field-description --onProperty designation --title "Designation" --text "Product designation for product movement.";
description add-field-description --onProperty designation --title "Designation" --text "Designation du produit pour un mouvement de produit." --locale fr;

field number --named initialQty --type java.math.BigDecimal;
description add-field-description --onProperty initialQty --title "Initial Quantity" --text "The quantity in stock before the movement.";
description add-field-description --onProperty initialQty --title "Quantité Initiale" --text "La quantité de produits dans le stock avant le mouvement." --locale fr;
@/* Default=0 */;

field number --named finalQty --type java.math.BigDecimal;
description add-field-description --onProperty finalQty --title "Final Quantity" --text "The quantity in stock after the movement.";
description add-field-description --onProperty finalQty --title "Quantité Finale" --text "La quantité de produits dans le stock apres le mouvement." --locale fr;
@/* Default=0 */;

field string --named cashDrawerNumber;
description add-field-description --onProperty cashDrawerNumber --title "Cash Drawer Number" --text "The cash drawer number for cash movements.";
description add-field-description --onProperty cashDrawerNumber --title "Numéro de Caisse" --text "Le numéro de caisse pour les mouvement de caise." --locale fr;

field number --named totalPurchasingPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasingPrice --title "Total Purchasing Price" --text "Total purchasing price for movement of type purchase.";
description add-field-description --onProperty totalPurchasingPrice --title "Prix Achat Total" --text "Prix total achat pour les mouvements de type approvisionement." --locale fr;
constraint NotNull --onProperty amountBeforeTax;

field number --named totalDiscount --type java.math.BigDecimal;
description add-field-description --onProperty totalDiscount --title "Total Discount" --text "Total discount of the purchase.";
description add-field-description --onProperty totalDiscount --title "Remise Totale" --text "Remise totale sur cet approvisionement." --locale fr;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "Total sale price for the movement type sales";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Totale" --text "Prix de vente total pour les mouvements de type vente." --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this stock movement.";
description add-field-description --onProperty note --title "Note" --text "Description de ce mouvement de stock." --locale fr;
constraint Size --onProperty note --max 256;

field boolean --named canceled; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the stock movement was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Precise si le mouvement de tock a été annulée ou non." --locale fr;
@/* default=false */;




 