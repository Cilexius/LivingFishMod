package livingfish.entities;

import livingfish.init.ModConfigs;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntitySalmon extends EntityFish {

	private static final ResourceLocation ADULT_TEXTURE = new ResourceLocation("livingfish:textures/entities/fish_salmon.png");
	private static final ResourceLocation BABY_TEXTURE = new ResourceLocation("livingfish:textures/entities/fish_salmon_baby.png");
	  
	public EntitySalmon(World world) {
		super(world);
	}
	
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ModConfigs.salmonMaxHealth);
    }

	public ResourceLocation getTextureLocationBaby() {
		return BABY_TEXTURE;
	}
	  
	public ResourceLocation getTextureLocationAdult() {
		return ADULT_TEXTURE;
	}
	
	public float getBaseScale() {
		return 1.0F;
	}
	
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntitySalmon(this.worldObj);
	}
	
	public ItemStack getDropItemStack() {
		return new ItemStack(Items.FISH, 1, 1);
	}
	
}
