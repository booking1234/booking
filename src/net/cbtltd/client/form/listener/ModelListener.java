package net.cbtltd.client.form.listener;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface ModelListener 
extends IsSerializable {
	void onModelRefresh();
	void onModelReset();
}