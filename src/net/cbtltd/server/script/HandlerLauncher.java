package net.cbtltd.server.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.cbtltd.rest.nextpax.A_Handler;
import net.cbtltd.server.RazorServer;
import net.cbtltd.server.api.PartnerMapper;
import net.cbtltd.shared.api.HasUrls;

import com.google.gwt.dev.util.collect.HashMap;
import org.apache.log4j.Logger;

public class HandlerLauncher {
	private static Map<String, Handleable> launchers = new HashMap<String, Handleable>();
	private static final Logger LOG = Logger.getLogger(A_Handler.class.getName());
	static {
		launchers.put("ih", new net.cbtltd.rest.interhome._Test());
		launchers.put("sc", new net.cbtltd.rest.summitcove._Test());
		launchers.put("rtr", new net.cbtltd.rest.rtr._Test());
		launchers.put("kigo", new net.cbtltd.rest.kigo._Test());
		launchers.put("bookt", new net.cbtltd.rest.bookt._Test());
		launchers.put("np", new net.cbtltd.rest.nextpax._Test());
		launchers.put("ciirus", new net.cbtltd.rest.ciirus._Test());
		launchers.put("maxxton", new net.cbtltd.rest.maxxton._Test());
		launchers.put("laketahoeaccommodations", new net.cbtltd.rest.laketahoeaccommodations._Test());
		launchers.put("aaxsys", new net.cbtltd.rest.aaxsys._Test());
		launchers.put("ru", new net.cbtltd.rest.ru._Test());
		launchers.put("rms", new net.cbtltd.rest.rms._Test());
		launchers.put("pathway", new net.cbtltd.rest.pathway._Test());
		launchers.put("webchalet", new net.cbtltd.rest.webchalet._Test());
		launchers.put("streamline", new net.cbtltd.rest.streamline._Test());
		launchers.put("lodgix", new net.cbtltd.rest.lodgix._Test()); 
		launchers.put("leisurelink", new net.cbtltd.rest.leisurelink._Test());
		launchers.put("barefoot", new net.cbtltd.rest.barefoot._Test());
		launchers.put("locations", new net.cbtltd.rest.GoogleLocationProcessor());
	}
	
	// Passing parameters: args[0]={ih,bookt,ru} args[1]={readProducts:readPrices}
	// Will execute readProducts and readPrices for Iterhome, Bookt and Rentals United
	public static void main(String[] args) {
		LOG.debug("args length:"+args.length+" :");
		for(String a : args){
			LOG.debug(a);
		}
		
		String[] pmses = args[0].split("[,:;]");
		String[] methods = args[1].split("[,:;]");
		
		Pattern pattern = Pattern.compile("\\$\\{.*\\}");
		for (int i = 0; i < args.length; i++) {
			Matcher matcher = pattern.matcher(args[i]);
			if (matcher.matches()) {
				args[i] = null;
			}
		}
		
		List<Handleable> pmsesToExecute = new ArrayList<Handleable>();
		for(String launcher : pmses) {
			if(launcher.equals("np")) {
				launchers.remove(launcher);
				// retrieve the nextpax partners from the third argument
				if(args[2] != null) {
					String[] partyids = args[2].split("[,:;]");
					for(String partyid:partyids) {
						pmsesToExecute.add(new net.cbtltd.rest.nextpax._Test(partyid));
					}
				}
			}else if(launcher.equals("ciirus")) {
				launchers.remove(launcher);
				// retrieve the ciirus partners from the third argument
				if(args.length>2 && args[2] != null) {
					String[] partyids = args[2].split("[,:;]");
					for(String partyid:partyids) {
						pmsesToExecute.add(new net.cbtltd.rest.ciirus._Test(partyid));
					}
				}else{
					//when partyId missing run for all PM (but not for main account)
					List<String> partnerPartyIdList = RazorServer.openSession().getMapper(PartnerMapper.class).getAllChildPartnerPartyIDByAbbreviation(HasUrls.ABBREVATION_CIIRUS);
					for(String partyId : partnerPartyIdList){
						pmsesToExecute.add(new net.cbtltd.rest.ciirus._Test(partyId));
					}
				}
			}else if(launcher.equals("streamline")) {
				launchers.remove(launcher);
				// retrieve the streamline partners from the third argument
				if(args.length>2 && args[2] != null) {
					String[] partyids = args[2].split("[,:;]");
					for(String partyid:partyids) {
						pmsesToExecute.add(new net.cbtltd.rest.streamline._Test(partyid));
					}
				}else{
					//when partyId missing run for all PM (but not for main account)
					List<String> partnerPartyIdList = RazorServer.openSession().getMapper(PartnerMapper.class).getAllChildPartnerPartyIDByAbbreviation(HasUrls.ABBREVATION_STREAMLINE);
					for(String partyId : partnerPartyIdList){
						pmsesToExecute.add(new net.cbtltd.rest.streamline._Test(partyId));
					}
				}
			}else if(launcher.equals("pathway")) {
				launchers.remove(launcher);
				// retrieve the pathway partners from the third argument
				if(args.length>2 && args[2] != null) {
					String[] partyids = args[2].split("[,:;]");
					for(String partyid:partyids) {
						pmsesToExecute.add(new net.cbtltd.rest.pathway._Test(partyid));
					}
				}else{
					//when partyId missing run for all PM (but not for main account)
					List<String> partnerPartyIdList = RazorServer.openSession().getMapper(PartnerMapper.class).getAllChildPartnerPartyIDByAbbreviation(HasUrls.ABBREVATION_PATHWAY_GDS_MAIN);
					for(String partyId : partnerPartyIdList){
						pmsesToExecute.add(new net.cbtltd.rest.pathway._Test(partyId));
					}
				}
			}else if(launcher.equals("lodgix")) {
				launchers.remove(launcher);
				// retrieve the pathway partners from the third argument
				if(args.length>2 && args[2] != null) {
					String[] partyids = args[2].split("[,:;]");
					for(String partyid:partyids) {
						pmsesToExecute.add(new net.cbtltd.rest.lodgix._Test(partyid));
					}
				}else{
					//when partyId missing run for all PM (but not for main account)
					List<String> partnerPartyIdList = RazorServer.openSession().getMapper(PartnerMapper.class).getAllChildPartnerPartyIDByAbbreviation(HasUrls.ABBREVATION_LODGIX);
					for(String partyId : partnerPartyIdList){
						pmsesToExecute.add(new net.cbtltd.rest.lodgix._Test(partyId));
					}
			}
			}else if(launcher.equals("aaxsys")) {
				launchers.remove(launcher);
				// retrieve the aaxsys partners from the third argument
				if(args.length>2 && args[2] != null) {
					String[] partyids = args[2].split("[,:;]");
					for(String partyid:partyids) {
						pmsesToExecute.add(new net.cbtltd.rest.aaxsys._Test(partyid));
					}
				}else{
					//when partyId missing run for all PM (but not for main account)
					List<String> partnerPartyIdList = RazorServer.openSession().getMapper(PartnerMapper.class).getAllChildPartnerPartyIDByAbbreviation(HasUrls.ABBREVATION_AAXSYS);
					for(String partyId : partnerPartyIdList){
						pmsesToExecute.add(new net.cbtltd.rest.aaxsys._Test(partyId));
					}
				}
			} else if (launcher.equals("ru")) {
				launchers.remove(launcher);
				// retrieve the Rentals United owner\owners from the third argument
				if(args[2] != null) {
					String[] partyids = args[2].split("[,:;]");
					for(String partyid:partyids) {
						pmsesToExecute.add(new net.cbtltd.rest.ru._Test(partyid));
					}
				} // if there are no arguments, just skip. Cannot update without owner.
			}else if(launcher.equals("barefoot")) {
				launchers.remove(launcher);
				// retrieve the barefoot partners from the third argument
				if(args.length>2 && args[2] != null) {
					String[] partyids = args[2].split("[,:;]");
					for(String partyid:partyids) {
						pmsesToExecute.add(new net.cbtltd.rest.barefoot._Test(partyid));
					}
				}else{
					//when partyId missing run for all PM (but not for main account)
					List<String> partnerPartyIdList = RazorServer.openSession().getMapper(PartnerMapper.class).getAllChildPartnerPartyIDByAbbreviation(HasUrls.ABBREVATION_BAREFOOT);
					for(String partyId : partnerPartyIdList){
						pmsesToExecute.add(new net.cbtltd.rest.barefoot._Test(partyId));
					}
				}
			}
			
			else {
				pmsesToExecute.add(launchers.get(launcher));
			}
		}
		
		for(Handleable launcher : pmsesToExecute) {
			
			for(String method : methods) {
				if(method.equalsIgnoreCase("readProducts")) { launcher.readProducts(); }
				else if(method.equalsIgnoreCase("readPrices")) { launcher.readPrices(); }
				else if(method.equalsIgnoreCase("readSchedule")) { launcher.readSchedules(); }
				else if(method.equalsIgnoreCase("readAlerts")) { launcher.readAlerts();	}
				else if(method.equalsIgnoreCase("readSpecials")) { launcher.readSpecials();	}
				else if(method.equalsIgnoreCase("readLocations")) { launcher.readLocations(); }
				else if(method.equalsIgnoreCase("readDescriptions")) { launcher.readDescriptions(); }
				else if(method.equalsIgnoreCase("readImages")) { launcher.readImages(); }
			}
		}
	}
}