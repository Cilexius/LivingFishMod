package livingfish.items;

import java.util.List;

import javax.annotation.Nullable;

import livingfish.utils.FishUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.SoundEvent;
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
		this.setCreativeTab(CreativeTabs.MISC);
	}
	
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        boolean flag = false;
        RayTraceResult raytraceresult = this.rayTrace(world, player, flag);
        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, world, itemStack, raytraceresult);
        if (ret != null) return ret;

        if (raytraceresult == null) {
            return new ActionResult(EnumActionResult.PASS, itemStack);
        } else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return new ActionResult(EnumActionResult.PASS, itemStack);
        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!world.isBlockModifiable(player, blockpos)) {
                return new ActionResult(EnumActionResult.FAIL, itemStack);
            } else {
                boolean flag1 = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
                BlockPos blockpos1 = flag1 && raytraceresult.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(raytraceresult.sideHit);

                if (!player.canPlayerEdit(blockpos1, raytraceresult.sideHit, itemStack)) {
                    return new ActionResult(EnumActionResult.FAIL, itemStack);
                } else if (this.tryPlaceContainedLiquid(player, world, blockpos1, itemStack)) {
                    player.addStat(StatList.getObjectUseStats(this));
                    return !player.capabilities.isCreativeMode ? new ActionResult(EnumActionResult.SUCCESS, new ItemStack(Items.BUCKET)) : new ActionResult(EnumActionResult.SUCCESS, itemStack);
                } else {
                    return new ActionResult(EnumActionResult.FAIL, itemStack);
                }
            }
        }
    }

    public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World world, BlockPos pos, ItemStack itemStack) {
        IBlockState iblockstate = world.getBlockState(pos);
        Material material = iblockstate.getMaterial();
        boolean flag = !material.isSolid();
        boolean flag1 = iblockstate.getBlock().isReplaceable(world, pos);

        if (!world.isAirBlock(pos) && !flag && !flag1) {
            return false;
        } else {
            if (world.provider.doesWaterVaporize()) {
                int l = pos.getX();
                int i = pos.getY();
                int j = pos.getZ();
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

                for (int k = 0; k < 8; ++k) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
                }
            } else {
                if (!world.isRemote && (flag || flag1) && !material.isLiquid()) {
                    world.destroyBlock(pos, true);
                }

                SoundEvent soundevent = SoundEvents.ITEM_BUCKET_EMPTY;
                world.playSound(player, pos, soundevent, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), 11);
            }

            //spawn Fish from bucket
            if (!world.isRemote) {
                FishUtils.spawnFishByName(this.fishName, this.isChildBucket(itemStack), world, pos);
            }
            
            return true;
        }
    }
    
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean isSelected) {
    	if (this.isChild) {
    		this.setChildBucket(itemStack);
    		this.isChild = false;
    	}
    }
    
    public static void setChildBucket(ItemStack itemStack) {
    	NBTTagCompound nbt = new NBTTagCompound();
    	if(!itemStack.hasTagCompound()) {
        	itemStack.setTagCompound(nbt);
        	itemStack.getTagCompound().setBoolean("child", true);
    	}
    }
    
    public static boolean isChildBucket(ItemStack itemStack) {
    	if (itemStack.hasTagCompound() && itemStack.getTagCompound().getBoolean("child")) {
    		return true;
    	}
    	return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced) {
        if (itemStack.hasTagCompound() && this.isChildBucket(itemStack)) {
        	list.add("Child Bucket");
        }
    }
    
}
