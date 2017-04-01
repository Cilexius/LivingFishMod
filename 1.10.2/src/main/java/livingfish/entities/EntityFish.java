package livingfish.entities;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;

import livingfish.entities.ais.EntityAIFindBaitedHook;
import livingfish.entities.ais.EntityAIFindBreedEntityItem;
import livingfish.entities.ais.EntityAIFishAvoidEntity;
import livingfish.entities.ais.EntityAIWanderFish;
import livingfish.entities.ais.FishMoveHelper;
import livingfish.utils.ItemUtils;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;

public abstract class EntityFish extends EntityAnimal {

    private static final Set<Item> BREEDING_ITEMS = Sets.newHashSet(new Item[] {Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS});
    private static final DataParameter<Byte> STATUS = EntityDataManager.<Byte>createKey(EntityGuardian.class, DataSerializers.BYTE);
	public float rotationPitchToSet;
	public float moveVertical;
	
	public EntityFish(World world) {
		super(world);
		this.setSize(0.2F, 0.2F);
        this.moveHelper = new FishMoveHelper(this);
	}
	
    public static void registerFixesGuardian(DataFixer fixer) {
        EntityLiving.registerFixesMob(fixer, "Fish");
    }
	  
    protected void initEntityAI() {
        EntityAIMoveTowardsRestriction entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
        this.tasks.addTask(12, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(21, new EntityAIFindBreedEntityItem(this, 1.2D, 18.0D));
        this.tasks.addTask(22, new EntityAIFindBaitedHook(this, 1.2D, 24.0D));
        this.tasks.addTask(30, new EntityAIFishAvoidEntity(this, EntityPlayer.class, 4.0F, 1.7D, 2.2D));
        this.tasks.addTask(31, new EntityAIWanderFish(this, 1.0D, 10));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    }
	
	public abstract ResourceLocation getTextureLocationBaby();
	  
	public abstract ResourceLocation getTextureLocationAdult();
	
	public abstract float getBaseScale();
	
	public abstract EntityAgeable createChild(EntityAgeable ageable);
	
	public abstract ItemStack getDropItemStack();

	public float getScale() {
		if (this.isChild()) {
			return 0.7F * getBaseScale();
		}
		return getBaseScale();
	}
	
	public float getScaleX() {
		return getScale();
	}
	  
	public float getScaleY() {
		return getScale();
	}
	  
	public float getScaleZ() {
		return getScale();
	}
	
    public float getEyeHeight() {
        return 0.2F;
    }
	
    @Nullable
    protected ResourceLocation getLootTable() {
    	return null;
    }
    
    protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
    	if (!this.isChild()) {
        	this.entityDropItem(this.getDropItemStack(), 0.0F);
    	}
    }
    
    public boolean isBreedingItem(@Nullable ItemStack itemStack) {
        return itemStack != null && BREEDING_ITEMS.contains(itemStack.getItem());
    }

    protected PathNavigate getNewNavigator(World world) {
        return new PathNavigateSwimmer(this, world);
    }
    
    public int getTalkInterval() {
        return 80;
    }
    
    protected SoundEvent getHurtSound() {
        return SoundEvents.ENTITY_ELDER_GUARDIAN_HURT_LAND;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ELDER_GUARDIAN_HURT_LAND;
    }
    
    public boolean canBreatheUnderwater() {
        return true;
    }
    
    protected boolean canTriggerWalking() {
        return false;
    }
    
    protected boolean canDespawn() {
        return true;
    }
    
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(STATUS, Byte.valueOf((byte)0));
    }
    
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
    }

    private boolean isSyncedFlagSet(int flagId) {
        return (((Byte)this.dataManager.get(STATUS)).byteValue() & flagId) != 0;
    }

    private void setSyncedFlag(int flagId, boolean state) {
        byte b0 = ((Byte)this.dataManager.get(STATUS)).byteValue();

        if (state) {
            this.dataManager.set(STATUS, Byte.valueOf((byte)(b0 | flagId)));
        } else {
            this.dataManager.set(STATUS, Byte.valueOf((byte)(b0 & ~flagId)));
        }
    }

    public boolean isMoving() {
        return this.isSyncedFlagSet(2);
    }

    private void setMoving(boolean moving) {
        this.setSyncedFlag(2, moving);
    }
    
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
    }
    
    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater()) {
            --i;
            this.setAir(i);

            if (this.getAir() == -20) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.drown, 2.0F);
            }
        } else {
            this.setAir(300);
        }
    }
    
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.inWater) {
            this.setAir(300);
        } else if (this.onGround) {
            this.motionY += 0.5D;
            this.motionX += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.motionZ += (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
            this.rotationYaw = this.rand.nextFloat() * 360.0F;
        }
        
        if (!this.worldObj.isRemote) {
            for (EntityItem item : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(0.8D, 0.8D, 0.8D))) {
                if (!item.isDead && item.getEntityItem() != null && !item.cannotPickup() && this.isBreedingItem(item.getEntityItem())) {
                	if (this.getGrowingAge() == 0 && !this.isInLove()) {
                		EntityPlayer player = null;
                		this.setInLove(player);
                		ItemUtils.subtractItem(item);
                	}
                	if(this.isChild()) {
                		this.ageUp((int)((float)(-this.getGrowingAge() / 20) * 0.1F), true);
                		ItemUtils.subtractItem(item);
                	}
                }
            }   
        }
    }
    
    public boolean isNotColliding() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
    }
    
    public void moveEntityWithHeading(float strafe, float forward) {
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                this.moveRelative(strafe, forward, 0.04F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.800000011920929D;
                this.motionY *= 0.800000011920929D;
                this.motionZ *= 0.800000011920929D;
            } else {
                super.moveEntityWithHeading(strafe, forward);
            }
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }
    
}
