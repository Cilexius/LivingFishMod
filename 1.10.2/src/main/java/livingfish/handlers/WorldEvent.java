package livingfish.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import livingfish.entities.EntityFish;
import livingfish.init.ModConfigs;
import livingfish.utils.BlockUtils;
import livingfish.utils.FishUtils;
import livingfish.utils.MathUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

public class WorldEvent {

	public List<String> fishSpawnList= new ArrayList<String>();
	public List<String> fishSpawnListWithWeight= new ArrayList<String>();
	public int timer;
	public int range = ModConfigs.range;
	public int maxFishPerPlayer = ModConfigs.maxFishPerPlayer;
	public int min;
	public int max;
	
	@SubscribeEvent
	public void worldTick(WorldTickEvent event) {
		if (event.side.isServer()) {
			World world = event.world;
			this.trySpawn(world);
		}
	}
	
	public void trySpawn(World world) {
		
		if (timer > 0) {
			timer--;
			return;
		} else {
			if (world.isDaytime()) {
				if (ModConfigs.timerAtDay == -1) {
					return;
				} else {
					timer = (ModConfigs.timerAtDay);
				}
			} else {
				if (ModConfigs.timerAtNight == -1) {
					return;

				} else {
					timer = (ModConfigs.timerAtNight);
				}
			}
		}
		
		if (this.fishSpawnList.size() <= 0 || this.fishSpawnListWithWeight.size() <= 0) {
			this.createFishSpawnList();
		}
		
		for (EntityPlayer player : world.playerEntities) {

			BlockPos pos = player.getPosition();
			
			if (this.getFishAmountAroundPlayer(player) < this.maxFishPerPlayer) {
				if (this.fishSpawnListWithWeight.size() > 0) {
					Random rand = new Random();				
					String fishName = this.fishSpawnListWithWeight.get(rand.nextInt(this.fishSpawnListWithWeight.size()));
					this.getFishGroupWeightByName(fishName);
					int amount = MathUtils.getRandomAmountBetweenMinAndMax(this.min, this.max);
					
					if (amount > 0) {
						BlockPos spawnPos = this.getValidSpawnPos(world, pos, this.range);
						for (int i = 0; i < amount; i++) {
							FishUtils.spawnFishByName(fishName, false, world, spawnPos);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Counts Fish around Player and returns an amount
	 */
	public int getFishAmountAroundPlayer(EntityPlayer player) {
		List<EntityFish> fishList = new ArrayList<EntityFish>();
		int checkDistance = (this.range * 2);
		List<EntityFish> list = player.worldObj.getEntitiesWithinAABB(EntityFish.class, player.getEntityBoundingBox().expand(checkDistance, checkDistance, checkDistance));
		for (EntityFish fish : list) {
			fishList.add(fish);
		}
		return fishList.size();
	}
	
	public void createFishSpawnList() {
		this.fishSpawnList.add("cod");
		this.fishSpawnList.add("salmon");
		this.fishSpawnList.add("clownfish");
		this.fishSpawnList.add("pufferfish");
		
		this.createFishSpawnListWithWeight();
	}
	
	public void createFishSpawnListWithWeight() {
		for (int i = 0; i < this.fishSpawnList.size(); i++) {
			String fish = this.fishSpawnList.get(i);
			for (int j = 0; j < FishUtils.getFishSpawnWeightByName(fish); j++) {
				this.fishSpawnListWithWeight.add(fish);
			}
		}
	}
	
	public void getFishGroupWeightByName(String name) {
		if (name == "cod") {
			this.min = ModConfigs.codMin;
			this.max = ModConfigs.codMax;
		} else if (name == "salmon"){
			this.min = ModConfigs.salmonMin;
			this.max = ModConfigs.salmonMax;
		} else if (name == "clownfish"){
			this.min = ModConfigs.clownfishMin;
			this.max = ModConfigs.clownfishMax;
		} else {
			this.min = ModConfigs.pufferfishMin;
			this.max = ModConfigs.pufferfishMax;
		}
	}
	
	public static BlockPos getValidSpawnPos(World world, BlockPos pos, int spawnRange) {
		
		if (pos.getY() >= 40 && pos.getY() <= 140) {
			
			Random rand = new Random();
			
			//x
			int minX = pos.getX() - spawnRange;
			int maxX = pos.getX() + spawnRange;
			int minMaxX = maxX - minX;
			if (minMaxX < 0) {
				minMaxX *= -1;
			}
			int randX = rand.nextInt(minMaxX);
			int x = minX + randX;
			
			//z
			int minZ = pos.getZ() - spawnRange;
			int maxZ = pos.getZ() + spawnRange;
			int minMaxZ = maxZ - minZ;
			if (minMaxZ < 0) {
				minMaxZ *= -1;
			}
			int randZ = rand.nextInt(minMaxZ);
			int z = minZ + randZ;
			
			//y
			int minY = pos.getY() - 14;
			int maxY = pos.getY() + 4;
			int minMaxY = maxY - minY;
			if (minMaxY < 0) {
				minMaxY *= -1;
			}
			int randY = rand.nextInt(minMaxY);
			int y = minY + randY;
			
			BlockPos spawnPos = new BlockPos(x, y, z);
			
			int safetyDistance = ModConfigs.safetyDistance;
			if (world.getClosestPlayer(x, y, z, safetyDistance, false) == null && BlockUtils.isWater(world, spawnPos)) {
				return spawnPos;
			}
			
		}
		
		return null;
	}
	
}
