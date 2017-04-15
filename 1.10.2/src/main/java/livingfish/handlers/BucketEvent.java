package livingfish.handlers;

import java.util.List;

import livingfish.entities.EntityFish;
import livingfish.init.ModBlocks;
import livingfish.items.ItemFishBucket;
import livingfish.utils.BlockUtils;
import livingfish.utils.FishUtils;
import livingfish.utils.MathUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BucketEvent {
	
	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event) {
		
		World world = event.getWorld();
		EntityPlayer player = event.getEntityPlayer();
		EnumHand hand = player.getActiveHand();
		
		if (player != null) {			
			
			RayTraceResult rayTrace = event.getTarget();
			
			if (rayTrace != null && rayTrace.typeOfHit.BLOCK != null) {
				
				BlockPos pos = rayTrace.getBlockPos();
				IBlockState state = world.getBlockState(pos);
				Material material = state.getMaterial();
				
				if (BlockUtils.isWater(world, pos)) {

					EntityFish fish = null;
					
					List<EntityFish> list = player.worldObj.getEntitiesWithinAABB(EntityFish.class, player.getEntityBoundingBox().expand(4.0D, 4.0D, 4.0D));
					int i = 0;
			    	while (i < list.size()) {
			    		
			    		EntityFish fishOnList = list.get(i);
			    		if(MathUtils.getDistanceSqEntitytoBlockPos(fishOnList, pos) < 1.0D) {
			    			fish = fishOnList;
			    			break;
			    		}
			    		i++;
			    	}
			    	
			    	if (!world.isRemote && !player.capabilities.isCreativeMode) {
			    		if (fish != null) {
	            			ItemFishBucket fishBucket = ((ItemFishBucket) FishUtils.getFishBucketByFish(fish));
		            		if (fishBucket != null) {
		            			if (fish.isChild()) {
				            		fishBucket.isChild = true;
			            		}
			            		
			            		if (player.inventory.getCurrentItem().stackSize-- == 0) {
			                        player.setHeldItem(hand, new ItemStack(fishBucket));
			                    } else if (!player.inventory.addItemStackToInventory(new ItemStack(fishBucket))) {
			                        player.dropItem(new ItemStack(fishBucket), false);
			                    }
			            		
			            		fish.setDead();
		            		}
			    		} else {
			    			ItemStack stackToReturn = new ItemStack(Items.WATER_BUCKET);
			    			if (player.inventory.getCurrentItem().stackSize-- == 0) {
		                        player.setHeldItem(hand, stackToReturn);
		                    } else if (!player.inventory.addItemStackToInventory(stackToReturn)) {
		                        player.dropItem(stackToReturn, false);
		                    }
			    		}
            		}
	    			if (BlockUtils.isTank_Water(world, pos)) {
		    			world.setBlockState(pos, ModBlocks.tank.getDefaultState());
	    			} else {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
	    			}
	    			
	    			event.setCanceled(true);
				}
			}				
		}
	}
	
	@SubscribeEvent
	public void onRightClickBucketItem(RightClickItem event) {
		
		World world = event.getWorld();
		EntityPlayer player = event.getEntityPlayer();
		ItemStack stack = event.getItemStack();
		
		if (stack != null) {
			
			if (stack.getItem() == Items.WATER_BUCKET) {
				
				RayTraceResult rayTrace = MathUtils.rayTrace(player, 5.0D, true, 1.0F);
				
				if (rayTrace != null) {
					
					BlockPos rayTracePos = rayTrace.getBlockPos();
					
					if (BlockUtils.isTank(world, rayTracePos)) {
						if (!player.capabilities.isCreativeMode) {
							ItemStack stackToReturn = new ItemStack(Items.BUCKET);
			    			if (player.inventory.getCurrentItem().stackSize-- == 0) {
		                        player.setHeldItem(player.getActiveHand(), stackToReturn);
		                    } else if (!player.inventory.addItemStackToInventory(stackToReturn)) {
		                        player.dropItem(stackToReturn, false);
		                    }
						}
						world.setBlockState(rayTracePos, ModBlocks.tank_water.getDefaultState());
						event.setCanceled(true);
					}
					
					if (BlockUtils.isTank_Water(world, rayTracePos)) {
						event.setCanceled(true);
					}
				}
			}
		}
	}
	
}
