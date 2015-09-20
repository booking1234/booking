package net.cbtltd.shared;

public enum Error {
	account_id("Invalid or absent account ID"),
	account_data("There is no data for the account(s)"),
	account_list("Invalid list of account names and IDs"),
	address_invalid("Party address has an invalid format"),
	address_value("Party address does not contain value"),
	agent_to_pm("Property is disabled for channel partner."),
	anet_transaction_error("Error during Authorize.net transaction"),
	asset_list("Invalid list of asset names and IDs"),
	bank_transfer("Partner allows only  bank transfer"),
	bookable_online("Product is bookable online only"),
	cancel_unavailable("Reservation cancellation unavailable"),
	card_address("Invalid address for credit card"),
	card_approval_required("Call for card approval code"),
	card_code("Credit card CCV code is invalid"),
	card_expired("Credit card has expired"),
	card_gateway("Payment gateway error or failure"),
	card_holder("Credit card holder is invalid"),
	card_insufficient_funds("Credit card has insufficient funds"),
	card_month("Credit card expiry month is invalid"),
	card_number("Credit card number is invalid"),
	card_postal_code("Invalid postal or ZIP code for credit card"),
	card_reference("Invalid payment reference"),
	card_year("Credit card expiry year is invalid"),
	card_timeout("Bank interface timeout because of communications failure"),
	card_type("Unsupported credit card type"),
	card_amount("Invalid or absent credit card payment amount"),
	card_notsupported("Credit Card is not suppored for this method"),
	credit_card_error("Your Credit card could not be processed, would you like to try another one"),
	channel_partner_data("There is no data for the channel partners"),
	configuration_not_exists("There is no configuration with passed parameter"),
	country_code("Invalid two character ISO 3166-1 country code"),
	country_json("JSON request to country service failed"),
	country_list("Invalid list of country names and ISO 3166-1 codes"),
	country_region("Invalid ISO 3166-1 country code or ISO 3166-2 region code"),
	create_payment_profile_failed("Payment profile creation was failed"),
	criteria_invalid("Criteria are absent, null, blank or badly formatted = must be key=value pairs"),
	currency_exchange_rate("There is no exchange rate for the specified currency pair and date"),
	currency_code("Invalid or absent three character ISO 4217 currency code"),
	currency_json("JSON request to currency service failed"),
	currency_list("Invalid list of currency names and ISO 4217 codes"),
	data_unchanged("There have been no changes since the last download"),
	date_from("Invalid or absent arrival date"),
	date_format("Invalid date - must be yyyy-mm-dd format"),
	date_invalid("Invalid or absent date"),
	date_range("Invalid or negative date range"),
	date_to("Invalid or absent departure date"),
	database_cannot_find("Cannot find in database"),
	database_cannot_find_property_manager("Cannot find property manager info in database by supplier id"),
	database_cannot_find_manager_to_gateway("Cannot find manager to gateway in database by supplier id"),
	entity_id("Invalid or absent subsidiary account ID"),
	event_id("Invalid or absent event reference"),
	event_data("There is no data for the event(s)"),
	family_name("Invalid or absent family name"),
	fee_calculation("Something wrong with fees calculations"),
	finance_id("Invalid or absent bank or cash account ID"),
	finance_list("Invalid list of bank and cash account names and IDs"),
	gateway_handler("Cannot process gateway handler with ID"),
	image_data("There are no images for the specified parameters"),
	image_upload("There was an error with the upload of image"),
	inquiry_only("This property could be inquired only"),
	inquiry_state("Unknown inquiry state"),
	language_code("Invalid or absent two character ISO 639-1 language code"),
	language_json("JSON request to language service failed"),
	language_translate("Cannot translate to the specified language"),
	license_absent("This operation is not licensed for the client and/or product"),
	license_bad("There is no license for this supplier - see http://www.razor-cloud.com/docs/RazorLicense.pdf"),
	license_type("Invalid license request type "),
	license_wait("This operation is throttled - please space requests by at least"),
	location_id("Invalid or absent location ID"),
	locationid_nearby("Invalid or absent location ID, distance or unit"),
	location_name("Invalid or absent location name, region or country"),
	model_id("Invalid or absent model ID"),
	model_type("Invalid or absent model type"),
	monitor_data("There was an error monitoring data "),
	missing_adult_parameter("adult parameter must be greater than 0"),
	minimum_stay("Reservation must be for :"), 
	nameid_data("There are no names for the list "),
	nameid_json("JSON request to name ID service failed"),
	not_implemented("Not yet implemented"),
	number_format("Invalid number format or is not a number"),
	number_invalid("Invalid number or number is out of range"),
	organization_id("Invalid or absent organization ID"),
	organization_info("Invalid or absent property manager info"),
	parameter_absent("One of the necessary parameters is missing"),
	parameter_invalid("Input parameter is invalid"),
	parameter_null("Parameter is null"),
	party_altid("Invalid or absent partner ID"),
	party_attribute("The party has no attributes"),
	party_criteria("There are no parties that fit the specified criteria"),
	party_country("There are no countries set for the specified party"),
	party_data("There is no data for the party"),
	party_emailaddress("The email address is not valid"),
	party_id("Invalid or absent party ID"),
	party_inactive("The party is not active or has been suspended"),
	party_json("JSON request to party service failed"),
	party_list("Invalid list of party names and IDs"),
	party_name("The party name is not valid"),
	party_type("Invalid party type(s)"),
	party_value("Absent or invalid party values"),
	payment_gateway("Unexpected payment gateway ID"),
	payment_gateway_info("Unexpected payment gateway info"),
	payment_method_unsupported("Payment method is unsupported"),
	payment_profile_unsupported("Payment profile is unsupported"),
	payment_type_unsupported("Payment type is invalid"),
	pms_reservation_reject("We are unable to create a booking for the given dates"),
	pos_absent("There is no point of sale code"),
	pos_invalid("The point of sale code is invalid"),
	pos_party("Point of sale code is not valid for this party"),
	pos_product("Point of sale code is not valid for this product"),
	position_latitude("Invalid latitude - must be -90&#176; &lt;= latitude &lt;= 90&#176;"),
	position_longitude("Invalid longitude - must be -180&#176; &lt;= longitude &lt;= 180&#176;"),
	position_nearby("Invalid latitude, longitude, distance or unit"),
	price_data("Price does not exist for the product, dates and/or currency"),
	price_not_match("Price was changed on the PMS side"),
	price_missing("Live price was not returned"),
	price_id("Invalid or absent price ID"),
	price_json("JSON request to price service failed"),
	price_policy("Price or deposit policy has not been set for the prouct"),
	product_altid("Invalid or absent foreign product ID"),
	product_attribute("The property has no attributes"),
	product_criteria("There are no properties that fit the specified criteria"),
	product_data("There is no data for the property"),
	product_exists("A product or property with this name already exists"),
	product_id("Invalid or absent product ID"),
	product_inactive("The property is not active or has been suspended"),
	product_json("JSON request to product service failed"),
	product_list("Invalid list of property or product names and IDs"),
	product_location("There are no properties at this location"),
	product_not_available("Sorry but the property is no longer available for this time period. Please look for another property or enter different time period"),
	product_not_online("The product does not have online availability"),
	product_position("The property does not have a geographic position (latitude/longitude)"),
	product_rating("The property has no ratings or reviews"),
	product_state("Invalid or absent product state"),
	product_type("Invalid or absent product type"),
	product_upload("Invalid or incomplete product upload"),
	product_value("Invalid or absent product values"),
	products_selected("You need to choose at least one product."),
	rate_data("The reservation has no ratings or reviews"),
	rate_type("Invalid or absent rating type"),
	refund_unsupported("Refund is unsupported"),
	registration_status_data("Invalid input parameters"),
	reservation_agentid("Invalid or absent agent ID"),
	reservation_altid("Invalid or absent foreign reservation ID"),
	reservation_api("API error "),
	reservation_cancel("The reservation has been confirmed so may not be cancelled in this way - send a cancellation request to the property manager"),
	reservation_cancelled("Reservation is cancelled already"),
	reservation_confirmation("Reservation was not confirmed"),
	reservation_customerid("Invalid or absent guest ID"),
	reservation_bad("The reservation is invalid or does not belong to the property manager - if created by a PMS check it has the manager pos code prepended to the reservation number"),
	reservation_download("There are no reservations to download"),
	reservation_id("Invalid or absent reservation ID"),
	reservation_json("JSON request to reservation service failed"),
	reservation_organization("The reservation does not belong to the property manager"),
	reservation_state("Invalid or absent reservation state"),
	reservation_upload("There are no reservations that have been uploaded"),
	reservation_upload_id("Uploaded reservation numbers must be prefixed with the manager's pos code"),
	reservation_value("Invalid or absent reservation values"),
	rest_invalid("Invalid REST URL"),
	step_request_json("JSON request to registration step request failed"),
	rows_invalid("Invalid nomber of rows"),
	service_absent("No handler for the requested service"),
	service_duplicate("Duplicate service name"),
	service_ip_address("Invalid or unacceptable IP address"),
	service_method("Invalid method for the service"),
	special_absent("There are no specials available"),
	task_list("Invalid list of activity names and IDs"),
	tax_calculation("Something wrong with taxes calculations"),
	terms_invalid("Invalid or absent terms and conditions"),
	text_id("Invalid or absent text ID"),
	text_json("JSON request to text service failed"),
	text_model("Invalid or absent text model"),
	text_type("Invalid or absent text type"),
	text_to_speech("Invalid or absent audio version of text"),
	time_invalid("Invalid or absent time"),
	transaction_charged("Transaction was already charged"),
	type_invalid("The type is invalid or absent"),
	value_id("Invalid or absent value ID"),
	value_model("Invalid or absent value model"),
	widget_book("Book widget exception"),
	widget_calendar("Calendar widget exception"),
	widget_image("Image widget exception"),
	widget_map("Map widget exception"),
	widget_price("Price widget exception"),
	widget_quote("Quote widget exception"),
	widget_review("Review widget exception"),
	widget_route("Route widget exception"),
	widget_text("Text widget exception"),
	xml_invalid("Invalid XML message format");
	// TO DO - to fix the compile error
	
	private String notes;
	
	Error(String notes) {
		this.notes = notes;
	}

	public String getDetailedMessage() { return name() + ":" + notes; }
	public String getMessage() { return notes; }

//	public String getNotes() { return notes; }

}

/**
<!-- 

			<table>
				<thead>
					<tr>
						<th>Code</th>
						<th>Message</th>
					</tr>
				</thead>
				<tbody>
					<tr><td>account_id</td><td>Invalid or absent account ID</td></tr>
					<tr><td>account_data</td><td>There is no data for the account(s)</td></tr>
					<tr><td>account_list</td><td>Invalid list of account names and IDs</td></tr>
					<tr><td>asset_list</td><td>Invalid list of asset names and IDs</td></tr>
					<tr><td>card_address</td><td>Invalid address for credit card</td></tr>
					<tr><td>card_amount</td><td>Invalid or absent credit card payment amount</td></tr>
					<tr><td>card_approval_required</td><td>Call for card approval code</td></tr>
					<tr><td>card_code</td><td>Credit card CCV code is invalid</td></tr>
					<tr><td>card_expired</td><td>Credit card has expired</td></tr>
					<tr><td>card_gateway</td><td>Credit card payment gateway error or failure</td></tr>
					<tr><td>card_holder</td><td>Credit card holder is invalid</td></tr>
					<tr><td>card_insufficient_funds</td><td>Credit card has insufficient funds</td></tr>
					<tr><td>card_month</td><td>Credit card expiry month is invalid</td></tr>
					<tr><td>card_number</td><td>Credit card number is invalid</td></tr>
					<tr><td>card_postal_code</td><td>Invalid postal or ZIP code for credit card</td></tr>
					<tr><td>card_reference</td><td>Invalid credit card payment reference</td></tr>
					<tr><td>card_year</td><td>Credit card expiry year is invalid</td></tr>
					<tr><td>card_timeout</td><td>Bank interface timeout because of communications failure</td></tr>
					<tr><td>country_code</td><td>Invalid two character ISO 3166-1 country code</td></tr>
					<tr><td>country_json</td><td>JSON request to country service failed</td></tr>
					<tr><td>country_list</td><td>Invalid list of country names and ISO 3166-1 codes</td></tr>
					<tr><td>country_region</td><td>Invalid ISO 3166-1 country code or ISO 3166-2 region code</td></tr>
					<tr><td>criteria_invalid</td><td>Criteria are absent, null, blank or badly formatted = must be key=value pairs</td></tr>
					<tr><td>currency_exchange_rate</td><td>There is no exchange rate for the specified currency pair and date</td></tr>
					<tr><td>currency_code</td><td>Invalid or absent three character ISO 4217 currency code</td></tr>
					<tr><td>currency_json</td><td>JSON request to currency service failed</td></tr>
					<tr><td>currency_list</td><td>Invalid list of currency names and ISO 4217 codes</td></tr>
					<tr><td>data_unchanged</td><td>There have been no changes since the last download</td></tr>
					<tr><td>date_from</td><td>Invalid or absent arrival date</td></tr>
					<tr><td>date_format</td><td>Invalid date - must be yyyy-mm-dd format</td></tr>
					<tr><td>date_invalid</td><td>Invalid or absent date</td></tr>
					<tr><td>date_range</td><td>Invalid or negative date range</td></tr>
					<tr><td>date_to</td><td>Invalid or absent departure date</td></tr>
					<tr><td>entity_id</td><td>Invalid or absent subsidiary account ID</td></tr>
					<tr><td>event_id</td><td>Invalid or absent event reference</td></tr>
					<tr><td>event_data</td><td>There is no data for the event(s)</td></tr>
					<tr><td>family_name</td><td>Invalid or absent family name</td></tr>
					<tr><td>finance_id</td><td>Invalid or absent bank or cash account ID</td></tr>
					<tr><td>finance_list</td><td>Invalid list of bank and cash account names and IDs</td></tr>
					<tr><td>image_data</td><td>There are no images for the specified parameters</td></tr>
					<tr><td>image_upload</td><td>There was an error with the upload of image</td></tr>
					<tr><td>language_code</td><td>Invalid or absent two character ISO 639-1 language code</td></tr>
					<tr><td>language_json</td><td>JSON request to language service failed</td></tr>
					<tr><td>language_translate</td><td>Cannot translate to the specified language</td></tr>
					<tr><td>license_absent</td><td>This operation is not licensed for the client and/or product </td></tr>
					<tr><td>license_bad</td><td>There is no license for this supplier - see http://www.razor-cloud.com/docs/RazorLicense.pdf </td></tr>
					<tr><td>license_type</td><td>Invalid license request type</td></tr>
					<tr><td>license_wait</td><td>This operation is throttled - please space requests by at least</td></tr>
					<tr><td>location_id</td><td>Invalid or absent location ID</td></tr>
					<tr><td>locationid_nearby</td><td>Invalid or absent location ID, distance or unit</td></tr>
					<tr><td>location_name</td><td>Invalid or absent location name, region or country</td></tr>
					<tr><td>model_id</td><td>Invalid or absent model ID</td></tr>
					<tr><td>model_type</td><td>Invalid or absent model type</td></tr>
					<tr><td>monitor_data</td><td>There was an error monitoring data </td></tr>
					<tr><td>nameid_data</td><td>There are no names for the list </td></tr>
					<tr><td>nameid_json</td><td>JSON request to name ID service failed</td></tr>
					<tr><td>number_format</td><td>Invalid number format or is not a number</td></tr>
					<tr><td>number_invalid</td><td>Invalid number or number is out of range</td></tr>
					<tr><td>organization_id</td><td>Invalid or absent organization ID</td></tr>
					<tr><td>party_altid</td><td>Invalid or absent foreign party ID</td></tr>
					<tr><td>party_attribute</td><td>The party has no attributes</td></tr>
					<tr><td>party_criteria</td><td>There are no parties that fit the specified criteria</td></tr>
					<tr><td>party_country</td><td>There are no countries set for the specified party</td></tr>
					<tr><td>party_data</td><td>There is no data for the party</td></tr>
					<tr><td>party_emailaddress</td><td>There is no party with this email address</td></tr>
					<tr><td>party_id</td><td>Invalid or absent party ID</td></tr>
					<tr><td>party_inactive</td><td>The party is not active or has been suspended</td></tr>
					<tr><td>party_json</td><td>JSON request to party service failed</td></tr>
					<tr><td>party_list</td><td>Invalid list of party names and IDs</td></tr>
					<tr><td>party_type</td><td>Invalid party type(s)</td></tr>
					<tr><td>party_value</td><td>Absent or invalid party values</td></tr>
					<tr><td>pos_absent</td><td>There is no point of sale code</td></tr>
					<tr><td>pos_invalid</td><td>The point of sale code is invalid</td></tr>
					<tr><td>pos_party</td><td>Point of sale code is not valid for this party</td></tr>
					<tr><td>pos_product</td><td>Point of sale code is not valid for this product</td></tr>
					<tr><td>position_latitude</td><td>Invalid latitude - must be -90&#176; &lt;= latitude &lt;= 90&#176;</td></tr>
					<tr><td>position_longitude</td><td>Invalid longitude - must be -180&#176; &lt;= longitude &lt;= 180&#176;</td></tr>
					<tr><td>position_nearby</td><td>Invalid latitude, longitude, distance or unit</td></tr>
					<tr><td>price_data</td><td>Price does not exist for the product, dates and/or currency</td></tr>
					<tr><td>price_id</td><td>Invalid or absent price ID</td></tr>
					<tr><td>price_json</td><td>JSON request to price service failed</td></tr>
					<tr><td>price_policy</td><td>Price or deposit policy has not been set for the product</td></tr>
					<tr><td>product_altid</td><td>Invalid or absent foreign product ID</td></tr>
					<tr><td>product_attribute</td><td>The property has no attributes</td></tr>
					<tr><td>product_criteria</td><td>There are no properties that fit the specified criteria</td></tr>
					<tr><td>product_data</td><td>There is no data for the property</td></tr>
					<tr><td>product_exists</td><td>A product or property with this name already exists</td></tr>
					<tr><td>product_id</td><td>Invalid or absent product ID</td></tr>
					<tr><td>product_inactive</td><td>The property is not active or has been suspended</td></tr>
					<tr><td>product_json</td><td>JSON request to product service failed</td></tr>
					<tr><td>product_list</td><td>Invalid list of property or product names and IDs</td></tr>
					<tr><td>product_location</td><td>There are no properties at this location</td></tr>
					<tr><td>product_not_available</td><td>Property not available for these dates</td></tr>
					<tr><td>product_not_online</td><td>The product does not have online availability</td></tr>
					<tr><td>product_position</td><td>The property does not have a geographic position (latitude/longitude)</td></tr>
					<tr><td>product_rating</td><td>The property has no ratings or reviews</td></tr>
					<tr><td>product_state</td><td>Invalid or absent product state</td></tr>
					<tr><td>product_type</td><td>Invalid or absent product type</td></tr>
					<tr><td>product_upload</td><td>Invalid or incomplete product upload</td></tr>
					<tr><td>product_value</td><td>Invalid or absent product values</td></tr>
					<tr><td>rate_data</td><td>The reservation has no ratings or reviews</td></tr>
					<tr><td>rate_type</td><td>Invalid or absent rating type</td></tr>
					<tr><td>reservation_agentid</td><td>Invalid or absent agent ID</td></tr>
					<tr><td>reservation_altid</td><td>Invalid or absent foreign reservation ID</td></tr>
					<tr><td>reservation_api</td><td>API error - displays error returned by partner API</td></tr>
					<tr><td>reservation_bad</td><td>The reservation is invalid or does not belong to the pos code or has already been confirmed</td></tr>
					<tr><td>reservation_customerid</td><td>Invalid or absent guest ID</td></tr>
					<tr><td>reservation_cancel</td><td>The reservation has been confirmed so may not be cancelled in this way - send a cancellation request to the property manager</td></tr>
					<tr><td>reservation_download</td><td>There are no reservations to download</td></tr>
					<tr><td>reservation_id</td><td>Invalid or absent reservation ID</td></tr>
					<tr><td>reservation_json</td><td>JSON request to reservation service failed</td></tr>
					<tr><td>reservation_organization</td><td>The reservation does not belong to the property manager</td></tr>
					<tr><td>reservation_state</td><td>Invalid or absent reservation state</td></tr>
					<tr><td>reservation_upload</td><td>There are no reservations that have been uploaded</td></tr>
					<tr><td>reservation_upload_id</td><td>Uploaded reservation numbers must be prefixed with the manager's pos code</td></tr>
					<tr><td>reservation_value</td><td>Invalid or absent reservation values</td></tr>
					<tr><td>rest_invalid</td><td>Invalid REST URL</td></tr>
					<tr><td>rows_invalid</td><td>Invalid nomber of rows</td></tr>
					<tr><td>service_absent</td><td>No handler for the requested service</td></tr>
					<tr><td>service_duplicate</td><td>Duplicate service name</td></tr>
					<tr><td>service_ip_address</td><td>Invalid or unacceptable IP address</td></tr>
					<tr><td>service_method</td><td>Invalid method for the service</td></tr>
					<tr><td>special_absent</td><td>There are no specials available</td></tr>
					<tr><td>task_list</td><td>Invalid list of activity names and IDs</td></tr>
					<tr><td>terms_invalid</td><td>Invalid or absent terms and conditions</td></tr>
					<tr><td>text_id</td><td>Invalid or absent text ID</td></tr>
					<tr><td>text_json</td><td>JSON request to text service failed</td></tr>
					<tr><td>text_model</td><td>Invalid or absent text model</td></tr>
					<tr><td>text_type</td><td>Invalid or absent text type</td></tr>
					<tr><td>text_to_speech</td><td>Invalid or absent audio version of text</td></tr>
					<tr><td>time_invalid</td><td>Invalid or absent time</td></tr>
					<tr><td>type_invalid</td><td>The type is invalid or absent</td></tr>
					<tr><td>value_id</td><td>Invalid or absent value ID</td></tr>
					<tr><td>value_model</td><td>Invalid or absent value model</td></tr>
					<tr><td>widget_book</td><td>Book widget exception</td></tr>
					<tr><td>widget_calendar</td><td>Calendar widget exception</td></tr>
					<tr><td>widget_image</td><td>Image widget exception</td></tr>
					<tr><td>widget_map</td><td>Map widget exception</td></tr>
					<tr><td>widget_price</td><td>Price widget exception</td></tr>
					<tr><td>widget_quote</td><td>Quote widget exception</td></tr>
					<tr><td>widget_review</td><td>Review widget exception</td></tr>
					<tr><td>widget_route</td><td>Route widget exception</td></tr>
					<tr><td>widget_text</td><td>Text widget exception</td></tr>
					<tr><td>xml_invalid</td><td>Invalid XML message format");
					<tr><td>HTTP_400</td><td>Request cannot be fulfilled due to bad syntax</td></tr>
					<tr><td>HTTP_404</td><td>Server not found because of badly formed URL or server is temporarily unavailable</td></tr>
					<tr><td>HTTP_414</td><td>Request URI is too long, usually because of large REST upload requests</td></tr>
					<tr><td>HTTP_431</td><td>Server cannot accept the request because one or all URL parameters are too large</td></tr>
					<tr><td>HTTP_500</td><td> Internal server error because of badly formed URL, absent or incorrectly formatted parameters or parameters not in correct sequence</td></tr>
					<tr><td>HTTP_503</td><td>Server temporarily unavailable because it is overloaded or down for maintenance</td></tr> 
				</tbody>
				<tfoot><tr><th></th><th></th></tr></tfoot>
			</table>

 -->

 * The Class PaygateService is to make payments via the PayGate payment gateway.
 * It uses an XML message having the following information:
 * 
		Transaction Code (Number stat, Description sdesc)
		0 Not Done
		1 Approved
		2 Declined
		3 Paid
		4 Refunded
		5 Received by PayGate
		6 Replied to Client Returned by authtx if Unique Transaction is enabled
		and this transaction is a duplicate. In this case, check the
		result code to determine the original transaction result.
		
		Result Code (Number res Description rdesc)
		Credit Card Errors ? These Result Codes (res attribute) are returned if the transaction cannot be completed successfully
		due to a problem with the card or data. Unless otherwise noted the stat attribute will be 2.
		900001 Call for Approval Card processing : Authorisation (authtx) Query (querytx)
		900002 Card Expired Card processing : Authorisation (authtx) Query (querytx)
		900003 Insufficient Funds Card processing : Authorisation (authtx) Query (querytx)
		900004 Invalid Card Number Card processing : Authorisation (authtx) Query (querytx)
		900005 Bank Interface Timeout Indicates a communications failure between the banks systems.
 */