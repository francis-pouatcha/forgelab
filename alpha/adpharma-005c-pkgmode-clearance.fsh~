
@/* Enum type DocumentState State */;
java new-enum-type --named DocumentProcessingState --package ~.jpa ;
enum add-enum-class-description --title "Document State" --text "The state of a documents.";
enum add-enum-class-description --title "Document State" --text "The state of a documents." --locale fr;
java new-enum-const SUSPENDED ;
java new-enum-const ONGOING ;
enum add-enum-constant-description --onConstant SUSPENDED --title "SUSPENDED" --text "The ndocument is suspended.";
enum add-enum-constant-description --onConstant ONGOING  --title "ONGOING" --text "the document is ongoing " ;


@/* Entity Clearance Config */;
entity --named ClearanceConfig --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Clearance Configuration" --text "Configuration for the clearance of a product.";
description add-class-description --title "Configuration Solde" --text "Permet de créer une configuration du solde pour un produit." --locale fr;

field temporal --type TIMESTAMP --named startDate; 
@/*  pattern=dd-MM-yyyy Date */;
description add-field-description --onProperty startDate --title "Clearance Start Date" --text "Start date for this clearance";
description add-field-description --onProperty startDate --title "Date Debut Solde" --text "Date de debut du solde" --locale fr;
constraint NotNull --onProperty startDate --message "Veuillez entrer la date de debut du solde";

field temporal --type TIMESTAMP --named endDate; 
@/*  pattern=dd-MM-yyyy Date*/;
description add-field-description --onProperty endDate --title "Clearance End Date" --text "End date for this clearance";
description add-field-description --onProperty endDate --title "Date Fin Solde" --text "Date de fin du solde." --locale fr;
constraint NotNull --onProperty endDate;

field number --named discountRate --type java.math.BigDecimal;
@/*   Default=5 */;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "Discount rate for this clearance.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise pour ce solde." --locale fr;
constraint NotNull --onProperty discountRate --message "Veuillez entrer le taux de solde de ce produit";
constraint DecimalMin --onProperty discountRate --min 1.0 --message "Le taux de solde ne doit pas etre inferieur a 1";
constraint DecimalMax --onProperty discountRate --max 100.0 --message "Le taux de solde ne doit pas etre superieur a 100";

field custom --named clearanceState --type ~.jpa.DocumentProcessingState.java ;
@/* default EtatSolde.EN_COURS */;
description add-field-description --onProperty clearanceState --title "Clearance State" --text "The clearance state";
description add-field-description --onProperty clearanceState --title "État du Solde" --text "État du solde" --locale fr;
enum enumerated-field --onProperty clearanceState ;
field boolean --named active;
description add-field-description --onProperty active --title "Active" --text "Says if this clearance configuration is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si cette configuration solde est active ou non" --locale fr;
@/* default=Boolean.TRUE */;

cd ~~;
