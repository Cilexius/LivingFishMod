package livingfish.utils;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MiscUtils {

	public static boolean isWater(World world, BlockPos pos) {
		IBlockState blockState = world.getBlockState(pos);
		Material material = blockState.getMaterial();
		if (material == Material.WATER && ((Integer)blockState.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
			return true;
		}
		return false;
	}
	
}
