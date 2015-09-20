package net.cbtltd.server.util;

import org.apache.ibatis.session.SqlSession;

import net.cbtltd.server.RazorServer;
import net.cbtltd.server.ReservationService;
import net.cbtltd.server.api.PropertyManagerInfoMapper;
import net.cbtltd.server.api.ReservationMapper;
import net.cbtltd.shared.PropertyManagerInfo;
import net.cbtltd.shared.Reservation;



public class CommissionTest {

	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		SqlSession session = RazorServer.openSession();
		
		Reservation reservation = session.getMapper(ReservationMapper.class).read("26376734");
		PropertyManagerInfo pmi = session.getMapper(PropertyManagerInfoMapper.class).readbypmid(Integer.valueOf(reservation.getOrganizationid()));
		
		reservation.setNightlyrate(857.121825);
		double depositPercentage = ReservationService.getDeposit(reservation, pmi);
		Double depositNightlyrate = reservation.getNightlyrate() * depositPercentage / 100;				
		reservation.setNightlyrate(depositNightlyrate);
		
		
		CommissionSeparatorUtil separatorUtil = new CommissionSeparatorUtil(session, reservation, 9.42, reservation.getNightlyrate());
		
		Double crediCardFee = separatorUtil.getCreditCardFeeValue();
		Double totalCommissionValue = separatorUtil.getTotalCommissionValue();
		Double bpPaymentAmount = separatorUtil.getBpCommissionValue();
		Double pmsPaymentAmount = separatorUtil.getPmsMarkupValue();
		Double pmPaymentAmount = separatorUtil.getPmCommissionValue(); 
		Double partnerPaymentAmount = separatorUtil.getChannelPartnerCommissionValue();
		
		System.out.println("CCFE: " + crediCardFee);
		System.out.println("Total Commission: " + totalCommissionValue);
		System.out.println("BP payment: " + bpPaymentAmount);
		System.out.println("PMS payment: " + pmsPaymentAmount);
		System.out.println("Pm payment: " + pmPaymentAmount);
		System.out.println("Partner payment: " + partnerPaymentAmount);
		
	}

}
