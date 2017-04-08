package livingfish.entities.ais;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class EntityAIWanderFish extends EntityAIBase {
	
    private final EntityCreature creature;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private final double speed;
    private int executionChance;
    private boolean mustUpdate;

    public EntityAIWanderFish(EntityCreature creature, double speed) {
        this(creature, speed, 120);
    }

    public EntityAIWanderFish(EntityCreature creature, double speed, int chance) {
        this.creature = creature;
        this.speed = speed;
        this.executionChance = chance;
        this.setMutexBits(1);
    }

    public boolean shouldExecute() {
        if (!this.mustUpdate) {
            if (this.creature.getAge() >= 100) {
                return false;
            }

            if (this.creature.getRNG().nextInt(this.executionChance) != 0) {
                return false;
            }
        }

        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.creature, 10, 7);

        if (vec3d == null) {
            return false;
        } else {
            this.xPosition = vec3d.xCoord;
            this.yPosition = (vec3d.yCoord + this.creature.getEyeHeight());
            this.zPosition = vec3d.zCoord;
            this.mustUpdate = false;
            return true;
        }
    }

    public boolean continueExecuting() {
        return !this.creature.getNavigator().noPath();
    }

    public void startExecuting() {
        this.creature.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }
    
}
