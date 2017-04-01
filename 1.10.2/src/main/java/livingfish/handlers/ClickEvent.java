package livingfish.handlers;

import java.util.List;

import livingfish.entities.EntityFish;
import livingfish.items.ItemFishBucket;
import livingfish.utils.FishUtils;
import livingfish.utils.MathUtils;
import livingfish.utils.MiscUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClickEvent {
	
	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event) {
		
		World world = event.getWorld();
		
		if (!world.isRemote) {
			if (event.getEntityPlayer() != null) {
				
				EntityPlayer player = event.getEntityPlayer();
				EnumHand hand = player.getActiveHand();
				ItemStack itemStack = event.getEmptyBucket();
				RayTraceResult rayTrace = event.getTarget();
				BlockPos pos = rayTrace.getBlockPos();
				IBlockState blockState = world.getBlockState(pos);
				Material material = blockState.getMaterial();
				
				if (rayTrace != null) {
					if (MiscUtils.isWater(world, pos)) {
						
						List<EntityFish> list = player.worldObj.getEntitiesWithinAABB(EntityFish.class, player.getEntityBoundingBox().expand(4.0D, 4.0D, 4.0D));
						int i = 0;
				    	while (i < list.size()) {
				    		EntityFish fish = list.get(i);
				    		if(MathUtils.getDistanceSqEntitytoBlockPos(fish, pos) < 1.4D) {
			            		ItemFishBucket fishBucket = FishUtils.getFishBucketByFish(fish);
			            		if (fish.isChild()) {
				            		fishBucket.isChild = true;
			            		}
			            		if (player.inventory.getCurrentItem().stackSize-- == 0) {
			                        player.setHeldItem(hand, new ItemStack(fishBucket));
			                    } else if (!player.inventory.addItemStackToInventory(new ItemStack(fishBucket))) {
			                        player.dropItem(new ItemStack(fishBucket), false);
			                    }
				    			fish.setDead();
		                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
				    			event.setCanceled(true);
				    			i = 100;
				    		}
				    		i++;
				    	}

					}
				}
				
			}
		}
		
	}
	
}
