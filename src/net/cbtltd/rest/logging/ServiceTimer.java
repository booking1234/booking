package net.cbtltd.rest.logging;

import org.perf4j.slf4j.Slf4JStopWatch;

public class ServiceTimer {

	private static final ThreadLocal<Slf4JStopWatch> stopWatch = new ThreadLocal<Slf4JStopWatch>() {
		@Override
		public Slf4JStopWatch initialValue() {
			return new Slf4JStopWatch();
		}
	};

	public static Slf4JStopWatch getStopWatch() {
		return stopWatch.get();
	}

	public static Slf4JStopWatch setStopWatchTag(String tag) {
		return stopWatch.get().setTag(tag);
	}

	public static Slf4JStopWatch setStopWatchTagMessage(String tag, String message) {
		return stopWatch.get().setTag(tag).setMessage(message);
	}

	public static void remove() {
		stopWatch.remove();
	}

}
