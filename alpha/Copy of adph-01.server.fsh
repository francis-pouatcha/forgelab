
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
access admin-role;

java new-enum-const LOGIN;
enum add-enum-constant-description --onConstant LOGIN  --title "User" --text "Role assigned to each user that can sig into this system";
enum add-enum-constant-description --onConstant LOGIN  --title "Connection" --text "Rôle attribué à chaque utilisateur pouvant ce connecter à ce système" --locale fr;
access login-role;

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
access permission-table;

field string --named name;
description add-field-description --onProperty name --title "Permission Name" --text "The name of this permission";
description add-field-description --onProperty name --title "Nom de la Permission" --text "Intitule de cete permission" --locale fr;
display add-toString-field --field name;
display add-list-field --field name;
constraint NotNull --onProperty name;
description add-notNull-message --onProperty name --title "Enter the name of this autorization" --text "Enter the name of this autorization";
description add-notNull-message --onProperty name --title "Saisir le nom de cette autorisation" --text "Saisir le nom de cette autorisation" --locale fr;

field custom --named action --type ~.jpa.PermissionActionEnum;
description add-field-description --onProperty action --title "Authorized Action" --text "The action associated with a permission";
description add-field-description --onProperty action --title "Action Autorisation" --text "Action associée avec cette autorisation" --locale fr;
enum enumerated-field --onProperty action;
display add-list-field --field action;
display add-toString-field --field action;
constraint NotNull --onProperty action;
description add-notNull-message --onProperty action --title "Select an action for this autorization" --text "Select an action for this autorization";
description add-notNull-message --onProperty action --title "Selectioner une action à associer avec cette autorisation" --text "Selectioner une action à associer avec cette autorisation" --locale fr;


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

field string --named loginName;
description add-field-description --onProperty loginName --title "User Name" --text "The name used by the user to login.";
description add-field-description --onProperty loginName --title "Nom de Connection" --text "Le nom de connetion de cet utilisateur." --locale fr;
constraint NotNull --onProperty loginName;
description add-notNull-message --onProperty loginName --title "User name is required" --text "User name is required";
description add-notNull-message --onProperty loginName --title "Nom de connection est réquis" --text "Nom de connection est réquis" --locale fr;
display add-toString-field --field loginName;
display add-list-field --field loginName;
@/* Unique=true */;

field string --named fullName;
description add-field-description --onProperty fullName --title "Full Name" --text "The full name of the user.";
description add-field-description --onProperty fullName --title "Nom Complet" --text "Le nom complet dede cet ilisateur." --locale fr;
constraint NotNull --onProperty fullName;
description add-notNull-message --onProperty fullName --title "The full name is required" --text "The full name is required";
description add-notNull-message --onProperty fullName --title "Le nom complet est réquis" --text "Le nom complet est réquis" --locale fr;

field string --named password;
description add-field-description --onProperty password --title "Password" --text "The password of the user.";
description add-field-description --onProperty password --title "Mot de Passe" --text "Mot de passe de cet utilisateur." --locale fr;

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


cd ~~;


@/* Entity Address */;
entity --named Address --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Address" --text "An address";
description add-class-description  --locale fr --title "Adresse" --text "Une adresse";

field string --named street;
description add-field-description --onProperty street --title "Street" --text "The name of the street";
description add-field-description --onProperty street --title "Rue" --text "Nom de la rue" --locale fr;
display add-toString-field --field street;
display add-list-field --field street;

field string --named zipCode;
description add-field-description --onProperty zipCode --title "Zip Code" --text "The zip code oif this address";
description add-field-description --onProperty zipCode --title "Code Postale" --text "Le code poastale de cette adresse" --locale fr;
display add-toString-field --field zipCode;
display add-list-field --field zipCode;

field string --named city;
description add-field-description --onProperty city --title "City" --text "The city of this address";
description add-field-description --onProperty city --title "Ville" --text "La localite de cette adresse" --locale fr;
display add-toString-field --field city;
display add-list-field --field city;

field string --named country;
description add-field-description --onProperty country --title "Country" --text "The zip code oif this address";
description add-field-description --onProperty country --title "Pays" --text "Le pays de cette adresse" --locale fr;
display add-toString-field --field country;
display add-list-field --field country;


cd ~~;


@/* Entity Users */;
entity --named Users --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Users" --text "A person";
description add-class-description  --locale fr --title "Usersne" --text "Une personne";

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
description add-field-description --onProperty lastName --title "Nom" --text "Le nom de cet utilisateur." --locale fr;
constraint NotNull --onProperty lastName;

field string --named notes;
description add-field-description --onProperty notes --title "Notes" --text "The description of this person";
description add-field-description --onProperty notes --title "Notes" --text "La description de cette personne" --locale fr;
constraint Size --onProperty notes --max 256;

field temporal --type TIMESTAMP --named birthDate; 
description add-field-description --onProperty birthDate --title "Birth Date" --text "Date of birth";
description add-field-description --onProperty birthDate --title "Date de Naissance" --text "Date de naissance." --locale fr;
display add-list-field --field birthDate;
display add-toString-field --field birthDate;
format add-date-pattern --onProperty birthDate --pattern "dd-MM-yyyy"; 

field boolean --named active --primitive false;
description add-field-description --onProperty active --title "Active" --text "The active of roles assigned";
description add-field-description --onProperty active --title "Active" --text "Les active de role attribués a de cet utilisateur" --locale fr;

field  oneToMany --named superviseds --fieldType ~.jpa.Users --inverseFieldName supervisor;
description add-field-description --onProperty superviseds --title "Supervisor" --text "The supervisor of this person";
description add-field-description --onProperty superviseds --title "Superviseur" --text "Le superviseur de cette personne" --locale fr;
association set-type --onProperty superviseds --type AGGREGATION --targetEntity ~.jpa.Users.java;

description add-field-description --onProperty supervisor --title "Supervisor" --text "The supervisor of this person";
description add-field-description --onProperty supervisor --title "Superviseur" --text "Le superviseur de cette personne" --locale fr;
association set-type --onProperty supervisor --type AGGREGATION --targetEntity ~.jpa.Users.java;
association set-selection-mode --onProperty supervisor --selectionMode COMBOBOX;

field oneToMany --named addresses --fieldType ~.jpa.Address --inverseFieldName person --cascade ALL;
description add-field-description --onProperty addresses --title "Addresses" --text "The addresses of this person";
description add-field-description --onProperty addresses --title "Adresses" --text "Les adresses de cette personne" --locale fr;
association set-type --onProperty addresses --type COMPOSITION --targetEntity ~.jpa.Address.java;
association set-selection-mode --onProperty addresses --selectionMode TABLE;
association display-field --field addresses.zipCode;
association display-field --field addresses.street;
association display-field --field addresses.city;
association display-field --field addresses.country;

cd ../Address.java;
description add-field-description --onProperty person --title "Users" --text "The person owning this address";
description add-field-description --onProperty person --title "Usersne" --text "La personne proprietaire de cette addresse" --locale fr;
association set-type --onProperty person --type COMPOSITION --targetEntity ~.jpa.Users.java;


relationship add --sourceEntity ~.jpa.Users --sourceQualifier roleNames --targetEntity ~.jpa.RoleName;
cd ../Users.java;
description add-field-description --onProperty roleNames --title "Role Names" --text "The names of roles assigned to this user.";
description add-field-description --onProperty roleNames --title "Nom des Roles" --text "Les nom de role attribués a de cet utilisateur" --locale fr;
association set-type --onProperty roleNames --type AGGREGATION --targetEntity ~.jpa.RoleName;
association set-selection-mode --onProperty roleNames --selectionMode FORWARD;

relationship add --sourceEntity ~.jpa.Users --sourceQualifier staffMembers --targetEntity ~.jpa.Users --targetQualifier managers;
cd ../Users.java;
description add-field-description --onProperty staffMembers --title "Staff Members" --text "The staff members of this person";
description add-field-description --onProperty staffMembers --title "Equipage" --text "Equipage de cette persone" --locale fr;
association set-type --onProperty staffMembers --type AGGREGATION --targetEntity ~.jpa.Users;

cd ../Users.java;
description add-field-description --onProperty managers --title "Managers" --text "The managers of this person";
description add-field-description --onProperty managers --title "Manageurs" --text "Les manageurs de cette persone" --locale fr;
association set-type --onProperty managers --type AGGREGATION --targetEntity ~.jpa.Users;
association set-selection-mode --onProperty managers --selectionMode TABLE;

cd ~~;

envers setup;

envers audit-package --jpaPackage src/main/java/org/adorsys/;

cd ~~;

repogen new-repository --jpaPackage src/main/java/org/adorsys;

cd ~~;

reporest setup --activatorType APP_CLASS;

reporest endpoint-from-entity --jpaPackage src/main/java/org/adorsys;

cd ~~;

repotest setup;

repotest create-test --packages src/main/java/org/adorsys/;

cd ~~;


