package net.cbtltd.client.panel;


import net.cbtltd.client.AbstractRoot;
import net.cbtltd.shared.Account;
import net.cbtltd.shared.Contract;
import net.cbtltd.shared.Event;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Session;
import net.cbtltd.shared.Value;
import net.cbtltd.shared.reservation.Brochure;

import com.google.gwt.user.client.Window;

public class Popups {

	private static final String SESSIONID = "1";
	private static final String PARTYID = "5";
	private static final String PRODUCTID = "144";
	public static final Integer SESSION = 27;

	public static void render(int index) {
		Session session = new Session();
		session.setId(SESSIONID);
		session.setOrganizationid(PARTYID);
		AbstractRoot.setSession(session);
		
		switch(index) {

		case 1: new AboutPopup().center(); return;
		case 2: AccountPopup.getInstance().show(Account.Type.Expense, null); return;
		case 3: ActorPopup.getInstance().show(null); return;
		case 4: AssetPopup.getInstance().show(null, null, null); return;
		case 5: AuditPopup.getInstance().show(PRODUCTID, null); return;
		case 6: BrochurePopup.getInstance().show(PRODUCTID, PARTYID,  Brochure.CREATED); return;
		case 7: ContactPopup.getInstance().show(NameId.Type.Party, null, null); return;
		case 8: ContractPopup.getInstance().show(Contract.Type.Reservation, null); return;
		case 9: DesignPopup.getInstance().show(null); return;
		case 10: FinancePopup.getInstance().show(null); return;
		case 11: JournalPopup.getInstance().show(Event.Type.Journal, null, null); return;
		case 111: JournalPopup.getInstance().show(Event.Type.Payment, null, null); return;
		case 112: JournalPopup.getInstance().show(Event.Type.Purchase, null, null); return;
		case 113: JournalPopup.getInstance().show(Event.Type.PurchaseSale, null, null); return;
		case 114: JournalPopup.getInstance().show(Event.Type.Receipt, null, null); return;
		case 115: JournalPopup.getInstance().show(Event.Type.ReservationSale, null, null); return;
		case 116: JournalPopup.getInstance().show(Event.Type.Sale, null, null); return;
//		case 12: JurisdictionPopup.getInstance().show(null); return;
		case 15: LocationPopup.getInstance().show(null); return;
		case 16: OfflinePopup.getInstance().show(null); return;
		case 17: PartyPopup.getInstance().show("5"); return;
		case 18: PricePopup.getInstance().show(null); return;
		case 19: FeaturePopup.getInstance().show(PRODUCTID, null); return;
		case 20: ProductPopup.getInstance().show(PRODUCTID); return;
		case 21: RatePopup.getInstance().show(null, null); return;
		//case 22: ReportPopup.getInstance().show(null); return;
		case 23: QuotePopup.getInstance().show(null, null, null); return;
		case 24: ReservationPopup.getInstance().show(null); return;
		case 25: new SearchPopup(null).center(); return;
		case 26: ServicepricePopup.getInstance().show(PRODUCTID, null); return;
		case 27: new SessionPopup().show(true); return;
		case 28: TaskPopup.getInstance().show(null); return;
		case 29: TaxPopup.getInstance().show(NameId.Type.Party.name(), PARTYID, null); return;
		case 30: ValuePopup.getInstance().show(Value.Type.ProductFeature, null, null, null); return;
		case 31: YieldPopup.getInstance().show(PRODUCTID, null); return;
		case 32: AutoPopup.getInstance().show(); return;
		}
		Window.alert("Unknown class " + index + " - should not happen!");
	}

}
