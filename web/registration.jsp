<%@ page import="net.cbtltd.shared.Model, net.cbtltd.server.RazorServer, net.cbtltd.shared.Partner, 
	net.cbtltd.server.api.PartnerMapper, org.apache.ibatis.session.SqlSession,
	net.cbtltd.server.api.CountryCodeMapper, java.util.List" %>
<!DOCTYPE html>
<html>
	<%!
		private SqlSession sqlSession = null;
	%>
	<%!
		public void jspInit(){
			
			sqlSession = RazorServer.openSession();
		}

		public void jspDestroy(){
			sqlSession.close();
		}
	%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Registration</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='http://fonts.googleapis.com/css?family=Nunito:400,300,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" type="text/css" href="./Registration/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="./Registration/registration.css"/>
</head>
<body class="registration">
	<%
		String cname = "";
		String clogo = "";
		if(request.getAttribute("result") == null)
		{
			String pos = request.getParameter("pos");
			String partyId = Model.decrypt(pos);
			partyId = (partyId == null) ? "" : partyId;
			
			Partner partner =  sqlSession.getMapper(PartnerMapper.class).exists(partyId);
			
			if(partner != null && partner.getPartyname() != null)
			{
				cname = partner.getPartyname().split("[ ]+")[0];
				
				if(cname.length() > 1)
					cname = cname.substring(0,1).toUpperCase() + cname.substring(1);
			}
		}
		
	%>
<div class="right_bg"></div>
<header class="header">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
               <h3>Automated Listing Distribution</h3>
                <img src="./Registration/logo.png" class="header_brand">
               <h4>Provided by:</h4>

            </div>
        </div>
    </div>
</header>
<div class="content">
<div class="container">

    <div class="row">

        <div class="col-lg-8 col-md-7 col-sm-7 left_content">
			<%
				if(request.getAttribute("result") == null)
				{
					String compname = request.getParameter("compname") == null ? "" : request.getParameter("compname");
					String accid = request.getParameter("accid") == null ? "" : request.getParameter("accid");
					String fullname = request.getParameter("fullname") == null ? "" : request.getParameter("fullname");
					String country = request.getParameter("country") == null ? "" : request.getParameter("country");
					String addr = request.getParameter("addr") == null ? "" : request.getParameter("addr");
					String city = request.getParameter("city") == null ? "" : request.getParameter("city");
					String state = request.getParameter("state") == null ? "" : request.getParameter("state");
					String zip = request.getParameter("zip") == null ? "" : request.getParameter("zip");
					String telno = request.getParameter("telno") == null ? "" : request.getParameter("telno");
					String email = request.getParameter("email") == null ? "" : request.getParameter("email");
			%>
            <form name="reg1Form" role="form" class="reg_form" action="registration" onsubmit="return validate();" method="post">
                <div class="col-lg-6">
                    <div class="form-group">
                        <label for="acc_ID"><%= cname %> account id:</label>
                        <input required type="text" class="form-control" name="accid" id="acc_ID" value="<%= accid%>">
                    </div>
                    <div class="form-group">
					<div class="form-group">
                        <label for="Company_name">Company name:</label>
                        <input required type="text" class="form-control" name="compname" id="Company_name" value="<%= compname%>">
                    </div>
                        <label for="Full_name">Full name:</label>
                        <input required type="text" class="form-control" name="fullname" id="Full_name" value="<%= fullname %>">
                    </div>
                    <div class="form-group">
                        <label for="E_mail">E-mail:</label>
                        <input required type="email" class="form-control" name="email" id="E_mail" value="<%= email %>">
                    </div>
                    <div class="form-group">
                        <label for="Telephone">Telephone:</label>
                        <input required type="text" class="form-control" name="telno" id="Telephone" value="<%= telno %>">
                    </div>
                </div>
                <div class="col-lg-6">
                    <div class="form-group">
                        <label for="Adress">Address:</label>
                        <input required type="text" class="form-control" name="addr" id="Adress" value="<%= addr %>">
                    </div>
                    <div class="form-group">
                        <label for="City">City:</label>
                        <input required type="text" class="form-control" name="city" id="City" value="<%= city %>">
                    </div>
                    <div class="form-group">
                        <label for="state">State/Province:</label>
                        <input required type="text" class="form-control" name="state" id="state" value="<%= state %>">
                    </div>
					<div class="form-group">
						<label for="country">Country:</label>
						<select required class="form-control" name="country" id="country" type="text">
							<option value="" class="">Select One</option>
								<% 
									List<String> countries = sqlSession.getMapper(CountryCodeMapper.class).getAllCountries();
									for(String c : countries)
									{
								%>
								<option value="<%= c %>" <% if(country.equalsIgnoreCase(c)) { %> selected <% } %>><%= c %></option>
								<% 
									}
								%>
							</select>
					</div>
					<div class="form-group">

                        <label for="Zip_postal_code">Zip/Postal code:</label>
                        <input required type="text" class="form-control" name="zip" id="Zip_postal_code" value="<%= zip %>">
                    </div>
                    <div class="form-checkbox">
                        <label>
                            <input required type="checkbox" name="privacyPolicyAgreeCheckbox">  I have read and agree to the terms of the <a onclick="popup()">
                            BookingPal Privacy Policy
                            </a>
						</label>
                    </div>

                    <button type="submit" id="submit" class="btn btn-default reg_button" >Register</button>
                </div>
				
				<input type="text" id="cname" name="cname" style="display:none" value=<%= cname%>>

            </form>
			
			<% 
				} 
				else
				{
					cname = request.getParameter("cname");
					
					
					if(request.getAttribute("result").equals("success"))
					{
			%>
			
			<div id="registration_result" class="registration_result" style="color:green">
					<b>
						Registration success
					<%
					}
					else
					{
					%>
			<div id="registration_result" class="registration_result" style="color:red">
					<b>
						"Registration failed"
					<%
					}
					%>
					</b>
				<div class="contentTextBlue">
					In case of queries, please contact BookingPal at +1949-333-0724
				</div>
			</div>
			
			<% 
				}
				clogo = cname.toLowerCase() + "_logo.png";

			%>
        </div>
        <div class="col-lg-4 col-md-5 col-sm-5 right_content">
            <div class="reg_info">
                <div class="reg_info_heading"><%= cname %> and BookingPal have partnered to make listings distribution a snap.
                    Manage your listings on dozens of major travel websites and apps through one simple interface.
                </div>

                <div class="reg_info_advantages">
                    <div class="reg_info_advantage">
                        <div class="icon"></div>Connect to an extensive network of channel partners
                    </div>
                    <div class="reg_info_advantage">
                        <div class="icon"></div>Reach millions of travelers per month
                    </div>
                    <div class="reg_info_advantage">
                        <div class="icon"></div>Only pay for Bookings. Noup front fees!
                    </div>
                    <div class="reg_info_advantage">
                        <div class="icon"></div>Save time with only one account to manage and our universal contract
                    </div>
                    <div class="reg_info_logo">
                    <h4>Fully compatible with:</h4>
                    <img src="./Registration/<%= clogo %>" class="reg_info_brand" alt="<%= cname %>">
                    </div>
                </div>



            </div>
        </div>
    </div>

</div>
</div>


<div id="blanket" style="display:none;"></div>
<div id="popUpDiv" style="display:none;">
	<div id="privacyPolicy">
		<div id="privacyHeader">
			<div class="privacyHeaderLogo">
				<img src="./Registration/logo.png">
			</div>
			<div class="privacyHeaderExit">
				<a href="#" id="closeLink" onclick="popup()" ><img src="./Registration/exit.png"></a>
			</div>
		</div>
		
		<object id="privacyObject" name="privacyObject" type="text/html" data="https://www.mybookingpal.com/privacy-policy/html"></object>
		
	</div>
	
</div>

<script src="./Registration/validate.js" type="text/javascript"></script>
</body>
</html>