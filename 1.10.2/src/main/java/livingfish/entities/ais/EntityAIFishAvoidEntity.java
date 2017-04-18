package livingfish.entities.ais;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import livingfish.utils.BlockUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

public class EntityAIFishAvoidEntity<T extends Entity> extends EntityAIBase {
    private final Predicate<Entity> canBeSeenSelector;
    /** The entity we are attached to */
    protected EntityCreature entity;
    private final double farSpeed;
    private final double nearSpeed;
    protected T closestLivingEntity;
    private final float avoidDistance;
    /** The PathEntity of our entity */
    private Path entityPathEntity;
    /** The PathNavigate of our entity */
    private final PathNavigate entityPathNavigate;
    private final Class<T> classToAvoid;
    private final Predicate <? super T > avoidTargetSelector;

    public EntityAIFishAvoidEntity(EntityCreature Entity, Class<T> classToAvoid, float avoidDistance, double farSpeed, double nearSpeed) {
        this(Entity, classToAvoid, Predicates.<T>alwaysTrue(), avoidDistance, farSpeed, nearSpeed);
    }

    public EntityAIFishAvoidEntity(EntityCreature entity, Class<T> classToAvoid, Predicate <? super T > avoidTargetSelector, float avoidDistance, double farSpeed, double nearSpeed) {
        this.canBeSeenSelector = new Predicate<Entity>() {
            public boolean apply(@Nullable Entity entity) {
                return entity.isEntityAlive() && EntityAIFishAvoidEntity.this.entity.getEntitySenses().canSee(entity);
            }
        };
        this.entity = entity;
        this.classToAvoid = classToAvoid;
        this.avoidTargetSelector = avoidTargetSelector;
        this.avoidDistance = avoidDistance;
        this.farSpeed = farSpeed;
        this.nearSpeed = nearSpeed;
        this.entityPathNavigate = entity.getNavigator();
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        List<T> list = this.entity.worldObj.<T>getEntitiesWithinAABB(this.classToAvoid, this.entity.getEntityBoundingBox().expand((double)this.avoidDistance, 3.0D, (double)this.avoidDistance), Predicates.and(new Predicate[] {EntitySelectors.CAN_AI_TARGET, this.canBeSeenSelector, this.avoidTargetSelector}));

        if (list.isEmpty()) {
            return false;
        } else {
            this.closestLivingEntity = list.get(0);
            Vec3d vec3d = null;
            if (this.closestLivingEntity.isInWater()) {
            	vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, new Vec3d(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
            }

            if (vec3d == null) {
                return false;
            } else if (this.closestLivingEntity.getDistanceSq(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.entity)) {
                return false;
            } else {
                this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
                return this.entityPathEntity != null;
            }
        }
    }

    public boolean continueExecuting() {
        return !this.entityPathNavigate.noPath() && this.entity.isInWater();
    }

    public void startExecuting() {
        float speedModifier = BlockUtils.isTank_Water(this.entity.worldObj, this.entity.getPosition()) ? 0.7F : 1.0F;
        this.entityPathNavigate.setPath(this.entityPathEntity, (this.farSpeed * speedModifier));
    }

    public void resetTask() {
        this.closestLivingEntity = null;
    }
}