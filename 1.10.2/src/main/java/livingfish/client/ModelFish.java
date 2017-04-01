package livingfish.client;

import org.lwjgl.opengl.GL11;

import livingfish.entities.EntityFish;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelFish extends ModelBase {
	
	public static final float SCALE_FACTOR = 33.333332F;
	public static final float ROT_Y_POS = 21.0F;
	ModelRenderer adultTailSmall;
	ModelRenderer adultHead;
	ModelRenderer adultBody;
	ModelRenderer adultTailBig;
	ModelRenderer adultHeadSmall;
	ModelRenderer adultFinBodyBottom;
	ModelRenderer adultFinRight;
	ModelRenderer adultFinLeft;
	ModelRenderer adultTailMid;
	ModelRenderer adultFinTail;
	ModelRenderer adultFinBodyTop;
	ModelRenderer adultFinTailBottom;
	ModelRenderer adultFinTailTop;
	ModelRenderer babyTailSmall;
	ModelRenderer babyBody;
	ModelRenderer babyHead;
	ModelRenderer babyFinBodyBottom;
	ModelRenderer babyFinRight;
	ModelRenderer babyFinLeft;
	ModelRenderer babyTailBig;
	ModelRenderer babyFinTail;
	ModelRenderer babyFinBodyTop;
	ModelRenderer babyFinTailBottom;
	ModelRenderer babyFinTailTop;

	public ModelFish() {
	  this.adultBody = new ModelRenderer(this, 10, 0);
	  this.adultBody.addBox(-1.0F, -2.0F, -2.0F, 2, 4, 4);
	  this.adultBody.setRotationPoint(0.0F, 0.0F, 0.0F);
	  this.adultBody.setTextureOffset(64, 32);
	  this.adultBody.mirror = true;
	  setRotation(this.adultBody, 0.0F, 0.0F, 0.0F);
	  
	  this.adultFinLeft = new ModelRenderer(this, -4, 8);
	  this.adultFinLeft.addBox(0.0F, 0.0F, 0.0F, 3, 0, 4);
	  this.adultFinLeft.setRotationPoint(0.5F, 0.0F, -1.5F);
	  this.adultFinLeft.setTextureOffset(64, 32);
	  this.adultFinLeft.mirror = true;
	  setRotation(this.adultFinLeft, 0.0F, 0.0F, 0.0F);
	  
	  this.adultFinRight = new ModelRenderer(this, 2, 8);
	  this.adultFinRight.addBox(-2.0F, 0.0F, 0.0F, 3, 0, 4);
	  this.adultFinRight.setRotationPoint(-1.5F, 0.0F, -1.5F);
	  this.adultFinRight.setTextureOffset(64, 32);
	  this.adultFinRight.mirror = true;
	  setRotation(this.adultFinRight, 0.0F, 0.0F, 0.0F);
	  
	  this.adultFinBodyTop = new ModelRenderer(this, 12, 5);
	  this.adultFinBodyTop.addBox(0.0F, -3.0F, -2.0F, 0, 3, 4);
	  this.adultFinBodyTop.setRotationPoint(0.0F, -2.0F, 0.0F);
	  this.adultFinBodyTop.setTextureOffset(64, 32);
	  this.adultFinBodyTop.mirror = true;
	  setRotation(this.adultFinBodyTop, 0.0F, 0.0F, 0.0F);
	  
	  this.adultFinBodyBottom = new ModelRenderer(this, 20, 5);
	  this.adultFinBodyBottom.addBox(0.0F, 0.0F, -2.0F, 0, 3, 4);
	  this.adultFinBodyBottom.setRotationPoint(0.0F, 2.0F, 0.0F);
	  this.adultFinBodyBottom.setTextureOffset(64, 32);
	  this.adultFinBodyBottom.mirror = true;
	  setRotation(this.adultFinBodyBottom, 0.0F, 0.0F, 0.0F);
	  
	  this.adultBody.addChild(this.adultFinLeft);
	  this.adultBody.addChild(this.adultFinRight);
	  this.adultBody.addChild(this.adultFinBodyTop);
	  this.adultBody.addChild(this.adultFinBodyBottom);
	  
	  this.adultHead = new ModelRenderer(this, 4, 0);
	  this.adultHead.addBox(-0.5F, -1.5F, -2.0F, 1, 3, 2);
	  this.adultHead.setRotationPoint(0.0F, 0.0F, -1.5F);
	  this.adultHead.setTextureOffset(64, 32);
	  this.adultHead.mirror = true;
	  setRotation(this.adultHead, 0.0F, 0.0F, 0.0F);
	  
	  this.adultHeadSmall = new ModelRenderer(this, 0, 0);
	  this.adultHeadSmall.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 1);
	  this.adultHeadSmall.setRotationPoint(0.0F, 0.0F, -2.0F);
	  this.adultHeadSmall.setTextureOffset(64, 32);
	  this.adultHeadSmall.mirror = true;
	  setRotation(this.adultHeadSmall, 0.0F, 0.0F, 0.0F);
	  
	  this.adultHead.addChild(this.adultHeadSmall);
	  this.adultBody.addChild(this.adultHead);
	  
	  this.adultTailBig = new ModelRenderer(this, 22, 0);
	  this.adultTailBig.addBox(-0.5F, -1.5F, 0.0F, 1, 3, 3);
	  this.adultTailBig.setRotationPoint(0.0F, 0.0F, 1.5F);
	  this.adultTailBig.setTextureOffset(64, 32);
	  this.adultTailBig.mirror = true;
	  setRotation(this.adultTailBig, 0.0F, 0.0F, 0.0F);
	  
	  this.adultTailMid = new ModelRenderer(this, 30, 0);
	  this.adultTailMid.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 1);
	  this.adultTailMid.setRotationPoint(0.0F, 0.0F, 3.0F);
	  this.adultTailMid.setTextureOffset(64, 32);
	  this.adultTailMid.mirror = true;
	  setRotation(this.adultTailMid, 0.0F, 0.0F, 0.0F);
	  
	  this.adultTailSmall = new ModelRenderer(this, 34, 0);
	  this.adultTailSmall.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 1);
	  this.adultTailSmall.setRotationPoint(0.0F, 0.0F, 1.0F);
	  this.adultTailSmall.setTextureOffset(64, 32);
	  this.adultTailSmall.mirror = true;
	  setRotation(this.adultTailSmall, 0.0F, 0.0F, 0.0F);
	  
	  this.adultBody.addChild(this.adultTailBig);
	  this.adultTailBig.addChild(this.adultTailMid);
	  this.adultTailMid.addChild(this.adultTailSmall);
	  
	  this.adultFinTailTop = new ModelRenderer(this, 28, 6);
	  this.adultFinTailTop.addBox(0.0F, -3.0F, -1.0F, 0, 3, 3);
	  this.adultFinTailTop.setRotationPoint(0.0F, -1.5F, -1.5F);
	  this.adultFinTailTop.setTextureOffset(64, 32);
	  this.adultFinTailTop.mirror = true;
	  setRotation(this.adultFinTailTop, 0.0F, 0.0F, 0.0F);
	  
	  this.adultFinTailBottom = new ModelRenderer(this, 34, 6);
	  this.adultFinTailBottom.addBox(0.0F, 0.0F, -1.0F, 0, 3, 3);
	  this.adultFinTailBottom.setRotationPoint(0.0F, 1.5F, -1.5F);
	  this.adultFinTailBottom.setTextureOffset(64, 32);
	  this.adultFinTailBottom.mirror = true;
	  setRotation(this.adultFinTailBottom, 0.0F, 0.0F, 0.0F);
	  
	  this.adultFinTail = new ModelRenderer(this, 40, 6);
	  this.adultFinTail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 3);
	  this.adultFinTail.setRotationPoint(0.0F, 0.0F, 0.0F);
	  this.adultFinTail.setTextureOffset(64, 32);
	  this.adultFinTail.mirror = true;
	  setRotation(this.adultFinTail, 0.0F, 0.0F, 0.0F);
	  
	  this.adultTailMid.addChild(this.adultFinTailTop);
	  this.adultTailMid.addChild(this.adultFinTailBottom);
	  this.adultTailSmall.addChild(this.adultFinTail);
	  
	  this.babyBody = new ModelRenderer(this, 4, 0);
	  this.babyBody.addBox(-1.0F, -1.5F, -2.0F, 2, 3, 4);
	  this.babyBody.setRotationPoint(0.0F, 0.0F, 0.0F);
	  this.babyBody.setTextureOffset(64, 32);
	  this.babyBody.mirror = true;
	  setRotation(this.babyBody, 0.0F, 0.0F, 0.0F);
	  
	  this.babyFinLeft = new ModelRenderer(this, -3, 8);
	  this.babyFinLeft.addBox(0.0F, 0.0F, 0.0F, 3, 0, 3);
	  this.babyFinLeft.setRotationPoint(0.5F, 0.0F, 0.0F);
	  this.babyFinLeft.setTextureOffset(64, 32);
	  this.babyFinLeft.mirror = true;
	  setRotation(this.babyFinLeft, 0.0F, 0.0F, 0.0F);
	  
	  this.babyFinRight = new ModelRenderer(this, 3, 8);
	  this.babyFinRight.addBox(-2.0F, 0.0F, 0.0F, 3, 0, 3);
	  this.babyFinRight.setRotationPoint(-1.5F, 0.0F, 0.0F);
	  this.babyFinRight.setTextureOffset(64, 32);
	  this.babyFinRight.mirror = true;
	  setRotation(this.babyFinRight, 0.0F, 0.0F, 0.0F);
	  
	  this.babyFinBodyTop = new ModelRenderer(this, 12, 5);
	  this.babyFinBodyTop.addBox(0.0F, -3.0F, -2.0F, 0, 3, 3);
	  this.babyFinBodyTop.setRotationPoint(0.0F, -1.5F, 1.0F);
	  this.babyFinBodyTop.setTextureOffset(64, 32);
	  this.babyFinBodyTop.mirror = true;
	  setRotation(this.babyFinBodyTop, 0.0F, 0.0F, 0.0F);
	  
	  this.babyFinBodyBottom = new ModelRenderer(this, 18, 5);
	  this.babyFinBodyBottom.addBox(0.0F, 0.0F, -2.0F, 0, 3, 3);
	  this.babyFinBodyBottom.setRotationPoint(0.0F, 1.5F, 1.0F);
	  this.babyFinBodyBottom.setTextureOffset(64, 32);
	  this.babyFinBodyBottom.mirror = true;
	  setRotation(this.babyFinBodyBottom, 0.0F, 0.0F, 0.0F);
	  
	  this.babyBody.addChild(this.babyFinLeft);
	  this.babyBody.addChild(this.babyFinRight);
	  this.babyBody.addChild(this.babyFinBodyTop);
	  this.babyBody.addChild(this.babyFinBodyBottom);
	  
	  this.babyHead = new ModelRenderer(this, 0, 0);
	  this.babyHead.addBox(-0.5F, -1.0F, -1.0F, 1, 2, 1);
	  this.babyHead.setRotationPoint(0.0F, 0.0F, -2.0F);
	  this.babyHead.setTextureOffset(64, 32);
	  this.babyHead.mirror = true;
	  setRotation(this.babyHead, 0.0F, 0.0F, 0.0F);
	  
	  this.babyBody.addChild(this.babyHead);
	  
	  this.babyTailBig = new ModelRenderer(this, 16, 0);
	  this.babyTailBig.addBox(-0.5F, -1.0F, 0.0F, 1, 2, 2);
	  this.babyTailBig.setRotationPoint(0.0F, 0.0F, 1.5F);
	  this.babyTailBig.setTextureOffset(64, 32);
	  this.babyTailBig.mirror = true;
	  setRotation(this.babyTailBig, 0.0F, 0.0F, 0.0F);
	  
	  this.babyTailSmall = new ModelRenderer(this, 22, 0);
	  this.babyTailSmall.addBox(-0.5F, -0.5F, 0.0F, 1, 1, 1);
	  this.babyTailSmall.setRotationPoint(0.0F, 0.0F, 2.0F);
	  this.babyTailSmall.setTextureOffset(64, 32);
	  this.babyTailSmall.mirror = true;
	  setRotation(this.babyTailSmall, 0.0F, 0.0F, 0.0F);
	  
	  this.babyBody.addChild(this.babyTailBig);
	  this.babyTailBig.addChild(this.babyTailSmall);
	  
	  this.babyFinTailTop = new ModelRenderer(this, 24, 6);
	  this.babyFinTailTop.addBox(0.0F, -3.0F, -1.0F, 0, 3, 2);
	  this.babyFinTailTop.setRotationPoint(0.0F, -1.0F, 1.5F);
	  this.babyFinTailTop.setTextureOffset(64, 32);
	  this.babyFinTailTop.mirror = true;
	  setRotation(this.babyFinTailTop, 0.0F, 0.0F, 0.0F);
	  
	  this.babyFinTailBottom = new ModelRenderer(this, 28, 6);
	  this.babyFinTailBottom.addBox(0.0F, 0.0F, -1.0F, 0, 3, 2);
	  this.babyFinTailBottom.setRotationPoint(0.0F, 1.0F, 1.5F);
	  this.babyFinTailBottom.setTextureOffset(64, 32);
	  this.babyFinTailBottom.mirror = true;
	  setRotation(this.babyFinTailBottom, 0.0F, 0.0F, 0.0F);
	  
	  this.babyFinTail = new ModelRenderer(this, 32, 6);
	  this.babyFinTail.addBox(0.0F, -1.5F, 0.0F, 0, 3, 3);
	  this.babyFinTail.setRotationPoint(0.0F, 0.0F, 0.0F);
	  this.babyFinTail.setTextureOffset(64, 32);
	  this.babyFinTail.mirror = true;
	  setRotation(this.babyFinTail, 0.0F, 0.0F, 0.0F);
	  
	  this.babyTailBig.addChild(this.babyFinTailTop);
	  this.babyTailBig.addChild(this.babyFinTailBottom);
	  this.babyTailSmall.addChild(this.babyFinTail);
	}

	public void render(Entity entity, float time, float swingMax, float ageInTicks, float headPitch, float headYaw, float scaleFactor) {
		if ((entity instanceof EntityFish)) {
			EntityFish fish = (EntityFish)entity;
		    
		    GL11.glPushMatrix();
		    GL11.glTranslatef(0.0F, 21.0F * scaleFactor, 0.0F);
		    GL11.glScalef(fish.getScaleX(), fish.getScaleY(), fish.getScaleZ());
		    
		    setRotationAngles(time, swingMax, ageInTicks, headPitch, headYaw, scaleFactor, entity);
		    if (this.isChild) {
		      this.babyBody.render(scaleFactor);
		    } else {
		      this.adultBody.render(scaleFactor);
		    }
		    GL11.glPopMatrix();
		}
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float time, float swingMax, float ageInTicks, float headYaw, float headPitch, float scaleFactor, Entity entity) {
		float passiveSwing = MathHelper.cos(entity.distanceWalkedModified % 80 / 80.0F * 3.1415927F * 2.0F) * 0.1F;
		  
		this.adultHead.rotateAngleY = (MathHelper.cos(time) * swingMax * 0.8F + passiveSwing);
		this.babyHead.rotateAngleY = this.adultHead.rotateAngleY;
		this.adultHeadSmall.rotateAngleY = this.adultHead.rotateAngleY;
		  
		this.adultTailBig.rotateAngleY = (MathHelper.cos(time + 3.1415927F) * swingMax * 0.8F + passiveSwing);
		this.babyTailBig.rotateAngleY = this.adultTailBig.rotateAngleY;
		this.adultTailMid.rotateAngleY = this.adultTailBig.rotateAngleY;
		this.babyTailSmall.rotateAngleY = this.adultTailMid.rotateAngleY;
		this.adultTailSmall.rotateAngleY = this.adultTailMid.rotateAngleY;
		  
		this.adultBody.rotateAngleX = (-headPitch * 0.017453292F);
		this.babyBody.rotateAngleX = this.adultBody.rotateAngleX;
		if (ageInTicks > 0.5F) {
			this.adultBody.rotateAngleZ = 1.5707964F;
		} else {
			this.adultBody.rotateAngleZ = 0.0F;
		}
		this.babyBody.rotateAngleZ = this.adultBody.rotateAngleZ;
	}
	
}
