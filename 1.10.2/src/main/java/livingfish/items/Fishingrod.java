package livingfish.items;

import java.util.List;

import javax.annotation.Nullable;

import io.netty.buffer.Unpooled;
import livingfish.entities.EntityIronFishHook;
import livingfish.init.ModNetwork;
import livingfish.init.ModSounds;
import livingfish.network.SToCMessage;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
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
	
	public boolean casted;
	public boolean removeBait;
	
    public Fishingrod() {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack itemStack, @Nullable World world, @Nullable EntityLivingBase entity) {
                return entity == null ? 0.0F : (entity.getHeldItemMainhand() == itemStack && entity instanceof EntityPlayer && ((EntityPlayer)entity).fishEntity != null ? 1.0F : 0.0F);
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

    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
    	
    	if (!world.isRemote && player.isSneaking() && !this.hasBait(itemStack)) {
    		if (player.inventory.hasItemStack(new ItemStack(Items.WHEAT_SEEDS))) {
    			int slot = player.inventory.getSlotFor(new ItemStack(Items.WHEAT_SEEDS));
    			ItemStack playersStack = player.inventory.getStackInSlot(slot);
    			if(playersStack.stackSize > 1) {
    				playersStack.stackSize--;
    				player.inventory.setInventorySlotContents(slot, playersStack);
    			} else {
    				player.inventory.removeStackFromSlot(slot);
    			}
    			this.setBait(itemStack);
    		}
    		return new ActionResult(EnumActionResult.SUCCESS, itemStack);
    	}
    	
        if (player.fishEntity != null) {
            world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, ModSounds.rodReel, SoundCategory.NEUTRAL, 0.5F, 1.0F);
            int i = ((EntityIronFishHook) player.fishEntity).handleIronHookRetraction(1.3D);
            itemStack.damageItem(i, player);
            player.swingArm(hand);
        } else {
            world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote) {
                world.spawnEntityInWorld(new EntityIronFishHook(world, player, this, this.hasBait(itemStack)));
            }
            player.swingArm(hand);
            player.addStat(StatList.getObjectUseStats(this));
            
            if (!world.isRemote && player instanceof EntityPlayerMP) {
                PacketBuffer out = new PacketBuffer(Unpooled.buffer());
                out.writeInt(1);
                SToCMessage message = new SToCMessage(out);
                ModNetwork.networkWrapper.sendTo(message, (EntityPlayerMP) player);
            }

        }

        return new ActionResult(EnumActionResult.SUCCESS, itemStack);
    }
    
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean isSelected) {
    	if (!itemStack.hasTagCompound()) {
        	itemStack.setTagCompound(new NBTTagCompound());
        	itemStack.getTagCompound().setBoolean("baits", false);
        }
    	if (this.removeBait) {
    		this.removeBait(itemStack);
    		this.removeBait = false;
    	}
    }
    
    public static void setBait(ItemStack itemStack) {
    	NBTTagCompound nbt = itemStack.getTagCompound();
    	nbt.setBoolean("baits", true);
    }
    
    public static boolean hasBait(ItemStack itemStack) {
    	NBTTagCompound nbt = itemStack.getTagCompound();
    	return nbt.getBoolean("baits");
    }
    
    public static void removeBait(ItemStack itemStack) {
    	NBTTagCompound nbt = itemStack.getTagCompound();
    	nbt.setBoolean("baits", false);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced) {
        if (itemStack.hasTagCompound() && this.hasBait(itemStack)) {
        	list.add(TextFormatting.BLUE + "Has Bait");
        } else {
        	list.add(TextFormatting.BLUE + "Sneak + Right Click to set Bait");
        	if (!player.inventory.hasItemStack(new ItemStack(Items.WHEAT_SEEDS))) {
            	list.add(TextFormatting.ITALIC + "Needs Bait in inventory to work");
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
