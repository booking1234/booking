package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.ProductFilterSettings;

public interface ProductFilterSettingsMapper {
	List<ProductFilterSettings> getImportProductFilterSettings(String partnerid);
	List<ProductFilterSettings> getExportProductFilterSettings(String partnerid);

}
