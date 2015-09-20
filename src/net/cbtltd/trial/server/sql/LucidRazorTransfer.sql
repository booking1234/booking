DELIMITER $$

DROP PROCEDURE IF EXISTS `razor`.`LucidRazorTransfer` $$
CREATE PROCEDURE `razor`.`LucidRazorTransfer` ()
BEGIN

insert into razor.journal (ID, EventID, MatchID, AccountID, OrganizationID, LocationID, EntityType, EntityID,
Quantity, Unit, CreditAmount, DebitAmount, Currency, Description, Unitprice)
select ActionID, EventID, MatchID, AccountID, OrganizationID, LocationID, EntityType, EntityID,
Quantity, Unit, CreditAmount, DebitAmount, Currency, Description, (DebitAmount - CreditAmount) / Quantity
from lucid.action;

update razor.event set State = 'Created' where State = 'Invoiced';

insert into razor.contract (OrganizationID, PartyID, ActorID, Name, State, Process, Discount,
CreditTerm, CreditLimit, Target, Currency, Date, DueDate, DoneDate, Notes)
select OrganizationID, PartyID, ActorID, Type, State, Process, Discount,
CreditTerm, CreditLimit, Target, Currency, Date, DueDate, DoneDate, Notes
from lucid.contract where discount <> 20;

insert into razor.event (ID, OrganizationID, ActorID, ParentID, Name, State, Activity, Process, Type, Date, DueDate, DoneDate, Notes)
select EventID, OrganizationID, ActorID, ParentID, Name, State, Activity, Process, Type, Date, DueDate, DoneDate, Notes
from lucid.event;

insert into razor.finance (ID, OwnerID, AccountID, Name, State, Type, BankName, BranchName, BranchNumber, AccountNumber, ContactPerson, PhoneNumber, Currency, Notes, Month, Year, Code)
select FinanceID, OwnerID, AccountID, Name, State, Type, BankName, BranchName, BranchNumber, AccountNumber, ContactPerson, PhoneNumber, Currency, Notes, Month, Year, Code
from lucid.finance;

insert into razor.location (ID, Code, Name, State, Country, Region, Status, Type, IATA, Latitude, Longitude, Altitude, Notes)
select LocationID, Code, Name, State, Country, Subdivision, Status, Function, IATA, Latitude, Longitude, Altitude, Remarks
from lucid.location;

insert into razor.party (ID, EmployerID, CreatorID, LocationID, FinanceID, JurisdictionID, Name, State, ExtraName, IdentityNumber, TaxNumber, PostalAddress,
PostalCode, Country, EmailAddress, WebAddress, DayPhone, NightPhone, FaxPhone, MobilePhone, Password, Birthdate, Language, Currency, Unit,
Latitude, Longitude, Altitude, Notes)
select PartyID, ParentID, CreatorID, LocationID, FinanceID, 3418, Name, State, ExtraName, IdentityNumber, TaxNumber, PostalAddress,
PostalCode, Country, EmailAddress, WebAddress, DayPhone, NightPhone, FaxPhone, MobilePhone, Password, Birthdate, Language, Currency, 'EA',
Latitude, Longitude, Altitude, Notes
from lucid.party;

insert into razor.price (ID, EntityType, EntityID, PartyID, Name, State, Date, ToDate, Quantity, Unit, Value, Minimum, Currency)
select PriceID, EntityType, EntityID, PartyID, 'Retail', State, Date, ToDate, Quantity, Unit, Value, Minimum, Currency
from lucid.price;

insert into razor.relation (Link, HeadID, LineID)
select Link, HeadID, LineID
from lucid.relation
where Link <> 'Reservation Resource'
and Link <> 'Accommodation Size'
and Link <> 'Account Location'
and Link <> 'Account Organization'
and Link <> 'Account Subledger';

insert into razor.region (ID, Country, Name)
select Code, Country, Name from lucid.locationsubdivision;

insert into razor.report (ID, OrganizationID, ActorID, Name, State, Design, FromName, ToName, FromDate, ToDate, Date, Currency, Notes, Format)
select ReportID, OrganizationID, ActorID, Name, State, Design, FromName, ToName, FromDate, ToDate, Date, Currency, Notes, Format
from lucid.report where ReportID >= 18000;

insert into razor.reservation (ID, ParentID, OrganizationID, ActorID, AgentID, CustomerID, ServiceID, FinanceID, ProductID,
Name, State, Market, ArrivalTime, DepartureTime, Date, FromDate, ToDate, DueDate, DoneDate, Deposit, Price, Quote, Cost, Unit, Currency, Notes)
select distinct ReservationID, ParentID, OrganizationID, ActorID, AgentID, CustomerID, ServiceID, FinanceID, max(LineID),
Name, State, Market, ArrivalTime, DepartureTime, Date, ArrivalDate, DepartureDate, DueDate, DoneDate, Deposit, Rack, Price, Cost, Unit, Currency, Notes
from lucid.reservation
join lucid.relation on HeadID = ReservationID and Link = 'Reservation Resource' and HeadID <> 22187
group by ReservationID;

insert into razor.reservation (Name, ParentID, OrganizationID, ActorID, AgentID, CustomerID, ServiceID, FinanceID, ProductID,
State, Market, ArrivalTime, DepartureTime, Date, FromDate, ToDate, DueDate, DoneDate, Deposit, Price, Quote, Cost, Unit, Currency, Notes)
SELECT concat(reservation.Name, '-',lineid), reservation.ParentID, reservation.OrganizationID, reservation.ActorID, reservation.AgentID, reservation.CustomerID, reservation.ServiceID,
reservation.FinanceID, LineID, reservation.State, reservation.Market, reservation.ArrivalTime, reservation.DepartureTime,
reservation.Date, reservation.ArrivalDate, reservation.DepartureDate, reservation.DueDate, reservation.DoneDate, reservation.Deposit, reservation.Rack,
0.0, 0.0, reservation.Unit, reservation.Currency, reservation.Notes
FROM lucid.relation
join lucid.reservation on reservationid = headid
join lucid.party on partyid = organizationid
join razor.reservation as razorreservation on razorreservation.id = reservationid
join razor.product on product.id = lineid
where link = 'Reservation Resource'
and departuredate >= '2011-01-01'
and reservation.state not in ('Cancelled', 'Final')
and lineid <> productid

insert into razor.serial (ID, PartyID, Series, Format, Last)
select SerialID, PartyID, Series, Format, Last
from lucid.serial;

insert into razor.tax (AccountID, PartyID, Name, Type, Date, Threshold, Amount)
select AccountID, 3418, Name, Type, Date, Threshold, Amount
from lucid.tax;

insert into razor.text (ID, Language, Name, Type, Data, Date, Notes)
select Code, Language, Name, State, Data, Date, Notes
from lucid.text;

insert into razor.yield (ID, EntityType, EntityId, Name, State, FromDate, ToDate, Param, Amount, Modifier)
select YieldID, EntityType, EntityId, Name, State, FromDate, ToDate, Param, Amount, Modifier
from lucid.yield;

insert into razor.product (ID, PartofID, OwnerID, LocationID, SupplierID, Name, State, WebAddress, Tax, Code, Unspsc, Currency, Unit,
Latitude, Longitude, Altitude, Room, Person, Rating, Commission, Discount)
select distinct ProductID, PartofID, OwnerID, LocationID, SupplierID, Name, State, WebAddress, Tax, Code, Unspsc, Currency, Unit,
Latitude, Longitude, Altitude, min(substring(relation.LineID, 1,1)), 2 * min(substring(relation.LineID, 1,1)), 2 * substring(rating.LineID, 1,1), 20, 20
from lucid.product
join lucid.relation on Link = 'Accommodation Size' and HeadID = concat('Product',ProductID) and substring(LineID,1,1) in ('1', '2', '3', '4', '5', '6', '7', '8', '9')
join lucid.relation as rating on rating.Link = 'Grading' and rating.HeadID = concat('Product',ProductID)
group by ProductID;

insert into razor.rate (ID, EventID, CustomerID, ProductID, Name, Type, Quantity)
select RateID, HeadID, CustomerID, ProductID, Name, State, Quantity from lucid.rate
join lucid.relation on Link = 'Event Rate' and LineID = rate.RateID
group by rate.RateID;

insert into razor.relation (Link, HeadID, LineID)
select distinct 'Party Role', HeadID, 2 FROM lucid.relation where Link = 'Party Role' and LineID = 102;

insert into razor.relation (Link, HeadID, LineID)
select distinct 'Party Role', HeadID, 3 FROM lucid.relation where Link = 'Party Role' and LineID = 101;

insert into razor.relation (Link, HeadID, LineID)
select distinct 'Party Role', HeadID, 3 FROM lucid.relation where Link = 'Party Role' and LineID = 55;

insert into razor.relation (Link, HeadID, LineID)
select distinct 'Party Role', HeadID, 2 FROM lucid.relation where Link = 'Party Role' and LineID = 37;

insert into razor.relation (Link, HeadID, LineID)
select distinct 'Party Role', id, 10 FROM lucid.relation as type
join party on employerid = type.HeadID
where type.Link = 'Party Type'
and type.LineID = 'Agent'
and id = employerid;

insert into razor.relation (Link, HeadID, LineID)
select distinct 'Party Role', id, 11 FROM lucid.relation as type
join party on employerid = type.HeadID
where type.Link = 'Party Type'
and type.LineID = 'Agent'
and id <> employerid;

insert into razor.relation (Link, HeadID, LineID)
select distinct 'Party Role', OwnerID, 7 from razor.product
join relation as type on type.Link = 'Product Type'
and type.LineID = 'Accommodation'
and OwnerID is not null;

insert into razor.relation (Link, HeadID, LineID)
select distinct 'Organization Party', HeadID, '2'
from razor.relation
where Link = 'Party Type' and  LineID = 'Organization';

insert into razor.relation (Link, HeadID, LineID) values ('Party Type', '3418', 'Jurisdiction');

update party set Name = 'South African Revenue Service' where ID = 3418;

update tax set Type = 'SalesTax' where Type = 'Sales Tax';

insert into razor.session (ActorID, Login) select ActorID, min(Login) FROM lucid.session group by ActorID;

update journal set entitytype = 'Account' where entitytype = '' or entitytype is null;

select HeadID,'Reservation','Reserved','now',0x01,0x00,2,'DAY'
from razor.relation where Link = 'Party Type' and 'LineID' = 'Organization';

insert into workflow (ID, Process, State, Datename, Enabled, Prior, Duration, Unit) 
select distinct HeadID,'Reservation','Reserved','reservation.date',0x01,0x00,2,'DAY' 
from relation where Link = 'Party Type' and LineID = 'Organization';

insert into workflow (ID, Process, State, Datename, Enabled, Prior, Duration, Unit)
select distinct HeadID,'Reservation','Confirmed','reservation.date',0x01,0x00,5,'DAY'
from relation where Link = 'Party Type' and LineID = 'Organization';

insert into workflow (ID, Process, State, Datename, Enabled, Prior, Duration, Unit) 
select distinct HeadID,'Reservation','FullyPaid','reservation.fromdate',0x01,0x01,30,'DAY'
from relation where Link = 'Party Type' and LineID = 'Organization';

insert into workflow (ID, Process, State, Datename, Enabled, Prior, Duration, Unit) 
select distinct HeadID,'Reservation','Briefed','reservation.fromdate',0x01,0x01,7,'DAY'
from relation where Link = 'Party Type' and LineID = 'Organization';

insert into workflow (ID, Process, State, Datename, Enabled, Prior, Duration, Unit) 
select distinct HeadID,'Reservation','Arrived','reservation.fromdate',0x01,0x01,1,'DAY'
from relation where Link = 'Party Type' and LineID = 'Organization';

insert into workflow (ID, Process, State, Datename, Enabled, Prior, Duration, Unit) 
select distinct HeadID,'Reservation','PreDeparture','reservation.todate',0x01,0x01,1,'DAY'
from relation where Link = 'Party Type' and LineID = 'Organization';

insert into workflow (ID, Process, State, Datename, Enabled, Prior, Duration, Unit) 
select distinct HeadID,'Reservation','Departed','reservation.todate',0x01,0x00,7,'DAY'
from relation where Link = 'Party Type' and LineID = 'Organization';

END $$

DELIMITER ;