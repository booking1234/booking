package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.List;

import net.cbtltd.shared.Fee;

public interface FeeMapper {
	
	void create (Fee action);
	Fee exists(Fee action);
	Fee read(int id);
	void cancelversion(Fee action);
	void cancelfeelist(List<Integer> feeIdList);
	ArrayList<Fee> readbyproductandstate(Fee action);
	ArrayList<Fee> readbydateandproduct(Fee action);
	ArrayList<Fee> readspecialbydateandproduct(Fee action);
}