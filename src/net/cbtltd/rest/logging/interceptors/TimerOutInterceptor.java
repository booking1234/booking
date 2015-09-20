package net.cbtltd.rest.logging.interceptors;

import net.cbtltd.rest.logging.ServiceTimer;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

public class TimerOutInterceptor extends AbstractPhaseInterceptor<Message> {

	public TimerOutInterceptor() {
		super(Phase.SETUP_ENDING);
	}

	public void handleMessage(final Message arg0) throws Fault {
		ServiceTimer.getStopWatch().stop();
		ServiceTimer.remove();
	}
}
