/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.trial.test;

import java.io.BufferedWriter;
import java.io.FileWriter;

import net.cbtltd.server.BCrypt;
import net.cbtltd.shared.Country;
import net.cbtltd.shared.Currency;
import net.cbtltd.shared.Language;
import net.cbtltd.shared.Party;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Text;
import net.cbtltd.shared.Value;

/**
 * The Class NavisTest is to create a test database for NAVIS.
 */
public class NavisTest {

	private static final String password = BCrypt.hashpw("password", BCrypt.gensalt());

	/**
	 * Final counts.
	 *
	 * @return the string
	 */
	private static final String finalCounts() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n/* Asset Records ").append(assetid).append(" */");
		sb.append("\n/* Audit Records ").append(auditid).append(" */");
		sb.append("\n/* Contract Records ").append(contractid).append(" */");
		sb.append("\n/* Party Records ").append(partyid).append(" */");
		sb.append("\n/* Price Records ").append(priceid).append(" */");
		sb.append("\n/* Product Records ").append(productid).append(" */");
		sb.append("\n/* Relation Records ").append(relationid).append(" */");
		sb.append("\n/* Text Records ").append(textcounter).append(" */");
		sb.append("\n/* Yield Records ").append(yieldid).append(" */");
		sb.append("\n/* Workflow Records ").append(workflowid).append(" */");
		return sb.toString();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		initializeRelation();
		initializeAsset();
		initializeAudit();
		initializeContract();
		initializeParty();
		initializePrice();
		initializeText();
		initializeYield();
		initializeWorkflow();
		initializeProduct();
		addProducts();
		finalizeProduct();
		finalizeWorkflow();
		finalizeYield();
		finalizeText();
		finalizePrice();
		finalizeParty();
		finalizeContract();
		finalizeAudit();
		finalizeAsset();
		finalizeRelation();
		try {	
			FileWriter writer = new FileWriter("c:\\demo06.sql");
			BufferedWriter out = new BufferedWriter(writer);
			out.write(initializeDB());
			out.close();
			writer = new FileWriter("c:\\asset.sql");
			out = new BufferedWriter(writer);
			out.write(assetDB.toString());
			out.close();
			writer = new FileWriter("c:\\audit.sql");
			out = new BufferedWriter(writer);
			out.write(auditDB.toString());
			out.close();
			writer = new FileWriter("c:\\contract.sql");
			out = new BufferedWriter(writer);
			out.write(contractDB.toString());
			out.close();
			writer = new FileWriter("c:\\party.sql");
			out = new BufferedWriter(writer);
			out.write(partyDB.toString());
			out.close();
			writer = new FileWriter("c:\\product.sql");
			out = new BufferedWriter(writer);
			out.write(productDB.toString());
			out.close();
			writer = new FileWriter("c:\\price.sql");
			out = new BufferedWriter(writer);
			out.write(priceDB.toString());
			out.close();
			writer = new FileWriter("c:\\relation.sql");
			out = new BufferedWriter(writer);
			out.write(relationDB.toString());
			out.close();
			writer = new FileWriter("c:\\text.sql");
			out = new BufferedWriter(writer);
			out.write(textDB.toString());
			out.close();
			writer = new FileWriter("c:\\yield.sql");
			out = new BufferedWriter(writer);
			out.write(yieldDB.toString());
			out.close();
			writer = new FileWriter("c:\\workflow.sql");
			out = new BufferedWriter(writer);
			out.write(workflowDB.toString());
			out.write(finalizeDB());
			out.write(finalCounts());
			out.close();
		} catch(Throwable x) {System.out.println("Error " + x.getMessage());}
	}

	/**
	 * Gets a random number in the specified range.
	 *
	 * @param min the minimum value.
	 * @param max the maximum value.
	 * @return the random number.
	 */
	private static final double getRandom(double min, double max) {
		return min + (Math.random() * (max - min));
	}

	/**
	 * Initialize the database.
	 *
	 * @return the string.
	 */
	private static final String initializeDB() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n-- MySQL Administrator dump 1.4");
		sb.append("\n--");
//		sb.append("\n-- Host: localhost    Database: demo");
		sb.append("\n-- ------------------------------------------------------");
		sb.append("\n-- Server version	5.5.20-log");
		sb.append("\n");
		sb.append("\n/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;");
		sb.append("\n/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;");
		sb.append("\n/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;");
		sb.append("\n/*!40101 SET NAMES utf8 */;");
		sb.append("\n/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;");
		sb.append("\n/*!40103 SET TIME_ZONE='+00:00' */;");
		sb.append("\n/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;");
		sb.append("\n/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;");
		sb.append("\n/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;");
		sb.append("\n/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;");
		sb.append("\n\nCREATE DATABASE IF NOT EXISTS `demo` /*!40100 DEFAULT CHARACTER SET latin1 */;");
		sb.append("\nUSE `demo`;");
		return sb.toString();
	}

	/**
	 * Finalize the database.
	 *
	 * @return the string.
	 */
	private static final String finalizeDB() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;");
		sb.append("\n");
		sb.append("\n/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;");
		sb.append("\n/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;");
		sb.append("\n/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;");
		sb.append("\n/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;");
		sb.append("\n/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;");
		sb.append("\n/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;");
		sb.append("\n/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;");
		return sb.toString();
	}

	/** The relationid counter. */
	private static int relationid = 1728098;

	/** The relation string builder. */
	private static StringBuilder relationDB = new StringBuilder();

	/**
	 * Initialize relation.
	 */
	private static final void initializeRelation() {
//		relationDB.append("\n--");
//		relationDB.append("\n-- Table structure for table `relation`");
//		relationDB.append("\n--");
//		relationDB.append("\n");
//		relationDB.append("\nDROP TABLE IF EXISTS `relation`;");
//		relationDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		relationDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		relationDB.append("\nCREATE TABLE `relation` (");
//		relationDB.append("\n  `Index` int(10) unsigned NOT NULL AUTO_INCREMENT,");
//		relationDB.append("\n  `Link` varchar(50) NOT NULL,");
//		relationDB.append("\n  `HeadID` varchar(50) NOT NULL,");
//		relationDB.append("\n  `LineID` varchar(50) NOT NULL,");
//		relationDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		relationDB.append("\n  PRIMARY KEY (`Index`),");
//		relationDB.append("\n  KEY `HeadID` (`HeadID`,`Link`,`Index`),");
//		relationDB.append("\n  KEY `LineID` (`LineID`,`Link`,`Index`),");
//		relationDB.append("\n  KEY `Link` (`Link`,`Index`)");
//		relationDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;");
//		relationDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		relationDB.append("\n");
		relationDB.append("\n--");
		relationDB.append("\n-- Dumping data for table `relation`");
		relationDB.append("\n--");
		relationDB.append("\n");
		relationDB.append("\nLOCK TABLES `relation` WRITE;");
		relationDB.append("\n/*!40000 ALTER TABLE `relation` DISABLE KEYS */;");
		relationDB.append("\nINSERT INTO `relation` (`Index`, Link, HeadID, LineID) VALUES ");
	}

	/**
	 * Adds the relation.
	 *
	 * @param link the relation name.
	 * @param headid the head id
	 * @param lineid the line id
	 */
	private static void addRelation(String link, String headid, String lineid) {
		relationid++;
		if (relationid % 100 == 0) {
			int length = relationDB.length();
			relationDB.replace(length-1, length, ";");
			relationDB.append("\nINSERT INTO `relation` (`Index`, Link, HeadID, LineID) VALUES ");
		}
		relationDB.append("\n(" + relationid + ",'" + link + "','" + headid + "','" + lineid + "'),");
	}

	/**
	 * Finalize relation.
	 */
	private static final void finalizeRelation() {
		int length = relationDB.length();
		relationDB.replace(length-1, length, ";");
		relationDB.append("\n/*!40000 ALTER TABLE `relation` ENABLE KEYS */;");
		relationDB.append("\nUNLOCK TABLES;");
		relationDB.append("\n");
	}

	/** The assetid counter. */
	private static int assetid = 106807;

	/** The asset string builder. */
	private static StringBuilder assetDB = new StringBuilder();

	/**
	 * Initialize asset.
	 */
	private static final void initializeAsset() {
//		assetDB.append("\n--");
//		assetDB.append("\n-- Table structure for table `asset`");
//		assetDB.append("\n--");
//		assetDB.append("\n");
//		assetDB.append("\nDROP TABLE IF EXISTS `asset`;");
//		assetDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		assetDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		assetDB.append("\nCREATE TABLE `asset` (");
//		assetDB.append("\n  `ID` int(10) NOT NULL AUTO_INCREMENT,");
//		assetDB.append("\n  `OwnerID` int(10) DEFAULT NULL,");
//		assetDB.append("\n  `SupplierID` int(10) DEFAULT NULL,");
//		assetDB.append("\n  `LocationID` int(10) DEFAULT NULL,");
//		assetDB.append("\n  `DepreciationID` int(10) unsigned DEFAULT NULL,");
//		assetDB.append("\n  `Name` varchar(50) DEFAULT NULL,");
//		assetDB.append("\n  `State` varchar(15) DEFAULT NULL,");
//		assetDB.append("\n  `Type` varchar(30) DEFAULT NULL,");
//		assetDB.append("\n  `Code` varchar(50) DEFAULT NULL,");
//		assetDB.append("\n  `Place` varchar(50) DEFAULT NULL,");
//		assetDB.append("\n  `DateAcquired` datetime DEFAULT NULL,");
//		assetDB.append("\n  `DateDisposed` datetime DEFAULT NULL,");
//		assetDB.append("\n  `Currency` varchar(3) DEFAULT NULL,");
//		assetDB.append("\n  `Unit` varchar(3) DEFAULT NULL,");
//		assetDB.append("\n  `Latitude` double DEFAULT NULL,");
//		assetDB.append("\n  `Longitude` double DEFAULT NULL,");
//		assetDB.append("\n  `Altitude` double DEFAULT '0',");
//		assetDB.append("\n  `Notes` varchar(5000) DEFAULT NULL,");
//		assetDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		assetDB.append("\n  `Quantity` int(10) unsigned DEFAULT '1',");
//		assetDB.append("\n  `Cost` double DEFAULT '0',");
//		assetDB.append("\n  `Price` double DEFAULT '0',");
//		assetDB.append("\n  `ParentID` int(10) unsigned DEFAULT NULL,");
//		assetDB.append("\n  PRIMARY KEY (`ID`) USING BTREE,");
//		assetDB.append("\n  KEY `Name` (`Name`,`OwnerID`) USING BTREE,");
//		assetDB.append("\n  KEY `DepreciationID` (`DepreciationID`,`ID`) USING BTREE,");
//		assetDB.append("\n  KEY `SerialNumber` (`Code`,`ID`) USING BTREE");
//		assetDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
//		assetDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		assetDB.append("\n");
		assetDB.append("\n--");
		assetDB.append("\n-- Dumping data for table `asset`");
		assetDB.append("\n--");
		assetDB.append("\n");
		assetDB.append("\nLOCK TABLES `asset` WRITE;");
		assetDB.append("\n/*!40000 ALTER TABLE `asset` DISABLE KEYS */;");
		assetDB.append("\nINSERT INTO `asset` (ID, OwnerID, SupplierID, Name, State, Type, Code, Place, DateAcquired, DateDisposed, Currency, Unit, Notes, Quantity, Cost, Price, ParentID) VALUES ");
	}

	/**
	 * Adds an asset using the specified parameters.
	 *
	 * @param ownerid the owner id
	 * @param supplierid the supplier id
	 * @param productid the product id
	 * @param name the name
	 * @param type the type
	 * @param code the code
	 * @param place the place
	 * @param notes the notes
	 * @param quantity the quantity
	 * @param cost the cost
	 * @param price the price
	 */
	private static final void addAsset(int ownerid, int supplierid, int productid, String name, String type, String code, String place, String notes, int quantity, int cost, int price) {
		assetid++;
		if (assetid % 100 == 0) {
			int length = assetDB.length();
			assetDB.replace(length-1, length, ";");
			assetDB.append("\nINSERT INTO `asset` (ID, OwnerID, SupplierID, Name, State, Type, Code, Place, DateAcquired, DateDisposed, Currency, Unit, Notes, Quantity, Cost, Price, ParentID) VALUES ");
		}
		assetDB.append("\n(" + assetid + "," + ownerid + "," + supplierid + ",'" + name + "','Created','" + type + "','" + code + "', '" + place + "', '2010-09-01 00:00:00', '2011-11-25 00:00:00', 'USD', 'EA','" + notes + "'," + quantity + ", " + cost + ", " + price + ", " + productid + "),");
	}

	/**
	 * Finalize asset.
	 */
	private static final void finalizeAsset() {
		int length = assetDB.length();
		assetDB.replace(length-1, length, ";");
		assetDB.append("\n/*!40000 ALTER TABLE `asset` ENABLE KEYS */;");
		assetDB.append("\nUNLOCK TABLES;");
		assetDB.append("\n");
	}


	/** The auditid. */
	private static int auditid = 142409;

	/** The audit string builder. */
	private static StringBuilder auditDB = new StringBuilder();

	/**
	 * Initialize audit.
	 */
	private static final void initializeAudit() {
//		auditDB.append("\n--");
//		auditDB.append("\n-- Table structure for table `audit`");
//		auditDB.append("\n--");
//		auditDB.append("\n");
//		auditDB.append("\nDROP TABLE IF EXISTS `audit`;");
//		auditDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		auditDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		auditDB.append("\nCREATE TABLE `audit` (");
//		auditDB.append("\n  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,");
//		auditDB.append("\n  `ProductID` int(10) unsigned DEFAULT NULL,");
//		auditDB.append("\n  `Name` varchar(50) DEFAULT NULL,");
//		auditDB.append("\n  `State` varchar(15) DEFAULT NULL,");
//		auditDB.append("\n  `Notes` varchar(255) DEFAULT NULL,");
//		auditDB.append("\n  `Date` date DEFAULT NULL,");
//		auditDB.append("\n  `Rating` int(10) unsigned DEFAULT '5',");
//		auditDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		auditDB.append("\n  PRIMARY KEY (`ID`)");
//		auditDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
//		auditDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		auditDB.append("\n");
		auditDB.append("\n--");
		auditDB.append("\n-- Dumping data for table `audit`");
		auditDB.append("\n--");
		auditDB.append("\n");
		auditDB.append("\nLOCK TABLES `audit` WRITE;");
		auditDB.append("\n/*!40000 ALTER TABLE `audit` DISABLE KEYS */;");
		auditDB.append("\nINSERT INTO `audit` (ID, ProductID, Name, State, Notes, Date, Rating) VALUES ");
	}

	/**
	 * Adds an audit using the specified parameters.
	 *
	 * @param productid the productid
	 * @param name the name
	 * @param notes the notes
	 * @param date the date
	 * @param rating the rating
	 */
	private static final void addAudit(int productid, String name, String notes, String date, int rating) {
		auditid++;
		if (auditid % 100 == 0) {
			int length = auditDB.length();
			auditDB.replace(length-1, length, ";");
			auditDB.append("\nINSERT INTO `audit` (ID, ProductID, Name, State, Notes, Date, Rating) VALUES ");
		}
		auditDB.append("\n(" + auditid + "," + productid + ",'" + name + "','Created','" + notes + "','" + date + "', " + rating + "),");
	}

	/**
	 * Finalize audit.
	 */
	private static final void finalizeAudit() {
		int length = auditDB.length();
		auditDB.replace(length-1, length, ";");
		auditDB.append("\n/*!40000 ALTER TABLE `audit` ENABLE KEYS */;");
		auditDB.append("\nUNLOCK TABLES;");
		auditDB.append("\n");
	}

	/** The contractid. */
	private static int contractid = 5760;

	/** The contract string builder. */
	private static StringBuilder contractDB = new StringBuilder();

	/**
	 * Initialize contract.
	 */
	private static final void initializeContract() {
//		contractDB.append("\n--");
//		contractDB.append("\n-- Table structure for table `contract`");
//		contractDB.append("\n--");
//		contractDB.append("\n");
//		contractDB.append("\nDROP TABLE IF EXISTS `contract`;");
//		contractDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		contractDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		contractDB.append("\nCREATE TABLE `contract` (");
//		contractDB.append("\n  `ID` int(11) NOT NULL AUTO_INCREMENT,");
//		contractDB.append("\n  `OrganizationID` int(10) unsigned DEFAULT '0',");
//		contractDB.append("\n  `ActorID` int(10) unsigned DEFAULT '2',");
//		contractDB.append("\n  `PartyID` int(10) unsigned DEFAULT '0',");
//		contractDB.append("\n  `Name` varchar(15) DEFAULT NULL,");
//		contractDB.append("\n  `State` varchar(15) DEFAULT NULL,");
//		contractDB.append("\n  `Process` varchar(15) DEFAULT NULL,");
//		contractDB.append("\n  `Discount` int(10) unsigned DEFAULT NULL,");
//		contractDB.append("\n  `CreditTerm` varchar(15) DEFAULT NULL,");
//		contractDB.append("\n  `CreditLimit` double DEFAULT NULL,");
//		contractDB.append("\n  `Target` double DEFAULT NULL,");
//		contractDB.append("\n  `Currency` varchar(3) DEFAULT NULL,");
//		contractDB.append("\n  `Date` datetime DEFAULT NULL,");
//		contractDB.append("\n  `DueDate` datetime DEFAULT NULL,");
//		contractDB.append("\n  `DoneDate` datetime DEFAULT NULL,");
//		contractDB.append("\n  `Notes` varchar(1000) DEFAULT NULL,");
//		contractDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		contractDB.append("\n  PRIMARY KEY (`ID`),");
//		contractDB.append("\n  KEY `Type` (`Name`,`Date`) USING BTREE");
//		contractDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=5760 DEFAULT CHARSET=latin1;");
//		contractDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		contractDB.append("\n");
		contractDB.append("\n--");
		contractDB.append("\n-- Dumping data for table `contract`");
		contractDB.append("\n--");
		contractDB.append("\n");
		contractDB.append("\nLOCK TABLES `contract` WRITE;");
		contractDB.append("\n/*!40000 ALTER TABLE `contract` DISABLE KEYS */;");
		contractDB.append("\nINSERT INTO `contract` (`ID`,`OrganizationID`,`ActorID`,`PartyID`,`Name`,`State`,`Process`,`Discount`,`CreditTerm`,`CreditLimit`,`Target`,`Currency`,`Date`,`DueDate`,`DoneDate`,`Notes`) VALUES ");
	}

	/**
	 * Adds a contract using the specified parameters.
	 *
	 * @param organizationid the organizationid
	 * @param agentid the agentid
	 * @param organizationname the organizationname
	 * @param agentname the agentname
	 * @param discount the discount
	 */
	private static final void addContract(int organizationid, int agentid, String organizationname, String agentname, int discount) {
		contractid++;
		if (contractid % 100 == 0) {
			int length = contractDB.length();
			contractDB.replace(length-1, length, ";");
			contractDB.append("\nINSERT INTO `contract` (`ID`,`OrganizationID`,`ActorID`,`PartyID`,`Name`,`State`,`Process`,`Discount`,`CreditTerm`,`CreditLimit`,`Target`,`Currency`,`Date`,`DueDate`,`DoneDate`,`Notes`) VALUES ");
		}
		String notes = "Contract between " + organizationname + " and " + agentname;
		contractDB.append("\n(" + contractid + "," + organizationid + "," + organizationid + "," + agentid + ",'0','Created','Contract'," + discount + ",'None',0,0,'USD','2011-01-01 00:00:00','2014-01-01 00:00:00',NULL,'" + notes + "'),");
	}

	/**
	 * Finalize contract.
	 */
	private static final void finalizeContract() {
		int length = contractDB.length();
		contractDB.replace(length-1, length, ";");
		contractDB.append("\n/*!40000 ALTER TABLE `contract` ENABLE KEYS */;");
		contractDB.append("\nUNLOCK TABLES;");
		contractDB.append("\n");
	}

	/** The partyid counter. */
	private static int partyid = 143759;

	/** The party string builder. */
	private static StringBuilder partyDB = new StringBuilder();

	/**
	 * Initialize party.
	 */
	private static final void initializeParty() {
//		partyDB.append("\n--");
//		partyDB.append("\n-- Table structure for table `party`");
//		partyDB.append("\n--");
//		partyDB.append("\n");
//		partyDB.append("\nDROP TABLE IF EXISTS `party`;");
//		partyDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		partyDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		partyDB.append("\nCREATE TABLE `party` (");
//		partyDB.append("\n  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,");
//		partyDB.append("\n  `EmployerID` int(10) unsigned DEFAULT NULL,");
//		partyDB.append("\n  `CreatorID` int(10) unsigned DEFAULT NULL,");
//		partyDB.append("\n  `LocationID` int(10) unsigned DEFAULT NULL,");
//		partyDB.append("\n  `FinanceID` int(10) unsigned DEFAULT NULL,");
//		partyDB.append("\n  `JurisdictionID` int(10) unsigned DEFAULT NULL,");
//		partyDB.append("\n  `Name` varchar(50) DEFAULT NULL,");
//		partyDB.append("\n  `State` varchar(15) DEFAULT NULL,");
//		partyDB.append("\n  `ExtraName` varchar(50) DEFAULT NULL,");
//		partyDB.append("\n  `IdentityNumber` varchar(20) DEFAULT NULL,");
//		partyDB.append("\n  `TaxNumber` varchar(20) DEFAULT NULL,");
//		partyDB.append("\n  `PostalAddress` varchar(100) DEFAULT NULL,");
//		partyDB.append("\n  `PostalCode` varchar(10) DEFAULT NULL,");
//		partyDB.append("\n  `Country` varchar(3) DEFAULT 'ZA',");
//		partyDB.append("\n  `EmailAddress` varchar(100) DEFAULT NULL,");
//		partyDB.append("\n  `WebAddress` varchar(100) DEFAULT NULL,");
//		partyDB.append("\n  `DayPhone` varchar(20) DEFAULT NULL,");
//		partyDB.append("\n  `NightPhone` varchar(20) DEFAULT NULL,");
//		partyDB.append("\n  `FaxPhone` varchar(20) DEFAULT NULL,");
//		partyDB.append("\n  `MobilePhone` varchar(30) DEFAULT NULL,");
//		partyDB.append("\n  `Password` varchar(200) DEFAULT NULL,");
//		partyDB.append("\n  `Birthdate` datetime DEFAULT NULL,");
//		partyDB.append("\n  `Language` varchar(2) DEFAULT 'EN',");
//		partyDB.append("\n  `Currency` varchar(3) DEFAULT NULL,");
//		partyDB.append("\n  `Unit` varchar(3) DEFAULT NULL,");
//		partyDB.append("\n  `Latitude` double DEFAULT NULL,");
//		partyDB.append("\n  `Longitude` double DEFAULT NULL,");
//		partyDB.append("\n  `Altitude` double DEFAULT '0',");
//		partyDB.append("\n  `Rank` int(10) unsigned DEFAULT '0',");
//		partyDB.append("\n  `Notes` varchar(5000) DEFAULT NULL,");
//		partyDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		partyDB.append("\n  PRIMARY KEY (`ID`) USING BTREE,");
//		partyDB.append("\n  KEY `EmailAddress` (`EmailAddress`) USING BTREE,");
//		partyDB.append("\n  KEY `Name` (`Name`,`ID`) USING BTREE");
//		partyDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 15360 kB';");
//		partyDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		partyDB.append("\n");
		partyDB.append("\n--");
		partyDB.append("\n-- Dumping data for table `party`");
		partyDB.append("\n--");
		partyDB.append("\n");
		partyDB.append("\nLOCK TABLES `party` WRITE;");
		partyDB.append("\n/*!40000 ALTER TABLE `party` DISABLE KEYS */;");
		partyDB.append("\nINSERT INTO `party` (ID, EmployerID, CreatorID, LocationID, FinanceID, JurisdictionID, Name, State, ExtraName, IdentityNumber, TaxNumber, PostalAddress, PostalCode, Country, EmailAddress, WebAddress, DayPhone, NightPhone, FaxPhone, MobilePhone, Password, Birthdate, Language, Currency, Unit, Latitude, Longitude, Altitude, Rank, Notes) VALUES ");
//		partyDB.append("\n(1,4,4,58482,NULL,3418,'Administrator','Created',NULL,NULL,NULL,NULL,'','ZA','a@b.com',NULL,NULL,NULL,NULL,NULL,'$2a$10$QQYV2.mztpIYLmoXyPjdDOMz4bFl.hOGvQXpQVnKlA.4pZfIL7XZa',NULL,'EN','USD','EA',-33.92889,18.41722,0,0,NULL),");
//		partyDB.append("\n(2,NULL,4,58482,NULL,3418,'Unallocated','Created',NULL,NULL,NULL,NULL,'','ZA','unallocated@razor-cloud.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'EN','USD','EA',NULL,NULL,0,0,NULL),");
//		partyDB.append("\n(3,NULL,4,58615,NULL,3418,'SARS - PAYE/SDL/UIF','Created','',NULL,'','','','ZA','info1@sars.gov.za',NULL,'',NULL,'','',NULL,NULL,'EN','USD','EA',-25.725,28.18472,0,0,NULL),");
//		partyDB.append("\n(4,4,4,0,NULL,3418,'CBT Ltd','Created','','05245957','','Abacus House\n367 Blandford Road\nBeckenham Kent UK\nBR3 4NW','BR3 4NW','ZA','info@razor-cloud.com','www.razor-cloud.com/service','+27 21 438 4402',NULL,'','','$2a$10$.PYqZHEx/4AUoFJdimnlZexTZxvTFuGzke2vcsRxq8ifsSLomMtPm',NULL,'EN','USD','EA',NULL,NULL,0,0,NULL),");
//		partyDB.append("\n(5,5,4,0,NULL,3418,'Nox Rentals','Created','Richard Marshall','2002/011707/23','4310227717','P.O.Box 32452\nCamps Bay\nCape Town','8040','ZA','info@noxrentals.co.za','www.noxrentals.co.za','+27 21 438 6440',NULL,'+27 21 438 2564','+27 84 804 9902','$2a$10$YIE9Gek1UW0uDJy9ud5E5.uGja3KSLUFhczVFmoN7x7z/vZLfY.Oq','2002-02-07 00:00:00','EN','USD','EA',-33.963437184398,18.377423286438,0,0,NULL),");
//		partyDB.append("\n(6,5,4,58482,NULL,3418,'Marshall, Richard','Created','Richard','7705245234080','0607/600/14/5P','703 Westridge\n93 Beach Rd\nMouille Point\nSea Point\nCape Town','8005','ZA','richard@noxrentals.co.za','www.noxrentals.co.za','021 438 6440',NULL,'','082 886 1706','$2a$10$MtSHCqk8RDMXf2mUXj5EY.0i53ZbGEv1Pq9aY3Z4pEt/2WTrSXXPe','1977-05-24 00:00:00','EN','USD','EA',-33.92889,18.41722,0,0,NULL),");
//		addRelation(Relation.PARTY_ROLE, "6", "2");
	}

	/**
	 * Adds a party using the specified parameters.
	 *
	 * @param organizationid the organizationid
	 * @param locationid the locationid
	 * @param name the name
	 * @param type the type
	 * @param postcode the postcode
	 * @param country the country
	 * @param currency the currency
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distance the distance
	 * @return the int
	 */
	private static final int addParty(int organizationid, int locationid, String name, Party.Type type, String postcode, String country, String currency, double latitude, double longitude, double distance) {
		partyid++;
		if (partyid % 100 == 0) {
			int length = partyDB.length();
			partyDB.replace(length-1, length, ";");
			partyDB.append("\nINSERT INTO `party` (ID, EmployerID, CreatorID, LocationID, FinanceID, JurisdictionID, Name, State, ExtraName, IdentityNumber, TaxNumber, PostalAddress, PostalCode, Country, EmailAddress, WebAddress, DayPhone, NightPhone, FaxPhone, MobilePhone, Password, Birthdate, Language, Currency, Unit, Latitude, Longitude, Altitude, Rank, Notes) VALUES ");
		}
		String email = String.valueOf(partyid) + "@navis.com";
		double radius = distance / 140.0;
		partyDB.append("\n(" + partyid + "," + partyid + ",5," + locationid + ",NULL,3418,'" + name + "','Created','" + Data.getRandomString(Data.MALE_NAMES) + "','" + Data.getRandomIndex(10000000) + "','" + Data.getRandomIndex(90000000) + "','','" + postcode + "','" + country +"','" + email + "',NULL,NULL,NULL,NULL,NULL,'" + password + "',NULL,'EN','" + currency + "','EA'," + (latitude + getRandom(-radius, radius)) + "," + (longitude + getRandom(-radius, radius)) + ",0,0,NULL),");
		if (organizationid > 0) {addRelation(Relation.ORG_PARTY_ + type.name(), String.valueOf(organizationid), String.valueOf(partyid));}
//		addRelation(Relation.PARTY_TYPE, String.valueOf(partyid), type.name());
		addRelation(Relation.ORGANIZATION_CURRENCY, String.valueOf(partyid), Currency.Code.USD.name());
		addRelation(Relation.ORGANIZATION_LANGUAGE, String.valueOf(partyid), Language.EN);
		return partyid;
	}

	/**
	 * Finalize party.
	 */
	private static final void finalizeParty() {
		int length = partyDB.length();
		partyDB.replace(length-1, length, ";");
		partyDB.append("\n/*!40000 ALTER TABLE `party` ENABLE KEYS */;");
		partyDB.append("\nUNLOCK TABLES;");
		partyDB.append("\n");
	}

	/** The priceid counter. */
	private static int priceid = 356597;

	/** The price string builder. */
	private static StringBuilder priceDB = new StringBuilder();

	/**
	 * Initialize price.
	 */
	private static final void initializePrice() {
//		priceDB.append("\n--");
//		priceDB.append("\n-- Table structure for table `price`");
//		priceDB.append("\n--");
//		priceDB.append("\nDROP TABLE IF EXISTS `price`;");
//		priceDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		priceDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		priceDB.append("\nCREATE TABLE `price` (");
//		priceDB.append("\n  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,");
//		priceDB.append("\n  `EntityType` varchar(15) DEFAULT NULL,");
//		priceDB.append("\n  `EntityID` int(10) unsigned DEFAULT NULL,");
//		priceDB.append("\n  `PartyID` int(10) unsigned DEFAULT NULL,");
//		priceDB.append("\n  `Name` varchar(50) CHARACTER SET latin1 COLLATE latin1_bin DEFAULT NULL,");
//		priceDB.append("\n  `State` varchar(15) DEFAULT NULL,");
//		priceDB.append("\n  `Type` varchar(50) DEFAULT 'Reservation',");
//		priceDB.append("\n  `Date` date DEFAULT NULL,");
//		priceDB.append("\n  `ToDate` date DEFAULT NULL,");
//		priceDB.append("\n  `Quantity` double DEFAULT '0',");
//		priceDB.append("\n  `Unit` varchar(3) DEFAULT NULL,");
//		priceDB.append("\n  `Value` double DEFAULT NULL,");
//		priceDB.append("\n  `Minimum` double DEFAULT '0',");
//		priceDB.append("\n  `Currency` varchar(3) DEFAULT NULL,");
//		priceDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		priceDB.append("\n  `Cost` double DEFAULT '0',");
//		priceDB.append("\n  `SupplierID` int(10) unsigned DEFAULT NULL,");
//		priceDB.append("\n  `Payer` varchar(15) DEFAULT 'Unknown',");
//		priceDB.append("\n  `Rule` varchar(15) DEFAULT 'Manual',");
//		priceDB.append("\n  PRIMARY KEY (`ID`) USING BTREE,");
//		priceDB.append("\n  KEY `Entity` (`EntityType`,`EntityID`,`Date`,`Quantity`,`Unit`,`State`) USING BTREE");
//		priceDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=29119 DEFAULT CHARSET=latin1 COMMENT='InnoDB free: 12288 kB';");
//		priceDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
		priceDB.append("\n--");
		priceDB.append("\n-- Dumping data for table `price`");
		priceDB.append("\n--");
		priceDB.append("\nLOCK TABLES `price` WRITE;");
		priceDB.append("\n/*!40000 ALTER TABLE `price` DISABLE KEYS */;");
		priceDB.append("\nINSERT INTO `price` (ID, EntityType, EntityID, PartyID, Name, State, Type, Date, ToDate, Quantity, Unit, Value, Minimum, Currency, Cost, SupplierID) VALUES "); 
	}

	/**
	 * Adds a price using the specified parameters.
	 *
	 * @param productid the product id
	 * @param organizationid the organization id
	 * @param entitytype the type
	 * @param name the name
	 * @param fromdate the from date
	 * @param todate the to date
	 * @param value the value
	 * @param minimum the minimum
	 * @param cost the cost
	 * @param supplierid the supplier id
	 */
	private static final void addPrice(int productid, int organizationid, String entitytype, String name, String type, String fromdate, String todate, double value, double minimum, double cost, Integer supplierid) {
		priceid++;
		if (priceid % 100 == 0) {
			int length = priceDB.length();
			priceDB.replace(length-1, length, ";");
			priceDB.append("\nINSERT INTO `price` (ID, EntityType, EntityID, PartyID, Name, State, Type, Date, ToDate, Quantity, Unit, Value, Minimum, Currency, Cost, SupplierID) VALUES ");			
		}
		priceDB.append("\n(" + priceid + ",'" + entitytype + "'," + productid + "," + organizationid + ",'" + name + "','Created','" + type + "','"  + fromdate + "','" + todate + "',0,'DAY'," + ((int)value/5) * 5 + "," + ((int)minimum/5) * 5 + ",'USD'," + (int)cost +"," + supplierid +"),");
		//(9805,'Product',125,5,'Reservation','Created','2010-12-16','2011-01-08',0,'DAY',2300,4600,'USD','2011-12-01 14:33:05',0,NULL)
		//29158, 'Feature', 146, 5, 'Airport Transfer', 'Created', '2011-12-01', '2012-02-29', 1, 'EA', 350, , 'USD', 2011-12-19 16:51:03, 250, 7866
	}

	/**
	 * Finalize price.
	 */
	private static final void finalizePrice() {
		int length = priceDB.length();
		priceDB.replace(length-1, length, ";");
		priceDB.append("\n/*!40000 ALTER TABLE `price` ENABLE KEYS */;");
		priceDB.append("\nUNLOCK TABLES;");
	}

	/** The text record counter. */
	private static int textcounter = 0;

	/** The text string builder. */
	private static StringBuilder textDB = new StringBuilder();

	/**
	 * Initialize text.
	 */
	private static final void initializeText() {
//		textDB.append("\n--");
//		textDB.append("\n-- Table structure for table `text`");
//		textDB.append("\n--");
//		textDB.append("\n");
//		textDB.append("\nDROP TABLE IF EXISTS `text`;");
//		textDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		textDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		textDB.append("\nCREATE TABLE `text` (");
//		textDB.append("\n  `ID` varchar(50) NOT NULL,");
//		textDB.append("\n  `Language` varchar(2) NOT NULL DEFAULT 'EN',");
//		textDB.append("\n  `Name` varchar(100) DEFAULT NULL,");
//		textDB.append("\n  `State` varchar(15) DEFAULT 'Created',");
//		textDB.append("\n  `Type` varchar(10) DEFAULT NULL,");
//		textDB.append("\n  `Data` blob,");
//		textDB.append("\n  `Date` datetime DEFAULT NULL,");
//		textDB.append("\n  `Notes` varchar(20000) CHARACTER SET utf8 DEFAULT NULL,");
//		textDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		textDB.append("\n  PRIMARY KEY (`ID`,`Language`) USING BTREE,");
//		textDB.append("\n  KEY `Name` (`Name`,`ID`,`Language`) USING BTREE");
//		textDB.append("\n) ENGINE=InnoDB DEFAULT CHARSET=latin1;");
//		textDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		textDB.append("\n");
		textDB.append("\n--");
		textDB.append("\n-- Dumping data for table `text`");
		textDB.append("\n--");
		textDB.append("\n");
		textDB.append("\nLOCK TABLES `text` WRITE;");
		textDB.append("\n/*!40000 ALTER TABLE `text` DISABLE KEYS */;");
		textDB.append("\nINSERT INTO `text` (ID, Language, Name, State, Type, Data, Date, Notes) VALUES ");
	}

	/**
	 * Adds a text record using the specified parameters.
	 *
	 * @param textid the text id
	 * @param language the language
	 * @param name the name
	 * @param type the type
	 * @param notes the notes
	 */
	private static final void addText(String textid, String language, String name, String type, String notes) {
		textcounter++;
		if (textcounter % 100 == 0) {
			int length = textDB.length();
			textDB.replace(length-1, length, ";");
			//if (textcounter % 1000 == 0) {outText(textcounter);}
			textDB.append("\nINSERT INTO `text` (ID, Language, Name, State, Type, Data, Date, Notes) VALUES ");
		}
		textDB.append("\n('" + textid + "','" + language + "','" + name + "', 'Created','" + type + "',NULL,'2011-12-10','" + notes + "'),");
	}

	/**
	 * Finalize text.
	 */
	private static final void finalizeText() {
		int length = textDB.length();
		textDB.replace(length-1, length, ";");
		textDB.append("\n/*!40000 ALTER TABLE `text` ENABLE KEYS */;");
		textDB.append("\nUNLOCK TABLES;");
		textDB.append("\n");
	}

	/** The workflowid counter. */
	private static int workflowid = 1345;

	/** The workflow string builder. */
	private static StringBuilder workflowDB = new StringBuilder();

	/**
	 * Initialize workflow.
	 */
	private static final void initializeWorkflow() {
//		workflowDB.append("\n--");
//		workflowDB.append("\n-- Table structure for table `workflow`");
//		workflowDB.append("\n--");
//		workflowDB.append("\n");
//		workflowDB.append("\nDROP TABLE IF EXISTS `workflow`;");
//		workflowDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		workflowDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		workflowDB.append("\nCREATE TABLE `workflow` (");
//		workflowDB.append("\n  `Sequence` int(10) unsigned NOT NULL AUTO_INCREMENT,");
//		workflowDB.append("\n  `ID` int(11) NOT NULL DEFAULT '0',");
//		workflowDB.append("\n  `Process` varchar(15) NOT NULL,");
//		workflowDB.append("\n  `State` varchar(15) NOT NULL,");
//		workflowDB.append("\n  `Datename` varchar(25) NOT NULL,");
//		workflowDB.append("\n  `Enabled` bit(1) DEFAULT NULL,");
//		workflowDB.append("\n  `Prior` bit(1) DEFAULT NULL,");
//		workflowDB.append("\n  `Duration` int(10) unsigned DEFAULT NULL,");
//		workflowDB.append("\n  `Unit` varchar(3) DEFAULT NULL,");
//		workflowDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		workflowDB.append("\n  PRIMARY KEY (`Sequence`) USING BTREE,");
//		workflowDB.append("\n  UNIQUE KEY `ID` (`ID`,`Process`,`State`)");
//		workflowDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
//		workflowDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		workflowDB.append("\n");
		workflowDB.append("\n--");
		workflowDB.append("\n-- Dumping data for table `workflow`");
		workflowDB.append("\n--");
		workflowDB.append("\n");
		workflowDB.append("\nLOCK TABLES `workflow` WRITE;");
		workflowDB.append("\n/*!40000 ALTER TABLE `workflow` DISABLE KEYS */;");
		workflowDB.append("\nINSERT INTO `workflow` (Sequence, ID, Process, State, Datename, Enabled, Prior, Duration, Unit) VALUES ");
	}

	/**
	 * Adds a work flow record using the specified parameters.
	 *
	 * @param organizationid the organizationid
	 * @param state the state
	 * @param datename the datename
	 * @param prior the prior
	 * @param days the days
	 */
	private static final void addWorkflow(int organizationid, String state, String datename, boolean prior, int days) {
		workflowid++;
		if (workflowid % 100 == 0) {
			int length = workflowDB.length();
			workflowDB.replace(length-1, length, ";");
			workflowDB.append("\nINSERT INTO `workflow` (Sequence, ID, Process, State, Datename, Enabled, Prior, Duration, Unit) VALUES ");
		}
		workflowDB.append("\n(" + workflowid + "," + organizationid + ",'Reservation','" + state + "','" + datename + "',''," + (prior ? 1 : 0) + "," + days + ",'DAY'),");
	}

	/**
	 * Finalize workflow.
	 */
	private static final void finalizeWorkflow() {
		int length = workflowDB.length();
		workflowDB.replace(length-1, length, ";");
		workflowDB.append("\n/*!40000 ALTER TABLE `workflow` ENABLE KEYS */;");
		workflowDB.append("\nUNLOCK TABLES;");
		workflowDB.append("\n");
	}

	/** The yieldid counter. */
	private static int yieldid = 53401;

	/** The yield string builder. */
	private static StringBuilder yieldDB = new StringBuilder();

	/**
	 * Initialize yield.
	 */
	private static final void initializeYield() {
//		yieldDB.append("\nDROP TABLE IF EXISTS `yield`;");
//		yieldDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		yieldDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		yieldDB.append("\nCREATE TABLE `yield` (");
//		yieldDB.append("\n  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,");
//		yieldDB.append("\n  `EntityType` varchar(15) DEFAULT NULL,");
//		yieldDB.append("\n  `EntityID` int(10) unsigned DEFAULT NULL,");
//		yieldDB.append("\n  `Name` varchar(50) DEFAULT NULL,");
//		yieldDB.append("\n  `State` varchar(25) DEFAULT NULL,");
//		yieldDB.append("\n  `FromDate` datetime DEFAULT NULL,");
//		yieldDB.append("\n  `ToDate` datetime DEFAULT NULL,");
//		yieldDB.append("\n  `Param` int(10) unsigned DEFAULT '0',");
//		yieldDB.append("\n  `Amount` double DEFAULT NULL,");
//		yieldDB.append("\n  `Modifier` varchar(25) DEFAULT NULL,");
//		yieldDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		yieldDB.append("\n  PRIMARY KEY (`ID`) USING BTREE");
//		yieldDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;");
//		yieldDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		yieldDB.append("\n");
		yieldDB.append("\n--");
		yieldDB.append("\n-- Dumping data for table `yield`");
		yieldDB.append("\n--");
		yieldDB.append("\n");
		yieldDB.append("\nLOCK TABLES `yield` WRITE;");
		yieldDB.append("\n/*!40000 ALTER TABLE `yield` DISABLE KEYS */;");
		yieldDB.append("\nINSERT INTO `yield` (ID, EntityType, EntityID, Name, State, FromDate, ToDate, Param, Amount, Modifier) VALUES "); //(32,'Product',143,'Maximum Gap Filler','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',21,25,'Decrease Percent','2011-06-27 12:09:39'),(34,'Product',143,'Last Minute Lead Time','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',7,30,'Decrease Percent','2011-06-27 08:10:49'),(35,'Product',143,'Last Minute Lead Time','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',14,15,'Decrease Percent','2011-06-27 08:10:59'),(36,'Product',143,'Length of Stay','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',14,10,'Decrease Percent','2011-06-27 08:35:37'),(38,'Product',143,'Length of Stay','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',7,5,'Decrease Percent','2011-06-27 08:35:27'),(39,'Product',143,'Length of Stay','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',21,15,'Decrease Percent','2011-06-27 08:35:21'),(45,'Product',381,'Date Range','Final','2010-12-01 00:00:00','2012-12-15 00:00:00',14944,10,'Decrease Percent','2011-09-15 11:43:37');");
	}

	/**
	 * Adds a yield record using the specified parameters.
	 *
	 * @param productid the productid
	 * @param rule the rule
	 * @param param the param
	 * @param percent the percent
	 */
	private static final void addYield(int productid, String rule, int param, int percent) {
		yieldid++;
		if (yieldid % 100 == 0) {
			int length = yieldDB.length();
			yieldDB.replace(length-1, length, ";");
			yieldDB.append("\nINSERT INTO `yield` (ID, EntityType, EntityID, Name, State, FromDate, ToDate, Param, Amount, Modifier) VALUES "); //(32,'Product',143,'Maximum Gap Filler','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',21,25,'Decrease Percent','2011-06-27 12:09:39'),(34,'Product',143,'Last Minute Lead Time','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',7,30,'Decrease Percent','2011-06-27 08:10:49'),(35,'Product',143,'Last Minute Lead Time','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',14,15,'Decrease Percent','2011-06-27 08:10:59'),(36,'Product',143,'Length of Stay','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',14,10,'Decrease Percent','2011-06-27 08:35:37'),(38,'Product',143,'Length of Stay','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',7,5,'Decrease Percent','2011-06-27 08:35:27'),(39,'Product',143,'Length of Stay','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',21,15,'Decrease Percent','2011-06-27 08:35:21'),(45,'Product',381,'Date Range','Final','2010-12-01 00:00:00','2012-12-15 00:00:00',14944,10,'Decrease Percent','2011-09-15 11:43:37');");
		}
		yieldDB.append("\n(" + yieldid + ",'Product'," + productid + ",'" + rule + "','Created','2010-03-11 00:00:00','2020-03-14 00:00:00',21,25,'Decrease Percent'),");
	}

	/**
	 * Finalize yield.
	 */
	private static final void finalizeYield() {
		int length = yieldDB.length();
		yieldDB.replace(length-1, length, ";");
		yieldDB.append("\n/*!40000 ALTER TABLE `yield` ENABLE KEYS */;");
		yieldDB.append("\nUNLOCK TABLES;");
		yieldDB.append("\n");
	}

	/** The productid counter. */
	private static int productid = 35603;

	/** The product string builder. */
	private static StringBuilder productDB = new StringBuilder();

	/**
	 * Initialize product.
	 */
	private static final void initializeProduct() {
//		productDB.append("\n--");
//		productDB.append("\n-- Table structure for table `product`");
//		productDB.append("\n--");
//		productDB.append("\n");
//		productDB.append("\nDROP TABLE IF EXISTS `product`;");
//		productDB.append("\n/*!40101 SET @saved_cs_client     = @@character_set_client */;");
//		productDB.append("\n/*!40101 SET character_set_client = utf8 */;");
//		productDB.append("\nCREATE TABLE `product` (");
//		productDB.append("\n  `ID` int(10) NOT NULL AUTO_INCREMENT,");
//		productDB.append("\n  `PartofID` int(10) unsigned DEFAULT NULL,");
//		productDB.append("\n  `OwnerID` int(10) DEFAULT NULL,");
//		productDB.append("\n  `LocationID` int(10) DEFAULT NULL,");
//		productDB.append("\n  `SupplierID` int(10) DEFAULT NULL,");
//		productDB.append("\n  `Name` varchar(50) DEFAULT NULL,");
//		productDB.append("\n  `State` varchar(15) DEFAULT NULL,");
//		productDB.append("\n  `Type` varchar(25) DEFAULT NULL,");
//		productDB.append("\n  `WebAddress` varchar(255) DEFAULT NULL,");
//		productDB.append("\n  `Tax` varchar(15) DEFAULT NULL,");
//		productDB.append("\n  `Code` varchar(50) DEFAULT NULL,");
//		productDB.append("\n  `Unspsc` varchar(8) DEFAULT NULL,");
//		productDB.append("\n  `Servicedays` varchar(7) DEFAULT '0111111',");
//		productDB.append("\n  `Currency` varchar(3) DEFAULT NULL,");
//		productDB.append("\n  `Unit` varchar(3) DEFAULT NULL,");
//		productDB.append("\n  `Room` int(10) unsigned DEFAULT '1',");
//		productDB.append("\n  `Quantity` int(10) unsigned DEFAULT '1',");
//		productDB.append("\n  `Person` int(10) unsigned DEFAULT '2',");
//		productDB.append("\n  `Linenchange` int(10) unsigned DEFAULT '3',");
//		productDB.append("\n  `Rating` int(10) unsigned DEFAULT '6',");
//		productDB.append("\n  `Refresh` int(10) unsigned DEFAULT '1',");
//		productDB.append("\n  `Commission` double DEFAULT '20',");
//		productDB.append("\n  `Discount` double DEFAULT '20',");
//		productDB.append("\n  `OwnerDiscount` double DEFAULT '0',");
//		productDB.append("\n  `Rank` double DEFAULT '0',");
//		productDB.append("\n  `Latitude` double DEFAULT '0',");
//		productDB.append("\n  `Longitude` double DEFAULT '0',");
//		productDB.append("\n  `Altitude` double DEFAULT '0',");
//		productDB.append("\n  `version` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
//		productDB.append("\n  `Physicaladdress` varchar(100) DEFAULT NULL,");
//		productDB.append("\n  PRIMARY KEY (`ID`) USING BTREE,");
//		productDB.append("\n  KEY `Name` (`Name`,`SupplierID`) USING BTREE,");
//		productDB.append("\n  KEY `Code` (`Code`),");
//		productDB.append("\n  KEY `LocationID` (`LocationID`),");
//		productDB.append("\n  KEY `OwnerID` (`OwnerID`),");
//		productDB.append("\n  KEY `PartofID` (`PartofID`,`Name`)");
//		productDB.append("\n) ENGINE=InnoDB AUTO_INCREMENT=1833 DEFAULT CHARSET=latin1;");
//		productDB.append("\n/*!40101 SET character_set_client = @saved_cs_client */;");
//		productDB.append("\n");
		productDB.append("\n--");
		productDB.append("\n-- Dumping data for table `product`");
		productDB.append("\n--");
		productDB.append("\n");
		productDB.append("\nLOCK TABLES `product` WRITE;");
		productDB.append("\n/*!40000 ALTER TABLE `product` DISABLE KEYS */;");
		productDB.append("\nINSERT INTO `product` (ID, OwnerID, LocationID, SupplierID, Name, State, Type, WebAddress, Tax, Code, Unspsc, Servicedays, Currency, Unit, Room, Quantity, Person, Linenchange, Rating, Refresh, Commission, Discount, Rank, Latitude, Longitude, Altitude) VALUES ");
	}

	/**
	 * Adds the products.
	 */
	private static final void addProducts() {
/*
 		//NC Coast
		int[] organizationids = addOrganizations("Raleigh", "27601", Country.US, Currency.Code.USD.name(), 57735, 35.775207,-78.635187, 40);
		addProducts("Crystal Coast", "28512", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 57735, 34.748101,-76.548672, 8); //
		addProducts("Crystal Coast", "28512", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 57735, 34.809293,-76.488991, 6); //
		addProducts("Crystal Coast", "28512", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 57735, 34.867342,-76.424446, 8); //
		addProducts("Crystal Coast", "28512", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 57735, 34.920845,-76.579628, 5); //
		addProducts("Crystal Coast", "28512", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 57735, 34.85551,-76.692925, 6); //
		addProducts("Dolphin Bay", "98245", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 55535, 48.6339922, -122.8824044, 10); //
		addProducts("Oak Island", "28465", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 57735, 33.954752,-78.121233, 6); //
		addProducts("Oak Island", "28465", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 57735, 33.948202,-78.02187, 4); //
		addProducts("Oak Island", "28465", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 57735, 33.95048,-78.208294, 8); //
		addProducts("Oak Island", "28465", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 57735, 33.949056,-78.312664, 6); //
		addProducts("Ocracoke Island", "27960", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 50104, 35.112687, -75.975895, 3); //
		addProducts("Ocracoke Island", "27960", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 50104, 35.101864,-75.977254, 3); //
		addProducts("Outer Banks", "27915", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 50104, 35.912411,-75.646591, 5); //
		addProducts("Outer Banks", "27915", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 50104, 36.067417,-75.714455, 5); //
		addProducts("Outer Banks", "27915", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 50104, 35.711395,-75.500908, 5); //
		addProducts("Palm Springs", "92262", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 53916, 33.823990,-116.530339, 20); //
		addProducts("Pawleys Is", "92262", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_17, organizationids, 53916, 33.442615,-79.142089, 10); //
		addProducts("West Palm Beach", "33401", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 57483, 26.72476,-80.066829, 15); //

		//Orlando
		organizationids = addOrganizations("Orlando", "32816", Country.US, Currency.Code.USD.name(), 53783, 28.537481,-81.386948, 40);
		addProducts("Disney World", "32836", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 53783, 28.38249,-81.564074, 15); //
		addProducts("Disney World", "32836", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 53783, 28.389286,-81.584158, 5); //
		addProducts("Disney World", "32836", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 53783, 28.367084,-81.582956, 5); //
		addProducts("Disney World", "32836", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 53783, 28.361042,-81.548796, 10); //
		addProducts("Disney World", "32836", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 53783, 28.386568,-81.532316, 10); //
		addProducts("Universal", "32839", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 53783, 28.477142,-81.471226, 10); //
		addProducts("Orlando", "32816", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 53783, 28.600970, -81.201328, 10); //
		addProducts("Orlando", "32816", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 53783, 28.655043,-81.305809, 8); //
		addProducts("Orlando", "32816", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 53783, 28.533258,-81.501503, 8); //
		addProducts("Orlando", "32816", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 53783, 28.471106,-81.202812, 8); //
		addProducts("Orlando", "32801", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 53783, 28.545926, -81.375847, 8); //
		addProducts("Orlando", "32801", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 53783, 28.575176,-81.341457, 8); //
		addProducts("Paradise Heights", "32703", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 53783, 28.623857,-81.514292, 6); //
		addProducts("Paradise Heights", "32703", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 53783, 28.575628,-81.549311, 6); //
		addProducts("Paradise Heights", "32703", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 53783, 28.685317,-81.546564, 6); //
		addProducts("Winter Garden", "32789", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 53783, 28.558441,-81.574717, 6); //
		addProducts("Winter Garden", "32789", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_17, organizationids, 53783, 28.539894,-81.667929, 6); //
		addProducts("Lake Florence", "32818", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 53783, 28.589948,-81.717024, 10); //

		//Miami
		organizationids = addOrganizations("Miami", "33139", Country.US, Currency.Code.USD.name(), 52444, 25.843157,-80.262222, 10);
		addProducts("Miami Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 52444, 25.799118,-80.142016, 5); //
		addProducts("Miami Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 52444, 25.779953,-80.136023, 4); //
		addProducts("Miami Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 52444, 25.836359,-80.126753, 4); //
		addProducts("Miami Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 52444, 25.800355,-80.200739, 5); //
		addProducts("Miami Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 52444, 25.777788,-80.206919, 6); //
		addProducts("Bay Harbor", "33154", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 52444, 25.880693, -80.130186, 10); //,
		addProducts("Biscayne Point", "33141", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 52444, 25.866406, -80.127511, 5); //,
		addProducts("Biscayne Point", "33141", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 52444, 25.851963,-80.141172, 5); //,
		addProducts("Biscayne Park", "33141", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 52444, 25.881466,-80.182886, 5); //,
		addProducts("Miami Shores", "33141", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 52444, 25.853508,-80.193701, 6); //,
		addProducts("Aventura", "33160", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 52444, 25.96036, -80.137124, 4); //,
		addProducts("Aventura", "33160", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 52444, 25.979805,-80.139441, 4); //,
		addProducts("Fort Lauderdale", "33301", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 52444, 26.129857,-80.145979, 6); //,
		addProducts("Fort Lauderdale", "33301", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 52444, 26.10404,-80.143819, 6); //,
		addProducts("Fort Lauderdale", "33301", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 52444, 26.170845,-80.144634, 6); //,
		addProducts("Pompano", "33064", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 52444, 26.223677,-80.129671, 8); //,
		addProducts("Pompano", "33064", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_17, organizationids, 52444, 26.292953,-80.1192, 8); //,
		addProducts("Delray Beach", "33122", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 52444, 26.458587, -80.076799, 5); //,

		//West Palm Beach
		organizationids = addOrganizations("West Palm Beach", "33139", Country.US, Currency.Code.USD.name(), 57483, 26.694091,-80.206146, 20);
		addProducts("W Palm Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 57483, 26.711727,-80.069833, 6); //
		addProducts("W Palm Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 57483, 26.691177,-80.073538, 5); //
		addProducts("W Palm Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 57483, 26.739325,-80.073881, 5); //
		addProducts("W Palm Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 57483, 26.767836,-80.085382, 5); //
		addProducts("W Palm Beach", "33139", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 57483, 26.725833,-80.115938, 6); //
		addProducts("Palm Beach", "33414", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 57483, 26.705976,-80.046401, 8); //
		addProducts("Riviera", "33129", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 57483, 26.777645,-80.070448, 6); //
		addProducts("Riviera", "33129", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 57483, 26.776572, -80.057988, 6); //
		addProducts("Riviera", "33129", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 57483, 26.801856,-80.080576, 6); //
		addProducts("Riviera", "33129", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 57483, 26.753275,-80.080061, 6); //
		addProducts("N Palm Beach", "33403", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 57483, 26.830811,-80.080218, 6); //
		addProducts("N Palm Beach", "33403", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 57483, 26.890536,-80.107698, 6); //
		addProducts("The Bluffs", "38120", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 57483, 26.907069,-80.092421, 8); //,
		addProducts("The Bluffs", "38120", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 57483, 26.866496,-80.096884, 8); //,
		addProducts("The Bluffs", "38120", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 57483, 26.9311,-80.125723, 8); //,
		addProducts("Jupiter", "33469", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 57483, 26.907988,-80.106325, 5); //,
		addProducts("Jupiter", "33469", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 57483, 26.956044,-80.124178, 5); //,
		addProducts("Juno", "33408", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 57483, 26.881962,-80.073524, 6); //,

		//Tampa
		organizationids = addOrganizations("Tampa", "33606", Country.US, Currency.Code.USD.name(), 56473, 28.027137,-82.437515, 20);
		addProducts("St Petersburg", "33701", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 56473, 27.812357, -82.716293, 8); //
		addProducts("St Petersburg", "33701", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 56473, 27.769836,-82.679787, 6); //
		addProducts("St Petersburg", "33701", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 56473, 27.862146,-82.655754, 5); //
		addProducts("St Petersburg", "33701", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 56473, 27.870645,-82.761498, 5); //
		addProducts("St Petersburg", "33701", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 56473, 27.709063,-82.724419, 2); //
		addProducts("Tarpon Springs", "34689", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 56473, 28.146552, -82.756677, 6); //,
		addProducts("East Lake", "33610", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 56473, 28.112414, -82.695837, 6); //,
		addProducts("East Lake", "33610", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 56473, 28.140119,-82.690029, 6); //,
		addProducts("East Lake", "33610", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 56473, 28.087126,-82.686253, 6); //,
		addProducts("East Lake", "33610", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 56473, 28.114383,-82.624111, 6); //,
		addProducts("Tampa", "33606", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 56473, 27.973482,-82.464466, 8); //,
		addProducts("Tampa", "33606", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 56473, 27.942853,-82.504091, 6); //,
		addProducts("Bay Port", "33615", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 56473, 28.002889,-82.584071, 6); //,
		addProducts("Bay Port", "33615", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 56473, 28.024409,-82.61447, 6); //,
		addProducts("Bay Port", "33615", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 56473, 27.988793,-82.558508, 5); //,
		addProducts("Clearwater", "33755", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 56473, 27.968327,-82.769308, 8); //,
		addProducts("Clearwater", "33755", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_17, organizationids, 56473, 28.013195,-82.765903, 6); //,
		addProducts("Seminole", "33604", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 56473, 27.841049, -82.80055, 10); //,

		//Jacksonville
		organizationids = addOrganizations("Jacksonville", "32223", Country.US, Currency.Code.USD.name(), 50733, 30.366951,-81.711617, 10);
		addProducts("Amelia Island", "32034", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 50733, 30.630822, -81.467171, 5); //
		addProducts("Amelia Island", "32034", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 50733, 30.597434,-81.474323, 4); //
		addProducts("Amelia Island", "32034", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 50733, 30.585022,-81.526508, 5); //
		addProducts("Amelia Island", "32034", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 50733, 30.652385,-81.551914, 6); //
		addProducts("Amelia Island", "32034", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 50733, 30.574677,-81.550541, 4); //
		addProducts("Atlantic Beach", "32233", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 50733, 30.336806,-81.416345, 8); //
		addProducts("Jacksonville Beach", "32250", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 50733, 30.288125,-81.402011, 4); //
		addProducts("Jacksonville Beach", "32250", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 50733, 30.326064,-81.418018, 5); //
		addProducts("Jacksonville Beach", "32250", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 50733, 30.255509,-81.417675, 5); //
		addProducts("Jacksonville Beach", "32250", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 50733, 30.226145,-81.404285, 3); //
		addProducts("Sawgrass", "32081", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 50733, 30.19796,-81.430779, 6); //
		addProducts("Sawgrass", "32081", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 50733, 30.270928,-81.463738, 6); //
		addProducts("Fernandina Beach", "32034", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 50733, 30.670253, -81.446142, 4); //
		addProducts("Fernandina Beach", "32034", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 50733, 30.641457,-81.46863, 5); //
		addProducts("Fernandina Beach", "32034", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 50733, 30.625505,-81.499014, 6); //
		addProducts("San Jose", "32217", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 50733, 30.262033,-81.594629, 6); //
		addProducts("San Jose", "32217", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 50733, 30.216207,-81.577806, 6); //
		addProducts("Jacksonville", "32223", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 50733, 30.347991, -81.663637, 10); //

		//Naples/Sanibel/Gulf Coast
		organizationids = addOrganizations("Fort Myers", "33912", Country.US, Currency.Code.USD.name(), 49161, 26.633342,-81.829033, 20);
		addProducts("Sanibel Island", "33957", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 49161, 26.448443, -82.112045, 2); //
		addProducts("Sanibel Island", "33957", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 49161, 26.443832,-82.052135, 2); //
		addProducts("Sanibel Island", "33957", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 49161, 26.520043,-82.086811, 2); //
		addProducts("Sanibel Island", "33957", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 49161, 26.498537,-81.957722, 2); //
		addProducts("Sanibel Island", "33957", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 49161, 26.573482,-82.099514, 2); //
		addProducts("Cape Coral", "33904", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 49161, 26.648993,-81.988764, 10); //
		addProducts("Englewood", "34223", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 49161, 26.961973,-82.352965, 6); //
		addProducts("Englewood", "34223", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 49161, 26.948393,-82.336063, 6); //
		addProducts("Englewood", "34223", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 49161, 26.993066,-82.366962, 6); //
		addProducts("Englewood", "34223", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 49161, 26.939517,-82.32542, 6); //
		addProducts("Charlotte Park", "33950", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 49161, 26.906457,-82.067242, 5); //
		addProducts("Charlotte Park", "33950", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 49161, 26.90462,-82.018147, 10); //
		addProducts("Fort Myers", "33912", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 49161, 26.495771,-81.934719, 5); //
		addProducts("Fort Myers", "33912", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 49161, 26.472724,-81.842022, 5); //
		addProducts("Fort Myers", "33912", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 49161, 26.435532,-81.805973, 5); //
		addProducts("Naple Park", "34108", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 49161, 26.266479,-81.796331, 6); //
		addProducts("Naple Park", "34108", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 49161, 26.230914,-81.787577, 6); //
		addProducts("Marco Island", "34145", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 49161, 25.940757,-81.714106, 10); //

		//Napa/Sonoma
		organizationids = addOrganizations("Napa Valley", "94574", Country.US, Currency.Code.USD.name(), 53038, 38.309875,-122.296486, 10);
		addProducts("Napa Valley", "94574", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 53038, 38.315262,-122.296429, 30); //
		addProducts("Napa Valley", "94574", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 53038, 38.234944,-122.295914, 30); //
		addProducts("Napa Valley", "94574", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 53038, 38.401949,-122.364578, 30); //
		addProducts("Napa Valley", "94574", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 53038, 38.477245,-122.430496, 30); //
		addProducts("Napa Valley", "94574", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 53038, 38.552461,-122.510147, 30); //
		addProducts("Agua Caliente", "95476", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 53038, 38.323882,-122.50454, 30); //
		addProducts("Deer Park", "94576", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 53038, 38.517549,-122.468433, 30); //
		addProducts("Calistoga", "94576", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 53038, 38.580379,-122.569084, 30); //
		addProducts("Rutherford", "94576", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 53038, 38.453589,-122.396736, 30); //
		addProducts("RLS Park", "94576", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 53038, 38.666748,-122.617149, 30); //
		addProducts("Lower Lake", "95457", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 53038, 38.910805,-122.619495, 30); //
		addProducts("Morgan Valley", "95457", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 53038, 38.896911,-122.552891, 30); //
		addProducts("Sonoma", "94559", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 53038, 38.292497,-122.459135, 30); //
		addProducts("Sonoma", "94559", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 53038, 38.347311,-122.509975, 30); //
		addProducts("Sonoma", "94559", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 53038, 38.245461,-122.428265, 30); //
		addProducts("Lake Berryessa", "94574", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 53038, 38.587088,-122.230167, 30); //
		addProducts("Upper Lake", "95485", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 53038, 39.143109,-122.897587, 30); //

		//Los Angeles/Catalina Island
		organizationids = addOrganizations("Los Angeles", "90011", Country.US, Currency.Code.USD.name(), 51763, 34.034453,-118.26828, 20);
		addProducts("Avalon", "90704 ", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 51763, 33.340854,-118.329027, 2); //
		addProducts("Avalon", "90704 ", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 51763, 33.339707,-118.340292, 2); //
		addProducts("Avalon", "90704 ", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 51763, 33.332393,-118.327246, 2); //
		addProducts("Avalon", "90704 ", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 51763, 33.32766,-118.344584, 2); //
		addProducts("Avalon", "90704 ", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 51763, 33.32393,-118.333769, 2); //
		addProducts("Two Harbors", "90704 ", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 51763, 33.43975,-118.49822, 6); //
		addProducts("McGee Lake", "90704", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 51763, 33.354818,-118.438178, 2); //
		addProducts("McGee Lake", "90704", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 51763, 33.356556,-118.448596, 2); //
		addProducts("McGee Lake", "90704", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 51763, 33.34953,-118.444648, 2); //
		addProducts("McGee Lake", "90704", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 51763, 33.348096,-118.430142, 2); //
		addProducts("Palos Verdes", "90275", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 51763, 33.780146,-118.389387, 5); //
		addProducts("Palos Verdes", "90275", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 51763, 33.756743,-118.389387, 5); //
		addProducts("Huntington Beach", "92605", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 51763, 33.690924,-117.980518, 6); //
		addProducts("Seal Beach", "92605", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 51763, 33.74718,-118.03997, 6); //
		addProducts("Costa Mesa", "92605", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 51763, 33.659782,-117.891655, 6); //
		addProducts("Laguna Beach", "92651", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 51763, 33.551553,-117.76248, 5); //
		addProducts("Laguna Miguel", "92651", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_17, organizationids, 51763, 33.528087,-117.718878, 8); //
		addProducts("San Clemente", "92672", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 51763, 33.466962,-117.605267, 8); //

		//San Francisco
		organizationids = addOrganizations("San Francisco", "94103", Country.US, Currency.Code.USD.name(), 55335, 37.751851,-122.445745, 6);
		addProducts("San Francisco", "94103", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 55335, 37.776956,-122.443156, 5); //
		addProducts("San Francisco", "94103", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 55335, 37.73434,-122.437935, 5); //
		addProducts("San Francisco", "94103", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 55335, 37.690884,-122.444801, 5); //
		addProducts("San Francisco", "94103", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 55335, 37.655014,-122.439995, 5); //
		addProducts("San Francisco", "94103", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 55335, 37.620214,-122.435188, 5); //
		addProducts("Sausalito", "94965", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 55335, 37.859676,-122.495556, 10); //
		addProducts("San Rafael", "94903", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 55335, 37.970726,-122.532978, 6); //
		addProducts("San Rafael", "94903", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 55335, 37.994133,-122.50823, 6); //
		addProducts("San Rafael", "94903", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 55335, 38.044847,-122.652969, 16); //
		addProducts("San Rafael", "94903", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 55335, 37.914003,-122.52368, 16); //
		addProducts("Santa Cruz", "95060", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 55335, 36.984729,-122.050409, 8); //
		addProducts("Santa Cruz", "95060", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 55335, 36.996795,-122.004919, 8); //
		addProducts("Monterey", "94127", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 55335, 36.589895,-121.92461, 4); //
		addProducts("Monterey", "94127", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 55335, 36.591549,-121.827106, 4); //
		addProducts("Carmel", "94127", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 55335, 36.537502,-121.879292, 4); //
		addProducts("Rio Del Mar", "95003", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 55335, 36.983358,-121.841927, 6); //
		addProducts("Moss Landing", "95003", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 55335, 36.831272,-121.73481, 6); //
		addProducts("El Granada", "94018", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 55335, 37.514492,-122.46171, 6); //

		//Texas Gulf Coast
		organizationids = addOrganizations("Houston", "77036", Country.US, Currency.Code.USD.name(), 50485, 29.760801,-95.36705, 20);
		addProducts("Galveston Is", "77550", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 49370, 29.2858,-94.825172, 2); //
		addProducts("Galveston Is", "77550", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 49370, 29.311699,-94.787664, 2); //
		addProducts("Galveston Is", "77550", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 49370, 29.253006,-94.884481, 2); //
		addProducts("Galveston Is", "77550", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 49370, 29.235182,-94.908171, 2); //
		addProducts("Galveston Is", "77550", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 49370, 29.21286,-94.938898, 1.5); //
		addProducts("Crystal Beach", "77650", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 49370, 29.467699,-94.614286, 10); //
		addProducts("Port Aransas", "78373", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 49370, 27.828298,-97.069216, 2); //
		addProducts("Port Aransas", "78373", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 49370, 27.815242,-97.089643, 2); //
		addProducts("Port Aransas", "78373", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 49370, 27.781835,-97.111273, 1.5); //
		addProducts("Port Aransas", "78373", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 49370, 27.707087,-97.162943, 1.5); //
		addProducts("South Padre Is", "78597", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 49370, 26.11298,-97.168336, 1); //
		addProducts("South Padre Is", "78597", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 49370, 26.08793,-97.163458, 1); //
		addProducts("St Luis Pass", "77554", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 49370, 29.224396,-95.284138, 6); //
		addProducts("St Luis Pass", "77554", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 49370, 29.106876,-95.360355, 6); //
		addProducts("St Luis Pass", "77554", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 49370, 29.018649,-95.360355, 6); //
		addProducts("Quintana", "77541 ", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 49370, 28.947021,-95.29551, 2); //
		addProducts("Quintana", "77541 ", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_17, organizationids, 49370, 28.932749,-95.312762, 2); //
		addProducts("Rockport", "78381", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 49370, 28.052288,-97.073851, 5); //

		//South Carolina
		organizationids = addOrganizations("Charleston", "29401", Country.US, Currency.Code.USD.name(), 47144, 32.878434,-80.01915, 20);
		addProducts("Myrtle Beach", "29577", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_01, organizationids, 47144, 33.717915,-78.927183, 6); //
		addProducts("Myrtle Beach", "29577", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_02, organizationids, 47144, 33.760311,-78.873024, 6); //
		addProducts("Myrtle Beach", "29577", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_03, organizationids, 47144, 33.702635,-78.993187, 6); //
		addProducts("Myrtle Beach", "29577", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_04, organizationids, 47144, 33.65921,-79.028893, 6); //
		addProducts("Garden City", "29577", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_05, organizationids, 47144, 33.615763,-79.076271, 6); //
		addProducts("Hilton Head", "29928", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_06, organizationids, 47144, 32.215996,-80.737896, 5); //
		addProducts("Charleston", "29401", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_07, organizationids, 47144, 32.79651,-79.945564, 5); //
		addProducts("Charleston", "29401", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_08, organizationids, 47144, 32.809207,-79.877501, 5); //
		addProducts("Charleston", "29401", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_09, organizationids, 47144, 32.739783,-79.955606, 5); //
		addProducts("Charleston", "29401", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_10, organizationids, 47144, 32.6992,-79.957323, 5); //
		addProducts("Beaufort", "29901", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_11, organizationids, 47144, 32.443147,-80.682936, 5); //
		addProducts("Beaufort", "29901", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_12, organizationids, 47144, 32.392863,-80.695467, 5); //
		addProducts("Kiawah Is", "29455", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_13, organizationids, 47144, 32.6174,-80.062723, 2); //
		addProducts("Kiawah Is", "29455", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_14, organizationids, 47144, 32.623473,-80.030994, 2); //
		addProducts("Kiawah Is", "29455", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_15, organizationids, 47144, 32.614508,-80.095882, 2); //
		addProducts("Pawleys Is", "29585", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_16, organizationids, 47144, 33.444047,-79.143305, 5); //
		addProducts("Pawleys Is", "29585", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_17, organizationids, 47144, 33.479134,-79.124422, 5); //
		addProducts("Garden City", "29576", Country.US, Currency.Code.USD.name(), Data.PRODUCTS_18, organizationids, 47144, 33.58041,-79.002864, 10); //
*/
		//International
		int[] organizationids = addOrganizations("Sal Rei","5110","CV","CVE", 10507,16.178408,-22.91954,5);
		addProducts("Sal Rei","5110","CV","CVE", Data.PRODUCTS_01, organizationids, 10507,16.178408,-22.91954,5);
		organizationids = addOrganizations("El Gouna","N/A","EG","EGP", 15699,27.38762,33.678589,5);
		addProducts("El Gouna","N/A","EG","EGP", Data.PRODUCTS_01, organizationids, 15699,27.38762,33.678589,5);
		organizationids = addOrganizations("Mwabungu","N/A","KE","KES", 34966,-4.346582,39.537048,5);
		addProducts("Mwabungu","N/A","KE","KES", Data.PRODUCTS_01, organizationids, 34966,-4.346582,39.537048,5);
		organizationids = addOrganizations("Port Louis","N/A","MU","MUR", 36115,-20.286673,57.580719,10);
		addProducts("Port Louis","N/A","MU","MUR", Data.PRODUCTS_01, organizationids, 36115,-20.286673,57.580719,10);
		organizationids = addOrganizations("Pointe d Esny","N/A","MU","MUR", 62487,-20.4283,57.726974,10);
		addProducts("Pointe d Esny","N/A","MU","MUR", Data.PRODUCTS_01, organizationids, 62487,-20.4283,57.726974,10);
		organizationids = addOrganizations("Tata","84000","MA","MAD", 28975,29.752455,-7.973328,5);
		addProducts("Tata","84000","MA","MAD", Data.PRODUCTS_01, organizationids, 28975,29.752455,-7.973328,5);
		organizationids = addOrganizations("Casablanca","20000","MA","MAD", 9217,33.600894,-7.635498,10);
		addProducts("Casablanca","20000","MA","MAD", Data.PRODUCTS_01, organizationids, 9217,33.600894,-7.635498,10);
		organizationids = addOrganizations("Fes","30000","MA","MAD", 241,34.033599,-4.999981,5);
		addProducts("Fes","30000","MA","MAD", Data.PRODUCTS_01, organizationids, 241,34.033599,-4.999981,5);
		organizationids = addOrganizations("Marrakech","40000","MA","MAD", 35763,31.632337,-8.012123,10);
		addProducts("Marrakech","40000","MA","MAD", Data.PRODUCTS_01, organizationids, 35763,31.632337,-8.012123,10);
		organizationids = addOrganizations("Nador","62000","MA","MAD", 35766,35.169879,-2.932663,5);
		addProducts("Nador","62000","MA","MAD", Data.PRODUCTS_01, organizationids, 35766,35.169879,-2.932663,5);
		organizationids = addOrganizations("Ifrane","53000","MA","MAD", 35735,33.533668,-5.116711,5);
		addProducts("Ifrane","53000","MA","MAD", Data.PRODUCTS_01, organizationids, 35735,33.533668,-5.116711,5);
		organizationids = addOrganizations("Maputo","1100","MZ","MZM", 36783,-25.977799,32.585449,10);
		addProducts("Maputo","1100","MZ","MZM", Data.PRODUCTS_01, organizationids, 36783,-25.977799,32.585449,10);
		organizationids = addOrganizations("Dakar","N/A","SN","XOF", 43983,14.750979,-17.333336,5);
		addProducts("Dakar","N/A","SN","XOF", Data.PRODUCTS_01, organizationids, 43983,14.750979,-17.333336,5);
		organizationids = addOrganizations("Saly-Portudal","N/A","SN","XOF", 43980,14.447522,-17.017994,10);
		addProducts("Saly-Portudal","N/A","SN","XOF", Data.PRODUCTS_01, organizationids, 43980,14.447522,-17.017994,10);
		organizationids = addOrganizations("Thiès","N/A","SN","XOF", 43980,14.804094,-16.927185,5);
		addProducts("Thiès","N/A","SN","XOF", Data.PRODUCTS_01, organizationids, 43980,14.804094,-16.927185,5);
		organizationids = addOrganizations("Victoria","N/A","SC","SCR", 8229,-4.615609,55.43066,5);
		addProducts("Victoria","N/A","SC","SCR", Data.PRODUCTS_01, organizationids, 8229,-4.615609,55.43066,5);
		organizationids = addOrganizations("Port Elizabeth","6000","ZA","ZAR", 58113,-33.92513,25.554199,20);
		addProducts("Port Elizabeth","6000","ZA","ZAR", Data.PRODUCTS_01, organizationids, 58113,-33.92513,25.554199,20);
		organizationids = addOrganizations("Johannesburg","2000","ZA","ZAR", 58538,-26.20381,28.047752,20);
		addProducts("Johannesburg","2000","ZA","ZAR", Data.PRODUCTS_01, organizationids, 58538,-26.20381,28.047752,20);
		organizationids = addOrganizations("Durban","4000","ZA","ZAR", 58498,-29.850173,31.025391,20);
		addProducts("Durban","4000","ZA","ZAR", Data.PRODUCTS_01, organizationids, 58498,-29.850173,31.025391,20);
		organizationids = addOrganizations("Mokopane","600","ZA","ZAR", 61015,-24.176825,29.014893,10);
		addProducts("Mokopane","600","ZA","ZAR", Data.PRODUCTS_01, organizationids, 61015,-24.176825,29.014893,10);
		organizationids = addOrganizations("Hazy View","1242","ZA","ZAR", 59007,-25.030861,31.121521,5);
		addProducts("Hazy View","1242","ZA","ZAR", Data.PRODUCTS_01, organizationids, 59007,-25.030861,31.121521,5);
		organizationids = addOrganizations("Hermanus","7200","ZA","ZAR", 58833,-34.408043,19.26178,5);
		addProducts("Hermanus","7200","ZA","ZAR", Data.PRODUCTS_01, organizationids, 58833,-34.408043,19.26178,5);
		organizationids = addOrganizations("Langebaan","7357","ZA","ZAR", 58863,-33.087515,18.029938,5);
		addProducts("Langebaan","7357","ZA","ZAR", Data.PRODUCTS_01, organizationids, 58863,-33.087515,18.029938,5);
		organizationids = addOrganizations("Stellenbosch","7599","ZA","ZAR", 58647,-33.936239,18.861809,5);
		addProducts("Stellenbosch","7599","ZA","ZAR", Data.PRODUCTS_01, organizationids, 58647,-33.936239,18.861809,5);
		organizationids = addOrganizations("Cape Town","8000","ZA","ZAR", 58977,-33.922851,18.422699,20);
		addProducts("Cape Town","8000","ZA","ZAR", Data.PRODUCTS_01, organizationids, 58977,-33.922851,18.422699,20);
		organizationids = addOrganizations("Kariba","N/A","ZW","ZWD", 58739,-16.522341,28.851128,5);
		addProducts("Kariba","N/A","ZW","ZWD", Data.PRODUCTS_01, organizationids, 58739,-16.522341,28.851128,5);
		organizationids = addOrganizations("Victoria Falls","N/A","ZW","ZWD", 58747,-17.933091,25.833406,5);
		addProducts("Victoria Falls","N/A","ZW","ZWD", Data.PRODUCTS_01, organizationids, 58747,-17.933091,25.833406,5);
		organizationids = addOrganizations("Shanghai","200000 – 202100","CN","CNY", 10044,31.231592,121.47583,10);
		addProducts("Shanghai","200000 – 202100","CN","CNY", Data.PRODUCTS_01, organizationids, 10044,31.231592,121.47583,10);
		organizationids = addOrganizations("Kyoto","604-8571","JP","JPY", 34044,35.011721,135.768013,20);
		addProducts("Kyoto","604-8571","JP","JPY", Data.PRODUCTS_01, organizationids, 34044,35.011721,135.768013,20);
		organizationids = addOrganizations("St Petersburg","194 100","RU","RUB", 56147,60.075803,30.119019,10);
		addProducts("St Petersburg","194 100","RU","RUB", Data.PRODUCTS_01, organizationids, 56147,60.075803,30.119019,10);
		organizationids = addOrganizations("Viti Levu","10385","FJ","FJD", 17913,-17.773781,177.380018,5);
		addProducts("Viti Levu","10385","FJ","FJD", Data.PRODUCTS_01, organizationids, 17913,-17.773781,177.380018,5);
		organizationids = addOrganizations("Auckland","1150","NZ","NZD", 38980,-36.847758,174.766388,20);
		addProducts("Auckland","1150","NZ","NZD", Data.PRODUCTS_01, organizationids, 38980,-36.847758,174.766388,20);
		organizationids = addOrganizations("Christchurch","8011","NZ","NZD", 24847,-43.528638,172.633667,10);
		addProducts("Christchurch","8011","NZ","NZD", Data.PRODUCTS_01, organizationids, 24847,-43.528638,172.633667,10);
		organizationids = addOrganizations("Canberra","2600","AU","AUD", 1437,-35.281921,149.128675,5);
		addProducts("Canberra","2600","AU","AUD", Data.PRODUCTS_01, organizationids, 1437,-35.281921,149.128675,5);
		organizationids = addOrganizations("Sydney","2000","AU","AUD", 2192,-33.906611,151.200714,5);
		addProducts("Sydney","2000","AU","AUD", Data.PRODUCTS_01, organizationids, 2192,-33.906611,151.200714,5);
		organizationids = addOrganizations("Port Macquarie","2444","AU","AUD", 2070,-31.428663,152.907715,5);
		addProducts("Port Macquarie","2444","AU","AUD", Data.PRODUCTS_01, organizationids, 2070,-31.428663,152.907715,5);
		organizationids = addOrganizations("Coffs Harbour","2450","AU","AUD", 1488,-30.297018,153.116455,5);
		addProducts("Coffs Harbour","2450","AU","AUD", Data.PRODUCTS_01, organizationids, 1488,-30.297018,153.116455,5);
		organizationids = addOrganizations("Nimbin","2480","AU","AUD", 1256,-28.595525,153.222885,5);
		addProducts("Nimbin","2480","AU","AUD", Data.PRODUCTS_01, organizationids, 1256,-28.595525,153.222885,5);
		organizationids = addOrganizations("Wollongong","2520","AU","AUD", 2315,-34.425036,150.89447,10);
		addProducts("Wollongong","2520","AU","AUD", Data.PRODUCTS_01, organizationids, 2315,-34.425036,150.89447,10);
		organizationids = addOrganizations("Eden","2551","AU","AUD", 1580,-37.07271,149.875488,5);
		addProducts("Eden","2551","AU","AUD", Data.PRODUCTS_01, organizationids, 1580,-37.07271,149.875488,5);
		organizationids = addOrganizations("Byron Bay","2481","AU","AUD", 1256,-28.642389,153.61084,5);
		addProducts("Byron Bay","2481","AU","AUD", Data.PRODUCTS_01, organizationids, 1256,-28.642389,153.61084,5);
		organizationids = addOrganizations("Newcastle ","2300","AU","AUD", 1976,-32.92668,151.778913,10);
		addProducts("Newcastle ","2300","AU","AUD", Data.PRODUCTS_01, organizationids, 1976,-32.92668,151.778913,10);
		organizationids = addOrganizations("Darwin","800","AU","AUD", 1542,-12.462726,130.841804,5);
		addProducts("Darwin","800","AU","AUD", Data.PRODUCTS_01, organizationids, 1542,-12.462726,130.841804,5);
		organizationids = addOrganizations("Brisbane","4000","AU","AUD", 1398,-28.025925,153.388367,5);
		addProducts("Brisbane","4000","AU","AUD", Data.PRODUCTS_01, organizationids, 1398,-28.025925,153.388367,5);
		organizationids = addOrganizations("Surfers Paradise","4217","AU","AUD", 1256,-28.002586,153.431282,20);
		addProducts("Surfers Paradise","4217","AU","AUD", Data.PRODUCTS_01, organizationids, 1256,-28.002586,153.431282,20);
		organizationids = addOrganizations("Bundaberg North ","4670","AU","AUD", 1256,-24.864906,152.348657,0);
		addProducts("Bundaberg North ","4670","AU","AUD", Data.PRODUCTS_01, organizationids, 1256,-24.864906,152.348657,0);
		organizationids = addOrganizations("Rockhampton","4700","AU","AUD", 2111,-23.372514,150.490723,10);
		addProducts("Rockhampton","4700","AU","AUD", Data.PRODUCTS_01, organizationids, 2111,-23.372514,150.490723,10);
		organizationids = addOrganizations("Airlie Beach","4802","AU","AUD", 1263,-20.268719,148.718448,5);
		addProducts("Airlie Beach","4802","AU","AUD", Data.PRODUCTS_01, organizationids, 1263,-20.268719,148.718448,5);
		organizationids = addOrganizations("Townsville","4810","AU","AUD", 2234,-19.138033,146.829185,5);
		addProducts("Townsville","4810","AU","AUD", Data.PRODUCTS_01, organizationids, 2234,-19.138033,146.829185,5);
		organizationids = addOrganizations("Cairns","4870","AU","AUD", 1428,-16.926107,145.742569,5);
		addProducts("Cairns","4870","AU","AUD", Data.PRODUCTS_01, organizationids, 1428,-16.926107,145.742569,5);
		organizationids = addOrganizations("Noosa Heads","4567","AU","AUD", 1256,-26.397021,153.090534,50);
		addProducts("Noosa Heads","4567","AU","AUD", Data.PRODUCTS_01, organizationids, 1256,-26.397021,153.090534,50);
		organizationids = addOrganizations("Adelaide","5000","AU","AUD", 1259,-34.928656,138.599911,30);
		addProducts("Adelaide","5000","AU","AUD", Data.PRODUCTS_01, organizationids, 1259,-34.928656,138.599911,30);
		organizationids = addOrganizations("Hobart","5700","AU","AUD", 1704,-42.881813,147.323828,5);
		addProducts("Hobart","5700","AU","AUD", Data.PRODUCTS_01, organizationids, 1704,-42.881813,147.323828,5);
		organizationids = addOrganizations("Melbourne","3000","AU","AUD", 1876,-37.81331,144.963055,20);
		addProducts("Melbourne","3000","AU","AUD", Data.PRODUCTS_01, organizationids, 1876,-37.81331,144.963055,20);
		organizationids = addOrganizations("Perth","6000","AU","AUD", 2041,-31.952745,115.857439,10);
		addProducts("Perth","6000","AU","AUD", Data.PRODUCTS_01, organizationids, 2041,-31.952745,115.857439,10);
		organizationids = addOrganizations("Speight Town","BB23001","BB","BBD", 2421,13.251976,-59.641857,0);
		addProducts("Speight Town","BB23001","BB","BBD", Data.PRODUCTS_01, organizationids, 2421,13.251976,-59.641857,0);
		organizationids = addOrganizations("Belmopan","N/A","BZ","BZD", 6260,17.251646,-88.766956,10);
		addProducts("Belmopan","N/A","BZ","BZD", Data.PRODUCTS_01, organizationids, 6260,17.251646,-88.766956,10);
		organizationids = addOrganizations("San Ramón","20201","CR","CRC", 10360,10.087178,-84.469972,5);
		addProducts("San Ramón","20201","CR","CRC", Data.PRODUCTS_01, organizationids, 10360,10.087178,-84.469972,5);
		organizationids = addOrganizations("Cartago","30101","CR","CRC", 10293,9.860628,-83.921814,5);
		addProducts("Cartago","30101","CR","CRC", Data.PRODUCTS_01, organizationids, 10293,9.860628,-83.921814,5);
		organizationids = addOrganizations("San José ","20102","CR","CRC", 10360,9.933513,-84.083176,30);
		addProducts("San José ","20102","CR","CRC", Data.PRODUCTS_01, organizationids, 10360,9.933513,-84.083176,30);
		organizationids = addOrganizations("Liberia","50101","CR","CRC", 10402,10.630831,-85.439987,10);
		addProducts("Liberia","50101","CR","CRC", Data.PRODUCTS_01, organizationids, 10402,10.630831,-85.439987,10);
		organizationids = addOrganizations("Heredia","40101","CR","CRC", 10395,9.999958,-84.114761,5);
		addProducts("Heredia","40101","CR","CRC", Data.PRODUCTS_01, organizationids, 10395,9.999958,-84.114761,5);
		organizationids = addOrganizations("Limón","70101","CR","CRC", 10360,9.989392,-83.037415,5);
		addProducts("Limón","70101","CR","CRC", Data.PRODUCTS_01, organizationids, 10360,9.989392,-83.037415,5);
		organizationids = addOrganizations("Puntarenas","60101","CR","CRC", 10422,9.977938,-84.829388,20);
		addProducts("Puntarenas","60101","CR","CRC", Data.PRODUCTS_01, organizationids, 10422,9.977938,-84.829388,20);
		organizationids = addOrganizations("San Salvador","N/A","SV","SVC", 399,13.690689,-89.189758,5);
		addProducts("San Salvador","N/A","SV","SVC", Data.PRODUCTS_01, organizationids, 399,13.690689,-89.189758,5);
		organizationids = addOrganizations("Guatemala City","1001","GT","GTQ", 28261,14.625112,-90.532837,5);
		addProducts("Guatemala City","1001","GT","GTQ", Data.PRODUCTS_01, organizationids, 28261,14.625112,-90.532837,5);
		organizationids = addOrganizations("Tegucigalpa","N/A","HN","HNL", 28446,14.083468,-87.216511,5);
		addProducts("Tegucigalpa","N/A","HN","HNL", Data.PRODUCTS_01, organizationids, 28446,14.083468,-87.216511,5);
		organizationids = addOrganizations("Tijuna","22000","MX","MXN", 36130,32.533789,-117.017784,20);
		addProducts("Tijuna","22000","MX","MXN", Data.PRODUCTS_01, organizationids, 36130,32.533789,-117.017784,20);
		organizationids = addOrganizations("Tampico","89100","MX","MXN", 36468,22.309426,-97.866211,5);
		addProducts("Tampico","89100","MX","MXN", Data.PRODUCTS_01, organizationids, 36468,22.309426,-97.866211,5);
		organizationids = addOrganizations("Guanajuato","36000","MX","MXN", 36266,21.012727,-101.255493,10);
		addProducts("Guanajuato","36000","MX","MXN", Data.PRODUCTS_01, organizationids, 36266,21.012727,-101.255493,10);
		organizationids = addOrganizations("Acapulco","39300","MX","MXN", 36133,16.864348,-99.881516,30);
		addProducts("Acapulco","39300","MX","MXN", Data.PRODUCTS_01, organizationids, 36133,16.864348,-99.881516,30);
		organizationids = addOrganizations("Chihuahua","31000","MX","MXN", 36188,28.636363,-106.088791,10);
		addProducts("Chihuahua","31000","MX","MXN", Data.PRODUCTS_01, organizationids, 36188,28.636363,-106.088791,10);
		organizationids = addOrganizations("Oaxaca","68000","MX","MXN", 36363,17.067287,-96.712646,10);
		addProducts("Oaxaca","68000","MX","MXN", Data.PRODUCTS_01, organizationids, 36363,17.067287,-96.712646,10);
		organizationids = addOrganizations("Cancún","77500","MX","MXN", 36130,21.160081,-86.845551,30);
		addProducts("Cancún","77500","MX","MXN", Data.PRODUCTS_01, organizationids, 36130,21.160081,-86.845551,30);
		organizationids = addOrganizations("Managua","N/A","NI","NIO", 36946,12.136341,-86.250916,5);
		addProducts("Managua","N/A","NI","NIO", Data.PRODUCTS_01, organizationids, 36946,12.136341,-86.250916,5);
		organizationids = addOrganizations("Panama City","N/A","PA","USD", 53941,8.994618,-79.518356,5);
		addProducts("Panama City","N/A","PA","USD", Data.PRODUCTS_01, organizationids, 53941,8.994618,-79.518356,5);
		organizationids = addOrganizations("Arecibo","612","PR","USD", 41150,18.473422,-66.715679,5);
		addProducts("Arecibo","612","PR","USD", Data.PRODUCTS_01, organizationids, 41150,18.473422,-66.715679,5);
		organizationids = addOrganizations("Ponce","730","PR","USD", 41199,18.01008,-66.621094,10);
		addProducts("Ponce","730","PR","USD", Data.PRODUCTS_01, organizationids, 41199,18.01008,-66.621094,10);
		organizationids = addOrganizations("Prague","110 00","CZ","CZK", 7917,50.088428,14.420929,20);
		addProducts("Prague","110 00","CZ","CZK", Data.PRODUCTS_01, organizationids, 7917,50.088428,14.420929,20);
		organizationids = addOrganizations("Valletta","VLT","MT","MTL", 36095,35.899341,14.516544,5);
		addProducts("Valletta","VLT","MT","MTL", Data.PRODUCTS_01, organizationids, 36095,35.899341,14.516544,5);
		organizationids = addOrganizations("Eisenstadt","7000","AT","EUR", 520,47.846229,16.528072,5);
		addProducts("Eisenstadt","7000","AT","EUR", Data.PRODUCTS_01, organizationids, 520,47.846229,16.528072,5);
		organizationids = addOrganizations("Klagenfurt","9010","AT","EUR", 665,46.723506,14.180775,20);
		addProducts("Klagenfurt","9010","AT","EUR", Data.PRODUCTS_01, organizationids, 665,46.723506,14.180775,20);
		organizationids = addOrganizations("Lienz","9900","AT","EUR", 721,46.827843,12.76268,5);
		addProducts("Lienz","9900","AT","EUR", Data.PRODUCTS_01, organizationids, 721,46.827843,12.76268,5);
		organizationids = addOrganizations("Sankt Pölten","3100","AT","EUR", 429,48.210032,16.369629,30);
		addProducts("Sankt Pölten","3100","AT","EUR", Data.PRODUCTS_01, organizationids, 429,48.210032,16.369629,30);
		organizationids = addOrganizations("Salzburg","5010","AT","EUR", 877,47.810388,13.055019,30);
		addProducts("Salzburg","5010","AT","EUR", Data.PRODUCTS_01, organizationids, 877,47.810388,13.055019,30);
		organizationids = addOrganizations("Graz","8010","AT","EUR", 583,47.074097,15.439224,30);
		addProducts("Graz","8010","AT","EUR", Data.PRODUCTS_01, organizationids, 583,47.074097,15.439224,30);
		organizationids = addOrganizations("Innsbruck","6010","AT","EUR", 638,47.27294,11.405182,30);
		addProducts("Innsbruck","6010","AT","EUR", Data.PRODUCTS_01, organizationids, 638,47.27294,11.405182,30);
		organizationids = addOrganizations("Linz","4010","AT","EUR", 725,48.311058,14.28566,20);
		addProducts("Linz","4010","AT","EUR", Data.PRODUCTS_01, organizationids, 725,48.311058,14.28566,20);
		organizationids = addOrganizations("Vienna","1010","AT","EUR", 8231,48.214608,16.374435,30);
		addProducts("Vienna","1010","AT","EUR", Data.PRODUCTS_01, organizationids, 8231,48.214608,16.374435,30);
		organizationids = addOrganizations("Bregenz","6900","AT","EUR", 488,47.502185,9.742212,20);
		addProducts("Bregenz","6900","AT","EUR", Data.PRODUCTS_01, organizationids, 488,47.502185,9.742212,20);
		organizationids = addOrganizations("Brussells","1000","BE","EUR", 2448,50.851041,4.350586,10);
		addProducts("Brussells","1000","BE","EUR", Data.PRODUCTS_01, organizationids, 2448,50.851041,4.350586,10);
		organizationids = addOrganizations("Split","21000","HR","HRK", 28631,43.515195,16.461639,5);
		addProducts("Split","21000","HR","HRK", Data.PRODUCTS_01, organizationids, 28631,43.515195,16.461639,5);
		organizationids = addOrganizations("Pore?","52440","HR","HRK", 28456,45.230415,13.605194,5);
		addProducts("Pore?","52440","HR","HRK", Data.PRODUCTS_01, organizationids, 28456,45.230415,13.605194,5);
		organizationids = addOrganizations("Pazin","52000","HR","HRK", 28578,45.239119,13.938904,5);
		addProducts("Pazin","52000","HR","HRK", Data.PRODUCTS_01, organizationids, 28578,45.239119,13.938904,5);
		organizationids = addOrganizations("Pula","52100","HR","HRK", 28597,44.895769,13.915558,10);
		addProducts("Pula","52100","HR","HRK", Data.PRODUCTS_01, organizationids, 28597,44.895769,13.915558,10);
		organizationids = addOrganizations("Kraljevica","51262","HR","HRK", 28527,45.27537,14.573364,10);
		addProducts("Kraljevica","51262","HR","HRK", Data.PRODUCTS_01, organizationids, 28527,45.27537,14.573364,10);
		organizationids = addOrganizations("Lovran","51415","HR","HRK", 28456,45.296143,14.272614,5);
		addProducts("Lovran","51415","HR","HRK", Data.PRODUCTS_01, organizationids, 28456,45.296143,14.272614,5);
		organizationids = addOrganizations("Rijeka","51000","HR","HRK", 28604,45.332599,14.436722,10);
		addProducts("Rijeka","51000","HR","HRK", Data.PRODUCTS_01, organizationids, 28604,45.332599,14.436722,10);
		organizationids = addOrganizations("Osijek","31000","HR","HRK", 28575,45.569352,18.675385,10);
		addProducts("Osijek","31000","HR","HRK", Data.PRODUCTS_01, organizationids, 28575,45.569352,18.675385,10);
		organizationids = addOrganizations("Zagreb","1000","HR","HRK", 28688,45.827842,15.974121,30);
		addProducts("Zagreb","1000","HR","HRK", Data.PRODUCTS_01, organizationids, 28688,45.827842,15.974121,30);
		organizationids = addOrganizations("Famagusta","5000","CY","CYP", 10520,35.1536,33.935394,10);
		addProducts("Famagusta","5000","CY","CYP", Data.PRODUCTS_01, organizationids, 10520,35.1536,33.935394,10);
		organizationids = addOrganizations("Kyrenia","9000","CY","CYP", 10524,35.302798,33.235703,5);
		addProducts("Kyrenia","9000","CY","CYP", Data.PRODUCTS_01, organizationids, 10524,35.302798,33.235703,5);
		organizationids = addOrganizations("Larnaca","6000","CY","CYP", 10525,34.918311,33.633099,5);
		addProducts("Larnaca","6000","CY","CYP", Data.PRODUCTS_01, organizationids, 10525,34.918311,33.633099,5);
		organizationids = addOrganizations("Limassol","3000","CY","CYP", 10527,34.710009,33.022842,10);
		addProducts("Limassol","3000","CY","CYP", Data.PRODUCTS_01, organizationids, 10527,34.710009,33.022842,10);
		organizationids = addOrganizations("Nicosia","1000","CY","CYP", 10529,35.173808,33.367538,10);
		addProducts("Nicosia","1000","CY","CYP", Data.PRODUCTS_01, organizationids, 10529,35.173808,33.367538,10);
		organizationids = addOrganizations("Paphos","8000","CY","CYP", 10530,34.774896,32.421684,10);
		addProducts("Paphos","8000","CY","CYP", Data.PRODUCTS_01, organizationids, 10530,34.774896,32.421684,10);
		organizationids = addOrganizations("Rønne","3700","DK","DKK", 15003,55.106659,14.710007,10);
		addProducts("Rønne","3700","DK","DKK", Data.PRODUCTS_01, organizationids, 15003,55.106659,14.710007,10);
		organizationids = addOrganizations("Copenhagen","1000","DK","DKK", 15003,55.679327,12.568359,20);
		addProducts("Copenhagen","1000","DK","DKK", Data.PRODUCTS_01, organizationids, 15003,55.679327,12.568359,20);
		organizationids = addOrganizations("Odense","5000","DK","DKK", 15318,55.411477,10.403366,10);
		addProducts("Odense","5000","DK","DKK", Data.PRODUCTS_01, organizationids, 15318,55.411477,10.403366,10);
		organizationids = addOrganizations("Aarhus","8000","DK","DKK", 15003,56.166773,10.205269,20);
		addProducts("Aarhus","8000","DK","DKK", Data.PRODUCTS_01, organizationids, 15003,56.166773,10.205269,20);
		organizationids = addOrganizations("Gedser","4874","DK","DKK", 15134,54.578032,11.931152,5);
		addProducts("Gedser","4874","DK","DKK", Data.PRODUCTS_01, organizationids, 15134,54.578032,11.931152,5);
		organizationids = addOrganizations("Helsingør","3000","DK","DKK", 15003,56.034459,12.592049,5);
		addProducts("Helsingør","3000","DK","DKK", Data.PRODUCTS_01, organizationids, 15003,56.034459,12.592049,5);
		organizationids = addOrganizations("Strasbourg","67000","FR","EUR", 23403,48.337995,7.443237,30);
		addProducts("Strasbourg","67000","FR","EUR", Data.PRODUCTS_01, organizationids, 23403,48.337995,7.443237,30);
		organizationids = addOrganizations("Périgueux","24000","FR","EUR", 17977,45.182037,0.714111,5);
		addProducts("Périgueux","24000","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,45.182037,0.714111,5);
		organizationids = addOrganizations("Bergerac","24100","FR","EUR", 18544,44.851975,0.472412,5);
		addProducts("Bergerac","24100","FR","EUR", Data.PRODUCTS_01, organizationids, 18544,44.851975,0.472412,5);
		organizationids = addOrganizations("Bordeaux","33000","FR","EUR", 18699,44.696847,-0.298004,20);
		addProducts("Bordeaux","33000","FR","EUR", Data.PRODUCTS_01, organizationids, 18699,44.696847,-0.298004,20);
		organizationids = addOrganizations("Mont-de-Marsan","40000","FR","EUR", 21547,43.893934,-0.505371,5);
		addProducts("Mont-de-Marsan","40000","FR","EUR", Data.PRODUCTS_01, organizationids, 21547,43.893934,-0.505371,5);
		organizationids = addOrganizations("Villeneuve-sur-Lot","47300","FR","EUR", 23906,44.41024,0.708618,10);
		addProducts("Villeneuve-sur-Lot","47300","FR","EUR", Data.PRODUCTS_01, organizationids, 23906,44.41024,0.708618,10);
		organizationids = addOrganizations("Bayonne","64100","FR","EUR", 18452,43.488798,-1.472168,5);
		addProducts("Bayonne","64100","FR","EUR", Data.PRODUCTS_01, organizationids, 18452,43.488798,-1.472168,5);
		organizationids = addOrganizations("Clermont-Ferrand","63000","FR","EUR", 19353,45.777341,3.087158,30);
		addProducts("Clermont-Ferrand","63000","FR","EUR", Data.PRODUCTS_01, organizationids, 19353,45.777341,3.087158,30);
		organizationids = addOrganizations("Dinan","22100","FR","EUR", 19635,48.458352,-2.04895,5);
		addProducts("Dinan","22100","FR","EUR", Data.PRODUCTS_01, organizationids, 19635,48.458352,-2.04895,5);
		organizationids = addOrganizations("Quimper","29000","FR","EUR", 22286,48.00095,-4.0979,5);
		addProducts("Quimper","29000","FR","EUR", Data.PRODUCTS_01, organizationids, 22286,48.00095,-4.0979,5);
		organizationids = addOrganizations("Brest","29200","FR","EUR", 6225,48.38909,-4.487915,10);
		addProducts("Brest","29200","FR","EUR", Data.PRODUCTS_01, organizationids, 6225,48.38909,-4.487915,10);
		organizationids = addOrganizations("Saint-Malo","35400","FR","EUR", 17977,48.647428,-2.026978,5);
		addProducts("Saint-Malo","35400","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,48.647428,-2.026978,5);
		organizationids = addOrganizations("Lorient","56100","FR","EUR", 21116,47.743017,-3.36731,10);
		addProducts("Lorient","56100","FR","EUR", Data.PRODUCTS_01, organizationids, 21116,47.743017,-3.36731,10);
		organizationids = addOrganizations("Rennes","35000","FR","EUR", 22347,48.118434,-1.675415,30);
		addProducts("Rennes","35000","FR","EUR", Data.PRODUCTS_01, organizationids, 22347,48.118434,-1.675415,30);
		organizationids = addOrganizations("Dijon","21000","FR","EUR", 19634,47.077604,4.411011,30);
		addProducts("Dijon","21000","FR","EUR", Data.PRODUCTS_01, organizationids, 19634,47.077604,4.411011,30);
		organizationids = addOrganizations("Troyes","10000","FR","EUR", 23635,48.296556,4.074726,30);
		addProducts("Troyes","10000","FR","EUR", Data.PRODUCTS_01, organizationids, 23635,48.296556,4.074726,30);
		organizationids = addOrganizations("Propriano","20110","FR","EUR", 22241,41.676502,8.904419,10);
		addProducts("Propriano","20110","FR","EUR", Data.PRODUCTS_01, organizationids, 22241,41.676502,8.904419,10);
		organizationids = addOrganizations("Cargèse","20130","FR","EUR", 17977,42.136423,8.598862,10);
		addProducts("Cargèse","20130","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,42.136423,8.598862,10);
		organizationids = addOrganizations("Bonifacio","20169","FR","EUR", 18685,41.369852,9.211693,10);
		addProducts("Bonifacio","20169","FR","EUR", Data.PRODUCTS_01, organizationids, 18685,41.369852,9.211693,10);
		organizationids = addOrganizations("Calvi","20260","FR","EUR", 18931,42.56623,8.751984,10);
		addProducts("Calvi","20260","FR","EUR", Data.PRODUCTS_01, organizationids, 18931,42.56623,8.751984,10);
		organizationids = addOrganizations("Ajaccio","20000","FR","EUR", 18029,41.919651,8.739281,10);
		addProducts("Ajaccio","20000","FR","EUR", Data.PRODUCTS_01, organizationids, 18029,41.919651,8.739281,10);
		organizationids = addOrganizations("Aix-en-Provence","13100","FR","EUR", 18026,43.53262,5.443726,10);
		addProducts("Aix-en-Provence","13100","FR","EUR", Data.PRODUCTS_01, organizationids, 18026,43.53262,5.443726,10);
		organizationids = addOrganizations("Toulon","83000","FR","EUR", 23562,43.129052,5.916138,10);
		addProducts("Toulon","83000","FR","EUR", Data.PRODUCTS_01, organizationids, 23562,43.129052,5.916138,10);
		organizationids = addOrganizations("Fréjus","83600","FR","EUR", 17977,43.436966,6.734619,5);
		addProducts("Fréjus","83600","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,43.436966,6.734619,5);
		organizationids = addOrganizations("Gap","5000","FR","EUR", 20031,44.559163,6.075439,5);
		addProducts("Gap","5000","FR","EUR", Data.PRODUCTS_01, organizationids, 20031,44.559163,6.075439,5);
		organizationids = addOrganizations("Cannes","6400","FR","EUR", 18949,43.552529,7.014771,10);
		addProducts("Cannes","6400","FR","EUR", Data.PRODUCTS_01, organizationids, 18949,43.552529,7.014771,10);
		organizationids = addOrganizations("Nice","6000","FR","EUR", 21795,43.696424,7.266083,10);
		addProducts("Nice","6000","FR","EUR", Data.PRODUCTS_01, organizationids, 21795,43.696424,7.266083,10);
		organizationids = addOrganizations("Marseille","13006","FR","EUR", 21319,43.297198,5.369568,2);
		addProducts("Marseille","13006","FR","EUR", Data.PRODUCTS_01, organizationids, 21319,43.297198,5.369568,2);
		organizationids = addOrganizations("Besancon","25000","FR","EUR", 18569,47.157972,6.056213,30);
		addProducts("Besancon","25000","FR","EUR", Data.PRODUCTS_01, organizationids, 18569,47.157972,6.056213,30);
		organizationids = addOrganizations("Rambouillet","78120","FR","EUR", 22306,48.649242,1.83197,10);
		addProducts("Rambouillet","78120","FR","EUR", Data.PRODUCTS_01, organizationids, 22306,48.649242,1.83197,10);
		organizationids = addOrganizations("L Isle-Adam","95290","FR","EUR", 17977,49.113883,2.224045,5);
		addProducts("L Isle-Adam","95290","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,49.113883,2.224045,5);
		organizationids = addOrganizations("Meaux","77100","FR","EUR", 21370,48.956777,2.879791,5);
		addProducts("Meaux","77100","FR","EUR", Data.PRODUCTS_01, organizationids, 21370,48.956777,2.879791,5);
		organizationids = addOrganizations("Paris","75000","FR","EUR", 7568,48.859294,2.351074,30);
		addProducts("Paris","75000","FR","EUR", Data.PRODUCTS_01, organizationids, 7568,48.859294,2.351074,30);
		organizationids = addOrganizations("Carcassonne","11000","FR","EUR", 18967,43.20918,2.351074,10);
		addProducts("Carcassonne","11000","FR","EUR", Data.PRODUCTS_01, organizationids, 18967,43.20918,2.351074,10);
		organizationids = addOrganizations("Narbonne","11100","FR","EUR", 21748,43.189158,2.999268,10);
		addProducts("Narbonne","11100","FR","EUR", Data.PRODUCTS_01, organizationids, 21748,43.189158,2.999268,10);
		organizationids = addOrganizations("Sète","34200","FR","EUR", 17977,43.401056,3.696899,10);
		addProducts("Sète","34200","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,43.401056,3.696899,10);
		organizationids = addOrganizations("Béziers","34500","FR","EUR", 17977,43.34915,3.218994,10);
		addProducts("Béziers","34500","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,43.34915,3.218994,10);
		organizationids = addOrganizations("Mende","48000","FR","EUR", 21382,44.516093,3.499146,5);
		addProducts("Mende","48000","FR","EUR", Data.PRODUCTS_01, organizationids, 21382,44.516093,3.499146,5);
		organizationids = addOrganizations("Montpellier","34000","FR","EUR", 21612,43.675818,3.240967,5);
		addProducts("Montpellier","34000","FR","EUR", Data.PRODUCTS_01, organizationids, 21612,43.675818,3.240967,5);
		organizationids = addOrganizations("Limoges","87000","FR","EUR", 21040,45.834062,1.261368,5);
		addProducts("Limoges","87000","FR","EUR", Data.PRODUCTS_01, organizationids, 21040,45.834062,1.261368,5);
		organizationids = addOrganizations("Tours","37000","FR","EUR", 23582,47.41322,0.696259,5);
		addProducts("Tours","37000","FR","EUR", Data.PRODUCTS_01, organizationids, 23582,47.41322,0.696259,5);
		organizationids = addOrganizations("Metz","57000","FR","EUR", 21422,49.119838,6.177063,5);
		addProducts("Metz","57000","FR","EUR", Data.PRODUCTS_01, organizationids, 21422,49.119838,6.177063,5);
		organizationids = addOrganizations("Cahors","46000","FR","EUR", 18920,44.441624,1.444702,5);
		addProducts("Cahors","46000","FR","EUR", Data.PRODUCTS_01, organizationids, 18920,44.441624,1.444702,5);
		organizationids = addOrganizations("Tarbes","65000","FR","EUR", 23460,43.229195,0.071411,5);
		addProducts("Tarbes","65000","FR","EUR", Data.PRODUCTS_01, organizationids, 23460,43.229195,0.071411,5);
		organizationids = addOrganizations("Albi","81000","FR","EUR", 1266,43.921637,2.147827,5);
		addProducts("Albi","81000","FR","EUR", Data.PRODUCTS_01, organizationids, 1266,43.921637,2.147827,5);
		organizationids = addOrganizations("Montauban","82000","FR","EUR", 21520,44.020472,1.345825,10);
		addProducts("Montauban","82000","FR","EUR", Data.PRODUCTS_01, organizationids, 21520,44.020472,1.345825,10);
		organizationids = addOrganizations("Toulouse","31000","FR","EUR", 23565,43.605505,1.444359,10);
		addProducts("Toulouse","31000","FR","EUR", Data.PRODUCTS_01, organizationids, 23565,43.605505,1.444359,10);
		organizationids = addOrganizations("Lille","59000","FR","EUR", 3106,50.629211,3.057289,20);
		addProducts("Lille","59000","FR","EUR", Data.PRODUCTS_01, organizationids, 3106,50.629211,3.057289,20);
		organizationids = addOrganizations("Coutances","50200","FR","EUR", 19497,49.04867,-1.439209,5);
		addProducts("Coutances","50200","FR","EUR", Data.PRODUCTS_01, organizationids, 19497,49.04867,-1.439209,5);
		organizationids = addOrganizations("Granville","50400","FR","EUR", 20145,48.839413,-1.598511,10);
		addProducts("Granville","50400","FR","EUR", Data.PRODUCTS_01, organizationids, 20145,48.839413,-1.598511,10);
		organizationids = addOrganizations("Caen","14000","FR","EUR", 18915,49.181703,-0.373535,10);
		addProducts("Caen","14000","FR","EUR", Data.PRODUCTS_01, organizationids, 18915,49.181703,-0.373535,10);
		organizationids = addOrganizations("Amiens","80000","FR","EUR", 18098,49.894413,2.295628,5);
		addProducts("Amiens","80000","FR","EUR", Data.PRODUCTS_01, organizationids, 18098,49.894413,2.295628,5);
		organizationids = addOrganizations("Cognac","16100","FR","EUR", 19369,45.69467,-0.32959,5);
		addProducts("Cognac","16100","FR","EUR", Data.PRODUCTS_01, organizationids, 19369,45.69467,-0.32959,5);
		organizationids = addOrganizations("Royan","17200","FR","EUR", 22482,45.629405,-1.049194,10);
		addProducts("Royan","17200","FR","EUR", Data.PRODUCTS_01, organizationids, 22482,45.629405,-1.049194,10);
		organizationids = addOrganizations("Poitiers","86000","FR","EUR", 22112,46.580457,0.340576,5);
		addProducts("Poitiers","86000","FR","EUR", Data.PRODUCTS_01, organizationids, 22112,46.580457,0.340576,5);
		organizationids = addOrganizations("Valence","26000","FR","EUR", 23681,44.933696,4.888916,5);
		addProducts("Valence","26000","FR","EUR", Data.PRODUCTS_01, organizationids, 23681,44.933696,4.888916,5);
		organizationids = addOrganizations("Saint-Étienne","42000","FR","EUR", 17977,45.448571,4.389038,10);
		addProducts("Saint-Étienne","42000","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,45.448571,4.389038,10);
		organizationids = addOrganizations("Lyon","69003","FR","EUR", 21180,45.767523,4.828491,10);
		addProducts("Lyon","69003","FR","EUR", Data.PRODUCTS_01, organizationids, 21180,45.767523,4.828491,10);
		organizationids = addOrganizations("Chambéry","73000","FR","EUR", 17977,45.56791,5.916138,10);
		addProducts("Chambéry","73000","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,45.56791,5.916138,10);
		organizationids = addOrganizations("Annecy","74000","FR","EUR", 18137,45.897655,6.124878,5);
		addProducts("Annecy","74000","FR","EUR", Data.PRODUCTS_01, organizationids, 18137,45.897655,6.124878,5);
		organizationids = addOrganizations("Grenoble","38000","FR","EUR", 20153,45.209134,5.449219,30);
		addProducts("Grenoble","38000","FR","EUR", Data.PRODUCTS_01, organizationids, 20153,45.209134,5.449219,30);
		organizationids = addOrganizations("Saint-Chamond","42400","FR","EUR", 17977,45.479392,4.509888,5);
		addProducts("Saint-Chamond","42400","FR","EUR", Data.PRODUCTS_01, organizationids, 17977,45.479392,4.509888,5);
		organizationids = addOrganizations("Montbrison","42600","FR","EUR", 21532,45.610195,4.053955,5);
		addProducts("Montbrison","42600","FR","EUR", Data.PRODUCTS_01, organizationids, 21532,45.610195,4.053955,5);
		organizationids = addOrganizations("Tarare","69170","FR","EUR", 23457,45.897655,4.432983,5);
		addProducts("Tarare","69170","FR","EUR", Data.PRODUCTS_01, organizationids, 23457,45.897655,4.432983,5);
		organizationids = addOrganizations("Roanne","42300","FR","EUR", 22398,46.035109,4.081421,10);
		addProducts("Roanne","42300","FR","EUR", Data.PRODUCTS_01, organizationids, 22398,46.035109,4.081421,10);
		organizationids = addOrganizations("Stuttgart","70182","DE","EUR", 14416,48.777687,9.181824,20);
		addProducts("Stuttgart","70182","DE","EUR", Data.PRODUCTS_01, organizationids, 14416,48.777687,9.181824,20);
		organizationids = addOrganizations("Munich","80337","DE","EUR", 11088,48.152344,11.561737,30);
		addProducts("Munich","80337","DE","EUR", Data.PRODUCTS_01, organizationids, 11088,48.152344,11.561737,30);
		organizationids = addOrganizations("Berlin","10119","DE","EUR", 11412,52.538779,13.40744,30);
		addProducts("Berlin","10119","DE","EUR", Data.PRODUCTS_01, organizationids, 11412,52.538779,13.40744,30);
		organizationids = addOrganizations("Potsdam","14473","DE","EUR", 13796,52.404095,13.074417,20);
		addProducts("Potsdam","14473","DE","EUR", Data.PRODUCTS_01, organizationids, 13796,52.404095,13.074417,20);
		organizationids = addOrganizations("Bremen","28219","DE","EUR", 11570,53.090725,8.819275,10);
		addProducts("Bremen","28219","DE","EUR", Data.PRODUCTS_01, organizationids, 11570,53.090725,8.819275,10);
		organizationids = addOrganizations("Hamburg","20354","DE","EUR", 12399,53.569676,9.996185,10);
		addProducts("Hamburg","20354","DE","EUR", Data.PRODUCTS_01, organizationids, 12399,53.569676,9.996185,10);
		organizationids = addOrganizations("Wiesbaden","65187","DE","EUR", 14853,50.067497,8.243866,10);
		addProducts("Wiesbaden","65187","DE","EUR", Data.PRODUCTS_01, organizationids, 14853,50.067497,8.243866,10);
		organizationids = addOrganizations("Cuxhaven","27474","DE","EUR", 11701,53.865486,8.690186,0);
		addProducts("Cuxhaven","27474","DE","EUR", Data.PRODUCTS_01, organizationids, 11701,53.865486,8.690186,0);
		organizationids = addOrganizations("Northeim","37154","DE","EUR", 13571,51.6998,9.992065,5);
		addProducts("Northeim","37154","DE","EUR", Data.PRODUCTS_01, organizationids, 13571,51.6998,9.992065,5);
		organizationids = addOrganizations("Wolfsburg","38448","DE","EUR", 14920,52.439269,10.772095,10);
		addProducts("Wolfsburg","38448","DE","EUR", Data.PRODUCTS_01, organizationids, 14920,52.439269,10.772095,10);
		organizationids = addOrganizations("Hanover","30159","DE","EUR", 6988,52.38943,9.72805,10);
		addProducts("Hanover","30159","DE","EUR", Data.PRODUCTS_01, organizationids, 6988,52.38943,9.72805,10);
		organizationids = addOrganizations("Neubrandenburg","17034","DE","EUR", 13434,53.566414,13.271484,5);
		addProducts("Neubrandenburg","17034","DE","EUR", Data.PRODUCTS_01, organizationids, 13434,53.566414,13.271484,5);
		organizationids = addOrganizations("Greifswald","17489","DE","EUR", 12276,54.081951,13.386841,10);
		addProducts("Greifswald","17489","DE","EUR", Data.PRODUCTS_01, organizationids, 12276,54.081951,13.386841,10);
		organizationids = addOrganizations("Ribnitz-Damgarten","18311","DE","EUR", 11088,54.242761,12.43103,10);
		addProducts("Ribnitz-Damgarten","18311","DE","EUR", Data.PRODUCTS_01, organizationids, 11088,54.242761,12.43103,10);
		organizationids = addOrganizations("Schwerin","19055","DE","EUR", 14216,53.631611,11.409302,5);
		addProducts("Schwerin","19055","DE","EUR", Data.PRODUCTS_01, organizationids, 14216,53.631611,11.409302,5);
		organizationids = addOrganizations("Rostock","18055","DE","EUR", 13995,54.090409,12.102814,10);
		addProducts("Rostock","18055","DE","EUR", Data.PRODUCTS_01, organizationids, 13995,54.090409,12.102814,10);
		organizationids = addOrganizations("Düsseldorf","40211","DE","EUR", 11088,51.227958,6.809464,20);
		addProducts("Düsseldorf","40211","DE","EUR", Data.PRODUCTS_01, organizationids, 11088,51.227958,6.809464,20);
		organizationids = addOrganizations("Mainz","55128","DE","EUR", 13166,49.996705,8.247299,10);
		addProducts("Mainz","55128","DE","EUR", Data.PRODUCTS_01, organizationids, 13166,49.996705,8.247299,10);
		organizationids = addOrganizations("Saarbrücken","66125","DE","EUR", 11088,49.242035,6.996918,5);
		addProducts("Saarbrücken","66125","DE","EUR", Data.PRODUCTS_01, organizationids, 11088,49.242035,6.996918,5);
		organizationids = addOrganizations("Dresden","1307","DE","EUR", 6751,51.036213,13.758316,10);
		addProducts("Dresden","1307","DE","EUR", Data.PRODUCTS_01, organizationids, 6751,51.036213,13.758316,10);
		organizationids = addOrganizations("Magdeburg","39108","DE","EUR", 13158,52.135174,11.629715,10);
		addProducts("Magdeburg","39108","DE","EUR", Data.PRODUCTS_01, organizationids, 13158,52.135174,11.629715,10);
		organizationids = addOrganizations("Lübeck","23552","DE","EUR", 11088,53.865486,10.689697,10);
		addProducts("Lübeck","23552","DE","EUR", Data.PRODUCTS_01, organizationids, 11088,53.865486,10.689697,10);
		organizationids = addOrganizations("Flensburg","24937","DE","EUR", 12048,54.797518,9.442749,5);
		addProducts("Flensburg","24937","DE","EUR", Data.PRODUCTS_01, organizationids, 12048,54.797518,9.442749,5);
		organizationids = addOrganizations("Husum","25813","DE","EUR", 12664,54.485996,9.052734,5);
		addProducts("Husum","25813","DE","EUR", Data.PRODUCTS_01, organizationids, 12664,54.485996,9.052734,5);
		organizationids = addOrganizations("Kiel","24103","DE","EUR", 12794,54.326735,10.124245,10);
		addProducts("Kiel","24103","DE","EUR", Data.PRODUCTS_01, organizationids, 12794,54.326735,10.124245,10);
		organizationids = addOrganizations("Erfurt","99086","DE","EUR", 11969,50.99215,11.027527,10);
		addProducts("Erfurt","99086","DE","EUR", Data.PRODUCTS_01, organizationids, 11969,50.99215,11.027527,10);
		organizationids = addOrganizations("Rhodes","851","GR","EUR", 2098,36.444002,28.227139,10);
		addProducts("Rhodes","851","GR","EUR", Data.PRODUCTS_01, organizationids, 2098,36.444002,28.227139,10);
		organizationids = addOrganizations("Heraklion","141","GR","EUR", 27920,35.332772,25.139122,5);
		addProducts("Heraklion","141","GR","EUR", Data.PRODUCTS_01, organizationids, 27920,35.332772,25.139122,5);
		organizationids = addOrganizations("Athens","100","GR","EUR", 45614,37.978033,23.734932,20);
		addProducts("Athens","100","GR","EUR", Data.PRODUCTS_01, organizationids, 45614,37.978033,23.734932,20);
		organizationids = addOrganizations(" Corfu City","491","GR","EUR", 27794,39.620764,19.918556,10);
		addProducts(" Corfu City","491","GR","EUR", Data.PRODUCTS_01, organizationids, 27794,39.620764,19.918556,10);
		organizationids = addOrganizations("Patras","261","GR","EUR", 28111,38.262446,21.739197,10);
		addProducts("Patras","261","GR","EUR", Data.PRODUCTS_01, organizationids, 28111,38.262446,21.739197,10);
		organizationids = addOrganizations("Poros","180 20","GR","EUR", 28126,37.544577,23.466797,5);
		addProducts("Poros","180 20","GR","EUR", Data.PRODUCTS_01, organizationids, 28126,37.544577,23.466797,5);
		organizationids = addOrganizations("Carlow","N/A","IE","EUR", 29475,52.836166,-6.924133,5);
		addProducts("Carlow","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29475,52.836166,-6.924133,5);
		organizationids = addOrganizations("Cavan","N/A","IE","EUR", 19031,53.995258,-7.36084,5);
		addProducts("Cavan","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 19031,53.995258,-7.36084,5);
		organizationids = addOrganizations("Ennis","N/A","IE","EUR", 29557,52.844668,-8.983383,10);
		addProducts("Ennis","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29557,52.844668,-8.983383,10);
		organizationids = addOrganizations("Cork","N/A","IE","EUR", 29524,51.8998,-8.466339,10);
		addProducts("Cork","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29524,51.8998,-8.466339,10);
		organizationids = addOrganizations("Donegal","N/A","IE","EUR", 29534,54.655563,-8.10997,5);
		addProducts("Donegal","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29534,54.655563,-8.10997,5);
		organizationids = addOrganizations("Dublin","N/A","IE","EUR", 6756,53.349732,-6.268387,10);
		addProducts("Dublin","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 6756,53.349732,-6.268387,10);
		organizationids = addOrganizations("Galway","N/A","IE","EUR", 29568,53.274042,-9.051704,10);
		addProducts("Galway","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29568,53.274042,-9.051704,10);
		organizationids = addOrganizations("Kilarney","N/A","IE","EUR", 29395,52.062623,-9.52446,20);
		addProducts("Kilarney","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29395,52.062623,-9.52446,20);
		organizationids = addOrganizations("Kildare","N/A","IE","EUR", 29594,53.159947,-6.908684,5);
		addProducts("Kildare","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29594,53.159947,-6.908684,5);
		organizationids = addOrganizations("Kilkenny","N/A","IE","EUR", 29595,52.65452,-7.247887,5);
		addProducts("Kilkenny","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29595,52.65452,-7.247887,5);
		organizationids = addOrganizations("Portlaoise","N/A","IE","EUR", 29666,53.034607,-7.302475,5);
		addProducts("Portlaoise","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29666,53.034607,-7.302475,5);
		organizationids = addOrganizations("Leitrim","N/A","IE","EUR", 29614,53.989505,-8.062763,10);
		addProducts("Leitrim","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29614,53.989505,-8.062763,10);
		organizationids = addOrganizations("Limerick","N/A","IE","EUR", 29618,52.664515,-8.625641,10);
		addProducts("Limerick","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29618,52.664515,-8.625641,10);
		organizationids = addOrganizations("Longford","N/A","IE","EUR", 1833,53.72617,-7.798748,5);
		addProducts("Longford","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 1833,53.72617,-7.798748,5);
		organizationids = addOrganizations("Louth","N/A","IE","EUR", 26144,53.952449,-6.538925,5);
		addProducts("Louth","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 26144,53.952449,-6.538925,5);
		organizationids = addOrganizations("Westport","N/A","IE","EUR", 8286,53.800043,-9.524117,10);
		addProducts("Westport","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 8286,53.800043,-9.524117,10);
		organizationids = addOrganizations("Navan","N/A","IE","EUR", 29649,53.654609,-6.681747,5);
		addProducts("Navan","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29649,53.654609,-6.681747,5);
		organizationids = addOrganizations("Monaghan","N/A","IE","EUR", 29636,54.248779,-6.969109,5);
		addProducts("Monaghan","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29636,54.248779,-6.969109,5);
		organizationids = addOrganizations("Tullamore","N/A","IE","EUR", 29720,53.274863,-7.492332,5);
		addProducts("Tullamore","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29720,53.274863,-7.492332,5);
		organizationids = addOrganizations("Rosscommon","N/A","IE","EUR", 29395,53.628557,-8.187904,5);
		addProducts("Rosscommon","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29395,53.628557,-8.187904,5);
		organizationids = addOrganizations("Silgo","N/A","IE","EUR", 29395,54.27164,-8.471832,5);
		addProducts("Silgo","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29395,54.27164,-8.471832,5);
		organizationids = addOrganizations("Tipperary","N/A","IE","EUR", 29714,52.473423,-8.162155,5);
		addProducts("Tipperary","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29714,52.473423,-8.162155,5);
		organizationids = addOrganizations("Waterford","N/A","IE","EUR", 8257,52.257441,-7.128754,10);
		addProducts("Waterford","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 8257,52.257441,-7.128754,10);
		organizationids = addOrganizations("Mullingar","N/A","IE","EUR", 29645,53.524187,-7.34127,5);
		addProducts("Mullingar","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29645,53.524187,-7.34127,5);
		organizationids = addOrganizations("Wexford","N/A","IE","EUR", 29730,52.339535,-6.459274,5);
		addProducts("Wexford","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29730,52.339535,-6.459274,5);
		organizationids = addOrganizations("Wicklow","N/A","IE","EUR", 29732,52.980586,-6.042824,5);
		addProducts("Wicklow","N/A","IE","EUR", Data.PRODUCTS_01, organizationids, 29732,52.980586,-6.042824,5);
		organizationids = addOrganizations("Scanno","67038","IT","EUR", 30598,41.902277,13.882599,20);
		addProducts("Scanno","67038","IT","EUR", Data.PRODUCTS_01, organizationids, 30598,41.902277,13.882599,20);
		organizationids = addOrganizations("Bari","70132","IT","EUR", 30788,41.128375,16.86676,20);
		addProducts("Bari","70132","IT","EUR", Data.PRODUCTS_01, organizationids, 30788,41.128375,16.86676,20);
		organizationids = addOrganizations("Potenza","85100","IT","EUR", 32450,40.632975,15.809326,10);
		addProducts("Potenza","85100","IT","EUR", Data.PRODUCTS_01, organizationids, 32450,40.632975,15.809326,10);
		organizationids = addOrganizations("Catanzaro","88100","IT","EUR", 31224,38.907332,16.590385,10);
		addProducts("Catanzaro","88100","IT","EUR", Data.PRODUCTS_01, organizationids, 31224,38.907332,16.590385,10);
		organizationids = addOrganizations("Caserta","81100","IT","EUR", 31118,41.079351,14.326172,0);
		addProducts("Caserta","81100","IT","EUR", Data.PRODUCTS_01, organizationids, 31118,41.079351,14.326172,0);
		organizationids = addOrganizations("Salerno","84124","IT","EUR", 32623,40.680638,14.760132,0);
		addProducts("Salerno","84124","IT","EUR", Data.PRODUCTS_01, organizationids, 32623,40.680638,14.760132,0);
		organizationids = addOrganizations("Naples","80136","IT","EUR", 53042,40.850177,14.268494,50);
		addProducts("Naples","80136","IT","EUR", Data.PRODUCTS_01, organizationids, 53042,40.850177,14.268494,50);
		organizationids = addOrganizations("Bologna","40124","IT","EUR", 30852,44.492587,11.335144,20);
		addProducts("Bologna","40124","IT","EUR", Data.PRODUCTS_01, organizationids, 30852,44.492587,11.335144,20);
		organizationids = addOrganizations("Undine","33100","IT","EUR", 30598,46.068467,13.239899,10);
		addProducts("Undine","33100","IT","EUR", Data.PRODUCTS_01, organizationids, 30598,46.068467,13.239899,10);
		organizationids = addOrganizations("Genoa","16145","IT","EUR", 31634,44.402392,8.937378,50);
		addProducts("Genoa","16145","IT","EUR", Data.PRODUCTS_01, organizationids, 31634,44.402392,8.937378,50);
		organizationids = addOrganizations("La Spezia","19124","IT","EUR", 31735,44.111254,9.827271,0);
		addProducts("La Spezia","19124","IT","EUR", Data.PRODUCTS_01, organizationids, 31735,44.111254,9.827271,0);
		organizationids = addOrganizations("Monza","20900","IT","EUR", 32087,45.590978,9.261475,5);
		addProducts("Monza","20900","IT","EUR", Data.PRODUCTS_01, organizationids, 32087,45.590978,9.261475,5);
		organizationids = addOrganizations("Milan","20151","IT","EUR", 52519,45.467836,9.181824,50);
		addProducts("Milan","20151","IT","EUR", Data.PRODUCTS_01, organizationids, 52519,45.467836,9.181824,50);
		organizationids = addOrganizations("Ancona","60122","IT","EUR", 30674,43.616194,13.50769,20);
		addProducts("Ancona","60122","IT","EUR", Data.PRODUCTS_01, organizationids, 30674,43.616194,13.50769,20);
		organizationids = addOrganizations("Termoli","86039","IT","EUR", 33004,42.000835,14.989643,5);
		addProducts("Termoli","86039","IT","EUR", Data.PRODUCTS_01, organizationids, 33004,42.000835,14.989643,5);
		organizationids = addOrganizations("Turin","10151","IT","EUR", 58184,45.0774,7.679443,10);
		addProducts("Turin","10151","IT","EUR", Data.PRODUCTS_01, organizationids, 58184,45.0774,7.679443,10);
		organizationids = addOrganizations("Fiumicino","54","IT","EUR", 31532,41.779505,12.249756,10);
		addProducts("Fiumicino","54","IT","EUR", Data.PRODUCTS_01, organizationids, 31532,41.779505,12.249756,10);
		organizationids = addOrganizations("Viterbo","1100","IT","EUR", 33255,42.415346,12.084961,5);
		addProducts("Viterbo","1100","IT","EUR", Data.PRODUCTS_01, organizationids, 33255,42.415346,12.084961,5);
		organizationids = addOrganizations("Rome","185","IT","EUR", 55106,41.893077,12.495575,50);
		addProducts("Rome","185","IT","EUR", Data.PRODUCTS_01, organizationids, 55106,41.893077,12.495575,50);
		organizationids = addOrganizations("San Marino","47890","IT","EUR", 32693,43.932146,12.448711,5);
		addProducts("San Marino","47890","IT","EUR", Data.PRODUCTS_01, organizationids, 32693,43.932146,12.448711,5);
		organizationids = addOrganizations("Olbia","7026","IT","EUR", 32174,40.930115,9.492188,10);
		addProducts("Olbia","7026","IT","EUR", Data.PRODUCTS_01, organizationids, 32174,40.930115,9.492188,10);
		organizationids = addOrganizations("Sassari","7100","IT","EUR", 32827,40.730608,8.547363,10);
		addProducts("Sassari","7100","IT","EUR", Data.PRODUCTS_01, organizationids, 32827,40.730608,8.547363,10);
		organizationids = addOrganizations("Cagliari","9122","IT","EUR", 30973,39.219487,9.107666,20);
		addProducts("Cagliari","9122","IT","EUR", Data.PRODUCTS_01, organizationids, 30973,39.219487,9.107666,20);
		organizationids = addOrganizations("Syracuse","96100","IT","EUR", 56440,37.066136,15.285416,10);
		addProducts("Syracuse","96100","IT","EUR", Data.PRODUCTS_01, organizationids, 56440,37.066136,15.285416,10);
		organizationids = addOrganizations("Catania","95122","IT","EUR", 31223,37.509726,15.073242,10);
		addProducts("Catania","95122","IT","EUR", Data.PRODUCTS_01, organizationids, 31223,37.509726,15.073242,10);
		organizationids = addOrganizations("Palmero","90138","IT","EUR", 30598,38.123754,13.364868,20);
		addProducts("Palmero","90138","IT","EUR", Data.PRODUCTS_01, organizationids, 30598,38.123754,13.364868,20);
		organizationids = addOrganizations("Trento","38100","IT","EUR", 33071,46.06942,11.112671,10);
		addProducts("Trento","38100","IT","EUR", Data.PRODUCTS_01, organizationids, 33071,46.06942,11.112671,10);
		organizationids = addOrganizations("Massa","54100","IT","EUR", 31939,44.048116,10.129395,10);
		addProducts("Massa","54100","IT","EUR", Data.PRODUCTS_01, organizationids, 31939,44.048116,10.129395,10);
		organizationids = addOrganizations("Viareggio","55049","IT","EUR", 33182,43.874138,10.26123,10);
		addProducts("Viareggio","55049","IT","EUR", Data.PRODUCTS_01, organizationids, 33182,43.874138,10.26123,10);
		organizationids = addOrganizations("Sienna","56040","IT","EUR", 30598,43.317185,11.326904,10);
		addProducts("Sienna","56040","IT","EUR", Data.PRODUCTS_01, organizationids, 30598,43.317185,11.326904,10);
		organizationids = addOrganizations("Grosseto","58100","IT","EUR", 31691,42.771211,11.107178,10);
		addProducts("Grosseto","58100","IT","EUR", Data.PRODUCTS_01, organizationids, 31691,42.771211,11.107178,10);
		organizationids = addOrganizations("Pisa","56127","IT","EUR", 32348,43.715535,10.38208,10);
		addProducts("Pisa","56127","IT","EUR", Data.PRODUCTS_01, organizationids, 32348,43.715535,10.38208,10);
		organizationids = addOrganizations("Florence","50121","IT","EUR", 6836,43.779027,11.239014,50);
		addProducts("Florence","50121","IT","EUR", Data.PRODUCTS_01, organizationids, 6836,43.779027,11.239014,50);
		organizationids = addOrganizations("Perugia","6121","IT","EUR", 32294,43.113014,12.387085,20);
		addProducts("Perugia","6121","IT","EUR", Data.PRODUCTS_01, organizationids, 32294,43.113014,12.387085,20);
		organizationids = addOrganizations("Saint-Marcel","11020","IT","EUR", 30598,45.733984,7.46521,10);
		addProducts("Saint-Marcel","11020","IT","EUR", Data.PRODUCTS_01, organizationids, 30598,45.733984,7.46521,10);
		organizationids = addOrganizations("Vincenza","36100","IT","EUR", 30598,45.548679,11.535645,10);
		addProducts("Vincenza","36100","IT","EUR", Data.PRODUCTS_01, organizationids, 30598,45.548679,11.535645,10);
		organizationids = addOrganizations("Venice","30125","IT","EUR", 56985,45.437008,12.332153,20);
		addProducts("Venice","30125","IT","EUR", Data.PRODUCTS_01, organizationids, 56985,45.437008,12.332153,20);
		organizationids = addOrganizations("Monte Carlo","98000","MC","EUR", 35786,43.739848,7.426414,10);
		addProducts("Monte Carlo","98000","MC","EUR", Data.PRODUCTS_01, organizationids, 35786,43.739848,7.426414,10);
		organizationids = addOrganizations("Assen","9400","NL","EUR", 37019,52.991644,6.564331,5);
		addProducts("Assen","9400","NL","EUR", Data.PRODUCTS_01, organizationids, 37019,52.991644,6.564331,5);
		organizationids = addOrganizations("Emmeloord","8300","NL","EUR", 37266,52.713003,5.751343,5);
		addProducts("Emmeloord","8300","NL","EUR", Data.PRODUCTS_01, organizationids, 37266,52.713003,5.751343,5);
		organizationids = addOrganizations("Leeuwarden","8900","NL","EUR", 37561,53.196161,5.806274,10);
		addProducts("Leeuwarden","8900","NL","EUR", Data.PRODUCTS_01, organizationids, 37561,53.196161,5.806274,10);
		organizationids = addOrganizations("Arnhem","6800","NL","EUR", 37017,51.98488,5.899658,5);
		addProducts("Arnhem","6800","NL","EUR", Data.PRODUCTS_01, organizationids, 37017,51.98488,5.899658,5);
		organizationids = addOrganizations("Groningen","9700","NL","EUR", 37344,53.220013,6.566734,5);
		addProducts("Groningen","9700","NL","EUR", Data.PRODUCTS_01, organizationids, 37344,53.220013,6.566734,5);
		organizationids = addOrganizations("Venlo","5900","NL","EUR", 38026,51.375209,6.174316,5);
		addProducts("Venlo","5900","NL","EUR", Data.PRODUCTS_01, organizationids, 38026,51.375209,6.174316,5);
		organizationids = addOrganizations("Eindhoven","5600","NL","EUR", 37258,51.442025,5.473938,10);
		addProducts("Eindhoven","5600","NL","EUR", Data.PRODUCTS_01, organizationids, 37258,51.442025,5.473938,10);
		organizationids = addOrganizations("Amsterdam","1000","NL","EUR", 37004,52.368892,4.949341,20);
		addProducts("Amsterdam","1000","NL","EUR", Data.PRODUCTS_01, organizationids, 37004,52.368892,4.949341,20);
		organizationids = addOrganizations("Zwolle","8000","NL","EUR", 38202,52.521235,6.089172,5);
		addProducts("Zwolle","8000","NL","EUR", Data.PRODUCTS_01, organizationids, 38202,52.521235,6.089172,5);
		organizationids = addOrganizations("Rotterdam","3000","NL","EUR", 37848,51.927331,4.476929,20);
		addProducts("Rotterdam","3000","NL","EUR", Data.PRODUCTS_01, organizationids, 37848,51.927331,4.476929,20);
		organizationids = addOrganizations("Utrecht","3500","NL","EUR", 38003,52.090476,5.110016,10);
		addProducts("Utrecht","3500","NL","EUR", Data.PRODUCTS_01, organizationids, 38003,52.090476,5.110016,10);
		organizationids = addOrganizations("Middelburg","4300","NL","EUR", 3232,51.498485,3.619995,20);
		addProducts("Middelburg","4300","NL","EUR", Data.PRODUCTS_01, organizationids, 3232,51.498485,3.619995,20);
		organizationids = addOrganizations("Évora","7000","PT","EUR", 41220,38.572864,-7.908783,10);
		addProducts("Évora","7000","PT","EUR", Data.PRODUCTS_01, organizationids, 41220,38.572864,-7.908783,10);
		organizationids = addOrganizations("Albufeira","8200","PT","EUR", 41236,37.092431,-8.253479,10);
		addProducts("Albufeira","8200","PT","EUR", Data.PRODUCTS_01, organizationids, 41236,37.092431,-8.253479,10);
		organizationids = addOrganizations("Portimao","8500","PT","EUR", 41616,37.134593,-8.541527,10);
		addProducts("Portimao","8500","PT","EUR", Data.PRODUCTS_01, organizationids, 41616,37.134593,-8.541527,10);
		organizationids = addOrganizations("Lagos","8600","PT","EUR", 36907,37.099551,-8.677139,10);
		addProducts("Lagos","8600","PT","EUR", Data.PRODUCTS_01, organizationids, 36907,37.099551,-8.677139,10);
		organizationids = addOrganizations("Sagres","8650","PT","EUR", 41220,37.009133,-8.940125,5);
		addProducts("Sagres","8650","PT","EUR", Data.PRODUCTS_01, organizationids, 41220,37.009133,-8.940125,5);
		organizationids = addOrganizations("Faro","8000","PT","EUR", 6824,37.015164,-7.934189,20);
		addProducts("Faro","8000","PT","EUR", Data.PRODUCTS_01, organizationids, 6824,37.015164,-7.934189,20);
		organizationids = addOrganizations("Horta ","9997","PT","EUR", 41463,38.52883,-28.627625,5);
		addProducts("Horta ","9997","PT","EUR", Data.PRODUCTS_01, organizationids, 41463,38.52883,-28.627625,5);
		organizationids = addOrganizations("Coimbra","3000","PT","EUR", 41387,40.212965,-8.42926,20);
		addProducts("Coimbra","3000","PT","EUR", Data.PRODUCTS_01, organizationids, 41387,40.212965,-8.42926,20);
		organizationids = addOrganizations("Lisbon","1149","PT","EUR", 51630,38.708554,-9.137878,30);
		addProducts("Lisbon","1149","PT","EUR", Data.PRODUCTS_01, organizationids, 51630,38.708554,-9.137878,30);
		organizationids = addOrganizations("Funchal","9060","PT","EUR", 41443,32.639375,-16.940918,10);
		addProducts("Funchal","9060","PT","EUR", Data.PRODUCTS_01, organizationids, 41443,32.639375,-16.940918,10);
		organizationids = addOrganizations("Porto","4000","PT","EUR", 41617,41.149706,-8.618774,20);
		addProducts("Porto","4000","PT","EUR", Data.PRODUCTS_01, organizationids, 41617,41.149706,-8.618774,20);
		organizationids = addOrganizations("Santa Pola","3130","ES","EUR", 17162,38.195022,-0.549316,5);
		addProducts("Santa Pola","3130","ES","EUR", Data.PRODUCTS_01, organizationids, 17162,38.195022,-0.549316,5);
		organizationids = addOrganizations("Torrevieja","3180","ES","EUR", 17267,37.978845,-0.681152,10);
		addProducts("Torrevieja","3180","ES","EUR", Data.PRODUCTS_01, organizationids, 17267,37.978845,-0.681152,10);
		organizationids = addOrganizations("Xàbia","3730","ES","EUR", 15754,38.789148,0.166512,5);
		addProducts("Xàbia","3730","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,38.789148,0.166512,5);
		organizationids = addOrganizations("Benidorm","3503","ES","EUR", 16024,38.535276,-0.126343,10);
		addProducts("Benidorm","3503","ES","EUR", Data.PRODUCTS_01, organizationids, 16024,38.535276,-0.126343,10);
		organizationids = addOrganizations("Alicante","3000","ES","EUR", 15869,38.350273,-0.483398,20);
		addProducts("Alicante","3000","ES","EUR", Data.PRODUCTS_01, organizationids, 15869,38.350273,-0.483398,20);
		organizationids = addOrganizations("Cadiz","11012","ES","EUR", 16093,36.536123,-6.295166,20);
		addProducts("Cadiz","11012","ES","EUR", Data.PRODUCTS_01, organizationids, 16093,36.536123,-6.295166,20);
		organizationids = addOrganizations("Cordova","14002","ES","EUR", 47688,37.909534,-4.790039,10);
		addProducts("Cordova","14002","ES","EUR", Data.PRODUCTS_01, organizationids, 47688,37.909534,-4.790039,10);
		organizationids = addOrganizations("Ejido","29100","ES","EUR", 16306,36.75649,-3.02124,10);
		addProducts("Ejido","29100","ES","EUR", Data.PRODUCTS_01, organizationids, 16306,36.75649,-3.02124,10);
		organizationids = addOrganizations("Seville","41004","ES","EUR", 55594,37.396346,-5.998535,0);
		addProducts("Seville","41004","ES","EUR", Data.PRODUCTS_01, organizationids, 55594,37.396346,-5.998535,0);
		organizationids = addOrganizations("Almería","4001","ES","EUR", 15754,36.844461,-2.48291,10);
		addProducts("Almería","4001","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,36.844461,-2.48291,10);
		organizationids = addOrganizations("Malaga","29002","ES","EUR", 16670,36.73008,-4.438477,20);
		addProducts("Malaga","29002","ES","EUR", Data.PRODUCTS_01, organizationids, 16670,36.73008,-4.438477,20);
		organizationids = addOrganizations("Saragossa","50002","ES","EUR", 15754,41.664705,-0.878906,10);
		addProducts("Saragossa","50002","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,41.664705,-0.878906,10);
		organizationids = addOrganizations("Gijon","33205","ES","EUR", 16406,43.542576,-5.668945,10);
		addProducts("Gijon","33205","ES","EUR", Data.PRODUCTS_01, organizationids, 16406,43.542576,-5.668945,10);
		organizationids = addOrganizations("Capdepera","7580","ES","EUR", 15754,39.707187,3.433228,5);
		addProducts("Capdepera","7580","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,39.707187,3.433228,5);
		organizationids = addOrganizations("Mahon","7700","ES","EUR", 16667,39.888665,4.262695,10);
		addProducts("Mahon","7700","ES","EUR", Data.PRODUCTS_01, organizationids, 16667,39.888665,4.262695,10);
		organizationids = addOrganizations("Menorca","7700","ES","EUR", 15754,40.002372,3.839722,10);
		addProducts("Menorca","7700","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,40.002372,3.839722,10);
		organizationids = addOrganizations("Ibiza Town","7800","ES","EUR", 15754,38.912407,1.428223,10);
		addProducts("Ibiza Town","7800","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,38.912407,1.428223,10);
		organizationids = addOrganizations("St Antoni","7820","ES","EUR", 15754,38.980763,1.304626,5);
		addProducts("St Antoni","7820","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,38.980763,1.304626,5);
		organizationids = addOrganizations("Palma","7006","ES","EUR", 36799,39.58029,2.642212,10);
		addProducts("Palma","7006","ES","EUR", Data.PRODUCTS_01, organizationids, 36799,39.58029,2.642212,10);
		organizationids = addOrganizations("Bilbao","48005","ES","EUR", 16053,43.257206,-2.927856,10);
		addProducts("Bilbao","48005","ES","EUR", Data.PRODUCTS_01, organizationids, 16053,43.257206,-2.927856,10);
		organizationids = addOrganizations("Santander","39003","ES","EUR", 17163,43.468868,-3.812256,10);
		addProducts("Santander","39003","ES","EUR", Data.PRODUCTS_01, organizationids, 17163,43.468868,-3.812256,10);
		organizationids = addOrganizations("Albacete","2001","ES","EUR", 15789,39.00211,-1.867676,10);
		addProducts("Albacete","2001","ES","EUR", Data.PRODUCTS_01, organizationids, 15789,39.00211,-1.867676,10);
		organizationids = addOrganizations("Valladolid","47620","ES","EUR", 17310,41.664705,-4.746094,10);
		addProducts("Valladolid","47620","ES","EUR", Data.PRODUCTS_01, organizationids, 17310,41.664705,-4.746094,10);
		organizationids = addOrganizations("Roses","17480","ES","EUR", 19314,42.269179,3.175049,5);
		addProducts("Roses","17480","ES","EUR", Data.PRODUCTS_01, organizationids, 19314,42.269179,3.175049,5);
		organizationids = addOrganizations("Lerida","25005","ES","EUR", 16610,41.619549,0.626221,5);
		addProducts("Lerida","25005","ES","EUR", Data.PRODUCTS_01, organizationids, 16610,41.619549,0.626221,5);
		organizationids = addOrganizations("Tarragona","43004","ES","EUR", 17228,41.118676,1.211243,10);
		addProducts("Tarragona","43004","ES","EUR", Data.PRODUCTS_01, organizationids, 17228,41.118676,1.211243,10);
		organizationids = addOrganizations("Manresa","8242","ES","EUR", 16685,41.725462,1.823215,10);
		addProducts("Manresa","8242","ES","EUR", Data.PRODUCTS_01, organizationids, 16685,41.725462,1.823215,10);
		organizationids = addOrganizations("Barcelona","8028","ES","EUR", 15996,41.409776,2.142334,30);
		addProducts("Barcelona","8028","ES","EUR", Data.PRODUCTS_01, organizationids, 15996,41.409776,2.142334,30);
		organizationids = addOrganizations("Caceres","10005","ES","EUR", 4483,39.478606,-6.394043,5);
		addProducts("Caceres","10005","ES","EUR", Data.PRODUCTS_01, organizationids, 4483,39.478606,-6.394043,5);
		organizationids = addOrganizations("Vigo","36205","ES","EUR", 17341,42.244785,-8.712158,10);
		addProducts("Vigo","36205","ES","EUR", Data.PRODUCTS_01, organizationids, 17341,42.244785,-8.712158,10);
		organizationids = addOrganizations("Madrid","28046","ES","EUR", 16665,40.419769,-3.698273,20);
		addProducts("Madrid","28046","ES","EUR", Data.PRODUCTS_01, organizationids, 16665,40.419769,-3.698273,20);
		organizationids = addOrganizations("Murcia","30153","ES","EUR", 16795,37.986693,-1.131935,20);
		addProducts("Murcia","30153","ES","EUR", Data.PRODUCTS_01, organizationids, 16795,37.986693,-1.131935,20);
		organizationids = addOrganizations("Pampalona","31003","ES","EUR", 15754,42.827639,-1.642456,10);
		addProducts("Pampalona","31003","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,42.827639,-1.642456,10);
		organizationids = addOrganizations("Logroño","26001","ES","EUR", 15754,42.466525,-2.449265,5);
		addProducts("Logroño","26001","ES","EUR", Data.PRODUCTS_01, organizationids, 15754,42.466525,-2.449265,5);
		organizationids = addOrganizations("Valencia","46006","ES","EUR", 17302,39.470125,-0.384521,20);
		addProducts("Valencia","46006","ES","EUR", Data.PRODUCTS_01, organizationids, 17302,39.470125,-0.384521,20);
		organizationids = addOrganizations("Basel","4000","CH","CHF", 8533,47.558226,7.592583,10);
		addProducts("Basel","4000","CH","CHF", Data.PRODUCTS_01, organizationids, 8533,47.558226,7.592583,10);
		organizationids = addOrganizations("Thun","3600","CH","CHF", 9058,46.748683,7.6264,10);
		addProducts("Thun","3600","CH","CHF", Data.PRODUCTS_01, organizationids, 9058,46.748683,7.6264,10);
		organizationids = addOrganizations("Lucerne","6000","CH","CHF", 51813,47.058896,8.322144,10);
		addProducts("Lucerne","6000","CH","CHF", Data.PRODUCTS_01, organizationids, 51813,47.058896,8.322144,10);
		organizationids = addOrganizations("St Gallen","9000","CH","CHF", 9020,47.424371,9.371166,10);
		addProducts("St Gallen","9000","CH","CHF", Data.PRODUCTS_01, organizationids, 9020,47.424371,9.371166,10);
		organizationids = addOrganizations("Geneva","1211","CH","CHF", 49432,46.198547,6.142387,10);
		addProducts("Geneva","1211","CH","CHF", Data.PRODUCTS_01, organizationids, 49432,46.198547,6.142387,10);
		organizationids = addOrganizations("Chur","7000","CH","CHF", 8601,46.859252,9.519653,10);
		addProducts("Chur","7000","CH","CHF", Data.PRODUCTS_01, organizationids, 8601,46.859252,9.519653,10);
		organizationids = addOrganizations("Fribourg","1700","CH","CHF", 8687,46.80382,7.146606,10);
		addProducts("Fribourg","1700","CH","CHF", Data.PRODUCTS_01, organizationids, 8687,46.80382,7.146606,10);
		organizationids = addOrganizations("Schwyz","6400","CH","CHF", 8998,47.020525,8.64727,5);
		addProducts("Schwyz","6400","CH","CHF", Data.PRODUCTS_01, organizationids, 8998,47.020525,8.64727,5);
		organizationids = addOrganizations("Bellinzona","6500","CH","CHF", 8538,46.198607,9.028015,10);
		addProducts("Bellinzona","6500","CH","CHF", Data.PRODUCTS_01, organizationids, 8538,46.198607,9.028015,10);
		organizationids = addOrganizations("Sion","1900","CH","CHF", 9008,46.229609,7.362041,10);
		addProducts("Sion","1900","CH","CHF", Data.PRODUCTS_01, organizationids, 9008,46.229609,7.362041,10);
		organizationids = addOrganizations("Lausanne","1000","CH","CHF", 8791,46.520131,6.633682,10);
		addProducts("Lausanne","1000","CH","CHF", Data.PRODUCTS_01, organizationids, 8791,46.520131,6.633682,10);
		organizationids = addOrganizations("Zurich","8000","CH","CHF", 8350,47.369292,8.53981,10);
		addProducts("Zurich","8000","CH","CHF", Data.PRODUCTS_01, organizationids, 8350,47.369292,8.53981,10);
		organizationids = addOrganizations("Cihanbeyli","42850","TR","TRL", 44504,38.668356,32.915039,5);
		addProducts("Cihanbeyli","42850","TR","TRL", Data.PRODUCTS_01, organizationids, 44504,38.668356,32.915039,5);
		organizationids = addOrganizations("Istanbul","34122","TR","TRL", 44633,41.006848,28.977814,10);
		addProducts("Istanbul","34122","TR","TRL", Data.PRODUCTS_01, organizationids, 44633,41.006848,28.977814,10);
		organizationids = addOrganizations("Fethiye","48300","TR","TRL", 44568,36.646385,29.137115,20);
		addProducts("Fethiye","48300","TR","TRL", Data.PRODUCTS_01, organizationids, 44568,36.646385,29.137115,20);
		organizationids = addOrganizations("Izmir","35050","TR","TRL", 44635,38.418897,27.129021,10);
		addProducts("Izmir","35050","TR","TRL", Data.PRODUCTS_01, organizationids, 44635,38.418897,27.129021,10);
		organizationids = addOrganizations("Antayla","7000","TR","TRL", 44376,36.889506,30.690308,20);
		addProducts("Antayla","7000","TR","TRL", Data.PRODUCTS_01, organizationids, 44376,36.889506,30.690308,20);
		organizationids = addOrganizations("Telaviv","61999","IL","ILS", 29734,32.068611,34.777222,10);
		addProducts("Telaviv","61999","IL","ILS", Data.PRODUCTS_01, organizationids, 29734,32.068611,34.777222,10);
		organizationids = addOrganizations("Dubai","N/A","AE","AED", 24,25.244696,55.327148,10);
		addProducts("Dubai","N/A","AE","AED", Data.PRODUCTS_01, organizationids, 24,25.244696,55.327148,10);
		organizationids = addOrganizations("Edmonton","AB T5J 4K2","CA","CAD", 6773,53.544796,-113.490829,10);
		addProducts("Edmonton","AB T5J 4K2","CA","CAD", Data.PRODUCTS_01, organizationids, 6773,53.544796,-113.490829,10);
		organizationids = addOrganizations("Vancouver","BC V5Y 1R3","CA","CAD", 8209,49.261755,-123.113136,20);
		addProducts("Vancouver","BC V5Y 1R3","CA","CAD", Data.PRODUCTS_01, organizationids, 8209,49.261755,-123.113136,20);
		organizationids = addOrganizations("Winnipeg","MB R3B 0S1","CA","CAD", 8323,49.900827,-97.136993,5);
		addProducts("Winnipeg","MB R3B 0S1","CA","CAD", Data.PRODUCTS_01, organizationids, 8323,49.900827,-97.136993,5);
		organizationids = addOrganizations("Fredericton","NB E3B 3P2","CA","CAD", 6883,45.964277,-66.643066,5);
		addProducts("Fredericton","NB E3B 3P2","CA","CAD", Data.PRODUCTS_01, organizationids, 6883,45.964277,-66.643066,5);
		organizationids = addOrganizations("St Johns","NL A1C 1Z5","CA","CAD", 6270,47.562164,-52.712402,5);
		addProducts("St Johns","NL A1C 1Z5","CA","CAD", Data.PRODUCTS_01, organizationids, 6270,47.562164,-52.712402,5);
		organizationids = addOrganizations("Halifax","NS B3J","CA","CAD", 6980,44.648872,-63.575263,10);
		addProducts("Halifax","NS B3J","CA","CAD", Data.PRODUCTS_01, organizationids, 6980,44.648872,-63.575263,10);
		organizationids = addOrganizations("Toronto","ON M5G 2A3","CA","CAD", 8165,43.653963,-79.383087,10);
		addProducts("Toronto","ON M5G 2A3","CA","CAD", Data.PRODUCTS_01, organizationids, 8165,43.653963,-79.383087,10);
		organizationids = addOrganizations("Charlottetown"," PE C1A 4C6","CA","CAD", 6602,46.238277,-63.130875,10);
		addProducts("Charlottetown"," PE C1A 4C6","CA","CAD", Data.PRODUCTS_01, organizationids, 6602,46.238277,-63.130875,10);
		organizationids = addOrganizations("Quebec City","QC G1N 3L8","CA","CAD", 6270,46.80476,-71.242218,15);
		addProducts("Quebec City","QC G1N 3L8","CA","CAD", Data.PRODUCTS_01, organizationids, 6270,46.80476,-71.242218,15);
		organizationids = addOrganizations("Regina","SK S4R 2A1","CA","CAD", 7720,50.4551,-104.606667,5);
		addProducts("Regina","SK S4R 2A1","CA","CAD", Data.PRODUCTS_01, organizationids, 7720,50.4551,-104.606667,5);
		organizationids = addOrganizations("Buenos Aries","C1084AAD","AR","ARS", 235,-34.597042,-58.359375,20);
		addProducts("Buenos Aries","C1084AAD","AR","ARS", Data.PRODUCTS_01, organizationids, 235,-34.597042,-58.359375,20);
		organizationids = addOrganizations("Mendoza","M5500GJF","AR","ARS", 323,-32.89011,-68.843851,10);
		addProducts("Mendoza","M5500GJF","AR","ARS", Data.PRODUCTS_01, organizationids, 323,-32.89011,-68.843851,10);
		organizationids = addOrganizations("Bariloche","R8400HFF","AR","ARS", 384,-41.141433,-71.30127,10);
		addProducts("Bariloche","R8400HFF","AR","ARS", Data.PRODUCTS_01, organizationids, 384,-41.141433,-71.30127,10);
		organizationids = addOrganizations("Maceió","57000","BR","BRL", 4212,-9.665738,-35.735092,20);
		addProducts("Maceió","57000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-9.665738,-35.735092,20);
		organizationids = addOrganizations("Manaus","68000","BR","BRL", 5232,-3.107606,-60.023804,5);
		addProducts("Manaus","68000","BR","BRL", Data.PRODUCTS_01, organizationids, 5232,-3.107606,-60.023804,5);
		organizationids = addOrganizations("Canavieiras","40000","BR","BRL", 4553,-15.68651,-38.95752,5);
		addProducts("Canavieiras","40000","BR","BRL", Data.PRODUCTS_01, organizationids, 4553,-15.68651,-38.95752,5);
		organizationids = addOrganizations("Ilhéus","40000","BR","BRL", 4212,-14.753635,-39.089355,10);
		addProducts("Ilhéus","40000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-14.753635,-39.089355,10);
		organizationids = addOrganizations("Salvador","40000","BR","BRL", 5724,-12.967089,-38.512573,20);
		addProducts("Salvador","40000","BR","BRL", Data.PRODUCTS_01, organizationids, 5724,-12.967089,-38.512573,20);
		organizationids = addOrganizations("Fortaleza","63000","BR","BRL", 4833,-3.718319,-38.542786,20);
		addProducts("Fortaleza","63000","BR","BRL", Data.PRODUCTS_01, organizationids, 4833,-3.718319,-38.542786,20);
		organizationids = addOrganizations("Brasília","70000","BR","BRL", 4212,-15.78036,-47.927856,10);
		addProducts("Brasília","70000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-15.78036,-47.927856,10);
		organizationids = addOrganizations("Vitória","29000","BR","BRL", 4212,-20.315009,-40.301456,10);
		addProducts("Vitória","29000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-20.315009,-40.301456,10);
		organizationids = addOrganizations("Goiânia","72800","BR","BRL", 4212,-16.676978,-49.266815,10);
		addProducts("Goiânia","72800","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-16.676978,-49.266815,10);
		organizationids = addOrganizations("São Luís","65000","BR","BRL", 4212,-2.530153,-44.305115,5);
		addProducts("São Luís","65000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-2.530153,-44.305115,5);
		organizationids = addOrganizations("Camp Grande","79000","BR","BRL", 4212,-20.437308,-54.64325,5);
		addProducts("Camp Grande","79000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-20.437308,-54.64325,5);
		organizationids = addOrganizations("Belo Horizonte","30000","BR","BRL", 4405,-19.918808,-43.938103,10);
		addProducts("Belo Horizonte","30000","BR","BRL", Data.PRODUCTS_01, organizationids, 4405,-19.918808,-43.938103,10);
		organizationids = addOrganizations("Belém","66000","BR","BRL", 4212,-1.453473,-48.501892,5);
		addProducts("Belém","66000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-1.453473,-48.501892,5);
		organizationids = addOrganizations("João Pessoa","58000","BR","BRL", 4212,-7.11452,-34.860306,10);
		addProducts("João Pessoa","58000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-7.11452,-34.860306,10);
		organizationids = addOrganizations("Curitiba","80000","BR","BRL", 4740,-25.428393,-49.273338,5);
		addProducts("Curitiba","80000","BR","BRL", Data.PRODUCTS_01, organizationids, 4740,-25.428393,-49.273338,5);
		organizationids = addOrganizations("Recife","50000","BR","BRL", 5653,-8.053791,-34.881248,20);
		addProducts("Recife","50000","BR","BRL", Data.PRODUCTS_01, organizationids, 5653,-8.053791,-34.881248,20);
		organizationids = addOrganizations("Teresina","64000","BR","BRL", 6010,-5.086841,-42.801361,5);
		addProducts("Teresina","64000","BR","BRL", Data.PRODUCTS_01, organizationids, 6010,-5.086841,-42.801361,5);
		organizationids = addOrganizations("Rio de Janeiro","20000","BR","BRL", 5683,-22.900213,-43.206482,30);
		addProducts("Rio de Janeiro","20000","BR","BRL", Data.PRODUCTS_01, organizationids, 5683,-22.900213,-43.206482,30);
		organizationids = addOrganizations("Natal","59000","BR","BRL", 5350,-5.793971,-35.210495,10);
		addProducts("Natal","59000","BR","BRL", Data.PRODUCTS_01, organizationids, 5350,-5.793971,-35.210495,10);
		organizationids = addOrganizations("Porto Alegre","90000","BR","BRL", 5588,-30.026894,-51.227875,10);
		addProducts("Porto Alegre","90000","BR","BRL", Data.PRODUCTS_01, organizationids, 5588,-30.026894,-51.227875,10);
		organizationids = addOrganizations("Joinville","89100","BR","BRL", 5146,-26.303264,-48.845215,10);
		addProducts("Joinville","89100","BR","BRL", Data.PRODUCTS_01, organizationids, 5146,-26.303264,-48.845215,10);
		organizationids = addOrganizations("Itajai","88302-201","BR","BRL", 5030,-26.912274,-48.669434,0);
		addProducts("Itajai","88302-201","BR","BRL", Data.PRODUCTS_01, organizationids, 5030,-26.912274,-48.669434,0);
		organizationids = addOrganizations("Florianópolis","88000","BR","BRL", 4212,-30.047105,-51.217575,20);
		addProducts("Florianópolis","88000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-30.047105,-51.217575,20);
		organizationids = addOrganizations("Santos","11100","BR","BRL", 5802,-23.956136,-46.334839,20);
		addProducts("Santos","11100","BR","BRL", Data.PRODUCTS_01, organizationids, 5802,-23.956136,-46.334839,20);
		organizationids = addOrganizations("São José dos Campos","12200","BR","BRL", 4212,-23.160563,-45.878906,10);
		addProducts("São José dos Campos","12200","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-23.160563,-45.878906,10);
		organizationids = addOrganizations("Campinas","13010","BR","BRL", 4530,-22.907803,-47.059937,20);
		addProducts("Campinas","13010","BR","BRL", Data.PRODUCTS_01, organizationids, 4530,-22.907803,-47.059937,20);
		organizationids = addOrganizations("Ribeirão Preto","14000","BR","BRL", 4212,-21.166484,-47.823486,10);
		addProducts("Ribeirão Preto","14000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-21.166484,-47.823486,10);
		organizationids = addOrganizations("Poços de Caldas","37701-010","BR","BRL", 4212,-21.779905,-46.560059,10);
		addProducts("Poços de Caldas","37701-010","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-21.779905,-46.560059,10);
		organizationids = addOrganizations("São Paulo","1000","BR","BRL", 4212,-23.543845,-46.638336,30);
		addProducts("São Paulo","1000","BR","BRL", Data.PRODUCTS_01, organizationids, 4212,-23.543845,-46.638336,30);
		organizationids = addOrganizations("Aracaju","49000","BR","BRL", 4301,-10.90883,-37.074051,10);
		addProducts("Aracaju","49000","BR","BRL", Data.PRODUCTS_01, organizationids, 4301,-10.90883,-37.074051,10);
		organizationids = addOrganizations("Palmas","73000","BR","BRL", 5425,-10.168967,-48.330231,5);
		addProducts("Palmas","73000","BR","BRL", Data.PRODUCTS_01, organizationids, 5425,-10.168967,-48.330231,5);
		organizationids = addOrganizations("Santiago","8320000","CL","CLF", 5781,-33.415395,-70.545959,20);
		addProducts("Santiago","8320000","CL","CLF", Data.PRODUCTS_01, organizationids, 5781,-33.415395,-70.545959,20);
		organizationids = addOrganizations("Quito","170101","EC","ECV", 15589,-0.222816,-78.512421,10);
		addProducts("Quito","170101","EC","ECV", Data.PRODUCTS_01, organizationids, 15589,-0.222816,-78.512421,10);
		organizationids = addOrganizations("Lima","LIMA 01","PE","PEN", 39331,-12.093039,-77.046475,20);
		addProducts("Lima","LIMA 01","PE","PEN", Data.PRODUCTS_01, organizationids, 39331,-12.093039,-77.046475,20);
		organizationids = addOrganizations("Puntu del Este","20100","UY","UYU", 58047,-34.966999,-54.94812,10);
		addProducts("Puntu del Este","20100","UY","UYU", Data.PRODUCTS_01, organizationids, 58047,-34.966999,-54.94812,10);
		organizationids = addOrganizations("Kuta","80361","ID","IDR", 27663,-8.720861,115.182037,20);
		addProducts("Kuta","80361","ID","IDR", Data.PRODUCTS_01, organizationids, 27663,-8.720861,115.182037,20);
		organizationids = addOrganizations("Bangkok","10110","TH","THB", 44161,13.751391,100.504303,20);
		addProducts("Bangkok","10110","TH","THB", Data.PRODUCTS_01, organizationids, 44161,13.751391,100.504303,20);
		organizationids = addOrganizations("Hat Rin","84000","TH","THB", 44150,9.67623,100.067596,5);
		addProducts("Hat Rin","84000","TH","THB", Data.PRODUCTS_01, organizationids, 44150,9.67623,100.067596,5);
		organizationids = addOrganizations("Phuket","83000","TH","THB", 44235,7.885147,98.383942,5);
		addProducts("Phuket","83000","TH","THB", Data.PRODUCTS_01, organizationids, 44235,7.885147,98.383942,5);
		organizationids = addOrganizations("Maret","84000","TH","THB", 18934,9.447707,100.026398,5);
		addProducts("Maret","84000","TH","THB", Data.PRODUCTS_01, organizationids, 18934,9.447707,100.026398,5);
		organizationids = addOrganizations("Hua Hin","77000","TH","THB", 44177,12.567968,99.96048,5);
		addProducts("Hua Hin","77000","TH","THB", Data.PRODUCTS_01, organizationids, 44177,12.567968,99.96048,5);


		System.out.println("Finished!");
	}

	/**
	 * Adds a product record using the specified parameters.
	 *
	 * @param locationid the location id
	 * @param ownerid the owner id
	 * @param supplierid the supplier id
	 * @param name the name
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param room the room
	 * @param person the person
	 * @param rating the rating
	 * @param commission the commission
	 * @param discount the discount
	 * @return the id of the product
	 */
	private static final int addProduct(int locationid, int ownerid, int supplierid, String name, double latitude, double longitude, int room, int person, int rating, int commission, int discount) {
		productid++;
		if (productid % 100 == 0) {
			int length = productDB.length();
			productDB.replace(length-1, length, ";");
			productDB.append("\nINSERT INTO `product` (ID, OwnerID, LocationID, SupplierID, Name, State, Type, WebAddress, Tax, Code, Unspsc, Servicedays, Currency, Unit, Room, Quantity, Person, Linenchange, Rating, Refresh, Commission, Discount, Rank, Latitude, Longitude, Altitude) VALUES ");
		}
		productDB.append("\n(" + productid + "," + ownerid + "," + locationid + "," + supplierid + ",'" + name + "','Created','Accommodation','http://razor-cloud.com/service/?product=" + productid + "','VAT Normal','',NULL,'1111111','USD','DAY'," + room + ",1," + person + ",3," + rating + ",1," + commission + "," + discount +",0," + latitude + "," + longitude + ",0),");
		return productid;
	}

	/**
	 * Adds the organizations at the location name.
	 *
	 * @param name the location name
	 * @param postcode the postcode
	 * @param country the country
	 * @param currency the currency
	 * @param locationid the location id
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distance the distance
	 * @return the list of organization ids
	 */
	private static final int[] addOrganizations(String name, String postcode, String country, String currency, int locationid, double latitude, double longitude, double distance) {
		System.out.println("Adding properties at " + name);
		//Organizations at location name
		int[] organizationids = new int[Data.VRM_TYPES.length];
		for (int index = 0; index < Data.VRM_TYPES.length; index++) {
			String organizationname = name + " " + Data.VRM_TYPES[index];
			int organizationid = addParty(0, locationid, organizationname, Party.Type.Organization, postcode, country, currency, latitude, longitude, distance);
			organizationids[index] = organizationid;
			addRelation(Relation.PARTY_ROLE, String.valueOf(organizationid), "2");
			
			//Accounts
			for (int accountid = 1; accountid <= 73; accountid++) {addRelation(Relation.ORGANIZATION_ACCOUNT, String.valueOf(partyid), String.valueOf(accountid));}
			//Product Features
			addRelation(Value.Type.ProductFeature.name(), String.valueOf(organizationid), "Airport Transfer");
			addRelation(Value.Type.ProductFeature.name(), String.valueOf(organizationid), "Maid Service");
			addRelation(Value.Type.ProductFeature.name(), String.valueOf(organizationid), "Laundry Service");
			//addRelation(Value.Type.ProductFeature.name(), String.valueOf(organizationid), "Food Stocks");

			double price = getRandom(10.0, 500.0);
			String suppliername = Data.getRandomString(Data.FAMILY_NAMES) + " Shuttles";
			int supplierid = addParty(organizationid, locationid, suppliername, Party.Type.Supplier, postcode, country, currency, latitude, longitude, distance);
			addPrice(productid, organizationid, "Feature", "Airport Transfer", "Service", "2011-09-01", "2013-10-31", price, 0.0, price * 0.7, supplierid);

			price = getRandom(50.0, 200.0);
			suppliername = Data.getRandomString(Data.FAMILY_NAMES) + " Cleaning Services";
			supplierid = addParty(organizationid, locationid, suppliername, Party.Type.Supplier, postcode, country, currency, latitude, longitude, distance);
			addPrice(productid, organizationid, "Feature", "Maid Service", "Service", "2011-09-01", "2013-10-31", price, 0.0, price * 0.7, supplierid);

			price = getRandom(10.0, 50.0);
			suppliername = Data.getRandomString(Data.FAMILY_NAMES) + " Laundry";
			supplierid = addParty(organizationid, locationid, suppliername, Party.Type.Supplier, postcode, country, currency, latitude, longitude, distance);
			addPrice(productid, organizationid, "Feature", "Laundry Service", "Service", "2011-09-01", "2013-10-31", price, 0.0, price * 0.7, supplierid);

			String agentname = Data.getRandomString(Data.FAMILY_NAMES) + " " + Data.getRandomString(Data.AGENT_TYPES);
			int agentid = addParty(organizationid, locationid, agentname, Party.Type.Agent, postcode, country, currency, getRandom(-50, 50.0), getRandom(-180, 180), 0.0);
			addRelation(Relation.PARTY_ROLE, String.valueOf(agentid), "10");
			addContract(organizationid, agentid, organizationname, agentname, Data.getRandomInteger(10, 50));

			agentname = Data.getRandomString(Data.FAMILY_NAMES) + " " + Data.getRandomString(Data.AGENT_TYPES);
			agentid = addParty(organizationid, locationid, agentname, Party.Type.Agent, postcode, country, currency, getRandom(-50, 50.0), getRandom(-180, 180), 0.0);
			addRelation(Relation.PARTY_ROLE, String.valueOf(agentid), "10");
			addContract(organizationid, agentid, organizationname, agentname, Data.getRandomInteger(10, 50));

			agentname = Data.getRandomString(Data.FAMILY_NAMES) + " " + Data.getRandomString(Data.AGENT_TYPES);
			agentid = addParty(organizationid, locationid, agentname, Party.Type.Agent, postcode, country, currency, getRandom(-50, 50.0), getRandom(-180, 180), 0.0);
			addRelation(Relation.PARTY_ROLE, String.valueOf(agentid), "10");
			addContract(organizationid, agentid, organizationname, agentname, Data.getRandomInteger(10, 50));

			String textid = "Party" + organizationid + "Contract";
			addText(textid, Language.EN, organizationname, Text.Type.HTML.name(), getContract(organizationname).substring(0, Data.getRandomIndex(400)));

			addWorkflow(organizationid,"Reserved", "reservation.date", false, 2);
			addWorkflow(organizationid,"Confirmed", "reservation.date", false, 5);
			addWorkflow(organizationid,"FullyPaid", "reservation.fromdate", true, 30);
			addWorkflow(organizationid,"Briefed", "reservation.fromdate", true, 7);
			addWorkflow(organizationid,"Arrived", "reservation.fromdate", true, 1);
			addWorkflow(organizationid,"PreDeparture", "reservation.todate", true, 1);
			addWorkflow(organizationid,"Departed", "reservation.todate", false, 7);
		}
		return organizationids;
	}
	
	/**
	 * Adds product records using the specified parameters.
	 *
	 * @param name the name
	 * @param postcode the post code
	 * @param country the country
	 * @param currency the currency
	 * @param productnames the product names
	 * @param organizationids the organizationids
	 * @param locationid the location id
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param distance the distance
	 */
	private static final void addProducts(String name, String postcode, String country, String currency, String[] productnames, int[] organizationids, int locationid, double latitude, double longitude, double distance) {
		System.out.println("Adding properties at " + name);
		for (int index = 0; index < productnames.length; index++) {
			int organizationid = organizationids[Data.getRandomIndex(Data.VRM_TYPES.length)];

			String ownername = Data.getRandomString(Data.FAMILY_NAMES) + "," + Data.getRandomString(Data.MALE_NAMES);
			int ownerid = addParty(organizationid, locationid, ownername, Party.Type.Owner, postcode, country, currency, latitude, longitude, distance);

			int room = Data.getRandomInteger(1, 12);
			int rating = Data.getRandomInteger(1, 10);
			int commission = Data.getRandomInteger(5, 50);
			int discount = Data.getRandomInteger(5, 50);
			double radius = distance / 140.0;
			int productid = addProduct(locationid, ownerid, organizationid, productnames[index] + " " + name, latitude + getRandom(-radius, radius), longitude + getRandom(-radius, radius), room, room * 2, rating, commission, discount);
//			addRelation(Relation.PRODUCT_TYPE, String.valueOf(productid), Product.Type.Accommodation.name());
			addRelation(Relation.ORGANIZATION_PRODUCT, String.valueOf(organizationid), String.valueOf(productid));

			//Attributes
			addRelation(Relation.PRODUCT_ATTRIBUTE, String.valueOf(productid), Data.getRandomString(Data.ATTRIBUTES));
			addRelation(Relation.PRODUCT_ATTRIBUTE, String.valueOf(productid), Data.getRandomString(Data.ATTRIBUTES));
			addRelation(Relation.PRODUCT_ATTRIBUTE, String.valueOf(productid), Data.getRandomString(Data.ATTRIBUTES));
			addRelation(Relation.PRODUCT_ATTRIBUTE, String.valueOf(productid), Data.getRandomString(Data.ATTRIBUTES));

			//Images
			//TODO: 
			for (int i = 0; i <= 9; i++) {
				String textid = "Product" + productid + "-00" + i + ".jpg";
				addText(textid, Language.EN, textid, Text.Type.Image.name(), "");
//				addRelation(Relation.PRODUCT_IMAGE, String.valueOf(productid), "Product144-00" + i + ".jpg");
			}

			//Prices
			double price = getRandom(100.0, 1000.0);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2011-11-01", "2011-12-31", price, 0.0, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2012-01-01", "2012-03-31", price * 1.2, 0.0, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2012-04-01", "2012-06-30", price * 1.4, 0.0, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2012-07-01", "2012-08-31", price * 1.9, price * 13.3, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2012-09-01", "2012-10-31", price * 1.4, 0.0, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2012-11-01", "2012-12-31", price * 1.05, 0.0, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2013-01-01", "2013-03-31", price * 1.05 * 1.2, 0.0, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2013-04-01", "2013-06-30", price * 1.05 * 1.4, 0.0, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2013-07-01", "2013-08-31", price * 1.05 * 1.9, price * 13.3, 0.0, null);
			addPrice(productid, organizationid, "Product", "Reservation", "Reservation", "2013-09-01", "2013-10-31", price * 1.05 * 1.4, 0.0, 0.0, null);

			//Yield Management
			if (productid%4 == 0) {
				addYield(productid, "Maximum Gap Filler", 21, 25);
				addYield(productid, "Last Minute Lead Time", 7, 30);
				addYield(productid, "Last Minute Lead Time", 14, 15);
				addYield(productid, "Length of Stay", 14, 10);
				addYield(productid, "Length of Stay", 7, 5);
				addYield(productid, "Length of Stay", 21, 15);
			}
			
			//Asset Places
			addRelation(Value.Type.AssetPlace.name(), String.valueOf(productid), "Kitchen");
			addRelation(Value.Type.AssetPlace.name(), String.valueOf(productid), "Lounge");
			addRelation(Value.Type.AssetPlace.name(), String.valueOf(productid), "Bedroom");
			addRelation(Value.Type.AssetPlace.name(), String.valueOf(productid), "Bathroom");
			addRelation(Value.Type.AssetPlace.name(), String.valueOf(productid), "Master Bedroom");
			addRelation(Value.Type.AssetPlace.name(), String.valueOf(productid), "Master Bathroom");

			//Asset instances
			String suppliername = Data.getRandomString(Data.FAMILY_NAMES) + " Furnishers";
			int supplierid = addParty(organizationid, locationid, suppliername, Party.Type.Supplier, postcode, country, currency, latitude, longitude, distance);
			addAsset(ownerid, supplierid, productid, "Leather Couch", "Furniture", "Colonial", "Lounge", "Brown leather two seater couch.", 1, 350, 400);

			suppliername = Data.getRandomString(Data.FAMILY_NAMES) + " Wholesale";
			supplierid = addParty(organizationid, locationid, suppliername, Party.Type.Supplier, postcode, country, currency, latitude, longitude, distance);
			addAsset(ownerid, supplierid, productid, Data.getRandomString(Data.FURNITURE_TYPES), "Furniture", "Colonial", "Lounge", "Brand new condition.", 1, 350, 400);

			suppliername = Data.getRandomString(Data.FAMILY_NAMES) + " Discounters";
			supplierid = addParty(organizationid, locationid, suppliername, Party.Type.Supplier, postcode, country, currency, latitude, longitude, distance);
			addAsset(ownerid, supplierid, productid, Data.getRandomString(Data.FURNITURE_TYPES), "Furniture", "Colonial", "Lounge", "Slightly damaged on top.", 1, 350, 400);

			//Quality Audit Types
			for (int i = 0; i < Data.AUDIT_NAMES.length; i++) {
				addRelation(Value.Type.AuditName.name(), String.valueOf(organizationid), Data.AUDIT_NAMES[i]);
			}

			//Quality Audit Results
			addAudit(productid, Data.getRandomString(Data.AUDIT_NAMES), "Excellent!", "2011-11-09", 9);
			addAudit(productid, Data.getRandomString(Data.AUDIT_NAMES), "Needs immediate attention!", "2011-10-19", 4);
			addAudit(productid, Data.getRandomString(Data.AUDIT_NAMES), "Acceptable but should be improved", "2011-09-09", 6);
			addAudit(productid, Data.getRandomString(Data.AUDIT_NAMES), "Completely unacceptable - resolve immediately!", "2011-01-15", 2);

			//Description
			String textid = "Product" + productid + "Public";
			addText(textid, Language.EN, productnames[index], Text.Type.HTML.name(), getDescription(productnames[index], ownername).substring(0, Data.getRandomIndex(300)));
		}
	}

	/**
	 * Gets the product description.
	 *
	 * @param productname the product name
	 * @param ownername the owner name
	 * @return the description
	 */
	private static final String getDescription(String productname, String ownername) {
		StringBuilder sb = new StringBuilder();
		sb.append(productname + " is owned by " + Party.getNaturalName(ownername));
		sb.append("\nVacation rental is the renting out of a furnished apartment or house on a temporary basis to tourists as an alternative to a hotel. The term vacation rental is mainly used in the US. In Europe the term villa rental or villa holiday is preferred for rentals of detached houses in warm climates. Other terms used are self-catering rentals, holiday homes, holiday lets (in the United Kingdom), cottage holidays (for rentals of smaller accommodation in rural locations) and gites (in rural locations in France).");
		sb.append("\nVacation rentals have long been a popular travel option in Europe (especially in the UK) as well as in Canada and are becoming increasingly popular across the world.");
		sb.append("\nTypes of Accommodation");
		sb.append("\nVacation rentals usually occur in privately owned vacation properties (holiday homes), so the variety of accommodation is broad and inconsistent. The property is a fully furnished property, such as a holiday cottage, condominium, townhome or single-family-style home. Farm stay can encompass participation on a working farm, or a more conventional rental that happens to be co-located on a farm. The client/traveler arranges to rent the vacation rental property for a designated period of time. Some rent on nightly basis similar to hotel rooms, although the more prevalent vacation rental industry practice is typically weekly rentals.");
		sb.append("\nVacation rentals can range from budget studio apartments to lavish, expensive private villas in the worlds most desirable locations, some with pricetags of many thousands per night and all the amenities you would find in any luxury accommodation (fully staffed, private beaches, boats, chefs, cooking lessons, etc.) to cater to the guests. Some vacation rentals, particularly condominiums or apartments, offer many of the same services hotels offer to their guests, e.g., front desk check-in, 24-hour maintenance, in-house housekeeping, concierge service. At the other end of the spectrum are campervan and motorhome rentals.");
		sb.append("\nLocations");
		sb.append("\nVilla holidays are very popular in Europe, and main destinations include Spain, France, Greece, and Turkey. In France, they are known as gîtes.");
		sb.append("\nVacation rentals are available in most states of the US and is prevalent in major tourist areas such as Florida, Hawaii and California. The vacation rental market is much larger in Europe than it is in the United States, and Florida is a popular destination for villa holidays for Europeans.");
		sb.append("\nComparison with other Accommodations");
		sb.append("\nConsumers unfamiliar with the concept of a vacation rental may confuse it with the seemingly similar, but distinctly different, timeshare. A timeshare can still be rented as a vacation rental should an owner decide to put his owned week(s) on a vacation rental program. Many timeshare resorts offer quarter ownership, which provides 13 weeks of use or rental.");
		sb.append("\nA timeshare is a piece of real estate—often a fully furnished condominium—that is jointly shared by multiple owners. While different types of timeshare ownerships exist, in general, each owner bears a portion of the responsibility, along with the right to a segment of time in which he or she is granted sole use of the property. Timeshare resorts allow financially qualified guests to rent and tour their unowned properties and then make those properties available to the guest for purchase. Timeshare owners can also choose to bank their week with and exchange company such as RCI, or rent the unit.");
		sb.append("\nHotels and hostels generally do not include rental of a full standalone home or apartment, but are purpose-built.");
		return sb.toString();
	}

	/**
	 * Gets the organization contract.
	 *
	 * @param organizationname the organization name
	 * @return the contract text
	 */
	private static final String getContract(String organizationname) {
		StringBuilder sb = new StringBuilder();
		sb.append("RENTAL AGREEMENT");
		sb.append("\n" + organizationname);
		sb.append("\nSmoking is allowed outside only.");
		sb.append("\nPeople other than those in the Guest party set forth above may not stay overnight in the property.  Any other person in the property is the sole responsibility of Guest.");
		sb.append("\nAll of the units are privately owned; the owners are not responsible for any accidents, injuries or illness that occurs while on the premises or its facilities. The Homeowners are not responsible for the loss of personal belongings or valuables of the guest. By accepting this reservation, it is agreed that all guests are expressly assuming the risk of any harm arising from their use of the premises or others whom they invite to use the premise.");
		sb.append("\nKeep the property and all furnishings in good order");
		sb.append("\nOnly use appliances for their intended uses");
		sb.append("\nPets are permitted only with prior approval and the Pet Addendum must be completed.  ");
		sb.append("\nParking:");
		sb.append("\nParking is limited to " + (int)getRandom(1, 6) + " vehicle(s).  Vehicles are to be parked in designated parking areas only.  Parking on the road is not permitted.  Any illegally parked cars are subject to towing; applicable fines/towing fees are the sole responsibility of the vehicle owner.");
		sb.append("\n");
		sb.append("\nHousekeeping:  There is no daily housekeeping service. While linens and bath towels are included in the unit, daily maid service is not included in the rental rate.   We suggest you bring beach towels. We do not permit towels or linens to be taken from the units. ");
		sb.append("\nHot Tub: When using the hot tub, remember there is a certain health risk associated with this facility. Use at your own risk.  Our housekeepers drain, sanitize, refill and replenish chemicals in all tubs prior to your arrival; therefore, it may not be warm until later that evening. Hot tub covers are for insulation purposes and are not designed to support a person or persons.  They will break and you may be charged for replacement. Remember when not using the hot tub, leave cover on so hot tub will stay warm.");
		sb.append("\nFireplace: The fireplace is a non-vented propane gas log fired firebox.  Please do not throw any paper or other combustible materials in the fireplace.");
		sb.append("\nWater and Septic: The property is on a well and septic systems.  The mineral content in the water is high.  During a drought, the well water may have an odor.  The septic system is very effective; however, it will clog up if improper material is flushed.  DO NOT FLUSH anything other than toilet paper.  No feminine products should be flushed at anytime.");
		sb.append("\nStorms:");
		sb.append("\nIf there is a storm or hurricane, no refunds will be given unless: ");
		sb.append("\nThe state or local authorities order mandatory evacuations in a Tropical Storm/Hurricane Warning area and/or ");
		sb.append("\nA mandatory evacuation order has been given for the Tropical Storm/Hurricane Warning area of residence of a vacationing guest. ");
		sb.append("\nThe day that the authorities order a mandatory evacuation order in a Tropical Storm/Hurricane Warning, area, we will refund: ");
		sb.append("\nAny unused portion of rent from a guest currently registered; ");
		sb.append("\nAny unused portion of rent from a guest that is scheduled to arrive, and wants to shorten the stay, to come in after the Hurricane Warning is lifted; and ");
		sb.append("\nAny advance rents collected or deposited for a reservation that is scheduled to arrive during the Hurricane Warning period. ");
		sb.append("\n");
		sb.append("\nPET ADDENDUM");
		sb.append("\nAll pets must comply with the following specifications (documentation from an accredited veterinarian must be provided by Guest upon request):");
		sb.append("\nMay not exceed " + Data.getRandomIndex(30) + " lbs.");
		sb.append("\nMust be at least " + Data.getRandomIndex(3) + " year(s) of age or older.");
		sb.append("\nMust be spayed or neutered. ");
		sb.append("\nMust be up-to-date on rabies vaccinations and all other vaccinations. Heartworm preventive is highly recommended. ");
		sb.append("\n");
		sb.append("\nAll pets must be leashed at all times.  ");
		sb.append("\nGuest is responsible for cleaning up any/all pet refuse.  ");
		sb.append("\nPets are not allowed on furniture at any time.  Any evidence of pets on furniture may incur extra cleaning fees.  ");
		sb.append("\nAll pets are to be treated with a topical flea and tick repellent three (3) days prior to arrival.  Fleas and ticks are very rampant in this area and can cause harmful/fatal illness to humans and pets.  ");
		sb.append("\nPet must not cause damage to premises or furnishings. If damages are caused, the cost of the damage may be deducted from security deposit.");
		sb.append("\nGuest should prevent pets from producing excessive noise at a level that disturbs neighbors.");
		sb.append("\nPet will not be left unattended for an undue length of time, either indoors or out. Pet will not be left unattended on balcony, patio, or porch.");
		sb.append("\nHomeowner assumes no responsibility for illness or injury that may incur to pets or humans while on the premises. ");
		sb.append("\n");
		sb.append("\nThe Guest shall be solely responsible for the pet while on the property.");
		sb.append("\n");
		sb.append("\nSign_________________________________________________________ Date_____");
		return sb.toString();
	}

	/**
	 * Finalize product.
	 */
	private static final void finalizeProduct() {
		int length = productDB.length();
		productDB.replace(length-1, length, ";");
		productDB.append("\n/*!40000 ALTER TABLE `product` ENABLE KEYS */;");
		productDB.append("\nUNLOCK TABLES;");
		productDB.append("\n");
	}

}
