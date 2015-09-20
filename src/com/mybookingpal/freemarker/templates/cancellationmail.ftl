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
                            <div style="float:left;">
                                <img src="https://s3.amazonaws.com/mybookingpal/templates/logo.png">
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td valign="middle" height="10" colspan="3" class="spacer"></td>
                                </tr>
                                
                                
                                <tr>
                                    <td>
                                        <span style="font-family:Open Sans; font-size:25px; font-weight:bold; color:#9E040F; padding-left:129px;">CANCELLATION</span>
                                    </td>
                                </tr>
                            </table>    
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
                        <td class="content" style="font-family:Open Sans; color:#052A4D;">Dear ${reservationWrapper.reservation.customer.name},</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:Open Sans; color:#052A4D;">Your reservation at ${reservationWrapper.reservation.product.name} at ${reservationWrapper.reservation.product.location.name} is now canceled.</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="20" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="24" colspan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td  valign="top">
                            <div style="float:left; margin-right:23px;">
                                <img src="${reservationWrapper.imageSrc}" width="140" height="140"/>
                            </div>
                            <div>
                                <a href="#" style="font-family:Open Sans; text-decoration:underline; color:#0094F9; font-size:22px; font-weight:bold; line-height:26px;">${reservationWrapper.reservation.product.name},${reservationWrapper.reservation.product.location.region} ${reservationWrapper.reservation.product.location.country}</a>
                            </div>
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                <tr>
                                    <td valign="middle" height="10" colspan="3" class="spacer"></td>
                                </tr>
                                <tr>
                                    <td valigh="top" align="top" style="vertical-align:top;">
                                        <span style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px; padding-right:10px;">Address:</span></td>
                                    <td>
                                        <p style="margin:0; padding:0; color:#052A4D; font-family:Open Sans; font-size:14px;">${reservationWrapper.reservation.product.address[0]}</p>
                                        <p style="padding:0; margin:0; font-family:Open Sans; font-size:14px;">${reservationWrapper.reservation.product.location.name}, ${reservationWrapper.reservation.product.location.region}</p>
                                        <p style="padding:0; margin:0; font-family:Open Sans; font-size:14px;">${reservationWrapper.reservation.product.location.country}</p>
                                    </td>
                                </tr>
                                <tr>
                                    <td valign="middle" height="10" colspan="3" class="spacer"></td>
                                </tr>
                                <tr>
                                    <td><span style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px;">Phone:</span></td>
                                    <td><a href="#" style="color:#0094F9; font-family:Open Sans; font-size:14px; text-decoration:underline;">${reservationWrapper.reservation.supplier.dayphone}</a></td>
                                </tr>
                                <tr>
                                    <td valign="middle" height="10" colspan="3" class="spacer"></td>
                                </tr>
                                <tr>
                                    <td><span style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px;">Email:</span></td>
                                    <td><a href="#" style="color:#0094F9; font-family:Open Sans; font-size:14px; text-decoration:underline;">${reservationWrapper.reservation.supplier.emailaddress}</a></td>
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
                        <div style="border-top:1px dashed #052A4D;"></div>
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
                                        <div style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px; width:220px;">Booking Number:</div>
                                    </td>
                                    <td >
                                        <span style="color:#052A4D; font-family:Open Sans; font-size:14px;">${reservationWrapper.reservation.altid}</span>
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
                            <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                                
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
                                        <div style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px; width:220px;">Check-in:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:Open Sans; font-size:14px;">${reservationWrapper.dayOfArrivalDate},${reservationWrapper.monthOfArrivalDate} ${reservationWrapper.dateOfArivalDate},${reservationWrapper.yearOfArivalDate} (${reservationWrapper.reservation.arrivaltime})</span>
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
                                        <div style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px; width:220px;">Check-out:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:Open Sans; font-size:14px;">${reservationWrapper.dayOfDepDate},${reservationWrapper.monthOfDepDate} ${reservationWrapper.dateOfDepDate},${reservationWrapper.yearOfDepDate}  (${reservationWrapper.reservation.departuretime})</span>
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
                                        <div style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px; width:220px;">Booked by:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:Open Sans; font-size:14px;">${reservationWrapper.reservation.customer.name} (<a href="#" style="font-family:Open Sans; text-decoration:underline; color:#0094F9;">${reservationWrapper.reservation.customer.emailaddress}</a>)</span>
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
                                        <div style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px; width:220px;">Booked first made on:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:Open Sans; font-size:14px;">${reservationWrapper.bookingFirstMadeOn}</span>
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
                                        <div style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:14px; width:220px;">Last modification made on:</div>
                                    </td>
                                    <td>
                                        <span style="color:#052A4D; font-family:Open Sans; font-size:14px;">${reservationWrapper.lastModificationDate}</span>
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
                        <td style="background-color:#D7E9F4; padding:7px;">
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important; width:100%;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:16px;">Total cancellation cost</span>
                                </td>
                                <td align="right">
                                    <span style="color:#052A4D; font-family:Open Sans; font-size:16px;">${reservationWrapper.reservation.currency}<span style="padding-left:5px;">${reservationWrapper.totalCancellationCost}</span></span>
                                </td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="35" colsan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-size:22px; font-family:Open Sans; font-weight:bold; color:#052A4D;">Customer Service</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="10" colsan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:16px;">Support in English:</span>
                                </td>
                                <td align="right">
                                    <a href="#" style="color:#0094F9; font-family:Open Sans; font-size:16px; text-decoration:underline;">1 (888) 850 3958</a>
                                </td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>  
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colsan="3" class="spacer"></td>
                    </tr>
                     <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:16px;">Support in Spanish:</span>
                                </td>
                                <td align="right">
                                    <a href="#" style="color:#0094F9; font-family:Open Sans; font-size:16px; text-decoration:underline;">1 (866) 938 1297</a>
                                </td>
                            </tr>
                        </table>
                        </td>
                        <td style="width:46px;"></td>  
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colsan="3" class="spacer"></td>
                    </tr>
                     <tr>
                        <td style="width:40px;"></td>
                        <td>
                        <table border="0" cellspacing="0" cellpadding="0" style="margin:0px !important; padding:0px !important;">
                            <tr>
                                <td>
                                    <span style="color:#052A4D; font-family:Open Sans; font-weight:bold; font-size:16px;">When abroad:</span>
                                </td>
                                <td align="right">
                                    <a href="#" style="color:#0094F9; font-family:Open Sans; font-size:16px; text-decoration:underline;">+44 20 3320 260</a>
                                </td>
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
                        <td>
                        <div style="border-top:1px dashed #052A4D;"></div>
                        </td>
                        <td style="width:46px;"></td>   
                    </tr>
                    <tr>
                        <td valign="middle" height="5" colsan="3" class="spacer"></td>
                    </tr>
                    <tr>
                        <td style="width:40px;"></td>
                        <td class="content" style="font-family:Open Sans; font-size:12px; color:#052A4D;">Copyright &copy; 2014 <a href="#" style="color:#0094F9; font-family:Open Sans; font-weight:bold; text-decoration:underline;">MyBookingPal.com</a>. All rights reserved. This email was sent by MyBookingPal.com</td>
                        <td style="width:46px;"></td>
                    </tr>
                    <tr>
                        <td valign="middle" height="40" colsan="3" class="spacer"></td>
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
</body><br></html>