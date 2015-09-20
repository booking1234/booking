function validate()
{
	var pmsAccountId = document.forms["reg1Form"]["accid"].value;
	var companyName = document.forms["reg1Form"]["compname"].value;
	var fullName = document.forms["reg1Form"]["fullname"].value;
	var address = document.forms["reg1Form"]["addr"].value;
	var city = document.forms["reg1Form"]["city"];
	var state = document.forms["reg1Form"]["state"];
	var zip = document.forms["reg1Form"]["zip"].value;
	var telMainPart = document.forms["reg1Form"]["telno"].value;
	var email = document.forms["reg1Form"]["email"].value;
	var country = document.forms["reg1Form"]["country"].value;
	var privacyPolicyAgreeCheckbox = document.forms["reg1Form"]["privacyPolicyAgreeCheckbox"];
	
	if(!validate_pms(pmsAccountId))
	{
		return false;
	}
	if(!validate_cname(companyName))
	{
		return false;
	}
	if(!validate_fname(fullName))
	{
		return false;
	}
	if(!validate_telMainPart(telMainPart))
	{
		return false;
	}
	if(!validate_email(email))
	{
		return false;
	}
	if(!validate_address(address))
	{
		return false;
	}
	if(!validate_city(city))
	{
		return false;
	}
	if(!validate_state(state))
	{
		return false;
	}
	if(!validate_country(country))
	{
		return false;
	}
	if(!validate_zip(zip))
	{
		return false;
	}
	if(!privacyPolicyAgreeCheckbox.checked)
	{
		alert("Please agree to the privacy policy");
		return false;
	}
	
	document.getElementById("submit").disabled = true;
	document.getElementById("submit").style.visibility = "hidden";
	return true;
}

function isEmpty(value)
{
	if(value == null || value == "")
	{
		return true;
	}
	
	return false;
}

function validate_pms(pms)
{
	if(isEmpty(pms))
	{
		alert("PMS account ID empty");
		return false;
	}
	
	if(isNaN(pms))
	{
		alert("PMS ID: " + pms + " is invalid");
		return false;
	}
	
	return true;
}

function validate_address(address)
{
	if(isEmpty(address))
	{
		alert("Address is empty");
		return false;
	}
	
	return true;
}

function validate_city(city)
{
	if(isEmpty(city))
	{
		alert("City is empty");
		return false;
	}
	
	if(!isNaN(city))
	{
		alert("City: " + city + " is invalid");
		return false;
	}
		
	return true;
}

function validate_country(country)
{
	if(isEmpty(country))
	{
		alert("Please select country");
		return false;
	}
	return true;
}

function validate_cname(companyName)
{
	if(isEmpty(companyName))
	{
		alert("Company name is empty");
		return false;
	}
	
	return true;
}

function validate_fname(name)
{
	if(isEmpty(name))
	{
		alert("Full name is empty");
		return false;
	}
	
	if(!/^[A-Za-z\s]+$/.test(name))
	{
		alert("Invalid full name");
		return false;
	}
	
	return true;
}

function validate_lname(name)
{
	if(isEmpty(name))
	{
		alert("Last name is empty");
		return false;
	}
	
	if(!isNaN(name))
	{
		alert("Invalid last name");
		return false;
	}
	return true;
}

function validate_state(state)
{
	if(isEmpty(state))
	{
		alert("State is empty");
		return false;
	}
	
	if(!isNaN(state))
	{
		alert("State: " + state + " is invalid");
		return false;
	}
	return true;
}

function validate_email(email)
{
	if(isEmpty(email))
	{
		alert("Email empty");
		return false;
	}
	var atpos = email.indexOf("@");
    var dotpos = email.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length) {
        alert("email: " + email + " is not a valid e-mail address");
        return false;
    }
	return true;
}

function validate_telCCode(telCCode)
{
	if(isEmpty(telCCode))
	{
		alert("Telephone code empty");
		return false;
	}
	
	if(isNaN(telCCode))
	{
		alert("Telephone Code: " + telCCode + " is invalid");
		return false;
	}
	return true;
}

function validate_telMainPart(num)
{
	if(isEmpty(num))
	{
		alert("Telephone number empty");
		return false;
	}
	
	if(isNaN(num))
	{
		alert("Telephone no: " + num + " is invalid");
		return false;
	}
	return true;
}

function validate_zip(zip)
{
	if(isEmpty(zip))
	{
		alert("Zip empty");
		return false;
	}
	
	if(isNaN(zip))
	{
		alert("Zip: " + zip + " is invalid");
		return false;
	}
	return true;
}

function toggle(div_id) {
	var el = document.getElementById(div_id);
	if ( el.style.display == 'none' ) {	el.style.display = 'block';}
	else {el.style.display = 'none';}
}

function blanket_size(popUpDivVar) {
	if (typeof window.innerWidth != 'undefined') {
		viewportheight = window.innerHeight;
	} else {
		viewportheight = document.documentElement.clientHeight;
	}
	if ((viewportheight > document.body.parentNode.scrollHeight) && (viewportheight > document.body.parentNode.clientHeight)) {
		blanket_height = viewportheight;
	} else {
		if (document.body.parentNode.clientHeight > document.body.parentNode.scrollHeight) {
			blanket_height = document.body.parentNode.clientHeight;
		} else {
			blanket_height = document.body.parentNode.scrollHeight;
		}
	}
	var blanket = document.getElementById('blanket');
	blanket.style.height = blanket_height + 'px';
	var popUpDiv = document.getElementById(popUpDivVar);
	popUpDiv_height=blanket_height/2-200;//200 is half popup's height
	popUpDiv.style.top = popUpDiv_height + 'px';
}
function window_pos(popUpDivVar) {
	if (typeof window.innerWidth != 'undefined') {
		viewportwidth = window.innerHeight;
	} else {
		viewportwidth = document.documentElement.clientHeight;
	}
	if ((viewportwidth > document.body.parentNode.scrollWidth) && (viewportwidth > document.body.parentNode.clientWidth)) {
		window_width = viewportwidth;
	} else {
		if (document.body.parentNode.clientWidth > document.body.parentNode.scrollWidth) {
			window_width = document.body.parentNode.clientWidth;
		} else {
			window_width = document.body.parentNode.scrollWidth;
		}
	}
	var popUpDiv = document.getElementById(popUpDivVar);
	window_width=window_width/2-200;//200 is half popup's width
	popUpDiv.style.left = window_width + 'px';
}

function popup() {
	blanket_size('popUpDiv');
	window_pos('popUpDiv');
	toggle('blanket');
	toggle('popUpDiv');
}