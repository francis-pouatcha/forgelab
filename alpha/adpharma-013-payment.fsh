

@/* =============================== */;
@/* Payment, CashDrawers */;

@/* Type Paiement*/;
entity --named PaymentType --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Payment Type" --text "Type of payment.";
description add-class-description  --locale fr --title "Type Paiement" --text "Type de paiement.";
@/* Enumération{CASH, CREDIT, CHEQUE, PROFORMAT, BON_CMD, BON_CMD_PARTIEL,  VENTE_PARTIEL, CARTE_CREDIT, BON_AVOIR_CLIENT} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this payment type.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce type de paiement." --locale fr;

@/* Type Payeur*/;
entity --named PayerType --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Payer Type" --text "Type of payer.";
description add-class-description  --locale fr --title "Type Payeur" --text "Type de payeur.";
@/* Enumeration{CLIENT, CLIENT_PAYEUR} */;

field string --named name;
description add-field-description --onProperty name --title "Name" --text "The name of this payer type.";
description add-field-description --onProperty name --title "Nom" --text "Le nom de ce type de payeur." --locale fr;

@/* Entité Caisse */;
entity --named CashDrawer --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Cash Drawer" --text "A cash drawer.";
description add-class-description  --locale fr --title "Caisse" --text "Une caisse.";

field string --named cashDrawerNumber;
description add-field-description --onProperty cashDrawerNumber --title "Cash Drawer Number" --text "The number of this cash drawer.";
description add-field-description --onProperty cashDrawerNumber --title "Numéro de Caisse" --text "Le numéro de cette caisse." --locale fr;

field manyToOne --named cashier --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty cashier --title "Cashier" --text "The user collecting the payment on this drawer.";
description add-field-description --onProperty cashier --title "Caissier" --text "L'utilisateur percevant le paiement surcette caisse." --locale fr;

field manyToOne --named closedBy --fieldType ~.jpa.PharmaUser;
description add-field-description --onProperty closedBy --title "Closed By" --text "The user who closed this cash drawer.";
description add-field-description --onProperty closedBy --title "Fermé Par" --text "Utilisateur ayant fermé la caisse." --locale fr;

field manyToOne --named site --fieldType ~.jpa.Site
constraint NotNull --onProperty site;
description add-field-description --onProperty site --title "Site" --text "Site in which this drawer resides.";
description add-field-description --onProperty site --title "Site" --text "Site dans lequel la caisse est gerée." --locale fr;

field temporal --type TIMESTAMP --named openingDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty openingDate --title "Opening Date" --text "The opening date of this drawer.";
description add-field-description --onProperty openingDate --title "Date d'Ouverture" --text "La date d'ouverture de cette caisse." --locale fr;

field temporal --type TIMESTAMP --named closingDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty closingDate --title "Closing Date" --text "The closing date of this drawer.";
description add-field-description --onProperty closingDate --title "Date de Fermeture" --text "La date de fermeture de cette caisse." --locale fr;

field number --named initialAmount --type java.math.BigDecimal;
description add-field-description --onProperty initialAmount --title "Initial Amount" --text "The initial amount.";
description add-field-description --onProperty initialAmount --title "Fond de Caisse" --text "Le fond initial de la caisse." --locale fr;
@/* Default=0 */;

field boolean --named opened;
description add-field-description --onProperty opened --title "Open" --text "Indicates whether the cash drawer is open.";
description add-field-description --onProperty opened --title "Ouverte" --text "Indique si la caisse est ouverte." --locale fr;
@/* default=true */;

field number --named totalCashIn --type java.math.BigDecimal;
description add-field-description --onProperty totalCashIn --title "Total Cash In" --text "The total cash in.";
description add-field-description --onProperty totalCashIn --title "Total Encaissement" --text "L'encaissement totale." --locale fr;
@/* Default=0 */;
@/* Somme des espèces qui entrent en caisse(ttotal_cash+total_cheque+total_bon_cmd+total_bon_client+total_bon_cmd) */;

field number --named totalCashOut --type java.math.BigDecimal;
description add-field-description --onProperty totalCashOut --title "Total Cash Out" --text "Total withdrawal from this drawer.";
description add-field-description --onProperty totalCashOut --title "Total Retrait" --text "Total des decaissements éffectués en caisse." --locale fr;
@/* Default=0 */;

field number --named totalCash --type java.math.BigDecimal;
description add-field-description --onProperty totalCash --title "Total Cash" --text "Total cash in this drawer.";
description add-field-description --onProperty totalCash --title "Total Cash" --text "Total cash dans cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCreditSales --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditSales --title "Total Credit Sales" --text "Total credit sales in this drawer.";
description add-field-description --onProperty totalCreditSales --title "Total Credit" --text "Total credit vendu par cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCheck --type java.math.BigDecimal;
description add-field-description --onProperty totalCheck --title "Total Checks" --text "Total checks in this drawer.";
description add-field-description --onProperty totalCheck --title "Total Chèque" --text "Total chèque dans cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCreditCard --type java.math.BigDecimal;
description add-field-description --onProperty totalCreditCard --title "Total Credit Card" --text "Total credit cards by this drawer.";
description add-field-description --onProperty totalCreditCard --title "Total Carte Credit" --text "Total carte de credit par cette caisse." --locale fr;
@/* Default=0 */;

field number --named totalCompanyVoucher --type java.math.BigDecimal;
description add-field-description --onProperty totalCpyVoucher --title "Total Company Vouchera" --text "Total voucher (from company or hospital) in this drawer.";
description add-field-description --onProperty totalCpyVoucher --title "Total Avoir Companie" --text "Totale des bons (de sociéte ou d'hopital) qu'il ya en caisse." --locale fr;
@/* Default=0 */;

field number --named totalClientVoucher --type java.math.BigDecimal;
description add-field-description --onProperty totalClientVoucher --title "Total Client Voucher" --text "Total voucher (from client) in this drawer.";
description add-field-description --onProperty totalClientVoucher --title "Total Avoir Client" --text "Totale des bons (client) qu'il ya en caisse." --locale fr;
@/* Default=0 */;

field number --named totalCashDebt --type java.math.BigDecimal;
description add-field-description --onProperty totalCashDebt --title "Total Cash Debt" --text "Total cash from client debts in the drawer.";
description add-field-description --onProperty totalCashDebt --title "Total Cash Dette" --text "Totale cash des dettes client dans cette caisse." --locale fr;
@/* Default=0 */;

@/* Entité  paiement */;
entity --named Payment --package ~.jpa --idStrategy AUTO;
description add-class-description --title "Payment" --text "A payment.";
description add-class-description  --locale fr --title "Paiement" --text "Un paiement.";

field string --named paymentNumber;
description add-field-description --onProperty paymentNumber --title "Payment Number" --text "The paiment number.";
description add-field-description --onProperty paymentNumber --title "Numéro du Paiement" --text "Le numéro du paiement." --locale fr;

field temporal --type TIMESTAMP --named paymentDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
description add-field-description --onProperty paymentDate --title "Payment Date" --text "The payment date.";
description add-field-description --onProperty paymentDate --title "Date de Paiement" --text "La date de paiement." --locale fr;

field temporal --type TIMESTAMP --named recordDate; 
@/* pattern='' dd-MM-yyyy HH:mm'';*/;
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

field manyToOne --named invoice --fieldType ~.jpa.Invoice;
description add-field-description --onProperty invoice --title "Invoice" --text "The invoice of this payment.";
description add-field-description --onProperty invoice --title "Facture" --text "La facture de ce paiement." --locale fr;

cd ../Invoice.java;

field oneToMany --named payments --fieldType ~.jpa.Payment --inverseFieldName invoice --fetchType EAGER  --cascade;
description add-field-description --onProperty payments --title "Payments" --text "Payments associated with this invoice.";
description add-field-description --onProperty payments --title "Piements" --text "Payements associe avec cette facture. " --locale fr;
@/* OneToMany(cascadeType=All) */;

cd ../Payment.java;

field manyToOne --named paymentType --fieldType ~.jpa.PaymentType;
description add-field-description --onProperty paymentType --title "Payment Type" --text "The type of this payment.";
description add-field-description --onProperty paymentType --title "Type de Paiement" --text "La type de ce paiement." --locale fr;
@/* Enumération{CASH, CREDIT, CHEQUE, PROFORMAT, BON_CMD, BON_CMD_PARTIEL,  VENTE_PARTIEL, CARTE_CREDIT, BON_AVOIR_CLIENT} */;

field boolean --named paymentReceiptPrinted;
description add-field-description --onProperty paymentReceiptPrinted --title "Open" --text "Indicates whether the payment receipt is printed or not.";
description add-field-description --onProperty paymentReceiptPrinted --title "Ouverte" --text "Indique si le reçu de paiement est imprimé ou pas." --locale fr;
@/* default=false */;

field string --named paidBy;
description add-field-description --onProperty paidBy --title "Paid By" --text "name of the person performing this payment.";
description add-field-description --onProperty paidBy --title "Payé Par" --text "Nom de la personne efectuant ce paiement." --locale fr;

field manyToOne --named payerType --fieldType ~.jpa.PayerType;
description add-field-description --onProperty payerType --title "Payer Type" --text "The type of this payer.";
description add-field-description --onProperty payerType --title "Type de Payeur" --text "La type de ce payeur." --locale fr;
@/* qui_paye, Enumeration{CLIENT, CLIENT_PAYEUR} */;

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
description add-field-description --onProperty compensation --title "Compensation" --text "Indique si c'est un paiement par compensation de bon avoir ou non." --locale fr;
@/* default=false, compenser */;

