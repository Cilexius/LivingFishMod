package livingfish.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class PlayerUtils {

	public static int getSlotFor(ItemStack stack, EntityPlayer player) {
		
		InventoryPlayer inventory = player.inventory;
		
        for (int i = 0; i < inventory.mainInventory.length; ++i) {
            if (inventory.mainInventory[i] != null && PlayerUtils.stackEqualExact(stack, inventory.mainInventory[i])) {
                return i;
            }
        }

        return -1;
    }
	
    public static boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }
    
}
