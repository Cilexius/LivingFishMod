package livingfish.utils;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class MathUtils {
	
	/**
	 * Returns the distance between two Entities
	 * @param entityA
	 * @param entityB
	 * @return Distance between the two Entities
	 */
	public static double getDistanceSqEntitytoEntity(Entity entityA, Entity entityB) {
		double distanceX = entityA.posX - entityB.posX;
		double distanceY = entityA.posY - entityB.posY;
		double distanceZ = entityA.posZ - entityB.posZ;
		double distanceSq = MathUtils.getDistanceSq(distanceX, distanceY, distanceZ);
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
		double distanceY = entity.posY - pos.getY();
		double distanceZ = entity.posZ - pos.getZ();
		double distanceSq = MathUtils.getDistanceSq(distanceX, distanceY, distanceZ);
		return distanceSq;
	}

	public static double getDistanceSq(double distanceX, double distanceY, double distanceZ) {
		double distanceSq = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
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
		double distanceY = puller.posY - pulledEntity.posY;
		double distanceZ = puller.posZ - pulledEntity.posZ;
		double distanceSq = MathUtils.getDistanceSqEntitytoEntity(puller, pulledEntity);
		
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
	
}
