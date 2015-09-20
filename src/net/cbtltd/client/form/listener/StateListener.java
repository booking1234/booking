package net.cbtltd.client.form.listener;

import java.util.EventListener;
import java.util.List;

import net.cbtltd.client.HasCommand;
import net.cbtltd.shared.Model;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface StateListener
extends EventListener, IsSerializable {
	void onStateChange(List<HasCommand> commands);
}