package net.cbtltd.server.api;

import net.cbtltd.shared.ConvertionInfo;

public interface ConvertionInfoMapper {
	void create(ConvertionInfo convertionInfo);
	ConvertionInfo read(int id);
	void update(ConvertionInfo convertionInfo);
	void updateByPrimaryKey(ConvertionInfo convertionInfo);
	void delete(int id);
}