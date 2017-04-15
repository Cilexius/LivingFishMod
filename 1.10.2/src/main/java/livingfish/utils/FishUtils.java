package livingfish.utils;

import java.util.ArrayList;
import java.util.List;

import livingfish.entities.EntityClownFish;
import livingfish.entities.EntityCod;
import livingfish.entities.EntityFish;
import livingfish.entities.EntityPufferFish;
import livingfish.entities.EntitySalmon;
import livingfish.init.ModConfigs;
import livingfish.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FishUtils {
	
	public static Item getFishBucketByFish(EntityFish fish) {
		Item fishBucket = null;
		if (fish instanceof EntityCod) {
			fishBucket = ModItems.fish_bucket_cod;
		} else if (fish instanceof EntitySalmon) {
			fishBucket = ModItems.fish_bucket_salmon;
		} else if (fish instanceof EntityClownFish) {
			fishBucket = ModItems.fish_bucket_clownfish;
		} else if (fish instanceof EntityPufferFish) {
			fishBucket = ModItems.fish_bucket_pufferfish;
		}
		return fishBucket;
	}
	
	public static EntityFish getFishByName(String fishName, World world) {
		EntityFish fish = null;
		if (fishName == "cod") {
			fish = new EntityCod(world);
		} else if (fishName == "salmon") {
			fish = new EntitySalmon(world);
		} else if (fishName == "clownfish") {
			fish = new EntityClownFish(world);
		} else if (fishName == "pufferfish") {
			fish = new EntityPufferFish(world);
		}
		return fish;
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
	
	public static boolean isBait(Item item) {
		List<Item> baits = new ArrayList<Item>();
		baits.add(Items.WHEAT_SEEDS);
		baits.add(Items.MELON_SEEDS);
		baits.add(Items.PUMPKIN_SEEDS);
		baits.add(Items.BEETROOT_SEEDS);
		if (baits.contains(item)) {
			return true;
		}
		return false;
	}
	
}
