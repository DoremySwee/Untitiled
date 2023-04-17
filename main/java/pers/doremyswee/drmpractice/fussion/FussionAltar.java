package pers.doremyswee.drmpractice.fussion;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import pers.doremyswee.drmpractice.BlockBase;
import pers.doremyswee.drmpractice.RandomAltars;

import static net.minecraft.inventory.InventoryHelper.spawnItemStack;

@Mod.EventBusSubscriber(modid= RandomAltars.ID)
public class FussionAltar extends BlockBase {
    //A simple Altar on which items are put, with pedestals containing liquids from which liquid ingredients are extracted.
    public FussionAltar() {
        super(Material.WOOD,"fussion_altar");
        setTE(FussionAltarTE.class);
        setHardness(1);
        setHarvestLevel("axe",1);
        setResistance(1);
        ClientRegistry.bindTileEntitySpecialRenderer(FussionAltarTE.class, new AltarTESR());
        setLightOpacity(1);
        reg();
    }
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new FussionAltarTE();
    }
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.isRemote) {
            FussionAltarTE te=((FussionAltarTE) worldIn.getTileEntity(pos));
            boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
            boolean flag1 = te.powered;
            if (flag && !flag1) {
                te.progress = 0;
            }
            te.powered= worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
        }
    }
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        FussionAltarTE te=((FussionAltarTE)worldIn.getTileEntity(pos));
        for(ItemStack i:te.contents){
            spawnItemStack(worldIn, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, i);
        }
    }
}
