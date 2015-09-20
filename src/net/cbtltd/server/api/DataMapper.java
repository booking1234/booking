/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;
import java.util.Date;

import net.cbtltd.shared.Data;

public interface DataMapper
extends AbstractMapper<Data> {
	void delete(String id);
	Date last(Data action);
	Integer count(Data action);
	ArrayList<Data> list(Data action);
}