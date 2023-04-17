package pers.doremyswee.drmpractice.recipes;

import com.google.common.base.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public abstract class IAltarRecipe<T extends TileEntity>{
    public abstract boolean matchRecipe(T te);
    public abstract List<List<ItemStack>> getJEIItems();
    public abstract List<List<FluidStack>> getJEIFluids();
}
