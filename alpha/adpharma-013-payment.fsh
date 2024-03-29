

@/* =============================== */;

@/* Type Paiement*/;
java new-enum-type  --named PaymentType --package ~.jpa ;
enum add-enum-class-description --title "Payment Type" --text "Type of payment.";
enum add-enum-class-description  --locale fr --title "Type Paiement" --text "Type de paiement.";
java new-enum-const CASH;
java new-enum-const CHEQUE;
java new-enum-const CARTE_CREDIT;
java new-enum-const BON_AVOIR_CLIENT;
enum generate-description-keys ;
enum generate-description-keys --locale fr ;

@/* Entité  paiement */;
entity --named Payment --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Payment" --text "A payment.";
description add-class-description  --locale fr --title "Paiement" --text "Un paiement.";

field string --named paymentNumber;
description add-field-description --onProperty paymentNumber --title "Payment Number" --text "The paiment number.";
description add-field-description --onProperty paymentNumber --title "Numéro du Paiement" --text "Le numéro du paiement." --locale fr;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern=dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "The payment date.";
description add-field-description --onProperty paymentDate --title "Date de Paiement" --text "La date de paiement." --locale fr;

field temporal --type TIMESTAMP --named recordDate; 
@/* pattern=dd-MM-yyyy HH:mm*/;
description add-field-description --onProperty recordDate --title "Record Date" --text "The record date for this paiement.";
description add-field-description --onProperty recordDate --title "Date de Saisie" --text "La date de saisie du paiement." --locale fr;

field number --named amount --type java.math.BigDecimal;
description add-field-description --onProperty amount --title "Payment Amount" --text "The payment amount.";
description add-field-description --onProperty amount --title "Montant du Paiement" --text "Le montant du paiement." --locale fr;

field number --named receivedAmount --type java.math.BigDecimal;
description add-field-description --onProperty receivedAmount --title "Received Amount" --text "The amount received from the payment.";
description add-field-description --onProperty receivedAmount --title "Montant Reçue" --text "Le montant reçue du paiement." --locale fr;

field number --named difference --type java.math.BigDecimal;
description add-field-description --onProperty difference --title "Difference" --text "The difference (amount returned to payer).";
description add-field-description --onProperty difference --title "Différence" --text "La différence (montant retourné au client)." --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "Site in which the payment occurs.";
description add-field-description --onProperty site --title "Magasin" --text "Site dans lequel s'effectue le paiement." --locale fr;

field manyToOne --named cashier --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment.";
description add-field-description --onProperty cashier --title "Caissier" --text "L'utilisateur percevant le paiement." --locale fr;

field manyToOne --named cashDrawer --fieldType ~.jpa.CashDrawer;
description add-field-description --onProperty cashDrawer --title "Cash Drawer" --text "The cash drawer in use.";
description add-field-description --onProperty cashDrawer --title "Caisse" --text "La caisse utilisé." --locale fr;

cd ../Invoice.java;

field oneToMany --named payments --fieldType ~.jpa.Payment --inverseFieldName invoice --fetchType EAGER  --cascade;
description add-field-description --onProperty payments --title "Payments" --text "Payments associated with this invoice.";
description add-field-description --onProperty payments --title "Piements" --text "Payements associe avec cette facture. " --locale fr;

cd ../Payment.java;

field custom --named paymentType --type ~.jpa.PaymentType;
description add-field-description --onProperty paymentType --title "Payment Type" --text "The type of this payment.";
description add-field-description --onProperty paymentType --title "Type de Paiement" --text "La type de ce paiement." --locale fr;
enum enumerated-field --onProperty paymentType ;

field boolean --named paymentReceiptPrinted;
description add-field-description --onProperty paymentReceiptPrinted --title "Open" --text "Indicates whether the payment receipt is printed or not.";
description add-field-description --onProperty paymentReceiptPrinted --title "Ouverte" --text "Indique si le reçu de paiement est imprimé ou pas." --locale fr;
@/* default=false */;

field string --named paidBy;
description add-field-description --onProperty paidBy --title "Paid By" --text "name of the person performing this payment.";
description add-field-description --onProperty paidBy --title "Payé Par" --text "Nom de la personne efectuant ce paiement." --locale fr;

field string --named clientName;
description add-field-description --onProperty clientName --title "Client Name" --text "Name of the client performing this payment.";
description add-field-description --onProperty clientName --title "Nom du Client" --text "Nom du client efectuant ce paiement." --locale fr;

field string --named note;
description add-field-description --onProperty note --title "Note" --text "Description of the payment";
description add-field-description --onProperty note --title "Note" --text "Description du paiement" --locale fr;
constraint Size --onProperty note --max 256;
@/* informations */;

field string --named voucherNumber;
description add-field-description --onProperty voucherNumber --title "Voucher Number" --text "Number of the voucher performing this payment.";
description add-field-description --onProperty voucherNumber --title "Numéro du Bon" --text "Numéro de bon effectuant le paiement." --locale fr;


field number --named voucherAmount --type java.math.BigDecimal;
description add-field-description --onProperty voucherAmount --title "Voucher Amount" --text "The voucher amount for this payment.";
description add-field-description --onProperty voucherAmount --title "Montant du Bon" --text "Le montant du bon pour ce paiement." --locale fr;

field boolean --named compensation;
description add-field-description --onProperty compensation --title "Compensation" --text "Indicates whether this payment is a compensation (coupon, voucher).";
description add-field-description --onProperty compensation --title "Compensation" --text "Indique si c est un paiement par compensation de bon avoir ou non." --locale fr;
@/* default=false */;
cd ~~ ;

