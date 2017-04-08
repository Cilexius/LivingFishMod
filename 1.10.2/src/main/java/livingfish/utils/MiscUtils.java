package livingfish.utils;

import livingfish.blocks.BlockTank;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiscUtils {

	public static boolean isWater(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Material material = state.getMaterial();
		if (material == Material.WATER && ((Integer)state.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isTank(World world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() instanceof BlockTank;
	}
	
}