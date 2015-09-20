
package net.cbtltd.rest.maxxton;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.cbtltd.rest.maxxton package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Special_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Special");
    private final static QName _DebitCardContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardContainer");
    private final static QName _NewyseWebserviceError_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "NewyseWebserviceError");
    private final static QName _Property_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Property");
    private final static QName _ObjectContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ObjectContainer");
    private final static QName _Payment_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Payment");
    private final static QName _FacilityContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "FacilityContainer");
    private final static QName _TipTripContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "TipTripContainer");
    private final static QName _WorkOrderCategoryContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WorkOrderCategoryContainer");
    private final static QName _DebitCardCustomerInfo_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardCustomerInfo");
    private final static QName _WSDebitCard_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSDebitCard");
    private final static QName _ReservationBillLine_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ReservationBillLine");
    private final static QName _FacilityCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "FacilityCriteria");
    private final static QName _ReservedResourceContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ReservedResourceContainer");
    private final static QName _ReservationCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ReservationCriteria");
    private final static QName _ResourceCapacityContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResourceCapacityContainer");
    private final static QName _ResourceCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResourceCriteria");
    private final static QName _ActivityCategory_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ActivityCategory");
    private final static QName _DebitCardItemContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardItemContainer");
    private final static QName _Country_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Country");
    private final static QName _AccommodationKindCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationKindCriteria");
    private final static QName _WSWorkOrderArea_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSWorkOrderArea");
    private final static QName _Price_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Price");
    private final static QName _Brochure_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Brochure");
    private final static QName _BrochureCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "BrochureCriteria");
    private final static QName _VoucherContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "VoucherContainer");
    private final static QName _ResortCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResortCriteria");
    private final static QName _AccommodationKindContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationKindContainer");
    private final static QName _BrochureContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "BrochureContainer");
    private final static QName _DebitCardTransactionContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardTransactionContainer");
    private final static QName _DebitCardItem_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardItem");
    private final static QName _DebitCardCustomer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardCustomer");
    private final static QName _TipTrip_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "TipTrip");
    private final static QName _AvailabilityContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AvailabilityContainer");
    private final static QName _CustomerInfo_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "CustomerInfo");
    private final static QName _OpeningTime_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "OpeningTime");
    private final static QName _Image_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Image");
    private final static QName _AccommodationTypeSearchCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationTypeSearchCriteria");
    private final static QName _TipTripCategory_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "TipTripCategory");
    private final static QName _SourceContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "SourceContainer");
    private final static QName _Object_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Object");
    private final static QName _WSWorkOrderCategory_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSWorkOrderCategory");
    private final static QName _CountryContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "CountryContainer");
    private final static QName _WorkOrderAreaCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WorkOrderAreaCriteria");
    private final static QName _WorkOrderItemCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WorkOrderItemCriteria");
    private final static QName _Subject_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Subject");
    private final static QName _DebitCardItemCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardItemCriteria");
    private final static QName _AvailabilityCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AvailabilityCriteria");
    private final static QName _BrochureRequest_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "BrochureRequest");
    private final static QName _WSCustomerTitle_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSCustomerTitle");
    private final static QName _ImageCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ImageCriteria");
    private final static QName _PropertyContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "PropertyContainer");
    private final static QName _ResortActivityContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResortActivityContainer");
    private final static QName _AccommodationTypeSearchContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationTypeSearchContainer");
    private final static QName _Facility_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Facility");
    private final static QName _Address_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Address");
    private final static QName _ResortActivity_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResortActivity");
    private final static QName _TipTripCategoryContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "TipTripCategoryContainer");
    private final static QName _AccommodationTypeCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationTypeCriteria");
    private final static QName _ResourceAddition_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResourceAddition");
    private final static QName _VoucherItem_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "VoucherItem");
    private final static QName _WSSource_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSSource");
    private final static QName _WorkOrderCategoryCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WorkOrderCategoryCriteria");
    private final static QName _PincodeItem_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "PincodeItem");
    private final static QName _PropertyCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "PropertyCriteria");
    private final static QName _SubjectContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "SubjectContainer");
    private final static QName _WorkOrderAreaContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WorkOrderAreaContainer");
    private final static QName _CustomerReservationInfo_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "CustomerReservationInfo");
    private final static QName _WorkOrder_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WorkOrder");
    private final static QName _AccommodationTypeSearch_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationTypeSearch");
    private final static QName _ResortContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResortContainer");
    private final static QName _VoucherCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "VoucherCriteria");
    private final static QName _PincodeRegistration_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "PincodeRegistration");
    private final static QName _Resort_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Resort");
    private final static QName _ObjectAvailabilityContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ObjectAvailabilityContainer");
    private final static QName _DebitCardCustomerInfoContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardCustomerInfoContainer");
    private final static QName _WSReservedResource_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSReservedResource");
    private final static QName _SourceCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "SourceCriteria");
    private final static QName _AddressCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AddressCriteria");
    private final static QName _SessionCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "SessionCriteria");
    private final static QName _DebitCardConsumption_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardConsumption");
    private final static QName _WSReservationSubject_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSReservationSubject");
    private final static QName _ReservationSubjectContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ReservationSubjectContainer");
    private final static QName _DebitCardCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardCriteria");
    private final static QName _Reservation_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Reservation");
    private final static QName _PaymentCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "PaymentCriteria");
    private final static QName _AccommodationTypeContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationTypeContainer");
    private final static QName _ReservationSubjectCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ReservationSubjectCriteria");
    private final static QName _WSWorkOrderItem_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSWorkOrderItem");
    private final static QName _ResortActivityCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResortActivityCriteria");
    private final static QName _WSResourceCapacity_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WSResourceCapacity");
    private final static QName _ImageContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ImageContainer");
    private final static QName _ObjectAvailability_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ObjectAvailability");
    private final static QName _KCPincodes_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "KCPincodes");
    private final static QName _ReservationContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ReservationContainer");
    private final static QName _ResourceAdditionCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResourceAdditionCriteria");
    private final static QName _DebitCardCustomerContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "DebitCardCustomerContainer");
    private final static QName _TipTripCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "TipTripCriteria");
    private final static QName _CustomerTitleCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "CustomerTitleCriteria");
    private final static QName _Availability_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Availability");
    private final static QName _CustomerTitleContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "CustomerTitleContainer");
    private final static QName _AccommodationKind_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationKind");
    private final static QName _SubjectCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "SubjectCriteria");
    private final static QName _Customer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "Customer");
    private final static QName _ReservedResourceCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ReservedResourceCriteria");
    private final static QName _ResourceAdditionContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResourceAdditionContainer");
    private final static QName _WorkOrderItemContainer_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "WorkOrderItemContainer");
    private final static QName _ResourceCapacityCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ResourceCapacityCriteria");
    private final static QName _ObjectAvailabilityCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ObjectAvailabilityCriteria");
    private final static QName _ObjectCriteria_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "ObjectCriteria");
    private final static QName _GetReservation_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "GetReservation");
    private final static QName _AccommodationType_QNAME = new QName("http://service.newyse.ws.services.newyse.maxxton/", "AccommodationType");
    private final static QName _PropertyCriteriaIncludePartial_QNAME = new QName("", "IncludePartial");
    private final static QName _BrochureCriteriaResortCode_QNAME = new QName("", "ResortCode");
    private final static QName _ResortActivityCriteriaToDate_QNAME = new QName("", "ToDate");
    private final static QName _ResortActivityCriteriaFromDate_QNAME = new QName("", "FromDate");
    private final static QName _ImageCriteriaOnlyImageUrl_QNAME = new QName("", "OnlyImageUrl");
    private final static QName _AvailabilityCriteriaEndDate_QNAME = new QName("", "EndDate");
    private final static QName _AvailabilityCriteriaStartDate_QNAME = new QName("", "StartDate");
    private final static QName _AvailabilityCriteriaResourceId_QNAME = new QName("", "ResourceId");
    private final static QName _AvailabilityCriteriaNrOfNights_QNAME = new QName("", "NrOfNights");
    private final static QName _AvailabilityCriteriaSpecialCode_QNAME = new QName("", "SpecialCode");
    private final static QName _ResortActivityReserve_QNAME = new QName("", "Reserve");
    private final static QName _ResortActivityText_QNAME = new QName("", "Text");
    private final static QName _ResortActivityDescription_QNAME = new QName("", "Description");
    private final static QName _ResortActivityHeadText_QNAME = new QName("", "HeadText");
    private final static QName _ObjectCriteriaObjectId_QNAME = new QName("", "ObjectId");
    private final static QName _WorkOrderShortDescription_QNAME = new QName("", "ShortDescription");
    private final static QName _WorkOrderResortId_QNAME = new QName("", "ResortId");
    private final static QName _ResortCriteriaDistributionChannelId_QNAME = new QName("", "DistributionChannelId");
    private final static QName _ResortCriteriaDistributionChannelCode_QNAME = new QName("", "DistributionChannelCode");
    private final static QName _WSWorkOrderItemName_QNAME = new QName("", "Name");
    private final static QName _TipTripUrl_QNAME = new QName("", "Url");
    private final static QName _TipTripPrice_QNAME = new QName("", "Price");
    private final static QName _TipTripLocation_QNAME = new QName("", "Location");
    private final static QName _WorkOrderItemCriteriaWorkOrderCategoryId_QNAME = new QName("", "WorkOrderCategoryId");
    private final static QName _WorkOrderItemCriteriaWorkOrderItemId_QNAME = new QName("", "WorkOrderItemId");
    private final static QName _ReservationCriteriaSendMethodCode_QNAME = new QName("", "SendMethodCode");
    private final static QName _ReservationCriteriaSourceCode_QNAME = new QName("", "SourceCode");
    private final static QName _DebitCardItemCriteriaReservationId_QNAME = new QName("", "ReservationId");
    private final static QName _CustomerWorkPhone_QNAME = new QName("", "WorkPhone");
    private final static QName _CustomerZipcode_QNAME = new QName("", "Zipcode");
    private final static QName _CustomerPoBox_QNAME = new QName("", "PoBox");
    private final static QName _CustomerCustomerId_QNAME = new QName("", "CustomerId");
    private final static QName _CustomerIbanNumber_QNAME = new QName("", "IbanNumber");
    private final static QName _CustomerEmailAllowed_QNAME = new QName("", "EmailAllowed");
    private final static QName _CustomerLatitude_QNAME = new QName("", "Latitude");
    private final static QName _CustomerLastname_QNAME = new QName("", "Lastname");
    private final static QName _CustomerFirstname_QNAME = new QName("", "Firstname");
    private final static QName _CustomerPoBoxZipcode_QNAME = new QName("", "PoBoxZipcode");
    private final static QName _CustomerMailAllowed_QNAME = new QName("", "MailAllowed");
    private final static QName _CustomerVatNumber_QNAME = new QName("", "VatNumber");
    private final static QName _CustomerMobilePhone_QNAME = new QName("", "MobilePhone");
    private final static QName _CustomerDepartment_QNAME = new QName("", "Department");
    private final static QName _CustomerTitleCode_QNAME = new QName("", "TitleCode");
    private final static QName _CustomerAttentionOf_QNAME = new QName("", "AttentionOf");
    private final static QName _CustomerSex_QNAME = new QName("", "Sex");
    private final static QName _CustomerBankAccountTypeId_QNAME = new QName("", "BankAccountTypeId");
    private final static QName _CustomerMiddle_QNAME = new QName("", "Middle");
    private final static QName _CustomerFax_QNAME = new QName("", "Fax");
    private final static QName _CustomerCompanyName_QNAME = new QName("", "CompanyName");
    private final static QName _CustomerCountry_QNAME = new QName("", "Country");
    private final static QName _CustomerBirthDate_QNAME = new QName("", "BirthDate");
    private final static QName _CustomerCity_QNAME = new QName("", "City");
    private final static QName _CustomerPrivatePhone_QNAME = new QName("", "PrivatePhone");
    private final static QName _CustomerBankAccountNumber_QNAME = new QName("", "BankAccountNumber");
    private final static QName _CustomerEmail_QNAME = new QName("", "Email");
    private final static QName _CustomerDistrict_QNAME = new QName("", "District");
    private final static QName _CustomerLongitude_QNAME = new QName("", "Longitude");
    private final static QName _CustomerIsCompany_QNAME = new QName("", "IsCompany");
    private final static QName _UpdateReservationCriteriaArrivalDate_QNAME = new QName("", "ArrivalDate");
    private final static QName _UpdateReservationCriteriaDepartureDate_QNAME = new QName("", "DepartureDate");
    private final static QName _UpdateReservationCriteriaVoucher_QNAME = new QName("", "Voucher");
    private final static QName _TipTripCriteriaTipTripCategoryId_QNAME = new QName("", "TipTripCategoryId");
    private final static QName _SubjectQuantitySubjectId_QNAME = new QName("", "SubjectId");
    private final static QName _WorkOrderAreaCriteriaAreaId_QNAME = new QName("", "areaId");
    private final static QName _ResortResortParentId_QNAME = new QName("", "ResortParentId");
    private final static QName _AccommodationTypeKindCode_QNAME = new QName("", "KindCode");
    private final static QName _AccommodationTypeImageManagerId_QNAME = new QName("", "ImageManagerId");
    private final static QName _AccommodationTypePropertyManagerId_QNAME = new QName("", "PropertyManagerId");
    private final static QName _AccommodationTypeKindId_QNAME = new QName("", "KindId");
    private final static QName _AccommodationTypeCode_QNAME = new QName("", "Code");
    private final static QName _AccommodationTypeDescription2_QNAME = new QName("", "Description2");
    private final static QName _AccommodationTypeCriteriaOnlyBookable_QNAME = new QName("", "OnlyBookable");
    private final static QName _PropertyValue_QNAME = new QName("", "Value");
    private final static QName _PropertyPartial_QNAME = new QName("", "Partial");
    private final static QName _PropertyGroupName_QNAME = new QName("", "GroupName");
    private final static QName _PropertyGroupCode_QNAME = new QName("", "GroupCode");
    private final static QName _PropertyHasPreferenceCosts_QNAME = new QName("", "HasPreferenceCosts");
    private final static QName _ResourceAdditionMaxReservable_QNAME = new QName("", "MaxReservable");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.cbtltd.rest.maxxton
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WorkOrderItemContainer }
     * 
     */
    public WorkOrderItemContainer createWorkOrderItemContainer() {
        return new WorkOrderItemContainer();
    }

    /**
     * Create an instance of {@link ResourceAdditionContainer }
     * 
     */
    public ResourceAdditionContainer createResourceAdditionContainer() {
        return new ResourceAdditionContainer();
    }

    /**
     * Create an instance of {@link CustomerTitleContainer }
     * 
     */
    public CustomerTitleContainer createCustomerTitleContainer() {
        return new CustomerTitleContainer();
    }

    /**
     * Create an instance of {@link Availability }
     * 
     */
    public Availability createAvailability() {
        return new Availability();
    }

    /**
     * Create an instance of {@link ResourceAdditionCriteria }
     * 
     */
    public ResourceAdditionCriteria createResourceAdditionCriteria() {
        return new ResourceAdditionCriteria();
    }

    /**
     * Create an instance of {@link DebitCardCustomerContainer }
     * 
     */
    public DebitCardCustomerContainer createDebitCardCustomerContainer() {
        return new DebitCardCustomerContainer();
    }

    /**
     * Create an instance of {@link ReservationContainer }
     * 
     */
    public ReservationContainer createReservationContainer() {
        return new ReservationContainer();
    }

    /**
     * Create an instance of {@link KCPincodes }
     * 
     */
    public KCPincodes createKCPincodes() {
        return new KCPincodes();
    }

    /**
     * Create an instance of {@link ImageContainer }
     * 
     */
    public ImageContainer createImageContainer() {
        return new ImageContainer();
    }

    /**
     * Create an instance of {@link AccommodationTypeContainer }
     * 
     */
    public AccommodationTypeContainer createAccommodationTypeContainer() {
        return new AccommodationTypeContainer();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link ReservationSubjectContainer }
     * 
     */
    public ReservationSubjectContainer createReservationSubjectContainer() {
        return new ReservationSubjectContainer();
    }

    /**
     * Create an instance of {@link DebitCardCustomerInfoContainer }
     * 
     */
    public DebitCardCustomerInfoContainer createDebitCardCustomerInfoContainer() {
        return new DebitCardCustomerInfoContainer();
    }

    /**
     * Create an instance of {@link ObjectAvailabilityContainer }
     * 
     */
    public ObjectAvailabilityContainer createObjectAvailabilityContainer() {
        return new ObjectAvailabilityContainer();
    }

    /**
     * Create an instance of {@link ResortContainer }
     * 
     */
    public ResortContainer createResortContainer() {
        return new ResortContainer();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearch }
     * 
     */
    public AccommodationTypeSearch createAccommodationTypeSearch() {
        return new AccommodationTypeSearch();
    }

    /**
     * Create an instance of {@link SubjectContainer }
     * 
     */
    public SubjectContainer createSubjectContainer() {
        return new SubjectContainer();
    }

    /**
     * Create an instance of {@link PincodeItem }
     * 
     */
    public PincodeItem createPincodeItem() {
        return new PincodeItem();
    }

    /**
     * Create an instance of {@link WorkOrderAreaContainer }
     * 
     */
    public WorkOrderAreaContainer createWorkOrderAreaContainer() {
        return new WorkOrderAreaContainer();
    }

    /**
     * Create an instance of {@link VoucherItem }
     * 
     */
    public VoucherItem createVoucherItem() {
        return new VoucherItem();
    }

    /**
     * Create an instance of {@link TipTripCategoryContainer }
     * 
     */
    public TipTripCategoryContainer createTipTripCategoryContainer() {
        return new TipTripCategoryContainer();
    }

    /**
     * Create an instance of {@link Facility }
     * 
     */
    public Facility createFacility() {
        return new Facility();
    }

    /**
     * Create an instance of {@link ResortActivity }
     * 
     */
    public ResortActivity createResortActivity() {
        return new ResortActivity();
    }

    /**
     * Create an instance of {@link PropertyContainer }
     * 
     */
    public PropertyContainer createPropertyContainer() {
        return new PropertyContainer();
    }

    /**
     * Create an instance of {@link BrochureRequest }
     * 
     */
    public BrochureRequest createBrochureRequest() {
        return new BrochureRequest();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearchContainer }
     * 
     */
    public AccommodationTypeSearchContainer createAccommodationTypeSearchContainer() {
        return new AccommodationTypeSearchContainer();
    }

    /**
     * Create an instance of {@link ResortActivityContainer }
     * 
     */
    public ResortActivityContainer createResortActivityContainer() {
        return new ResortActivityContainer();
    }

    /**
     * Create an instance of {@link CountryContainer }
     * 
     */
    public CountryContainer createCountryContainer() {
        return new CountryContainer();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearchCriteria }
     * 
     */
    public AccommodationTypeSearchCriteria createAccommodationTypeSearchCriteria() {
        return new AccommodationTypeSearchCriteria();
    }

    /**
     * Create an instance of {@link SourceContainer }
     * 
     */
    public SourceContainer createSourceContainer() {
        return new SourceContainer();
    }

    /**
     * Create an instance of {@link TipTrip }
     * 
     */
    public TipTrip createTipTrip() {
        return new TipTrip();
    }

    /**
     * Create an instance of {@link AvailabilityContainer }
     * 
     */
    public AvailabilityContainer createAvailabilityContainer() {
        return new AvailabilityContainer();
    }

    /**
     * Create an instance of {@link BrochureContainer }
     * 
     */
    public BrochureContainer createBrochureContainer() {
        return new BrochureContainer();
    }

    /**
     * Create an instance of {@link DebitCardTransactionContainer }
     * 
     */
    public DebitCardTransactionContainer createDebitCardTransactionContainer() {
        return new DebitCardTransactionContainer();
    }

    /**
     * Create an instance of {@link AccommodationKindContainer }
     * 
     */
    public AccommodationKindContainer createAccommodationKindContainer() {
        return new AccommodationKindContainer();
    }

    /**
     * Create an instance of {@link VoucherContainer }
     * 
     */
    public VoucherContainer createVoucherContainer() {
        return new VoucherContainer();
    }

    /**
     * Create an instance of {@link DebitCardItemContainer }
     * 
     */
    public DebitCardItemContainer createDebitCardItemContainer() {
        return new DebitCardItemContainer();
    }

    /**
     * Create an instance of {@link ResourceCapacityContainer }
     * 
     */
    public ResourceCapacityContainer createResourceCapacityContainer() {
        return new ResourceCapacityContainer();
    }

    /**
     * Create an instance of {@link ReservationCriteria }
     * 
     */
    public ReservationCriteria createReservationCriteria() {
        return new ReservationCriteria();
    }

    /**
     * Create an instance of {@link ReservedResourceContainer }
     * 
     */
    public ReservedResourceContainer createReservedResourceContainer() {
        return new ReservedResourceContainer();
    }

    /**
     * Create an instance of {@link TipTripContainer }
     * 
     */
    public TipTripContainer createTipTripContainer() {
        return new TipTripContainer();
    }

    /**
     * Create an instance of {@link WorkOrderCategoryContainer }
     * 
     */
    public WorkOrderCategoryContainer createWorkOrderCategoryContainer() {
        return new WorkOrderCategoryContainer();
    }

    /**
     * Create an instance of {@link FacilityContainer }
     * 
     */
    public FacilityContainer createFacilityContainer() {
        return new FacilityContainer();
    }

    /**
     * Create an instance of {@link ObjectContainer }
     * 
     */
    public ObjectContainer createObjectContainer() {
        return new ObjectContainer();
    }

    /**
     * Create an instance of {@link DebitCardContainer }
     * 
     */
    public DebitCardContainer createDebitCardContainer() {
        return new DebitCardContainer();
    }

    /**
     * Create an instance of {@link Special }
     * 
     */
    public Special createSpecial() {
        return new Special();
    }

    /**
     * Create an instance of {@link NewyseWebserviceError }
     * 
     */
    public NewyseWebserviceError createNewyseWebserviceError() {
        return new NewyseWebserviceError();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link Payment }
     * 
     */
    public Payment createPayment() {
        return new Payment();
    }

    /**
     * Create an instance of {@link DebitCardCustomerInfo }
     * 
     */
    public DebitCardCustomerInfo createDebitCardCustomerInfo() {
        return new DebitCardCustomerInfo();
    }

    /**
     * Create an instance of {@link ReservationBillLine }
     * 
     */
    public ReservationBillLine createReservationBillLine() {
        return new ReservationBillLine();
    }

    /**
     * Create an instance of {@link WSDebitCard }
     * 
     */
    public WSDebitCard createWSDebitCard() {
        return new WSDebitCard();
    }

    /**
     * Create an instance of {@link FacilityCriteria }
     * 
     */
    public FacilityCriteria createFacilityCriteria() {
        return new FacilityCriteria();
    }

    /**
     * Create an instance of {@link ResourceCriteria }
     * 
     */
    public ResourceCriteria createResourceCriteria() {
        return new ResourceCriteria();
    }

    /**
     * Create an instance of {@link ActivityCategory }
     * 
     */
    public ActivityCategory createActivityCategory() {
        return new ActivityCategory();
    }

    /**
     * Create an instance of {@link Country }
     * 
     */
    public Country createCountry() {
        return new Country();
    }

    /**
     * Create an instance of {@link AccommodationKindCriteria }
     * 
     */
    public AccommodationKindCriteria createAccommodationKindCriteria() {
        return new AccommodationKindCriteria();
    }

    /**
     * Create an instance of {@link WSWorkOrderArea }
     * 
     */
    public WSWorkOrderArea createWSWorkOrderArea() {
        return new WSWorkOrderArea();
    }

    /**
     * Create an instance of {@link Price }
     * 
     */
    public Price createPrice() {
        return new Price();
    }

    /**
     * Create an instance of {@link BrochureCriteria }
     * 
     */
    public BrochureCriteria createBrochureCriteria() {
        return new BrochureCriteria();
    }

    /**
     * Create an instance of {@link ResortCriteria }
     * 
     */
    public ResortCriteria createResortCriteria() {
        return new ResortCriteria();
    }

    /**
     * Create an instance of {@link Brochure }
     * 
     */
    public Brochure createBrochure() {
        return new Brochure();
    }

    /**
     * Create an instance of {@link DebitCardCustomer }
     * 
     */
    public DebitCardCustomer createDebitCardCustomer() {
        return new DebitCardCustomer();
    }

    /**
     * Create an instance of {@link DebitCardItem }
     * 
     */
    public DebitCardItem createDebitCardItem() {
        return new DebitCardItem();
    }

    /**
     * Create an instance of {@link OpeningTime }
     * 
     */
    public OpeningTime createOpeningTime() {
        return new OpeningTime();
    }

    /**
     * Create an instance of {@link CustomerInfo }
     * 
     */
    public CustomerInfo createCustomerInfo() {
        return new CustomerInfo();
    }

    /**
     * Create an instance of {@link Image }
     * 
     */
    public Image createImage() {
        return new Image();
    }

    /**
     * Create an instance of {@link TipTripCategory }
     * 
     */
    public TipTripCategory createTipTripCategory() {
        return new TipTripCategory();
    }

    /**
     * Create an instance of {@link WSWorkOrderCategory }
     * 
     */
    public WSWorkOrderCategory createWSWorkOrderCategory() {
        return new WSWorkOrderCategory();
    }

    /**
     * Create an instance of {@link Object }
     * 
     */
    public Object createObject() {
        return new Object();
    }

    /**
     * Create an instance of {@link DebitCardItemCriteria }
     * 
     */
    public DebitCardItemCriteria createDebitCardItemCriteria() {
        return new DebitCardItemCriteria();
    }

    /**
     * Create an instance of {@link Subject }
     * 
     */
    public Subject createSubject() {
        return new Subject();
    }

    /**
     * Create an instance of {@link WorkOrderAreaCriteria }
     * 
     */
    public WorkOrderAreaCriteria createWorkOrderAreaCriteria() {
        return new WorkOrderAreaCriteria();
    }

    /**
     * Create an instance of {@link WorkOrderItemCriteria }
     * 
     */
    public WorkOrderItemCriteria createWorkOrderItemCriteria() {
        return new WorkOrderItemCriteria();
    }

    /**
     * Create an instance of {@link AvailabilityCriteria }
     * 
     */
    public AvailabilityCriteria createAvailabilityCriteria() {
        return new AvailabilityCriteria();
    }

    /**
     * Create an instance of {@link WSCustomerTitle }
     * 
     */
    public WSCustomerTitle createWSCustomerTitle() {
        return new WSCustomerTitle();
    }

    /**
     * Create an instance of {@link ImageCriteria }
     * 
     */
    public ImageCriteria createImageCriteria() {
        return new ImageCriteria();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link AccommodationTypeCriteria }
     * 
     */
    public AccommodationTypeCriteria createAccommodationTypeCriteria() {
        return new AccommodationTypeCriteria();
    }

    /**
     * Create an instance of {@link ResourceAddition }
     * 
     */
    public ResourceAddition createResourceAddition() {
        return new ResourceAddition();
    }

    /**
     * Create an instance of {@link WSSource }
     * 
     */
    public WSSource createWSSource() {
        return new WSSource();
    }

    /**
     * Create an instance of {@link CustomerReservationInfo }
     * 
     */
    public CustomerReservationInfo createCustomerReservationInfo() {
        return new CustomerReservationInfo();
    }

    /**
     * Create an instance of {@link WorkOrderCategoryCriteria }
     * 
     */
    public WorkOrderCategoryCriteria createWorkOrderCategoryCriteria() {
        return new WorkOrderCategoryCriteria();
    }

    /**
     * Create an instance of {@link PropertyCriteria }
     * 
     */
    public PropertyCriteria createPropertyCriteria() {
        return new PropertyCriteria();
    }

    /**
     * Create an instance of {@link WorkOrder }
     * 
     */
    public WorkOrder createWorkOrder() {
        return new WorkOrder();
    }

    /**
     * Create an instance of {@link PincodeRegistration }
     * 
     */
    public PincodeRegistration createPincodeRegistration() {
        return new PincodeRegistration();
    }

    /**
     * Create an instance of {@link VoucherCriteria }
     * 
     */
    public VoucherCriteria createVoucherCriteria() {
        return new VoucherCriteria();
    }

    /**
     * Create an instance of {@link Resort }
     * 
     */
    public Resort createResort() {
        return new Resort();
    }

    /**
     * Create an instance of {@link SourceCriteria }
     * 
     */
    public SourceCriteria createSourceCriteria() {
        return new SourceCriteria();
    }

    /**
     * Create an instance of {@link WSReservedResource }
     * 
     */
    public WSReservedResource createWSReservedResource() {
        return new WSReservedResource();
    }

    /**
     * Create an instance of {@link AddressCriteria }
     * 
     */
    public AddressCriteria createAddressCriteria() {
        return new AddressCriteria();
    }

    /**
     * Create an instance of {@link SessionCriteria }
     * 
     */
    public SessionCriteria createSessionCriteria() {
        return new SessionCriteria();
    }

    /**
     * Create an instance of {@link DebitCardConsumption }
     * 
     */
    public DebitCardConsumption createDebitCardConsumption() {
        return new DebitCardConsumption();
    }

    /**
     * Create an instance of {@link WSReservationSubject }
     * 
     */
    public WSReservationSubject createWSReservationSubject() {
        return new WSReservationSubject();
    }

    /**
     * Create an instance of {@link DebitCardCriteria }
     * 
     */
    public DebitCardCriteria createDebitCardCriteria() {
        return new DebitCardCriteria();
    }

    /**
     * Create an instance of {@link PaymentCriteria }
     * 
     */
    public PaymentCriteria createPaymentCriteria() {
        return new PaymentCriteria();
    }

    /**
     * Create an instance of {@link ReservationSubjectCriteria }
     * 
     */
    public ReservationSubjectCriteria createReservationSubjectCriteria() {
        return new ReservationSubjectCriteria();
    }

    /**
     * Create an instance of {@link ResortActivityCriteria }
     * 
     */
    public ResortActivityCriteria createResortActivityCriteria() {
        return new ResortActivityCriteria();
    }

    /**
     * Create an instance of {@link WSWorkOrderItem }
     * 
     */
    public WSWorkOrderItem createWSWorkOrderItem() {
        return new WSWorkOrderItem();
    }

    /**
     * Create an instance of {@link WSResourceCapacity }
     * 
     */
    public WSResourceCapacity createWSResourceCapacity() {
        return new WSResourceCapacity();
    }

    /**
     * Create an instance of {@link ObjectAvailability }
     * 
     */
    public ObjectAvailability createObjectAvailability() {
        return new ObjectAvailability();
    }

    /**
     * Create an instance of {@link TipTripCriteria }
     * 
     */
    public TipTripCriteria createTipTripCriteria() {
        return new TipTripCriteria();
    }

    /**
     * Create an instance of {@link CustomerTitleCriteria }
     * 
     */
    public CustomerTitleCriteria createCustomerTitleCriteria() {
        return new CustomerTitleCriteria();
    }

    /**
     * Create an instance of {@link SubjectCriteria }
     * 
     */
    public SubjectCriteria createSubjectCriteria() {
        return new SubjectCriteria();
    }

    /**
     * Create an instance of {@link AccommodationKind }
     * 
     */
    public AccommodationKind createAccommodationKind() {
        return new AccommodationKind();
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link ReservedResourceCriteria }
     * 
     */
    public ReservedResourceCriteria createReservedResourceCriteria() {
        return new ReservedResourceCriteria();
    }

    /**
     * Create an instance of {@link ResourceCapacityCriteria }
     * 
     */
    public ResourceCapacityCriteria createResourceCapacityCriteria() {
        return new ResourceCapacityCriteria();
    }

    /**
     * Create an instance of {@link ObjectAvailabilityCriteria }
     * 
     */
    public ObjectAvailabilityCriteria createObjectAvailabilityCriteria() {
        return new ObjectAvailabilityCriteria();
    }

    /**
     * Create an instance of {@link ObjectCriteria }
     * 
     */
    public ObjectCriteria createObjectCriteria() {
        return new ObjectCriteria();
    }

    /**
     * Create an instance of {@link GetReservation }
     * 
     */
    public GetReservation createGetReservation() {
        return new GetReservation();
    }

    /**
     * Create an instance of {@link AccommodationType }
     * 
     */
    public AccommodationType createAccommodationType() {
        return new AccommodationType();
    }

    /**
     * Create an instance of {@link SubjectQuantity }
     * 
     */
    public SubjectQuantity createSubjectQuantity() {
        return new SubjectQuantity();
    }

    /**
     * Create an instance of {@link Accommodation }
     * 
     */
    public Accommodation createAccommodation() {
        return new Accommodation();
    }

    /**
     * Create an instance of {@link Preference }
     * 
     */
    public Preference createPreference() {
        return new Preference();
    }

    /**
     * Create an instance of {@link UpdateReservationCriteria }
     * 
     */
    public UpdateReservationCriteria createUpdateReservationCriteria() {
        return new UpdateReservationCriteria();
    }

    /**
     * Create an instance of {@link Addition }
     * 
     */
    public Addition createAddition() {
        return new Addition();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearchObject }
     * 
     */
    public AccommodationTypeSearchObject createAccommodationTypeSearchObject() {
        return new AccommodationTypeSearchObject();
    }

    /**
     * Create an instance of {@link WorkOrderItemContainer.WorkOrderItems }
     * 
     */
    public WorkOrderItemContainer.WorkOrderItems createWorkOrderItemContainerWorkOrderItems() {
        return new WorkOrderItemContainer.WorkOrderItems();
    }

    /**
     * Create an instance of {@link ResourceAdditionContainer.ResourceAdditions }
     * 
     */
    public ResourceAdditionContainer.ResourceAdditions createResourceAdditionContainerResourceAdditions() {
        return new ResourceAdditionContainer.ResourceAdditions();
    }

    /**
     * Create an instance of {@link CustomerTitleContainer.CustomerTitles }
     * 
     */
    public CustomerTitleContainer.CustomerTitles createCustomerTitleContainerCustomerTitles() {
        return new CustomerTitleContainer.CustomerTitles();
    }

    /**
     * Create an instance of {@link Availability.Prices }
     * 
     */
    public Availability.Prices createAvailabilityPrices() {
        return new Availability.Prices();
    }

    /**
     * Create an instance of {@link ResourceAdditionCriteria.SubjectQuantities }
     * 
     */
    public ResourceAdditionCriteria.SubjectQuantities createResourceAdditionCriteriaSubjectQuantities() {
        return new ResourceAdditionCriteria.SubjectQuantities();
    }

    /**
     * Create an instance of {@link DebitCardCustomerContainer.DebitCardCustomers }
     * 
     */
    public DebitCardCustomerContainer.DebitCardCustomers createDebitCardCustomerContainerDebitCardCustomers() {
        return new DebitCardCustomerContainer.DebitCardCustomers();
    }

    /**
     * Create an instance of {@link ReservationContainer.Reservations }
     * 
     */
    public ReservationContainer.Reservations createReservationContainerReservations() {
        return new ReservationContainer.Reservations();
    }

    /**
     * Create an instance of {@link KCPincodes.PincodeItems }
     * 
     */
    public KCPincodes.PincodeItems createKCPincodesPincodeItems() {
        return new KCPincodes.PincodeItems();
    }

    /**
     * Create an instance of {@link ImageContainer.Images }
     * 
     */
    public ImageContainer.Images createImageContainerImages() {
        return new ImageContainer.Images();
    }

    /**
     * Create an instance of {@link AccommodationTypeContainer.AccommodationTypes }
     * 
     */
    public AccommodationTypeContainer.AccommodationTypes createAccommodationTypeContainerAccommodationTypes() {
        return new AccommodationTypeContainer.AccommodationTypes();
    }

    /**
     * Create an instance of {@link Reservation.BillLines }
     * 
     */
    public Reservation.BillLines createReservationBillLines() {
        return new Reservation.BillLines();
    }

    /**
     * Create an instance of {@link Reservation.AgentBillLines }
     * 
     */
    public Reservation.AgentBillLines createReservationAgentBillLines() {
        return new Reservation.AgentBillLines();
    }

    /**
     * Create an instance of {@link Reservation.PayingCustomerBillLines }
     * 
     */
    public Reservation.PayingCustomerBillLines createReservationPayingCustomerBillLines() {
        return new Reservation.PayingCustomerBillLines();
    }

    /**
     * Create an instance of {@link Reservation.Infotexts }
     * 
     */
    public Reservation.Infotexts createReservationInfotexts() {
        return new Reservation.Infotexts();
    }

    /**
     * Create an instance of {@link Reservation.ReservedResources }
     * 
     */
    public Reservation.ReservedResources createReservationReservedResources() {
        return new Reservation.ReservedResources();
    }

    /**
     * Create an instance of {@link ReservationSubjectContainer.ReservationSubjects }
     * 
     */
    public ReservationSubjectContainer.ReservationSubjects createReservationSubjectContainerReservationSubjects() {
        return new ReservationSubjectContainer.ReservationSubjects();
    }

    /**
     * Create an instance of {@link DebitCardCustomerInfoContainer.DebitCardCustomerInfoItems }
     * 
     */
    public DebitCardCustomerInfoContainer.DebitCardCustomerInfoItems createDebitCardCustomerInfoContainerDebitCardCustomerInfoItems() {
        return new DebitCardCustomerInfoContainer.DebitCardCustomerInfoItems();
    }

    /**
     * Create an instance of {@link ObjectAvailabilityContainer.ObjectAvailabilities }
     * 
     */
    public ObjectAvailabilityContainer.ObjectAvailabilities createObjectAvailabilityContainerObjectAvailabilities() {
        return new ObjectAvailabilityContainer.ObjectAvailabilities();
    }

    /**
     * Create an instance of {@link ResortContainer.Resorts }
     * 
     */
    public ResortContainer.Resorts createResortContainerResorts() {
        return new ResortContainer.Resorts();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearch.AccommodationTypeSearchObjects }
     * 
     */
    public AccommodationTypeSearch.AccommodationTypeSearchObjects createAccommodationTypeSearchAccommodationTypeSearchObjects() {
        return new AccommodationTypeSearch.AccommodationTypeSearchObjects();
    }

    /**
     * Create an instance of {@link SubjectContainer.Subjects }
     * 
     */
    public SubjectContainer.Subjects createSubjectContainerSubjects() {
        return new SubjectContainer.Subjects();
    }

    /**
     * Create an instance of {@link PincodeItem.Pincodes }
     * 
     */
    public PincodeItem.Pincodes createPincodeItemPincodes() {
        return new PincodeItem.Pincodes();
    }

    /**
     * Create an instance of {@link WorkOrderAreaContainer.WorkOrderAreas }
     * 
     */
    public WorkOrderAreaContainer.WorkOrderAreas createWorkOrderAreaContainerWorkOrderAreas() {
        return new WorkOrderAreaContainer.WorkOrderAreas();
    }

    /**
     * Create an instance of {@link VoucherItem.Codes }
     * 
     */
    public VoucherItem.Codes createVoucherItemCodes() {
        return new VoucherItem.Codes();
    }

    /**
     * Create an instance of {@link TipTripCategoryContainer.TipTripCategories }
     * 
     */
    public TipTripCategoryContainer.TipTripCategories createTipTripCategoryContainerTipTripCategories() {
        return new TipTripCategoryContainer.TipTripCategories();
    }

    /**
     * Create an instance of {@link Facility.Resorts }
     * 
     */
    public Facility.Resorts createFacilityResorts() {
        return new Facility.Resorts();
    }

    /**
     * Create an instance of {@link Facility.OpeningTimes }
     * 
     */
    public Facility.OpeningTimes createFacilityOpeningTimes() {
        return new Facility.OpeningTimes();
    }

    /**
     * Create an instance of {@link ResortActivity.ActivityCategories }
     * 
     */
    public ResortActivity.ActivityCategories createResortActivityActivityCategories() {
        return new ResortActivity.ActivityCategories();
    }

    /**
     * Create an instance of {@link ResortActivity.Resorts }
     * 
     */
    public ResortActivity.Resorts createResortActivityResorts() {
        return new ResortActivity.Resorts();
    }

    /**
     * Create an instance of {@link ResortActivity.OpeningTimes }
     * 
     */
    public ResortActivity.OpeningTimes createResortActivityOpeningTimes() {
        return new ResortActivity.OpeningTimes();
    }

    /**
     * Create an instance of {@link PropertyContainer.Properties }
     * 
     */
    public PropertyContainer.Properties createPropertyContainerProperties() {
        return new PropertyContainer.Properties();
    }

    /**
     * Create an instance of {@link BrochureRequest.BrochureCodes }
     * 
     */
    public BrochureRequest.BrochureCodes createBrochureRequestBrochureCodes() {
        return new BrochureRequest.BrochureCodes();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearchContainer.AccommodationTypes }
     * 
     */
    public AccommodationTypeSearchContainer.AccommodationTypes createAccommodationTypeSearchContainerAccommodationTypes() {
        return new AccommodationTypeSearchContainer.AccommodationTypes();
    }

    /**
     * Create an instance of {@link ResortActivityContainer.ResortActivities }
     * 
     */
    public ResortActivityContainer.ResortActivities createResortActivityContainerResortActivities() {
        return new ResortActivityContainer.ResortActivities();
    }

    /**
     * Create an instance of {@link CountryContainer.Countries }
     * 
     */
    public CountryContainer.Countries createCountryContainerCountries() {
        return new CountryContainer.Countries();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearchCriteria.SubjectQuantities }
     * 
     */
    public AccommodationTypeSearchCriteria.SubjectQuantities createAccommodationTypeSearchCriteriaSubjectQuantities() {
        return new AccommodationTypeSearchCriteria.SubjectQuantities();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearchCriteria.SpecialCodes }
     * 
     */
    public AccommodationTypeSearchCriteria.SpecialCodes createAccommodationTypeSearchCriteriaSpecialCodes() {
        return new AccommodationTypeSearchCriteria.SpecialCodes();
    }

    /**
     * Create an instance of {@link AccommodationTypeSearchCriteria.Properties }
     * 
     */
    public AccommodationTypeSearchCriteria.Properties createAccommodationTypeSearchCriteriaProperties() {
        return new AccommodationTypeSearchCriteria.Properties();
    }

    /**
     * Create an instance of {@link SourceContainer.Sources }
     * 
     */
    public SourceContainer.Sources createSourceContainerSources() {
        return new SourceContainer.Sources();
    }

    /**
     * Create an instance of {@link TipTrip.Resorts }
     * 
     */
    public TipTrip.Resorts createTipTripResorts() {
        return new TipTrip.Resorts();
    }

    /**
     * Create an instance of {@link AvailabilityContainer.Availabilities }
     * 
     */
    public AvailabilityContainer.Availabilities createAvailabilityContainerAvailabilities() {
        return new AvailabilityContainer.Availabilities();
    }

    /**
     * Create an instance of {@link BrochureContainer.Brochures }
     * 
     */
    public BrochureContainer.Brochures createBrochureContainerBrochures() {
        return new BrochureContainer.Brochures();
    }

    /**
     * Create an instance of {@link DebitCardTransactionContainer.DebitCardConsumptions }
     * 
     */
    public DebitCardTransactionContainer.DebitCardConsumptions createDebitCardTransactionContainerDebitCardConsumptions() {
        return new DebitCardTransactionContainer.DebitCardConsumptions();
    }

    /**
     * Create an instance of {@link AccommodationKindContainer.AccommodationKinds }
     * 
     */
    public AccommodationKindContainer.AccommodationKinds createAccommodationKindContainerAccommodationKinds() {
        return new AccommodationKindContainer.AccommodationKinds();
    }

    /**
     * Create an instance of {@link VoucherContainer.Vouchers }
     * 
     */
    public VoucherContainer.Vouchers createVoucherContainerVouchers() {
        return new VoucherContainer.Vouchers();
    }

    /**
     * Create an instance of {@link DebitCardItemContainer.DebitCardItems }
     * 
     */
    public DebitCardItemContainer.DebitCardItems createDebitCardItemContainerDebitCardItems() {
        return new DebitCardItemContainer.DebitCardItems();
    }

    /**
     * Create an instance of {@link ResourceCapacityContainer.ResourceCapacity }
     * 
     */
    public ResourceCapacityContainer.ResourceCapacity createResourceCapacityContainerResourceCapacity() {
        return new ResourceCapacityContainer.ResourceCapacity();
    }

    /**
     * Create an instance of {@link ReservationCriteria.Preferences }
     * 
     */
    public ReservationCriteria.Preferences createReservationCriteriaPreferences() {
        return new ReservationCriteria.Preferences();
    }

    /**
     * Create an instance of {@link ReservationCriteria.SubjectQuantities }
     * 
     */
    public ReservationCriteria.SubjectQuantities createReservationCriteriaSubjectQuantities() {
        return new ReservationCriteria.SubjectQuantities();
    }

    /**
     * Create an instance of {@link ReservationCriteria.Additions }
     * 
     */
    public ReservationCriteria.Additions createReservationCriteriaAdditions() {
        return new ReservationCriteria.Additions();
    }

    /**
     * Create an instance of {@link ReservedResourceContainer.ReservedResourceList }
     * 
     */
    public ReservedResourceContainer.ReservedResourceList createReservedResourceContainerReservedResourceList() {
        return new ReservedResourceContainer.ReservedResourceList();
    }

    /**
     * Create an instance of {@link TipTripContainer.TipTrips }
     * 
     */
    public TipTripContainer.TipTrips createTipTripContainerTipTrips() {
        return new TipTripContainer.TipTrips();
    }

    /**
     * Create an instance of {@link WorkOrderCategoryContainer.WorkOrderCategories }
     * 
     */
    public WorkOrderCategoryContainer.WorkOrderCategories createWorkOrderCategoryContainerWorkOrderCategories() {
        return new WorkOrderCategoryContainer.WorkOrderCategories();
    }

    /**
     * Create an instance of {@link FacilityContainer.Facilities }
     * 
     */
    public FacilityContainer.Facilities createFacilityContainerFacilities() {
        return new FacilityContainer.Facilities();
    }

    /**
     * Create an instance of {@link ObjectContainer.Objects }
     * 
     */
    public ObjectContainer.Objects createObjectContainerObjects() {
        return new ObjectContainer.Objects();
    }

    /**
     * Create an instance of {@link DebitCardContainer.DebitCards }
     * 
     */
    public DebitCardContainer.DebitCards createDebitCardContainerDebitCards() {
        return new DebitCardContainer.DebitCards();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Special }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Special")
    public JAXBElement<Special> createSpecial(Special value) {
        return new JAXBElement<Special>(_Special_QNAME, Special.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardContainer")
    public JAXBElement<DebitCardContainer> createDebitCardContainer(DebitCardContainer value) {
        return new JAXBElement<DebitCardContainer>(_DebitCardContainer_QNAME, DebitCardContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewyseWebserviceError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "NewyseWebserviceError")
    public JAXBElement<NewyseWebserviceError> createNewyseWebserviceError(NewyseWebserviceError value) {
        return new JAXBElement<NewyseWebserviceError>(_NewyseWebserviceError_QNAME, NewyseWebserviceError.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Property }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Property")
    public JAXBElement<Property> createProperty(Property value) {
        return new JAXBElement<Property>(_Property_QNAME, Property.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ObjectContainer")
    public JAXBElement<ObjectContainer> createObjectContainer(ObjectContainer value) {
        return new JAXBElement<ObjectContainer>(_ObjectContainer_QNAME, ObjectContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Payment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Payment")
    public JAXBElement<Payment> createPayment(Payment value) {
        return new JAXBElement<Payment>(_Payment_QNAME, Payment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FacilityContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "FacilityContainer")
    public JAXBElement<FacilityContainer> createFacilityContainer(FacilityContainer value) {
        return new JAXBElement<FacilityContainer>(_FacilityContainer_QNAME, FacilityContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipTripContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "TipTripContainer")
    public JAXBElement<TipTripContainer> createTipTripContainer(TipTripContainer value) {
        return new JAXBElement<TipTripContainer>(_TipTripContainer_QNAME, TipTripContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkOrderCategoryContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WorkOrderCategoryContainer")
    public JAXBElement<WorkOrderCategoryContainer> createWorkOrderCategoryContainer(WorkOrderCategoryContainer value) {
        return new JAXBElement<WorkOrderCategoryContainer>(_WorkOrderCategoryContainer_QNAME, WorkOrderCategoryContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardCustomerInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardCustomerInfo")
    public JAXBElement<DebitCardCustomerInfo> createDebitCardCustomerInfo(DebitCardCustomerInfo value) {
        return new JAXBElement<DebitCardCustomerInfo>(_DebitCardCustomerInfo_QNAME, DebitCardCustomerInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSDebitCard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSDebitCard")
    public JAXBElement<WSDebitCard> createWSDebitCard(WSDebitCard value) {
        return new JAXBElement<WSDebitCard>(_WSDebitCard_QNAME, WSDebitCard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationBillLine }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ReservationBillLine")
    public JAXBElement<ReservationBillLine> createReservationBillLine(ReservationBillLine value) {
        return new JAXBElement<ReservationBillLine>(_ReservationBillLine_QNAME, ReservationBillLine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FacilityCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "FacilityCriteria")
    public JAXBElement<FacilityCriteria> createFacilityCriteria(FacilityCriteria value) {
        return new JAXBElement<FacilityCriteria>(_FacilityCriteria_QNAME, FacilityCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservedResourceContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ReservedResourceContainer")
    public JAXBElement<ReservedResourceContainer> createReservedResourceContainer(ReservedResourceContainer value) {
        return new JAXBElement<ReservedResourceContainer>(_ReservedResourceContainer_QNAME, ReservedResourceContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ReservationCriteria")
    public JAXBElement<ReservationCriteria> createReservationCriteria(ReservationCriteria value) {
        return new JAXBElement<ReservationCriteria>(_ReservationCriteria_QNAME, ReservationCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceCapacityContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResourceCapacityContainer")
    public JAXBElement<ResourceCapacityContainer> createResourceCapacityContainer(ResourceCapacityContainer value) {
        return new JAXBElement<ResourceCapacityContainer>(_ResourceCapacityContainer_QNAME, ResourceCapacityContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResourceCriteria")
    public JAXBElement<ResourceCriteria> createResourceCriteria(ResourceCriteria value) {
        return new JAXBElement<ResourceCriteria>(_ResourceCriteria_QNAME, ResourceCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActivityCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ActivityCategory")
    public JAXBElement<ActivityCategory> createActivityCategory(ActivityCategory value) {
        return new JAXBElement<ActivityCategory>(_ActivityCategory_QNAME, ActivityCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardItemContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardItemContainer")
    public JAXBElement<DebitCardItemContainer> createDebitCardItemContainer(DebitCardItemContainer value) {
        return new JAXBElement<DebitCardItemContainer>(_DebitCardItemContainer_QNAME, DebitCardItemContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Country }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Country")
    public JAXBElement<Country> createCountry(Country value) {
        return new JAXBElement<Country>(_Country_QNAME, Country.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationKindCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationKindCriteria")
    public JAXBElement<AccommodationKindCriteria> createAccommodationKindCriteria(AccommodationKindCriteria value) {
        return new JAXBElement<AccommodationKindCriteria>(_AccommodationKindCriteria_QNAME, AccommodationKindCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSWorkOrderArea }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSWorkOrderArea")
    public JAXBElement<WSWorkOrderArea> createWSWorkOrderArea(WSWorkOrderArea value) {
        return new JAXBElement<WSWorkOrderArea>(_WSWorkOrderArea_QNAME, WSWorkOrderArea.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Price }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Price")
    public JAXBElement<Price> createPrice(Price value) {
        return new JAXBElement<Price>(_Price_QNAME, Price.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Brochure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Brochure")
    public JAXBElement<Brochure> createBrochure(Brochure value) {
        return new JAXBElement<Brochure>(_Brochure_QNAME, Brochure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BrochureCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "BrochureCriteria")
    public JAXBElement<BrochureCriteria> createBrochureCriteria(BrochureCriteria value) {
        return new JAXBElement<BrochureCriteria>(_BrochureCriteria_QNAME, BrochureCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoucherContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "VoucherContainer")
    public JAXBElement<VoucherContainer> createVoucherContainer(VoucherContainer value) {
        return new JAXBElement<VoucherContainer>(_VoucherContainer_QNAME, VoucherContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResortCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResortCriteria")
    public JAXBElement<ResortCriteria> createResortCriteria(ResortCriteria value) {
        return new JAXBElement<ResortCriteria>(_ResortCriteria_QNAME, ResortCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationKindContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationKindContainer")
    public JAXBElement<AccommodationKindContainer> createAccommodationKindContainer(AccommodationKindContainer value) {
        return new JAXBElement<AccommodationKindContainer>(_AccommodationKindContainer_QNAME, AccommodationKindContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BrochureContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "BrochureContainer")
    public JAXBElement<BrochureContainer> createBrochureContainer(BrochureContainer value) {
        return new JAXBElement<BrochureContainer>(_BrochureContainer_QNAME, BrochureContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardTransactionContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardTransactionContainer")
    public JAXBElement<DebitCardTransactionContainer> createDebitCardTransactionContainer(DebitCardTransactionContainer value) {
        return new JAXBElement<DebitCardTransactionContainer>(_DebitCardTransactionContainer_QNAME, DebitCardTransactionContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardItem")
    public JAXBElement<DebitCardItem> createDebitCardItem(DebitCardItem value) {
        return new JAXBElement<DebitCardItem>(_DebitCardItem_QNAME, DebitCardItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardCustomer")
    public JAXBElement<DebitCardCustomer> createDebitCardCustomer(DebitCardCustomer value) {
        return new JAXBElement<DebitCardCustomer>(_DebitCardCustomer_QNAME, DebitCardCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipTrip }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "TipTrip")
    public JAXBElement<TipTrip> createTipTrip(TipTrip value) {
        return new JAXBElement<TipTrip>(_TipTrip_QNAME, TipTrip.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AvailabilityContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AvailabilityContainer")
    public JAXBElement<AvailabilityContainer> createAvailabilityContainer(AvailabilityContainer value) {
        return new JAXBElement<AvailabilityContainer>(_AvailabilityContainer_QNAME, AvailabilityContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "CustomerInfo")
    public JAXBElement<CustomerInfo> createCustomerInfo(CustomerInfo value) {
        return new JAXBElement<CustomerInfo>(_CustomerInfo_QNAME, CustomerInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OpeningTime }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "OpeningTime")
    public JAXBElement<OpeningTime> createOpeningTime(OpeningTime value) {
        return new JAXBElement<OpeningTime>(_OpeningTime_QNAME, OpeningTime.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Image }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Image")
    public JAXBElement<Image> createImage(Image value) {
        return new JAXBElement<Image>(_Image_QNAME, Image.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationTypeSearchCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationTypeSearchCriteria")
    public JAXBElement<AccommodationTypeSearchCriteria> createAccommodationTypeSearchCriteria(AccommodationTypeSearchCriteria value) {
        return new JAXBElement<AccommodationTypeSearchCriteria>(_AccommodationTypeSearchCriteria_QNAME, AccommodationTypeSearchCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipTripCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "TipTripCategory")
    public JAXBElement<TipTripCategory> createTipTripCategory(TipTripCategory value) {
        return new JAXBElement<TipTripCategory>(_TipTripCategory_QNAME, TipTripCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SourceContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "SourceContainer")
    public JAXBElement<SourceContainer> createSourceContainer(SourceContainer value) {
        return new JAXBElement<SourceContainer>(_SourceContainer_QNAME, SourceContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Object")
    public JAXBElement<Object> createObject(Object value) {
        return new JAXBElement<Object>(_Object_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSWorkOrderCategory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSWorkOrderCategory")
    public JAXBElement<WSWorkOrderCategory> createWSWorkOrderCategory(WSWorkOrderCategory value) {
        return new JAXBElement<WSWorkOrderCategory>(_WSWorkOrderCategory_QNAME, WSWorkOrderCategory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CountryContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "CountryContainer")
    public JAXBElement<CountryContainer> createCountryContainer(CountryContainer value) {
        return new JAXBElement<CountryContainer>(_CountryContainer_QNAME, CountryContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkOrderAreaCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WorkOrderAreaCriteria")
    public JAXBElement<WorkOrderAreaCriteria> createWorkOrderAreaCriteria(WorkOrderAreaCriteria value) {
        return new JAXBElement<WorkOrderAreaCriteria>(_WorkOrderAreaCriteria_QNAME, WorkOrderAreaCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkOrderItemCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WorkOrderItemCriteria")
    public JAXBElement<WorkOrderItemCriteria> createWorkOrderItemCriteria(WorkOrderItemCriteria value) {
        return new JAXBElement<WorkOrderItemCriteria>(_WorkOrderItemCriteria_QNAME, WorkOrderItemCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Subject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Subject")
    public JAXBElement<Subject> createSubject(Subject value) {
        return new JAXBElement<Subject>(_Subject_QNAME, Subject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardItemCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardItemCriteria")
    public JAXBElement<DebitCardItemCriteria> createDebitCardItemCriteria(DebitCardItemCriteria value) {
        return new JAXBElement<DebitCardItemCriteria>(_DebitCardItemCriteria_QNAME, DebitCardItemCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AvailabilityCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AvailabilityCriteria")
    public JAXBElement<AvailabilityCriteria> createAvailabilityCriteria(AvailabilityCriteria value) {
        return new JAXBElement<AvailabilityCriteria>(_AvailabilityCriteria_QNAME, AvailabilityCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BrochureRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "BrochureRequest")
    public JAXBElement<BrochureRequest> createBrochureRequest(BrochureRequest value) {
        return new JAXBElement<BrochureRequest>(_BrochureRequest_QNAME, BrochureRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSCustomerTitle }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSCustomerTitle")
    public JAXBElement<WSCustomerTitle> createWSCustomerTitle(WSCustomerTitle value) {
        return new JAXBElement<WSCustomerTitle>(_WSCustomerTitle_QNAME, WSCustomerTitle.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImageCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ImageCriteria")
    public JAXBElement<ImageCriteria> createImageCriteria(ImageCriteria value) {
        return new JAXBElement<ImageCriteria>(_ImageCriteria_QNAME, ImageCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropertyContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "PropertyContainer")
    public JAXBElement<PropertyContainer> createPropertyContainer(PropertyContainer value) {
        return new JAXBElement<PropertyContainer>(_PropertyContainer_QNAME, PropertyContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResortActivityContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResortActivityContainer")
    public JAXBElement<ResortActivityContainer> createResortActivityContainer(ResortActivityContainer value) {
        return new JAXBElement<ResortActivityContainer>(_ResortActivityContainer_QNAME, ResortActivityContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationTypeSearchContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationTypeSearchContainer")
    public JAXBElement<AccommodationTypeSearchContainer> createAccommodationTypeSearchContainer(AccommodationTypeSearchContainer value) {
        return new JAXBElement<AccommodationTypeSearchContainer>(_AccommodationTypeSearchContainer_QNAME, AccommodationTypeSearchContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Facility }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Facility")
    public JAXBElement<Facility> createFacility(Facility value) {
        return new JAXBElement<Facility>(_Facility_QNAME, Facility.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Address }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Address")
    public JAXBElement<Address> createAddress(Address value) {
        return new JAXBElement<Address>(_Address_QNAME, Address.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResortActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResortActivity")
    public JAXBElement<ResortActivity> createResortActivity(ResortActivity value) {
        return new JAXBElement<ResortActivity>(_ResortActivity_QNAME, ResortActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipTripCategoryContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "TipTripCategoryContainer")
    public JAXBElement<TipTripCategoryContainer> createTipTripCategoryContainer(TipTripCategoryContainer value) {
        return new JAXBElement<TipTripCategoryContainer>(_TipTripCategoryContainer_QNAME, TipTripCategoryContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationTypeCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationTypeCriteria")
    public JAXBElement<AccommodationTypeCriteria> createAccommodationTypeCriteria(AccommodationTypeCriteria value) {
        return new JAXBElement<AccommodationTypeCriteria>(_AccommodationTypeCriteria_QNAME, AccommodationTypeCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceAddition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResourceAddition")
    public JAXBElement<ResourceAddition> createResourceAddition(ResourceAddition value) {
        return new JAXBElement<ResourceAddition>(_ResourceAddition_QNAME, ResourceAddition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoucherItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "VoucherItem")
    public JAXBElement<VoucherItem> createVoucherItem(VoucherItem value) {
        return new JAXBElement<VoucherItem>(_VoucherItem_QNAME, VoucherItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSSource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSSource")
    public JAXBElement<WSSource> createWSSource(WSSource value) {
        return new JAXBElement<WSSource>(_WSSource_QNAME, WSSource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkOrderCategoryCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WorkOrderCategoryCriteria")
    public JAXBElement<WorkOrderCategoryCriteria> createWorkOrderCategoryCriteria(WorkOrderCategoryCriteria value) {
        return new JAXBElement<WorkOrderCategoryCriteria>(_WorkOrderCategoryCriteria_QNAME, WorkOrderCategoryCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PincodeItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "PincodeItem")
    public JAXBElement<PincodeItem> createPincodeItem(PincodeItem value) {
        return new JAXBElement<PincodeItem>(_PincodeItem_QNAME, PincodeItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropertyCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "PropertyCriteria")
    public JAXBElement<PropertyCriteria> createPropertyCriteria(PropertyCriteria value) {
        return new JAXBElement<PropertyCriteria>(_PropertyCriteria_QNAME, PropertyCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "SubjectContainer")
    public JAXBElement<SubjectContainer> createSubjectContainer(SubjectContainer value) {
        return new JAXBElement<SubjectContainer>(_SubjectContainer_QNAME, SubjectContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkOrderAreaContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WorkOrderAreaContainer")
    public JAXBElement<WorkOrderAreaContainer> createWorkOrderAreaContainer(WorkOrderAreaContainer value) {
        return new JAXBElement<WorkOrderAreaContainer>(_WorkOrderAreaContainer_QNAME, WorkOrderAreaContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerReservationInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "CustomerReservationInfo")
    public JAXBElement<CustomerReservationInfo> createCustomerReservationInfo(CustomerReservationInfo value) {
        return new JAXBElement<CustomerReservationInfo>(_CustomerReservationInfo_QNAME, CustomerReservationInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WorkOrder")
    public JAXBElement<WorkOrder> createWorkOrder(WorkOrder value) {
        return new JAXBElement<WorkOrder>(_WorkOrder_QNAME, WorkOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationTypeSearch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationTypeSearch")
    public JAXBElement<AccommodationTypeSearch> createAccommodationTypeSearch(AccommodationTypeSearch value) {
        return new JAXBElement<AccommodationTypeSearch>(_AccommodationTypeSearch_QNAME, AccommodationTypeSearch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResortContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResortContainer")
    public JAXBElement<ResortContainer> createResortContainer(ResortContainer value) {
        return new JAXBElement<ResortContainer>(_ResortContainer_QNAME, ResortContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoucherCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "VoucherCriteria")
    public JAXBElement<VoucherCriteria> createVoucherCriteria(VoucherCriteria value) {
        return new JAXBElement<VoucherCriteria>(_VoucherCriteria_QNAME, VoucherCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PincodeRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "PincodeRegistration")
    public JAXBElement<PincodeRegistration> createPincodeRegistration(PincodeRegistration value) {
        return new JAXBElement<PincodeRegistration>(_PincodeRegistration_QNAME, PincodeRegistration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Resort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Resort")
    public JAXBElement<Resort> createResort(Resort value) {
        return new JAXBElement<Resort>(_Resort_QNAME, Resort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectAvailabilityContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ObjectAvailabilityContainer")
    public JAXBElement<ObjectAvailabilityContainer> createObjectAvailabilityContainer(ObjectAvailabilityContainer value) {
        return new JAXBElement<ObjectAvailabilityContainer>(_ObjectAvailabilityContainer_QNAME, ObjectAvailabilityContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardCustomerInfoContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardCustomerInfoContainer")
    public JAXBElement<DebitCardCustomerInfoContainer> createDebitCardCustomerInfoContainer(DebitCardCustomerInfoContainer value) {
        return new JAXBElement<DebitCardCustomerInfoContainer>(_DebitCardCustomerInfoContainer_QNAME, DebitCardCustomerInfoContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSReservedResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSReservedResource")
    public JAXBElement<WSReservedResource> createWSReservedResource(WSReservedResource value) {
        return new JAXBElement<WSReservedResource>(_WSReservedResource_QNAME, WSReservedResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SourceCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "SourceCriteria")
    public JAXBElement<SourceCriteria> createSourceCriteria(SourceCriteria value) {
        return new JAXBElement<SourceCriteria>(_SourceCriteria_QNAME, SourceCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AddressCriteria")
    public JAXBElement<AddressCriteria> createAddressCriteria(AddressCriteria value) {
        return new JAXBElement<AddressCriteria>(_AddressCriteria_QNAME, AddressCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SessionCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "SessionCriteria")
    public JAXBElement<SessionCriteria> createSessionCriteria(SessionCriteria value) {
        return new JAXBElement<SessionCriteria>(_SessionCriteria_QNAME, SessionCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardConsumption }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardConsumption")
    public JAXBElement<DebitCardConsumption> createDebitCardConsumption(DebitCardConsumption value) {
        return new JAXBElement<DebitCardConsumption>(_DebitCardConsumption_QNAME, DebitCardConsumption.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSReservationSubject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSReservationSubject")
    public JAXBElement<WSReservationSubject> createWSReservationSubject(WSReservationSubject value) {
        return new JAXBElement<WSReservationSubject>(_WSReservationSubject_QNAME, WSReservationSubject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationSubjectContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ReservationSubjectContainer")
    public JAXBElement<ReservationSubjectContainer> createReservationSubjectContainer(ReservationSubjectContainer value) {
        return new JAXBElement<ReservationSubjectContainer>(_ReservationSubjectContainer_QNAME, ReservationSubjectContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardCriteria")
    public JAXBElement<DebitCardCriteria> createDebitCardCriteria(DebitCardCriteria value) {
        return new JAXBElement<DebitCardCriteria>(_DebitCardCriteria_QNAME, DebitCardCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Reservation")
    public JAXBElement<Reservation> createReservation(Reservation value) {
        return new JAXBElement<Reservation>(_Reservation_QNAME, Reservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "PaymentCriteria")
    public JAXBElement<PaymentCriteria> createPaymentCriteria(PaymentCriteria value) {
        return new JAXBElement<PaymentCriteria>(_PaymentCriteria_QNAME, PaymentCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationTypeContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationTypeContainer")
    public JAXBElement<AccommodationTypeContainer> createAccommodationTypeContainer(AccommodationTypeContainer value) {
        return new JAXBElement<AccommodationTypeContainer>(_AccommodationTypeContainer_QNAME, AccommodationTypeContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationSubjectCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ReservationSubjectCriteria")
    public JAXBElement<ReservationSubjectCriteria> createReservationSubjectCriteria(ReservationSubjectCriteria value) {
        return new JAXBElement<ReservationSubjectCriteria>(_ReservationSubjectCriteria_QNAME, ReservationSubjectCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSWorkOrderItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSWorkOrderItem")
    public JAXBElement<WSWorkOrderItem> createWSWorkOrderItem(WSWorkOrderItem value) {
        return new JAXBElement<WSWorkOrderItem>(_WSWorkOrderItem_QNAME, WSWorkOrderItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResortActivityCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResortActivityCriteria")
    public JAXBElement<ResortActivityCriteria> createResortActivityCriteria(ResortActivityCriteria value) {
        return new JAXBElement<ResortActivityCriteria>(_ResortActivityCriteria_QNAME, ResortActivityCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WSResourceCapacity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WSResourceCapacity")
    public JAXBElement<WSResourceCapacity> createWSResourceCapacity(WSResourceCapacity value) {
        return new JAXBElement<WSResourceCapacity>(_WSResourceCapacity_QNAME, WSResourceCapacity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImageContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ImageContainer")
    public JAXBElement<ImageContainer> createImageContainer(ImageContainer value) {
        return new JAXBElement<ImageContainer>(_ImageContainer_QNAME, ImageContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectAvailability }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ObjectAvailability")
    public JAXBElement<ObjectAvailability> createObjectAvailability(ObjectAvailability value) {
        return new JAXBElement<ObjectAvailability>(_ObjectAvailability_QNAME, ObjectAvailability.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KCPincodes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "KCPincodes")
    public JAXBElement<KCPincodes> createKCPincodes(KCPincodes value) {
        return new JAXBElement<KCPincodes>(_KCPincodes_QNAME, KCPincodes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ReservationContainer")
    public JAXBElement<ReservationContainer> createReservationContainer(ReservationContainer value) {
        return new JAXBElement<ReservationContainer>(_ReservationContainer_QNAME, ReservationContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceAdditionCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResourceAdditionCriteria")
    public JAXBElement<ResourceAdditionCriteria> createResourceAdditionCriteria(ResourceAdditionCriteria value) {
        return new JAXBElement<ResourceAdditionCriteria>(_ResourceAdditionCriteria_QNAME, ResourceAdditionCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DebitCardCustomerContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "DebitCardCustomerContainer")
    public JAXBElement<DebitCardCustomerContainer> createDebitCardCustomerContainer(DebitCardCustomerContainer value) {
        return new JAXBElement<DebitCardCustomerContainer>(_DebitCardCustomerContainer_QNAME, DebitCardCustomerContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TipTripCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "TipTripCriteria")
    public JAXBElement<TipTripCriteria> createTipTripCriteria(TipTripCriteria value) {
        return new JAXBElement<TipTripCriteria>(_TipTripCriteria_QNAME, TipTripCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerTitleCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "CustomerTitleCriteria")
    public JAXBElement<CustomerTitleCriteria> createCustomerTitleCriteria(CustomerTitleCriteria value) {
        return new JAXBElement<CustomerTitleCriteria>(_CustomerTitleCriteria_QNAME, CustomerTitleCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Availability }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Availability")
    public JAXBElement<Availability> createAvailability(Availability value) {
        return new JAXBElement<Availability>(_Availability_QNAME, Availability.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerTitleContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "CustomerTitleContainer")
    public JAXBElement<CustomerTitleContainer> createCustomerTitleContainer(CustomerTitleContainer value) {
        return new JAXBElement<CustomerTitleContainer>(_CustomerTitleContainer_QNAME, CustomerTitleContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationKind }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationKind")
    public JAXBElement<AccommodationKind> createAccommodationKind(AccommodationKind value) {
        return new JAXBElement<AccommodationKind>(_AccommodationKind_QNAME, AccommodationKind.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "SubjectCriteria")
    public JAXBElement<SubjectCriteria> createSubjectCriteria(SubjectCriteria value) {
        return new JAXBElement<SubjectCriteria>(_SubjectCriteria_QNAME, SubjectCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Customer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "Customer")
    public JAXBElement<Customer> createCustomer(Customer value) {
        return new JAXBElement<Customer>(_Customer_QNAME, Customer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservedResourceCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ReservedResourceCriteria")
    public JAXBElement<ReservedResourceCriteria> createReservedResourceCriteria(ReservedResourceCriteria value) {
        return new JAXBElement<ReservedResourceCriteria>(_ReservedResourceCriteria_QNAME, ReservedResourceCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceAdditionContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResourceAdditionContainer")
    public JAXBElement<ResourceAdditionContainer> createResourceAdditionContainer(ResourceAdditionContainer value) {
        return new JAXBElement<ResourceAdditionContainer>(_ResourceAdditionContainer_QNAME, ResourceAdditionContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WorkOrderItemContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "WorkOrderItemContainer")
    public JAXBElement<WorkOrderItemContainer> createWorkOrderItemContainer(WorkOrderItemContainer value) {
        return new JAXBElement<WorkOrderItemContainer>(_WorkOrderItemContainer_QNAME, WorkOrderItemContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceCapacityCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ResourceCapacityCriteria")
    public JAXBElement<ResourceCapacityCriteria> createResourceCapacityCriteria(ResourceCapacityCriteria value) {
        return new JAXBElement<ResourceCapacityCriteria>(_ResourceCapacityCriteria_QNAME, ResourceCapacityCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectAvailabilityCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ObjectAvailabilityCriteria")
    public JAXBElement<ObjectAvailabilityCriteria> createObjectAvailabilityCriteria(ObjectAvailabilityCriteria value) {
        return new JAXBElement<ObjectAvailabilityCriteria>(_ObjectAvailabilityCriteria_QNAME, ObjectAvailabilityCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "ObjectCriteria")
    public JAXBElement<ObjectCriteria> createObjectCriteria(ObjectCriteria value) {
        return new JAXBElement<ObjectCriteria>(_ObjectCriteria_QNAME, ObjectCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "GetReservation")
    public JAXBElement<GetReservation> createGetReservation(GetReservation value) {
        return new JAXBElement<GetReservation>(_GetReservation_QNAME, GetReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccommodationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.newyse.ws.services.newyse.maxxton/", name = "AccommodationType")
    public JAXBElement<AccommodationType> createAccommodationType(AccommodationType value) {
        return new JAXBElement<AccommodationType>(_AccommodationType_QNAME, AccommodationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IncludePartial", scope = PropertyCriteria.class, defaultValue = "false")
    public JAXBElement<Boolean> createPropertyCriteriaIncludePartial(Boolean value) {
        return new JAXBElement<Boolean>(_PropertyCriteriaIncludePartial_QNAME, Boolean.class, PropertyCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortCode", scope = BrochureCriteria.class)
    public JAXBElement<String> createBrochureCriteriaResortCode(String value) {
        return new JAXBElement<String>(_BrochureCriteriaResortCode_QNAME, String.class, BrochureCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ToDate", scope = ResortActivityCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createResortActivityCriteriaToDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ResortActivityCriteriaToDate_QNAME, XMLGregorianCalendar.class, ResortActivityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FromDate", scope = ResortActivityCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createResortActivityCriteriaFromDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ResortActivityCriteriaFromDate_QNAME, XMLGregorianCalendar.class, ResortActivityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortCode", scope = ResortActivityCriteria.class)
    public JAXBElement<String> createResortActivityCriteriaResortCode(String value) {
        return new JAXBElement<String>(_BrochureCriteriaResortCode_QNAME, String.class, ResortActivityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OnlyImageUrl", scope = ImageCriteria.class, defaultValue = "false")
    public JAXBElement<Boolean> createImageCriteriaOnlyImageUrl(Boolean value) {
        return new JAXBElement<Boolean>(_ImageCriteriaOnlyImageUrl_QNAME, Boolean.class, ImageCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EndDate", scope = AvailabilityCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createAvailabilityCriteriaEndDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvailabilityCriteriaEndDate_QNAME, XMLGregorianCalendar.class, AvailabilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StartDate", scope = AvailabilityCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createAvailabilityCriteriaStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvailabilityCriteriaStartDate_QNAME, XMLGregorianCalendar.class, AvailabilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResourceId", scope = AvailabilityCriteria.class)
    public JAXBElement<Long> createAvailabilityCriteriaResourceId(Long value) {
        return new JAXBElement<Long>(_AvailabilityCriteriaResourceId_QNAME, Long.class, AvailabilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NrOfNights", scope = AvailabilityCriteria.class)
    public JAXBElement<Integer> createAvailabilityCriteriaNrOfNights(Integer value) {
        return new JAXBElement<Integer>(_AvailabilityCriteriaNrOfNights_QNAME, Integer.class, AvailabilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SpecialCode", scope = AvailabilityCriteria.class)
    public JAXBElement<String> createAvailabilityCriteriaSpecialCode(String value) {
        return new JAXBElement<String>(_AvailabilityCriteriaSpecialCode_QNAME, String.class, AvailabilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortCode", scope = AvailabilityCriteria.class)
    public JAXBElement<String> createAvailabilityCriteriaResortCode(String value) {
        return new JAXBElement<String>(_BrochureCriteriaResortCode_QNAME, String.class, AvailabilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Reserve", scope = ResortActivity.class)
    public JAXBElement<String> createResortActivityReserve(String value) {
        return new JAXBElement<String>(_ResortActivityReserve_QNAME, String.class, ResortActivity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Text", scope = ResortActivity.class)
    public JAXBElement<String> createResortActivityText(String value) {
        return new JAXBElement<String>(_ResortActivityText_QNAME, String.class, ResortActivity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = ResortActivity.class)
    public JAXBElement<String> createResortActivityDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, ResortActivity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "HeadText", scope = ResortActivity.class)
    public JAXBElement<String> createResortActivityHeadText(String value) {
        return new JAXBElement<String>(_ResortActivityHeadText_QNAME, String.class, ResortActivity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResourceId", scope = ObjectCriteria.class)
    public JAXBElement<Long> createObjectCriteriaResourceId(Long value) {
        return new JAXBElement<Long>(_AvailabilityCriteriaResourceId_QNAME, Long.class, ObjectCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ObjectId", scope = ObjectCriteria.class)
    public JAXBElement<Long> createObjectCriteriaObjectId(Long value) {
        return new JAXBElement<Long>(_ObjectCriteriaObjectId_QNAME, Long.class, ObjectCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ShortDescription", scope = WorkOrder.class)
    public JAXBElement<String> createWorkOrderShortDescription(String value) {
        return new JAXBElement<String>(_WorkOrderShortDescription_QNAME, String.class, WorkOrder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = WorkOrder.class)
    public JAXBElement<String> createWorkOrderDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, WorkOrder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortId", scope = WorkOrder.class)
    public JAXBElement<Long> createWorkOrderResortId(Long value) {
        return new JAXBElement<Long>(_WorkOrderResortId_QNAME, Long.class, WorkOrder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ObjectId", scope = WorkOrder.class)
    public JAXBElement<Long> createWorkOrderObjectId(Long value) {
        return new JAXBElement<Long>(_ObjectCriteriaObjectId_QNAME, Long.class, WorkOrder.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DistributionChannelId", scope = ResortCriteria.class)
    public JAXBElement<Long> createResortCriteriaDistributionChannelId(Long value) {
        return new JAXBElement<Long>(_ResortCriteriaDistributionChannelId_QNAME, Long.class, ResortCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DistributionChannelCode", scope = ResortCriteria.class)
    public JAXBElement<String> createResortCriteriaDistributionChannelCode(String value) {
        return new JAXBElement<String>(_ResortCriteriaDistributionChannelCode_QNAME, String.class, ResortCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = WSWorkOrderItem.class)
    public JAXBElement<String> createWSWorkOrderItemName(String value) {
        return new JAXBElement<String>(_WSWorkOrderItemName_QNAME, String.class, WSWorkOrderItem.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Text", scope = TipTrip.class)
    public JAXBElement<String> createTipTripText(String value) {
        return new JAXBElement<String>(_ResortActivityText_QNAME, String.class, TipTrip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = TipTrip.class)
    public JAXBElement<String> createTipTripDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, TipTrip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EndDate", scope = TipTrip.class)
    public JAXBElement<XMLGregorianCalendar> createTipTripEndDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvailabilityCriteriaEndDate_QNAME, XMLGregorianCalendar.class, TipTrip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Url", scope = TipTrip.class)
    public JAXBElement<String> createTipTripUrl(String value) {
        return new JAXBElement<String>(_TipTripUrl_QNAME, String.class, TipTrip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StartDate", scope = TipTrip.class)
    public JAXBElement<XMLGregorianCalendar> createTipTripStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvailabilityCriteriaStartDate_QNAME, XMLGregorianCalendar.class, TipTrip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Price", scope = TipTrip.class)
    public JAXBElement<String> createTipTripPrice(String value) {
        return new JAXBElement<String>(_TipTripPrice_QNAME, String.class, TipTrip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Location", scope = TipTrip.class)
    public JAXBElement<String> createTipTripLocation(String value) {
        return new JAXBElement<String>(_TipTripLocation_QNAME, String.class, TipTrip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "HeadText", scope = TipTrip.class)
    public JAXBElement<String> createTipTripHeadText(String value) {
        return new JAXBElement<String>(_ResortActivityHeadText_QNAME, String.class, TipTrip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "WorkOrderCategoryId", scope = WorkOrderItemCriteria.class)
    public JAXBElement<Long> createWorkOrderItemCriteriaWorkOrderCategoryId(Long value) {
        return new JAXBElement<Long>(_WorkOrderItemCriteriaWorkOrderCategoryId_QNAME, Long.class, WorkOrderItemCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortId", scope = WorkOrderItemCriteria.class)
    public JAXBElement<Long> createWorkOrderItemCriteriaResortId(Long value) {
        return new JAXBElement<Long>(_WorkOrderResortId_QNAME, Long.class, WorkOrderItemCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "WorkOrderItemId", scope = WorkOrderItemCriteria.class)
    public JAXBElement<Long> createWorkOrderItemCriteriaWorkOrderItemId(Long value) {
        return new JAXBElement<Long>(_WorkOrderItemCriteriaWorkOrderItemId_QNAME, Long.class, WorkOrderItemCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SendMethodCode", scope = ReservationCriteria.class)
    public JAXBElement<String> createReservationCriteriaSendMethodCode(String value) {
        return new JAXBElement<String>(_ReservationCriteriaSendMethodCode_QNAME, String.class, ReservationCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SourceCode", scope = ReservationCriteria.class)
    public JAXBElement<String> createReservationCriteriaSourceCode(String value) {
        return new JAXBElement<String>(_ReservationCriteriaSourceCode_QNAME, String.class, ReservationCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = Brochure.class)
    public JAXBElement<String> createBrochureDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, Brochure.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ReservationId", scope = DebitCardItemCriteria.class)
    public JAXBElement<Long> createDebitCardItemCriteriaReservationId(Long value) {
        return new JAXBElement<Long>(_DebitCardItemCriteriaReservationId_QNAME, Long.class, DebitCardItemCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ObjectId", scope = AccommodationTypeSearchCriteria.class)
    public JAXBElement<Long> createAccommodationTypeSearchCriteriaObjectId(Long value) {
        return new JAXBElement<Long>(_ObjectCriteriaObjectId_QNAME, Long.class, AccommodationTypeSearchCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortCode", scope = AccommodationTypeSearchCriteria.class)
    public JAXBElement<String> createAccommodationTypeSearchCriteriaResortCode(String value) {
        return new JAXBElement<String>(_BrochureCriteriaResortCode_QNAME, String.class, AccommodationTypeSearchCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = TipTripCategory.class)
    public JAXBElement<String> createTipTripCategoryName(String value) {
        return new JAXBElement<String>(_WSWorkOrderItemName_QNAME, String.class, TipTripCategory.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "WorkPhone", scope = Customer.class)
    public JAXBElement<String> createCustomerWorkPhone(String value) {
        return new JAXBElement<String>(_CustomerWorkPhone_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Zipcode", scope = Customer.class)
    public JAXBElement<String> createCustomerZipcode(String value) {
        return new JAXBElement<String>(_CustomerZipcode_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PoBox", scope = Customer.class)
    public JAXBElement<String> createCustomerPoBox(String value) {
        return new JAXBElement<String>(_CustomerPoBox_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CustomerId", scope = Customer.class)
    public JAXBElement<Long> createCustomerCustomerId(Long value) {
        return new JAXBElement<Long>(_CustomerCustomerId_QNAME, Long.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IbanNumber", scope = Customer.class)
    public JAXBElement<String> createCustomerIbanNumber(String value) {
        return new JAXBElement<String>(_CustomerIbanNumber_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EmailAllowed", scope = Customer.class)
    public JAXBElement<Boolean> createCustomerEmailAllowed(Boolean value) {
        return new JAXBElement<Boolean>(_CustomerEmailAllowed_QNAME, Boolean.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Latitude", scope = Customer.class)
    public JAXBElement<Double> createCustomerLatitude(Double value) {
        return new JAXBElement<Double>(_CustomerLatitude_QNAME, Double.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Lastname", scope = Customer.class)
    public JAXBElement<String> createCustomerLastname(String value) {
        return new JAXBElement<String>(_CustomerLastname_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Firstname", scope = Customer.class)
    public JAXBElement<String> createCustomerFirstname(String value) {
        return new JAXBElement<String>(_CustomerFirstname_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PoBoxZipcode", scope = Customer.class)
    public JAXBElement<String> createCustomerPoBoxZipcode(String value) {
        return new JAXBElement<String>(_CustomerPoBoxZipcode_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MailAllowed", scope = Customer.class)
    public JAXBElement<Boolean> createCustomerMailAllowed(Boolean value) {
        return new JAXBElement<Boolean>(_CustomerMailAllowed_QNAME, Boolean.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "VatNumber", scope = Customer.class)
    public JAXBElement<String> createCustomerVatNumber(String value) {
        return new JAXBElement<String>(_CustomerVatNumber_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MobilePhone", scope = Customer.class)
    public JAXBElement<String> createCustomerMobilePhone(String value) {
        return new JAXBElement<String>(_CustomerMobilePhone_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Department", scope = Customer.class)
    public JAXBElement<String> createCustomerDepartment(String value) {
        return new JAXBElement<String>(_CustomerDepartment_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TitleCode", scope = Customer.class)
    public JAXBElement<String> createCustomerTitleCode(String value) {
        return new JAXBElement<String>(_CustomerTitleCode_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AttentionOf", scope = Customer.class)
    public JAXBElement<String> createCustomerAttentionOf(String value) {
        return new JAXBElement<String>(_CustomerAttentionOf_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Sex", scope = Customer.class)
    public JAXBElement<String> createCustomerSex(String value) {
        return new JAXBElement<String>(_CustomerSex_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BankAccountTypeId", scope = Customer.class)
    public JAXBElement<Long> createCustomerBankAccountTypeId(Long value) {
        return new JAXBElement<Long>(_CustomerBankAccountTypeId_QNAME, Long.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Middle", scope = Customer.class)
    public JAXBElement<String> createCustomerMiddle(String value) {
        return new JAXBElement<String>(_CustomerMiddle_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Fax", scope = Customer.class)
    public JAXBElement<String> createCustomerFax(String value) {
        return new JAXBElement<String>(_CustomerFax_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CompanyName", scope = Customer.class)
    public JAXBElement<String> createCustomerCompanyName(String value) {
        return new JAXBElement<String>(_CustomerCompanyName_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Country", scope = Customer.class)
    public JAXBElement<String> createCustomerCountry(String value) {
        return new JAXBElement<String>(_CustomerCountry_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BirthDate", scope = Customer.class)
    public JAXBElement<XMLGregorianCalendar> createCustomerBirthDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CustomerBirthDate_QNAME, XMLGregorianCalendar.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "City", scope = Customer.class)
    public JAXBElement<String> createCustomerCity(String value) {
        return new JAXBElement<String>(_CustomerCity_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PrivatePhone", scope = Customer.class)
    public JAXBElement<String> createCustomerPrivatePhone(String value) {
        return new JAXBElement<String>(_CustomerPrivatePhone_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BankAccountNumber", scope = Customer.class)
    public JAXBElement<String> createCustomerBankAccountNumber(String value) {
        return new JAXBElement<String>(_CustomerBankAccountNumber_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Email", scope = Customer.class)
    public JAXBElement<String> createCustomerEmail(String value) {
        return new JAXBElement<String>(_CustomerEmail_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "District", scope = Customer.class)
    public JAXBElement<String> createCustomerDistrict(String value) {
        return new JAXBElement<String>(_CustomerDistrict_QNAME, String.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Longitude", scope = Customer.class)
    public JAXBElement<Double> createCustomerLongitude(Double value) {
        return new JAXBElement<Double>(_CustomerLongitude_QNAME, Double.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IsCompany", scope = Customer.class)
    public JAXBElement<Boolean> createCustomerIsCompany(Boolean value) {
        return new JAXBElement<Boolean>(_CustomerIsCompany_QNAME, Boolean.class, Customer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ArrivalDate", scope = UpdateReservationCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createUpdateReservationCriteriaArrivalDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UpdateReservationCriteriaArrivalDate_QNAME, XMLGregorianCalendar.class, UpdateReservationCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CustomerId", scope = UpdateReservationCriteria.class)
    public JAXBElement<Long> createUpdateReservationCriteriaCustomerId(Long value) {
        return new JAXBElement<Long>(_CustomerCustomerId_QNAME, Long.class, UpdateReservationCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepartureDate", scope = UpdateReservationCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createUpdateReservationCriteriaDepartureDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UpdateReservationCriteriaDepartureDate_QNAME, XMLGregorianCalendar.class, UpdateReservationCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Voucher", scope = UpdateReservationCriteria.class)
    public JAXBElement<String> createUpdateReservationCriteriaVoucher(String value) {
        return new JAXBElement<String>(_UpdateReservationCriteriaVoucher_QNAME, String.class, UpdateReservationCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "WorkPhone", scope = Address.class)
    public JAXBElement<String> createAddressWorkPhone(String value) {
        return new JAXBElement<String>(_CustomerWorkPhone_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Fax", scope = Address.class)
    public JAXBElement<String> createAddressFax(String value) {
        return new JAXBElement<String>(_CustomerFax_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Country }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Country", scope = Address.class)
    public JAXBElement<Country> createAddressCountry(Country value) {
        return new JAXBElement<Country>(_CustomerCountry_QNAME, Country.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Zipcode", scope = Address.class)
    public JAXBElement<String> createAddressZipcode(String value) {
        return new JAXBElement<String>(_CustomerZipcode_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "City", scope = Address.class)
    public JAXBElement<String> createAddressCity(String value) {
        return new JAXBElement<String>(_CustomerCity_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PrivatePhone", scope = Address.class)
    public JAXBElement<String> createAddressPrivatePhone(String value) {
        return new JAXBElement<String>(_CustomerPrivatePhone_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PoBox", scope = Address.class)
    public JAXBElement<String> createAddressPoBox(String value) {
        return new JAXBElement<String>(_CustomerPoBox_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Email", scope = Address.class)
    public JAXBElement<String> createAddressEmail(String value) {
        return new JAXBElement<String>(_CustomerEmail_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "District", scope = Address.class)
    public JAXBElement<String> createAddressDistrict(String value) {
        return new JAXBElement<String>(_CustomerDistrict_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Latitude", scope = Address.class)
    public JAXBElement<Double> createAddressLatitude(Double value) {
        return new JAXBElement<Double>(_CustomerLatitude_QNAME, Double.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Longitude", scope = Address.class)
    public JAXBElement<Double> createAddressLongitude(Double value) {
        return new JAXBElement<Double>(_CustomerLongitude_QNAME, Double.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PoBoxZipcode", scope = Address.class)
    public JAXBElement<String> createAddressPoBoxZipcode(String value) {
        return new JAXBElement<String>(_CustomerPoBoxZipcode_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MobilePhone", scope = Address.class)
    public JAXBElement<String> createAddressMobilePhone(String value) {
        return new JAXBElement<String>(_CustomerMobilePhone_QNAME, String.class, Address.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ObjectId", scope = ResourceAdditionCriteria.class)
    public JAXBElement<Long> createResourceAdditionCriteriaObjectId(Long value) {
        return new JAXBElement<Long>(_ObjectCriteriaObjectId_QNAME, Long.class, ResourceAdditionCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "TipTripCategoryId", scope = TipTripCriteria.class)
    public JAXBElement<Long> createTipTripCriteriaTipTripCategoryId(Long value) {
        return new JAXBElement<Long>(_TipTripCriteriaTipTripCategoryId_QNAME, Long.class, TipTripCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortCode", scope = TipTripCriteria.class)
    public JAXBElement<String> createTipTripCriteriaResortCode(String value) {
        return new JAXBElement<String>(_BrochureCriteriaResortCode_QNAME, String.class, TipTripCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ToDate", scope = FacilityCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createFacilityCriteriaToDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ResortActivityCriteriaToDate_QNAME, XMLGregorianCalendar.class, FacilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FromDate", scope = FacilityCriteria.class)
    public JAXBElement<XMLGregorianCalendar> createFacilityCriteriaFromDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ResortActivityCriteriaFromDate_QNAME, XMLGregorianCalendar.class, FacilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortCode", scope = FacilityCriteria.class)
    public JAXBElement<String> createFacilityCriteriaResortCode(String value) {
        return new JAXBElement<String>(_BrochureCriteriaResortCode_QNAME, String.class, FacilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SubjectId", scope = SubjectQuantity.class)
    public JAXBElement<Long> createSubjectQuantitySubjectId(Long value) {
        return new JAXBElement<Long>(_SubjectQuantitySubjectId_QNAME, Long.class, SubjectQuantity.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResourceId", scope = ObjectAvailabilityCriteria.class)
    public JAXBElement<Long> createObjectAvailabilityCriteriaResourceId(Long value) {
        return new JAXBElement<Long>(_AvailabilityCriteriaResourceId_QNAME, Long.class, ObjectAvailabilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ObjectId", scope = ObjectAvailabilityCriteria.class)
    public JAXBElement<Long> createObjectAvailabilityCriteriaObjectId(Long value) {
        return new JAXBElement<Long>(_ObjectCriteriaObjectId_QNAME, Long.class, ObjectAvailabilityCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = WSWorkOrderCategory.class)
    public JAXBElement<String> createWSWorkOrderCategoryName(String value) {
        return new JAXBElement<String>(_WSWorkOrderItemName_QNAME, String.class, WSWorkOrderCategory.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortId", scope = WorkOrderAreaCriteria.class)
    public JAXBElement<Long> createWorkOrderAreaCriteriaResortId(Long value) {
        return new JAXBElement<Long>(_WorkOrderResortId_QNAME, Long.class, WorkOrderAreaCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "areaId", scope = WorkOrderAreaCriteria.class)
    public JAXBElement<Long> createWorkOrderAreaCriteriaAreaId(Long value) {
        return new JAXBElement<Long>(_WorkOrderAreaCriteriaAreaId_QNAME, Long.class, WorkOrderAreaCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "WorkOrderItemId", scope = WorkOrderAreaCriteria.class)
    public JAXBElement<Long> createWorkOrderAreaCriteriaWorkOrderItemId(Long value) {
        return new JAXBElement<Long>(_WorkOrderItemCriteriaWorkOrderItemId_QNAME, Long.class, WorkOrderAreaCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ObjectId", scope = WorkOrderAreaCriteria.class)
    public JAXBElement<Long> createWorkOrderAreaCriteriaObjectId(Long value) {
        return new JAXBElement<Long>(_ObjectCriteriaObjectId_QNAME, Long.class, WorkOrderAreaCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SpecialCode", scope = Accommodation.class)
    public JAXBElement<String> createAccommodationSpecialCode(String value) {
        return new JAXBElement<String>(_AvailabilityCriteriaSpecialCode_QNAME, String.class, Accommodation.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ShortDescription", scope = Resort.class)
    public JAXBElement<String> createResortShortDescription(String value) {
        return new JAXBElement<String>(_WorkOrderShortDescription_QNAME, String.class, Resort.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = Resort.class)
    public JAXBElement<String> createResortDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, Resort.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortParentId", scope = Resort.class)
    public JAXBElement<Long> createResortResortParentId(Long value) {
        return new JAXBElement<Long>(_ResortResortParentId_QNAME, Long.class, Resort.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ShortDescription", scope = AccommodationType.class)
    public JAXBElement<String> createAccommodationTypeShortDescription(String value) {
        return new JAXBElement<String>(_WorkOrderShortDescription_QNAME, String.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = AccommodationType.class)
    public JAXBElement<String> createAccommodationTypeName(String value) {
        return new JAXBElement<String>(_WSWorkOrderItemName_QNAME, String.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "KindCode", scope = AccommodationType.class)
    public JAXBElement<String> createAccommodationTypeKindCode(String value) {
        return new JAXBElement<String>(_AccommodationTypeKindCode_QNAME, String.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = AccommodationType.class)
    public JAXBElement<String> createAccommodationTypeDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ImageManagerId", scope = AccommodationType.class)
    public JAXBElement<Long> createAccommodationTypeImageManagerId(Long value) {
        return new JAXBElement<Long>(_AccommodationTypeImageManagerId_QNAME, Long.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResourceId", scope = AccommodationType.class)
    public JAXBElement<Long> createAccommodationTypeResourceId(Long value) {
        return new JAXBElement<Long>(_AvailabilityCriteriaResourceId_QNAME, Long.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PropertyManagerId", scope = AccommodationType.class)
    public JAXBElement<Long> createAccommodationTypePropertyManagerId(Long value) {
        return new JAXBElement<Long>(_AccommodationTypePropertyManagerId_QNAME, Long.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "KindId", scope = AccommodationType.class)
    public JAXBElement<Long> createAccommodationTypeKindId(Long value) {
        return new JAXBElement<Long>(_AccommodationTypeKindId_QNAME, Long.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Code", scope = AccommodationType.class)
    public JAXBElement<String> createAccommodationTypeCode(String value) {
        return new JAXBElement<String>(_AccommodationTypeCode_QNAME, String.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortCode", scope = AccommodationType.class)
    public JAXBElement<String> createAccommodationTypeResortCode(String value) {
        return new JAXBElement<String>(_BrochureCriteriaResortCode_QNAME, String.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description2", scope = AccommodationType.class)
    public JAXBElement<String> createAccommodationTypeDescription2(String value) {
        return new JAXBElement<String>(_AccommodationTypeDescription2_QNAME, String.class, AccommodationType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "WorkOrderCategoryId", scope = WorkOrderCategoryCriteria.class)
    public JAXBElement<Long> createWorkOrderCategoryCriteriaWorkOrderCategoryId(Long value) {
        return new JAXBElement<Long>(_WorkOrderItemCriteriaWorkOrderCategoryId_QNAME, Long.class, WorkOrderCategoryCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortId", scope = WorkOrderCategoryCriteria.class)
    public JAXBElement<Long> createWorkOrderCategoryCriteriaResortId(Long value) {
        return new JAXBElement<Long>(_WorkOrderResortId_QNAME, Long.class, WorkOrderCategoryCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = WSWorkOrderArea.class)
    public JAXBElement<String> createWSWorkOrderAreaName(String value) {
        return new JAXBElement<String>(_WSWorkOrderItemName_QNAME, String.class, WSWorkOrderArea.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResourceId", scope = AccommodationTypeCriteria.class)
    public JAXBElement<Long> createAccommodationTypeCriteriaResourceId(Long value) {
        return new JAXBElement<Long>(_AvailabilityCriteriaResourceId_QNAME, Long.class, AccommodationTypeCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OnlyBookable", scope = AccommodationTypeCriteria.class, defaultValue = "true")
    public JAXBElement<Boolean> createAccommodationTypeCriteriaOnlyBookable(Boolean value) {
        return new JAXBElement<Boolean>(_AccommodationTypeCriteriaOnlyBookable_QNAME, Boolean.class, AccommodationTypeCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResortCode", scope = AccommodationTypeCriteria.class)
    public JAXBElement<String> createAccommodationTypeCriteriaResortCode(String value) {
        return new JAXBElement<String>(_BrochureCriteriaResortCode_QNAME, String.class, AccommodationTypeCriteria.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = WSSource.class)
    public JAXBElement<String> createWSSourceDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, WSSource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EndDate", scope = WSSource.class)
    public JAXBElement<XMLGregorianCalendar> createWSSourceEndDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvailabilityCriteriaEndDate_QNAME, XMLGregorianCalendar.class, WSSource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StartDate", scope = WSSource.class)
    public JAXBElement<XMLGregorianCalendar> createWSSourceStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvailabilityCriteriaStartDate_QNAME, XMLGregorianCalendar.class, WSSource.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = Property.class)
    public JAXBElement<String> createPropertyName(String value) {
        return new JAXBElement<String>(_WSWorkOrderItemName_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Value", scope = Property.class)
    public JAXBElement<String> createPropertyValue(String value) {
        return new JAXBElement<String>(_PropertyValue_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Partial", scope = Property.class)
    public JAXBElement<Boolean> createPropertyPartial(Boolean value) {
        return new JAXBElement<Boolean>(_PropertyPartial_QNAME, Boolean.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = Property.class)
    public JAXBElement<String> createPropertyDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EndDate", scope = Property.class)
    public JAXBElement<XMLGregorianCalendar> createPropertyEndDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvailabilityCriteriaEndDate_QNAME, XMLGregorianCalendar.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StartDate", scope = Property.class)
    public JAXBElement<XMLGregorianCalendar> createPropertyStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_AvailabilityCriteriaStartDate_QNAME, XMLGregorianCalendar.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GroupName", scope = Property.class)
    public JAXBElement<String> createPropertyGroupName(String value) {
        return new JAXBElement<String>(_PropertyGroupName_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GroupCode", scope = Property.class)
    public JAXBElement<String> createPropertyGroupCode(String value) {
        return new JAXBElement<String>(_PropertyGroupCode_QNAME, String.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "HasPreferenceCosts", scope = Property.class)
    public JAXBElement<Boolean> createPropertyHasPreferenceCosts(Boolean value) {
        return new JAXBElement<Boolean>(_PropertyHasPreferenceCosts_QNAME, Boolean.class, Property.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ShortDescription", scope = Object.class)
    public JAXBElement<String> createObjectShortDescription(String value) {
        return new JAXBElement<String>(_WorkOrderShortDescription_QNAME, String.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = Object.class)
    public JAXBElement<String> createObjectName(String value) {
        return new JAXBElement<String>(_WSWorkOrderItemName_QNAME, String.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = Object.class)
    public JAXBElement<String> createObjectDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ImageManagerId", scope = Object.class)
    public JAXBElement<Long> createObjectImageManagerId(Long value) {
        return new JAXBElement<Long>(_AccommodationTypeImageManagerId_QNAME, Long.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ResourceId", scope = Object.class)
    public JAXBElement<Long> createObjectResourceId(Long value) {
        return new JAXBElement<Long>(_AvailabilityCriteriaResourceId_QNAME, Long.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PropertyManagerId", scope = Object.class)
    public JAXBElement<Long> createObjectPropertyManagerId(Long value) {
        return new JAXBElement<Long>(_AccommodationTypePropertyManagerId_QNAME, Long.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Code", scope = Object.class)
    public JAXBElement<String> createObjectCode(String value) {
        return new JAXBElement<String>(_AccommodationTypeCode_QNAME, String.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ObjectId", scope = Object.class)
    public JAXBElement<Long> createObjectObjectId(Long value) {
        return new JAXBElement<Long>(_ObjectCriteriaObjectId_QNAME, Long.class, Object.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MaxReservable", scope = ResourceAddition.class)
    public JAXBElement<Integer> createResourceAdditionMaxReservable(Integer value) {
        return new JAXBElement<Integer>(_ResourceAdditionMaxReservable_QNAME, Integer.class, ResourceAddition.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ShortDescription", scope = ResourceAddition.class)
    public JAXBElement<String> createResourceAdditionShortDescription(String value) {
        return new JAXBElement<String>(_WorkOrderShortDescription_QNAME, String.class, ResourceAddition.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Name", scope = ResourceAddition.class)
    public JAXBElement<String> createResourceAdditionName(String value) {
        return new JAXBElement<String>(_WSWorkOrderItemName_QNAME, String.class, ResourceAddition.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Description", scope = ResourceAddition.class)
    public JAXBElement<String> createResourceAdditionDescription(String value) {
        return new JAXBElement<String>(_ResortActivityDescription_QNAME, String.class, ResourceAddition.class, value);
    }

}
