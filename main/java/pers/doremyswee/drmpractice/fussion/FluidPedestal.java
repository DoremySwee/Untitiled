package pers.doremyswee.drmpractice.fussion;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pers.doremyswee.drmpractice.BlockBase;

import static net.minecraft.inventory.InventoryHelper.spawnItemStack;

public class FluidPedestal extends BlockBase {
    public FluidPedestal() {
        super(Material.IRON, "fluid_pedestal");
        setHardness(1);
        setHarvestLevel("axe",1);
        setResistance(1);
        setLightOpacity(1);
        setTE(FluidPedestalTE.class);
        ClientRegistry.bindTileEntitySpecialRenderer(FluidPedestalTE.class, new FluidPedestalTESR());
        reg();
    }
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new FluidPedestalTE();
    }
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        ItemStack item=new ItemStack(getItemBlock());
        if(worldIn.getTileEntity(pos)instanceof FluidPedestalTE){
            FluidStack stack=((FluidPedestalTE)worldIn.getTileEntity(pos)).content;
            if(stack!=null)item.setTagCompound(stack.writeToNBT(new NBTTagCompound()));
        }
        spawnItemStack(worldIn, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, item);
    }
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess blockAccess, BlockPos pos, IBlockState state, int fortune) {}
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
