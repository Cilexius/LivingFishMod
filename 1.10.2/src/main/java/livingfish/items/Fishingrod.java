package livingfish.items;

import java.util.List;

import javax.annotation.Nullable;

import livingfish.entities.EntityIronFishHook;
import livingfish.init.ModSounds;
import livingfish.utils.FishUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Fishingrod extends ItemFishingRod {
	
    public Fishingrod() {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity) {
                return entity == null ? 0.0F : (entity.getHeldItemMainhand() == stack && entity instanceof EntityPlayer && ((EntityPlayer)entity).fishEntity != null ? 1.0F : 0.0F);
            }
        });
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
    	if (player.isSneaking()) {
    		if (!world.isRemote && !this.hasBait(stack) && player.getHeldItemOffhand() != null) {
    			if (FishUtils.isBait(player.getHeldItemOffhand().getItem())) {
        			if(player.getHeldItemOffhand().stackSize > 1) {
        				player.getHeldItemOffhand().stackSize--;
        			} else {
        				player.setHeldItem(EnumHand.OFF_HAND, null);
        			}
        			this.setBait(stack);
    			}
    		}
    		return new ActionResult(EnumActionResult.SUCCESS, stack);
    	}
    	
        if (player.fishEntity != null) {
            world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, ModSounds.rodReel, SoundCategory.NEUTRAL, 0.5F, 1.0F);
            int i = ((EntityIronFishHook) player.fishEntity).handleIronHookRetraction(1.3D);
            stack.damageItem(i, player);
            player.swingArm(hand);
        } else {
            world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityIronFishHook(world, player, stack, this.hasBait(stack)));
            }
            player.swingArm(hand);
            player.addStat(StatList.getObjectUseStats(this));
        }

        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }
    
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
    	if (!stack.hasTagCompound()) {
        	stack.setTagCompound(new NBTTagCompound());
        	stack.getTagCompound().setBoolean("baits", false);
        }
    }
    
    public static void setBait(ItemStack stack) {
    	NBTTagCompound nbt = stack.getTagCompound();
    	nbt.setBoolean("baits", true);
    }
    
    public static boolean hasBait(ItemStack stack) {
    	NBTTagCompound nbt = stack.getTagCompound();
    	return nbt.getBoolean("baits");
    }
    
    public static void removeBait(ItemStack stack) {
    	NBTTagCompound nbt = stack.getTagCompound();
    	nbt.setBoolean("baits", false);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {
        if (stack.hasTagCompound() && this.hasBait(stack)) {
        	list.add(TextFormatting.BLUE + "Has Bait");
        } else {
        	list.add(TextFormatting.BLUE + "Sneak + Right Click to set Bait");
        	if (player.getHeldItemOffhand() == null) {
        		list.add(TextFormatting.ITALIC + "Needs Bait in Off Hand to work");
        	} else  if (!(FishUtils.isBait(player.getHeldItemOffhand().getItem()))) {
        		list.add(TextFormatting.ITALIC + "Needs Bait in Off Hand to work");
        	}
        }
    }

    public boolean isItemTool(ItemStack stack) {
        return super.isItemTool(stack);
    }

    public int getItemEnchantability() {
        return 1;
    }
    
}
