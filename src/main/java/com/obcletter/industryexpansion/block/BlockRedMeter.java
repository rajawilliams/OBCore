package com.obcletter.industryexpansion.block;

import com.obcletter.industryexpansion.client.tileentity.TileEntityRedMeter;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedMeter extends BlockTileEntity<TileEntityRedMeter> {

	public BlockRedMeter(String name) {
		super(Material.ANVIL, name);
	}

	@Override
	public Class<TileEntityRedMeter> getTileEntityClass() {
		return TileEntityRedMeter.class;
	}

	@Override
	public TileEntityRedMeter createTileEntity(World world, IBlockState state) {
		return new TileEntityRedMeter();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			TileEntityRedMeter meter = getTileEntity(worldIn, pos);
			if (facing == EnumFacing.NORTH) {
				meter.incrementCount();
			} else if (facing == EnumFacing.SOUTH) {
				meter.decrementCount();
			}
			worldIn.notifyNeighborsOfStateChange(pos, state.getBlock(), true);
			playerIn.sendMessage(new TextComponentString("Current output: " + meter.getCount()));
		}
		return true;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		TileEntityRedMeter meter = getTileEntity(blockAccess, pos);
		return meter.getCount();
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public BlockRedMeter setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

}
