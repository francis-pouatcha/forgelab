


@/* ================================ */;
@/* Inventories */;

@/* InventoryStatus */;
entity --named InventoryStatus --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Inventory Status" --text "The status of this inventory.";
description add-class-description  --locale fr --title "Status Inventaire" --text "Etat de cet inventaire.";
@/* Enumeration{EN_COURS, CLOSE} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this inventory status.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de cet etat d'inventaire." --locale fr;

@/* Entite Ligne Inventaire */;
entity --named InventoryItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Inventory Item" --text "An inventory item";
description add-class-description  --locale fr --title "Ligne Inventaire" --text "Une ligne inventaire.";

field string --named iiNumber;
description add-field-description --onProperty iiNumber --title "Item Number" --text "The inventory item number.";
description add-field-description --onProperty iiNumber --title "Numéro de Ligne" --text "Le numéro de la ligne d'inventaire." --locale fr;

field long --named expectedQty;
description add-field-description --onProperty expectedQty --title "Expected Quantity in Stock" --text "The quantity of this article expected to be in stock.";
description add-field-description --onProperty expectedQty --title "Quantité Attendue en Stock" --text "Quantité de produits supposé en le stock." --locale fr;

field long --named asseccedQty;
description add-field-description --onProperty asseccedQty --title "Real Quantity" --text "Actual quantity of products physically counted.";
description add-field-description --onProperty asseccedQty --title "Real Quantité" --text "Quantité réelle de produits de la ligne comptés physiquement." --locale fr;

field long --named gap;
description add-field-description --onProperty gap --title "Gap" --text "Deviation of access and expected quantity.";
description add-field-description --onProperty gap --title "Écart" --text "Écart de stock de la ligne d'inventaire." --locale fr;
@/* formule=(qte_reel - qte_en_stock) */;

field number --named lastSalesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty lastSalesPricePU --title "Last Sales Price per Unit" --text "The last sales price per unit.";
description add-field-description --onProperty lastSalesPricePU --title "Dernier Prix de Vente Unitaire" --text "Le dernier prix de vente unitaire." --locale fr;
@/*  Default=0 */; 

field number --named totalPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPrice --title "Total Price" --text "The total price.";
description add-field-description --onProperty totalPrice --title "Prix Total" --text "Le prix total." --locale fr;
@/*  Default=0, formule=(prix_unitaire*ecart) */;

field manyToOne --named recordingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty recordingUser --title "Recording User" --text "The user recording this inventory item.";
description add-field-description --onProperty recordingUser --title "Agent Saisie" --text "Responsable de la saisie de la ligne d'inventaire" --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date of this inventory item.";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie de cette ligne inventaire." --locale fr;

field manyToOne --named article --fieldType ~.jpa.Article
constraint NotNull --onProperty article
description add-field-description --onProperty article --title "Article" --text "The product associated with this inventory line.";
description add-field-description --onProperty article --title "Produit" --text "Le produit attaché à la ligne d'inventaire." --locale fr;


@/* Entite Inventaire */;
entity --named Inventory --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Inventory" --text "An inventory";
description add-class-description  --locale fr --title "Inventaire" --text "Un inventaire.";

field string --named inventoryNumber;
description add-field-description --onProperty inventoryNumber --title "Inventory Number" --text "The inventory number.";
description add-field-description --onProperty inventoryNumber --title "Numéro d'Inventaire" --text "Le numéro d'inventaire." --locale fr;

field string --named purchaseOrderNumber;
description add-field-description --onProperty purchaseOrderNumber --title "Purchase Order Number" --text "Identifier of a purchase order following an inventory.";
description add-field-description --onProperty purchaseOrderNumber --title "Numéro d'Approvisionement" --text "Identifiant de l'approvisionement suite à un inventaire" --locale fr;

field manyToOne --named recordingUser --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty recordingUser --title "Recording User" --text "The user recording this inventory.";
description add-field-description --onProperty recordingUser --title "Agent Saisie" --text "L'utilisateur saisissant cet inventaire." --locale fr;

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Amount" --text "The amount of this inventory.";
description add-field-description --onProperty amount --title "Montant" --text "Le montant de cet inventaire." --locale fr;

field manyToOne --named inventoryStatus --fieldType ~.jpa.InventoryStatus;
description add-field-description --onProperty inventoryStatus --title "Inventory Status" --text "The status of this inventory.";
description add-field-description --onProperty inventoryStatus --title "État d'Inventaire" --text "L'état de cet inventaire." --locale fr;
@/* Enumeration{EN_COURS, CLOSE} */;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this inventory.";
description add-field-description --onProperty note --title "Note" --text "Description de cet inventaire." --locale fr;
constraint Size --onProperty note --max 256;

field temporal --type TIMESTAMP --named inventoryDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty inventoryDate --title "Inventory Date" --text "The date of this inventory.";
description add-field-description --onProperty inventoryDate --title "Date d'Inventaire" --text "La date de cet inventaire." --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site;
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "The site of this inventory.";
description add-field-description --onProperty site --title "Magasin" --text "Le site de cet inventaire." --locale fr;

cd ../InventoryItem.java;

field manyToOne --named inventory --fieldType ~.jpa.Inventory;
constraint NotNull --onProperty inentory;
description add-field-description --onProperty inventory --title "Inventory" --text "The inventory containing this line.";
description add-field-description --onProperty inventory --title "Inventaire" --text "L'inventaire contenant cette ligne." --locale fr;

cd ../Inventory.java;

field oneToMany --named inventoryItems --fieldType ~.jpa.InventoryItem --inverseFieldName inventory --fetchType EAGER  --cascade;
description add-field-description --onProperty inventoryItems --title "Inventory Items" --text "The inventory items";
description add-field-description --onProperty inventoryItems --title "Lignes Inventaire" --text "Les ligne d'inventaire" --locale fr;


@/* Entité TransformationProduit */;
entity --named ProductTransformation --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Product Transformation" --text "Product transformation";
description add-class-description  --locale fr --title "Transformation Produit" --text "Transformation produit";

field string --named ptNumber;
description add-field-description --onProperty ptNumber --title "Product Transformation Number" --text "The product transformation number.";
description add-field-description --onProperty ptNumber --title "Numéro Transformation Produit" --text "Le numéro de transformation produit." --locale fr;

field oneToOne --named source --fieldType ~.jpa.Article;
constraint NotNull --onProperty source;
description add-field-description --onProperty source --title "Source Product" --text "The compound product to be decompose.";
description add-field-description --onProperty source --title "Produit Origine" --text "Le produit Composant que l'on veut decomposer." --locale fr;

field oneToOne --named target --fieldType ~.jpa.Article;
constraint NotNull --onProperty target;
description add-field-description --onProperty target --title "Target Product" --text "The compound product generated from the decomposition.";
description add-field-description --onProperty target --title "Produit Cible" --text "Le produit composé generé à partir de la décomposition." --locale fr;

field number --named targetQuantity --type java.math.BigDecimal;
description add-field-description --onProperty targetQuantity --title "Target Quantity" --text "Quantity of the target product";
description add-field-description --onProperty targetQuantity --title "Quantité Cible" --text "Quantité du produit cible.." --locale fr;
constraint NotNull --onProperty targetQuantity;

field number --named salesPrice --type java.math.BigDecimal;
description add-field-description --onProperty salesPrice --title "Sales Price" --text "Sales price.";
description add-field-description --onProperty salesPrice --title "Prix de Vente" --text "Prix de vente." --locale fr;
constraint NotNull --onProperty salesPrice;

field boolean --named active;
description add-field-description --onProperty active --title "Active" --text "Alows activation or deactivation of this transformation.";
description add-field-description --onProperty active --title "Actif" --text "Permet d'activer ou de desactiver la transformation.." --locale fr;
@/* Default= true */;


 