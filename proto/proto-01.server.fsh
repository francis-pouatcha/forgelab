
set ACCEPT_DEFAULTS true;

new-project --named proto.server --topLevelPackage org.adorsys.proto.server --finalName proto.server --projectFolder proto.server;

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
enum add-enum-constant-description --onConstant ADMIN --title "Administration" --text "Adminitrator of this platform";
enum add-enum-constant-description --onConstant ADMIN --title "Administration" --text "Administrateur de ce progiciel" --locale fr;

java new-enum-const LOGIN;
enum add-enum-constant-description --onConstant LOGIN  --title "Login" --text "User of this application" ;
enum add-enum-constant-description --onConstant LOGIN  --title "Connexion" --text "Utilisateur de ce progiciel" --locale fr ;

java new-enum-const MANAGER;
enum add-enum-constant-description --onConstant MANAGER  --title "Manager" --text "Manager" ;
enum add-enum-constant-description --onConstant MANAGER  --title "Manageur" --text "Manageur" --locale fr;

cd ~~;


@/* Enum AccessRoleEnum */;
java new-enum-type --named AccessRoleEnum --package ~.jpa;
enum add-enum-class-description --title "Access Role Names" --text "The name of access roles defined in this application";
enum add-enum-class-description --title "Enumeration des Droit pour Accès" --text "les noms de rolles autorissant accès au systeme" --locale fr;

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
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;

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
group add --grouper ~.jpa.WorkGroup --named ADMIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole ADMIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;

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


@/* Entity Login */;
entity --named Login --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Login" --text "A login record";
description add-class-description --title "Connection" --text "Une connection" --locale fr;
access login-table;
group add --grouper ~.jpa.WorkGroup --named LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --roleEnum ~.jpa.AccessRoleEnum --toRole ADMIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action EDIT --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;

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

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of the site";
description add-field-description --onProperty email --title "Email" --text "Email du site" --locale fr;
display add-list-field --field email;

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

field string --named saleKey;
description add-field-description --onProperty saleKey --title "Sale Key" --text "The sales key of saller for a sales session open to all users. ";
description add-field-description --onProperty saleKey --title "Sale Key" --text "Clé de vente du vendeur pour la session vente ouverte à tous les utilisateurs." --locale fr;

field number --named discountRate --type java.math.BigDecimal;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "The discount rate this user can give to clients.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise que cet utilisateur peut accorder aux clients." --locale fr;
format add-number-type --onProperty discountRate --type PERCENTAGE;
@/* Default= 15% */;

field custom --named gender --type ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this user";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de cet utilisateur" --locale fr;
enum enumerated-field --onProperty gender ;
display add-list-field --field gender;
display add-toString-field --field gender;

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
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;

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


@/* Entity Agency */;
entity --named Agency --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Agency" --text "An agency of this pharmacie";
description add-class-description  --locale fr --title "Agence" --text "Une agence de cette pharmacie";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;

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
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The  name of this Agency is required" --text "The  name of this Agency is required";
description add-notNull-message --onProperty name --title "Le libellé  de cette agence est réquis" --text "Le libellé  de cette AGENCE est réquis" --locale fr;


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
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;

field string --named name;
description add-field-description --onProperty name --title "Currency Name" --text "The name of this currency.";
description add-field-description --onProperty name --title "Libellé de la Devise" --text "Le nom de cette devise." --locale fr;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "The currency name is required" --text "The currency name is required";
description add-notNull-message --onProperty name --title "Le libellé de la devise est réquis" --text "Le libellé de la devise est réquis" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field number --named cfaEquivalent --type java.math.BigDecimal;
description add-field-description --onProperty cfaEquivalent --title "CFA Equivalent" --text "Corresponding CFA value for conversions.";
description add-field-description --onProperty cfaEquivalent --title "Equivalent CFA" --text "Valeur equivalant cfa pour faire les conversions." --locale fr;
format add-number-type --onProperty cfaEquivalent --type CURRENCY;

cd ~~;


@/* Entity VAT */;
entity --named VAT --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Tax" --text "The value added tax";
description add-class-description  --locale fr --title "Taxe" --text "Taxe sur la valeure ajoutée";
group add --grouper ~.jpa.WorkGroup --named MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action ALL --roleEnum ~.jpa.AccessRoleEnum --toRole MANAGER;
access add-permission --actionEnum ~.jpa.PermissionActionEnum --action READ --roleEnum ~.jpa.AccessRoleEnum --toRole LOGIN;

field string --named name;
description add-field-description --onProperty name --title "VAT Code" --text "The code of this VAT";
description add-field-description --onProperty name --title "Code TVA" --text "Le code de la TVA" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;

field number --named rate --type java.math.BigDecimal;
description add-field-description --onProperty rate --title "VAT Rate" --text "The VAT rate";
description add-field-description --onProperty rate --title "Taux TVA" --text "Taux de la TVA" --locale fr;
display add-list-field --field rate;
format add-number-type --onProperty rate --type  PERCENTAGE;

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "Says if this VAT is active or not";
description add-field-description --onProperty active --title "Actif" --text "Indique si la TVA est Actif ou pas." --locale fr;
display add-list-field --field active;


cd ~~;

repogen setup;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest access-control --roleTable ~.jpa.RoleName.java --permissionTable ~.jpa.PermissionName.java --loginTable ~.jpa.Login.java;

cd ~~;

@/*  repotest setup */;

@/*  repotest create-test --packages src/main/java/org/adorsys/proto/ */;

cd ~~;

mvn clean install -DskipTests;


