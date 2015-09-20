package net.cbtltd.server.api;

import java.util.List;

import net.cbtltd.shared.ProductExportSettings;
import net.cbtltd.shared.ProductFilterSettings;

public interface ProductExportSettingsMapper {
	List<ProductExportSettings> getProductExportSettings(String partnerid);
	

}
