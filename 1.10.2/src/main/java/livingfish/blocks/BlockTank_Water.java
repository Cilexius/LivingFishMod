package livingfish.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import livingfish.LivingFish;
import livingfish.init.ModBlocks;
import livingfish.init.ModConfigs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTank_Water extends Block {
	
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool UP = PropertyBool.create("up");
	
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_DOWN = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.02D, 1.0D);
    
    public static final AxisAlignedBB BOUNDINGBOX_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    
	public BlockTank_Water() {
		super(Material.WATER);
		this.setHardness(0.4F);
		this.setSoundType(SoundType.GLASS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLiquid.LEVEL, Integer.valueOf(0))
        													.withProperty(NORTH, Boolean.valueOf(false))
        													.withProperty(EAST, Boolean.valueOf(false))
        													.withProperty(SOUTH, Boolean.valueOf(false))
        													.withProperty(WEST, Boolean.valueOf(false))
        													.withProperty(DOWN, Boolean.valueOf(false))
        													.withProperty(UP, Boolean.valueOf(false)));
		this.setCreativeTab(LivingFish.instance.tab);
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
        return state.getBoundingBox(world, pos).offset(pos);
    }
    
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return true;
    }
    
    public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
        return false;
    }
    
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, DOWN, UP, BlockLiquid.LEVEL});
    }
    
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
    
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity) {
    	state = this.getActualState(state, world, pos);
    	
        if (!((Boolean)state.getValue(NORTH)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        }

        if (!((Boolean)state.getValue(SOUTH)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
        }

        if (!((Boolean)state.getValue(EAST)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        }

        if (!((Boolean)state.getValue(WEST)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        }
        
        if (!((Boolean)state.getValue(DOWN)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_DOWN);
        }
    }
    
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
    	return state.withProperty(NORTH, this.canTankConnectTo(world, pos, EnumFacing.NORTH))
    			.withProperty(EAST, this.canTankConnectTo(world, pos, EnumFacing.EAST))
    			.withProperty(SOUTH, this.canTankConnectTo(world, pos, EnumFacing.SOUTH))
    			.withProperty(WEST, this.canTankConnectTo(world, pos, EnumFacing.WEST))
    			.withProperty(DOWN, this.canTankConnectTo(world, pos, EnumFacing.DOWN))
    			.withProperty(UP, this.canTankConnectTo(world, pos, EnumFacing.UP))
    			.withProperty(BlockLiquid.LEVEL, Integer.valueOf(8));
    }
    
    public boolean canTankConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
    	BlockPos neighbourPos = pos.offset(facing);
    	IBlockState neighbourState = world.getBlockState(neighbourPos);
    	return neighbourState.getBlock() == this;
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return BOUNDINGBOX_AABB;
    }
    
    @Override
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
    	
        Vec3d vec3d = start.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
        Vec3d vec3d1 = end.subtract((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());

    	RayTraceResult rayTraceWall = null;
    	List<AxisAlignedBB> walls = new ArrayList <AxisAlignedBB>();
    	
    	state = this.getActualState(state, world, pos);
    	
    	if (!((Boolean)state.getValue(DOWN)).booleanValue()) {
    		walls.add(AABB_WALL_DOWN);
    	}
    	if(!((Boolean)state.getValue(NORTH)).booleanValue()) {
    		walls.add(AABB_WALL_NORTH);
    	}
    	if (!((Boolean)state.getValue(SOUTH)).booleanValue()) {
    		walls.add(AABB_WALL_SOUTH);
    	}
    	if (!((Boolean)state.getValue(EAST)).booleanValue()) {
    		walls.add(AABB_WALL_EAST);
    	}
    	if (!((Boolean)state.getValue(WEST)).booleanValue()) {
    		walls.add(AABB_WALL_WEST);
    	}
    	
    	for (AxisAlignedBB wall : walls) {
    		rayTraceWall = wall.calculateIntercept(vec3d, vec3d1);
    		if (rayTraceWall != null) {
    			rayTraceWall = new RayTraceResult(rayTraceWall.hitVec.addVector((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), rayTraceWall.sideHit, pos);
    			break;
    		}
    	}
    	
    	return rayTraceWall;
    }
    
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock) {
    }
    
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	if (ModConfigs.dropTank) {
    		if (!ModConfigs.looseWaterOnDestruction) {
        		return Item.getItemFromBlock(ModBlocks.tank_water);
    		} else {
        		return Item.getItemFromBlock(ModBlocks.tank);
    		}

    	}
        return null;
    }
    
    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
    	if (ModConfigs.looseWaterOnDestruction) {
    		world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), 11);
    	}
    }
    
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
    	if (ModConfigs.looseWaterOnDestruction) {
    		world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), 11);
    	}
    }
    
}
