<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
	
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>${title}</title>
</head>
<body style="padding:0px !important; margin:0px !important;-webkit-text-size-adjust:none;" bgcolor="#efefef" >
    <table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%" style="margin:0px !important; padding:0px !important; background-color:#efefef;" >
    <tr>
        <td style="padding: 0 25px 25px 25px;" id="emailcontent">
        <div style="width:100%; max-width:652px; margin: 0 auto;">
        <table border="0" cellpadding="0" cellspacing="0" id="mainemailwrap" style="margin:0px !important; padding:0px !important; background-color:#ffffff; width:100%; max-width:652px">
            <tr>
                <td align="center">
                <!--start of master table-->
                <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;" id="mainemailwrap" >
                    <tr>
                        <td valign="middle" height="40" class="spacer"></td>
                    </tr>
                    <!--main image - image part -->
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="promo_image">
                            <img src="https://s3.amazonaws.com/mybookingpal/templates/logo.png">
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <!-- main image - END image part -->
                    <tr>
                        <td valign="middle" height="20" class="spacer"></td>
                    </tr>
                </table>
                <!-- email body -->
                <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;" id="mainemailwrap" >
                    <tr>
                        <td valign="middle" height="22" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:23px; font-weight:bold; color:#8AC43F;">Thanks ${reservationWrapper.reservation.customer.name}! Your reservation is now confirmed.</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="18" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:22px; font-weight:bold; color:#0094F9;">${reservationWrapper.reservation.product.name},${reservationWrapper.reservation.product.location.region} ${reservationWrapper.reservation.product.location.country}</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="20" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td  valign="top">
                            <div style="float:left; margin-right:23px;">
                                <img src="${reservationWrapper.imageSrc}" width="140" height="140"/>
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td valigh="top" align="top" style="vertical-align:top;">
                                        <span style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px;">Address:</span></td>
                                    <td style="padding-left:10px;">
                                        <p style="margin:0; padding:0; color:#052A4D; font-family:arial; font-size:14px;"> ${reservationWrapper.reservation.product.address[0]}</p>
                                        <p style="padding:0; margin:0; font-family:arial; font-size:14px;">${reservationWrapper.reservation.product.location.name}, ${reservationWrapper.reservation.product.location.region},${reservationWrapper.reservation.product.location.country}</p>
                                        </br>
                                    </td>
                                   
                                </tr>
                                <tr>
                                <td valigh="top" align="top" style="vertical-align:top;">
                                        <span style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px;">Location Map:</span></td>
                                 <td style="padding-left:10px;">
                                   <img src=http://maps.googleapis.com/maps/api/staticmap?center=${reservationWrapper.reservation.product.latitude},${reservationWrapper.reservation.product.longitude}&zoom=13&size=200x100&maptype=roadmap/>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="middle" height="10" colspan="3" class="spacer"></td>
                                </tr>
                                     <td valigh="top" align="top" style="vertical-align:top;">
                                        <span style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px;">Getting There:</span></td>
                                 <td style="padding-left:10px;">
                                    <td style="padding-left:10px;">
		                                 <a href="${reservationWrapper.url}getMap?longitude=${reservationWrapper.reservation.product.longitude}&latitude=${reservationWrapper.reservation.product.latitude}">Get Directions</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="middle" height="10" colspan="3" class="spacer"></td>
                                </tr>
                                <tr>
                                    <td><span style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px;">Phone:</span></td>
                                    <td style="padding-left:10px;"><a href="#" style="color:#0094F9; font-family:arial; font-size:14px; font-weight:bold; text-decoration:underline;">${reservationWrapper.reservation.supplier.dayphone}</a></td>
                                </tr>
                                <tr>
                                    <td valign="middle" height="10" colspan="3" class="spacer"></td>
                                </tr>
                                <tr>
                                    <td><span style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px;">Email:</span></td>
                                    <td style="padding-left:10px;"><a href="#" style="color:#0094F9; font-family:arial; font-size:14px; font-weight:bold; text-decoration:underline;">${reservationWrapper.reservation.supplier.emailaddress}</a></td>
                                </tr>
                                <tr>
                                	<#if userName??>
   Hi ${userName}, How are you?
</#if>
                                    <td><span style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px;"></span></td>
                                    <td style="padding-left:10px;"><a href="#" style="color:#0094F9; font-family:arial; font-size:14px; font-weight:bold; text-decoration:underline;"></a></td>
                                </tr>
                            </table>    
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="24" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <a href="#" style="text-decoration:none; margin-right:10px;">
                                <img src="https://s3.amazonaws.com/mybookingpal/templates/butt1.jpg">
                            </a>
                            <a href="${reservationWrapper.url}printEmail?emailType=Reservation&reservationId=${reservationWrapper.reservation.altid}" style="text-decoration:none;">
                                <img src="https://s3.amazonaws.com/mybookingpal/templates/butt2.jpg">
                            </a>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="24" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Your reservation</div>
                                    </td>
                                    <td >
                                        <span style="color:#052A4D; font-family:arial; font-size:14px;">${reservationWrapper.lengthOfStay} night,${reservationWrapper.reservation.quantity} room</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Check-in:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:arial; font-size:14px;">${reservationWrapper.dayOfArrivalDate},${reservationWrapper.monthOfArrivalDate} ${reservationWrapper.dateOfArivalDate},${reservationWrapper.yearOfArivalDate} (${reservationWrapper.reservation.arrivaltime})</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Check-out:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:arial; font-size:14px;">${reservationWrapper.dayOfDepDate},${reservationWrapper.monthOfDepDate} ${reservationWrapper.dateOfDepDate},${reservationWrapper.yearOfDepDate} (${reservationWrapper.reservation.departuretime})</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                     <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Booking number:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:arial; font-size:14px;">${reservationWrapper.reservation.altid}</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                  
                   
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Booked by:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:arial; font-size:14px;">${reservationWrapper.reservation.customer.name} (<a href="#" style="font-family:arial; text-decoration:underline; color:#0094F9;">${reservationWrapper.reservation.customer.emailaddress}</a>)</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="22" colspan="3" class="spacer"></td>
                    </tr>  
                    <tr>
                        <td style="width:40px;"></td>
                        <td style="background-color:#D7E9F4; padding:18px;">
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important; width:100%;">
                            
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:arial; font-weight:bold; font-size:20px;">Total cost</span>
                                </td>
                                <td align="right">
                                    <span style="color:#052A4D; font-family:arial; font-size:20px; font-weight:bold;">$<span>${reservationWrapper.reservation.cost} ${reservationWrapper.reservation.currency}</span></span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div style="text-align:right;">
                                    <span><img src="https://s3.amazonaws.com/mybookingpal/templates/checkboxArrow.png?"></span>
                                    <a href="#" style="font-family:arial; font-size:14px; text-decoration:underline; color:#0094F9;">Best Price Guaranteed</a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td valign="middle" height="30" colsan="3" class="spacer"></td>
                            </tr>
                            <tr>
                                <td><span style="font-family:arial; font-size:14px; color:#052A4D;">You'll pay when you stay at ${reservationWrapper.reservation.product.name} at ${reservationWrapper.reservation.product.location.region} ${reservationWrapper.reservation.product.location.country}.</span></td>
                            </tr>
                            <tr>
                                <td><span style="font-family:arial; font-size:14px; color:#052A4D;">TAX (14%) not included.</span></td>
                            </tr>
                            <tr>
                                <td><span style="font-family:arial; font-size:14px; color:#052A4D;">City tax (1.50%) not included.</span></td>
                            </tr>
                            <tr>
                                <td><span style="font-family:arial; font-size:14px; color:#052A4D;">Please note: additional supplements (e.g. extra bed) are not added to this total.</span></td>
                            </tr>
                            <tr>
                                <td><span style="font-family:arial; font-size:14px; color:#052A4D;">The total price shown is the amount you will pay to the property. Booking.com does not charge any reservation, administration or other fees.</span></td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="20" colsan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:22px; font-weight:bold; color:#052A4D;">${reservationWrapper.reservation.product.name},${reservationWrapper.reservation.product.location.region} ${reservationWrapper.reservation.product.location.country}</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="20" colsan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <div style="width:100%; height:200px; background-color:#D7E9F4;"></div>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="20" colsan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:22px; font-weight:bold; color:#052A4D;">Room Details</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colsan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; font-weight:bold; color:#052A4D;">Bed type preferences are subject to availability and cannot be guaranteed.</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="15" colsan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Guest name</div>
                                    </td>
                                    <td >
                                        <span style="color:#052A4D; font-family:arial; font-size:14px;">${reservationWrapper.guestName}   </span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Number of guests</div>
                                    </td>
                                    <td >
                                        <span style="color:#052A4D; font-family:arial; font-size:14px;">${reservationWrapper.reservation.adult} adults, ${reservationWrapper.reservation.child} child (up to 17 years of age)</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Meal plan</div>
                                    </td>
                                    <td >
                                        <span style="color:#052A4D; font-family:arial; font-size:14px;">${reservationWrapper.mealPlan}  </span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td>
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px;">Prepayment</div>
                                    </td>
                                    <td >
                                        <span style="color:#052A4D; font-family:arial; font-size:14px; font-weight:bold;">No deposit will be charged.</span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td style="vertical-align:top;">
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px; ">Cancellation policy</div>
                                    </td>
                                    <td >
                                        <div style="color:#052A4D; font-family:arial; font-size:14px; width:300px; line-height:22px;">
										<#list reservationWrapper.listCancellationPolicy as policy>
									${policy}</br>
									</#list>
										</div>
                                        <div style="color:#052A4D; font-family:arial; font-size:14px; width:300px; line-height:22px;">Any cancellation or modification fees are determined by the property.</div>
                                        <div style="color:#052A4D; font-family:arial; font-size:14px; width:300px; line-height:22px;">Additional costs will be paid to the property.</div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="15" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td style="vertical-align:top;">
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px; ">Cancellation cost</div>
                                    </td>
                                    <td >
                                        <div style="color:#052A4D; font-family:arial; font-size:14px; width:300px; line-height:22px;">
										
										<#list reservationWrapper.listCancellationRules as rules>
										${rules}</br>
										</#list>

										</div>
                                        <div style="color:#052A4D; font-family:arial; font-size:14px; width:300px; line-height:22px;"><a href="${reservationWrapper.cancellationLink}" style="text-decoration:underline; color:#0094F9;">Cancel your booking.</a></div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="25" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                            
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="25" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:22px; font-weight:bold; color:#052A4D;">Important Information</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">You must show a valid photo ID and credit card upon check-in. Please note that all special requests cannot be guaranteed and are subject to availability upon check-in. Additional charges may apply.</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="14" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">Please note: Guests are charged tax per day in addition to the daily private parking charge.</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="25" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:22px; font-weight:bold; color:#052A4D;">Payment</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">You have now confirmed and guaranteed your reservation by credit card.
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="14" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">All payments are to be made at the property during your stay, unless otherwise stated in the hotel policies or in the room conditions.
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="14" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">The hotel reserves the right to pre-authorize credit cards prior to arrival.
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="14" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; font-weight:bold; color:#052A4D;">This property accepts the following forms of payment:
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="25" colspan="3" class="spacer"></td>
                    </tr>
					<#if reservationWrapper.parkingAvailable || reservationWrapper.internetAvailable>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:22px; font-weight:bold; color:#052A4D;">Booking Conditions</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="14" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
						<#if reservationWrapper.parkingAvailable>
	
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td style="vertical-align:top;">
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px; ">Guest Parking</div>
                                    </td>
                                    <td >
									
                                        <div style="color:#052A4D; font-family:arial; font-size:14px; width:300px; line-height:22px;">Private parking is available on site (reservation is not needed) and costs ${reservationWrapper.reservation.currency} TBD per day.</div>
                                    </td>
                                </tr>
                            </table>
							</#if>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
						<#if reservationWrapper.internetAvailable>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td style="vertical-align:top;">
                                        <div style="color:#052A4D; font-family:arial; font-weight:bold; font-size:14px; width:220px; ">Internet</div>
                                    </td>
                                    <td >
                                        <div style="color:#052A4D; font-family:arial; font-size:14px; width:300px; line-height:22px;">WiFi is available in all areas and charges apply. Internet via TV is available in all areas and charges apply.</div>
                                    </td>
                                </tr>
                            </table>
							</#if>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr> 
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="25" colspan="3" class="spacer"></td>
                    </tr>
					</#if>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:22px; font-weight:bold; color:#052A4D;">Need help?</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">For any questions related to the property, you can contact ${reservationWrapper.reservation.product.name} at
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:arial; font-size:14px;">Beverly Hills directly at:</span>
                                </td>
                                <td align="right">
                                    <a href="#" style="color:#0094F9; font-family:arial; font-size:14px; font-weight:bold; text-decoration:underline;">${reservationWrapper.reservation.supplier.dayphone}</a>
                                </td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:arial; font-size:14px;">If you'd like to change or upgrade your reservation, visit</span>
                                </td>
                                <td align="right">
                                    <a href="#" style="color:#0094F9; font-family:arial; font-size:14px; font-weight:bold; text-decoration:underline; padding-left:5px;">My BookingPal.com.</a>
                                </td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">Our Customer Service team is also here to help. Send us a message or call us at:
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:arial; font-size:14px;">Support in English:</span>
                                </td>
                                <td align="right">
                                    <a href="#" style="color:#0094F9; font-family:arial; font-size:14px; font-weight:bold; text-decoration:underline;">1 (888) 850 3958</a>
                                </td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:arial; font-size:14px;">Support in Spanish:</span>
                                </td>
                                <td align="right">
                                    <a href="#" style="color:#0094F9; font-family:arial; font-size:14px; font-weight:bold; text-decoration:underline;">1 (866) 938 1297</a>
                                </td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:14px; color:#052A4D;">
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:arial; font-size:14px;">When abroad:</span>
                                </td>
                                <td align="right">
                                    <a href="#" style="color:#0094F9; font-family:arial; font-size:14px; font-weight:bold; text-decoration:underline;">+44 20 3320 2609</a>
                                </td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="45" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:18px; font-style:italic; font-weight:bold; color:#052A4D;">Have a great trip!</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:arial; font-size:18px; font-style:italic; font-weight:bold; color:#052A4D;">MyBookingPal.com Customer Service Team</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="25" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content">
                        <div style="font-family:arial; font-size:14px; color:#052A4D; width:400px;">Copyright &copy; 2014 <a href="#" style="color:#0094F9; font-family:arial; font-weight:bold; text-decoration:underline;">MyBookingPal.com</a>. All rights reserved. This email was sent by MyBookingPal.com</div></td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="60" colsan="3" class="spacer"></td>
                    </tr>    
                </table>
                <!--end of master table-->
                </td>
            </tr>
            </table>
        </div>
        </td>
    </tr>
</table>
<input type="hidden" id="from" name="from" value=""/>
</body><br></html>