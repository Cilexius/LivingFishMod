package livingfish.handlers;

import livingfish.utils.BlockUtils;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WaterEvent {

	@SubscribeEvent
	public void onWater(BlockEvent event) {
		if (event.getState().getBlock() instanceof BlockDynamicLiquid) {
			if (((Integer)event.getState().getValue(BlockLiquid.LEVEL)).intValue() != 0) {
				if (BlockUtils.checkNeighborBlocks(event.getWorld(), event.getPos())) {
					event.getWorld().setBlockState(event.getPos(), Blocks.AIR.getDefaultState());
				}
			}
		}
	}
	
}
