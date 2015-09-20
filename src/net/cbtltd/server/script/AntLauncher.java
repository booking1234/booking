package net.cbtltd.server.script;

import net.cbtltd.server.LicenseService;
import net.cbtltd.server.job.PendingPayment;
import net.cbtltd.server.job.PropertyManagerPayment;

import net.cbtltd.server.util.UtilsHandler;

public class AntLauncher {
	public static void main(String[] args) {
		
		String[] taskArguments = args[0].split(" ");
		if(taskArguments[0].equals("pending")) {
			PendingPayment.execute();
		} else if (taskArguments[0].equals("pm_payment")) {
			PropertyManagerPayment.execute();
		} else if (taskArguments[0].equals("balance")) {
			LicenseService.balance();
		}
		if (taskArguments[0].equals("utils")) {
			//put jobs here if needed more
			String taskJob = taskArguments[1];
			String partyID = taskArguments[2];
			if (taskJob.equals("cleanImageTable")) {
				System.out.println("Starting utility cleanImageTable for party " + partyID);
				UtilsHandler.cleanImageTable(partyID);
			}
		}
	}
}
