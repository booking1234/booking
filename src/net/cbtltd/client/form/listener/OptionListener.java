package net.cbtltd.client.form.listener;

import java.util.EventListener;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface OptionListener extends EventListener, IsSerializable {
	void onOptionChange(String option);
	void setReadOption(String option, boolean visible);
	void setWriteOption(String option, boolean enabled);
}