
@/* Download and install jboss-eap-6.1 */;
@/* Install these plugins if not done yet. */;
@/* forge install-plugin arquillian */;
@/* forge install-plugin jboss-as-7 */;
@/* forge install-plugin angularjs */;
@/* git clone https://github.com/clovisgakam/forge-envers-plugin.git */;
@/* forge source-plugin forge-envers-plugin */;
@/* git clone https://github.com/clovisgakam/forge-enumeration-plugin.git */;
@/* forge source-plugin forge-enumeration-plugin */;
@/* git clone https://github.com/francis-pouatcha/forge-repository-plugin.git */;
@/* forge source-plugin forge-repository-plugin */;
@/* git clone https://github.com/francis-pouatcha/javaext.description.git */;
@/* cd javaext.description | mvn clean install | cd .. */;
@/* git clone https://github.com/francis-pouatcha/forge-description-plugin.git */;
@/* forge source-plugin forge-description-plugin */;

set ACCEPT_DEFAULTS true;

new-project --named adpharma.server --topLevelPackage org.adorsys.adpharma.server --finalName adpharma.server --projectFolder adpharma.server;

as7 setup;

persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

set ACCEPT_DEFAULTS false;

enum setup;

repogen setup;

association setup;

@/* Enum Group: can be used to group components and limit the amount of components displayed to a user. */;
@/* These are not one to one identical to roles. */;
java new-enum-type --named WorkGroup --package ~.jpa;
enum add-enum-class-description --title "Work Group" --text "A work group";
enum add-enum-class-description --title "Groupe de Travail" --text "Un groupe de travail" --locale fr;
java new-enum-const ADMIN;
enum add-enum-constant-description --onConstant ADMIN --title "Administrator" --text "Adminitrator of this platform";
enum add-enum-constant-description --onConstant ADMIN --title "Administrateur" --text "Administrateur de ce progiciel" --locale fr;
java new-enum-const USER;
enum add-enum-constant-description --onConstant USER  --title "User" --text "User of this application" ;
enum add-enum-constant-description --onConstant USER  --title "Utilisateur" --text "Utilisateur de ce progiciel" --locale fr ;
java new-enum-const CASHIER;
enum add-enum-constant-description --onConstant CASHIER  --title "Cachier" --text "Cashier" ;
enum add-enum-constant-description --onConstant CASHIER  --title "Caissier" --text "Caissier" --locale fr;
java new-enum-const WAREHOUSEMAN;
enum add-enum-constant-description --onConstant WAREHOUSEMAN  --title "Warehouseman" --text "Warehouseman" ;
enum add-enum-constant-description --onConstant WAREHOUSEMAN  --title "Magasinier" --text "Magasinier" --locale fr;
java new-enum-const MANAGER;
enum add-enum-constant-description --onConstant MANAGER  --title "Manager" --text "Manager" ;
enum add-enum-constant-description --onConstant MANAGER  --title "Manageur" --text "Manageur" --locale fr;
java new-enum-const SALES;
enum add-enum-constant-description --onConstant SALES  --title "Sales" --text "Sales" ;
enum add-enum-constant-description --onConstant SALES  --title "Vente" --text "Vente" --locale fr;

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

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

cd ~~;



@/* Entity Company */;
entity --named Company --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Company" --text "The Company";
description add-class-description  --locale fr --title "Compagnie" --text "La compagnie";
group add --grouper ~.jpa.WorkGroup --named MANAGER;

field string --named displayName;
description add-field-description --onProperty displayName --title "Display Name" --text "The display name";
description add-field-description --onProperty displayName --title "Nom affiché" --text "Nom affiché" --locale fr;
constraint NotNull --onProperty displayName;
description add-notNull-message --onProperty displayName --title "Display name is required" --text "Display name is required";
description add-notNull-message --onProperty displayName --title "Nom affiché est réquis" --text "Nom affiché est réquis" --locale fr;
display add-toString-field --field displayName;
display add-list-field --field displayName;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The site phone number";
description add-field-description --onProperty phone --title "Téléphone" --text "Téléphone du site" --locale fr;
display add-list-field --field phone;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the site";
description add-field-description --onProperty fax --title "Fax" --text "Fax du site" --locale fr;
display add-list-field --field fax;

field string --named siteManager;
description add-field-description --onProperty siteManager --title "Site Manager" --text "The name of the site manager";
description add-field-description --onProperty siteManager --title "Manager du Site" --text "Le nom du manager du site" --locale fr;
display add-list-field --field siteManager;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of the site";
description add-field-description --onProperty email --title "Email" --text "Email du site" --locale fr;
display add-list-field --field email;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street of this company";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue de cette company" --locale fr;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code of this company";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette companie" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city in which this company is lacated";
description add-field-description --onProperty city --title "Ville" --text "La ville ou cette companie est isntallé" --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The country in which this comapny is located";
description add-field-description --onProperty country --title "Pays" --text "Le pays ou cette companie est installée" --locale fr;

field string --named siteInternet;
description add-field-description --onProperty siteInternet --title "Web Site" --text "The web site of this company";
description add-field-description --onProperty siteInternet --title "Site Internet" --text "Site internet du cette companie" --locale fr;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile Phone" --text "The mobile phone of this company";
description add-field-description --onProperty mobile --title "Téléphone Mobile" --text "Téléphone mobile de cette companie" --locale fr;

field string --named registerNumber;
description add-field-description --onProperty registerNumber --title "Register Number" --text "The register number of this company";
description add-field-description --onProperty registerNumber --title "Numéro de Registre" --text "Numéro de registre du commerce de cette société" --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

cd ~~;


@/* Entity Gender */;
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


@/* Entity Agence */;
entity --named Agency --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Agency" --text "An agency of this pharmacie";
description add-class-description  --locale fr --title "Agence" --text "Une agence de cette pharmacie";
group add --grouper ~.jpa.WorkGroup --named MANAGER;

field string --named agencyNumber;
description add-field-description --onProperty agencyNumber --title "Agency Number" --text "The number of this agency";
description add-field-description --onProperty agencyNumber --title "Numéro Agence" --text "Numéro de la filiale" --locale fr;
display add-toString-field --field agencyNumber;
display add-list-field --field agencyNumber;
@/* add unique constraint generator here */;

field string --named name;
description add-field-description --onProperty name --title "Agency Name" --text "The name of this agency";
description add-field-description --onProperty name --title "Nom Agence" --text "Le nom de cette agence" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "The description of this agency";
description add-field-description --onProperty description --title "Description" --text "Description de la filiale" --locale fr;
constraint Size --onProperty description --max 256;
description add-size-message --onProperty description --title "The agency description must have less than 256 characters" --text "The agency description must have less than 256 characters";
description add-size-message --onProperty description --title "La description de cette agence doit avoir moins de 256 caractères" --text "La description de cette agence doit avoir moins de 256 caractères" --locale fr;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this agency is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si la filiale est active ou non" --locale fr;
display add-list-field --field active;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street of this agency";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue de cette agence" --locale fr;
display add-list-field --field name;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code of this agency";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette agence" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this agency";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette agence" --locale fr;
display add-list-field --field name;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The country of this agency";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette agence" --locale fr;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The site phone number";
description add-field-description --onProperty phone --title "Téléphone" --text "Téléphone du site" --locale fr;
display add-list-field --field phone;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the site";
description add-field-description --onProperty fax --title "Fax" --text "Fax du site" --locale fr;
display add-list-field --field fax;

field manyToOne --named company --fieldType ~.jpa.Company;
description add-field-description --onProperty company --title "Company" --text "The company owner of this agency";
description add-field-description --onProperty company --title "Compagnie" --text "la company pocedant cette agence" --locale fr;
association set-selection-mode --onProperty company --selectionMode COMBOBOX;
association set-type --onProperty company --type AGGREGATION --targetEntity ~.jpa.Company;

field string --named ticketMessage;
description add-field-description --onProperty ticketMessage --title "Ticket Message" --text "The  message for ticker";
description add-field-description --onProperty ticketMessage --title "Message Ticket" --text "le message du ticket" --locale fr;
constraint Size --onProperty ticketMessage --max 256;
description add-size-message --onProperty ticketMessage --title "The ticket message must have less than 256 characters" --text "The ticket message must have less than 256 characters";
description add-size-message --onProperty ticketMessage --title "Le message ticket doit avoir moins de 256 caractères" --text "Le message ticket doit avoir moins de 256 caractères" --locale fr;

field string --named invoiceMessage;
description add-field-description --onProperty invoiceMessage --title "Invoice Message" --text "The message for invoice";
description add-field-description --onProperty invoiceMessage --title "Message Facture" --text "Le message Pour la facture" --locale fr;
constraint Size --onProperty invoiceMessage --max 256;
description add-size-message --onProperty invoiceMessage --title "The invoice message must have less than 256 characters" --text "The invoice message must have less than 256 characters";
description add-size-message --onProperty invoiceMessage --title "Le message sur la facture doit avoir moins de 256 caractères" --text "Le message sur la facture doit avoir moins de 256 caractères" --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 
cd ~~;



@/* Entity Currency */;
entity --named Currency --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Currency" --text "A currency";
description add-class-description  --locale fr --title "Devise" --text "Une devise";
group add --grouper ~.jpa.WorkGroup --named MANAGER;

field string --named name;
description add-field-description --onProperty name --title "Currency Name" --text "The name of this currency.";
description add-field-description --onProperty name --title "Libellé de la Devise" --text "Le nom de cette devise." --locale fr;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The currency name is required" --text "The currency name is required";
description add-notNull-message --onProperty name --title "Le libellé de la devise est réquis" --text "Le libellé de la devise est réquis" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field string --named shortName;
description add-field-description --onProperty shortName --title "Short Name" --text "The short name of this currency.";
description add-field-description --onProperty shortName --title "Libelle Court" --text "Abréviation du nom de cette devise." --locale fr;
constraint NotNull --onProperty shortName;
description add-notNull-message --onProperty shortName --title "The short name of this currency is required" --text "The short name of this currency is required";
description add-notNull-message --onProperty shortName --title "Le libellé court de cette devise est réquis" --text "Le libellé court de cette devise est réquis" --locale fr;

field number --named cfaEquivalent --type java.math.BigDecimal;
description add-field-description --onProperty cfaEquivalent --title "CFA Equivalent" --text "Corresponding CFA value for conversions.";
description add-field-description --onProperty cfaEquivalent --title "Equivalent CFA" --text "Valeur equivalant cfa pour faire les conversions." --locale fr;
format add-number-type --onProperty cfaEquivalent --type CURRENCY;


cd ~~ ;


@/* Entity Person */;
entity --named Person --package ~.jpa --idStrategy AUTO;
description add-class-description --title "User" --text "A user of this application";
description add-class-description  --locale fr --title "Utilisateur" --text "Un utilisateur de cette application.";
group add --grouper ~.jpa.WorkGroup --named ADMIN;
@/* TODO access add-permission  --action ALL --toRole ADMIN*/;

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this user";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de cet utilisateur" --locale fr;
enum enumerated-field --onProperty gender ;
display add-list-field --field gender;
display add-toString-field --field gender;

field string --named firstName;
description add-field-description --onProperty firstName --title "First Name" --text "The first name of the user.";
description add-field-description --onProperty firstName --title "Prénom" --text "Le prénom de cet utilisateur." --locale fr;

field string --named lastName;
description add-field-description --onProperty lastName --title "Last Name" --text "The last name of the user.";
description add-field-description --onProperty lastName --title "Nom de famille" --text "Le nom de cet utilisateur." --locale fr;
constraint NotNull --onProperty lastName;
description add-notNull-message --onProperty lastName --title "Last name is required" --text "User name is required";
description add-notNull-message --onProperty lastName --title "Le nom de famille est réquis" --text "Le nom de famille est réquis" --locale fr;

field string --named phoneNumber;
description add-field-description --onProperty phoneNumber --title "Phone Number" --text "The phone number of this user.";
description add-field-description --onProperty phoneNumber --title "Numéro de Telephone" --text "Numéro de téléphone de cet utilisateur." --locale fr;
display add-list-field --field phoneNumber;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street of this user";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue de cet utilisateur" --locale fr;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code of this user";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cet utilisateur" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this user";
description add-field-description --onProperty city --title "Ville" --text "La vile de cet utilisateur" --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The country of this user";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cet utilisateur" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of this user";
description add-field-description --onProperty email --title "Email" --text "Adresse email de cet utilisateur" --locale fr;
display add-list-field --field userName;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The agency in which the user is registered.";
description add-field-description --onProperty agency --title "Agence" --text "Agence dans laquel cet utilisateur est enregistré." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;

field number --named discountRate --type java.math.BigDecimal;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "The discount rate this user can give to clients.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise que cet utilisateur peut accorder aux clients." --locale fr;
format add-number-type --onProperty discountRate --type PERCENTAGE;
@/* Default= 15% */;

field string --named saleKey;
description add-field-description --onProperty saleKey --title "Sale Key" --text "The sales key of saller for a sales session open to all users. ";
description add-field-description --onProperty saleKey --title "Sale Key" --text "Clé de vente du vendeur pour la session vente ouverte à tous les utilisateurs." --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named login --fieldType ~.jpa.Login;
description add-field-description --onProperty login --title "Login" --text "The Login of this person ";
description add-field-description --onProperty login --title "Compte Utilisateur" --text "le compte de cette utilisateur." --locale fr;
association set-selection-mode --onProperty login --selectionMode FORWARD;
association set-type --onProperty login --type COMPOSITION --targetEntity ~.jpa.Login;
display add-toString-field --field login.loginName;
display add-list-field --field login.loginName;

cd ~~;


@/* Entity VAT */;
entity --named VAT --package ~.jpa --idStrategy AUTO;
description add-class-description --title "VAT" --text "The value added tax";
description add-class-description  --locale fr --title "TVA" --text "Taxe sur la valeure ajoutée";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;

field string --named code;
description add-field-description --onProperty code --title "VAT Code" --text "The code of this VAT";
description add-field-description --onProperty code --title "Code TVA" --text "Le code de la TVA" --locale fr;
display add-toString-field --field code;
display add-list-field --field code;

field number --named rate --type java.math.BigDecimal;
description add-field-description --onProperty rate --title "VAT Rate" --text "The VAT rate";
description add-field-description --onProperty rate --title "Taux TVA" --text "Taux de la TVA" --locale fr;
display add-list-field --field rate;
format add-number-type --onProperty rate --type  PERCENTAGE;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this VAT is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si la TVA est Actif ou pas." --locale fr;
display add-list-field --field active;

@/* Entity SalesMargin */;
entity --named SalesMargin --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Margin" --text "The sales margin on this product.";
description add-class-description  --locale fr --title "Taux de Marge" --text "Le taux de marge du produit.";

field string --named code;
description add-field-description --onProperty code --title "Margin Code" --text "The code of this margin";
description add-field-description --onProperty code --title "Code Marge" --text "Numéro de la marge" --locale fr;
display add-toString-field --field code;
display add-list-field --field code;

field number --named rate --type java.math.BigDecimal;
description add-field-description --onProperty rate --title "Marging Rate" --text "The rate of this margin.";
description add-field-description --onProperty rate --title "Taux de Marge" --text "Taux de la marge." --locale fr;
format add-number-type --onProperty rate --type  PERCENTAGE;
display add-list-field --field rate;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this margin rate is Active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si ce taux de marge est actif ou pas." --locale fr;
display add-list-field --field active;


cd ~~ ;


@/* Enum type DocumentState State */;
java new-enum-type --named DocumentProcessingState --package ~.jpa ;
enum add-enum-class-description --title "State" --text "The state of the document";
enum add-enum-class-description --title "Status" --text "État du document" --locale fr;
java new-enum-const SUSPENDED ;
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspended" --text "Suspended document";
enum add-enum-constant-description --onConstant SUSPENDED --title "Suspendu" --text "Document suspendu" --locale fr ;
java new-enum-const ONGOING ;
enum add-enum-constant-description --onConstant ONGOING --title "Ongoing" --text "Ongoing document";
enum add-enum-constant-description --onConstant ONGOING --title "En Cours" --text "Document en cours" --locale fr ;
java new-enum-const RESTORED ;
enum add-enum-constant-description --onConstant RESTORED --title "Restored" --text "Restored document";
enum add-enum-constant-description --onConstant RESTORED --title "Restauré" --text "Document restauré" --locale fr ;
java new-enum-const CLOSED ;
enum add-enum-constant-description --onConstant CLOSED --title "Closed" --text "Closed document";
enum add-enum-constant-description --onConstant CLOSED --title "Cloturé" --text "Document cloturé" --locale fr ;


cd ~~;


@/* Entity Clearance Config */;
entity --named ClearanceConfig --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Clearance Configuration" --text "Configuration for the clearance of a product.";
description add-class-description --title "Configuration Solde" --text "Permet de créer une configuration du solde pour un produit." --locale fr;
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;

field temporal --type TIMESTAMP --named startDate; 
@/*  pattern=dd-MM-yyyy Date */;
description add-field-description --onProperty startDate --title "Clearance Start Date" --text "Start date for this clearance";
description add-field-description --onProperty startDate --title "Date Début Solde" --text "Date de debut du solde" --locale fr;
constraint NotNull --onProperty startDate;
description add-notNull-message --onProperty startDate --title "The clearance start date is required" --text "The clearance start date is required";
description add-notNull-message --onProperty startDate --title "La date du début du solde est réquise" --text "La date du début du solde est réquise" --locale fr;
display add-toString-field --field startDate;
display add-list-field --field startDate;
format add-date-pattern --onProperty startDate --pattern "dd-MM-yyyy"; 

field temporal --type TIMESTAMP --named endDate; 
@/*  pattern=dd-MM-yyyy Date*/;
description add-field-description --onProperty endDate --title "Clearance End Date" --text "End date for this clearance";
description add-field-description --onProperty endDate --title "Date Fin Solde" --text "Date de fin du solde." --locale fr;
constraint NotNull --onProperty endDate;
description add-notNull-message --onProperty endDate --title "The clearance end date is required" --text "The clearance end date is required";
description add-notNull-message --onProperty endDate --title "La date de fin du solde est réquise" --text "La date de fin du solde est réquise" --locale fr;
display add-toString-field --field endDate;
display add-list-field --field endDate;
format add-date-pattern --onProperty endDate --pattern "dd-MM-yyyy"; 

field number --named discountRate --type java.math.BigDecimal;
@/*   Default=5 */;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "Discount rate for this clearance.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise pour ce solde." --locale fr;
constraint NotNull --onProperty discountRate;
description add-notNull-message --onProperty discountRate --title "The clearance discount rate is required" --text "The clearance discount rate is required";
description add-notNull-message --onProperty discountRate --title "Le taux de remise du solde est réquis" --text "Le taux de remise du solde est réquis" --locale fr;
constraint DecimalMin --onProperty discountRate --min 1.0;
description add-decimalMin-message --onProperty discountRate --title "The clearance minimum discount rate is 1%" --text "The clearance minimum discount rate is 1%";
description add-decimalMin-message --onProperty discountRate --title "Le taux de remise minimum du solde est de 1%" --text "Le taux de remise minimum du solde est de 1%" --locale fr;
constraint DecimalMax --onProperty discountRate --max 100.0;
description add-decimalMax-message --onProperty discountRate --title "The clearance maximum discount rate is 100%" --text "The clearance maximum discount rate is 100%";
description add-decimalMax-message --onProperty discountRate --title "Le taux de remise maximum du solde est de 100%" --text "Le taux de remise maximum du solde est de 100%" --locale fr;
format add-number-type --onProperty discountRate --type PERCENTAGE;
display add-toString-field --field discountRate;
display add-list-field --field discountRate;

field custom --named clearanceState --type ~.jpa.DocumentProcessingState.java ;
@/* default DocumentProcessingState.ONGOING */;
description add-field-description --onProperty clearanceState --title "Clearance State" --text "The clearance state";
description add-field-description --onProperty clearanceState --title "État du Solde" --text "État du solde" --locale fr;
enum enumerated-field --onProperty clearanceState ;
display add-toString-field --field clearanceState;
display add-list-field --field clearanceState;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this clearance configuration is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si cette configuration solde est active ou non" --locale fr;
@/* default=Boolean.TRUE */;
display add-list-field --field active;


cd ~~;


@/* Entity PackagingMode, ModeConditionnement */;
entity --named PackagingMode --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Packaging Mode" --text "The product packaging mode";
description add-class-description --locale fr --title "Mode de Conditionement" --text "Le mode de conditionnement du produit";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;

field string --named name;
description add-field-description --onProperty name --title "Packaging Mode Name" --text "The name of this packaging mode";
description add-field-description --onProperty name --title "Libelle du Mode de Conditionement" --text "Nom du mode de conditionnement" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

cd ~~;


@/* Entity Section */;
entity --named Section --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Section" --text "A section in the storage of this pharmacie.";
description add-class-description  --locale fr --title "Rayon" --text "Un rayon dans le magasin de cette pharmacie";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;

field string --named sectionCode;
description add-field-description --onProperty sectionCode --title "Section Code" --text "The code of this section";
description add-field-description --onProperty sectionCode --title "Code Rayon" --text "Le code du rayon" --locale fr;
display add-toString-field --field sectionCode;
display add-list-field --field sectionCode;

field string --named name;
description add-field-description --onProperty name --title "Section Name" --text "The name of this section";
description add-field-description --onProperty name --title "Nom du Rayon" --text "Le nom du rayon" --locale fr;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The section name is required" --text "The section name is required";
description add-notNull-message --onProperty name --title "Le nom du rayon est réquis" --text "Le nom du rayon est réquis" --locale fr;
display add-list-field --field name;

field string --named displayName;
description add-field-description --onProperty displayName --title "Display Name" --text "Field to display the name of this section";
description add-field-description --onProperty displayName --title "Nom Affiche" --text "Champ affichant le nom du rayon du nom du rayon" --locale fr;
display add-list-field --field displayName;

field string --named position;
description add-field-description --onProperty position --title "Position" --text "Code to identify the position of his section";
description add-field-description --onProperty position --title "Position" --text "Code identifiant la position du rayon." --locale fr;
display add-list-field --field position;

field string --named geoCode;
description add-field-description --onProperty geoCode --title "Geographic Code" --text "Geographic code for the identification of the position of this article in the pharmacie";
description add-field-description --onProperty geoCode --title "Code Géographique" --text "Code géographique identifiant physiquement un produit dans la pharmacie" --locale fr;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "Description of the section";
description add-field-description --onProperty description --title "Description" --text "Description du rayon" --locale fr;
constraint Size --onProperty description --max 256;
description add-size-message --onProperty description --title "The description must have less than 256 characters" --text "The description must have less than 256 characters";
description add-size-message --onProperty description --title "La description doit avoir moins de 256 caractères" --text "La description doit avoir moins de 256 caractères" --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Agency in which the section is located";
description add-field-description --onProperty agency --title "Agency" --text "Agency dans lequel le rayon se trouve." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX ;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "An agency must be selected" --text "An agency must be selected";
description add-notNull-message --onProperty agency --title "Une agence doit être sélectionné" --text "Une agence doit être sélectionné" --locale fr;



cd ~~ ;



@/* ================================== */;
@/* Product */;

@/* Entity ProductFamily */;
entity --named ProductFamily --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Product Family" --text "The product family";
description add-class-description  --locale fr --title "Famille Produit" --text "La famille produit";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;

field string --named code;
description add-field-description --onProperty code --title "Product Family Code" --text "The code of this product family";
description add-field-description --onProperty code --title "Code de la Famille Produit" --text "Le code de la famille produit" --locale fr;
display add-toString-field --field name;
display add-list-field --field code;

field string --named name;
description add-field-description --onProperty name --title "Product Name" --text "The name of this product family";
description add-field-description --onProperty name --title "Libelle du produit" --text "Le nom de la famille produit" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "The description of this product family";
description add-field-description --onProperty description --title "Description" --text "La description de la famille produit." --locale fr;
constraint Size --onProperty description --max 256;
description add-size-message --onProperty description --title "The description must have less than 256 characters" --text "The description must have less than 256 characters";
description add-size-message --onProperty description --title "La description doit avoir moins de 256 caractères" --text "La description doit avoir moins de 256 caractères" --locale fr;

field manyToOne --named parentFamilly --fieldType ~.jpa.ProductFamily;
description add-field-description --onProperty parentFamilly --title "Parent Familly" --text "The parent familly";
description add-field-description --onProperty parentFamilly --title "Famille Parent" --text "La famille parent du produit " --locale fr;
association set-selection-mode --onProperty parentFamilly --selectionMode COMBOBOX;
association set-type --onProperty parentFamilly --type AGGREGATION --targetEntity ~.jpa.ProductFamily;

cd ~~;


@/* Entity Article */;
entity --named Article --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Article" --text "An article or any oder drug sold in the pharmacy";
description add-class-description  --locale fr --title "Article" --text "Un produit ou medicament en vente dans cette pharmacie";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;
group add --grouper ~.jpa.WorkGroup --named WAREHOUSEMAN;
@/* TODO access add-permission  --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission  --action CREATE --toRole WAREHOUSEMAN*/;
group add --grouper ~.jpa.WorkGroup --named CASHIER;
@/* TODO access add-permission  --action READ --toRole CASHIER*/;
group add --grouper ~.jpa.WorkGroup --named SALES;
@/* TODO access add-permission  --action READ --toRole SALES*/;

field string --named articleName;
description add-field-description --onProperty articleName --title "Article Name" --text "The name of this article";
description add-field-description --onProperty articleName --title "Nom de cet Article" --text "Le nom du produit" --locale fr;
display add-toString-field --field articleName;
display add-list-field --field articleName;

field string --named pic; 
@/* Unique */;
description add-field-description --onProperty pic --title "Product Identification Code" --text "The standard product identification code of this product.";
description add-field-description --onProperty pic --title "Code Identifiant Prouit" --text "Le Code identifiant standard du produit." --locale fr;
display add-toString-field --field pic;
display add-list-field --field pic;

field string --named manufacturer;
description add-field-description --onProperty manufacturer --title "Manufacturer name" --text "The name of this article";
description add-field-description --onProperty manufacturer --title "Fabricant" --text "Le nom du fabricant du produit" --locale fr;
display add-list-field --field manufacturer;

field manyToOne --named section --fieldType ~.jpa.Section;
description add-field-description --onProperty section --title "Section" --text "The section in which the product is stored";
description add-field-description --onProperty section --title "Rayon" --text "Le rayon dans lequel le produit est classé." --locale fr;
association set-selection-mode --onProperty section --selectionMode COMBOBOX;
association set-type --onProperty section --type AGGREGATION --targetEntity ~.jpa.Section;
@/* TODO access add-permission --entity ~.jpa.Section --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Section --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Section --action READ --toRole SALES*/;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this article is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si le produit est active ou non" --locale fr;
display add-list-field --field active;

field manyToOne --named family --fieldType ~.jpa.ProductFamily;
description add-field-description --onProperty family --title "Product Family" --text "Specifies the product family of this article.";
description add-field-description --onProperty family --title "Famille Produit" --text "Spécifie la famille de produit à laquelle appartient le produit." --locale fr;
association set-selection-mode --onProperty family --selectionMode COMBOBOX;
association set-type --onProperty family --type AGGREGATION --targetEntity ~.jpa.ProductFamily;
@/* TODO access add-permission --entity ~.jpa.ProductFamily --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.ProductFamily --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.ProductFamily --action READ --toRole SALES*/;

field number --named qtyInStock --type java.math.BigDecimal;
description add-field-description --onProperty qtyInStock --title "Quantity in Stock" --text "The quantity of this article in stock.";
description add-field-description --onProperty qtyInStock --title "Quantité en Stock" --text "Quantité reelle de produits dans le stock." --locale fr;
display add-list-field --field qtyInStock;

field number --named pppu --type java.math.BigDecimal;
description add-field-description --onProperty pppu --title "Purchase Price per Unit" --text "Purchase price per unit.";
description add-field-description --onProperty pppu --title "Prix Unitaire Achat" --text "Prix achat unitaire du produit." --locale fr;

field number --named sppu --type java.math.BigDecimal;
description add-field-description --onProperty sppu --title "Sales Price per Unit" --text "Sales price per unit.";
description add-field-description --onProperty sppu --title "Prix de Vente Unitaire" --text "Prix de vente unitaire du produit" --locale fr;
display add-list-field --field sppu;

field long --named maxQtyPerPO --primitive false;
description add-field-description --onProperty maxQtyPerPO --title "Max Quantity per PO" --text "Maximal quantity per purchase order";
description add-field-description --onProperty maxQtyPerPO --title "Quantite Maximale par Commande" --text "Quantite maximale de produits commandable" --locale fr;

field number --named maxDiscountRate --type java.math.BigDecimal;
@/* Default=5 */;
description add-field-description --onProperty maxDiscountRate --title "Max Discount Rate" --text "Maximal discount rate given to buyers of this product.";
description add-field-description --onProperty maxDiscountRate --title "Taux Maximal Remise" --text "Taux de remise max en % accordes aux utilisateurs sur le produit" --locale fr;
format add-number-type --onProperty maxDiscountRate --type PERCENTAGE;

field number --named totalStockPrice --type java.math.BigDecimal; 
@/* Default=0, calcul=  Somme(prix vente  des lignes approvisionnement du produit*qte de chaque ligne)*/;
description add-field-description --onProperty totalStockPrice --title "Total Stock Price" --text "Total value of products in stock";
description add-field-description --onProperty totalStockPrice --title "Valeure Total du Stock" --text "Valeure totale des produits en stocks" --locale fr;
format add-number-type --onProperty totalStockPrice --type CURRENCY;

field temporal --type TIMESTAMP --named lastStockEntry; 
@/* Pattern= dd-MM-yyy */;
description add-field-description --onProperty lastStockEntry --title "Last Delivery Date" --text "Last purchase delivery date for this article";
description add-field-description --onProperty lastStockEntry --title "Date Derniere Livraison" --text "Date de la derniere livraison achat pour ce produit" --locale fr;
format add-date-pattern --onProperty lastStockEntry --pattern "dd-MM-yyyy"; 

field temporal --type TIMESTAMP --named lastOutOfStock; 
@/*pattern= dd-MM-yyyy  (qtéStock=0) */;
description add-field-description --onProperty lastOutOfStock --title "Last Out of Stock Date" --text "Date of last out of stock for this article";
description add-field-description --onProperty lastOutOfStock --title "Date Derniere Rupture de stock" --text "Date de la derniere rupture de stock pour ce produit" --locale fr;
format add-date-pattern --onProperty lastOutOfStock --pattern "dd-MM-yyyy"; 

field manyToOne --named defaultSalesMargin --fieldType ~.jpa.SalesMargin; 
description add-field-description --onProperty defaultSalesMargin --title "Sales Margin" --text "The sales margin on this product.";
description add-field-description --onProperty defaultSalesMargin --title "Taux de Marge" --text "Le taux de marge du produit." --locale fr;
association set-selection-mode --onProperty defaultSalesMargin --selectionMode COMBOBOX;
association set-type --onProperty defaultSalesMargin --type AGGREGATION --targetEntity ~.jpa.SalesMargin;
@/* TODO access add-permission --entity ~.jpa.SalesMargin --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.SalesMargin --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.SalesMargin --action READ --toRole SALES*/;

field manyToOne --named packagingMode --fieldType ~.jpa.PackagingMode; 
description add-field-description --onProperty packagingMode --title "Packaging Mode" --text "THe product packaging mode";
description add-field-description --onProperty packagingMode --title "Mode de Conditionement" --text "Le mode de conditionnement du produit" --locale fr;
association set-selection-mode --onProperty packagingMode --selectionMode COMBOBOX;
association set-type --onProperty packagingMode --type AGGREGATION --targetEntity ~.jpa.PackagingMode;
@/* TODO access add-permission --entity ~.jpa.PackagingMode --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.PackagingMode --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.PackagingMode --action READ --toRole SALES*/;

field boolean --named authorizedSale --primitive false; 
@/*  vente_autorisee  default=true  */;
description add-field-description --onProperty authorizedSale --title "Product Identification Code" --text "Allows to release a prouct for sale.";
description add-field-description --onProperty authorizedSale --title "Code Identifiant Prouit" --text "Autorise ou non un produit à la vente" --locale fr;
display add-list-field --field authorizedSale;

field boolean --named approvedOrder --primitive false; 
description add-field-description --onProperty approvedOrder --title "Approved Order" --text "Document if the next order of this product is approved.";
description add-field-description --onProperty approvedOrder --title "Commande Autorisée" --text "Autorise ou non le produit à la commande." --locale fr;

field long --named maxStockQty --primitive false;
description add-field-description --onProperty maxStockQty --title "Max Stock Quantity" --text "Sets the standard max stock quantity for this product.";
description add-field-description --onProperty maxStockQty --title "Quantité Plafond" --text "Permet de fixer le max de qté en stock de produit." --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The agency hosting this product.";
description add-field-description --onProperty agency --title "Filiale" --text "La filiale à laquelle le produit appartient." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole SALES*/;


field manyToOne --named clearanceConfig --fieldType ~.jpa.ClearanceConfig;
description add-field-description --onProperty clearanceConfig --title "Clearance Configuration" --text "Configuration for the clearance of this product.";
description add-field-description --onProperty clearanceConfig --title "Configuration Solde" --text "Permet de créer une configuration du solde pour ce produit." --locale fr;
association set-selection-mode --onProperty clearanceConfig --selectionMode COMBOBOX;
association set-type --onProperty clearanceConfig --type AGGREGATION --targetEntity ~.jpa.ClearanceConfig;
@/* TODO access add-permission --entity ~.jpa.ClearanceConfig --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.ClearanceConfig --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.ClearanceConfig --action READ --toRole SALES*/;


field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 
cd ~~;



@/* Entity ArticleEquivalence */;
entity --named ArticleEquivalence --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Article Equivalence" --text "An article equivalent to this article";
description add-class-description  --locale fr --title "Equivalence Produit" --text "Un article équivalent à cet article";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;
group add --grouper ~.jpa.WorkGroup --named WAREHOUSEMAN;
@/* TODO access add-permission  --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission  --action CREATE --toRole WAREHOUSEMAN*/;
group add --grouper ~.jpa.WorkGroup --named CASHIER;
@/* TODO access add-permission  --action READ --toRole CASHIER*/;
group add --grouper ~.jpa.WorkGroup --named SALES;
@/* TODO access add-permission  --action READ --toRole SALES*/;


field manyToOne --named mainArticle --fieldType ~.jpa.Article;
description add-field-description --onProperty mainArticle --title "Main Article" --text "The main Article";
description add-field-description --onProperty mainArticle --title "Article Principal" --text "Article principale." --locale fr;
association set-selection-mode --onProperty mainArticle --selectionMode COMBOBOX;
association set-type --onProperty mainArticle --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field mainArticle.articleName;
display add-list-field --field mainArticle.articleName;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole SALES*/;

field manyToOne --named equivalentArticle --fieldType ~.jpa.Article;
description add-field-description --onProperty equivalentArticle --title "Equivalent Article" --text "The Equivalent Article";
description add-field-description --onProperty equivalentArticle --title "Article Equivalent" --text "Article equivalent." --locale fr;
association set-selection-mode --onProperty equivalentArticle --selectionMode COMBOBOX;
association set-type --onProperty equivalentArticle --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field equivalentArticle.articleName;
display add-list-field --field equivalentArticle.articleName;



cd ~~;



@/* Entity Supplier */;
entity --named Supplier --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Supplier" --text "The supplier";
description add-class-description  --locale fr --title "Fournisseur" --text "Le fournisseur";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;
group add --grouper ~.jpa.WorkGroup --named WAREHOUSEMAN;
@/* TODO access add-permission  --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission  --action CREATE --toRole WAREHOUSEMAN*/;
group add --grouper ~.jpa.WorkGroup --named CASHIER;
@/* TODO access add-permission  --action READ --toRole CASHIER*/;
group add --grouper ~.jpa.WorkGroup --named SALES;
@/* TODO access add-permission  --action READ --toRole SALES*/;

field string --named name;
description add-field-description --onProperty name --title "Supplier Name" --text "The name of the supplier.";
description add-field-description --onProperty name --title "Nom du Fournisseur" --text "Le nom du fournisseur." --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The phone number of the supplier";
description add-field-description --onProperty phone --title "Téléphone" --text "Le téléphone du fournisseur" --locale fr;
display add-list-field --field phone;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile" --text "The mobile phone number of the supplier";
description add-field-description --onProperty mobile --title "Mobile" --text "Le téléphone mobile du fournisseur" --locale fr;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the supplier";
description add-field-description --onProperty fax --title "Fax" --text "Le fax du fournisseur" --locale fr;
display add-list-field --field fax;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email of the supplier";
description add-field-description --onProperty email --title "Email" --text "Le email du fournisseur" --locale fr;
display add-list-field --field email;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street of this supplier";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue de ce fournisseur" --locale fr;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code of this supplier";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de ce fournisseur" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this supplier";
description add-field-description --onProperty city --title "Ville" --text "La ville de ce fournisseur" --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The country of this supplier";
description add-field-description --onProperty country --title "Pays" --text "Le pays de ce fournisseur" --locale fr;

field string --named internetSite;
description add-field-description --onProperty internetSite --title "Web Site" --text "The web site of the supplier";
description add-field-description --onProperty internetSite --title "Site Internet" --text "Le site internet du fournisseur" --locale fr;

field string --named responsable;
description add-field-description --onProperty responsable --title "Person In Charge" --text "The person in charge at the supplier side.";
description add-field-description --onProperty responsable --title "Responsable" --text "Le responsable chez le fournisseur." --locale fr;

field string --named revenue;
description add-field-description --onProperty revenue --title "Revenue" --text "The revenue of this supplier side.";
description add-field-description --onProperty revenue --title "Chiffre d Affaires" --text "Le chiffre d affaires de ce fournisseur." --locale fr;

field string --named taxIdNumber;
description add-field-description --onProperty taxIdNumber --title "Tax Id Number" --text "The tax id number of this supplier";
description add-field-description --onProperty taxIdNumber --title "Numéro du Contribuable" --text "Le numéro du contribuable de ce fournisseur" --locale fr;

field string --named commRegNumber;
description add-field-description --onProperty commRegNumber --title "Commercial Register Number" --text "The commercial register number of this supplier";
description add-field-description --onProperty commRegNumber --title "Numéro Registre du Commerce" --text "Le numéro du registre de commerce de ce fournisseur" --locale fr;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "Description of this supplier";
description add-field-description --onProperty description --title "Description" --text "Description de ce fournisseur" --locale fr;
constraint Size --onProperty description --max 256;
description add-size-message --onProperty description --title "The description must have less than 256 characters" --text "The description must have less than 256 characters";
description add-size-message --onProperty description --title "La description doit avoir moins de 256 caractères" --text "La description doit avoir moins de 256 caractères" --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

cd ~~;


@/* Enum ProcurementOrderType */;
java new-enum-type --named ProcurementOrderType --package ~.jpa ;
enum add-enum-class-description --title "Procurement Order Type" --text "Procurement order type";
enum add-enum-class-description  --locale fr --title "Type Commande Fournisseur" --text "Type commande fournisseur";
java new-enum-const ORDINARY;
enum add-enum-constant-description --onConstant ORDINARY --title "Ordinary" --text "Ordinary procurement order";
enum add-enum-constant-description --onConstant ORDINARY --title "Ordinaire" --text "Commande fournisseur ordinaire" --locale fr ;
java new-enum-const PACKAGED;
enum add-enum-constant-description --onConstant PACKAGED --title "Packaged" --text "Packaged procurement order";
enum add-enum-constant-description --onConstant PACKAGED --title "Emballée" --text "Commande fournisseur emballée" --locale fr ;
java new-enum-const SPECIAL;
enum add-enum-constant-description --onConstant SPECIAL --title "Special" --text "Special procurement order";
enum add-enum-constant-description --onConstant SPECIAL --title "Spéciale" --text "Commande fournisseur spéciale" --locale fr ;


cd ~~;


@/* Enum  ProcmtOrderTriggerMode */;
java new-enum-type --named ProcmtOrderTriggerMode --package ~.jpa ;
enum add-enum-class-description --title "Procurement Order Trigger Mode" --text "Procurement order trigger mode";
enum add-enum-class-description  --locale fr --title "Criteres de Preparation de Commande Fournisseur" --text "Criteres pour initialisation de commande fournisseur";
java new-enum-const MANUAL;
enum add-enum-constant-description --onConstant MANUAL --title "Manual" --text "Manual procurement order trigger mode";
enum add-enum-constant-description --onConstant MANUAL --title "Manuelle" --text "Initialisation de commande fournisseur manuelle" --locale fr ;
java new-enum-const STOCK_SHORTAGE;
enum add-enum-constant-description --onConstant STOCK_SHORTAGE --title "Stock shortage" --text "Procurement order trigger on stock shortage";
enum add-enum-constant-description --onConstant STOCK_SHORTAGE --title "Pénurie de Stock" --text "Commande fournisseur initialisé sur pénurie" --locale fr ;
java new-enum-const MOST_SOLD;
enum add-enum-constant-description --onConstant MOST_SOLD --title "Most product sold" --text "Procurement order trigerred for most sold product";
enum add-enum-constant-description --onConstant MOST_SOLD --title "Produits les Plus Vendu" --text "Commande initialisé pour les produits les plus vendus" --locale fr ;



cd ~~;



@/* Entity ProcurementOrderItem */;
entity --named ProcurementOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order Item" --text "Procurement order item";
description add-class-description  --locale fr --title "Ligne Commande Fournisseur" --text "Ligne de commande fournisseur";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;
group add --grouper ~.jpa.WorkGroup --named WAREHOUSEMAN;
@/* TODO access add-permission  --action READ --toRole WAREHOUSEMAN*/;
group add --grouper ~.jpa.WorkGroup --named CASHIER;
@/* TODO access add-permission  --action READ --toRole CASHIER*/;
group add --grouper ~.jpa.WorkGroup --named SALES;
@/* TODO access add-permission  --action READ --toRole SALES*/;

field string --named indexLine; 
description add-field-description --onProperty indexLine --title "Line Index" --text "Index for searching through purchase order items";
description add-field-description --onProperty indexLine --title "Index de Ligne" --text "Index permettant de rechercher la ligne de commande fournisseur" --locale fr;
display add-list-field --field indexLine;

field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The article for this lot";
description add-field-description --onProperty article --title "Produit" --text "Le produit du lot" --locale fr;
association set-selection-mode --onProperty article --selectionMode COMBOBOX;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;
constraint NotNull --onProperty article;
description add-notNull-message --onProperty article --title "The article for this lot must be selected" --text "The article for this lot must be selected";
description add-notNull-message --onProperty article --title "Le produit de ce lot doit être sélectionné" --text "Le produit de ce lot doit être sélectionné" --locale fr;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole SALES*/;

field temporal --type TIMESTAMP --named recCreated; 
@/* Pattern=dd-MM-yyy  */;
description add-field-description --onProperty recCreated --title "Record Created" --text "Order item record creation date";
description add-field-description --onProperty recCreated --title "Date de Saisie" --text "Date à laquelle la ligne de commande a été saisi(crée)" --locale fr;
format add-date-pattern --onProperty recCreated --pattern "dd-MM-yyyy HH:mm"; 

field number --named qtyOrdered --type java.math.BigDecimal;
description add-field-description --onProperty qtyOrdered --title "Quantity Ordered" --text "The quantity ordered in this lot.";
description add-field-description --onProperty qtyOrdered --title "Quantité Commandée" --text "La quantité de produits commandés dans le lot." --locale fr;
display add-toString-field --field qtyOrdered;
display add-list-field --field qtyOrdered;
@/* Default=0 */;

field number --named availableQty --type java.math.BigDecimal;
description add-field-description --onProperty availableQty --title "Available Quantity" --text "The quantity available at the supplier.";
description add-field-description --onProperty availableQty --title "Quantité Disponible" --text "La quantité de produits disponible chez le fournisseur." --locale fr;
@/* Default=0 quantité_fournie*/;

field manyToOne --named creatingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this procurement order item";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur ayant crée cet ligne de commande" --locale fr;
constraint NotNull --onProperty creatingUser;
description add-notNull-message --onProperty creatingUser --title "The creating user must be selected" --text "The creating user must be selected";
description add-notNull-message --onProperty creatingUser --title "Utilisateur créant doit être sélectionné" --text "Utilisateur créant doit être sélectionné" --locale fr;
association set-selection-mode --onProperty creatingUser --selectionMode COMBOBOX;
association set-type --onProperty creatingUser --type AGGREGATION --targetEntity ~.jpa.Login;
@/* TODO access add-permission --entity ~.jpa.Login --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Login --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Login --action READ --toRole SALES*/;

field boolean --named valid --primitive false;
description add-field-description --onProperty valid --title "Valid" --text "Determines if the order item is valid or not according to the expectations of the supplier.";
description add-field-description --onProperty valid --title "Valide" --text "Détermine si la ligne de commande est valide ou pas selon les attentes du fournisseur." --locale fr;
display add-list-field --field valid;

field number --named purchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty purchasePrice --title "Minimum Purchase Price Suggested" --text "Minimum suggested purchase price suggested for this purchase order item.";
description add-field-description --onProperty purchasePrice --title "Prix d Achat " --text "Prix d achat minimum proposé d un produit de la ligne de commande Fournisseur." --locale fr;
format add-number-type --onProperty purchasePrice --type CURRENCY;
display add-list-field --field purchasePrice;
@/* Default=0 */;

field number --named salesPrice --type java.math.BigDecimal;
description add-field-description --onProperty salesPrice --title "Minimum Sales Price" --text "Minimum sales price for the product of this procurement order item.";
description add-field-description --onProperty salesPrice --title "Prix de Vente Minimum" --text "Prix de vente minimum pour les produits de cette ligne de commande fournisseur." --locale fr;
format add-number-type --onProperty salesPrice --type CURRENCY;
display add-list-field --field salesPrice;
@/* Default=0 */;

field number --named totalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasePrice --title "Total Purchase Price" --text "Total purchase price for this procurement order item.";
description add-field-description --onProperty totalPurchasePrice --title "Prix d Achat Totale" --text "Prix achat totale pour cette ligne de commande fournisseur." --locale fr;
format add-number-type --onProperty totalPurchasePrice --type CURRENCY;
display add-list-field --field totalPurchasePrice;
@/* Default=0, Formule=prix_achat_min*quantité_commandée */;


cd ~~


@/* Entity ProcurementOrder */;
entity --named ProcurementOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Procurement Order" --text "Procurement order";
description add-class-description  --locale fr --title "Commande Fournisseur" --text "Commande fournisseur";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;
group add --grouper ~.jpa.WorkGroup --named WAREHOUSEMAN;
@/* TODO access add-permission  --action READ --toRole WAREHOUSEMAN*/;
group add --grouper ~.jpa.WorkGroup --named CASHIER;
@/* TODO access add-permission  --action READ --toRole CASHIER*/;
group add --grouper ~.jpa.WorkGroup --named SALES;
@/* TODO access add-permission  --action READ --toRole SALES*/;

field string --named procurementOrderNumber;
description add-field-description --onProperty procurementOrderNumber --title "Procurement Order Number" --text "The procurement order number";
description add-field-description --onProperty procurementOrderNumber --title "Numéro Commande Fournisseur" --text "Numéro de la commande fournisseur" --locale fr;
display add-toString-field --field procurementOrderNumber;
display add-list-field --field procurementOrderNumber;
constraint NotNull --onProperty procurementOrderNumber;
description add-notNull-message --onProperty procurementOrderNumber --title "The procurement order number is required" --text "The procurement order number is required";
description add-notNull-message --onProperty procurementOrderNumber --title "Le numéro de la commande fournisseur est réquis" --text "Le numéro de la commande fournisseur est réquis" --locale fr;

field temporal --type TIMESTAMP --named submissionDate; 
@/* pattern=dd-MM-yyyy HH:mm */;
description add-field-description --onProperty submissionDate --title "Submission Date" --text "Date of submission of the order to Suplier created from the module Ubipharm";
description add-field-description --onProperty submissionDate --title "Date de Soumission" --text "Date de soumission de la commande au fournisseur crée à partir du module d Ubipharm" --locale fr;
format add-date-pattern --onProperty submissionDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named createdDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty createdDate --title "Created Date" --text "The creation date";
description add-field-description --onProperty createdDate --title "Date de Creation" --text "La date de creation" --locale fr;
format add-date-pattern --onProperty createdDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named creatingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this procurement order item";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur ayant crée cet ligne de commande" --locale fr;
constraint NotNull --onProperty creatingUser;
description add-notNull-message --onProperty creatingUser --title "The creating user must be selected" --text "The creating user must be selected";
description add-notNull-message --onProperty creatingUser --title "Utilisateur créant doit être sélectionné" --text "Utilisateur créant doit être sélectionné" --locale fr;
association set-selection-mode --onProperty creatingUser --selectionMode COMBOBOX;
association set-type --onProperty creatingUser --type AGGREGATION --targetEntity ~.jpa.Login;
@/* TODO access add-permission --entity ~.jpa.Login --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Login --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Login --action READ --toRole SALES*/;

field custom --named procmtOrderTriggerMode --type ~.jpa.ProcmtOrderTriggerMode;
description add-field-description --onProperty procmtOrderTriggerMode --title "Procurement Order Trigger Mode" --text "Procurement Order Trigger Mode.";
description add-field-description --onProperty procmtOrderTriggerMode --title "Criteres de Preparation" --text "Criteres de Preparation de Commande Fournisseur." --locale fr;
enum enumerated-field --onProperty procmtOrderTriggerMode ;

field custom --named procurementOrderType --type ~.jpa.ProcurementOrderType;
description add-field-description --onProperty procurementOrderType --title "Procurement Order Type" --text "Procurement Order Type.";
description add-field-description --onProperty procurementOrderType --title "Type Commande Fournisseur" --text "Type Commande Fournisseur." --locale fr;
enum enumerated-field --onProperty procurementOrderType ;

field manyToOne --named supplier --fieldType ~.jpa.Supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;
association set-selection-mode --onProperty supplier --selectionMode  COMBOBOX;
association set-type --onProperty supplier --type AGGREGATION --targetEntity ~.jpa.Supplier;
@/* TODO access add-permission --entity ~.jpa.Supplier --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Supplier --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Supplier --action READ --toRole SALES*/;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The Agency mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty agency --title "Agency" --text "L agence mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;
association set-selection-mode --onProperty agency --selectionMode  COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole SALES*/;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this purchase order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette approvisionement." --locale fr;
constraint NotNull --onProperty amountBeforeTax;
description add-notNull-message --onProperty amountBeforeTax --title "The total amount before tax for this purchase order is required" --text "The total amount before tax for this purchase order is required";
description add-notNull-message --onProperty amountBeforeTax --title "Le montant total hors Taxes pour cette approvisionement est réquis" --text "Le montant total hors Taxes pour cette approvisionement est réquis" --locale fr;
display add-list-field --field amountBeforeTax;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this purchase order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette approvisionement." --locale fr;
format add-number-type --onProperty amountAfterTax --type CURRENCY;
display add-list-field --field amountAfterTax;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this purchase order.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Montant de la remise de l approvisionement." --locale fr;
format add-number-type --onProperty amountDiscount --type CURRENCY;
display add-list-field --field amountDiscount;

field number --named netAmountToPay --type java.math.BigDecimal;
description add-field-description --onProperty netAmountToPay --title "Net Amount to Pay" --text "Teh net amount to pay.";
description add-field-description --onProperty netAmountToPay --title "Montant net a payer" --text "Montant de la remise de l approvisionement." --locale fr;
format add-number-type --onProperty netAmountToPay --type CURRENCY;
display add-list-field --field netAmountToPay;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur la valeur ajoute" --locale fr;
association set-selection-mode --onProperty vat --selectionMode  COMBOBOX;
association set-type --onProperty vat --type AGGREGATION --targetEntity ~.jpa.VAT;
display add-list-field --field vat.rate;
@/* TODO access add-permission --entity ~.jpa.VAT --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.VAT --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.VAT --action READ --toRole SALES*/;

field manyToOne --named currency --fieldType ~.jpa.Currency;
description add-field-description --onProperty currency --title "Currency" --text "The currency used for the conversion of the currency stated on the delivery note in local currency (FCFA).";
description add-field-description --onProperty currency --title "Devise" --text "La devise utilisée pour la conversion de la monnaie mentionnée sur le bordereau de livraison en monnaie locale(FCFA)." --locale fr;
association set-selection-mode --onProperty currency --selectionMode  COMBOBOX;
association set-type --onProperty currency --type AGGREGATION --targetEntity ~.jpa.Currency;
@/* TODO access add-permission --entity ~.jpa.Currency --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Currency --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Currency --action READ --toRole SALES*/;


field custom --named procurementProcessingState --type ~.jpa.DocumentProcessingState.java;
description add-field-description --onProperty procurementProcessingState --title "Procurement State" --text "The delivery processing state.";
description add-field-description --onProperty procurementProcessingState --title "Status Commande" --text "L etat de traitement de cette Commande." --locale fr;
enum enumerated-field --onProperty procurementProcessingState ;

field oneToMany --named procurementOrderItems --fieldType ~.jpa.ProcurementOrderItem --inverseFieldName procurementOrder;
description add-field-description --onProperty procurementOrderItems --title "Procurement Order Items" --text "Procurement Order Items";
description add-field-description --onProperty procurementOrderItems --title "Ligne de commande" --text "ligne de commande fournisseur" --locale fr;
association set-type --onProperty procurementOrderItems --type COMPOSITION --targetEntity ~.jpa.ProcurementOrderItem;
association set-selection-mode --onProperty procurementOrderItems --selectionMode TABLE;
@/* TODO access add-permission --entity ~.jpa.ProcurementOrderItem --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.ProcurementOrderItem --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.ProcurementOrderItem --action READ --toRole SALES*/;

cd ../ProcurementOrderItem.java;
description add-field-description --onProperty procurementOrder --title "Procurement Order" --text "The procurement order containing this item";
description add-field-description --onProperty procurementOrder --title "Commande Fourniseur" --text "La commande fournisseur contenant cette ligne" --locale fr;
association set-type --onProperty procurementOrder --type COMPOSITION --targetEntity ~.jpa.ProcurementOrder;



cd ~~;



@/* Entity DeliveryItem */;
entity --named DeliveryItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Delivery Order Item" --text "Delivery order item";
description add-class-description  --locale fr --title "Ligne de livraison" --text "la ligne de livraison";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;
group add --grouper ~.jpa.WorkGroup --named WAREHOUSEMAN;
@/* TODO access add-permission  --action READ --toRole WAREHOUSEMAN*/;
group add --grouper ~.jpa.WorkGroup --named CASHIER;
@/* TODO access add-permission  --action READ --toRole CASHIER*/;
group add --grouper ~.jpa.WorkGroup --named SALES;
@/* TODO access add-permission  --action READ --toRole SALES*/;

field string --named lineIndex;
description add-field-description --onProperty lineIndex --title "Line Index" --text "The index of a line of this item in the PO";
description add-field-description --onProperty lineIndex --title "Index de ligne" --text "L index de la ligne de cette LAP" --locale fr;
display add-list-field --field lineIndex;

field string --named internalPic; 
description add-field-description --onProperty internalPic --title "Local PIC" --text "The internal product identification code used to identify lots during sales.";
description add-field-description --onProperty internalPic --title "CIP Maison" --text "Le code identifiant produit maison, utilisé pour identifier les lots de produits lors de la vente." --locale fr;
constraint Size --onProperty internalPic --min 7;
description add-size-message --onProperty internalPic --title "The internal product identification code must have more than 7 characters" --text "The internal product identification code must have more than 7 characters";
description add-size-message --onProperty internalPic --title "Le code pour identification de produit interne doit avoir plus de 7 caractères" --text "Le code pour identification de produit interne doit avoir plus de 7 caractères" --locale fr;
display add-list-field --field internalPic;


field string --named mainPic; 
description add-field-description --onProperty mainPic --title "Main PIC" --text "The main product identification code used to identify lots during sales.";
description add-field-description --onProperty mainPic --title "CIP Principal" --text "Le code identifiant produit principal, utilisé pour identifier les lots de produits lors de la vente." --locale fr;
constraint Size --onProperty mainPic --min 7;
description add-size-message --onProperty mainPic --title "The internal product identification code must have more than 7 characters" --text "The internal product identification code must have more than 7 characters";
description add-size-message --onProperty mainPic --title "Le code pour identification de produit interne doit avoir plus de 7 caractères" --text "Le code pour identification de produit interne doit avoir plus de 7 caractères" --locale fr;
display add-list-field --field mainPic;

field string --named secondaryPic; 
description add-field-description --onProperty secondaryPic --title "Secondary PIC" --text "The main product identification code used to identify lots during sales.";
description add-field-description --onProperty secondaryPic --title "CIP Secondaire" --text "Le code identifiant produit principal, utilisé pour identifier les lots de produits lors de la vente." --locale fr;
constraint Size --onProperty secondaryPic --min 7;
description add-size-message --onProperty secondaryPic --title "The secondary product identification code must have more than 7 characters" --text "The internal product identification code must have more than 7 characters";
description add-size-message --onProperty secondaryPic --title "Le code pour identification de produit secondaire doit avoir plus de 7 caractères" --text "Le code pour identification de produit interne doit avoir plus de 7 caractères" --locale fr;
display add-list-field --field secondaryPic;


field string --named articleName;
description add-field-description --onProperty articleName --title "Article Name" --text "The number of the invoice.";
description add-field-description --onProperty articleName --title "Nom de l article" --text "Le numéro de cette facture." --locale fr;
display add-toString-field --field articleName;
display add-list-field --field articleName;


field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The article fo this delivery order item";
description add-field-description --onProperty article --title "Produit" --text "Le produit de cette ligne de livraison" --locale fr;
association set-selection-mode --onProperty article --selectionMode COMBOBOX;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;
constraint NotNull --onProperty article;
description add-notNull-message --onProperty article --title "The article fo this delivery order item must be selected" --text "The article fo this delivery order item must be selected";
description add-notNull-message --onProperty article --title "Le produit de cette ligne de livraison doit être sélectionné" --text "Le produit de cette ligne de livraison doit être sélectionné" --locale fr;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole SALES*/;

field temporal --type TIMESTAMP --named expirationDate; 
@/* Pattern=dd-MM-yyy  */;
description add-field-description --onProperty expirationDate --title "Expiration Date" --text "Expiration date for the article in this lot";
description add-field-description --onProperty expirationDate --title "Date de Peremption" --text "Date de peremption du produit dans le lot" --locale fr;
display add-list-field --field expirationDate;
format add-date-pattern --onProperty expirationDate --pattern "dd-MM-yyyy"; 

field temporal --type TIMESTAMP --named productRecCreated; 
@/* Pattern= dd-MM-yyy  */;
description add-field-description --onProperty productRecCreated --title "Product Record Created" --text "Product record creation date";
description add-field-description --onProperty productRecCreated --title "Date de Saisie du Produit" --text "Date à laquelle le produit a été saisi(crée)" --locale fr;
format add-date-pattern --onProperty productRecCreated --pattern "dd-MM-yyyy HH:mm "; 

field number --named freeQuantity --type java.math.BigDecimal;
description add-field-description --onProperty freeQuantity --title "Free Quantity" --text "The auntity of products given by the supplier free of charge during purchasing. These articles are a value aded for the products in stock.";
description add-field-description --onProperty freeQuantity --title "Quantité Gratuite" --text "La quantité de produits fournis gratuitement par le fournisseur lors de l approvisionnement. Ces produits sont une plus value pour les produits dans le stock" --locale fr;
display add-list-field --field freeQuantity;

field number --named claimedQuantity --type java.math.BigDecimal;
description add-field-description --onProperty claimedQuantity --title "Claimed Quantity" --text "The quantity of products claimed";
description add-field-description --onProperty claimedQuantity --title "Quantité reclamée" --text "Quantité de produits reclamés" --locale fr;
display add-list-field --field claimedQuantity;
@/*  Default=0 */; 

field number --named stockQuantity --type java.math.BigDecimal;
description add-field-description --onProperty stockQuantity --title "Stock Quantity" --text "The quantity of products claimed";
description add-field-description --onProperty stockQuantity --title "Quantité en Stock" --text "Quantité de produits du lot." --locale fr;
display add-list-field --field stockQuantity;
@/*  Default=0 */;  

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix de vente unitaire." --locale fr;
format add-number-type --onProperty salesPricePU --type CURRENCY;
display add-list-field --field salesPricePU;
@/*  Default=0 */; 

field number --named purchasePricePU --type java.math.BigDecimal;
description add-field-description --onProperty purchasePricePU --title "Purchase Price per Unit" --text "The purchase price per unit.";
description add-field-description --onProperty purchasePricePU --title "Prix d Achat Unitaire" --text "Prix d achat unitaire." --locale fr;
format add-number-type --onProperty purchasePricePU --type CURRENCY;
display add-list-field --field purchasePricePU;
@/*  Default=0 */; 

field number --named totalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasePrice --title "Total Purchase Price" --text "The total purchase price.";
description add-field-description --onProperty totalPurchasePrice --title "Prix d Achat Total" --text "Prix d achat totale." --locale fr;
format add-number-type --onProperty totalPurchasePrice --type CURRENCY;
display add-list-field --field totalPurchasePrice;
@/*  Default=0 */; 
@/*Formule= (prix_achat_unitaire*[qte_aprovisionée-qte_unité_gratuite]) */;

cd ~~ ;

@/* Entity Delivery */;
entity --named Delivery --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Delivery" --text "The Delivery";
description add-class-description  --locale fr --title "Livraison" --text "Une Livraison";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;
group add --grouper ~.jpa.WorkGroup --named WAREHOUSEMAN;
@/* TODO access add-permission  --action CREATE --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission  --action READ --toRole WAREHOUSEMAN*/;
group add --grouper ~.jpa.WorkGroup --named CASHIER;
@/* TODO access add-permission  --action READ --toRole CASHIER*/;
group add --grouper ~.jpa.WorkGroup --named SALES;
@/* TODO access add-permission  --action READ --toRole SALES*/;

field string --named deliveryNumber;
description add-field-description --onProperty deliveryNumber --title "Delivery  Number" --text "The Delivery order number";
description add-field-description --onProperty deliveryNumber --title "Numéro de la Livraison" --text "Numéro de la livraison" --locale fr;
display add-toString-field --field deliveryNumber;
display add-list-field --field deliveryNumber;
constraint NotNull --onProperty deliveryNumber;
description add-notNull-message --onProperty deliveryNumber --title "The Delivery order number is required" --text "The Delivery order number is required";
description add-notNull-message --onProperty deliveryNumber --title "Le numéro de  livraison est réquise" --text "Le numéro de  livraison est réquise" --locale fr;

field string --named deliverySlipNumber;
description add-field-description --onProperty deliverySlipNumber --title "Delivery Slip Number" --text "The delivery slip number (generaly available on the delivery slip)";
description add-field-description --onProperty deliverySlipNumber --title "Numéro Bordereau de Livraison" --text "Numéro de bordereau de livraison (mentionné géneralement sur le bordereau de livraison)" --locale fr;
display add-list-field --field deliverySlipNumber;
constraint NotNull --onProperty deliverySlipNumber;
description add-notNull-message --onProperty deliverySlipNumber --title "The delivery slip number is required" --text "The delivery slip number is required";
description add-notNull-message --onProperty deliverySlipNumber --title "Le numéro de bordereau de livraison est réquis" --text "Le numéro de bordereau de livraison est réquis" --locale fr;

field temporal --type TIMESTAMP --named dateOnDeliverySlip; 
description add-field-description --onProperty dateOnDeliverySlip --title "Date on Delivery Slip" --text "Date as mentioned on delivery slip";
description add-field-description --onProperty dateOnDeliverySlip --title "Date sur Bordereau" --text "Date de livraison mentionée sur le bordereau de livraison de l approvisionement" --locale fr;
display add-list-field --field dateOnDeliverySlip;
format add-date-pattern --onProperty dateOnDeliverySlip --pattern "dd-MM-yyyy"; 

field manyToOne --named creatingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this purchase order.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur ayant crée cet approvisionement." --locale fr;
constraint NotNull --onProperty creatingUser;
description add-notNull-message --onProperty creatingUser --title "The creating user must be selected" --text "The creating user must be selected";
description add-notNull-message --onProperty creatingUser --title "Utilisateur créant doit être sélectionné" --text "Utilisateur créant doit être sélectionné" --locale fr;
association set-selection-mode --onProperty creatingUser --selectionMode COMBOBOX;
association set-type --onProperty creatingUser --type AGGREGATION --targetEntity ~.jpa.Login;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Article --action READ --toRole SALES*/;

field manyToOne --named currency --fieldType ~.jpa.Currency;
description add-field-description --onProperty currency --title "Currency" --text "The currency used for the conversion of the currency stated on the delivery note in local currency (FCFA).";
description add-field-description --onProperty currency --title "Devise" --text "La devise utilisée pour la conversion de la monnaie mentionnée sur le bordereau (FCFA)." --locale fr;
association set-selection-mode --onProperty currency --selectionMode  COMBOBOX;
association set-type --onProperty currency --type AGGREGATION --targetEntity ~.jpa.Currency;

field temporal --type TIMESTAMP --named orderDate; 
description add-field-description --onProperty orderDate --title "Order Date" --text "Order date.";
description add-field-description --onProperty orderDate --title "Date de Commande" --text "Date de commande." --locale fr;
format add-date-pattern --onProperty orderDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named deliveryDate; 
description add-field-description --onProperty deliveryDate --title "Delivery Date" --text "Date on which the products where effectively delivered products.";
description add-field-description --onProperty deliveryDate --title "Date de Livraison" --text "Date à laquelle a été livrée les produits qui entrent en stock.." --locale fr;
format add-date-pattern --onProperty deliveryDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named supplier --fieldType ~.jpa.Supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The supplier mentioned on the delivery slip while products are being delivered.";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le fournisseur mentionné sur le bordereau de livraison des produits qui entrent en stock." --locale fr;
association set-selection-mode --onProperty supplier --selectionMode  COMBOBOX;
association set-type --onProperty supplier --type AGGREGATION --targetEntity ~.jpa.Supplier;
@/* TODO access add-permission --entity ~.jpa.Supplier --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Supplier --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Supplier --action READ --toRole SALES*/;

field temporal --type TIMESTAMP --named paymentDate; 
description add-field-description --onProperty paymentDate --title "Payment Date" --text "Date of settlement of this order.";
description add-field-description --onProperty paymentDate --title "Date de Règlement" --text "Date à laquelle l approvisionement a été doit être reglé auprès du fournisseur. Prévu pour recapituler les dettes fournisseur." --locale fr;
display add-list-field --field paymentDate;
format add-date-pattern --onProperty paymentDate --pattern "dd-MM-yyyy HH:mm"; 

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this delivery";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette livraison" --locale fr;
constraint NotNull --onProperty amountBeforeTax;
description add-notNull-message --onProperty amountBeforeTax --title "The total amount before tax for this delivery is required" --text "The total amount before tax for this delivery is required";
description add-notNull-message --onProperty amountBeforeTax --title "Le montant total hors Taxes pour cette livraison est réquis" --text "Le montant total hors Taxes pour cette livraison est réquis" --locale fr;
display add-list-field --field amountBeforeTax;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this purchase order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette approvisionement." --locale fr;
format add-number-type --onProperty amountAfterTax --type CURRENCY;
display add-list-field --field amountAfterTax;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this purchase order.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Montant de la remise de l approvisionement." --locale fr;
format add-number-type --onProperty amountDiscount --type CURRENCY;
display add-list-field --field amountDiscount;

field number --named netAmountToPay --type java.math.BigDecimal;
description add-field-description --onProperty netAmountToPay --title "Net Amount to Pay" --text "Teh net amount to pay.";
description add-field-description --onProperty netAmountToPay --title "Montant net a payer" --text "Montant de la remise de l approvisionement." --locale fr;
format add-number-type --onProperty netAmountToPay --type CURRENCY;
display add-list-field --field netAmountToPay;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur la valeur ajoute" --locale fr;
association set-selection-mode --onProperty vat --selectionMode  COMBOBOX;
association set-type --onProperty vat --type AGGREGATION --targetEntity ~.jpa.VAT;
display add-list-field --field vat.rate;
@/* TODO access add-permission --entity ~.jpa.VAT --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.VAT --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.VAT --action READ --toRole SALES*/;

field temporal --type TIMESTAMP --named creationDate; 
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this order.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette commande." --locale fr;
format add-date-pattern --onProperty creationDate --pattern "dd-MM-yyyy HH:mm"; 

field boolean --named claims --primitive false ;
description add-field-description --onProperty claims --title "Claims" --text "Specifies whether the purchase contains claims or not.";
description add-field-description --onProperty claims --title "Réclamations" --text "Précise si l approvisionement contient des réclamations ou non." --locale fr;
@/* default=false */;
display add-list-field --field claims;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

field custom --named deliveryProcessingState --type ~.jpa.DocumentProcessingState.java;
description add-field-description --onProperty deliveryProcessingState --title "Delivery  State" --text "The delivery processing state.";
description add-field-description --onProperty deliveryProcessingState --title "Etat  Livraison" --text "L etat de traitement de cette Livraison." --locale fr;
enum enumerated-field --onProperty deliveryProcessingState ;

field manyToOne --named receivingAgency --fieldType ~.jpa.Agency;
description add-field-description --onProperty receivingAgency --title "Agency" --text "Name of the agency in which the product was delivered.";
description add-field-description --onProperty receivingAgency --title "Filiale" --text "Nom de la filiale dans laquelle l entree en stock s effectue" --locale fr;
association set-selection-mode --onProperty receivingAgency --selectionMode  COMBOBOX;
association set-type --onProperty receivingAgency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field receivingAgency.name;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.Agency --action READ --toRole SALES*/;

field oneToMany --named deliveryItems --fieldType ~.jpa.DeliveryItem --inverseFieldName delivery;
description add-field-description --onProperty deliveryItems --title "Delivery  Items" --text "Delivery Items";
description add-field-description --onProperty deliveryItems --title "Ligne de livraison" --text "ligne de livraison" --locale fr;
association set-type --onProperty deliveryItems --type COMPOSITION --targetEntity ~.jpa.DeliveryItem;
association set-selection-mode --onProperty deliveryItems --selectionMode TABLE;
@/* TODO access add-permission --entity ~.jpa.DeliveryItem --action READ --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.DeliveryItem --action CREATE --toRole WAREHOUSEMAN*/;
@/* TODO access add-permission --entity ~.jpa.DeliveryItem --action READ --toRole CASHIER*/;
@/* TODO access add-permission --entity ~.jpa.DeliveryItem --action READ --toRole SALES*/;

cd ../DeliveryItem.java;
description add-field-description --onProperty delivery --title "Delivery" --text "The delivery containing this item";
description add-field-description --onProperty delivery --title "Livraison" --text "La livraison contenant cette ligne" --locale fr;
association set-type --onProperty delivery --type COMPOSITION --targetEntity ~.jpa.Delivery;

@/* ==================================== */;
@/* Movement Stock*/;

@/* Type Movement Stock*/;
java new-enum-type --named StockMovementType --package ~.jpa ;
enum add-enum-class-description --title "Stock Movement Type" --text "Type of movement made ​​in the stock";
enum add-enum-class-description  --locale fr --title "Type Mouvement Stock" --text "Type de mouvement effectué dans le stock";
java new-enum-const OUT;
enum add-enum-constant-description --onConstant OUT --title "Outbound" --text "Stock receip.";
enum add-enum-constant-description --locale fr --onConstant OUT --title "Sortie" --text "Stock delivery.";
java new-enum-const IN;
enum add-enum-constant-description --onConstant IN --title "Inbound" --text "Sortie de stock.";
enum add-enum-constant-description --locale fr --onConstant IN --title "Entrée" --text "Entrée de stock.";

@/* Movement Stock Endpoint*/;
java new-enum-type --named StockMovementTerminal --package ~.jpa ;
enum add-enum-class-description --title "Stock Movement Endpoint" --text "An origin or the destination of a stock movement.";
enum add-enum-class-description  --locale fr --title "Origine ou Destination du Mouvement Stock" --text "Origine ou la destination du movement de stock.";
java new-enum-const WAREHOUSE;
enum add-enum-constant-description --onConstant WAREHOUSE --title "Wharehouse" --text "Warehouse";
enum add-enum-constant-description --locale fr --onConstant WAREHOUSE --title "Magasin" --text "Magasin.";
java new-enum-const SUPPLIER;
enum add-enum-constant-description --onConstant SUPPLIER --title "Supplier" --text "Supplier";
enum add-enum-constant-description --locale fr --onConstant SUPPLIER --title "Fournisseur" --text "Forunisseur";
java new-enum-const CUSTOMER;
enum add-enum-constant-description --onConstant CUSTOMER --title "Customer" --text "Customer";
enum add-enum-constant-description --locale fr --onConstant CUSTOMER --title "Client" --text "Client";
java new-enum-const TRASH;
enum add-enum-constant-description --onConstant TRASH --title "Trash" --text "Trash";
enum add-enum-constant-description --locale fr --onConstant TRASH --title "Poubelle" --text "Poubelle";
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

@/* Mouvement de stock */;
entity --named StockMovement --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Stock Mouvement" --text "It saves the traces of all the movements that take place in the stock (inputs, outputs, inventory, returns processing, etc ...)";
description add-class-description --title "Mouvement de Stock" --text "Elle permet de sauvegarder les traces de  tous les mouvements qui prennen place dans le stock(entrees, sorties, inventaires, retours, transformation, etc...)" --locale fr ;
group add --grouper ~.jpa.WorkGroup --named MANAGER;
@/* TODO access add-permission  --action ALL --toRole MANAGER*/;
group add --grouper ~.jpa.WorkGroup --named WAREHOUSEMAN;
@/* TODO access add-permission  --action ALL --toRole WAREHOUSEMAN*/;
group add --grouper ~.jpa.WorkGroup --named CASHIER;
@/* TODO access add-permission  --action ALL --toRole CASHIER*/;
group add --grouper ~.jpa.WorkGroup --named SALES;
@/* TODO access add-permission  --action ALL --toRole SALES*/;

field temporal --type TIMESTAMP --named creationDate; 
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this stock movement.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette movement de stock." --locale fr;
format add-date-pattern --onProperty creationDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named creatingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty creatingUser --title "Creating User" --text "The user creating this stock movement.";
description add-field-description --onProperty creatingUser --title "Agent Créateur" --text "Utilisateur originaire du mouvement." --locale fr;
constraint NotNull --onProperty creatingUser;
description add-notNull-message --onProperty creatingUser --title "The creating user must be selected" --text "The creating user must be selected";
description add-notNull-message --onProperty creatingUser --title "Utilisateur créant doit être sélectionné" --text "Utilisateur créant doit être sélectionné" --locale fr;
association set-selection-mode --onProperty creatingUser --selectionMode COMBOBOX;
association set-type --onProperty creatingUser --type AGGREGATION --targetEntity ~.jpa.Login;

field number --named movedQty --type java.math.BigDecimal;
description add-field-description --onProperty movedQty --title "Quantity Moved" --text "The quantity moved during this stockage operation.";
description add-field-description --onProperty movedQty --title "Quantité Deplacés" --text "La quantité de produits deplacés lors de cette operation de stockage." --locale fr;
display add-toString-field --field movedQty;
display add-list-field --field movedQty;
@/* Default=0 */;

field custom --named movementType --type ~.jpa.StockMovementType;
description add-field-description --onProperty movementType --title "Movement Type" --text "The type of this stock movement";
description add-field-description --onProperty movementType --title "Type de Mouvement" --text "Le type de ce mouvement de stock" --locale fr;
enum enumerated-field --onProperty movementType;
display add-toString-field --field movementType;
display add-list-field --field movementType;
constraint NotNull --onProperty movementType;
description add-notNull-message --onProperty movementType --title "The type of this stock movement is required" --text "The type of this stock movement is required";
description add-notNull-message --onProperty movementType --title "Le type de ce mouvement de stock est réquis" --text "Le type de ce mouvement de stock est réquis" --locale fr;

field custom --named movementOrigin --type ~.jpa.StockMovementTerminal;
description add-field-description --onProperty movementOrigin --title "Movement Origin" --text "The starting point of the movement.";
description add-field-description --onProperty movementOrigin --title "Origine du Mouvement" --text "Le point de depart du mouvement." --locale fr;
enum enumerated-field --onProperty movementOrigin ;
display add-toString-field --field movementOrigin;
display add-list-field --field movementOrigin;
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

field custom --named movementDestination --type ~.jpa.StockMovementTerminal;
description add-field-description --onProperty movementDestination --title "Movement Destination" --text "Point of arrival of the movement.";
description add-field-description --onProperty movementDestination --title "Destination du Mouvement" --text "Point arrivée du mouvement." --locale fr;
enum enumerated-field --onProperty movementDestination;
display add-toString-field --field movementDestination;
display add-list-field --field movementDestination;
@/* Enumeration{MAGASIN, FOURNISSEUR, CLIENT} */;

field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The article of this stock movement";
description add-field-description --onProperty article --title "Article" --text "Le produit de ce mouvement de stock" --locale fr;
association set-selection-mode --onProperty article --selectionMode COMBOBOX;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;
constraint NotNull --onProperty article;
description add-notNull-message --onProperty article --title "The article fo this stock movement must be selected" --text "The article fo this stock movement must be selected";
description add-notNull-message --onProperty article --title "Le produit de ce mouvement de stock doit être sélectionné" --text "Le produit de ce mouvement de stock doit être sélectionné" --locale fr;

field string --named originatedDocNumber; 
description add-field-description --onProperty originatedDocNumber --title "Originated Doc" --text "The standard product identification code for a product in stock.";
description add-field-description --onProperty originatedDocNumber --title "Document" --text "Le document ratache a ce mouvement de stock." --locale fr;

field string --named internalPic; 
description add-field-description --onProperty internalPic --title "Article Local Pic" --text "The product local pic.";
description add-field-description --onProperty internalPic --title "PIC Maison" --text "Le code interne de l article ." --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Name of the agency in which the movement takes place";
description add-field-description --onProperty agency --title "Filiale" --text "Nom de la filiale dans laquelle le mouvement a lieu" --locale fr;
association set-selection-mode --onProperty agency --selectionMode  COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;

field number --named initialQty --type java.math.BigDecimal;
description add-field-description --onProperty initialQty --title "Initial Quantity" --text "The quantity in stock before the movement.";
description add-field-description --onProperty initialQty --title "Quantité Initiale" --text "La quantité de produits dans le stock avant le mouvement." --locale fr;
@/* Default=0 */;
display add-list-field --field initialQty;

field number --named finalQty --type java.math.BigDecimal;
description add-field-description --onProperty finalQty --title "Final Quantity" --text "The quantity in stock after the movement.";
description add-field-description --onProperty finalQty --title "Quantité Finale" --text "La quantité de produits dans le stock apres le mouvement." --locale fr;
@/* Default=0 */;
display add-list-field --field finalQty;

field number --named totalPurchasingPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasingPrice --title "Total Purchasing Price" --text "Total purchasing price for this stock movement";
description add-field-description --onProperty totalPurchasingPrice --title "Prix Achat Total" --text "Prix total achat pour ce mouvement de stock" --locale fr;
format add-number-type --onProperty totalPurchasingPrice --type CURRENCY;
display add-list-field --field totalPurchasingPrice;
constraint NotNull --onProperty totalPurchasingPrice;
description add-notNull-message --onProperty totalPurchasingPrice --title "The total purchasing price for this stock movement is required" --text "The total purchasing price for this stock movement is required";
description add-notNull-message --onProperty totalPurchasingPrice --title "Le prix total achat pour ce mouvement de stock est réquis" --text "Le prix total achat pour ce mouvement de stock est réquis" --locale fr;

field number --named totalDiscount --type java.math.BigDecimal;
description add-field-description --onProperty totalDiscount --title "Total Discount" --text "Total discount of the purchase.";
description add-field-description --onProperty totalDiscount --title "Remise Totale" --text "Remise totale sur cet approvisionement." --locale fr;
format add-number-type --onProperty totalDiscount --type CURRENCY;
display add-list-field --field totalDiscount;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "Total sale price for the movement type sales";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Totale" --text "Prix de vente total pour les mouvements de type vente." --locale fr;
format add-number-type --onProperty totalSalesPrice --type CURRENCY;
display add-list-field --field totalSalesPrice;

cd ~~ ;

@/* ==================================== */;
@/* Client */;

@/* Employeur */;
entity --named Employer --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Employer" --text "The client Employer.";
description add-class-description  --locale fr --title "Employeur" --text "Employeur du client.";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The employer name";
description add-field-description --onProperty name --title "Nom" --text "Le nom de cet employeur" --locale fr;
display add-toString-field --field name;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The employer name is required" --text "The employer name is required";
description add-notNull-message --onProperty name --title "Le nom de cet employeur est réquis" --text "Le nom de cet employeur est réquis" --locale fr;
display add-list-field --field name;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The employer Phone";
description add-field-description --onProperty phone --title "Telephone" --text "Le telephone de cet employeur" --locale fr;
display add-list-field --field phone;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue" --locale fr;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code oif this address";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette adresse" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this address";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette adresse" --locale fr;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The zip code oif this address";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette adresse" --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

@/* Type Client*/;
java new-enum-type --named CustomerType --package ~.jpa;
enum add-enum-class-description --title "Customer Type" --text "Type of client.";
enum add-enum-class-description  --locale fr --title "Type de client" --text "Type de client.";
java new-enum-const INDIVIDUAL;
enum add-enum-constant-description --onConstant INDIVIDUAL --title "Individual" --text "Individual person";
enum add-enum-constant-description --locale fr --onConstant INDIVIDUAL --title "Physique" --text "Personnalié physique";
java new-enum-const LEGAL;
enum add-enum-constant-description --onConstant LEGAL --title "Legal" --text "Legal entity";
enum add-enum-constant-description --locale fr --onConstant LEGAL --title "Morale" --text "Personnalité juridique";
@/* enumeration{PHYSIQUE, MORAL} */;

@/* Entité CategorieClient */;
entity --named CustomerCategory --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Customer Category" --text "The client categories.";
description add-class-description  --locale fr --title "Categorie Client" --text "Les categorie de client.";

field string --named name;
description add-field-description --onProperty name --title "Category Name" --text "The name of this client category";
description add-field-description --onProperty name --title "Libelle Categorie" --text "Le nom de cette categorie client" --locale fr;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The name of this client category is required" --text "The name of this client category is required";
description add-notNull-message --onProperty name --title "Le nom de cette categorie client est réquis" --text "Le nom de cette categorie client est réquis" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field number --named discountRate --type java.math.BigDecimal;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "Discount rate for this client category.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise pour cette categorie client." --locale fr;
format add-number-type --onProperty discountRate --type PERCENTAGE;
display add-list-field --field discountRate;

field string --named description;
description add-field-description --onProperty description --title "Description" --text "Description of this client category.";
description add-field-description --onProperty description --title "Description" --text "Description de cette categorie client." --locale fr;
constraint Size --onProperty description --max 256;
description add-size-message --onProperty description --title "The description must have less than 256 characters" --text "The description must have less than 256 characters";
description add-size-message --onProperty description --title "La description doit avoir moins de 256 caractères" --text "La description doit avoir moins de 256 caractères" --locale fr;


@/* Client */;
entity --named Customer --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Customer" --text "The client.";
description add-class-description  --locale fr --title "Client" --text "Un client.";

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this client.";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de ce client." --locale fr;
enum enumerated-field --onProperty gender ;

field string --named firstName;
description add-field-description --onProperty firstName --title "First Name" --text "The first name of this client.";
description add-field-description --onProperty firstName --title "Nom" --text "Le prénom de ce client." --locale fr;

field string --named lastName;
description add-field-description --onProperty lastName --title "last Name" --text "The name of this client";
description add-field-description --onProperty lastName --title "Prénom" --text "Le nom de ce client" --locale fr;
constraint NotNull --onProperty lastName;
description add-notNull-message --onProperty lastName --title "The user last name is required" --text "The user last name is required";
description add-notNull-message --onProperty lastName --title "Le nom de ce client est réquis" --text "Le nom de ce client est réquis" --locale fr;

field string --named fullName;
description add-field-description --onProperty fullName --title "Full Name" --text "The full name of this client";
description add-field-description --onProperty fullName --title "Nom Complet" --text "Le nom complet de ce client" --locale fr;
display add-toString-field --field fullName;
display add-list-field --field fullName;
@/* Le nom complet du client(nom+prenom) */;
constraint NotNull --onProperty fullName;
description add-notNull-message --onProperty fullName --title "The full name of this client is required" --text "The full name of this client is required";
description add-notNull-message --onProperty fullName --title "Le nom complet de ce client est réquis" --text "Le nom complet de ce client est réquis" --locale fr;

field temporal --type TIMESTAMP --named birthDate; 
@/* pattern= dd-MM-yyyy */;
description add-field-description --onProperty birthDate --title "Birth Date" --text "The birth date of this client";
description add-field-description --onProperty birthDate --title "Date de Naissance" --text "La date de naissance du client" --locale fr;
display add-list-field --field birthDate;
format add-date-pattern --onProperty birthDate --pattern "dd-MM-yyyy"; 

field string --named landLinePhone;
description add-field-description --onProperty landLinePhone --title "Land Line Phone" --text "The client land line phone number";
description add-field-description --onProperty landLinePhone --title "Téléphone Fixe" --text "Téléphone fixe du client" --locale fr;
display add-list-field --field landLinePhone;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile Phone" --text "The mobile phone of the client";
description add-field-description --onProperty mobile --title "Téléphone Mobile" --text "Téléphone Mobile du client" --locale fr;
display add-list-field --field mobile;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the client";
description add-field-description --onProperty fax --title "Fax" --text "Fax du client" --locale fr;
display add-list-field --field fax;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of the client";
description add-field-description --onProperty email --title "Email" --text "Email du client" --locale fr;
display add-list-field --field email;

field boolean --named creditAuthorized --primitive false;
description add-field-description --onProperty creditAuthorized --title "Credit Authorized" --text "Whether or not the customer can purchase on credit";
description add-field-description --onProperty creditAuthorized --title "Crédit Autorisé" --text "Autorise ou non le crédit au client" --locale fr;
display add-list-field --field creditAuthorized;

field boolean --named discountAuthorized --primitive false;
description add-field-description --onProperty discountAuthorized --title "Discount Authorized" --text "Whether or not the customer can be given discount";
description add-field-description --onProperty discountAuthorized --title "Remise Autorisée" --text "Autorise ou non la remise globale sur les produits au client" --locale fr;
@/* default=true */;
display add-list-field --field discountAuthorized;

field number --named maxAccordedCredit --type java.math.BigDecimal;
description add-field-description --onProperty maxAccordedCredit --title "Max Credit " --text "Total credit line for this customer..";
description add-field-description --onProperty maxAccordedCredit --title "Credit Maximum" --text "Le montant max de credit qu on peut accorder au client." --locale fr;
@/* Default=0 */;

field manyToOne --named employer --fieldType ~.jpa.Employer;
description add-field-description --onProperty employer --title "Employer" --text "The employer of this client.";
description add-field-description --onProperty employer --title "Employeur" --text "L employeur de ce client." --locale fr;
association set-selection-mode --onProperty employer --selectionMode COMBOBOX;
association set-type --onProperty employer --type AGGREGATION --targetEntity ~.jpa.Employer;

field manyToOne --named customerCategory --fieldType ~.jpa.CustomerCategory;
description add-field-description --onProperty customerCategory --title "Customer Category" --text "The category this client belongs to.";
description add-field-description --onProperty customerCategory --title "Category Client" --text "La categorie de client à laquelle appartient le client." --locale fr;
association set-selection-mode --onProperty customerCategory --selectionMode COMBOBOX;
association set-type --onProperty customerCategory --type AGGREGATION --targetEntity ~.jpa.CustomerCategory;

field number --named totalDebt --type java.math.BigDecimal;
description add-field-description --onProperty totalDebt --title "Total Debt" --text "Total debts of this customer.";
description add-field-description --onProperty totalDebt --title "Dette Total" --text "Montant total des dettes du client." --locale fr;
format add-number-type --onProperty totalDebt --type CURRENCY;
@/* Default=0 */;

field custom --named customerType --type ~.jpa.CustomerType;
description add-field-description --onProperty customerType --title "Customer Type" --text "The client type.";
description add-field-description --onProperty customerType --title "Type Client" --text "Le type de client." --locale fr;
enum enumerated-field --onProperty customerType ;
@/* enumeration{PHYSIQUE, MORAL} */;

field string --named serialNumber;
description add-field-description --onProperty serialNumber --title "Serial Number" --text "The serial number of this client.";
description add-field-description --onProperty serialNumber --title "Matricule Client" --text "Le numéro matricule de ce client." --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

cd ~~ ;

@/* Insurrance */;
entity --named Insurrance --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Insurrance" --text "The Insurrance .";
description add-class-description --locale fr --title "Assurance" --text "Assurance";

field temporal --type TIMESTAMP --named beginDate; 
@/* pattern= dd-MM-yyyy */;
description add-field-description --onProperty beginDate --title "Begin Date" --text "The date of beginning pf this inssurance";
description add-field-description --onProperty beginDate --title "date de debut" --text "La date de debut de cette assurance" --locale fr;
display add-list-field --field beginDate;
format add-date-pattern --onProperty beginDate --pattern "dd-MM-yyyy"; 

field temporal --type TIMESTAMP --named endDate; 
@/* pattern= dd-MM-yyyy */;
description add-field-description --onProperty endDate --title "End Date" --text "The date of ending pf this inssurance";
description add-field-description --onProperty endDate --title "date de fin" --text "La date de fin de cette assurance" --locale fr;
display add-list-field --field endDate;
format add-date-pattern --onProperty endDate --pattern "dd-MM-yyyy"; 

field manyToOne --named customer --fieldType ~.jpa.Customer;
description add-field-description --onProperty customer --title "Customer" --text "The Customer.";
description add-field-description --onProperty customer --title "Client" --text "Le client." --locale fr;
association set-selection-mode --onProperty customer --selectionMode COMBOBOX;
association set-type --onProperty customer --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field customer.fullName;
display add-toString-field --field customer.fullName;

field manyToOne --named insurer --fieldType ~.jpa.Customer;
description add-field-description --onProperty insurer --title "Insurer" --text "The insurer";
description add-field-description --onProperty insurer --title "Assureur" --text "Assureur" --locale fr;
association set-selection-mode --onProperty insurer --selectionMode COMBOBOX;
association set-type --onProperty insurer --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field insurer.fullName;
display add-toString-field --field insurer.fullName;

field number --named coverageRate --type java.math.BigDecimal;
@/* default=100% */;
description add-field-description --onProperty coverageRate --title "Coverage Rate" --text "The coverage rate for this client.";
description add-field-description --onProperty coverageRate --title "Taux Couverture" --text "Taux de couverture pour ce client." --locale fr;
format add-number-type --onProperty coverageRate --type  PERCENTAGE;
display add-list-field --field coverageRate;
display add-toString-field --field coverageRate;

cd ~~

@/* ================================ */;
@/* Inventories */;

@/* Entite Ligne Inventaire */;
entity --named InventoryItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Inventory Item" --text "An inventory item";
description add-class-description  --locale fr --title "Ligne Inventaire" --text "Une ligne inventaire.";

field number --named expectedQty --type java.math.BigDecimal;
description add-field-description --onProperty expectedQty --title "Expected Quantity in Stock" --text "The quantity of this article expected to be in stock.";
description add-field-description --onProperty expectedQty --title "Quantité Attendue en Stock" --text "Quantité de produits supposé en le stock." --locale fr;
display add-toString-field --field expectedQty;
display add-list-field --field expectedQty;

field number --named asseccedQty --type java.math.BigDecimal;
description add-field-description --onProperty asseccedQty --title "Real Quantity" --text "Actual quantity of products physically counted.";
description add-field-description --onProperty asseccedQty --title "Real Quantité" --text "Quantité réelle de produits de la ligne comptés physiquement." --locale fr;
display add-toString-field --field asseccedQty;
display add-list-field --field asseccedQty;

field long --named gap --primitive false;
description add-field-description --onProperty gap --title "Gap" --text "Deviation of access and expected quantity.";
description add-field-description --onProperty gap --title "Écart" --text "Écart de stock de la ligne d inventaire." --locale fr;
@/* formule=(qte_reel - qte_en_stock) */;
display add-toString-field --field gap;
display add-list-field --field gap;

field number --named gapSalesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty gapSalesPricePU --title "Sales Price per Unit" --text "The last sales price per unit.";
description add-field-description --onProperty gapSalesPricePU --title "Prix de Vente Unitaire" --text "Le dernier prix de vente unitaire." --locale fr;
format add-number-type --onProperty gapSalesPricePU --type CURRENCY;
@/*  Default=0 */; 
display add-list-field --field gapSalesPricePU;

field number --named gapPurchasePricePU --type java.math.BigDecimal;
description add-field-description --onProperty gapPurchasePricePU --title "Gap Purchase Price per Unit" --text "The last Purchase price per unit.";
description add-field-description --onProperty gapPurchasePricePU --title "Prix d Achat Unitaire Écart" --text "Le dernier prix de Achatte unitaire." --locale fr;
format add-number-type --onProperty gapPurchasePricePU --type CURRENCY;
@/*  Default=0 */; 
display add-list-field --field gapPurchasePricePU;

field number --named gapTotalSalePrice --type java.math.BigDecimal;
description add-field-description --onProperty gapTotalSalePrice --title "Total Sale Price" --text "The total price.";
description add-field-description --onProperty gapTotalSalePrice --title "Prix de Vente Total" --text "Le prix total." --locale fr;
format add-number-type --onProperty gapTotalSalePrice --type CURRENCY;
@/*  Default=0, formule=(prix_unitaire*ecart) */;
display add-list-field --field gapTotalSalePrice;

field number --named gapTotalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty gapTotalPurchasePrice --title "Total Purchase Price" --text "The total price.";
description add-field-description --onProperty gapTotalPurchasePrice --title "Prix d Achat Total" --text "Le prix total." --locale fr;
format add-number-type --onProperty gapTotalPurchasePrice --type CURRENCY;
@/*  Default=0, formule=(prix_unitaire*ecart) */;
display add-list-field --field gapTotalPurchasePrice;

field manyToOne --named recordingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty recordingUser --title "Recording User" --text "The user recording this inventory item";
description add-field-description --onProperty recordingUser --title "Agent Saisie" --text "Responsable de la saisie de la ligne d inventaire" --locale fr;
association set-selection-mode --onProperty recordingUser --selectionMode COMBOBOX;
association set-type --onProperty recordingUser --type AGGREGATION --targetEntity ~.jpa.Login;
constraint NotNull --onProperty recordingUser;
description add-notNull-message --onProperty recordingUser --title "The user recording this inventory item must be selected" --text "The user recording this inventory item must be selected";
description add-notNull-message --onProperty recordingUser --title "La personne saisissant cette ligne d inventaire doit être sélectionné" --text "La personne saisissant cette ligne d inventaire doit être sélectionné" --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date of this inventory item.";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie de cette ligne inventaire." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

field string --named internalPic;
description add-field-description --onProperty internalPic --title "Product Internal Pic" --text "The product internal pic .";
description add-field-description --onProperty internalPic --title "CIP Maison" --text "Lecip maison." --locale fr;
display add-toString-field --field internalPic;
display add-list-field --field internalPic;


field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The product associated with this inventory line";
description add-field-description --onProperty article --title "Produit" --text "Le produit attaché à la ligne d inventaire" --locale fr;
association set-selection-mode --onProperty article --selectionMode COMBOBOX;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;
constraint NotNull --onProperty article;
description add-notNull-message --onProperty article --title "The article associated with this inventory item must be selected" --text "The article associated with this inventory item must be selected";
description add-notNull-message --onProperty article --title "Le produit sujet de cette ligne d inventaire doit être sélectionné" --text "Le produit sujet de cette ligne d inventaire doit être sélectionné" --locale fr;

@/* Entite Inventaire */;
entity --named Inventory --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Inventory" --text "An inventory";
description add-class-description  --locale fr --title "Inventaire" --text "Un inventaire.";

field string --named inventoryNumber;
description add-field-description --onProperty inventoryNumber --title "Inventory Number" --text "The inventory number.";
description add-field-description --onProperty inventoryNumber --title "Numéro d Inventaire" --text "Le numéro d inventaire." --locale fr;
display add-toString-field --field inventoryNumber;
display add-list-field --field inventoryNumber;

field manyToOne --named recordingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty recordingUser --title "Recording User" --text "The user recording this inventory item.";
description add-field-description --onProperty recordingUser --title "Agent Saisie" --text "Responsable de la saisie de la ligne d inventaire" --locale fr;
association set-selection-mode --onProperty recordingUser --selectionMode COMBOBOX;
association set-type --onProperty recordingUser --type AGGREGATION --targetEntity ~.jpa.Login;
constraint NotNull --onProperty recordingUser;
description add-notNull-message --onProperty recordingUser --title "The user recording this inventory must be selected" --text "The user recording this inventory must be selected";
description add-notNull-message --onProperty recordingUser --title "La personne saisissant cet inventaire doit être sélectionné" --text "La personne saisissant cet inventaire doit être sélectionné" --locale fr;

field number --named gapSaleAmount --type java.math.BigDecimal;
description add-field-description --onProperty gapSaleAmount --title "Gap Sale Amount" --text "The amount of this inventory.";
description add-field-description --onProperty gapSaleAmount --title "Montant Des Ecart en Vente" --text "Le montant de cet inventaire." --locale fr;
format add-number-type --onProperty gapSaleAmount --type CURRENCY;
display add-list-field --field gapSaleAmount;

field number --named gapPurchaseAmount --type java.math.BigDecimal;
description add-field-description --onProperty gapPurchaseAmount --title "Gap Purchase Amount" --text "The amount of this inventory.";
description add-field-description --onProperty gapPurchaseAmount --title "Montant Des Ecart en Achat" --text "Le montant de cet inventaire." --locale fr;
format add-number-type --onProperty gapPurchaseAmount --type CURRENCY;
display add-list-field --field gapPurchaseAmount;

field custom --named inventoryStatus --type ~.jpa.DocumentProcessingState.java;
description add-field-description --onProperty inventoryStatus --title "Inventory Status" --text "The status of this inventory.";
description add-field-description --onProperty inventoryStatus --title "État d Inventaire" --text "L état de cet inventaire." --locale fr;
enum enumerated-field --onProperty inventoryStatus ;
display add-list-field --field inventoryStatus;

field string --named description;
description add-field-description --onProperty description --title "Note" --text "Description of this inventory.";
description add-field-description --onProperty description --title "Note" --text "Description de cet inventaire." --locale fr;
constraint Size --onProperty description --max 256;
description add-size-message --onProperty description --title "The description must have less than 256 characters" --text "The description must have less than 256 characters";
description add-size-message --onProperty description --title "La description doit avoir moins de 256 caractères" --text "La description doit avoir moins de 256 caractères" --locale fr;

field temporal --type TIMESTAMP --named inventoryDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty inventoryDate --title "Inventory Date" --text "The date of this inventory.";
description add-field-description --onProperty inventoryDate --title "Date de cet Inventaire" --text "La date de cet inventaire." --locale fr;
format add-date-pattern --onProperty inventoryDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The site of this inventory";
description add-field-description --onProperty agency --title "Agence" --text "Le site de cet inventaire" --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The site of this inventory must be selected" --text "The site of this inventory must be selected";
description add-notNull-message --onProperty agency --title "Le site de cet inventaire doit être sélectionné" --text "Le site de cet inventaire doit être sélectionné" --locale fr;

field oneToMany --named inventoryItems --fieldType ~.jpa.InventoryItem --inverseFieldName inventory;
description add-field-description --onProperty inventoryItems --title "Inventory Items" --text "The inventory items";
description add-field-description --onProperty inventoryItems --title "Lignes Inventaire" --text "Les ligne d inventaire" --locale fr;
association set-type --onProperty inventoryItems --type COMPOSITION --targetEntity ~.jpa.InventoryItem;
association set-selection-mode --onProperty inventoryItems --selectionMode TABLE;

cd ../InventoryItem.java;
description add-field-description --onProperty inventory --title "Inventory" --text "The inventory containing this item";
description add-field-description --onProperty inventory --title "Inventaire" --text "Inventaire contenant cette ligne" --locale fr;
association set-type --onProperty inventory --type COMPOSITION --targetEntity ~.jpa.Inventory;

@/* Entité ProductDetailConfig */;
entity --named ProductDetailConfig --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Product Details Config" --text "Product transformation";
description add-class-description  --locale fr --title "Transformation Produit" --text "Transformation produit";

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date ";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie ." --locale fr;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

field oneToOne --named source --fieldType ~.jpa.Article;
description add-field-description --onProperty source --title "Source Product" --text "The compound product to be decomposed";
description add-field-description --onProperty source --title "Produit Origine" --text "Le produit composant à decomposer" --locale fr;
association set-selection-mode --onProperty source --selectionMode FORWARD;
association set-type --onProperty source --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field source.articleName;
display add-list-field --field source.articleName;
constraint NotNull --onProperty source;
description add-notNull-message --onProperty source --title "The compound product to be decomposed must be selected" --text "The compound product to be decomposed must be selected";
description add-notNull-message --onProperty source --title "Le produit composant à decomposer doit être sélectionné" --text "Le produit composant à decomposer doit être sélectionné" --locale fr;

field oneToOne --named target --fieldType ~.jpa.Article;
description add-field-description --onProperty target --title "Target Product" --text "The compound product generated from the decomposition";
description add-field-description --onProperty target --title "Produit Cible" --text "Le produit composé generé à partir de la décomposition" --locale fr;
association set-selection-mode --onProperty target --selectionMode FORWARD;
association set-type --onProperty target --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field target.articleName;
display add-list-field --field target.articleName;
constraint NotNull --onProperty target;
description add-notNull-message --onProperty target --title "The compound product generated from the decomposition must be selected" --text "The compound product generated from the decomposition must be selected";
description add-notNull-message --onProperty target --title "Le produit composé generé à partir de la décomposition doit être sélectionné" --text "Le produit composé generé à partir de la décomposition doit être sélectionné" --locale fr;

field number --named targetQuantity --type java.math.BigDecimal;
description add-field-description --onProperty targetQuantity --title "Target Quantity" --text "Quantity of the target product";
description add-field-description --onProperty targetQuantity --title "Quantité Cible" --text "Quantité du produit cible" --locale fr;
constraint NotNull --onProperty targetQuantity;
description add-notNull-message --onProperty targetQuantity --title "The quantity of the target product is required" --text "The quantity of the target product is required";
description add-notNull-message --onProperty targetQuantity --title "La quantité du produit cible réquise" --text "La quantité du produit cible réquise" --locale fr;
display add-list-field --field targetQuantity;

field number --named salesPrice --type java.math.BigDecimal;
description add-field-description --onProperty salesPrice --title "Sales Price" --text "Sales price of target article";
description add-field-description --onProperty salesPrice --title "Prix de Vente" --text "Prix de vente du produit cible" --locale fr;
constraint NotNull --onProperty salesPrice;
description add-notNull-message --onProperty salesPrice --title "The sales price of target article is required" --text "The sales price of target article is required";
description add-notNull-message --onProperty salesPrice --title "Le prix de vente du produit cible est réquis" --text "Le prix de vente du produit cible est réquis" --locale fr;
format add-number-type --onProperty salesPrice --type CURRENCY;
display add-list-field --field salesPrice;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Alows activation or deactivation of this transformation.";
description add-field-description --onProperty active --title "Actif" --text "Permet d activer ou de desactiver la transformation.." --locale fr;
@/* Default= true */;
display add-list-field --field active;

cd ~~ ;

@/* Entité Caisse */;
entity --named CashDrawer --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Cash Drawer" --text "A cash drawer.";
description add-class-description  --locale fr --title "Caisse" --text "Une caisse.";

field string --named cashDrawerNumber;
description add-field-description --onProperty cashDrawerNumber --title "Cash Drawer Number" --text "The number of this cash drawer.";
description add-field-description --onProperty cashDrawerNumber --title "Numéro de Caisse" --text "Le numéro de cette caisse." --locale fr;
display add-toString-field --field cashDrawerNumber;
display add-list-field --field cashDrawerNumber;

field manyToOne --named cashier --fieldType ~.jpa.Login;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment on this drawer.";
description add-field-description --onProperty cashier --title "Caissier" --text "Utilisateur percevant le paiement surcette caisse." --locale fr;
association set-selection-mode --onProperty cashier --selectionMode COMBOBOX;
association set-type --onProperty cashier --type AGGREGATION --targetEntity ~.jpa.Login;

field manyToOne --named closedBy --fieldType ~.jpa.Login;
description add-field-description --onProperty closedBy --title "Closed By" --text "The user who closed this cash drawer.";
description add-field-description --onProperty closedBy --title "Fermé Par" --text "Utilisateur ayant fermé la caisse." --locale fr;
association set-selection-mode --onProperty closedBy --selectionMode COMBOBOX;
association set-type --onProperty closedBy --type AGGREGATION --targetEntity ~.jpa.Login;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Site in which this drawer resides.";
description add-field-description --onProperty agency --title "Agency" --text "Site dans lequel la caisse est gerée." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The site of this drawer must be selected" --text "The site of this drawer must be selected";
description add-notNull-message --onProperty agency --title "Le site de cette caisse doit être sélectionné" --text "Le site de cette caisse doit être sélectionné" --locale fr;

field temporal --type TIMESTAMP --named openingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty openingDate --title "Opening Date" --text "The opening date of this drawer.";
description add-field-description --onProperty openingDate --title "Date d Ouverture" --text "La date d ouverture de cette caisse." --locale fr;
display add-list-field --field openingDate;
format add-date-pattern --onProperty openingDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named closingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty closingDate --title "Closing Date" --text "The closing date of this drawer.";
description add-field-description --onProperty closingDate --title "Date de Fermeture" --text "La date de fermeture de cette caisse." --locale fr;
display add-list-field --field closingDate;
format add-date-pattern --onProperty closingDate --pattern "dd-MM-yyyy HH:mm"; 

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "The initial amount.";
description add-field-description --onProperty initialAmount --title "Fond de Caisse" --text "Le fond initial de la caisse." --locale fr;
format add-number-type --onProperty initialAmount --type CURRENCY ;
display add-list-field --field initialAmount;
@/* Default=0 */;

field number --named totalCashIn --type java.math.BigDecimal;
description add-field-description --onProperty totalCashIn --title "Total Cash In" --text "The total cash in.";
description add-field-description --onProperty totalCashIn --title "Total Encaissement" --text "L encaissement totale." --locale fr;
format add-number-type --onProperty totalCashIn --type CURRENCY ;
display add-list-field --field totalCashIn;
@/* Default=0 */;

field number --named totalCashOut --type java.math.BigDecimal;
description add-field-description --onProperty totalCashOut --title "Total Cash Out" --text "Total withdrawal from this drawer.";
description add-field-description --onProperty totalCashOut --title "Total Retrait" --text "Total des decaissements éffectués en caisse." --locale fr;
format add-number-type --onProperty totalCashOut --type CURRENCY ;
display add-list-field --field totalCashOut;
@/* Default=0 */;

field number --named totalCash --type java.math.BigDecimal;
description add-field-description --onProperty totalCash --title "Total Cash" --text "Total cash in this drawer.";
description add-field-description --onProperty totalCash --title "Total Cash" --text "Total cash dans cette caisse." --locale fr;
format add-number-type --onProperty totalCash --type CURRENCY ;
display add-list-field --field totalCash;
@/* Default=0 */;

field number --named totalCheck --type java.math.BigDecimal;
description add-field-description --onProperty totalCheck --title "Total Checks" --text "Total checks in this drawer.";
description add-field-description --onProperty totalCheck --title "Total Chèque" --text "Total chèque dans cette caisse." --locale fr;
format add-number-type --onProperty totalCheck --type CURRENCY ;
display add-list-field --field totalCheck;
@/* Default=0 */;

field number --named totalCreditCard --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditCard --title "Total Credit Card" --text "Total credit cards by this drawer.";
description add-field-description --onProperty totalCreditCard --title "Total Carte Credit" --text "Total carte de credit par cette caisse." --locale fr;
format add-number-type --onProperty totalCreditCard --type CURRENCY ;
display add-list-field --field totalCreditCard;
@/* Default=0 */;

field number --named totalCompanyVoucher --type java.math.BigDecimal;
description add-field-description --onProperty totalCompanyVoucher --title "Total Company Vouchera" --text "Total voucher (from company or hospital) in this drawer.";
description add-field-description --onProperty totalCompanyVoucher --title "Total Avoir Compangny" --text "Totale des bons (de sociéte ou d hopital) qu il ya en caisse." --locale fr;
format add-number-type --onProperty totalCompanyVoucher --type CURRENCY ;
display add-list-field --field totalCompanyVoucher;
@/* Default=0 */;

field number --named totalClientVoucher --type java.math.BigDecimal;
description add-field-description --onProperty totalClientVoucher --title "Total Client Voucher" --text "Total voucher (from client) in this drawer.";
description add-field-description --onProperty totalClientVoucher --title "Total Avoir Client" --text "Totale des bons (client) qu il ya en caisse." --locale fr;
format add-number-type --onProperty totalClientVoucher --type CURRENCY ;
display add-list-field --field totalClientVoucher;
@/* Default=0 */;

field boolean --named opened --primitive false;
description add-field-description --onProperty opened --title "Open" --text "Indicates whether the cash drawer is open.";
description add-field-description --onProperty opened --title "Ouverte" --text "Indique si la caisse est ouverte." --locale fr;
display add-list-field --field opened;
@/* default=true */;

@/* Commande Client */;

 @/* SalesOrderType */;
java new-enum-type --named SalesOrderType --package ~.jpa ;
enum add-enum-class-description --title "Sales Order Type" --text "The type of this sales order.";
enum add-enum-class-description  --locale fr --title "Type de Commande Client" --text "Le type d une commande client.";
java new-enum-const CASH_SALE;
enum add-enum-constant-description --onConstant CASH_SALE --title "Cash Sale" --text "Cash Sale";
enum add-enum-constant-description --locale fr --onConstant CASH_SALE --title "Vente Au comptant" --text "Vente en espece";
java new-enum-const PARTIAL_SALE;
enum add-enum-constant-description --onConstant PARTIAL_SALE --title "Partial Sale" --text "Credit sale";
enum add-enum-constant-description --locale fr --onConstant PARTIAL_SALE --title "Vente Partiel" --text "Vente à crédit";
java new-enum-const PROFORMA_SALE;
enum add-enum-constant-description --onConstant PROFORMA_SALE --title "Pro Forma Sale" --text "Pro forma sale";
enum add-enum-constant-description --locale fr --onConstant PROFORMA_SALE --title "Vente Pro Forma" --text "Vente pro forma";

@/* Sales Order Item */;
entity --named SalesOrderItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order Item" --text "The sales order item.";
description add-class-description  --locale fr --title "Ligne Commande Client" --text "La ligne de command client.";

field number --named orderedQty --type java.math.BigDecimal;
description add-field-description --onProperty orderedQty --title "Quantity Ordered" --text "The quantity ordered in this line.";
description add-field-description --onProperty orderedQty --title "Quantité Commandés" --text "La quantité commandée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-list-field --field orderedQty;

field number --named returnedQty --type java.math.BigDecimal;
description add-field-description --onProperty returnedQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty returnedQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-list-field --field returnedQty;

field number --named deliveredQty --type java.math.BigDecimal;
description add-field-description --onProperty deliveredQty --title "Quantity Returned" --text "The quantity returned in this line.";
description add-field-description --onProperty deliveredQty --title "Quantité Retournée" --text "La quantité retournée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-list-field --field deliveredQty;

field temporal --type TIMESTAMP --named recordDate; 
@/* Pattern=d-MM-yyy HH:MM  */;
description add-field-description --onProperty recordDate --title "Record Date" --text "Creation date for this line.";
description add-field-description --onProperty recordDate --title "Date de Saisie" --text "Date de saisie de cette ligne." --locale fr;
format add-date-pattern --onProperty recordDate --pattern "dd-MM-yyyy HH:mm"; 

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix de vente unitaire." --locale fr;
format add-number-type --onProperty salesPricePU --type CURRENCY;
@/*  Default=0 */; 
display add-list-field --field salesPricePU;

field number --named totalSalePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalePrice --title "Total Sale Price" --text "Total Sale Price.";
description add-field-description --onProperty totalSalePrice --title "Prix de vente Total" --text "Prix de vente Total." --locale fr;
format add-number-type --onProperty totalSalePrice --type CURRENCY;
@/* Default=0., Formule=(remise * qté_commande) */;
display add-list-field --field totalSalePrice;

field string --named internalPic; 
description add-field-description --onProperty internalPic --title "Local PIC" --text "The internal product identification code used to identify lots during sales.";
description add-field-description --onProperty internalPic --title "CIP Maison" --text "Le code identifiant produit maison, utilisé pour identifier les lots de produits lors de la vente." --locale fr;
constraint Size --onProperty internalPic --min 7;
description add-size-message --onProperty internalPic --title "The internal product identification code must have more than 7 characters" --text "The internal product identification code must have more than 7 characters";
description add-size-message --onProperty internalPic --title "Le code pour identification de produit interne doit avoir plus de 7 caractères" --text "Le code pour identification de produit interne doit avoir plus de 7 caractères" --locale fr;
display add-list-field --field internalPic;

field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The article of this sales order item";
description add-field-description --onProperty article --title "Article" --text "Le produit de cette ligne de commande client" --locale fr;
association set-selection-mode --onProperty article --selectionMode COMBOBOX;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;
constraint NotNull --onProperty article;
description add-notNull-message --onProperty article --title "The article of this sales order item must be selected" --text "The article of this sales order item must be selected";
description add-notNull-message --onProperty article --title "Le produit de cette ligne de commande client doit être sélectionné" --text "Le produit de cette ligne de commande client doit être sélectionné" --locale fr;


cd ~~;



@/* Sales Order */;
entity --named SalesOrder --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Sales Order" --text "A sales order.";
description add-class-description  --locale fr --title "Commande Client" --text "Une commande client.";

field manyToOne --named cashDrawer --fieldType ~.jpa.CashDrawer;
description add-field-description --onProperty cashDrawer --title "Cash Drawer" --text "The cash drawer in use.";
description add-field-description --onProperty cashDrawer --title "Caisse" --text "La caisse utilisé." --locale fr;
association set-selection-mode --onProperty cashDrawer --selectionMode COMBOBOX;
association set-type --onProperty cashDrawer --type AGGREGATION --targetEntity ~.jpa.CashDrawer;
display add-list-field --field cashDrawer.cashDrawerNumber;

field string --named soNumber;
description add-field-description --onProperty soNumber --title "Sales Order Number" --text "The sales order number.";
description add-field-description --onProperty soNumber --title "Numéro de Commande Client" --text "Le numéro de la commande client." --locale fr;
display add-toString-field --field soNumber;
display add-list-field --field soNumber;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this order.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette commande." --locale fr;
format add-date-pattern --onProperty creationDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named cancelationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty cancelationDate --title "Cancelation Date" --text "The cancelation date of this order.";
description add-field-description --onProperty cancelationDate --title "Date d Annulation" --text "La date d annulation de cette commande." --locale fr;
format add-date-pattern --onProperty cancelationDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named restorationDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty restorationDate --title "Restoration Date" --text "The restoration date of this order.";
description add-field-description --onProperty restorationDate --title "Date de Restauration" --text "La date de restauration de cette commande." --locale fr;
format add-date-pattern --onProperty restorationDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named customer --fieldType ~.jpa.Customer;
description add-field-description --onProperty customer --title "Customer" --text "The client ordering";
description add-field-description --onProperty customer --title "Client" --text "Le client qui passe la commande" --locale fr;
association set-selection-mode --onProperty customer --selectionMode COMBOBOX;
association set-type --onProperty customer --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field customer.fullName;
constraint NotNull --onProperty customer;
description add-notNull-message --onProperty customer --title "The client ordering must be selected" --text "The client ordering must be selected";
description add-notNull-message --onProperty customer --title "Le client doit être sélectionné" --text "Le client doit être sélectionné" --locale fr;

field manyToOne --named insurance --fieldType ~.jpa.Insurrance;
description add-field-description --onProperty insurance --title "Insurance" --text "The Insurance in charge";
description add-field-description --onProperty insurance --title "Assurance" --text "Assurance prenant la facture en charge" --locale fr;
association set-selection-mode --onProperty insurance --selectionMode FORWARD;
association set-type --onProperty insurance --type AGGREGATION --targetEntity ~.jpa.Insurrance;
display add-list-field --field insurance.customer.fullName;
display add-list-field --field insurance.insurer.fullName;

field manyToOne --named vat --fieldType ~.jpa.VAT;
description add-field-description --onProperty vat --title "VAT" --text "The value added tax";
description add-field-description --onProperty vat --title "TVA" --text "La taxe sur valeur ajouté" --locale fr;
association set-selection-mode --onProperty vat --selectionMode COMBOBOX;
association set-type --onProperty vat --type AGGREGATION --targetEntity ~.jpa.VAT;
display add-list-field --field vat.rate;

field manyToOne --named salesAgent --fieldType ~.jpa.Login;
description add-field-description --onProperty salesAgent --title "Sales Agent" --text "The user making this sale";
description add-field-description --onProperty salesAgent --title "Vendeur" --text "Utilisateur éffectuant cette vente" --locale fr;
association set-selection-mode --onProperty salesAgent --selectionMode FORWARD;
association set-type --onProperty salesAgent --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field salesAgent.fullName;
constraint NotNull --onProperty salesAgent;
description add-notNull-message --onProperty salesAgent --title "The user making this sale must be selected" --text "The user making this sale must be selected";
description add-notNull-message --onProperty salesAgent --title "Le vendeur doit être sélectionné" --text "Le vendeur doit être sélectionné" --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The Agency where sale has been made";
description add-field-description --onProperty agency --title "Agence" --text "Agence dans la quelle la vente a été éffectuée" --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency ;
description add-notNull-message --onProperty agency --title "The agency where sale has been made must be selected" --text "The agency where sale has been made must be selected";
description add-notNull-message --onProperty agency --title "Le site dans la quelle la vente a été éffectuée doit être sélectionné" --text "Le site dans la quelle la vente a été éffectuée doit être sélectionné" --locale fr;

field custom --named salesOrderStatus --type ~.jpa.DocumentProcessingState.java;
description add-field-description --onProperty salesOrderStatus --title "Status" --text "The status of this sales order.";
description add-field-description --onProperty salesOrderStatus --title "Status" --text "État de cette commande client." --locale fr;
enum enumerated-field --onProperty salesOrderStatus ;
display add-list-field --field salesOrderStatus;

field boolean --named cashed --primitive false;
description add-field-description --onProperty cashed --title "Cashed" --text "Indicates whether the order has been cashed or not.";
description add-field-description --onProperty cashed --title "Encaissé" --text "Indique si la commande a été encaissée ou non." --locale fr;
@/* default=false */;
display add-list-field --field cashed;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this sales order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette commande client." --locale fr;
format add-number-type --onProperty amountBeforeTax --type CURRENCY ;
@/* Default=0, montant_ht */;
display add-list-field --field amountBeforeTax;

field number --named amountVAT --type java.math.BigDecimal;
description add-field-description --onProperty amountVAT --title "Amount VAT" --text "Total amount VAT for this sales order.";
description add-field-description --onProperty amountVAT --title "Montant TVA" --text "Montant total TVA pour cette commande client." --locale fr;
format add-number-type --onProperty amountVAT --type CURRENCY ;
@/* Default=0, montant_tva */;
display add-list-field --field amountVAT;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this sales order. The sum of all discounts.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Remise totale de la commande, c est-à-dire la somme des remise totales des  lignes de commande." --locale fr;
format add-number-type --onProperty amountDiscount --type CURRENCY ;
display add-list-field --field amountDiscount;

field number --named totalReturnAmount --type java.math.BigDecimal;
description add-field-description --onProperty totalReturnAmount --title "Total Return Amount" --text "Total Return Amount.";
description add-field-description --onProperty totalReturnAmount --title "Montant total retour" --text "Montant total retour." --locale fr;
format add-number-type --onProperty totalReturnAmount --type CURRENCY ;
display add-list-field --field totalReturnAmount;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this sales order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette commande client." --locale fr;
format add-number-type --onProperty amountAfterTax --type CURRENCY ;
display add-list-field --field amountAfterTax;

field custom --named salesOrderType --type ~.jpa.SalesOrderType;
description add-field-description --onProperty salesOrderType --title "Type" --text "The type of this sales order.";
description add-field-description --onProperty salesOrderType --title "Type" --text "Le type de cette commande client." --locale fr;
enum enumerated-field --onProperty salesOrderType;
display add-list-field --field salesOrderType;

field oneToMany --named salesOrderItems --fieldType ~.jpa.SalesOrderItem --inverseFieldName salesOrder;
description add-field-description --onProperty salesOrderItems --title "Sales Order Items" --text "Sales order items owned by the sales order";
description add-field-description --onProperty salesOrderItems --title "Lignes Inventaire" --text "Les lignes sous controlle de cette commande client" --locale fr;
association set-type --onProperty salesOrderItems --type COMPOSITION --targetEntity ~.jpa.SalesOrderItem;
association set-selection-mode --onProperty salesOrderItems --selectionMode TABLE;

cd ../SalesOrderItem.java;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order containing this item";
description add-field-description --onProperty salesOrder --title "Commande client" --text "Commande client contenant cette ligne" --locale fr;
association set-type --onProperty salesOrder --type COMPOSITION --targetEntity ~.jpa.SalesOrder;


@/* Invoice */;

@/* InvoiceType */;
java new-enum-type --named InvoiceType --package ~.jpa ;
enum add-enum-class-description --title "Invoice Type" --text "The type of this invoice.";
enum add-enum-class-description  --locale fr --title "Type de Facture" --text "Le type d une facture.";
java new-enum-const CASHDRAWER;
enum add-enum-constant-description --onConstant CASHDRAWER --title "Cashdrawer invoice" --text "Cashdrawer invoice";
enum add-enum-constant-description --locale fr --onConstant CASHDRAWER --title "Facture de caisse" --text "Facture de caisse";
java new-enum-const PROFORMA;
enum add-enum-constant-description --onConstant PROFORMA --title "Proforma Invoice" --text "Proforma Invoice";
enum add-enum-constant-description --locale fr --onConstant PROFORMA --title "Facture Proforma" --text "Facture proforma";
java new-enum-const VOUCHER;
enum add-enum-constant-description --onConstant VOUCHER --title "Vouche invoice" --text "Voucher invoice";
enum add-enum-constant-description --locale fr --onConstant VOUCHER --title "Facture d avoir" --text "Facture d avoir";

@/* Entite Ligne Facture */;
entity --named CustomerInvoiceItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice Item" --text "An invoice item.";
description add-class-description  --locale fr --title "Ligne Facture" --text "Une ligne facture.";


field string --named internalPic;
description add-field-description --onProperty internalPic --title "Local PIC" --text "The Local pic of referenced product.";
description add-field-description --onProperty internalPic --title "Cip MAison" --text "Le cip Maison du produit reference." --locale fr;
display add-toString-field --field internalPic;
display add-list-field --field internalPic;

field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The  Article";
description add-field-description --onProperty article --title "Article" --text "Article ." --locale fr;
association set-selection-mode --onProperty article --selectionMode COMBOBOX;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;


field long --named indexLine --primitive false; 
description add-field-description --onProperty indexLine --title "Line Index" --text "Index for searching through invoice items";
description add-field-description --onProperty indexLine --title "Index de Ligne" --text "Index permettant de rechercher la ligne de facture" --locale fr;
display add-toString-field --field indexLine;
display add-list-field --field indexLine;

field number --named purchasedQty --type java.math.BigDecimal;
description add-field-description --onProperty purchasedQty --title "Quantity Purchased" --text "The quantity purchased in this line.";
description add-field-description --onProperty purchasedQty --title "Quantité Achetée" --text "La quantité achetée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-toString-field --field purchasedQty;
display add-list-field --field purchasedQty;

field number --named salesPricePU --type java.math.BigDecimal;
description add-field-description --onProperty salesPricePU --title "Sales Price per Unit" --text "The sales price per unit for product of this line.";
description add-field-description --onProperty salesPricePU --title "Prix de Vente Unitaire" --text "Prix unitaire du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty salesPricePU --type CURRENCY ;
display add-list-field --field salesPricePU;

field number --named totalSalesPrice --type java.math.BigDecimal;
description add-field-description --onProperty totalSalesPrice --title "Total Sales Price" --text "The total sales price for product of this line.";
description add-field-description --onProperty totalSalesPrice --title "Prix de Vente Total" --text "Prix total du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty totalSalesPrice --type CURRENCY ;
display add-list-field --field totalSalesPrice;


@/* Entité Facture */;
entity --named CustomerInvoice --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice" --text "An invoice.";
description add-class-description  --locale fr --title "Facture" --text "Une facture.";


field custom --named invoiceType --type ~.jpa.InvoiceType;
description add-field-description --onProperty invoiceType --title "Invoice Type" --text "The type of this invoice.";
description add-field-description --onProperty invoiceType --title "Type de Facture" --text "Le type de cette facture." --locale fr;
enum enumerated-field --onProperty invoiceType ;
display add-list-field --field invoiceType;

field string --named invoiceNumber;
description add-field-description --onProperty invoiceNumber --title "Invoice Number" --text "The number of the invoice.";
description add-field-description --onProperty invoiceNumber --title "Numéro Facture" --text "Le numéro de cette facture." --locale fr;
display add-toString-field --field invoiceNumber;
display add-list-field --field invoiceNumber;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this invoicd.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette facture." --locale fr;
display add-list-field --field creationDate;
format add-date-pattern --onProperty creationDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named customer --fieldType ~.jpa.Customer;
description add-field-description --onProperty customer --title "Customer" --text "The client referenced by this invoice";
description add-field-description --onProperty customer --title "Client" --text "Le client mentionné sur la facture" --locale fr;
association set-selection-mode --onProperty customer --selectionMode COMBOBOX;
association set-type --onProperty customer --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field customer.fullName;
constraint NotNull --onProperty customer ;
description add-notNull-message --onProperty customer --title "The customer must be selected" --text "The customer must be selected";
description add-notNull-message --onProperty customer --title "Le client doit être sélectionné" --text "Le client doit être sélectionné" --locale fr;

field manyToOne --named insurance --fieldType ~.jpa.Insurrance;
description add-field-description --onProperty insurance --title "Insurance" --text "The Insurance in charge";
description add-field-description --onProperty insurance --title "Assurance" --text "Assurance prenant la facture en charge" --locale fr;
association set-selection-mode --onProperty insurance --selectionMode COMBOBOX;
association set-type --onProperty insurance --type AGGREGATION --targetEntity ~.jpa.Insurrance;
display add-list-field --field insurance.customer.fullName;
display add-list-field --field insurance.insurer.fullName;

field manyToOne --named creatingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty creatingUser --title "Sales Agent" --text "The user creating this invoice";
description add-field-description --onProperty creatingUser --title "Vendeur" --text "Éditeur de cette facture" --locale fr;
constraint NotNull --onProperty creatingUser;
description add-notNull-message --onProperty creatingUser --title "The creating user must be selected" --text "The creating user must be selected";
description add-notNull-message --onProperty creatingUser --title "Utilisateur créant doit être sélectionné" --text "Utilisateur créant doit être sélectionné" --locale fr;
association set-selection-mode --onProperty creatingUser --selectionMode COMBOBOX;
association set-type --onProperty creatingUser --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field creatingUser.fullName;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The agency where sale has been made.";
description add-field-description --onProperty agency --title "Agence" --text "Agence dans la quelle la vente a été éffectuée." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The agency of this sale must be selected" --text "The agency of this sale must be selected";
description add-notNull-message --onProperty agency --title "Le site de cette vente doit être sélectionné" --text "Le site de cette vente doit être sélectionné" --locale fr;

field manyToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order generating of this invoice.";
description add-field-description --onProperty salesOrder --title "Commande Client" --text "Commande client originaire de la facture" --locale fr;
association set-selection-mode --onProperty salesOrder --selectionMode  COMBOBOX;
association set-type --onProperty salesOrder --type AGGREGATION --targetEntity ~.jpa.SalesOrder;
display add-list-field --field salesOrder.soNumber;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the invoice is settled.";
description add-field-description --onProperty settled --title "Soldée" --text "Indique si la facture est soldée ou pas." --locale fr;
@/* default=false */;
display add-list-field --field settled;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this sales order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette commande client." --locale fr;
@/* Default=0, montant_ht */;
display add-list-field --field amountBeforeTax;

field number --named amountVAT --type java.math.BigDecimal;
description add-field-description --onProperty amountVAT --title "Amount VAT" --text "Total amount VAT for this sales order.";
description add-field-description --onProperty amountVAT --title "Montant TVA" --text "Montant total TVA pour cette commande client." --locale fr;
format add-number-type --onProperty amountVAT --type CURRENCY ;
display add-list-field --field amountVAT;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this sales order. The sum of all discounts.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Remise totale de la commande, c est-à-dire la somme des remise totales des  lignes de commande." --locale fr;
format add-number-type --onProperty amountDiscount --type CURRENCY;
display add-list-field --field amountDiscount;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this sales order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette commande client." --locale fr;
format add-number-type --onProperty amountAfterTax --type CURRENCY;
display add-list-field --field amountAfterTax;

field number --named netToPay --type java.math.BigDecimal;
description add-field-description --onProperty netToPay --title "Net a Payer" --text "The net amount to pay.";
description add-field-description --onProperty netToPay --title "Net a Payer" --text "Le montant net à payer." --locale fr;
format add-number-type --onProperty netToPay --type CURRENCY ;
display add-list-field --field netToPay;

field number --named customerRestTopay --type java.math.BigDecimal;
description add-field-description --onProperty customerRestTopay --title "Customer Rest To pay" --text "Customer Rest To pay.";
description add-field-description --onProperty customerRestTopay --title "Rest A payer client" --text "Rest A payer client." --locale fr;
format add-number-type --onProperty customerRestTopay --type CURRENCY;
display add-list-field --field customerRestTopay;

field number --named insurranceRestTopay --type java.math.BigDecimal;
description add-field-description --onProperty insurranceRestTopay --title "Insurrance Rest To pay" --text "Insurrance Rest To pay.";
description add-field-description --onProperty insurranceRestTopay --title "Reste A payer client" --text "Reste A payer client." --locale fr;
format add-number-type --onProperty insurranceRestTopay --type CURRENCY;
display add-list-field --field insurranceRestTopay;

field boolean --named cashed --primitive false; 
description add-field-description --onProperty cashed --title "Cashed" --text "Sates if the invoice is cashed.";
description add-field-description --onProperty cashed --title "encaisseé" --text "Indique si la facture est encaissée ou pas." --locale fr;
@/* default=false */;
display add-list-field --field cashed;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "The advance payment.";
description add-field-description --onProperty advancePayment --title "Net a Payer" --text "L avance sur paiement." --locale fr;
format add-number-type --onProperty advancePayment --type CURRENCY;
display add-list-field --field advancePayment;

field number --named totalRestToPay --type java.math.BigDecimal;
description add-field-description --onProperty totalRestToPay --title "Ret to Pay" --text "The rest to pay.";
description add-field-description --onProperty totalRestToPay --title "Reste a Payer" --text "Le reste a payer." --locale fr;
format add-number-type --onProperty totalRestToPay --type CURRENCY;
display add-list-field --field totalRestToPay;

field oneToMany --named invoiceItems --fieldType ~.jpa.CustomerInvoiceItem --inverseFieldName invoice;
description add-field-description --onProperty invoiceItems --title "Invoice Items" --text "The invoice items";
description add-field-description --onProperty invoiceItems --title "Lignes Facture" --text "Les ligne facture" --locale fr;
association set-type --onProperty invoiceItems --type COMPOSITION --targetEntity ~.jpa.CustomerInvoiceItem;
association set-selection-mode --onProperty invoiceItems --selectionMode TABLE;

cd ../CustomerInvoiceItem.java;
description add-field-description --onProperty invoice --title "Invoice" --text "The invoice containing this item";
description add-field-description --onProperty invoice --title "Facture" --text "Facture contenant cette ligne" --locale fr;
association set-type --onProperty invoice --type COMPOSITION --targetEntity ~.jpa.CustomerInvoice;

cd ~~


@/* Entite Ligne Facture Fournisseur */;
entity --named SupplierInvoiceItem --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Supplier Invoice Item" --text "An supplier invoice item.";
description add-class-description  --locale fr --title "Ligne Facture Fournisseur" --text "Une ligne facture fournisseur.";


field string --named internalPic;
description add-field-description --onProperty internalPic --title "Local PIC" --text "The Local pic of referenced product.";
description add-field-description --onProperty internalPic --title "Cip MAison" --text "Le cip Maison du produit reference." --locale fr;
display add-toString-field --field internalPic;
display add-list-field --field internalPic;

field manyToOne --named article --fieldType ~.jpa.Article;
description add-field-description --onProperty article --title "Article" --text "The  Article";
description add-field-description --onProperty article --title "Article" --text "Article ." --locale fr;
association set-selection-mode --onProperty article --selectionMode COMBOBOX;
association set-type --onProperty article --type AGGREGATION --targetEntity ~.jpa.Article;
display add-toString-field --field article.articleName;
display add-list-field --field article.articleName;


field long --named indexLine --primitive false; 
description add-field-description --onProperty indexLine --title "Line Index" --text "Index for searching through invoice items";
description add-field-description --onProperty indexLine --title "Index de Ligne" --text "Index permettant de rechercher la ligne de facture" --locale fr;
display add-toString-field --field indexLine;
display add-list-field --field indexLine;

field number --named deliveryQty --type java.math.BigDecimal;
description add-field-description --onProperty deliveryQty --title "Quantity delivery" --text "The quantity delivered in this line.";
description add-field-description --onProperty deliveryQty --title "Quantité Livrée" --text "La quantité livrée dans cette ligne." --locale fr;
@/* Default=0 */;
display add-toString-field --field deliveryQty;
display add-list-field --field deliveryQty;

field number --named purchasePricePU --type java.math.BigDecimal;
description add-field-description --onProperty purchasePricePU --title "Purchase Price per Unit" --text "The order price per unit for product of this line.";
description add-field-description --onProperty purchasePricePU --title "Prix d achat Unitaire" --text "Prix unitaire du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty purchasePricePU --type CURRENCY ;
display add-list-field --field purchasePricePU;

field number --named totalPurchasePrice --type java.math.BigDecimal;
description add-field-description --onProperty totalPurchasePrice --title "Total Order Price" --text "The total sales price for product of this line.";
description add-field-description --onProperty totalPurchasePrice --title "Prix de Order Total" --text "Prix total du produit de la ligne de facture" --locale fr;
format add-number-type --onProperty totalPurchasePrice --type CURRENCY ;
display add-list-field --field totalPurchasePrice;


@/* Entité Facture */;
entity --named SupplierInvoice --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Invoice" --text "An invoice.";
description add-class-description  --locale fr --title "Facture" --text "Une facture.";


field custom --named invoiceType --type ~.jpa.InvoiceType;
description add-field-description --onProperty invoiceType --title "Invoice Type" --text "The type of this invoice.";
description add-field-description --onProperty invoiceType --title "Type de Facture" --text "Le type de cette facture." --locale fr;
enum enumerated-field --onProperty invoiceType ;
display add-list-field --field invoiceType;

field string --named invoiceNumber;
description add-field-description --onProperty invoiceNumber --title "Invoice Number" --text "The number of the invoice.";
description add-field-description --onProperty invoiceNumber --title "Numéro Facture" --text "Le numéro de cette facture." --locale fr;
display add-toString-field --field invoiceNumber;
display add-list-field --field invoiceNumber;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern= dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this invoicd.";
description add-field-description --onProperty creationDate --title "Date de Création" --text "La date de création de cette facture." --locale fr;
display add-list-field --field creationDate;
format add-date-pattern --onProperty creationDate --pattern "dd-MM-yyyy HH:mm"; 

field manyToOne --named supplier --fieldType ~.jpa.Supplier;
description add-field-description --onProperty supplier --title "Supplier" --text "The Supplier referenced by this invoice";
description add-field-description --onProperty supplier --title "Fournisseur" --text "Le Fournisseur mentionné sur la facture" --locale fr;
association set-selection-mode --onProperty supplier --selectionMode COMBOBOX;
association set-type --onProperty supplier --type AGGREGATION --targetEntity ~.jpa.Supplier;
display add-list-field --field supplier.name;
constraint NotNull --onProperty  supplier ;
description add-notNull-message --onProperty supplier --title "The supplier must be selected" --text "The customer must be selected";
description add-notNull-message --onProperty supplier --title "Le Founisseur doit être sélectionné" --text "Le client doit être sélectionné" --locale fr;

field manyToOne --named creatingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty creatingUser --title "Sales Agent" --text "The user creating this invoice";
description add-field-description --onProperty creatingUser --title "Vendeur" --text "Éditeur de cette facture" --locale fr;
constraint NotNull --onProperty creatingUser;
description add-notNull-message --onProperty creatingUser --title "The creating user must be selected" --text "The creating user must be selected";
description add-notNull-message --onProperty creatingUser --title "Utilisateur créant doit être sélectionné" --text "Utilisateur créant doit être sélectionné" --locale fr;
association set-selection-mode --onProperty creatingUser --selectionMode COMBOBOX;
association set-type --onProperty creatingUser --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field creatingUser.fullName;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "The agency where sale has been made.";
description add-field-description --onProperty agency --title "Agence" --text "Agence dans la quelle la vente a été éffectuée." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The agency of this sale must be selected" --text "The agency of this sale must be selected";
description add-notNull-message --onProperty agency --title "Le site de cette vente doit être sélectionné" --text "Le site de cette vente doit être sélectionné" --locale fr;

field manyToOne --named delivery --fieldType ~.jpa.Delivery;
description add-field-description --onProperty delivery --title "Delivery" --text "The Delivery  generating of this invoice.";
description add-field-description --onProperty delivery --title "Livraison Fournisseur" --text "La livraison  originaire de la facture" --locale fr;
association set-selection-mode --onProperty delivery --selectionMode  COMBOBOX;
association set-type --onProperty delivery --type AGGREGATION --targetEntity ~.jpa.Delivery;
display add-list-field --field delivery.deliveryNumber;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the invoice is settled.";
description add-field-description --onProperty settled --title "Soldée" --text "Indique si la facture est soldée ou pas." --locale fr;
@/* default=false */;
display add-list-field --field settled;

field number --named amountBeforeTax --type java.math.BigDecimal;
description add-field-description --onProperty amountBeforeTax --title "Amount Before Tax" --text "Total amount before tax for this sales order.";
description add-field-description --onProperty amountBeforeTax --title "Montant hors Taxes" --text "Montant total hors Taxes pour cette commande client." --locale fr;
@/* Default=0, montant_ht */;
display add-list-field --field amountBeforeTax;

field number --named amountVAT --type java.math.BigDecimal;
description add-field-description --onProperty amountVAT --title "Amount VAT" --text "Total amount VAT for this sales order.";
description add-field-description --onProperty amountVAT --title "Montant TVA" --text "Montant total TVA pour cette commande client." --locale fr;
format add-number-type --onProperty amountVAT --type CURRENCY ;
display add-list-field --field amountVAT;

field number --named amountDiscount --type java.math.BigDecimal;
description add-field-description --onProperty amountDiscount --title "Discount Amount" --text "Discount amount for this sales order. The sum of all discounts.";
description add-field-description --onProperty amountDiscount --title "Montant Remise" --text "Remise totale de la commande, c est-à-dire la somme des remise totales des  lignes de commande." --locale fr;
format add-number-type --onProperty amountDiscount --type CURRENCY;
display add-list-field --field amountDiscount;

field number --named amountAfterTax --type java.math.BigDecimal;
description add-field-description --onProperty amountAfterTax --title "Amount after Tax" --text "Total amount after tax for this sales order.";
description add-field-description --onProperty amountAfterTax --title "Montant TTC" --text "Montant total TTC pour cette commande client." --locale fr;
format add-number-type --onProperty amountAfterTax --type CURRENCY;
display add-list-field --field amountAfterTax;

field number --named netToPay --type java.math.BigDecimal;
description add-field-description --onProperty netToPay --title "Net a Payer" --text "The net amount to pay.";
description add-field-description --onProperty netToPay --title "Net a Payer" --text "Le montant net à payer." --locale fr;
format add-number-type --onProperty netToPay --type CURRENCY ;
display add-list-field --field netToPay;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "The advance payment.";
description add-field-description --onProperty advancePayment --title "Net a Payer" --text "L avance sur paiement." --locale fr;
format add-number-type --onProperty advancePayment --type CURRENCY;
display add-list-field --field advancePayment;

field number --named totalRestToPay --type java.math.BigDecimal;
description add-field-description --onProperty totalRestToPay --title "Ret to Pay" --text "The rest to pay.";
description add-field-description --onProperty totalRestToPay --title "Reste a Payer" --text "Le reste a payer." --locale fr;
format add-number-type --onProperty totalRestToPay --type CURRENCY;
display add-list-field --field totalRestToPay;

field oneToMany --named invoiceItems --fieldType ~.jpa.SupplierInvoiceItem --inverseFieldName invoice;
description add-field-description --onProperty invoiceItems --title "Invoice Items" --text "The invoice items";
description add-field-description --onProperty invoiceItems --title "Lignes Facture" --text "Les ligne facture" --locale fr;
association set-type --onProperty invoiceItems --type COMPOSITION --targetEntity ~.jpa.SupplierInvoiceItem;
association set-selection-mode --onProperty invoiceItems --selectionMode TABLE;

cd ../SupplierInvoiceItem.java;
description add-field-description --onProperty invoice --title "Invoice" --text "The invoice containing this item";
description add-field-description --onProperty invoice --title "Facture" --text "Facture contenant cette ligne" --locale fr;
association set-type --onProperty invoice --type COMPOSITION --targetEntity ~.jpa.SupplierInvoice;



@/* Mode Paiement*/;
java new-enum-type  --named PaymentMode --package ~.jpa;
enum add-enum-class-description --title "Payment Mode" --text "Mode of payment.";
enum add-enum-class-description  --locale fr --title "Mode Paiement" --text "Mode de paiement.";
java new-enum-const CASH;
enum add-enum-constant-description --onConstant CASH --title "Cash" --text "Cash Payment";
enum add-enum-constant-description --locale fr --onConstant CASH --title "Caisse" --text "Paiement Caisse";
java new-enum-const CHECK;
enum add-enum-constant-description --onConstant CHECK --title "Check" --text "Check Payement";
enum add-enum-constant-description --locale fr --onConstant CHECK --title "Cheque" --text "Paiement Cheque";
java new-enum-const CREDIT_CARD;
enum add-enum-constant-description --onConstant CREDIT_CARD --title "Credit Card" --text "Credit card payment";
enum add-enum-constant-description --locale fr --onConstant CREDIT_CARD --title "Carte de crédit" --text "Paiement par cartes de crédit.";
java new-enum-const VOUCHER;
enum add-enum-constant-description --onConstant VOUCHER --title "Client Voucher" --text "Client voucher";
enum add-enum-constant-description --locale fr --onConstant VOUCHER --title "Avoir Client" --text "Avoir client";

@/* Entité  paiement */;
entity --named Payment --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Payment" --text "A payment.";
description add-class-description  --locale fr --title "Paiement" --text "Un paiement.";

field string --named paymentNumber;
description add-field-description --onProperty paymentNumber --title "Payment Number" --text "The paiment number.";
description add-field-description --onProperty paymentNumber --title "Numéro du Paiement" --text "Le numéro du paiement." --locale fr;
display add-toString-field --field paymentNumber;
display add-list-field --field paymentNumber;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern=dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "The payment date.";
description add-field-description --onProperty paymentDate --title "Date de Paiement" --text "La date de paiement." --locale fr;
display add-list-field --field paymentDate;
format add-date-pattern --onProperty paymentDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named recordDate; 
@/* pattern=dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty recordDate --title "Record Date" --text "The record date for this paiement.";
description add-field-description --onProperty recordDate --title "Date de Saisie" --text "La date de saisie du paiement." --locale fr;
display add-list-field --field recordDate;
format add-date-pattern --onProperty recordDate --pattern "dd-MM-yyyy HH:mm"; 

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Payment Amount" --text "The payment amount.";
description add-field-description --onProperty amount --title "Montant du Paiement" --text "Le montant du paiement." --locale fr;
format add-number-type --onProperty amount --type CURRENCY;
display add-list-field --field amount;

field number --named receivedAmount --type java.math.BigDecimal;
description add-field-description --onProperty receivedAmount --title "Received Amount" --text "The amount received from the payment.";
description add-field-description --onProperty receivedAmount --title "Montant Reçue" --text "Le montant reçue du paiement." --locale fr;
format add-number-type --onProperty receivedAmount --type CURRENCY;
display add-list-field --field receivedAmount;

field number --named difference --type java.math.BigDecimal;
description add-field-description --onProperty difference --title "Difference" --text "The difference (amount returned to payer).";
description add-field-description --onProperty difference --title "Différence" --text "La différence (montant retourné au client)." --locale fr;
format add-number-type --onProperty difference --type CURRENCY;
display add-list-field --field difference;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Agency in which the payment occurs.";
description add-field-description --onProperty agency --title "Agency" --text "Agence dans lequel s effectue le paiement." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The agency of this payment must be selected" --text "The agency of this payment must be selected";
description add-notNull-message --onProperty agency --title "Le site de cet paiement doit être sélectionné" --text "Le site de cet paiement doit être sélectionné" --locale fr;

field manyToOne --named cashier --fieldType ~.jpa.Login;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment.";
description add-field-description --onProperty cashier --title "Caissier" --text "L utilisateur percevant le paiement." --locale fr;
association set-selection-mode --onProperty cashier --selectionMode COMBOBOX;
association set-type --onProperty cashier --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field cashier.fullName;
constraint NotNull --onProperty cashier;
description add-notNull-message --onProperty cashier --title "The cashier must be selected" --text "The cashier must be selected";
description add-notNull-message --onProperty cashier --title "Le caissier doit être sélectionné" --text "Le caissier doit être sélectionné" --locale fr;

field manyToOne --named cashDrawer --fieldType ~.jpa.CashDrawer;
description add-field-description --onProperty cashDrawer --title "Cash Drawer" --text "The cash drawer in use.";
description add-field-description --onProperty cashDrawer --title "Caisse" --text "La caisse utilisé." --locale fr;
association set-selection-mode --onProperty cashDrawer --selectionMode COMBOBOX;
association set-type --onProperty cashDrawer --type AGGREGATION --targetEntity ~.jpa.CashDrawer;
display add-list-field --field cashDrawer.cashDrawerNumber;

relationship add --sourceEntity ~.jpa.Payment --sourceQualifier invoices --targetEntity ~.jpa.CustomerInvoice --targetQualifier payments;

cd ../Payment.java;
description add-field-description --onProperty invoices --title "Invoices" --text "Invoices associated with this payment";
description add-field-description --onProperty invoices --title "Facture" --text "Facture associe avec ce payement" --locale fr;
association set-type --onProperty invoices --type AGGREGATION --targetEntity ~.jpa.CustomerInvoice;
association set-selection-mode --onProperty invoices --selectionMode TABLE;

cd ../CustomerInvoice.java;
description add-field-description --onProperty payments --title "Payments" --text "payments associated with this invoice";
description add-field-description --onProperty payments --title "Paiements" --text "Payement associe avec cette facture" --locale fr;
association set-type --onProperty payments --type AGGREGATION --targetEntity ~.jpa.Payment;

cd ../Payment.java;

field custom --named paymentMode --type ~.jpa.PaymentMode;
description add-field-description --onProperty paymentMode --title "Payment Mode" --text "The Mode of this payment.";
description add-field-description --onProperty paymentMode --title "Mode de Paiement" --text "Le Mode de paiement." --locale fr;
enum enumerated-field --onProperty paymentMode ;
display add-list-field --field paymentMode;

field boolean --named paymentReceiptPrinted --primitive false;
description add-field-description --onProperty paymentReceiptPrinted --title "Open" --text "Indicates whether the payment receipt is printed or not.";
description add-field-description --onProperty paymentReceiptPrinted --title "Ouverte" --text "Indique si le reçu de paiement est imprimé ou pas." --locale fr;
@/* default=false */;
display add-list-field --field paymentReceiptPrinted;

field manyToOne --named paidBy --fieldType ~.jpa.Customer;
description add-field-description --onProperty paidBy --title "Paid By" --text "Paid By.";
description add-field-description --onProperty paidBy --title "Payer Par" --text "Payer Par." --locale fr;
association set-selection-mode --onProperty paidBy --selectionMode COMBOBOX;
association set-type --onProperty paidBy --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field paidBy.fullName;


@/* Entité EtatCredits */;
entity --named DebtStatement --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Debt Statement" --text "The sum of all the debts of a client";
description add-class-description  --locale fr --title "Etat Credits" --text "Le cumul de toutes les dettes d un client";

field string --named statementNumber;
description add-field-description --onProperty statementNumber --title "Statement Number" --text "The number identifying this statement.";
description add-field-description --onProperty statementNumber --title "Numéro de l État" --text "Le numéro identifiant cet état." --locale fr;
display add-toString-field --field statementNumber;
display add-list-field --field statementNumber;

@/* Why not an insurance object */;
field manyToOne --named insurrance --fieldType ~.jpa.Customer;
description add-field-description --onProperty insurrance --title "Insurrance" --text "The client carrying this debt.";
description add-field-description --onProperty insurrance --title "Insurrance" --text "Le client portant cette dette." --locale fr;
association set-selection-mode --onProperty insurrance --selectionMode COMBOBOX;
association set-type --onProperty insurrance --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field insurrance.fullName;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Agency in which the debt originated";
description add-field-description --onProperty agency --title "Agency" --text "Agence dans lequel la dette a été réalisée" --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The originating agency of this debt must be selected" --text "The originating agency of this debt must be selected";
description add-notNull-message --onProperty agency --title "Le site de cette dette doit être sélectionné" --text "Le site de cette dette doit être sélectionné" --locale fr;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "Date these debts was paid.";
description add-field-description --onProperty paymentDate --title "Date Paiement" --text "Date à laquelle ces dettes ont été payées." --locale fr;
display add-list-field --field paymentDate;
format add-date-pattern --onProperty paymentDate --pattern "dd-MM-yyyy HH:mm"; 

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "Initial amount of this statement.";
description add-field-description --onProperty initialAmount --title "Montant Initial" --text "Montant initial de cet état." --locale fr;
format add-number-type --onProperty initialAmount --type CURRENCY;
display add-list-field --field initialAmount;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "Advance payment.";
description add-field-description --onProperty advancePayment --title "Montant Avancé" --text "Montant avancé." --locale fr;
format add-number-type --onProperty advancePayment --type CURRENCY;
display add-list-field --field advancePayment;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining amount of this debt.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Le montant restant a payer pour cette dette." --locale fr;
format add-number-type --onProperty restAmount --type CURRENCY;
display add-list-field --field restAmount;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the statement is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si cet état est soldé ou non." --locale fr;
@/* default=false */;
display add-list-field --field settled;

field number --named amountFromVouchers --type java.math.BigDecimal;
description add-field-description --onProperty amountFromVouchers --title "Rest Amount" --text "Amount of of vouchers if the customer uses vouchers to pay these debts.";
description add-field-description --onProperty amountFromVouchers --title "Montant Restant" --text "Montant de l avoir si le client en possède utilisé pour rembourser les dettes." --locale fr;
format add-number-type --onProperty amountFromVouchers --type CURRENCY;
display add-list-field --field amountFromVouchers;

field boolean --named canceled --primitive false; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the statement is canceled or not.";
description add-field-description --onProperty canceled --title "Annulée" --text "Precise si l états a été annulée ou non." --locale fr;
@/* default=false */;
display add-list-field --field canceled;

field boolean --named useVoucher --primitive false; 
description add-field-description --onProperty useVoucher --title "Use Vouchers" --text "Specifies whether the client can use his voucher to pay its debts.";
description add-field-description --onProperty useVoucher --title "Consommer Avoir" --text "Indique si le client peut consommer ou non ses avoirs pour payer ses dettes." --locale fr;
@/* default=false */;
display add-list-field --field useVoucher;


relationship add --sourceEntity ~.jpa.DebtStatement --sourceQualifier invoices --targetEntity ~.jpa.CustomerInvoice;
cd ../DebtStatement.java;
description add-field-description --onProperty invoices --title "Invoices" --text "Invoices referenced by this debt statement";
description add-field-description --onProperty invoices --title "Factures" --text "Factures contenant cette dette" --locale fr;
association set-type --onProperty invoices --type AGGREGATION --targetEntity ~.jpa.CustomerInvoice;


@/* ================================== */;
@/* Prescriptions */;

@/* Entité Hospital */;
entity --named Hospital --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Hospital" --text "Hospital";
description add-class-description  --locale fr --title "Hopital" --text "Hopital";

field string --named name;
description add-field-description --onProperty name --title "Hospital Name" --text "The name of this Hospital.";
description add-field-description --onProperty name --title "Nom de cet Hopital" --text "Le nom de cet hopital." --locale fr;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The hospital name is required" --text "The hospital name is required";
description add-notNull-message --onProperty name --title "Le nom de cet hopital est réquis" --text "Le nom de cet hopital est réquis" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The hospital Phone.";
description add-field-description --onProperty phone --title "Telephone" --text "Telephone" --locale fr;
display add-list-field --field phone;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue" --locale fr;
display add-list-field --field street;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code oif this address";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette adresse" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this address";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette adresse" --locale fr;
display add-list-field --field city;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The zip code oif this address";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette adresse" --locale fr;

@/* Entité Prescriber */;
entity --named Prescriber --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Prescriber" --text "Prescriber";
description add-class-description  --locale fr --title "Prescripteur" --text "Prescripteur";

field string --named name;
description add-field-description --onProperty name --title "Prescriber Name" --text "The name of this Prescrip.";
description add-field-description --onProperty name --title "Nom du Prescripteur" --text "Le nom de ce Prescripteur." --locale fr;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The prescriber name is required" --text "The prescriber name is required";
description add-notNull-message --onProperty name --title "Le nom du prescripteur est réquis" --text "Le nom du prescripteur est réquis" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field string --named phone;
description add-field-description --onProperty phone --title "Phone" --text "The Prescriber Phone.";
description add-field-description --onProperty phone --title "Telephone" --text "Telephone" --locale fr;
display add-list-field --field phone;

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue" --locale fr;
display add-list-field --field street;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code oif this address";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette adresse" --locale fr;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this address";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette adresse" --locale fr;
display add-list-field --field city;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The zip code oif this address";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette adresse" --locale fr;

@/* Entité Ordonnanciers */;
entity --named PrescriptionBook --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Prescription Blook" --text "Prescription book";
description add-class-description  --locale fr --title "Ordonnancier" --text "Ordonnancier";

field manyToOne --named prescriber --fieldType ~.jpa.Prescriber;
description add-field-description --onProperty prescriber --title "Prescriber" --text "The doctor who prescribed the order.";
description add-field-description --onProperty prescriber --title "Prescripteur" --text "Le medecin ayant prescrit de cet ordonnance." --locale fr;
association set-selection-mode --onProperty prescriber --selectionMode COMBOBOX;
association set-type --onProperty prescriber --type AGGREGATION --targetEntity ~.jpa.Prescriber;
display add-list-field --field prescriber.name;
constraint NotNull --onProperty prescriber;
description add-notNull-message --onProperty prescriber --title "The percriber must be selected" --text "The percriber must be selected";
description add-notNull-message --onProperty prescriber --title "Le prescripteur doit être sélectionnée" --text "Le prescripteur doit être sélectionnée" --locale fr;

field manyToOne --named hospital --fieldType ~.jpa.Hospital;
description add-field-description --onProperty hospital --title "Hospital" --text "The hospital subjet of this prescription.";
description add-field-description --onProperty hospital --title "Hopital" --text "Cet hopital ayant prescrit l ordonnance." --locale fr;
association set-selection-mode --onProperty hospital --selectionMode COMBOBOX;
association set-type --onProperty hospital --type AGGREGATION --targetEntity ~.jpa.Hospital;
display add-list-field --field hospital.name;
constraint NotNull --onProperty hospital;
description add-notNull-message --onProperty hospital --title "The hospital must be selected" --text "The hospital must be selected";
description add-notNull-message --onProperty hospital --title "Le hopital doit être sélectionnée" --text "Le hopital doit être sélectionnée" --locale fr;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Originating agency";
description add-field-description --onProperty agency --title "Agency" --text "Agence originaire" --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;
constraint NotNull --onProperty agency;
description add-notNull-message --onProperty agency --title "The originating agency of this prescription book must be selected" --text "The originating agency of this prescription book must be selected";
description add-notNull-message --onProperty agency --title "Le site de cet ordonnancier doit être sélectionné" --text "Le site de cet ordonnancier doit être sélectionné" --locale fr;

field manyToOne --named recordingAgent --fieldType ~.jpa.Login;
description add-field-description --onProperty recordingAgent --title "Recording Agent" --text "The user who recorded this prescription.";
description add-field-description --onProperty recordingAgent --title "Agent Saisie" --text "Utilisateur saisiessant cet ordonnance." --locale fr;
association set-selection-mode --onProperty recordingAgent --selectionMode COMBOBOX;
association set-type --onProperty recordingAgent --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field recordingAgent.fullName;
constraint NotNull --onProperty recordingAgent;
description add-notNull-message --onProperty recordingAgent --title "The recording user must be selected" --text "The recording user must be selected";
description add-notNull-message --onProperty recordingAgent --title "La personne editant doit être sélectionné" --text "La personne editant doit être sélectionné" --locale fr;

field string --named prescriptionNumber;
description add-field-description --onProperty prescriptionNumber --title "Prescription Number" --text "The prescription number.";
description add-field-description --onProperty prescriptionNumber --title "Numéro de l Ordonnance" --text "Le numéro de l ordonnance." --locale fr;
display add-toString-field --field prescriptionNumber;
display add-list-field --field prescriptionNumber;

field manyToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order containing this prescription.";
description add-field-description --onProperty salesOrder --title "Commande Client" --text "La commandeclient qui contient cet ordonnance." --locale fr;
association set-selection-mode --onProperty salesOrder --selectionMode COMBOBOX;
association set-type --onProperty salesOrder --type AGGREGATION --targetEntity ~.jpa.SalesOrder;
display add-list-field --field salesOrder.soNumber;

field temporal --type TIMESTAMP --named prescriptionDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty prescriptionDate --title "Prescription Date" --text "The prescription date.";
description add-field-description --onProperty prescriptionDate --title "Date de Prescription" --text "La date de prescription." --locale fr;
display add-list-field --field prescriptionDate;
format add-date-pattern --onProperty prescriptionDate --pattern "dd-MM-yyyy HH:mm"; 

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date.";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie." --locale fr;
display add-list-field --field recordingDate;
format add-date-pattern --onProperty recordingDate --pattern "dd-MM-yyyy HH:mm"; 

cd ~~;

@/* Accounts Receivable */;

@/* Entité AvoirClient */;
entity --named CustomerVoucher --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client Voucher" --text "Client voucher";
description add-class-description  --locale fr --title "Avoir Client" --text "Avoir client";

field string --named voucherNumber;
description add-field-description --onProperty voucherNumber --title "Voucher Number" --text "The client voucher number.";
description add-field-description --onProperty voucherNumber --title "Numéro de cet Avoir" --text "Le numéro de l avoir du client." --locale fr;
display add-toString-field --field voucherNumber;
display add-list-field --field voucherNumber;

field manyToOne --named customerInvoice --fieldType ~.jpa.CustomerInvoice;
description add-field-description --onProperty customerInvoice --title "Customer Invoice" --text "The customer invoice associated with this voucher.";
description add-field-description --onProperty customerInvoice --title "Facure client" --text "La la facture client associe avec cet avoir." --locale fr;
association set-selection-mode --onProperty customerInvoice --selectionMode COMBOBOX;
association set-type --onProperty customerInvoice --type AGGREGATION --targetEntity ~.jpa.CustomerInvoice;
display add-list-field --field salesOrder.soNumber;

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Amount" --text "Voucher amount.";
description add-field-description --onProperty amount --title "Montant" --text "Montant de cet avoir" --locale fr;
format add-number-type --onProperty amount --type CURRENCY;
display add-list-field --field amount;
constraint NotNull --onProperty amount;
description add-notNull-message --onProperty amount --title "The voucher amount is required" --text "The voucher amount is required";
description add-notNull-message --onProperty amount --title "Le montant de cet avoir est réquis" --text "Le montant de cet avoir est réquis" --locale fr;

field manyToOne --named customer --fieldType ~.jpa.Customer;
description add-field-description --onProperty customer --title "Customer" --text "Customer.";
description add-field-description --onProperty customer --title "Client" --text "Client." --locale fr;
association set-selection-mode --onProperty customer --selectionMode COMBOBOX;
association set-type --onProperty customer --type AGGREGATION --targetEntity ~.jpa.Customer;
display add-list-field --field customer.fullName;

field manyToOne --named agency --fieldType ~.jpa.Agency;
description add-field-description --onProperty agency --title "Agency" --text "Agency.";
description add-field-description --onProperty agency --title "Agence" --text "Agence." --locale fr;
association set-selection-mode --onProperty agency --selectionMode COMBOBOX;
association set-type --onProperty agency --type AGGREGATION --targetEntity ~.jpa.Agency;
display add-list-field --field agency.name;

field boolean --named canceled --primitive false; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the voucher was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Indique si l avoir  a été annulé ou non." --locale fr;
@/* default=false */;
display add-list-field --field canceled;

field manyToOne --named recordingUser --fieldType ~.jpa.Login;
description add-field-description --onProperty recordingUser --title "User" --text "The user modifying this voucher.";
description add-field-description --onProperty recordingUser --title "Agent" --text "Agent de saisie ayant édité cet avoir." --locale fr;
association set-selection-mode --onProperty recordingUser --selectionMode COMBOBOX;
association set-type --onProperty recordingUser --type AGGREGATION --targetEntity ~.jpa.Login;
display add-list-field --field recordingUser;

field temporal --type TIMESTAMP --named modifiedDate; 
@/* pattern= dd-MM-yyyy HH:mm */;
description add-field-description --onProperty modifiedDate --title "Last Modified" --text "The last modification date.";
description add-field-description --onProperty modifiedDate --title "Derniere Edition" --text "La data de derniere edition de l avoir." --locale fr;
display add-list-field --field modifiedDate;
format add-date-pattern --onProperty modifiedDate --pattern "dd-MM-yyyy HH:mm"; 

field number --named amountUsed --type java.math.BigDecimal;
description add-field-description --onProperty amountUsed --title "Amount Used" --text "Amount used.";
description add-field-description --onProperty amountUsed --title "Montant Utilisé" --text "Montant utilisé." --locale fr;
format add-number-type --onProperty amountUsed --type CURRENCY;
display add-list-field --field amountUsed;

field boolean --named settled --primitive false; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the voucher is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si l avoir est soldé ou non." --locale fr;
@/* default=false */;
display add-list-field --field settled;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining credit on this voucher.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Rest de l avoir client" --locale fr;
@/* Default=0 */;
display add-list-field --field restAmount;

field boolean --named voucherPrinted --primitive false;
description add-field-description --onProperty voucherPrinted --title "Printed" --text "Indicates whether the voucher is printed or not.";
description add-field-description --onProperty voucherPrinted --title "Imprimé" --text "Indique si l avoir est imprimé ou pas." --locale fr;
@/* default=false */;
display add-list-field --field voucherPrinted;

cd ~~;

repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest access-control --roleTable ~.jpa.RoleName.java --permissionTable ~.jpa.PermissionName.java --loginTable ~.jpa.Login.java;

cd ~~;

repotest setup;

repotest create-test --packages src/main/java/org/adorsys/adpharma/;

cd ~~;

mvn clean install -DskipTests;












