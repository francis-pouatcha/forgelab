
set ACCEPT_DEFAULTS true;

new-project --named adph.server --topLevelPackage org.adorsys.adph.server --finalName adph.server --projectFolder adph.server;

as7 setup;

persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

repogen setup;

association setup;

@/* Enum Gender */;
java new-enum-type --named Gender --package ~.jpa;
enum add-enum-class-description --title "Gender" --text "The gender of a user.";
enum add-enum-class-description --locale fr --title "Genre" --text "Le genre de cet utilisateur.";

java new-enum-const MALE;
enum add-enum-constant-description --onConstant MALE --title "Male" --text "Gender type male.";
enum add-enum-constant-description --locale fr --onConstant MALE --title "Masculin" --text "Le genre masculin.";

java new-enum-const FEMALE;
enum add-enum-constant-description --onConstant FEMALE  --title "Female" --text "Gender type female" ;
enum add-enum-constant-description --locale fr --onConstant FEMALE  --title "Feminin" --text "Le genre feminin" ;

java new-enum-const NEUTRAL;
enum add-enum-constant-description --onConstant NEUTRAL  --title "Neutral" --text "Gender type neutral" ;
enum add-enum-constant-description --locale fr --onConstant NEUTRAL  --title "Neutre" --text "Le genre neutre" ;


cd ~~;


@/* Enum AccessRoleEnum */;
java new-enum-type --named AccessRoleEnum --package ~.jpa;
enum add-enum-class-description --title "Access Role Names" --text "The name of access roles defined in this application";
enum add-enum-class-description --title "Enumeration des Droit pour Accès" --text "les noms de rolles autprissant accès au systeme" --locale fr;

java new-enum-const ADMIN;
enum add-enum-constant-description --onConstant ADMIN --title "Administrator" --text "The user administrator";
enum add-enum-constant-description --onConstant ADMIN --title "Administrateur" --text "Administrateur du système" --locale fr;

java new-enum-const LOGIN;
enum add-enum-constant-description --onConstant LOGIN  --title "User" --text "Role assigned to each user that can sig into this system";
enum add-enum-constant-description --onConstant LOGIN  --title "Connection" --text "Rôle attribué à chaque utilisateur pouvant ce connecter à ce système" --locale fr;
access login-role --onConstant LOGIN;

java new-enum-const MANAGER;
enum add-enum-constant-description --onConstant MANAGER  --title "Manager" --text "The manager has access to each component except the user management component";
enum add-enum-constant-description --onConstant MANAGER  --title "Gestionnaire" --text "Le gestionnaire a accès à chaque module sauf le module de gestion des utilisateurs" --locale fr;

java new-enum-const CASHIER;
enum add-enum-constant-description --onConstant CASHIER  --title "Cashier" --text "The cashier has access to the modules CashDrawer, Payment and Invoice";
enum add-enum-constant-description --onConstant CASHIER  --title "Caissier" --text "Le caissier a accès aux modules Caisse, Paiement et facturation" --locale fr;

java new-enum-const WAREHOUSEMAN;
enum add-enum-constant-description --onConstant WAREHOUSEMAN  --title "Warehousemann" --text "The warehousemann has access to the modules Article, Delivery, Procurement Order, Packaging Mode, Supplier, Inventory, Purchase Order";
enum add-enum-constant-description --onConstant WAREHOUSEMAN  --title "Magasinier" --text "Le magasinier a accès aux modules Produit, Livraison, Approvisionement, Mode de Conditionement, Inventaire, Commande Fournisseur" --locale fr;

java new-enum-const SALES;
enum add-enum-constant-description --onConstant SALES  --title "Sales" --text "The sales has access to the modules Sales Order, Customer, Insurance, Employer, Hospital, Prescriber, Prescription Book";
enum add-enum-constant-description --onConstant SALES  --title "Vendeur" --text "Le vendeur a accès aux modules Command Client, Client, Assurance, Employeur, Hopital, Prescripteur, Ordonnancier" --locale fr;

cd ~~;


@/* Enum PermissionActionEnum */;
java new-enum-type --named PermissionActionEnum --package ~.jpa;
enum add-enum-class-description --title "Authorized Action" --text "An action associated with a permission";
enum add-enum-class-description --title "Action Autorisation" --text "Une action associée avec cette autorisation" --locale fr;

java new-enum-const ALL;
enum add-enum-constant-description --onConstant ALL --title "All Actions" --text "Allow all actions associated with this permission";
enum add-enum-constant-description --onConstant ALL --title "Toutes Action" --text "Permettre toute action associée à cette autorisation" --locale fr;

java new-enum-const READ;
enum add-enum-constant-description --onConstant READ  --title "Read" --text "The bearer can read component protected by this permission";
enum add-enum-constant-description --onConstant READ  --title "Lecture" --text "Le porteur peut lire la donnée protégée par cette autorisation" --locale fr;

java new-enum-const CREATE;
enum add-enum-constant-description --onConstant CREATE  --title "Create" --text "Allows the bearer to create an instance of the component protected by the associated permission";
enum add-enum-constant-description --onConstant CREATE  --title "Créer" --text "Permet au porteur de créer une instance du genre protégé par la permission associée" --locale fr;

java new-enum-const EDIT;
enum add-enum-constant-description --onConstant EDIT  --title "Modify" --text "Allows the bearer to modify an instance of the component protected by the associated permission";
enum add-enum-constant-description --onConstant EDIT  --title "Modifier" --text "Permet au porteur de modifier une instance du genre protégé par la permission associée" --locale fr;


cd ~~;


@/* Entity PermissionName */;
entity --named PermissionName --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Permission" --text "A permission protecting an entity";
description add-class-description --title "Permission" --text "Une permission protegeant une entité" --locale fr;
access permission-table --actionEnumClass ~.jpa.PermissionActionEnum;

field string --named name;
description add-field-description --onProperty name --title "Permission Name" --text "The name of this permission";
description add-field-description --onProperty name --title "Nom de la Permission" --text "Intitule de cete permission" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "Enter the name of this autorization" --text "Enter the name of this autorization";
description add-notNull-message --onProperty name --title "Saisir le nom de cette autorisation" --text "Saisir le nom de cette autorisation" --locale fr;
access permission-name-field --onProperty name;

field custom --named action --type ~.jpa.PermissionActionEnum;
description add-field-description --onProperty action --title "Authorized Action" --text "The action associated with a permission";
description add-field-description --onProperty action --title "Action Autorisation" --text "Action associée avec cette autorisation" --locale fr;
enum enumerated-field --onProperty action;
display add-list-field --field action;
display add-toString-field --field action;
constraint NotNull --onProperty action;
description add-notNull-message --onProperty action --title "Select an action for this autorization" --text "Select an action for this autorization";
description add-notNull-message --onProperty action --title "Selectioner une action à associer avec cette autorisation" --text "Selectioner une action à associer avec cette autorisation" --locale fr;
access permission-action-field --onProperty action;

cd ~~;


@/* Entity RoleName */;
entity --named RoleName --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Roles" --text "Entity used to map roles";
description add-class-description --title "Roles" --text "Entité écoutant mes roles" --locale fr;
access role-table --enumClass ~.jpa.AccessRoleEnum;

field string --named name;
description add-field-description --onProperty name --title "Role Name" --text "The name of this role";
description add-field-description --onProperty name --title "Intitule du Role" --text "Intitule de ce role" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;
access role-name-field --onProperty name;

relationship add --sourceEntity ~.jpa.RoleName --sourceQualifier permissions --targetEntity ~.jpa.PermissionName;
cd ../RoleName.java;
description add-field-description --onProperty permissions --title "Permissions of this Role" --text "Permissions associated with this role.";
description add-field-description --onProperty permissions --title "Permissions de ce Role" --text "Les permissions associées avec ce role" --locale fr;
association set-type --onProperty permissions --type AGGREGATION --targetEntity ~.jpa.PermissionName;
association set-selection-mode --onProperty permissions --selectionMode TABLE;


cd ~~;


@/* Entity Login */;
entity --named Login --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Login" --text "A login record";
description add-class-description --title "Connection" --text "Une connection" --locale fr;
access login-table;

access login-entry --userName "admin" --password "admin";
access role-entry --userName "admin" --role "LOGIN";
access role-entry --userName "admin" --role "ADMIN"; 

field string --named loginName;
description add-field-description --onProperty loginName --title "User Name" --text "The name used by the user to login.";
description add-field-description --onProperty loginName --title "Nom de Connection" --text "Le nom de connetion de cet utilisateur." --locale fr;
constraint NotNull --onProperty loginName;
description add-notNull-message --onProperty loginName --title "User name is required" --text "User name is required";
description add-notNull-message --onProperty loginName --title "Nom de connection est réquis" --text "Nom de connection est réquis" --locale fr;
display add-toString-field --field loginName;
display add-list-field --field loginName;
@/* Unique=true */;
access login-name-field --onProperty loginName;

field string --named fullName;
description add-field-description --onProperty fullName --title "Full Name" --text "The full name of the user.";
description add-field-description --onProperty fullName --title "Nom Complet" --text "Le nom complet dede cet ilisateur." --locale fr;
constraint NotNull --onProperty fullName;
description add-notNull-message --onProperty fullName --title "The full name is required" --text "The full name is required";
description add-notNull-message --onProperty fullName --title "Le nom complet est réquis" --text "Le nom complet est réquis" --locale fr;
access full-name-field --onProperty fullName;

field string --named password;
description add-field-description --onProperty password --title "Password" --text "The password of the user.";
description add-field-description --onProperty password --title "Mot de Passe" --text "Mot de passe de cet utilisateur." --locale fr;
access password-field --onProperty password;

field boolean --named disableLogin --primitive false;
@/*  default=false */;
description add-field-description --onProperty disableLogin --title "Disable Login" --text "Indicates whether the user login is disabled.";
description add-field-description --onProperty disableLogin --title "Login Desactivé" --text "Indique si le login de cet utilisateur est desactivé ou non." --locale fr;

field boolean --named accountLocked --primitive false;
@/*  default=false */;
description add-field-description --onProperty accountLocked --title "Account Locked" --text "Indicates whether the user account is locked.";
description add-field-description --onProperty accountLocked --title "Compte Bloqué" --text "Indique si le compte(login+password) est bloqué ou pas." --locale fr;

field temporal --type TIMESTAMP --named credentialExpiration; 
@/* Default = +50 ans   */;
description add-field-description --onProperty credentialExpiration --title "Password Expiration" --text "Date of expiration of the user password.";
description add-field-description --onProperty credentialExpiration --title "Expiration Mot de Passe" --text "Date expiration du certificat utilisateur." --locale fr;
format add-date-pattern --onProperty credentialExpiration --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named accountExpiration; 
@/* pattern=dd-MM-yyyy HH:mm  Default = +50 ans */;
description add-field-description --onProperty accountExpiration --title "Account Expiration Date" --text "Account expiration date";
description add-field-description --onProperty accountExpiration --title "Date pour Expiration du Compte" --text "Date pour expiration du compte" --locale fr;
format add-date-pattern --onProperty accountExpiration --pattern "dd-MM-yyyy HH:mm"; 

relationship add --sourceEntity ~.jpa.Login --sourceQualifier roleNames --targetEntity ~.jpa.RoleName;
cd ../Login.java;
description add-field-description --onProperty roleNames --title "Role Names" --text "The names of roles assigned to this login";
description add-field-description --onProperty roleNames --title "Nom des Roles" --text "Les nom de role attribués a de cet utilisateur" --locale fr;
association set-type --onProperty roleNames --type AGGREGATION --targetEntity ~.jpa.RoleName;
association set-selection-mode --onProperty roleNames --selectionMode FORWARD;

cd ~~;

envers setup;

envers audit-package --jpaPackage src/main/java/org/adorsys/;

cd ~~;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest access-control --roleTable ~.jpa.RoleName.java --permissionTable ~.jpa.PermissionName.java --loginTable ~.jpa.Login.java;

cd ~~;

repotest setup;

repotest create-test --packages src/main/java/org/adorsys/;

cd ~~;


