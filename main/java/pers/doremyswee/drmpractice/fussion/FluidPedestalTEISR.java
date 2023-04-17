package pers.doremyswee.drmpractice.fussion;

import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class FluidPedestalTEISR extends TileEntityItemStackRenderer {
    @Override
    public void renderByItem(ItemStack stack) {
        FluidPedestalTESR.render(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("content")));
    }
}
