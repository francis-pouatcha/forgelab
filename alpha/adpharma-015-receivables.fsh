
@/* ====================================== */;
@/* Accounts Receivable */;

@/* VoucherType */;
entity --named VoucherType --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Voucher Type" --text "The type of a voucher.";
description add-class-description  --locale fr --title "Type de Bon" --text "Le type d'un avoir.";
@/* Enumeration{CREATION, RETOUR} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this voucher type.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce type de bon avoir." --locale fr;

@/* Entité AvoirClient */;
entity --named ClientVoucher --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client Voucher" --text "Client voucher";
description add-class-description  --locale fr --title "Avoir Client" --text "Avoir client";

field string --named voucherNumber;
description add-field-description --onProperty voucherNumber --title "Voucher Number" --text "The client voucher number.";
description add-field-description --onProperty voucherNumber --title "Numéro de l'Avoir" --text "Le numéro de l'avoir du client." --locale fr;

field temporal --type TIMESTAMP --named modifDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty modifDate --title "Modified" --text "Modification date.";
description add-field-description --onProperty modifDate --title "Modifié" --text "Date d'edition de l'avoir." --locale fr;

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Amount" --text "Voucher amount.";
description add-field-description --onProperty amount --title "Montant" --text "Montant de l'avoir" --locale fr;
constraint NotNull --onProperty targetQuantity;

field string --named clientName;
description add-field-description --onProperty clientName --title "Client Name" --text "The name of the client holding this voucher.";
description add-field-description --onProperty clientName --title "Nom Client" --text "Nom du client qui possède l'avoir." --locale fr;

field string --named clientNumber;
description add-field-description --onProperty clientNumber --title "Client Number" --text "The number of the client holding this voucher.";
description add-field-description --onProperty clientNumber --title "Numéro Client" --text "Le numéro du client qui possède l'avoir." --locale fr;

field boolean --named canceled; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the voucher was canceled or not.";
description add-field-description --onProperty canceled --title "Annulé" --text "Indique si l'avoir  a été annulé ou non." --locale fr;
@/* default=false */;

@/* Question: why no relationshitp to pharma user */;
field string --named recordingUser;
description add-field-description --onProperty recordingUser --title "User" --text "The user modifying this voucher.";
description add-field-description --onProperty recordingUser --title "Agent" --text "L'agent de saisie ayant édité cet avoir." --locale fr;
@/* default=getUserName() */;

field temporal --type TIMESTAMP --named modifiedDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty modifiedDate --title "Last Modified" --text "The last modification date.";
description add-field-description --onProperty modifiedDate --title "Derniere Edition" --text "La data de derniere edition de l'avoir." --locale fr;

field manyToOne --named voucherType --fieldType ~.jpa.VoucherType;
description add-field-description --onProperty voucherType --title "Voucher Type" --text "The type of this voucher.";
description add-field-description --onProperty voucherType --title "Type de Bon Avoir" --text "Le type de ce bon avoir." --locale fr;
@/* Enumeration{CREATION, RETOUR} */;

field number --named amountUsed --type java.math.BigDecimal;
description add-field-description --onProperty amountUsed --title "Amount Used" --text "Amount used.";
description add-field-description --onProperty amountUsed --title "Montant Utilisé" --text "Montant utilisé." --locale fr;
@/* Default=0 */;

field boolean --named settled; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the voucher is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si l'avoir est soldé ou non." --locale fr;
@/* default=false */;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining credit on this voucher.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Rest de l'avoir client" --locale fr;
@/* Default=0 */;

field boolean --named voucherPrinted;
description add-field-description --onProperty voucherPrinted --title "Printed" --text "Indicates whether the voucher is printed or not.";
description add-field-description --onProperty voucherPrinted --title "Imprimé" --text "Indique si l'avoir est imprimé ou pas." --locale fr;
@/* default=false */;


@/* Entité DetteClient */;
entity --named ClientDebt --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Client Debt" --text "Client debt";
description add-class-description  --locale fr --title "Dette Client" --text "Dette client";

@/* Question: why not the reference to the invoice */;
field string --named invoiceNumber;
description add-field-description --onProperty invoiceNumber --title "Invoice Number" --text "The number of the invoice associated with this debt.";
description add-field-description --onProperty invoiceNumber --title "Numéro de Facture" --text "Le numéro de la facture client associe avec cette dette." --locale fr;

@/* Question: why not the reference to the client */;
field string --named clientNumber;
description add-field-description --onProperty clientNumber --title "Client Number" --text "The number of the client associated with this debt.";
description add-field-description --onProperty clientNumber --title "Numéro Client" --text "Le numéro du client associe avec cette dette." --locale fr;

field string --named clientName;
description add-field-description --onProperty clientName --title "Client Name" --text "The name of the client associated with this debt.";
description add-field-description --onProperty clientName --title "Nom du Client" --text "Le nom du client associe avec cette dette." --locale fr;

field temporal --type TIMESTAMP --named creationDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty creationDate --title "Creation Date" --text "The creation date of this debt.";
description add-field-description --onProperty creationDate --title "Date de Creation" --text "La date de creation de cette dette." --locale fr;

field temporal --type TIMESTAMP --named endDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty endDate --title "End Date" --text "The end date for this debt";
description add-field-description --onProperty endDate --title "Date de Fin" --text "La date de fin de la dette." --locale fr;

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "Initial amount of this debt.";
description add-field-description --onProperty initialAmount --title "Montant Initial" --text "Montant initial de la dette." --locale fr;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "Advance payment.";
description add-field-description --onProperty advancePayment --title "Montant Avancé" --text "Montant avancé." --locale fr;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining amount of this debt.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Le montant restant a payer pour cette dette." --locale fr;
@/* Default=0 */;

field boolean --named settled; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the debt is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si la dette est soldé ou non." --locale fr;
@/* default=false */;

field temporal --type TIMESTAMP --named lastPaymentDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty lastPaymentDate --title "Last Payment Date" --text "The last payment date.";
description add-field-description --onProperty lastPaymentDate --title "Date Dernier Versement " --text "La date du dernier versement." --locale fr;

field boolean --named canceled; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the debt is canceled or not.";
description add-field-description --onProperty canceled --title "Annulée" --text "Indique si la dette est annulée ou non." --locale fr;
@/* default=false */;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "Date the debt was paid";
description add-field-description --onProperty paymentDate --title "Date Paiement" --text "Date à laquelle la dette a été payée" --locale fr;

@/* Entité EtatCredits */;
entity --named DebtStatement --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Debt Statement" --text "The sum of all the debts of a client";
description add-class-description  --locale fr --title "Etat Credits" --text "Le cumul de toutes les dettes d'un client";

field string --named statementNumber;
description add-field-description --onProperty statementNumber --title "Statement Number" --text "The number identifying this statement.";
description add-field-description --onProperty statementNumber --title "Numéro de l'État" --text "Le numéro identifiant cet état." --locale fr;

field manyToOne --named client --fieldType ~.jpa.Client;
description add-field-description --onProperty client --title "Client" --text "The client carrying this debt.";
description add-field-description --onProperty client --title "Client" --text "Le client portant cette dette." --locale fr;

field temporal --type TIMESTAMP --named modifDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty modifDate --title "Modified" --text "Modification date.";
description add-field-description --onProperty modifDate --title "Modifié" --text "Date d'edition de cet état." --locale fr;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "Date these debts was paid.";
description add-field-description --onProperty paymentDate --title "Date Paiement" --text "Date à laquelle ces dettes ont été payées." --locale fr;

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "Initial amount of this statement.";
description add-field-description --onProperty initialAmount --title "Montant Initial" --text "Montant initial de cet état." --locale fr;

field number --named advancePayment --type java.math.BigDecimal;
description add-field-description --onProperty advancePayment --title "Advance Payment" --text "Advance payment.";
description add-field-description --onProperty advancePayment --title "Montant Avancé" --text "Montant avancé." --locale fr;

field number --named restAmount --type java.math.BigDecimal;
description add-field-description --onProperty restAmount --title "Rest Amount" --text "The remaining amount of this debt.";
description add-field-description --onProperty restAmount --title "Montant Restant" --text "Le montant restant a payer pour cette dette." --locale fr;
@/* Default=0 */;

field boolean --named settled; 
description add-field-description --onProperty settled --title "Settled" --text "Sates if the statement is settled or not.";
description add-field-description --onProperty settled --title "Soldé" --text "Indique si cet état est soldé ou non." --locale fr;
@/* default=false */;

field number --named amountFromVouchers --type java.math.BigDecimal;
description add-field-description --onProperty amountFromVouchers --title "Rest Amount" --text "Amount of of vouchers if the customer uses vouchers to pay these debts.";
description add-field-description --onProperty amountFromVouchers --title "Montant Restant" --text "Montant de l'avoir si le client en possède utilisé pour rembourser les dettes." --locale fr;
@/* Default=0 */;

field boolean --named canceled; 
description add-field-description --onProperty canceled --title "Canceled" --text "Sates if the statement is canceled or not.";
description add-field-description --onProperty canceled --title "Annulée" --text "Precise si l'états a été annulée ou non." --locale fr;
@/* default=false */;

field boolean --named useVoucher; 
description add-field-description --onProperty useVoucher --title "Use Vouchers" --text "Specifies whether the client can use his voucher to pay its debts.";
description add-field-description --onProperty useVoucher --title "Consommer Avoir" --text "Indique si le client peut consommer ou non ses avoirs pour payer ses dettes." --locale fr;
@/* default=false */;

cd ../ClientDebt.java;

field manyToOne --named debtStatement --fieldType ~.jpa.DebtStatement;
description add-field-description --onProperty debtStatement --title "Debt Statement" --text "The debt statement containing this debt.";
description add-field-description --onProperty debtStatement --title "Etat Credits" --text "L'état crédit contenant cette dette." --locale fr;

 