package livingfish.utils;

import livingfish.entities.EntityClownFish;
import livingfish.entities.EntityCod;
import livingfish.entities.EntityFish;
import livingfish.entities.EntityPufferFish;
import livingfish.entities.EntitySalmon;
import livingfish.init.ModConfigs;
import livingfish.init.ModItems;
import livingfish.items.ItemFishBucket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FishUtils {
	
	public static ItemFishBucket getFishBucketByFish(EntityFish fish) {
		if (fish instanceof EntitySalmon) {
			return (ItemFishBucket) ModItems.fish_bucket_salmon;
		} else if (fish instanceof EntityClownFish) {
			return (ItemFishBucket) ModItems.fish_bucket_clownfish;
		} else if (fish instanceof EntityPufferFish) {
			return (ItemFishBucket) ModItems.fish_bucket_pufferfish;
		} else {
			return (ItemFishBucket) ModItems.fish_bucket_cod;
		}
	}
	
	public static EntityFish getFishByName(String fishName, World world) {
		if (fishName == "salmon") {
			return new EntitySalmon(world);
		} else if (fishName == "clownfish") {
			return new EntityClownFish(world);
		} else if (fishName == "pufferfish") {
			return new EntityPufferFish(world);
		} else {
			return new EntityCod(world);
		}
	}
	
	public static void spawnFishByName(String fishName, boolean isChild, World world, BlockPos pos) {
		if (pos != null) {
			EntityFish fish;
			if (fishName == "salmon") {
				fish = new EntitySalmon(world);
			} else if (fishName == "clownfish") {
				fish = new EntityClownFish(world);
			} else if (fishName == "pufferfish") {
				fish = new EntityPufferFish(world);
			} else {
				fish = new EntityCod(world);
			}
			if (isChild) {
				fish.setGrowingAge(-24000);
			}
	        fish.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5F, 0.0F, 0.0F);
	        world.spawnEntityInWorld(fish);
		}
	}
	
	public static int getFishSpawnWeightByName(String fishName) {
		int weight;
		if (fishName == "salmon") {
			weight = ModConfigs.salmonWeight;
		} else if (fishName == "clownfish") {
			weight = ModConfigs.clownfishWeight;
		} else if (fishName == "pufferfish") {
			weight = ModConfigs.pufferfishWeight;
		} else {
			weight = ModConfigs.codWeight;
		}
		return weight;
	}
	
}
