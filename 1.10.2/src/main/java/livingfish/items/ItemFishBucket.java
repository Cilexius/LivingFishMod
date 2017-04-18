package livingfish.items;

import java.util.List;

import javax.annotation.Nullable;

import livingfish.LivingFish;
import livingfish.init.ModBlocks;
import livingfish.utils.FishUtils;
import livingfish.utils.BlockUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFishBucket extends Item {

	public String fishName;
	public boolean isChild;
	
	public ItemFishBucket(String fishName) {
		this.fishName = fishName;
		this.setMaxStackSize(1);
		this.setCreativeTab(LivingFish.instance.tab);
	}
	
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        RayTraceResult rayTrace = this.rayTrace(world, player, true);
        if (rayTrace == null) {
            return new ActionResult(EnumActionResult.PASS, stack);
        } else if (rayTrace.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult(EnumActionResult.PASS, stack);
        } else {
            BlockPos pos = rayTrace.getBlockPos();

            if (!player.canPlayerEdit(pos, rayTrace.sideHit, stack)) {
                return new ActionResult(EnumActionResult.FAIL, stack);
            } else if (this.tryToPlaceContent(player, world, pos, stack, rayTrace.sideHit)) {
                player.addStat(StatList.getObjectUseStats(this));
                return !player.capabilities.isCreativeMode ? new ActionResult(EnumActionResult.SUCCESS, new ItemStack(Items.BUCKET)) : new ActionResult(EnumActionResult.SUCCESS, stack);
            } else {
                return new ActionResult(EnumActionResult.FAIL, stack);
            }
        }
    }

    public boolean tryToPlaceContent(@Nullable EntityPlayer player, World world, BlockPos pos, ItemStack stack, EnumFacing facing) {
        boolean isReplaceable = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
        if (!BlockUtils.isWater(world, pos) && !BlockUtils.isTank(world, pos) && !BlockUtils.isTank_Water(world, pos)) {
        	pos = pos.offset(facing);
        }
        Material material = world.getBlockState(pos).getMaterial();
        
        if (!world.isAirBlock(pos) && material.isSolid() && !isReplaceable && !world.isBlockModifiable(player, pos)) {
            return false;
        } else if (BlockUtils.isTank(world, pos)) {
    		world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        	if (!world.isRemote) {
        		world.setBlockState(pos, ModBlocks.tank_water.getDefaultState());
                FishUtils.spawnFishByName(this.fishName, this.isChildBucket(stack), world, pos);
            }
            return true;
        } else if (BlockUtils.isTank_Water(world, pos)) {
    		world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        	if (!world.isRemote) {
                FishUtils.spawnFishByName(this.fishName, this.isChildBucket(stack), world, pos);
            }
        	return true;
        } else if (BlockUtils.isWater(world, pos)) {
            world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!world.isRemote) {
                FishUtils.spawnFishByName(this.fishName, this.isChildBucket(stack), world, pos);
            }
            return true;
        } else {
            world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        	if (world.provider.doesWaterVaporize()) {

                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                for (int k = 0; k < 8; ++k) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
                }
            } else if (!material.isSolid() && !material.isLiquid()) {
            	if (!world.isRemote) {
            		world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), 11);
            	}
        	}
        	if (!world.isRemote) {
                FishUtils.spawnFishByName(this.fishName, this.isChildBucket(stack), world, pos);
            }
            return true;
        }
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
    	if (this.isChild) {
    		this.setChildBucket(stack);
    		this.isChild = false;
    	}
    }
    
    public static void setChildBucket(ItemStack stack) {
    	NBTTagCompound nbt = new NBTTagCompound();
    	if(!stack.hasTagCompound()) {
        	stack.setTagCompound(nbt);
        	stack.getTagCompound().setBoolean("child", true);
    	}
    }
    
    public static boolean isChildBucket(ItemStack stack) {
    	if (stack.hasTagCompound() && stack.getTagCompound().getBoolean("child")) {
    		return true;
    	}
    	return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (stack.hasTagCompound() && this.isChildBucket(stack)) {
        	list.add(I18n.format("livingfish.childbucket"));
        }
    }
    
}
