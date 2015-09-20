package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.json.minstay.WeeklyMinstay;
import net.cbtltd.shared.MinStay;
import net.cbtltd.shared.minstay.MinstayWeek;

public interface PropertyMinStayMapper {
	void create(MinStay action);
	MinStay read(MinStay action);
	MinStay readbyexample(MinStay action);
	void update(MinStay action);
	void delete(MinStay action);
	MinStay exists(MinStay action);
	List<WeeklyMinstay> getMinstayByWeeks(MinstayWeek action);
	void deleteversion(MinStay action);
}
