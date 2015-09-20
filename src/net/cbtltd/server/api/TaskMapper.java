/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.NameId;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.api.HasTableService;
import net.cbtltd.shared.task.Schedule;
import net.cbtltd.shared.task.ScheduleItem;
import net.cbtltd.shared.task.TaskTable;

public interface TaskMapper
extends AbstractMapper<Task> {
	
	ArrayList<NameId> collisions(Task task); //Collide
	ArrayList<Task> readbyparentid(String parentid);
	void deletebyparentid(String parentid);
	
	ArrayList<ScheduleItem> scheduleitems(Schedule available); //Available

	Integer maintenancetaskcount(HasTableService action);
	ArrayList<Task>  maintenancetasklist(HasTableService action);
	
	Integer workcount(TaskTable action);
	ArrayList<Task> worklist(TaskTable action);

}
