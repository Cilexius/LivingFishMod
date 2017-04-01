package livingfish.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
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

public class BlockTank extends Block {

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
    protected static final AxisAlignedBB AABB_WALL_DOWN = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1D, 1.0D);
    
    public static final AxisAlignedBB BOUNDINGBOX_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	  
	public BlockTank() {
		super(Material.WATER);
		this.setHardness(0.3F);
		this.setSoundType(SoundType.GLASS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLiquid.LEVEL, Integer.valueOf(0))
        		.withProperty(NORTH, Boolean.valueOf(false))
        		.withProperty(EAST, Boolean.valueOf(false))
        		.withProperty(SOUTH, Boolean.valueOf(false))
        		.withProperty(WEST, Boolean.valueOf(false))
        		.withProperty(DOWN, Boolean.valueOf(false))
        		.withProperty(UP, Boolean.valueOf(false)));
		this.setCreativeTab(CreativeTabs.DECORATIONS);
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
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
    
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }
    
    public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
        return motion;
    }
    
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, DOWN, UP, BlockLiquid.LEVEL});
    }
    
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
    
    public void addCollisionBoxToList(IBlockState blockState, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
    	blockState = this.getActualState(blockState, world, pos);
    	
        if (!((Boolean)blockState.getValue(NORTH)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        }

        if (!((Boolean)blockState.getValue(SOUTH)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
        }

        if (!((Boolean)blockState.getValue(EAST)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        }

        if (!((Boolean)blockState.getValue(WEST)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        }
        
        if (!((Boolean)blockState.getValue(DOWN)).booleanValue()) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_DOWN);
        }
    }
    
    public IBlockState getActualState(IBlockState blockState, IBlockAccess world, BlockPos pos) {
    	return blockState.withProperty(NORTH, this.canTankConnectTo(world, pos, EnumFacing.NORTH))
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
    
    public boolean isBlockSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
    	return true;
    }
    
}
