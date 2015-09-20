/**
 * @author	abookingnet
 * @see License at http://abookingnet.com
 * @version	4.0.0
 */
package net.cbtltd.server.api;

import java.util.ArrayList;

import net.cbtltd.shared.Asset;
import net.cbtltd.shared.NameId;
import net.cbtltd.shared.NameIdAction;
import net.cbtltd.shared.asset.AssetTable;

public interface AssetMapper
extends AbstractMapper<Asset> {
	Asset exists(Asset action);
	Integer count(AssetTable action);
	ArrayList<Asset> list(AssetTable action);
	ArrayList<NameId> namebylocation (NameIdAction model);
}