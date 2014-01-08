

@/* ==================================== */;
@/* Client */;


@/* Type Client*/;
java new-enum-type --named ClientType --package ~.jpa ;
enum add-enum-class-description --title "Client Type" --text "Type of client.";
enum add-enum-class-description  --locale fr --title "Type of Client" --text "Type de client.";
java new-enum-const PHYSIQUE;
java new-enum-const MORAL;
enum generate-description-keys ;
@/* enumeration{PHYSIQUE, MORAL} */;

@/* Entité CategorieClient */;
entity --named ClientCategory --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client Category" --text "The client categories.";
description add-class-description  --locale fr --title "Categorie Client" --text "Les categorie de client.";

field string --named categoryNumber;
description add-field-description --onProperty categoryNumber --title "Client Category Number" --text "The client category number.";
description add-field-description --onProperty categoryNumber --title "Numéro de Categorie Client" --text "Le numéro de categorie client." --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this client category.";
description add-field-description --onProperty name --title "Libelle" --text "Le nom de cette categorie client." --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this client category.";
description add-field-description --onProperty note --title "Note" --text "Description de cette categorie client." --locale fr;
constraint Size --onProperty note --max 256;

field number --named discountRate --type java.math.BigDecimal;
description add-field-description --onProperty discountRate --title "Discount Rate" --text "Discount rate for this client category.";
description add-field-description --onProperty discountRate --title "Taux Remise" --text "Taux de remise pour cette categorie client." --locale fr;





@/* Client */;
entity --named Client --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client" --text "The client.";
description add-class-description  --locale fr --title "Categorie" --text "Un client.";

field string --named clientNumber;
description add-field-description --onProperty clientNumber --title "Client Number" --text "The client number.";
description add-field-description --onProperty clientNumber --title "Numéro du Client" --text "Le numéro du client." --locale fr;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this client.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce client." --locale fr;

field string --named firstName;
description add-field-description --onProperty firstName --title "First Name" --text "The first name of this client.";
description add-field-description --onProperty firstName --title "Prénom" --text "Le prénom de ce client." --locale fr;

field string --named fullName;
description add-field-description --onProperty fullName --title "Full Name" --text "The full name of this client.";
description add-field-description --onProperty fullName --title "Nom Complet" --text "Le nom complet de ce client." --locale fr;
@/* Le nom complet du client(nom+prenom) */;

field string --named landLinePhone;
description add-field-description --onProperty landLinePhone --title "Land Line Phone" --text "The client land line phone number";
description add-field-description --onProperty landLinePhone --title "Téléphone Fixe" --text "Téléphone fixe du client" --locale fr;

field string --named mobile;
description add-field-description --onProperty mobile --title "Mobile Phone" --text "The mobile phone of the client";
description add-field-description --onProperty mobile --title "Téléphone Mobile" --text "Téléphone Mobile du client" --locale fr;

field string --named fax;
description add-field-description --onProperty fax --title "Fax" --text "The fax number of the client";
description add-field-description --onProperty fax --title "Fax" --text "Fax du client" --locale fr;

field string --named email;
description add-field-description --onProperty email --title "Email" --text "The email address of the client";
description add-field-description --onProperty email --title "Email" --text "Email du client" --locale fr;

field boolean --named creditAuthorized;
description add-field-description --onProperty creditAuthorized --title "Credit Authorized" --text "Whether or not the customer can purchase on credit";
description add-field-description --onProperty creditAuthorized --title "Crédit Autorisé" --text "Autorise ou non le crédit au client" --locale fr;

field boolean --named discountAuthorized;
description add-field-description --onProperty discountAuthorized --title "Discount Authorized" --text "Whether or not the customer can be given discount";
description add-field-description --onProperty discountAuthorized --title "Remise Autorisée" --text "Autorise ou non la remise globale sur les produits au client" --locale fr;
@/* default=true */;

field number --named totalCreditLine --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditLine --title "Total Purchasing Price" --text "Total credit line for this customer..";
description add-field-description --onProperty totalCreditLine --title "Prix d'Achat Total" --text "Le montant max de credit qu'on peut accorder au client." --locale fr;
@/* Default=0 */;

field string --named employer;
description add-field-description --onProperty employer --title "Employer" --text "The client's employer.";
description add-field-description --onProperty employer --title "Employeur" --text "L'employeur du client." --locale fr;

field temporal --type TIMESTAMP --named birthDate; 
@/* pattern= dd-MM-yyyy */;
description add-field-description --onProperty birthDate --title "Birth Date" --text "The birth date of this client";
description add-field-description --onProperty birthDate --title "Date de Naissance" --text "La date de naissance du client" --locale fr;

field manyToOne --named gender --fieldType ~.jpa.Gender;
description add-field-description --onProperty gender --title "Gender" --text "The gender of this client.";
description add-field-description --onProperty gender --title "Genre" --text "Le genre de ce client." --locale fr;

field number --named coverageRate --type java.math.BigDecimal;
@/* default=100% */;
description add-field-description --onProperty coverageRate --title "Coverage Rate" --text "The coverage rate for this client.";
description add-field-description --onProperty coverageRate --title "Taux Couverture" --text "Taux de couverture pour ce client." --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of this client.";
description add-field-description --onProperty note --title "Note" --text "Description de cette client." --locale fr;
constraint Size --onProperty note --max 256;

field manyToOne --named payingClient --fieldType ~.jpa.Client;
description add-field-description --onProperty payingClient --title "Paying Client" --text "The paying client.";
description add-field-description --onProperty payingClient --title "Client Payeur" --text "Le client payeur." --locale fr;

field manyToOne --named clientCategory --fieldType ~.jpa.Client;
description add-field-description --onProperty clientCategory --title "Client Category" --text "The category this client belongs to.";
description add-field-description --onProperty clientCategory --title "Category Client" --text "La categorie de client à laquelle appartient le client." --locale fr;

field number --named totalDebt --type java.math.BigDecimal;
description add-field-description --onProperty totalDebt --title "Total Debt" --text "Total debts of this customer.";
description add-field-description --onProperty totalDebt --title "Dette Total" --text "Montant total des dettes du client." --locale fr;
@/* Default=0 */;

field custom --named clientType --type ~.jpa.ClientType;
description add-field-description --onProperty clientType --title "Client Type" --text "The client type.";
description add-field-description --onProperty clientType --title "Type Client" --text "Le type de client." --locale fr;
enum enumerated-field --onProperty clientType ;
@/* enumeration{PHYSIQUE, MORAL} */;

field string --named serialNumber;
description add-field-description --onProperty serialNumber --title "Serial Number" --text "The serial number of this client.";
description add-field-description --onProperty serialNumber --title "Matricule Client" --text "Le numéro matricule de ce client." --locale fr;

cd ~~ ;

