package com.mybookingpal.eventbus;

import com.google.inject.Inject;

public class EventBusManager {
	@Inject public static SqlEventPublisher eventPublisher;
	@Inject public static ChannelEventListener eventListener;
	
	public static SqlEventPublisher getEventPublisher() {
		return eventPublisher;
	}
}
