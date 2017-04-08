package livingfish.entities.ais;

import java.util.List;
import java.util.Random;

import livingfish.entities.EntityFish;
import livingfish.entities.EntityIronFishHook;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class EntityAIFindBaitedHook extends EntityAIBase {
	private Random rand = new Random();
	private EntityFish fish;
	private EntityIronFishHook hook;
	private double searchDistance;
	private double movementSpeed;
	private World world;

	public EntityAIFindBaitedHook(EntityFish fish, double speed, double searchDistance) {
		this.fish = fish;
		this.movementSpeed = speed;
		this.world = fish.worldObj;
		this.searchDistance = searchDistance;
		this.setMutexBits(1);
	}

	public boolean shouldExecute() {
		if ((this.fish.isChild()) || (this.rand.nextFloat() > 0.05F)) {
			return false;
		}
		this.hook = findHook();
		if (this.hook == null) {
			return false;
		}
		return true;
	}

	public boolean continueExecuting() {
		return (!this.fish.getNavigator().noPath()) && (this.hook.isEntityAlive());
	}

	public void startExecuting() {
		this.fish.getNavigator().tryMoveToEntityLiving(this.hook, this.movementSpeed);
	}

	private EntityIronFishHook findHook() {
		List<EntityIronFishHook> list = this.fish.worldObj.getEntitiesWithinAABB(EntityIronFishHook.class, this.fish.getEntityBoundingBox().expand(this.searchDistance, this.searchDistance, this.searchDistance));
		for (EntityIronFishHook hook : list) {
    		if ((hook.isInWater()) && (hook.hasBait ||  this.rand.nextInt(20) == 0)) {
    			if (this.fish.getDistanceSqToEntity(hook) < 1.0D && hook.fishOnHook == null) {
        			this.fish.onHook = true;
        			this.fish.angler = hook.angler;
        			hook.setFishOnHook(this.fish);
        			hook.fishingrod.removeBait = true;
    			}
    			return hook;
    		}
		}
		return null;
	}
}
