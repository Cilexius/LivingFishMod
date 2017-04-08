package livingfish.utils;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class MathUtils {
	
	/**
	 * Returns the distance between two Entities
	 * @param entityA
	 * @param entityB
	 * @param excludeY
	 * @return Distance between the two Entities
	 */
	public static double getDistanceSqEntitytoEntity(Entity entityA, Entity entityB, boolean excludeY) {
		double distanceX = entityA.posX - entityB.posX;
		double distanceZ = entityA.posZ - entityB.posZ;
		double distanceSq = 0.0D;
		if (excludeY) {
			distanceSq = MathUtils.getDistanceSq(distanceX, distanceZ);
		} else {
			double distanceY = (entityA.posY + entityA.getEyeHeight()) - (entityB.posY + entityB.getEyeHeight());
			distanceSq = MathUtils.getDistanceSq(distanceX, distanceY, distanceZ);
		}
		return distanceSq;
	}
	
	/**
	 * Returns the distance between an Entity and a Block Position
	 * @param entity
	 * @param pos
	 * @return Distance between an Entity and a Block Position
	 */
	public static double getDistanceSqEntitytoBlockPos(Entity entity, BlockPos pos) {
		double distanceX = entity.posX - pos.getX();
		double distanceY = (entity.posY + entity.getEyeHeight()) - pos.getY();
		double distanceZ = entity.posZ - pos.getZ();
		double distanceSq = MathUtils.getDistanceSq(distanceX, distanceY, distanceZ);
		return distanceSq;
	}

	public static double getDistanceSq(double distanceX, double distanceY, double distanceZ) {
		double distanceSq = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
		return distanceSq;
	}
	
	public static double getDistanceSq(double distanceX, double distanceZ) {
		double distanceSq = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
		return distanceSq;
	}
	
	/**
	 * Pulls the an Entity to another Entity.
	 * @param puller The Entity that pulls (Doesn't move itself)
	 * @param modifier The length, which is subtracted from the distance
	 * @param pulledEntity The Entity that is pulled (This one will move)
	 */
	public static void pullEntitytoEntity(Entity puller, double modifier, Entity pulledEntity) {
		double distanceX = puller.posX - pulledEntity.posX;
		double distanceY = (puller.posY + puller.getEyeHeight()) - (pulledEntity.posY + puller.getEyeHeight());
		double distanceZ = puller.posZ - pulledEntity.posZ;
		double distanceSq = MathUtils.getDistanceSqEntitytoEntity(puller, pulledEntity, false);
		
		/*
		 * Figures out for each axis, whether puller or pulledEntity is closer to the origin.
		 * 
		 * 0|----------------puller-------------pulledEntity------------------------------------------>for (x/y/z) each
		 * 
		 * 								or
		 * 
		 * 0|----------------pulledEntity-------------puller------------------------------------------>for (x/y/z) each
		 * 
		 * Now, if Puller is closer to the origin, then the distance is < 0. Therefore we have to change the sign in front of the modifier to minus.
		 */
		double modifierX = modifier;
		double modifierY = modifier;
		double modifierZ = modifier;
		
		if (distanceX < 0) {
			modifierX *= -1;
		}
		if (distanceY < 0) {
			modifierY *= -1;
		}
		if (distanceZ < 0) {
			modifierZ *= -1;
		}
		
		pulledEntity.setPosition(pulledEntity.posX + modifierX, pulledEntity.posY + pulledEntity.getEyeHeight() + modifierY, pulledEntity.posZ + modifierZ);

	}
	
	public static int getRandomAmountBetweenMinAndMax(int min, int max) {
		
		Random rand = new Random();
		int amount;
		
		if (max <= 0) {
			amount = 0;
		} else if (max == 1) {
			amount = 1;
		} else {
			amount = rand.nextInt(max - min) + min;
		}
		return amount;
	}
	
    public static RayTraceResult rayTrace(Entity entity, double blockReachDistance, boolean stopOnLiquid, float partialTicks) {
        Vec3d vec3d = entity.getPositionEyes(partialTicks);
        Vec3d vec3d1 = entity.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * blockReachDistance, vec3d1.yCoord * blockReachDistance, vec3d1.zCoord * blockReachDistance);
        return entity.worldObj.rayTraceBlocks(vec3d, vec3d2, stopOnLiquid, false, true);
    }
	
}
