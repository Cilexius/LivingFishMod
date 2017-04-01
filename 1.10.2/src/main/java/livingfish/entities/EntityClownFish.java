package livingfish.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityClownFish extends EntityFish {

	private static final ResourceLocation ADULT_TEXTURE = new ResourceLocation("livingfish:textures/entities/fish_clownfish.png");
	private static final ResourceLocation BABY_TEXTURE = new ResourceLocation("livingfish:textures/entities/fish_clownfish_baby.png");
	  
	public EntityClownFish(World world) {
		super(world);
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
    }

	public ResourceLocation getTextureLocationBaby() {
		return BABY_TEXTURE;
	}
	  
	public ResourceLocation getTextureLocationAdult() {
		return ADULT_TEXTURE;
	}
	
	public float getBaseScale() {
		return 0.5F;
	}
	
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityClownFish(this.worldObj);
	}
	
	public ItemStack getDropItemStack() {
		return new ItemStack(Items.FISH, 1, 2);
	}
	
}
