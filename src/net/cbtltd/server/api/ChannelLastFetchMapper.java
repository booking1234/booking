package net.cbtltd.server.api;

import net.cbtltd.shared.ChannelLastFetch;

public interface ChannelLastFetchMapper {
	void update(ChannelLastFetch action);
	ChannelLastFetch read(ChannelLastFetch action);
	void create(ChannelLastFetch action);
}
