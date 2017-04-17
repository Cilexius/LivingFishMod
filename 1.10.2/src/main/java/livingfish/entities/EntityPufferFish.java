package livingfish.entities;

import livingfish.init.ModConfigs;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityPufferFish extends EntityFish {

	private static final ResourceLocation ADULT_TEXTURE = new ResourceLocation("livingfish:textures/entities/fish_pufferfish.png");
	private static final ResourceLocation BABY_TEXTURE = new ResourceLocation("livingfish:textures/entities/fish_pufferfish_baby.png");
	public float puffedXScale = 2.7F;
	public float puffedYScale = 1.5F;
	public float puffedZScale = 1.0F;
	public float puffedAmount = 0.0F;
	public int scaredTime = 0;
	
	public EntityPufferFish(World world) {
		super(world);
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ModConfigs.pufferfishMaxHealth);
    }

	public ResourceLocation getTextureLocationBaby() {
		return BABY_TEXTURE;
	}
	  
	public ResourceLocation getTextureLocationAdult() {
		return ADULT_TEXTURE;
	}
	
	public float getBaseScale() {
		return 0.8F;
	}
	
	public float getScaleX() {
	    return super.getScaleX() * (1.0F + this.puffedAmount * ((this.isInWater() ? this.puffedXScale : this.puffedYScale) - 1.0F));
	}
	  
	public float getScaleY() {
		return super.getScaleY() * (1.0F + this.puffedAmount * ((this.isInWater() ? this.puffedYScale : this.puffedXScale) - 1.0F));
	}
	  
	public float getScaleZ() {
		return super.getScaleZ() * (1.0F + this.puffedAmount * (this.puffedZScale - 1.0F));
	}
	
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityPufferFish(this.worldObj);
	}
	
	public ItemStack getDropItemStack() {
		return new ItemStack(Items.FISH, 1, 3);
	}
    
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setFloat("PuffedAmount", this.puffedAmount);
        compound.setInteger("ScaredTime", this.scaredTime);
    }
    
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.puffedAmount = compound.getFloat("PuffedAmount");
        this.scaredTime = compound.getInteger("ScaredTime");
    }
    
    public void onLivingUpdate() {
    	super.onLivingUpdate();
    	if (this.worldObj.isRemote) {
			if (this.getRNG().nextInt(ModConfigs.pufferfishRandomScared) == 0) {
    			this.scaredTime += (200 + ModConfigs.pufferfishAddExtraScaredTime);
    		}
    		if (this.scaredTime > 0) {
    			this.scaredTime -= 1;
    			if (this.getRNG().nextInt(1000) == 0) {
        			this.scaredTime += 100;
        		}
    		}
    		if ((this.scaredTime > 0) && (this.puffedAmount < 0.95F)) {
    			this.puffedAmount = ((this.puffedAmount * 7.0F + 1.0F) / 8.0F);
    		} else if ((this.scaredTime <= 0) && (this.puffedAmount > 0.05F)) {
    			this.puffedAmount = ((this.puffedAmount * 7.0F + 0.0F) / 8.0F);
    		}
    	}
	}
    
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	this.scaredTime = (200 + ModConfigs.pufferfishAddExtraScaredTime);
    	return super.attackEntityFrom(source, amount);
	}
    
}
