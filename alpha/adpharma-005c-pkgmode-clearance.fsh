
@/* Entity Clearance State */;
entity --named ClearanceState --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Clearance State" --text "The state of a clearance.";
description add-class-description --title "Etate Solde" --text "Éetat du solde." --locale fr;
@/* java new-enum-const SUSPENDED */;
@/* java new-enum-const ONGOING */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this clearance state.";
description add-field-description --onProperty name --title "Libelle" --text "Le Nom de ce solde. " --locale fr;


@/* Entity Clearance Config */;
entity --named ClearanceConfig --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Clearance Configuration" --text "Configuration for the clearance of a product.";
description add-class-description --title "Configuration Solde" --text "Permet de créer une configuration du solde pour un produit." --locale fr;

field temporal --type TIMESTAMP --named startDate; 
@/*  pattern=dd-MM-yyyy Date*/;
description add-field-description --onProperty startDate --title "Clearance Start Date" --text "Start date for this clearance";
description add-field-description --onProperty startDate --title "Date Debut Solde" --text "Date de debut du solde" --locale fr;
constraint NotNull --onProperty startDate;
@/* message="Veuillez entrer la date de debut du solde") */;

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
@/*   @Column(columnDefinition="Decimal(10,2)") */;

field manyToOne --named clearanceState --fieldType ~.jpa.ClearanceState;
@/* default EtatSolde.EN_COURS */;
description add-field-description --onProperty clearanceState --title "Clearance State" --text "The clearance state";
description add-field-description --onProperty clearanceState --title "État du Solde" --text "État du solde" --locale fr;

field boolean --named active;
description add-field-description --onProperty active --title "Active" --text "Says if this clearance configuration is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si cette configuration solde est active ou non" --locale fr;
@/* default=Boolean.TRUE */;
