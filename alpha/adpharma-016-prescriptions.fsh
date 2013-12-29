

@/* ================================== */;
@/* Prescriptions */;

@/* Entité Ordonnanciers */;
entity --named PrescriptionBook --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Prescription Blook" --text "Prescription book";
description add-class-description  --locale fr --title "Ordonnancier" --text "Ordonnancier";

field string --named prescriber;
description add-field-description --onProperty prescriber --title "Prescriber" --text "The doctor who prescribed the order.";
description add-field-description --onProperty prescriber --title "Prescripteur" --text "Le medecin ayant prescrit l'ordonnance." --locale fr;
constraint NotNull --onProperty prescriber;

field string --named hospital;
description add-field-description --onProperty hospital --title "Hospital" --text "The hospital subjet of this prescription.";
description add-field-description --onProperty hospital --title "Hopital" --text "L'hopital ayant prescrit l'ordonnance." --locale fr;
constraint NotNull --onProperty hospital;

field string --named patientName;
description add-field-description --onProperty patientName --title "Patient Name" --text "The patient who received the prescription.";
description add-field-description --onProperty patientName --title "patient Name" --text "Le patient ayant reçu l'ordonnance." --locale fr;

@/* Question: why don't we have a foreign key relationship to pharmaUser */;
field string --named recordingAgent;
description add-field-description --onProperty recordingAgent --title "Recording Agent" --text "The user who recorded this prescription.";
description add-field-description --onProperty recordingAgent --title "Agent Saisie" --text "L'utilisateur saisiessant cet ordonnance." --locale fr;

field string --named prescriptionNumber;
description add-field-description --onProperty prescriptionNumber --title "Prescription Number" --text "The prescription number.";
description add-field-description --onProperty prescriptionNumber --title "Numéro de l'Ordonnance" --text "Le numéro de l'ordonnance." --locale fr;

field oneToOne --named salesOrder --fieldType ~.jpa.SalesOrder;
constraint NotNull --onProperty salesOrder;
description add-field-description --onProperty salesOrder --title "Sales Order" --text "The sales order containing this prescription.";
description add-field-description --onProperty salesOrder --title "Commande Client" --text "La commandeclient qui contient l'ordonnance." --locale fr;

field temporal --type TIMESTAMP --named prescriptionDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty prescriptionDate --title "Prescription Date" --text "The prescription date.";
description add-field-description --onProperty prescriptionDate --title "Date de Prescription" --text "La date de prescription." --locale fr;

field temporal --type TIMESTAMP --named recordingDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty recordingDate --title "Recording Date" --text "The recording date.";
description add-field-description --onProperty recordingDate --title "Date de Saisie" --text "La date de saisie." --locale fr;


 