package net.cbtltd.client.form.listener;
import java.util.EventListener;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface EnableListener extends EventListener, IsSerializable {
	void onEnableChange(String option);
	void setEnableKey(String key, boolean enabled);
}