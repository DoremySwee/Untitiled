package pers.doremyswee.drmpractice.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class NBTHelper {
    public static BlockPos formBlockPos(NBTTagCompound t){
        return new BlockPos(t.getInteger("X"),t.getInteger("Y"),t.getInteger("Z"));
    }
    public static NBTTagCompound serializeBlockPos(BlockPos p){
        NBTTagCompound tag=new NBTTagCompound();
        tag.setInteger("X",p.getX());
        tag.setInteger("Y",p.getY());
        tag.setInteger("Z",p.getZ());
        return tag;
    }
}
