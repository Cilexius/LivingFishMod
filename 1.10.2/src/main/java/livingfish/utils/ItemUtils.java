package livingfish.utils;

import net.minecraft.entity.item.EntityItem;

public class ItemUtils {

	/**
	 * Removes always one Item from an EntityItemStack
	 * @param item The EntityItem
	 */
    public static void subtractItem(EntityItem item) {
    	if (item.getEntityItem().stackSize > 1) {
    		--item.getEntityItem().stackSize;
		} else {
    		item.setDead();
		}
    }
    
}
