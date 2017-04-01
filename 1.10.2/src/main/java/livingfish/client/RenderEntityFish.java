package livingfish.client;

import org.lwjgl.opengl.GL11;

import livingfish.entities.EntityFish;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderEntityFish extends RenderLiving {
	
	public RenderEntityFish(RenderManager manager, ModelBase model, float shadowSize) {
		super(manager, model, shadowSize);
	}

	protected ResourceLocation getEntityTexture(Entity fish) {
		return getEntityTexture((EntityFish)fish);
	}
	
	protected ResourceLocation getEntityTexture(EntityFish fish) {
	  return fish.isChild()? fish.getTextureLocationBaby() : fish.getTextureLocationAdult();
	}
	
	protected void rotateCorpse(EntityLivingBase fish, float x, float y, float z) {
		rotateCorpse((EntityFish)fish, x, y, z);
	}

	protected void rotateCorpse(EntityFish fish, float x, float y, float z) {
		GL11.glTranslatef(0.0F, -0.1F, 0.0F);
		super.rotateCorpse(fish, x, y, z);
	}

	protected float handleRotationFloat(EntityLivingBase fish, float rotation) {
		return handleRotationFloat((EntityFish)fish, rotation);
	}
	
	protected float handleRotationFloat(EntityFish fish, float partialTick) {
		if (fish.isInWater()) {
			return 0.0F;
		}
		return 1.0F;
	}
	
    public void doRender(EntityLiving fish, double x, double y, double z, float entityYaw, float partialTicks) {
    	doRender((EntityFish)fish, x, y, z, entityYaw, partialTicks);
    }
    
    public void doRender(EntityFish fish, double x, double y, double z, float entityYaw, float partialTicks) {
    	super.doRender(fish, x, y, z, entityYaw, partialTicks);
    }
    
}
