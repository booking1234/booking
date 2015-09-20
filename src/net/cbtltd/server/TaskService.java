/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server;

import java.util.ArrayList;

import net.cbtltd.server.api.ContactMapper;
import net.cbtltd.server.api.IsService;
import net.cbtltd.server.api.TaskMapper;
import net.cbtltd.shared.Data;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.Relation;
import net.cbtltd.shared.Serial;
import net.cbtltd.shared.Table;
import net.cbtltd.shared.Task;
import net.cbtltd.shared.task.Contact;
import net.cbtltd.shared.task.ContactCreate;
import net.cbtltd.shared.task.ContactRead;
import net.cbtltd.shared.task.ContactTable;
import net.cbtltd.shared.task.ContactUpdate;
import net.cbtltd.shared.task.MaintenanceTaskTable;
import net.cbtltd.shared.task.TaskCreate;
import net.cbtltd.shared.task.TaskDelete;
import net.cbtltd.shared.task.TaskRead;
import net.cbtltd.shared.task.TaskTable;
import net.cbtltd.shared.task.TaskUpdate;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/** The Class TaskService responds to task (marketing, maintenance, service and other activities) requests. */
public class TaskService
implements IsService {

	private static final Logger LOG = Logger.getLogger(TaskService.class.getName());
	private static TaskService service;

	/**
	 * Gets the single instance of TaskService to manage Product instances.
	 * @see net.cbtltd.shared.Task
	 *
	 * @return single instance of TaskService.
	 */
	public static synchronized TaskService getInstance() {
		if (service == null){service = new TaskService();}
		return service;
	}

	/**
	 * Executes the TaskCreate action to create a Task instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Task execute(SqlSession sqlSession, TaskCreate action) {
		try {
			action.setName(SessionService.pop(sqlSession, action.getOrganizationid(), Serial.TASK));
			action.setState(Task.INITIAL);
			sqlSession.getMapper(TaskMapper.class).create(action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the TaskRead action to read a Task instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Task execute(SqlSession sqlSession, TaskRead action) {
		Task task = null;
		try {
			task = sqlSession.getMapper(TaskMapper.class).read(action.getId());
			task.setResources(RelationService.read(sqlSession, Relation.TASK_RESOURCE, action.getId(), null));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return task;
	}

	/**
	 * Executes the Task action to update a Task instance only.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Task execute(SqlSession sqlSession, Task action) {
		try {sqlSession.getMapper(TaskMapper.class).update(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the TaskUpdate action to update a Task instance and its resources and text.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Task execute(SqlSession sqlSession, TaskUpdate action) {
		try {
			sqlSession.getMapper(TaskMapper.class).update(action);
			RelationService.replace(sqlSession, Relation.TASK_RESOURCE, action.getId(), action.getResources());
			TextService.update(sqlSession, action.getTexts());
			MonitorService.update(sqlSession, Data.Origin.CONSOLE, NameId.Type.Task, action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}

	/**
	 * Executes the TaskDelete action to delete a Task instance.
	 * This sets its state to final but does not delete the instance.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Task execute(SqlSession sqlSession, TaskDelete action) {
		try {
			action.setState(Task.FINAL);
			sqlSession.getMapper(TaskMapper.class).update(action);
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return null;
	}

	/**
	 * Gets a list of child tasks for the specified parent task ID.
	 *
	 * @param sqlSession the current SQL session.
	 * @param parentid the ID of the parent task.
	 * @return the list of child tasks.
	 */
	public static final ArrayList<Task> readbyparentid(SqlSession sqlSession, String parentid) {
		ArrayList<Task> tasks = null;
		try {tasks = sqlSession.getMapper(TaskMapper.class).readbyparentid(parentid);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return tasks;
	}
	
	/**
	 * Update a list of child tasks for the specified parent task ID.
	 *
	 * @param sqlSession the current SQL session.
	 * @param parentid the ID of the parent task.
	 * @param tasks the list of child tasks.
	 */
	public static final void update(SqlSession sqlSession, String parentid, ArrayList<Task> tasks) {
		try {
			if (tasks == null || tasks.isEmpty()) {return;}
			sqlSession.getMapper(TaskMapper.class).deletebyparentid(parentid);
			for (Task task : tasks) {sqlSession.getMapper(TaskMapper.class).create(task);}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
	}
	
	/**
	 * Executes the MaintenanceTaskTable action to read a table of maintenance Task instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Task> execute(SqlSession sqlSession, MaintenanceTaskTable action) {
		Table<Task> table = new Table<Task>();
		try {
			table.setDatasize(sqlSession.getMapper(TaskMapper.class).maintenancetaskcount(action));
			table.setValue(sqlSession.getMapper(TaskMapper.class).maintenancetasklist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the NameIdAction action to read a list of task NameId instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<NameId> execute(SqlSession sqlSession, NameIdAction action) {
		Table<NameId> table = new Table<NameId>();
		try {
			if (action.isSuggested()) {table.setValue(sqlSession.getMapper(TaskMapper.class).nameidbyid(action));}
			else {table.setValue(sqlSession.getMapper(TaskMapper.class).nameidbyname(action));}
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}

	/**
	 * Executes the TaskTable action to read a list of task NameId instances for work flow.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Task> execute(SqlSession sqlSession, TaskTable action) {
		Table<Task> table = new Table<Task>();
		try {
			Integer count = sqlSession.getMapper(TaskMapper.class).workcount(action);
			table.setDatasize(count == null ? 0 : count);
			table.setValue(sqlSession.getMapper(TaskMapper.class).worklist(action));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
	
	/**
	 * Executes the ContactCreate action to create a Contact instance for a marketing task.
	 * @see net.cbtltd.shared.task.Contact

	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the contact
	 */
	public final Contact execute(SqlSession sqlSession, ContactCreate action) {
		try {sqlSession.getMapper(ContactMapper.class).create(action);}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}
	
	/**
	 * Executes the ContactRead action to read a Contact instance.
	 * @see net.cbtltd.shared.task.Contact
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the contact
	 */
	public final Contact execute(SqlSession sqlSession, ContactRead action) {
		Contact contact = null;
		try {
			contact = sqlSession.getMapper(ContactMapper.class).read(action.getId());
			contact.setAdressees(RelationService.read(sqlSession, Relation.CONTACT_PARTY, action.getId(), null));
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return contact;
	}
	
	/**
	 * Executes the ContactUpdate action to update a Contact instance.
	 * @see net.cbtltd.shared.task.Contact
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the contact
	 */
	public final Contact execute(SqlSession sqlSession, ContactUpdate action) {
		try {
			sqlSession.getMapper(ContactMapper.class).update(action);
			RelationService.replace(sqlSession, Relation.CONTACT_PARTY, action.getId(), action.getAdressees());
		}
		catch (Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return action;
	}
	
	/**
	 * Executes the ContactTable action to read a table of Contact instances.
	 *
	 * @param sqlSession the current SQL session.
	 * @param action the action to be executed.
	 * @return the response.
	 */
	public final Table<Contact> execute(SqlSession sqlSession, ContactTable action) {
		Table<Contact> table = new Table<Contact>();
		try {
			table.setDatasize(sqlSession.getMapper(ContactMapper.class).countcontacts(action));
			table.setValue(sqlSession.getMapper(ContactMapper.class).listcontacts(action));
		}
		catch(Throwable x) {sqlSession.rollback(); MonitorService.log(x);}
		return table;
	}
}
