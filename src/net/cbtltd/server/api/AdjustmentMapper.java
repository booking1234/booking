package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.Adjustment;
import net.cbtltd.shared.api.HasPrice;

public interface AdjustmentMapper {

	void create(Adjustment action);
	void update(Adjustment action);
	Adjustment exists(Adjustment action);
	Adjustment read(int id);
	Adjustment readbyexample(Adjustment action);
	Adjustment readbyreservation(HasPrice action);
	void cancelversion(Adjustment action);
	void canceladjustmentlist(List<String> ajustmentIdList);
	Double getfixprice(Adjustment action);
	ArrayList<Adjustment> readbyproductandstate(Adjustment action);
}
