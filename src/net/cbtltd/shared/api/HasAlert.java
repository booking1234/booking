package net.cbtltd.shared.api;

import java.util.Date;

public interface HasAlert {
	String getEntitytype();
	String getEntityid();
	Date getFromdate();
	Date getTodate();
}
