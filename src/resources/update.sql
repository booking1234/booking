USE `razor`;

/* 
 * ID 					- ID of entry
 * create_date 			- time and date entry was created 
 * payment_gateway_id	- foreign key to PaymentGatewayProviders
 * funds_holder 		- this value is set based on whether the PM is using their Payment Gateway account or BookingPal payment gateway account
 * 							0 - Indicates that the PM is holding the funds for this property and that we need to invoice the PM for the funds
 * 							1 - Indicates that BookingPal is holding the funds and those we need to send a check to the PM for the booking
 * 							2 - Indicates that the Payment processor has split the funds with all parties
 * supplier_id 			- property manager ID
 * gateway_code 		- Username or code used for the payment gateways
 * gateway_account_id 	- ID or account used for payment gateway
 * additional_info 		- any other information required for this specific payment gateway provider
 */

CREATE TABLE IF NOT EXISTS manager_to_gateway (id INT NOT NULL auto_increment, create_date DATETIME, payment_gateway_id INT, funds_holder TINYINT, supplier_id INT, 
	gateway_code VARCHAR(255), gateway_account_id VARCHAR(255), additional_info VARCHAR(255), PRIMARY KEY  USING BTREE (id));

/*
 * ID			- Auto ID, this ID shoukd be stored in the manager_to_gateway table during the registration process
 * name			- name of the payment gateway, these values should be displayed in all dropdowns when selecting a payment gateway provider 
 * create_date	- date and time that this entry was created
 * fee			- the fee that is charged for each transaction by the payment gateway
 * autopay		- this will indicate if the all the payments will be made by the payment gateway or BookingPal will need to call the payment 
 * 				  gateway for each additional payment
 * 					0 - payment gateway will charge remaining payments
 * 					1 - BookingPal will need to call payment gateway for each additional charge
 */
CREATE TABLE IF NOT EXISTS payment_gateway_provider (id INT NOT NULL auto_increment, name VARCHAR(100), create_date DATETIME, fee TINYINT, autopay TINYINT(1), PRIMARY KEY  USING BTREE (id));

/*
 * ID 						- Auto ID for transaction
 * create_date				- insert date and time
 * server_id				- web server that submitted ID
 * 								each web server should have a unique ID. This ID must be passed in and saved when a payment transaction is made
 * reservation_id			- ID of Razor Booking ID
 * pms_confirmation_id		- PMS confirmation ID
 * payment_method			- 	0 - Credit card
 * 								1 - ACH
 * 								2 - Mail
 * 								3 - API
 * gateway_transaction_id	- payment gateway transaction ID
 * gateway_id				- ID of payment gateay that was used for the transaction
 * funds_holder				- this value is set based on the vakue in the manager_to_gateway table
 * 								0 - indicates that the PM is holding the funds for this property and that we need to invoice the PM for the funds
 * 								1 - indicates that BookingPal is holding the funds ant those we need to send a check to the OM for the booking
 * 								2 - indeicates that the payment processor has split the funds with all parties
 * 								3 - indicates that the channel partner is holding the funds for this booking
 * partial_iin				- last 4 digits of the credit card used
 * status					- status returned by the payment gateway
 * 								accepted - payment processed and accepted
 * 								declined - credit card was declined
 * 								failed - credit card was rejected by processor (invalid information supplier or pther reasons)
 * message					- the message returned by the payment processor. If the payment was declined or rejected, the message will help to identify the issue
 * partner_id				- ID of the partner that booked the property
 * supplier_id				- ID of the property manager of the property
 * charge_date				- the date and time the payment gateway returned with the accepted charge
 * total_amount				- total amount that was charged on the credit card. This should include the initial charge and the pending payments if any.
 * currency					- the currency used for this transaction using the ISO 4217 format
 * total_commission			- this should the total commission that was paid for this property. This should be determined by looking up the commission for this property
 * partner_payment			- the amount that will be paid to the partner
 * 								when calculating this value find the partner in the partner table and use the percentage entered for this partner
 * total_bookingpal_payment	- the amount that will be paid to BookingPal from the total commission collected for the TotalAmountCharged
 * 								this percentage will be saved in a settings table under 'BookingPal Commission'
 * final_amount 			- the amount to be collected once the complete payment to the property is made
 * credit_card_fee			- the fee that is charged by the payment gateway provider. If the valie is not returned, use the value from payment_gateway_provider provider table
 * charge_type				- this value should indicate the type of charge that was made
 * 								Full - full payment was made
 * 								Deposit - only deposit payment was made
 * 								Security - only security deposit was made
 * 								Security and deposit - security deposit and rental deposit were made
 * 								Refund - if the transaction is a refund transaction, all values for a refund should be negative
 * 								Cancelled - booking was cancelled but a penalty payment was made // deprecated
 * cancelled				- flag that shows if transaction was cancelled
 */
CREATE TABLE IF NOT EXISTS payment_transaction (id BIGINT NOT NULL auto_increment, create_date DATETIME, server_id INT, reservation_id INT, pms_confirmation_id INT, payment_method INT,
	gateway_transaction_id VARCHAR(100), gateway_id INT, funds_holder TINYINT, partial_iin INT, status VARCHAR(100), message VARCHAR(1000), partner_id INT, supplier_id INT,
	charge_date DATETIME, total_amount DOUBLE, currency VARCHAR(100), total_commission DOUBLE, partner_payment DOUBLE, total_bookingpal_payment DOUBLE,
	final_amount DOUBLE, credit_card_fee DOUBLE, charge_type VARCHAR(100), cancelled (TINYINT), PRIMARY KEY USING BTREE (id));
	
/*
 * Add Phone Number column to the reservation table

ALTER TABLE reservation ADD phone VARCHAR(20) AFTER Notes; */

/* ChannelPartnersAdditionalInfo */

CREATE TABLE IF NOT EXISTS `channel_partner` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`channel_name` VARCHAR(255) NULL DEFAULT NULL,
	`logo_url` VARCHAR(255) NULL DEFAULT NULL,
	`website_url` VARCHAR(255) NULL DEFAULT NULL,
	`channel_type` VARCHAR(50) NULL DEFAULT NULL,
	`coverage` VARCHAR(255) NULL DEFAULT NULL,
	`contact_type` VARCHAR(255) NULL DEFAULT NULL,
	`payment_process` VARCHAR(255) NULL DEFAULT NULL,
	`payouts` VARCHAR(255) NULL DEFAULT NULL,
	`damage_coverage` VARCHAR(255) NULL DEFAULT NULL,
	`traffic` VARCHAR(255) NULL DEFAULT NULL,
	`commission` DOUBLE(9,2) NULL DEFAULT NULL,
	`listing_fees` VARCHAR(255) NULL DEFAULT NULL,
	`privacy_policy` VARCHAR(255) NULL DEFAULT NULL,
	`terms_conditions` VARCHAR(255) NULL DEFAULT NULL,
	`selected` TINYINT(1) NULL DEFAULT NULL,
	`phone` VARCHAR(255) NULL DEFAULT NULL,
	`email` VARCHAR(255) NULL DEFAULT NULL,
	`office_address` VARCHAR(255) NULL DEFAULT NULL,
	`description` TEXT NULL,
	PRIMARY KEY (`id`) USING BTREE
);

/* ChannelPartnersToPropertyManagerMap */		
		
CREATE TABLE IF NOT EXISTS `manager_to_channel` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`property_manager_id` INT(11) NOT NULL,
	`channel_partner_id` INT(11) NOT NULL,
	PRIMARY KEY (`id`) USING BTREE
);
		
/* PaymentMethod */

CREATE TABLE IF NOT EXISTS `payment_method` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pmid` INT(11) NOT NULL,
	`type` VARCHAR(20) NOT NULL,
	`payment_info` VARCHAR(300) NULL DEFAULT NULL,
	`entry_date_time` DATETIME NULL DEFAULT NULL,
	`amount` FLOAT(2,1) NULL DEFAULT NULL,
	`verified_date` DATE NULL DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE
);

/* == Property Manager Info table == */

CREATE TABLE IF NOT EXISTS `property_manager_info` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pm_id` INT(11) NOT NULL,
	`pms_id` INT(11) NULL DEFAULT NULL,
	`funds_holder` TINYINT(4) NULL DEFAULT NULL,
	`registration_step_id` INT(10) UNSIGNED NULL DEFAULT NULL,
	`damage_coverage_type` TINYINT(4) NULL DEFAULT NULL,
	`damage_insurance` VARCHAR(255) NULL DEFAULT NULL,
	`cancellation_type` TINYINT(4) NULL DEFAULT NULL,
	`number_of_payments` INT(10) UNSIGNED NULL DEFAULT NULL,
	`payment_amount` DOUBLE NULL DEFAULT NULL,
	`payment_type` INT(10) UNSIGNED NULL DEFAULT NULL, /* 0. "% Booking Value", 1. "% Daily Rate", 2. "Amount per Booking", 3. "Amount per Day" */
	`remainder_payment_date` INT(10) UNSIGNED NULL DEFAULT NULL, /* days quantity */
	`check_in_time` TIME NULL DEFAULT '10:30:00',
	`check_out_time` TIME NULL DEFAULT '10:30:00',
	`terms_link` VARCHAR(255) NULL DEFAULT NULL, 
	`new_registration` TINYINT(1) UNSIGNED NULL DEFAULT NULL,
	`created_date` DATETIME NOT NULL DEFAULT '2000-01-01 00:00:00',
	PRIMARY KEY (`id`) USING BTREE
)

/* == Add column 'CleaningFee, Deposit, AssignedtoManager' to the table 'Product' == */

ALTER TABLE `product`
	ADD COLUMN 	`CleaningFee` DOUBLE NULL DEFAULT '0',
	ADD COLUMN	`SecurityDeposit` DOUBLE NULL DEFAULT '0',
	ADD COLUMN `AssignedtoManager` TINYINT(1) UNSIGNED NULL DEFAULT NULL AFTER `Physicaladdress`;

/*
 * ID - auto ID
 * entry_date_time - date and time of entry 
 * booking_id - Razor booking ID
 * pms_confirmation_id - PMS confirmation ID
 * payment_gateway_id - ID of payment gateway that was used for the transaction
 * funds_holder - This value should be set based on the value set in the PropertyManagerToPaymentGatewayMap table
 * 	0 - Indicates that the PM is holding the funds for this property and that we need to invoice the PM for the funds
 * 	1 - Indicates that BookingPal is holding the funds and those we need to send a check ti the PM for the booking
 *  2 - Indicates that the Payment processor has split the funds with all parties
 *  3 - Indicates that the Channel Partner is holding the funds for this booking 
 * partial_iin - last 4 digits of the credit card used
 * first_name - first name of person charged
 * last_name - last name of person charged
 * phone_number - telephone number of the person charged stored as ITU-T E.164
 * partner_id - ID of the partner that booked the property
 * supplier_id - ID of the property manager of the property
 * charge_date - the date and time the next payment will be processed by the payment gateway
 * charge_amount - total amount remaining to be charged
 * currency - the currency used for this transaction using the ISO 4217 format
 * commission - commission remaining to be collected. This should determined by looking up the commission for this property.
 * partner_payment - The amount that will be paid to the partner
 * 	When calculating this value, find the partner in the partner table and use the percentage entered for this partner 
 * bookingpal_payment - the amount that is pending from the remaining balance to be charged
 * 	this percentage will be saved in a settings table under 'BookingPal Commission'
 * gateway_transaction_id - this is ID returned by the payment gateway when the initial payment was made. 
 * This ID should be used to verify the payment and or make additional payments
 * status -
 * 	1 - Active - transaction is waiting to be charged
 * 	2 - Pending - Charge was made but not cleared
 * 	3 - Cleared - Charge was made and was cleared
 * 	4 - Failed - Charge was made but failed
 * 	5 - Deleted - Transaction was deleted by Admin
 * 	6 - Cancelled = the reservation was cancelled by the PMS 
 * autopay - this value will come from the selected patment gateway
 * 	0 - payment gatewat will charge remaining payments
 * 	1 - BookingPal will need to call payment gateway for each additional charge
 */
CREATE TABLE IF NOT EXISTS pending_transaction(id INT NOT NULL auto_increment, entry_date_time DATETIME, booking_id INT, pms_confirmation_id INT, payment_gateway_id INT,
	funds_holder INT, partial_iin INT, first_name VARCHAR(100), last_name VARCHAR(100), phone_number VARCHAR(100), partner_id INT, supplier_id INT, charge_date DATE, charge_amount DOUBLE,
	currency VARCHAR(100), commission DOUBLE, partner_payment DOUBLE, bookingpal_payment DOUBLE, gateway_transaction_id VARCHAR(100), status SMALLINT, autopay TINYINT(1),
	PRIMARY KEY USING BTREE (id));

/* Adding the AltID column to the Price table */
ALTER TABLE PRICE ADD AltID VARCHAR(100) DEFAULT NULL;

/* Adding the confirmation_id column to the reservation table */
ALTER TABLE reservation ADD confirmation_id VARCHAR(100) DEFAULT NULL;
/* Added Property Manager id */
ALTER TABLE product ADD AltSupplierId VARCHAR(15) NULL DEFAULT NULL  AFTER AltID;

/* Foreign key to party table */
ALTER TABLE channel_partner ADD party_id INT NULL DEFAULT NULL;

/* Adding SupportsCreditCard, SendConfirmationEmails columns to the Partner table */
ALTER TABLE `partner` 
	ADD COLUMN `SupportsCreditCard` TINYINT(1) NULL DEFAULT '0' AFTER `BookOffline`,
	ADD COLUMN `SendConfirmationEmails` TINYINT(1) NULL DEFAULT '0' AFTER `SupportsCreditCard`,
	ADD COLUMN `RegisterEmptyProperties` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' AFTER `SendConfirmationEmails`,
	ADD COLUMN `SeparatePMAccounts` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' AFTER `RegisterEmptyProperties`;

/* Adding 'payment_processing_type' column to the 'property_manager_info' table */
ALTER TABLE `property_manager_info`
	ADD COLUMN `payment_processing_type` TINYINT(4) NULL DEFAULT NULL AFTER `funds_holder`;
	
/* Added google names, parent locations and date of creation to `location` table*/
ALTER TABLE `location`
	ADD COLUMN `GName` VARCHAR(255) NULL DEFAULT NULL AFTER `Name`,
	ADD COLUMN `AdminArea_lvl_1` VARCHAR(255) NULL DEFAULT NULL AFTER `Region`,
	ADD COLUMN `AdminArea_lvl_2` VARCHAR(255) NULL DEFAULT NULL AFTER `AdminArea_lvl_1`,
	ADD COLUMN `CodeInterhome` VARCHAR(10) NULL DEFAULT NULL AFTER `Notes`,
	ADD COLUMN `DateCreated` TIMESTAMP DEFAULT '0000-00-00 00:00:00' AFTER `CodeInterhome`,
	ADD COLUMN `ActiveProducts` INT(11) NULL DEFAULT '0' AFTER `version`;

/* Table stores information about customer payment profiles for further safety work with credit cards. Customer profile ID and customer payment profile ID is provided by the payment gateway */
CREATE TABLE IF NOT EXISTS customer_payment_profile(id INT NOT NULL auto_increment, reservation_id INT, customer_profile_id VARCHAR(255), 
customer_payment_profile_id VARCHAR(255), gateway_id INT, PRIMARY KEY USING BTREE (id));


ALTER TABLE `razor`.`currency` ADD COLUMN `State` varchar(20) AFTER `Jetpay`, CHANGE COLUMN `version` `version` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP AFTER `State`;

/* User type for party instance */
ALTER TABLE `razor`.`party`	ADD COLUMN `UserType` VARCHAR(20) NULL DEFAULT NULL  AFTER `version`;
	
/* 
 * `id` - auto id;
 * `party_id` - id of PM from 'party' table;
 * `MC` - is support Master Card cards;
 * `VISA` - is support VISA cards;
 * `AE` - is support AMERICAN EXPRESS cards;
 * `DISCOVER` - is support `DISCOVER` cards;
 * `DINERSCLUB` - is support `DINES CLUB` cards;
 * `JCB` - is support `JCB` cards;
 * `none` - none of this types (need to clarify).
 */
 
CREATE TABLE `property_manager_support_cc` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`party_id` INT NOT NULL,
	`MC` TINYINT NULL,
	`VISA` TINYINT NULL,
	`AE` TINYINT NULL,
	`DISCOVER` TINYINT NULL,
	`DINERSCLUB` TINYINT NULL,
	`JCB` TINYINT NULL,
	`none` TINYINT NULL,
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

/* add LocationType, CodeRentalsUnited and ParentID columns */
ALTER TABLE `location`
	ADD COLUMN `LocationType` VARCHAR(255) NULL DEFAULT 'locality' AFTER `ActiveProducts`,
	ADD COLUMN `ParentID` INT(10) NULL DEFAULT NULL AFTER `LocationType`,
	ADD COLUMN `CodeRentalsUnited` VARCHAR(10) NULL DEFAULT NULL AFTER `CodeInterhome`;
	
ALTER TABLE `price`
	ADD COLUMN `Minstay` INT NULL DEFAULT '0' AFTER `Available`;
	
/* add fileld 'order' for order by. */
ALTER TABLE `channel_partner`
	ADD COLUMN `order` INT(11) NULL DEFAULT '0' AFTER `party_id`;
	
	
/* Adding cruise attribute */
INSERT INTO razor.attribute (List, ID, Name, Type) values ('PCT', 12, 'Cruise', 'RZ');

/* Improve product lookup performance */
ALTER TABLE razor.product ADD INDEX alt_product_lookup (AltID, AltPartyId);

ALTER TABLE razor.location MODIFY AdminArea_lvl_1 varchar(255) CHARACTER SET utf8;
ALTER TABLE razor.location MODIFY AdminArea_lvl_2 varchar(255) CHARACTER SET utf8;
ALTER TABLE razor.location MODIFY `GName` varchar(255) CHARACTER SET utf8;


ALTER TABLE razor.price ADD INDEX alt_price_lookup (EntityType, EntityID, PartyID, AltID);

/*
 * `id` - auto id;
 * `pm_id` - property manager id;
 * `cancelation_date` - period (days count) during which traveler can cancel the reservation if cancelationType=2;
 * `cancelation_refund` - refund value (%) that traveler receive in case of cancellation;
 * `cancelation_transaction_fee` - fee value that traveler pay for the transaction cancellation.
 */
CREATE TABLE `property_manager_cancellation_rule` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pm_id` INT(11) NOT NULL,
	`cancellation_date` VARCHAR(50) NOT NULL,
	`cancellation_refund` INT(11) NOT NULL DEFAULT '0',
	`cancellation_transaction_fee` DOUBLE NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`) USING BTREE
);

/*New table for minstay values*/
CREATE TABLE `property_min_stay` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`SupplierID` INT(10) NULL DEFAULT NULL,
	`ProductID` VARCHAR(15) NULL DEFAULT NULL,
	`FromDate` DATE NOT NULL DEFAULT '1970-01-01',
	`ToDate` DATE NOT NULL DEFAULT '1970-01-01',
	`Value` INT(10) NULL DEFAULT '1',
	`version` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,	
	PRIMARY KEY (`id`) USING BTREE
);

CREATE TABLE `image` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `language` varchar(2) NOT NULL DEFAULT 'EN',
  `product_id` int(11) DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `old_name` varchar(255) DEFAULT NULL,
  `state` varchar(15) DEFAULT 'Created',
  `type` enum('hosted','linked','blob') NOT NULL,
  `data` blob,
  `notes` varchar(20000) CHARACTER SET utf8 DEFAULT NULL,
  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `standard` tinyint(1) NOT NULL DEFAULT '0',
  `thumbnail` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `Name` (`name`,`product_id`,`language`) USING BTREE
);

/* New table for the end of a day property manager payment job */
CREATE TABLE payment_register(
	ID INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	entry_date_time DATETIME,
	reservation_id INT(10),
	property_id INT(10),
	pm_id INT(10),
	partner_id INT(10),
	payment_transaction_id INT(10),
	type SMALLINT,
	cleared BOOLEAN,
	PRIMARY KEY (`id`) USING BTREE
);

/*New column for reading prices. This is for Maxxton at the moment. Should be true if there is more prices for that property with same starting date.*/
ALTER TABLE `razor`.`product` ADD COLUMN `UseOnePriceRow` TINYINT(1) NOT NULL DEFAULT 0  AFTER `SecurityDeposit` ;
/* Booking Com Integration changes starts here */

/* New Table to store the last fetch time for bookingcom */
CREATE TABLE IF NOT EXISTS channel_lastfetch ( channel_id INT(10) NOT NULL, api_name varchar(50),last_fetch varchar(50) );
/* intialize the variable with a value initial */
insert into channel_lastfetch values(276,'reservation','Intial');
insert into channel_lastfetch values(276,'availability','Intial');


/*
Table to maintain the mapping between channel product id and BP product id */
CREATE TABLE `channel_product_map` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`ProductID` VARCHAR(50) NULL DEFAULT NULL,
	`Channel_ProductID` VARCHAR(50) NULL DEFAULT NULL,
	`Channel_ID` INT(10) NOT NULL,
	`Channel_RoomID` VARCHAR(50) NULL,
	`version` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,	
	PRIMARY KEY (`id`) USING BTREE
);

INSERT INTO `razor`.`channel_product_map` (`ProductID`, `Channel_ProductID`, `Channel_ID`,`Channel_RoomID`) VALUES ('121', '13104', '276','1310404');

INSERT INTO `razor`.`channel_product_map` (`ProductID`, `Channel_ProductID`, `Channel_ID`,`Channel_RoomID`) VALUES ('122', '13104', '276','1310401');

INSERT INTO `razor`.`channel_product_map` (`ProductID`, `Channel_ProductID`, `Channel_ID`,`Channel_RoomID`) VALUES ('123', '13104', '276','1310406');

INSERT INTO `razor`.`channel_product_map` (`ProductID`, `Channel_ProductID`, `Channel_ID`,`Channel_RoomID`) VALUES ('124', '13104', '276','1310405');



ALTER TABLE `razor`.`price` ADD COLUMN `Maxstay` INT(11)  null  AFTER `Minstay`;

CREATE TABLE `reservation_ext` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AltID` varchar(20) DEFAULT NULL,
  `ReservationID` int(10) unsigned DEFAULT NULL,
  `PartyID` int(10) unsigned DEFAULT NULL,
  `Name` varchar(50) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL,
  `State` varchar(15) DEFAULT NULL,
  `Addon_Type` varchar(50) NOT NULL,
  `Type` varchar(5) NOT NULL,
  `Quantity` double DEFAULT '0',
  `Unit` varchar(3) DEFAULT NULL,
  `Value` double DEFAULT NULL,
  `Minimum` double DEFAULT '0',
  `Factor` double DEFAULT '1',
  `Currency` varchar(3) DEFAULT NULL,
  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Cost` double DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE,
  KEY `Addon` (`ReservationID`,`Addon_Type`) USING BTREE,
 )

/* Booking Com Integration changes ends here */

/* Added abbreavation column to partner for common PMS name */
ALTER TABLE `razor`.`partner` ADD COLUMN `Abbrevation` VARCHAR(5) NULL  AFTER `SendConfirmationEmails` 
, ADD UNIQUE INDEX `Abbrevation_UNIQUE` (`Abbrevation` ASC) ;


update partner  SET  partner.Abbrevation = 'INT'    where partner.PartyName =   'Interhome AG';
update partner  SET  partner.Abbrevation =  'NEX'   where partner.PartyName =  'NextpaxMainAccount';
update partner  SET  partner.Abbrevation =  'DAN'   where partner.PartyName =  'DanCenter';
update partner  SET  partner.Abbrevation =  'HOG'   where partner.PartyName =  'Hogenboom';
update partner  SET  partner.Abbrevation =  'HAP'   where partner.PartyName =  'HappyHome';
update partner  SET  partner.Abbrevation =  'GRA'   where partner.PartyName =  'GranAlacant';
update partner  SET  partner.Abbrevation =  'HOL'   where partner.PartyName =  'HolidayHome';
update partner  SET  partner.Abbrevation =  'INC'   where partner.PartyName =  'InterChalet';
update partner  SET  partner.Abbrevation =  'INH'   where partner.PartyName =  'Interhome';
update partner  SET  partner.Abbrevation =  'NOV'   where partner.PartyName =  'Novasol';
update partner  SET  partner.Abbrevation =  'ROO'   where partner.PartyName =  'Roompot';
update partner  SET  partner.Abbrevation =  'TOP'   where partner.PartyName =  'TopicTravel';
update partner  SET  partner.Abbrevation =  'TUI'   where partner.PartyName =  'TUIFerienhaus';
update partner  SET  partner.Abbrevation =  'UPH'   where partner.PartyName =  'UphillTravel';
update partner  SET  partner.Abbrevation =  'VAC'   where partner.PartyName =  'Vacasol';
update partner  SET  partner.Abbrevation =  'BEL'   where partner.PartyName =  'Belvilla';
update partner  SET  partner.Abbrevation =  'BUN'   where partner.PartyName =  'Bungalow Net';
update partner  SET  partner.Abbrevation =  'LAK'   where partner.PartyName =  'Lake Tahoe Accommodations';
update partner  SET  partner.Abbrevation =  'MEX'   where partner.PartyName =  'Maxxton';
update partner  SET  partner.Abbrevation =  'CII'   where partner.PartyName =  'Ciirus API';
update partner  SET  partner.Abbrevation =  'AAX'   where partner.PartyName =  'Aaxsys';
update partner  SET  partner.Abbrevation =  'REN'   where partner.PartyName =  'Rentals United API';
update partner  SET  partner.Abbrevation =  'ORL'   where partner.PartyName =  'Orlando';
update partner  SET  partner.Abbrevation =  'FLO'   where partner.PartyName =  'Floriwood';

/* new table with extra values for prices */
CREATE TABLE IF NOT EXISTS `razor`.`adjustment` (
	`ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`ProductID` INT(11) UNSIGNED NULL DEFAULT NULL,
	`PartyID` INT(11) UNSIGNED NULL DEFAULT NULL,
	`State` TINYINT(4) NULL DEFAULT '1',
	`FromDate` DATE NULL DEFAULT NULL,
	`ToDate` DATE NULL DEFAULT NULL,
	`Extra` DOUBLE NULL DEFAULT '0',
	`FixPrice` DOUBLE NULL DEFAULT '0',
	`Currency` VARCHAR(3) NULL DEFAULT NULL,
	`MinStay` SMALLINT(6) UNSIGNED NULL DEFAULT '0',
	`MaxStay` SMALLINT(6) UNSIGNED NULL DEFAULT '0',
	`Servicedays` BIT(7) NULL DEFAULT b'1111111',
	`version` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`ID`),
	INDEX `PRODUCT` (`ProductID`, `PartyID`),
	INDEX `FromDate` (`FromDate`),
	INDEX `ToDate` (`ToDate`)
);


/**
 * Lookup table to map all country codes with the country name
 */
CREATE TABLE IF NOT EXISTS `razor`.`country_code`(
	country varchar(50) primary key, 
	iso_code2 varchar(2) not null,
	iso_code3 varchar(3) not null
);

insert into `razor`.`country_code` (iso_code2,iso_code3,country) values 
('AF','AFG','Afghanistan'),
('AL','ALB','Albania'),
('DZ','DZA','Algeria'),
('AS','ASM','American Samoa'),
('AD','AND','Andorra'),
('AO','AGO','Angola'),
('AI','AIA','Anguilla'),
('AQ','ATA','Antarctica'),
('AR','ARG','Argentina'),
('AM','ARM','Armenia'),
('AW','ABW','Aruba'),
('AU','AUS','Australia'),
('AT','AUT','Austria'),
('AZ','AZE','Azerbaijan'),
('BS','BHS','Bahamas'),
('BH','BHR','Bahrain'),
('BD','BGD','Bangladesh'),
('BB','BRB','Barbados'),
('BY','BLR','Belarus'),
('BE','BEL','Belgium'),
('BZ','BLZ','Belize'),
('BJ','BEN','Benin'),
('BM','BMU','Bermuda'),
('BT','BTN','Bhutan'),
('BO','BOL','Bolivia'),
('BA','BIH','Bosnia and Herzegovina'),
('BW','BWA','Botswana'),
('BR','BRA','Brazil'),
('VG','VGB','British Virgin Islands'),
('BN','BRN','Brunei'),
('BG','BGR','Bulgaria'),
('BF','BFA','Burkina Faso'),
('BI','BDI','Burundi'),
('KH','KHM','Cambodia'),
('CM','CMR','Cameroon'),
('CA','CAN','Canada'),
('CV','CPV','Cape Verde'),
('KY','CYM','Cayman Islands'),
('CF','CAF','Central African Republic'),
('CL','CHL','Chile'),
('CN','CHN','China'),
('CO','COL','Colombia'),
('KM','COM','Comoros'),
('CK','COK','Cook Islands'),
('CR','CRI','Costa Rica'),
('HR','HRV','Croatia'),
('CU','CUB','Cuba'),
('CW','CUW','Curacao'),
('CY','CYP','Cyprus'),
('CZ','CZE','Czech Republic'),
('CD','COD','Democratic Republic of Congo'),
('DK','DNK','Denmark'),
('DJ','DJI','Djibouti'),
('DM','DMA','Dominica'),
('DO','DOM','Dominican Republic'),
('TL','TLS','East Timor'),
('EC','ECU','Ecuador'),
('EG','EGY','Egypt'),
('SV','SLV','El Salvador'),
('GQ','GNQ','Equatorial Guinea'),
('ER','ERI','Eritrea'),
('EE','EST','Estonia'),
('ET','ETH','Ethiopia'),
('FK','FLK','Falkland Islands'),
('FO','FRO','Faroe Islands'),
('FJ','FJI','Fiji'),
('FI','FIN','Finland'),
('FR','FRA','France'),
('PF','PYF','French Polynesia'),
('GA','GAB','Gabon'),
('GM','GMB','Gambia'),
('GE','GEO','Georgia'),
('DE','DEU','Germany'),
('GH','GHA','Ghana'),
('GI','GIB','Gibraltar'),
('GR','GRC','Greece'),
('GL','GRL','Greenland'),
('GP','GLP','Guadeloupe'),
('GU','GUM','Guam'),
('GT','GTM','Guatemala'),
('GN','GIN','Guinea'),
('GW','GNB','Guinea-Bissau'),
('GY','GUY','Guyana'),
('HT','HTI','Haiti'),
('HN','HND','Honduras'),
('HK','HKG','Hong Kong'),
('HU','HUN','Hungary'),
('IS','ISL','Iceland'),
('IN','IND','India'),
('ID','IDN','Indonesia'),
('IR','IRN','Iran'),
('IQ','IRQ','Iraq'),
('IE','IRL','Ireland'),
('IM','IMN','Isle of Man'),
('IL','ISR','Israel'),
('IT','ITA','Italy'),
('CI','CIV','Ivory Coast'),
('JM','JAM','Jamaica'),
('JP','JPN','Japan'),
('JO','JOR','Jordan'),
('KZ','KAZ','Kazakhstan'),
('KE','KEN','Kenya'),
('KI','KIR','Kiribati'),
('XK','XKX','Kosovo'),
('KW','KWT','Kuwait'),
('KG','KGZ','Kyrgyzstan'),
('LA','LAO','Laos'),
('LV','LVA','Latvia'),
('LB','LBN','Lebanon'),
('LS','LSO','Lesotho'),
('LR','LBR','Liberia'),
('LY','LBY','Libya'),
('LI','LIE','Liechtenstein'),
('LT','LTU','Lithuania'),
('LU','LUX','Luxembourg'),
('MO','MAC','Macau'),
('MK','MKD','Macedonia'),
('MG','MDG','Madagascar'),
('MW','MWI','Malawi'),
('MY','MYS','Malaysia'),
('MV','MDV','Maldives'),
('ML','MLI','Mali'),
('MT','MLT','Malta'),
('MH','MHL','Marshall Islands'),
('MR','MRT','Mauritania'),
('MU','MUS','Mauritius'),
('MX','MEX','Mexico'),
('FM','FSM','Micronesia'),
('MD','MDA','Moldova'),
('MC','MCO','Monaco'),
('MN','MNG','Mongolia'),
('ME','MNE','Montenegro'),
('MS','MSR','Montserrat'),
('MA','MAR','Morocco'),
('MZ','MOZ','Mozambique'),
('MM','MMR','Myanmar [Burma]'),
('NA','NAM','Namibia'),
('NR','NRU','Nauru'),
('NP','NPL','Nepal'),
('NL','NLD','Netherlands'),
('NC','NCL','New Caledonia'),
('NZ','NZL','New Zealand'),
('NI','NIC','Nicaragua'),
('NE','NER','Niger'),
('NG','NGA','Nigeria'),
('NU','NIU','Niue'),
('NF','NFK','Norfolk Island'),
('KP','PRK','North Korea'),
('MP','MNP','Northern Mariana Islands'),
('NO','NOR','Norway'),
('OM','OMN','Oman'),
('PK','PAK','Pakistan'),
('PW','PLW','Palau'),
('PA','PAN','Panama'),
('PG','PNG','Papua New Guinea'),
('PY','PRY','Paraguay'),
('PE','PER','Peru'),
('PH','PHL','Philippines'),
('PN','PCN','Pitcairn Islands'),
('PL','POL','Poland'),
('PT','PRT','Portugal'),
('PR','PRI','Puerto Rico'),
('QA','QAT','Qatar'),
('CG','COG','Republic of the Congo'),
('RE','REU','Reunion'),
('RO','ROU','Romania'),
('RU','RUS','Russia'),
('RW','RWA','Rwanda'),
('BL','BLM','Saint Barth?lemy'),
('SH','SHN','Saint Helena'),
('KN','KNA','Saint Kitts and Nevis'),
('LC','LCA','Saint Lucia'),
('MF','MAF','Saint Martin'),
('PM','SPM','Saint Pierre and Miquelon'),
('VC','VCT','Saint Vincent and the Grenadines'),
('WS','WSM','Samoa'),
('SM','SMR','San Marino'),
('ST','STP','Sao Tome and Principe'),
('SA','SAU','Saudi Arabia'),
('SN','SEN','Senegal'),
('RS','SRB','Serbia'),
('SC','SYC','Seychelles'),
('SL','SLE','Sierra Leone'),
('SG','SGP','Singapore'),
('SK','SVK','Slovakia'),
('SI','SVN','Slovenia'),
('SB','SLB','Solomon Islands'),
('SO','SOM','Somalia'),
('ZA','ZAF','South Africa'),
('KR','KOR','South Korea'),
('SS','SSD','South Sudan'),
('ES','ESP','Spain'),
('LK','LKA','Sri Lanka'),
('SD','SDN','Sudan'),
('SR','SUR','Suriname'),
('SZ','SWZ','Swaziland'),
('SE','SWE','Sweden'),
('CH','CHE','Switzerland'),
('SY','SYR','Syria'),
('TW','TWN','Taiwan'),
('TJ','TJK','Tajikistan'),
('TZ','TZA','Tanzania'),
('TH','THA','Thailand'),
('TG','TGO','Togo'),
('TK','TKL','Tokelau'),
('TT','TTO','Trinidad and Tobago'),
('TN','TUN','Tunisia'),
('TR','TUR','Turkey'),
('TM','TKM','Turkmenistan'),
('TV','TUV','Tuvalu'),
('UG','UGA','Uganda'),
('UA','UKR','Ukraine'),
('AE','ARE','United Arab Emirates'),
('GB','GBR','United Kingdom'),
('US','USA','United States'),
('UY','URY','Uruguay'),
('UZ','UZB','Uzbekistan'),
('VU','VUT','Vanuatu'),
('VA','VAT','Vatican'),
('VE','VEN','Venezuela'),
('VN','VNM','Vietnam'),
('EH','ESH','Western Sahara'),
('YE','YEM','Yemen'),
('ZM','ZMB','Zambia'),
('ZW','ZWE','Zimbabwe');

/**
State/Region Code for US
Ref:http://www.infoplease.com/ipa/A0110468.html
**/
CREATE TABLE IF NOT EXISTS `razor`.`state_code`(
	name varchar(50) primary key, 
	abbrevation varchar(50) not null,
	code varchar(2) not null,
	country_code varchar(2)
);

insert into `razor`.`state_code` (name,abbrevation,code,country_code) values 
('Alabama','Ala','AL','US'),
('Alaska','Alaska','AK','US'),
('American Samoa','','AS','US'),
('Arizona','Ariz','AZ','US'),
('Arkansas','Ark','AR','US'),
('California','Calif','CA','US'),
('Colorado','Colo','CO','US'),
('Connecticut','Conn','CT','US'),
('Delaware','Del','DE','US'),
('Dist. of Columbia','D.C.','DC','US'),
('Florida','Fla','FL','US'),
('Georgia','Ga','GA','US'),
('Guam','Guam','GU','US'),
('Hawaii','Hawaii','HI','US'),
('Idaho','Idaho','ID','US'),
('Illinois','Ill','IL','US'),
('Indiana','Ind','IN','US'),
('Iowa','Iowa','IA','US'),
('Kansas','Kans','KS','US'),
('Kentucky','Ky','KY','US'),
('Louisiana','La','LA','US'),
('Maine','Maine','ME','US'),
('Maryland','Md','MD','US'),
('Marshall Islands','','MH','US'),
('Massachusetts','Mass','MA','US'),
('Michigan','Mich','MI','US'),
('Micronesia','','FM','US'),
('Minnesota','Minn','MN','US'),
('Mississippi','Miss','MS','US'),
('Missouri','Mo','MO','US'),
('Montana','Mont','MT','US'),
('Nebraska','Nebr','NE','US'),
('Nevada','Nev','NV','US'),
('New Hampshire','N.H.','NH','US'),
('New Jersey','N.J.','NJ','US'),
('New Mexico','N.M.','NM','US'),
('New York','N.Y.','NY','US'),
('North Carolina','N.C.','NC','US'),
('North Dakota','N.D.','ND','US'),
('Northern Marianas','','MP','US'),
('Ohio','Ohio','OH','US'),
('Oklahoma','Okla','OK','US'),
('Oregon','Ore','OR','US'),
('Palau','','PW','US'),
('Pennsylvania','Pa','PA','US'),
('Puerto Rico','P.R.','PR','US'),
('Rhode Island','R.I.','RI','US'),
('South Carolina','S.C.','SC','US'),
('South Dakota','S.D.','SD','US'),
('Tennessee','Tenn','TN','US'),
('Texas','Tex','TX','US'),
('Utah','Utah','UT','US'),
('Vermont','Vt','VT','US'),
('Virginia','Va','VA','US'),
('Virgin Islands','V.I.','VI','US'),
('Washington','Wash','WA','US'),
('West Virginia','W.Va.','WV','US'),
('Wisconsin','Wis.','WI','US'),
('Wyoming','Wyo.','WY','US');


/**
 * updating specific fields of property using below table config 
 */
create table product_update_settings (
		id int(11) NOT NULL AUTO_INCREMENT,
		altpartyid int(10) NOT NULL, 
		field varchar(50) NOT NULL,
		update_interval_as_cron varchar(15) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY field_per_partner (field,altpartyid)
);

insert into product_update_settings (altpartyid,field,update_interval_as_cron) values 
	(100,'imagedata','* * * * sun'),
	(100,'pricedata','* * * * *'),
	(100,'bookeddate','* * * * *'),
	(100,'minstay', '* * * * sun'),
	(100,'description', '* * * * sun'),
	(100,'attribute','* * * * sun');

/**
 * either updating or not, the property using below filter table config 
 */
create table product_filter_settings (
		id int(11) NOT NULL AUTO_INCREMENT,
		altpartyid int(10) NOT NULL, 
		filter_by varchar(50) NOT NULL,
		filter_values varchar(500) NOT NULL,
		opr_type varchar(10) not null,
	PRIMARY KEY (id),
	UNIQUE KEY field_per_partner (filter_by,altpartyid,opr_type)
);

insert into product_filter_settings (altpartyid,filter_by,filter_values,opr_type) values
('100','country','US','import'),
('100','country','US','export');


create table product_export_settings (
		id int(11) NOT NULL AUTO_INCREMENT,
		altpartyid int(10) NOT NULL, 
		variable varchar(50) NOT NULL,
		settings_value varchar(500) NOT NULL,
	PRIMARY KEY (id),
	UNIQUE KEY field_per_partner (altpartyid,variable)
);

insert into product_export_settings (altpartyid,variable,settings_value) values
(100,'fileLocation','C:/test'),
(100,'fileName','export'),
(100,'propertiesPerFile','5');



/*
 * URL column is increased with size to hold big lengthy url
 */ 
alter table image modify column url varchar(550) DEFAULT NULL;

/*
 * Add inquire_state column to product to specify way of inquiry (API or e-mail).
 * Could be null in case of inquiry is not supported.
 */
alter table product add column inquire_state VARCHAR(10) NOT NULL DEFAULT "Send e-mail";


alter table property_manager_info add column support_inquire tinyint(4) DEFAULT "1"	;


/*
 * Adding few more product types in attribute table 
 * url : http://confluence.skywidetech.com/display/GALLOPERP/Property+Class+Type+(PCT)
 */
insert into attribute  (List,ID,Name,Type,version) values
('PCT',53,'Vacation rental' ,'H',Default),
('PCT',54,'Economy' ,'H',Default),
('PCT',55,'Midscale' ,'H',Default),
('PCT',56,'Upscale' ,'H',Default),
('PCT',57,'Luxury' ,'H',Default),
('PCT',58,'Union' ,'H',Default),
('PCT',59,'Leisure' ,'H',Default),
('PCT',60,'Wholesale' ,'H',Default),
('PCT',61,'Transient' ,'H',Default);


/*
BP PMS REST End Points DDL starts here
*/

CREATE TABLE `price_ext` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `priceID` int(10) unsigned NOT NULL,
  `rateID` VARCHAR(10)NULL,
  `ClosedOnArrival` int(10) unsigned DEFAULT NULL,
  `ClosedOnDep` int(10) unsigned DEFAULT NULL,
  `Closed` int(10) unsigned DEFAULT NULL,
   `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=79367872 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 12288 kB';

CREATE TABLE `rates` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(10) NOT NULL,
  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=79367872 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 12288 kB';

CREATE TABLE `property_dealrates` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rateId` int(10) NOT NULL,
  `propertyId` int(10) NOT NULL,
  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=79367872 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 12288 kB';

/*
BP PMS REST End Points DDL ends here
*/

/*
 * column added to property manager's support info :- a flag to indicate Amex card support or not 
 */

alter table property_manager_support_cc add column AMEX TINYINT(1) NULL DEFAULT NULL;

/*
Tripvillas Start
*/
/*
 * Adding few more amenities in attribute table 
 * url : http://confluence.skywidetech.com/display/GALLOPERP/Room+Amenity+Type+(RMA)
 */
insert into attribute  (List,ID,Name,Type,version) values
('RMA',73,'Newspaper',Default,Default),
('RMA',58,'King bed',Default,Default),
('RMA',86,'Queen bed',Default,Default),
('RMA',33,'Double bed',Default,Default),
('RMA',203,'Single bed',Default,Default),
('RMA',113,'Twin bed',Default,Default),
('RMA',102,'Sofa Bed',Default,Default);

/*
 * Adding few more recreation in attribute table 
 * url : http://confluence.skywidetech.com/display/GALLOPERP/Recreation+Service+Type+(RST)
 */
insert into attribute  (List,ID,Name,Type,version) values
('RST',161,'Walking track',Default,Default);

/*
Tripvillas End
*/

/* flipkey:- new column "DisplayAdrress" added for showing address or not*/
alter table product add DisplayAddress tinyint(1) default 1 not null;

/* Rate Id from Booking.com */
ALTER TABLE `razor`.`channel_product_map` 
ADD COLUMN `Channel_RateID` VARCHAR(45) NULL DEFAULT NULL AFTER `Channel_RoomID`;

ALTER TABLE `razor`.`party` 
CHANGE COLUMN `EmailAddress` `EmailAddress` VARCHAR(100) CHARACTER SET 'utf8' NULL DEFAULT NULL ;

ALTER TABLE `razor`.`party` 
CHANGE COLUMN `Notes` `Notes` VARCHAR(5000) CHARACTER SET 'utf8' NULL DEFAULT NULL ;


ALTER TABLE `razor`.`reservation` 
CHANGE COLUMN `Notes` `Notes` varchar(3000) CHARACTER SET 'utf8' DEFAULT NULL ;

/*
 * integration-1026-Tripvillas-start
 * 
 * Tripvillas product_rooms table creation for all products.
*/
CREATE TABLE `razor`.`product_rooms` ( `rid` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,

  `product_id` VARCHAR(45) NOT NULL, `room_id` VARCHAR(45) NOT NULL, PRIMARY KEY (`rid`))



ENGINE = InnoDB;

/*
 * Tripvillas increasing character length in product table
 */
ALTER TABLE `razor`.`product` MODIFY COLUMN `AltID` VARCHAR(50) CHARACTER SET utf8 DEFAULT NULL,
 MODIFY COLUMN `Options` VARCHAR(25) CHARACTER SET utf8 DEFAULT NULL;
ALTER TABLE `razor`.`product` MODIFY COLUMN `Physicaladdress` VARCHAR(255) CHARACTER SET utf8 DEFAULT NULL;
ALTER TABLE `razor`.`price` MODIFY COLUMN `AltID` VARCHAR(50) CHARACTER SET utf8 DEFAULT NULL; 

ALTER TABLE razor.product CONVERT TO CHARACTER SET utf8; 

truncate table razor.country_code;


alter table razor.country_code add column phone_code varchar(10);
insert into razor.country_code (country, iso_code2, iso_code3, phone_code) values
('China','CN','CHN','+86'),
('India','IN','IND','+91'),
('United States','US','USA','+1'),
('Indonesia','ID','IDN','+62'),
('Brazil','BR','BRA','+55'),
('Pakistan','PK','PAK','+92'),
('Bangladesh','BD','BGD','+880'),
('Nigeria','NG','NGA','+234'),
('Russia','RU','RUS','+7'),
('Japan','JP','JPN','+81'),
('Mexico','MX','MEX','+52'),
('Philippines','PH','PHL','+63'),
('Vietnam','VN','VNM','+84'),
('Ethiopia','ET','ETH','+251'),
('Egypt','EG','EGY','+20'),
('Germany','DE','DEU','+49'),
('Turkey','TR','TUR','+90'),
('Democratic Republic of the Congo','CD','COD','+243'),
('Iran','IR','IRN','+98'),
('Thailand','TH','THA','+66'),
('France','FR','FRA','+33'),
('United Kingdom','GB','GBR','+44'),
('Italy','IT','ITA','+39'),
('South Africa','ZA','ZAF','+27'),
('South Korea','KR','KOR','+82'),
('Burma (Myanmar)','MM','MMR','+95'),
('Ukraine','UA','UKR','+380'),
('Colombia','CO','COL','+57'),
('Sudan','SD','SDN','+249'),
('Tanzania','TZ','TZA','+255'),
('Argentina','AR','ARG','+54'),
('Spain','ES','ESP','+34'),
('Kenya','KE','KEN','+254'),
('Poland','PL','POL','+48'),
('Morocco','MA','MAR','+212'),
('Algeria','DZ','DZA','+213'),
('Canada','CA','CAN','+1'),
('Uganda','UG','UGA','+256'),
('Peru','PE','PER','+51'),
('Iraq','IQ','IRQ','+964'),
('Saudi Arabia','SA','SAU','+966'),
('Nepal','NP','NPL','+977'),
('Afghanistan','AF','AFG','+93'),
('Uzbekistan','UZ','UZB','+998'),
('Venezuela','VE','VEN','+58'),
('Malaysia','MY','MYS','+60'),
('Ghana','GH','GHA','+233'),
('Yemen','YE','YEM','+967'),
('Taiwan','TW','TWN','+886'),
('North Korea','KP','PRK','+850'),
('Romania','RO','ROU','+40'),
('Mozambique','MZ','MOZ','+258'),
('Sri Lanka','LK','LKA','+94'),
('Australia','AU','AUS','+61'),
('Madagascar','MG','MDG','+261'),
('Ivory Coast','CI','CIV','+225'),
('Syria','SY','SYR','+963'),
('Cameroon','CM','CMR','+237'),
('Netherlands','NL','NLD','+31'),
('Chile','CL','CHL','+56'),
('Burkina Faso','BF','BFA','+226'),
('Kazakhstan','KZ','KAZ','+7'),
('Niger','NE','NER','+227'),
('Ecuador','EC','ECU','+593'),
('Cambodia','KH','KHM','+855'),
('Malawi','MW','MWI','+265'),
('Senegal','SN','SEN','+221'),
('Guatemala','GT','GTM','+502'),
('Angola','AO','AGO','+244'),
('Mali','ML','MLI','+223'),
('Zambia','ZM','ZMB','+260'),
('Cuba','CU','CUB','+53'),
('Zimbabwe','ZW','ZWE','+263'),
('Greece','GR','GRC','+30'),
('Portugal','PT','PRT','+351'),
('Tunisia','TN','TUN','+216'),
('Rwanda','RW','RWA','+250'),
('Belgium','BE','BEL','+32'),
('Chad','TD','TCD','+235'),
('Czech Republic','CZ','CZE','+420'),
('Guinea','GN','GIN','+224'),
('Hungary','HU','HUN','+36'),
('Somalia','SO','SOM','+252'),
('Bolivia','BO','BOL','+591'),
('Dominican Republic','DO','DOM','+1 809'),
('Belarus','BY','BLR','+375'),
('Sweden','SE','SWE','+46'),
('Haiti','HT','HTI','+509'),
('Burundi','BI','BDI','+257'),
('Benin','BJ','BEN','+229'),
('Azerbaijan','AZ','AZE','+994'),
('Austria','AT','AUT','+43'),
('Honduras','HN','HND','+504'),
('Switzerland','CH','CHE','+41'),
('Serbia','RS','SRB','+381'),
('Tajikistan','TJ','TJK','+992'),
('Israel','IL','ISR','+972'),
('Bulgaria','BG','BGR','+359'),
('El Salvador','SV','SLV','+503'),
('Hong Kong','HK','HKG','+852'),
('Paraguay','PY','PRY','+595'),
('Laos','LA','LAO','+856'),
('Sierra Leone','SL','SLE','+232'),
('Jordan','JO','JOR','+962'),
('Libya','LY','LBY','+218'),
('Papua New Guinea','PG','PNG','+675'),
('Togo','TG','TGO','+228'),
('Nicaragua','NI','NIC','+505'),
('Eritrea','ER','ERI','+291'),
('Denmark','DK','DNK','+45'),
('Slovakia','SK','SVK','+421'),
('Kyrgyzstan','KG','KGZ','+996'),
('Finland','FI','FIN','+358'),
('Turkmenistan','TM','TKM','+993'),
('United Arab Emirates','AE','ARE','+971'),
('Norway','NO','NOR','+47'),
('Singapore','SG','SGP','+65'),
('Georgia','GE','GEO','+995'),
('Bosnia and Herzegovina','BA','BIH','+387'),
('Central African Republic','CF','CAF','+236'),
('Croatia','HR','HRV','+385'),
('Moldova','MD','MDA','+373'),
('Costa Rica','CR','CRC','+506'),
('New Zealand','NZ','NZL','+64'),
('Ireland','IE','IRL','+353'),
('Lebanon','LB','LBN','+961'),
('Republic of the Congo','CG','COG','+242'),
('Puerto Rico','PR','PRI','+1'),
('Albania','AL','ALB','+355'),
('Lithuania','LT','LTU','+370'),
('Uruguay','UY','URY','+598'),
('Liberia','LR','LBR','+231'),
('Oman','OM','OMN','+968'),
('Panama','PA','PAN','+507'),
('Mauritania','MR','MRT','+222'),
('Mongolia','MN','MNG','+976'),
('Armenia','AM','ARM','+374'),
('Jamaica','JM','JAM','+1 876'),
('Kuwait','KW','KWT','+965'),
('Latvia','LV','LVA','+371'),
('Lesotho','LS','LSO','+266'),
('Namibia','NA','NAM','+264'),
('Macedonia','MK','MKD','+389'),
('Slovenia','SI','SVN','+386'),
('Botswana','BW','BWA','+267'),
('Gambia','GM','GMB','+220'),
('Guinea-Bissau','GW','GNB','+245'),
('Gabon','GA','GAB','+241'),
('Estonia','EE','EST','+372'),
('Mauritius','MU','MUS','+230'),
('Trinidad and Tobago','TT','TTO','+1 868'),
('Timor-Leste','TL','TLS','+670'),
('Swaziland','SZ','SWZ','+268'),
('Fiji','FJ','FJI','+679'),
('Qatar','QA','QAT','+974'),
('Cyprus','CY','CYP','+357'),
('Guyana','GY','GUY','+592'),
('Comoros','KM','COM','+269'),
('Bahrain','BH','BHR','+973'),
('Bhutan','BT','BTN','+975'),
('Montenegro','ME','MNE','+382'),
('Equatorial Guinea','GQ','GNQ','+240'),
('Solomon Islands','SB','SLB','+677'),
('Macau','MO','MAC','+853'),
('Djibouti','DJ','DJI','+253'),
('Luxembourg','LU','LUX','+352'),
('Suriname','SR','SUR','+597'),
('Cape Verde','CV','CPV','+238'),
('Malta','MT','MLT','+356'),
('Maldives','MV','MDV','+960'),
('Brunei','BN','BRN','+673'),
('Bahamas','BS','BHS','+1 242'),
('Belize','BZ','BLZ','+501'),
('Iceland','IS','IS','+354'),
('French Polynesia','PF','PYF','+689'),
('Barbados','BB','BRB','+1 246'),
('New Caledonia','NC','NCL','+687'),
('Netherlands Antilles','AN','ANT','+599'),
('Mayotte','YT','MYT','+262'),
('Samoa','WS','WSM','+685'),
('Vanuatu','VU','VUT','+678'),
('Sao Tome and Principe','ST','STP','+239'),
('Guam','GU','GUM','+1 671'),
('Saint Lucia','LC','LCA','+1 758'),
('Tonga','TO','TON','+676'),
('Kiribati','KI','KIR','+686'),
('US Virgin Islands','VI','VIR','+1 340'),
('Micronesia','FM','FSM','+691'),
('Saint Vincent and the Grenadines','VC','VCT','+1 784'),
('Aruba','AW','ABW','+297'),
('Grenada','GD','GRD','+1 473'),
('Northern Mariana Islands','MP','MNP','+1 670'),
('Seychelles','SC','SYC','+248'),
('Antigua and Barbuda','AG','ATG','+1 268'),
('Andorra','AD','AND','+376'),
('Isle of Man','IM','IMN','+44'),
('Dominica','DM','DMA','+1 767'),
('Bermuda','BM','BMU','+1 441'),
('American Samoa','AS','ASM','+1 684'),
('Marshall Islands','MH','MHL','+692'),
('Greenland','GL','GRL','+299'),
('Cayman Islands','KY','CYM','+1 345'),
('Faroe Islands','FO','FRO','+298'),
('Saint Kitts and Nevis','KN','KNA','+1 869'),
('Liechtenstein','LI','LIE','+423'),
('Monaco','MC','MCO','+377'),
('San Marino','SM','SMR','+378'),
('Saint Martin','MF','MAF','+1 599'),
('Gibraltar','GI','GIB','+350'),
('British Virgin Islands','VG','VGB','+1 284'),
('Turks and Caicos Islands','TC','TCA','+1 649'),
('Palau','PW','PLW','+680'),
('Wallis and Futuna','WF','WLF','+681'),
('Anguilla','AI','AIA','+1 264'),
('Nauru','NR','NRU','+674'),
('Tuvalu','TV','TUV','+688'),
('Cook Islands','CK','COK','+682'),
('Saint Helena','SH','SHN','+290'),
('Saint Barthelemy','BL','BLM','+590'),
('Saint Pierre and Miquelon','PM','SPM','+508'),
('Montserrat','MS','MSR','+1 664'),
('Falkland Islands','FK','FLK','+500'),
('Tokelau','TK','TKL','+690'),
('Christmas Island','CX','CXR','+61'),
('Niue','NU','NIU','+683'),
('Holy See (Vatican City)','VA','VAT','+39'),
('Cocos (Keeling) Islands','CC','CCK','+61'),
('Pitcairn Islands','PN','PCN','+870'),
('Antarctica','AQ','ATA','+672');

/*
 * integration-1026-Tripvillas-end
*/

-- Add skip_license field to party. Parties with 'true' value will be skipped during the license monthly job
alter table party add column skip_license tinyint(1) not null default '0'
update party set skip_license = 1 where id in ('837', '7905', '61447', '90640', '99039', '99058', '99060', '99062', '99064', '100296', '100293', '102005', '120523', '128700', '128701', '133832',
'138849', '138926', '139079', '144787', '147626', '165663', '11274', '139219', '147768', '150269', '159113', '186873', '189386', '189958', '190379', '191944', '290192', '294110', '294338',
'296095', '296147', '302874', '303914', '307047', '310312', '314370', '314372', '314381', '314534', '314545', '314547', '315850', '325061', '210254', '325369', '321479', '231057', '317832',
'314757', '210270', '324833', '324431', '102017', '254061', '324539', '325252', '205124', '231048', '191908', '231049', '231050', '231051', '231053', '318396')

-- Table that is responsible for convertion from custom currency to USD in case of Bookingpal is funds holder. BP-1190 ticket.
create table if not exists convertion_info (id INT NOT NULL auto_increment, from_currency VARCHAR(255), from_amount DOUBLE, rate DOUBLE,
	convertion_date DATETIME, PRIMARY KEY  USING BTREE (id));
	
-- UI side requirement
ALTER TABLE `razor`.`channel_partner` ADD COLUMN `generate_xml` tinyint(1) DEFAULT '0' AFTER `order`;

-- Streamline PM code is much longer than previous 15 character.
ALTER TABLE `razor`.`party` CHANGE COLUMN `AltID` `AltID` VARCHAR(40) NULL DEFAULT NULL;

-- NET Rate flag is true if net rate and false if retail rate
alter table payment_transaction add column net_rate tinyint(1) not null default 0;

-- BookingPal commission
-- alter table product add column bp_commission double not null default 2;

-- When the transaction is being saved we will need to save the PMS dollar amount of their commission in this column
alter table payment_transaction add column net_rate_pms_commission double;

-- PM commission field regarding Net rates story
alter table property_manager_info add column commission double;

-- Flag describes is property should be calculated as net rate or retail rate
-- alter table product add column net_rate tinyint(1) not null default 0;
alter table property_manager_info add column net_rate tinyint(1) not null default 0;

-- BookingPal commission
alter table property_manager_info add column bp_commission double;

-- Storing all commission values:
-- Total BookingPal Payment
-- Partner Payment
-- PM Payment
-- PMS Payment
-- Credit Card Fee
-- alter table payment_transaction add column bp_commission_value double;
alter table payment_transaction add column partner_commission_value double;
alter table payment_transaction add column pm_commission_value double;

alter table payment_transaction add column channel_partner_commission double;
alter table property_manager_info add column pms_markup double;

--Hotel Combined Changes
ALTER TABLE `razor`.`reservation_ext` 
CHANGE COLUMN `ReservationID` `ReservationID` VARCHAR(50) NULL DEFAULT NULL;
/*
 * HomeAway : resco feed : begin
 * entry into party and partner table.
 */

/* #ID1# */INSERT INTO `razor`.`party` ( `CreatorID`, `LocationID`, `FinanceID`, `JurisdictionID`, `Name`, `State`, `ExtraName`, `TaxNumber`, `PostalAddress`, `PostalCode`, `Country`, `EmailAddress`, `WebAddress`, `DayPhone`, `NightPhone`, `FaxPhone`, `MobilePhone`, `Language`, `Currency`, `Unit`, `FormatDate`, `FormatPhone`, `Latitude`, `Longitude`, `Altitude`, `Rank`, `Notes`) 
VALUES ( null, null, '0', '0', 'ISILink Channel Partner', 'Created', '', '', '', '', 'US', '', '', '', '', '', '', 'EN', 'ZAR', 'EA', 'dd/MM/yyyy', '(###)###-####', null, null, '0', '0', '');

/* #ID2# */INSERT INTO `razor`.`party` ( `CreatorID`, `LocationID`, `FinanceID`, `JurisdictionID`, `Name`, `State`, `ExtraName`, `TaxNumber`, `PostalAddress`, `PostalCode`, `Country`, `EmailAddress`, `WebAddress`, `DayPhone`, `NightPhone`, `FaxPhone`, `MobilePhone`, `Language`, `Currency`, `Unit`, `FormatDate`, `FormatPhone`, `Latitude`, `Longitude`, `Altitude`, `Rank`, `Notes`) 
VALUES ( null, null, '0', '0', 'ISILink Channel Partner - Instant Soft', 'Created', '', '', '', '', 'US', '', '', '', '', '', '', 'EN', 'ZAR', 'EA', 'dd/MM/yyyy', '(###)###-####', null, null, '0', '0', '');

/* #ID3# */INSERT INTO `razor`.`party` ( `CreatorID`, `LocationID`, `FinanceID`, `JurisdictionID`, `Name`, `State`, `ExtraName`, `TaxNumber`, `PostalAddress`, `PostalCode`, `Country`, `EmailAddress`, `WebAddress`, `DayPhone`, `NightPhone`, `FaxPhone`, `MobilePhone`, `Language`, `Currency`, `Unit`, `FormatDate`, `FormatPhone`, `Latitude`, `Longitude`, `Altitude`, `Rank`, `Notes`) 
VALUES ( null, null, '0', '0', 'ISILink Channel Partner - LLC', 'Created', '', '', '', '', 'US', '', '', '', '', '', '', 'EN', 'ZAR', 'EA', 'dd/MM/yyyy', '(###)###-####', null, null, '0', '0', '');



INSERT INTO `razor`.`partner` ( `PartyID`, `PartyName`, `State`, `BookEmailAddress`, `BookWebAddress`,`Apikey`, `Currency`, `AlertWait`, `PriceWait`, `ProductWait`, `ScheduleWait`, `SpecialWait`, `Subscription`, `Transaction`, `BookOffline`, `SupportsCreditCard`, `SendConfirmationEmails`, `Abbrevation`, `version`) 
VALUES (  #ID1#, 'ISILink Channel Partner', 'Created', NULL, NULL,'DV12,2144', 'USD', '0', '0', '0', '0', '0', '0', '0', 0, '0', '0', null, NULL);

INSERT INTO `razor`.`partner` ( `PartyID`, `PartyName`, `State`, `BookEmailAddress`, `BookWebAddress`,`Apikey`, `Currency`, `AlertWait`, `PriceWait`, `ProductWait`, `ScheduleWait`, `SpecialWait`, `Subscription`, `Transaction`, `BookOffline`, `SupportsCreditCard`, `SendConfirmationEmails`, `Abbrevation`, `version`) 
VALUES (  #ID2#, 'Palmetto Vacation Rentals LLC', 'Created', NULL, NULL,'2144', 'USD', '0', '0', '0', '0', '0', '0', '0', 0, '0', '0', null, NULL);

INSERT INTO `razor`.`partner` ( `PartyID`, `PartyName`, `State`, `BookEmailAddress`, `BookWebAddress`,`Apikey`, `Currency`, `AlertWait`, `PriceWait`, `ProductWait`, `ScheduleWait`, `SpecialWait`, `Subscription`, `Transaction`, `BookOffline`, `SupportsCreditCard`, `SendConfirmationEmails`, `Abbrevation`, `version`) 
VALUES (  #ID3#, 'Instant Software - V12.NET', 'Created', NULL, NULL,'DV12', 'USD', '0', '0', '0', '0', '0', '0', '0', 0, '0', '0', null, NULL);


/**
 * #ID1#, #ID2#, #ID3# are ids of entry in party table.
 * This ids are mapped/referenced in partner table
 * ISILink Channel Partner combines both [DV12,2144]
 * [DV12]Instant Software - V12.NET
 * [2144]Palmetto Vacation Rentals LLC
 * HomeAway :resco feed : end 
 */
-- Fields for admin page.
ALTER TABLE `razor`.`channel_partner` ADD COLUMN `send_email` tinyint(1) DEFAULT 1 AFTER `generate_xml`; 
ALTER TABLE `razor`.`channel_partner` ADD COLUMN `ftp_password` varchar(255) AFTER `send_email`;

--Add Floor and Space values for product entity
ALTER TABLE `product`
	ADD COLUMN `Floor` INT(10) UNSIGNED NULL DEFAULT '1' AFTER `Toilet`,
	ADD COLUMN `Space` VARCHAR(50) NULL AFTER `Floor`;
-- PM addittional commission
alter table property_manager_info add column additional_commission double(7,2) NULL DEFAULT NULL;

-- PM addittional commission percent value
alter table payment_transaction add column additional_commission_value DOUBLE NULL DEFAULT NULL after channel_partner_commission;

-- Pending payment Net Rate fields
ALTER TABLE `pending_transaction`
 ADD COLUMN `credit_card_fee` DOUBLE NULL DEFAULT NULL AFTER `autopay`,
 ADD COLUMN `net_rate` TINYINT(1) NULL DEFAULT NULL AFTER `credit_card_fee`,
 ADD COLUMN `pm_commission_value` DOUBLE NULL DEFAULT NULL AFTER `net_rate`,
 ADD COLUMN `pms_payment` DOUBLE NULL DEFAULT NULL AFTER `pm_commission_value`,
 ADD COLUMN `additional_commission_value` DOUBLE NULL DEFAULT NULL AFTER `pms_payment`;

CREATE TABLE IF NOT EXISTS property_manager_configuration (id INT NOT NULL auto_increment, validation INT default 0,
	initialization INT default 0, price INT default 0, availability INT default 0, payment INT default 0, partner_reservation INT default 0, 
	verify_and_cancel INT default 0, payment_transaction INT default 0,	format INT default 0, send_admin_email INT default 0, PRIMARY KEY  USING BTREE (id));
	
-- inserting default configuration
INSERT INTO property_manager_configuration(validation, initialization, price, availability, payment, partner_reservation, verify_and_cancel,
	payment_transaction, format, send_admin_email) values(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

ALTER TABLE property_manager_info ADD COLUMN configuration_id INT DEFAULT 1;

-- Remove NULL values from price minimum
ALTER TABLE `price`
	CHANGE COLUMN `Minimum` `Minimum` DOUBLE NOT NULL DEFAULT '0' AFTER `Value`;
	
	
CREATE TABLE `fee` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `AltID` varchar(100) DEFAULT NULL,
  `EntityType` tinyint(1) DEFAULT '1',
  `ProductID` int(11) DEFAULT NULL,
  `PartyID` int(11) DEFAULT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `State` tinyint(1) DEFAULT '1',
  `OptionValue` varchar(100) DEFAULT NULL,
  `TaxType` tinyint(1) DEFAULT '1',
  `FromDate` date DEFAULT NULL,
  `ToDate` date DEFAULT NULL,
  `Unit` tinyint(1) DEFAULT '1',
  `Value` double DEFAULT '0',
  `ValueType` tinyint(1) DEFAULT '1',
  `Currency` varchar(3) DEFAULT NULL,
  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
);
	

ALTER TABLE `razor`.`tax` 
ADD COLUMN `MandatoryType` VARCHAR(20) NULL DEFAULT 'MandatoryTax' AFTER `Amount`,
ADD COLUMN `Weight` TINYINT(2) NULL DEFAULT 0 AFTER `MandatoryType`,
ADD COLUMN `AltID` VARCHAR(100) NULL DEFAULT NULL AFTER `Weight`,
ADD COLUMN `OptionValue` VARCHAR(100) NULL DEFAULT NULL AFTER `AltID`;

-- this is only for people who create previous version of fee table which have this column. 
ALTER TABLE `razor`.`fee`  DROP COLUMN `PetFee`;

ALTER TABLE `tax`
	ADD COLUMN `ProductID` INT(10) UNSIGNED NULL DEFAULT NULL AFTER `PartyID`;

ALTER TABLE `razor`.`fee` ADD COLUMN `Weight` TINYINT(2) NULL DEFAULT 0 AFTER `ValueType`;

ALTER TABLE `razor`.`tax` DROP COLUMN `Weight`;

