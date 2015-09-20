package net.cbtltd.rest.jetpay;

import net.cbtltd.shared.api.IsGateway;
import net.cbtltd.shared.finance.GatewayAction;

import com.jetpay.sax.JetPay;

public class JetpayHandler implements IsGateway {

	public String getAltpartyid() {
		return "Altpartyid"; //TODO:
	}
	
	public void createPayment(GatewayAction action) {
		
	}

	public GatewayAction getPayment(GatewayAction action) {
		return null;
	}

	public void createRefund(GatewayAction action) {
		
	}

	public void test() {
		JetPay jetPay = new JetPay();

		jetPay.Transtype = "SALE";

		jetPay.TerminalID = "TESTTERMINAL";

		jetPay.IndustryType = "RETAIL";

		jetPay.Origin = "POS";

		jetPay.TransactionID = jetPay.GenerateTransactionID();

		jetPay.CardNum = "4000300020001000";

		jetPay.CardExpMonth = "07";

		jetPay.CardExpYear = "13";

		jetPay.TotalAmount = 500;

		jetPay.ExecuteTransaction();



		if (jetPay.ErrMsg != null) {

			System.out.println(jetPay.ErrMsg);

		}

		System.out.println("Transaction ID: " + jetPay.TransactionID);

		System.out.println("Action Code: " + jetPay.ActionCode);

		System.out.println("Approval Code: " + jetPay.Approval);

		System.out.println("Response: " + jetPay.ResponseText);



		// Clean up the object for reuse

		jetPay = new JetPay();



		/** Test AUTHONLY */

		System.out.println("------Test AUTHONLY transaction-----");

		jetPay.Transtype = "AUTHONLY";

		jetPay.TerminalID = "TESTTERMINAL";

		jetPay.IndustryType = "RETAIL";

		jetPay.Origin = "POS";

		jetPay.TransactionID = jetPay.GenerateTransactionID();

		jetPay.CardNum = "4000300020001000";

		jetPay.CardExpMonth = "07";

		jetPay.CardExpYear = "13";

		jetPay.TotalAmount = 600;

		jetPay.CVV2 = "121";

		jetPay.BillingAddress = "123 Anystreet";

		jetPay.BillingCity = "Anycity";

		jetPay.BillingStateProv = "TX";

		jetPay.BillingPostalCode = "12345";

		jetPay.ExecuteTransaction();



		if (jetPay.ErrMsg != null) {

			System.out.println(jetPay.ErrMsg);

		}

		System.out.println("Transaction ID: " + jetPay.TransactionID);

		System.out.println("Action Code: " + jetPay.ActionCode);

		System.out.println("Approval Code: " + jetPay.Approval);

		System.out.println("Response: " + jetPay.ResponseText);

		System.out.println("CVV2 Response: " + jetPay.CVV2Response);

		System.out.println("AVS Response: " + jetPay.AVS);



		// Store the approval code

		String strApprovalCodeFromTheAuthOnly = jetPay.Approval;



		// Clean up the object for reuse

		jetPay = new JetPay();



		/** Test CAPT */

		System.out.println("------Test CAPT transaction-----");

		jetPay.Transtype = "CAPT";

		jetPay.TerminalID = "TESTTERMINAL";

		jetPay.Approval = strApprovalCodeFromTheAuthOnly;  // Approval code from the AUTHONLY transaction

		jetPay.TransactionID = jetPay.GenerateTransactionID();

		jetPay.CardNum = "4000300020001000";

		jetPay.CardExpMonth = "07";

		jetPay.CardExpYear = "13";

		jetPay.TotalAmount = 600;

		jetPay.ExecuteTransaction();



		if (jetPay.ErrMsg != null) {

			System.out.println(jetPay.ErrMsg);

		}

		System.out.println("Transaction ID: " + jetPay.TransactionID);

		System.out.println("Action Code: " + jetPay.ActionCode);

		System.out.println("Approval Code: " + jetPay.Approval);

		System.out.println("Response: " + jetPay.ResponseText);



		// Store the transaction ID of the CAPT

		String strOriginalTransactionID = jetPay.TransactionID;



		jetPay = new JetPay();



		/** Test VOID */

		System.out.println("------Test VOID transaction-----");

		jetPay.Transtype = "VOID";

		jetPay.TerminalID = "TESTTERMINAL";

		jetPay.Approval = strApprovalCodeFromTheAuthOnly;  // Approval code from the AUTHONLY transaction

		jetPay.TransactionID = strOriginalTransactionID; // Transaction ID of the CAPT transaction

		jetPay.CardNum = "4000300020001000";

		jetPay.CardExpMonth = "07";

		jetPay.CardExpYear = "13";

		jetPay.TotalAmount = 600;

		jetPay.ExecuteTransaction();



		if (jetPay.ErrMsg != null) {

			System.out.println(jetPay.ErrMsg);

		}

		System.out.println("Transaction ID: " + jetPay.TransactionID);

		System.out.println("Action Code: " + jetPay.ActionCode);

		System.out.println("Approval Code: " + jetPay.Approval);

		System.out.println("Response: " + jetPay.ResponseText);



		jetPay = new JetPay();



		/** Test CREDIT */

		System.out.println("------Test CREDIT transaction-----");

		jetPay.Transtype = "CREDIT";

		jetPay.TerminalID = "TESTTERMINAL";

		jetPay.TransactionID = jetPay.GenerateTransactionID();

		jetPay.CardNum = "4000300020001000";

		jetPay.CardExpMonth = "07";

		jetPay.CardExpYear = "13";

		jetPay.TotalAmount = 1000;

		jetPay.ExecuteTransaction();



		if (jetPay.ErrMsg != null) {

			System.out.println(jetPay.ErrMsg);

		}

		System.out.println("Transaction ID: " + jetPay.TransactionID);

		System.out.println("Action Code: " + jetPay.ActionCode);

		System.out.println("Approval Code: " + jetPay.Approval);

		System.out.println("Response: " + jetPay.ResponseText);



		jetPay = new JetPay();



		/** Test CHECK */

		System.out.println("------Test CHECK transaction-----");

		jetPay.Transtype = "CHECK";

		jetPay.TerminalID = "TESTTERMINAL";

		jetPay.IndustryType = "RETAIL";

		jetPay.Origin = "POS";

		jetPay.TransactionID = jetPay.GenerateTransactionID();

		jetPay.ABANumber = "122000496";

		jetPay.CheckNumber = "1001";

		jetPay.CheckingAccountType = "CHECKING";

		jetPay.CheckingAccountNumber = "1234567890";

		jetPay.CardName = "TEST NAME";

//TODO:		jetPay.SECType = "PPD";

		jetPay.TotalAmount = 500;

		jetPay.ExecuteTransaction();



		if (jetPay.ErrMsg != null) {

			System.out.println(jetPay.ErrMsg);

		}

		System.out.println("Transaction ID: " + jetPay.TransactionID);

		System.out.println("Action Code: " + jetPay.ActionCode);

		System.out.println("Approval Code: " + jetPay.Approval);

		System.out.println("Response: " + jetPay.ResponseText);
	}
}
