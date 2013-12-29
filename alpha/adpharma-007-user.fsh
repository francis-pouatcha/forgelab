
@/* Entity Gender */;
entity --named Gender --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Gender" --text "The gender of a user.";
description add-class-description  --locale fr --title "Genre" --text "Le genre de cet utilisateur.";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this gender.";
description add-field-description --onProperty name --title "Intitule" --text "Intitule de ce genre" --locale fr;
@/* Enumeration{Masculin, feminin, mademoiselle, enfant, Docteur, Neutre} */;

@/* Entity RoleName */;
entity --named RoleName --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Roles" --text "Names of roles assignable to users";
description add-class-description  --locale fr --title "Roles" --text "Nom de role attribuable aux utilisateurs.";

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this role.";
description add-field-description --onProperty name --title "Intitule" --text "Intitule de ce role" --locale fr;

field string --named resourceKey;
description add-field-description --onProperty resourceKey --title "Resource Key" --text "The key use to fetch the language resource from the database.";
description add-field-description --onProperty resourceKey --title "Clef Ressource" --text "La clef utilise pour lire les valeure literaires de la base de donnee." --locale fr;

@/* Entity PharmaUser */;
entity --named PharmaUser --package ~.jpa --idStrategy AUTO;
description add-class-description --title "User" --text "A user of this application";
description add-class-description  --locale fr --title "Utilisateur" --text "Un utilisateur de cette application.";

field string --named userNumber;
description add-field-description --onProperty userNumber --title "User Number" --text "The number of this user.";
description add-field-description --onProperty userNumber --title "Numéro de cet Utilisateur" --text "Le numéro de cet utilisateur." --locale fr;

field manyToOne --named gender --fieldType ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this user";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de cet utilisateur" --locale fr;

field string --named userName;
description add-field-description --onProperty userName --title "User Name" --text "The name used by the user to login.";
description add-field-description --onProperty userName --title "Nom de cet Utilisateur" --text "Le nom de connexion de cet utilisateur." --locale fr;
constraint NotNull --onProperty userName;
@/* Unique=true */;

field string --named firstName;
description add-field-description --onProperty firstName --title "First Name" --text "The first name of the user.";
description add-field-description --onProperty firstName --title "Prénom" --text "Le prénom de cet utilisateur." --locale fr;

field string --named lastName;
description add-field-description --onProperty lastName --title "Last Name" --text "The last name of the user.";
description add-field-description --onProperty lastName --title "Nom" --text "Le nom de cet utilisateur." --locale fr;
constraint NotNull --onProperty lastName;

field string --named fullName;
description add-field-description --onProperty fullName --title "Full Name" --text "The full name of the user.";
description add-field-description --onProperty fullName --title "Nom Complet" --text "Le nom complet dede cet ilisateur." --locale fr;
constraint NotNull --onProperty fullName;
@/* Nom complet de lutilisateur(nom+prenom) */;

field string --named password;
description add-field-description --onProperty password --title "Password" --text "The password of the user.";
description add-field-description --onProperty password --title "Mot de Passe" --text "Mot de passe de cet utilisateur." --locale fr;

field manyToMany --named roleNames --fieldType ~.jpa.RoleName;
description add-field-description --onProperty roleNames --title "Role Names" --text "The names of roles assigned to this user.";
description add-field-description --onProperty roleNames --title "Nom des Roles" --text "Les nom de role attribués a de cet utilisateur" --locale fr;

field string --named phoneNumber;
description add-field-description --onProperty phoneNumber --title "Phone Number" --text "The phone number of this user.";
description add-field-description --onProperty phoneNumber --title "Numéro de Telephone" --text "Numéro de téléphone de cet utilisateur." --locale fr;

field boolean --named disableLogin;
@/*  default=true */;
description add-field-description --onProperty disableLogin --title "Disable Login" --text "Indicates whether the user login is disabled.";
description add-field-description --onProperty disableLogin --title "Login Desactivé" --text "Indique si le login de cet utilisateur est desactivé ou non." --locale fr;

field boolean --named accountLocked;
@/*  default=false */;
description add-field-description --onProperty accountLocked --title "Disable Login" --text "Indicates whether the user account is locked.";
description add-field-description --onProperty accountLocked --title "Login Desactivé" --text "Indique si le compte(login+password) est bloqué ou pas." --locale fr;

field temporal --type TIMESTAMP --named credentialExpiration; 
@/* pattern= dd-MM-yyyy HH:mm  Default = +50 ans   */;
description add-field-description --onProperty credentialExpiration --title "Password Expiration" --text "Deta of expiration of the user password.";
description add-field-description --onProperty credentialExpiration --title "Expiration Mot de Passe" --text "Date expiration du certificat utilisateur." --locale fr;

field temporal --type TIMESTAMP --named accountExpiration; 
@/* pattern=dd-MM-yyyy HH:mm  Default = +50 ans */;
description add-field-description --onProperty accountExpiration --title "Last Out of Stock Date" --text "Date of expiration of the user account.";
description add-field-description --onProperty accountExpiration --title "Date Derniere Rupture de stock" --text "Date expiration du compte utilisateur" --locale fr;

field string --named passwordSalt;
description add-field-description --onProperty passwordSalt --title "Password Salt" --text "The salt used to encrypt the user password.";
description add-field-description --onProperty passwordSalt --title "Clé de hachage du Mot de Passe" --text "La clé de hachage pour encryptage des mots de passe en MD5" --locale fr;
@/* Default= ace6b4f53 */;

field string --named address;
description add-field-description --onProperty address --title "Address" --text "The address of this user.";
description add-field-description --onProperty address --title "Adresse" --text "Adresse de cet utilisateur" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of this user..";
description add-field-description --onProperty email --title "Email" --text "Adresse email de cet utilisateur" --locale fr;

field manyToOne --named office --fieldType ~.jpa.Site;
description add-field-description --onProperty office --title "Office" --text "The site in which the user is registered.";
description add-field-description --onProperty office --title "Site" --text "Site dans lequel cet utilisateur est enregistré." --locale fr;

field number --named discountRate --type java.math.BigDecimal;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "The discount rate this user can give to clients.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise que cet utilisateur peut accorder aux clients." --locale fr;
@/* Default= 15% */;

field string --named saleKey;
description add-field-description --onProperty saleKey --title "Sale Key" --text "The sales key for a sales session open to all users. ";
description add-field-description --onProperty saleKey --title "Sale Key" --text "Clé de vente pour la session vente ouverte à tous les utilisateurs." --locale fr;

