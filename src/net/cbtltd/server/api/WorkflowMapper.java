/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdState;
import net.cbtltd.shared.Process;
import net.cbtltd.shared.Workflow;
import net.cbtltd.shared.workflow.WorkflowTable;

public interface WorkflowMapper {

	void create(Workflow action);
	Workflow exists(Workflow action);
	
	ArrayList<Workflow> read(String id);
	void update(Workflow action);
	void copy(NameId nameid); //name = from, id = to organizationid
	void delete(String id);
	
	Integer reservationcount(WorkflowTable action);
	Integer taskcount(WorkflowTable action);
	ArrayList<Process> worklist(WorkflowTable action);
	ArrayList<NameId> nameidstate(NameIdState action);
	
	Double progressoverduereservation(Workflow action);
	Double progresstotalreservation(Workflow action);
	Double progressoverduetask(Workflow action);
	Double progresstotaltask(Workflow action);
}
