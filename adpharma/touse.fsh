@/* Enum AccessRoleEnum */;
java new-enum-type --named AccessRoleEnum --package ~.jpa;
enum add-enum-class-description --title "Access Role Names" --text "The name of access roles defined in this application";
enum add-enum-class-description --title "Enumeration des Droit pour Accès" --text "les noms de rolles autprissant accès au systeme" --locale fr;

java new-enum-const ADMIN;
enum add-enum-constant-description --onConstant ADMIN --title "Administrator" --text "The user administrator";
enum add-enum-constant-description --onConstant ADMIN --title "Administrateur" --text "Administrateur du système" --locale fr;

java new-enum-const LOGIN;
enum add-enum-constant-description --onConstant LOGIN  --title "Login" --text "Role assigned to each user that can sig into this system";
enum add-enum-constant-description --onConstant LOGIN  --title "Connection" --text "Rôle attribué à chaque utilisateur pouvant ce connecter à ce système" --locale fr;
access login-role --onConstant LOGIN;

java new-enum-const MANAGER;
enum add-enum-constant-description --onConstant MANAGER  --title "Manager" --text "The manager has access to each component except the user management component";
enum add-enum-constant-description --onConstant MANAGER  --title "Gestionnaire" --text "Le gestionnaire a accès à chaque module sauf le module de gestion des utilisateurs" --locale fr;

java new-enum-const CASHIER;
enum add-enum-constant-description --onConstant CASHIER  --title "Cashier" --text "The cashier has access to the modules CashDrawer, Payment and Invoice";
enum add-enum-constant-description --onConstant CASHIER  --title "Caissier" --text "Le caissier a accès aux modules Caisse, Paiement et facturation" --locale fr;

java new-enum-const STOCKS;
enum add-enum-constant-description --onConstant STOCKS  --title "Stocks" --text "The warehousemann has access to the modules Article, Delivery, Procurement Order, Packaging Mode, Supplier, Inventory, Purchase Order";
enum add-enum-constant-description --onConstant STOCKS  --title "Magasinier" --text "Le magasinier a accès aux modules Produit, Livraison, Approvisionement, Mode de Conditionement, Inventaire, Commande Fournisseur" --locale fr;

java new-enum-const SALES;
enum add-enum-constant-description --onConstant SALES  --title "Sales" --text "The sales has access to the modules Sales Order, Customer, Insurance, Employer, Hospital, Prescriber, Prescription Book";
enum add-enum-constant-description --onConstant SALES  --title "Vendeur" --text "Le vendeur a accès aux modules Command Client, Client, Assurance, Employeur, Hopital, Prescripteur, Ordonnancier" --locale fr;

constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The agency  is required" --text "The agency  is required";
description add-notNull-message --onProperty agency --title "Le Agence est réquis" --text "l agence est requis" --locale fr;