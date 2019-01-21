package com.obcletter.industryexpansion.block;

import com.obcletter.industryexpansion.IndustryExpansion;
import com.obcletter.industryexpansion.client.ModGuiHandler;
import com.obcletter.industryexpansion.client.tileentity.TileEntityFreezer;
import com.obcletter.industryexpansion.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockFreezer extends BlockTileEntity<TileEntityFreezer> {

	public BlockFreezer(String name) {
		super(Material.ANVIL, name);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (!playerIn.isSneaking()) {
				playerIn.openGui(IndustryExpansion.instance, ModGuiHandler.FREEZER, worldIn, pos.getX(), pos.getY(),
						pos.getZ());
			}
		}
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!worldIn.isRemote) {
			if (worldIn.isBlockIndirectlyGettingPowered(pos) >= 7) {
				TileEntityFreezer freezerTile = getTileEntity(worldIn, pos);
				ItemStack stack = freezerTile.getInventory().getStackInSlot(0);
				System.out.println("powered");
				if (!stack.isEmpty()) {
					if (stack.getItem() == ModItems.raw_hardened_redstone) {
						freezerTile.getInventory().extractItem(0, stack.getCount(), false);
						ItemStack strk = new ItemStack(ModItems.redstone_core, stack.getCount());
						freezerTile.getInventory().insertItem(0, strk, false);
					}
				}

			}
		}
	}

	@Override
	public Class getTileEntityClass() {
		return TileEntityFreezer.class;
	}

	@Override
	public TileEntityFreezer createTileEntity(World world, IBlockState state) {
		return new TileEntityFreezer();
	}

	@Override
	public BlockFreezer setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityFreezer tile = getTileEntity(worldIn, pos);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
		ItemStack stack = itemHandler.getStackInSlot(0);
		if (!stack.isEmpty()) {
			EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
			worldIn.spawnEntity(item);
		}
		super.breakBlock(worldIn, pos, state);
	}

}
