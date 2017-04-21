package livingfish.handlers;

import livingfish.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClickEvent {

	@SubscribeEvent
	public void onRighClickItem(RightClickItem event) {
		
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getItemStack();
		
		if (stack != null && stack.getItem() == Items.IRON_INGOT && player.isSneaking()) {
			
			ItemStack stackToReturn = new ItemStack(ModItems.ironhook);
			stackToReturn.stackSize = 2;
			if (player.inventory.getCurrentItem().stackSize-- == 0) {
                player.setHeldItem(player.getActiveHand(), stackToReturn);
            } else if (!player.inventory.addItemStackToInventory(stackToReturn)) {
                player.dropItem(stackToReturn, false);
            }
			
		}
	}
	
}
