package net.cbtltd.rest.logging.interceptors;

import net.cbtltd.rest.logging.ServiceTimer;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class TimerInInterceptor extends AbstractPhaseInterceptor<Message> {

	// see CXF doc on what the phases are to gauge what time you want to
	// capture.
	public TimerInInterceptor() {
		super(Phase.RECEIVE);
	}

	public void handleMessage(final Message message) throws Fault {
		ServiceTimer.getStopWatch().start();
	}

}
