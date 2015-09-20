/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.shared.design;

import net.cbtltd.shared.ModelTable;
import net.cbtltd.shared.Service;
import net.cbtltd.shared.api.HasTableService;

public class ReportTable extends ModelTable
implements HasTableService {

	@Override
	public Service service() {return Service.REPORT;}

}
