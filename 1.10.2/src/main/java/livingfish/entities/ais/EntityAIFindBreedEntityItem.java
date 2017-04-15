package livingfish.entities.ais;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

public class EntityAIFindBreedEntityItem extends EntityAIBase {
	
	private Random rand = new Random();
	private EntityAnimal animal;
	private EntityItem breedItem;
	private double searchDistance;
	private double movementSpeed;
	private World world;
	
	public EntityAIFindBreedEntityItem(EntityAnimal animal, double speed, double searchDistance) {
		this.animal = animal;
	    this.movementSpeed = speed;
	    this.world = animal.worldObj;
	    this.searchDistance = searchDistance;
	    this.setMutexBits(1);
	}

	public boolean shouldExecute() {
		if (this.animal.isInLove() && (!animal.isInWater()) || (this.rand.nextFloat() > 0.05F)) {
			return false;
		}
		this.breedItem = findBreedItem();
		if (this.breedItem == null) {
			return false;
		}
		return true;
	}
	
	public boolean continueExecuting() {
		return ((!this.animal.getNavigator().noPath()) && (this.breedItem.isEntityAlive())) ;
	}
	
    public void startExecuting() {
    	this.animal.getNavigator().tryMoveToEntityLiving(this.breedItem, this.movementSpeed);
    }
    
    private EntityItem findBreedItem() {
    	List<EntityItem> list = this.animal.worldObj.getEntitiesWithinAABB(EntityItem.class, this.animal.getEntityBoundingBox().expand(this.searchDistance, this.searchDistance, this.searchDistance));
    	for (EntityItem item : list) {
        	if (item.isInWater() && this.animal.getDistanceToEntity(item) >= 0.1D) {
        		if ((item.getEntityItem() != null) && (this.animal.isBreedingItem(item.getEntityItem()))) {
        			return item;
        		}
        	}
        }
        return null;
    }
}
